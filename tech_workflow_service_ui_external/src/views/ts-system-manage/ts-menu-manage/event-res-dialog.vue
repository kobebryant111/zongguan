<template>
  <div>
    <el-dialog
        title="按钮事件"
        width="80%"
        append-to-body
        top="5vh"
        :visible="visible"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :fullscreen="false"
        @open="onopen"
        @close="onclose"
    >

      <el-form ref="searchForm" v-model="searchForm" :inline="true" size="small" label-width="100px">
        <el-form-item prop="eventNameLike" label="事件名称">
          <el-input v-model="searchForm.eventNameLike" placeholder="请输入事件名称"/>
        </el-form-item>
        <el-form-item prop="urlLike" label="事件url">
          <el-input v-model="searchForm.urlLike" placeholder="请输入事件url"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" icon="el-icon-search" @click="search">查询</el-button>
          <el-button type="primary" size="small" icon="el-icon-refresh" @click="resetSearchFrm">重置</el-button>
        </el-form-item>
      </el-form>

      <el-button-group style="margin-bottom: 10px">
        <el-button type="primary" icon="el-icon-plus" size="small" @click="addRes">新增</el-button>
        <el-button type="primary" icon="el-icon-edit" size="small" @click="modifyRes">修改</el-button>
        <el-button type="primary" icon="el-icon-delete" size="small" @click="deleteRes">删除</el-button>
<!--        <el-button type="primary" icon="el-icon-refresh" size="small" @click="syncRes">同步</el-button>-->
      </el-button-group>

      <ts-multi-table
          ref="eventsTable"
          :query="searchForm"
          :options="tableOptions"
          :pagination="pagination">
        <el-table-column prop="eventCode" label="事件编号"></el-table-column>
        <el-table-column prop="eventName" label="事件名称"></el-table-column>
        <el-table-column prop="url" label="事件URL"></el-table-column>
      </ts-multi-table>

      <div style="text-align: right;margin-top: 10px">
        <el-button type="primary" size="small" @click="sure">确认</el-button>
        <el-button type="default" size="small" @click="cancel">取消</el-button>
      </div>
    </el-dialog>
    <ts-multi-dialog
        :visible.sync="dialogVisible"
        :options="dialogOptions">
    </ts-multi-dialog>
  </div>
</template>

<script>
import EventForm from './event-form'
export default {
  name: 'EventResDialog',
  components: {},
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    menuCode: {
      type: String,
      default: ''
    }
  },
  computed: {
    judgeMenuCode() {
      return this.menuCode
    }
  },
  data() {
    return {
      modifyAdd: false,
      inlineVisible: false,
      eventCodes: [],
      // 表单
      dialogVisible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      dialogOptions: {
        title: '事件维护', // [必填]
        width: '850px', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        resource: EventForm,
        data: {
          eventCode: '', // 事件编号
          url: '', // 事件url
          eventName: ''// 事件名称
        },
        beforeClose: (data, done) => { done() }, // [可不填] 关闭右上角弹框前的回调
        callbacks: [(data, done) => { done() }, this.confirm] // [必填] 若取消按钮没有回调，可写默认值null 如[null, this.submit]
      },
      // 搜索
      searchForm: {
        eventNameLike: '',
        urlLike: ''
      },
      // 表格属性设置
      tableOptions: {
        type: 'checkbox',
        apiName: this.$api.eventResQuery,
        paramsOrdata: 'data',
        apiRequiredParams: {
          judgeMenuCode: this.judgeMenuCode
        }
      },
      pagination: {
        layout: 'total, prev, pager, next'
      }
    }
  },
  mounted() {
  },
  methods: {
    // 用户搜索
    search() {
      this.eventCodes = []
      this.$refs['eventsTable'].serchHandel()
    },
    resetSearchFrm() {
      this.searchForm = {}
    },
    getEventRes() {
      // 查询
      this.searchForm.eventNameLike = ''
      this.searchForm.urlLike = ''
      this.tableOptions.apiRequiredParams.judgeMenuCode = this.judgeMenuCode
      if (this.$refs['eventsTable']) {
        this.$refs['eventsTable'].refreshDataSource()
      }
    },
    addRes() {
      // 新增
      Object.keys(this.dialogOptions.data).forEach(key => (this.dialogOptions.data[key] = ''))
      this.dialogVisible = true
      this.modifyAdd = false
    },
    confirm(data, done) {
      // TODO
      // 新增，修改事件
      let param = data
      if (this.modifyAdd) {
        param = Object.assign(param, { id: this.eventCodes[0].id })
      }
      console.log('-122332param', param)
      this.$request({
        url: this.$api.eventResSave,
        data: param
      }).then((response) => {
        this.$message.success(response.description)
        this.onopen()
        done()
      })
    },
    modifyRes() {
      // 修改 TODO
      this.eventCodes = this.$refs['eventsTable'].getSelectedData()
      if (this.eventCodes.length === 1) {
        if (this.checkCode()) return
        this.modifyAdd = true
        this.dialogVisible = true
        this.dialogOptions.data.eventCode = this.eventCodes[0].eventCode
        this.dialogOptions.data.eventName = this.eventCodes[0].eventName
        this.dialogOptions.data.url = this.eventCodes[0].url
      } else {
        this.$message.warning('请选择一条记录')
      }
    },
    deleteRes() {
      this.eventCodes = this.$refs['eventsTable'].getSelectedData()
      // 删除
      if (this.eventCodes.length === 1) {
        if (this.checkCode()) return
        this.$confirm('您确定删除这条记录吗？', '提示').then(() => {
          this.$request({
            url: this.$api.eventResDelete,
            data: {
              id: this.eventCodes[0].id,
              all_turn_field: 'id'
            }
          }).then((response) => {
            this.$message.success(response.description)
            this.onopen()
          })
        }).catch(() => {
        })
      } else {
        this.$message.warning('请选择一条记录')
      }
    },
    syncRes() {
      // 同步
      this.$confirm('将会更新事件信息，确认同步？', '提示').then(() => {
        this.$request({
          url: this.$api.eventResSync
        }).then((response) => {
          this.$message.success(response.description)
          this.onopen()
        })
      }).catch(() => {
      })
    },
    sure() {
      this.eventCodes = this.$refs['eventsTable'].getSelectedData()
      // 确认
      if (this.eventCodes.length === 0) {
        this.$alert('请选择一条或多条记录')
        return
      }
      let codes = []
      for (let i = 0; i < this.eventCodes.length; i++) {
        codes = codes.concat(this.eventCodes[i].eventCode)
      }
      this.$emit('addEventRes', codes)
    },
    onopen() {
      this.eventCodes = []
      if (this.$refs.eventsTable) {
        this.$refs.eventsTable.clearMultiple()
      }
      this.getEventRes()
    },
    cancel() {
      // 取消
      this.$emit('update:visible', false)
    },
    onclose() {
      this.$emit('update:visible', false)
    },
    checkCode() {
      // TODO 防止修改删除
      // if (this.eventCodes[0].eventCode.indexOf('SYSEVENT') !== -1) {
      //   this.$message.warning('禁止删改');
      //   return true;
      // }
      // return false;
    }
  }
}
</script>

<style scoped>

</style>

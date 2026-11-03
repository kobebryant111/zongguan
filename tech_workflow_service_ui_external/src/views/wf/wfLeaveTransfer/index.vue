<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="分类">
                    <workflow-class-multi-tree :isMultiSelect = "isMultiSelect"  ref="tree" :fatherValue.sync="fatherValue" />
                </el-form-item>
                <el-form-item label="交接人" >
                    <select-user-input :fatherUserId.sync="fatherUserId"  />
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button :icon="icon" type="primary" size="mini" @click="additionalQuery" ></el-button>
                </el-form-item>
            </el-form>
        </div>
        <div  v-show="isQuery" style="margin-left: 10px;margin-top: -10px">
            <el-form inline>
                <el-form-item label="状态">
                    <ts-multi-select  v-model="query.status"  :params="selectParam" ></ts-multi-select>
                </el-form-item>
            </el-form>
        </div>
        <div class="buttonDiv">
            <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="add">增加</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-edit" @click="update">修改</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-check" @click="startTransfer">开始授权</el-button>
            <el-button  type="danger"  size="mini" icon="el-icon-close" @click="stop">强制终止离岗移交授权</el-button>
            <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
        </div>
        <div>
            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery">
                <el-table-column prop="classifyId" label="分类ID" min-width="180" show-overflow-tooltip/>
                <el-table-column prop="classifyName" label="分类名称"/>
                <el-table-column prop="transferId" label="转办人"/>
                <el-table-column prop="startTime" label="起始时间" min-width="120" sortable>
                    <template slot-scope="scope"><i class="el-icon-time"></i><span style="margin-left: 10px">{{scope.row.startTime}}</span></template>
                </el-table-column>
                <el-table-column prop="endTime" label="结束时间" min-width="120" >
                    <template slot-scope="scope"><i class="el-icon-time"></i><span style="margin-left: 10px">{{scope.row.endTime}}</span></template>
                </el-table-column>
                <el-table-column prop="status" :formatter="conversionType" label="状态"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
import addLeaveTransFormDialog from './addLeaveTransFormDialog.vue'
import WorkflowClassMultiTree from '../components/WorkflowClassMultiTree/index'
import SelectUserInput from '../components/SelectUserInput/index'
import { mapState, mapGetters } from 'vuex'

export default {
  components: { SelectUserInput, WorkflowClassMultiTree },
  computed: {
    ...mapState({
      'userInfo': state => state.user.userInfo,
      'nowThemeInfo': state => state.theme.nowThemeInfo
    })
  },
  data() {
    return {
      isQuery: false,
      icon: 'el-icon-arrow-down',
      isMultiSelect: false,
      isSave: 'true',
      fatherValue: '',
      fatherUserId: '',
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        desc: ''
      },
      selectParam: { options: [{ label: '生效', value: '1' }, { label: '失效', value: '0' }], text: 'label', value: 'value', width: '165px',
        size: 'mini' },
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      query: {}, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'checkbox', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.leaveTransferList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: { tenantId: this.$store.state.user.userInfo.tenantId }, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      },
      formOptions: {
        title: '添加配置', // [必填]
        resource: addLeaveTransFormDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      }
    }
  },
  methods: {
    additionalQuery() {
      if (this.isQuery) {
        this.isQuery = false
        this.icon = 'el-icon-arrow-down'
      } else {
        this.isQuery = true
        this.icon = 'el-icon-arrow-up'
      }
    },
    conversionType(row, column) {
      if (row[column.property] == 1) {
        return '生效'
      } else {
        return '失效'
      }
    },
    add() {
      this.formOptions.data = {}
      this.formOptions.data.tenantId = this.userInfo.tenantId
      this.formOptions.title = '添加配置'
      this.visible = true
      sessionStorage.setItem('isUpdate', false)
    },
    startTransfer() {
      const bean = this.$refs['list'].getSelectedData()
      if (bean.length === 0) {
        this.$tsVue.error('请选择数据')
      } else {
        this.$confirm('是否启动移交授权？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const param = JSON.stringify(bean)
          console.log(param)
          this.$request({
            url: this.$api.startWfLeaveTransfer, // [必填] 请求地址
            data: { 'items': JSON.stringify(bean) }, // [选填] 请求参数
            type: 'post', // [选填] String 请求方式 get|post 默认post
            paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
          }).then((response) => {
            this.$tsVue.success(response.description)
            this.$refs['list'].refreshCurrenPage()
          })
        }).catch(() => {
        })
      }
    },
    // 修改弹窗
    update() {
      const bean = this.$refs['list'].getSelectedData()
      if (bean.length !== 1) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        sessionStorage.setItem('isUpdate', true)
        this.formOptions.data = bean[0]
        this.formOptions.data.tenantId = this.userInfo.tenantId
        this.formOptions.title = '修改配置'
        this.visible = true
      }
    },
    beforeClose(data, done) {
      sessionStorage.setItem('isUpdate', false)
      data = ''
      done()
    },
    // 弹窗清空数据清除
    cancel(data, done) {
      sessionStorage.setItem('isUpdate', false)
      data = ''
      done()
    },
    // 弹窗提交保存
    submit(data, done) {
      data.operationUserId = this.userInfo.loginName
      // 开始校验
      if (data.transferId === data.userId) {
        this.$tsVue.error('保存失败，移交人和交接人不能为同一人')
        return
      }
      if ((data.classifyId === undefined || data.classifyId.length === 0) && (data.procInstId === '' || data.procInstId === undefined)) {
        this.$tsVue.error('保存失败， 流程分类和流程实例Id请填写其中一项')
        return
      }
      data.tenantId = this.userInfo.tenantId
      var array = []
      // 存在多选的情况

      if (data.classifyId !== undefined && data.classifyId.indexOf(',') !== -1) {
        const classifyIdList = data.classifyId.split(',')
        const classifyNameList = data.classifyName.split(',')
        for (let i = 0; i < classifyIdList.length; i++) {
          const bean = {}
          bean.classifyId = classifyIdList[i]
          bean.classifyName = classifyNameList[i]
          bean.describe = data.describe
          bean.endTime = data.endTime
          bean.isPower = data.isPower
          bean.isTransfer = data.isTransfer
          bean.isTransferHistoricalTasks = data.isTransferHistoricalTasks
          bean.startTime = data.startTime
          bean.status = data.status
          bean.operationUserId = data.operationUserId
          bean.transferId = data.transferId
          bean.userId = data.userId
          bean.tenantId = this.userInfo.tenantId
          array.push(bean)
        }
      } else {
        array.push(data)
      }

      this.$request({
        url: this.$api.saveLeaven, // [必填] 请求地址
        data: array, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'data' // [选填] String 传递参数方式 data|params 默认datalabel
      }).then((response) => {
        this.$tsVue.success(response.description)
        this.$refs['list'].refreshDataSource()
      })
      sessionStorage.setItem('isUpdate', false)
      done()
    },
    stop() {
      const bean = this.$refs['list'].getSelectedData()
      if (bean.length === 0) {
        this.$tsVue.error('请选择数据')
      } else {
        this.$confirm('是否强制终止离岗移交授权？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const param = JSON.stringify(bean)
          console.log(param)
          this.$request({
            url: this.$api.stopWfLeaveTransfer, // [必填] 请求地址
            data: { 'items': JSON.stringify(bean) }, // [选填] 请求参数
            type: 'post', // [选填] String 请求方式 get|post 默认post
            paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
          }).then((response) => {
            this.$tsVue.success(response.description)
            this.$refs['list'].refreshCurrenPage()
          })
        }).catch(() => {
        })
      }
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
      this.$refs.tree.resetChecked()
      this.fatherUserId = ''
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      if (this.fatherValue !== '') {
        this.$set(this.query, 'classifyId', this.fatherValue)
      }
      if (this.fatherUserId !== '') {
        this.$set(this.query, 'transferId', this.fatherUserId)
      }
      this.$refs['list'].serchHandel()
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
       margin-left: 10px;
        margin-top: 10px;
    }

    .mright_10 {
        margin-right: 10px;
    }

    .text_color {
        color: #3B8EDA
    }
    .buttonDiv{
        margin-bottom: 10px;
    }
    .funBox {
        display: flex;
        justify-content: flex-start;
    }
    .moneyCon {
        margin-top: 20px;
        display: flex;

        .item {
            margin-left: auto;
        }
    }
</style>

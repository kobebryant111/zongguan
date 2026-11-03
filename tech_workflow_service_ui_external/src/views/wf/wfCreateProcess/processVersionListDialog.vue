<!--
 * @Author: zhuofushen
 * @Date: 2020-03-13 15:45:43
 * @LastEditTime: 2020-04-09 21:01:29
 * @LastEditors: OBKoro1
 * @Description: 传递给弹框的表单组件
 * 注意点：
 * 1.el-form 标签中一定要设置ref名称，model数据，以及rules校验规则
 * 2.el-form-item 标签中必须定义prop属性来匹配校验的字段
 * 3.如果要自动过滤用户输入的首尾空白字符，可以给v-model添加trim修饰符：<input v-model.trim="msg">
 * 4.如果想自动将用户的输入值转为数值类型，可以给v-model添加number修饰符：<input v-model.number="age" type="number">
 -->
<template>
    <div>
      <div>
        <el-button  type="primary" size="mini" icon="el-icon-video-play"  @click="comparison">比对</el-button>
        <el-button  type="primary" size="mini" icon="el-icon-delete"  @click="deleteVersion">删除</el-button>
        <el-button type="primary" size="mini"  @click="updateVersion" icon="el-icon-edit" >更新</el-button>

      </div>
        <ts-multi-table ref="list"  :options="options" >
            <el-table-column prop="processDefineId" label="流程定义ID" min-width="180" show-overflow-tooltip/>
            <el-table-column prop="processDefineVersion" label="版本号" width="80"/>
            <el-table-column  label="流程文件" align="center" header-align="center">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini"  @click="download(scope.row)" icon="el-icon-bottom" >bpmn下载</el-button>
                </template>
            </el-table-column>
<!--    暂不显示图片        <el-table-column prop="modelKey" label="图片"/>-->
<!--            <el-table-column label="操作" align="center" header-align="center">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button type="primary" size="mini"  @click="backProcess" icon="el-icon-back" >回退</el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
        </ts-multi-table>
        <el-dialog title="流程图源码比对" :visible.sync="dialogFormVisible"  width="85%" >
            <div  style="width: 100% ;background-color: chocolate;height: 600px;" >
                <code-diff :old-string="oldStr" :new-string="newStr"  outputFormat="side-by-side" context="1000"  />
            </div>
        </el-dialog>
    </div>
</template>
<script>
import CodeDiff from 'vue-code-diff'
export default {
  components: { CodeDiff },
  props: {
    initData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      modelId: '',
      dialogFormVisible: false,
      oldStr: '',
      newStr: '',
      options: { // 必填
        initTable: false,
        type: 'checkbox', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.versionList,
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: { 'tenantId': this.$store.state.user.userInfo.tenantId }, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      },
      model: {}
    }
  },
  mounted() {
    this.getInitData()
  },
  methods: {
    comparison() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length !== 2) {
        this.$tsVue.error('请选择2行数据')
        return
      } else {
        this.dialogFormVisible = true
        const processDefineId1 = object[0].processDefineId
        const processDefineId2 = object[1].processDefineId
        this.oldStr = ''
        this.newStr = ''
        this.$request({
          url: this.$api.versionComparison, // [必填] 请求地址
          data: { 'processDefineId1': processDefineId1, 'processDefineId2': processDefineId2 }, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.oldStr = response.data.processDefineId1Bpmn
          this.newStr = response.data.processDefineId2Bpmn
        })
      }
    },
    deleteVersion() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length < 1) {
        this.$tsVue.error('请选择数据')
        return
      }
      const _this = this
      this.$tsVue.confirm('确认删除历史版本', () => {
        const deploys = []
        for (const p in object) {
          deploys.push(object[p].deploymentId)
        }
        _this.$request({
          url: _this.$api.deleteVersion,
          data: deploys,
          type: 'post',
          paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          _this.$tsVue.success('删除成功')
          _this.$refs['list'].refreshDataSource()
        })
      })
    },
    backProcess(row) {
      const param = { 'modelId': this.modelId, 'version': row.processDefineVersion }
      this.$request({
        url: this.$api.restoreVersion, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
      })
    },
    // updateVersion() {
    //   const row = this.$refs['list'].getSelectedData()
    //   if (row.length != 1) {
    //     this.$tsVue.error('请选择一条数据')
    //     return
    //   }
    //
    //   const obj = row[0]
    //   obj.id = obj.processDefineKey
    //   obj.modelKey = obj.processDefineKey
    //
    //   row.needupdate = 'yes'
    //   this.$tsVue.tansunPushAddTag(this, '/wf/processDesigner', obj)
    //   const dialogObj = this.$tsVue.tansunDialogData(this)
    //   if (dialogObj) {
    //     dialogObj.close()
    //   }
    // },
    download(row) {
      // const url = process.env.VUE_APP_BASE_API + this.$api.downLoadBpmn + '?processDefineId=' + row.processDefineId
      // window.location.href = url
      const _this = this
      _this.$tsVue.tsRequestByGetBlob(_this.$request, {
        url: _this.$api.downLoadBpmn,
        params: {
          processDefineId: row.processDefineId
        },
        cb: function(res) {
          _this.$tsVue.success(response.description)
        }
      })
    },
    getInitData() {
      // 通过props属性获取
      this.options.apiRequiredParams.key = this.initData.modelKey

      if (this.$refs['list']) {
        this.$refs['list'].refreshDataSource()
      }
    },
    updateVersion() {
      const row = this.$refs['list'].getSelectedData()
      if (row.length != 1) {
        this.$tsVue.error('请选择一条数据')
        return
      }

      const obj = row[0]
      const object = Object.assign({}, this.initData, obj)
      object.id = '' // 组件传部署ID的话无法去获取最新版本的source
      object.isUpdate = true
      const VISITEDVIEWS = JSON.parse(localStorage.getItem('VISITEDVIEWS')) || null
      // 判断是否已打开流程设计器标签页
      let hasTag = false
      VISITEDVIEWS.filter(item => {
        if (item.name === 'processDesigner') {
          hasTag = true
        }
      })
      if (hasTag) {
        this.$confirm('是否重新加载流程图', '信息', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'success'
        }).then(() => {
          object.needupdate = 'yes'
          this.$tsVue.tansunPushAddTag(this, '/wf/processDesigner', object)
        }).catch(() => {
        })
      } else {
        object.needupdate = 'yes'
        this.$tsVue.tansunPushAddTag(this, '/wf/processDesigner', object)
      }
      const dialogObj = this.$tsVue.tansunDialogData(this)
      if (dialogObj) {
        dialogObj.close()
      }
    }
  }
}
</script>
<style scoped>

</style>


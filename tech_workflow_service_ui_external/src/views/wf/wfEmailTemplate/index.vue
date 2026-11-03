<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="模板名称">
                    <ts-multi-input v-model="query.tempName" :params="param" />
                </el-form-item>
                <el-form-item label="模板标签">
                    <ts-multi-input v-model="query.tempTag" :params="param" />
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-search"  size="mini" type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-refresh" size="mini" type="primary" @click="reset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="emailTemplateDiv">
            <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="addEmailTemplate">增加</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-edit" @click="updateEmailTemplate">修改</el-button>
            <el-button  type="primary"  size="mini" icon="el-icon-video-play" @click="playEmailTemplate('1')">启用</el-button>
            <el-button  type="primary"  size="mini" icon="el-icon-video-pause" @click="playEmailTemplate('0')">禁用</el-button>
            <el-button  type="danger"  size="mini" icon="el-icon-delete" @click="deleteEmailTemplate">删除</el-button>
            <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
        </div>
        <div>
            <ts-multi-table ref="emailTemplateList" :query="query" :options="options" @reset-query="refreshQuery">
                <el-table-column prop="tempName" label="模板名称"/>
                <el-table-column prop="tempBody" label="模板内容" min-width="230" show-overflow-tooltip/>
                <el-table-column prop="tempTag" label="模板标签"/>
                <el-table-column prop="tempStatus" :formatter="conversionType" label="模板状态"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
import addEmailTemplateFormDialog from './addEmailTemplateFormDialog.vue'
export default {
  data() {
    return {
      param: {
        size: 'mini',
        clearable: true
      },
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      query: { 'tenantId': this.$store.state.user.userInfo.tenantId }, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.queryEmailTemplate,
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
      formOptions: {
        title: '添加按钮', // [必填]
        resource: addEmailTemplateFormDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '40%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        // beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 | string | ——
      }

    }
  },
  mounted() {
    this.query.tenantId = this.$store.state.user.userInfo.tenantId
  },
  methods: {
    conversionType(row) {
      if (row['tempStatus'] == 1) {
        return '启用'
      } else {
        return '禁用'
      }
    },
    addEmailTemplate: function() {
      this.visible = true
      this.formOptions.data = {}
      this.formOptions.data.tenantId = this.$store.state.user.userInfo.tenantId
      this.formOptions.data.tenantName = this.$store.state.user.userInfo.tenantName
      this.formOptions.data.tempTag = 'defaultTag'
      this.formOptions.title = '新增模板'
    },
    // 修改弹窗
    updateEmailTemplate() {
      const emailTemplate = this.$refs['emailTemplateList'].getRadioData()
      console.log('emailTemplate', emailTemplate)
      if (emailTemplate == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        this.visible = true
        this.formOptions.data = emailTemplate
        this.formOptions.title = '修改模板'
      }
    },
    // 弹窗提交保存
    submit(data, done) {
      const emailTemplate = {
        id: data.id,
        tempName: data.tempName,
        tempTag: data.tempTag,
        tempBody: data.tempBody,
        tenantId: data.tenantId
      }
      this.$request({
        url: this.$api.saveEmailTemplate, // [必填] 请求地址
        data: emailTemplate, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['emailTemplateList'].refreshDataSource()
        this.formOptions.data = ''
        done()
      })
    },
    beforeClose(data, done) {
      data = ''
      done()
    },
    // 弹窗清空数据清除
    cancel(data, done) {
      data = ''
      done()
    },
    playEmailTemplate(status) {
      const object = this.$refs['emailTemplateList'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      this.$confirm('确定' + (status && status === '1' ? '启用？' : '禁用？'), '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          id: object.id,
          tempStatus: status
        }
        this.$request({
          url: this.$api.playEmailTemplate, // [必填] 请求地址
          data: data, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description)
          this.$refs['emailTemplateList'].refreshDataSource()
        })
      }).catch(() => {
      })
    },
    deleteEmailTemplate() {
      const object = this.$refs['emailTemplateList'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          id: object.id
        }
        this.$request({
          url: this.$api.deleteEmailTemplate, // [必填] 请求地址
          data: data, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description)
          this.$refs['emailTemplateList'].refreshDataSource()
        })
      }).catch(() => {
      })
    },
    // 重置
    reset() {
      this.$refs['emailTemplateList'].resetData()
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      this.$refs['emailTemplateList'].serchHandel()
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        padding: 10px;
    }
    .emailTemplateDiv{
        margin-bottom: 10px;
    }
</style>


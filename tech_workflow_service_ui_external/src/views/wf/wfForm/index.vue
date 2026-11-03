<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="表单编号:">
                    <el-input placeholder="请输入表单编号" size="mini" v-model="query.formCode"></el-input>
                </el-form-item>
                <el-form-item label="表单名称:">
                    <el-input placeholder="请输入表单名称" size="mini" v-model="query.formName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>

                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="buttonDiv">
            <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="add">增加</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-edit" @click="update">修改</el-button>
            <el-button  type="danger"  size="mini" icon="el-icon-delete" @click="deleteForm">删除</el-button>
<!--            <el-button  type="primary"  size="mini" icon="el-icon-s-order" @click="maintain">详情维护</el-button>-->
            <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
        </div>
        <div>
            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery">
                <el-table-column prop="formCode" label="表单编号" min-width="180" show-overflow-tooltip/>
                <el-table-column prop="formName" label="表单名称"/>
                <el-table-column prop="formUrl" label="表单地址"/>
                <el-table-column prop="remarks" label="备注信息"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
import addFormDialog from './addFormDialog.vue'
export default {
  data() {
    return {
      id: '',
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      query: {"tenantId":this.$store.state.user.userInfo.tenantId}, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.queryForm,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: {"tenantId":this.$store.state.user.userInfo.tenantId}, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      },
      formOptions: {
        title: '添加表单', // [必填]
        resource: addFormDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '45%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
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
    add() {
      this.visible = true
      this.formOptions.data = ''
      this.formOptions.title = '添加表单'
    },
    // 修改弹窗
    update() {
      const form = this.$refs['list'].getRadioData()
      if (form == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        this.visible = true
        this.formOptions.data = form
        this.formOptions.title = '修改表单'
      }
    },
    maintain() {
      const form = this.$refs['list'].getRadioData()
      if (form == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        this.$tsVue.tansunPushNoAddTag(this, '/wf/wfFormDetail', { id: form.id })
      }
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
    // 弹窗提交保存
    submit(data, done) {
      const form = {
        id: data.id,
        formCode: data.formCode,
        formName: data.formName,
        formUrl: data.formUrl,
        remarks: data.remarks,
        tenantId: this.$store.state.user.userInfo.tenantId
      }
      this.$request({
        url: this.$api.saveForm, // [必填] 请求地址
        data: form, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshDataSource()
        this.formOptions.data = ''
        done()
      })
    },
    deleteForm() {
      const result = this.$refs['list'].getRadioData()
      if (result == null) {
        this.$tsVue.error('请选择一条记录')
        return
      }
      this.$confirm('确定删除表单信息？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const result = this.$refs['list'].getRadioData()
        const data = {
          id: result.id
        }
        this.$request({
          url: this.$api.deleteForm, // [必填] 请求地址
          data: data, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description)
          this.$refs['list'].refreshDataSource()
        })
      })
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      this.$refs['list'].serchHandel()
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        padding: 10px;
    }
    .buttonDiv{
      margin-bottom: 10px;
    }
</style>

<template>
    <div class="app-container">
        <el-row :gutter="25">
            <el-col :span="4">
                <div class="ts-pane-box">
                    <el-tabs type="border-card" >
                        <el-tab-pane label="流程分类树">
                            <ts-multi-trees
                                    style="width: 100%;height: 400px;overflow-y:auto;"
                                    :options="treeOptions"
                                    ref="classifyTree"
                                    @node-click="handleNodeClick">
                            </ts-multi-trees>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-col>
            <el-col :span="20">
                <div class="ts-tabs-box">
                         <el-tabs>
                                <div>
                                    <el-form inline>
                                        <el-form-item label="流程变量名称：">
                                            <el-input placeholder="请输入流程变量名称" size="mini" v-model="query.varName"></el-input>
                                        </el-form-item>
                                        <el-form-item label="流程分类名称：">
                                            <el-input placeholder="请输入流程分类名称" size="mini" v-model="query.classifyName"></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button icon="el-icon-search" size="mini" type="primary" @click="searchAll">查询所有分类</el-button>
                                        </el-form-item>
                                    </el-form>
                                </div>
                                <div class="buttonDiv">
                                    <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="add">增加</el-button>
                                    <el-button  type="primary" size="mini" icon="el-icon-edit" @click="update">修改</el-button>
                                    <el-button  type="danger"  size="mini" icon="el-icon-delete" @click="deleteProcessVariableConfig">删除</el-button>
                                    <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
                                </div>
                                <div>
                                    <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery">
                                        <el-table-column prop="classifyId" label="流程分类ID" min-width="150" show-overflow-tooltip/>
                                        <el-table-column prop="classifyName" label="流程分类名称"/>
                                        <el-table-column prop="varName" label="流程变量名称"/>
                                        <el-table-column prop="varCode" label="流程变量编码"/>
                                        <el-table-column prop="varType" label="流程变量类型"/>
                                        <el-table-column prop="varClass" :formatter="conversionQuery" label="流程变量分类"/>
                                    </ts-multi-table>
                                </div>
                    </el-tabs>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import addTaskExpandPlanDialog from './processVariableConfigDialog.vue'
export default {
  data() {
    return {
      treeStyle: {
        'height': '400px'
      },
      treeOptions: {
        apiName: this.$api.classifyTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramMode: 'params',
        params: { tenantId: this.$store.state.user.userInfo.tenantId }
      },
      icon: 'el-icon-arrow-down',
      isMultiSelect: false,
      selectClassifyNode: '',
      isSave: 'true',
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      query: {}, // 最好必填，做搜索条件扩展！！！约定
      formOptions: {
        title: '待办已办扩展方案', // [必填]
        resource: addTaskExpandPlanDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        // beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 | string | ——
      },
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.processVariableConfigList,
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
      }
    }
  },
  methods: {
    conversionQuery(row, column) {
      if (row[column.property] === '1') {
        return '系统变量'
      } else {
        return '业务变量'
      }
    },
    handleNodeClick(node) {
      this.selectClassifyNode = node
      this.$set(this.options.apiRequiredParams, 'classifyId', node.id)
      // 需要重置列表的页数和页长
      this.$refs['list'].refreshDataSource()
    },
    conversionType(row, column) {
      if (row[column.property] == 0) {
        return '字典'
      } else if (row[column.property] == 1) {
        return '字符串'
      } else {
        return '日期'
      }
    },
    add() {
      this.visible = true
      this.formOptions.data = {}
      this.formOptions.data.tenantId = this.$store.state.user.userInfo.tenantId
      this.formOptions.data.tenantName = this.$store.state.user.userInfo.tenantName
      this.formOptions.title = '添加流程变量'
    },
    // 修改弹窗
    update() {
      const bean = this.$refs['list'].getRadioData()
      if (bean == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        this.visible = true
        this.formOptions.data = bean
        this.formOptions.data.tenantId = this.$store.state.user.userInfo.tenantId
        this.formOptions.data.tenantName = this.$store.state.user.userInfo.tenantName
        this.formOptions.title = '修改流程变量'
        this.isSave = false
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
      this.$request({
        url: this.$api.processVariableConfigSave, // [必填] 请求地址
        data: data, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshDataSource()
        done()
        this.formOptions.data = ''
      })
    },
    deleteProcessVariableConfig() {
      const result = this.$refs['list'].getRadioData()
      if (result == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = { id: result.id }
        this.$request({
          url: this.$api.processVariableConfigDelete, // [必填] 请求地址
          data: data, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          this.$refs['list'].refreshDataSource()
        })
      }).catch(() => {
      })
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
      this.$refs['classifyTree'].resetChecked()
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      this.$refs['list'].serchHandel()
    },
    searchAll() {
      delete this.options.apiRequiredParams.classifyId
      this.$refs['list'].serchHandel()
    }
  }
}
</script>

<style lang="scss" scoped>
    .buttonDiv{
        margin-bottom: 10px;
    }
    .el-header, .el-footer {
        background-color: #B3C0D1;
        color: #333;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        line-height: 200px;
    }

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }
    .maintree_title {
        width:100%;
        height:35px;
        line-height:35px;
        padding-left:10px;
        background:#4586E0;
        color:#FFF;
        font-weight: bold;
    }
    .col2 {
        width:20%;
        float:left;
    }
    .col8 {
        width:80%;
        float:left;
    }
    .mright_10 {
        margin-right: 10px;
    }

    .text_color {
        color: #3B8EDA
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

    .el-row {
        margin-bottom: 20px;
        &:last-child {
            margin-bottom: 0;
        }
    }
    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #99a9bf;
    }
    .bg-purple {
        background: #d3dce6;
    }
    .bg-purple-light {
        background: #e5e9f2;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }
</style>

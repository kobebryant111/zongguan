<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="测试编号：">
                    <el-input  size="mini" v-model="query.modelKey"></el-input>
                </el-form-item>
                <el-form-item label="测试名称：">
                    <el-input  size="mini" v-model="query.modelName"></el-input>
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
            <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline"  @click="addTest">创建用例</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-edit" @click="updateTest">修改用例</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-delete" @click="deleteTest">删除用例</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-video-play"  @click="startProcess">执行模拟测试</el-button>
            <el-button  type="primary" size="mini" icon="el-icon-video-play"  @click="showBranch">分支场景预测</el-button>
            <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
        </div>
        <div>
            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery">
                <el-table-column prop="processDefKey" label="关联的流程键值" min-width="60" show-overflow-tooltip/>
                <el-table-column prop="planCode" label="测试编号"/>
                <el-table-column prop="planName" label="测试名称"/>
                <el-table-column prop="tenantName" label="流程租户"/>
                <el-table-column prop="classifyName" label="分类名称"/>
                <el-table-column prop="createTime" label="创建时间" min-width="100" sortable></el-table-column>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
import addTestFormDialog from './addTestFormDialog.vue'
export default {
  data() {
    return {
      id: '',
      isSave: 'true',
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      query: {}, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.testList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 session，默认是data
        apiRequiredParams: {}, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      },
      formOptions: {
        title: '增加测试用例', // [必填]
        resource: addTestFormDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
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
    addTest() {
      this.visible = true
      this.formOptions.data = ''
      this.formOptions.title = '添加测试用例'
    },
    // 修改弹窗
    updateTest() {
      const test = this.$refs['list'].getRadioData()
      if (test == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        this.visible = true
        this.id = test.id
        this.formOptions.data = test
        this.formOptions.title = '修改测试用例'
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
      const test = {
        planCode: data.planCode,
        planName: data.planName,
        proDefKey: data.processDefKey
      }
      this.$request({
        url: this.$api.addTest, // [必填] 请求地址
        data: test, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshCurrenPage()
        this.formOptions.data = ''
        this.id = ''
        done()
      })
    },
    deleteTest() {
      this.$confirm('确定删除该测试用例？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const result = this.$refs['list'].getRadioData()
        const data = {
          id: result.id,
          proDefKey: result.processDefKey
        }
        this.$request({
          url: this.$api.deleteTest, // [必填] 请求地址
          data: data, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          this.$refs['list'].refreshCurrenPage()
        })
      }).catch(() => {
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
    },
    showBranch() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      object.needupdate = 'yes'
      this.$tsVue.tansunPushAddTag(this, '/wf/branchScenario', object)
    },
    startProcess() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      object['isTask'] = false
      this.$tsVue.tansunPushNoAddTag(this, '/wf/startProcess', object)
      // }
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

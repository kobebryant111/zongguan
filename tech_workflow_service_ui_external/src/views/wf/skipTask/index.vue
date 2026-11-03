<template>
        <div class="body_mg20">
            <div class="layui-form" >
                <div style="height:217px;background-color:#ffffff;border-radius: 5px;opacity: 1;padding: 10px 27px 0 20px ">
                    <div style="color:#555555;font-weight: bold;font-size: 14px;line-height: 32px;margin-bottom: 6px">全局跨步调试开关</div>
                    <div style="width: 100%;border:1px solid rgba(226,226,226,1);margin-bottom: 17px"></div>
                    <el-row>
                        <el-form ref="formRef" :model="globalEntity"  label-width="auto" class="double-col-form">
                            <el-form-item label="是否开启全局跨步" prop="isOpen">
                                <ts-multi-select v-model="globalEntity.isOpen" :params="selectParam3"></ts-multi-select>
                            </el-form-item>
                            <el-form-item label="最后节点审批人" prop="taskAssignee">
                                <el-input v-model="globalEntity.taskAssignee"></el-input>
                            </el-form-item>
                        </el-form>
                    </el-row>
                    <el-row style="margin-top: 20px">
                        <el-form ref="formRef"  label-width="auto" class="single-col-form">
                            <el-form-item label="说明：" prop="loginName">
                                <el-input
                                        type="textarea"
                                        :rows="3"
                                        placeholder="1.打开全局控制后 启动流程会自动将流程提交至指定最后一个任务，用指定人员审批（默认根据流程图生成处理人）。2.是否开启优先级：流程启动参数开关 》流程键值开关 》全局开关">
                                </el-input>
                            </el-form-item>
                            <!-- 表单内容 -->
                        </el-form>
                    </el-row>
                </div>
                <div class="center" style="width: 100%;margin-bottom: 17px;background-color:#ffffff" >
                    <el-button type="primary" size="mini" @click="globalSave">保存</el-button>
                </div>
                <div style="height:500px;background-color:#ffffff;border-radius: 5px;opacity: 1;padding: 10px 27px 0 20px ">
                    <div style="color:#555555;font-weight: bold;font-size: 14px;line-height: 32px;margin-bottom: 6px">流程键值跨步调试开关</div>
                    <div style="width: 100%;border:1px solid rgba(226,226,226,1);margin-bottom: 17px"></div>
                    <div>
                        <el-button-group class="handle-container">
                            <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline"  @click="add">新增</el-button>
                            <el-button  type="primary" size="mini" icon="el-icon-edit" @click="update">修改</el-button>
                            <el-button  type="primary" size="mini" icon="el-icon-delete" @click="deleteSkipTask">删除</el-button>
                            <el-button  type="primary" size="mini" icon="el-icon-video-play"  @click="changeOpenStatus('N')">关闭</el-button>
                            <el-button  type="primary" size="mini" icon="el-icon-video-play"  @click="changeOpenStatus('Y')">开启</el-button>
                            <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
                        </el-button-group>
                        <div style="width: 100%;margin-bottom: 17px;background-color:#ffffff">
                            <ts-multi-table ref="skipTasklist" :options="options">
                                <el-table-column prop="processDefKey" label="流程键值" min-width="60" show-overflow-tooltip/>
                                <el-table-column prop="processName" label="流程名称"/>
                                <el-table-column prop="tenantId" label="租户编码"/>
                                <el-table-column prop="tenantName" label="租户名称"/>
                                <el-table-column prop="taskAssignee" label="任务节点审批人"/>
                                <el-table-column prop="targetNodeId" label="跳转任务节点Id" />
                                <el-table-column prop="targetNodeName" label="跳转任务节点名称" />
                                <el-table-column prop="isOpen"  width="100px" :formatter="conversion" label="状态"/>
                            </ts-multi-table>
                        </div>
                        <div class="center" style="width: 100%;margin-bottom: 17px;background-color:#ffffff" >
                            <el-form ref="formRef"  label-width="auto" class="single-col-form">
                                <el-form-item label="说明：" prop="loginName">
                                    <el-input
                                            type="textarea"
                                            :rows="3"
                                            placeholder="1.根据配置，启动流程后将流程提交至指定步骤（默认最后一个任务），用指定人员审批（默认根据流程图生成处理人）。
2.是否开启优先级 高于 全局开关">
                                    </el-input>
                                </el-form-item>
                                <!-- 表单内容 -->
                            </el-form>
                        </div>
                    </div>
                </div>
                <div style="height:217px;background-color:#ffffff;border-radius: 5px;opacity: 1;padding: 10px 27px 0 20px ">
                        <div style="color:#555555;font-weight: bold;font-size: 14px;line-height: 32px;margin-bottom: 6px">流程启动参数跨步调试开关</div>
                    <div style="width: 100%;border:1px solid rgba(226,226,226,1);margin-bottom: 17px"></div>
                    <div>
                        <el-form ref="formRef" label-width="auto" class="single-col-form">
                            <el-form-item label="说明：" prop="loginName">
                                <el-input
                                        type="textarea"
                                        :rows="7"
                                        placeholder="1.启动流程接口(startProcess、startAndSubmitProcess)可以通过参数控制流程是否跨步调试
2.通过processInfo.debugInfo.isOpen 开启或关闭单条流程实例跨步调试 Y:开启跨步，N:关闭跨步调试
3.通过processInfo.debugInfo.targetTaskNodeId 设置流程启动后跳转的任务节点id
4.通过processInfo.debugInfo.targetTaskAssignee 设置流程启动后跳转的任务节点处理人
5.是否开启优先级 高于 全局开关、流程键值开关">
                                </el-input>
                            </el-form-item>
                            <!-- 表单内容 -->
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
</template>

<script>
import addPlanFormDialog from './addPlanFormDialog.vue'
export default {
  name: 'Index',
  data: function() {
    return {
      id: '',
      selectParam3: {options: [{text: '是', value: 'Y'}, {text: '否', value: 'N'}]},
      visible: false,
      globalEntity: {},
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.skipTask,
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
        resource: addPlanFormDialog, // 模拟懒加载组件
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
    conversion(row, column) {
      console.log("zhuangtai")
      console.log(row)
      if (row.isOpen === 'Y') {
        return '是'
      } else {
        return '否'
      }
    },
    add() {
      this.visible = true
      this.formOptions.data = ''
      this.formOptions.title = '添加开关'
    },
    update() {
      const test = this.$refs['skipTasklist'].getRadioData()
       test["modify"]="true"
      if (test == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        this.visible = true
        this.id = test.id
        this.formOptions.data = test
        this.formOptions.title = '修改跨步开关'
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
      console.log(data)
      if(data && data.id){
            delete data.createTime;
        }
        if(this.globalEntity && this.globalEntity.id){
            delete this.globalEntity.createTime;
        }
      let test = data
      test['isGlobal']='N'
      if(data == 'Y'){
        test=this.globalEntity
      }
      console.log(this.globalEntity)
      this.$request({
        url: this.$api.saveSkipTask, // [必填] 请求地址
        data: test, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['skipTasklist'].refreshDataSource()
        done()
        this.formOptions.data = ''
      })
    },
    deleteSkipTask() {
      this.$confirm('确定删除该跨步用例？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const result = this.$refs['skipTasklist'].getRadioData()
        const data = {
          "id":result.id
        }
        console.log(data)
        this.$request({
          url: this.$api.deleteSkipTask, // [必填] 请求地址
          data: data, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          this.$refs['skipTasklist'].refreshCurrenPage()
        })
      }).catch(() => {
      })
    },
    changeOpenStatus(data) {
      const test = this.$refs['skipTasklist'].getRadioData()
      if (test == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {

        const param = {
          'id': test.id,
          'isOpen': data
        }
        this.$request({
          url: this.$api.changeOpenStatus, // [必填] 请求地址
          data: param, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          this.$refs['skipTasklist'].refreshCurrenPage()
        })
      }
    },
    globalSave() {
        const test = this.globalEntity
        if(test && test.id){
            delete test.createTime;
        }
        if(this.globalEntity && this.globalEntity.id){
            delete this.globalEntity.createTime;
        }
        this.$request({
            url: this.$api.saveSkipTask, // [必填] 请求地址
            data: test, // [选填] 请求参数
            type: 'post', // [选填] String 请求方式 get|post 默认post
            paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
            this.$tsVue.success(response.description, () => {})
            this.$refs['skipTasklist'].refreshDataSource()
            done()
            this.formOptions.data = ''
        })
    },
    initTableData(){
      this.$request({
        url: this.$api.getGlobalEntity,
        type: 'post',
        paramMode: 'params'
      }).then((response) => {
        this.globalEntity = response.data
        if (!this.globalEntity){
          this.globalEntity = {};
          this.globalEntity.isGlobal = 'Y';
        }
      }).catch(error => {
        reject(error)
      })
    }
  },
  mounted() {
    this.initTableData()
  }
}
</script>

<style scoped>

</style>

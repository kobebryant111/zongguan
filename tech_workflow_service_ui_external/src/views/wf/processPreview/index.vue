
<template>
    <div class="wfd-container" v-if="showPreview">
        <!--camunda TODO-->
        <el-button type="primary" @click="processInfo">流程实例信息</el-button>
        <el-button type="primary" @click="refresh" class="refresh">刷新</el-button>
        <el-button type="primary" @click="suspend" class="suspend">挂起</el-button>
        <el-button type="primary" @click="active" class="active">激活</el-button>
        <el-button type="primary" @click="terminateProcess" class="terminate">终止</el-button>
        <el-button type="primary" @click="deleteProcess" class="delete">删除</el-button>

        <el-button type="primary" class="returnLevel" @click="back">返回实例列表</el-button>
        <!--camunda TODO-->
        <wfd-vue
          v-if="show"
          :workFlowMode = "workFlowMode"
          :baseApi = this.baseApi
          :processInstanceId = 'model.processInstanceId'
          :processDefineId = "model.processDefineId"
          :isSimpleMode = true
          :tokenInfo = tokenInfo
          :extraData="extraData"
          lang="zh"/>
        <div style="margin-top: 30px;margin-bottom: 30px;padding-bottom: 10px">
            <ts-multi-table  ref="list"  :options="options">
                <el-table-column prop="taskName" label="名称" min-width="80" show-overflow-tooltip/>
                <el-table-column prop="taskId" label="任务ID"/>
                <el-table-column prop="beginTime"  label="开始时间"/>
                <el-table-column prop="endTime"  label="结束时间"/>
                <el-table-column prop="status"  label="任务审阅状态"/>
                <el-table-column prop="finish"  label="任务状态"/>
                <el-table-column prop="assignee" label="处理人"/>
                <el-table-column label="操作" min-width="160" align="center" header-align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" icon="iconfont iconsearch" @click="taskInfo(scope.row)">查看详情</el-button>
                    </template>
                </el-table-column>
            </ts-multi-table>
        </div>
        <ts-multi-dialog ref="dialog" :visible.sync="visible" :options="taskInfoOptions"></ts-multi-dialog>

        <ts-multi-dialog :visible.sync="showProcessInfo" :options="processInfoOptions"></ts-multi-dialog>
    </div>
</template>
<script>
import WfdVue from 'tansun-wf-designer'
import taskInfoDialog from './taskInfoDialog.vue'
import processInfoDialog from './processInfoDialog.vue'
export default {
  components: { WfdVue },
  data() {
    return {
      showPreview:false,
      extraData: { tenantId: this.$store.state.user.userInfo.tenantId },
      showProcessInfo:false,
      tokenInfo: this.$tsVue.tansunGetToken(),
      show: true,
      workFlowMode: 'preview',
      baseApi: process.env.VUE_APP_BASE_API,
      visible: false,
      model: this.$router.currentRoute.query,
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        tbMockData:[],
        maxHeight:600,
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        // apiName: this.$api.getRunningApproveEditTable,
        // method: 'post', // 默认走‘post’
        // paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: {}, // 选填 表示接口的必需恒定不变的参数
        layout: '', // 分页器轮廓
        // showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        // showConfirmBtn: true // [可不填 | Boolean] 默认值true 显示确认按钮
      },
      processInfoOptions:{
        title: '流程实例信息', // [必填]
        resource: processInfoDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [null, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      taskInfoOptions: {
        title: '任务维护', // [必填]
        resource: taskInfoDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [null, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      }
    }
  },
  created(){

  },
  mounted() {},
  activated() {
    console.log("当前数据")
    console.log(this.$router.currentRoute)
    this.model = this.$router.currentRoute.query
    this.options.apiRequiredParams = {
      'processDefineId': this.$router.currentRoute.query.processDefineId,
      'processInstanceId': this.$router.currentRoute.query.processInstanceId
    }
    this.processInfoOptions.data = {
      'processInstanceId':this.$router.currentRoute.query.processInstanceId,
      'processDefineId': this.$router.currentRoute.query.processDefineId,
      'processDefineName':this.$router.currentRoute.query.processDefineName,
      'startTime':this.$router.currentRoute.query.startTime,
      'endTime':this.$router.currentRoute.query.endTime,
      'userId':this.$router.currentRoute.query.userId
    }
    this.getList()
    this.showPreview = true
  },
  deactivated() {
    this.showPreview = false
  },
  methods: {
    getList(){
      this.$request({
        url: this.$api.getRunningApproveEditTable, // [必填] 请求地址
        data: this.options.apiRequiredParams, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.options.tbMockData = response.data.grid.list
        this.$refs.list.initTable()
      })
    },
    conversion(row, column) {
      if (row[column.property] === '') {
        return ' '
      } else {
          if(row[column.property]==null){
              return ''
          }
        return row[column.property] + '(' + row['assigneeName'] + ')'
      }
    },
    refresh() {
      //遍历所有任务，判断是否完成
      this.show = false
      setTimeout(() => {
        this.getList()
        this.show = true
      }, 10)
    },
    //流程删除
    deleteProcess(){
      const param = {
        processInstanceId: this.model.processInstanceId
      }
      this.$request({
        url: this.$api.wfDeleteInfo, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshCurrenPage()
        done()
      })
    },
    //终止流程
    terminateProcess(){
      const param = {
        processInstanceId: this.model.processInstanceId
      }
      this.$request({
        url: this.$api.terminateProcess, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshCurrenPage()
        done()
      })
    },
    //激活流程
    active(){
      const param = {
        processInstanceId: this.model.processInstanceId
      }
      this.$request({
        url: this.$api.wfActivate, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshCurrenPage()
        done()
      })
    },
   //查看流程实例信息
   processInfo(){
     let endTime=[];
      this.$refs['list'].dataSource.map(item=>{
        if(item.endTime!=null){
          endTime.push(item.endTime)
        }
      })
      if(this.$refs['list'].dataSource.length==endTime.length){
        //已结束
         let sort=endTime.sort();
        //  that
           this.model.endTime=sort[endTime.length-1]
           this.processInfoOptions.data.endTime=sort[endTime.length-1]
      }
     this.showProcessInfo=true
   },
   //挂起流程
    suspend(){
      const param = {
        processInstanceId: this.model.processInstanceId
      }
      this.$request({
        url: this.$api.wfSuspend, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshCurrenPage()
        done()
      })
    },
    back() {
      this.$tsVue.tansunPushAddTag(this, '/wf/instance/wfRunningList')
    },
    submit(data, done, datum) {
      const tableData = datum.tableData
      const set = new Set()
      tableData.forEach(object => {
        set.add(object.userId)
      })
      const userList = Array.from(set)
      const param = {
        taskId: data.taskId,
        processInstanceId: this.model.processInstanceId,
        userList: userList.join(','),
        comment: data.comment,
        assignee: data.assignee
      }
      console.log('param', param)
      this.$request({
        url: this.$api.updateCandidateUser, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshCurrenPage()
        done()
      })
    },
    beforeClose(data, done) {
      data = ''
      done()
    },
    taskInfo(data) {
      this.taskInfoOptions.data = data
      this.taskInfoOptions.data['processDefineId'] = this.model.processDefineId
      this.visible = true
    }
  }
}
</script>
<style lang="scss" scoped>
.wfd-container {
  //position: sticky;
  width: 100%;
  background: #fff;
  position: relative;
  padding-left: 30px;
  padding-right: 30px;

  .wf-designer {
    height: 70vh;
    /deep/ .canvasPanel {
      z-index: 0 !important;
    }
  }

  .el-button {
    position: absolute;
    top: 10px;
    left: 10px;
    z-index: 1;
  }
  .returnLevel{
    position: absolute;
    top: 10px;
    left: 1250px;
    z-index: 1;
  }
  .refresh{
    position: absolute;
    top: 10px;
    left: 130px;
    z-index: 1;
  }
  .suspend{
    position: absolute;
    top: 10px;
    left: 205px;
    z-index: 1;
  }
  .active{
    position: absolute;
    top: 10px;
    left: 280px;
    z-index: 1;
  }
  .terminate{
    position: absolute;
    top: 10px;
    left: 355px;
    z-index: 1;
  }
  .delete{
    position: absolute;
    top: 10px;
    left: 430px;
    z-index: 1;
  }
}
</style>


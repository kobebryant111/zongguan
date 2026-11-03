<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="流程状态:">
                    <ts-multi-select  v-model="processType" :params="selectParam"></ts-multi-select>
                </el-form-item>
                <el-form-item label="流程实例ID：">
                    <el-input  size="mini" v-model="query.processInstanceId"></el-input>
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
        <div v-show="isQuery" style="margin-left: 10px;margin-top: -10px">
            <el-form inline>
                <el-form-item label="流程定义ID：">
                    <el-input size="mini" v-model="query.processDefineId"></el-input>
                </el-form-item>
                <el-form-item label="流程定义键值:" >
                    <el-input size="mini" v-model="query.processDefineKey"></el-input>
                </el-form-item>
                <el-form-item label="流程名称：" v-if = showButton>
                    <el-input size="mini" v-model="query.processName"></el-input>
                </el-form-item>
            </el-form>
        </div>
        <div  class="buttonDiv">
            <el-button v-if ="showButton" type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="suspend">挂起</el-button>
            <el-button v-if ="showButton" type="primary" size="mini" icon="el-icon-folder-checked" @click="activate">恢复</el-button>
            <el-button v-if ="showButton" type="primary" size="mini" icon="el-icon-edit" @click="taskIntervene">任务干预</el-button>
            <el-button type="primary" size="mini" icon="el-icon-video-play" @click="maintenance">流程轨迹图</el-button>
            <el-button v-if ="showButton" type="primary" size="mini" icon="el-icon-close"  @click="endWorkFlow" >批量终止</el-button>
            <el-button type="primary" size="mini" icon="el-icon-search"  @click="lookVariable" >查看流程变量</el-button>
            <el-button type="primary" size="mini" icon="el-icon-search"  @click="batchDelete" >批量删除</el-button>
        </div>
        <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
        <ts-multi-dialog :visible.sync="endVisible" :options="endOptions"></ts-multi-dialog>
        <ts-multi-dialog :visible.sync="variableVisible" :options="variableOptions"></ts-multi-dialog>
        <div>
            <ts-multi-table  ref="list" :query="query" :options="options" @reset-query="refreshQuery" >
                <el-table-column prop="tenantId" label="租户编号" min-width="80" show-overflow-tooltip/>
                <el-table-column prop="processInstanceId" label="流程实例ID"/>
                <el-table-column prop="processDefineKey" label="流程定义键值"/>
                <el-table-column prop="processDefineName" label="流程名称"/>
                <el-table-column prop="processDefineVersion"  width="130px" label="流程定义版本号"/>
                <el-table-column prop="isSuspended"  width="100px" :formatter="conversionSuspended"  label="是否挂起"  v-if="nowApi === 'runProcess'"/>
                <el-table-column prop="endTime"  width="100px" :formatter="conversion" label="状态"/>
                <el-table-column prop="startTime"  width="200px" label="流程启动时间">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.startTime | tsDate('YYYY-MM-DD HH:mm:ss')}}</span>
                    </template>
                </el-table-column>
                <el-table-column  prop="endTime" label="流程结束时间" :formatter="conversionTime" v-if="nowApi === 'endProcess'|| nowApi ==='allProcess'">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.endTime | tsDate('YYYY-MM-DD HH:mm:ss')}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="businessKey" label="业务主键"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
import endDialog from './endDialog.vue'
import WorkFlowInterveneDialog from './workFlowInterveneDialog.vue'
import processVariableDialog from './processVariableDialog.vue'
export default {
  data() {
    return {
      showButton: true,
      refita: true,
      keys:'',
      isQuery: false,
      icon: 'el-icon-arrow-down',
      endVisible: false,
      variableVisible: false,
      processType: 'runProcess',
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        desc: ''
      },
      selectParam: { clearable: true, size: 'mini', options: [{ text: '结束', value: 'endProcess' }, { text: '运行', value: 'runProcess' }, { text: '所有', value: 'allProcess' }] },
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      query: {}, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'checkbox', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.wfRunningList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: { 'ignoreFinish':
         'unfinish', 'startTimeSort': 'desc', tenantId: this.$store.state.user.userInfo.tenantId }, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true // [可不填 | Boolean] 默认值true 显示确认按钮

      },
      formOptions: {
        title: '任务干预', // [必填]
        resource: WorkFlowInterveneDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '40%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      endOptions: {
        title: '终止意见', // [必填]
        resource: endDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '35%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submitEnd], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      variableOptions: {
        title: '查询流程变量', // [必填]
        resource: processVariableDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '60%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submitVariable], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      nowApi: 'runProcess'
    }
  },
  methods: {
    //批量删除
    batchDelete(){
      const object = this.$refs['list'].getSelectedData();
      if (object.length == 0) {
        this.$tsVue.error('请选择一条数据')
        return;
      }
      for (let i = 0; i < object.length; i++) {
        this.keys += object[i].processInstanceId;
        if (i !== object.length - 1) {
          this.keys += ',';
        }
      }
      const param = {
        processInstanceId: this.keys
      }
      this.$request({
        url: this.$api.wfBatchDeleteInfo, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {})
        this.$refs['list'].refreshCurrenPage()
        done()
      })
    },    
    suspend() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length == 0) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      }
      if (object[0].isSuspended === true) {
        this.$tsVue.error('该条记录已挂起，请勾选未挂起的记录')
      } else {
        this.$request({
          url: this.$api.wfSuspend, // [必填] 请求地址
          data: { 'processInstanceId': object[0].processInstanceId }, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          this.$refs['list'].refreshCurrenPage()
        })
      }
    },
    submit(data, done, datum) {
      console.log(data)
      if (datum.selectValue === '' || !datum.selectValue) {
        this.$tsVue.error('请选择节点信息')
        return
      }
      if (datum.userId === '' || !datum.userId) {
        this.$tsVue.error('请选择用户')
        return
      }
      const param = {
        processInstanceId: datum.model.processInstanceId,
        nextTaskId: datum.selectValue,
        userId: datum.userId
      }
      this.$request({
        url: this.$api.saveWfPointInfo, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          this.$tsVue.success('任务干预成功')
          done()
        }
      })
    },
    submitVariable(data, done, datum) {
      const param = datum.expandOptions
      param['processInstanceId'] = datum.processInstanceId
      console.log('param', param)
      this.$request({
        url: this.$api.saveProcessVariable, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description)
      })
      done()
    },
    submitEnd(data, done) {
      const object = {
        processInstanceId: data.processInstanceId,
        comment: data.reason
      }
      this.$request({
        url: this.$api.terminateProcess, // [必填] 请求地址
        data: object, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description)
        this.$refs['list'].refreshCurrenPage()
      })
      this.endOptions.data = ''
      done()
    },
    BatchsubmitEnd(data) {
      // const object = {
      //   processInstanceId: data.processInstanceId,
      // }
      this.$request({
        url: this.$api.batchTerminateProcess, // [必填] 请求地址
        data: data, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description)
        this.$refs['list'].refreshCurrenPage()
      })
      this.endOptions.data = ''
      done()
    },
    endWorkFlow() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length == 0) {
        this.$tsVue.error('请选择一条数据')
      }
      // else if(object.length>1){
      //   this.$tsVue.error('只能选择一条数据')
      // } else {
        this.endOptions.data = object
        this.BatchsubmitEnd(this.endOptions.data)
        // this.endVisible = true
      // }
    },
    lookVariable() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length == 0) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      } else {
        this.variableOptions.data = object[0]
        this.variableVisible = true
      }
    },
    activate() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length == 0) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      }
      if (object[0].isSuspended === false) {
        this.$tsVue.error('该条记录已恢复，请勾选未挂起的记录')
      } else {
        this.$request({
          url: this.$api.wfActivate, // [必填] 请求地址
          data: { 'processInstanceId': object[0].processInstanceId }, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description)
          this.$refs['list'].refreshCurrenPage()
        })
      }
    },
    conversionTime(row, column) {
      if (row[column.property] == null) {
        return ''
      }
    },
    conversionSuspended(row, column) {
      if (row[column.property] === false) {
        return '否'
      } else {
        return '是'
      }
    },
    conversion(row, column) {
      if (row[column.property] == null || row[column.property]=='') {
        return '正在运行'
      } else {
        return '已结束'
      }
    },
    maintenance() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length == 0) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      } else {
        const VISITEDVIEWS = JSON.parse(localStorage.getItem('VISITEDVIEWS')) || null
        // 判断是否已打开流程设计器标签页
        let hasTag = false
        VISITEDVIEWS.filter(item => {
          if (item.name === 'processPreview') {
            hasTag = true
          }
        })
        // camunda TODO
        // if (hasTag) {
        //       this.$confirm('是否重新加载流程图', '信息', {
        //           confirmButtonText: '确定',
        //           cancelButtonText: '取消',
        //           type: 'success'
        //       }).then(() => {
        //           object.needupdate = 'yes'
        //           this.$tsVue.tansunPushAddTag(this, '/wf/processPreview', object)
        //       }).catch(() => {
        //       })
        //   } else {
        object[0].needupdate = 'yes'
        this.$tsVue.tansunPushAddTag(this, '/wf/processPreview', object[0])
        // }
      }
    },
    taskIntervene() {
      const object = this.$refs['list'].getSelectedData()
      if (object.length == 0) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      } else {
        this.formOptions.data = object[0]
        console.log("当前的值为")
        console.log(this.formOptions.data)
        this.visible = true
      }
    },
    additionalQuery() {
      if (this.isQuery) {
        this.isQuery = false
        this.icon = 'el-icon-arrow-down'
      } else {
        this.isQuery = true
        this.icon = 'el-icon-arrow-up'
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
      //   console.table(this.$refs['list'].optionsSet)
      this.$refs['list'].serchHandel()
    }
  },
  watch: {
    processType(newval, oldval) {
      if (newval === '' || oldval === newval) {
        return
      } else {
        // this.$refs['demo'].optionsSet.apiName = this.apiNameMap[newval] || apiNameMap['one']
        if (newval === 'endProcess') {
          this.nowApi = 'endProcess'
          this.$refs['list'].optionsSet.apiName = this.$api.wfRunningList
          this.options.apiRequiredParams.ignoreFinish = 'finish'
          this.showButton = false
        } else if (newval === 'runProcess') {
          // 如果是进行的流程那么修改列表的接口重新发请求
          this.nowApi = 'runProcess'
          this.$refs['list'].optionsSet.apiName = this.$api.wfRunningList
          this.options.apiRequiredParams.ignoreFinish = 'unfinish'
          this.showButton = true
        } else if (newval === 'allProcess') {
          this.nowApi = 'allProcess'
          this.$refs['list'].optionsSet.apiName = this.$api.wfRunningList
          this.options.apiRequiredParams.ignoreFinish = ''
          this.showButton = false
        }
        this.$refs['list'].serchHandel()
      }
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        margin-left: 10px;
        margin-top: 10px;
    }
    .buttonDiv{
        margin-bottom: 10px;
    }
</style>

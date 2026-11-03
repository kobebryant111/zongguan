<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="活动实例ID:">
                    <el-input  size="mini" v-model="query.executionId"></el-input>
                </el-form-item>
                <el-form-item label="活动定义ID:">
                    <el-input  size="mini" v-model="query.activityId"></el-input>
                </el-form-item>
                <el-form-item label="流程实例ID：">
                    <el-input  size="mini" v-model="query.processInstanceId"></el-input>
                </el-form-item>
                <el-form-item label="流程定义ID：">
                    <el-input  size="mini" v-model="query.processDefineKey"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                </el-form-item>
                <!-- <el-form-item>
                    <el-button :icon="icon" type="primary" size="mini" @click="additionalQuery" ></el-button>
                </el-form-item> -->
            </el-form>
        </div>
        <!-- <div v-show="isQuery" style="margin-left: 10px;margin-top: -10px">
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
        </div> -->
        <div>
            <ts-multi-table  ref="list" :query="query" :options="options" @reset-query="refreshQuery" >
                <el-table-column prop="tenantId" label="租户编号" min-width="80" show-overflow-tooltip/>
                <el-table-column prop="executionId" label="活动实例ID"/>
                <el-table-column prop="failedActId" label="活动定义ID"/>
                <el-table-column prop="processInstanceId" label="流程实例ID"/>
                <el-table-column prop="createTime" label="启动时间"/>
                <el-table-column prop="rev" label="重试错误次数"/>
                <el-table-column prop="createTime" label="异常信息">
                    <template slot-scope="scope">
                          <el-button @click="errorInfo(scope.row.exceptionMsg)" type="primary">查看</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="选择">
                    <template slot-scope="scope">
                          <el-button @click="dealErrorInfo(scope.row)" type="primary">处理异常</el-button>
                    </template>
                </el-table-column>
            </ts-multi-table>
        </div>

        <el-dialog
          title="错误信息"
          :visible.sync="showErrorInfo"
          width="30%"
          :before-close="handleClose">
          <span>{{errorMsg}}</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="showErrorInfo = false">取 消</el-button>
            <el-button type="primary" @click="showErrorInfo = false">确 定</el-button>
          </span>
        </el-dialog>



        <el-dialog
          title="处理异常"
          :visible.sync="dealError"
          width="30%"
          :before-close="handleClose">
          <span>
            <el-radio-group v-model="radio">
              <el-radio :label="1">重新启动活动</el-radio>
            </el-radio-group>
          </span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dealError = false">取 消</el-button>
            <el-button type="primary" @click="selectDealMethod">确 定</el-button>
          </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
  data() {
    return {
      selectInfo:{

      },
      radio:'',
      errorMsg:'',
      dealError:false,  //处理异常
      showErrorInfo:false,
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
        apiName: this.$api.wfErrorJobList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        // apiRequiredParams: { 'ignoreFinish':
        //  'unfinish', 'startTimeSort': 'desc', tenantId: this.$store.state.user.userInfo.tenantId }, // 选填 表示接口的必需恒定不变的参数
        apiRequiredParams: {tenantId: this.$store.state.user.userInfo.tenantId}, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true // [可不填 | Boolean] 默认值true 显示确认按钮

      },
      nowApi: 'runProcess'
    }
  },
  methods: {
    //处理异常
    selectDealMethod(){
      if(this.radio==''){
        this.$tsVue.error('请选择一条数据')
        return
      }
      const param={
          processInstanceId:this.selectInfo.processInstanceId,
          executionId:this.selectInfo.executionId,
          activityId:this.selectInfo.failedActId
      }
      this.$request({
        url: this.$api.retryServiceTask, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          this.$tsVue.success('任务处理成功')
          this.$refs['list'].refreshCurrenPage()
          this.dealError=false
          done()
        }
      })
      
    },
    //打开处理异常
    dealErrorInfo(row){
      this.selectInfo=row
      this.dealError=true
    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
    errorInfo(row){
        this.errorMsg=row
        this.showErrorInfo=true;
    },
    submit(data, done, datum) {
      console.log(datum)
      if (datum.model.processInstanceId === '') {
        this.$tsVue.error('请选择节点信息')
        return
      }
      if (datum.model.userId === '') {
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
      // const param = datum.expandOptions
      // param['processInstanceId'] = datum.processInstanceId
      const param = {
        'assigneeList': '{}'
      }
      console.log('param', param)
      this.$request({
        url: this.$api.saveProcessVariable, // [必填] 请求地址
        data: { 'key': JSON.stringify(param) }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description)
      })
      done()
    },
    submitEnd(data, done) {
      const object = {
        processInstanceId: data.processInstanceId,
        reason: data.reason
      }
      this.$request({
        url: this.$api.wfDeleteInfo, // [必填] 请求地址
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
    endWorkFlow() {
      const object = this.$refs['list'].getSelectedData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      } else {
        this.endOptions.data = object
        this.endVisible = true
      }
    },
    lookVariable() {
      const object = this.$refs['list'].getSelectedData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      } else {
        this.variableOptions.data = object
        this.variableVisible = true
      }
    },
    activate() {
      const object = this.$refs['list'].getSelectedData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      }
      if (object.isSuspended === false) {
        this.$tsVue.error('该条记录已恢复，请勾选未挂起的记录')
      } else {
        this.$request({
          url: this.$api.wfActivate, // [必填] 请求地址
          data: { 'processInstanceId': object.processInstanceId }, // [选填] 请求参数
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
      if (row[column.property] == null) {
        return '正在运行'
      } else {
        return '已结束'
      }
    },
    maintenance() {
      const object = this.$refs['list'].getSelectedData()
      if (object == null) {
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
        object.needupdate = 'yes'
        this.$tsVue.tansunPushAddTag(this, '/wf/processPreview', object[0])
        // }
      }
    },
    taskIntervene() {
      const object = this.$refs['list'].getSelectedData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      }else if(object.length>1){
        this.$tsVue.error('只能选择一条数据')
      } else {
        this.formOptions.data = object
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

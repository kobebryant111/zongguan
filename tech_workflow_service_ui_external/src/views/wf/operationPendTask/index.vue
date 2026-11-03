<template>
    <div class="app-container">
        <el-row :gutter="25">
            <el-col :span="24">
                <div class="ts-tabs-box">
                    <el-tabs>
                      <div>
                        <div class="leftmenu_navtitle">模拟业务表单数据</div>
                        <div class="allcontent">
                          <div class="col_td" v-for="(item, index) of expandOptions" v-if="item.isQueryCondition == 'Y'">
                            <div class="col4 text_right">{{ item.fieldName }}：</div>
                            <div class="wd190 float_left">
                             <el-input v-if="item.fieldType == 'String'"  placeholder="请输入" size="mini" v-model="expandData[item.fieldKey]"></el-input>
                             <el-input type="number" v-if="item.fieldType == 'Integer' || item.fieldType=='Long' || item.fieldType=='Double'"  placeholder="请输入" size="mini" v-model="expandData[item.fieldKey]" v-on:blur="toNumber(item.fieldKey)"></el-input>
                             <el-col :span="11" v-if="item.fieldType == 'date'" style="padding: 0px">
                                <ts-multi-date-picker v-if="item.fieldType == '2'" v-model="expandData[item.fieldKey]" :params="datePickerParams" />
                              </el-col>
                            </div>
                          </div>

                        </div>

                      </div>
                        <div>
                            <div class="leftmenu_navtitle">执行轨迹
                                <el-button  type="primary" size="mini" icon="el-icon-search" @click="showTrajectory">查询轨迹图</el-button>
<!--                              <el-button type="primary" size="mini" style="float: right" @click="processTrace">流程轨迹</el-button>-->
                            </div>
                            <ts-multi-table ref="list" :options="options" @row-click="taskClick">
                                <el-table-column prop="taskName" label="名称"/>
                                <el-table-column prop="taskId" label="任务ID"/>
                                <el-table-column prop="beginTime" :formatter="conversionDate" label="开始时间"/>
                                <el-table-column prop="endTime"  :formatter="conversionDate" label="结束时间"/>
                                <el-table-column prop="assignee" label="负责人"/>
                                <el-table-column prop="status" label="状态"/>
                                <el-table-column prop="comment" label="处理意见"/>
                            </ts-multi-table>
                        </div>
                      <div v-if="!this.isComplete">
                        <div class="leftmenu_navtitle">下一步任务列表</div>
                        <ts-multi-table ref="nextTaskList" :options="nextTaskListOptions">
                          <el-table-column prop="taskKey" label="任务节点ID"/>
                          <el-table-column prop="taskName" label="任务名称"/>
                          <el-table-column prop="taskType" label="任务类型">
                            <template slot-scope="scope">
                              <el-tag :type="'success'" disable-transitions>{{scope.row.isEndNode == 'Y'?'结束节点':(scope.row.isMulti == 'false'?'用户任务':'会签任务')}}</el-tag>
                            </template>
                          </el-table-column>
                          <el-table-column prop="conditions" label="必要条件变量">
                            <template slot-scope="scope">
                              <el-tag :type="'success'" disable-transitions>{{scope.row.conditions}}</el-tag>
                            </template>
                          </el-table-column>
                          <el-table-column prop="conditions" label="条件表达式">
                            <template slot-scope="scope">
                              <el-tag :type="'success'" disable-transitions>{{scope.row.expressions}}</el-tag>
                            </template>
                          </el-table-column>
                        </ts-multi-table>
                      </div>
                        <div>
                            <div class="leftmenu_navtitle">流程审批</div>
                            <div class="allcontent">
                                <form name="classifyForm">
                                    <div class="col_td">
                                        <div class="col4 text_right">流程实例ID：</div>
                                        <div class="wd190 float_left">
                                            <el-input  size="mini"  v-model="newProcessInstanceId"  :disabled="true" />
                                        </div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right">任务ID：</div>
                                        <div class="wd190 float_left"> <el-input size="mini"  v-model="taskId" :disabled="true" /></div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right">处理人：</div>
                                        <div class="wd190 float_left"> <ts-multi-select v-model="userId" :params="powerUserParam"></ts-multi-select></div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right">执行操作：</div>
                                        <div class="wd190 float_left">
                                            <ts-multi-select v-model="selectOperation" :params="selectOperationParam"></ts-multi-select>
                                        </div>
                                    </div>
                                    <div class="col_td" v-if="selectOperation === 'nextTask' && nextTaskArrays.length > 1 ">
                                        <div class="col4 text_right">选择下一节点：</div>
                                        <div class="wd190 float_left">
                                            <ts-multi-select v-model="activityId" :params="nextTaskArraysParams"></ts-multi-select>
                                        </div>
                                    </div>
                                    <div class="col_td" v-if="selectOperation === 'nextTask' && chooseUser.chooseType === 'common' ">
                                        <div class="col4 text_right">下一节点处理人：</div>
                                        <div class="wd190 float_left"> <el-input size="mini" v-model="nextUsers" @click.native="showSelectPersonDialog" /></div>
                                    </div>
                                     <!--只有当前节点任务不是会签任务，并且下一个节点是会签节点才会显示-->
                                    <div class="col_td" v-if="selectOperation === 'nextTask' && chooseUser.chooseType === 'multi' && routeData.isMulti !== 'true' ">
                                        <div class="col4 text_right">请选择会签处理人：</div>
                                        <div class="wd190 float_left"> <el-input size="mini" v-model="users" @click.native="showSelectPersonDialog" /></div>
                                    </div>
                                    <div class="col_td" v-if="selectOperation === 'appointTask'">
                                        <div class="col4 text_right">退回指定节点：</div>
                                        <div class="wd190 float_left"> <ts-multi-select v-model="selectNextActivityId" :params="selectNextNodeParam"></ts-multi-select></div>
                                    </div>
                                    <div class="col_td" v-if="selectOperation === 'relay'">
                                        <div class="col4 text_right">转办人ID：</div>
                                        <div class="wd190 float_left">  <SelectUserInput :fatherUserId.sync="selectUserId"  /></div>
                                    </div>
                                    <div class="col_td" v-if="selectOperation === 'nextTask' && routeData.isMulti === 'true' ">
                                        <div class="col4 text_right">会审意见：</div>
                                        <div class="wd190 float_left"> <ts-multi-select v-model="votingOpinion" :params="selectMultiParam"></ts-multi-select></div>
                                    </div>
                                    <div class="col_td" v-if="selectOperation === 'addGroupUser' && routeData.isMulti === 'true'">
                                        <div class="col4 text_right">选择加签人 ：</div>
                                        <div class="wd190 float_left">
                                            <el-input size="mini" v-model="addTaskUsers" @click.native="showSelectPersonDialog" />
                                        </div>
                                    </div>
                                    <div class="col_tr">
                                        <div style="margin-left:12%;">审批意见：</div>
                                        <div >
                                            <el-input class="layui-textarea"  :disabled="isComplete" v-model="comment" :rows="3" type="textarea" placeholder="请输入内容"   show-word-limi/>
                                        </div>
                                    </div>
                                   <div  class="col_tr">
                                      <div style="margin-left:12%;">mPara变量: </div>
                                      <div ><el-input  type="textarea" class="layui-textarea"  :rows="3"  v-model="paramStr" />
                                   </div>
                             </div>
                                    <div class="col_td ng-scope">
                                        <div class="col15 text_right">
                                            <el-button type="primary" size="mini" @click="performTask" icon="el-icon-check" v-show="processState === '正在进行'">执行</el-button>
                                            <el-button type="primary" size="mini" @click="back" icon="el-icon-back">返回</el-button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </el-tabs>
                </div>
            </el-col>
        </el-row>
        <ts-multi-dialog  ref="exportNode" :visible.sync="visibleSelectUser" :options="selectPersonOptions"></ts-multi-dialog>
        <ts-multi-dialog :visible.sync="trajectoryDialogVisible" :options="processTrajectoryOptions"></ts-multi-dialog>
    </div>
</template>

<script>
import selectPersonDialog from './selectPersonDialog.vue'
import processTrajectoryDialog from '../wfPendTask/processTrajectoryDialog.vue'
import Qs from 'qs'
import { mapGetters, mapState } from 'vuex'
export default {
  props: {
    task: {
      type: Object,
      default: () => ({})
    },
    isTask: {
      type: Boolean,
      default: false
    },
    processInstanceId: {
      type: String,
      default: ''
    },
    classifyCode: {
      type: String,
      default: ''
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'routeMenu'
    ]),
    ...mapState({
      'userInfo': state => state.user.userInfo,
      'nowThemeInfo': state => state.theme.nowThemeInfo
    }),
    restaurants() {
      const list = this.loadAll()
      return list.filter(item => {
        return item.url
      })
    },
    getProcess() {
      return this.processInstanceId
    }
  },
  data: function() {
    return {
      trajectoryDialogVisible: false,
      isMulti: false,
      paramStr: '',
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.getRunningApproveEditTable,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 session，默认是data
        apiRequiredParams: {
          'processDefineId': this.$router.currentRoute.query.processDefineId,
          'processInstanceId': this.processInstanceId || this.$router.currentRoute.query.processInstanceId }, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true // [可不填 | Boolean] 默认值true 显示确认按钮
      },
      nextTaskListOptions: { // 必填
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.getAllPath,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 session，默认是data
        initTable: true,
        apiRequiredParams: {
          'processDefineId': this.$router.currentRoute.query.processDefinitionId,
          'activityId': this.$router.currentRoute.query.activityId
        },

        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true // [可不填 | Boolean] 默认值true 显示确认按钮
      },
      processTrajectoryOptions: {
        title: '流程预览', // [必填]
        resource: processTrajectoryDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '70%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        callbacks: [null, null], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      // 如果是代办任务进来没有processState字段，默认是正在进行
      processState: this.$router.currentRoute.query.processState === undefined ? '正在进行' : this.$router.currentRoute.query.processState,
      selectPersonOptions: {
        title: '选择候选用户', // [必填]
        resource: selectPersonDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '1300px', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        buttons: ['取 消', '确 定'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      visibleSelectUser: false,
      // 下拉选择框的属性 和 绑定的值
      selectOperationParam: { options: [{ 'text': '下一步', 'value': 'nextTask' }, { 'text': '退回首节点', 'value': 'firstTask' }, { 'text': '退回上一节点', 'value': 'lastTask' }] },
      powerUserParam: { options: [{ 'text': 'superadmin', 'value': 'superadmin' }, { 'text': '张三', 'value': 'zhangsan' }, { 'text': '李四', 'value': 'lisi' }, { 'text': '王五', 'value': 'wangwu' }] },
	     usersParam: {
        options: [{ name: '张三', value: 'zhangsan' }, { name: '李四', value: 'lisi' }, { name: '王五', value: 'wangwu' }],
        separator: ',', // 非必填 多选时的分割符，默认 ,
        text: 'name', value: 'value', multiple: true
      },
      isPassParam: {
	       options: [{ 'text': '通过', 'value': 'Y' }, { 'text': '不通过', 'value': 'N' }]
      },
	  powerUser: '',
      selectOperation: '',
      selectNextNodeParam: { options: [] },
      selectNextActivityId: '',
      selectMultiParam: { options: [{ 'text': '赞成', 'value': 'agree' }, { 'text': '反对', 'value': 'against' }, { 'text': '弃权', 'value': 'noOpinion' }] },
      votingOpinion: '',
      // 选择转办用户的ID
      selectUserId: '',
      // 选人模型下弹窗选择的用户
      nextUsers: '',
      // 会签模式下选择的用户
      users: '',
      userId: '',
      // 加签人
      addTaskUsers: '',
      // 审批意见
      comment: '',
      newProcessInstanceId: this.processInstanceId || this.$router.currentRoute.query.processInstanceId,
      processDefineId: this.$router.currentRoute.query.processDefineId,
      // processDefineId: this.$router.currentRoute.query===undefined?this.task.row.processDefineKey:this.$router.currentRoute.query.processDefineId,
      // processInstanceId: this.$router.currentRoute.query===undefined?this.task.row.processInstanceId:this.$router.currentRoute.query.processInstanceId,
      taskId: this.$router.currentRoute.query.taskId,
      // 路由传过来的数据
      routeData: {},
      chooseUser: {},
      firstNextTaskInfo: '',
      taskExpandDatas: [],
      // 代办 false or 已办 true 进来的,在create中赋值
      isComplete: '',
      selectUserParam: {
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        pagination: true, // 非必填 是否分页
        // 分页多选情况下 如果有赋默认值时需要手动配置默认选中的options
        selectOptions: [],
        clearable: true,
        filterable: true,
        size: 'mini',
        paramMode: 'params', // 非必填 传递参数方式 data|params 默认data
        remote: true, // 非必填 是否通过远程搜索
        optionWidth: 250, // 非必填 宽度默认400
        header: ['账号', '用户名'], // 表格的头部
        body: ['userLoginName', 'userName'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'userLoginName', // 下拉框显示内容，根据需要修改字段名称
        value: 'userLoginName', // 下拉框对应文本的值，根据需要修改字段名称
        pageNum: 1, // 非必填 当前页 分页有效
        pageSize: 5, // 非必填 每页数量 分页有效
        param: {}, // 追加参数
        multiple: true, // 是否多选
        api: this.$api.userList// 接口
        // 非必填 下拉数据默认取 res.data.grid.list 为数据源,如果不满足 自行处理好 options 数据源
        // resHandle: (res, callback) => {
        //   // console.log(res)
        //   const options = res.data.grid.list// 数据源
        //   const hasNextPage = res.data.grid.hasNextPage// 是否有下一页
        //   callback(options, hasNextPage)
        // }
      },
      expandOptions: [],
      expandData: {},
      datePickerParams: {
        type: 'datetime',
        placeholder: '选择日期时间'
      },
      nextTaskArrays: [],
      nextTaskArraysParams: { options: [] },
      activityId: ''
    }
  },
  methods: {
    cancel(data, done) {
      data = ''
      done()
    },
    showTrajectory() {
      this.$request({
        url: this.$api.pendTask, // [必填] 请求地址
        data: { 'processInstanceId': this.processInstanceId }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200 && response.data.grid.list.length > 0) {
          const object = {
            'processDefinitionId': response.data.grid.list[0].processDefinitionId,
            'processInstanceId': this.processInstanceId
          }
          this.processTrajectoryOptions.data = object
          this.trajectoryDialogVisible = true
        }
      })
    },
    taskClick(data) {
      this.powerUserParam.options = []
      this.userId = ''
      this.selectOperation = ''
      // this.selectOperationParam = { options: [] }
      this.processDefineId = data.row.processDefineKey
      this.taskId = data.row.taskId
      if (data.row.assignee != undefined) {
        const powerUsers = data.row.assignee.split(',')
        powerUsers.forEach(
          object => {
            const node = {
              'text': object,
              'value': object
            }
            this.powerUserParam.options.push(node)
          }
        )
      }
      if (data.row.finish === '已完成') {
        this.isComplete = true
      } else {
        this.isComplete = false
      }
      this.initSelectData()
      this.initTaskExpandDatas()
    },
    performTask() {
      if (this.selectOperation === '') {
        this.$tsVue.error('请选择需要执行的操作')
        return
      }
      debugger
      // 拼凑参数
      const taskList = []
      taskList.push({ 'nextActivityId': this.selectNextActivityId })
      const variables = []
      variables.push({ 'key': '', 'value': '' })
      const formDatas = []
      formDatas.push({ 'key': '', 'value': '' })

      const task = {
        'processDefinitionId': this.routeData.processDefinitionId,
        'taskId': this.routeData.taskId,
        'processInstanceId': this.routeData.processInstanceId,
        // 'activityId': this.routeData.activityId,
        'classifyCode': this.routeData.classifyCode,
        'taskList': taskList,
        'variables': variables,
        'formDatas': formDatas,
        'tUserId': this.selectUserId,
        'nextParticipates': this.nextUsers.split(','),
        'nextTask': null,
        'taskExpandDatas': this.taskExpandDatas,
        'button': this.selectOperation,
        'comment': this.comment,
        'mPara': {},
        'userId': this.userId,
        'deleteTaskIds': this.taskId.split(','),
        'addTaskUsers': this.addTaskUsers.split(','),
        'activityId': this.activityId,
        'votingOpinion': this.votingOpinion
      }
      if (this.paramStr != null && this.paramStr.length > 0) {
        task.mPara = JSON.parse(this.paramStr)
      }
      if (this.users) {
        task.mPara.postInfos = this.users.split(',')
      }
      debugger
      if (!task.mPara.postInfos) {
        task.mPara.postInfos = task.nextParticipates
      }
      Object.assign(task.mPara, this.expandData)
      this.$request({
        url: this.$api.executeOperation, // [必填] 请求地址
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        transformRequest: [function(data) { // 在请求之前对data传参进行格式转换
          data = Qs.stringify(data)
		        console.log(data)
          return data
        }],
        data: { 'taskId': this.routeData.taskId, 'button': this.selectOperation, 'paramStr': JSON.stringify(task) }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description)
        this.users = ''
        this.addTaskUsers = ''
        this.nextUsers = ''
        this.$refs['list'].refreshCurrenPage()
        this.initTask()
      })
    },
    // 提交后进行回调，同时刷新父组件和兄弟组件
    // 弹窗提交保存
    submit(data, done, datum) {
      // 获取已经选择了用户id 数组,需要判断下一个节点是啥类型 对应添加数据
      if (this.isMulti) {
        this.users = datum.selectUserList.map(item => item.userLoginName).join()
        this.addTaskUsers = datum.selectUserList.map(item => item.userLoginName).join()
      } else {
        this.nextUsers = datum.selectUserList.map(item => item.userLoginName).join()
      }
      done()
    },
    back() {
      this.$tsVue.tansunBack(this, { 'needupdate': 'yes' })
    },
    conversionDate(row, column) {
      if (row[column.property] !== null) {
        return row[column.property].substr(0, 19).replace('T', ' ')
      }
    },
    showSelectPersonDialog() {
      // 需要请求回调成功后才能打开页面不然提示下吧
      if (this.firstNextTaskInfo === '') {
        console.log('需要请求回调成功后才能打开弹窗')
      } else {
        this.selectPersonOptions.data = this.firstNextTaskInfo
        this.visibleSelectUser = true
      }
    },
    initSelectData() {
      // 因为刷新页面后路由数据格式会变掉这里需要每次拿到taskId 请求后端获取指定taskId的数据
      if (this.isComplete) {
        this.getTaskInfo(this.$api.wfCompleteTask)
      } else {
        this.getTaskInfo(this.$api.pendOrgTask)
      }
    },
    getTaskInfo(url) {
      const _this = this
      console.log(url)
      this.$request({
        url: url, // [必填] 请求地址
        data: { 'taskId': this.taskId || this.$router.currentRoute.query.taskId }, // [选填] 请求参数
        type: 'get', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        const options = []
        debugger
        _this.routeData = response.data.grid.list[0]
        if (!_this.routeData || _this.routeData.actButton === '') {
          // this.selectOperationParam.options = []
        } else {
          let buttonList = _this.routeData.actButton
          console.log('buttonList', buttonList)
          if (typeof buttonList == 'string') {
            buttonList = JSON.parse(buttonList)
          }
          buttonList.forEach(object => {
            const node = {
              'text': object.alias[0],
              'value': object.key
            }
            const isPendFlag = _this.isComplete === false ? 'Y' : 'N'
            if (object.isPend === isPendFlag) {
              options.push(node)
            }
          })
          // this.selectOperationParam.options = options
        }
      })
    },

    initTaskExpandDatas() {
      this.$request({
        url: this.$api.virtualForm, // [必填] 请求地址
        data: { 'wfClassId': this.classifyCode }, // [选填] 请求参数
        type: 'get', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          this.expandOptions = response.data

          this.$request({
            url: this.$api.getTaskExpandDetail,
            data: { 'taskId': this.taskId || this.$router.currentRoute.query.taskId },
            type: 'post',
            paramMode: 'params'
          }).then((response) => {
            if (response.data) {
              this.expandData = response.data
            }
            this.expandData.role = this.userInfo.currRlId
          })
        }
      })
    },
    initAllPath() {
      this.$request({
        url: this.$api.getAllPath,
        data: { 'processDefineId': this.$router.currentRoute.query.processDefinitionId, activityId: this.$router.currentRoute.query.activityId },
        type: 'post',
        paramMode: 'params'
      }).then((response) => {
        console.log(222)
        console.log(response.data)
        if (response.data) {
          this.expandData = response.data
        }
      })
    },
    processTrace() {

    },
    initTask() {
      debugger
      const _this = this
      this.$request({
        url: this.$api.pendOrgTask, // [必填] 请求地址
        data: { 'processInstanceId': this.processInstanceId, 'queryAllTask': true }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200 && response.data.grid.list.length > 0) {
          _this.userId = ''
          // this.powerUserParam.options = []
          debugger
          _this.taskId = response.data.grid.list[0].taskId
          _this.nextTaskListOptions.apiRequiredParams.processDefineId = response.data.grid.list[0].processDefinitionId
          _this.nextTaskListOptions.apiRequiredParams.activityId = response.data.grid.list[0].activityId
          _this.$refs['nextTaskList'].refreshCurrenPage()
          const powerUsers = response.data.grid.list[0].powerUsers
          if (powerUsers) {
            powerUsers.forEach(
              object => {
                const node = {
                  'text': object,
                  'value': object
                }
                _this.powerUserParam.options.push(node)
              }
            )
            console.log('下拉框')
            console.log(_this.powerUserParam.options)
          }

          _this.selectOperation = ''
          const options = []
          debugger
          _this.routeData = response.data.grid.list[0]
          if (!_this.routeData || _this.routeData.actButton === '') {
            // this.selectOperationParam.options = []
          } else {
            let buttonList = _this.routeData.actButton
            console.log('buttonList', buttonList)
            if (typeof buttonList == 'string') {
              buttonList = JSON.parse(buttonList)
            }
            for (const obj of buttonList) {
              const node = {
                'text': obj.alias[0],
                'value': obj.key
              }
              if (obj.isPend === 'Y') {
                options.push(node)
              }
            }

            // this.selectOperationParam.options = options
            console.log('按钮下拉框')
            console.log(_this.selectOperationParam.options)
          }
          _this.initTaskExpandDatas()
        }
        // if (response.status === 200 && response.data.grid.list.length === 0) {
        //   this.$tsVue.success('流程提交完成', () => {
        //     this.$tsVue.tansunBack(this, { 'needupdate': 'yes' })
        //   })
        // }
      })
    }
  },
  watch: {
    task() {
      if (this.task) {
        console.log(this.task.row)
        this.processDefineId = this.task.row.processDefineKey
        this.newProcessInstanceId = this.task.row.processInstanceId
        this.taskId = this.task.row.taskId
        const powerUsers = this.task.row.powerUsers
        powerUsers.forEach(
          object => {
            const node = {
              'text': object,
              'value': object
            }
            // this.powerUserParam.options.push(node)
          }
        )
        console.log('下拉框')
        console.log(this.powerUserParam.options)
        this.selectOperation = ''
        this.initSelectData()
        this.initTaskExpandDatas()
      }
    },
    selectOperation(newValue) {
      // TODO 查询下一节点，兼容多种数据类型
      const mPara = []
      if (this.expandData) {
        for (const key in this.expandData) {
          mPara.push({ 'key': key, 'value': this.expandData[key] })
        }
      }
      debugger
      if (this.paramStr != null && this.paramStr.length > 0) {
        const o = JSON.parse(this.paramStr)
        const keys = Object.keys(o)
        for (const key of keys) {
          mPara.push({ 'key': key, 'value': o[key] })
        }
      }
      // private   String   key;
      //
      // private   Object    value;
      //
      // private   Class     clazz = String.class;
      // 选择下一步 的时候
      const _this = this
      if (newValue === 'nextTask') {
        this.$request({
          url: this.$api.queryNextTaskList, // [必填] 请求地址
          data: { 'taskId': this.taskId, 'mPara': JSON.stringify(mPara) }, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          debugger
          if (response.status === 200 && response.data && response.data.length > 0) {
            // multi: 会签选人，common:普通选人，parallel：并行选人
            console.log(response.data.length)
            _this.nextTaskArrays = []
            _this.nextTaskArraysParams = { options: [] }
            if (response.data.length > 1) {
              _this.chooseUser['chooseUser'] = 'parallel'
              _this.nextTaskArrays = response.data
              const options = []
              _this.nextTaskArrays.forEach(object => {
                const node = {
                  'text': object.nextActivityIName,
                  'value': object.nextActivityId
                }
                options.push(node)
              })
              _this.nextTaskArraysParams.options = options
            } else {
              const firstNextTaskInfo = response.data[0]
              _this.firstNextTaskInfo = response.data[0]
              _this.chooseUser['firstNextTaskInfo'] = firstNextTaskInfo
              if (firstNextTaskInfo.nextIsMulti === 'true') {
                _this.isMulti = true
                _this.chooseUser['chooseType'] = 'multi'
              } else {
				            _this.chooseUser['chooseType'] = undefined
              }
              //  else {
              //   _this.chooseUser['chooseType'] = 'common'
              // }
            }
            console.log('chooseUser.chooseType:' + _this.chooseUser['chooseType'])
          } else {
          //
          }
        })
      }
      if (newValue === 'appointTask') {
        this.$request({
          url: this.$api.taskNodeList, // [必填] 请求地址
          data: { 'taskId': this.taskId, 'processInstanceId': this.newProcessInstanceId }, // [选填] 请求参数
          type: 'get', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          if (response.status === 200 && response.data.length !== 0) {
            const nodeList = response.data
            nodeList.forEach(object => {
              const node = {
                'text': object.name,
                'value': object.activitiId
              }
              _this.selectNextNodeParam.options.push(node)
            })
          } else {
            _this.selectNextNodeParam.options = []
          }
        })
      }
    },
    activityId(newValue) {
      console.log(newValue)
      const data = this.nextTaskArrays.filter(function(item) {
        if (item.nextActivityId == newValue) {
          return item
        }
      })
      const firstNextTaskInfo = data[0]
      this.firstNextTaskInfo = data[0]
      this.chooseUser['firstNextTaskInfo'] = firstNextTaskInfo
      if (firstNextTaskInfo.nextIsMulti === 'true') {
        this.isMulti = true
        this.chooseUser['chooseType'] = 'multi'
      } else {
        this.chooseUser['chooseType'] = undefined
      }
      //  else {
      //   this.chooseUser['chooseType'] = 'common'
      // }
    },
    getProcess(newValue, oldValue) {
      this.options.apiRequiredParams.processInstanceId = newValue
      console.log('流程实例id更新了')
      this.$refs['list'].refreshCurrenPage()
      this.initTask()
    },
    toNumber(key) {
      this.expandData[key] = Number(this.expandData[key])
    }
  },
  created() {
    this.processInstanceId = this.processInstanceId || this.$router.currentRoute.query.processInstanceId
    this.isComplete = this.$router.currentRoute.query.isComplete
    if (this.$router.currentRoute.query.isComplete === 'true') {
      this.isComplete = true
    }
    if (this.$router.currentRoute.query.isComplete === 'false') {
      this.isComplete = false
    }
  },
  mounted() {
    debugger
    if (!this.isComplete) {
      this.initTask()
    } else {
      this.initSelectData()
    }
    this.initTaskExpandDatas()
    // this.initAllPath();
  }
}

</script>

<style lang="scss" scoped>
    .col_td {
        width:50%;
        float:left;
        height:28px;
        margin-top: 15px;
        line-height:28px;
        margin-bottom:8px;
    }
    .col_tr {
        width:100%;
        float:left;
        margin-top: 15px;
        display:table;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
    }
    .leftmenu_navtitle {
        height:32px;
        line-height:32px;
        font-weight:bold;
        border-bottom:solid 3px #4586E0;
    }
    .col1,.col2,.col3,.col4,.col5,.col6,.col7,.col8,.col9,.col10 {
        line-height:26px;
    }
    .layui-textarea {
        margin-top: -20px;
        margin-left: 20%;
        width: 62%;
        line-height:20px;
        position:relative
    }
    .allcontent {
        width:98%;
        padding:1%;
        display:table;
        margin-bottom:20px;
    }
    .col4 {
        width:40%;
        float:left;
    }
    .text_right {
        text-align:right;
    }
    .textarea {
        margin-right: -40px;
    }
    .wd190 {
        width:190px !important;
        position:relative;
    }
    .float_left {
        float:left;
    }
</style>

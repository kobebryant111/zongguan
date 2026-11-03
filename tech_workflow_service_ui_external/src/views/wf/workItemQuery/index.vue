
<template>
    <div class="app-container">
        <div class="ts-tabs-box">
            <el-tabs>
                <div>
                    <el-form inline>
                        <el-form-item label="流程实例ID" >
                            <el-input placeholder="请输入流程名称" size="mini" v-model="query.processInstanceId"></el-input>
                        </el-form-item>
                        <el-form-item label="任务ID">
                            <el-input placeholder="请输入任务ID" size="mini" v-model="query.taskId"></el-input>
                        </el-form-item>
                        <el-form-item label="任务名称">
                            <el-input placeholder="请输入任务名称" size="mini" v-model="query.taskName"></el-input>
                        </el-form-item>
                        <el-form-item label="业务主键">
                            <el-input placeholder="请输入业务主键" size="mini" v-model="query.businessKey"></el-input>
                        </el-form-item>
                        <el-form-item label="用户名称">
                            <el-select v-model="query.userName"  clearable  filterable   placeholder="请选择" @change="userChanage">
                                <el-option v-for="item in userList"  :label="item.userName"  :value="item.userLoginName" :key="item.userLoginName" />
                            </el-select>
                        </el-form-item>
                        <el-form-item :label="item.fieldName" v-for="(item) of plans" v-show="item.isQueryCondition == 'Y'">
                            <el-input v-if="item.fieldType != '2'"  placeholder="请输入" size="mini" v-model="query[item.fieldKey]"></el-input>
                            <el-col :span="11" v-if="item.fieldType == '2'" style="padding: 0px">
                                <ts-multi-date-picker v-if="item.fieldType == '2'" v-model="query[item.fieldKey + 'Before']" :params="datePickerParams" />
                            </el-col>
                            <el-col class="line" :span="2" v-if="item.fieldType == '2'" >-</el-col>
                            <el-col :span="11" v-if="item.fieldType == '2'" style="padding: 0px">
                                <ts-multi-date-picker v-if="item.fieldType == '2'" v-model="query[item.fieldKey + 'After']" :params="datePickerParams"  style="padding: 0px"></ts-multi-date-picker>
                            </el-col>

                        </el-form-item>
                        <el-form-item label="任务创建时间：">
                            <el-date-picker
                                    v-model="createTime"
                                    value-format="yyyy-MM-dd"
                                    type="daterange"
                                    range-separator="至"
                                    start-placeholder="开始日期"
                                    end-placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="流程启动时间：">
                            <el-date-picker
                                    v-model="processCreateTime"
                                    value-format="yyyy-MM-dd"
                                    type="daterange"
                                    range-separator="至"
                                    start-placeholder="开始日期"
                                    end-placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item>
                            <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                        </el-form-item>
                        <el-form-item>
                            <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                        </el-form-item>
                    </el-form>
                </div>
                <div class="buttonDiv" v-if="!isTask">
                    <el-button v-if ="showButton" type="primary" size="mini" icon="el-icon-close"  @click="endWorkFlow" >终止</el-button>
                    <el-button type="primary" size="mini" icon="el-icon-search"  @click="batchDelete" >删除</el-button>
                </div>
                <ts-multi-dialog :visible.sync="endVisible" :options="endOptions"></ts-multi-dialog>
                <div>
                    <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery" @row-click="childClick">
                        <el-table-column prop="taskId" label="任务ID" min-width="90" show-overflow-tooltip/>
                        <el-table-column prop="activityName" label="任务名称"/>
                        <el-table-column prop="processInstanceId" label="流程实例ID"/>
                        <el-table-column prop="processDefineName" label="流程定义名称"/>
                        <el-table-column prop="processStartTime" label="任务开始时间"/>
                        <el-table-column prop="startUserId" label="发起人"/>
                        <el-table-column prop="assignee" label="处理人"/>
                        <el-table-column prop="createTime" label="创建时间"/>
                        <el-table-column
                            v-for="(item) of plans"
                            v-show="item.fieldStatus !=='0'"
                            :key="item.id"
                            :prop="item.fieldKey"
                            :label="item.fieldName"
                        ></el-table-column>
                    </ts-multi-table>
                </div>
            </el-tabs>
        </div>
    </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import defaultSettings from '@/plugins/settings'
import processTrajectoryDialog from './processTrajectoryDialog.vue'
import endDialog from './endDialog.vue'

const sessionMode = defaultSettings.enableSession
export default {
  props: {
    isTask: {
      type: Boolean,
      default: false
    },
    newProcessInstanceId: {
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
      debugger
      const list = this.loadAll()
      debugger
      return list.filter(item => {
        return item.url
      })
    }
  },
  data() {
    return {
      processCreateTime: [],
      createTime: [],
      endTime: [],
      showButton: true,
      endVisible: false,
      treeStyle: {
        'height': '400px'
      },
      trajectoryDialogVisible: false,
      isMultiSelect: false,
      userList: [],
      query: { userNum: '' }, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.wfWorkItemList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        initTable: false,
        apiRequiredParams: {
          'taskPoolStatus': '1',
          tenantId: this.$store.state.user.userInfo.tenantId

        }, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
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
      plans: [],
      datePickerParams: {
        type: 'datetime',
        placeholder: '选择日期时间'
      }
    }
  },
  methods: {
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
    },
    endWorkFlow() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      } else {
        this.endOptions.data = [{ 'processInstanceId': object.processInstanceId }]
        // this.endVisible = true
        this.BatchsubmitEnd(this.endOptions.data)
      }
    },
    // 批量删除
    batchDelete() {
      const object = this.$refs['list'].getRadioData()
      this.keys = object.processInstanceId
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
      })
    },
    handleNodeClick(node) {
      this.$set(this.options.apiRequiredParams, 'classifyCode', node.code)
      // 需要重置列表的页数默认是1
      this.$refs['list'].refreshDataSource()
      if (node.plans) {
        this.plans = node.plans
      }
    },
    userChanage(e) {
      // console.log(e)
      // this.$forceUpdate()
    },
    childClick(data) {
      console.log(data)
      this.$emit('childByValue', data)
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
      delete this.options.apiRequiredParams.classifyCode
      this.$refs['list'].serchHandel()
    }
  },
  mounted() {
    const _this = this

      this.$request({
          url: this.$api.pendOrgUserList,
          data: {},
          type: 'post'
      }).then(res => {
          for (const v of res.data) {
              const user = {}
              _this.$set(user, 'userNum', v.useId)
              const userName = v.userName + '(' + v.userLoginName + ')'
              _this.$set(user, 'userName', userName)
              _this.$set(user, 'userLoginName', v.userLoginName)
              _this.userList.push(user)
          }
      })
  },
  created() {
    // 统一按登录的用户查询代办
    if (!this.isTask) {
      // this.$set(this.options.apiRequiredParams, 'userId', this.userInfo.loginName)
      this.options.initTable = true
    }
  },
  watch: {
    createTime(newval, oldval) {
      this.query = JSON.parse(JSON.stringify(this.query))
      if (newval) {
        this.query.createTimeAfter = newval[0]
        this.query.createTimeBefore = newval[1]
      } else {
        this.query.createTimeAfter = undefined
        this.query.createTimeBefore = undefined
      }
    },
    endTime(newval, oldval) {
      this.query = JSON.parse(JSON.stringify(this.query))
      if (newval) {
        this.query.endTimeAfter = newval[0]
        this.query.endTimeBefore = newval[1]
      } else {
        this.query.endTimeAfter = undefined
        this.query.endTimeBefore = undefined
      }
    },
    processCreateTime(newval, oldval) {
      this.query = JSON.parse(JSON.stringify(this.query))
      if (newval) {
        this.query.processCreateTimeAfter = newval[1]
        this.query.processCreateTimeBefore = newval[0]
      } else {
        this.query.processCreateTimeAfter = undefined
        this.query.processCreateTimeBefore = undefined
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
    .mright_10 {
        margin-right: 10px;
    }

    .text_color {
        color: #3B8EDA
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

    .leftmenu_navtitle {
      height:32px;
      line-height:32px;
      font-weight:bold;
      border-bottom:solid 3px #4586E0;
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
</style>

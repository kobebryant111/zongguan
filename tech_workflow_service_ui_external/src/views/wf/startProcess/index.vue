<template>
    <div class="app-container">
        <el-row :gutter="25">
<!--            <el-col :span="4">-->
<!--                <div class="ts-pane-box">-->
<!--                    <el-tabs type="border-card" >-->
<!--                        <el-tab-pane label="测试用例树">-->
<!--                            <ts-multi-trees-->
<!--                                    :style = {height:treeStyle.height}-->
<!--                                    :options="options"-->
<!--                                    ref="testTree"-->
<!--                                    @node-click="handleNodeClick">-->
<!--                            </ts-multi-trees>-->
<!--                            <div class="maintree_button">-->
<!--                                <el-button type="primary"  size="mini" @click="addTest">新增测试</el-button>-->
<!--                                <el-button type="primary" size="mini" @click="deleteTest">清除历史</el-button>-->
<!--                            </div>-->
<!--                        </el-tab-pane>-->
<!--                    </el-tabs>-->
<!--                </div>-->
<!--            </el-col>-->
            <el-col :span=24 >
                <div class="ts-tabs-box">
                    <div class="app-container">
                        <div class="ts-tabs-box">
                            <el-tabs>
                                <div class="leftmenu_navtitle">流程基本信息</div>
                                <div class="allcontent">
                                    <form name="classifyForm">
                                        <div class="col_td">
                                            <div class="col4 text_right"><i class="red_input">*</i>流程键值：</div>
                                            <div class="wd190 float_left">
                                                <el-input  size="mini" v-model="form.processDefKey" :disabled="true" />
                                            </div>
                                        </div>
                                        <div class="col_td">
                                            <div class="col4 text_right"><i class="red_input">*</i>租户：</div>
                                            <div class="wd190 float_left"> <el-input size="mini" v-model="form.tenantName"  :disabled="true" /></div>
                                        </div>
                                        <div class="col_td">
                                            <div class="col4 text_right">业务主键：</div>
                                            <div class="wd190 float_left"><el-input  size="mini" v-model="form.businessKey" /></div>
                                        </div>
                                        <div class="col_td">
                                            <div class="col4 text_right">流程实例id：</div>
                                            <div class="wd190 float_left"><el-input  size="mini" v-model="form.processInstanceId" /></div>
                                        </div>
                                        <div class="col_td">
                                            <div class="col4 text_right">发起人：</div>
                                            <div class="wd190 float_left"><el-input  size="mini" v-model="form.userId" /></div>
                                        </div>
                                        <div class="col_td">
                                            <div class="col4 text_right">发起人部门：</div>
                                            <div class="wd190 float_left">
                                                <el-input  size="mini" v-model="form.startDeptId" />
                                            </div>
                                        </div>
                                        <div class="col_td">
                                            <div class="col4 text_right">发起人机构：</div>
                                            <div class="wd190 float_left">
                                                <el-input  size="mini" v-model="form.startOrgId" />
                                            </div>
                                        </div>
                                        <div class="col_td">
                                            <div class="col4 text_right">发起人角色：</div>
                                            <div class="wd190 float_left">
                                                <el-input  size="mini" v-model="form.startRoleId" />
                                            </div>
                                        </div>
                                        <!-- 赞不展示                            <div class="col_td">-->
                                        <!--                                <div class="col4 text_right">上一节点审批人同机构id：</div>-->
                                        <!--                                <div class="wd190 float_left">-->
                                        <!--                                    <el-input  size="mini" v-model="form.beforeNodeOrgId" />-->
                                        <!--                                </div>-->
                                        <!--                            </div>-->
                                        <!--                            <div class="col_td">-->
                                        <!--                                <div class="col4 text_right">上一节点审批人上级机构同机构id：</div>-->
                                        <!--                                <div class="wd190 float_left">-->
                                        <!--                                    <el-input  size="mini" v-model="form.beforeNodeParentOrgId" />-->
                                        <!--                                </div>-->
                                        <!--                            </div>-->
                                        <!--                            <div class="col_td">-->
                                        <!--                                <div class="col4 text_right">与上一个节点审批人下级机构同机构id：</div>-->
                                        <!--                                <div class="wd190 float_left">-->
                                        <!--                                    <el-input  size="mini" v-赞model="form.beforeNodeChildOrgId" />-->
                                        <!--                                </div>-->
                                        <!--                            </div>-->
                                        <!--                            <div class="col_td">-->
                                        <!--                                <div class="col4 text_right">与上一个节点审批人同部门id：</div>-->
                                        <!--                                <div class="wd190 float_left">-->
                                        <!--                                    <el-input  size="mini" v-model="form.eforeNodeDeptId" />-->
                                        <!--                                </div>-->
                                        <!--                            </div>-->
                                        <!--                            <div class="col_td">-->
                                        <!--                                <div class="col4 text_right">与上一个节点审批人上级部门同部门id：</div>-->
                                        <!--                                <div class="wd190 float_left">-->
                                        <!--                                    <el-input  size="mini" v-model="form.beforeNodeParentDeptId" />-->
                                        <!--                                </div>-->
                                        <!--                            </div>-->
                                        <!--                            <div class="col_td">-->
                                        <!--                                <div class="col4 text_right">与上一个节点审批人下级部门同部门id：</div>-->
                                        <!--                                <div class="wd190 float_left">-->
                                        <!--                                    <el-input  size="mini" v-model="form.beforeNodeChildDeptId" />-->
                                        <!--                                </div>-->
                                        <!--                            </div>-->
                                        <div class="col_td">
                                            <div class="col4 text_right" >是否同时提交首个任务：</div>
                                            <el-switch
                                                    placement="top"
                                                    v-model="form.andSubmit"
                                                    active-text="是"
                                                    inactive-text="否">
                                            </el-switch>
                                        </div>
                                    </form>
                                </div>
                            </el-tabs>
                        </div>
                    </div>
                    <div class="app-container" v-if="!isTask">
                        <div class="ts-tabs-box" v-if="expandOptions && expandOptions.length >0">
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
                                      <div  class="col_tr">
                                      <div  style="margin-left:12%;">mPara变量: </div>
                                      <div  ><el-input  type="textarea" class="layui-textarea"  :rows="3"  v-model="paramStr"  />
                                      </div>
                                   </div>
                                    </div>
                                </div>
                            </el-tabs>

                        </div>
                        <el-row :gutter="25">
                            <el-col :span="24">

                            </el-col>
                        </el-row>
                    </div>

                    <div style="margin-top: 30px;width: 100%;text-align:center" v-if="!isTask">
                        <el-button type="primary" @click="submit" icon="el-icon-circle-check">启动</el-button>
                        <el-button type="primary" @click="back" icon="el-icon-back">返回</el-button>
                    </div>
                    <OperationTask v-if="isTask" :task="task" :processInstanceId="processInstanceId" :classifyCode="classifyCode"></OperationTask>

                </div>
            </el-col>

        </el-row>
    </div>
</template>

<script>
import OperationTask from '../operationPendTask/index.vue'
import { mapState, mapGetters } from 'vuex'
import defaultSettings from '@/plugins/settings'
const sessionMode = defaultSettings.enableSession

export default {
  components: {
    OperationTask
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
    }
  },
  data: function() {
    return {
      treeStyle: {
        'height': '400px'
      },
      value: '是',
      isTask: false,
      node: {},
      paramStr: '',
      mPara: {
      },
      formData: {
      },
      task: {},
      queryForm: {},
      processInstanceId: '',
      classifyCode: '',
      datePickerParams: {
        type: 'datetime',
        placeholder: '选择日期时间'
      },
      expandData: {},
      form: {},
      selectParam: {
        options: [{ label: '是', value: '1' }, { label: '否', value: '0' }], text: 'label', value: 'value',
        size: 'mini', width: '190px'
      },
      options: {
        onlyChildSelected: true, // 选填，是否只允许选择叶子节点
        apiName: this.$api.testTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        params: {},
        callback: (data) => {
          // 构建树map 方便后期查找映射父节点
          data.forEach(object => {
            this.treeDataMap[object.id] = object
          })
        }
      },
      expandOptions: []
    }
  },
  methods: {
    back() {
      this.$tsVue.tansunBack(this, { 'needupdate': 'yes' })
    },
    addTest() {
      this.isTask = false
      this.form = this.queryForm
    },
    deleteTest() {
      this.$confirm('确定删除该测试用例所有实例？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          'planCode': this.queryForm.planCode
        }
        this.$request({
          url: this.$api.deleteIns, // [必填] 请求地址
          data: data, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {
            this.$refs['testTree'].updateTree()
          })
        })
      }).catch(() => {
      })
    },
    initTableData() {
      this.$request({
        url: this.$api.virtualForm,
        data: { 'wfClassId': this.queryForm.classifyCode },
        type: 'post',
        paramMode: 'params'
      }).then((response) => {
        if (response.data) {
          this.expandOptions = response.data
        }
      }).catch(error => {
        reject(error)
      })
    },
    childByValue(data) {
      // data就是子组件传过来的值
      this.task = data
    },
    submit() {
      debugger
      console.log('aaa--')
      console.log(this.expandData)
      this.expandData['nextUser'] = this.form.userId
      this.form['mPara'] = this.expandData
      this.form['formData'] = this.expandData
      this.form['taskExpandData'] = this.expandData
      this.form['processDefineKey'] = this.queryForm.processDefKey
      console.log(this.form)
      if (this.paramStr != null && this.paramStr.length > 0) {
        this.form['mPara'] = JSON.parse(this.paramStr)
      }
      debugger
      const _this = this
      this.$request({
        url: this.$api.startTest, // [必填] 请求地
        data: this.form, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description, () => {
          _this.isTask = true
          _this.processInstanceId = response.data
          // this.$refs['testTree'].updateTree()
        })
      })
    },
    handleNodeClick(node) {
      console.log('节点信息')
      console.log(node)
      this.form.processDefKey = node.processDefKey
      this.form.tenantName = node.processInfo.tenantId
      this.form.businessKey = node.processInfo.businessKey
      this.form.processInstanceId = node.processInstanceId
      this.processInstanceId = node.processInstanceId
      this.isTask = true
    },
    toNumber(key) {
      this.expandData[key] = Number(this.expandData[key])
    }
  },
  created() {
    this.queryForm = this.$router.currentRoute.query
    this.options.params.processDefineKey = this.queryForm.processDefineKey

    this.classifyCode = this.queryForm.classifyCode
  },
  mounted() {
    this.queryForm.userId = this.userInfo.loginName
    this.queryForm.startDeptId = this.userInfo.currDeptCd
    this.queryForm.startOrgId = this.userInfo.currInstCd
    this.queryForm.startRoleId = this.userInfo.currRlId
    Object.assign({}, this.queryForm)
    this.form = { ... this.queryForm }
    this.initTableData()
  }
}
</script>

<style lang="scss" scoped>
    .subposition {
        width:100%;
        height:30px;
        line-height:12px;
        font-size:12px;
        font-weight:bold;

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
    .col_td {
        width:50%;
        float:left;
        height:28px;
        margin-top: 15px;
        line-height:28px;
        margin-bottom:8px;
    }
    .red_input {
        font-size:13px;
        color:#FF0000;
        display:inline-block;
        font-weight:bold;
        margin-right:5px;
        font-style:normal;
    }
    .maintree_title {
        width:100%;
        height:34px;
        line-height:34px;
        padding-left:10px;
        background:#4586E0;
        color:#FFF;
        font-weight: bold;
    }
    .leftmenu_navtitle {
        height:32px;
        line-height:32px;
        font-weight:bold;
        margin-top: 20px;
        border-bottom:solid 3px #4586E0;
    }
    .col1,.col2,.col3,.col4,.col5,.col6,.col7,.col8,.col9,.col10 {
        line-height:26px;
    }
    .float_left {
        float:left;
    }
    .allcontent {
        width:98%;
        padding:1%;
        display:table;
        margin-bottom:20px;
    }
    .layui-textarea {
        margin-top: -20px;
        margin-left: 20%;
        width: 62%;
        line-height:20px;
        position:relative
    }
    .col4 {
        width:40%;
        float:left;
    }
    .text_right {
        text-align:right;
    }
    .wd190 {
        width:190px !important;
        position:relative;
    }
    .float_left {
        float:left;
    }
</style>

<template>
    <div class="app-container">
        <el-row :gutter="25">
            <el-col :span="4">
                <div class="ts-pane-box">
                    <el-tabs type="border-card" >
                        <el-tab-pane label="测试用例树">
                            <ts-multi-trees
                                    style="width: 100%;height: 400px;"
                                    :options="options"
                                    ref="testTree"
                                    @node-click="handleNodeClick">
                            </ts-multi-trees>
                            <div class="maintree_button">
                                <el-button type="primary"  size="mini" @click="addTest">新增测试</el-button>
                                <el-button type="primary" size="mini" @click="deleteTest">清除历史</el-button>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-col>
            <el-col :span=20 >
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
                                        <div class="col_td">
                                            <div class="col4 text_right">发起人上级机构：</div>
                                            <div class="wd190 float_left">
                                                <el-input  size="mini" v-model="form.startParentOrgId" />
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
                    <div class="app-container">
                        <div class="ts-tabs-box" v-if="expandOptions && expandOptions.length >0">
                            <el-tabs>
                                <div>
                                    <div class="leftmenu_navtitle">模拟业务表单数据</div>
                                    <div class="allcontent">
                                        <div class="col_td" v-for="(item, index) of expandOptions" v-if="item.isQueryCondition == 'Y'">
                                            <div class="col4 text_right">{{ item.fieldName }}：</div>
                                            <div class="wd190 float_left">
                                                <el-input v-if="item.fieldType != '2'"  placeholder="请输入" size="mini" v-model="expandData[item.fieldKey]"></el-input>

                                                <el-col :span="11" v-if="item.fieldType == '2'" style="padding: 0px">
                                                    <ts-multi-date-picker v-if="item.fieldType == '2'" v-model="expandData[item.fieldKey]" :params="datePickerParams" />
                                                </el-col>
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
                    <div v-if="isTask">
                        <div class="leftmenu_navtitle"  >待办任务</div>
                        <ts-multi-table ref="pendList" :options="pendOptions"  @row-click="pendClick">
                            <el-table-column prop="taskId" label="任务ID" width="120" show-overflow-tooltip/>
                            <el-table-column prop="activityName" label="任务名称"/>
                            <el-table-column prop="processInstanceId" label="流程实例ID"/>
                            <el-table-column prop="processDefineName" label="流程名称"/>
                            <el-table-column prop="powerUserNames"  label="处理人"/>
                            <el-table-column prop="remarks" width="250" label="备注"/>
                            <el-table-column prop="processStartTime" label="流程开始时间"/>
                            <el-table-column prop="businessKey" label="发起人"/>
                        </ts-multi-table>
                    </div>
                    <div v-if="isTask">
                        <div class="leftmenu_navtitle">审批历史
                        </div>
                        <ts-multi-table ref="hisList" :options="hisOptions" @row-click="hisClick">
                            <el-table-column prop="taskName" label="名称"/>
                            <el-table-column prop="taskId" label="任务ID"/>
                            <el-table-column prop="beginTime" :formatter="conversionDate" label="开始时间"/>
                            <el-table-column prop="endTime"  :formatter="conversionDate" label="结束时间"/>
                            <el-table-column prop="assigneeName" label="负责人"/>
                            <el-table-column prop="finish" label="处理状态"/>
                            <el-table-column prop="comment" label="处理意见"/>
                        </ts-multi-table>
                    </div>
                    <div v-if="isTask">
                        <div class="leftmenu_navtitle">流程审批</div>
                        <div class="allcontent">
                            <form name="classifyForm">
                                <div class="col_td">
                                    <div class="col4 text_right">流程实例ID：</div>
                                    <div class="wd190 float_left">
                                        <el-input  size="mini"  v-model="processInstanceId"  :disabled="true" />
                                    </div>
                                </div>
                                <div class="col_td">
                                    <div class="col4 text_right">任务ID：</div>
                                    <div class="wd190 float_left"> <el-input size="mini"  v-model="task.taskId" :disabled="true" /></div>
                                </div>
                                <div class="col_td">
                                    <div class="col4 text_right">处理人：</div>
                                    <div class="wd190 float_left">
                                        <ts-multi-select v-model="task.userId" :params="powerUserParam"></ts-multi-select>
                                    </div>
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
                                    <div class="wd190 float_left"> <ts-multi-select v-model="addTaskUsers" :params="selectUserParam"></ts-multi-select></div>
                                </div>
                                <div class="col_tr">
                                    <div style="margin-left:15%;">审批意见：</div>
                                    <div >
                                        <el-input class="layui-textarea"  :disabled="isComplete" v-model="comment" :rows="4" type="textarea" placeholder="请输入内容"  maxlength="10" show-word-limi/>
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
                </div>
            </el-col>

        </el-row>
    </div>
</template>

<script>
    import OperationTask from '../operationPendTask/index.vue'
    import WfPendTask from '../wfPendTask/index.vue'
    import { mapState, mapGetters } from 'vuex'
    import defaultSettings from '@/plugins/settings'
    const sessionMode = defaultSettings.enableSession

    export default {
        components: {
            OperationTask,
            WfPendTask
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
                value: '是',
                isTask: false,
                node: {},
                mPara: {
                },
                formData: {
                },
                task: {},
                queryForm:{},
                processInstanceId: '',
                classifyCode: '',
                selectOperation:'',
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
                pendOptions: { // 必填
                    type: 'radio', // radio|checkbox|'' 单选/多选/无选
                    // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
                    apiName: this.$api.pendTask,
                    method: 'post', // 默认走‘post’
                    paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
                    initTable: false,
                    apiRequiredParams: {
                        'processInstanceId': this.processInstanceId,
                        'taskPoolStatus': '1'
                    }, // 选填 表示接口的必需恒定不变的参数
                    showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
                    showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
                    isNeedPagination: true, // 选填 是否需要分页器，默认是true
                    pageNum: 1, // 选填 当前页码
                    pageSize: 10, // 选填 每页条数
                    pagesizes: [5, 10, 15, 20], // 选填 页码间距
                    layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
                },
                hisOptions: { // 必填
                    // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
                    apiName: this.$api.getRunningApproveEditTable,
                    method: 'post', // 默认走‘post’
                    paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 session，默认是data
                    apiRequiredParams: {
                        'processInstanceId': this.processInstanceId
                    }, // 选填 表示接口的必需恒定不变的参数
                    showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
                    showConfirmBtn: true // [可不填 | Boolean] 默认值true 显示确认按钮
                },
                expandOptions: [],
                powerUserParam: { options: [] },
            }
        },
        methods: {
            back() {
                this.$tsVue.tansunBack(this, { 'needupdate': 'yes' })
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
            submit() {
                this.expandData['nextUser'] = this.form.userId
                this.form['mPara'] = this.expandData
                this.form['formData'] = this.expandData
                this.form['taskExpandData'] = this.expandData
                this.form['processDefineKey'] = this.queryForm.processDefKey
                this.$request({
                    url: this.$api.startTest, // [必填] 请求地
                    data: this.form, // [选填] 请求参数
                    type: 'post', // [选填] String 请求方式 get|post 默认post
                    paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
                }).then((response) => {
                    this.$tsVue.success(response.description, () => {
                        this.isTask = true
                        this.processInstanceId = response.data
                        this.$refs['testTree'].updateTree()
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
                this.isTask = true
                this.processInstanceId = node.processInstanceId
            },
            pendClick(data){
                this.task=data.row
                const powerUsers = data.row.powerUsers
                powerUsers.forEach(
                    object => {
                        const node = {
                            'text': object,
                            'value': object
                        }
                        this.powerUserParam.options.push(node)
                    }
                )
                this.getTaskInfo(this.$api.pendTask)
            },
            hisClick(data){
                this.task=data.row
                this.task.userId=data.row.userId
                this.getTaskInfo(this.$api.wfCompleteTask)
            },
            getTaskInfo(url) {
                this.$request({
                    url: url, // [必填] 请求地址
                    data: { 'taskId': this.task.taskId }, // [选填] 请求参数
                    type: 'get', // [选填] String 请求方式 get|post 默认post
                    paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
                }).then((response) => {
                    const options = []
                    this.routeData = response.data.grid.list[0]
                    if (!this.routeData  || this.routeData.actButton === '') {
                        // this.selectOperationParam.options = []
                    } else {
                        const buttonList = this.routeData.actButton
                        console.log('buttonList', buttonList)
                        buttonList.forEach(object => {
                            const node = {
                                'text': object.alias[0],
                                'value': object.key
                            }
                            const isPendFlag = this.isComplete === false ? 'Y' : 'N'
                            if (object.isPend === isPendFlag) {
                                options.push(node)
                            }
                        })
                        // this.selectOperationParam.options = options
                    }
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
        },
        created() {
            this.queryForm = this.$router.currentRoute.query;
            this.options.params.processDefineKey = this.queryForm.processDefineKey;
            this.classifyCode = this.queryForm.classifyCode;
        },
        mounted() {
            this.queryForm.userId = this.userInfo.loginName
            this.queryForm.startDeptId = this.userInfo.currDeptCd
            this.queryForm.startOrgtId = this.userInfo.currInstCd
            this.queryForm.startRoleId = this.userInfo.currRlId
            Object.assign({},this.queryForm)
            this.form = {... this.queryForm};
            this.initTableData()
        },
        watch: {
            processInstanceId(newValue) {
                this.pendOptions.apiRequiredParams.processInstanceId = newValue
                this.hisOptions.apiRequiredParams.processInstanceId = newValue
                console.log('流程实例id更新了')
                this.$refs['pendList'].refreshCurrenPage()
                this.$refs['hisList'].refreshCurrenPage()
            }
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

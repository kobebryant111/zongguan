<!-- 离场申请-发起 -->
<template>
    <div>
        <div class="nav">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="3">
                            <p class="nav_name">外包人员离场申请</p>
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <el-col :span="1.5">
                            <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                        </el-col>
                    </div>
                </div>
            </el-row>
        </div>
        <div class="contentMain">
            <div class="grxx-content">
                <div class="grxx-title">
                    <p>个人信息</p>
                </div>
                <div>
                    <table class="grxx-table">
                        <tr class="grxx-table-tr">
                            <div>
                                <td>姓名</td>
                                <td>{{wblcData.userName == undefined ? '无' : wblcData.userName}}</td>
                            </div>
                            <div>
                                <td>性别</td>
                                <td>{{wblcData.sexname == undefined ? '无' : wblcData.sexname}}</td>
                            </div>
                            <div>
                                <td>联系电话</td>
                                <td>{{wblcData.mobile == undefined ? '无' : wblcData.mobile}}</td>
                            </div>
                            <div>
                                <td>供应商</td>
                                <td>{{wblcData.outsourcname == undefined ? '无' : wblcData.outsourcname}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>证件类型</td>
                                <td>{{wblcData.certificateTypename == undefined ? '无' : wblcData.certificateTypename}}</td>
                            </div>
                            <div>
                                <td>证件号码</td>
                                <td>{{wblcData.certificateNumber == undefined ? '无' : wblcData.certificateNumber}}</td>
                            </div>
                            <div>
                                <td>生日</td>
                                <td>{{wblcData.birthday == undefined ? '无' : wblcData.birthday}}</td>
                            </div>
                            <div>
                                <td>籍贯</td>
                                <td>{{wblcData.hometown == undefined ? '无' : wblcData.hometown}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>毕业院校</td>
                                <td>{{wblcData.college == undefined ? '无' : wblcData.college}}</td>
                            </div>
                            <div>
                                <td>专业信息</td>
                                <td>{{wblcData.profession == undefined ? '无' : wblcData.profession}}</td>
                            </div>
                            <div>
                                <td>学历</td>
                                <td>{{wblcData.educationname == undefined ? '无' : wblcData.educationname}}</td>
                            </div>
                            <div>
                                <td>婚姻状况</td>
                                <td>{{wblcData.maritalStatusname == undefined ? '无' : wblcData.maritalStatusname}}</td>
                            </div>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="grxx-content">
                <div class="grxx-title">
                    <p>登记信息</p>
                </div>
                <div>
                    <table class="grxx-table">
                        <tr class="grxx-table-tr">
                            <div>
                                <td>外包方式</td>
                                <td>{{wblcData.outsourceTypename == undefined ? '无' : wblcData.outsourceTypename}}</td>
                            </div>
                            <div>
                                <td>所属合同</td>
                                <td>{{wblcData.htmc == undefined ? '合同暂未签订' : wblcData.htmc}}</td>
                            </div>
                            <div>
                                <td>结算方式</td>
                                <td>{{wblcData.userTypename == undefined ? '无' : wblcData.userTypename}}</td>
                            </div>
                            <div>
                                <td>入职时间</td>
                                <td>{{wblcData.beginTime == undefined ? '无' : wblcData.beginTime}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>所属部门</td>
                                <td>{{wblcData.depname == undefined ? '无' : wblcData.depname}}</td>
                            </div>
                            <div>
                                <td>所在小组</td>
                                <td>{{wblcData.groupname == undefined ? '无' : wblcData.groupname}}</td>
                            </div>
                            <div>
                                <td>行内负责人</td>
                                <td>{{wblcData.personInChargename == undefined ? '无' : wblcData.personInChargename}}</td>
                            </div>
                            <div>
                                <td>岗位</td>
                                <td>{{wblcData.postname == undefined ? '无' : wblcData.postname}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>项目名称</td>
                                <td>{{wblcData.projectname == undefined ? '无' : wblcData.projectname}}</td>
                            </div>
                            <div>
                                <td>项目经理</td>
                                <td>{{wblcData.projectmanagername == undefined ? '无' : wblcData.projectmanagername}}</td>
                            </div>
                            <div>
                                <td>系统名称</td>
                                <td>{{wblcData.systemnames == undefined ? '无' : wblcData.systemnames}}</td>
                            </div>
                            <div>
                                <td>系统负责人</td>
                                <td>{{wblcData.systemprincipalnames == undefined ? '无' :
                                    wblcData.systemprincipalnames}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>工号</td>
                                <td>{{wblcData.jobId == undefined ? '无' : wblcData.jobId}}</td>
                            </div>
                            <div>
                            </div>
                            <div>
                            </div>
                            <div>
                            </div>
                        </tr>
                    </table>
                </div>
            </div>


            <div class="grxx-content">
                <div class="grxx-title">
                    <p>离场信息</p>
                </div>
                <div class="lcxx-mian">
                    <el-form :model="wblcEditData" ref="wblcForm">
                        <el-row type="flex" justify="space-between">
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">实际离职时间 *</p>
                                <el-form-item prop="yjlgsj" :rules="[{ required: true, message: '请选择离职时间', trigger: 'blur' }]">
                                    <el-date-picker v-model="wblcEditData.yjlgsj" type="date" placeholder="请选择日期"
                                        value-format="yyyy-MM-dd"
                                        ></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <!-- <p class="lcxx-mian-cell-title">离场说明 *</p>
                                <el-form-item prop="leaveexplain" :rules="[{ required: true, message: '请填写离场说明', trigger: 'blur' }]">
                                    <el-input type="textarea" autosize v-model="wblcEditData.leaveexplain" placeholder="请填写离场的原因，及对部门或当前项目组的影响"></el-input>
                                </el-form-item> -->
                                <div v-if="wblcData.personInCharge == userId" key="1">
                                    <p class="lcxx-mian-cell-title">离场说明 *</p>
                                    <el-form-item prop="leaveexplain" :rules="[{ required: true, message: '请填写离场说明', trigger: 'blur' }]">
                                        <el-input type="textarea" autosize v-model="wblcEditData.leaveexplain"
                                            placeholder="请填写离场的原因，及对部门或当前项目组的影响"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">离场说明</p>
                                    <el-form-item>
                                        <el-input type="textarea" autosize v-model="wblcEditData.leaveexplain"
                                            placeholder="请填写离场的原因，及对部门或当前项目组的影响"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="7">
                                <!-- <p class="lcxx-mian-cell-title">工作交接情况 *</p>
                                <el-form-item prop="workhandover" :rules="[{ required: true, message: '请填写工作交接情况', trigger: 'blur' }]">
                                    <el-input type="textarea" autosize v-model="wblcEditData.workhandover" placeholder="填写撤离后的应对措施，替换人员的技能概况及到位时间"></el-input>
                                </el-form-item> -->
                                <div v-if="wblcData.personInCharge == userId" key="1">
                                    <p class="lcxx-mian-cell-title">工作交接情况 *</p>
                                    <el-form-item prop="workhandover" :rules="[{ required: true, message: '请填写工作交接情况', trigger: 'blur' }]">
                                        <el-input type="textarea" autosize v-model="wblcEditData.workhandover"
                                            placeholder="填写撤离后的应对措施，替换人员的技能概况及到位时间"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">工作交接情况</p>
                                    <el-form-item>
                                        <el-input type="textarea" autosize v-model="wblcEditData.workhandover"
                                            placeholder="填写撤离后的应对措施，替换人员的技能概况及到位时间"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row type="flex" justify="space-between">
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">外包商领导意见</p>
                                <el-form-item>
                                    <el-input type="textarea" autosize v-model="wblcEditData.organizersuggestion"
                                        placeholder="请输入"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">项目经理意见</p>
                                <el-form-item>
                                    <el-input type="textarea" autosize v-model="wblcEditData.managersuggestion"
                                        placeholder="请输入"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7"></el-col>
                        </el-row>
                    </el-form>
                </div>
            </div>

            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs ref="log"></flow-logs>
            </div>
        </div>

        <div class="nav bottom">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="1.5">
                            <button-delete :buttonName="buttonDel" :size="size" :fns="delBox"></button-delete>
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <div class="wrapper-right-keep">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonKeep" :size="size" :fns="saveRequire"></button-common>
                            </el-col>
                        </div>
                        <div>
                            <el-col :span="1.5">
                                <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
                            </el-col>
                        </div>
                    </div>
                </div>
            </el-row>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
    import { save, queryDetail, getBaseInfo, outLeaveStart, del } from "@/api/integrated/wblc";
    import common from "@/api/common";
    import { doDel } from '@/api/common/flowApi';
    import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
    import Moment from 'moment';
    import { mapGetters } from "vuex";
    export default {
        name: 'wblcApply',
        components: { buttonOrange, buttonCommon, buttonDelete, flowLogs },
        data() {
            return {
                buttonClose: '关 闭', //取消按钮名字
                size: 'mini',  //按钮大小
                buttonDel: '删除',
                buttonKeep: '存草稿',
                buttonCommit: '提交',
                wblcEditData: {},
                wblcData: {},
                workItemID: '',
                processDefName: '', //流程定义名称
                delDialog: false, //是否显示删除弹窗
                bps: {},
                instanceid: '',
            };
        },
        computed: {
            ...mapGetters(["userId"])
        },
        created() {
        },
        mounted() {
        },
        methods: {
            // 初始化
            init(item) {
                console.log("离场页面", item);
                this.bps = item;
                this.workItemID = item.workItemID;
                this.processDefName = item.processDefName;
                this.instanceid = item.processInstID;
                this.getWblcDetail(item.processInstID);
                this.$refs.log.init(item.processInstID);
            },

            // 获取离场详细信息
            getWblcDetail(id) {
                let params = {};
                params.instanceid = id;
                let data = common.paramsFormat(params);
                getBaseInfo(data)
                    .then(res => {
                        if (res.data.status == 200) {
                            let wblcDetail = res.data.results.baseinfo;
                            if (wblcDetail.depname != undefined) {
                                var reg = /([^-]+)$/;
                                wblcDetail.depname = wblcDetail.depname.match(reg)[1];
                            }
                            if (wblcDetail.beginTime != undefined) {
                                wblcDetail.beginTime = Moment(wblcDetail.beginTime).format('YYYY-MM-DD');
                            }
                            this.wblcData = wblcDetail;
                            this.wblcEditData = res.data.results.lzsq;
                        } else {
                            this.$message.error('获取数据失败');
                        }

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
            },

            // 打开删除messageBox窗口
            delBox() {
                this.$confirm('确认删除该流程吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.delPro();
                }).catch(() => {

                })
            },

            // 保存需求信息
            saveRequire() {
                const loading = common.loading(this);
                let data = common.paramsFormat(this.wblcEditData);
                save(data).then(res => {
                    // 关闭loading
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('保存成功');
                    } else {
                        this.$message.error('保存草稿失败');
                    }
                });

            },

            // 提交本页面数据
            commit() {
                const loading = common.loading(this);
                let data = {};
                data.lzsq = this.wblcEditData;
                data.bps = this.bps;
                outLeaveStart(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        let message = common.getFlowMessge(res.data.message);
                        let messageArr = message.split(",");
                        // this.messageNotify(messageArr);
                        messageArr[2] = messageArr[2].split(")")[0] + ')';
                        this.messageNotifyHnfzr(messageArr);
                        let finishData = {};
                        finishData.userName = this.wblcData.userName;
                        finishData.jobId = this.wblcData.jobId;
                        finishData.outsourcname = this.wblcData.outsourcname;
                        finishData.depname = this.wblcData.depname;
                        finishData.personInChargename = this.wblcData.personInChargename;
                        finishData.saveMark = '1';
                        this.$router.push({
                            name: 'wblcFinish',
                            params: {
                                finishData: finishData
                            }
                        });
                    } else {
                        this.$message.error('操作失败');
                    }
                });
            },

            // 验证必填项并提交
            proving() {
                this.$refs['wblcForm'].validate((valid) => {
                    if (valid) {
                        this.commit();
                    } else {
                        this.$message({
                            showClose: true,
                            message: '操作失败,请填写必填项',
                            type: 'error'
                        })
                        return false;
                    }
                })
            },

            // 删除流程
            delPro() {
                const loading = common.loading(this)
                let params = {};
                params.processInstID = this.instanceid;
                params.workItemID = this.workItemID;
                params.processDefName = this.processDefName;
                let data = common.paramsFormat(params);
                doDel(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('流程删除成功');
                        this.$router.back();
                    } else {
                        this.$message.error('流程删除失败');
                    }

                });
            },

            // 删除按钮
            deleteProcess() {
                this.delDialog = true;
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },

            //消息提示
            messageNotify(messageVal) {
                this.$notify({
                    title: '提示',
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageVal[2] + '</div></div>',
                    duration: 0
                })
            },

            // 消息提示(多节点)
            messageNotifyHnfzr(messageVal) {
                if (messageVal.length > 3) {
                    messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(")")[0] + ')';
                }
                let messageSec = messageVal.slice(2).join("，");
                this.$notify({
                    title: '提示',
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
                    duration: 0
                })
            },
        }
    };
</script>
<style scoped>
    .contentMain {
        width: 100%;
        height: 100%;
        font-size: 16px;
        padding: 0 20px;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
        height: 24px;
        font-size: 16px;
        font-weight: 600;
        color: rgba(38, 38, 38, 1);
        line-height: 24px;
        display: inline-block;
    }

    .nav .wrapper {
        margin-left: 25px;
        margin-right: 25px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .wrapper-right-keep {
        margin-right: 10px;
    }

    .grxx-title {
        height: 60px;
        line-height: 60px;
        padding: 0 25px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .grxx-title>p {
        color: #262626;
        font-weight: 600;
    }

    .grxx-content {
        background-color: #ffffff;
    }

    .grxx-table {
        width: 100%;
        padding: 10px;
        table-layout: fixed;
    }

    .grxx-table td {
        width: 100%;
        font-size: 15px;
        padding: 5px 0;
    }

    .grxx-table-tr {
        display: flex;
        justify-content: space-between;
    }

    .grxx-table-tr div {
        display: inline-block;
    }

    .grxx-table-tr td:nth-child(odd) {
        color: #909399;
        width: 100px;
        white-space: nowrap;
    }

    .grxx-table-tr td:nth-child(even) {
        color: #262626;
        width: 261px;
    }

    .lcxx-mian {
        padding: 0px 18px;
    }

    .lcxx-mian-cell-title {
        color: #262626;
        font-size: 14px;
    }

    .bottom {
        margin-top: 20px;
        padding: 15px 0;
    }
</style>
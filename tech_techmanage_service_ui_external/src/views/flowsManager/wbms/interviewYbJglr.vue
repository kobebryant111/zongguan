<!-- 结果录入-已办 -->
<template>
    <div>
        <div class="head-container">
            <!-- 头部 -->
            <!-- bin -->
            <div class="nav">
                <el-row>
                    <!-- <el-col :span="24"> -->
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="1.5">
                                <p class="nav_name">外包面试审批</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                            </el-col>
                        </div>
                    </div>
                    <!-- </el-col> -->
                </el-row>
            </div>
        </div>
        <!-- 主体 -->
        <div class="contentMainScore">
            <div class="contentTwo_title">
                <p class="contentTwo_title_p">面试信息</p>
            </div>
            <div class="content">
                <!-- 表第一行 -->
                <div class="row_1">
                    <el-row type="flex" justify="space-between">
                        <el-col :span="7">
                            <p>部门</p>
                            <el-select v-model="msgdeptname" disabled>
                                <el-option :value="msgdeptname">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="7">
                            <p>人力需求</p>
                            <el-select v-model="rlIds" placeholder="请选择人力需求" :disabled="true">
                                <el-option :value="rlIds"></el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="7">
                            <p>面试官</p>
                            <el-select v-model="msgNames" placeholder="请选择面试官" disabled>
                                <el-option :value="msgNames"></el-option>
                            </el-select>
                        </el-col>
                    </el-row>
                    <el-row type="flex" justify="space-between">
                        <el-col :span="7">
                            <p>是否符合要求</p>
                            <el-radio-group v-model="tableModel.meet" :disabled="true">
                                <el-radio v-for="item in meet_options" :label="item.label" :key="item.label">{{item.value}}</el-radio>
                            </el-radio-group>
                        </el-col>
                        <el-col :span="7">
                            <p>原因</p>
                            <el-input type="textarea" autosize v-model="tableModel.remarks" :disabled="true"></el-input>
                        </el-col>
                        <el-col :span="7">

                        </el-col>
                    </el-row>
                </div>

                <!-- 应聘者信息 -->
                <div class="row_2 row_2_interview_resultEntry">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">应聘者信息</p>
                    </div>
                    <el-row>
                        <el-col :span="24">
                            <div class="row_2_content">
                                <el-form :model="tableModel" ref="formYpz" size="small">
                                    <el-table :data="tableModel.ypzData">
                                        <el-table-column prop="msname" label="姓名" width="80" :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column prop="sexName" label="性别" width="50"></el-table-column>
                                        <el-table-column prop="birthday" label="出生日期" width="100"></el-table-column>
                                        <el-table-column prop="outsourcName" label="外包商" :show-overflow-tooltip="true"
                                            width="220"></el-table-column>
                                        <el-table-column prop="positionName" label="人员类型" width="120"></el-table-column>
                                        <el-table-column prop="positionName" label="人员属性" width="80">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.researchers == 1">人力外包</span>
                                                <span v-else-if="scope.row.researchers == 2">项目人力</span>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="workyears" label="年限" width="50"></el-table-column>
                                        <el-table-column prop="suggestlevel" label="等级" width="80">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.suggestlevel == 1">1 (1-3年)</span>
                                                <span v-else-if="scope.row.suggestlevel == 2">2 (3-5年)</span>
                                                <span v-else-if="scope.row.suggestlevel == 3">3 (5-7年)</span>
                                                <span v-else-if="scope.row.suggestlevel == 4">4 (7年以上)</span>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="简历" width="100">
                                            <template slot-scope="scope">
                                                <a href="javascript:void();" @click="openNtkoWindow(scope.row.jl)"
                                                    style="color: rgba(251, 164, 47, 1)">预览</a>
                                                <a href="javascript:void();" @click="jlDownliad(scope.row.jl)" style="color: rgba(251, 164, 47, 1)">下载</a>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="mspy" label="评语" width="200" :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column prop="score" label="成绩" width="60"></el-table-column>
                                        <!-- 
                                            <el-table-column prop="interviewresult" label="结果" width="80">
                                            <template slot-scope="scope">
                                                <span v-for="(item, index) of scoreData" :key="index">
                                                    <span v-if="item.dictid == scope.row.interviewresult">{{
                                                        item.dictname}}</span>
                                                </span>
                                            </template>
                                        </el-table-column>
                                        -->
                                        <el-table-column prop="suggestprice" label="建议单价" width="150">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`ypzData.${scope.$index}.suggestprice`">
                                                    <el-input-number size="medium" v-model="scope.row.suggestprice"
                                                        controls-position="right" :step="500" :disabled="true"></el-input-number>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label=""></el-table-column>
                                    </el-table>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                    <div class="contentTwo_title_2">
                        <p class="contentTwo_title_p_2">单价参考</p>
                    </div>
                    <el-row>
                        <el-col>
                            <div class="row_2_content_2">

                                <el-table :data="priceConsult" style="width: 100%">
                                    <el-table-column prop="outsourcname" label="公司" :show-overflow-tooltip="true" width="220">

                                    </el-table-column>
                                    <el-table-column prop="positionName" label="人员类型" :show-overflow-tooltip="true"
                                        width="90">

                                    </el-table-column>
                                    <el-table-column label="一档(1-3年)" :show-overflow-tooltip="true" width="170">
                                        <template slot-scope="scope">
                                            <span>
                                                {{scope.row.firststartprie}} - {{scope.row.firstendprie}}
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="二档(3-5年)" width="170" :show-overflow-tooltip="true">
                                        <template slot-scope="scope">
                                            <span>
                                                {{scope.row.secondstartprie}} - {{scope.row.secondendprie}}
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="三档(5-7年)" width="170" :show-overflow-tooltip="true">
                                        <template slot-scope="scope">
                                            <span>
                                                {{scope.row.thirdstartprie}} - {{scope.row.thirdendprie}}
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="四档(7年以上)" width="170" :show-overflow-tooltip="true">
                                        <template slot-scope="scope">
                                            <span>
                                                {{scope.row.fourthstartprie}} - {{scope.row.fourthendprie}}
                                            </span>
                                        </template>
                                    </el-table-column>

                                    <el-table-column>

                                    </el-table-column>
                                </el-table>

                            </div>
                        </el-col>
                    </el-row>
                </div>


            </div>

            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs :processInstId="instanceid" ref="log"></flow-logs>
            </div>
        </div>

        <!-- 空格占位 -->
        <div class="blank"></div>

        <!-- 底部操作栏 -->
        <div class="bottom_zb">
            <yb-revoke :flowItem="flowItem"></yb-revoke>
        </div>
    </div>
</template>
<script>
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    import { getInterviewScore, getOutsourcPrice, doResultEntry, queryManpowerdemands, download, rlxqQueryDetail } from "@/api/integrated/wbms";
    import departmentTwo from "@/views/plugins/components/DepartmentTwo";
    import employee from "@/views/plugins/components/Employee";
    import { getDicEntry } from "@/api/eosDictEntry";
    import flowBtn from "@/views/plugins/camunda/FlowBtn";
    import common from "@/api/common";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import Moment from 'moment'
    import ybRevoke from "@/views/plugins/camunda/ybRevoke";
    let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
    export default {
        name: "ResultEntry",
        components: { flowLogs, departmentTwo, employee, flowBtn, buttonOrange, buttonCommon, ybRevoke },
        data() {
            return {
                ypzData: [], // 应聘者数据集合
                tableModel: {
                    ypzData: []
                },
                msgData: [], // 面试官数据集合
                scoreData: [], // 面试结果数据集合
                flowItem: {}, // 流程对象
                objectcode: "6000000300",
                objectgrade: "2",
                instanceid:"",
                mpOpts: [], // 人力需求集合数据
                rlIds: '', //人力需求绑定值
                msgdeptno: "", // 部门编号
                msgdeptname: "", //部门名字
                msgNames: '',    // 面试官名称
                buttonClose: '关 闭', //取消按钮名字
                size: 'mini',  //按钮大小
                successMessage: '', // 成功消息提示
                errorMessage: '', //失败消息提示
                rulePrice: {
                    suggestprice: [
                        { required: false, message: '', trigger: 'blur' }
                    ],
                },
                priceConsult: [], //单价参考
                meet_options: [
                    {
                        value: '是',
                        label: '1'
                    },
                    {
                        value: '否',
                        label: '0'
                    }
                ], //是否符合要求
            };
        },

        methods: {
            init(params) {
                const self = this;
                self.flowItem = params;
                self.instanceid = params.processInstanceId;
                self.$nextTick(_ => {
                    getInterviewScore({ instanceid: self.instanceid }).then(res => {
                        if (res.data.status != 200) {
                            self.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        const data = res.data.results;
                        var reg = /([^-]+)$/;
                        self.msgdeptname = data.erList[0].msgdeptname.match(reg)[1]
                        self.getRlxq(data.eeList[0].rlId);
                        self.msgdeptno = data.erList[0].msgdeptno;
                        let msgId = [],
                            msgName = [];
                        // 获取面试官id和名称
                        data.erList.forEach(ii => {
                            msgName.push(ii.msgname);
                        });
                        self.msgIds = data.erList[0].msgid;
                        self.msgNames = msgName.join(",");
                        let temps = [];
                        let counts = 0;
                        // 外包商单价
                        data.eeList.forEach(item => {
                            let params = { id: item.wbsid, position: item.position };

                            getOutsourcPrice(params).then(res => {
                                const priceData = res.data.results;
                                if (priceData !== undefined) {
                                    item.upData = priceData;
                                    temps.push(item);
                                }
                            }).then(() => {
                                counts++;
                                self.tableModel.ypzData = data.eeList;
                                self.tableModel.meet = data.meet.toString();
                                self.tableModel.remarks = data.remarks;
                                if (counts == data.eeList.length) {
                                    this.$nextTick(() => {
                                        this.filArr(temps);
                                    })
                                }
                            });
                        });
                    }); // init end

                    // 流程日志
                    self.$refs.log.init(self.instanceid);
                    // 业务字典 面试结果
                    getDicEntry({ dicttypeid: "NEW_BUSI_OUTSOURCE_INTERVRESULT" }).then(
                        res => {
                            if (res.data.status !== 200) {
                                self.alert(res.data.message, { type: "error" });
                                return false;
                            }
                            self.scoreData = res.data.results;
                        }
                    ); // end
                });
            }, // init end

            // 获取人力需求
            getRlxq(val) {
                let params = {};
                params.id = val;
                rlxqQueryDetail(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            let rlIdData = res.data.results;
                            var reg = /([^-]+)$/;
                            this.rlIds = rlIdData.username + rlIdData.depname.match(reg)[1] + Moment(rlIdData.supplytime).format('YYYY-MM-DD') + rlIdData.usertypename;
                        } else {
                            this.errorMessage = '获取数据失败'
                            this.messageError();
                        }
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 数组去重
            filArr(ele) {
                let priceArr = [];
                let eleFilter = [];
                if (ele == []) {
                    return;
                } else {
                    for (var i = 0; i < ele.length; i++) {
                        if (!(eleFilter.includes(ele[i].wbsid + '-' + ele[i].position))) {
                            eleFilter.push(ele[i].wbsid + '-' + ele[i].position);
                            priceArr.push(ele[i].upData);
                        }
                    }

                }
                this.priceConsult = priceArr;
            },

            /**
             * 流转
             */
            liuzhuan: function (params) {
                const loading = common.loading(this);
                this.flowItem.conclusion = params.conclusion;
                this.flowItem.suggest = params.suggest;
                let wbData = { eeList: this.tableModel.ypzData };
                let folw = { bps: this.flowItem };
                let newObj = Object.assign(folw, wbData);
                return doResultEntry(newObj)
                    .then(res => {
                        loading.close();
                        return res.data
                    })
                    .catch(error => {
                        this.$alert(error, { type: "error" });
                    });
            }, // 流转 end


            onCancel() { },
            onSubmit() { },

            // 流转并验证
            proving(data) {
                let status = new Promise(() => { });
                if (data.conclusion == "01") {
                    this.$refs['formYpz'].validate((valid) => {
                        if (valid) {
                            status = this.liuzhuan(data).then((val) => {
                                return val;
                            });
                        } else {
                            this.errorMessage = '操作失败,请填写必填项'
                            this.messageError();
                            return false;
                        }
                    })
                } else {
                    status = this.liuzhuan(data).then((val) => {
                        return val;
                    });
                }
                return status;
            },

            // 文档下载
            jlDownliad(docId) {
                const self = this;
                common.downloadJl(docId, self);
            },

            // ntko
            openNtkoWindow(objectId) {
                var url = window.location.href;
                let ntkoed = ntkoBrowser.ExtensionInstalled();
                if (ntkoed) {
                    ntkoBrowser.openWindow(
                        url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
                    );
                } else {
                    window.open(
                        url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
                    );
                }
                // 注册回调方法
                window.ntkoCloseEvent = function () {
                    alert("跨浏览器插件应用程序窗口已关闭!");
                };
            },

            // 成功消息提示
            messageSuccess() {
                this.$message({
                    message: this.successMessage,
                    type: 'success'
                })
            },

            // 失败消息提示
            messageError() {
                this.$message.error(this.errorMessage);
            },

            // 关闭按钮
            close() {
                // this.$router.back();
                this.$router.push({ name: 'dashboard' });
            }
        },

    };
</script>

<style scoped>
    /* bin修改 */
    .contentMainScore {
        width: 100%;
        height: 100%;
        font-size: 16px;
        padding: 10px 20px;
    }

    /* 导航菜单栏 */
    .nav {
        width: 100%;
        /* height: 30px; */
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 100px;
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

    /* 操作日志 */
    .contentLog {
        margin-top: 15px;
    }

    .content {
        width: 100%;
        font-size: 16px;
        /* background-color: #ffffff; */
        /* padding: 15px 20px; */
    }

    .content p {
        color: #262626;
        font-size: 14px;
        /* margin: 12px 0; */
    }

    .content .row_2_detail {
        font-size: 16px;
        font-weight: 600;
    }

    .contentTwo_title {
        height: 60px;
        line-height: 60px;
        background-color: #ffffff;
        padding: 0 25px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .content .contentTwo_title_p {
        color: #262626;
        font-size: 16px;
        font-weight: 600;
    }

    .contentMainScore .row_1 {
        background-color: #ffffff;
        padding: 0 20px 15px 20px;
        /* padding: 0 20px; */
    }

    .contentMainScore .row_2_content {
        background-color: #ffffff;
        padding: 0 20px 15px 20px;
        /* margin-top: 15px; */
    }

    .contentMainScore .row_2_content_2 {
        background-color: #ffffff;
        padding: 0 20px 15px 20px;
        /* margin-top: 15px; */
    }

    .czLog {
        overflow: hidden;
    }

    .contentTwo_title_2 {
        height: 60px;
        line-height: 60px;
        background-color: #ffffff;
        padding: 0 25px;
    }

    .contentTwo_title_2 .contentTwo_title_p_2 {
        color: #262626;
        font-size: 16px;
        font-weight: 600;
        margin: 0;
    }

    /* 空格占位 */
    .blank {
        width: 100%;
        height: 100px;
    }

    .bottom_zb {
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    /* 授权弹窗 */
    .power_dia {
        font-size: 16px;
        color: #262626;
    }

    .approv_dia {
        font-size: 14px;
        margin-top: 8px;
    }

    .approv_dia_con1,
    .approv_dia_con2 {
        margin-top: 20px;
        color: #262626;
    }

    .ryzrApprove_dialog {
        overflow: hidden;
    }

    .dia_button {
        float: right;
        margin: 85px 0 15px 0;
    }

    .butt_cancel {
        margin-right: 10px;
    }

    /* 退回弹窗 */
    .retreatCon {
        display: flex;
        justify-content: space-around;
        /* flex-direction: row; */
    }

    .retreatCon_2 {
        width: 87%;
    }

    .dia_button_type2_zb {
        float: right;
        margin: 25px 0 15px 0;
    }

    .retreat_dia_titlte_zb {
        font-size: 16px;
        color: #262626;
        margin-top: 4px;
    }

    .retreat_dia_con_zb {
        margin-top: 20px;
    }

    /* 退回弹窗element警告图标 */
    .box_retreat_zb {
        color: rgba(251, 164, 47, 1);
        font-size: 180%;
    }
</style>

<style>
    /* 禁用的输入框 */
    .contentMainScore .el-select {
        display: block;
    }

    /* table表格修改 */

    .row_2_content .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .row_2_content .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    .row_2_content .el-table--medium th {
        padding: 0;
    }

    .row_2_content .el-table--medium td {
        padding: 0;
    }


    .row_2_content .el-table th {
        padding: 10px 0;
    }

    .row_2_content_2 .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    .row_2_content_2 .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    /* form表单样式修改 */
    .row_2_content .el-form-item {
        margin: 16px 0;
    }

    /* 计数器修改 */
    .row_2_interview_resultEntry .el-input-number--medium {
        width: 130px;
    }
</style>
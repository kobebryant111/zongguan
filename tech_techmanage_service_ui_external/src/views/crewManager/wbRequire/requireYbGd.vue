<!-- 人力需求-已办和归档 -->
<template>
    <div class="rlxqContent rlxqContentApprove">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="5">
                                <p class="nav_name">人力外包需求审批</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <!-- 人力需求 -->
        <div class="contentMain">
            <div class="contentTwo_title">
                <p class="contentTwo_title_p">需求方信息</p>
            </div>
            <div class="content">
                <!-- 表第一行 -->
                <div class="row_1">
                    <el-row :gutter="80">
                        <el-col :span="8">
                            <div>
                                <p>需求发起人</p>
                                <el-select v-model="peoRequireDetail.username" disabled>
                                    <el-option :value="peoRequireDetail.username">
                                    </el-option>
                                </el-select>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>部门</p>
                                <el-select v-model="peoRequireDetail.dept" disabled>
                                    <el-option :value="peoRequireDetail.dept">
                                    </el-option>
                                </el-select>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>提至供应商时间 *</p>
                                <el-date-picker v-model="peoRequireDetail.supplytime" type="date" placeholder="请选择日期"
                                    value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
                            </div>
                        </el-col>
                    </el-row>
                </div>


                <!-- 需求信息 -->
                <div class="row_require">
                    <el-row>
                        <el-col :span="24">
                            <div class="contentTwo_title">
                                <p class="row_2_detail">需求详情</p>
                            </div>
                            <div class="row_table">
                                <el-table :data="peoRequireDetail.infos" style="width: 100%">
                                    <el-table-column type="index" label="序号" width="50">

                                    </el-table-column>
                                    <el-table-column prop="usertype" label="人员类型" width="140">
                                        <template slot-scope="scope">
                                            <el-select v-model="scope.row.usertype" placeholder="请选择" :disabled="true">
                                                <el-option v-for="item in peoForm_options" :key="item.dictid" :label="item.dictname"
                                                    :value="item.dictid"></el-option>
                                            </el-select>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="usernum" label="需求人数" width="120">
                                        <template slot-scope="scope">
                                            <el-input-number v-model="scope.row.usernum" size="medium"
                                                controls-position="right" :min="1" :max="50000" :disabled="true"></el-input-number>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="isadvertise" label="联系招聘" width="110" :render-header="renderHeader">
                                        <template slot-scope="scope">
                                            <el-select v-model="scope.row.isadvertise" placeholder="请选择" :disabled="true">
                                                <el-option v-for="item in connect_options" :key="item.dictid" :label="item.dictname"
                                                    :value="item.dictid"></el-option>
                                            </el-select>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="addreason" label="增加原因" width="230" :show-overflow-tooltip="true">
                                        <template slot-scope="scope">
                                            <!-- <el-input v-model="scope.row.addreason" placeholder="请输入" :disabled="true"></el-input> -->
                                            <div class="table_supply_w">
                                                <div class="table_supply">
                                                    {{scope.row.addreason}}
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="personrequire" label="人员要求" width="230"
                                        :show-overflow-tooltip="true">
                                        <template slot-scope="scope">
                                            <!-- <el-input v-model="scope.row.personrequire" placeholder="请输入" :disabled="true"></el-input> -->
                                            <div class="table_supply_w">
                                                <div class="table_supply">
                                                    {{scope.row.personrequire}}
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="supplynames" label="推荐外包商" width="343"
                                        :show-overflow-tooltip="true">
                                        <template slot-scope="scope">
                                            <!-- <div>
                                                <el-input v-model="scope.row.supplynames" suffix-icon="el-icon-more"
                                                    placeholder="请选择推荐外包商" :disabled="true"></el-input>
                                            </div> -->
                                            <div class="table_supply_w">
                                                <div class="table_supply">
                                                    {{scope.row.supplynames}}
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="operation" label="">
                                        <template slot-scope="scope">

                                        </template>
                                    </el-table-column>

                                </el-table>
                            </div>
                        </el-col>
                    </el-row>
                </div>

                <!-- 操作日志 -->
                <div class="czLog">
                    <flow-logs-gd ref="log"></flow-logs-gd>
                </div>
            </div>
        </div>

        <!-- 空格占位 -->
        <div class="blank"></div>

        <div class="nav-bottom" v-show="flowItem.status == 'yb'">
            <yb-revoke :flowItem="flowItem"></yb-revoke>
        </div>
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import { getWbCompany, queryEmployeeTree, queryResourceDept, queryWorkItem } from "@/api/integrated/wbryzrlc";
    import { liuzhuan, save, delRequireStart, queryDetail, twoManagerAudit, confirm } from "@/api/integrated/xqgl";
    import { getDicEntry } from "@/api/eosDictEntry";
    import common from "@/api/common";
    import helpHint from '@/views/crewManager/common/helpHint' //引入自定义联系招聘表头
    import { doDel, getLogDetail, getAuthList, doAuth, drawback } from '@/api/common/flowApi'
    import $ from "jquery"; 
    import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
    import ybRevoke from "@/views/plugins/camunda/ybRevoke";
    export default {
        name: 'RequireApprove',
        components: { buttonCommon, helpHint, ybRevoke, FlowLogsGd },
        data() {
            return {
                buttonClose: '关 闭', //关闭按钮名字
                size: 'mini',  //按钮大小
                peoForm_options: [],//人员类型形式
                connect_options: [], //联系招聘下拉内容
                successMessage: '', // 成功消息提示
                errorMessage: '', //失败消息提示
                instanceid: '', //流程ID
                workItemID: '', //工作项id
                processDefName: '', //流程定义名称
                processInstName: '',  //流程实例名称
                activityInstId: '', //当前活动实例ID
                peoRequireDetail: {}, //需求详情
                id: '',
                activityDefID: '', //当前节点ID
                requireStatus: '', //当前节点状态
                flowItem: {},
            };
        },
        methods: {
            // 初始化
            init(item) { 
                this.flowItem = item;
                if (item.status == "gd") {
                    this.instanceid = item.instanceid; 
                    this.workItemID = item.projectNo;
                    this.$refs.log.init(item);
                } else {
                    this.instanceid = item.processInstanceId; 
                    this.workItemID = item.projectNo;
                    this.$refs.log.init(item);
                }
                this.processDefName = item.processDefineName;
                this.processInstName = item.processDefineName;
                this.activityInstID = item.activityId;
                this.activityDefID = item.activityId;
                this.requireStatus = item.status;
                this.$nextTick(_ => {
                    this.getDic();
                    this.getRequireDetail();
                });

            },

            // 查询业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "REQUIRE_PEOPLE_TYPE,WHETHER_CONTACT_RECRUITMENT"
                }) .then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //人员类型
                    this.peoForm_options = data.filter(item => {
                        return item.dicttypeid == "REQUIRE_PEOPLE_TYPE";
                    });
                    //联系招聘
                    this.connect_options = data.filter(item => {
                        return item.dicttypeid == "WHETHER_CONTACT_RECRUITMENT";
                    });
                    return data;
                });
            },
            // 获取需求审批详细信息
            getRequireDetail() {
                let params = {};
                params.instanceid = this.instanceid;
                queryDetail(params)
                    .then(res => {
                        this.peoRequireDetail = res.data.results;
                        this.id = res.data.results.id;
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
            },

            // 联系招聘右边图标
            renderHeader() {
                return this.$createElement('helpHint', {
                    props: {
                        content: '是否需要综合部联系招聘',
                        name: '联系招聘'
                    }
                })
            },

            // 验证必填项
            proving() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        this.commit();
                    } else {
                        return false;
                    }
                })
            },


            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },

            // 成功消息提示
            saveSuccess() {
                this.$message({
                    message: this.successMessage,
                    type: 'success'
                })
            },

            // 失败消息提示
            saveError() {
                this.$message.error(this.errorMessage);
            },


        }
    };
</script>
<style scoped>
    .rlxqContent {
        overflow: hidden;
        padding-bottom: 85px;
    }

    .contentMain {
        width: 100%;
        /* height: 100%; */
        font-size: 16px;
        padding: 10px 20px;
        margin-bottom: 65px;
    }


    .nav {
        width: 100%;
        /* height: 30px; */
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 200px;
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
        margin: 12px 0;
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

    .contentTwo_title_p {
        color: #262626;
        font-weight: 600;
    }

    .contentMain .row_1 {
        background-color: #ffffff;
        padding: 0 20px 15px 20px;
        /* padding: 0 20px; */
    }

    .contentMain .row_require {
        background-color: #ffffff;
        /* padding: 0 20px 15px 20px; */
        margin-top: 15px;
    }

    .row_table {
        padding: 12px 20px 15px 20px;
    }

    .bottom {
        position: absolute;
        bottom: 0;
        padding: 15px 0;
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

    /* 供应商 */
    .table_supply_w {
        width: 100%;
        height: 36px;
        line-height: 36px;
        font-size: inherit;
        border: 1px solid #E4E7ED;
        background-color: #F5F7FA;
        border-radius: 4px;
        padding: 0 15px;
        margin: 0;
        cursor: pointer;
    }

    .table_supply {
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }

    /* 空格占位 */
    .blank {
        width: 100%;
        height: 100px;
    }

    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }
</style>

<style>
    /* table表格修改 */

    .contentMain .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .contentMain .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }


    /* form表单样式修改 */
    .contentMain .el-form-item {
        margin: 16px 0;
    }

    /* 计数器 */
    .contentMain .el-input-number--medium {
        width: 100px;
    }

    /* 级联框修改 */
    .contentMain .el-cascader {
        display: block;
    }

    /* el-input获得焦点样式修改 */
    .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    /* 禁用的输入框 */
    .contentMain .el-select {
        display: block;
    }


    /* 下拉框获得焦点样式修改 */
    .el-select .el-input.is-focus .el-input__inner {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-select .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    /* 级联选择器 */

    .el-cascader {
        display: block;
    }

    .el-cascader .el-input .el-input__inner:focus,
    .el-cascader .el-input.is-focus .el-input__inner {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-cascader-node.in-active-path,
    .el-cascader-node.is-active,
    .el-cascader-node.is-selectable.in-checked-path {
        color: rgba(251, 164, 47, 1);
    }

    .el-cascader__suggestion-item.is-checked {
        color: rgba(251, 164, 47, 1);
    }

    /* 弹窗 */
    .rlxqContentApprove .el-dialog {
        overflow: hidden;
    }

    .rlxqContentApprove .el-dialog__body {
        padding: 0 30px;
    }

    /* 弹窗关闭按钮 */
    .approveCon .el-dialog__headerbtn:focus .el-dialog__close,
    .el-dialog__headerbtn:hover .el-dialog__close {
        color: rgba(251, 164, 47, 1);
    }

    /* 退回弹窗 */
    .retreatDialog .el-dialog {
        width: 30%;
    }
</style>
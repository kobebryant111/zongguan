<!-- 外包面试流程--已办 -->
<template>
    <div class="container container_zb">
        <!-- 头部 -->
        <!-- bin -->
        <div class="nav">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="1.5">
                            <p class="nav_name">发起面试</p>
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

        <!-- bin改 -->
        <!-- 发起面试 -->
        <div class="contentMain contentMain_Add">
            <div class="contentTwo_title">
                <p class="contentTwo_title_p">面试信息</p>
            </div>
            <div class="content">
                <el-form :model="interviewerInfo" ref="interviewerInfo">
                    <div class="row_1">
                        <div>
                            <el-row type="flex" justify="space-between">
                                <el-col :span="7">
                                    <div>
                                        <p>部门</p>
                                        <el-form-item>
                                            <el-select v-model="interviewerInfo.msgdeptno" :disabled="true">
                                                <el-option v-for="item in deptOpts" :key="item.id" :label="item.text"
                                                    :value="item.id">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>人力需求</p>
                                        <el-form-item>
                                            <el-select v-model="rlIds" placeholder="请选择人力需求" :disabled="true">
                                                <el-option :value="rlIds"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>面试官</p>
                                        <el-form-item>
                                            <el-select v-model="msgNames" placeholder="请选择面试官" disabled>
                                                <el-option :value="msgNames"></el-option>
                                            </el-select>
                                        </el-form-item>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>

                    <!-- 表格 -->
                    <div class="row_2 row_2_add">
                        <el-row>
                            <el-col :span="24">
                                <div class="tableCon">
                                    <p class="row_2_detail">应聘者信息</p>
                                    <el-table :data="interviewerInfo.eeList" style="width: 100%">
                                        <el-table-column prop="msname" label="姓名" width="120" :show-overflow-tooltip="true">
                                            <template slot-scope="scope">
                                                <el-form-item>
                                                    <el-input v-model="scope.row.msname" placeholder="请输入" :disabled="true" />
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="sex" label="性别" width="110">
                                            <template slot-scope="scope">
                                                <el-form-item>
                                                    <el-select v-model="scope.row.sex" placeholder="请选择" :disabled="true">
                                                        <el-option v-for="item in sexOpts" :key="item.dictid" :label="item.dictname"
                                                            :value="item.dictid"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="sex" label="出生日期" width="160">
                                            <template slot-scope="scope">
                                                <el-form-item>
                                                    <el-date-picker v-model="scope.row.birthday" type="date"
                                                        placeholder="请选择日期" value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="wbsid" label="外包商" width="260" :show-overflow-tooltip="true">
                                            <template slot-scope="scope">
                                                <div class="table_supply_w">
                                                    <div class="table_supply">
                                                        {{scope.row.outsourcName}}
                                                    </div>
                                                </div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="position" label="人员类型" width="155">
                                            <template slot-scope="scope">
                                                <el-form-item>
                                                    <el-select v-model="scope.row.position" placeholder="请选择" :disabled="true">
                                                        <el-option v-for="item in peoForm_options" :key="item.dictid"
                                                            :label="item.dictname" :value="item.dictid"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="positionName" label="人员属性" width="80">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.researchers == 1">人力外包</span>
                                                <span v-else-if="scope.row.researchers == 2">项目人力</span>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="workyears" label="工作年限" width="120">
                                            <template slot-scope="scope">
                                                <el-form-item>
                                                    <el-input-number v-model="scope.row.workyears" size="medium"
                                                        controls-position="right" :min="0" :max="50" :step="0.5"
                                                        :disabled="true"></el-input-number>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="suggestlevel" label="建议等级" width="100">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.suggestlevel == 1">1 (1-3年)</span>
                                                <span v-else-if="scope.row.suggestlevel == 2">2 (3-5年)</span>
                                                <span v-else-if="scope.row.suggestlevel == 3">3 (5-7年)</span>
                                                <span v-else-if="scope.row.suggestlevel == 4">4 (7年以上)</span>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="jl" label="简历" width="150">
                                            <template slot-scope="scope">
                                                <el-form-item ref="headfileUpload">
                                                    <div class="upload_scss">
                                                        <div @click="openNtkoWindow(scope.row.jl)">预览</div>
                                                        <div @click="jlDownliad(scope.row.jl)">下载</div>
                                                    </div>

                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="msDate" label="面试日期" width="160">
                                            <template slot-scope="scope">
                                                <el-form-item>
                                                    <el-date-picker v-model="scope.row.msDate" type="date" placeholder="请选择日期"
                                                        value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="">

                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-form>

                <!-- 操作日志 -->
                <div class="czLog">
                    <flow-logs :processInstId="instanceid" ref="log"></flow-logs>
                </div>
            </div>
        </div>

        <div class="blank"></div>

        <div class="bottom_zb">
            <yb-revoke :flowItem="flowItem"></yb-revoke>
        </div>

    </div>
</template>
<script>
    import { getDicEntry } from "@/api/eosDictEntry";
    import common from '@/api/common'
    import { getInterviewScore, download, rlxqQueryDetail } from '@/api/integrated/wbms'
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import { getDeptTwo } from "@/api/systemApi/department";
    import helpHint from '@/views/crewManager/common/helpHint'; //引入自定义联系招聘表头
    import { getListAll } from '@/api/common/workflowApi';
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    import ybRevoke from "@/views/plugins/camunda/ybRevoke";
    import Moment from 'moment'
    let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
    export default {
        name: 'interviewYb',
        components: { buttonCommon, helpHint, ybRevoke, flowLogs },
        data() {
            var self = this;
            return {
                // 面试官表单
                interviewerInfo: {
                    msgdeptno: "", // 部门编号
                    msgdeptname: "", // 部门名称
                    rlId: '', //选择人力需求
                    msgname: '', // 面试官名称
                    msgid: '', // 面试官编号
                    eeList: [{
                        msname: '',
                        sex: '',
                        birthday: '',
                        wbsid: '', //外包商
                        position: '', //人员类型
                        workyears: '', //工作年限
                        suggestlevel: '1',
                        jl: '', //简历
                        msDate: '' //面试日期
                    }]
                },
                rlIds: '', //人力需求绑定值
                msgids: [], //面试官绑定值
                msgNames: '',    // 面试官名称
                peoForm_options: [ //人员类型形式
                    {
                        dictid: '',
                        dictname: ''
                    }
                ],
                outsourceno_options: [{ //外包商
                    id: '',
                    outsourcname: ''
                }],
                eeList: [], // 面试者表单
                appData: [{ index: 0 }], //面试者数据
                sexOpts: [], // 性别集合
                deptOpts: [], // 部门数据
                objectcode: "6000000300",
                objectgrade: "2",
                buttonClose: '关 闭', //取消按钮名字
                size: 'mini',  //按钮大小
                navName: '', //导航菜单名字
                errorMessage: '', //失败提示
                id: '', //存草稿主表Id
                instanceid: '', //流程Id
                workItemID: '', //工作项id
                processDefName: '', //流程定义名称
                flowItem: {}, //bps数据
            };
        },

        mounted() {
            this.$nextTick(() => {
                this.getDept();
                this.getAllWbs();
                this.getInterviewDetail();
            });
        },

        methods: {
            init(params) {
                let self = this;
                self.flowItem = params; 
                self.instanceid = params.processInstanceId;
                this.$refs.log.init(self.instanceid);
                // 查询业务字典
                getDicEntry({ dicttypeid: "BUSI_OUTSOURCE_GENFER,RLWBXQLX" }).then(res => {
                    if (res.data.status !== 200) {
                        this.alter(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    // 性别
                    this.sexOpts = data.filter(item => {
                        return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
                    });

                    //人员类型
                    this.peoForm_options = data.filter(item => {
                        return item.dicttypeid == "RLWBXQLX" && item.dictid != "CZ";
                    });
                    return data;
                });


            },

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

            // 获取部门
            getDept() {
                const self = this;
                // 2级部门
                getDeptTwo({
                    objectcode: this.objectcode,
                    objectgrade: this.objectgrade
                }).then(res => {
                    if (res.data.status !== 200) {
                        this.alter(res.data.message, { type: "error" });
                        return false;
                    }
                    // 进行过滤
                    let data = res.data.results;
                    let name = data[0].text;
                    self.deptOpts = data[0].children.filter(tt => {
                        tt.text = tt.text.replace(name + "-", "");
                        return tt;
                    });
                }); // end
            },

            // 查询所有外包商
            getAllWbs() {
                getListAll()
                    .then(res => {
                        this.outsourceno_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 文档下载
            jlDownliad(docId) {
                const self = this;
                common.downloadJl(docId, self);
            },

            // 获取保存的需求详细信息
            getInterviewDetail() {
                let params = {};
                params.instanceid = this.instanceid;
                getInterviewScore(params)
                    .then(res => {
                        var detailData = res.data.results;
                        if (res.data.status == 200) {
                            this.interviewerInfo.msgdeptno = "dep" + detailData.erList[0].msgdeptno;
                            detailData.eeList = detailData.eeList.map(li => {
                                if (li.wbsid != undefined) {
                                    li.wbsid = Number(li.wbsid);
                                    return li;
                                } else {
                                    return li;
                                }
                            })
                            this.interviewerInfo.msgid = detailData.erList[0].msgid;
                            this.interviewerInfo.msgname = detailData.erList[0].msgname;
                            // let msgids = detailData.erList[0].msgid.split(",");
                            // msgids = msgids.map(item => {
                            //     return "emp" + item
                            // })
                            let msgName = [];
                            // 获取面试官id和名称
                            detailData.erList.forEach(ii => {
                                msgName.push(ii.msgname);
                            });
                            this.msgNames = msgName.join(",");
                            this.getRlxq(detailData.eeList[0].rlId);
                            this.interviewerInfo.eeList = detailData.eeList;
                            this.id = detailData.erList[0].id;
                            this.instanceid = detailData.erList[0].instanceid;
                            // this.$nextTick(() => {
                            //     this.getWorkItem();
                            // })
                        } else {
                            this.errorMessage = "获取数据失败";
                            this.messageError();
                        }
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
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

            // 关闭按钮
            close() {
                // this.$router.back();
                this.$router.push({ name: 'dashboard' });
            },

            // 失败消息提示
            messageError() {
                this.$message.error(this.errorMessage);
            },

            // 自定义简历右边图标
            renderHeader() {
                return this.$createElement('helpHint', {
                    props: {
                        content: '导入简历可以填充前面的字段',
                        name: '简历 *'
                    }
                })
            },
        }
    };
</script>
<style scoped>
    .head-container {
        width: 100%;
        background: #fff;
        padding: 5px;
        height: 70px;
        line-height: 70px;
    }

    .head-container div {
        float: left;
    }

    .head-container div:last-child {
        float: right;
    }

    /* bin修改 */

    .contentMain {
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

    .content .row_1 {
        background-color: #ffffff;
        /* padding: 0 20px 15px 20px; */
        padding: 0 20px;

    }

    .contentMain .row_2 {
        background-color: #ffffff;
        padding: 0 20px 15px 20px;
        margin-top: 15px;
    }

    .add_row {
        color: #faad14;
        font-weight: 500;
        margin-left: 20px;
        cursor: pointer;
    }

    /* 空格占位 */
    .blank {
        width: 100%;
        height: 160px;
    }

    .upload_scss div {
        font-size: 14px;
        color: rgba(251, 164, 47, 1);
        display: inline-block;
        cursor: pointer;
    }

    /* 重传 */
    .againUpLoad {
        font-size: 14px;
        color: rgba(251, 164, 47, 1);
        display: inline-block;
    }

    .wrapper_right_bottom_1_zb {
        margin-right: 10px;
    }

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
</style>

<style>
    /* 级联选择器 */
    .contentMain_Add .el-cascader {
        display: block;
    }

    /* 下拉框 */
    .contentMain_Add .el-select {
        display: block;
    }

    /* 表格删除按钮 */

    .contentMain_Add .el-icon-delete {
        width: 11px;
        height: 11px;
        color: #f56c6c;
    }

    .operation_add .el-button {
        border: 1px solid #ffffff;
        padding: 0;
    }

    /* table表格修改 */

    .row_2_add .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .row_2_add .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    .row_2_add .el-table--medium th {
        padding: 0;
    }

    .row_2_add .el-table--medium td {
        padding: 0;
    }


    .row_2_add .el-table th {
        padding: 10px 0;
    }

    /* form表单样式修改 */
    .contentMain_Add .el-form-item {
        margin: 16px 0;
    }

    /* 计数器 */
    .contentMain_Add .el-input-number--medium {
        width: 100px;
    }

    /* 上传文件按钮 */

    .tableCon .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }

    /* 重传 */
    .againUpLoad .el-upload--picture-card:hover {
        color: rgba(251, 164, 47, 1);
    }

    .againUpLoad .el-upload:focus {
        color: rgba(251, 164, 47, 1);
    }
</style>
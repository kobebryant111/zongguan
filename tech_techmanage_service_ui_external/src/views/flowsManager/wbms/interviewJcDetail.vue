<!-- 面试详情 -->
<template>
    <div class="rlxqContent rlxqContentApprove">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="5">
                                <p class="nav_name">面试详情</p>
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
        <div class="contentMain_interview">
            <div class="contentTwo_title">
                <p class="contentTwo_title_p">基本信息</p>
            </div>
            <div class="content">
                <!-- 表第一行 -->
                <div class="row_con">
                    <el-row :gutter="80">
                        <el-col :span="8">
                            <div class="name">
                                <p>部门</p>
                                <el-select v-model="depname" disabled>
                                    <el-option :value="depname">
                                    </el-option>
                                </el-select>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>人力需求</p>
                                <el-select v-model="rlIds" :disabled="true">
                                    <el-option :value="rlIds"></el-option>
                                </el-select>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>面试日期</p>
                                <el-date-picker v-model="ypzData[0].msDate" type="date" placeholder="选择日期" format="yyyy-MM-dd"
                                    :disabled="true"></el-date-picker>
                            </div>
                        </el-col>
                    </el-row>
                </div>

                <!--应聘者信息 -->
                <div class="row_require">
                    <el-row>
                        <el-col :span="24">
                            <div class="contentTwo_title">
                                <p class="row_require_detail">应聘者信息</p>
                            </div>
                            <div class="row_table">
                                <el-table :data="ypzData">
                                    <el-table-column prop="msname" label="姓名" width="80" :show-overflow-tooltip="true"></el-table-column>
                                    <el-table-column label="性别" width="50">
                                        <template slot-scope="scope">
                                            <span v-for="(item, index) of sexData" :key="index">
                                                <span v-if="item.dictid == scope.row.sex">{{
                                                    item.dictname}}</span>
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="birthday" label="出生日期" width="100"></el-table-column>
                                    <el-table-column prop="outsourcname" label="外包商" :show-overflow-tooltip="true"
                                        width="220"></el-table-column>
                                    <el-table-column label="人员类型" width="100">
                                        <template slot-scope="scope">
                                            <span v-for="(item, index) of rylxData" :key="index">
                                                <span v-if="item.dictid == scope.row.position">{{
                                                    item.dictname}}</span>
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="positionName" label="人员属性" width="80">
                                        <template slot-scope="scope">
                                            <span v-if="scope.row.researchers == 1">人力外包</span>
                                            <span v-else-if="scope.row.researchers == 2">项目人力</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="workyears" label="年限" width="50"></el-table-column>
                                    <el-table-column prop="suggestlevel" label="等级" width="100">
                                        <template slot-scope="scope">
                                            <span v-if="scope.row.suggestlevel == 1">1 (1-3年)</span>
                                            <span v-else-if="scope.row.suggestlevel == 2">2 (3-5年)</span>
                                            <span v-else-if="scope.row.suggestlevel == 3">3 (5-7年)</span>
                                            <span v-else-if="scope.row.suggestlevel == 4">4 (7年以上)</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="简历" width="100">
                                        <template slot-scope="scope">
                                            <a href="javascript:void(0);" @click="openNtkoWindow(scope.row.jl)" style="color: rgba(251, 164, 47, 1)">预览</a>
                                            <a href="javascript:void(0);" @click="jlDownliad(scope.row.jl)" style="color: rgba(251, 164, 47, 1)">下载</a>
                                        </template>
                                    </el-table-column> 
                                    <el-table-column prop="score" label="成绩" width="60"></el-table-column>
                                    <el-table-column prop="interviewresult" label="结果" width="80">
                                        <template slot-scope="scope">
                                            <span v-for="(item, index) of scoreData" :key="index">
                                                <span v-if="item.dictid == scope.row.interviewresult">{{
                                                    item.dictname}}</span>
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="suggestprice" label="建议单价" width="100"
                                        :show-overflow-tooltip="true"></el-table-column>
                                    <el-table-column></el-table-column>
                                </el-table>
                            </div>
                        </el-col>
                    </el-row>
                </div>

                <!--面试成绩 -->
                <div class="row_require">
                    <el-row>
                        <el-col :span="24">
                            <div class="contentTwo_title">
                                <p class="row_require_detail">面试成绩</p>
                            </div>
                            <div class="row_table">
                                <el-table :data="scoreList" style="width: 100%">
                                    <el-table-column prop="msgname" label="面试官" width="140"></el-table-column>
                                    <el-table-column prop="msgDepname" label="部门" width="180" :show-overflow-tooltip="true"></el-table-column>
                                    <el-table-column prop="technology" label="技术" width="105"></el-table-column>
                                    <el-table-column prop="comprehensive" label="综合" width="105"></el-table-column>
                                    <el-table-column prop="experience" label="项目经验" width="177">
                                     <template slot-scope="scope">
                                        <el-select disabled v-model="scope.row.experience" placeholder="项目经验">
                                            <el-option v-for="item in experienceData" :label="item.dictname" :key="item.dictid" :value="item.dictid"></el-option>
                                        </el-select>
                                    </template>
                                    </el-table-column>
                                    <el-table-column prop="education" label="学历" width="177">
                                     <template slot-scope="scope">
                                        <el-select disabled v-model="scope.row.education" placeholder="学历">
                                            <el-option v-for="item in educationData" :label="item.dictname" :key="item.dictid" :value="item.dictid"></el-option>
                                        </el-select>
                                    </template>
                                    </el-table-column>
                                    <el-table-column prop="remark" label="评语" :show-overflow-tooltip="true"></el-table-column>
                                    <el-table-column label=""></el-table-column>
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
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件
    import { getInterviewScore, rlxqQueryDetail, download } from '@/api/integrated/wbms';
    import { getEmployUser,queryWorkItem } from '@/api/common/workflowApi';
    import { getDicEntry } from "@/api/eosDictEntry";
    import common from "@/api/common";
    import Moment from 'moment';
    import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd"; 
    let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
    export default {
        name: 'msDetail',
        components: { buttonCommon, FlowLogsGd },
        data() {
            return {
                ypzData: [],
                educationData: [], // 业务字段-学历
                experienceData: [], // 业务字段-项目经验
                buttonClose: '关 闭', //关闭按钮名字
                size: 'mini',  //按钮大小
                instanceid: '', //流程ID
                depname: '', //部门名称
                rlIds: '', //人力需求绑定值
                scoreData: [], // 面试结果数据集合
                sexData: [], // 性别数据集合
                rylxData: [], // 人员类型数据集合
                scoreList: [], //面试打分数据
                initData: null,
            };
        },
        created() {
            // 如果本地没数据
            if(!JSON.parse(window.localStorage.getItem('ypzData'))) {
                window.localStorage.setItem('ypzData', JSON.stringify(this.$route.params.ypzData));
            } else {
                this.initData = JSON.parse(window.localStorage.getItem('ypzData'));
            }
            this.ypzData[0] = this.$route.params.ypzData || this.initData;
            this.$nextTick(() => {
                this.getWorkItem(this.ypzData[0].instanceid); 
                this.getRlxq(this.ypzData[0].rlId);
                this.getDic();
                this.getInterviewDetail();
            })
        },
        mounted() {

        },
        watch: {

        },
        methods: {
            // 查询业务字典
            getDic() {
                 // 获取字典
                getDicEntry({ dicttypeid: "NEW_BUSI_OUTSOURCE_INTERVRESULT,BUSI_OUTSOURCE_GENFER,RLWBXQLX,INTERVIEW_MARKING,MSDF_EDUCATION,MSXM_LEVEL"}).then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        // 面试结果
                        this.scoreData = data.filter(item => {
                            return item.dicttypeid == "NEW_BUSI_OUTSOURCE_INTERVRESULT";
                        });
                        // 性别
                        this.sexData = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
                        });
                        //人员类型
                        this.rylxData = data.filter(item => {
                            return item.dicttypeid == "RLWBXQLX";
                        });
                         this.educationData = data.filter(item => {
                            return item.dicttypeid == "MSDF_EDUCATION";
                        });
                        this.experienceData = data.filter(item => {
                            return item.dicttypeid == "MSXM_LEVEL";
                        });
                        return data;
                    });
            },

            //获取待办工作项详情-单条
            getWorkItem(val) {
                const loading = common.loading(this)
                let params = {};
                params.instanceId = val;
                queryWorkItem(params).then(res => {
                    loading.close();
                    if (res.data.results != undefined) {
                        let item = {};
                        if (res.data.results.isFinished == "Y") {
                            item.status = 'gd';
                            item.instanceid = val;
                        } else {
                            item.processInstanceId = val;
                            item.status = 'yb';
                        }
                        this.$nextTick(() => { 
                            this.$refs.log.init(item);
                        })
                    } 
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 获取面试详细信息
            getInterviewDetail() {
                let params = {};
                params.instanceid = this.ypzData[0].instanceid;
                getInterviewScore(params)
                    .then(res => {
                        var detailData = res.data.results;
                        if (res.data.status == 200) {
                            detailData.eeList = detailData.eeList.filter(li => li.idcard == this.ypzData[0].idcard)
                            this.ypzData[0].msDate = Moment(detailData.eeList[0].msDate).format('YYYY-MM-DD');
                            this.ypzData[0].mspy = detailData.eeList[0].mspy;
                            this.scoreList = detailData.eeList[0].scoreList.filter(li => li.idcard ==  this.ypzData[0].idcard);
                            this.scoreList.forEach((item, index) => {
                                this.userSearch(item.msgid, index);
                            })
                        } else {
                            this.$message.error('获取数据失败');
                        }

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
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
                            this.depname = rlIdData.depname.match(reg)[1]
                            this.rlIds = rlIdData.username + rlIdData.depname.match(reg)[1] + Moment(rlIdData.supplytime).format('YYYY-MM-DD') + rlIdData.usertypename;
                        } else {
                            this.$message.error('获取数据失败');
                        }
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 行员查询
            userSearch(id, index) {
                getEmployUser(id).then(res => {
                    if (res.data.status == 200) {
                        let data = res.data.results[0];
                        this.scoreList[index].msgDepname = data.chargeName.split("-")[1];
                        this.scoreList.splice(0, 0);
                    } else {
                        this.$message.error('获取数据失败');
                    }

                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 预览
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

            // 文档下载
            jlDownliad(docId) {
                const self = this;
                common.downloadJl(docId, self);
            },



            // 关闭按钮
            close() {
                window.localStorage.clear('ypzData')
                // this.$router.back();
                this.$router.push({ name: 'wbmsManager' });
            },

            // 成功消息提示
            saveSuccess(successMessage) {
                this.$message({
                    showClose: true,
                    message: successMessage,
                    type: 'success'
                })
            },


        }
    };
</script>
<style scoped>
    .rlxqContent {
        overflow: hidden;
    }

    .contentMain_interview {
        width: 100%;
        font-size: 16px;
        padding: 10px 20px;
        margin-bottom: 65px;
    }

    .nav {
        width: 100%;
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

    .content {
        width: 100%;
        font-size: 16px;
    }

    .content p {
        color: #262626;
        font-size: 14px;
        margin: 12px 0;
    }



    .content .row_require_detail {
        font-size: 16px;
        font-weight: 600;
    }

    .content .add_row {
        font-size: 14px;
    }

    .contentTwo_title,
    .contentTwo_bottom {
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

    .contentMain_interview .row_con,
    .row_con_down {
        background-color: #ffffff;
        padding: 0 20px 15px 20px;
    }

    .contentMain_interview .row_require {
        background-color: #ffffff;
        margin: 15px 0;
    }

    .row_table {
        padding: 12px 20px 15px 20px;
    }

    .contentTwo_bottom>.contentTwo_title_p {
        font-size: 16px;
    }

    .bottom {
        position: absolute;
        bottom: 0;
        padding: 15px 0;
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
</style>

<style>
    /* table表格修改 */
    .contentMain_interview .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .contentMain_interview .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }


    /* form表单样式修改 */
    .contentMain_interview .el-form-item {
        margin: 16px 0;
    }

    /* 计数器 */
    .contentMain_interview .el-input-number--medium {
        width: 100%;
    }

    /* 级联框修改 */
    .contentMain_interview .el-cascader {
        display: block;
    }

    /* el-input获得焦点样式修改 */
    .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    /* 禁用的输入框 */
    .contentMain_interview .el-select {
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
</style>
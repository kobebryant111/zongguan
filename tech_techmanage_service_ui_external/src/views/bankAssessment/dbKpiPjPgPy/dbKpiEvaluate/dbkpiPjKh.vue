<!-- kpi评价-待办 -->
<template>
    <div class="kpisp-container">
        <div class="kpisp-body">
            <div class="kpisp-body-operate">
                <div class="kpisp-body-operate-left">
                    <i class="el-icon-view" @click="showHi">
                        <span class="xkg-marginleft-10px">{{ifShow ? '显示所有内容' : '隐藏所有内容'}}</span>
                    </i>
                </div>
            </div>
            <div class="kpisp-body-content" :class="{ifWarp: !ifShow}">
                <el-table :data="khData" :row-class-name="tableRowClassName">
                    <el-table-column label="序号" type="index" min-width="5%"></el-table-column>
                    <el-table-column label="指标项" prop="itemdescribe" min-width="10%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="工作内容" prop="plandescribe" min-width="15%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="工作目标" prop="workremark" min-width="15%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="计划完成时间" prop="finishdate" min-width="15%"></el-table-column>
                    <el-table-column label="分值" prop="value" min-width="5%"></el-table-column>
                    <el-table-column label="自评" min-width="10%" :show-overflow-tooltip="ifShow">
                        <template slot-scope="scope">
                            <template v-if="lcData.itemstatus == 3">{{scope.row.halfyearevalself}}</template>
                            <template v-else>{{scope.row.evalself}}</template>
                        </template>
                    </el-table-column>
                    <el-table-column label="初评分" prop="firstevalscore" min-width="5%"></el-table-column>
                    <el-table-column label="终评分" prop="secondevalscore" min-width="5%"></el-table-column>
                    <el-table-column label="状态" prop="itemstatusname" min-width="10%"></el-table-column>
                    <el-table-column label="操作" min-width="10%">
                        <template slot-scope="scope">
                            <div>
                                <!-- <button-oranges v-if="(scope.row.itemstatus == 5 && userId == pjPeoData.firstcheck) || (scope.row.itemstatus == 6 && userId == pjPeoData.secondcheck)"
                                    buttonName="评 价" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="corrSingle"></button-oranges> -->
                                <button-oranges v-if="scope.row.itemstatus == judgeStatus" buttonName="评 价" class="xkg-marginleft-10px"
                                    size="mini" :parentData="scope.row" :fns="corrSingle"></button-oranges>
                                <button-commons v-else buttonName="查 看" class="xkg-marginleft-10px" size="mini"
                                    :parentData="scope.row" :fns="corrSingle"></button-commons>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 评价单人 -->
        <el-dialog title="KPI指标详情" :visible.sync="ifShowXgSingDialog">
            <div class="zpContent">
                <el-steps :active="active" align-center>
                    <el-step title="自评" :description="updateSingData.evalselftime"></el-step>
                    <el-step :title="updateSingData.firstevalname" :description="updateSingData.firstevaltime"></el-step>
                    <el-step :title="updateSingData.secondevalname" :description="updateSingData.secondevaltime"></el-step>
                    <el-step title="完成"></el-step>
                </el-steps>
                <div class="form-none-marginbottom">
                    <el-form label-position="left" label-width="110px">
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="指标项：">
                                    <div>{{updateSingData.itemdescribe}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="分值：">
                                    <div>{{updateSingData.value}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="工作内容：">
                                    <div class="tdWarp">{{updateSingData.plandescribe}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="工作目标：">
                                    <div class="tdWarp">{{updateSingData.workremark}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="计划完成时间：">
                                    <div>{{updateSingData.finishdate}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">

                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <div class="zpContent-zp">
                    <p class="zpContent-zp-title">自评</p>
                    <el-form label-position="left" label-width="110px">
                        <el-form-item label="完成情况描述：">
                            <div v-if="lcData.itemstatus == 3" key="1" class="tdWarp">{{updateSingData.halfyearevalself}}</div>
                            <div v-else key="2" class="tdWarp">{{updateSingData.evalself}}</div>
                        </el-form-item>
                    </el-form>
                </div>
                <div v-if="ygDetail.firstcheck != undefined">
                    <div v-if="updateSingData.itemstatus == 5 && updateSingData.itemstatus == judgeStatus" class="zpContent-zp">
                        <p class="zpContent-zp-title">初评</p>
                        <el-form label-position="left" label-width="110px">
                            <el-row :gutter="20">
                                <el-col :span="12">
                                    <el-form-item label="评价：">
                                        <el-select placeholder="评价" v-model="updateSingData.firstevalgoal" @change="cpChange">
                                            <el-option v-for="(item, index) in eval_options" :key="index" :label="item.dictname"
                                                :value="item.dictid"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="评分：">
                                        <div>{{updateSingData.firstevalscore}}</div>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </el-form>
                    </div>
                    <div v-else class="zpContent-zp">
                        <p class="zpContent-zp-title">初评</p>
                        <el-form label-position="left" label-width="110px">
                            <el-row :gutter="20">
                                <el-col :span="12">
                                    <el-form-item label="评价：">
                                        <div v-for="item in eval_options" :key="item.dictid" v-if="item.dictid == updateSingData.firstevalgoal">{{item.dictname}}</div>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="评分：">
                                        <div>{{updateSingData.firstevalscore}}</div>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </el-form>
                    </div>
                </div>
                <div v-if="updateSingData.itemstatus == 6 && updateSingData.itemstatus == judgeStatus" class="zpContent-zp">
                    <p class="zpContent-zp-title">终评</p>
                    <el-form label-position="left" label-width="110px">
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="评价：">
                                    <el-select placeholder="评价" v-model="updateSingData.secondevalgoal" @change="zpChange">
                                        <el-option v-for="(item, index) in eval_options" :key="index" :label="item.dictname"
                                            :value="item.dictid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="评分：">
                                    <div>{{updateSingData.secondevalscore}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <div v-if="updateSingData.itemstatus > 6" class="zpContent-zp">
                    <p class="zpContent-zp-title">终评</p>
                    <el-form label-position="left" label-width="110px">
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="评价：">
                                    <div v-for="item in eval_options" :key="item.dictid" v-if="item.dictid == updateSingData.secondevalgoal">{{item.dictname}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="评分：">
                                    <div>{{updateSingData.secondevalscore}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <div class="hmddialog-bottom" v-if="updateSingData.itemstatus == judgeStatus">
                    <el-button @click="cancelXgSing">取 消</el-button>
                    <el-button v-if="updateSingData.itemstatus == 5 || updateSingData.itemstatus == 6" @click="backSelfEval">退回自评</el-button>
                    <el-button v-if="updateSingData.itemstatus == 6 && ygDetail.firstcheck != undefined" @click="backFirstEval">退回初评</el-button>
                    <el-button type="primary" @click="sureXgSing">保 存</el-button>
                    <!-- <el-button type="primary" @click="commitSingleEval">提 交</el-button> -->
                </div>
                <div v-else class="hmddialog-bottoms">
                    <el-button @click="cancelXgSing">确定</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonCommons from '@/components/eleButton/buttonCommons';
    import { getPpmKpiPmDetailInfo } from "@/api/hykh/kpiZp";
    import { judgeStatus, updateDetail, submitEval, backEvalSelf, backFirstEval } from "@/api/hykh/kpiEval";
    import { getPpmKpiPmDetails } from "@/api/hykh/myDb";
    import common from "@/api/common";
    import Moment from 'moment';
    import { mapGetters } from 'vuex';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'dbkpiPjKh',
        components: { buttonOranges, buttonCommons },
        data() {
            return {
                ifShow: true,
                ifShowXgSingDialog: false, //修改单人弹窗
                updateSingData: {}, //修改单人数据
                khData: [], //列表数据
                lcData: {},
                active: -1,
                eval_options: [], //评价数据
                judgeStatus: null, //判断状态
                ygDetail: {}, //员工详情
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        mounted() {
            this.getDic();
        },
        methods: {
            // 设置表格行颜色
            tableRowClassName(row, rowIndex) {
                if (row.row.itemstatus == this.judgeStatus) {
                    if (row.row.itemstatus == 5 && row.row.firstevalgoal != undefined) {
                        return 'pink-row'
                    } else if (row.row.itemstatus == 6 && row.row.secondevalgoal != undefined) {
                        return 'pink-row'
                    }
                }
                return ''
            },

            // 显示or隐藏内容
            showHi() {
                this.ifShow = !this.ifShow
            },

            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_EVAL_SET" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //评价
                    this.eval_options = data.filter(item => {
                        item.dictid = Number(item.dictid);
                        return item.dicttypeid == "KPI_EVAL_SET";
                    });
                });
            },

            // 通过当前人和审批人判断考核所处阶段
            judgeHandlePeople(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat({ check: this.userId, eid: val.eid, flag: 'pj' });
                judgeStatus(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.judgeStatus = Number(res.data.message);
                        this.$nextTick(() => {
                            this.seaTableData();
                        })
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取考核信息
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '1' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.khData = res.data.results.filter(li => {
                            if (li.finishdate != undefined) {
                                li.finishdate = Moment(li.finishdate).format('YYYY-MM-DD');
                            } else {
                                li.finishdate = ''
                            }
                            return li
                        });
                        this.khData.splice(0, 0)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 初始化
            getZbData(lc, ygxq) {
                this.judgeHandlePeople(lc);
                this.lcData = lc;
                this.ygDetail = ygxq;
            },

            // 评价单人
            corrSingle(val) { 
                const loading = common.loading(this);
                getPpmKpiPmDetailInfo({ id: val.id }).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        if (res.data.results.itemstatus == 3 || res.data.results.itemstatus == 4) {
                            this.active = 1;
                        } else if (res.data.results.itemstatus == 5) {
                            this.active = 2;
                        } else if (res.data.results.itemstatus == 6) {
                            this.active = 3;
                        } else if (res.data.results.itemstatus == 7) {
                            this.active = 4;
                        }
                        if (res.data.results.firstevalname == undefined) {
                            res.data.results.firstevalname = '初评';
                        }
                        // 终评显示初评内容
                        // if (res.data.results.itemstatus == 6 && res.data.results.itemstatus == this.judgeStatus && res.data.results.firstevalgoal != undefined) {
                        //     res.data.results.secondevalgoal = res.data.results.firstevalgoal;
                        //     res.data.results.secondevalscore = res.data.results.firstevalscore;
                        // }
                        if (res.data.results.secondevalname == undefined) {
                            res.data.results.secondevalname = '终评'
                        }
                        if (res.data.results.evalselftime != undefined) {
                            res.data.results.evalselftime = Moment(res.data.results.evalselftime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.evalselftime = '';
                        }
                        if (res.data.results.finishdate != undefined) {
                            res.data.results.finishdate = Moment(res.data.results.finishdate).format('YYYY-MM-DD');
                        } else {
                            res.data.results.finishdate = '';
                        }
                        if (res.data.results.firstevaltime != undefined) {
                            res.data.results.firstevaltime = Moment(res.data.results.firstevaltime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.firstevaltime = '';
                        }
                        if (res.data.results.secondevaltime != undefined) {
                            res.data.results.secondevaltime = Moment(res.data.results.secondevaltime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.secondevaltime = '';
                        }
                        this.updateSingData = res.data.results;
                        this.ifShowXgSingDialog = true;
                    } else {
                        this.$message.error('获取数据失败')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 评价单人确定
            sureXgSing() { 
                if (this.updateSingData.itemstatus == 5) {
                    if (this.updateSingData.firstevalgoal == undefined || this.updateSingData.firstevalgoal == '') {
                        this.$message.error('请选择评价');
                        return
                    }
                } else {
                    if (this.updateSingData.secondevalgoal == undefined || this.updateSingData.secondevalgoal == '') {
                        this.$message.error('请选择评价');
                        return
                    }
                }
                const loading = common.loading(this);
                let params = {};
                params.id = this.updateSingData.id;
                params.pid = this.updateSingData.pid;
                if (this.updateSingData.itemstatus == 5) {
                    params.firstevalgoal = this.updateSingData.firstevalgoal;
                    params.firstevalscore = this.updateSingData.firstevalscore;
                } else {
                    params.secondevalgoal = this.updateSingData.secondevalgoal;
                    params.secondevalscore = this.updateSingData.secondevalscore;
                }
                params.tscore = this.updateSingData.tscore;
                let data = common.paramsFormat(params);
                updateDetail(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$message.success('保存成功');
                        this.ifShowXgSingDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 提交单人评价
            commitSingleEval() { 
                if (this.updateSingData.itemstatus == 5) {
                    if (this.updateSingData.firstevalgoal == undefined || this.updateSingData.firstevalgoal == '') {
                        this.$message.error('请选择评价');
                        return
                    }
                } else {
                    if (this.updateSingData.secondevalgoal == undefined || this.updateSingData.secondevalgoal == '') {
                        this.$message.error('请选择评价');
                        return
                    }
                }
                const loading = common.loading(this);
                let params = {};
                params.id = this.updateSingData.id;
                params.itemstatus = this.updateSingData.itemstatus;
                if (this.updateSingData.itemstatus == 5) {
                    params.firstevalgoal = this.updateSingData.firstevalgoal;
                    params.firstevalscore = this.updateSingData.firstevalscore;
                } else {
                    params.secondevalgoal = this.updateSingData.secondevalgoal;
                    params.secondevalscore = this.updateSingData.secondevalscore;
                }
                params.tscore = this.updateSingData.tscore;
                let data = common.paramsFormat(params);
                submitEval(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$emit('tongji', 'kpipj');
                        this.$message.success('提交成功');
                        this.ifShowXgSingDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 评价单人取消
            cancelXgSing() {
                this.ifShowXgSingDialog = false;
            },

            // 初评评价改变
            cpChange(val) { 
                this.updateSingData.tscore = this.updateSingData.firstevalscore = common.accMul(this.updateSingData.value, Number(val))
            },

            // 终评评价改变
            zpChange(val) { 
                this.updateSingData.tscore = this.updateSingData.secondevalscore = common.accMul(this.updateSingData.value, Number(val))
            },

            // 退回自评
            backSelfEval() { 
                const loading = common.loading(this);
                let params = {};
                params.id = this.updateSingData.id;
                let data = common.paramsFormat(params);
                backEvalSelf(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) { 
                        this.ifShowXgSingDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 退回初评
            backFirstEval() { 
                const loading = common.loading(this);
                let params = {};
                params.id = this.updateSingData.id;
                let data = common.paramsFormat(params);
                backFirstEval(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$message.success('退回初评成功');
                        this.ifShowXgSingDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            }
        }
    }
</script>
<style scoped>
    .kpisp-body-operate {
        margin-bottom: 15px;
    }

    .kpisp-body-operate-left {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        line-height: 32px;
    }

    .el-icon-view {
        font-size: 12px;
        color: #8E8E8E;
        cursor: pointer;
    }

    .hmddialog-bottom {
        text-align: right;
    }

    .hmddialog-bottoms {
        text-align: center;
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
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        width: 100px;
        white-space: nowrap;
    }

    .zpContent {
        width: 100%;
    }

    .grxx-table-tr td:nth-child(even) {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        width: 261px;
    }

    .zpContent-zp {
        padding: 20px 15px 1px 15px;
        background-color: rgba(242, 242, 242, 1);
        margin-bottom: 15px;
    }

    .zpContent-zp .grxx-table {
        padding: 10px 0;
    }

    .zpContent-zp-title {
        margin: 0 0 10px 0;
        text-align: center;
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 16px;
    }

    .tdWarp {
        white-space: pre-wrap;
    }
</style>

<style>
    .el-table .pink-row {
        background: rgba(255, 218, 223, 0.972549019607843);
    }

    .form-none-marginbottom .el-form-item {
        margin-bottom: 0;
    }

    .zpContent .el-step__head.is-process {
        color: #C0C4CC;
        border-color: #C0C4CC;
    }

    .zpContent .el-step__title.is-process {
        color: #C0C4CC;
    }

    .zpContent .el-step__description.is-process {
        color: #C0C4CC;
    }

    .ifWarp .el-table .cell {
        white-space: pre-wrap;
    }
</style>
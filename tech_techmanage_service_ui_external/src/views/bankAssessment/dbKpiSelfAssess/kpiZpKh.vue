<!-- kpi自评 -->
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
                            <template v-if="scope.row.evalself != undefined">{{scope.row.evalself}}</template>
                            <template v-else>{{scope.row.halfyearevalself}}</template>
                        </template>
                    </el-table-column>
                    <el-table-column label="初评分" prop="firstevalscore" min-width="5%"></el-table-column>
                    <el-table-column label="终评分" prop="secondevalscore" min-width="5%"></el-table-column>
                    <el-table-column label="状态" prop="itemstatusname" min-width="10%"></el-table-column>
                    <el-table-column label="操作" min-width="10%">
                        <template slot-scope="scope">
                            <div>
                                <button-oranges v-if="scope.row.itemstatus == 3 || scope.row.itemstatus == 4"
                                    buttonName="自 评" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="corrSingle"></button-oranges>
                                <button-commons v-if="scope.row.itemstatus == 5 || scope.row.itemstatus == 6 || scope.row.itemstatus == 7"
                                    buttonName="查 看" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="corrSingle"></button-commons>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 自评单人 -->
        <el-dialog title="KPI指标详情" :visible.sync="ifShowXgSingDialog">
            <div class="zpContent">
                <el-steps :active="active" align-center>
                    <el-step title="KPI自评" :description="updateSingData.evalselftime"></el-step>
                    <el-step :title="updateSingData.firstevalname" :description="updateSingData.firstevaltime"></el-step>
                    <el-step :title="updateSingData.secondevalname" :description="updateSingData.secondevaltime"></el-step>
                    <el-step title="完成"></el-step>
                </el-steps>
                <div class="form-none-marginbottom">
                    <!-- <table class="grxx-table">
                        <tr class="grxx-table-tr">
                            <div>
                                <td>指标项：</td>
                                <td>{{updateSingData.itemdescribe}}</td>
                            </div>
                            <div>
                                <td>分值：</td>
                                <td>{{updateSingData.value}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>工作内容：</td>
                                <td class="tdWarp">{{updateSingData.plandescribe}}</td>
                            </div>
                            <div>
                                <td>工作目标：</td>
                                <td class="tdWarp">{{updateSingData.workremark}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>计划完成时间：</td>
                                <td>{{updateSingData.regtime}}</td>
                            </div>
                        </tr>
                    </table> -->
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
                <div v-if="updateSingData.itemstatus == 3 || updateSingData.itemstatus == 4">
                    <div class="zpContent-zp">
                        <p class="zpContent-zp-title">自评</p>
                        <el-form label-position="left" label-width="110px">
                            <el-form-item label="完成情况描述：">
                                <el-input v-if="updateSingData.itemstatus == 4" key="1" maxlength="300" show-word-limit
                                    type="textarea" :autosize="{ minRows: 5, maxRows: 7}" placeholder="请输入" v-model="updateSingData.evalself"></el-input>
                                <el-input v-else key="2" maxlength="300" show-word-limit type="textarea" :autosize="{ minRows: 5, maxRows: 7}"
                                    placeholder="请输入" v-model="updateSingData.halfyearevalself"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="hmddialog-bottom">
                        <el-button @click="cancelXgSing">取 消</el-button>
                        <el-button type="primary" @click="sureXgSing">保 存</el-button>
                    </div>
                </div>
                <div v-else>
                    <div class="zpContent-zp">
                        <p class="zpContent-zp-title">自评</p>
                        <el-form label-position="left" label-width="110px">
                            <el-form-item label="完成情况描述：">
                                <div v-if="updateSingData.evalself != undefined" key="1" class="tdWarp">{{updateSingData.evalself}}</div>
                                <div v-else key="2" class="tdWarp">{{updateSingData.halfyearevalself}}</div>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="zpContent-zp" v-if="updateSingData.firsteval != undefined">
                        <p class="zpContent-zp-title">初评</p>
                        <el-form label-position="left" label-width="110px">
                            <el-row :gutter="20">
                                <el-col :span="12">
                                    <el-form-item label="评价：">
                                        <!-- <div>{{updateSingData.firstevalgoal}}</div> -->
                                        <div v-for="item in eval_options" :key="item.dictid" v-if="item.dictid == updateSingData.firstevalgoal">{{item.dictname}}</div>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="评分：">
                                        <div>{{updateSingData.firstevalscore}}</div>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <!-- <el-row>
                                <el-col :span="24">
                                    <el-form-item label="评语：">
                                        <div>{{updateSingData.firstevalcomment}}</div>
                                    </el-form-item>
                                </el-col>
                            </el-row> -->
                        </el-form>
                    </div>
                    <div class="zpContent-zp" v-if="updateSingData.secondeval != undefined">
                        <p class="zpContent-zp-title">终评</p>
                        <el-form label-position="left" label-width="110px">
                            <el-row :gutter="20">
                                <el-col :span="12">
                                    <el-form-item label="评价：">
                                        <!-- <div>{{updateSingData.secondevalgoal}}</div> -->
                                        <div v-for="item in eval_options" :key="item.dictid" v-if="item.dictid == updateSingData.secondevalgoal">{{item.dictname}}</div>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="评分：">
                                        <div>{{updateSingData.secondevalscore}}</div>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <!-- <el-row>
                                <el-col :span="24">
                                    <el-form-item label="评语：">
                                        <div>{{updateSingData.secondevalcomment}}</div>
                                    </el-form-item>
                                </el-col>
                            </el-row> -->
                        </el-form>
                    </div>
                    <div class="hmddialog-bottom">
                        <el-button @click="cancelXgSing">确 定</el-button>
                    </div>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonCommons from '@/components/eleButton/buttonCommons';
    import { getPpmKpiPmDetailInfo, updatePpmKpiPmDetailDto } from "@/api/hykh/kpiZp";
    import { getPpmKpiPmDetails, getKpiItems, addPpmKpiPmDetailItemApprove } from "@/api/hykh/myDb";
    import { getDicEntry } from "@/api/eosDictEntry";
    import common from "@/api/common";
    import Moment from 'moment';
    export default {
        name: 'kpiZpKh',
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
            }
        },
        mounted() {
            this.getDic();
        },
        methods: {
            // 设置表格行颜色
            tableRowClassName(row, rowIndex) {
                // if (this.lcData.status == 4) {
                //     if (row.row.evalself != undefined && (row.row.itemstatus == 3 || row.row.itemstatus == 4)) {
                //         return 'pink-row'
                //     }
                // } else {
                //     if (row.row.halfyearevalself != undefined && (row.row.itemstatus == 3 || row.row.itemstatus == 4)) {
                //         return 'pink-row'
                //     }
                // }
                if (row.row.evalself != undefined && row.row.itemstatus == 4) {
                    return 'pink-row'
                } else if (row.row.halfyearevalself != undefined && row.row.itemstatus == 3) {
                    return 'pink-row'
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

            // 获取考核信息
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '1' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取考核信息', res.data)
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
            getZbData(val) {
                this.lcData = val;
                this.$nextTick(() => {
                    this.seaTableData();
                })
            },

            // 自评单人
            corrSingle(val) {
                console.log('修改单人', val);
                const loading = common.loading(this);
                getPpmKpiPmDetailInfo({ id: val.id }).then(res => {
                    loading.close();
                    console.log("获取自评数据", res.data)
                    if (res.data.status == 200) {
                        if (res.data.results.itemstatus == 3 || res.data.results.itemstatus == 4) {
                            this.active = 1;
                        } else if (res.data.results.itemstatus == 5) {
                            this.active = 2;
                        } else if (res.data.results.itemstatus == 6) {
                            this.active = 3;
                        } else if (res.data.results.itemstatus == 7) {
                            this.active = 4;
                        } else {
                            this.active = -1;
                        }
                        if (res.data.results.firstevalname == undefined) {
                            res.data.results.firstevalname = '初评'
                        }
                        if (res.data.results.secondevalname == undefined) {
                            res.data.results.secondevalname = '终评'
                        }
                        if (res.data.results.evalselftime != undefined) {
                            res.data.results.evalselftime = Moment(res.data.results.evalselftime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.evalselftime = '';
                        }
                        if (res.data.results.regtime != undefined) {
                            res.data.results.regtime = Moment(res.data.results.regtime).format('YYYY-MM-DD');
                        } else {
                            res.data.results.regtime = '';
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

            // 自评单人确定
            sureXgSing() {
                console.log("自评单人确定", this.updateSingData)
                if (this.updateSingData.itemstatus == 4) {
                    if (this.updateSingData.evalself == undefined || this.updateSingData.evalself == '') {
                        this.$message.error('请填写自评');
                        return
                    }
                } else {
                    if (this.updateSingData.halfyearevalself == undefined || this.updateSingData.halfyearevalself == '') {
                        this.$message.error('请填写自评');
                        return
                    }
                }
                const loading = common.loading(this);
                // let myDate = new Date();
                // let currentDate = myDate.getFullYear() + '-' + (myDate.getMonth() + 1) + '-' + myDate.getDate() + 'T' + myDate.getHours() + ':' +myDate.getMinutes() + ':' + myDate.getSeconds();
                let params = {};
                params.id = this.updateSingData.id;
                // params.evalselftime = currentDate;
                if (this.updateSingData.itemstatus == 4) {
                    params.evalself = this.updateSingData.evalself;
                } else {
                    params.halfyearevalself = this.updateSingData.halfyearevalself;
                }
                let data = common.paramsFormat(params);
                updatePpmKpiPmDetailDto(data).then(res => {
                    loading.close();
                    console.log("自评单人确定", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('保存成功');
                        this.ifShowXgSingDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error('保存失败')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 自评单人取消
            cancelXgSing() {
                this.ifShowXgSingDialog = false;
            },
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

    .form-none-marginbottom .el-form-item {
        margin-bottom: 0;
    }
</style>
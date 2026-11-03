<!-- 评价发起 -->
<template>
    <div class="khfq-container">
        <div class="khfq-body">
            <!-- <h3 class="khfq-body-title">{{currentYear}}年考核发起</h3> -->
            <div class="khfq-body-cont">
                <el-table :data="khfqData">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="时间" prop="term" width="150"></el-table-column>
                    <el-table-column label="考核内容" prop="content" width="300"></el-table-column>
                    <el-table-column label="被发起角色" prop="relroles" width="300"></el-table-column>
                    <el-table-column label="自动发起时间" prop="finishtime" width="105"></el-table-column>
                    <el-table-column label="当前状态" width="100">
                        <template slot-scope="scope">
                            <span v-if="scope.row.islaunch == 1">已发起</span>
                            <span v-else-if="scope.row.islaunch == 0">未发起</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="160">
                        <template slot-scope="scope">
                            <!-- <button-deepskyblue v-if="scope.row.itemtype != '12'" size="mini" buttonName="修 改"
                                :parentData="scope.row" :fns="corrSingle"></button-deepskyblue> -->
                            <el-button type="warning" size="mini" @click="corrSingle(scope.row)">修 改</el-button>
                            <el-button type="primary" size="mini" @click="startTable(scope.row)">立即发起</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 修改单人 -->
        <el-dialog :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <h3 class="jzTitle">{{currentYear}}考核发起</h3>
                <el-form :model="updateSingData" ref="formXgSing">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>考核项</p>
                                        <el-form-item prop="content">
                                            <el-input type="textarea" autosize v-model="updateSingData.content"
                                                :disabled="true"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>半年度</p>
                                        <el-form-item prop="term">
                                            <el-input v-model="updateSingData.term" :disabled="true"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>发起日期 *</p>
                                        <el-form-item prop="finishtime" :rules="[{ required: true, message: '请选择发起日期', trigger: 'change'}]">
                                            <el-date-picker v-model="updateSingData.finishtime" type="date" placeholder="请选择日期"
                                                value-format="yyyy-MM-dd"></el-date-picker>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <!-- <el-col :span="12">
                                    <div>
                                        <p>结束日期 *</p>
                                        <el-form-item prop="endtime" :rules="[{ required: true, message: '请选择结束日期', trigger: 'change'}]">
                                            <el-date-picker v-model="updateSingData.endtime" type="date" placeholder="请选择日期"
                                                value-format="yyyy-MM-dd"></el-date-picker>
                                        </el-form-item>
                                    </div>
                                </el-col> -->
                            </el-row>
                        </div>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelXgSing"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="保 存" size="mini" :fns="sureXgSing"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { getPpmKpiItemSwitchList, updatePpmKpiItemSwitch, updateLaunchPpmKpiItemSwitch, launchEmployee, launchManagers, launchWorkManager, launchSelfEval, launchPostEvaluationEval, launchYearData, launchComprehensive, launchComment, launchDepManagersSelfEval } from "@/api/hykh/khStart";
    import Moment from 'moment';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    export default {
        name: 'khfqCenter',
        components: { buttonDeepskyblue, buttonCommon, buttonOrange },
        data() {
            return {
                yearArr: [],
                currentYear: 2021,
                khfqData: [],
                activeValue: 1,
                inactiveValue: 0,
                updateSingData: {}, //修改单人数据
                ifShowXgSingDialog: false, //修改单人弹窗
            }
        },
        mounted() {
            this.init();
            this.seaTableData();
        },
        methods: {
            init() {
                let date = new Date();
                this.currentYear = date.getFullYear();
                this.yearArr = [date.getFullYear(), date.getFullYear() - 1];
            },

            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ tyear: this.currentYear, itype: '1' });
                getPpmKpiItemSwitchList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.khfqData = res.data.results.filter(li => {
                            if (li.launchtime != undefined) {
                                li.launchtime = Moment(li.launchtime).format('YYYY-MM-DD');
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 开关变化
            swChange(val) {
                const loading = common.loading(this);
                let params = val;
                let data = common.paramsFormat(params);
                updatePpmKpiItemSwitch(data).then(res => {
                    loading.close();
                    console.log('开关变化', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('成功');
                        this.seaTableData();
                        this.$emit('updateZd');
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 表中发起
            startTable(val) {
                if (val.itemtype == '5' || val.itemtype == '8') {
                    this.fqZhnlpg(val);
                } else if (val.itemtype == '7') {
                    this.fqManagerHalfKpiZp(val)
                } else if (val.itemtype == '10') {
                    this.fqLzpg(val)
                } else if (val.itemtype == '6' || val.itemtype == '9') {
                    this.fqZtpy(val);
                } else {
                    this.$message('暂未开放');
                }
            },

            // 发起综合能力评估
            fqZhnlpg(val) {
                // const loading = common.loading(this);
                const loading = this.$loading({
                    lock: true,
                    text: '请稍等',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0,0,0,0.7)'
                });
                let params = {};
                params.tyear = val.tyear;
                params.term = val.term;
                params.itemtype = val.itemtype;
                let data = common.paramsFormat(params);
                launchComprehensive(data).then(res => {
                    loading.close();
                    console.log('发起综合能力评估', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('发起成功');
                        this.fqUpdate(val);
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 发起经理
            fqManagers(val) {
                const loading = common.loading(this);
                let params = {};
                params.tyear = val.tyear;
                params.itemtype = val.itemtype;
                let data = common.paramsFormat(params);
                launchManagers(data).then(res => {
                    loading.close();
                    console.log('发起员工', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('发起成功');
                        this.fqUpdate(val);
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 发起主持工作经理
            fqWorkManager(val) {
                const loading = common.loading(this);
                let params = {};
                params.tyear = val.tyear;
                params.itemtype = val.itemtype;
                let data = common.paramsFormat(params);
                launchWorkManager(data).then(res => {
                    loading.close();
                    console.log('发起员工', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('发起成功');
                        this.fqUpdate(val);
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 发起经理半年度KPI自评考核
            fqManagerHalfKpiZp(val) {
                const loading = common.loading(this);
                let params = {};
                params.tyear = val.tyear;
                params.itemtype = val.itemtype;
                let data = common.paramsFormat(params);
                launchSelfEval(data).then(res => {
                    loading.close();
                    console.log('发起经理半年度KPI自评考核', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('发起成功');
                         this.fqUpdate(val);
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 发起履职评估
            fqLzpg(val) {
                const loading = common.loading(this);
                let params = {};
                params.tyear = val.tyear;
                params.itemtype = val.itemtype;
                let data = common.paramsFormat(params);
                launchPostEvaluationEval(data).then(res => {
                    loading.close();
                    console.log('发起履职评估', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('发起成功');
                         this.fqUpdate(val);
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 发起总体评语
            fqZtpy(val) {
                const loading = common.loading(this);
                let params = {};
                params.tyear = val.tyear;
                params.term = val.term;
                params.itemtype = val.itemtype;
                let data = common.paramsFormat(params);
                launchComment(data).then(res => {
                    loading.close();
                    console.log('发起总体评语', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('发起成功');
                         this.fqUpdate(val);
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 发起更新
            fqUpdate(val) {
                const loading = common.loading(this);
                let date = new Date();
                let currentMonth = date.getMonth() + 1;
                val.islaunch = 1;
                val.isswitch = 1;
                let params = val;
                let data = common.paramsFormat(params);
                updateLaunchPpmKpiItemSwitch(data).then(res => {
                    loading.close();
                    console.log('开关变化', res.data)
                    if (res.data.status == 200) {
                        this.seaTableData();
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
                this.updateSingData = JSON.parse(JSON.stringify(val));
                this.ifShowXgSingDialog = true;
            },

            // 修改单人确定
            sureXgSing() {
                console.log("修改单人确定", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.id = this.updateSingData.id;
                        params.finishtime = this.updateSingData.finishtime;
                        // params.endtime = this.updateSingData.endtime;
                        let data = common.paramsFormat(params);
                        updatePpmKpiItemSwitch(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.ifShowXgSingDialog = false;
                                this.seaTableData();
                            } else {
                                this.$message.error('更新失败')
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    } else {
                        this.$message.error('请填写必填项');
                        return false;
                    }
                });
            },

            // 修改单人取消
            cancelXgSing() {
                this.ifShowXgSingDialog = false;
            },

            // 修改单人弹窗关闭回调
            xgSingClose() {
                this.$refs["formXgSing"].clearValidate();
            },
        }
    }
</script>
<style scoped>
    .khfq-container {
        padding: 15px;
    }

    .khfq-body {
        background: #FFFFFF;
    }

    .khfq-body-title {
        font-size: 16px;
    }

    .hmddialog-bottom {
        text-align: center;
    }
</style>
<style>
    .khfq-head .el-input__prefix {
        line-height: 36px;
    }

    .khfq-head .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }
</style>
<!-- 发起评级 -->
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
                        <el-table-column label="发起日期" prop="launchtime" width="105"></el-table-column>
                        <el-table-column label="当前状态" width="100">
                            <template slot-scope="scope">
                                <span v-if="scope.row.islaunch == 1">已发起</span>
                                <span v-else-if="scope.row.islaunch == 0">未发起</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button type="warning" size="mini" @click="startTable(scope.row)">发 起</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </div>
    </template>
    <script>
        import common from "@/api/common";
        import { getPpmKpiItemSwitchList, updatePpmKpiItemSwitch, updateLaunchPpmKpiItemSwitch, launchEmployee, launchManagers, launchWorkManager, launchSelfEval, launchPostEvaluationEval, launchYearData } from "@/api/hykh/khStart";
        import Moment from 'moment';
        export default {
            name: 'khfqCenter',
            components: {},
            data() {
                return {
                    yearArr: [],
                    currentYear: 2021,
                    khfqData: [],
                    activeValue: 1,
                    inactiveValue: 0,
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
                    let data = common.paramsFormat({ tyear: this.currentYear, itype: '2' });
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
                    if (val.itemtype == '11') {
                        this.fqYearPj(val);
                    } else {
                        this.$message('暂未开放');
                    }
                },
    
                // 发起员工
                fqEmployee(val) {
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
                    launchEmployee(data).then(res => {
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
    
                // 发起年度评级
                fqYearPj(val) {
                    const loading = common.loading(this);
                    let params = {};
                    params.tyear = val.tyear;
                    let data = common.paramsFormat(params);
                    launchYearData(data).then(res => {
                        loading.close();
                        console.log('发起年度评级', res.data)
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
                }
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
    </style>
    <style>
        .khfq-head .el-input__prefix {
            line-height: 36px;
        }
    
        .khfq-head .el-input--prefix .el-input__inner {
            padding-left: 50px;
        }
    </style>
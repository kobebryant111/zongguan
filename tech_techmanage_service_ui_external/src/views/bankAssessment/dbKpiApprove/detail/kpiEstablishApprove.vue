<!-- KPI审批-聚合页 -->
<template>
    <div class="kpizd-container">
        <div class="kpizd-nav">
            <div class="kh-back-bread">
                <div class="kh-back-bread-butt">
                    <el-button size="small" icon="el-icon-arrow-left" @click="lastPage">返回</el-button>
                </div>
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item v-for="(item,index) in routesMeta" :key="index">
                        <span :class="[routesMeta.length-1 == index ? 'lastJc' : 'crumbOther']">{{item}}</span>
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div>
                <el-button type="info" size="small" @click="goBack">退回修改</el-button>
                <button-oranges buttonName="审批通过" class="xkg-marginleft-10px" size="small" :fns="approval"></button-oranges>
            </div>
        </div>
        <div class="kpizd-head">
            <el-steps :active="active" align-center>
                <el-step title="KPI制定/修改" :description="lcData.createtime"></el-step>
                <el-step :title="lcData.firstcheckname" :description="lcData.firstchecktime"></el-step>
                <el-step :title="lcData.secondcheckname" :description="lcData.secondchecktime"></el-step>
                <el-step title="生效中"></el-step>
            </el-steps>
        </div>
        <div class="kpizd-body">
            <div class="kpizd-body-content">
                <el-tabs type="border-card">
                    <el-tab-pane label="KPI指标">
                        <kpi-approve ref="kpisp"></kpi-approve>
                    </el-tab-pane>
                    <el-tab-pane label="知识技能提升">
                        <zhnl-promote ref="kpizhnl">
                        </zhnl-promote>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
        <el-dialog title="是否退回修改？" :visible.sync="ifShowThDialog" center width="35%">
            <div>
                <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" placeholder="请输入修改意见" v-model="rollbacksuggest"></el-input>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelXgSing">取 消</el-button>
                    <el-button type="primary" @click="backChange">确 定</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { approveKpiDetail, getPpmKpiPmInfo, rollbackKpiDetail } from "@/api/hykh/myDb";
    import kpiApprove from '@/views/bankAssessment/dbKpiApprove/detail/kpiApprove';
    import zhnlPromote from '@/views/bankAssessment/dbKpiApprove/detail/zhnlPromote';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import Moment from 'moment';
    export default {
        name: 'kpiEstablishApprove',
        components: { kpiApprove, buttonOranges, zhnlPromote },
        data() {
            return {
                lcData: {},
                active: -1,
                ifShowThDialog: false,
                routerData: {},
                rollbacksuggest: '',
                routesMeta: []
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "kpiPj") {
                    vm.routesMeta = ['我的考核', to.params.eidname, 'KPI考核', 'KPI审批']
                } else if (from.name == "kpispList") {
                    vm.routesMeta = ['我的待办', 'KPI审批', to.params.eidname]
                }
            })
        },
        // beforeRouteLeave(to, from, next) {
        //     if (to.name == "kpispList") {
        //         this.$router.push({name: 'myKpiDb'});
        //     }
        //     next();
        // },
        mounted() {
            console.log('路由传值审批', this.$route.params)
            this.routerData = this.$route.params;
            this.getLcData(this.$route.params);
        },
        methods: {
            // 获取流程信息
            getLcData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getPpmKpiPmInfo(data).then(res => {
                    loading.close();
                    console.log('获取流程信息', res.data)
                    if (res.data.status == 200) {
                        if (res.data.results.status == 0) {
                            this.active = 1;
                        } else if (res.data.results.status == 1) {
                            this.active = 2;
                        } else if (res.data.results.status == 2) {
                            this.active = 3;
                        } else {
                            this.active = 4;
                        }
                        if (res.data.results.firstcheckname == undefined) {
                            res.data.results.firstcheckname = '初审'
                        }
                        if (res.data.results.secondcheckname == undefined) {
                            res.data.results.secondcheckname = '终审'
                        }
                        if (res.data.results.createtime != undefined) {
                            res.data.results.createtime = Moment(res.data.results.createtime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.createtime = '';
                        }
                        if (res.data.results.firstchecktime != undefined) {
                            res.data.results.firstchecktime = Moment(res.data.results.firstchecktime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.firstchecktime = '';
                        }
                        if (res.data.results.secondchecktime != undefined) {
                            res.data.results.secondchecktime = Moment(res.data.results.secondchecktime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.secondchecktime = '';
                        }
                        this.lcData = res.data.results;
                        this.$nextTick(() => {
                            this.$refs.kpisp.getZbData(res.data.results);
                            this.$refs.kpizhnl.getZbData(res.data.results);
                        })
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 审批弹窗
            approval(val) {
                this.$confirm('是否确认审批通过？', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.commit();
                }).catch(error => { })
            },

            // 审批通过
            commit() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ id: this.lcData.id });
                approveKpiDetail(data).then(res => {
                    loading.close();
                    console.log('审批通过', res.data)
                    if (res.data.status == 200) {
                        this.$router.back();
                        this.$message.success(res.data.message)
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 退回弹窗
            goBack() {
                this.ifShowThDialog = true;
            },

            //退回弹窗取消
            cancelXgSing() {
                this.ifShowThDialog = false;
            },

            // 退回修改
            backChange() {
                if (this.rollbacksuggest == '') {
                    this.$message.error('请填写退回意见');
                    return
                }
                const loading = common.loading(this);
                let data = common.paramsFormat({ id: this.lcData.id, rollbacksuggest: this.rollbacksuggest });
                rollbackKpiDetail(data).then(res => {
                    loading.close();
                    console.log('退回修改', res.data)
                    if (res.data.status == 200) {
                        // this.getLcData(this.routerData);
                        this.$router.back();
                        this.ifShowThDialog = false;
                        this.$message.success(res.data.message)
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 返回
            lastPage() {
                this.$router.back()
            }

        }
    }
</script>
<style scoped>
    .kpizd-container {
        padding: 8px 15px 15px 15px;
    }

    .kpizd-head {
        background: inherit;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 10px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        color: #CCCCCC;
        padding: 45px 0;
    }

    .kpizd-body {
        margin-top: 20px;
    }

    .kpizd-nav {
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
    }

    .hmddialog-bottom {
        text-align: center;
        margin-top: 20px;
    }

    .lastJc {
        font-family: '微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑', sans-serif;
        font-weight: 700;
        color: #414141;
    }

    .crumbOther {
        font-family: '微软雅黑', sans-serif;
        font-weight: 400;
        color: #939393;
    }

    .kh-back-bread {
        height: 32px;
    }

    .kh-back-bread-butt {
        height: 32px;
        line-height: 32px;
        float: left;
        margin-right: 10px;
    }
</style>

<style>
    .kpizd-head .el-step__head.is-process {
        color: #C0C4CC;
        border-color: #C0C4CC;
    }

    .kpizd-head .el-step__title.is-process {
        color: #C0C4CC;
    }

    .kpizd-head .el-step__description.is-process {
        color: #C0C4CC;
    }

    .kh-back-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
        float: left;
    }
</style>
<!-- kpi制定-聚合页 -->
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
                <button-oranges v-if="ifShowButt" buttonName="提 交" class="xkg-marginleft-10px" size="small" :fns="getApprovalAuth"></button-oranges>
            </div>
        </div>
        <!-- <div class="kpizd-head" v-if="lcData.rollbacksuggest != undefined"> -->
        <div class="kpizd-head">
            <el-steps :active="active" align-center>
                <el-step title="KPI制定/修改" :description="lcData.createtime"></el-step>
                <el-step :title="lcData.firstcheckname" :description="lcData.firstchecktime"></el-step>
                <el-step :title="lcData.secondcheckname" :description="lcData.secondchecktime"></el-step>
                <el-step title="生效中"></el-step>
            </el-steps>
            <!-- <div class="kpizd-head-middle" v-if="lcData.status != 3"> -->
            <div class="kpizd-head-middle" v-if="lcData.rollbacksuggest != undefined">
                <el-form label-position="left" label-width="90px">
                    <el-form-item label="退回意见：">
                        <div class="kpizd-head-middle-text">
                            <el-scrollbar style="height:100%;width:100%;">
                                <div>{{lcData.rollbacksuggest}}</div>
                            </el-scrollbar>
                        </div>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <div class="kpizd-body">
            <div class="kpizd-body-content">
                <el-tabs type="border-card">
                    <el-tab-pane label="KPI指标">
                        <template>
                            <kpi-approve ref="kpisp" :ifShowButt="ifShowButt" @getLcData="getLcData"></kpi-approve>
                        </template>
                    </el-tab-pane>
                    <el-tab-pane label="知识技能提升">
                        <template>
                            <zhnl-promote ref="kpizhnl" :ifShowButt="ifShowButt" @getLcData="getLcData">
                            </zhnl-promote>
                        </template>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { submitKpiDetail, getPpmKpiPmInfo, querySwitch } from "@/api/hykh/myDb";
    import kpiApprove from '@/views/bankAssessment/kpiEstablish/kpiApprove';
    import zhnlPromote from '@/views/bankAssessment/kpiEstablish/zhnlPromote';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import Moment from 'moment';
    import { getPpmKpiRoleInfo } from "@/api/hykh/ygKh";
    export default {
        name: 'kpiEstablish',
        components: { kpiApprove, buttonOranges, zhnlPromote },
        data() {
            return {
                lcData: {},
                active: -1,
                ifShowButt: false,
                routerData: {},
                routesMeta: []
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "kpiZp") {
                    vm.routesMeta = ['我的考核', 'KPI考核', 'KPI制定']
                } else if (from.name == "myKpiDb") {
                    vm.routesMeta = ['我的待办', 'KPI制定']
                }
            })
        },
        mounted() {
            console.log('路由传值', this.$route.params)
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
                        this.seaSwitch(res.data.results);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 查询开关详情
            seaSwitch(val) {
                if (val.status == 0 || val.status == 3) {
                    this.ifShowButt = true;
                }
                this.$nextTick(() => {
                    this.$refs.kpisp.getZbData(val);
                    this.$refs.kpizhnl.getZbData(val);
                })
            },

            // // 查询开关详情
            // seaSwitch(val) {
            //     if (val.role == 'COMM_USER' && val.term == '上半年') {
            //         const loading = common.loading(this);
            //         querySwitch({ itemtype: '1', tyear: val.tyear }).then(res => {
            //             loading.close();
            //             console.log('查询开关详情', res.data)
            //             if (res.data.status == 200) {
            //                 if (res.data.results.isswitch == 1 && (val.status == 0 || val.status == 3)) {
            //                     this.ifShowButt = true;
            //                 }
            //                 this.$nextTick(() => {
            //                     this.$refs.kpisp.getZbData(val);
            //                     this.$refs.kpizhnl.getZbData(val);
            //                 })
            //             } else {
            //                 this.$message.error(res.data.message)
            //             }
            //         }).catch(error => {
            //             loading.close();
            //             this.$message.error(error)
            //         })
            //     } else if (val.role == 'COMM_USER' && val.term == '下半年') {
            //         const loading = common.loading(this);
            //         querySwitch({ itemtype: '2', tyear: val.tyear }).then(res => {
            //             loading.close();
            //             console.log('查询开关详情', res.data)
            //             if (res.data.status == 200) {
            //                 if (res.data.results.isswitch == 1 && val.status == 0) {
            //                     this.ifShowButt = true;
            //                 }
            //                 this.$nextTick(() => {
            //                     this.$refs.kpisp.getZbData(val);
            //                     this.$refs.kpizhnl.getZbData(val);
            //                 })
            //             } else {
            //                 this.$message.error(res.data.message)
            //             }
            //         }).catch(error => {
            //             loading.close();
            //             this.$message.error(error)
            //         })
            //     } else {
            //         const loading = common.loading(this);
            //         querySwitch({ itemtype: '3', tyear: val.tyear }).then(res => {
            //             loading.close();
            //             console.log('查询开关详情', res.data)
            //             if (res.data.status == 200) {
            //                 if (res.data.results.isswitch == 1 && val.status == 0) {
            //                     this.ifShowButt = true;
            //                 }
            //                 this.$nextTick(() => {
            //                     this.$refs.kpisp.getZbData(val);
            //                     this.$refs.kpizhnl.getZbData(val);
            //                 })
            //             } else {
            //                 this.$message.error(res.data.message)
            //             }
            //         }).catch(error => {
            //             loading.close();
            //             this.$message.error(error)
            //         })
            //     }
            // },

            // 提交弹窗
            approval(val) {
                this.$confirm(val, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    customClass: 'kpi-message-box'
                }).then(() => {
                    this.commit();
                }).catch(error => { })
            },

            // 提交
            commit() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ id: this.lcData.id });
                submitKpiDetail(data).then(res => {
                    loading.close();
                    console.log('提交', res.data)
                    if (res.data.status == 200) {
                        // this.getLcData(this.routerData);
                        this.$router.push({ name: 'myKpiDb' })
                        this.$message.success(res.data.message)
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 根据工号查询详情
            getApprovalAuth() {
                const loading = common.loading(this);
                getPpmKpiRoleInfo({ badge: this.lcData.eid }).then(res => {
                    loading.close();
                    console.log('根据工号查询详情', res.data)
                    if (res.data.status == 200) {
                        let data = res.data.results;
                        let mes = '';
                        if (data.firstcheck == undefined) {
                            // data.firstcheck = '无';
                            // data.firstcheckname = '';
                            mes = '请核对初审人"' + data.secondcheckname + '(' + data.secondcheck + ')"和终审人"' + data.secondcheckname + '(' + data.secondcheck + ')"是否准确,如有问题请联系考核管理员';
                        } else {
                            mes = '请核对初审人"' + data.firstcheckname + '(' + data.firstcheck + ')"和终审人"' + data.secondcheckname + '(' + data.secondcheck + ')"是否准确,如有问题请联系考核管理员';
                        }
                        // let mes = '请核对初审人"' + data.firstcheckname + '(' + data.firstcheck + ')"和终审人"' + data.secondcheckname + '(' + data.secondcheck + ')"是否准确,如果有误,请前往"我的考核-设置"修改';
                        this.approval(mes)
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
        margin-bottom: 8px;
    }

    .kpizd-head-middle {
        padding: 15px 85px;
        height: 80px;
    }

    .kpizd-head-middle-text {
        height: 100px;
        color: #333333;
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
    .kpizd-head-middle .el-form-item__label {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #333333;
    }

    .kpizd-head-middle .el-form {
        height: 100%;
    }

    .kpizd-head-middle .el-form-item {
        height: 100%;
    }

    .kpizd-head-middle .el-form-item__content {
        height: 100%;
    }

    .kpizd-head-middle .el-scrollbar__wrap {
        overflow-x: auto;
    }

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

    .kpi-message-box .el-message-box__message p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 18px;
        letter-spacing: normal;
        color: #333333;
    }

    .kh-back-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
        float: left;
    }
</style>
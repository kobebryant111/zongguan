<!-- kpi自评-聚合页 -->
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
                <div v-if="routerData.isDb == '1' || routerData.isDb == '2'">
                    <button-oranges v-if="(lcData.status == '3' || lcData.status == '4') && lcData.role != 'DEP_MANAGER' && lcData.role != 'DEP_MANAGERS'"
                        buttonName="提交" class="xkg-marginleft-10px" size="small" :fns="getApprovalAuth"></button-oranges>
                </div>
                <div v-else>
                    <button-oranges v-if="lcData.status == '3' || lcData.status == '4'" buttonName="提交" class="xkg-marginleft-10px"
                        size="small" :fns="getApprovalAuth"></button-oranges>
                </div>
            </div>
        </div>
        <div class="kpizd-tongji" v-if="routerData.isDb == '1' || routerData.isDb == '2'">
            <div class="kpizd-tongji-top">
                <div class="kpizd-tongji-top-left">{{routerData.tyear}}年{{routerData.term}}KPI考核</div>
                <div>
                    <el-button type="primary" @click="toKpizd">KPI制定/修改</el-button>
                </div>
            </div>
            <el-divider></el-divider>
            <div class="kpizd-tongji-bottom">
                <div class="kpizd-tongji-bottom-div">
                    <div>
                        <p class="kpizd-tongji-bottom-div-describe">考核项</p>
                        <p class="kpizd-tongji-bottom-div-number">{{tjData.kpsItems}}</p>
                    </div>
                </div>
                <div class="kpizd-tongji-bottom-div">
                    <div>
                        <p class="kpizd-tongji-bottom-div-describe">配置中</p>
                        <p class="kpizd-tongji-bottom-div-number">{{tjData.configureItems}}</p>
                    </div>
                </div>
                <div class="kpizd-tongji-bottom-div">
                    <div>
                        <p class="kpizd-tongji-bottom-div-describe">已完成</p>
                        <p class="kpizd-tongji-bottom-div-number">{{tjData.finishItems}}</p>
                    </div>
                </div>
                <div class="kpizd-tongji-bottom-div">
                    <div>
                        <p class="kpizd-tongji-bottom-div-describe">评价中</p>
                        <p class="kpizd-tongji-bottom-div-number">{{tjData.evalItems}}</p>
                    </div>
                </div>
                <div class="kpizd-tongji-bottom-div">
                    <div>
                        <p class="kpizd-tongji-bottom-div-describe">得分</p>
                        <p v-if="tjData.kpiscore != undefined" class="kpizd-tongji-bottom-div-number">{{tjData.kpiscore}}
                            <span>分</span>
                        </p>
                        <p v-else class="kpizd-tongji-bottom-div-number">0
                            <span>分</span>
                        </p>
                    </div>
                </div>
                <div class="kpizd-tongji-bottom-div">
                    <div>
                        <p class="kpizd-tongji-bottom-div-describe">能力提升</p>
                        <p class="kpizd-tongji-bottom-div-number">{{tjData.abilityItems}}
                            <span>项</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="kpizd-body">
            <div class="kpizd-body-content">
                <el-tabs type="border-card">
                    <el-tab-pane label="KPI指标">
                        <kpi-zp-kh ref="kpisp"></kpi-zp-kh>
                    </el-tab-pane>
                    <el-tab-pane label="知识技能提升">
                        <kpi-zpzhnl-promote ref="kpizhnl">
                        </kpi-zpzhnl-promote>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { submitSelfEval, submitSelfEvalHalfSelf, getPpmKpiPmInfoTotalDto } from "@/api/hykh/kpiZp";
    import { getPpmKpiPmInfo } from "@/api/hykh/myDb";
    import kpiZpKh from '@/views/bankAssessment/dbKpiSelfAssess/kpiZpKh';
    import kpiZpzhnlPromote from '@/views/bankAssessment/dbKpiSelfAssess/kpiZpzhnlPromote';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import Moment from 'moment';
    import { getPpmKpiRoleInfo } from "@/api/hykh/ygKh";
    export default {
        name: 'kpiZp',
        components: { kpiZpKh, buttonOranges, kpiZpzhnlPromote },
        data() {
            return {
                lcData: {},
                routerData: {},
                tjData: {},
                routesMeta: []
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "myKh" || from.name == "kpiEstablish") {
                    vm.routesMeta = ['我的考核', 'KPI考核']
                } else if (from.name == "myKpiDb") {
                    vm.routesMeta = ['我的待办', 'KPI考核']
                }
            })
        },
        mounted() {
            console.log('路由传值', this.$route.params, this.$route.matched)
            let valueTrans = {};
            if (JSON.stringify(this.$route.params) == '{}') {
                valueTrans = JSON.parse(sessionStorage.getItem('hykh_mykh_kpizp_jump_data'))
            } else {
                valueTrans = this.$route.params;
            }
            // if (valueTrans.term == undefined) {
            //     valueTrans.term = ''
            // }
            this.routerData = valueTrans;
            this.$nextTick(() => {
                this.getLcData(valueTrans);
                this.getKhMainStatistics(valueTrans);
            })
            // this.routerData = this.$route.params;
            // this.getLcData(this.$route.params);
            // this.getKhMainStatistics(this.$route.params);
        },
        methods: {
            // 获得考核主项的统计项
            getKhMainStatistics(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getPpmKpiPmInfoTotalDto(data).then(res => {
                    loading.close();
                    console.log('获得考核主项的统计项', res.data)
                    if (res.data.status == 200) {
                        this.tjData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取流程信息
            getLcData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getPpmKpiPmInfo(data).then(res => {
                    loading.close();
                    console.log('获取流程信息', res.data)
                    if (res.data.status == 200) {
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
                if (this.routerData.juStatus == '半年度') {
                    submitSelfEvalHalfSelf(data).then(res => {
                        loading.close();
                        console.log('提交', res.data)
                        if (res.data.status == 200) {
                            if (this.routerData.isDb == '1') {
                                this.$router.push({ name: 'myKh' })
                            } else if (this.routerData.isDb == '2') {
                                this.$router.push({ name: 'ygkhDetail' })
                            } else {
                                this.$router.push({ name: 'myKpiDb' })
                            }
                            this.$message.success(res.data.message)
                        } else {
                            this.$message.error(res.data.message)
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                } else {
                    submitSelfEval(data).then(res => {
                        loading.close();
                        console.log('提交', res.data)
                        if (res.data.status == 200) {
                            if (this.routerData.isDb == '1') {
                                this.$router.push({ name: 'myKh' })
                            } else if (this.routerData.isDb == '2') {
                                this.$router.push({ name: 'ygkhDetail' })
                            } else {
                                this.$router.push({ name: 'myKpiDb' })
                            }
                            this.$message.success(res.data.message)
                        } else {
                            this.$message.error(res.data.message)
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }
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
                            // data.firstcheck = '';
                            // data.firstcheckname = '';
                            mes = '请核对初评人"' + data.secondcheckname + '(' + data.secondcheck + ')"和终评人"' + data.secondcheckname + '(' + data.secondcheck + ')"是否准确,如有问题请联系考核管理员';
                        } else {
                            mes = '请核对初评人"' + data.firstcheckname + '(' + data.firstcheck + ')"和终评人"' + data.secondcheckname + '(' + data.secondcheck + ')"是否准确,如有问题请联系考核管理员';
                        }

                        this.approval(mes)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 跳转KPI制定
            toKpizd() {
                console.log("跳转KPI制定", this.routerData);
                sessionStorage.setItem('hykh_mykh_kpizp_jump_data', JSON.stringify(this.routerData));
                this.$router.push({
                    name: 'kpiEstablish',
                    params: this.routerData
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

    .kpizd-tongji {
        height: 191px;
        background-color: rgba(255, 255, 255, 1);
        border-radius: 10px;
        padding: 15px;
    }

    .kpizd-tongji-top {
        display: flex;
        justify-content: space-between;
        padding: 0 10px;
    }

    .kpizd-tongji-top-left {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-size: 24px;
        color: #000333;
    }

    .kpizd-tongji-bottom {
        display: flex;
        justify-content: space-between;
    }

    .kpizd-tongji-bottom-div {
        border-left: 1px solid #DCDFE6;
        width: 16%;
        text-align: center;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .kpizd-tongji-bottom-div-describe {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-size: 14px;
        color: #333333;
        margin: 0;
    }

    .kpizd-tongji-bottom-div-number {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-size: 40px;
        color: #333333;
        margin: 3px 0 0 0;
    }

    .kpizd-tongji-bottom-div-number span {
        font-size: 14px;
    }

    .kpizd-tongji-bottom-div:first-child {
        border-left: none;
    }

    .kpizd-body {
        margin-top: 20px;
    }

    .kpizd-nav {
        /* text-align: right; */
        display: flex;
        justify-content: space-between;
        margin-bottom: 8px;
    }

    .hmddialog-bottom {
        text-align: center;
        margin-top: 20px;
    }

    .kpizd-body-content {
        background-color: #ffffff;
        border-radius: 10px;
        padding: 10px;
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
        height: 33px;
    }

    .kh-back-bread-butt {
        height: 33px;
        line-height: 33px;
        float: left;
        margin-right: 10px;
    }
</style>

<style>
    .kpi-message-box .el-message-box__message p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 18px;
        letter-spacing: normal;
        color: #333333;
    }

    .kh-back-bread .el-breadcrumb {
        height: 33px;
        line-height: 33px;
        float: left;
    }
</style>
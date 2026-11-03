<!-- kpi评价-聚合页 -->
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
                <button-oranges v-if="lcData.status == 4" buttonName="提交" class="xkg-marginleft-10px" size="small" :fns="approval"></button-oranges>
            </div>
        </div>
        <div class="kpizd-tongji" v-if="routerData.isDb == '1' || routerData.isDb == '2'">
            <div class="kpizd-tongji-top">
                <div class="kpizd-tongji-top-left">{{routerData.tyear}}年{{routerData.term}}KPI考核</div>
                <div>
                    <el-badge v-if="ifShowSpButt && tjData.approveItems != 0" :value="tjData.approveItems">
                        <el-button type="primary" @click="toKpizd">KPI审批</el-button>
                    </el-badge>
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
                        <kpi-pj-kh ref="kpisp" @getKhMainStatistics="getKhMainStatistics"></kpi-pj-kh>
                    </el-tab-pane>
                    <el-tab-pane label="知识技能提升">
                        <kpi-pjzhnl-promote ref="kpizhnl">
                        </kpi-pjzhnl-promote>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { getPpmKpiPmInfoTotalDto } from "@/api/hykh/kpiZp";
    import { submitKpiEval } from "@/api/hykh/kpiEval";
    import { getPpmKpiPmInfo } from "@/api/hykh/myDb";
    import kpiPjKh from '@/views/bankAssessment/employeeKh/kpiEvaluate/kpiPjKh';
    import kpiPjzhnlPromote from '@/views/bankAssessment/employeeKh/kpiEvaluate/kpiPjzhnlPromote';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import Moment from 'moment';
    import { getPpmKpiRoleByBadge } from "@/api/hykh/ygKh";
    import { mapGetters } from 'vuex';
    export default {
        name: 'kpiPj',
        components: { kpiPjKh, buttonOranges, kpiPjzhnlPromote },
        data() {
            return {
                lcData: {},
                routerData: {},
                tjData: {},
                ifShowSpButt: false,
                routesMeta: []
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "ygkhDetail" || from.name == "kpiEstablishApprove") {
                    if (to.params.eidname != undefined) {
                        vm.routesMeta = ['员工考核', to.params.eidname, 'KPI考核']
                    } else {
                        vm.routesMeta = ['员工考核', vm.routerData.eidname, 'KPI考核']
                    }
                }
            })
        },
        computed: {
            ...mapGetters(['userId'])
        },
        mounted() {
            console.log('路由传值', this.$route.params)
            let valueTrans = {};
            if (JSON.stringify(this.$route.params) == '{}') {
                valueTrans = JSON.parse(sessionStorage.getItem('hykh_ygkh_kpipj_jump_data'))
            } else {
                valueTrans = this.$route.params;
            }
            if (valueTrans.term == undefined) {
                valueTrans.term = ''
            }
            this.routerData = valueTrans;
            this.$nextTick(() => {
                this.getLcData(valueTrans);
                // this.getKhMainStatistics(valueTrans);
            })
        },
        methods: {
            // 根据工号查询详情
            getApprovalAuth(val) {
                getPpmKpiRoleByBadge({ badge: val.eid }).then(res => {
                    console.log('根据工号查询详情', res.data)
                    if (res.data.status == 200) {
                        if (this.userId == res.data.results.firstcheck && this.tjData.status == 1) { //初审
                            this.ifShowSpButt = true
                        } else if (this.userId == res.data.results.secondcheck && this.tjData.status == 2) { //终审
                            this.ifShowSpButt = true
                        }
                        this.$refs.kpisp.getZbData(this.lcData, this.routerData, res.data.results);
                        // this.$refs.kpizhnl.getZbData(this.lcData);
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 获得考核主项的统计项
            getKhMainStatistics(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getPpmKpiPmInfoTotalDto(data).then(res => {
                    loading.close();
                    console.log('获得考核主项的统计项', res.data)
                    if (res.data.status == 200) {
                        this.tjData = res.data.results;
                        this.$nextTick(() => {
                            this.getApprovalAuth(val);
                        })
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
                            // this.$refs.kpisp.getZbData(this.lcData, this.routerData);
                            this.$refs.kpizhnl.getZbData(this.lcData);
                            this.getKhMainStatistics(this.routerData);
                            // this.getApprovalAuth(val);
                        })
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 提交弹窗
            approval(val) {
                this.$confirm('是否确认提交？', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.commit();
                }).catch(error => { })
            },

            // 提交
            commit() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ id: this.lcData.id, eid: this.routerData.eid });
                submitKpiEval(data).then(res => {
                    loading.close();
                    console.log('提交', res.data)
                    if (res.data.status == 200) {
                        this.$message.success(res.data.message);
                        // this.$router.push({ name: 'ygkhDetail' });
                        this.getLcData(this.routerData);
                        this.getKhMainStatistics(this.routerData);
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 跳转KPI审批
            toKpizd() {
                console.log("跳转KPI审批", this.routerData);
                sessionStorage.setItem('hykh_ygkh_kpipj_jump_data', JSON.stringify(this.routerData));
                this.$router.push({
                    name: 'kpiEstablishApprove',
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
    .kh-back-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
        float: left;
    }
</style>
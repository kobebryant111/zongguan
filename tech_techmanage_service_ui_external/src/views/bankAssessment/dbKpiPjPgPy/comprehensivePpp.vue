<!-- 待办-评价评估评语综合页面 -->
<template>
    <div class="comprehensiveppp-container">
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
        <div class="ppp-head">
            <el-badge :value="tjData.kpi_items" v-if="routerData.role == 'COMM_USER' || routerData.role == 'DEP_MANAGERS'">
                <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}">KPI评价</el-button>
            </el-badge>
            <el-badge :value="tjData.comprehensiveAbility_items" class="middSty" v-if="tjData.comprehensiveAbility_items != 0">
                <el-button @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}">综合能力评估</el-button>
            </el-badge>
            <el-badge :value="tjData.first_half_year_comment_items" v-if="tjData.first_half_year_comment_items != 0"
                class="middSty">
                <el-button @click="cliTag(3)" :class="{ 'buttFous': showCon == 3}">上半年总体评语</el-button>
            </el-badge>
            <el-badge :value="tjData.last_half_year_comment_items" v-if="tjData.last_half_year_comment_items != 0"
                class="middSty">
                <el-button @click="cliTag(4)" :class="{ 'buttFous': showCon == 4}">下半年总体评语</el-button>
            </el-badge>
            <el-badge class="middSty" :value="tjData.postEvaluation_items" v-if="tjData.postEvaluation_items != 0">
                <el-button @click="cliTag(5)" :class="{ 'buttFous': showCon == 5}">履职评估</el-button>
            </el-badge>
        </div>
        <div class="ppp-body">
            <!-- <transition name="Fade"> -->
            <dbkpi-pj v-if="showCon == 1" :getTongJi="getTongJi"></dbkpi-pj>
            <db-zhnl-assessment v-else-if="showCon == 2" ref="zhnlpg" @getTongJi="getTongJi"></db-zhnl-assessment>
            <db-population-comment-first v-else-if="showCon == 3" @getTongJi="getTongJi"></db-population-comment-first>
            <db-population-comment-later v-else-if="showCon == 4" @getTongJi="getTongJi"></db-population-comment-later>
            <db-lzpg-eval v-else-if="showCon == 5" :getTongJi="getTongJi"></db-lzpg-eval>
            <!-- </transition> -->
        </div>
    </div>
</template>
<script>
    import dbkpiPj from '@/views/bankAssessment/dbKpiPjPgPy/dbKpiEvaluate/dbkpiPj';
    import dbZhnlAssessment from '@/views/bankAssessment/dbKpiPjPgPy/dbZhnlAssessment';
    import dbLzpgEval from '@/views/bankAssessment/dbKpiPjPgPy/lzpg/dbLzpgEval';
    import dbPopulationCommentFirst from '@/views/bankAssessment/dbKpiPjPgPy/dbPopulationCommentFirst';
    import dbPopulationCommentLater from '@/views/bankAssessment/dbKpiPjPgPy/dbPopulationCommentLater';
    import { getCheckEvalItemsSummary } from "@/api/hykh/kpiEval";
    import { mapGetters } from 'vuex';
    import common from "@/api/common";
    export default {
        name: 'comprehensivePpp',
        components: { dbkpiPj, dbZhnlAssessment, dbLzpgEval, dbPopulationCommentFirst, dbPopulationCommentLater },
        data() {
            return {
                showCon: null,
                routerData: {},
                routesMeta: [],
                tjData: {}
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "dbkpiPjList") {
                    if (to.params.term == undefined) {
                        vm.routesMeta = ['我的待办', '考核评价', to.params.eidname + to.params.tyear + '年KPI考核']
                    } else {
                        vm.routesMeta = ['我的待办', '考核评价', to.params.eidname + to.params.tyear + to.params.term + '考核']
                    }
                }
            })
        },
        mounted() {
            console.log('路由传值综合', this.$route.params)
            // let valueTrans = {};
            // valueTrans = this.$route.params;
            // if (valueTrans.term == undefined) {
            //     valueTrans.term = ''
            // }
            this.routerData = this.$route.params;
            this.getTongJi();
        },
        methods: {
            // 点击tag
            cliTag(v) {
                this.showCon = v;
                // this.$nextTick(() => {
                //     if (v == 2) {
                //         this.$refs.zhnlpg.init(this.routerData);
                //     }
                // })
            },

            // 获取统计数据
            getTongJi(val) {
                console.log('测试事件发射传值', val)
                let params = {};
                params.check = this.userId;
                params.eid = this.routerData.eid;
                params.pid = this.routerData.id;
                let data = common.paramsFormat(params);
                getCheckEvalItemsSummary(data).then(res => {
                    console.log('获取统计数据', res.data)
                    if (res.data.status == 200) {
                        if (val == undefined) {
                            if (res.data.results.kpi_items != 0 && res.data.results.kpi_items != undefined) {
                                this.showCon = 1
                            } else if (res.data.results.comprehensiveAbility_items != 0 && res.data.results.comprehensiveAbility_items != undefined) {
                                this.showCon = 2
                            } else if (res.data.results.first_half_year_comment_items != 0 && res.data.results.first_half_year_comment_items != undefined) {
                                this.showCon = 3
                            } else if (res.data.results.last_half_year_comment_items != 0 && res.data.results.last_half_year_comment_items != undefined) {
                                this.showCon = 4
                            } else if (res.data.results.postEvaluation_items != 0 && res.data.results.postEvaluation_items != undefined) {
                                this.showCon = 5
                            } else {
                                this.showCon = 1
                            }
                        } else {
                            if (val == 'kpipj') {
                                if (res.data.results.kpi_items != 0) {
                                    this.showCon = 1
                                } else {
                                    if (res.data.results.comprehensiveAbility_items != 0 && res.data.results.comprehensiveAbility_items != undefined) {
                                        this.showCon = 2
                                    } else if (res.data.results.first_half_year_comment_items != 0 && res.data.results.first_half_year_comment_items != undefined) {
                                        this.showCon = 3
                                    } else if (res.data.results.last_half_year_comment_items != 0 && res.data.results.last_half_year_comment_items != undefined) {
                                        this.showCon = 4
                                    } else if (res.data.results.postEvaluation_items != 0 && res.data.results.postEvaluation_items != undefined) {
                                        this.showCon = 5
                                    } else {
                                        this.$router.back();
                                    }
                                }
                            } else if (val == 'zhnlpg') {
                                if (res.data.results.comprehensiveAbility_items != 0) {
                                    this.showCon = 2
                                } else {
                                    if (res.data.results.kpi_items != 0 && res.data.results.kpi_items != undefined) {
                                        this.showCon = 1
                                    } else if (res.data.results.first_half_year_comment_items != 0 && res.data.results.first_half_year_comment_items != undefined) {
                                        this.showCon = 3
                                    } else if (res.data.results.last_half_year_comment_items != 0 && res.data.results.last_half_year_comment_items != undefined) {
                                        this.showCon = 4
                                    } else if (res.data.results.postEvaluation_items != 0 && res.data.results.postEvaluation_items != undefined) {
                                        this.showCon = 5
                                    } else {
                                        this.$router.back();
                                    }
                                }
                            } else if (val == 'firstpy') {
                                if (res.data.results.last_half_year_comment_items != 0 && res.data.results.last_half_year_comment_items != undefined) {
                                    this.showCon = 4
                                } else if (res.data.results.postEvaluation_items != 0 && res.data.results.postEvaluation_items != undefined) {
                                    this.showCon = 5
                                } else if (res.data.results.kpi_items != 0 && res.data.results.kpi_items != undefined) {
                                    this.showCon = 1
                                } else if (res.data.results.comprehensiveAbility_items != 0 && res.data.results.comprehensiveAbility_items != undefined) {
                                    this.showCon = 2
                                } else {
                                    this.$router.back();
                                }
                            } else if (val == 'latterpy') {
                                if (res.data.results.postEvaluation_items != 0 && res.data.results.postEvaluation_items != undefined) {
                                    this.showCon = 5
                                } else if (res.data.results.kpi_items != 0 && res.data.results.kpi_items != undefined) {
                                    this.showCon = 1
                                } else if (res.data.results.comprehensiveAbility_items != 0 && res.data.results.comprehensiveAbility_items != undefined) {
                                    this.showCon = 2
                                } else if (res.data.results.first_half_year_comment_items != 0 && res.data.results.first_half_year_comment_items != undefined) {
                                    this.showCon = 3
                                } else {
                                    this.$router.back();
                                }
                            } else if (val == 'lzpg') {
                                if (res.data.results.postEvaluation_items != 0) {
                                    this.showCon = 5
                                } else {
                                    if (res.data.results.kpi_items != 0 && res.data.results.kpi_items != undefined) {
                                        this.showCon = 1
                                    } else if (res.data.results.comprehensiveAbility_items != 0 && res.data.results.comprehensiveAbility_items != undefined) {
                                        this.showCon = 2
                                    } else if (res.data.results.first_half_year_comment_items != 0 && res.data.results.first_half_year_comment_items != undefined) {
                                        this.showCon = 3
                                    } else if (res.data.results.last_half_year_comment_items != 0 && res.data.results.last_half_year_comment_items != undefined) {
                                        this.showCon = 4
                                    } else {
                                        this.$router.back();
                                    }
                                }
                            }
                        }
                        this.tjData = res.data.results;
                    }
                }).catch(error => {
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
    .comprehensiveppp-container {
        padding: 8px 15px 15px 15px;
    }

    .ppp-head {
        margin-top: 8px;
    }

    .buttFous {
        background-color: rgba(255, 255, 255, 1);
        box-sizing: border-box;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(255, 184, 34, 1);
        border-radius: 4px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        color: #FFB822;
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

    .middSty {
        margin-left: 15px;
    }

    /* .Fade-enter,
    .Fade-leave-to {
        opacity: 0;
        transform: translateX(150px);
    } */

    /* .Fade-enter-to,
    .Fade-leave {
        opacity: 1;
    } */

    /* .Fade-enter-active,
    .Fade-leave-active {
        transition: all 0.8s ease;
    } */

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
    }
</style>
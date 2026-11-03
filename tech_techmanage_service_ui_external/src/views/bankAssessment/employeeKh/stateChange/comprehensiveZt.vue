<!-- 状态修改-评价评估评语综合页面 -->
<template>
    <div class="comprehensiveppp-container">
        <div>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-for="(item,index) in routesMeta" :key="index">
                    <span :class="[routesMeta.length-1 == index ? 'lastJc' : 'crumbOther']">{{item}}</span>
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="ppp-head">
            <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}" v-if="routerData.role == 'COMM_USER' || routerData.role == 'DEP_MANAGERS'">KPI评价</el-button>
            <el-button @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}" class="middSty" v-if="routerData.role == 'COMM_USER'">综合能力评估</el-button>
            <el-button @click="cliTag(3)" :class="{ 'buttFous': showCon == 3}" class="middSty">总体评语</el-button>
            <el-button @click="cliTag(4)" :class="{ 'buttFous': showCon == 4}" class="middSty" v-if="routerData.role != 'COMM_USER'">履职评估</el-button>
        </div>
        <div class="ppp-body">
            <ztkpi-pj v-if="showCon == 1"></ztkpi-pj>
            <zt-zhnl-assessment v-else-if="showCon == 2"></zt-zhnl-assessment>
            <population-eval v-else-if="showCon == 3"></population-eval>
            <ztxg-lzpg v-else-if="showCon == 4"></ztxg-lzpg>
        </div>
    </div>
</template>
<script>
    import ztkpiPj from '@/views/bankAssessment/employeeKh/stateChange/ztKpiEvaluate/ztkpiPj';
    import ztZhnlAssessment from '@/views/bankAssessment/employeeKh/stateChange/ztZhnlAssessment';
    import populationEval from '@/views/bankAssessment/employeeKh/stateChange/populationEval';
    import ZtxgLzpg from '@/views/bankAssessment/employeeKh/stateChange/ztxgLzpg';
    import { mapGetters } from 'vuex';
    import common from "@/api/common";
    export default {
        name: 'comprehensiveZt',
        components: { ztkpiPj, ztZhnlAssessment, populationEval, ZtxgLzpg },
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
                vm.routesMeta = ['状态更改', to.params.eidname]
            })
        },
        mounted() {
            console.log('路由传值综合', this.$route.params)
            this.routerData = this.$route.params;
            if (this.routerData.role == 'COMM_USER' || this.routerData.role == 'DEP_MANAGERS') {
                this.showCon = 1
            } else {
                this.showCon = 3
            }
        },
        methods: {
            // 点击tag
            cliTag(v) {
                this.showCon = v;
            },
        }
    }
</script>
<style scoped>
    .comprehensiveppp-container {
        padding: 15px;
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
</style>
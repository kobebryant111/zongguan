<!-- 配置中心-聚合页 -->
<template>
    <div class="pzzx-container">
        <div class="pzzx-head">
            <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}">员工管理</el-button>
            <el-button @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}">二级部管理</el-button>
            <el-button @click="cliTag(3)" :class="{ 'buttFous': showCon == 3}">中心管理</el-button>
            <el-button @click="cliTag(4)" :class="{ 'buttFous': showCon == 4}">一级部管理</el-button>
        </div>
        <div class="pzzx-body">
            <!-- <staff-manager v-if="showCon == 1"></staff-manager>
            <two-pz-manage v-else-if="showCon == 2"></two-pz-manage>
            <center-pz-manage v-else-if="showCon == 3"></center-pz-manage>
            <one-pz-manage v-else-if="showCon == 4"></one-pz-manage> -->
            <staff-manager v-show="showCon == 1"></staff-manager>
            <two-pz-manage v-show="showCon == 2"></two-pz-manage>
            <center-pz-manage v-show="showCon == 3"></center-pz-manage>
            <one-pz-manage v-show="showCon == 4"></one-pz-manage>
        </div>
    </div>
</template>
<script>
    import staffManager from "@/views/bankAssessment/setCenter/pzCenter/staffManager";
    import TwoPzManage from "@/views/bankAssessment/setCenter/pzCenter/twoPzManage";
    import CenterPzManage from "@/views/bankAssessment/setCenter/pzCenter/centerPzManage";
    import OnePzManage from "@/views/bankAssessment/setCenter/pzCenter/onePzManage";
    export default {
        name: 'pzCenter',
        components: { staffManager, TwoPzManage, CenterPzManage, OnePzManage },
        data() {
            return {
                showCon: 1
            }
        },
        mounted() {

        },
        methods: {
            // 点击tag
            cliTag(v) {
                this.showCon = v;
            }
        }
    }
</script>
<style scoped>
    .pzzx-container {
        padding: 15px;
    }

    .pzzx-head {
        padding-bottom: 15px;
    }

    .buttFous {
        color: #409EFF;
        border-color: #c6e2ff;
        background-color: #ecf5ff;
    }
</style>
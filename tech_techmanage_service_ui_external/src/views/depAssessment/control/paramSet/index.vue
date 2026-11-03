<!-- 配置中心-聚合页 -->
<template>
    <div class="paramSet-container">
        <div class="paramSet-head">
            <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}">考核层级设置</el-button>
            <el-button @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}">档位评分设置</el-button>
        </div>
        <div class="paramSet-body">
            <dep-item-grade-manage v-if="showCon == 1"></dep-item-grade-manage>
            <dep-assessment-level-manage v-if="showCon == 2"></dep-assessment-level-manage>
        </div>
    </div>
</template>
<script>
    import DepItemGradeManage from "@/views/depAssessment/control/paramSet/depItemGradeManage";
    import DepAssessmentLevelManage from "@/views/depAssessment/control/paramSet/depAssessmentLevelManage";
    export default {
        name: 'paramSetCenter',
        components: { DepItemGradeManage, DepAssessmentLevelManage },
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
    .paramSet-container {
        padding: 15px;
    }

    .paramSet-head {
        padding-bottom: 15px;
    }

    .buttFous {
        color: #409EFF;
        border-color: #c6e2ff;
        background-color: #ecf5ff;
    }
</style>
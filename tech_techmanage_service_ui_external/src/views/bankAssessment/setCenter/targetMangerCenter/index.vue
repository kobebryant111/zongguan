<!-- 指标管理中心-聚合页 -->
<template>
    <div class="target-container">
        <div class="target-body">
            <el-tabs v-model="activeTabName" type="border-card">
                <el-tab-pane label="KPI指标配置" name="kpizb">
                    <kpi-target-set></kpi-target-set>
                </el-tab-pane>
                <el-tab-pane label="综合能力评估项配置" name="zhnl">
                    <zhnl-assess-set></zhnl-assess-set>
                </el-tab-pane>
                <el-tab-pane label="履职评估项配置" name="lzpg">
                    <lzpg-assess-set></lzpg-assess-set>
                </el-tab-pane>
                <el-tab-pane label="平衡计分项配置" name="phjf">
                        <phjf-assess-set></phjf-assess-set>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>
<script>
    import kpiTargetSet from "@/views/bankAssessment/setCenter/targetMangerCenter/kpiTargetSet";
    import zhnlAssessSet from "@/views/bankAssessment/setCenter/targetMangerCenter/zhnlAssessSet";
    import lzpgAssessSet from "@/views/bankAssessment/setCenter/targetMangerCenter/lzpgAssessSet";
    import phjfAssessSet from "@/views/bankAssessment/setCenter/targetMangerCenter/phjfAssessSet";
    export default {
        name: 'targetMangerCenter',
        components: { kpiTargetSet, zhnlAssessSet, lzpgAssessSet, phjfAssessSet },
        data() {
            return {
                activeTabName: 'kpizb', //默认tab
            }
        },
        mounted() {
        },
        methods: {

        }
    }
</script>
<style scoped>
    .target-container {
        padding: 15px;
    }
</style>
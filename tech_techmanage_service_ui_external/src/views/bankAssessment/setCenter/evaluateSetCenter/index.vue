<!-- 评价设置中心-聚合页 -->
<template>
    <div class="szzx-container">
        <pjsz-center></pjsz-center>
        <!-- <el-tabs type="border-card">
            <el-tab-pane label="评价设置中心">
                <pjsz-center></pjsz-center>
            </el-tab-pane>
            <el-tab-pane label="评级设置中心">
                <pingji-set></pingji-set>
            </el-tab-pane>
        </el-tabs> -->
    </div>
</template>
<script>
    import pjszCenter from "@/views/bankAssessment/setCenter/evaluateSetCenter/pjszCenter";
    import pingjiSet from "@/views/bankAssessment/setCenter/evaluateSetCenter/pingjiSet";
    export default {
        name: 'evaluateSetCenter',
        components: { pjszCenter, pingjiSet },
        data() {
            return {

            }
        },
        mounted() {
        },
        methods: {

        }
    }
</script>
<style scoped>
    .szzx-container {
        padding: 15px;
    }
</style>
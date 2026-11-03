<!-- 评级设置 -->
<template>
    <div class="grade-container">
        <div class="grade-head">
            <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}">级别设置</el-button>
            <el-button @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}">一级部</el-button>
            <el-button @click="cliTag(3)" :class="{ 'buttFous': showCon == 3}">中 心</el-button>
            <el-button @click="cliTag(4)" :class="{ 'buttFous': showCon == 4}">二级部</el-button>
        </div>
        <div class="grade-body">
            <transition name="el-fade-in">
                <level-set v-if="showCon == 1"></level-set>
                <class-a v-else-if="showCon == 2"></class-a>
                <center-set v-else-if="showCon == 3"></center-set>
                <class-b v-else-if="showCon == 4"></class-b>
            </transition>
        </div>
    </div>
</template>
<script>
    import LevelSet from "@/views/bankAssessment/setCenter/pingjiSetCenter/levelSet";
    import ClassA from "@/views/bankAssessment/setCenter/pingjiSetCenter/classA";
    import centerSet from "@/views/bankAssessment/setCenter/pingjiSetCenter/centerSet";
    import ClassB from "@/views/bankAssessment/setCenter/pingjiSetCenter/classB";
    export default {
        name: 'gradeSet',
        components: { LevelSet, ClassA, centerSet, ClassB },
        data() {
            return {
                showCon: 1
            }
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
    .grade-container {
        padding: 15px;
    }

    .grade-head {
        padding-bottom: 15px;
    }

    .buttFous {
        /* color: #409EFF;
        border-color: #c6e2ff;
        background-color: #ecf5ff; */
        color: #ffffff;
        background-color: rgba(251, 164, 47, 1);
        border-color: rgba(251, 164, 47, 1);
    }
</style>
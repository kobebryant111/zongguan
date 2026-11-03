<!-- 评级配置-聚合页 -->
<template>
    <div class="pzzx-container">
        <div class="pzzx-head">
            <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}">级别设置</el-button>
            <el-button v-if="sign == 2" @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}">部门总数</el-button>
            <el-button v-if="sign == 1" @click="cliTag(3)" :class="{ 'buttFous': showCon == 3}">中心内部门数量</el-button>
        </div>
        <div class="pzzx-body">
            <gradeSet v-if="showCon == 1" :sign="sign"></gradeSet>
            <depNum v-if="showCon == 2 && sign == 2" :sign="sign"></depNum>
            <centerNum v-if="showCon == 3 && sign == 1" :sign="sign"></centerNum>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import gradeSet from "@/views/depAssessment/grade/gradeSet";
    import depNum from "@/views/depAssessment/grade/depNum";
    import centerNum from "@/views/depAssessment/grade/centerNum";
    export default {
        name: 'index',
        components: { gradeSet, depNum, centerNum },
        data() {
            return {
                showCon: 1, // 当前选择
                sign: null, // 部门级别
            }
        },
        created(){
            if(this.$route.name == "ejbGrade"){
                this.sign = 1;
            }else if(this.$route.name == "zxGrade"){
                this.sign = 2;
            }
        },
        mounted() {},
        methods: {
            // 点击tag
            cliTag(v) {
                this.showCon = v;
            },
        }
    }
</script>
<style scoped>
    .pzzx-container {
        padding: 15px;
    }

    .pzzx-head {
        padding-bottom: 15px;
        display: flex;
        overflow-x: auto;
    }

    .buttFous {
        color: #409EFF;
        border-color: #c6e2ff;
        background-color: #ecf5ff;
    }
</style>
<!-- 分管总评级 -->
<template>
    <div class="general-container">
        <div class="ppp-head">
            <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}">员工评级</el-button>
            <el-button @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}">经理评级</el-button>
        </div>
        <div class="ppp-body">
            <general-staff v-if="showCon == 1"></general-staff>
            <general-manage v-else-if="showCon == 2"></general-manage>
        </div>
    </div>
</template>
<script>
    import GeneralStaff from '@/views/bankAssessment/dbYearKhpj/general/generalStaff'
    import GeneralManage from '@/views/bankAssessment/dbYearKhpj/general/generalManage'
    export default {
        name: 'generalManagerKhpj',
        components: { GeneralStaff, GeneralManage },
        data() {
            return {
                showCon: 1,
            }
        },
        mounted() {

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
    .ppp-head{
        padding: 10px 15px 0 15px;
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
</style>
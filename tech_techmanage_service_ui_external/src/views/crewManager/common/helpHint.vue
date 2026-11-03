<!-- 发起需求人员招聘和发起面试简历提示 -->
<template>
    <div>
        <span>
            <slot>{{name}}</slot>
        </span>
       
        <el-tooltip :content="content" :placement="placement">
            <i class="el-icon-question" style="cursor: pointer;margin-left: 6px;"></i>
        </el-tooltip>
    </div>

</template>
<script>
    export default {
        data() {
            return {

            }
        },
        props: {
            placement: {
                default: 'top'
            },
            content: String,
            name: String
        },
        mounted() {

        }
    }
</script>
<style scoped>

</style>
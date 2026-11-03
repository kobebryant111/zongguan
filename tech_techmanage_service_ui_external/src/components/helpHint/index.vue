<!-- 表格提示组件 -->
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
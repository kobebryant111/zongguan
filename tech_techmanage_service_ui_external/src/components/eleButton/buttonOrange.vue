<template>
    <div class="buttOrange">
        <el-button type="warning" :size="size" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
    </div>
</template>
<script>
    export default {
        props: {
            size: { //按钮尺寸
                type: String,
                default : 'medium'
            },
            disabled: { //是否可点击
                type: Boolean
            },
            buttonName: { //按钮名字
                type: String
            },
            fns: {
                type: Function
            }
        },
        data() {
            return {

            }
        },
        methods: {
            clickEve() {
                this.fns();
            }
        }
    }
</script>

<style scoped>
    .buttOrange {
        display: inline-block;
    }
</style>

<!-- element样式修改 -->
<style>
    .buttOrange .el-button:focus {
        color: #ffffff;
        background-color: #ebb563;
        border-color: #ebb563;
    }

    .buttOrange .el-button:hover {
        color: #ffffff;
        background-color: #ebb563;
        border-color: #ebb563;
    }

    .buttOrange .el-button {
        background-color: rgba(251, 164, 47, 1);
        border-color: rgba(251, 164, 47, 1);
    }
</style>
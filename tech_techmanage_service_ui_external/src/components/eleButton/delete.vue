<template>
    <div class="buttDele">
        <el-button :size="size" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
    </div>
</template>
<script>
    export default {
        props: {
            size: { //按钮尺寸
                type: String
            },
            buttonName: { //按钮名字
                type: String
            },
            disabled: { //是否可点击
                type: Boolean
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
    .buttDele {
        display: inline-block;
    }
</style>

<!-- element样式修改 -->
<style>
    /* .buttDele .el-button:focus {
        color: #ff4d4f;
        border-color: #ff4d4f;
    }

    .buttDele .el-button:hover {
        color: #f78989;
        border-color: #f78989;
    } */

     .buttDele .el-button {
        color: #ff4d4f;
        border-color: #ff4d4f;
    }
</style>
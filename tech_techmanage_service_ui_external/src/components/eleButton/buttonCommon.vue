<template>
    <div class="buttCommon">
        <el-button :size="size" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
    </div>
</template>
<script>
    export default {
        props: {
            size: { //按钮尺寸
                type: String,
                default:'medium'
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
    .buttCommon {
        display: inline-block;
    }
</style>

<!-- element样式修改 -->
<style>
    .buttCommon .el-button:focus {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .buttCommon .el-button:hover {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }
</style>
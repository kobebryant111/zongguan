<template>
    <div class="butt-fuscia">
        <el-button size="small" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
    </div>
</template>
<script>
    export default {
        props: {
            size: { //按钮尺寸
                type: String
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
    .butt-fuscia {
        display: inline-block;
    }
</style>

<!-- element样式修改 -->
<style>
    .butt-fuscia .el-button {
        border-width: 0px;
        width: 82px;
        height: 33px;
        background: inherit;
        background-color: rgba(253, 57, 122, 1);
        border: none;
        border-radius: 5px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        font-size: 12px;
        color: #FFFFFF;
    }

    .butt-fuscia .el-button:focus {
        background-color: rgba(255, 105, 180);
        border-color: #FFF0F5;
    }

    .butt-fuscia .el-button:hover {
        background-color: rgba(255, 105, 180);
        border-color: #FFF0F5;
    }
</style>
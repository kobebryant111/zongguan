<template>
    <div class="butt-oranges">
        <el-button class="butt-oranges-small" v-if="size == 'small'" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
        <el-button class="butt-oranges-mini" v-if="size == 'mini'" size="mini" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
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
            },
            parentData: {
                
            }
        },
        data() {
            return {

            }
        },
        methods: {
            clickEve() {
                this.fns(this.parentData);
            }
        }
    }
</script>

<style scoped>
    .butt-oranges {
        display: inline-block;
    }
</style>

<!-- element样式修改 -->
<style>
    .butt-oranges-small {
        border-width: 0px;
        width: 82px;
        height: 33px;
        background: inherit;
        background-color: rgba(255, 184, 34, 1);
        border: none;
        border-radius: 5px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        font-size: 12px;
        color: #FFFFFF;
    }

    .butt-oranges .el-button:focus {
        background-color: #ebb563;
        border-color: #FFF0F5;
        color: #FFFFFF;
    }

    .butt-oranges .el-button:hover {
        background-color: #ebb563;
        border-color: #FFF0F5;
        color: #FFFFFF;
    }

    .butt-oranges-mini {
        border-width: 0px;
        width: 56px;
        height: 26px;
        background: inherit;
        background-color: rgba(255, 184, 34, 1);
        border: none;
        border-radius: 5px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        font-size: 12px;
        color: #FFFFFF;
    }
</style>
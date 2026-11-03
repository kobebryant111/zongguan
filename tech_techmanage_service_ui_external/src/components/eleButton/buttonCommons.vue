<template>
    <div class="butt-commons">
        <el-button class="butt-commons-small" v-if="size == 'small'" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
        <el-button class="butt-commons-mini" v-if="size == 'mini'" size="mini" @click="clickEve" :disabled="disabled">{{buttonName}}</el-button>
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
    .butt-commons {
        display: inline-block;
    }
</style>

<!-- element样式修改 -->
<style>
    .butt-commons-small {
        border-width: 0px;
        width: 82px;
        height: 33px;
        background: inherit;
        background-color: rgba(253, 253, 253, 1);
        box-sizing: border-box;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(238, 229, 255, 1);
        border-radius: 5px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        font-size: 12px;
        color: #919191;
    }

    .butt-commons .el-button:focus {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .butt-commons .el-button:hover {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .butt-commons-mini {
        border-width: 0px;
        /* width: 56px; */
        height: 27px;
        background: inherit;
        background-color: rgba(253, 253, 253, 1);
        box-sizing: border-box;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(238, 229, 255, 1);
        border-radius: 5px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        font-size: 12px;
        color: #919191;
    }
</style>
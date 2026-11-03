<!-- 懒加载树通用控件 -->
<template>
    <div class="lazy-tree">
        <el-input v-model="inputContent" :disabled="true">
            <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button>
        </el-input>
        <el-dialog :title="titleName" :visible.sync="treeDialog" class="treeDialogCon">
            <el-scrollbar style="height:100%">
                <div class="tree-container">
                    <el-tree :data="treeData" :props="treeProps" lazy :load="loadNode" ref="tree" @node-click="treeClick"
                        :node-key="nodeKey" :highlight-current="true" :filter-node-method="filterNode"
                        :default-expand-all="defaultExpandAll"></el-tree>
                </div>
            </el-scrollbar>
            <div class="dia-bottom">
                <el-row :gutter="10" type="flex" justify="end">
                    <el-col :span="1.5">
                        <button-common :buttonName="buttonCancel" :size="size" :fns="cancelSel"></button-common>
                    </el-col>
                    <el-col :span="1.5">
                        <button-orange :buttonName="buttonSure" :size="size" :fns="sureSel"></button-orange>
                    </el-col>
                </el-row>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    export default {
        name: 'lazyTree',
        components: { buttonOrange, buttonCommon },
        data() {
            return {
                inputContent: this.input,
                treeDialog: false,
                inputContentTem: '', //输入框临时数据
                buttonCancel: '取 消',
                buttonSure: '确 定',
                size: 'mini',  //按钮大小
            }
        },
        props: {
            input: { //输入框内容
                type: String
            },
            disabled: { //是否可点击
                type: Boolean
            },
            titleName: { //弹窗名字
                type: String
            },
            treeData: { //tree数据
                type: [Array, Object],
                required: true
            },
            nodeKey: { //节点唯一标识
                type: String,
                required: true
            },
            treeProps: { //配置信息
                type: Object,
                required: true
            },
            loadNode: { //懒加载方法
                type: Function
            },
            filterNode: { //过滤方法
                type: Function
            },
            treeClick: {
                type: Function
            },
            defaultExpandAll: { //是否展开全部节点
                type: Boolean,
                default: () => false
            },
        },
        methods: {
            // 显示弹窗
            showDialog() {
                this.treeDialog = true;
            },
            //点击节点
            getChildrenNode(val) {
                console.log('子节点信息', val);
                this.inputContentTem = val;
            },

            // 取消
            cancelSel() {
                this.treeDialog = false;
                this.inputContentTem = '';
            },

            // 确认
            sureSel() {
                this.inputContent = this.inputContentTem;
                this.inputContentTem = '';
                this.treeDialog = false;
            }
        }
    }
</script>
<style scoped>
    .tree-container {
        /* min-height: 600px;
        max-height: 600px; */
        height: 400px;
        padding-top: 10px;
    }

    .dia-bottom {
        margin-top: 10px;
    }
</style>
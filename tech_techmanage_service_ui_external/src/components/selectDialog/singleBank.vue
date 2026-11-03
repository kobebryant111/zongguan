<!-- 选择全部行员懒加载树通用控件 -->
<template>
    <div class="lazy-tree">
        <el-input v-model="inputContent" :disabled="true">
            <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button>
        </el-input>
        <el-dialog :title="titleName" :visible.sync="treeDialog" class="treeDialogCon">
            <div>
                <el-row :gutter="10">
                    <el-col :span="8">
                        <el-input v-model="searchUser" placeholder="员工号/员工名称/员工名称拼音"></el-input>
                    </el-col>
                    <el-col :span="2">
                        <div class="butt-search">
                            <button-orange :buttonName="buttonSearch" size="small" :fns="searchUserBtn"></button-orange>
                        </div>
                    </el-col>
                </el-row>
            </div>
            <el-scrollbar style="height:100%">
                <div class="tree-container">
                    <el-tree :data="treeData" :props="treeProps" lazy :load="loadNode" ref="tree" @node-click="getChildrenNode"
                        node-key="id" :highlight-current="true" :filter-node-method="filterNode" :default-expand-all="false"></el-tree>
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
    import buttonOrange from '@/components/eleButton/buttonOrange'
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import { userTreeList } from "@/api/systemApi/relateUser";
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
                titleName: 'XX银行',
                treeData: [], //树数据
                treeProps: {
                    children: 'children',
                    label: 'text',
                    isLeaf: 'isLeaf'
                }, //配置信息
                treeNodeData: {}, //点击的树节点数据
                searchUser: "", // 用户树搜索用户的条件
                buttonSearch: '搜索'
            }
        },
        props: {
            input: { //输入框内容
                type: String
            },
            disabled: { //是否可点击
                type: Boolean
            },
            fns: {
                type: Function
            },
            filterNode: { //过滤方法
                type: Function
            }
        },
        mounted() {
            this.loadDefaultNode()
        },
        methods: {
            // 显示弹窗
            showDialog() {
                this.treeDialog = true;
            },

            // 加载默认节点
            loadDefaultNode() {
                userTreeList().then(res => {
                    this.treeData = res.data.results;
                    console.log("treeData:", this.treeData);
                });
            }, // end

            // 机构树懒加载
            loadNode(node, resolve) {
                if (node.level === 0) {
                    return resolve(node.data);
                }
                if (node.level === 1) {
                    if (node.data.children != undefined) {
                        return resolve(node.data.children);
                    } else {
                        return resolve([]);
                    }
                }
                if (node.level > 1) {
                    this.getCurrentDepPerson(node, resolve);
                }
            },

            // 懒加载子级节点
            getCurrentDepPerson(node, resolve) {
                let params = node.data.attr.split("-");
                console.log("params", params);
                if (params.length < 3) {
                    resolve([]);
                    return false;
                }
                let data = {};
                data.id = params[0];
                data.current = params[1];
                data.next = params[2];
                userTreeList(data).then(res => {
                    if (res.data.status == 200) {
                        let data = res.data.results;
                        data.forEach(item => {
                            item.text = item.text;
                            if (item.attr == 'emp') {
                                item.isLeaf = true;
                            }
                        })
                        resolve(data);
                    } else {
                        // this.$message.error('加载人员失败');
                        resolve([])
                    }
                });
            },

            // 搜索用户树
            searchUserBtn() {
                let self = this;
                // if (self.searchUser == null || self.searchUser.length <= 0) {
                //     self.$message.error("搜索条件不能为空");
                //     return false;
                // }
                userTreeList({ searchUser: self.searchUser }).then(res => {
                    if (res.data.status !== 200) {
                        self.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    self.treeData = res.data.results;
                });
            }, // end

            //点击节点
            getChildrenNode(val) {
                console.log('子节点信息', val);
                this.treeNodeData = val;
                this.inputContentTem = val.text;
            },

            // 取消
            cancelSel() {
                this.treeDialog = false;
                this.inputContentTem = '';
                this.treeNodeData = '';
            },

            // 确认
            sureSel() {
                this.fns(this.treeNodeData);
                this.inputContent = this.inputContentTem.split('[')[0];
                this.inputContentTem = '';
                this.treeNodeData = '';
                this.treeDialog = false;
            }
        }
    }
</script>
<style scoped>
    .tree-container {
        height: 400px;
        padding-top: 10px;
    }

    .dia-bottom {
        margin-top: 10px;
    }

    .butt-search {
        height: 35px;
        line-height: 35px;
    }
</style>
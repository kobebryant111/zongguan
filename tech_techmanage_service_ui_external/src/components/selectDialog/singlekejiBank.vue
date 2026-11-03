<!-- 单选金融科技部行员 -->
<template>
    <div class="single-choice">
        <el-input v-model="inputContent" :disabled="true">
            <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button>
        </el-input>
        <el-dialog :title="titleName" :visible.sync="flDialog" class="flDialogCon">
            <div class="flDialogBody">
                <div class="flDialogBody_title">请选择人员</div>
                <div class="flDialogBody_con">
                    <el-row>
                        <el-col :span="12">
                            <div class="flDialogBody_con_left">
                                <div class="flDialogBody_con_left_input">
                                    <el-input placeholder="搜索名称" v-model="filterText" prefix-icon="el-icon-search"
                                        class="searchText"></el-input>
                                </div>
                                <el-scrollbar style="height:100%">
                                    <div class="flDialogBody_con_tree">
                                        <el-tree ref="tree" :data="treeData" :props="treeProps" node-key="id"
                                            :default-expand-all="false" @current-change="getCurrentNode"
                                            :filter-node-method="filterNode" highlight-current />
                                    </div>
                                </el-scrollbar>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="flDialogBody_con_right">
                                <div class="flDialogBody_con_right_title">
                                    已选
                                </div>
                                <el-scrollbar style="height:100%">
                                    <div class="flDialogBody_con_right_main" v-if="ifwbtcRight">
                                        <ul class="flDialogBody_ul">
                                            <div class="flList">
                                                <div>
                                                    <li class="flDialogBody_ul_up">姓名：{{treeNodeData.text}}</li>
                                                    <li class="flDialogBody_ul_low">部门编号：{{treeNodeData.pid}}</li>
                                                </div>
                                            </div>
                                        </ul>
                                    </div>
                                </el-scrollbar>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div class="wrapper_bottom">
                    <el-row :gutter="10" type="flex" justify="end">
                        <el-col :span="1.5">
                            <button-common :buttonName="buttonCancel" :size="size" :fns="flCancel"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange :buttonName="buttonSure" :size="size" :fns="flSure"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange'
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
    export default {
        name: 'singlekejiBank',
        components: { buttonOrange, buttonCommon },
        data() {
            return {
                inputContent: this.input,
                flDialog: false, //是否选择变更人员弹窗
                size: 'mini',  //按钮大小
                buttonCancel: '取 消',
                buttonSure: '确 定',
                groupid_options: [],
                filterText: '', //过滤机构树
                treeNodeData: {}, //点击的树节点数据
                ifwbtcRight: false, //是否显示弹窗右边
                treeData: [], //树数据
                titleName: '金融科技部', //大标题名字
                treeProps: {
                    children: 'children',
                    label: 'text'
                }, //配置信息
            }
        },
        props: {
            input: { //输入框内容
                type: String
            },
            depno: { //部门编号
                type: String,
                required: true
            },
            fns: {
                type: Function
            },
        },
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        mounted() {
            this.getAppList();
        },
        methods: {
            // 弹窗显示
            showDialog() {
                this.flDialog = true;
            },

            //弹窗确认
            flSure() {
                this.inputContent = this.treeNodeData.text;
                this.fns(this.treeNodeData);
                // this.treeNodeData = '';
                this.flDialog = false;
            },

            //弹窗取消
            flCancel() {
                // this.treeNodeData = '';
                this.flDialog = false;
            },

            //选中节点变化时触发的事件
            getCurrentNode: function (node) {
                console.log('节点信息', node);
                if (node.children != undefined) {
                    return false;
                } else {
                    this.ifwbtcRight = true;
                    this.treeNodeData = node;
                }
            },

            // 机构树过滤
            filterNode(value, data) {
                if (!value) return true;
                return data.text.indexOf(value) !== -1;
            },

            //获取金融科技部行员列表
            getAppList() {
                // let params = {};
                // params = "7004"; //130是总行科技部部门编号
                queryEmployeeTree(this.depno)
                    .then(res => {
                        this.treeData = res.data.results[0].children.filter(li => {
                            li.text = li.text.replace('总行金融科技部-', '');
                            return li;
                        });
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });  // end
            },
        }
    }
</script>

<style scoped>
    .flDialogBody_con {
        border: 1px solid #d9d9d9;
        margin: 15px 0;
    }

    .flDialogBody_con_left {
        border-right: 1px solid #d9d9d9;
        padding: 10px 0 10px 10px;
    }

    .flDialogBody_con_left_input {
        padding-right: 10px;
    }

    .flDialogBody_con_right_title {
        font-size: 16px;
        font-weight: 550;
        color: #303133;
    }

    .flDialogBody_con_right_title span {
        margin: 0 5px;
    }

    .flDialogBody_con_tree {
        min-height: 300px;
        max-height: 300px;
        padding-top: 10px;
    }

    .flDialogBody_con_right_main {
        min-height: 310px;
        max-height: 310px;
        /* padding-top: 5px; */
    }

    .flDialogBody_con_right {
        padding: 18px 0 0 15px;
    }

    .flDialogBody_ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
    }

    .flDialogBody_ul div {
        margin-top: 8px;
    }

    .flDialogBody_ul_up {
        color: #303133;
        font-size: 14px;
        font-weight: 400;
    }

    .flDialogBody_ul_low {
        color: #C0C4CC;
        font-size: 14px;
        font-weight: 400;
        margin-top: 10px;
    }

    .flList {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-right: 10px;
    }

    .el-icon-circle-close {
        cursor: pointer;
    }
</style>

<style>
    .flDialogCon .el-dialog__body {
        padding: 0 20px 10px 20px;
    }

    .single-choice .el-input.is-disabled .el-input__inner {
        background-color: transparent;
    }
</style>
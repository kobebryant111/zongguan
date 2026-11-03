<!-- 关联项目树 -->
<template>
    <div class="single-choice">
        <el-input v-model="inputContent" :disabled="true" :size="size">
            <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button>
        </el-input>
        <el-dialog :title="titleName" :visible.sync="flDialog" width="30%" append-to-body>
            <el-row>
                <el-col>
                    <div class="flDialogBody_con_left_input">
                        <el-input placeholder="搜索名称" v-model="filterText" prefix-icon="el-icon-search" class="searchText"></el-input>
                    </div>
                    <el-scrollbar style="height:100%">
                        <div class="flDialogBody_con_tree">
                            <el-tree ref="tree" :data="treeData" :props="treeProps" node-key="id" :default-expand-all="false" @node-click="treeClick" :filter-node-method="filterNode" highlight-current :default-expanded-keys="expandedKeys" />
                        </div>
                    </el-scrollbar>
                </el-col>
            </el-row>
        </el-dialog>
    </div>
</template>
<script>
import common from '@/api/common'
import { queryRelaProgramTree } from '@/api/resource/added/resourceManager'
export default {
    props: {
        size: {
            type: String
        }
    },
    data() {
        return {
            inputContent: this.input,
            flDialog: false, //是否选择变更人员弹窗
            filterText: '', //过滤机构树
            treeData: [], //树数据
            titleName: '关联项目', //大标题名字
            treeProps: {
                label: 'text',
                children: 'children'
            }, //配置信息
            expandedKeys: ['org1']
        }
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val)
        }
    },
    mounted() {
        this.packageInit()
    },
    methods: {
        // 弹窗显示
        showDialog() {
            this.flDialog = true
        },
        treeClick(node, nodeArr, currentEle) {
            if (node.pid != '-1') {
                this.inputContent = node.text
                this.flDialog = false
                let treeNodeData = node //选中的树节点数据
                this.$emit('change', treeNodeData, 'proTree')
            }
        },
        // 树过滤
        filterNode(value, data) {
            if (!value) return true
            let lx = typeof data.text
            if (lx == 'string') {
                return data.text.indexOf(value) !== -1
            }
        },
        packageInit: function () {
            this.init()
        },
        init() {
            queryRelaProgramTree()
                .then((res) => {
                    if (res.data.status == 200) {
                        this.treeData = res.data.results
                    } else {
                        this.$message.error('获取关联系统失败')
                    }
                })
                .catch((error) => {
                    this.$message.error(error)
                })
        },
        clearValue(val){
            this.inputContent = val;
        }
    }
}
</script>

<style scoped>
.flDialogBody_con_left_input {
    padding-right: 10px;
}

.flDialogBody_con_tree {
    min-height: 300px;
    max-height: 300px;
    padding-top: 10px;
}
</style>

<style>
.single-choice .el-input.is-disabled .el-input__inner {
    background-color: transparent;
}
</style>
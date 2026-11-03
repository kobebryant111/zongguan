<!-- 关联系统树 -->
<template>
    <div class="single-choice">
        <el-form ref="form" inline :model="form" v-model="form" :disabled="disabled">
            <el-form-item prop="inputContent">
                <el-input v-model="inputContent" readonly :size="size">
                    <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button>
                </el-input>
            </el-form-item>
        </el-form>
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
import { queryRelaSysTree, getRelaSysData } from '@/api/resource/added/resourceManager'
export default {
    props: {
        size: {
            type: String
        },
        disabled: {
            type: Boolean
        }
    },
    data() {
        return {
            form: {
                inputContent: ''
            },
            inputContent: this.input,
            flDialog: false, //是否选择变更人员弹窗
            filterText: '', //过滤机构树
            treeData: [], //树数据
            titleName: '关联系统', //大标题名字
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
            if (node.attr == 'sys') {
                this.inputContent = node.text
                let param = {}
                param.pcode = node.pcode
                let data = common.paramsFormat(param)
                getRelaSysData(data).then((res) => {
                    if (res.data.status == 200) {
                        let treeNodeData = node //选中的树节点数据
                        treeNodeData.tecDep = res.data.results[0].TEC_DEP == null ? '' : res.data.results[0].TEC_DEP
                        treeNodeData.tecDepName = res.data.results[0].TEC_DEP_NAME == null ? '' : res.data.results[0].TEC_DEP_NAME
                        treeNodeData.busDep = res.data.results[0].BUS_DEP == null ? '' : res.data.results[0].BUS_DEP
                        treeNodeData.busDepName = res.data.results[0].BUS_DEP_NAME == null ? '' : res.data.results[0].BUS_DEP_NAME
                        treeNodeData.omLevel = res.data.results[0].OM_LEVEL == null ? '' : res.data.results[0].OM_LEVEL
                        treeNodeData.refMoney = res.data.results[0].REFMONEY = null ? '' : res.data.results[0].REFMONEY
                        treeNodeData.ciid = res.data.results[0].CIID = null ? '' : res.data.results[0].CIID
                        this.$emit('change', treeNodeData, 'sysTree')
                        console.log('此系统所属的业务部门：' + treeNodeData.busDepName + '(' + treeNodeData.busDep + ')，' + '技术部门：' + treeNodeData.tecDepName + '(' + treeNodeData.tecDep + ')')
                    }
                })
                this.flDialog = false
            }
        },
        // 机构树过滤
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
            queryRelaSysTree()
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
        clearValue(val) {
            this.inputContent = val
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
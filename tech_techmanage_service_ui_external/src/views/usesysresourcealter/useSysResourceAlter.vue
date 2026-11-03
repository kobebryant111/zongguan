<!-- 流程通知管理 -->
<template>
    <div class="app-container">
        <use-sys-res-alter-flow-db v-if="auditNodeState ==1" ref="useSysResAlterFlowDb"></use-sys-res-alter-flow-db>
        <use-sys-res-alter-flow-yb v-if="auditNodeState ==2" ref="useSysResAlterFlowYb"></use-sys-res-alter-flow-yb>
        <use-sys-res-alter-flow-edit v-if="auditNodeState ==3" ref="useSysResAlterFlowEdit"></use-sys-res-alter-flow-edit>
    </div>
</template>
<script>
import common from '@/api/common'
import useSysResAlterFlowEdit from '@/views/usesysresourcealter/useSysResAlterFlowEdit'
import useSysResAlterFlowDb from '@/views/usesysresourcealter/useSysResAlterFlowDb'
import useSysResAlterFlowYb from '@/views/usesysresourcealter/useSysResAlterFlowYb'
export default {
    components: { useSysResAlterFlowDb, useSysResAlterFlowYb, useSysResAlterFlowEdit },
    data() {
        return {
            auditNodeState: '', // 节点状态
            id: '',
            flowItem: {} //流程相关信息
        }
    },
    created() {},
    mounted() {
        this.flowItem = this.$route.params
        // 应用系统资源变更
        this.useSysResourceAlterFlow()
    },
    methods: {
        useSysResourceAlterFlow() {
            if (this.flowItem.status == 'db') {
                if (this.flowItem.activityDefID == 'manualActivity') {
                    this.flowItem.actionURL = 'useSysResAlterFlowEdit'
                } else {
                    this.flowItem.actionURL = 'useSysResAlterFlowDb'
                }
                this.$nextTick((_) => { 
                    this.showNodeAudit(this.flowItem)
                })
            } else if (this.flowItem.status == 'yb') {
                this.flowItem.actionURL = 'useSysResAlterFlowYb'
                //this.flowItem.activityDefID = 'useSysResAlterFlowYb'
                this.$nextTick((_) => { 
                    this.showNodeAudit(this.flowItem)
                })
            }
        },
        /**
         * 显示对应节点的审批内容
         */
        showNodeAudit: function (item) {
            let url = item.actionURL
            let self = this
            const flowMap = new Map([
                [
                    { name: 'useSysResAlterFlowDb' },
                    () => {
                        self.auditNodeState = 1
                        this.$nextTick((_) => {
                            this.$refs.useSysResAlterFlowDb.init(item)
                        })
                    }
                ],
                [
                    { name: 'useSysResAlterFlowYb' },
                    () => {
                        self.auditNodeState = 2
                        this.$nextTick((_) => {
                            this.$refs.useSysResAlterFlowYb.init(item)
                        })
                    }
                ],
                [
                    { name: 'useSysResAlterFlowEdit' },
                    () => {
                        self.auditNodeState = 3
                        this.$nextTick((_) => {
                            this.$refs.useSysResAlterFlowEdit.init(item)
                        })
                    }
                ]
            ])

            const shwoFlowNode = (name) => {
                ;[...flowMap].forEach(([key, val]) => {
                    name.indexOf(key.name) != -1 ? val.call() : ''
                })
            }

            shwoFlowNode(url)
        }
    }
}
</script>
<style lang="scss">
.el-form-item {
    margin-bottom: 5px;
}
</style>
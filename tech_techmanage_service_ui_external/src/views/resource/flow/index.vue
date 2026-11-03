<!-- 流程通知管理 -->
<template>
    <div class="app-container">
        <resource-expand-flow ref="resourceExpandFlow" v-if="sourceType=='1'  && auditNodeState ==-1 " :sourceType="sourceType" :busId="id" :workItemId="flowItem.workItemID" :processInstId="flowItem.rootProcInstID" :applydesc="flowItem.workItemName" :activityDefID="flowItem.activityDefID"></resource-expand-flow>
        <resource-expand-edit ref="resourceExpandEdit" v-if="sourceType=='1' && auditNodeState ==2 " :sourceType="sourceType" :busId="id" :workItemId="flowItem.workItemID" :processInstId="flowItem.rootProcInstID" :applydesc="flowItem.workItemName" :activityDefID="flowItem.activityDefID"></resource-expand-edit>
        <resource-expand-flow-yb ref="resourceExpandFlowYb" v-if="sourceType=='1'  && auditNodeState ==1 " :sourceType="sourceType" :busId="id" :workItemId="flowItem.workItemID" :processInstId="flowItem.rootProcInstID" :applydesc="flowItem.workItemName" :activityDefID="flowItem.activityDefID"></resource-expand-flow-yb>
        <resource-add-flow ref="resourceAddFlow" v-if="sourceType=='0' && auditNodeState ==-1 " :sourceType="sourceType" :busId="id" :workItemId="flowItem.workItemID" :processInstId="flowItem.rootProcInstID" :applydesc="flowItem.workItemName" :activityDefID="flowItem.activityDefID"></resource-add-flow>
        <resource-add-edit ref="resourceAddEdit" v-if="sourceType=='0' && auditNodeState ==2  " :sourceType="sourceType" :busId="id" :workItemId="flowItem.workItemID" :processInstId="flowItem.rootProcInstID" :applydesc="flowItem.workItemName" :activityDefID="flowItem.activityDefID"></resource-add-edit>
        <resource-add-flow-yb ref="resourceAddFlowYb" v-if="sourceType=='0' && auditNodeState ==1 " :sourceType="sourceType" :busId="id" :workItemId="flowItem.workItemID" :processInstId="flowItem.rootProcInstID" :applydesc="flowItem.workItemName" :activityDefID="flowItem.activityDefID"></resource-add-flow-yb>

    </div>
</template>
<script>
import common from '@/api/common'
import resourceExpandFlow from '@/views/resource/flow/resourceExpandFlow'
import resourceExpandEdit from '@/views/resource/flow/resourceExpandEdit'
import resourceExpandFlowYb from '@/views/resource/flow/resourceExpandFlowYb'
import resourceAddFlow from '@/views/resource/flow/resourceAddFlow'
import resourceAddFlowYb from '@/views/resource/flow/resourceAddFlowYb'
import resourceAddEdit from '@/views/resource/flow/resourceAddEdit'
import { queryBusInfoByProcessInstId } from '@/api/resource/expand/resourceExpand'
export default {
    components: { resourceExpandFlow, resourceExpandEdit, resourceAddFlow, resourceAddEdit, resourceAddFlowYb, resourceExpandFlowYb },
    data() {
        return {
            auditNodeState: '', // 节点状态
            sourceType: '',
            id: '',
            flowItem: {} //流程相关信息
        }
    },
    created() {},
    mounted() {
        console.log(this.$route.params)
        this.flowItem = this.$route.params
        //根据流程获取申请明细，判断是资源申请还是资源扩容
        let param = {}

        param.processInstId = this.flowItem.processInstID
        let data = common.paramsFormat(param)
        queryBusInfoByProcessInstId(data).then((res) => {
            if (res.data.status != 200) {
                this.$message.error(res.data.message)
                return false
            } else {
                //类型
                console.log(res.data.results)
                this.sourceType = res.data.results.sourceType
                //主键
                this.id = res.data.results.id
                if (this.sourceType == '0') {
                    // 资源新增流程
                    this.addFlow()
                } else if (this.sourceType == '1') {
                    // 资源扩容流程
                    this.expandFlow()
                }
            }
        })
    },
    methods: {
        addFlow() {
            if (this.flowItem.status == 'db') {
                if (this.flowItem.activityDefID == 'manualActivity') {
                    this.flowItem.actionURL = 'resourceAddEdit'
                } else {
                    this.flowItem.actionURL = 'resourceAddFlow'
                }
                this.$nextTick((_) => {
                    console.log('待办')
                    this.showNodeAudit(this.flowItem)
                })
            } else if (this.flowItem.status == 'yb') {
                this.flowItem.actionURL = 'resourceAddFlowYb'
                this.$nextTick((_) => {
                    console.log('已办')
                    this.showNodeAudit(this.flowItem)
                })
            }
        },

        expandFlow() {
            if (this.flowItem.status == 'db') {
                if (this.flowItem.activityDefID == 'manualActivity') {
                    this.flowItem.actionURL = 'resourceExpandEdit'
                } else {
                    this.flowItem.actionURL = 'resourceExpandFlow'
                }
                this.$nextTick((_) => {
                    console.log('待办')
                    this.showNodeAudit(this.flowItem)
                })
            } else if (this.flowItem.status == 'yb') {
                this.flowItem.actionURL = 'resourceExpandFlowYb'
                this.$nextTick((_) => {
                    console.log('已办')
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
                    { name: 'resourceAddFlow' },
                    () => {
                        self.auditNodeState = -1
                        // this.$nextTick((_) => {
                        //     this.$refs.resourceAddFlow.init(item)
                        // })
                    }
                ],
                [
                    { name: 'resourceAddFlowYb' },
                    () => {
                        self.auditNodeState = 1
                        this.$nextTick((_) => {
                            this.$refs.resourceAddFlowYb.init(item)
                        })
                    }
                ],
                [
                    { name: 'resourceAddEdit' },
                    () => {
                        self.auditNodeState = 2
                        // this.$nextTick((_) => {
                        //     this.$refs.resourceAddFlowYb.init(item)
                        // })
                    }
                ],
                [
                    { name: 'resourceExpandFlow' },
                    () => {
                        self.auditNodeState = -1
                        // this.$nextTick((_) => {
                        //     this.$refs.resourceAddFlow.init(item)
                        // })
                    }
                ],
                [
                    { name: 'resourceExpandFlowYb' },
                    () => {
                        self.auditNodeState = 1
                        this.$nextTick((_) => {
                            this.$refs.resourceExpandFlowYb.init(item)
                        })
                    }
                ],
                [
                    { name: 'resourceExpandEdit' },
                    () => {
                        self.auditNodeState = 2
                        // this.$nextTick((_) => {
                        //     this.$refs.resourceAddFlowYb.init(item)
                        // })
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
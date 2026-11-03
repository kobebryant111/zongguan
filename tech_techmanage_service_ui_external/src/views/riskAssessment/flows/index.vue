<!-- 风险评估 聚合页-->
<template>
    <div>
        <!-- 申请 -->
        <fx-applyfor v-if="auditNodeState==-1" ref="fxApplyfor"></fx-applyfor>
        <!-- 草稿 -->
        <fx-add v-if="auditNodeState==1" ref="fxAdd"></fx-add>
        <!-- 审批 -->
        <fx-approve v-if="auditNodeState==2" ref="fxApprove"></fx-approve>
        <!-- 已办待办 -->
        <fx-ybgd v-if="auditNodeState==3" ref="fxYbgd"></fx-ybgd>
    </div>
</template>

<script>
import fxApplyfor from '@/views/riskAssessment/flows/applyfor'
import fxAdd from '@/views/riskAssessment/flows/add'
import fxApprove from '@/views/riskAssessment/flows/approve'
import fxYbgd from '@/views/riskAssessment/flows/ybgd'
export default {
    components: { 
        fxApplyfor, 
        fxAdd, 
        fxApprove, 
        fxYbgd 
    },
    data() {
        return {
            auditNodeState: "", // 节点状态
            flowItem: this.$route.query, // 流程对象
        }
    },
    mounted() { 
      console.log('风险评估', this.flowItem)
      this.init()
    },
    methods: {
        init() {
            if(this.flowItem.status === 'db') {
                this.showNodeAudit(this.flowItem);
            } else if(this.flowItem.status == 'yb' || this.flowItem.status == 'gd') {
                this.flowItem.formScene = 'fxYbgd'
                this.$nextTick(_ => {
                    this.showNodeAudit(this.flowItem);
                })
            } else {
                this.showNodeAudit(this.flowItem)
            }
        },
        // 显示对应节点的审批内容
        showNodeAudit: function(item) {
            let url = item.formScene;
            let self = this;
            const flowMap = new Map([
                [
                    { name: "fxApplyfor" },
                    () => {
                        self.auditNodeState = -1;
                        this.$nextTick(_ => {
                            this.$refs.fxApplyfor.init(item);
                        })
                    }
                ],
                [
                    { name: "fxsave" },
                    () => {
                        self.auditNodeState = 1;
                        this.$nextTick(_ => {
                            this.$refs.fxAdd.init(item);
                        })
                    }
                ],
                [
                    { name: "erfxApprove" },
                    () => {
                        self.auditNodeState = 2;
                        this.$nextTick(_ => {
                            this.$refs.fxApprove.init(item)
                        })
                    }
                ],
                [
                    { name: "yifxApprove" },
                    () => {
                        self.auditNodeState = 2;
                        this.$nextTick(_ => {
                            this.$refs.fxApprove.init(item);
                        })
                    }
                ],
                [
                    { name: "fxYbgd" },
                    () => {
                        self.auditNodeState = 3;
                        this.$nextTick(_ => {
                            this.$refs.fxYbgd.init(item);
                        })
                    }
                ]
            ]);

            const shwoFlowNode = name => {
              [...flowMap].forEach(([key, val]) => {
                name.indexOf(key.name) != -1 ? val.call() : "";
              });
            };

            shwoFlowNode(url);
        }
    },
}
</script>
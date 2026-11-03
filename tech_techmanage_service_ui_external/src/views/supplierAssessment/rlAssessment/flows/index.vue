<!-- 人力外包考核 聚合页-->
<template>
    <div>
        <!-- 申请 -->
        <lrkh-applyfor v-if="auditNodeState==-1" ref="lrkhApplyfor"></lrkh-applyfor>
        <!-- 草稿 -->
        <lrkh-add v-if="auditNodeState==1" ref="lrkhAdd"></lrkh-add>
        <!-- 审批 -->
        <lrkh-approve v-if="auditNodeState==2" ref="lrkhApprove"></lrkh-approve>
        <!-- 已办待办 -->
        <lrkh-ybgd v-if="auditNodeState==3" ref="lrkhYbgd"></lrkh-ybgd>
    </div>
</template>

<script>
import lrkhApplyfor from '@/views/supplierAssessment/rlAssessment/flows/applyfor'
import lrkhAdd from '@/views/supplierAssessment/rlAssessment/flows/add'
import lrkhApprove from '@/views/supplierAssessment/rlAssessment/flows/approve'
import lrkhYbgd from '@/views/supplierAssessment/rlAssessment/flows/ybgd'
export default {
    components: { 
        lrkhApplyfor, 
        lrkhAdd, 
        lrkhApprove, 
        lrkhYbgd 
    },
    data() {
        return {
            auditNodeState: "", // 节点状态
            flowItem: this.$route.query, // 流程对象
        }
    },
    mounted() { 
      console.log('人力考核', this.flowItem)
      this.init()
    },
    methods: {
        init() {
            if(this.flowItem.status === 'db') {
                this.showNodeAudit(this.flowItem);
            } else if(this.flowItem.status == 'yb' || this.flowItem.status == 'gd') {
                this.flowItem.formScene = 'lrkhYbgd'
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
                    { name: "lrkhApplyfor" },
                    () => {
                        self.auditNodeState = -1;
                        this.$nextTick(_ => {
                            this.$refs.lrkhApplyfor.init(item);
                        })
                    }
                ],
                [
                    { name: "lrkhsave" },
                    () => {
                        self.auditNodeState = 1;
                        this.$nextTick(_ => {
                            this.$refs.lrkhAdd.init(item);
                        })
                    }
                ],
                [
                    { name: "erlrkhApprove" },
                    () => {
                        self.auditNodeState = 2;
                        this.$nextTick(_ => {
                            this.$refs.lrkhApprove.init(item)
                        })
                    }
                ],
                [
                    { name: "yilrkhApprove" },
                    () => {
                        self.auditNodeState = 2;
                        this.$nextTick(_ => {
                            this.$refs.lrkhApprove.init(item);
                        })
                    }
                ],
                [
                    { name: "lrkhYbgd" },
                    () => {
                        self.auditNodeState = 3;
                        this.$nextTick(_ => {
                            this.$refs.lrkhYbgd.init(item);
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
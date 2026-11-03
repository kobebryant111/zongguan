<!-- 项目外包考核 聚合页-->
<template>
    <div>
        <!-- 申请 -->
        <xmkh-applyfor v-if="auditNodeState==-1" ref="xmkhApplyfor"></xmkh-applyfor>
        <!-- 草稿 -->
        <xmkh-add v-if="auditNodeState==1" ref="xmkhAdd"></xmkh-add>
        <!-- 审批 -->
        <xmkh-approve v-if="auditNodeState==2" ref="xmkhApprove"></xmkh-approve>
        <!-- 已办待办 -->
        <xmkh-ybgd v-if="auditNodeState==3" ref="xmkhYbgd"></xmkh-ybgd>
    </div>
</template>

<script>
import xmkhApplyfor from '@/views/supplierAssessment/xmAssessment/flows/applyfor'
import xmkhAdd from '@/views/supplierAssessment/xmAssessment/flows/add'
import xmkhApprove from '@/views/supplierAssessment/xmAssessment/flows/approve'
import xmkhYbgd from '@/views/supplierAssessment/xmAssessment/flows/ybgd'
export default {
    components: { 
        xmkhApplyfor, 
        xmkhAdd, 
        xmkhApprove, 
        xmkhYbgd 
    },
    data() {
        return {
            auditNodeState: "", // 节点状态
            flowItem: this.$route.query, // 流程对象
        }
    },
    mounted() { 
      console.log('项目考核', this.flowItem)
      this.init()
    },
    methods: {
        init() {
            if(this.flowItem.status === 'db') {
                this.showNodeAudit(this.flowItem);
            } else if(this.flowItem.status == 'yb' || this.flowItem.status == 'gd') {
                this.flowItem.formScene = 'xmkhYbgd'
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
                    { name: "xmkhApplyfor" },
                    () => {
                        self.auditNodeState = -1;
                        this.$nextTick(_ => {
                            this.$refs.xmkhApplyfor.init(item);
                        })
                    }
                ],
                [
                    { name: "xmkhsave" },
                    () => {
                        self.auditNodeState = 1;
                        this.$nextTick(_ => {
                            this.$refs.xmkhAdd.init(item);
                        })
                    }
                ],
                [
                    { name: "erxmkhApprove" },
                    () => {
                        self.auditNodeState = 2;
                        this.$nextTick(_ => {
                            this.$refs.xmkhApprove.init(item)
                        })
                    }
                ],
                [
                    { name: "yixmkhApprove" },
                    () => {
                        self.auditNodeState = 2;
                        this.$nextTick(_ => {
                            this.$refs.xmkhApprove.init(item);
                        })
                    }
                ],
                [
                    { name: "xmkhYbgd" },
                    () => {
                        self.auditNodeState = 3;
                        this.$nextTick(_ => {
                            this.$refs.xmkhYbgd.init(item);
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
<!-- 单价调整 聚合页-->
<template>
  <!-- 发起申请 -->
  <lcfq-apply v-if="auditNodeState==-1" ref="lcfqApply"></lcfq-apply>  
  <!-- 添加、退回 -->
  <lcfq-add v-else-if="auditNodeState==1" ref="lcfqAdd"></lcfq-add>
  <!-- 审批、二级部和综合部 -->
  <lcfq-approve v-else-if="auditNodeState==2" ref="lcfqApprove"></lcfq-approve>
  <!-- 已办、归档 -->
  <lcfq-yb-gd v-else-if="auditNodeState==3" ref="lcfqYbGd"></lcfq-yb-gd>
</template>
<script>
  import lcfqApply from '@/views/crewManager/wbPersonAssessment/lcfqFlows/lcfqApplyfor'
  import lcfqAdd from '@/views/crewManager/wbPersonAssessment/lcfqFlows/lcfqAdd'
  import lcfqApprove from '@/views/crewManager/wbPersonAssessment/lcfqFlows/lcfqApprove'
  import lcfqYbGd from '@/views/crewManager/wbPersonAssessment/lcfqFlows/lcfqYbGd'
  
  export default {
    components: {
      lcfqApply,
      lcfqAdd,
      lcfqApprove,
      lcfqYbGd
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() { 
      console.log('单价调整', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
          this.flowItem.formScene = "lcfqYbGd"
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } else {
          this.showNodeAudit(this.flowItem)
        }
      },
      /**
     * 显示对应节点的审批内容
     */
      showNodeAudit: function (item) {
        let url = item.formScene;
        let self = this;
        const flowMap = new Map([
          [
            { name: "lcfqApply" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.lcfqApply.init(item);
              });
            }
          ],
          [
            { name: "lcfqapplysave" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.lcfqAdd.init(item);
              });
            }
          ],
          [
            { name: "erlcfqConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.lcfqApprove.init(item);
              });
            }
          ],
          [
            { name: "zhlcfqConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.lcfqApprove.init(item);
              });
            }
          ],
          [
            { name: "lcfqYbGd" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.lcfqYbGd.init(item);
              });
            }
          ],

        ]);

        const shwoFlowNode = name => {
          [...flowMap].forEach(([key, val]) => {
            name.indexOf(key.name) != -1 ? val.call() : "";
          });
        };

        shwoFlowNode(url);
      },
    }
  };
</script>
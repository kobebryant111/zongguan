<!-- 提前入场申请 聚合页-->
<template>
  <!-- 发起申请 -->
  <tqrcsq-apply v-if="auditNodeState==-1" ref="tqrcsqApply"></tqrcsq-apply>
  <!-- 添加、退回 -->
  <tqrcsq-add v-else-if="auditNodeState==1" ref="tqrcsqAdd"></tqrcsq-add>
  <!-- 审批、二级部和综合部 -->
  <tqrcsq-approve v-else-if="auditNodeState==2" ref="tqrcsqApprove"></tqrcsq-approve>
  <!-- 已办、归档 -->
  <tqrcsq-yb-gd v-else-if="auditNodeState==3" ref="tqrcsqYbGd"></tqrcsq-yb-gd>
</template>
<script>
  import tqrcsqApply from '@/views/crewManager/tqrcApplyfor/tqrcshFlows/tqrcsqApplyfor'
  import tqrcsqAdd from '@/views/crewManager/tqrcApplyfor/tqrcshFlows/tqrcsqAdd'
  import tqrcsqApprove from '@/views/crewManager/tqrcApplyfor/tqrcshFlows/tqrcsqApprove'
  import tqrcsqYbGd from '@/views/crewManager/tqrcApplyfor/tqrcshFlows/tqrcsqYbGd'
  
  export default {
    components: {
      tqrcsqApply,
      tqrcsqAdd,
      tqrcsqApprove,
      tqrcsqYbGd
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() { 
      // console.log('提前入场申请', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
          this.flowItem.formScene = "tqrcsqYbGd"
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
            { name: "tqrcsqApply" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.tqrcsqApply.init(item);
              });
            }
          ],
          [
            { name: "tqrcsqapplysave" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.tqrcsqAdd.init(item);
              });
            }
          ],
          [
            { name: "ertqrcsqConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.tqrcsqApprove.init(item);
              });
            }
          ],
          [
            { name: "zhtqrcsqConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.tqrcsqApprove.init(item);
              });
            }
          ],
          [
            { name: "tqrcsqYbGd" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.tqrcsqYbGd.init(item);
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
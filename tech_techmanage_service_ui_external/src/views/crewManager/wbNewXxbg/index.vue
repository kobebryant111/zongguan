<!-- 信息变更 聚合页-->
<template>
  <!-- 发起申请 -->
  <xxbg-apply v-if="auditNodeState==-1" ref="xxbgApply"></xxbg-apply>
  <!-- 添加、退回 -->
  <xxbg-add v-else-if="auditNodeState==1" ref="xxbgAdd"></xxbg-add>
  <!-- 审批、二级部和综合部 -->
  <xxbg-approve v-else-if="auditNodeState==2" ref="xxbgApprove"></xxbg-approve>
  <!-- 已办、归档 -->
  <xxbg-yb-gd v-else-if="auditNodeState==3" ref="xxbgYbGd"></xxbg-yb-gd>
</template>
<script>
  import xxbgApply from '@/views/crewManager/wbNewXxbg/flows/xxbgApplyfor'
  import xxbgApprove from '@/views/crewManager/wbNewXxbg/flows/xxbgApprove'
  import xxbgAdd from '@/views/crewManager/wbNewXxbg/flows/xxbgAdd'
  import xxbgYbGd from '@/views/crewManager/wbNewXxbg/flows/xxbgYbGd'
  
  export default {
    components: {
      xxbgApply,
      xxbgApprove,
      xxbgAdd,
      xxbgYbGd
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() { 
      // console.log('信息变更', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
          this.flowItem.formScene = "xxbgYbGd"
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
            { name: "xxbgApply" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.xxbgApply.init(item);
              });
            }
          ],
          [
            { name: "xxbgapplysave" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.xxbgAdd.init(item);
              });
            }
          ],
          [
            { name: "erxxbgConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.xxbgApprove.init(item);
              });
            }
          ],
          [
            { name: "zhxxbgConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.xxbgApprove.init(item);
              });
            }
          ],
          [
            { name: "xxbgYbGd" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.xxbgYbGd.init(item);
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
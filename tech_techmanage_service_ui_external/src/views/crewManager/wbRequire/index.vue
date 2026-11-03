<!-- 外包需求 聚合页-->
<template>
  <!-- 发起申请 -->
  <require-manage v-if="auditNodeState==-1" ref="requireApply"></require-manage>
  <!-- 添加、退回 -->
  <require-add v-else-if="auditNodeState==1" ref="requireAdd"></require-add>
  <!-- 审批、二级部和综合部 -->
  <require-approve v-else-if="auditNodeState==2" ref="reqApprove"></require-approve>
  <!-- 已办、归档 -->
  <require-yb-gd v-else-if="auditNodeState==3" ref="reqYbGd"></require-yb-gd>
</template>
<script>
  import RequireManage from '@/views/crewManager/wbRequire/requireManage'
  import RequireApprove from '@/views/crewManager/wbRequire/requireApprove'
  import RequireAdd from '@/views/crewManager/wbRequire/requireAdd'
  import RequireYbGd from '@/views/crewManager/wbRequire/requireYbGd'
  
  export default {
    components: {
      RequireManage,
      RequireApprove,
      RequireAdd,
      RequireYbGd
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() { 
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
          this.flowItem.formScene = "rlxqybgd"
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
            { name: "requireApply" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.requireApply.init(item);
              });
            }
          ],
          [
            { name: "applysave" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.requireAdd.init(item);
              });
            }
          ],
          [
            { name: "erConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.reqApprove.init(item);
              });
            }
          ],
          [
            { name: "zhConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.reqApprove.init(item);
              });
            }
          ],
          [
            { name: "rlxqybgd" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.reqYbGd.init(item);
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
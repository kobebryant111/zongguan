<!-- 部门分配 聚合页-->
<template>
  <!-- 发起申请 -->
  <dep-ass-apply v-if="auditNodeState==-1" ref="depAssApply"></dep-ass-apply>
  <!-- 审批 -->
  <bmfp-approve v-else-if="auditNodeState==1" ref="bmfpApprove"></bmfp-approve>
  <!-- 已办归档 -->
  <bmfp-yb-gd v-else-if="auditNodeState==2" ref="bmfpYbGd"></bmfp-yb-gd>
  <!-- 退回 -->
  <bmfp-back v-else-if="auditNodeState==3" ref="bmfpBack"></bmfp-back>
</template>
<script>
  import depAssApply from '@/views/bankManager/newEmManager/departAssign/depAssApply'
  import bmfpApprove from '@/views/bankManager/newEmManager/departAssign/bmfpApprove'
  import bmfpYbGd from '@/views/bankManager/newEmManager/departAssign/bmfpYbGd'
  import bmfpBack from '@/views/bankManager/newEmManager/departAssign/bmfpBack'
  export default {
    components: { depAssApply, bmfpApprove, bmfpYbGd, bmfpBack },
    data() {
      return {
        auditNodeState: '', // 节点状态
        flowItem: {},  // 流程对象
      };
    },
    mounted() {
      console.log('部门分配流程', this.flowItem, this.$route.params)
      if (this.$route.params.processDefName != undefined) {
        this.flowItem = this.$route.params;
      } else {
        this.flowItem = this.$route.params;
        this.flowItem.actionURL = 'depAssApply'
      }

      this.init()
    },
    methods: {
      init() {
        if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity1")) {
          this.flowItem.actionURL = 'bmfpsp';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity")) {
          this.flowItem.actionURL = 'bmBack';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        }
        else if ((this.flowItem.status == "yb") && (this.flowItem.activityDefID == "manualActivity1")) {
          this.flowItem.actionURL = "bmfpYbGd"
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } else if (this.flowItem.status == "gd") {
          this.flowItem.actionURL = "bmfpYbGd"
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        }
        else {
          this.showNodeAudit(this.flowItem)
        }

      },
      /**
     * 显示对应节点的审批内容
     */
      showNodeAudit: function (item) {
        let url = item.actionURL;
        let self = this;
        const flowMap = new Map([
          [
            { name: "depAssApply" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.depAssApply.init(item);
              });
            }
          ],
          [
            { name: "bmfpsp" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.bmfpApprove.init(item);
              });
            }
          ],
          [
            { name: "bmfpYbGd" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.bmfpYbGd.init(item);
              });
            }
          ],
          [
            { name: "bmBack" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.bmfpBack.init(item);
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
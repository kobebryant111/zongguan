<!-- 外包考勤修改 聚合页-->
<template>
  <!-- 发起申请 -->
  <wb-kqxg-apply v-if="auditNodeState==-1" ref="wbKqxgApply"></wb-kqxg-apply>
  <!-- 保存、退回 -->
  <wbkqxg-save-back v-else-if="auditNodeState==1" ref="wbkqxgSaveBack"></wbkqxg-save-back>
  <!-- 二级部经理审核 -->
  <wbkqxg-two-manager v-else-if="auditNodeState==2" ref="wbkqxgTwoManager"></wbkqxg-two-manager>
  <!-- 分管总经理审核 -->
  <wbkqxg-fg-manager v-else-if="auditNodeState==3" ref="wbkqxgFgManager"></wbkqxg-fg-manager>
  <!-- 分管总经理审核 -->
  <wbkqxg-zhb-confirm v-else-if="auditNodeState==4" ref="wbkqxgZhbConfirm"></wbkqxg-zhb-confirm>
  <!-- 已办归档 -->
  <wbkqxg-yb-gd v-else-if="auditNodeState==5" ref="wbkqxgYbGd"></wbkqxg-yb-gd>
</template>
<script>
  import wbKqxgApply from '@/views/crewManager/wbAttendanceMo/wbKqxgApply'
  import wbkqxgSaveBack from '@/views/crewManager/wbAttendanceMo/wbkqxgSaveBack'
  import wbkqxgTwoManager from '@/views/crewManager/wbAttendanceMo/wbkqxgTwoManager'
  import wbkqxgFgManager from '@/views/crewManager/wbAttendanceMo/wbkqxgFgManager'
  import wbkqxgZhbConfirm from '@/views/crewManager/wbAttendanceMo/wbkqxgZhbConfirm'
  import wbkqxgYbGd from '@/views/crewManager/wbAttendanceMo/wbkqxgYbGd'
  export default {
    components: { wbKqxgApply, wbkqxgSaveBack, wbkqxgTwoManager, wbkqxgFgManager, wbkqxgZhbConfirm, wbkqxgYbGd },
    data() {
      return {
        auditNodeState: '', // 节点状态
        flowItem: {},  // 流程对象
      };
    },
    mounted() {
      console.log('外包考勤修改流程', this.flowItem, this.$route.params)
      if (this.$route.params.processDefName != undefined) {
        this.flowItem = this.$route.params;
      } else {
        this.flowItem = this.$route.params;
        this.flowItem.actionURL = 'wbKqxgApply'
      }

      this.init()
    },
    methods: {
      init() {
        if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity")) {
          this.flowItem.actionURL = 'kqxgSaveBack';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity1")) {
          this.flowItem.actionURL = 'kqxgTwoManger';
          this.$nextTick(_ => {
            console.log("二级部", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity2")) {
          this.flowItem.actionURL = 'kqxgFgManger';
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity3")) {
          this.flowItem.actionURL = 'kqxgZhbConfirm';
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "yb") && (this.flowItem.activityDefID == "manualActivity" || this.flowItem.activityDefID == "manualActivity1" || this.flowItem.activityDefID == "manualActivity2" || this.flowItem.activityDefID == "manualActivity3")) {
          this.flowItem.actionURL = "kqxgGd"
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } else if (this.flowItem.status == "gd") {
          this.flowItem.actionURL = "kqxgGd"
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
            { name: "wbKqxgApply" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.wbKqxgApply.init(item);
              });
            }
          ],
          [
            { name: "kqxgSaveBack" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.wbkqxgSaveBack.init(item);
              });
            }
          ],
          [
            { name: "kqxgTwoManger" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.wbkqxgTwoManager.init(item);
              });
            }
          ],
          [
            { name: "kqxgFgManger" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.wbkqxgFgManager.init(item);
              });
            }
          ],
          [
            { name: "kqxgZhbConfirm" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.wbkqxgZhbConfirm.init(item);
              });
            }
          ],
          [
            { name: "kqxgGd" },
            () => {
              self.auditNodeState = 5;
              this.$nextTick(_ => {
                this.$refs.wbkqxgYbGd.init(item);
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
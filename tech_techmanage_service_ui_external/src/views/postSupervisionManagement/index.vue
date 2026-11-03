<!-- 数据中心后督管理 聚合页-->
<template>
  <!-- 确认并反馈 -->
  <confirm-and-feedback v-if="auditNodeState==1" ref="confirmAndFeedback"></confirm-and-feedback>
  <!-- 二级部经理审核 -->
  <ps-two-manager v-else-if="auditNodeState==2" ref="psTwoManager"></ps-two-manager>
  <!-- 发起人核实 -->
  <ps-audit v-else-if="auditNodeState==3" ref="psAudit"></ps-audit>
  <!-- 已办归档 -->
  <ps-yb-gd v-else-if="auditNodeState==4" ref="psYbGd"></ps-yb-gd>
</template>
<script>
  import confirmAndFeedback from '@/views/postSupervisionManagement/confirmAndFeedback' //确认并反馈
  import psTwoManager from '@/views/postSupervisionManagement/psTwoManager' //二级部经理审核
  import psAudit from '@/views/postSupervisionManagement/psAudit' // 发起人核实
  import psYbGd from '@/views/postSupervisionManagement/psYbGd' // 已办归档
  export default {
    components: { confirmAndFeedback, psTwoManager, psAudit, psYbGd },
    data() {
      return {
        auditNodeState: '', // 节点状态
        flowItem: {},  // 流程对象
      };
    },
    mounted() {
      console.log('数据中心后督管理流程', this.flowItem, this.$route.params)
      if (this.$route.params.processDefName != undefined) {
        this.flowItem = this.$route.params;
      } else {
        this.flowItem = this.$route.params;
        this.flowItem.actionURL = 'postSupInfoRecordAndEntry'
      }

      this.init()
    },
    methods: {
      init() {
        if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity")) {
          this.flowItem.actionURL = 'postSupInfoRecordAndEntry';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity1")) {
          this.flowItem.actionURL = 'confirmAndFeedback';
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity2")) {
          this.flowItem.actionURL = 'psTwoManager';
          this.$nextTick(_ => {
            console.log("二级部", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        }else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity3")) {
          this.flowItem.actionURL = 'psAudit';
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } 
        else if ((this.flowItem.status == "yb" || this.flowItem.status == "gd")) {
          this.flowItem.actionURL = 'psYbGd';
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
            { name: "confirmAndFeedback" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.confirmAndFeedback.init(item);
              });
            }
          ],
          [
            { name: "psTwoManager" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.psTwoManager.init(item);
              });
            }
          ],
          [
            { name: "psAudit" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.psAudit.init(item);
              });
            }
          ],
          [
            { name: "psYbGd" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.psYbGd.init(item);
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
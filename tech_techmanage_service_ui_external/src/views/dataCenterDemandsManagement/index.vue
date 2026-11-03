<!-- 数据中心需求管理 聚合页-->
<template>
  <!-- 发起申请 -->
  <data-center-demands-launch v-if="auditNodeState==-1" ref="dataCenterDemandsLaunch"></data-center-demands-launch>
  <!-- 保存、退回 -->
  <data-center-demands-save-back v-else-if="auditNodeState==1" ref="dataCenterDemandsSaveBack"></data-center-demands-save-back>
  <!-- 二级部经理审核 -->
  <dcdl-two-manager v-else-if="auditNodeState==2" ref="dcdlTwoManager"></dcdl-two-manager>
  <!-- 运维部人员收口 -->
  <dcdl-ywb-end v-else-if="auditNodeState==3" ref="dcdlYwbEnd"></dcdl-ywb-end>
  <!-- 发起已办归档 -->
  <dcdl-yb-gd v-else-if="auditNodeState==4" ref="dcdlYbGd"></dcdl-yb-gd>
</template>
<script>
  import dataCenterDemandsLaunch from '@/views/dataCenterDemandsManagement/dataCenterDemandsLaunch' //发起
  import dataCenterDemandsSaveBack from '@/views/dataCenterDemandsManagement/dataCenterDemandsSaveBack' //保存退回
  import dcdlTwoManager from '@/views/dataCenterDemandsManagement/dcdlTwoManager' // 二级部经理审核
  import dcdlYwbEnd from '@/views/dataCenterDemandsManagement/dcdlYwbEnd' //运维部人员收口
  import dcdlYbGd from '@/views/dataCenterDemandsManagement/dcdlYbGd' //发起已办归档
  export default {
    components: { dataCenterDemandsLaunch, dataCenterDemandsSaveBack, dcdlTwoManager, dcdlYwbEnd, dcdlYbGd },
    data() {
      return {
        auditNodeState: '', // 节点状态
        flowItem: {},  // 流程对象
      };
    },
    mounted() {
      console.log('数据中心需求管理流程', this.flowItem, this.$route.params)
      if (this.$route.params.processDefName != undefined) {
        this.flowItem = this.$route.params;
      } else {
        this.flowItem = this.$route.params;
        this.flowItem.actionURL = 'dataCenterDemandsLaunch'
      }

      this.init()
    },
    methods: {
      init() {
        if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity")) {
          this.flowItem.actionURL = 'dataCenterDemandsSaveBack';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity1")) {
          this.flowItem.actionURL = 'dcdlTwoManager';
          this.$nextTick(_ => {
            console.log("二级部", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity2")) {
          this.flowItem.actionURL = 'dcdlYwbEnd';
          this.$nextTick(_ => {
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "yb" || this.flowItem.status == "gd")) {
          this.flowItem.actionURL = 'dcdlYbGd';
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
            { name: "dataCenterDemandsLaunch" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.dataCenterDemandsLaunch.init(item);
              });
            }
          ],
          [
            { name: "dataCenterDemandsSaveBack" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.dataCenterDemandsSaveBack.init(item);
              });
            }
          ],
          [
            { name: "dcdlTwoManager" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.dcdlTwoManager.init(item);
              });
            }
          ],
          [
            { name: "dcdlYwbEnd" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.dcdlYwbEnd.init(item);
              });
            }
          ],
          [
            { name: "dcdlYbGd" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.dcdlYbGd.init(item);
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
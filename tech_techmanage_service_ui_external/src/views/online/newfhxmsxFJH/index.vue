<!-- 分行意向上线 聚合页-->
<template>
  <!-- 发起申请 -->
  <addonlinefhflow v-if="auditNodeState==-1" ref="addonlinefhflow"></addonlinefhflow>
  <!-- 分行科技二级部经理审批 -->
  <approve-ejbmanagers v-else-if="auditNodeState==1" ref="approveEjbmanagers"></approve-ejbmanagers>
  <!-- 分行业务总经理审批 -->
  <approve-buszjl v-else-if="auditNodeState==2" ref="approveBuszjl"></approve-buszjl>
  <!-- 分行科技总经理审批 -->
  <approve-techzjl v-else-if="auditNodeState==3" ref="approveTechzjl"></approve-techzjl>
  <!-- 分行业务总经理已办、分行业务总经理已办、分行科技总经理已办 -->
  <onlinefh-yb v-else-if="auditNodeState==4" ref="onlinefhYb"></onlinefh-yb>
  <!-- 分行业务总经理归档、分行业务总经理归档、分行科技总经理归档 -->
  <onlinefh-gd v-else-if="auditNodeState==5" ref="onlinefhGd"></onlinefh-gd>
</template>
<script>
  import addonlinefhflow from '@/views/online/newfhxmsxFJH/addonlinefhflow'
  import approveEjbmanagers from '@/views/online/newfhxmsxFJH/approveEjbmanagers'
  import approveBuszjl from '@/views/online/newfhxmsxFJH/approveBuszjl'
  import approveTechzjl from '@/views/online/newfhxmsxFJH/approveTechzjl'
  import onlinefhYb from '@/views/online/newfhxmsxFJH/onlinefhYb'
  import onlinefhGd from '@/views/online/newfhxmsxFJH/onlinefhGd'
  export default {
    components: {
      addonlinefhflow,
      approveEjbmanagers,
      approveBuszjl,
      approveTechzjl,
      onlinefhYb,
      onlinefhGd,
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() {
      console.log('分行意向上线流程', this.flowItem)
      if (this.$route.params.processDefName != undefined) {
        this.flowItem = this.$route.params;
      } else {
        this.flowItem = this.$route.params;
        this.flowItem.actionURL = 'newfhxmsxFJH';
      }
      this.init()
    },
    methods: {
      init() {
        if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity1")) {
          this.flowItem.actionURL = 'approveEjbmanagers';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity2")) {
          this.flowItem.actionURL = 'approveBuszjl';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if ((this.flowItem.status == "db") && (this.flowItem.activityDefID == "manualActivity3")) {
          this.flowItem.actionURL = 'approveTechzjl';
          this.$nextTick(_ => {
            console.log("db", this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
        } else if (this.flowItem.status == "yb") {
          this.flowItem.actionURL = "onlinefhYb"
          this.$nextTick(_ => {
            console.log("已办");
            this.showNodeAudit(this.flowItem);
          });
        } else if (this.flowItem.status == "gd") {
          this.flowItem.actionURL = "onlinefhGd"
          this.$nextTick(_ => {
           console.log("归档");
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
        let url = item.actionURL;
        let self = this;
        const flowMap = new Map([
          [
            { name: "addonlinefhflow" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.addonlinefhflow.init(item);
              });
            }
          ],
          [
            { name: "approveEjbmanagers" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.approveEjbmanagers.init(item);
              });
            }
          ],
          [
            { name: "approveBuszjl" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.approveBuszjl.init(item);
              });
            }
          ],
          [
            { name: "approveTechzjl" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.approveTechzjl.init(item);
              });
            }
          ],
          [
            { name: "onlinefhYb" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.onlinefhYb.init(item);
              });
            }
          ],
          [
            { name: "onlinefhGd" },
            () => {
              self.auditNodeState = 5;
              this.$nextTick(_ => {
                this.$refs.onlinefhGd.init(item);
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
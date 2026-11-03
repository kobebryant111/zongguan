<!-- 分行意向上线 聚合页-->
<template>
  <!-- 分行业务经理审批 -->
  <approve-review-by-busjl v-if="auditNodeState==1" ref="approveReviewByBusjl"></approve-review-by-busjl>
  <!-- 分行业务总经理已办、分行业务总经理已办、分行科技总经理已办 -->
  <onlinefh-yb v-else-if="auditNodeState==2" ref="onlinefhYb"></onlinefh-yb>
  <!-- 分行业务总经理归档、分行业务总经理归档、分行科技总经理归档 -->
  <onlinefh-gd v-else-if="auditNodeState==3" ref="onlinefhGd"></onlinefh-gd>
</template>
<script>
  import approveReviewByBusjl from '@/views/online/newfhsxyzFJH/approveReviewByBusjl'
  import onlinefhYb from '@/views/online/newfhsxyzFJH/onlinefhYb'
  import onlinefhGd from '@/views/online/newfhsxyzFJH/onlinefhGd'
  export default {
    components: {
      approveReviewByBusjl,
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
        if ((this.flowItem.status == "db")) {
          this.flowItem.actionURL = 'approveReviewByBusjl';
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
            { name: "approveReviewByBusjl" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.approveReviewByBusjl.init(item);
              });
            }
          ],
          [
            { name: "onlinefhYb" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.onlinefhYb.init(item);
              });
            }
          ],
          [
            { name: "onlinefhGd" },
            () => {
              self.auditNodeState = 3;
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
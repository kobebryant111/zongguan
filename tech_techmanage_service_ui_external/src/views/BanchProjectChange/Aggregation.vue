<!-- 分行项目变更聚合 -->
<template>
  <!-- 分行系统负责人应用变更申请 -->
  <pager1 v-if="auditNodeState==1" ref="pager1"></pager1>
  <!-- 分行研发部/IT支持部经理审批 -->
  <pager2 v-else-if="auditNodeState==2" ref="pager2"></pager2>
  <!-- 分行科技部/运营部总经理审批 -->
  <pager3 v-else-if="auditNodeState==3" ref="pager3"></pager3>
  <!-- 主办人员办理 -->
  <pager4 v-else-if="auditNodeState==4" ref="pager4"></pager4>
  <!-- 归档 -->
  <gd-detail v-else-if="auditNodeState==5" ref="gdDetail"></gd-detail>
  <!-- 草稿 -->
  <draft v-else-if="auditNodeState==6" ref="draft"></draft>
</template>
<script>
import pager1 from "@/views/BanchProjectChange/pager1";
import pager2 from "@/views/BanchProjectChange/pager2";
import pager3 from "@/views/BanchProjectChange/pager3";
import pager4 from "@/views/BanchProjectChange/pager4";
import gdDetail from '@/views/BanchProjectChange/GdDetail'
import Draft from '@/views/BanchProjectChange/Draft'
export default {
  components: {
    pager1,
    pager2,
    pager3,
    pager4,
    gdDetail,
    Draft
  },
  data() {
    return {
      auditNodeState: "", // 节点状态
      flowItem: {} // 流程对象
    };
  },
  mounted() {
    console.log("分行自研项目应用变更 聚合", this.flowItem, this.$route.params);
    if (this.$route.params.processDefName != undefined) {
      this.flowItem = this.$route.params;
    } else {
      this.flowItem = this.$route.params;
      this.flowItem.actionURL = "newFhyyxtbg";
    }
     this.init();
  },
  methods: {
    init() {
      let self = this;
      // 草稿
      if (self.flowItem.activityDefID == "manualActivity") {
       self.flowItem.actionURL = "draft";
        // 分行研发部/IT支持部经理审批
      } else if (self.flowItem.activityDefID == "manualActivity1") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "pager2";
        } else {    // 已办
          self.flowItem.actionURL = "pager2";
        }
        // 分行科技部/运营部总经理审批
      } else if(self.flowItem.activityDefID == "manualActivity2") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "pager3";
        } else {       // 已办
          self.flowItem.actionURL = "pager2";
        }
        // 主办人员办理
      } else if(self.flowItem.activityDefID == "manualActivity3") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "pager4";
        } else {       // 已办
          self.flowItem.actionURL = "pager3";
        }
        
      }  else {
          self.flowItem.actionURL = "gdDetail";
      }
      
      self.$nextTick(_ => {
        console.log(self.flowItem.status, self.flowItem);
        self.showNodeAudit(self.flowItem);
      });
    },
    /**
     * 显示对应节点的审批内容
     */
    showNodeAudit: function(item) {
      let url = item.actionURL;
      let self = this;
      const flowMap = new Map([
        [
          { name: "pager1" },
          () => {
            self.auditNodeState = 1;
            self.$nextTick(_ => {
              self.$refs.pager1.init(item);
            });
          }
        ],
        [
          { name: "pager2" },
          () => {
            self.auditNodeState = 2;
            self.$nextTick(_ => {
              self.$refs.pager2.init(item);
            });
          }
        ],
        [
          { name: "gdDetail" },
          () => {
            self.auditNodeState = 5;
            self.$nextTick(_ => {
              self.$refs.gdDetail.init(item);
            });
          }
        ],
        [
          { name: "pager3" },
          () => {
            self.auditNodeState = 3;
            self.$nextTick(_ => {
              self.$refs.pager3.init(item);
            });
          }
        ],
        [
          { name: "pager4" },
          () => {
            self.auditNodeState = 4;
            self.$nextTick(_ => {
              self.$refs.pager4.init(item);
            });
          }
        ],
        [
          { name: "draft" },
          () => {
            self.auditNodeState = 6;
            self.$nextTick(_ => {
              self.$refs.draft.init(item);
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
    }
  }
};
</script>


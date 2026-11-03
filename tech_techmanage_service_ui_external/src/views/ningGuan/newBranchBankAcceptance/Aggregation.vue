<!-- 新支行验收流程 聚合页-->
<template>
  <!-- 发起页面 -->
  <initiate-xzh v-if="auditNodeState==1" ref="initiate"></initiate-xzh>
  <!-- 草稿 -->
  <draft v-else-if="auditNodeState==2" ref="draft"></draft>
  <!-- 流程审批 -->
  <flow v-else-if="auditNodeState==3" ref="flow"></flow>
  <!-- 归档 -->
  <archive v-else-if="auditNodeState==4" ref="archive"></archive>
  <!-- 详情 -->
  <detail v-else-if="auditNodeState==5" ref="detail"></detail>
</template>
<script>
import InitiateXzh from "@/views/ningGuan/newBranchBankAcceptance/InitiateXzh";
import Draft from "@/views/ningGuan/newBranchBankAcceptance/Draft";
import Flow from "@/views/ningGuan/newBranchBankAcceptance/Flow";
import Archive from "@/views/ningGuan/newBranchBankAcceptance/Archive";
import Detail from "@/views/ningGuan/newBranchBankAcceptance/Detail";

export default {
  components: {
    InitiateXzh,
    Draft,
    Flow,
    Archive,
    Detail
  },
  data() {
    return {
      auditNodeState: "", // 节点状态
      flowItem: {} // 流程对象
    };
  },
  mounted() {
    console.log("新支行验收流程 聚合", this.flowItem, this.$route.params);
    console.log(this.$route.params.processDefName)
    if (this.$route.params.processDefName != undefined) {
      this.flowItem = this.$route.params;
    } else if(this.$route.params.flowid != undefined){
      this.flowItem = {
        processInstID: this.$route.params.instanceid,
        status : 'gd'
      }
    } else {
      this.flowItem = this.$route.params;
      this.flowItem.actionURL = "list";
    }

    this.init();
  },
  methods: {
    init() {
      let self = this; 
      console.log(self.flowItem)
      if (self.flowItem.actionURL === "list") {
        this.$router.push({name: "ningGuanflow.pager"})
      } else if (self.flowItem.activityDefID == "manualActivity0") {
        if(self.flowItem.status === "yb") {
          self.flowItem.actionURL = "detail";
        }else{
          self.flowItem.actionURL = "draft";
        }
      } else {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "flow";
        } else if(self.flowItem.status === "yb") {
          self.flowItem.actionURL = "detail";
        } else if(self.flowItem.status === "gd") {
          self.flowItem.actionURL = "archive";
        }
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
          { name: "initiate" },
          () => {
            self.auditNodeState = 1;
            self.$nextTick(_ => {
              self.$refs.initiate.init(item);
            });
          }
        ],
        [
          { name: "draft" },
          () => {
            self.auditNodeState = 2;
            self.$nextTick(_ => {
              self.$refs.draft.init(item);
            });
          }
        ],
        [
          { name: "flow" },
          () => {
            self.auditNodeState = 3;
            self.$nextTick(_ => {
              self.$refs.flow.init(item);
            });
          }
        ],
        [
          { name: "archive" },
          () => {
            self.auditNodeState = 4;
            self.$nextTick(_ => {
              self.$refs.archive.init(item);
            });
          }
        ],
        [
          { name: "detail" },
          () => {
            self.auditNodeState = 5;
            self.$nextTick(_ => {
              self.$refs.detail.init(item);
            });
          }
        ]
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
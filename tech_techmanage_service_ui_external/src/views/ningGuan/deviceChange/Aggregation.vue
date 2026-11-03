<!-- 宁波地区管理部流程 聚合页-->
<template>
  <!-- 二级部经理审批 -->
  <second-level-manager v-if="auditNodeState==1" ref="secondLevelManager"></second-level-manager>
  <!-- 处理人实施 -->
  <implement v-else-if="auditNodeState==2" ref="implement"></implement>
  <!-- 监控变更确认 -->
  <monitor v-else-if="auditNodeState==3" ref="monitor"></monitor>
  <!-- 出入库录入确认 -->
  <in-lib v-else-if="auditNodeState==4" ref="inLib"></in-lib>
  <!-- CMDB确认 -->
  <cmdb v-else-if="auditNodeState==5" ref="cmdb"></cmdb>
  <!-- 归档 -->
  <archive v-else-if="auditNodeState==6" ref="archive"></archive>
  <!-- 查询 -->
  <pagers v-else-if="auditNodeState==7" ref="pagers"></pagers>
  <!-- 草稿 -->
  <draft v-else-if="auditNodeState==0" ref="draft"></draft>

</template>
<script>
import SecondLevelManager from "@/views/ningGuan/deviceChange/SecondLevelManager";
import Implement from "@/views/ningGuan/deviceChange/Implement";
import Monitor from "@/views/ningGuan/deviceChange/Monitor";
import InLib from "@/views/ningGuan/deviceChange/InLib";
import Cmdb from "@/views/ningGuan/deviceChange/Cmdb";
import Archive from "@/views/ningGuan/deviceChange/Archive";
import Pagers from "@/views/ningGuan/deviceChange/Pagers";
import Draft from "@/views/ningGuan/deviceChange/Draft";

export default {
  components: { SecondLevelManager, Implement, Monitor,InLib, Cmdb, Archive, Pagers, Draft },
  data() {
    return {
      auditNodeState: "", // 节点状态
      flowItem: {} // 流程对象
    };
  },
  mounted() {
    console.log("宁波地区管理部流程 聚合", this.flowItem, this.$route.params);
    if (this.$route.params.processDefName != undefined) {
      this.flowItem = this.$route.params;
    } else {
      this.flowItem = this.$route.params;
      this.flowItem.actionURL = "deviceChangeStick";
    }

    this.init();
  },
  methods: {
    init() {
      let self = this;
      // 二级部经理层
      if ( self.flowItem.activityDefID == "manualActivity11") {
        self.flowItem.actionURL = "secondLevelManager";
        // 实施人
      } else if (self.flowItem.activityDefID == "manualActivity1") {
        if(self.flowItem.status === 'db') {
            self.flowItem.actionURL = "implement";
          } else {
            self.flowItem.actionURL = "secondLevelManager";
          }
        // 监控变更变更
      } else if(self.flowItem.activityDefID == "manualActivity12") {
          if(self.flowItem.status === 'db') {
            self.flowItem.actionURL = "monitor";
          } else {
            self.flowItem.actionURL = "monitor";
          }
      // 出入库录入变更
      } else if(self.flowItem.activityDefID == "manualActivity13") {
          if(self.flowItem.status === 'db') {
            self.flowItem.actionURL = "inLib";
          } else {
            self.flowItem.actionURL = "monitor";
          }
      }
      // CMDB 确认
      else if(self.flowItem.activityDefID == "manualActivity121") {
          if(self.flowItem.status === 'db') {
            self.flowItem.actionURL = "cmdb";
          } else {
            self.flowItem.actionURL = "inLib";
          }
          // 发起申请||退回
      }else if(self.flowItem.activityDefID == "manualActivity") {
          if(self.flowItem.status === 'db') {
            self.flowItem.actionURL = "draft";
          } else {
            self.flowItem.actionURL = "secondLevelManager";
          }
      }
      // 归档
      else if(self.flowItem.status === 'gd') {
          self.flowItem.actionURL = "archive";
          // 草稿
      } else {
        this.$router.push({name: "ningGuanflow.pager"});
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
          { name: "draft" },
          () => {
            self.auditNodeState = 0;
            self.$nextTick(_ => {
              self.$refs.draft.init(item);
            });
          }
        ],
        [
          { name: "secondLevelManager" },
          () => {
            self.auditNodeState = 1;
            self.$nextTick(_ => {
              self.$refs.secondLevelManager.init(item);
            });
          }
        ],
        [
          { name: "implement" },
          () => {
            self.auditNodeState = 2;
            self.$nextTick(_ => {
              self.$refs.implement.init(item);
            });
          }
        ],
        [
          { name: "monitor" },
          () => {
            self.auditNodeState = 3;
            self.$nextTick(_ => {
              self.$refs.monitor.init(item);
            });
          }
        ],
        [
          { name: "inLib" },
          () => {
            self.auditNodeState = 4;
            self.$nextTick(_ => {
              self.$refs.inLib.init(item);
            });
          }
        ],
        [
          { name: "cmdb" },
          () => {
            self.auditNodeState = 5;
            self.$nextTick(_ => {
              self.$refs.cmdb.init(item);
            });
          }
        ],
        [
          { name: "archive" },
          () => {
            self.auditNodeState = 6;
            self.$nextTick(_ => {
              self.$refs.archive.init(item);
            });
          }
        ],
        [
          { name: "pagers" },
          () => {
            self.auditNodeState = 7;
            self.$nextTick(_ => {
              self.$refs.pagers.flowLoad(item);
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
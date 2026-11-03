<!-- 分行意向审批聚合 -->
<template>
  <!-- 研发二级部经理 -->
  <yfejbjlsp v-if="auditNodeState==1" ref="yfejbjlsp"></yfejbjlsp>
  <!-- 业务总经理意向审批 -->
  <bus-sum-manager v-else-if="auditNodeState==2" ref="busSumMManager"></bus-sum-manager>
  <!-- 分行合规总经理审批 -->
  <branch-compliance  v-else-if="auditNodeState==3" ref="branchCompliance"></branch-compliance>
  <!-- 分行人力资源部总经理审批 -->
  <branch-hr-manager  v-else-if="auditNodeState==4" ref="branchHrManager"></branch-hr-manager>
  <!-- 科技总经理审批 -->
  <science-gm v-else-if="auditNodeState==5" ref="scienceGm"></science-gm>
  <!-- 分行分管行长审批 -->
  <branch-president v-else-if="auditNodeState==6" ref="branchPresident"></branch-president>
  <!-- 分行管理部审核 -->
  <branch-manager v-else-if="auditNodeState==7" ref="branchManager"></branch-manager>
  <!-- 架构部三新审核 -->
  <framework-three v-else-if="auditNodeState==8" ref="frameworkThree"></framework-three>
  <!-- 架构部分管副总审批 -->
  <framework-branch-dgm v-else-if="auditNodeState==9" ref="frameworkBranchDgm"></framework-branch-dgm>
  <!-- 归档 -->
  <gd-detail v-else-if="auditNodeState==10" ref="gdDetail"></gd-detail>
</template>
<script>
import Yfejbjlsp from "@/views/branchIntention/Yfejbjlsp";
import busSumManager from "@/views/branchIntention/BusSumManager"
import gdDetail from '@/views/branchIntention/GdDetail'
import scienceGm from '@/views/branchIntention/ScienceGm'
import branchPresident from '@/views/branchIntention/BranchPresident'
import frameworkThree from '@/views/branchIntention/FrameworkThree'
import branchManager from '@/views/branchIntention/BranchManager'
import frameworkBranchDgm from '@/views/branchIntention/FrameworkBranchDgm'
import branchCompliance from '@/views/branchIntention/BranchCompliance'
import branchHrManager from '@/views/branchIntention/BranchHrManager'

export default {
  components: {
    Yfejbjlsp,
    busSumManager,
    gdDetail,
    scienceGm,
    branchPresident,
    frameworkThree,
    branchManager,
    frameworkBranchDgm,
    branchCompliance,
    branchHrManager
  },
  data() {
    return {
      auditNodeState: "", // 节点状态
      flowItem: {} // 流程对象
    };
  },
  mounted() {
    console.log("分行意向审批 聚合", this.flowItem, this.$route.params);
    if (this.$route.params.processDefName != undefined) {
      this.flowItem = this.$route.params;
    } else {
      this.flowItem = this.$route.params;
      this.flowItem.actionURL = "newFhywyxFjh";
    }
     this.init();
  },
  methods: {
    init() {
      let self = this;
      // 研发二级部经理
      if (self.flowItem.activityDefID == "manualActivity2") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "yfejbjlsp";
        } else {    // 已办
          self.flowItem.actionURL = "gdDetail";
        }
        // 业务总经理意向审批
      } else if (self.flowItem.activityDefID == "manualActivity3") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "busSumMManager";
        } else {    // 已办
          self.flowItem.actionURL = "scienceGm";
        }
        // 科技总经理审批
      } else if(self.flowItem.activityDefID == "manualActivity6") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "scienceGm";
        } else {       // 已办
          self.flowItem.actionURL = "busSumMManager";
        }
        // 分行分管行长审批
      } else if(self.flowItem.activityDefID == "manualActivity7") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "branchPresident";
        } else {       // 已办
          self.flowItem.actionURL = "scienceGm";
        }
        // 架构部三新审核
      } else if(self.flowItem.activityDefID == "manualActivity9") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "frameworkThree";
        } else {       // 已办
          self.flowItem.actionURL = "branchPresident";
        }
        // 分行管理部审核
      }else if(self.flowItem.activityDefID == "manualActivity8") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "branchManager";
        } else {       // 已办
          self.flowItem.actionURL = "branchPresident";
        }
      }else if(self.flowItem.activityDefID == "manualActivity10") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "frameworkBranchDgm";
        } else {       // 已办
          self.flowItem.actionURL = "frameworkThree";
        }
        // 分行合规总经理审批
      }else if(self.flowItem.activityDefID == "manualActivity4") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "branchCompliance";
        } else {       // 已办
          self.flowItem.actionURL = "busSumMManager";
        }
      }
      // 分行人力资源部总经理审批
      else if(self.flowItem.activityDefID == "manualActivity5") {
        if (self.flowItem.status === "db") {
          self.flowItem.actionURL = "branchHrManager";
        } else {       // 已办
          self.flowItem.actionURL = "branchCompliance";
        }
        // 归档
      } else {
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
          { name: "yfejbjlsp" },
          () => {
            self.auditNodeState = 1;
            self.$nextTick(_ => {
              self.$refs.yfejbjlsp.init(item);
            });
          }
        ],
        [
          { name: "busSumMManager" },
          () => {
            self.auditNodeState = 2;
            self.$nextTick(_ => {
              self.$refs.busSumMManager.init(item);
            });
          }
        ],
        [
          { name: "gdDetail" },
          () => {
            self.auditNodeState = 10;
            self.$nextTick(_ => {
              self.$refs.gdDetail.init(item);
            });
          }
        ],
        [
          { name: "scienceGm" },
          () => {
            self.auditNodeState = 5;
            self.$nextTick(_ => {
              self.$refs.scienceGm.init(item);
            });
          }
        ],
        [
          { name: "branchPresident" },
          () => {
            self.auditNodeState = 6;
            self.$nextTick(_ => {
              self.$refs.branchPresident.init(item);
            });
          }
        ],
        [
          { name: "frameworkThree" },
          () => {
            self.auditNodeState = 8;
            self.$nextTick(_ => {
              self.$refs.frameworkThree.init(item);
            });
          }
        ],
        [
          { name: "branchManager" },
          () => {
            self.auditNodeState = 7;
            self.$nextTick(_ => {
              self.$refs.branchManager.init(item);
            });
          }
        ],
        [
          { name: "frameworkBranchDgm" },
          () => {
            self.auditNodeState = 9;
            self.$nextTick(_ => {
              self.$refs.frameworkBranchDgm.init(item);
            });
          }
        ],
        [
          { name: "branchCompliance" },
          () => {
            self.auditNodeState = 3;
            self.$nextTick(_ => {
              self.$refs.branchCompliance.init(item);
            });
          }
        ],
        //branchHrManager
        [
          { name: "branchHrManager" },
          () => {
            self.auditNodeState = 4;
            self.$nextTick(_ => {
              self.$refs.branchHrManager.init(item);
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


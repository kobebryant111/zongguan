<!-- 外包人员信息变更 聚合页-->
<template>
  <div>
    <!-- 发起申请 -->
    <xxbg-add v-if="auditNodeState==-1" ref="xxbgAdd" @showUserInfo="showUserInfo"></xxbg-add>
    <!-- 添加、退回 -->
    <xxbg-save-back v-else-if="auditNodeState==1" ref="xxbgSaveBack" @showUserInfo="showUserInfo"></xxbg-save-back>
    <!-- 外包人员信息变更无打分二级部经理审批 -->
    <xxbg-two-manager v-else-if="auditNodeState==2" ref="xxbgTwoManager" @showUserInfo="showUserInfo"></xxbg-two-manager>
    <!-- 外包人员信息变更无打分综合部确认 -->
    <xxbg-zhb-confirm v-else-if="auditNodeState==3" ref="xxbgZhbConfirm" @showUserInfo="showUserInfo"></xxbg-zhb-confirm>
    <!-- 外包人员信息变更发起已办归档 -->
    <xxbg-ybgd v-else-if="auditNodeState==4" ref="xxbgYbgd" @showUserInfo="showUserInfo"></xxbg-ybgd>
    <!-- 外包人员信息变更面试打分 -->
    <xxbg-interview v-else-if="auditNodeState==5" ref="xxbgInterview" @showUserInfo="showUserInfo"></xxbg-interview>
    <!-- 外包人员信息变更打分结果录入 -->
    <xxbg-df-jglr v-else-if="auditNodeState==6" ref="xxbgDfJglr" @showUserInfo="showUserInfo"></xxbg-df-jglr>
    <!-- 外包人员信息变更打分二级部审批 -->
    <xxbg-df-two-manager v-else-if="auditNodeState==7" ref="xxbgDfTwoManager" @showUserInfo="showUserInfo"></xxbg-df-two-manager>
    <!-- 外包人员信息变更打分综合部确认 -->
    <xxbg-df-zhb-confirm v-else-if="auditNodeState==8" ref="xxbgDfZhbConfirm" @showUserInfo="showUserInfo"></xxbg-df-zhb-confirm>
    <!-- 外包人员信息变更打分分管总经理审批 -->
    <xxbg-df-fg-manager v-else-if="auditNodeState==9" ref="xxbgDfFgManager" @showUserInfo="showUserInfo"></xxbg-df-fg-manager>
    <!-- 外包人员信息变更打分总经理审批 -->
    <xxbg-df-manager v-else-if="auditNodeState==10" ref="xxbgDfManager" @showUserInfo="showUserInfo"></xxbg-df-manager>
    <!-- 面试打分已办归档 -->
    <ms-df-ybgd v-else-if="auditNodeState==11" ref="msDfYbgd" @showUserInfo="showUserInfo"></ms-df-ybgd>
    <!-- 录入结果打分已办归档 -->
    <lrjg-yb-gd v-else-if="auditNodeState==12" ref="lrjgYbgd" @showUserInfo="showUserInfo"></lrjg-yb-gd>
    <!-- 二级部审批有打分已办归档 -->
    <two-manager-ybgd v-else-if="auditNodeState==13" ref="twoManagerYbgd" @showUserInfo="showUserInfo"></two-manager-ybgd>
    <!-- 分管总经理和总经理审批已办归档 -->
    <xxbg-manager-ybgd v-else-if="auditNodeState==14" ref="xxbgManagerYbgd" @showUserInfo="showUserInfo"></xxbg-manager-ybgd>
    <div>
      <!-- 人员档案 -->
      <el-dialog :visible.sync="rydaVisible" width="80%" @close="rydaClose">
        <hmc-archives ref="ryda" :rydaDisplay="false"></hmc-archives>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import xxbgAdd from "@/views/crewManager/wbryxxbg/xxbgAdd";
  import xxbgSaveBack from "@/views/crewManager/wbryxxbg/xxbgSaveBack";
  import xxbgTwoManager from "@/views/crewManager/wbryxxbg/xxbgTwoManager";
  import xxbgZhbConfirm from "@/views/crewManager/wbryxxbg/xxbgZhbConfirm";
  import xxbgYbgd from "@/views/crewManager/wbryxxbg/xxbgYbgd";
  import xxbgInterview from "@/views/crewManager/wbryxxbg/xxbgInterview";
  import xxbgDfJglr from "@/views/crewManager/wbryxxbg/xxbgDfJglr";
  import xxbgDfTwoManager from "@/views/crewManager/wbryxxbg/xxbgDfTwoManager";
  import xxbgDfZhbConfirm from "@/views/crewManager/wbryxxbg/xxbgDfZhbConfirm";
  import xxbgDfFgManager from "@/views/crewManager/wbryxxbg/xxbgDfFgManager";
  import xxbgDfManager from "@/views/crewManager/wbryxxbg/xxbgDfManager";
  import msDfYbgd from "@/views/crewManager/wbryxxbg/msDfYbgd";
  import lrjgYbgd from "@/views/crewManager/wbryxxbg/lrjgYbgd";
  import twoManagerYbgd from "@/views/crewManager/wbryxxbg/twoManagerYbgd";
  import xxbgManagerYbgd from "@/views/crewManager/wbryxxbg/xxbgManagerYbgd";
  import hmcArchives from "@/views/crewManager/hmcArchives/hmcArchives";
  export default {
    components: {
      xxbgAdd,
      xxbgSaveBack,
      xxbgTwoManager,
      xxbgZhbConfirm,
      xxbgYbgd,
      xxbgInterview,
      xxbgDfJglr,
      xxbgDfTwoManager,
      xxbgDfZhbConfirm,
      xxbgDfFgManager,
      xxbgDfManager,
      msDfYbgd,
      lrjgYbgd,
      twoManagerYbgd,
      xxbgManagerYbgd,
      hmcArchives
    },
    data() {
      return {
        auditNodeState: "", // 节点状态
        flowItem: this.$route.query, // 流程对象
        badge: '',       // 人员档案工号
        rydaVisible: false,      // 人员档案dialog 显示
      };
    },
    mounted() {
      console.log("外包人员信息变更流程", this.flowItem);
      this.init();
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem);
        } else if (
          this.flowItem.status == "yb" &&
          (this.flowItem.activityDefID == "manualActivity2" ||
            this.flowItem.activityDefID == "manualActivity")
        ) {
          this.flowItem.actionURL = "xxbgYbgd";
          this.$nextTick(_ => {
            console.log("发起已办归档");
            this.showNodeAudit(this.flowItem);
          });
        } else if (
          this.flowItem.status == "yb" &&
          (this.flowItem.activityDefID == "manualActivity1" ||
            this.flowItem.activityDefID == "manualActivity3")
        ) {
          this.flowItem.actionURL = "xxbgtwoManagerYbgd";
          this.$nextTick(_ => {
            console.log("二级部已办归档");
            this.showNodeAudit(this.flowItem);
          });
        } else if (
          this.flowItem.status == "yb" &&
          this.flowItem.activityDefID == "manualActivity111"
        ) {
          this.flowItem.actionURL = "xxbgInterviewYbgd";
          this.$nextTick(_ => {
            console.log("面试打分已办归档");
            this.showNodeAudit(this.flowItem);
          });
        } else if (
          (this.flowItem.status == "yb" || this.flowItem.status == "gd") &&
          this.flowItem.activityDefID == "manualActivity4"
        ) {
          this.flowItem.actionURL = "managerYbgd";
          this.$nextTick(_ => {
            console.log("分管总经理和总经理审批已办归档");
            this.showNodeAudit(this.flowItem);
          });
        } else if (this.flowItem.status == "gd") {
          this.flowItem.actionURL = "managerYbgd";
          this.$nextTick(_ => {
            console.log("已办归档");
            this.showNodeAudit(this.flowItem);
          });
        } else {
          if (this.flowItem.actionURL == undefined) {
            this.flowItem.actionURL = 'wbxxbgAdd';
          }
          this.showNodeAudit(this.flowItem);
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
            { name: "wbxxbgAdd" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.xxbgAdd.init(item);
              });
            }
          ],
          [
            { name: "applysave" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.xxbgSaveBack.init(item);
              });
            }
          ],
          [
            { name: "xxbgYbgd" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.xxbgYbgd.init(item);
              });
            }
          ],
          [
            { name: "twoManageConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.xxbgTwoManager.init(item);
              });
            }
          ],
          [
            { name: "xxbgtwoManagerYbgd" },
            () => {
              self.auditNodeState = 13;
              this.$nextTick(_ => {
                this.$refs.twoManagerYbgd.init(item);
              });
            }
          ],
          [
            { name: "zhConfirm" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.xxbgZhbConfirm.init(item);
              });
            }
          ],
          [
            { name: "personInCharge" },
            () => {
              self.auditNodeState = 5;
              this.$nextTick(_ => {
                this.$refs.xxbgInterview.init(item);
              });
            }
          ],
          [
            { name: "xxbgInterviewYbgd" },
            () => {
              self.auditNodeState = 11;
              this.$nextTick(_ => {
                this.$refs.msDfYbgd.init(item);
              });
            }
          ],
          [
            { name: "input" },
            () => {
              self.auditNodeState = 6;
              this.$nextTick(_ => {
                this.$refs.xxbgDfJglr.init(item);
              });
            }
          ],
          [
            { name: "fgConfirm" },
            () => {
              self.auditNodeState = 9;
              this.$nextTick(_ => {
                this.$refs.xxbgDfFgManager.init(item);
              });
            }
          ],
          [
            { name: "managerYbgd" },
            () => {
              self.auditNodeState = 14;
              this.$nextTick(_ => {
                this.$refs.xxbgManagerYbgd.init(item);
              });
            }
          ],
          [
            { name: "confirm" },
            () => {
              self.auditNodeState = 10;
              this.$nextTick(_ => {
                this.$refs.xxbgDfManager.init(item);
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
      },
      /**
       * 外包档案
       */
      showUserInfo(v) {
        let self = this
        self.rydaVisible = true
        self.$nextTick(_ => {
          self.$refs['ryda'].badge = v
        })
      },
      /**
       * 人员档案dialog关闭
       */
      rydaClose() {
        this.rydaVisible = false;
      }
    }
  };
</script>
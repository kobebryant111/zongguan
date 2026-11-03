<!-- 数据中心门禁权限申请 聚合页-->
<template>
  <!-- 发起 -->
  <access-permission-launch v-if="auditNodeState==-1" ref="accessPermissionLaunch"></access-permission-launch>
  <!-- 保存退回 -->
  <access-permission-save-back v-else-if="auditNodeState==1" ref="accessPermissionSaveBack"></access-permission-save-back>
  <!-- 二级部经理审批 -->
  <ap-two-manager v-else-if="auditNodeState==2" ref="apTwoManager"></ap-two-manager>
  <!-- 管理员评估 -->
  <admin-evaluation v-else-if="auditNodeState==3" ref="adminEvaluation"></admin-evaluation>
  <!-- 管理员实施 -->
  <admin-implement v-else-if="auditNodeState==4" ref="adminImplement"></admin-implement>
  <!-- 分管总经理审核 -->
  <charge-manager-audit v-else-if="auditNodeState==5" ref="chargeManagerAudit"></charge-manager-audit>
  <!-- 数据中心总经理审批 -->
  <data-center-manager-audit v-else-if="auditNodeState==6" ref="dataCenterManagerAudit"></data-center-manager-audit>
  <!-- 已办归档 -->
  <ap-yb-gd v-else-if="auditNodeState==7" ref="apYbGd"></ap-yb-gd>
</template>
<script>
import accessPermissionLaunch from "@/views/accessPermission/accessPermissionLaunch"; //发起
import accessPermissionSaveBack from "@/views/accessPermission/accessPermissionSaveBack"; //保存退回
import apTwoManager from "@/views/accessPermission/apTwoManager"; //二级部经理审批
import adminEvaluation from "@/views/accessPermission/adminEvaluation"; //管理员评估
import adminImplement from "@/views/accessPermission/adminImplement"; //管理员实施
import chargeManagerAudit from "@/views/accessPermission/chargeManagerAudit"; //分管总经理审核
import dataCenterManagerAudit from "@/views/accessPermission/dataCenterManagerAudit"; //数据中心总经理审批
import apYbGd from "@/views/accessPermission/apYbGd"; //数据中心总经理审批

export default {
  components: {
    accessPermissionLaunch,
    accessPermissionSaveBack,
    apTwoManager,
    adminEvaluation,
    adminImplement,
    chargeManagerAudit,
    dataCenterManagerAudit,
    apYbGd
  },
  data() {
    return {
      auditNodeState: "", // 节点状态
      flowItem: {} // 流程对象
    };
  },
  mounted() {
    console.log("数据中心门禁权限申请流程", this.flowItem, this.$route.params);
    if (this.$route.params.processDefName != undefined) {
      this.flowItem = this.$route.params;
    } else {
      this.flowItem = this.$route.params;
      this.flowItem.actionURL = "accessPermissionLaunch";
    }

    this.init();
  },
  methods: {
    init() {
      if (
        this.flowItem.status == "db" &&
        this.flowItem.activityDefID == "manualActivity"
      ) {
        this.flowItem.actionURL = "accessPermissionSaveBack";
        this.$nextTick(_ => {
          console.log("db", this.flowItem);
          this.showNodeAudit(this.flowItem);
        });
      } else if (
        this.flowItem.status == "db" &&
        this.flowItem.activityDefID == "manualActivity1"
      ) {
        this.flowItem.actionURL = "apTwoManager";
        this.$nextTick(_ => {
          console.log("db", this.flowItem);
          this.showNodeAudit(this.flowItem);
        });
      } else if (
        this.flowItem.status == "db" &&
        this.flowItem.activityDefID == "manualActivity2"
      ) {
        this.flowItem.actionURL = "adminEvaluation";
        this.$nextTick(_ => {
          console.log("db", this.flowItem);
          this.showNodeAudit(this.flowItem);
        });
      } else if (
        this.flowItem.status == "db" &&
        this.flowItem.activityDefID == "manualActivity3"
      ) {
        this.flowItem.actionURL = "dataCenterManagerAudit";
        this.$nextTick(_ => {
          console.log("db", this.flowItem);
          this.showNodeAudit(this.flowItem);
        });
      } else if (
        this.flowItem.status == "db" &&
        this.flowItem.activityDefID == "manualActivity4"
      ) {
        this.flowItem.actionURL = "chargeManagerAudit";
        this.$nextTick(_ => {
          console.log("db", this.flowItem);
          this.showNodeAudit(this.flowItem);
        });
      } else if (
        this.flowItem.status == "db" &&
        this.flowItem.activityDefID == "manualActivity5"
      ) {
        this.flowItem.actionURL = "adminImplement";
        this.$nextTick(_ => {
          console.log("db", this.flowItem);
          this.showNodeAudit(this.flowItem);
        });
      } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
        this.flowItem.actionURL = "apYbGd";
        this.$nextTick(_ => {
          this.showNodeAudit(this.flowItem);
        });
      } else {
        this.showNodeAudit(this.flowItem);
      }
    },
    /**
     * 显示对应节点的审批内容
     */
    showNodeAudit: function(item) {
      let url = item.actionURL;
      let self = this;
      const flowMap = new Map([
        [
          { name: "accessPermissionLaunch" },
          () => {
            self.auditNodeState = -1;
            this.$nextTick(_ => {
              this.$refs.accessPermissionLaunch.init();
            });
          }
        ],
        [
          { name: "accessPermissionSaveBack" },
          () => {
            self.auditNodeState = 1;
            this.$nextTick(_ => {
              this.$refs.accessPermissionSaveBack.init(item);
            });
          }
        ],
        [
          { name: "apTwoManager" },
          () => {
            self.auditNodeState = 2;
            this.$nextTick(_ => {
              this.$refs.apTwoManager.init(item);
            });
          }
        ],
        [
          { name: "adminEvaluation" },
          () => {
            self.auditNodeState = 3;
            this.$nextTick(_ => {
              this.$refs.adminEvaluation.init(item);
            });
          }
        ],
        [
          { name: "adminImplement" },
          () => {
            self.auditNodeState = 4;
            this.$nextTick(_ => {
              this.$refs.adminImplement.init(item);
            });
          }
        ],
        [
          { name: "chargeManagerAudit" },
          () => {
            self.auditNodeState = 5;
            this.$nextTick(_ => {
              this.$refs.chargeManagerAudit.init(item);
            });
          }
        ],
        [
          { name: "dataCenterManagerAudit" },
          () => {
            self.auditNodeState = 6;
            this.$nextTick(_ => {
              this.$refs.dataCenterManagerAudit.init(item);
            });
          }
        ],
        [
          { name: "apYbGd" },
          () => {
            self.auditNodeState = 7;
            this.$nextTick(_ => {
              this.$refs.apYbGd.init(item);
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
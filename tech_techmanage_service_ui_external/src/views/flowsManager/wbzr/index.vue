<!-- 外包员工准入-->
<template>
  <div class="app-container">
    <flow-list
      processDefName="com.example.ppm.bps.wbryzrlc"
      :addFun="add"
      :jump-db="jumpDb"
      :dbFun="db"
      :get-dateil="chakan"
      ref="flowList"
    ></flow-list>
    <!-- dialog -->
    <div class="dialog-container">
      <el-dialog :visible.sync="closeVisible" width="80%" @close="dialogClose">
        <!-- 添加、编辑、发起申请-->
        <add
          :flow-obj="flowItem"
          :title-disabled="titleDisabled"
          v-if="auditNodeState==-1"
          ref="detail"
        ></add>
        <!-- 发起申请-退回 -->
        <apply :flow-obj="flowItem" v-else-if="auditNodeState==0"  ref="apply"></apply>
        <!-- 二级部经理审批 -->
        <audit-node2 :flow-obj="flowItem" v-else-if="auditNodeState==2"  ref="approve2"></audit-node2>
        <!-- 综合管理部人事信息审核 -->
        <audit-node5 :flow-obj="flowItem" v-else-if="auditNodeState==5"  ref="approve5"></audit-node5>
        <!-- 综合管理部风控征信审核 -->
        <audit-node1 :flow-obj="flowItem" v-else-if="auditNodeState==1"  ref="approve1"></audit-node1>
        <!-- 分管总经理审核 -->
        <audit-node3 :flow-obj="flowItem" v-else-if="auditNodeState==3"  ref="approve3"></audit-node3>
        <!-- 结算为人力总经理审批 -->
        <audit-node6 :flow-obj="flowItem" v-else-if="auditNodeState==6"  ref="approve6"></audit-node6>
        <!-- 结算为人力总经理审批 -->
        <audit-node-end :flow-obj="flowItem" v-else-if="auditNodeState==7"  ref="approve7"></audit-node-end>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import flowList from "@/views/plugins/flowPlug/FlowList";
import add from "@/views/flowsManager/wbzr/add";
import apply from "@/views/flowsManager/wbzr/apply";
import auditNode2 from "@/views/flowsManager/wbzr/auditNode2";
import auditNode5 from "@/views/flowsManager/wbzr/auditNode5";
import auditNode1 from "@/views/flowsManager/wbzr/auditNode1";
import auditNode3 from "@/views/flowsManager/wbzr/auditNode3";
import auditNode6 from "@/views/flowsManager/wbzr/auditNode6";
import auditNodeEnd from "@/views/flowsManager/wbzr/auditNodeEnd";

export default {
  name: "wbryzr",
  components: {
    flowList,
    add,
    auditNode2,
    auditNode5,
    auditNode1,
    apply,
    auditNode3,
    auditNode6,
    auditNodeEnd
  },
  data() {
    return {
      flowData: [], // 初始数据
      closeVisible: false, // dialog 默认关闭
      flowItem: {}, //
      titleDisabled: false, // 是否显示流程主题
      cleanForm: false, // 是否重置 form表单
      auditNodeState: -1, // 节点审批状态，只显示当前审批节点的组件内容
      apply : 0,        // 添加、编辑 控件 key
      appStatus : 'apply',
    };
  },
  mounted() {},
  methods: {
    /**
     * 执行待办
     */
    jumpDb: function(row) {
      this.flowItem = row;
      this.flowItem.status = "db";
      this.closeVisible = true;
      this.titleDisabled = true;

      // 显示对应节点的审批内容
      this.showNodeAudit(row);
    }, // 执行待办 end
    /**
     * 显示对应节点的审批内容
     */
    showNodeAudit: function(item) {
      let url = item.actionURL || item.flowurl;
      let self = this;
      const flowMap = new Map([
        [
          { name: "apply" },
          () => {
            console.log('apply')
            self.auditNodeState = 0;
            this.$nextTick(_ => {
              this.$refs.apply.init(item);
            });
          }
        ],
        [
          { name: "approve1" },
          () => {
            console.log('approve1')
            self.auditNodeState = 1;
            this.$nextTick(_ => {
              this.$refs.approve1.init(item);
            });
          }
        ],
        [
          { name: "approve2" },
          () => {
            console.log('approve2')
            self.auditNodeState = 2;
            this.$nextTick(_ => {
              this.$refs.approve2.init(item);
            });
          }
        ],
        [
          { name: "approve7" },
          () => {
            console.log('approve7')
            self.auditNodeState = 2;
            this.$nextTick(_ => {
              this.$refs.approve2.init(item);
            });
          }
        ],
        [
          { name: "approve3" },
          () => {
            console.log("approve3");
            self.auditNodeState = 3;
            this.$nextTick(_ => {
              this.$refs.approve3.init(item);
            });
          }
        ],

        [
          { name: "approve5" },
          () => {
            self.auditNodeState = 5;
            this.$nextTick(_ => {
              this.$refs.approve5.init();
            });
          }
        ],
        [
          { name: "approve6" },
          () => {
            self.auditNodeState = 6;
            this.$nextTick(_ => {
              this.$refs.approve6.init(item);
            });
          }
        ],
        [
          { name: "approveEnd" },
          () => {
            self.auditNodeState = 7;
            this.$nextTick(_ => {
              this.$refs.approve7.init(item);
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
     * 流程发起
     */
    add: function() {
      this.closeVisible = true;
      this.auditNodeState = -1;
      this.flowItem.status = "add";
    }, // 流程发起 end
    db: function() {
      this.auditNodeState = 2;
    },
    /**
     * 查看
     */
    chakan: function(row) {
      console.log("row=", row);
      this.flowItem = row;
      this.flowItem.status = "yb";
      this.closeVisible = true;
      this.showNodeAudit(row);
      
    },
    /**
     * 关闭dialog
     */
    dialogClose: function() {
      this.closeVisible = false;
    //   this.$refs.detail.form = {};
    }
  }
};
</script>
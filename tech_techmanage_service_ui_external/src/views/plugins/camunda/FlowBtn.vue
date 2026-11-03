<template>
  <div>
    <div>结论：
      <el-radio-group v-model="conclusion" @change="changeAudit">
        <el-radio label="01">同意</el-radio>
        <el-radio label="02">退回</el-radio>
      </el-radio-group>

      <el-input v-model="suggest" placeholder="同意"></el-input>
      <el-button
        type="dafult"
        @click="circulation"
        v-show="item.status == 'db' || item.status == 'add'"
        icon="el-icon-refresh"
      >流转</el-button>
      <el-button type="dafult" @click="add" v-show="saveVisbleState" icon="el-icon-edit">保存</el-button>
      <el-button type="dafult" @click="auth" icon="el-icon-setting" v-show="item.status == 'db'">授权</el-button>
      <el-button
        type="dafult"
        @click="diaodu"
        v-show="item.status == 'gl'"
        icon="el-icon-refresh"
      >调度</el-button>
      <el-button
        type="dafult"
        @click="deletes"
        v-show="item.status == 'db' && item.activityDefID == 'manualActivity'"
        icon="el-icon-delete"
      >删除</el-button>
      <el-button
        type="dafult"
        @click="drawback"
        v-show="item.status == 'yb' && drawbackState"
        icon="el-icon-refresh-left"
      >撤回</el-button>
    </div>
    <!-- 授权列表 -->
    <template>
      <el-dialog
        title="授权列表"
        :visible.sync="dialogCloseVisible"
        width="30%"
        :modal="false"
        :before-close="closeVisible"
      >
        <!-- 授权列表 -->
        <el-tree
          :data="authListData"
          v-if="authTreeShow"
          default-expand-all
          node-key="pcode"
          :props="defaultProps"
          @node-click="onNodeClick"
        ></el-tree>
        <!-- 授权信息 -->
        <table width="100%" v-else>
          <tr>
            <td>授权对象:</td>
            <td>
              <el-input v-model="authUserName" placeholder="授权对象" disabled></el-input>
            </td>
          </tr>
          <tr>
            <td>授权意见:</td>
            <td>
              <el-input type="textarea" v-model="suggest" placeholder="授权意见"></el-input>
            </td>
          </tr>
        </table>
        <div>
          <el-input v-model="searchData" placeholder="员工号/员工名称/员工名称拼音" style="width:60%"></el-input>
          <el-button @click="onSreach">查 询</el-button>
        </div>
        <span slot="footer">
          <el-button @click="closeVisible">取 消</el-button>
          <el-button type="primary" @click="onDefine">确 定</el-button>
        </span>
      </el-dialog>
    </template>
  </div>
</template>
<script>
import {
  getAuthList,
  doAuth,
  doDel,
  drawback,
  isDrawback
} from "@/api/common/workflowApi";
import { mapGetters } from "vuex";
import common from "@/api/common";

export default {
  name: "FlowBtn",
  props: {
    liuzhuan: {
      type: Function,
      required: true,
      default: _ => {}
    },
    save: {
      type: Function,
      required: false,
      default: _ => {}
    },
    flowItem: {
      type: Object,
      default: {}
    },
    shouquan: {
      type: Function,
      required: false,
      default: _ => {}
    },
    /**
     * 保存按钮是否可见， 默认false：不可见
     */
    saveVisbleState: {
      type: Boolean,
      default: false
    },
    /**
     * 删除业务流程方法
     */
    delFun: {
      type: Function,
      default: _ => {}
    }
  },
  data() {
    return {
      item: this.flowItem,
      drawbackState: false, // 是否可以撤回 默认不可以
      dialogCloseVisible: false, // dialog 关闭状态
      defaultProps: {
        children: "children",
        label: "text"
      },
      authListData: [], // 授权列表数据
      searchData: "", // 授权搜索条件
      authTreeShow: true, // 授权列表是否显示
      authUserName: "", // 授权对象
      authUserId: "", // 授权对象id
      suggest: "同意", // 授权意见 | 审批意见
      conclusion: "01", // 审批结论
      busData: [] // 业务字典- 审批结论
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    ...mapGetters(["deptId", "userId"])
  },
  methods: {
    init: function() {
      // 已办 判断是否可以撤回
      if (this.item.status === "yb") {
        isDrawback({
          workItemId: this.item.activityInstID,
          processInstId: this.item.processInstID
        }).then(res => {
          this.drawbackState = res.data.results.falg;
          this.item.workItemID = res.data.results.workItemId;
          console.log("btn111", this.item, res.data.results);
        });
      }
    },
    /**
     * 流转
     */
    circulation: function() {
      let data = {};
      data.conclusion = this.conclusion;
      data.suggest = this.suggest;
      this.liuzhuan(data);
    },
    /**
     * 保存
     */
    add: function() {
      this.save();
    },
    /**
     * 授权
     */
    auth: function() {
      this.dialogCloseVisible = true;
      // 授权数据
      getAuthList({ deptNo: this.deptId, userData: this.searchData })
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          this.authListData = res.data.results;
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        }); //
      // this.shouquan()
    },
    /**
     * dialog 关闭方法
     */
    closeVisible: function() {
      this.dialogCloseVisible = false;
    },
    /**
     * 单击节点事件
     */
    onNodeClick: function(row, node, _this) {
      console.log(row, node, _this);
      if (row.attr === "dep") {
        return false;
      }
      this.authUserName = row.text;
      this.authUserId = row.pcode;
    },

    /**
     * 授权确定事件
     */
    onDefine: function() {
      // 选择授权对象
      if (this.authTreeShow) {
        console.log("选择授权对象:");
        if (this.authUserId != "" && this.authUserId.length > 0) {
          this.authTreeShow = false;
        }
      } else {
        // 执行授权
        console.log("执行授权对象:");
        this.item.personId = this.authUserId;
        this.item.personName = this.authUserName.substring(
          0,
          this.authUserName.indexOf("(")
        );
        this.item.suggest = this.suggest;
        let data = common.paramsFormat(this.item);
        console.log("data:", data.toString());
        doAuth(data)
          .then(res => {
            if (res.data.status != 200) {
              this.$alert(res.data.message, { type: "error" });
              return false;
            }
            this.$alert(res.data.results, { type: "success" });
          })
          .catch(error => {
            this.$alert(error, { type: "error" });
          });
      }
    },
    /**
     * 查询授权条件
     */
    onSreach: function() {
      // 授权数据
      getAuthList({ userData: this.searchData })
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          this.authListData = res.data.results;
          this.searchData = "";
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
    }, // end
    /**
     * 调度
     */
    diaodu: function() {}, // end
    /**
     * 删除流程
     */
    deletes: function() {
      this.$confirm("确定删除吗？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(_ => {
          console.log("执行删除对象");
          let data = common.paramsFormat(this.item);
          doDel(data).then(res => {
            if (res.data.status != 200) {
              this.$alert(res.data.message, { type: "error" });
              return false;
            }
            this.delFun();
            // // 提示
            // this.$message({
            //   message: res.data.message,
            //   type: "success"
            // });
          });
        })
        .catch(_ => {});
    }, // end
    /**
     * 撤回
     */
    drawback: function() {
      this.$confirm("确定撤回吗？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(_ => {
        let data = common.paramsFormat(this.item);
        drawback(data).then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          // 提示
          this.$message({
            message: res.data.message,
            type: "success"
          });
        });
      });
    }, // end

    /**
     * 选择审核结果
     */
    changeAudit: function(val) {
      this.suggest = "01" === val ? "同意" : "不同意";
    }
  }
};
</script>

<template>
  <div class="approveBottom">
    <div class="nav bottoms">
      <el-row>
        <el-col :span="4" v-if="ifConclusion">
          <div class="wrappers_left">
            <div class="wrappers_left_left">结论 *</div>
            <div>
              <el-radio-group v-model="conclusion" @change="conclusionChange">
                <el-radio
                  v-for="item in conclusion_opts"
                  :label="item.dictid"
                  :key="item.dictid"
                >{{item.dictname}}</el-radio>
              </el-radio-group>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="wrappers_mid">
            <div class="wrappers_mid_left">意见 *</div>
            <div>
              <el-form :model="auditingForm" ref="auditingForm" :rules="rulesAuditing">
                <el-form-item prop="suggest">
                  <el-input
                    type="textarea"
                    v-model="auditingForm.suggest"
                    :autosize="{ minRows: 1, maxRows: 3}"
                  ></el-input>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="wrappers_right">
            <div class="wrappers_right_left">
              <button-common
                :buttonName="empowerName"
                :size="size"
                :fns="getAppList"
                :disabled="ifEmpower"
              ></button-common>
            </div>
            <div class="wrappers_right_left">
              <button-common
                :buttonName="keepName"
                :size="size"
                v-if="saveVisbleState"
                :fns="saveInfo"
              ></button-common>
            </div>
            <div>
              <button-orange :buttonName="liuzhuanName" :size="size" :fns="liuzhuanStart"></button-orange>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 授权弹窗 -->
    <el-dialog
      title="授权"
      :visible.sync="dialogEmpower"
      class="ryzrApprove_dialog"
      :append-to-body="true"
    >
      <div class="power_dia">授权</div>
      <div class="approv_dia">授权审批</div>
      <div class="approv_dia_con">
        <el-form :model="ruleForm" ref="ruleForm" :rules="rules">
          <div class="approv_dia_con_top">
            <p class="approv_dia_con2_p">授权对象 *</p>
            <el-form-item prop="approListData">
              <el-cascader
                ref="cascAppro"
                :options="empowerList"
                :props="approveParams"
                v-model="ruleForm.approListData"
                :show-all-levels="false"
                filterable
                @change="appLiChange"
              ></el-cascader>
            </el-form-item>
          </div>
          <div class="approv_dia_con_bottom">
            <p class="approv_dia_con2_p">授权意见 *</p>
            <el-form-item prop="approveSuggest">
              <el-input type="textarea" autosize v-model="ruleForm.approveSuggest"></el-input>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <div class="dia_button">
        <button-common
          :buttonName="cancelName"
          :size="size"
          :fns="approveCancel"
          class="butt_cancel"
        ></button-common>
        <button-orange :buttonName="sureName" :size="size" :fns="approveSure"></button-orange>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { doAuth, getAuthList } from "@/api/common/flowApi";
import { getDicEntry } from "@/api/eosDictEntry";
import common from "@/api/common";
// import { Loading } from 'element-ui';
// let loading;
export default {
  components: { buttonOrange, buttonCommon },
  name: "approveBottom",
  props: {
    proving: {
      // 流转验证
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
    },
    /**
     * 授权是否禁用， 默认false：不禁用
     */
    ifEmpower: {
      type: Boolean,
      default: false
    },
    /**
     * 结论是否显示， 默认true：显示
     */
    ifConclusion: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      item: this.flowItem, //父组件传来的数据
      size: "mini", //按钮大小
      empowerName: "授 权", //授权按钮
      keepName: "保 存", //保存按钮
      liuzhuanName: "流 转", //流转按钮
      approveSuggest: "", //授权意见
      ruleForm: {
        //验证字段
        approListData: "", //授权列表绑定值
        approveSuggest: "" //授权意见
      },
      rules: {
        approListData: [
          { required: true, message: "请选择授权人", trigger: "change" }
        ],
        approveSuggest: [
          { required: true, message: "请填写意见", trigger: "blur" }
        ]
      },
      dialogEmpower: false, //是否显示授权弹窗
      empowerList: [], //授权列表
      approveParams: {
        //授权列表配置信息
        label: "text",
        value: "text",
        children: "children"
      },
      approListData: "", //授权列表绑定值
      sureName: "确定",
      cancelName: "取消",
      personId: "", //指派的人员id
      personName: "", //指派的人员name
      conclusion: "01", //审核结论
      conclusion_opts: [], //审核结论内容
      auditingForm: {
        suggest: "同意" //审核意见
      },
      rulesAuditing: {
        suggest: [
          { required: true, message: "请填写审批意见", trigger: "blur" }
        ]
      },
      successMessage: "", // 成功消息提示
      errorMessage: "" //失败消息提示
    };
  },
  created() {
    this.init();
  },
  mounted() {
    console.log("审批", this.item);
  },
  computed: {},
  methods: {
    init() {
      let self = this;
      // 查询业务字典
      getDicEntry({ dicttypeid: "BUSI_NEWAPP" }).then(res => {
        if (res.data.status !== 200) {
          this.alter(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // 审核结论
        this.conclusion_opts = data.filter(item => {
          return item.dicttypeid == "BUSI_NEWAPP";
        });
        return data;
      });
    },

    //点击授权按钮获取授权列表并打开弹窗
    getAppList() {
      let params = {};
      // params.deptNo = "130"; //130是总行科技部部门编号
      getAuthList(params)
        .then(res => {
          var kjName = res.data.results[0].text;
          res.data.results[0].children.filter(li => {
            li.text = li.text.replace(kjName + "-", "");
            return li;
          });
          this.empowerList = res.data.results;
        })
        .catch(error => {
          this.$message.error(error);
        }); // end

      this.dialogEmpower = true;
    },

    // 授权列表选中节点发生变化时触发
    appLiChange(node) {
      console.log("node", node);
      let self = this;
      self.personName = node[node.length - 1];
      self.personId = self.personName
        .trim()
        .replace("(", ")")
        .split(")")[1];
      console.log("node111", self.personName, self.personId);
    },
    // 授权
    empower() {
      const loading = common.loading(this);
      let params = {};
      params.personId = this.personId;
      params.personName = this.personName;
      params.workItemId = this.flowItem.workItemID;
      params.processInstID = this.flowItem.processInstID;
      params.processInstName = this.flowItem.processInstName;
      params.processDefName = this.flowItem.processDefName;
      params.activityInstName = this.flowItem.activityInstName;
      params.suggest = this.ruleForm.approveSuggest;
      let data = common.paramsFormat(params);
      doAuth(data)
        .then(res => {
          console.log("授权返回", res.data);
          // 关闭loading
          loading.close();
          if (res.data.status == 200) {
            let message = common.getFlowMessge(res.data.results);
            console.log("message", message);
            let messageArr = message.split(",");
            messageArr[2] = messageArr[2].split(")")[0] + ")";
            this.$nextTick(() => {
              this.messageNotify(messageArr);
              this.$router.push({ name: "dashboard" });
            });
          } else {
            this.errorMessage = "流程授权失败";
            this.messageError();
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 授权取消
    approveCancel() {
      this.dialogEmpower = false;
    },

    // 授权确认
    approveSure() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          console.log("授权确认", valid);
          this.empower();
        } else {
          return false;
        }
      });
    },

    // 审核结论改变
    conclusionChange(val) {
      console.log("审核结论改变", val);
      if (val == "01") {
        this.auditingForm.suggest = "同意";
      } else {
        this.auditingForm.suggest = "不同意";
        this.$set(this.rulesAuditing, "suggest", [
          {
            required: false,
            message: "",
            trigger: ["blur","change"]
          }
        ]);
      }
    },

    saveInfo() {
      this.save();
    },

    // 流转版本2
    liuzhuanStart() {
      let data = {};
      data.conclusion = this.conclusion;
      data.suggest = this.auditingForm.suggest;
      if (this.conclusion == "01") {
        this.$refs["auditingForm"].validate(valid => {
          if (valid) {
            console.log("流转", data);
            this.proving(data).then(val => {
              console.log("流转-val", val);
              if (undefined == val) {
                return false;
              }
              if (val.status == 200) {
                
                if (val.message.indexOf("已结束") == -1) {
                  let message = common.getFlowMessge(val.message);
                  console.log("message", message);
                  let messageArr = message.split(",");
                  this.$nextTick(() => {
                    this.messageNotify(messageArr);
                    // this.$router.back();
                    this.$router.push({ name: "dashboard" });
                  });
                } else {
                  this.messageNotify2(val.message);
                  // this.$router.back();
                  this.$router.push({ name: "dashboard" });
                }
              } else {
                this.errorMessage = "操作失败";
                this.messageError();
              }
            });
          } else {
            return false;
          }
        });
      } else {
        this.proving(data).then(val => {
          console.log("退回", val);
          if (val.status == 200) {
            if (val.message.indexOf("已结束") == -1) {
              let message = common.getFlowMessge(val.message);
              
              console.log("message", message);
              let messageArr = message.split(",");
              this.$nextTick(() => {
                this.messageNotify(messageArr);
                // this.$router.back();
                this.$router.push({ name: "dashboard" });
              });
            } else {
              this.$nextTick(() => {
                this.messageNotify2(val.message);
                // this.$router.back();
                this.$router.push({ name: "dashboard" });
              });
            }
          } else {
            this.errorMessage = "操作失败";
            this.messageError();
          }
        });
      }
    },

    // 消息提示
    messageNotify(messageVal) {
      
      if (messageVal.length > 3) {
        messageVal[messageVal.length - 1] =
          messageVal[messageVal.length - 1].split(")")[0] + ")";
      }
      let messageSec = messageVal.slice(2).join("，");
      console.log("提示信息", messageSec, messageVal);
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
          "<div><div>" +
          messageVal[1] +
          "</div><div>" +
          messageSec +
          "</div></div>",
        duration: 0
      });
    },

    messageNotify2(messageVal) {
      this.$notify({
        title: "提示",
        message: messageVal,
        duration: 0
      });
    },

    // 成功消息提示
    messageSuccess() {
      this.$message({
        message: this.successMessage,
        type: "success"
      });
    },

    // 失败消息提示
    messageError() {
      this.$message.error(this.errorMessage);
    }
  }
};
</script>

<style scoped>
.approveBottom {
  overflow: hidden;
}

.nav {
  width: 100%;
  background-color: #ffffff;
}

.nav .wrapper {
  margin-left: 25px;
  margin-right: 25px;
  display: flex;
  justify-content: space-between;
}

.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}

.bottom {
  padding: 15px 0;
}

/* 授权弹窗 */
.power_dia {
  font-size: 16px;
  color: #262626;
}

.approv_dia {
  font-size: 14px;
  margin-top: 8px;
}

.approv_dia_con_top,
.approv_dia_con_bottom {
  margin-top: 20px;
  color: #262626;
}

.ryzrApprove_dialog {
  overflow: hidden;
}

.dia_button {
  text-align: right;
  margin-top: 70px;
}

.butt_cancel {
  margin-right: 10px;
}

/* 退回弹窗 */
.retreatCon {
  display: flex;
  justify-content: space-around;
  /* flex-direction: row; */
}

.retreatCon_2 {
  width: 87%;
}

.dia_button_type2_zb {
  float: right;
  margin: 25px 0 15px 0;
}

.retreat_dia_titlte_zb {
  font-size: 16px;
  color: #262626;
  margin-top: 4px;
}

.bottoms {
  padding: 0 25px;
}

.wrappers_left,
.wrappers_mid,
.wrappers_right {
  height: 70px;
  line-height: 70px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  font-size: 14px;
}

.wrappers_left_left,
.wrappers_mid_left,
.wrappers_right_left {
  margin-right: 10px;
}
</style>

<style>
/* 弹窗 */
.approveBottom .el-dialog {
  overflow: hidden;
}

.approveBottom .el-dialog__body {
  padding: 0 30px;
}

/* 弹窗关闭按钮 */
.approveCon .el-dialog__headerbtn:focus .el-dialog__close,
.el-dialog__headerbtn:hover .el-dialog__close {
  color: rgba(251, 164, 47, 1);
}

/* 退回弹窗 */
.retreatDialog .el-dialog {
  width: 30%;
}

/* 级联框修改 */
.approveBottom .el-cascader {
  display: block;
}

/* form表单修改 */
.bottoms .el-form-item {
  margin-bottom: 0;
}

.approv_dia_con_top .el-cascader--medium {
  width: 100%;
}
</style>
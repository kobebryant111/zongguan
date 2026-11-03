<!-- 合并信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">费用结算审批</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common
                :buttonName="buttonClose"
                :size="size"
                :fns="close"
              ></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>

    <!-- 支付信息 -->
    <div class="contentMain">
      <el-form :model="formData" ref="formData" :rules="rules">
        <div class="contentTwo_title">
          <p class="contentTwo_title_p">基本信息</p>
        </div>
        <div class="content">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>供应商名称 *</p>
                    <el-form-item prop="vendor">
                      <el-input
                        v-model="formData.vendorName"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>应付总金额 *</p>
                    <el-form-item prop="amount">
                      <el-input v-model="formData.amount" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>税率 *</p>
                    <el-form-item prop="taxRate">
                      <el-col :span="5">
                        <el-input
                          v-model="formData.taxRate"
                          readonly
                        ></el-input>
                      </el-col>
                      <el-col :span="3">&nbsp;%</el-col>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div v-if="false">
                    <p>实付金额（含税） *</p>
                    <el-form-item prop="amountTax">
                      <el-input
                        placeholder="请输入实付金额（含税）"
                        v-model="formData.amountTax"
                        disabled
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>项目经理 *</p>
                    <el-form-item prop="proManagerName">
                      <el-input
                        placeholder="请输入项目经理"
                        v-model="formData.proManagerName"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="24">
                  <div>
                    <p>备注 </p>
                    <el-form-item prop="remark">
                      <el-input
                        type="textarea"
                        :rows="3"
                        placeholder="请输入备注"
                        v-model="formData.remark"
                        :disabled="isProcess"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <!-- 人员信息 -->
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">支付记录</p>
      </div>
      <div class="content">
        <div class="row_1">
          <div>
            <el-table :data="scoreList" style="width: 100%">
              <el-table-column
                prop="proManagerName"
                label="项目经理"            
              ></el-table-column>
              <!-- :formatter="proFormmtter" -->
              <el-table-column
                prop="contractNo"
                label="合同编号"
                :show-overflow-tooltip="true"
              ></el-table-column>
              <el-table-column
                prop="contractName"
                label="合同名称"
                width="180"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                prop="vendor"
                width="200"
                label="供应商名称"
                show-overflow-tooltip
                :formatter="vendorFormmtter"
              ></el-table-column>
              <el-table-column
                prop="feeStartDate"
                label="费用开始日期"
              ></el-table-column>
              <el-table-column
                prop="feeEndDate"
                label="费用结束日期"
              ></el-table-column>
              <el-table-column
                prop="paymentSubmitDate"
                label="支付确认日期"
              ></el-table-column>
              <el-table-column prop="approvalStatus" label="审批状态"
                ><template slot-scope="scope">
                  <span v-if="scope.row.approvalStatus == '01'">审批通过</span>
                  <span v-else-if="scope.row.approvalStatus == '02'">退回</span>
                  <span v-else-if="scope.row.approvalStatus == '03'">撤回</span>
                  <span v-else-if="scope.row.approvalStatus == '04'"
                    >审批中</span
                  >
                  <span v-else-if="scope.row.approvalStatus == '05'">草稿</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="amount"
                label="应付总金额"
              >
              <template slot-scope="scope">
               <span>{{ scope.row.amount.toFixed(2)}}</span>
              </template>
              </el-table-column>
              <el-table-column prop="taxRate" label="税率">
                <template slot-scope="scope">
                  <span>{{ (scope.row.taxRate * 100).toFixed(0) + "%" }}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="amountTax"
                label="实付金额（含税）"
                width="150"
                v-if="false"
              >
              <template slot-scope="scope">
               <span>{{ scope.row.amountTax.toFixed(2)}}</span>
              </template>
              </el-table-column>
              <el-table-column label="操作" width="122">
                <template slot-scope="scope">
                  <!-- <el-link type="warning" :underline="false" @click="handleEdit(scope.row)">删除</el-link> -->
                  <el-link
                    type="warning"
                    :underline="false"
                    @click="handleDetail(scope.row)"
                    >详情</el-link
                  >
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
       <!-- 操作日志 -->
      <div class="czLog" v-show="flowItem.status == 'db' || flowItem.status == 'yb'">
        <flow-logs
          :processInstId="flowItem.processInstID"
          ref="log"
        ></flow-logs>
      </div>
      <!-- 操作日志 -->
      <div class="czLog" v-show="flowItem.status == 'gd'">
          <flow-logs-gd ref="loggd"></flow-logs-gd>
      </div>
    </div>
    <approve-bottom
      v-show="flowItem.status == 'db' && formScene == 'secMergeApproval'"
      :proving="proving"
      :flowItem="flowItem"
    ></approve-bottom>
    <div class="nav-bottom" v-show="flowItem.status == 'yb'">
      <yb-revoke :flowItem="flowItem" :fns="revoke"></yb-revoke>
    </div>
    <div
      class="nav bottom"
      v-show="flowItem.status == 'db' && formScene == 'mergeApproval'"
    >
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <button-delete
                :buttonName="buttonDel"
                :size="size"
                v-show="delBtnIf"
                :fns="delBox"
              ></button-delete>
            </el-col>
          </div>
          <div class="wrapper_right">
            <div>
              <el-col :span="1.5">
                <button-orange
                  :buttonName="buttonCommit"
                  :size="size"
                  :fns="proving"
                ></button-orange>
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
// import {
//   queryResourceDept,
//   queryEmployeeTree
// } from "@/api/integrated/wbryzrlc";
import pager from "@/components/Pager";
import departmentTwo from "@/views/plugins/components/DepartmentTwo";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonDelete from "@/components/eleButton/delete"; //引入删除按钮
import { uploadExcel } from "@/api/integrated/wbms";
import {
  save,
  twoManagerAudit,
  oneManagerAudit,
  queryProcessDetail,
  updateStatus,
  deleteFlow,
} from "@/api/payConfirm/expenseSettle";
import { queryDetailList } from "@/api/payConfirm/payConfirm";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
import { drawback } from "@/api/common/workflowApi";
import { getEmployUser } from "@/api/common/flowApi";
import approveBottom from "@/views/plugins/camunda/approveBottom";
import ybRevoke from "@/views/fileManager/common/ybRevoke";
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import Moment from "moment";
export default {
  name: "ProjectInfo",
  components: {
    flowLogs,
    FlowLogsGd,
    ybRevoke,
    approveBottom,
    departmentTwo,
    buttonOrange,
    buttonCommon,
    buttonDelete,
  },
  data() {
    return {
      depId: null,
      formData: {},
      managerData: {},
      deptmentData: {},
      size: "mini", //按钮大小
      title: "添加",
      buttonClose: "关 闭", //关闭按钮名字
      buttonDel: "删 除",
      buttonCancel: "取 消",
      buttonCommit: "提 交",
      buttonSure: "确 定",
      outsourceno_options: [], //外包商
      payConfirmId: [],
      scoreList: [],
      proManagerName: "", //名称格式
      fileData: [
        { key: 0, value: "总办议案" },
        { key: 1, value: "发票" },
        { key: 2, value: "其他" },
      ],
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      delBtnIf: true, //删除按钮是否出现
      isProcess: false,
      flowItem: {
        processInstID: "",
      }, //bps数据
      instanceId: "", //流程ID
      workItemID: "", //工作项id
      formScene: "", //场景
      processDefName: "", //流程定义名称
      processInstName: "", //流程实例名称
      activityInstName: "", //审批节点描述
      activityInstId: "", //当前活动实例ID
      activityDefID: "", //当前节点ID
      recordId: "",
      taskId: "",
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getWbcom();
    this.init();
  },
  methods: {
    /** 初始 */
    init() {  
      console.log(this.$route.query, "99999999");
      this.instanceId = this.$route.query.processInstanceId || this.$route.query.instanceid;
      this.flowItem = this.$route.query;
      this.flowItem.processInstID = this.$route.query.processInstanceId;
      this.workItemID = this.$route.query.businessKey;
      this.formScene = this.$route.query.formScene;
      this.processDefName = this.$route.query.processDefineKey;
      this.processInstName = this.$route.query.processDefineName;
      this.activityInstName = this.$route.query.activityName;
      this.activityInstID = this.$route.query.activityId;
      this.activityDefID = this.$route.query.activityId;
      this.recordId = this.$route.query.businessKey;
      this.taskId = this.$route.query.taskId;
      if (
          status == "yb" ||
          status == "gd" ||
          this.flowItem.formScene == "secMergeApproval"
        ) {
          this.isProcess = true;
        }
      if (this.instanceId) {
        let params = {};
        params.instanceId = this.instanceId;
        queryProcessDetail(params)
          .then(async (res) => {
            console.log(res, "res数据2121");
            this.formData = await res.data.results[0];
            this.formData.taxRate = Number(this.formData.taxRate * 100);
            queryDetailList(this.formData.payConfirmId).then((res) => {
              this.scoreList = res.data.results;
              console.log(res, "支付确认信息");
            });
          })
          .catch((error) => {}); // end
        this.$nextTick((_) => {
          // 流程日志
           if(this.flowItem.status == 'db' || this.flowItem.status == 'yb'  ){
              this.$refs.log.init(this.instanceId);
          }else if(this.flowItem.status == 'gd' ){
                 this.$refs.loggd.init(this.flowItem);
          }
        });
      }
    },
    // 查询外包商
    getWbcom() {
      getListAll()
        .then((res) => {
          this.outsourceno_options = res.data.results;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    /** 提交 */
    onSubmit() {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          const loading = common.loading(this);
          const data = {
            ...this.formData,
            payConfirmId: this.payConfirmId.join(","),
          };
          twoManagerAudit(data).then((res) => {
            loading.close();
            if (res.data.status == "200") {
              let message = res.data.message;
              if (message.indexOf(",") != -1) {
                let messageArr = message.split(",");
                this.messageNotifyS(messageArr);
              } else {
                this.$message.success(message);
              }
              this.$router.push({ name: "expenseSettle" });
            } else {
              this.$message.error(res.data.message);
            }
          });
        }
      });
    },
    // 关闭按钮
    close() {
       this.$router.push({ name: "dashboard" });
    },
    // excel上传
    handleDetail(params) {
      this.$router.push({
        path: "payConfirmDetail",
        query: params,
      });
    },
    // 验证必填项
    proving(data) {
      console.log(111111, data);
      let status = new Promise(() => {});
      status = this.liuzhuan(data).then((val) => {
        return val;
      });
      return status;
    },
    // 流转或者退回
    liuzhuan(data) {
      const loading = common.loading(this);
      let params = {
        ...this.formData,
        id: this.formData.id,
        instanceId: this.instanceId,
        activityInstID: this.activityInstID,
        activityInstName: this.activityInstName,
        recordId: this.recordId,
        totals: this.formData.totals,
      };
      if (data) {
        this.flowItem.conclusion = data.conclusion;
        this.flowItem.suggest = data.suggest;
      }
      params.bps = this.flowItem;
      params.bps.taskId = this.taskId;
      console.log(this.formScene,this.flowItem.conclusion, "参数是");
      if (this.activityInstName == "一级部负责人审批") {
        return oneManagerAudit(params).then((res) => {
          loading.close();
          let message = res.data.message;
          let flag = true;
          if (message.indexOf("," ) != -1 && this.flowItem.conclusion !='02') {
           let  messageArr = message.split(",");
            this.messageNotifyS(messageArr);
            flag =false;
          } else if(message.indexOf("," ) != -1 && flag){
             flag =false;
          }else if(flag){
             this.$message.success(message);
             flag =false;
          }
          this.$router.push({ name: "dashboard" });
          return res.data;
        });
      } else {
        return twoManagerAudit(params).then((res) => {
          loading.close();
          let message = res.data.message;
          if (message.indexOf(",") != -1) {
            // let messageArr = message.split(",");
             let messageArr = common.getFlowMessgeNew(message);
            this.messageTipsUpRight(messageArr);
          } else {
            this.$message.success(message);
          }
          this.$router.push({ name: "dashboard" });
          return res.data;
        });
      }
    },
     // 点击流转时, 页面右上角的消息提示
    messageTipsUpRight(messageVal) {
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
          "<div><div>" +
          messageVal   +
          "</div><div>",
        duration: 0,
      });
    },
    // 打开删除messageBox窗口
    delBox() {
      this.$confirm("确认删除该流程吗?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.delPro();
        })
        .catch(() => {});
    },
    // 删除流程
    delPro() {
      let params = {};
      params.id = this.workItemID;
      params.instanceId = this.instanceId;
      params.payConfirmId = this.formData.payConfirmId;
      deleteFlow(params).then((res) => {
        if (res.data.status == 200) {
          this.successMessage = "流程删除成功";
          this.saveSuccess();
          this.$router.push({ name: "dashboard" });
        } else {
          this.errorMessage = "流程删除失败";
          this.saveError();
        }
      });
    },
    revoke() {
      let params = {};
      const loading = common.loading(this);
      params.processInstId = this.instanceId || this.flowItem.processInstanceId;
      params.conclusion = "04";
      let data = common.paramsFormat(params);
      drawback(data)
        .then((res) => {
          loading.close();
          if (res.data.status == 200) {
            const data = {
              instanceId: this.instanceId || this.flowItem.processInstanceId,
              approvalStatus: "03", // 撤回
            };
            let message = res.data.message;
            this.messageNote = message;
            this.$nextTick(() => {
              this.messageNotify();
              this.$router.push({ name: "dashboard" });
            });
          } else {
            this.errorMessage = "操作失败";
            this.messageError();
          }
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    // 成功消息提示
    saveSuccess() {
      this.$message({
        showClose: true,
        message: this.successMessage,
        type: "success",
      });
    },

    // 失败消息提示
    saveError() {
      this.$message({
        showClose: true,
        message: this.errorMessage,
        type: "error",
      });
    },
    //消息提示
    messageNotify() {
      this.$notify({
        title: "提示",
        message: this.messageNote,
        duration: 0,
      });
    },
    //审批消息提示
    //消息提示
    messageNotifyS(messageVal) {
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
          "<div><div>" +
          messageVal[1] +
          "</div><div>" +
          messageVal[3] +
          "</div></div>",
        duration: 0,
      });
    },
    //  proFormmtter(row) {
    //   console.log(row, "行数据");
    //   let proManagerName = [];
    //   getEmployUser(row.proManager).then(async (res) => {
    //     if (res.data.status == 200) {
    //       let data = await res.data.results[0]
    //       proManagerName.push(data.empname);
    //     }
    //   });
    //   console.log(proManagerName,'9999999')
    //   return proManagerName.join();
    // },
    vendorFormmtter(row) {
      return this.formData.vendorName;
    },
  },
};
</script>
<style scoped>
/* bin修改 */
.contentMain {
  width: 100%;
  height: 100%;
  font-size: 16px;
  padding: 10px 20px;
}

/* 导航菜单栏 */
.nav {
  width: 100%;
  /* height: 30px; */
  background-color: #ffffff;
}

.nav .nav_name {
  width: 180px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
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

.wrapper_right_bottom_1_zb {
  margin-right: 10px;
}
.content {
  width: 100%;
  font-size: 16px;
}

.content p {
  color: #262626;
  font-size: 14px;
  margin: 12px 0;
}

.content .row_2_detail {
  font-size: 16px;
  font-weight: 600;
}

.contentTwo_title {
  height: 60px;
  line-height: 60px;
  background-color: #ffffff;
  padding: 0 25px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.contentTwo_title_p {
  color: #262626;
  font-weight: 600;
}

.content .row_1 {
  background-color: #ffffff;
  padding: 0 20px;
}

.contentMain .row_2 {
  background-color: #ffffff;
  padding: 0 20px 15px 20px;
  margin-top: 15px;
}

.add_row {
  color: #faad14;
  font-size: 14px;
  font-weight: 500;
  margin-left: 10px;
  cursor: pointer;
}

/* 空格占位 */
.blank {
  width: 100%;
  height: 160px;
}

.bottom {
  /* position: absolute;
  bottom: 0; */
  padding: 15px 0;
}
/* 上传文件按钮 */
.againUpLoad .el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}
</style>
<style>
.el-table__footer-wrapper tbody td.el-table__cell {
  background-color: #fff;
}
</style>
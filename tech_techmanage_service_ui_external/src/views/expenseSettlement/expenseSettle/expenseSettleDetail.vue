<!-- 合并信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">费用详情</p>
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
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>项目经理 *</p>
                    <el-form-item prop="proManager">
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
                        readonly
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
            <el-table :data="payConfirmList" style="width: 100%">
              <!-- :summary-method="getSummaries"
              show-summary -->
              <el-table-column
                prop="proManagerName"
                label="项目经理"
              ></el-table-column>
              <el-table-column
                prop="contractNo"
                label="合同编号"
                width="200"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                prop="contractName"
                label="合同名称"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                prop="vendor"
                width="200"
                label="供应商名称"
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
              <el-table-column prop="amount" label="应付总金额">
                <template slot-scope="scope">
                  <span>{{ scope.row.amount.toFixed(2) }}</span>
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
                  <span>{{ scope.row.amountTax.toFixed(2) }}</span>
                </template>
              </el-table-column>
              <!-- <el-table-column label="操作" width="122">
                <template slot-scope="scope">
                  <el-link type="warning" :underline="false" @click="handleEdit(scope.row)">删除</el-link>
                  <el-link type="warning" :underline="false" @click="handleDetail(scope.row)">详情</el-link>
                </template>
              </el-table-column> -->
            </el-table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import pager from "@/components/Pager";
import departmentTwo from "@/views/plugins/components/DepartmentTwo";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import { uploadExcel } from "@/api/integrated/wbms";
import { randomNumber } from "@/utils";
import { queryDetailList } from "@/api/payConfirm/expenseSettle";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
import { getEmployUser } from "@/api/common/flowApi";
import Moment from "moment";
export default {
  name: "ProjectInfo",
  components: { departmentTwo, buttonOrange, buttonCommon, FlowLogsGd },
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
      payConfirmList: [],
      fileData: [
        { key: 0, value: "总办议案" },
        { key: 1, value: "发票" },
        { key: 2, value: "其他" },
      ],
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      // 表单校验
      rules: {},
      proManagerName: "",
      vendorName: "",
    };
  },
  created() {
    this.getWbcom();
    this.init();
    // this.getDeptments();
    // if (this.formData.depId) {
    //   this.title = "修改";
    //   this.depId = this.formData.depId;
    //   let depId = String(this.formData.depId);
    //   this.formData.depId = depId;
    // }
  },
  methods: {
    /** 初始化 */
    init() {
      this.id = this.$route.query.id;
      if (this.id) {
        const data = {
          id: this.id,
        };
        queryDetailList(data).then((res) => {
          this.formData = res.data.results;
          /** 保留小数点2位 */
            if (this.formData.amount) {
              this.formData.amount = this.formData.amount.toFixed(2);
            }
            if (this.formData.amountTax) {
              this.formData.amountTax = this.formData.amountTax.toFixed(2);
            }
          this.formData.taxRate = Number(this.formData.taxRate) * 100;
          this.payConfirmList = this.formData.payConfirmList;
        });
      }
      this.$forceUpdate();
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
              this.$message.success("操作成功！");
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
      this.$router.push({ name: "expenseSettle" });
    },
    // excel上传
    // 上传文件
    uploadFile(file) {},

    // 上传文件状态改变
    changeFile(file, fileList) {
      let nowDate = new Date();
      // let objectNo = Moment(nowDate.toLocaleString()).format('YYMMDDHH');
      let objectNo = nowDate.getTime();
      var formData = new FormData();
      formData.append("file", file.raw);
      formData.append("objectNo", objectNo); //唯一编号
      formData.append("type", "RLWBXQ");
      formData.append("catalogNo", "CATALOG2019043000001");
      let data = formData;
      const loading = common.loading(this);
      uploadExcel(data).then((res) => {
        console.log(res, "上传结果");
        //   loading.close();
        //   this.importfxx(file.raw);
        //   if (res.data.status == "200") {
        //     this.ifFinish = false;
        //     this.docmId = res.data.results;
        //     this.currentRow.jl = res.data.results;
        //     this.successMessage = "文件上传成功";
        //     this.messageSuccess();
        //   } else {
        //     this.errorMessage = "文件上传失败";
        //     this.messageError();
        //   }
      });
    },
    numFilter(value) {
      let tempVal = parseFloat(value).toFixed(3);
      let realVal = tempVal.substring(0, tempVal.length - 1);
      return realVal;
    },
    handleDetail(params) {
      this.$router.push({
        path: "personInfo",
        query: params,
      });
    },
    // proFormmtter(row) {
    //   getEmployUser(row.proManager).then((res) => {
    //     if (res.data.status == 200) {
    //       this.proManagerName = res.data.results[0].empname;
    //     }
    //   });
    //   return this.proManagerName;
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
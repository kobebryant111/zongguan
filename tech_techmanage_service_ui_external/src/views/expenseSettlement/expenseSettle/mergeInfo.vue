<!-- 合并信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">合并信息</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
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
                    <el-form-item
                      prop="vendor"
                    >
                      <el-input v-model="formData.vendorName"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>应付总金额 *</p>
                    <el-form-item
                      prop="amount"
                    >
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
                      <el-input v-model="formData.taxRate" readonly></el-input>
                      </el-col>
                      <el-col :span="3">&nbsp;%</el-col>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div v-if="false">
                    <p>实付金额（含税） *</p>
                    <el-form-item
                      prop="amountTax"
                    >
                      <el-input placeholder="请输入实付金额（含税）" v-model="formData.amountTax" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>项目经理 *</p>
                    <el-form-item
                      prop="proManagerName"
                    >
                      <el-input placeholder="请输入项目经理" v-model="formData.proManagerName" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="24">
                  <div>
                    <p>备注 </p>
                    <el-form-item
                      prop="remark"
                    >
                      <el-input
                        type="textarea"
                        :rows="3"
                        maxlength="200"
                        placeholder="请输入备注"
                        v-model="formData.remark"
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
            <el-table
              :data="scoreList"
              style="width: 100%"         
            >
              <el-table-column prop="proManagerName" label="项目经理"></el-table-column>
              <el-table-column prop="contractNo"  label="合同编号" width="150"></el-table-column>
              <el-table-column prop="contractName" label="合同名称" width="180" show-overflow-tooltip></el-table-column>
              <el-table-column prop="vendorName"  label="供应商名称" width="200" show-overflow-tooltip></el-table-column>
              <el-table-column prop="feeStartDate" label="费用开始日期" width="150"></el-table-column>
              <el-table-column prop="feeEndDate" label="费用结束日期" width="150"></el-table-column>
              <el-table-column prop="paymentSubmitDate" label="支付确认日期" width="150"></el-table-column>
              <el-table-column prop="approvalStatus" label="审批状态">
                <template slot-scope="scope">
                  <span>审批通过</span>
                  <!-- <span v-else-if="scope.row.approvalStatus == '02'">退回</span>
                  <span v-else-if="scope.row.approvalStatus == '03'">撤回</span>
                  <span v-else-if="scope.row.approvalStatus == '04'">审批中</span>
                  <span v-else-if="scope.row.approvalStatus == '05'">草稿</span> -->
                </template>
              </el-table-column>
              <el-table-column prop="amount" label="应付总金额" width="150">
                 <template slot-scope="scope">
                  <span>{{ scope.row.amount.toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="taxRate" label="税率">
                <template slot-scope="scope">
                  <span>{{ (scope.row.taxRate * 100).toFixed(0) + "%" }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="amountTax" label="实付金额（含税）" width="150" v-if="false">
                <template slot-scope="scope">
                  <span>{{ scope.row.amountTax.toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="122">
                <template slot-scope="scope">
                  <!-- <el-link type="warning" :underline="false" @click="handleEdit(scope.row)">删除</el-link> -->
                  <el-link type="warning" :underline="false" @click="handleDetail(scope.row)">详情</el-link>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>

    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5"></el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5">
                <button-common :buttonName="buttonCancel" :size="size" :fns="close"/>
              </el-col>
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonCommit" :size="size" :fns="onSubmit"/>
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
import pager from "@/components/Pager";
import departmentTwo from "@/views/plugins/components/DepartmentTwo";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import { uploadExcel } from "@/api/integrated/wbms";
import { randomNumber } from "@/utils";
import { save,twoManagerAudit } from "@/api/payConfirm/expenseSettle";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
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
      scoreList: [],
      fileData: [
        { key: 0, value: "总办议案" },
        { key: 1, value: "发票" },
        { key: 2, value: "其他" }
      ],
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getWbcom();
    console.log("this.$route", this.$route, this.$route.params)
    this.scoreList = this.$route.params.selectData;
    if('undefined' === typeof(this.scoreList)) {
      this.scoreList = this.$route.params.selectData
    }
    this.init();
  },
  methods: {
    init(){
      console.log(this.scoreList,'09999999')
      this.formData.vendorCode = this.scoreList[0].vendor;
      this.formData.vendorName = this.scoreList[0].vendorName;
      this.formData.taxRate = Number(this.scoreList[0].taxRate) * 100;
      this.formData.proManager = this.scoreList[0].proManager;
      /** 项目经理拼接 */
      let proManagerName =[];
       proManagerName = this.objHeavy(this.scoreList);
       this.formData.proManagerName = proManagerName.map((item)=>{
        return item.proManagerName
      }).join(",");
      this.formData.amount = this.scoreList.reduce((c,item)=> c + item.amount * 1,0);
      this.formData.amount = this.formData.amount.toFixed(2);
      this.formData.amountTax = this.scoreList.reduce((c,item)=> c + item.amountTax * 1,0);
      this.formData.amountTax = this.formData.amountTax.toFixed(2);
      this.scoreList.map((item)=>{
       this.payConfirmId.push(item.id)
      })
      this.$forceUpdate();
    },
    /** 项目经理去重 */
    objHeavy(arr){
      let arr1 = [];
      let newArr = [];
      for(let i in arr){
        if(arr1.indexOf(arr[i].proManager)== -1){
          arr1.push(arr[i].proManager);
          newArr.push(arr[i]);
        }
      }
      return newArr;
    },
    // 查询外包商
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results;
        })
        .catch(error => {
          this.$message.error(error);
        });
    },
    /** 提交 */
    onSubmit() {
      this.$refs["formData"].validate(valid => {
        if (valid) {
          const loading = common.loading(this);
          const data = {
            ...this.formData,
            payConfirmId: this.payConfirmId.join(','),
          }
          twoManagerAudit(data).then(res => {
            loading.close();
            if (res.data.status == "200") {
              let messageArr =res.data.message.split(",");
               this.messageNotifyS(messageArr);
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
    numFilter(value) {
      let tempVal = parseFloat(value).toFixed(3);
      let realVal = tempVal.substring(0, tempVal.length - 1);
      return realVal;
    },
    handleDetail(params) {
       let text = this.$router.resolve({
        path: "payConfirmDetail",
        query: { id: params.id },
      });
      window.open(text.href, "_blank");
    },
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
  }
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
<!-- 支付确认信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">支付确认</p>
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
      <el-form :model="formData" ref="formData">
        <div class="contentTwo_title">
          <p class="contentTwo_title_p">基本信息</p>
        </div>
        <div class="content">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>合同编号 *</p>
                    <el-form-item prop="contractNo">
                      <el-input
                        placeholder="请输入"
                        v-model="formData.contractNo"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>

                <el-col :span="11">
                  <div>
                    <p>合同名称 *</p>
                    <el-form-item prop="contractName" ref="msgForm">
                      <el-input
                        placeholder="请填写合同名称"
                        v-model="formData.contractName"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>供应商名称 *</p>
                    <el-form-item prop="vendor">
                      <el-select
                        v-model="formData.vendor"
                        placeholder="请选择供应商"
                        disabled
                      >
                        <el-option
                          v-for="item in outsourceno_options"
                          :key="item.id"
                          :label="item.outsourcname"
                          :value="item.orgcode"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>项目经理 *</p>
                    <el-form-item prop="proManager">
                      <el-cascader
                        ref="cascader"
                        :options="requirePeo_options"
                        :props="requirePeoParams"
                        v-model="formData.proManager"
                        :show-all-levels="false"
                        @change="peoChange"
                        disabled
                      ></el-cascader>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>费用开始日期 *</p>
                    <el-form-item prop="feeStartDate">
                      <el-date-picker
                        v-model="formData.feeStartDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        readonly
                      />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>费用结束日期 *</p>
                    <el-form-item prop="feeEndDate">
                      <el-date-picker
                        v-model="formData.feeEndDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        readonly
                      />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>支付提交日期 *</p>
                    <el-form-item prop="paymentSubmitDate">
                      <el-date-picker
                        v-model="formData.paymentSubmitDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        readonly
                      />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>应付金额 *</p>
                    <el-form-item prop="amountRaw">
                      <el-input
                        v-model="formData.amountRaw"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>

              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>调整金额 *</p>
                    <el-form-item prop="amountSalt">
                      <el-input
                        v-model="formData.amountSalt"
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
                          placeholder="请输入税率"
                          v-model.number="formData.taxRate"
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
                        v-model="formData.amountTax"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="24">
                  <div>
                    <p>备注 *</p>
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
        <!-- 附件信息 -->
        <div class="contentTwo_title" v-if="false">
          <p class="contentTwo_title_p">附件信息</p>
        </div>
        <div class="content" v-if="false">
          <div class="row_1">
            <div>
              <el-row :gutter="50">
                <el-col :span="8">
                  <div>
                    <p class="file_title">合同附件 *</p>
                    <el-form-item>
                      <div
                        v-for="(item, index) in contractDocList"
                        :key="index"
                      >
                        {{ item.docname }}
                        <span
                          class="preview"
                          @click="openNtkoWindow(item, 'baomi')"
                          >预览</span
                        >
                        <span class="download" @click="downloadFile(item.docid)"
                          >下载</span
                        >
                        <!-- <span
                          class="xkg-color-yellow xkg-marginleft-5px delButt"
                          @click="delUpload(item.code)"
                        >删除</span> -->
                      </div>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row :gutter="50">
                <el-col :span="8">
                  <div>
                    <p  class="file_title">附件文档 *</p>
                    <el-form-item>
                      <div v-for="(item, index) in attachDocList" :key="index">
                        {{ item.docname }}
                        <span
                          class="preview"
                          @click="openNtkoWindow(item, 'baomi')"
                          >预览</span
                        >
                        <span class="download" @click="downloadFile(item.docid)"
                          >下载</span
                        >
                      </div>
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
        <p class="contentTwo_title_p">
          人员信息
          <button-common
            :buttonName="buttonAttendExport"
            :size="size"
            :fns="handelAttendExport"
          ></button-common>
          <button-common
            :buttonName="buttonPersonsExport"
            :size="size"
            :fns="handelPersonsExport"
          ></button-common>
        </p>
      </div>
      <div class="content">
        <div class="row_1">
          <div>
            <el-table :data="payConfirmList" style="width: 100%">
              <el-table-column prop="userName" label="姓名"></el-table-column>
              <el-table-column
                prop="paymentYear"
                label="支付年份"
                :show-overflow-tooltip="true"
              ></el-table-column>
              <el-table-column
                prop="paymentMonth"
                label="支付月份"
              ></el-table-column>
              <el-table-column prop="price" label="单价">
                <template slot-scope="scope">
                <span>{{ scope.row.price.toFixed(2)}}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="requiredAttendanceDays"
                label="应出勤"
              ></el-table-column>
              <el-table-column
                prop="actualAttendanceDays"
                label="人力实际出勤天数"
              ></el-table-column>
              <el-table-column
                prop="actualPrice"
                label="应付金额"
              >
               <template slot-scope="scope">
               <span>{{ scope.row.actualPrice.toFixed(2)}}</span>
              </template>
              </el-table-column>
              <!-- <el-table-column label="操作" width="122">
                <template slot-scope="scope">
                  <el-link
                    type="warning"
                    :underline="false"
                    @click="openNtkoWindow(scope.row.jl)"
                  >详情</el-link>
                </template>
              </el-table-column> -->
            </el-table>
          </div>
        </div>
      </div>
    </div>
    <!-- 预览图片弹窗 -->
    <el-dialog title="" :visible.sync="ylDialog">
      <div class="ylDialogCon">
        <el-image
          :src="ylPhotoUrl"
          style="width: 600px; height: 400px"
          :fit="photoFit"
          :preview-src-list="ylPhotoUrl.split(',')"
        />
      </div>
    </el-dialog>
  </div>
</template>
<script>
import common from "@/api/common";
import {
  queryResourceDept,
  queryEmployeeTree,
} from "@/api/integrated/wbryzrlc";
import {
  queryDetail,
  onePaymentConfirm,
  queryPersons,
  twoManagerAudit,
  exportExcelPersons,
  exportAttendanceDetails,
} from "@/api/payConfirm/payConfirm";
import { queryListByMapSmall } from "@/api/supplierManager/contractManage";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import { randomNumber, toPoint } from "@/utils";
import { getFlowData, getListAll, preview } from "@/api/common/workflowApi";
import { getEmployUser } from "@/api/common/flowApi";
import approveBottom from "@/views/plugins/camunda/approveBottom";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "ProjectInfo",
  components: { buttonOrange, buttonCommon, FlowLogsGd, approveBottom },
  data() {
    return {
      id: null,
      formData: {
        proManager: [],
      },
      managerData: {},
      deptmentData: {},
      size: "mini", //按钮大小
      title: "添加",
      buttonClose: "关 闭", //关闭按钮名字
      buttonDel: "删 除",
      buttonCancel: "取 消",
      buttonDraft: "存草稿",
      buttonCommit: "提 交",
      buttonSure: "确 定",
      buttonAttendExport: "人员考勤明细导出",
      buttonPersonsExport: "人力结算费用导出",
      outsourceno_options: [], //外包商
      requirePeo_options: [], //需求发起人数据
      requirePeoParams: {
        //需求发起人配置信息
        label: "text",
        value: "id",
        children: "children",
        // leaf: 'id'
      },
      nodeVals: [], //选中的label值
      userid: "",
      username: "",
      payConfirmList: [],
      fileData: [
        { key: 0, value: "总办议案" },
        { key: 1, value: "发票" },
        { key: 2, value: "其他" },
      ],
      approve_options: [
        {
          value: "0",
          label: "审批中",
        },
        {
          value: "1",
          label: "审批完成",
        },
      ],
      headers: { "Content-Type": "multipart/form-data" },
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      attachDocList: [], //上传文件列表
      contractDocList: [],
      contractId: "",
      ylDialog: false, //是否显示预览图片弹窗
      ylPhotoUrl: "", //预览图片地址
      photoFit: "scale-down",
      flowItem: {}, //bps数据
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
    };
  },
  created() {
    this.getWbcom();
    this.id = this.$route.query.id;
    this.userid = this.$store.getters.userId;
    this.reqSearch(this.userid);
    if (this.id) {
      this.getDetail(this.id);
    }
    this.searchEpmlo();
  },
  methods: {
    // 查询部门人员
    getEmployees(depId) {
      if (this.depId && depId != this.depId) {
        this.$set(this.formData, "badge", "");
      }
      queryEmployeeTree(depId)
        .then((res) => {
          if (res.data.status == 200) {
            this.depId = depId;
            var fild = res.data.results[0].children;
            this.managerData = fild.filter((li) => {
              li.id = li.id.replace("emp", "");
              return li;
            });
          } else {
            this.$message.error("获取部门数据失败");
          }
        })
        .catch((error) => {
          this.$message.error("当前部门下面无数据!");
        });
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
    //消息提示
    messageNotify(messageVal) {
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
          "<div><div>" +
          messageVal[1] +
          "</div><div>" +
          messageVal[2] +
          "</div></div>",
        duration: 0,
      });
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: "payConfirm" });
    },
    /** 查询详情信息 */
    getDetail(id) {
      this.attachDocList = [];
      this.contractDocList = [];
      queryDetail(id)
        .then((res) => {
          this.formData = res.data.results;
          /** 保留小数点2位 */
          if (this.formData.amount) {
            this.formData.amount = this.formData.amount.toFixed(2);
          }
          if (this.formData.amountRaw) {
            this.formData.amountRaw = this.formData.amountRaw.toFixed(2);
          }
          if (this.formData.amountSalt) {
            this.formData.amountSalt = this.formData.amountSalt.toFixed(2);
          }
          if (this.formData.amountTax) {
            this.formData.amountTax = this.formData.amountTax.toFixed(2);
          }
          console.log(this.formData, "详情信息");
          this.reqSearch(this.formData.proManager);
          this.changeContractNo(this.formData.contractNo);
          this.payConfirmList = this.formData.payConfirmPersonList;
          /** 获取附件 */
          if(this.formData.attachDocList != undefined && this.formData.attachDocList.length >0){
            this.formData.attachDocList.forEach((item)=>{
            this.getFileName(item.code);
          });
          }
          if(this.formData.contractDocList != undefined && this.formData.contractDocList.length >0){
           this.formData.contractDocList.forEach((item)=>{
            this.getContractFileName(item.code)
          });
          }

          this.formData.taxRate = Number(this.formData.taxRate) * 100;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    /** 获取合同文件名称 */
    getContractFileName(id) {
      preview(id).then((res) => {
        if (res.data.status == 200) {
          this.contractDocList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2,
          });
        }
      });
    },
    // 获取文件名称
    getFileName(id) {
      preview(id).then((res) => {
        if (res.data.status == 200) {
          this.attachDocList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2,
          });
        }
      });
    },

    // 删除上传
    delUpload(docid) {
      let fileList = this.fileList.filter((item) => item.docid != docid);
      this.fileList = fileList;
      this.cleanData(type);
    },
    // 清洗数据
    cleanData(type) {
      let fileList = [];
      this.fileList.forEach((item) => {
        fileList.push(item.docid);
      });
      this.$set(this.formData, "doc", fileList);
      this.$refs["formData"].validateField("fileList");
    },
    /** 下载文件 **/
    downloadFile(docId) {
      console.log(docId, "xxxxx");
      const self = this;
      common.downloadDoc(docId, self);
    },
    /** ntko插件预览 **/
    openNtkoWindow(obj, mark) {
      let formJudge = obj.docname.split(".")[obj.docname.split(".").length - 1];
      if (
        formJudge == "jpg" ||
        formJudge == "JPG" ||
        formJudge == "jpeg" ||
        formJudge == "JPEG" ||
        formJudge == "png" ||
        formJudge == "PNG"
      ) {
        this.ylDialog = true;
        let pos = obj.tag1.lastIndexOf("/");
        let str = obj.tag1.substr(0, pos);
        if (obj.tag2 != undefined) {
          this.ylPhotoUrl = str + "/" + obj.tag2;
        } else {
          this.ylPhotoUrl = obj.tag1;
        }
      } else if (
        formJudge == "zip" ||
        formJudge == "rar" ||
        formJudge == "txt"
      ) {
        this.$message.error("不能预览压缩文件或txt文件格式");
      } else {
        this.ntkoYl(obj.docid);
      }
    },
    // ntko预览方法
    ntkoYl(objectId) {
      var url = window.location.href;
      let ntkoed = ntkoBrowser.ExtensionInstalled();
      if (ntkoed) {
        ntkoBrowser.openWindow(
          url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
        );
      } else {
        window.open(
          url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
        );
      }
      // 注册回调方法
      window.ntkoCloseEvent = function () {
        alert("跨浏览器插件应用程序窗口已关闭!");
      };
    },
    // 查询行员
    searchEpmlo() {
      let params = "";
      params = "7004";
      queryEmployeeTree(params)
        .then((res) => {
          this.organizers_options = res.data.results;
          let data = res.data.results;
          let name = data[0].text;
          this.requirePeo_options = data[0].children.filter((tt) => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    // 需求发起人节点变化
    peoChange(v) {
      this.nodeVals = this.getCascaderObj(
        this.formData.proManager,
        this.requirePeo_options
      );
      this.userid = this.nodeVals[1].pcode;
      this.username = this.nodeVals[1].text;
      // this.dept = this.nodeVals[0].text;
      // var depId = this.nodeVals[1].pid;
      // this.deptno = depId.slice(3);
    },
    // 获取当前节点Label值方法
    getCascaderObj(val, opt) {
      return val.map(function (value, index, array) {
        for (var itm of opt) {
          if (itm.id == value) {
            opt = itm.children;
            return itm;
          }
        }
        return null;
      });
    },
    // 需求发起人的默认值
    reqSearch(kerword) {
      getEmployUser(kerword)
        .then((res) => {
          if (res.data.status == 200) {
            let data = res.data.results[0];
            let acquiesce = [];
            acquiesce[0] = "dep" + data.depid;
            acquiesce[1] = "emp" + data.badge;
            this.formData.proManager = acquiesce;
            // this.dept = data.chargeName.split("-")[1];
            this.deptno = data.depid;
            this.userid = data.badge;
          }
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    /** 总金额 */
    changeAmount() {
      if (this.formData.amountRaw) {
        this.formData.amount =
          Number(this.formData.amountRaw) + Number(this.formData.amountSalt);
        this.changeAmountTax();
      }
      this.$forceUpdate();
    },
      /** 总金额（含税） */
    changeAmountTax() {
      if (this.formData.amount && this.formData.taxRate) {
        this.formData.amountTax =
          this.formData.amount * (1 + Number(this.formData.taxRate));
      }
      this.$forceUpdate();
    },
    /** 获取合同Id */
    changeContractNo(value) {
      const params = {
        contractNo: value,
      };
      queryListByMapSmall(params)
        .then((res) => {
          if (res.data.status == "200") {
            this.contractId = res.data.results[0].id;
          }
        })
        .catch((error) => {
          this.$message.error("错误!");
        });
    },
    /** 考勤结算费用导出 */
    handelAttendExport() {
      let arr = [];
      this.payConfirmList.forEach(item =>{
        arr.push(item.jobId);
      })
      let arr1 = new Set(arr);
      /** 获取合同id */
      const data = {
        contractNo: this.contractId,
        proManager: this.userid,
        feeStartDate: this.formData.feeStartDate
          .replace(/\-/g, ""),
          // .slice(0, -2),
        feeEndDate: this.formData.feeEndDate.replace(/\-/g, ""),
        // .slice(0, -2),
        jobIdList: arr1,
      };
      const self = this;
      exportAttendanceDetails(data).then((res) => {
        common.download(res.data, self, "人员考勤明细.xls");
      });
    },
    /** 人力结算费用导出  */
    handelPersonsExport() {
      let arr = [];
      this.payConfirmList.forEach(item =>{
        arr.push(item.jobId);
      })
      let arr1 = new Set(arr);
      // console.log(arr1);
      const data = {
        contractNo: this.formData.contractNo,
        proManager: this.userid,
        feeStartDate: this.formData.feeStartDate
          .replace(/\-/g, ""),
          // .slice(0, -2),
        feeEndDate: this.formData.feeEndDate.replace(/\-/g, ""),
        // .slice(0, -2),
        jobIdList: arr1,
      };
      const self = this;
      exportExcelPersons(data).then((res) => {
        common.download(res.data, self, "人力结算费用.xls");
      });
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
.file_title{
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
  position: absolute;
  /* bottom: 0; */
  padding: 15px 0;
}
.preview,
.download {
  color: #fba42f;
  font-size: 14px;
  margin-left: 5px;
  cursor: pointer;
}
.el-cascader,.el-autocomplete{
  width: 100%;
}
/* 上传文件按钮 */
.againUpLoad .el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}
</style>
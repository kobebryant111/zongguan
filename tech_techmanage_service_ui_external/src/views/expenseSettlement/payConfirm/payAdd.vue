<!-- 添加支付确认信息 -->
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
                    <p>合同编号 *</p>
                    <el-form-item
                      prop="contractNo"
                      :rules="[
                        {
                          required: true,
                          message: '请填写合同编号',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-select
                        v-model="formData.contractNo"
                        placeholder="请选择"
                        @change="changeContractNo"
                        filterable
                        :disabled="isUpdate || isProcess"
                      >
                        <el-option
                          v-for="item in contractno_options"
                          :key="item.id"
                          :label="item.contractNo"
                          :value="item.contractNo"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>

                <el-col :span="11">
                  <div>
                    <p>合同名称 *</p>
                    <el-form-item
                      prop="contractName"
                      :rules="[
                        {
                          required: false,
                          message: '请填写合同名称',
                          trigger: 'blur',
                        },
                      ]"
                      ref="msgForm"
                    >
                      <!-- <el-input
                        placeholder="请填写合同名称"
                        v-model="formData.contractName"
                        :readonly="isUpdate"
                      ></el-input> -->
                      <el-autocomplete
                        v-model="formData.contractName"
                        :fetch-suggestions="querySearchAsync"
                        placeholder="请输入内容"
                        @select="handleSelect"
                        :disabled="isUpdate || isProcess"
                      ></el-autocomplete>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>供应商名称 *</p>
                    <el-form-item
                      prop="vendor"
                      :rules="[
                        {
                          required: true,
                          message: '请选择供应商',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-select
                        v-model="formData.vendor"
                        placeholder="请选择供应商"
                        filterable
                        @change="$forceUpdate()"
                        disabled
                      >
                        <!-- :disabled="isUpdate || isProcess" -->
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
                        filterable
                        @change="peoChange"
                        :disabled="isProcess"
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
                        @change="changeDate"
                        :disabled="isUpdate || isProcess"
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
                        @change="changeDate"
                        :disabled="isUpdate || isProcess"
                      />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>支付提交日期 *</p>
                    <el-form-item
                      prop="paymentSubmitDate"
                      :rules="[
                        {
                          required: true,
                          message: '请选择',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-date-picker
                        v-model="formData.paymentSubmitDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        :disabled="isProcess"
                      />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>应付金额 *</p>
                    <el-form-item
                      prop="amountRaw"
                      :rules="[
                        {
                          required: true,
                          message: '请输入应付总金额',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        v-model="formData.amountRaw"
                        @change="changeAmount"
                        :disabled="isProcess"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>调整金额</p>
                    <el-form-item
                      prop="amountSalt"
                      :rules="[
                        {
                          required: false,
                          message: '请输入调整金额',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        v-model="formData.amountSalt"
                        @change="changeAmount"
                        :disabled="isProcess"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>应付总金额 *</p>
                    <el-form-item
                      prop="amount"
                      :rules="[
                        {
                          required: false,
                          message: '请输入应付总金额',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        v-model="formData.amount"
                        @change="changeAmountTax"
                        disabled
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>税率 *</p>
                    <el-form-item
                      prop="taxRate"
                      :rules="[
                        {
                          required: true,
                          message: '请输入税率',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-col :span="5">
                        <el-input
                          placeholder="请输入税率"
                          v-model.number="formData.taxRate"
                          onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')"
                          @change="changeRate"
                          :disabled="isProcess"
                        ></el-input>
                      </el-col>
                      <el-col :span="3">&nbsp;&nbsp;%</el-col>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div v-if="false">
                    <p>实付金额（含税） *</p>
                    <el-form-item
                      prop="amountTax"
                      :rules="[
                        {
                          required: false,
                          message: '请输入实付金额',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        v-model="formData.amountTax"
                        disabled
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="24">
                  <div>
                    <p>备注 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[
                        {
                          required: true,
                          message: '请输入备注）',
                          trigger: 'change',
                        },
                      ]"
                    >
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
                    <el-form-item prop="doc">
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
                      </div>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row :gutter="50">
                <el-col :span="8">
                  <div>
                    <p class="file_title">附件文档 *</p>
                    <el-form-item prop="doc">
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
          <span v-if="payConfirmList.length > 0">
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
          </span>
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
                  <span>{{ scope.row.price.toFixed(2) }}</span>
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
              <el-table-column prop="actualPrice" label="应付金额">
                <template slot-scope="scope">
                  <span>{{ scope.row.actualPrice.toFixed(2) }}</span>
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
              </el-table-column>-->
            </el-table>
          </div>
        </div>
      </div>

      <!-- 操作日志 -->
      <div
        class="czLog"
        v-show="flowItem.status == 'db' || flowItem.status == 'yb'"
      >
        <flow-logs
          :processInstId="flowItem.processInstID"
          ref="log"
        ></flow-logs>
      </div>
      <!-- 操作日志 -->
      <div class="czLog" v-if="flowItem.status == 'gd'">
        <flow-logs-gd ref="loggd"></flow-logs-gd>
      </div>
    </div>
    <!-- 添加提交 -->
    <div class="nav bottom" v-if="!instanceId">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5"></el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5">
                <button-common
                  :buttonName="buttonDraft"
                  :size="size"
                  :fns="onDraft"
                />
              </el-col>
            </div>
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5">
                <button-common
                  :buttonName="buttonCancel"
                  :size="size"
                  :fns="close"
                />
              </el-col>
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange
                  :buttonName="buttonCommit"
                  :size="size"
                  :fns="proving"
                />
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>
    <!-- 流程提交 -->
    <approve-bottom
      v-show="flowItem.status == 'db' && formScene == 'secPayConfirm'"
      :proving="proving"
      :flowItem="flowItem"
    ></approve-bottom>
    <!-- 撤回 -->
    <div class="nav-bottom" v-show="flowItem.status == 'yb'">
      <yb-revoke :flowItem="flowItem" :fns="revoke"></yb-revoke>
    </div>
    <!-- 删除流程 -->
    <div
      class="nav bottom"
      v-show="flowItem.status == 'db' && formScene == 'firPayConfirm'"
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
  save,
  updatePayment,
  queryDetail,
  uploadExcel,
  draft,
  onePaymentConfirm,
  queryPersons,
  queryProcessDetail,
  approvalCirculate,
  updateStatus,
  getDbDetails,
  deleteFlow,
  twoManagerAudit,
  exportExcelPersons,
  exportAttendanceDetails,
} from "@/api/payConfirm/payConfirm";
import { queryListByMapSmall } from "@/api/supplierManager/contractManage";
import { drawback, preview } from "@/api/common/workflowApi";
// import { reBackTask } from "@/api/supplierManager/fileManage";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonDelete from "@/components/eleButton/delete"; //引入删除按钮
import { randomNumber, toPoint } from "@/utils";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
import { getEmployUser, getDb } from "@/api/common/flowApi";
import approveBottom from "@/views/plugins/camunda/approveBottom";
import ybRevoke from "@/views/fileManager/common/ybRevoke";
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "ProjectInfo",
  components: {
    flowLogs,
    FlowLogsGd,
    approveBottom,
    ybRevoke,
    buttonOrange,
    buttonCommon,
    buttonDelete,
  },
  data() {
    return {
      id: null,
      formData: {
        proManager: [],
        contractNo: null,
        contractName: null,
        vendor: null,
        feeStartDate: null,
        feeEndDate: null,
        paymentSubmitDate: null,
        amountRaw: null,
        amountSalt: null,
        amount: null,
        taxRate: null,
        amountTax: null,
        remark: null,
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
      isUpdate: false, // 是否可修改
      isProcess: false, // 是否审批
      outsourceno_options: [], //外包商
      contractno_options: [], //合同
      requirePeo_options: [], //需求发起人数据
      requirePeoParams: {
        //需求发起人配置信息
        label: "text",
        value: "id",
        children: "children",
        // leaf: 'id'
      },
      nodeVals: [], //选中的label值
      restaurants: [],
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
      attachDocList: [], //上传文件列表
      contractDocList: [],
      contractId: "", // 合同id
      ylDialog: false, //是否显示预览图片弹窗
      ylPhotoUrl: "", //预览图片地址
      photoFit: "scale-down",
      // 表单校验
      rules: {
        effectiveTime: [
          { required: true, message: "请选择生效日期", trigger: "blur" },
        ],
        expiryTime: [
          { required: true, message: "请选择失效日期", trigger: "blur" },
        ],
      },
      taskList: [],
      delBtnIf: true, //删除按钮是否出现
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
      successMessage: "", // 成功消息提示
      errorMessage: "", //失败消息提示
      messageNote: "", //新版消息提示
    };
  },
  created() {
    this.getWbcom();
    this.getContractNo();
    this.searchEpmlo();
    this.init();
  },
  methods: {
    init() {
      this.id = this.$route.query.id;
      this.instanceId =
        this.$route.query.processInstanceId || this.$route.query.instanceid;
      this.userid = this.$store.getters.userId;
      this.reqSearch(this.userid);
      /** 编辑 */
      if (this.id && !this.instanceId) {
        this.title = "修改";
        this.isUpdate = true;
        this.getDetail(this.id);
      } else if (!this.id) {
        this.isUpdate = false;
      }
      /** 流程实例 */
      if (this.instanceId) {
        this.flowItem = this.$route.query;
        console.log(this.flowItem, "流程状态");
        let status = this.flowItem.status;
        if (
          status == "yb" ||
          status == "gd" ||
          this.flowItem.formScene == "secPayConfirm"
        ) {
          this.isProcess = true;
        }
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
        let params = {};
        /** 清空附件 */
        this.attachDocList = [];
        this.contractDocList = [];
        queryProcessDetail(this.instanceId)
          .then(async (res) => {
            this.formData = await res.data.results;
            this.reqSearch(this.formData.proManager);
            this.AmountCalcula(this.formData);
            /** 保留小数点2位 */
            this.formData.taxRate = Number(this.formData.taxRate) * 100;
            // this.payConfirmList = this.formData.payConfirmList;
            this.payConfirmList = this.formData.payConfirmPersonList;

            /** 获取附件 */
            if (this.formData.attachDocList) {
              this.formData.attachDocList.forEach((item) => {
                this.getFileName(item.code);
              });
            }
            if (this.formData.contractDocList) {
              this.formData.contractDocList.forEach((item) => {
                this.getContractFileName(item.code);
              });
            }
            /** 获取合同id */
            this.getContractId(this.formData.contractNo);
            /** 获取支付信息 */
            queryPersons(data).then((res) => {
              this.payConfirmList = res.data.results;
            });
          })
          .catch((error) => {}); // end
        this.$nextTick((_) => {
          // 流程日志
          if (this.flowItem.status == "db" || this.flowItem.status == "yb") {
            this.$refs.log.init(this.instanceId);
          } else if (this.flowItem.status == "gd") {
            this.$refs.loggd.init(this.flowItem);
          }
        });
      }
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
    getContractNo() {
      const data = {
        isDel: "0",
      };
      queryListByMapSmall(data).then((res) => {
        this.contractno_options = res.data.results;
        console.log(this.contractno_options, "合同选项121212");
      });
    },
    changeContractNo(value) {
      const params = {
        contractNo: value,
      };
      queryListByMapSmall(params)
        .then(async (res) => {
          console.log(res.data, "合同信息");
          let data = await res.data.results[0];
          this.contractId = data.id;
          this.formData.contractName = data.contractName;
          this.formData.vendor = data.vendor;
          this.clearForm();
          this.$forceUpdate();
        })
        .catch((error) => {
          this.$message.error("错误!");
        });
    },
    //获取部门
    getDeptments() {
      let params = {};
      params.auth = true;
      params.objectgrade = "2";
      params.allowDept = "80000122";
      params.objectcode = "6000000300";
      queryResourceDept(params)
        .then((res) => {
          if (res.data.status == 200) {
            var fild = res.data.results;
            if (res.data.results[0].children) {
              var fild = res.data.results[0].children;
            }
            var reg = /([^-]+)$/;
            this.deptmentData = fild.filter((li) => {
              li.id = li.id.replace("dep", "");
              li.text = li.text.match(reg)[1] + "(" + li.id + ")";
              return li;
            });
          } else {
            this.$message.error("获取部门数据失败");
          }
        })
        .catch((error) => {
          this.$message.error("获取部门数据失败");
        });
    },
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
    /** 提交 */
    onSubmit() {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          console.log(this.formData, "form表单数据");
          const data = {
            ...this.formData,
            proManager: this.userid,
          };
          const loading = common.loading(this);
          if (this.id == null) {
            save(data).then((res) => {
              loading.close();
              if (res.data.status == "200") {
                let message = common.getFlowMessgeNew(res.data.message);
                let messageArr = message.split(",");
                this.$nextTick(() => {
                  this.messageNotify(messageArr);
                  this.$router.push({ name: "payConfirm" });
                });
              } else {
                this.$message.error(res.data.message);
              }
            });
          } else {
            updatePayment(data).then((res) => {
              loading.close();
              if (res.data.status == "200") {
                this.$message.success("操作成功！");
                this.$router.push({ name: "payConfirm" });
              } else {
                this.$message.error(res.data.message);
              }
            });
          }
        }
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

    // 关闭按钮
    close() {
      if (this.instanceId) {
        this.$router.push({ name: "dashboard" });
      } else {
        this.$router.push({ name: "payConfirm" });
      }
    },
    onDraft() {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          const loading = common.loading(this);
          const data = {
            ...this.formData,
            proManager: this.userid,
            payConfirmPersonList: this.payConfirmList,
          };
          draft(data).then((res) => {
            console.log(res, "支付确认草稿返回");
            loading.close();
            if (res.data.status == "200") {
              this.$message.success("操作成功！");
              this.$router.push({ name: "payConfirm" });
            } else {
              this.$message.error(res.data.message);
            }
          });
        }
      });
    },
    /** 查询详情信息 */
    getDetail(id) {
      this.attachDocList = [];
      this.contractDocList = [];
      queryDetail(id)
        .then((res) => {
          this.formData = res.data.results;
          /** 保留小数点2位 */
          this.AmountCalcula(this.formData);
          this.formData.taxRate = Number(this.formData.taxRate * 100);
          // this.payConfirmList = this.formData.payConfirmList;
          this.payConfirmList = this.formData.payConfirmPersonList;
          /** 获取附件 */
          if (this.formData.attachDocList) {
            this.formData.attachDocList.forEach((item) => {
              this.getFileName(item.code);
            });
          }
          if (this.formData.contractDocList) {
            this.formData.contractDocList.forEach((item) => {
              this.getContractFileName(item.code);
            });
          }
          this.reqSearch(this.formData.proManager);
          console.log(this.formData, "formData77777");
          if (this.formData.instanceId) {
            let params = {};
            params.processDefineKey = "payConfirmEdit";
            let data = common.paramsFormat(params);
            getDbDetails(data).then(async (res) => {
              this.taskList = await res.data.results.taskList;
              this.taskList = this.taskList.filter((item) => {
                return item.processInstanceId == this.formData.instanceId;
              });
              this.taskId = this.taskList[0].taskId;
              console.log(this.taskId, "7777444565623232");
            });
          }

          let doc = this.formData.doc.split(",");
          doc.forEach((a) => {
            this.getFileName(a);
          });
        })
        .catch((error) => {});
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
          console.log(this.contractDocList, "附件名称");
        }
      });
    },
    /** 获取附件文件名称 */
    getFileName(id) {
      preview(id).then((res) => {
        if (res.data.status == 200) {
          this.attachDocList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2,
          });
          console.log(this.attachDocList, "附件名称");
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
    /** change时间 */
    changeDate() {
      /** 获取支付信息 */
      if (this.formData.feeStartDate && this.formData.feeEndDate) {
        this.getPayInfo();
      }
    },
    setTimeDateFmt(s) {
      return s < 10 ? "0" + s : s;
    },
    /** 获取支付信息 */
    async getPayInfo() {
      const data = {
        contractNo: this.formData.contractNo,
        proManager: this.userid,
        feeStartDate: this.formData.feeStartDate,
        feeEndDate: this.formData.feeEndDate,
      };
      this.attachDocList = [];
      this.contractDocList = [];
      await queryPersons(data).then((res) => {
        console.log(res, "人员信息");
        this.payConfirmList = res.data.results;
      });
      onePaymentConfirm(data).then(async (res) => {
        console.log(res, "支付信息");
        let response = await res.data.results;
        // this.AmountCalcula(response);
        this.amountCalForAdd(response)
        if (response) {
          this.formData.taxRate = Number(response.taxRate) * 100;
          /** 获取附件 */
          if (response.attachDocList) {
            response.attachDocList.forEach((item) => {
              this.getFileName(item.code);
            });
          }
          if (response.contractDocList) {
            response.contractDocList.forEach((item) => {
              this.getContractFileName(item.code);
            });
          }
          /** 获取合同id */
          this.getContractId(response.contractNo);
        }
      });
      
    },
    amountCalForAdd(response) {
      if (response) {
        if (this.payConfirmList !== undefined
              && this.payConfirmList != null
              && this.payConfirmList.length > 0) {
          this.formData.amountRaw = response.amountRaw.toFixed(2);
          this.formData.amount =
            Number(this.formData.amountRaw) + Number(this.formData.amountSalt);
          /** 含税总金额 */
          if (this.formData.amount || this.formData.amount === 0) {
              this.formData.amountTax = 
              (this.formData.amount *(1 + Number(response.taxRate))).toFixed(2);
          }
        } else {
          this.formData.amountRaw = null;
          this.formData.amountSalt = null;
          this.formData.amount = null;
          this.formData.taxRate = null;
          this.formData.amountTax = null;
          this.$refs.formData.clearValidate();
          this.$forceUpdate();
        }
      } 
    },
    /** 获取合同id */
    getContractId(value) {
      const params = {
        contractNo: value,
      };
      queryListByMapSmall(params)
        .then(async (res) => {
          console.log(res.data, "合同Id");
          let data = await res.data.results[0];
          this.contractId = data.id;
        })
        .catch((error) => {
          this.$message.error("错误!");
        });
    },
    AmountCalcula(response) {
      if (response) {
        /** 保留小数点2位 */
        if (response.amountRaw) {
          this.formData.amountRaw = response.amountRaw.toFixed(2);
        }
        if (response.amountSalt) {
          this.formData.amountSalt = response.amountSalt.toFixed(2);
        }
        /** 应付总金额等于 应付金额+调整金额 */
        if (this.formData.amountRaw && this.formData.amountSalt) {
          this.formData.amount =
            Number(this.formData.amountRaw) + Number(this.formData.amountSalt);
        } else if (this.formData.amountRaw && !this.formData.amountSalt) {
          this.formData.amount = this.formData.amountRaw;
        }
        /** 含税总金额 */
        if (this.formData.amount) {
          this.formData.amountTax = (
            this.formData.amount *
            (1 + Number(response.taxRate))
          ).toFixed(2);
        }
      }
    },
    /** change税率 */
    changeRate() {
      this.formData.amountTax = (
        this.formData.amount *
        (1 + Number(this.formData.taxRate / 100))
      ).toFixed(2);
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
      this.clearForm();
    },
    /** 更换项目经理清空数据 */
    clearForm() {
      this.formData.feeStartDate = null;
      this.formData.feeEndDate = null;
      this.formData.paymentSubmitDate = null;
      this.formData.amountRaw = null;
      this.formData.amountSalt = null;
      this.formData.amount = null;
      this.formData.taxRate = null;
      this.formData.amountTax = null;
      this.formData.remark = null;
      this.attachDocList = [];
      this.contractDocList = [];
      this.payConfirmList = [];
      this.$refs.formData.clearValidate();
      this.$forceUpdate();
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
    // 验证必填项liu
    proving(data) {
      let status = new Promise(() => {});
      status = this.liuzhuan(data).then((val) => {
        return val;
      });
      console.log(status, "传的状态");
      return status;
    },
    // 流转或者退回
    liuzhuan(data) {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          // console.log(22222);
          const loading = common.loading(this);
          let params = {
            ...this.formData,
            id: this.formData.id,
            instanceId: this.formData.instanceId
              ? this.formData.instanceId
              : this.instanceId,
            activityInstID: this.activityInstID,
            activityInstName: this.activityInstName,
            recordId: this.recordId,
            totals: this.formData.totals,
            proManager: this.userid,
            payConfirmPersonList: this.payConfirmList,
          };

          this.flowItem = this.$route.query;
          this.flowItem.processInstID = this.$route.query.processInstanceId;
          if (data) {
            this.flowItem.conclusion = data.conclusion;
            this.flowItem.suggest = data.suggest;
          }
          params.bps = this.flowItem;
          params.bps.taskId = this.taskId;
          console.log(this.formScene, "参数是");
          /** 提交确认 */
          if (this.formScene == "firPayConfirm") {
            return approvalCirculate(params).then((res) => {
              loading.close();
              if (res.data.status == "200") {
                this.$router.push({ name: "dashboard" });
                let message = res.data.message;
                if (message.indexOf(",") != -1) {
                  let messageArr = message.split(",");
                  console.log(messageArr, "message数组");
                  this.messageNotifyS(messageArr);
                } else {
                  this.$message.success(res.data.message);
                }
              } else {
                this.$message.error(res.data.message);
              }
              return res.data;
            });
            /** 二级部审批 */
          } else if (this.formScene == "secPayConfirm") {
            return twoManagerAudit(params).then((res) => {
              loading.close();
              let message = res.data.message;
              console.log(message, "message消息");
              if (res.data.status == "200") {
                this.$router.push({ name: "dashboard" });
                if (message.indexOf(",") != -1) {
                  let messageArr = message.split(",");
                  console.log(messageArr, "message数组");
                  this.messageNotifyS(messageArr);
                } else {
                  this.$message.success(message);
                }
              } else {
                this.$message.error(res.data.message);
              }
              return res.data;
            });
          } else {
            return approvalCirculate(params).then((res) => {
              loading.close();
              if (res.data.status == "200") {
                this.$router.push({ name: "dashboard" });
                let message = res.data.message;
                if (message.indexOf(",") != -1) {
                  let messageArr = message.split(",");
                  console.log(messageArr, "message数组");
                  this.messageNotifyS(messageArr);
                } else {
                  this.$message.success(message);
                }
              } else {
                this.$message.error(res.data.message);
              }
              return res.data;
            });
          }
        }
      });
    },
    changeAmount() {
      if (this.formData.amountRaw) {
        this.formData.amount =
          Number(this.formData.amountRaw) + Number(this.formData.amountSalt);
        this.changeAmountTax();
      }
      this.$forceUpdate();
    },
    /** 计算总价 */
    changeAmountTax() {
      if (this.formData.amount && this.formData.taxRate) {
        this.formData.amountTax =
          this.formData.amount * (1 + Number(this.formData.taxRate / 100));
        this.formData.amountTax = this.formData.amountTax.toFixed(2);
      }
      this.$forceUpdate();
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
    /** 撤回 */
    revoke() {
      console.log("1111支付确认");
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
            updateStatus(data).then((res) => {
              console.log(res);
            });
            this.$message.success(res.data.message);
            this.$nextTick(() => {
              // this.messageNotify();
              this.$router.push({ name: "dashboard" });
            });
          } else {
            this.$message.error(res.data.message);
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
    /** 考勤结算费用导出 */
    handelAttendExport() {
      let arr = [];
      this.payConfirmList.forEach(item =>{
        arr.push(item.jobId);
      })
      let arr1 = new Set(arr);
      // console.log(arr1);
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
    /** 搜索 */
    querySearchAsync(queryString, cb) {
      if (queryString.length > 1) {
        queryListByMapSmall({ contractName: queryString }).then((res) => {
          console.log(res.data, "data");
          this.restaurants = res.data.results.map((item) => {
            return {
              ...item,
              value: item.contractName,
            };
          });
          cb(this.restaurants);
        });
      }
    },
    /** 选择合同名称 */
    handleSelect(item) {
      console.log(item, "选择");
      this.formData.contractNo = item.contractNo;
      this.formData.vendor = item.vendor;
      this.clearForm();
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
.file_title {
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
.el-cascader,
.el-autocomplete {
  width: 100%;
}
/* 上传文件按钮 */
.againUpLoad .el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}
</style>
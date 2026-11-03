<!-- 添加合同信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">{{ title }}合同</p>
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

    <!-- 项目信息 -->
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
                    <el-form-item
                      prop="contractNo"
                      :rules="[
                        {
                          required: true,
                          message: '请填写合同编号',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请输入"
                        v-model="formData.contractNo"
                        oninput="value=value.replace(/[\W]/g,'')"
                        :disabled="forbidden"
                      ></el-input>
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
                          required: true,
                          message: '请填写合同名称',
                          trigger: 'blur',
                        },
                      ]"
                      ref="msgForm"
                    >
                      <el-input
                        placeholder="建议:合同名称+生效日期"
                        v-model="formData.contractName"
                        :disabled="forbidden"
                      ></el-input>
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
                        :disabled="forbidden"
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
                    <p>
                      <span>关联附件信息 *</span>
                      <span
                        class="fileDetail"
                        v-if="formData.attachInfo"
                        @click="goFileDetail"
                        >附件详情</span
                      >
                    </p>
                    <el-form-item
                      prop="attachInfo"
                      :rules="[
                        {
                          required: true,
                          message: '请选择',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-select
                        v-model="formData.attachInfo"
                        placeholder="请选择"
                        filterable
                        :disabled="forbidden"
                      >
                        <el-option
                          v-for="item in relatedfile_options"
                          :key="item.id"
                          :label="item.fileName"
                          :value="item.id"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>生效日期 *</p>
                    <el-form-item
                      prop="startDate"
                      :rules="[
                        {
                          required: true,
                          message: '请选择生效日期',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-date-picker
                        v-model="formData.startDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        @change="changeDate"
                        :picker-options="pickerOptionStart"
                        :disabled="forbidden"
                      />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>失效日期 *</p>
                    <el-form-item
                      prop="endDate"
                      :rules="[
                        {
                          required: true,
                          message: '请选择失效日期',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-date-picker
                        v-model="formData.endDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        @change="changeDate"
                        :picker-options="pickerOptionEnd"
                        :disabled="forbidden"
                      />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>结算频率 *</p>
                    <el-form-item
                      prop="settlementFrequency"
                      :rules="[
                        {
                          required: true,
                          message: '请选择结算频率',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-select
                        v-model="formData.settlementFrequency"
                        placeholder="请选择结算频率"
                        :disabled="forbidden"
                      >
                        <el-option
                          v-for="item in jsForm_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>是否完成 *</p>
                    <el-form-item
                      prop="progress"
                      :rules="[
                        {
                          required: true,
                          message: '请选择是否完成',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-select
                        v-model="formData.progress"
                        placeholder="请选择"
                        :disabled="forbidden"
                      >
                        <el-option
                          v-for="item in isComplete_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>合同状态 *</p>
                    <el-form-item prop="status">
                      <el-input v-model="formData.status" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>外包属性 *</p>
                    <el-form-item
                      prop="outsourceType"
                      :rules="[
                        { required: true, message: '请选择', trigger: 'change' },
                      ]"
                    >
                      <el-select
                        v-model="formData.outsourceType"
                        placeholder="请选择"
                        :disabled="forbidden"
                      >
                        <el-option
                          v-for="item in outsourcetype_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
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
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-col :span="5">
                        <el-input
                          placeholder="请输入税率"
                          v-model.number="formData.taxRate"
                          onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')"
                        ></el-input>
                      </el-col>
                      <el-col :span="3">%</el-col>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="24">
                  <div>
                    <p>项目内容</p>
                    <el-form-item prop="proContent">
                      <el-input
                        type="textarea"
                        :autosize="{ minRows: 3, maxRows: 15 }"
                        v-model="formData.proContent"
                        maxlength="200"
                        :disabled="forbidden"
                      />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <!-- 附件上传 -->
              <div class="row_pxrz upload">
                <el-row :gutter="50">
                  <el-col :span="8">
                    <div>
                      <h5 style="margin-bottom: 6px; display: inline-block">
                        合同 *
                      </h5>
                      <span
                        prop="contractDoc"
                        :rules="[{ required: true, message: '请上传合同' }]"
                      >
                        <el-upload
                          class="upload-demo"
                          action="Fake Action"
                          :file-list="fileList"
                          :show-file-list="false"
                          :http-request="changeFile"
                          :headers="headers"
                          :disabled="forbidden"
                          multiple
                        >
                          <el-button size="small" type="default">
                            <i class="el-icon-upload2"></i>上传文件
                          </el-button>
                        </el-upload>
                        <div
                          v-for="(item, index) in fileList"
                          :key="index"
                          style="margin-bottom: 5px"
                        >
                          {{ item.docname }}
                          <span
                            class="preview"
                            v-if="item.tag1"
                            @click="openNtkoWindow(item, 'baomi')"
                            >预览</span
                          >
                          <span
                            class="download"
                            @click="downloadFile(item.docid)"
                            >下载</span
                          >
                          <span class="download" @click="delUpload(item.docid)"
                            >删除</span
                          >
                        </div>
                      </span>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>
          </div>
        </div>
        <!-- 收款人信息 -->
        <div class="contentTwo_title">
          <p class="contentTwo_title_p">收款人信息</p>
        </div>
        <div class="content">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>收款人 *</p>
                    <el-form-item
                      prop="payee"
                      :rules="[
                        {
                          required: true,
                          message: '请填写收款人',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请输入"
                        v-model="formData.payee"
                        :disabled="forbidden"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>

                <el-col :span="11">
                  <div>
                    <p>开户行 *</p>
                    <el-form-item
                      prop="bankDeposit"
                      :rules="[
                        {
                          required: true,
                          message: '请填写开户行',
                          trigger: 'blur',
                        },
                      ]"
                      ref="msgForm"
                    >
                      <el-input
                        placeholder="开户行"
                        v-model="formData.bankDeposit"
                        :disabled="forbidden"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>账号 *</p>
                    <el-form-item
                      prop="account"
                      :rules="[
                        {
                          required: true,
                          message: '请填写账号',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请输入"
                        v-model.trim="formData.account"
                        oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
                        :disabled="forbidden"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>交易类型 *</p>
                    <el-form-item
                      prop="transactionType"
                      :rules="[
                        { required: true, message: '请选择', trigger: 'change' },
                      ]"
                      ref="msgForm"
                    >
                      <el-select
                        v-model="formData.transactionType"
                        placeholder="请选择"
                        :disabled="forbidden"
                      >
                        <el-option
                          v-for="item in transType_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>行号 *</p>
                    <el-form-item
                      prop="bankCode"
                      :rules="[
                        {
                          required: true,
                          message: '请填写行号',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请输入"
                        v-model.trim="formData.bankCode"
                        oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
                        @blur="bankChange"
                        :disabled="forbidden"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <el-row>
        <div class="nav bottom">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="1.5"></el-col>
            </div>
            <div class="wrapper_right">
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
                    :fns="onSubmit"
                  />
                </el-col>
              </div>
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
  queryDetail,
  updateContract,
  queryListByMapSmall,
  uploadExcel,
  count,
} from "@/api/supplierManager/contractManage";
import { queryZBYA } from "@/api/supplierManager/fileManage";
import { getFlowData, getListAll, preview } from "@/api/common/workflowApi";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { toPoint } from "@/utils";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "contractInfo",
  components: { buttonOrange, buttonCommon },
  data() {
    return {
      id: null,
      depId: null,
      formData: {
        contractNo: null,
        contractName: null,
        vendor: null,
        attachInfo: null,
        startDate: null,
        endDate: null,
        status: null,
        taxRate: 6,
        proContent: null,
        progress: "0",
        settlementFrequency: "1",
        bankDeposit: null,
        account: null,
        transactionType: null,
        bankCode: null,
      },
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
      relatedfile_options: [], //关联附件
      forbidden: false,
      routerName: "",
      headers: { "Content-Type": "multipart/form-data" },
      pickerOptionStart: {
        disabledDate: (time) => {
          let endDateVal = this.formData.endDate;
          if (endDateVal) {
            return time.getTime() > new Date(endDateVal).getTime();
          }
        },
      },
      pickerOptionEnd: {
        disabledDate: (time) => {
          let beginDateVal = this.formData.startDate;
          if (beginDateVal) {
            return time.getTime() < new Date(beginDateVal).getTime();
          }
        },
      },
      jsForm_options: [
        // 结算方式
        {
          value: "0",
          label: "按月结算",
        },
        {
          value: "1",
          label: "按季结算",
        },
        {
          value: "2",
          label: "按年结算",
        },
      ],
      outsourcetype_options: [
        // 结算方式
        {
          value: "0",
          label: "项目",
        },
        {
          value: "1",
          label: "人力",
        },
      ],
      transType_options: [
        {
          value: "0",
          label: "行内",
        },
        {
          value: "1",
          label: "同城跨行",
        },
        {
          value: "2",
          label: "异地跨行",
        },
      ],
      isComplete_options: [
        {
          value: "0",
          label: "未完成",
        },
        {
          value: "1",
          label: "已完成",
        },
      ],
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      ylDialog: false, //是否显示预览图片弹窗
      ylPhotoUrl: "", //预览图片地址
      photoFit: "scale-down",
    };
  },
  created() {
    this.getWbcom();
    this.getContactFile();
    if (this.$route.query.id) {
      this.title = "修改";
      this.id = this.$route.query.id;
      this.getDetail(this.$route.query.id);
    }
  },
  methods: {
    /** change 行号 */
    bankChange(e) {
      this.formData.bankCode = e.target.value;
    },
    /** 查询外包商 */
    getWbcom() {
      getListAll()
        .then((res) => {
          this.outsourceno_options = res.data.results;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    /** 获取关联附件 */
    getContactFile() {
      const data = {};
      queryZBYA().then((res) => {
        this.relatedfile_options = res.data.results;
      });
    },
    /** 获取附件详情 */
    goFileDetail() {
      let text = this.$router.resolve({
        path: "fileDetail",
        query: { id: this.formData.attachInfo },
      });
      window.open(text.href, "_blank");
    },
    /** 查询详情信息 */
    getDetail(id) {
      queryDetail(id)
        .then((res) => {
          this.formData = res.data.results;
          console.log(this.formData, "888888");
          this.formData.taxRate = this.formData.taxRate * 100;
          let contractDoc = this.formData.contractDoc.split(",");
          console.log(this.formData.contractDoc, "合同文档");
          contractDoc.forEach((a) => {
            this.getFileName(a);
          });
        })
        .catch((error) => {});
    },
    /** 获取文件名称 */
    getFileName(id) {
      preview(id).then((res) => {
        if (res.data.status == 200) {
          this.fileList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2,
          });
        }
      });
    },
    /** 提交*/
    onSubmit() {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          if (this.fileList.length < 1) {
            this.$message.warning("请上传附件");
            return;
          }
          this.$confirm("确认使用当前开户行、账号、行号吗？", "取消", {
            confirmButtonText: "确认",
            concelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.cleanData();
              const loading = common.loading(this);
              if (!this.id) {
                const data = {
                  contractName: this.formData.contractName,
                  isDel: "0", // 有效
                };
                count(data).then((res) => {
                  if (res.data.results > 0) {
                    this.$confirm("已存在相同名称合同，请检查。", "取消", {
                      confirmButtonText: "确认",
                      concelButtonText: "取消",
                      type: "warning",
                    })
                      .then(() => {
                        this.saveContract();
                      })
                      .catch(() => {
                        loading.close();
                      });
                  } else {
                    this.saveContract();
                  }
                });
              } else {
                this.updateContract();
                // const data = {
                //   contractName: this.formData.contractName,
                //   isDel: "0", // 有效
                // };
                // count(data).then((res) => {
                //   if (res.data.results > 0) {
                //     this.$confirm("已存在相同名称合同，请检查。", "取消", {
                //       confirmButtonText: "确认",
                //       concelButtonText: "取消",
                //       type: "warning",
                //     })
                //       .then(() => {
                //         this.updateContract();
                //       })
                //       .catch(() => {
                //         loading.close();
                //       });
                //   } else {
                //     this.updateContract();
                //   }
                // });
              }
            })
            .catch(() => {
              return;
            });
        }
      });
    },
    /** 保存合同 */
    saveContract() {
      const loading = common.loading(this);
      this.formData.contractDoc = this.formData.contractDoc.join(",");
      console.log(this.formData, "formData");
      save(this.formData)
        .then((res) => {
          loading.close();
          if (res.data.status == "200") {
            this.$message.success("操作成功！");
            this.$router.push({ name: "contractManager" });
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((error) => {
          console.log(error, "eerrrror");
        });
    },
    /** 更新合同 */
    updateContract() {
      const loading = common.loading(this);
      this.formData.contractDoc = this.formData.contractDoc.join(",");
      updateContract(this.formData)
        .then((res) => {
          loading.close();
          if (res.data.status == "200") {
            this.$message.success("操作成功！");
            this.$router.push({ name: "contractManager" });
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((error) => {
          console.log(error, "eerrrror");
        });
    },
    /** 关闭按钮 **/
    close() {
      this.$router.push({ name: "contractManager" });
    },
    /** 上传文件状态改变 **/
    changeFile(file, fileList) {
      console.log(file, "9999");
      let nowDate = new Date();
      let objectNo = nowDate.getTime();
      var formData = new FormData();
      formData.append("objectNo", objectNo); //唯一编号
      formData.append("type", "RLWBXQ");
      formData.append("version", "release");
      formData.append("catalogNo", "CATALOG2022121200101");
      formData.append("file", file.file);
      let data = formData;
      uploadExcel(data).then((res) => {
        if (res.data.status == "200") {
          this.fileList.push({
            docid: res.data.results,
            docname: file.file.name,
          });
          this.cleanData();
          this.$message.success("上传成功");
        } else {
          this.$message.error("上传失败");
        }
      });
    },
    /** 删除上传 **/
    delUpload(docid) {
      let fileList = this.fileList.filter((item) => item.docid != docid);
      this.fileList = fileList;
      this.cleanData(type);
    },
    /** 清洗数据 **/
    cleanData(type) {
      let fileList = [];
      this.fileList.forEach((item) => {
        fileList.push(item.docid);
      });
      this.$set(this.formData, "contractDoc", fileList);
      this.$refs["formData"].validateField("fileList");
      console.log(this.formData, "数据清洗data");
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
    /** 是否生效中 */
    isDuringDate(beginDateStr, endDateStr) {
      var curDate = new Date(),
        beginDate = new Date(beginDateStr),
        endDate = new Date(endDateStr);
      if (curDate < beginDate) {
        this.formData.status = "未生效";
      } else if (curDate > endDate) {
        this.formData.status = "已失效";
      } else {
        this.formData.status = "生效";
      }
      this.$forceUpdate();
    },
    /** change开始时间 */
    changeDate() {
      var beginDateStr = this.formData.startDate,
        endDateStr = this.formData.endDate;
      if (beginDateStr && endDateStr) {
        this.isDuringDate(beginDateStr, endDateStr);
      } else {
        return;
      }
    },
      
  },
};
</script>
<style scoped>
.upload-demo {
  display: inline-block;
  margin-bottom: 6px;
}
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
  /* padding: 0 20px 15px 20px; */
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
  padding: 15px 0;
  background-color: #fff;
}
.fknr {
  width: 100%;
}
.fknr .el-form-item__content {
  width: 93%;
}
.preview,
.download {
  color: #fba42f;
  font-size: 14px;
  margin-left: 5px;
  cursor: pointer;
}
.fileDetail {
  color: #409eff;
  margin-left: 8px;
  cursor: pointer;
}
/* 上传文件按钮 */
.againUpLoad .el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}
.row_pxrz {
  background-color: #ffffff;
}
</style>
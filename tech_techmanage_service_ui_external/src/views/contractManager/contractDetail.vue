<!-- 添加合同信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">合同详情</p>
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
                      :rules="[{ required: true, message: '请填写合同编号', trigger: 'blur' }]"
                    >
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
                    <el-form-item
                      prop="contractName"
                      :rules="[{ required: true, message: '请填写合同名称', trigger: 'blur' }]"
                      ref="msgForm"
                    >
                      <el-input
                        placeholder="建议:合同名称+生效日期"
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
                    <el-form-item
                      prop="vendor"
                    >
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
                    <p>
                    <span>关联附件信息 *</span>
                    <span
                        class="fileDetail"
                        v-if="formData.attachInfo"
                        @click="goFileDetail"
                      >附件详情</span>
                      </p>
                    <el-form-item
                      prop="attachInfo"
                    >
                      <el-select
                        v-model="formData.attachInfo"
                        placeholder="请选择"
                        disabled
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
                    >
                      <el-date-picker
                        v-model="formData.startDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        @change="changeDate"
                        disabled
                      />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>失效日期 *</p>
                    <el-form-item
                      prop="endDate"
                    >
                      <el-date-picker
                        v-model="formData.endDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        @change="changeDate"
                        disabled
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
                    >
                      <el-select
                        v-model="formData.settlementFrequency"
                        placeholder="请选择结算频率"
                        disabled
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
                    >
                      <el-select
                        v-model="formData.progress"
                        placeholder="请选择"
                        disabled
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
                      :rules="[{ required: true, message: '请选择', trigger: 'change' }]"
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
                      :rules="[{ required: true, message: '请输入税率', trigger: 'change' }]"
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
                        :autosize="{ minRows: 3, maxRows: 15}"
                        v-model="formData.proContent"
                        maxlength="200"
                        disabled
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
                      <h5>合同 *</h5>
                      <el-form-item
                        prop="contractDoc"
                      >
                        <!-- <el-upload
                          class="upload-demo"
                          action="Fake Action"
                          :file-list="fileList"
                          :show-file-list="false"
                          :http-request="changeFile"
                          :headers="headers"
                          disabled
                          multiple
                        >
                          <el-button size="small" type="default">
                            <i class="el-icon-upload2"></i>上传文件
                          </el-button>
                        </el-upload> -->
                        <div v-for="(item, index) in fileList" :key="index">
                          {{item.docname}}
                          <span
                            class="preview"
                            @click="openNtkoWindow(item, 'baomi')"
                          >预览</span>
                          <span class="download" @click="downloadFile(item.docid)">下载</span>
                        </div>
                      </el-form-item>
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
                    >
                      <el-input placeholder="请输入" v-model="formData.payee" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>

                <el-col :span="11">
                  <div>
                    <p>开户行 *</p>
                    <el-form-item
                      prop="bankDeposit"
                      ref="msgForm"
                    >
                      <el-input
                        placeholder="开户行"
                        v-model="formData.bankDeposit"
                        readonly
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
                    >
                      <el-input placeholder="请输入" v-model="formData.account" readonly></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>交易类型 *</p>
                    <el-form-item
                      prop="transactionType"
                      :rules="[{ required: true, message: '请选择', trigger: 'blur' }]"
                      ref="msgForm"
                    >
                      <el-select
                        v-model="formData.transactionType"
                        placeholder="请选择"
                        disabled
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
                      :rules="[{ required: true, message: '请填写行号', trigger: 'blur' }]"
                    >
                      <el-input
                        placeholder="请输入"
                        v-model="formData.bankCode"
                        oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
                        @blur="bankChange"
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
  queryEmployeeTree
} from "@/api/integrated/wbryzrlc";
import {
  save,
  queryDetail,
  updateContract,
  queryListByMapSmall,
  uploadExcel,
  count
} from "@/api/supplierManager/contractManage";
import { queryZBYA  } from "@/api/supplierManager/fileManage";
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
        bankCode: null
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
      jsForm_options: [
        // 结算方式
        {
          value: "0",
          label: "按月结算"
        },
        {
          value: "1",
          label: "按季结算"
        },
        {
          value: "2",
          label: "按年结算"
        }
      ],
      outsourcetype_options:[ // 结算方式
        {
          value: "0",
          label: "项目"
        },
        {
          value: "1",
          label: "人力"
        }],
      transType_options: [
        {
          value: "0",
          label: "行内"
        },
        {
          value: "1",
          label: "同城跨行"
        },
        {
          value: "2",
          label: "异地跨行"
        }
      ],
      isComplete_options: [
        {
          value: "0",
          label: "未完成"
        },
        {
          value: "1",
          label: "已完成"
        }
      ],
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      ylDialog: false, //是否显示预览图片弹窗
      ylPhotoUrl: "", //预览图片地址
      photoFit:"scale-down",
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
    // console.log(this.$route.params.name,'name')
    // if (/^\d+$/.test(this.$route.params.id)) {
    //   sessionStorage.setItem("id", this.$route.params.id);
    //   sessionStorage.setItem("name", this.$route.params.name);
    //   this.id = sessionStorage.getItem("id");
    //   this.routerName = sessionStorage.getItem("name")
    // } else {
    //   this.id = sessionStorage.getItem("id");
    //   this.routerName = sessionStorage.getItem("name")
    // }
  },
  methods: {
    /** change 行号 */
    bankChange(e) {
      this.formData.bankCode = e.target.value;
    },
    /** 查询外包商 */
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results;
        })
        .catch(error => {
          this.$message.error(error);
        });
    },
    /** 获取关联附件 */
    getContactFile() {
      const data = {};
      queryZBYA().then(res => {
        this.relatedfile_options = res.data.results;
      });
    },
    /** 获取附件详情 */
    goFileDetail() {
      let text = this.$router.resolve({
        path: "fileDetail",
        query: { id: this.formData.attachInfo }
      });
      window.open(text.href, "_blank");
    },
    /** 查询详情信息 */
    getDetail(id) {
      queryDetail(id)
        .then(res => {
          this.formData = res.data.results;
          console.log(this.formData, "888888");
          this.formData.taxRate = this.formData.taxRate * 100;
          let contractDoc = this.formData.contractDoc.split(",");
          console.log(this.formData.contractDoc, "合同文档");
          contractDoc.forEach(a => {
            this.getFileName(a);
          });
        })
        .catch(error => {});
    },
    /** 获取文件名称 */
    getFileName(id) {
      preview(id).then(res => {
        if (res.data.status == 200) {
          this.fileList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2
          });
        }
      });
    },
    /** 提交*/
    onSubmit() {
      this.$refs["formData"].validate(valid => {
        if (valid) {
          this.cleanData();
          const loading = common.loading(this);
          if (!this.id) {
            const data = {
              contractName: this.formData.contractName
            };
            count(data).then(res => {
              if (res.data.results > 0) {
                this.$confirm("已存在相同名称附件，请检查。", "取消", {
                  confirmButtonText: "确认",
                  concelButtonText: "取消",
                  type: "warning"
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
            this.formData.contractDoc = this.formData.contractDoc.join(",");
            this.formData.taxRate = toPoint(this.formData.taxRate);
            updateContract(this.formData).then(res => {
              loading.close();
              if (res.data.status == "200") {
                this.$message.success("操作成功！");
                this.$router.push({ name: "contractManager" });
              } else {
                this.$message.error(res.data.message);
              }
            });
          }
        }
      });
    },
    /** 保存合同 */
    saveContract() {
      const loading = common.loading(this);
      this.formData.contractDoc = this.formData.contractDoc.join(",");
      this.formData.taxRate = toPoint(this.formData.taxRate);
      console.log(this.formData, "formData");
      save(this.formData).then(res => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.$router.push({ name: "contractManager" });
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    /** 关闭按钮 **/
    close() {
      this.$router.push({ name: "contractManager" });
    },
    /** 上传文件状态改变 **/
    changeFile(file, fileList) {
      console.log(file,'9999')
      let nowDate = new Date();
      let objectNo = nowDate.getTime();
      var formData = new FormData();
      formData.append("objectNo", objectNo); //唯一编号
      formData.append("type", "RLWBXQ");
      formData.append("version", "release");
      formData.append("catalogNo", "CATALOG2019043000001");
      formData.append("file", file.file);
      let data = formData;
      uploadExcel(data).then(res => {
        if (res.data.status == "200") {
          this.fileList.push({
            docid: res.data.results,
            docname: file.file.name
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
      let fileList = this.fileList.filter(item => item.docid != docid);
      this.fileList = fileList;
      this.cleanData(type);
    },
    /** 清洗数据 **/
    cleanData(type) {
      let fileList = [];
      this.fileList.forEach(item => {
        fileList.push(item.docid);
      });
      this.$set(this.formData, "contractDoc", fileList);
      this.$refs["formData"].validateField("fileList");
      console.log(this.formData,'数据清洗data')
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
      window.ntkoCloseEvent = function() {
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
    /** 银行卡号检查 */
    luhnCheck(bankno) {
      var lastNum = bankno.substr(bankno.length - 1, 1); // 取出最后一位
      var first15Num = bankno.substr(0, bankno.length - 1); // 前15或18位
      var newArr = new Array();
      for (var i = first15Num.length - 1; i > -1; i--) {
        newArr.push(first15Num.substr(i, 1));
      }
      var arrJiShu = new Array(); // 奇数位*2的积<9
      var arrJiShu2 = new Array(); // 奇数位*2的积>9
      var arrOuShu = new Array(); // 偶数位数组
      for (var j = 0; j < newArr.length; i++) {
        if ((j + 1) % 2 == 1) {
          if (parseInt(newArr[j]) * 2 < 9)
            arrJiShu.push(parseInt(newArr[j]) * 2);
          else arrJiShu2.push(parseInt(newArr[j]) * 2);
        } //偶数位
        else arrOuShu.push(newArr[j]);
      }
      var jishu_child1 = new Array(); // 奇数位*2>9的分割之后的数组个位数
      var jishu_child2 = new Array(); // 奇数位*2>9的分割之后的数组十位数
      for (var h = 0; h < arrJiShu2.length; i++) {
        jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
        jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
      }
      var sumJiShu = 0; // 奇数位*2<9的数组之和
      var sumOuShu = 0; // 偶数位*2<9的数组之和
      var sumJiShuChild1 = 0;
      var sumOuShuChild2 = 0;
      var sumTotal = 0;
      for (var m = 0; m < arrJiShu.length; m++) {
        sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
      }
      for (var n = 0; n < arrOuShu.length; n++) {
        sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
      }
      for (var p = 0; p < jishu_child1.length; p++) {
        sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
        sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
      }
      // 计算总和
      sumTotal =
        parseInt(sumJiShu) +
        parseInt(sumOuShu) +
        parseInt(sumJiShuChild1) +
        parseInt(sumOuShuChild2);
      // 计算luhn值
      var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
      var luhn = 10 - k;
      if (lastNum == luhn) {
        return true;
      } else {
        return false;
      }
    }
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
.fileDetail{
  color:#409eff;
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
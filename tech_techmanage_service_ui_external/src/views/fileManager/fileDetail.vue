<!-- 添加附件信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">附件详情</p>
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
    <!-- 附件信息 -->
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
                    <p>编号 *</p>
                    <el-form-item prop="fileNo">
                      <el-input
                        placeholder="请输入"
                        v-model="formData.fileNo"
                        disabled
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>

                <el-col :span="11">
                  <div>
                    <p>文件类型</p>
                    <el-form-item prop="fileType">
                      <el-select
                        v-model="formData.fileType"
                        placeholder="请选择文件类型"
                        disabled
                      >
                        <el-option
                          v-for="item in fileData"
                          :key="item.key"
                          :label="item.value"
                          :value="item.key"
                         
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>文件名称 *</p>
                    <el-form-item
                      prop="fileName"
                      ref="msgForm"
                    >
                      <el-input
                        placeholder="文件名称"
                        v-model="formData.fileName"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11" v-if="!isBill">
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
                        :picker-options="pickerOptionStart"
                        readonly
                      />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11" v-if="!isBill">
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
                        :picker-options="pickerOptionEnd"
                        readonly
                      />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col
                  :span="11"
                  v-if="formData.fileType == '0' ? true : false"
                >
                  <div>
                    <p>总人数 *</p>
                    <el-form-item
                      prop="totals"
                    >
                      <el-input
                        v-model="formData.totals"
                        @change="changeHeadCount"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row
                type="flex"
                justify="space-between"
                v-if="formData.fileType == '0' ? true : false"
              >
                <el-col :span="11">
                  <div>
                    <p>可用人数 *</p>
                    <el-form-item prop="availableCount">
                      <el-input
                        v-model="formData.availableCount"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>已用人数 *</p>
                    <el-form-item prop="numberUsed">
                      <el-input
                        placeholder="请输入已用人数"
                        v-model="formData.numberUsed"
                        readonly
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>文件状态</p>
                    <el-form-item prop="status">
                      <el-input v-model="formData.status" readonly />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>是否关联</p>
                    <el-form-item prop="isAssociated">
                      <el-select v-model="formData.isAssociated" disabled>
                        <el-option
                          v-for="item in isRelated_options"
                          :key="item.key"
                          :label="item.value"
                          :value="item.key"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between" v-if="isBill">
                <el-col :span="11">
                  <div>
                    <p>合同编号 *</p>
                    <el-form-item
                      prop="contractInfo"
                    >
                      <el-select
                        v-model="formData.contractInfo"
                        placeholder="请选择"
                        @change="changeContractNo"
                        disabled
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
                    <p>合同名称</p>
                    <el-form-item prop="contractName">
                      <el-input v-model="formData.contractName" disabled />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <!-- 附件上传 -->
              <el-row :gutter="5" >
                <el-col :span="12" justify="between">
                  <span style="margin-bottom: 6px">附件 *</span>
                  <el-form-item prop="doc">
                    <div
                      v-for="(item, index) in fileList"
                      :key="index"
                      style="margin-bottom: 5px"
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
  save,
  queryDetail,
  queryProcessDetail,
  queryLatestRecord,
  twoManagerAudit,
  updateFile,
  count,
  uploadExcel,
} from "@/api/supplierManager/fileManage";
import { queryListByMapSmall } from "@/api/supplierManager/contractManage";
import { getFlowData, getListAll, preview } from "@/api/common/workflowApi";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import approveBottom from "@/views/plugins/camunda/approveBottom";
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "fileInfo",
  components: { flowLogs, approveBottom, buttonOrange, buttonCommon },
  data() {
    return {
      id: null,
      formData: {
        isAssociated: "否",
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
      fileData: [
        { key: "0", value: "总办议案" },
        { key: "1", value: "发票" },
        { key: "2", value: "其他" },
      ],
      contractno_options: [], //合同
      isRelated_options: [
        { key: "N", value: "否" },
        { key: "Y", value: "是" },
      ],
      processInstanceId: "",
      headers: { "Content-Type": "multipart/form-data" },
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      ylDialog: false, //是否显示预览图片弹窗
      ylPhotoUrl: "", //预览图片地址
      photoFit:"scale-down",
      isBill: false,
      pickerOptionStart:{
        disabledDate: time=>{
          let endDateVal = this.formData.endDate;
          if(endDateVal){
            return time.getTime()>new Date(endDateVal).getTime();
          }
        }
      },
      pickerOptionEnd:{
        disabledDate: time=>{
          let beginDateVal = this.formData.startDate;
          if(beginDateVal){
            return time.getTime()<new Date(beginDateVal).getTime();
          }
        }
      },
      // 表单校验
      rules: {
        startDate: [
          { required: true, message: "请选择生效日期", trigger: "blur" },
        ],
        endDate: [
          { required: true, message: "请选择失效日期", trigger: "blur" },
        ],
        fileType: [
          { required: true, message: "请选择文件类型", trigger: "blur" },
        ],
      },
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
    };
  },
  created() {
    this.id = this.$route.params.id||this.$route.query.id;
    console.log(this.id,'传参id')
    if (this.id) {
      this.getDetail(this.id);
    }
    // this.getNumber();
    this.getContractNo();
  },
  watch: {
    "formData.fileType"() {
      this.$nextTick(() => {
        this.chanRules();
      });
    },
  },
  methods: {
    /** 提交 */
    onSubmit() {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          console.log(this.formData, "form表单数据");
          const loading = common.loading(this);
          this.cleanData();
          this.formData.doc = this.formData.doc.join(",");
          if (this.id == null) {
            const data = {
              fileName: this.formData.fileName,
            };
            count(data).then((res) => {
              if (res.data.results > 0) {
                this.$confirm("已存在相同名称附件，请检查。", "取消", {
                  confirmButtonText: "确认",
                  concelButtonText: "取消",
                  type: "warning",
                })
                  .then(() => {
                    this.saveFile();
                  })
                  .catch(() => {
                    loading.close();
                    console.log(error, "已取消");
                  });
              } else {
                this.saveFile();
              }
            });
          } else {
            updateFile(this.formData).then((res) => {
              loading.close();
              if (res.data.status == "200") {
                if (this.formData.fileType == "0") {
                  let message = common.getFlowMessgeNew(res.data.message);
                  let messageArr = message.split(",");
                  this.$nextTick(() => {
                    this.messageNotify(messageArr);
                    this.$router.push({ name: "fileManager" });
                  });
                } else {
                  this.$message.success("修改成功");
                }
              } else {
                this.$message.error(res.data.message);
              }
            });
          }
        }
      });
    },
    /** 保存文件 */
    saveFile() {
      const loading = common.loading(this);
      this.formData.fileNo = null;
      save(this.formData).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.$router.push({ name: "fileManager" });
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    // 关闭按钮
    close() {
      this.$router.push({ name: "fileManager" });
    },
    getDetail(id) {
      queryDetail(id)
        .then((res) => {
          this.formData = res.data.results;
          let doc = this.formData.doc.split(",");
          doc.forEach((a) => {
            this.getFileName(a);
          });
        })
        .catch((error) => {});
    },
    // 获取文件名称
    getFileName(id) {
      preview(id).then((res) => {
        if (res.data.status == 200) {
          this.fileList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2,
          });
          console.log(this.fileList, "文件名称");
        }
      });
    },
    // 上传文件状态改变
    changeFile(file, fileList) {
      let nowDate = new Date();
      let objectNo = nowDate.getTime();
      var formData = new FormData();
      formData.append("objectNo", objectNo); //唯一编号
      formData.append("type", "RLWBXQ");
      formData.append("version", "release");
      formData.append("catalogNo", "CATALOG2019043000001");
      formData.append("file", file.file);
      let data = formData;
      uploadExcel(data).then((res) => {
        console.log(res, "上传结果");
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
    /** 文件状态 */
    isDuringDate(beginDateStr, endDateStr) {
      var curDate = new Date(),
        beginDate = new Date(beginDateStr),
        endDate = new Date(endDateStr);
      if (curDate >= beginDate && curDate <= endDate) {
        this.formData.status = "生效";
      } else {
        this.formData.status = "未生效";
      }
      this.$forceUpdate();
    },
    /** change时间 */
    changeDate() {
      var beginDateStr = this.formData.startDate,
        endDateStr = this.formData.endDate;
      if (beginDateStr && endDateStr) {
        this.isDuringDate(beginDateStr, endDateStr);
      } else {
        return;
      }
    },
    /** 总人数最少为1 */
    inputChaneMin(data) {
      var min = 1;
      if (Number(this.formData[data].replace(/[^\d]/g, "")) < min) {
        this.formData[data] = min;
      } else {
        this.formData[data] = this.formData[data].replace(/[^\d]/g, "");
      }
    },
    setTimeDateFmt(s) {
      return s < 10 ? "0" + s : s;
    },
    chanRules() {
      this.isBill = this.formData.fileType == "1" ? true : false;
      if (this.formData.fileType == "1") {
        this.formData.totals = 1;
        this.formData.availableCount = 1;
        this.formData.numberUsed = 0;
        this.$forceUpdate();
      }
    },
    /** 编号 */
    randomNumber() {
      const now = new Date();
      let month = now.getMonth() + 1;
      let day = now.getDate();
      let hour = now.getHours();
      let minutes = now.getMinutes();
      let seconds = now.getSeconds();
      month = this.setTimeDateFmt(month);
      day = this.setTimeDateFmt(day);
      hour = this.setTimeDateFmt(hour);
      minutes = this.setTimeDateFmt(minutes);
      seconds = this.setTimeDateFmt(seconds);
      let orderCode =
        now.getFullYear().toString() +
        month.toString() +
        day +
        Math.round(Math.random() * 1000).toString();
      return orderCode;
    },
    getNumber() {
      queryLatestRecord().then(async (res) => {
        if (res.data.message == "表数据为空") {
          var time = Moment(new Date()).format("YYYY-MM-DD").replace(/-/g, "");
          this.formData.fileNo = time + "001";
        } else {
          this.formData.fileNo = await res.data.results.fileNo;
        }
        this.$forceUpdate();
      });
    },
    /** 获取合同编号 */
    getContractNo() {
      const data = {
        isDel: "0",
      };
      queryListByMapSmall(data).then((res) => {
        this.contractno_options = res.data.results;
        console.log(this.contractno_options, "合同选项121212");
      });
    },
    /** change合同编号 */
    changeContractNo(value) {
      const params = {
        contractNo: value,
      };
      queryListByMapSmall(params)
        .then(async (res) => {
          let data = await res.data.results[0];
          console.log(data, "8885564477");
          this.formData.contractName = data.contractName;
          this.$forceUpdate();
        })
        .catch((error) => {
          this.$message.error("错误!");
        });
    },
    /** 可用人数 */
    changeHeadCount(value) {
      this.formData.availableCount = 0;
      this.formData.numberUsed = 0;
      this.$forceUpdate();
    },
    cut(v) {
      this.current = v;
    },
    // 验证必填项
    proving(data) {
      let status = new Promise(() => {});
      status = this.liuzhuan(data).then((val) => {
        return val;
      });
      return status;
    },
    // 流转或者退回
    liuzhuan(data) {
      const loading = common.loading(this);
      let params = {};
      params.id = this.formData.id;
      params.instanceId = this.instanceId;
      params.activityInstID = this.activityInstID;
      params.activityInstName = this.activityInstName;
      params.recordId = this.recordId;
      params.totals = this.formData.totals;
      // params.supplytime = this.peoRequireDetail.supplytime;
      this.flowItem.conclusion = data.conclusion;
      this.flowItem.suggest = data.suggest;
      params.bps = this.flowItem;
      params.bps.taskId = this.taskId;
      console.log(this.formScene, "参数是");
      if (this.formScene == "fileManage") {
        return twoManagerAudit(params).then((res) => {
          loading.close();
          return res.data;
        });
      }
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
  margin-bottom: 5px;
  display: flex;
  justify-content: space-between;
}

.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}
.wrapper_right{
  margin-right: 20px;
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
.preview,
.download {
  color: #fba42f;
  font-size: 14px;
  margin-left: 5px;
  cursor: pointer;
}

/* 空格占位 */
.blank {
  width: 100%;
  height: 160px;
}

/* 上传文件按钮 */
.againUpLoad .el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}
</style>
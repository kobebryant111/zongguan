<!-- 添加附件信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">审批附件</p>
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
                      :rules="[
                        {
                          required: true,
                          message: '请填写文件名称',
                          trigger: 'blur',
                        },
                      ]"
                      ref="msgForm"
                    >
                      <el-input
                        placeholder="文件名称"
                        v-model="formData.fileName"
                        disabled
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>生效日期 *</p>
                    <el-form-item
                      prop="startDate"
                      :rules="
                        formData.fileType == 0
                          ? rules.startDate
                          : [
                              {
                                required: false,
                                message: '请选择生效日期',
                                trigger: 'blur',
                              },
                            ]
                      "
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
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>失效日期 *</p>
                    <el-form-item
                      prop="endDate"
                      :rules="
                        formData.fileType == 0
                          ? rules.endDate
                          : [
                              {
                                required: false,
                                message: '请选择失效日期',
                                trigger: 'blur',
                              },
                            ]
                      "
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
                <el-col :span="11">
                  <div>
                    <p>总人数 *</p>
                    <el-form-item
                      prop="totals"
                      :rules="[
                        {
                          required: true,
                          message: '请输入总人数',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-input
                        v-model="formData.totals"
                        @change="changeHeadCount"
                        disabled
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>可用人数 *</p>
                    <el-form-item prop="availableCount">
                      <el-input
                        v-model="formData.availableCount"
                        disabled
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
                        disabled
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
                      <el-input v-model="formData.status" disabled />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>是否关联</p>
                    <el-form-item prop="status">
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
              <!-- 附件上传 -->
              <div class="row_pxrz upload">
                <el-row :gutter="50">
                  <el-col :span="8">
                    <div>
                      <p>附件 *</p>
                      <el-form-item prop="doc">
                        <!-- :rules="[{ required: true, message: '请上传附件'}]" -->
                        <!-- <el-upload
                          class="upload-demo"
                          action="Fake Action"
                          multiple
                          :file-list="fileList"
                          :show-file-list="false"
                          :http-request="changeFile"
                          :headers="headers"
                        >
                          <el-button size="small" type="default">
                            <i class="el-icon-upload2"></i>上传文件
                          </el-button>
                        </el-upload> -->
                        <div v-for="(item, index) in fileList" :key="index">
                          {{ item.docname }}
                          <span
                            class="preview"
                            @click="openNtkoWindow(item, 'baomi')"
                            >预览</span
                          >
                          <span
                            class="download"
                            @click="downloadFile(item.docid)"
                            >下载</span
                          >
                          <!-- <span
                            class="xkg-color-yellow xkg-marginleft-5px delButt"
                            @click="delUpload(item.docid)"
                            >删除</span
                          > -->
                        </div>
                      </el-form-item>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>
          </div>
        </div>
      </el-form>
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

    <!-- 提交 -->
    <!-- <div class="nav bottom">
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
    </div> -->
    <approve-bottom
      v-show="flowItem.status == 'db' && formScene == 'secFileManage'"
      :proving="proving"
      :flowItem="flowItem"
    ></approve-bottom>
    <div class="nav-bottom" v-show="flowItem.status == 'yb'">
      <yb-revoke :flowItem="flowItem" :fns="revoke"></yb-revoke>
    </div>
    <div class="nav bottom" v-show="formScene == 'fileManage'&& flowItem.status == 'db'">
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
  save,
  queryDetail,
  queryProcessDetail,
  queryLatestRecord,
  approvalCirculate,
  twoManagerAudit,
  updateFile,
  count,
  deleteFlow,
  uploadExcel,
} from "@/api/supplierManager/fileManage";
import { getFlowData, getListAll, preview } from "@/api/common/workflowApi";
import { drawback } from "@/api/common/workflowApi";
import { updateFileStatus } from "@/api/supplierManager/fileManage";
import { doDe } from "@/api/integrated/xqgl";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonDelete from "@/components/eleButton/delete"; //引入删除按钮
import approveBottom from "@/views/plugins/camunda/approveBottom";
import ybRevoke from "@/views/fileManager/common/ybRevoke";
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "fileInfo",
  components: {
    flowLogs,
    FlowLogsGd,
    ybRevoke,
    approveBottom,
    buttonOrange,
    buttonCommon,
    buttonDelete,
  },
  data() {
    return {
      id: null,
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
      fileData: [
        { key: "0", value: "总办议案" },
        { key: "1", value: "发票" },
        { key: "2", value: "其他" },
      ],
      isRelated_options: [
        { key: "N", value: "否" },
        { key: "Y", value: "是" },
      ],

      headers: { "Content-Type": "multipart/form-data" },
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      ylDialog: false, //是否显示预览图片弹窗
      ylPhotoUrl: "", //预览图片地址
      photoFit:"scale-down",
      messageNote: "",
      errorMessage: "",
      flowItem: {
        processInstID: "",
      }, //bps数据
      delBtnIf: true, //删除按钮是否出现
      instanceId: "", //流程ID
      workItemID: "", //工作项id
      formScene: "", //场景
      processInstanceId: "",
      processDefName: "", //流程定义名称
      processInstName: "", //流程实例名称
      activityInstName: "", //审批节点描述
      activityInstId: "", //当前活动实例ID
      activityDefID: "", //当前节点ID
      recordId: "",
      taskId: "",
      // 表单校验
      rules: {
        startDate: [
          { required: true, message: "请选择生效日期", trigger: "blur" },
        ],
        endDate: [
          { required: true, message: "请选择失效日期", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
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
      if (this.instanceId) {
        let params = {};
        params.instanceId = this.instanceId;
        queryProcessDetail(params)
          .then((res) => {
            this.formData = res.data.results[0];
            let doc = this.formData.doc.split(",");
            doc.forEach((a) => {
              this.getFileName(a);
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
    /** 提交 */
    onSubmit() {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          console.log(this.formData, "form表单数据");
          const loading = common.loading(this);
          this.cleanData();
          this.formData.fileNo = null;
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
                this.$message.success("操作成功！");
                this.$router.push({ name: "fileManager" });
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
          console.log(this.formData, "formData");
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
    /** 可用人数 */
    changeHeadCount(value) {
      this.formData.availableCount = value;
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
      console.log(params, "参数是");
      if (this.formScene == "fileManage") {
        return approvalCirculate(params).then((res) => {
          let message = common.getFlowMessgeNew(res.data.message);
            let messageArr = message.split(",");
            this.$nextTick(() => {
              this.messageNotify(messageArr);
              this.$router.push({ name: "dashboard" });
            });
          loading.close();
          return res.data;
        });
      } else if (this.formScene == "secFileManage") {
        return twoManagerAudit(params).then((res) => {
          loading.close();
          return res.data;
        });
      } else {
        this.errorMessage = "操作失败";
        this.saveError();
      }
    },
    // 打开删除messageBox窗口
    delBox() {
      console.log("1111");
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
      console.log(params, "params");
      deleteFlow(params).then((res) => {
        if (res.data.status == 200) {
          this.$message.success("流程删除成功");
          this.$router.push({ name: "dashboard" });
        } else {
          this.$message.error("流程删除失败");
        }
      });
    },
    /** 撤回 */
    revoke() {
      let params = {};
      const loading = common.loading(this);
      params.processInstId =
        this.flowItem.instanceid || this.flowItem.processInstanceId;
      params.conclusion = "04";
      let data = common.paramsFormat(params);
      drawback(data)
        .then((res) => {
          loading.close();
          console.log(res, "res");
          if (res.data.status == 200) {
            const data = {
              instanceId:
                this.flowItem.instanceid || this.flowItem.processInstanceId,
              isDel: "1",
            };
            // updateFileStatus(data).then((res) => {
            //   console.log(res);
            // });
            let message = res.data.message;
            this.messageNote = message;
            this.$nextTick(() => {
              this.messageNotifyS();
              this.$router.push({ name: "dashboard" });
            });
          } else {
            this.errorMessage = "操作失败";
            this.$message.error(this.errorMessage);
          }
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    /** 再次提交 */
    reSubmit() {
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
    },
    //消息提示
    messageNotify(messageVal) {
      this.$notify({
        title: "提示",
        // message: this.messageNote,
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
    //消息提示
    messageNotifyS() {
      this.$notify({
        title: "提示",
        message: this.messageNote,
        duration: 0,
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
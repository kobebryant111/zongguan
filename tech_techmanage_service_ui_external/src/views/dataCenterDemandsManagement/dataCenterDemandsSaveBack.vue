<!-- 数据中心需求保存退回 -->
<template>
  <div>
    <div class="nav">
      <el-row>
        <el-col :span="24">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="18">
                <p class="nav_name">发起流程</p>
              </el-col>
            </div>
            <div class="wrapper_right">
              <el-col :span="1.5">
                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
              </el-col>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 基础信息 -->
    <div class="dcdl-title">
      <div class="dcdl-title-main">
        <p class="title_name">基础信息</p>
      </div>
    </div>

    <div class="dcdl-container">
      <el-form :model="demandData" ref="baseInfoForm" :rules="rules">
        <div class="dcdl-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">标题:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="title">
                  <el-input
                    placeholder="请填写系统简称+需求内容，限25字以内"
                    v-model="demandData.title"
                    maxlength="25"
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">需求负责人:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="demandBys">
                  <el-cascader
                    ref="cascader"
                    :options="requirePeo_options"
                    :props="msgPeoParams"
                    v-model="demandData.demandBys"
                    :show-all-levels="false"
                    filterable
                    @change="demandByChange"
                    style="width:100%"
                  ></el-cascader>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">申请日期:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="createDate">
                  <el-input v-model="demandData.createDate" :disabled="true"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="25">
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">流程编号:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="processinstId">
                  <el-input v-model="demandData.demandNo" :disabled="true"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">申请人:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="createBy">
                  <el-input v-model="demandData.createBy" :disabled="true"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">联系电话:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="phone">
                  <el-input v-model="demandData.phone"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">申请人所在部门:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="demandDept">
                  <el-select placeholder="部门" v-model="demandData.demandDept" :disabled="true">
                    <el-option
                      v-for="item in depData"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>

    <!-- 需求详情 -->
    <div class="dcdl-title">
      <div class="dcdl-title-main">
        <p class="title_name">需求详情</p>
      </div>
    </div>

    <div class="dcdl-container">
      <el-form :model="demandData" ref="demandDetailForm" :rules="rules">
        <div class="dcdl-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">需求分类:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="demandType">
                  <el-select v-model="demandData.demandType">
                    <el-option
                      v-for="item in demandType_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="requireList">
                <el-row>
                  <el-col :span="24">
                    <div>
                      <label class="mandatory">*</label>
                      <label class="labelstyle">需求期望上线时间:</label>
                    </div>
                  </el-col>
                </el-row>
                <div class="formstyle">
                  <el-form-item prop="demandOnlineDate">
                    <el-date-picker
                      v-model="demandData.demandOnlineDate"
                      value-format="yyyy-MM-dd"
                      :picker-options="pickerOptions"
                    ></el-date-picker>
                  </el-form-item>
                </div>
              </div>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">关联主系统:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="mainSystemId">
                  <el-cascader
                    ref="systemName"
                    :options="systemnos_options"
                    :props="systemParams"
                    v-model="demandData.mainSystemId"
                    :show-all-levels="false"
                    @change="systemChange"
                    filterable
                    style="width: 100%"
                  ></el-cascader>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="3">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">附件上传:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="files">
                  <el-button size="medium" type="primary" @click="showUpDialog">
                    <i class="el-icon-upload2"></i>上传文件
                  </el-button>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="30">
            <el-col :span="13">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">需求概述:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="demandRemakr">
                  <el-input
                    placeholder="500字"
                    type="textarea"
                    :autosize="{ minRows: 3}"
                    v-model="demandData.demandRemakr"
                    maxlength="500"
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </div>
        <div class="dcdl-main">
          <el-table
            :data="fileList"
            border
            highlight-current-row
            height="125"
            :header-cell-style="{'text-align':'center'}"
            :cell-style="{'text-align':'center'}"
          >
            <el-table-column type="index" label="序号" width="100" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="docname" label="文件名称" width="200" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="creators" label="上传人" width="200" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column
              prop="createtime"
              label="上传时间"
              width="200"
              :show-overflow-tooltip="true"
            ></el-table-column>
            <el-table-column prop="stage" label="上传阶段" width="200" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <a
                  href="javascript:void();"
                  @click="downloadFile(scope.row)"
                  style="color: rgba(251, 164, 47, 1)"
                >下载</a>
                <a
                  href="javascript:void();"
                  style="color: rgba(251, 164, 47, 1)"
                  @click="openNtkoWindow(scope.row.fileNo)"
                >预览</a>
                <!-- -->
                <a
                  href="javascript:void();"
                  @click="deleteFile(scope.row)"
                  style="color: rgba(251, 164, 47, 1)"
                >删除</a>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form>
    </div>

    <el-dialog title="附件上传" :visible.sync="showUpload" width="70%">
      <el-row type="flex">
        <div class="requireList_head_right_orange_common">
          <el-upload
            multiple
            class="upload-demo"
            action="Fake Action"
            :file-list="fileList"
            :show-file-list="false"
            :http-request="uploadFile"
            :on-change="changeFile"
          >
            <el-button size="medium" type="primary">
              <i class="el-icon-upload2"></i>上传文件
            </el-button>
          </el-upload>
        </div>
        <div class="requireList_head_right_orange_common">
          <el-button @click="downloadModle">下载模板</el-button>
        </div>
      </el-row>
      <div class="dcdl-main">
        <el-table
          :data="fileList"
          border
          highlight-current-row
          height="250"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}"
        >
          <el-table-column type="index" label="序号" width="100" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="docname" label="文件名称" width="200" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="creators" label="上传人" width="200" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="createtime" label="上传时间" width="200" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="stage" label="上传阶段" width="200" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <a
                href="javascript:void();"
                @click="downloadFile(scope.row)"
                style="color: rgba(251, 164, 47, 1)"
              >下载</a>
              <a
                href="javascript:void();"
                style="color: rgba(251, 164, 47, 1)"
                @click="openNtkoWindow(scope.row.fileNo)"
              >预览</a>
              <!-- -->
              <a
                href="javascript:void();"
                @click="deleteFile(scope.row)"
                style="color: rgba(251, 164, 47, 1)"
              >删除</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>

    <!-- 操作日志 -->
    <div class="czLog">
      <flow-logs ref="log"></flow-logs>
    </div>

    <div class="nav-bottom">
      <div class="wrapper">
        <div class="wrapper_left">
          <button-delete :buttonName="buttonDel" :size="size" :fns="delBox"></button-delete>
        </div>
        <div class="wrapper_right">
          <div class="wrapper-right-keep">
            <button-common :buttonName="buttonKeep" :size="size" :fns="saveDraft_DC"></button-common>
          </div>
          <div>
            <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { querySysList } from "@/api/branchIntentionApi/yyxmbg";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import {
  queryDetail,
  saveDraft,
  upload,
  flowStartAndRun,
  download,
  delDocument,
  getProductionDay
} from "@/api/dataCenter/dataCenDemManager";

import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import buttonDelete from "@/components/eleButton/delete";
import approveBottomDc from "./approveBottomDc";
import {
  queryResourceDept,
  querySystemTree,
  queryEmployeeTree
} from "@/api/integrated/wbryzrlc";
import {
  queryWorkItem,
  doDel,
  getFlowLog,
  getDownloadUrl
} from "@/api/common/flowApi";
import { getDicEntry } from "@/api/eosDictEntry";
import common from "@/api/common";
import { mapGetters } from "vuex";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;

export default {
  name: "dataCenterDemandsLaunch",
  components: {
    buttonOrange,
    buttonCommon,
    buttonDelete,
    flowLogs,
    approveBottomDc
  },
  data() {
    return {
      size: "mini", //按钮大小
      buttonKeep: "存草稿",
      buttonCommit: "提 交",
      buttonClose: "关 闭",
      buttonDel: "删除",
      demandData: {
        title: "", //标题
        demandBy: "", //需求负责人
        phone: "", //联系电话
        demandNo: "", //流程ID
        createBy: "", //申请人
        createDate: "", //申请日期
        demandDept: "", //申请人所在部门
        demandType: "", //需求分类
        demandOnlineDate: "", //需求期望上线时间
        mainSystemId: "", //关联主系统
        files: "", //附件
        demandRemakr: "", //需求概述
        demandBys: [] //需求负责人
      },
      showUpload: false,
      depData: [], //部门数据
      sysData: [], //主系统数据
      requirePeo_options: [], //需求负责人数据
      msgPeoParams: {
        //需求负责人配置信息
        label: "text",
        value: "id",
        children: "children",
        emitPath: false,
        multiple: true
        // leaf: 'id'
      },
      systemnos_options: [], //主系统数据
      systemParams: {
        //系统名称配置信息
        label: "text",
        value: "pcode",
        children: "children",
        emitPath: false
      },
      demandType_option: [], //需求分类
      fileList: [], //文件列表
      bps: {}, //bps数据
      instanceId: "", //流程id
      rules: {
        title: [{ required: true, message: "请填写标题内容", trigger: "blur" }],
        demandBys: [
          {
            type: "array",
            required: true,
            message: "请选择需求负责人",
            trigger: ["blur", "change"]
          }
        ],
        phone: [{ required: true, message: "请填写联系电话", trigger: "blur" }],
        demandType: [
          { required: true, message: "请选择需求分类", trigger: "change" }
        ],
        demandRemakr: [
          { required: true, message: "请填写需求概述", trigger: "blur" }
        ],
        demandOnlineDate: [
          { required: true, message: "请选择需求期望上线时间", trigger: "blur" }
        ],
        mainSystemId: [
          { required: true, message: "请选择关联主系统", trigger: "change" }
        ]
      },
      pickerOptions: {}
    };
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  mounted() {},
  methods: {
    init(item) {
      console.log("保存退回", item);
      this.bps = item;
      this.instanceId = item.processInstID;
      this.$refs.log.init(item.processInstID);
      this.getFlowLogs(item.processInstID);
      this.getDemandDetail(item.processInstID);
      this.getDep();
      this.searchEpmlo();
      this.getDic();
      this.searchSystem();
      let pipDates = this.initPipDates();
      //投产日
      this.pickerOptions = {
        // 选择当天及之后的日期
        disabledDate(time) {
          return time.getTime() < Date.now(); // - 8.64e7
        },
        //投产日标红
        cellClassName(Date) {
          if (pipDates.includes(Moment(Date).format("YYYY-MM-DD"))) {
            return "pip_date_class";
          } else if (pipDates.includes(Moment(Date).format("YYYY-M-DD"))) {
            return "pip_date_class";
          }
        }
      };
    },

    getFlowLogs(processInstId) {
      let id = processInstId;
      getFlowLog({ flowcaseno: id })
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          let fdata = res.data.results;
          if (fdata.length > 0) {
            for (var i = 0; i < fdata.length; i++) {
              if (fdata[i].conclusion == "02") {
                alert("该流程为被退回流程，请重新处理，谢谢！");
                break;
              }
            }
          }
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
    },

    // 查询行员
    searchEpmlo() {
      let params = "";
      params = "7004";
      queryEmployeeTree(params)
        .then(res => {
          let data = res.data.results;
          let name = data[0].text;
          this.requirePeo_options = data[0].children.filter(tt => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //显示上传弹出框
    showUpDialog() {
      this.showUpload = true;
    },

    //下载模板
    downloadModle() {
      const loading = common.loading(this);
      getDownloadUrl({ key: "SJZXXUGL_XQMB" }).then(res => {
        loading.close();
        if (res.data.status == 200) {
          console.log(res.data);
          const self = this;
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
      });
    },

    demandByChange(node) {
      console.log("demandByChange", node);
      if (node != undefined) {
        if (node.length > 0) {
          let str = [];
          for (var i = 0; i < node.length; i++) {
            str.push(this.$refs["cascader"].getCheckedNodes()[i].pathLabels[1]);
          }
          console.log(str);
          this.demandData.demandBy = str.join(",");
          console.log(this.demandData.demandBy);
        } else {
          this.demandData.demandBy = "";
        }
      }
    },

    //获取投产日
    initPipDates() {
      let params = {};
      params.size = 2000;
      params.index = 1;
      params.risktype = "02";
      params.isdel = "0";
      let data = common.paramsFormat(params);
      let datas = [];
      getProductionDay(data).then(res => {
        for (var i = 0; i < res.data.results.length; i++) {
          datas.push(res.data.results[i].productdate);
        }
      });
      console.log("datas2021:", datas);
      return datas;
    },

    // 获取详情
    getDemandDetail(val) {
      let params = {};
      params.processInstId = val;
      queryDetail(params)
        .then(res => {
          console.log("详情：", res.data);
          if (res.data.status == 200) {
            if (res.data.results.demandDept != undefined) {
              res.data.results.demandDept = "dep" + res.data.results.demandDept;
            }
            this.demandData = res.data.results;
            if (
              this.demandData.demandBy != undefined &&
              this.demandData.demandBy != ""
            ) {
              let list = this.demandData.demandBy.split(",");
              for (var i = 0; i < list.length; i++) {
                var reg = /\(([^)]*)\)/;
                var badge = list[i].match(reg)[1];
                list[i] = "emp" + badge;
              }
              this.demandData.demandBys = list;
            }
            if (res.data.results.docList != undefined) {
              this.fileList = res.data.results.docList;
            }
            console.log("详情：", res.data.results);
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询系统名称
    searchSystem() {
      let params = "";
      // params = this.pcode;
      params = "6000000300";
      querySystemTree(params).then(res => {
        console.log("系统：", res);
        if (res.data.status == 200) {
          res.data.results[0].children = res.data.results[0].children.filter(
            li => {
              li.text = li.text.replace("总行金融科技部-", "");
              return li;
            }
          );
          res.data.results[1].children[0].children = res.data.results[1].children[0].children.filter(
            li => {
              li.text = li.text.replace("永赢金租金融科技部-", "");
              return li;
            }
          );
          this.systemnos_options = res.data.results;
        } else {
          this.$message.error("获取系统名称失败");
        }
      });
    },

    // 系统名称变化
    systemChange(val) {},

    // 获取部门
    getDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          if (res.data.status == 200) {
            console.log("res1:", res);
            var fild = res.data.results[0].children;
            let name = res.data.results[0].text;
            this.depData = fild.filter(tt => {
              tt.text = tt.text.replace(name + "-", "");
              return tt;
            });

            console.log("depData:", this.depData);
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询业务字典
    getDic() {
      getDicEntry({
        dicttypeid: "NEWKG_XQFL"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        console.log("data:", data);
        //需求分类
        this.demandType_option = data.filter(item => {
          return item.dicttypeid == "NEWKG_XQFL";
        });

        return data;
      });
    },

    // 打开删除messageBox窗口
    delBox() {
      this.$confirm("确认删除该流程吗?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.delPro();
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 删除流程
    delPro() {
      let params = {};
      params.processInstID = this.bps.processInstID;
      params.workItemID = this.bps.workItemID;
      params.processDefName = this.bps.processDefName;
      let data = common.paramsFormat(params);
      console.log("data:", data);
      doDel(data)
        .then(res => {
          if (res.data.status == 200) {
            this.$message.success("流程删除成功");
            this.$router.push({ name: "dashboard" });
          } else {
            this.$message.error("流程删除失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 上传文件
    uploadFile(file) {},

    // 上传文件状态改变
    changeFile(file, fileList) {
      console.log("file:", file, fileList);
      let nowDate = new Date();
      // let objectNo = Moment(nowDate.toLocaleString()).format('YYMMDDHH');
      let objectNo = nowDate.getTime();
      var formData = new FormData();
      formData.append("file", file.raw);
      formData.append("objectNo", objectNo); //唯一编号
      let data = formData;
      const loading = common.loading(this);
      upload(data).then(res => {
        loading.close();
        if (res.data.status == "200") {
          this.fileList.push({
            docname: file.name,
            documentno: res.data.results,
            creators: this.demandData.createBy,
            createtime: Moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
            stage: "需求发起"
          });
          console.log("参数：", this.demandData, this.fileList);
          this.successMessage = "文件上传成功";
          this.messageSuccess();
        } else {
          this.errorMessage = "文件上传失败";
          this.messageError();
        }
      });
    },

    //下载文件
    downloadFile(row) {
      download(row.documentno).then(res => {
        const self = this;
        common.download(res.data, self, row.docname);
      });
    },

    //预览
    openNtkoWindow(objectId) {
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

    //删除文件
    deleteFile(row) {
      delDocument({ documentno: row.documentno }).then(res => {
        const index = this.fileList.indexOf(row);
        this.fileList.splice(index, 1);
        console.log("this.fileList:" + this.fileList);
        console.log("文件删除", res.data);
      });
    },

    //校验规则
    proving() {
      const rule1 = new Promise((resolve, reject) => {
        this.$refs["baseInfoForm"].validate(valid => {
          if (valid) {
            resolve();
          } else {
            this.$message({
              showClose: true,
              message: "操作失败,请填写必填项",
              type: "error"
            });
            return false;
          }
        });
      });
      const rule2 = new Promise((resolve, reject) => {
        this.$refs["demandDetailForm"].validate(valid => {
          if (valid) {
            resolve();
          } else {
            this.$message({
              showClose: true,
              message: "操作失败,请填写必填项",
              type: "error"
            });
            return false;
          }
        });
      });
      Promise.all([rule1, rule2]).then(() => {
        this.commit();
      });
    },

    //提交
    commit() {
      const loading = common.loading(this);
      this.demandData.demandDept = this.demandData.demandDept.replace(
        "dep",
        ""
      );
      this.demandData.files = "";
      for (var i = 0; i < this.fileList.length; i++) {
        if (i < this.fileList.length - 1) {
          this.demandData.files += this.fileList[i].documentno + ",";
        } else {
          this.demandData.files += this.fileList[i].documentno;
        }
      }
      let params = this.demandData;
      params.bps = this.bps;
      flowStartAndRun(params).then(res => {
        console.log("res.data:", res.data);
        loading.close();
        if (res.data.status == 200) {
          let message = common.getFlowMessge(res.data.message);
          let messageArr = message.split(",");
          messageArr[2] = messageArr[2].split(")")[0] + ")";
          this.messageNotifyMore(messageArr);
          this.$router.push({ name: "dashboard" });
        }
      });
    },

    // 存草稿
    saveDraft_DC() {
      const loading = common.loading(this);
      this.demandData.demandDept = this.demandData.demandDept.replace(
        "dep",
        ""
      );
      this.demandData.files = "";
      for (var i = 0; i < this.fileList.length; i++) {
        if (i < this.fileList.length - 1) {
          this.demandData.files += this.fileList[i].documentno + ",";
        } else {
          this.demandData.files += this.fileList[i].documentno;
        }
      }
      let params = this.demandData;
      params.bps = this.bps;
      saveDraft(params)
        .then(res => {
          loading.close();
          console.log("存草稿res:", res.data);
          if (res.data.status == 200) {
            this.$message.success("保存草稿成功");
            this.$router.push({ name: "dashboard" });
          } else {
            this.$message.error("保存草稿失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 消息提示(多节点)
    messageNotifyMore(messageVal) {
      if (messageVal.length > 3) {
        messageVal[messageVal.length - 1] =
          messageVal[messageVal.length - 1].split(")")[0] + ")";
      }
      let messageSec = messageVal.slice(2).join("，");
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
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: "dashboard" });
    }
  }
};
</script>

<style scoped>
.labelstyle {
  color: #606266;
  float: left;
  font-size: 14px;
  margin: 13px 0 8px 0;
}

.formstyle {
  margin: 13px 0 10px 0;
}

.mandatory {
  color: red;
  float: left;
  margin: 13px 5px 8px 0;
}

.czLog {
  margin-bottom: 80px;
}

.requireList_head_right_orange_common {
  margin: 0 5px 0 5px;
}

.nav-head {
  width: 100%;
  background-color: #ffffff;
  padding-left: 25px;
}

.nav-head-name {
  width: 150px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}

.dcdl-container {
  margin: 0 15px 0 15px;
}

.dcdl-main {
  /* min-height: calc(100vh - 84px); */
  padding: 15px;
  background-color: #ffffff;
}

.dcdl-title-main {
  /* min-height: calc(100vh - 84px); */
  padding: 0 15px 0 15px;
  border-bottom: 2px solid #dcdcdc;
  border-left: 1px solid #dcdcdc;
  background-color: #ffffff;
}

.dcdl-title {
  margin: 15px 15px 0 15px;
}

.nav {
  width: 100%;
  background-color: #ffffff;
}

.nav_name {
  width: 300px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}

.title_name {
  width: 150px;
  height: 20px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 20px;
  display: inline-block;
  margin-top: 10px;
  margin-bottom: 10px;
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

/* 布局调整 */
.dcdl-container .el-row {
  margin-bottom: -13px;
  display: flex;
  flex-wrap: wrap;
}

.wrapper-right-keep {
  margin-right: 20px;
}

.bottom {
  margin-top: 20px;
  padding: 15px 0;
}

.nav .wrapper {
  margin-left: 25px;
  margin-right: 25px;
  display: flex;
  justify-content: space-between;
}

.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  margin-top: 20px;
  padding: 15px 0;
  z-index: 999;
}

.nav-bottom .wrapper {
  width: 80%;
  margin-left: 25px;
  margin-right: 25px;
  display: flex;
  justify-content: space-between;
}

.el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}

.upload_scss div {
  font-size: 14px;
  color: rgba(251, 164, 47, 1);
  display: inline-block;
  cursor: pointer;
}

.requireList .el-input--prefix .el-input__inner {
  padding-left: 75px;
}

/* .blank {
  height: calc(100vh - 462px);
} */
</style>

<style>
.dcdl-container .el-form--inline .el-form-item {
  width: 100%;
}

.dcdl-container .el-form--inline .el-form-item__content {
  width: 70%;
}
</style>
<style lang="scss">
//投产日样式
.pip_date_class {
  span {
    background: red;
    color: #000000;
    border-radius: 50%;
    color: #fff !important;
    &:hover {
      background-color: #409eff;
    }
  }
  &::after {
    display: inline-block;
    position: absolute;
    width: 100%;
    font-size: 12px;
    color: red;
    bottom: -15px;
    left: 0;
    text-align: center;
    white-space: nowrap;
  }
}
</style>
<!-- 运维部人员收口 -->
<template>
  <div>
    <div class="nav">
      <el-row>
        <el-col :span="24">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="18">
                <p class="nav_name">数据中心需求管理-需求收口</p>
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

    <el-form :model="demandData" ref="baseInfoForm" :rules="rules">
      <div class="dcdl-container">
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
                    <label class="labelstyle">联系电话:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="phone">
                  <el-input v-model="demandData.phone" :disabled="true"></el-input>
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
        <!-- </el-form> -->
      </div>

      <!-- 需求详情 -->
      <div class="dcdl-title">
        <div class="dcdl-title-main">
          <p class="title_name">需求详情</p>
        </div>
      </div>

      <div class="dcdl-container">
        <!-- <el-form :model="demandData" ref="demandDetailForm" :rules="rules"> -->
        <div class="dcdl-main">
          <el-row justify="space-around" :gutter="30">
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
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">需求期望上线时间:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <div class="requireList">
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
                <a
                  v-if="scope.row.stage == '需求收口'"
                  href="javascript:void();"
                  @click="deleteFile(scope.row)"
                  style="color: rgba(251, 164, 47, 1)"
                >删除</a>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- </el-form> -->
      </div>

      <!-- 需求收口信息 -->
      <div class="dcdl-title">
        <div class="dcdl-title-main">
          <p class="title_name">需求收口信息</p>
        </div>
      </div>

      <div class="dcdl-container">
        <!-- <el-form :model="demandData" ref="endInfoForm" :rules="rules"> -->
        <div class="dcdl-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="mandatory">*</label>
                    <label class="labelstyle">需求状态:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="relationDemandStatus">
                  <el-select v-model="demandData.relationDemandStatus" @change="handleChange">
                    <el-option
                      v-for="item in demandState_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <!-- <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">需求进度:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="relationDemandProgress">
                  <el-input v-model="demandData.relationDemandProgress" @change="changeFunction">
                    <template slot="suffix">
                      <span style="color: red;">%</span>
                    </template>
                  </el-input>
                </el-form-item>
              </div>
            </el-col>-->
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label v-if="showInput" class="mandatory">*</label>
                    <label class="labelstyle">关联意向编号:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="relationDemandNo">
                  <el-autocomplete
                    multiple
                    popper-class="my-autocomplete"
                    v-model="demandData.relationDemandNo"
                    :fetch-suggestions="querySearchAsync"
                    @select="handleSelect"
                    style="width: 100%"
                    ref="relationDemandNoInput"
                  >
                    <template slot-scope="{ item }">
                      <div class="number">{{ item.demandno }}</div>
                      <span class="name">{{ item.demandname }}</span>
                    </template>
                  </el-autocomplete>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-form>

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
                v-if="scope.row.stage == '需求收口'"
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
      <approve-bottom-dc
        :proving="proving"
        :flowItem="bps"
        :demandData="demandData"
        :saveVisbleState="true"
        :fileList="fileList"
        v-if="isDb"
      ></approve-bottom-dc>
    </div>
  </div>
</template>

<script>
import { querySysList } from "@/api/branchIntentionApi/yyxmbg";
import { getEmpBaseInfo } from "@/api/integrated/wbryxxbg";
import {
  queryResourceDept,
  querySystemTree,
  queryEmployeeTree
} from "@/api/integrated/wbryzrlc";
import { queryWorkItem, doDel, getDownloadUrl } from "@/api/common/flowApi";
import { getDicEntry } from "@/api/eosDictEntry";
import { pagerList } from "@/api/branchIntentionApi/fhyx";
import { mapGetters } from "vuex";
import {
  upload,
  queryDetail,
  download,
  delDocument,
  runFlowInfo,
  modifyDacenterDemand,
  getProductionDay
} from "@/api/dataCenter/dataCenDemManager";

import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import buttonDelete from "@/components/eleButton/delete";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import approveBottomDc from "./approveBottomDc";
import common from "@/api/common";
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
    approveBottom,
    approveBottomDc
  },
  data() {
    return {
      size: "mini", //按钮大小
      buttonKeep: "保 存",
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
        relationDemandStatus: "", //需求状态
        relationDemandProgress: "", //需求进度
        relationDemandNo: "", //关联意向编号
        demandBys: [] //需求负责人
      },
      params: {
        bps: {}
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
      systemnos_options: [],
      systemParams: {
        //系统名称配置信息
        label: "text",
        value: "pcode",
        children: "children",
        emitPath: false
      },
      demandType_option: [], //需求分类
      demandState_option: [], //需求状态
      successMessage: "", //成功提示
      errorMessage: "", //失败提示
      fileList: [], //文件列表
      uploadBy: "", //上传人
      bps: {}, //bps数据
      isDb: true, // 是否待办 默认 true
      instanceId: "", //流程id
      relationDemandNoSuggestions: [],
      showInput: false,
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
        // phone: [{ required: true, message: "请填写联系电话", trigger: "blur" }],
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
        ],
        relationDemandStatus: [
          { required: true, message: "请选择需求状态", trigger: "change" }
        ],
        relationDemandProgress: [
          { required: true, message: "请填写需求进度", trigger: "blur" }
        ],
        relationDemandNo: [
          {
            required: false,
            message: "需求状态为需求评审中时，需求编号为必填项",
            trigger: "blur"
          }
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
      console.log("运维部收口", item);
      this.bps = item;
      this.instanceId = item.processInstID;
      this.$refs.log.init(item.processInstID);
      this.searchSystem();
      this.getDemandDetail(item.processInstID);
      this.getCurrentUser();

      // this.loadAll();
      this.getDep();
      this.searchEpmlo();
      this.getDic();
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

    //获取当前登录人用户信息
    getCurrentUser() {
      let data = common.paramsFormat(this.userId);
      getEmpBaseInfo(data).then(res => {
        let results = res.data.results;
        this.uploadBy = results.empname + "(" + results.badge + ")";
      });
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
      return datas;
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

    demandByChange(node) {
      if (node != undefined) {
        if (node.length > 0) {
          let str = [];
          for (var i = 0; i < node.length; i++) {
            str.push(this.$refs["cascader"].getCheckedNodes()[i].pathLabels[1]);
          }
          // this.demandData.demandBy = this.demandBy.join(',');
          this.demandData.demandBy = str.join(",");
          if (
            this.demandData.demandBy != undefined &&
            this.demandData.demandBy != "" &&
            this.demandData.demandBy.indexOf(",") == 0
          ) {
            this.demandData.demandBy = this.demandData.demandBy.substring(1);
          }
        } else {
          this.demandData.demandBy = "";
        }
      }
    },

    // 运维部保存编辑
    modifyDacenterDemand_DC() {
      const loading = common.loading(this);
      this.demandData.demandDept = this.demandData.demandDept.replace(
        "dep",
        ""
      );
      this.demandData.files = "";
      for (var i = 0; i < this.fileList.length; i++) {
        if (this.fileList[i].stage == "需求收口") {
          this.demandData.files += this.fileList[i].documentno + ",";
        }
      }
      this.demandData.files = this.demandData.files.substring(
        0,
        this.demandData.files.lastIndexOf(",")
      );
      let params = this.demandData;
      params.bps = this.bps;
      modifyDacenterDemand(params)
        .then(res => {
          loading.close();
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

    // 获取详情
    getDemandDetail(val) {
      let params = {};
      params.processInstId = val;
      queryDetail(params)
        .then(res => {
          if (res.data.status == 200) {
            if (res.data.results.demandDept != undefined) {
              res.data.results.demandDept = "dep" + res.data.results.demandDept;
            }
            this.demandData = res.data.results;
            let list = this.demandData.demandBy.split(",");
            for (var i = 0; i < list.length; i++) {
              var reg = /\(([^)]*)\)/;
              var badge = list[i].match(reg)[1];
              list[i] = "emp" + badge;
            }
            this.demandData.demandBys = list;
            if (res.data.results.docList != undefined) {
              this.fileList = res.data.results.docList;
            }
            if (this.demandData.relationDemandStatus == 3 && this.rules.hasOwnProperty("relationDemandNo")) {
              this.$set(this.rules, "relationDemandNo", [
                {
                  required: true,
                  message: "需求状态为需求评审中时，需求编号为必填项",
                  trigger: "blur"
                }
              ]);
              this.showInput = true;
            } else {
              this.$set(this.rules, "relationDemandNo", [
                {
                  required: false,
                  message: "需求状态为需求评审中时，需求编号为必填项",
                  trigger: "blur"
                }
              ]);
              this.showInput = false;
            }
          }
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
          const self = this;
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
      });
    },

    // 查询系统名称
    searchSystem() {
      let params = "";
      // params = this.pcode;
      params = "6000000300";
      querySystemTree(params)
        .then(res => {
          if (res.data.status == 200) {
            res.data.results[0].children = res.data.results[0].children.filter(
              li => {
                li.text = li.text.replace("总行金融科技部-", "");
                return li;
              }
            );
            this.systemnos_options = res.data.results;
          } else {
            this.$message.error("获取系统名称失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
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
            var fild = res.data.results[0].children;
            let name = res.data.results[0].text;
            this.depData = fild.filter(tt => {
              tt.text = tt.text.replace(name + "-", "");
              return tt;
            });
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //限制0-100
    changeFunction(val) {
      var reg = /^((?!0)\d{1,2}|100)$/;
      if (!val.match(reg) && val != "0") {
        this.demandData.relationDemandProgress = "";
        this.$message.error("需求编号为0-100的整数");
      }
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
        //需求分类
        this.demandType_option = data.filter(item => {
          return item.dicttypeid == "NEWKG_XQFL";
        });

        return data;
      });

      getDicEntry({
        dicttypeid: "NEWKG_XQZT"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //需求状态
        this.demandState_option = data.filter(item => {
          return item.dicttypeid == "NEWKG_XQZT";
        });

        return data;
      });
    },

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
      let data = formData;
      const loading = common.loading(this);
      upload(data).then(res => {
        loading.close();
        if (res.data.status == "200") {
          this.fileList.push({
            docname: file.name,
            documentno: res.data.results,
            creators: this.uploadBy,
            createtime: Moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
            stage: "需求收口"
          });
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
      });
    },

    handleChange(val) {
      if (val == 3 && this.rules.hasOwnProperty("relationDemandNo")) {
        this.$set(this.rules, "relationDemandNo", [
          {
            required: true,
            message: "需求状态为需求评审中时，需求编号为必填项",
            trigger: "blur"
          }
        ]);
        this.showInput = true;
      } else {
        this.$set(this.rules, "relationDemandNo", [
          {
            required: false,
            message: "需求状态为需求评审中时，需求编号为必填项",
            trigger: "blur"
          }
        ]);
        this.showInput = false;
      }
      this.$refs.relationDemandNoInput.focus();
      this.$refs.relationDemandNoInput.blur();
    },
    // 提交
    proving(data) {
      let status = new Promise(() => {});
      this.$refs["baseInfoForm"].validate(valid => {
        if (valid) {
          status = this.liuzhuan(data).then(val => {
            return val;
          });
        } else {
          this.$message({
            showClose: true,
            message: "操作失败,请填写必填项",
            type: "error"
          });
          return false;
        }
      });
      return status;
    },

    liuzhuan(params) {
      const loading = common.loading(this);
      this.bps.conclusion = params.conclusion;
      this.bps.suggest = params.suggest;
      this.bps.processInstId = this.instanceId;
      this.demandData.demandDept = this.demandData.demandDept.replace(
        "dep",
        ""
      );
      this.demandData.files = "";
      for (var i = 0; i < this.fileList.length; i++) {
        if (this.fileList[i].stage == "需求收口") {
          this.demandData.files += this.fileList[i].documentno + ",";
        }
      }
      this.demandData.files = this.demandData.files.substring(
        0,
        this.demandData.files.lastIndexOf(",")
      );
      let folw = this.demandData;
      folw.bps = {
        conclusion: params.conclusion,
        suggest: params.suggest,
        processInstId: this.instanceId
      };
      folw.processInstId = this.instanceId;
      return runFlowInfo(folw)
        .then(res => {
          loading.close();
          return res.data;
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //输入框建议
    loadAll(val) {
      let params = {};
      params.size = 20;
      params.index = 1;
      params.demandno = val;
      params.status = "01,02,03,04,05,06";
      params.startTime = "2019-01-01";
      params.endTime = "2030-12-31";
      let data = common.paramsFormat(params);
      pagerList(data).then(res => {
        this.relationDemandNoSuggestions = res.data.results;
      });
    },
    querySearchAsync(queryString, cb) {
      // this.loadAll(queryString);
      // var relationDemandNoSuggestions = this.relationDemandNoSuggestions;
      var relationDemandNoSuggestions = [];
      var results = queryString
        ? relationDemandNoSuggestions.filter(
            this.createStateFilter(queryString)
          )
        : relationDemandNoSuggestions;
      cb(results);
    },
    createStateFilter(queryString) {
      return state => {
        return (
          state.demandno.toLowerCase().indexOf(queryString.toLowerCase()) !=
            -1 || state.demandname.indexOf(queryString) != -1
        );
      };
    },
    handleSelect(item) {
      this.demandData.relationDemandNo = item.demandno;
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: "dashboard" });
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

.requireList_head_right_orange_common {
  margin: 0 5px 0 5px;
}

.czLog {
  margin-bottom: 80px;
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

.my-autocomplete li {
  line-height: normal;
  padding: 7px;
}

.number {
  /* padding: -10px; */
  margin: -5px 0 -15px 0;
  text-overflow: ellipsis;
  overflow: hidden;
}

.name {
  line-height: 14px;
  font-size: 12px;
  color: #b4b4b4;
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

.nav-bottom {
  width: 200%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  float: left;
  z-index: 999;
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
<!-- 已办归档 -->
<template>
  <div>
    <div class="nav">
      <el-row>
        <el-col :span="24">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="24">
                <p class="nav_name">数据中心需求管理-已办归档</p>
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
      <el-form :model="demandData" ref="baseInfoForm">
        <div class="dcdl-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">标题:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="title">
                  <el-input
                    placeholder="请填写系统简称+需求内容，限25字以内"
                    v-model="demandData.title"
                    :disabled="true"
                    :title="demandData.title"
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">需求负责人:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="demandBy">
                  <el-input v-model="demandData.demandBy" :disabled="true" :title="demandData.demandBy"></el-input>
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
      </el-form>
    </div>

    <!-- 需求详情 -->
    <div class="dcdl-title">
      <div class="dcdl-title-main">
        <p class="title_name">需求详情</p>
      </div>
    </div>

    <div class="dcdl-container">
      <el-form :model="demandData" ref="demandDetailForm">
        <div class="dcdl-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">需求分类:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="demandType">
                  <el-select v-model="demandData.demandType" :disabled="true">
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
                      :disabled="true"
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
                    disabled
                    style="width:100%"
                  ></el-cascader>
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
                    :disabled="true"
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
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form>
    </div>

    <!-- 需求收口信息 -->
    <div class="dcdl-title" v-if="flag">
      <div class="dcdl-title-main">
        <p class="title_name">需求收口信息</p>
      </div>
    </div>

    <div class="dcdl-container" v-if="flag">
      <el-form :model="demandData" ref="endInfoForm">
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
                <el-form-item>
                  <el-select v-model="demandData.relationDemandStatus" :disabled="true">
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
              <el-form-item label="需求进度">
                <el-input v-model="demandData.relationDemandProgress" :disabled="true">
                  <template slot="suffix">
                    <span style="color: red;">%</span>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>-->
            <el-col :span="6">
              <el-row>
                <el-col :span="24">
                  <div>
                    <label class="labelstyle">关联意向编号:</label>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item>
                  <el-input v-model="demandData.relationDemandNo" :disabled="true"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>

    <!-- 操作日志 -->
    <!-- <div class="czLog" v-if="bps.status == 'yb'">
      <flow-logs ref="log"></flow-logs>
    </div>-->
    <div class="czLog">
      <flow-logs-gd ref="log"></flow-logs-gd>
    </div>

    <div v-if=" bps.status == 'yb' && bps.activityDefID == 'manualActivity1' ">
      <yb-revoke :flowItem="bps"></yb-revoke>
    </div>
  </div>
</template>

<script>
import { querySysList } from "@/api/branchIntentionApi/yyxmbg";
import { getEmpBaseInfo } from "@/api/integrated/wbryxxbg";
import {
  queryDetail,
  download,
  runFlowInfo
} from "@/api/dataCenter/dataCenDemManager";
import { queryResourceDept, querySystemTree } from "@/api/integrated/wbryzrlc";
import { queryWorkItem, doDel } from "@/api/common/flowApi";
import { getDicEntry } from "@/api/eosDictEntry";
import { mapGetters } from "vuex";

import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import buttonDelete from "@/components/eleButton/delete";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import FlowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
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
    ybRevoke,
    FlowLogsGd
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
        demandRemakr: "" //需求概述
      },
      params: {
        bps: {}
      },
      depData: [], //部门数据
      sysData: [], //主系统数据
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
      fileList: [], //文件列表
      bps: {}, //bps数据
      isDb: true, // 是否待办 默认 true
      instanceId: "", //流程id
      flag: false //是否显示需求收口信息
    };
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  mounted() {},
  methods: {
    init(item) {
      console.log("已办归档", item);
      this.bps = item;
      if (item.status == "yb") {
        this.$refs.log.init(item);
        this.getDemandDetail(item.processInstID);
      } else if (item.status == "gd") {
        this.$refs.log.init(item);
        this.getDemandDetail(item.instanceid);
      }
      this.searchSystem();

      this.getDep();
      this.getDic();
    },

    // 获取详情
    getDemandDetail(val) {
      let params = {};
      params.processInstId = val;
      queryDetail(params)
        .then(res => {
          console.log("详情数据", res.data);
          if (res.data.status == 200) {
            if (res.data.results.demandDept != undefined) {
              res.data.results.demandDept = "dep" + res.data.results.demandDept;
            }
            this.demandData = res.data.results;
            if (res.data.results.docList != undefined) {
              this.fileList = res.data.results.docList;
            }
            if (
              (this.demandData.relationDemandStatus != undefined &&
                this.demandData.relationDemandStatus != "") ||
              (this.demandData.relationDemandProgress != undefined &&
                this.demandData.relationDemandProgress != "") ||
              (this.demandData.relationDemandNo != undefined &&
                this.demandData.relationDemandNo != "")
            ) {
              this.flag = true;
            } else {
              this.flag = false;
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
      querySystemTree(params)
        .then(res => {
          console.log("系统：", res);
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

            // console.log("depData:", this.depData);
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

      getDicEntry({
        dicttypeid: "NEWKG_XQZT"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        console.log("data:", data);
        //需求状态
        this.demandState_option = data.filter(item => {
          return item.dicttypeid == "NEWKG_XQZT";
        });

        return data;
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
    // 提交
    proving(data) {
      let status = new Promise(() => {});
      status = this.liuzhuan(data).then(val => {
        return val;
      });
      return status;
    },
    liuzhuan(params) {
      const loading = common.loading(this);
      this.bps.conclusion = params.conclusion;
      this.bps.suggest = params.suggest;
      this.bps.processInstId = this.instanceId;
      let folw = {
        bps: {
          conclusion: params.conclusion,
          suggest: params.suggest,
          processInstId: this.instanceId
        },
        processInstId: this.instanceId
      };
      console.log("folw", folw);
      return runFlowInfo(folw)
        .then(res => {
          loading.close();
          return res.data;
        })
        .catch(error => {
          this.$message.error(error);
        });
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
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
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

.el-tooltip__popper {
  max-width: 600px;
}
</style>
<!-- 管理员评估 -->
<template>
  <div class="container container_zb">
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">数据中心门禁权限申请-管理员评估</p>
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
    <!-- 发起面试 -->
    <div class="contentMain contentMain_Add">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">申请信息</p>
      </div>
      <div class="content">
        <el-form :model="applyInfo" ref="applyInfo">
          <div class="row_1">
            <div>
              <el-row justify="space-around" :gutter="30">
                <el-col :span="8">
                  <div>
                    <el-form-item label="发起人:" label-width="120px" prop="launchBy">
                      <el-input
                        :title="applyInfo.launchByName"
                        v-model="applyInfo.launchByName"
                        disabled
                        style="width: 250px;"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <el-form-item label="发起时间:" label-width="120px" prop="launchDate">
                      <el-input
                        :title="applyInfo.launchDate"
                        v-model="applyInfo.launchDate"
                        disabled
                        style="width: 250px;"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <el-form-item label="发起部门:" label-width="120px" prop="launchDept">
                      <el-input
                        :title="applyInfo.launchDeptName"
                        v-model="applyInfo.launchDeptName"
                        disabled
                        style="width: 250px;"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <el-form-item
                      label="申请地点:"
                      label-width="120px"
                      prop="applyAddr"
                      :rules="[{ required: true, message: '请选择申请地点', trigger: 'change' }]"
                    >
                      <el-select
                        placeholder="请选择"
                        v-model="applyInfo.applyAddr"
                        style="width: 250px;"
                        disabled
                      >
                        <el-option
                          v-for="item in apply_addr_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item.dictid"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="16">
                  <div style="float: left;">
                    <el-form-item
                      label="预计开卡时间:"
                      label-width="120px"
                      prop="openCardDate"
                      :rules="[{ required: true, message: '请选择预计开卡时间', trigger: 'change' }]"
                    >
                      <el-date-picker
                        disabled
                        v-model="applyInfo.openCardDate"
                        value-format="yyyy-MM-dd"
                        :picker-options="pickerOptions"
                        style="width: 250px;"
                      ></el-date-picker>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8" v-if="showManager">
                  <div>
                    <el-form-item
                      label="分管总经理:"
                      label-width="120px"
                      prop="managerUserName"
                      :rules="[{ required: true, message: '请选择分管总经理', trigger: ['change','blur'] }]"
                    >
                      <el-input
                        v-model="applyInfo.managerUserName"
                        :disabled="true"
                        style="width: 250px"
                        :title="applyInfo.managerUserName"
                      >
                        <!-- <el-button slot="append" icon="el-icon-more"></el-button> -->
                      </el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>

          <!-- 表格 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <div class="contentTwo_title">
                    <p class="row_2_detail">申请人信息
                      <!-- <span class="add_row" @click="eduExperAdd(1)">新增一行</span> -->
                      <!-- 申请表单 -->
                    </p>
                  </div>
                  <el-row justify="space-around" :gutter="30">
                    <el-col :span="24">
                      <el-form-item label="人员类型：" prop="userType">
                        <el-radio-group v-model="applyInfo.userType">
                          <el-radio label="行员" disabled></el-radio>
                          <el-radio label="外包" disabled></el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-table
                    ref="applyerInfo"
                    :data="applyInfo.applyerList"
                    highlight-current-row
                    max-height="510"
                    :row-style="{height: '50px'}"
                    style="width: 100%"
                  >
                    <el-table-column type="index" label="序号" width="50"></el-table-column>
                    <el-table-column prop="applyName" label="申请人姓名(工号)" width="135" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column
                      prop="applyDeptName"
                      label="申请人部门"
                      width="200"
                      :show-overflow-tooltip="true"
                    ></el-table-column>
                    <el-table-column prop="applyType" label="申请类型" width="80" :show-overflow-tooltip="true">
                      <template slot-scope="scope">
                        <span
                          v-for="item in apply_type_options"
                          :key="item.dictid"
                          v-if="item.dictid == scope.row.applyType"
                        >{{item.dictname}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="permissionsType" label="权限类别" width="100" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="reason" label="事由" width="200" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="companyName" label="公司" width="210" v-if="showMoreCol" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="applyTerm" label="申请时长" width="120" v-if="showMoreCol" :show-overflow-tooltip="true">
                      <template slot-scope="scope">
                        <span
                          v-for="item in apply_term_options"
                          :key="item.dictid"
                          v-if="item.dictid == scope.row.applyTerm"
                        >{{item.dictname}}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>
    </div>

    <!-- 操作日志 -->
    <div class="czLog">
      <flow-logs ref="log"></flow-logs>
    </div>
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flowItem="bps"></approve-bottom>
    </div>
  </div>
</template>
<script>
import morekejiBank from "@/views/accessPermission/add";
import { getDicEntry } from "@/api/eosDictEntry";
import {
  queryAllInfoDetail,
  adminAssessment,
  queryDetailForDept
} from "@/api/dataCenter/dataCenAccessPermission";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import common from "@/api/common";
import approveBottom from "./approveBottom";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonDelete from "@/components/eleButton/delete"; //引入删除按钮

import { queryWorkItem } from "@/api/integrated/wbryzrlc";
import { mapGetters } from "vuex";
import Moment from "moment";
export default {
  name: "apTwoManager",
  components: {
    morekejiBank,
    buttonOrange,
    buttonCommon,
    buttonDelete,
    flowLogs,
    approveBottom
  },
  data() {
    return {
      size: "small",
      totalNumber: 0,
      showUserDialog: false, //选择人员
      buttonAdd: "增加", //增加按钮
      buttonDel: "删除", //删除按钮
      buttonimp: "导入", //导入按钮
      buttonClose: "关闭", //关闭按钮
      buttonKeep: "存草稿", //保存按钮
      buttonCommit: "提交", //提交按钮
      depData: [], //部门数据
      selectRows: [], //选中行
      dept: "2", //总行金融科技部id

      // 申请表单
      applyInfo: {
        launchBy: "", //发起人
        launchByName: "", //发起人名称
        launchDate: "", //发起时间
        launchDept: "", //发起部门
        launchDeptName: "", //发起部门名称
        applyAddr: "", //申请地点
        openCardDate: "", //预计开卡时间
        userType: "", //人员类型
        applyerList: [
          // {
          //   applyName: "", //申请人
          //   applyUserid: "", //申请人工号
          //   applyDept: "", //申请人部门
          //   applyDeptName: "", //申请人部门名称
          //   applyType: "", //申请类型
          //   permissionsType: "", //权限类别
          //   reason: "", //事由
          //   companyId: "", //公司
          //   applyTerm: "" //申请时长
          // }
        ]
      },
      bps: {}, //bps数据
      drFileData: {}, //导入文件信息
      drList: [], //导入列表
      ifShowEnDialog: false, //是否显示导入弹窗
      apply_addr_options: [], //申请地点列表
      apply_type_options: [], //申请类型列表
      apply_term_options: [], //申请时长列表
      pickerOptions: {},
      showMoreCol: false, //显示公司和开卡时长
      showManager: false //显示分管总经理
    };
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  watch: {
    mpOpts(newVal, oldVal) {
      if (oldVal.length != 0) {
        this.applyInfo.rlId = "";
      }
    }
  },
  beforeUpdate() {},
  created() {},
  mounted() {},

  methods: {
    init(item) {
      console.log("管理员评估", item);
      this.bps = item;
      this.instanceId = item.processInstID;
      this.$refs.log.init(item.processInstID);
      this.getAPDetails(item.processInstID);
      this.getDep();
      this.getDict();
    },

    // 获取详情
    getAPDetails(val) {
      let params = {};
      params.processInstId = val;
      queryAllInfoDetail(params)
        .then(res => {
          if (res.data.status == 200) {
            this.applyInfo = res.data.results;
            if (this.applyInfo.userType == "行员") {
              this.showMoreCol = false;
            }
            if (this.applyInfo.userType == "外包") {
              this.showMoreCol = true;
            }
            if (
              this.applyInfo.managerUserName != undefined &&
              this.applyInfo.managerUserName != ""
            ) {
              this.showManager = true;
            }
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

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

    getDict() {
      // 查询业务字典
      getDicEntry({
        dicttypeid:
          "PPM_SJZXMJ_APPLYDD,PPM_ACCESS_APPLY_STATE,PPM_ACCE_APPLYTIME"
      }).then(res => {
        if (res.data.status !== 200) {
          this.alter(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // 申请地点
        this.apply_addr_options = data.filter(item => {
          return item.dicttypeid == "PPM_SJZXMJ_APPLYDD";
        });

        //申请类型
        this.apply_type_options = data.filter(item => {
          return item.dicttypeid == "PPM_ACCESS_APPLY_STATE";
        });

        // 申请时长
        this.apply_term_options = data.filter(item => {
          return item.dicttypeid == "PPM_ACCE_APPLYTIME";
        });

        return data;
      });
    },

    //保存
    save() {},

    //提交
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
      return adminAssessment(folw)
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
    },

    //获取待办工作项详情-单条
    getWorkItem(val) {
      const loading = common.loading(this);
      let params = {};
      params.processInstID = val;
      queryWorkItem(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.bps = res.data.results;
            this.delBtnIf = true;
          } else {
            this.$message.error("获取bps失败");
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

    //消息提示
    messageNotify(messageVal) {
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
    }
  }
};
</script>
<style scoped>
.czLog {
  margin-bottom: 80px;
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
  width: 300px;
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

.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}

.content {
  width: 100%;
  font-size: 16px;
  /* background-color: #ffffff; */
  /* padding: 15px 20px; */
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

.requireList_head_right_orange_common {
  margin: 0 5px 10px 5px;
  float: left;
}

.tips {
  color: red;
  font-size: 14px;
  font-weight: 500;
  margin-left: 10px;
}

/* 空格占位 */
.blank {
  width: 100%;
  height: 160px;
}

.bottom {
  position: absolute;
  bottom: 0;
  padding: 15px 0;
}

.upload_scss div {
  font-size: 14px;
  color: rgba(251, 164, 47, 1);
  display: inline-block;
  cursor: pointer;
}

/* 重传 */
.againUpLoad {
  font-size: 14px;
  color: rgba(251, 164, 47, 1);
  display: inline-block;
}

.wrapper_right_bottom_1_zb {
  margin-right: 10px;
}
</style>

<style>
/* 级联选择器 */
.contentMain_Add .el-cascader {
  display: block;
}

/* 下拉框 */
.contentMain_Add .el-select {
  display: block;
}

/* 表格删除按钮 */
.contentMain_Add .el-icon-delete {
  width: 11px;
  height: 11px;
  color: #f56c6c;
}

.operation_add .el-button {
  border: 1px solid #ffffff;
  padding: 0;
}

/* table表格修改 */

.row_2_add .el-table th {
  background: rgba(250, 250, 250, 1);
}

.row_2_add .el-table thead {
  font-size: 14px;
  color: rgba(38, 38, 38, 1);
  font-weight: 400;
}

.row_2_add .el-table--medium th {
  padding: 0;
}

.row_2_add .el-table--medium td {
  padding: 0;
}

.row_2_add .el-table th {
  padding: 10px 0;
}

/* form表单样式修改 */
.contentMain_Add .el-form-item {
  margin: 16px 0;
}

/* 计数器 */
.contentMain_Add .el-input-number--medium {
  width: 100px;
}

/* 上传文件按钮 */

.tableCon .el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}

/* 重传 */
.againUpLoad .el-upload--picture-card:hover {
  color: rgba(251, 164, 47, 1);
}

.againUpLoad .el-upload:focus {
  color: rgba(251, 164, 47, 1);
}
</style>
<style>
.el-tooltip__popper {
  max-width: 500px;
}
</style>
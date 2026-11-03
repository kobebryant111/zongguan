<!-- 确认并反馈 -->
<template>
  <div>
    <div class="nav">
      <el-row>
        <el-col :span="24">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="18">
                <p class="nav_name">数据中心后督管理-确认并反馈</p>
              </el-col>
            </div>
            <div style="float:right;" class="wrapper_right">
              <el-col :span="1.6">
                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
              </el-col>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <el-form :model="psireData" ref="baseInfoForm" :rules="rules">
      <!-- 基础信息 -->
      <div class="psire-title">
        <div class="psire-title-main">
          <p class="title_name">基础信息</p>
        </div>
      </div>

      <div class="psire-container">
        <div class="psire-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="12">
              <el-form-item label="标题:" style="width: 550px;" label-width="150px" prop="psireName">
                <el-input
                  :title="psireData.title"
                  style="width: 400px;"
                  v-model="psireData.title"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="申请日期:"
                style="width: 500px;"
                label-width="100px"
                prop="psireName"
              >
                <el-input
                  :title="psireData.applyDate"
                  style="width: 400px;"
                  v-model="psireData.applyDate"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="30">
            <el-col :span="12">
              <el-form-item
                label="流程名称:"
                style="width: 550px;"
                label-width="150px"
                prop="psireName"
              >
                <el-input
                  :title="psireData.psireName"
                  style="width: 400px;"
                  v-model="psireData.psireName"
                  maxlength="25"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="流程编号:" style="width: 500px;" label-width="100px" prop="psireNo">
                <el-input
                  :title="psireData.psireNo"
                  class="pointer_relationDemandNo"
                  v-model="psireData.psireNo"
                  disabled
                ></el-input>
                <div
                  class="overContainer"
                  @click="skipToOldKG(psireData.flowUrl,psireData.instanceId)"
                ></div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="30">
            <el-col :span="12">
              <el-form-item
                label="关联人员:"
                style="width: 550px;"
                label-width="150px"
                prop="relationUserName"
              >
                <el-input
                  :title="psireData.relationUserName"
                  style="width: 400px;"
                  v-model="psireData.relationUserName"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="关联部门:" style="width: 500px;" label-width="100px">
                <!-- <el-input v-model="psireData.dept" disabled></el-input> -->
                <el-select v-model="psireData.dept" disabled>
                  <el-option
                    style="width: 400px;"
                    v-for="item in depData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="30">
            <el-col :span="12">
              <el-form-item
                label="问题分类:"
                style="width: 550px;"
                label-width="150px"
                prop="problemType"
                disabled
              >
                <el-select
                  style="width: 400px;"
                  placeholder="请选择"
                  v-model="psireData.problemType"
                  disabled
                >
                  <el-option key="0" label="流程问题" value="0"></el-option>
                  <el-option key="1" label="专项问题" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="问题详情:"
                style="width: 500px;"
                label-width="100px"
                prop="problemDetail"
              >
                <el-input
                  :title="psireData.problemDetail"
                  style="width: 400px;"
                  v-model="psireData.problemDetail"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 反馈信息 -->
      <div class="psire-title">
        <div class="psire-title-main">
          <p class="title_name">反馈信息</p>
        </div>
      </div>

      <div class="psire-container">
        <div class="psire-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="20">
              <el-form-item label="反馈内容" prop="feedbackContent">
                <el-input
                  :title="psireData.feedbackContent"
                  placeholder="最多可输入500个字！"
                  type="textarea"
                  :autosize="{ minRows: 3}"
                  v-model="psireData.feedbackContent"
                  maxlength="500"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-form>
    <!-- 操作日志 -->
    <div class="czLog">
      <flow-logs ref="log"></flow-logs>
    </div>

    <div class="nav-bottom">
      <div class="wrapper">
        <div class="wrapper_left"></div>
        <div class="wrapper_right">
          <div class="requireList_head_right_orange_common">
            <button-common :buttonName="buttonSave" :size="sizes" :fns="save"></button-common>
          </div>
          <div class="requireList_head_right_orange_common">
            <button-orange :buttonName="buttonCircu" :size="sizes" :fns="proving"></button-orange>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange

import common from "@/api/common";
import Moment from "moment";
import {
  queryDetail,
  update,
  relationUserRunFlowInfo
} from "@/api/dataCenter/dataCenPostSupervision";
import { getDomainName } from "@/api/eosDictEntry";
import { mapGetters } from "vuex";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
export default {
  name: "confirmAndFeedback",
  components: {
    flowLogs,
    buttonCommon,
    buttonOrange
  },
  data() {
    return {
      size: "mini", //按钮大小
      sizes: "small", //按钮大小
      depData: [], //部门数据
      buttonClose: "关 闭", //关闭按钮
      buttonSave: "保存",
      buttonCircu: "流转",
      psireData: {
        title: "", //标题
        applyDate: "", //申请时间
        psireName: "", //流程名称
        psireNo: "", //流程编号
        relationUser: "", //关联人员id
        relationUserName: "", //关联人员
        dept: "", //关联部门
        problemType: "", //问题分类
        problemDetail: "", //问题详情
        feedbackContent: "" //反馈内容
      },
      bps: {},
      instanceId: "",
      rules: {
        feedbackContent: [
          { required: true, message: "请填写反馈信息", trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  mounted() {},
  methods: {
    init(item) {
      console.log("confirmAndFeedback", item);
      this.bps = item;
      this.instanceId = item.processInstID;
      this.$refs.log.init(item.processInstID);
      this.getDemandDetail(item.processInstID);

      this.getDep();
    },

    // 获取详情
    getDemandDetail(val) {
      let params = {};
      params.processInstId = val;
      queryDetail(params)
        .then(res => {
          if (res.data.status == 200) {
            this.psireData = res.data.results;
            this.psireData.dept = "dep" + this.psireData.dept;
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

    save() {
      let params = this.psireData;
      params.dept = params.dept.replace("dep", "");
      let data = common.paramsFormat(params);
      update(data).then(res => {
        if (res.data.status == 200) {
          this.$message.success("保存成功");
          this.$router.push({ name: "dashboard" });
        } else {
          this.$message.error("保存失败");
        }
      });
    },

    //提交
    proving() {
      this.$refs["baseInfoForm"].validate(valid => {
        if (valid) {
          this.liuzhuan();
        } else {
          this.$message({
            showClose: true,
            message: "操作失败,请填写必填项",
            type: "error"
          });
          return false;
        }
      });
    },

    //流转
    liuzhuan() {
      const loading = common.loading(this);
      this.bps.conclusion = "01";
      this.bps.suggest = "同意";
      this.bps.processInstId = this.instanceId;
      this.psireData.dept = this.psireData.dept.replace("dep", "");
      let folw = this.psireData;
      folw.bps = {
        conclusion: "01",
        suggest: "同意",
        processInstId: this.instanceId
      };
      folw.processInstId = this.instanceId;
      relationUserRunFlowInfo(folw).then(res => {
        loading.close();
        if (res.data.status == 200) {
          if (res.data.message.indexOf("已结束") == -1) {
            let message = common.getFlowMessge(res.data.message);
            
            let messageArr = message.split(",");
            this.$nextTick(() => {
              this.messageNotify(messageArr);
              // this.$router.back();
              this.$router.push({ name: "dashboard" });
            });
          } else {
            this.$nextTick(() => {
              this.messageNotify2(res.data.message);
              // this.$router.back();
              this.$router.push({ name: "dashboard" });
            });
          }
        }
      });
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: "dashboard" });
    },

    //跳转老应用
    skipToOldKG(flowUrl, instanceId) {
      let link = document.createElement("a");
      let url_params =
        "/com.example.ppm.system.projectM.newApplicationPostGovernor.flow?redirectUrl=" +
        flowUrl +
        "&processInstID=" +
        instanceId +
        "&md5=" +
        this.$md5(this.userId) +
        "&userid=" +
        this.userId;
      let params = { dicttypeid: "PPM_URL", dictid: "1" };
      getDomainName(params).then(res => {
        let webUrl = res.data.results;
        link.href = webUrl + url_params;
        link.target = "_blank";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      });
    },

    // 消息提示
    messageNotify(messageVal) {
      
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

    messageNotify2(messageVal) {
      this.$notify({
        title: "提示",
        message: messageVal,
        duration: 0
      });
    }
  }
};
</script>

<style scoped>
.overContainer {
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  position: absolute;
  cursor: pointer;
  z-index: 999;
}

.pointer_relationDemandNo {
  width: 400px;
  cursor: pointer;
  -webkit-text-fill-color: #0000ff;
}

.requireList_head_right_orange_common {
  margin: 0 5px 0 5px;
}

.requireContainer_head {
  margin: 0 30px 10px 0;
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

.psire-container {
  margin: 0 15px 0 15px;
}

.psire-main {
  /* min-height: calc(100vh - 84px); */
  padding: 15px;
  background-color: #ffffff;
}

.psire-title-main {
  /* min-height: calc(100vh - 84px); */
  padding: 0 15px 0 15px;
  border-bottom: 1px solid #dcdcdc;
  /* border-left: 1px solid #dcdcdc; */
  background-color: #ffffff;
}

.psire-title {
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

/* 布局调整 */
.psire-container .el-row {
  margin-bottom: -5px;
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

.czLog {
  margin-bottom: 80px;
}

/* .blank {
  height: calc(100vh - 462px);
} */
</style>

<style>
.psire-container .el-form--inline .el-form-item {
  width: 100%;
}

.psire-container .el-form--inline .el-form-item__content {
  width: 70%;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 600px;
}
</style>
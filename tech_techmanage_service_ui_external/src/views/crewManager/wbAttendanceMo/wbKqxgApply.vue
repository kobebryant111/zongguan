<!-- 外包考勤修改申请 -->
<template>
  <div>
    <!-- <div class="nav-head">
            <p class="nav-head-name">外包人员考勤修改</p>
    </div>-->
    <div class="nav">
      <el-row>
        <el-col :span="24">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="18">
                <p class="nav_name">外包人员考勤修改</p>
              </el-col>
            </div>
            <div class="wrapper_right">
              <el-col>
                <button-orange
                  :buttonName="exportBtn"
                  :size="size"
                  :fns="exportExcel"
                  v-if="ifExport"
                ></button-orange>
              </el-col>
              <el-col :span="1.5">
                <button-common
                  class="xkg-marginleft-10px"
                  :buttonName="buttonClose"
                  :size="size"
                  :fns="close"
                ></button-common>
              </el-col>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="wbkqxg-container">
      <el-form inline :model="kqxgData" ref="kqxgForm">
        <div class="wbkqxg-main">
          <!-- <div class="wbkqxg-title">
                        <p>外包人员考勤修改</p>
          </div>-->
          <el-row type="flex" justify="space-around">
            <el-col :span="12">
              <el-form-item
                label="发起人："
                prop="userName"
              >
                <el-input v-model="kqxgData.userName" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="所在部门："
                prop="depId"
              >
                <el-select placeholder="部门" v-model="kqxgData.depId" :disabled="true">
                  <el-option
                    v-for="item in depData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item
                label="修改原因描述："
                prop="reasonDes"
                :rules="[{ required: true, message: '请选择修改原因', trigger: 'blur' }]"
              >
                <el-input type="textarea" :autosize="{ minRows: 3}" v-model="kqxgData.reasonDes" maxlength="1000"></el-input></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item
                label="修改内容描述："
                prop="contentDes"
                :rules="[{ required: true, message: '请选择修改内容', trigger: 'blur' }]"
              >
                <el-input type="textarea" :autosize="{ minRows: 3}" v-model="kqxgData.contentDes" maxlength="1000"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>
    <div class="blank"></div>
    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete>
            </el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper-right-keep">
              <el-col :span="1.5">
                <button-common :buttonName="buttonKeep" :size="size" :fns="saveDraft"></button-common>
              </el-col>
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>
  </div>
</template>

<script>
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import buttonDelete from "@/components/eleButton/delete";
import { queryWorkItem } from "@/api/integrated/wbryzrlc";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { doDel } from "@/api/common/flowApi";
import { liuzhuan, save, exportExcel } from "@/api/integrated/wbKqxg";
import { getDicEntryOne } from "@/api/eosDictEntry";
import common from "@/api/common";
import { mapGetters } from "vuex";
export default {
  name: "wbKqxgApply",
  components: { buttonOrange, buttonCommon, buttonDelete },
  data() {
    return {
      size: "mini", //按钮大小
      buttonDel: "删 除",
      buttonKeep: "存草稿",
      buttonCommit: "提 交",
      buttonClose: "关 闭", //关闭按钮名字
      exportBtn: "导出",
      delBtnIf: false, //删除按钮是否出现
      kqxgData: {},
      depData: [], //部门数据
      bps: {}, //bps数据
      instanceId: "", //流程ID
      ifExport: false // 是否有导出权限
    };
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  mounted() {
    this.kqxgData.badge = this.userId;
    this.kqxgData.userName = this.name + "(" + this.userId + ")";
    this.kqxgData.depId = "dep" + this.deptId;
    // 新增导出权限 k6991
    let params = { dicttypeid: "NEW_MODIFY_EXPORT", dictid: "1" };
    getDicEntryOne(params).then(res => {
      if (res.data.results.dictname.indexOf(this.userId) !== -1) {
        this.ifExport = true;
      }
    });
  },
  methods: {
    init() {
      this.getDep();
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

    // 存草稿
    saveDraft() {
      const loading = common.loading(this);
      let params = {};
      params.badge = this.kqxgData.badge;
      params.userName = this.kqxgData.userName;
      let depN = this.kqxgData.depId;
      params.depId = depN.replace("dep", "");
      params.reasonDes = this.kqxgData.reasonDes;
      params.contentDes = this.kqxgData.contentDes;
      params.instanceId = this.instanceId;
      // let data = common.paramsFormat(params);
      save(params)
        .then(res => {
          loading.close();
          console.log("存草稿", res.data);
          if (res.data.status == 200) {
            this.instanceId = res.data.results;
            this.getWorkItem(res.data.results);
            this.$message.success("保存草稿成功");
          } else {
            this.$message.error("保存草稿失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 提交
    proving() {
      this.$refs["kqxgForm"].validate(valid => {
        if (valid) {
          this.commit();
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

    commit() {
      const loading = common.loading(this);
      let params = {};
      params.badge = this.kqxgData.badge;
      params.userName = this.kqxgData.userName;
      let depN = this.kqxgData.depId;
      params.depId = depN.replace("dep", "");
      params.reasonDes = this.kqxgData.reasonDes;
      params.contentDes = this.kqxgData.contentDes;
      params.instanceId = this.instanceId;
      // let data = common.paramsFormat(params);
      liuzhuan(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            let message = common.getFlowMessge(res.data.message);
            let messageArr = message.split(",");
            messageArr[2] = messageArr[2].split(")")[0] + ")";
            this.messageNotifyMore(messageArr);
            this.$router.push({ name: "dashboard" });
          } else {
            this.$message.error("操作失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
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

    // 关闭按钮
    close() {
      this.$router.push({ name: "dashboard" });
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
    /**
     * 导出
     */
    exportExcel() {
      let self = this;
      exportExcel().then(res => {
        // console.log('导出：', res.data)
        common.download(res.data, self, "外包考勤修改.xls");
      });
    } // end
  }
};
</script>

<style scoped>
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

.wbkqxg-container {
  margin: 15px 15px 0 15px;
}

.wbkqxg-main {
  /* min-height: calc(100vh - 84px); */
  padding: 15px;
  background-color: #ffffff;
}

.wbkqxg-title p {
  color: #606266;
  font-size: 16px;
  font-weight: 600;
  padding: 10px 0 5px 0;
}

.nav {
  width: 100%;
  background-color: #ffffff;
}

.nav .nav_name {
  width: 150px;
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

.wrapper-right-keep {
  margin-right: 10px;
}

.bottom {
  margin-top: 20px;
  padding: 15px 0;
}

.blank {
  height: calc(100vh - 462px);
}
</style>

<style>
.wbkqxg-container .el-form--inline .el-form-item {
  width: 100%;
}

.wbkqxg-container .el-form--inline .el-form-item__content {
  width: 70%;
}
</style>
<!-- 录入页面 -->
<template>
  <div class="requireContainer">
    <div class="requireContainer_head">
      <el-row type="flex">
        <div class="requireList_head_right_orange_common" v-if="xzqxFlag">
          <button-common :buttonName="buttonNew" :size="sizes" :fns="newPsData"></button-common>
        </div>
        <div class="requireList_head_right_orange_common" v-if="scqxFlag">
          <button-common :buttonName="buttonDelete" :size="sizes" :fns="delData"></button-common>
        </div>
        <div class="requireList_head_right_orange_common" v-if="xgqxFlag">
          <button-common :buttonName="buttonEdit" :size="sizes" :fns="editData"></button-common>
        </div>
        <div class="requireList_head_right_orange_common" v-if="drqxFlag">
          <button-common :buttonName="buttonImport" :size="sizes" :fns="importData"></button-common>
        </div>
        <div class="requireList_head_right_orange_common">
          <button-common :buttonName="buttonDownModel" :size="sizes" :fns="downloadModle"></button-common>
        </div>
        <div class="requireList_head_right_orange_common">
          <button-common :buttonName="buttonExport" :size="sizes" :fns="exportData"></button-common>
        </div>
        <div class="requireList_head_right_orange_common" v-if="cffklcFlag">
          <button-orange :buttonName="buttonStart" :size="sizes" :fns="startInstance"></button-orange>
        </div>
        <!-- <div class="requireList_head_right_orange_common">
        <button-orange :buttonName="buttonStart" :size="sizes" :fns="launchDemand"></button-orange>
        </div>-->
      </el-row>
    </div>
    <el-table
      style="margin-bottom: 80px;"
      :data="psireDatas"
      highlight-current-row
      height="510"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="psireName" label="流程名称" width="140" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="psireNo" label="流程编号" width="200" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <div @click="skipToOldKG(scope.row.flowUrl,scope.row.instanceId)">
            <span class="pointer_relationDemandNo">{{scope.row.psireNo}}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="relationUserName"
        label="关联人员"
        width="140"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column prop="deptName" label="关联部门" width="130" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="problemType" label="问题分类" width="100" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span v-if="scope.row.problemType == '0'">流程问题</span>
          <span v-else-if="scope.row.problemType == '1'">专项问题</span>
        </template>
      </el-table-column>
      <el-table-column prop="problemDetail" label="问题详情" width="160" :show-overflow-tooltip="true"></el-table-column>
    </el-table>

    <div class="nav-bottom">
      <div class="wrapper">
        <div class="wrapper_left"></div>
        <div class="wrapper_right">
          <div class="requireList_head_right_orange_common">
            <pager
              :fns="getPager"
              :total="totalNumber"
              :pageSize="size"
              v-model="$attrs"
              ref="pager"
            />
          </div>
        </div>
      </div>
    </div>

    <el-dialog :title="newDataTitle" :visible.sync="showNewData" v-if="showNewData" width="50%">
      <new-data
        :psireData="rowData"
        :flag="flag"
        :closeDialog="closeDialog"
        :getPsireData="getPsireData"
      ></new-data>
    </el-dialog>

    <el-dialog title="上传文件" :visible.sync="ifShowEnDialog" @close="scClose">
      <div>
        <div class="upload-butt">
          <el-upload
            action="Fake Action"
            accept=".xlsx, .xls"
            :file-list="drList"
            :show-file-list="true"
            :http-request="changeUpFile"
            :on-change="handleChange"
            :on-remove="handleRemove"
          >
            <el-button size="small" type="primary">
              <i class="el-icon-upload2"></i>
              <span>{{drList.length == 0 ? '上传文件' : '重新上传'}}</span>
            </el-button>
          </el-upload>
        </div>

        <div class="dia-bottom">
          <el-row :gutter="10" type="flex" justify="end">
            <el-col :span="1.5">
              <button-common buttonName="取 消" size="mini" :fns="cancelEnter"></button-common>
            </el-col>
            <el-col :span="1.5">
              <button-orange buttonName="确 定" size="mini" :fns="sureEnter"></button-orange>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import pager from "@/components/Pager";
import newData from "./newData";

import common from "@/api/common";
import Moment from "moment";

import { getDicEntry, getDomainName } from "@/api/eosDictEntry";
import { getDownloadUrl } from "@/api/common/flowApi";
import {
  queryPagerInput,
  batchDelete,
  importExcelInfo,
  flowStartAndRun,
  exportPostGovernorInputDatas
} from "@/api/dataCenter/dataCenPostSupervision";
import { mapGetters } from "vuex";

export default {
  name: "record",
  components: {
    buttonCommon,
    buttonOrange,
    newData,
    pager
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认:50
      index: 1, // 初始值 默认:1

      sizes: "small", //按钮大小
      buttonNew: "新增",
      buttonDelete: "删除",
      buttonEdit: "修改",
      buttonImport: "导入",
      buttonDownModel: "导入模板下载",
      buttonExport: "导 出", //导出按钮
      buttonStart: "触发反馈流程",
      drFileData: {}, //导入文件信息
      drList: [], //导入列表
      flag: true, //新增和修改判断
      newDataTitle: "",
      showNewData: false, //显示新增弹出框
      ifShowEnDialog: false, //是否显示导入弹窗
      selectData: [], //选中的数据
      rowData: {}, //当前行数据
      psireDatas: [], //后督数据
      exportParams: {}, //导出参数
      xzqx: [], //新增权限
      xzqxFlag: false,
      scqx: [], //删除权限
      scqxFlag: false,
      xgqx: [], //修改权限
      xgqxFlag: false,
      drqx: [], //导入权限
      drqxFlag: false,
      cffklc: [], //触发反馈流程权限
      cffklcFlag: false
    };
  },
  props: {
    paramsForm: {
      type: Object,
      required: true,
      default: () => ({})
    }
  },
  watch: {
    paramsForm: {
      handler(val) {
        this.exportParams = val;
      },
      deep: true
    }
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  created() {},
  mounted() {
    this.loadInit();
  },
  methods: {
    loadInit() {
      this.exportParams = this.paramsForm;
      let params = {};
      params = this.exportParams;
      params.size = this.size;
      params.index = this.index;
      this.getPsireDatabyParams(params);
      this.getDic();
    },

    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params = this.exportParams;
      params.size = size;
      params.index = index;
      // this.init(params);
      let data = common.paramsFormat(params);
      this.getPsireDatabyParams(params);
    }, // end

    //获取数据
    getPsireDatabyParams(paramsForm) {
      let params = paramsForm;
      let data = common.paramsFormat(params);
      queryPagerInput(data).then(res => {
        this.psireDatas = res.data.results;
        this.totalNumber = res.data.total;
        for (var i = 0; i < this.psireDatas.length; i++) {
          if (this.psireDatas[i].dept != undefined) {
            this.psireDatas[i].dept = "dep" + this.psireDatas[i].dept;
          }
        }
      });
    },

    //获取数据
    getPsireData() {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      let data = common.paramsFormat(params);
      queryPagerInput(data).then(res => {
        this.psireDatas = res.data.results;
        this.totalNumber = res.data.total;
        for (var i = 0; i < this.psireDatas.length; i++) {
          if (this.psireDatas[i].dept != undefined) {
            this.psireDatas[i].dept = "dep" + this.psireDatas[i].dept;
          }
        }
      });
    },

    // 查询业务字典
    getDic() {
      //新增记录权限
      getDicEntry({
        dicttypeid: "NEWKG_HDXZJLQX"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        this.xzqx = data.filter(item => {
          return item.dicttypeid == "NEWKG_HDXZJLQX";
        });
        return data;
      });
      //删除记录权限
      getDicEntry({
        dicttypeid: "NEWKG_HDSCJLQX"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        this.scqx = data.filter(item => {
          return item.dicttypeid == "NEWKG_HDSCJLQX";
        });
        return data;
      });
      //修改记录权限
      getDicEntry({
        dicttypeid: "NEWKG_HDXGJLQX"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        this.xgqx = data.filter(item => {
          return item.dicttypeid == "NEWKG_HDXGJLQX";
        });
        return data;
      });
      //导入权限
      getDicEntry({
        dicttypeid: "NEWKG_HDDRQX"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        this.drqx = data.filter(item => {
          return item.dicttypeid == "NEWKG_HDDRQX";
        });
        return data;
      });
      //触发反馈流程权限
      getDicEntry({
        dicttypeid: "NEWKG_HDCFFKLCQX"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        this.cffklc = data.filter(item => {
          return item.dicttypeid == "NEWKG_HDCFFKLCQX";
        });
        return data;
      });
      this.getPermission();
    },

    //权限判断
    getPermission() {
      for (var i = 0; i < this.xzqx.length; i++) {
        if (this.userId == this.xzqx[i].dictid) {
          this.xzqxFlag = true;
        }
      }
      for (var i = 0; i < this.scqx.length; i++) {
        if (this.userId == this.scqx[i].dictid) {
          this.scqxFlag = true;
        }
      }
      for (var i = 0; i < this.xgqx.length; i++) {
        if (this.userId == this.xgqx[i].dictid) {
          this.xgqxFlag = true;
        }
      }
      for (var i = 0; i < this.drqx.length; i++) {
        if (this.userId == this.drqx[i].dictid) {
          this.drqxFlag = true;
        }
      }
      for (var i = 0; i < this.cffklc.length; i++) {
        if (this.userId == this.cffklc[i].dictid) {
          this.cffklcFlag = true;
        }
      }
    },

    //新增
    newPsData() {
      this.rowData = {
        psireName: "", //流程名称
        psireNo: "", //流程编号
        relationUser: "", //关联人员
        deptName: "", //关联部门名称
        dept: "", //关联部门
        problemType: "", //问题分类
        problemDetail: "" //问题详情
      };
      this.flag = true;
      this.newDataTitle = "新增";
      this.showNewData = true;
    },

    //删除
    delData() {
      if (this.selectData.length > 0) {
        this.$confirm("是否删除选中的后督信息?", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: "warning"
        })
          .then(() => {
            batchDelete(this.selectData).then(res => {
              if (res.data.status == 200) {
                this.$message.success("删除成功");
                this.getPsireData();
              }
            });
          })
          .catch(error => {});
      }
    },

    //修改
    editData() {
      if (this.selectData.length > 0) {
        if (this.selectData.length > 1) {
          this.$message.error("一次仅可修改1条！");
        } else {
          this.rowData = this.selectData[0];
          this.flag = false;
          this.newDataTitle = "修改";
          this.showNewData = true;
        }
      }
    },

    //导入
    importData() {
      this.ifShowEnDialog = true;
    },

    // 关闭导入框
    scClose() {
      this.drList = [];
    },

    // 取消导入
    cancelEnter() {
      this.ifShowEnDialog = false;
    },

    // 确认导入
    sureEnter() {
      if (this.drList.length == 0) {
        this.$message.error("请上传文件");
        return;
      }
      const loading = common.loading(this);
      var formData = new FormData();
      formData.append("file", this.drFileData);
      formData.append("otype", 1);
      importExcelInfo(formData)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.$message.success("导入成功");
            this.ifShowEnDialog = false;
            this.getPsireData();
          } else {
            this.$message.error("导入失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 上传变化
    changeUpFile(file) {
      this.drFileData = file.file;
    },

    // on-change
    handleChange(file, fileList) {
      this.drList = [fileList[fileList.length - 1]];
    },

    // on-remove
    handleRemove() {
      this.drList = [];
    },

    //导入模板下载
    downloadModle() {
      const loading = common.loading(this);
      getDownloadUrl({ key: "SJZXHDGL_DRMB" }).then(res => {
        loading.close();
        if (res.data.status == 200) {
          const self = this;
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
      });
    },

    //导出
    exportData() {
      // let data = common.paramsFormat(this.exportParams);
      let params = {};
      let ids = "";
      for (var i = 0; i < this.selectData.length; i++) {
        if (i == this.selectData.length - 1) {
          ids = ids + this.selectData[i].id;
        } else {
          ids = ids + this.selectData[i].id + ",";
        }
      }
      params.ids = ids;
      let data = common.paramsFormat(params);
      exportPostGovernorInputDatas(data).then(res => {
        const self = this;
        common.download(res.data, self, "后督录入导出.xls");
      });
    },

    //触发反馈流程
    startInstance() {
      let massage = "";
      let flag = true;
      let commitData = [];
      for (var i = 0; i < this.selectData.length; i++) {
        let data = this.selectData[i];
        if (
          data.psireName == undefined ||
          data.psireName == "" ||
          data.psireNo == undefined ||
          data.psireNo == "" ||
          data.relationUser == undefined ||
          data.relationUser == "" ||
          data.dept == undefined ||
          data.dept == "" ||
          data.problemType == undefined ||
          data.problemType == "" ||
          data.problemDetail == undefined ||
          data.problemDetail == ""
        ) {
          flag = false;
        } else {
          if (data.dept != "" && data.dept != undefined) {
            data.dept = data.dept.replace("dep", "");
          }
          commitData.push(data);
        }
      }
      if (this.selectData.length > 0) {
        const loading = common.loading(this);
        flowStartAndRun(commitData).then(res => {
          if (res.data.status == 200) {
            loading.close();
            if (this.selectData.length == commitData.length) {
              this.$message.success("触发反馈流程成功");
            } else {
              this.$message.error(
                commitData.length +
                  "条反馈流程触发成功！" +
                  (this.selectData.length - commitData.length) +
                  "条反馈流程触发失败，请检查信息填写是否有误！"
              );
            }
            this.getPsireData();
          }
        });
      }
    },

    //处理多选
    handleSelectionChange(val) {
      this.selectData = val;
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

    //关闭
    closeDialog() {
      this.showNewData = false;
    }
  }
};
</script>
<style scoped>
.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
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

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}

.requireList_head_right_orange_common {
  margin: 0 5px 0 5px;
}

.requireContainer_head {
  float: right;
  margin-bottom: 10px;
}

.requireContainer {
  background-color: #ffffff;
  padding: 0 18px 0 18px;
}

/* 表头背景色 */
.requireContainer .el-table .th {
  background: #fafafa;
}

.pointer_relationDemandNo {
  cursor: pointer;
  color: #0000ff;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 600px;
}
</style>



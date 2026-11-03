<!-- 数据中心门禁权限查询 -->
<template>
  <div class="list-container requireList">
    <el-form size="mini" :model="paramsForm" inline>
      <div class="requireList_head">
        <div class="requireList_head_input">
          <el-row type="flex">
            <el-col :span="8">
              <el-form-item class="classforform">
                <span class="titleforesarch">发起人:</span>
                <div class="classforinput">
                  <el-select
                    v-model="paramsForm.launchBy"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="发起人姓名/工号"
                    :remote-method="searchUsers"
                    :loading="loading"
                  >
                    <el-option
                      v-for="item in userOptions"
                      :key="item.badge"
                      :label="item.text"
                      :value="item.badge"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item class="classforform">
                <span class="titleforesarch">申请日期:</span>
                <div class="classforinput">
                  <el-date-picker
                    v-model="paramsForm.launchDates"
                    value-format="yyyy-MM-dd"
                    type="daterange"
                    start-placeholde="开始日期"
                    end-placeholde="结束日期"
                    @change="dateChange"
                  ></el-date-picker>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item class="classforform">
                <div class="titleforesarch">
                  <span>流程状态:</span>
                </div>
                <div class="classforinput">
                  <el-select placeholder="请选择" v-model="paramsForm.flowStatus">
                    <!-- <el-option value></el-option> -->
                    <el-option
                      v-for="item in flowStatus_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictname"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>

            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item class="classforform">
                <span class="titleforesarch">申请人:</span>
                <div class="classforinput">
                  <el-select
                    v-model="paramsForm.applyName"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="申请人姓名/工号"
                    :remote-method="searchUsers"
                    :loading="loading"
                  >
                    <el-option
                      v-for="item in userOptions"
                      :key="item.badge"
                      :label="item.text"
                      :value="item.badge"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item class="classforform">
                <div class="titleforesarch">
                  <span>申请部门:</span>
                </div>
                <div class="classforinput">
                  <el-select placeholder="请选择" filterable v-model="paramsForm.applyDept">
                    <el-option
                      v-for="item in depData"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item class="classforform">
                <div class="titleforesarch">
                  <span>人员类型:</span>
                </div>
                <div class="classforinput">
                  <el-select placeholder="请选择" v-model="paramsForm.userType">
                    <el-option key="0" value="行员"></el-option>
                    <el-option key="1" value="外包"></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item class="classforform">
                <div class="titleforesarch">
                  <span>申请类型:</span>
                </div>
                <div class="classforinput">
                  <el-select placeholder="请选择" v-model="paramsForm.applyType">
                    <el-option
                      v-for="item in apply_type_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item class="classforform">
                <span class="titleforesarch">权限类别:</span>
                <div class="classforinput">
                  <el-autocomplete
                    style="width: 260px"
                    popper-class="my-autocomplete"
                    v-model="paramsForm.permissionsType"
                    :fetch-suggestions="querySearchAsync"
                    @select="handleSelect"
                  >
                    <template slot-scope="{ item }">
                      <div class="number">{{ item.permissioncategory }}</div>
                    </template>
                  </el-autocomplete>
                </div>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item class="classforform">
                <div class="titleforesarch">
                  <span>申请地点:</span>
                </div>
                <div class="classforinput">
                  <el-select placeholder="请选择" v-model="paramsForm.applyAddr">
                    <el-option
                      v-for="item in apply_addr_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 重置、查询、高级搜索 按钮 -->
        <div class="requireList_head_buttons">
          <el-row>
            <div class="requireList_head_right_orange">
              <clean-btn :size="sizes" :forms="this.paramsForm"/>
            </div>
            <div class="requireList_head_right_orange">
              <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
            </div>
            <div class="requireList_head_right_orange">
              <button-orange :buttonName="buttonSeniorSearch" :size="sizes" :fns="seniorSerch"></button-orange>
            </div>
          </el-row>
        </div>
      </div>
    </el-form>

    <div class="requireContainer">
      <div class="requireContainer_head">
        <el-row type="flex">
          <div class="requireList_head_right_orange_common">
            <button-common :buttonName="buttonClose" :size="sizes" :fns="close"></button-common>
          </div>
          <div class="requireList_head_right_orange_common" v-if="isYxfwb">
            <button-common :buttonName="buttonExport" :size="sizes" :fns="exportExcel"></button-common>
          </div>
          <div class="requireList_head_right_orange_common" v-if="isYxfwb">
            <button-common :buttonName="flowSetting" :size="sizes" :fns="openFlowSettings"></button-common>
          </div>
          <div class="requireList_head_right_orange_common">
            <button-orange :buttonName="buttonStart" :size="sizes" :fns="launchDemand"></button-orange>
          </div>
        </el-row>
      </div>

      <el-table
        style="margin-bottom: 80px;"
        :data="acPermissionDatas"
        border
        highlight-current-row
        height="510"
        @row-dblclick="showDetail"
      >
        <el-table-column
          fixed
          prop="launchByName"
          label="发起人"
          width="130"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="processInsName"
          label="流程名称"
          width="160"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column prop="flowStatus" label="流程状态" width="80" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="applyNames" label="申请人" width="130" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="applyAddr" label="申请地点" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          prop="applyTypeNames"
          label="申请类型"
          width="100"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="permissionsTypeNames"
          label="权限类别"
          width="140"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="launchDeptName"
          label="申请部门"
          width="240"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column prop="launchDate" label="申请时间" width="200" :show-overflow-tooltip="true"></el-table-column>
      </el-table>
      <el-dialog title="详情" :visible.sync="showDetails" width="70%">
        <access-permission-detail ref="details" :instanceId="detailId"></access-permission-detail>
      </el-dialog>

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
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import common from "@/api/common";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import accessPermissionDetail from "./accessPermissionDetail";
import cleanBtn from "@/components/CleanBtn"; //引入重置按钮
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { getDicEntry, getDomainName } from "@/api/eosDictEntry";
// import { getEmpBaseInfo } from "@/api/integrated/wbryxxbg";
import { pagerList } from "@/api/branchIntentionApi/fhyx";
import {
  queryAllInfoDetail,
  adminImplementation,
  queryPagerForApplyer,
  queryPagerForPermissionsType,
  queryPagerForSearch,
  exportAccessControlApplyInfo,
  queryPagerForEmployee
} from "@/api/dataCenter/dataCenAccessPermission";
import { userTreeList } from "@/api/systemApi/relateUser";

import helpHint from "@/components/helpHint";
import Moment from "moment";
export default {
  name: "accessPermissionSearch",
  components: {
    pager,
    buttonOrange,
    helpHint,
    buttonCommon,
    cleanBtn,
    accessPermissionDetail
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认:50
      index: 1, // 初始值 默认:1
      paramsForm: {
        launchBy: "", //发起人
        launchDates: [], //申请时间
        flowStatus: "", //流程状态
        applyName: "", //申请人
        applyDept: "", //申请部门
        userType: "", //人员类型
        applyType: "", //申请类型
        permissionsType: "", //权限类别
        applyAddr: "", //申请地点
        startDate: "", //申请时间开始时间
        endDate: "" //申请时间结束时间
      },
      buttonExport: "导 出", //导出按钮
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      buttonSeniorSearch: "高级搜索", //高级搜索按钮
      buttonClose: "关 闭", //关闭按钮名字
      buttonStart: "发起需求", //发起需求按钮
      flowSetting: "流程配置", //流程配置按钮
      apply_addr_option: [], //申请地点
      apply_type_option: [], //申请类型

      depData: [], // 部门数据
      flowStatus_option: [], //流程状态

      isSeniorSearch: false, //高级搜索
      acPermissionDatas: [], // 列表分页数据zb
      permissionsTypeSuggestions: [], //权限类别输入框建议
      userOptions: [], //人员建议列表
      loading: true,
      showFlowSettings: false, //显示流程配置
      isYxfwb: false, //运行服务部
      showDetails: false,
      detailId: ""
    };
  },
  created() {
    this.getDic();
    this.getDep();
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  mounted() {
    this.loadInit();
    this.loadAll();
  },
  methods: {
    /**
     * 分页初始化方法
     */
    loadInit: function() {
      this.paramsForm.flowStatus = "进行中";
      let param = {};
      param.size = this.size;
      param.index = this.index;
      param.flowStatus = "进行中";
      let data = common.paramsFormat(param);
      this.getPagerList(data);
      // this.getCurrentUser();
      if (this.deptId == "2494") {
        this.isYxfwb = true;
      } else {
        this.isYxfwb = false;
      }
    }, // end

    showDetail(row) {
      console.log(row);
      this.showDetails = true;
      this.detailId = row.processInstId;
    },

    //导出
    exportExcel() {
      let data = common.paramsFormat(this.paramsForm);
      exportAccessControlApplyInfo(data).then(res => {
        const self = this;
        common.download(res.data, self, "数据中心门禁权限申请导出文件.xls");
      });
    },

    dateChange(date) {
      if (
        (date[0] != undefined && date[0] != "") ||
        (date[1] != undefined && date[1] != "")
      ) {
        this.paramsForm.startDate = date[0];
        this.paramsForm.endDate = date[1];
      } else {
        this.paramsForm.startDate = "";
        this.paramsForm.endDate = "";
      }
    },

    //跳转老应用
    skipToOldKG(relationDemandNo) {
      let link = document.createElement("a");
      let url_params =
        "/com.example.ppm.system.projectM.newApplicationDemandYxM.flow?processInstID=" +
        relationDemandNo +
        "&processDefName=com.example.ppm.bps.daCenterDemandManager" +
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

    // 查询业务字典
    getDic() {
      getDicEntry({
        dicttypeid: "PPM_SJZXMJ_APPLYDD"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //申请地点
        this.apply_addr_option = data.filter(item => {
          return item.dicttypeid == "PPM_SJZXMJ_APPLYDD";
        });
        return data;
      });

      getDicEntry({
        dicttypeid: "PPM_ACCESS_APPLY_STATE"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //申请类型
        this.apply_type_option = data.filter(item => {
          return item.dicttypeid == "PPM_ACCESS_APPLY_STATE";
        });
        return data;
      });

      getDicEntry({
        dicttypeid: "NEWKE_LCZT"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //流程状态
        this.flowStatus_option = data.filter(item => {
          return item.dicttypeid == "NEWKE_LCZT";
        });
        return data;
      });
    },

    // 获取部门
    getDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          var fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter(tt => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 获取列表数据
    getPagerList(param) {
      console.log("param:", param);
      queryPagerForSearch(param).then(res => {
        if (res.data.status == 200) {
          this.acPermissionDatas = res.data.results;
          this.totalNumber = res.data.total;
        }
      });
    },

    searchUsers(val) {
      if (val != "") {
        this.loading = true;
        let params = {};
        params.index = 1;
        params.size = 50;
        params.empname = val;
        let data = common.paramsFormat(params);
        queryPagerForEmployee(data).then(res => {
          this.loading = false;
          if (res.data.status == 200) {
            this.userOptions = res.data.results.filter(li => {
              li.text = li.empname + "(" + li.badge + ")";
              li.isLeaf = true;
              return li;
            });
          } else {
            this.userOptions = [];
          }
        });
      }
    },

    //输入框建议
    loadAll() {
      let params = {};
      params.size = 100;
      params.index = 1;
      let data = common.paramsFormat(params);
      queryPagerForPermissionsType().then(res => {
        this.permissionsTypeSuggestions = res.data.results;
        this.permissionsTypeSuggestions.push({
          permissioncategory: "日常办公组"
        });
        // console.log("pagerList:",this.permissionsTypeSuggestions);
      });
    },
    querySearchAsync(queryString, cb) {
      var permissionsTypeSuggestions = this.permissionsTypeSuggestions;
      var results = queryString
        ? permissionsTypeSuggestions.filter(this.createStateFilter(queryString))
        : permissionsTypeSuggestions;
      cb(results);
    },
    createStateFilter(queryString) {
      return state => {
        return (
          state.permissioncategory != undefined &&
          state.permissioncategory.indexOf(queryString) != -1
        );
      };
    },
    handleSelect(item) {
      this.paramsForm.permissionsType = item.permissioncategory;
    },

    /**
     * 搜索事件
     */
    submitBtn: function() {
      let params = {};
      params = this.paramsForm;
      if (params.applyDept != undefined && params.applyDept != "") {
        params.applyDept = params.applyDept.replace("dep", "");
      }
      params.size = this.size;
      this.index = params.index = 1;
      this.$refs.pager.pager.currentPage = 1;
      let data = common.paramsFormat(params);
      this.getPagerList(data);
      if (params.applyDept != undefined && params.applyDept != "") {
        params.applyDept = "dep" + params.applyDept;
      }
    }, // 搜索事件 end

    /**
     * 高级搜索
     */
    seniorSerch() {
      if (this.isSeniorSearch == false) {
        this.isSeniorSearch = true;
        this.buttonSeniorSearch = "普通搜索";
      } else {
        this.isSeniorSearch = false;
        this.buttonSeniorSearch = "高级搜索";
        this.paramsForm.applyName = ""; //申请人
        this.paramsForm.applyDept = ""; //申请部门
        this.paramsForm.userType = ""; //人员类型
        this.paramsForm.applyType = ""; //申请类型
        this.paramsForm.permissionsType = ""; //权限类别
        this.paramsForm.applyAddr = ""; //申请地点
      }
    },

    //流程配置
    openFlowSettings() {
      this.$router.push({
        name: "flowSetting"
      });
    },

    // 发起需求
    launchDemand() {
      this.$router.push({
        name: "newAppAccessApply"
      });
    },

    /**
     * 点击table行
     */
    onSelectTable: function(selection, row) {},

    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params = this.paramsForm;
      if (params.applyDept != undefined && params.applyDept != "") {
        params.applyDept = params.applyDept.replace("dep", "");
      }
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      let data = common.paramsFormat(params);
      this.getPagerList(data);
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: "dashboard" });
    }
  }
};
</script>
<style scoped>
.titleforesarch {
  float: left;
  width: 70px;
}

.classforform {
  width: 340px;
}

.classforinput {
  float: left;
  width: 260px;
}

.requireList {
  padding: 15px;
}

.requireList_head {
  /* background-color: #ffffff;
    padding: 18px; */
  background-color: #ffffff;
  padding: 18px;
  display: flex;
  justify-content: space-between;
}

.requireContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 18px;
}

.requireContainer_head {
  float: right;
  margin-bottom: 10px;
}

.requireContainer_head_en {
  width: 160px;
}

.requireList_head_buttons {
  width: 20%;
}

.requireList_head_input {
  width: 80%;
}

.seniorSearch {
  margin: -25px 0 10px 0;
}

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

/* 占位 */
.occupied {
  width: 8px;
  display: inline-block;
}

.requireList_head_right {
  float: right;
}

.requireList_head_right div {
  display: inline-block;
}

.requireList_head_right_clean {
  margin-right: 10px;
}

/* el-row超出24换行 */
.el-row {
  display: flex;
  flex-wrap: wrap;
}

.my-autocomplete li {
  line-height: normal;
  padding: 7px;
}

.number {
  /* padding: -10px; */
  /* margin: -5px 0 -15px 0; */
  text-overflow: ellipsis;
  overflow: hidden;
}

.name {
  line-height: 14px;
  font-size: 12px;
  color: #b4b4b4;
}

/* 表格推荐供应商 */
.recGys {
  border: none;
}

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}

/* 弹窗关闭按钮 */
.cloosePre {
  margin: 15px 0;
  float: right;
}

.requireList_head_right_orange {
  margin: 0 5px 0 5px;
}

.requireList_head_right_orange_common {
  margin: 0 5px 0 5px;
}
</style>
<style>
/* el-input修改
.requireList .el-input--prefix .el-input__inner {
  padding-left: 75px;
} */

.requireList .el-input__prefix {
  left: 8px;
  color: #262626;
  font-size: 14px;
}

/* element表单修改 */
.requireList .el-form-item {
  margin-bottom: 10px;
}

/* 表头背景色 */
.requireList .el-table th {
  border: #ffffff;
  background: #fafafa;
}

.requireList .el-table,
.requireList .el-table td {
  border: #ffffff;
}

.pointer_relationDemandNo {
  cursor: pointer;
  color: #0000ff;
}

/* 弹窗 */
.dialog .el-dialog {
  overflow: hidden;
}
</style>

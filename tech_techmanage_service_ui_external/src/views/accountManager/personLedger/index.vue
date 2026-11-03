<!-- 非科技外包人员台账管理列表 -->
<template>
  <div class="list-container accountList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form :model="paramsForm" ref="formRef" label-width="95px">
        <el-row type="flex" >
          <el-col :span="6">
            <el-form-item prop label="姓名：">
              <el-input placeholder="请输入" v-model="paramsForm.userNameVal" filterable />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="所属部门：">
              <!-- <el-select v-model="paramsForm.deptVal" filterable>
                <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id" ></el-option>
              </el-select> -->
              <el-input v-model="paramsForm.deptVal" placeholder="请输入" filterable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="项目经理：">
              <el-input placeholder="请输入" v-model="paramsForm.pmVal" filterable />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="人员状态：">
              <!-- <el-input placeholder="请输入" v-model="paramsForm.state" filterable /> -->
              <el-select placeholder="请选择" v-model="paramsForm.state">
                <el-option v-for="item in personState_options" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-row type="flex" justify="end">
              <clean-btn :size="sizes" :forms="this.paramsForm" class="cleanbtn" />
              <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn" ></button-orange>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="requireContainer">
      <div class="requireContainer_head">
        <div class="requireContainer_head_en"></div>
        <el-row type="flex" justify="end">
          <el-col :span="12">
            <button-orange :buttonName="buttonAdd" :size="sizes" :fns="handleAdd" ></button-orange>
          </el-col>
          <el-col :span="12">
            <button-common :buttonName="buttonExport" :size="sizes" :fns="handelExport"></button-common>
          </el-col>
        </el-row>
      </div>
      <el-table :data="crewData" highlight-current-row height="680" fixed="left" @select-all="onSelectTable" @select="onSelectTable" tooltip-effect="dark">
        <el-table-column label="系统编号" prop="jobId" width="100" />
        <el-table-column label="姓名" prop="userName" width="100" />
        <el-table-column label="身份证" prop="cardId" width="120" />
        <el-table-column label="手机号" prop="telphone" width="120" />
        <el-table-column label="项目经理" prop="proPersonName" width="120" />
        <el-table-column label="所属部门" prop="deptName" width="300" show-overflow-tooltip />
        <!-- <el-table-column label="供应商"  prop="suppilerName" width="200" show-overflow-tooltip/>
        <el-table-column label="入场时间" prop="startingTime" :formatter ="startingtimeFormat" width="120" ></el-table-column>
        <el-table-column label="离场时间" prop="endingTime" :formatter ="endingtimeFormat" width="120" ></el-table-column> -->
        <el-table-column  label="人员状态" prop="state" width="60">
          <template slot-scope="scope">
            <span v-if="scope.row.state == '0'">在岗</span>
            <span v-else-if="scope.row.state == '1'">离岗</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <!-- <el-link type="warning" :underline="false" @click="onUserDetail(scope.row.badge)">详情</el-link> -->
            <el-link type="warning" :underline="false" @click="handleUpdate(scope.row)">修改</el-link >
            <!-- <el-link type="warning" :underline="false" @click="handleDel(scope.row)" >删除</el-link > -->
            <el-link type="warning" :underline="false" @click="handleDetail(scope.row)" >详情</el-link >
          </template>
        </el-table-column>
      </el-table>

      <div class="pager_zb">
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
      </div>
    </div>
  </div>
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
import { queryPager, delAccount, exportExcel } from "@/api/accountManager/personLedger";
import { queryRole } from "@/api/kqManager/calendar";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept, getWbCompany } from "@/api/integrated/wbryzrlc";
import Moment from "moment";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
export default {
  name: "personLedgerIndex",
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      totalNumber: 0,
      size: 10, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramsForm: {
        userName: "",
        deptVal: "",
        pmVal: "",
        state: "0"
      },
      crewData: [], // 列表分页数据
      drawer: false, // 默认不显示
      peo: "zh",
      buttonAdd: "新 增", //新增
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      deptno: "",
      depData: [],
      outsourceno_options: [], //外包商
      buttonImport: "导 入", //导出按钮
      rysx_options: [], //人员属性集合
      auth: false, // 是否有权限查看单价
      buttonExport: '导 出', //导出按钮
      personState_options: [
        {
          value: "0",
          label: "在岗",
        },
        {
          value: "1",
          label: "离岗",
        },
      ],
    };
  },
  created() {
    // this.getWbcom();
    this.filDep();
    this.loadInit();
  },
  mounted() {
    this.searchEntry();
  },
  methods: {
    // 导出
    handelExport() {
      var searchData = this.paramsForm;
      let params = common.paramsFormat(searchData)
      const self = this;
      exportExcel(params).then(res => {
          // console.log(res)
          common.download(res.data, self, '非科技外包人员台账.xls')
      })
    },
    /** 查询业务字典 */
    searchEntry() {
      getDicEntry({ dicttypeid: "NEWKG_RYSX" }).then((res) => {
        if (res.data.status !== 200) {
          this.alter(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // 人员属性
        this.rysx_options = data.filter((item) => {
          return item.dicttypeid == "NEWKG_RYSX";
        });
        return data;
      });
    },

    /** 分页初始化方法 */
    loadInit: function () {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      params.state = this.paramsForm.state;
      this.getPagerList(params);
      this.getPermission();
    },

    /** 获取列表数据 */
    getPagerList(params) {
      let data = common.paramsFormat(params);
      queryPager(data).then((res) => {
        if (res.data.status == 200) {
          this.crewData = res.data.results;
          console.log(self.crewData, "台账数据");
          this.totalNumber = res.data.total;
        }
      });
    },
    /** 获取部门 */
    filDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then((res) => {
          var fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter((tt) => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
          console.log(this.depData,"-1-1-1-1--1")
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },

    /** 查询外包商 */
    getWbcom() {
      getListAll()
        .then((res) => {
          this.outsourceno_options = res.data.results;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },

    /**  时间格式化 */
    startingtimeFormat: function (row, cloumn) {
      let time = "";
      if (row.startingTime != null) {
        // time = common.dateFormat(row.createtime, "yyyy年MM月dd日");
        time = Moment(row.startingTime).format("YYYY-MM-DD");
      }
      return time;
    },

    /**  时间格式化 */
    endingtimeFormat: function (row, cloumn) {
      let time = "";
      if (row.endingTime != null) {
        // time = common.dateFormat(row.createtime, "yyyy年MM月dd日");
        time = Moment(row.endingTime).format("YYYY-MM-DD");
      }
      return time;
    },

    /** 部门显示内容 */
    deptFormat(row, cloumn) {
      let dep = "";
      const reg = /([^-]+)$/;
      if (row.deptname != null) {
        dep = row.deptname.match(reg)[1];
      }
      return dep;
    },

    /** 搜索事件 */
    submitBtn: function () {
      let params = this.paramsForm;
      params.size = this.size;
      params.index = 1;
      this.getPagerList(params); //bin
    },

    /** 点击table行 */
    onSelectTable: function (selection, row) {},

    /** 分页触发 */
    getPager: function (size, index) {
      // console.log("getPager:size->",size ,";index->",index)
      let params = {}
      this.size = size;
      this.index = index;
      params.size = size;
      params.index = index;
      this.getPagerList(params);
    },
    /** 新增 **/
    handleAdd() {
      this.$router.push({
        name: "personInfoAdd",
      });
    },
    /** 修改 */
    handleUpdate(row) {
      this.$router.push({
        name: "personInfoUpdate",
        params: row,
      });
    },
    /** 删除 */
    handleDel(row) {
      this.$confirm("确认删除吗?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          delAccount(row.id)
            .then((res) => {
              this.loadInit();
            })
            .catch((error) => {
              this.$message.error(error);
            });
        })
        .catch((error) => {});
    },
    /** 详情 */
    handleDetail(row) {
      console.log(row,"******row")
      this.$router.push({
        name: "personDetail",
        params: row
      })
    },
    /** ntko */

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
      window.ntkoCloseEvent = function () {
        alert("跨浏览器插件应用程序窗口已关闭!");
      };
    },
     /** 获取权限 */
    getPermission() {
      queryRole().then((res) => {
        if (res.data.status == 200) {
          let roles = res.data.results;
          console.log(res.data.results, "99999权限");
          let auth = false;
          roles.forEach(function (item) {
            if (item.roleId == "00000001") {
              auth = true;
            }
          });
          this.auth = auth;
          console.log("auth", this.auth);
        } else {
          this.$message.error("查询权限失败");
        }
      });
    },
  },
};
</script>
<style scoped>
.accountList {
  padding: 15px;
}

.contractList_head {
  background-color: #ffffff;
  padding: 18px;
  display: flex;
  justify-content: space-between;
}
.form_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px 18px 0 18px;
}
.cleanbtn{
 margin-right: 10px;
}
.requireContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 15px;
}

.requireContainer_head {
  float: right;
  margin-bottom: 10px;
}

.requireContainer_head_en {
  width: 160px;
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

.head_right_orange {
  margin: 0 6px 0 10px;
}

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
</style>
<style>
/* el-input修改 */
.contractList .el-input--prefix .el-input__inner {
  padding-left: 50px;
}

/* 供应商输入框 */
.gongyingshang_zb .el-input--prefix .el-input__inner {
  padding-left: 65px;
}
/* 结算频率输入框 */
.jiesuanpinlv_zb .el-input--prefix .el-input__inner {
  padding-left: 75px;
}

.contractList .el-input__prefix {
  left: 8px;
  color: #262626;
  font-size: 14px;
}

/* element表单修改 */
.contractList .el-form-item {
  margin-bottom: 0px;
}

/* 表头背景色 */
.contractList .el-table th {
  background: #fafafa;
}
</style>
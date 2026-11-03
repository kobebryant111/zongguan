<!-- 台账管理列表 -->
<template>
  <div class="list-container accountList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form :model="paramsForm" :label-position="'left'" ref="formRef" label-width="100px">
        <el-row type="flex" justify="space-between">
          <el-col :span="6">
            <el-form-item prop label="外包项目名称">
                  <el-input
                    placeholder=""
                    v-model="paramsForm.projectName"
                    filterable
                  >
                  </el-input>
              </el-form-item>
          </el-col>
         <el-col :span="12">
            <el-row type="flex" justify="end">
              <clean-btn :size="sizes" :forms="this.paramsForm" class="cleanbtn" />
              <button-orange
                :buttonName="buttonSearch"
                :size="sizes"
                :fns="submitBtn"
              ></button-orange>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="requireContainer">
      <div class="requireContainer_head">
        <div class="requireContainer_head_en"></div>
        <el-row type="flex" justify="end">
          <!-- <el-col :span="12" v-if="auth"> -->
          <el-col :span="12">
            <el-upload
              style="display: inline-block"
              action="Fake Action"
              :file-list="fileList"
              :show-file-list="false"
              :http-request="handleImport"
            >
              <el-button size="small" type="button">
                <i class="el-icon-upload2"></i>导入
              </el-button>
            </el-upload>
          </el-col>
          <el-col :span="12">
            <button-orange
              :buttonName="buttonAdd"
              :size="sizes"
              :fns="handleAdd"
            ></button-orange>
          </el-col>
        </el-row>
      </div>
      <el-table
        :data="crewData"
        highlight-current-row
        height="680"
        fixed="left"
        @select-all="onSelectTable"
        @select="onSelectTable"
      >
        <el-table-column
          label="外包项目名称"
          prop="projectName"
          width="200"
        ></el-table-column>
        <el-table-column label="具体外包事项" prop="descriptions" width="200" />
        <el-table-column
          label="外包业务用工部门"
          width="165"
          prop="deptName"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column label="外包服务提供商" prop="vendorName" width="240"  show-overflow-tooltip/>
        <el-table-column
          label="服务商注册地"
          prop="registerPlace"
          width="130"
          show-overflow-tooltip
        />
        <el-table-column
          label="合同起始时间"
          prop="startDate"
          width="125"
        ></el-table-column>
        <el-table-column
          label="合同到期日"
          prop="endDate"
          width="125"
        ></el-table-column>
        <el-table-column label="外包项目金额" prop="amount" width="150" />
        <el-table-column label="外包项目人数" prop="nums" width="150" />
        <el-table-column
          label="服务方式（是否驻场）"
          prop="isStation"
          width="180"
        >
          <!-- <template slot-scope="scope">
            <span v-if="scope.row.isStation == 'Y'">是</span>
            <span v-else-if="scope.row.isStation == 'N'">否</span>
          </template> -->
        </el-table-column>
        <el-table-column label="是否分包或转包" prop="isSubcontract" width="150">
          <!-- <template slot-scope="scope">
            <span v-if="scope.row.isSubcontract == 'Y'">是</span>
            <span v-else-if="scope.row.isSubcontract == 'N'">否</span>
          </template> -->
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template slot-scope="scope">
            <!-- <el-link type="warning" :underline="false" @click="onUserDetail(scope.row.badge)">详情</el-link> -->
            <el-link
              type="warning"
              :underline="false"
              @click="handleUpdate(scope.row)"
              >修改</el-link
            >
            <el-link
              type="warning"
              :underline="false"
              @click="handleDel(scope.row)"
              >删除</el-link
            >
            <el-link
              type="warning"
              :underline="false"
              @click="handleDetail(scope.row)"
              >详情</el-link
            >
          </template>
        </el-table-column>
      </el-table>

      <div class="pager_zb">
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
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
import {
  queryPager,
  updateAccount,
  delAccount,
  importExcel,
} from "@/api/accountManager/accountManager";
import { queryRole } from "@/api/kqManager/calendar";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept, getWbCompany } from "@/api/integrated/wbryzrlc";
import Moment from "moment";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "contract",
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramsForm: {
        projectName: null,
      },
      crewData: [], // 列表分页数据
      drawer: false, // 默认不显示
      peo: "zh",
      buttonAdd: "新 增", //新增
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      deptno: "",
      depData: [],
      deptno_options: [], //部门数据
      outsourceno_options: [], //外包商
      jsForm_options: [
        // 结算方式
        {
          value: 0,
          label: "按月结算",
        },
        {
          value: 1,
          label: "按季结算",
        },
        {
          value: 2,
          label: "按年结算",
        },
      ],
      buttonImport: "导 入", //导出按钮
      tem_detp_id: "", // 临时部门编号
      rysx_options: [], //人员属性集合
      auth: false, // 是否有权限查看单价
      fileList: [],
    };
  },
  created() {
    this.getWbcom();
  },
  mounted() {
    this.loadInit();
    this.searchEntry();
  },
  methods: {
    /** 查询业务字典 */
    searchEntry() {
      getDicEntry({ dicttypeid: "NEWKG_RYSX" }).then((res) => {
        if (res.data.status !== 200) {
          this.alter(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        console.log(data, "data数据");
        // 人员属性
        this.rysx_options = data.filter((item) => {
          return item.dicttypeid == "NEWKG_RYSX";
        });
        console.log(data, "data数据21211221");
        return data;
      });
    },

    /** 分页初始化方法 */
    loadInit: function () {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      this.getPagerList(params);
       this.getPermission();
    },

    /** 获取列表数据 */
    getPagerList(params) {
      //   let data = common.paramsFormat(params);
      let self = this;
      queryPager(params).then((res) => {
        if (res.data.status == 200) {
          self.crewData = res.data.results;
          console.log(self.crewData, "台账数据");
          self.totalNumber = res.data.total;
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
    createtimeFormat: function (row, cloumn) {
      let time = "";
      if (row.createtime != null) {
        // time = common.dateFormat(row.createtime, "yyyy年MM月dd日");
        time = Moment(row.createtime).format("YYYY-MM-DD");
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
      let params = {};
      params.projectName = this.paramsForm.projectName;
      params.size = this.size;
      params.index = this.index;
      this.getPagerList(this.paramsForm); //bin
    },

    /** 点击table行 */
    onSelectTable: function (selection, row) {},

    /** 分页触发 */
    getPager: function (size, index) {
      let params = {};
      let depid = this.paramsForm.deptno;
      // params.deptno = depid.replace("dep", "");
      // params.msname = this.paramsForm.msname;
      // params.wbsid = this.paramsForm.wbsid;
      // params.depid = this.paramsForm.depid;
      // params.usertype = this.paramsForm.usertype;
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      this.getPagerList(params);
    },
    /** 新增 **/
    handleAdd() {
      this.$router.push({
        name: "accountAdd",
      });
    },
    /** 修改 */
    handleUpdate(params) {
      this.$router.push({
        name: "accountAdd",
        query: { id: params.id },
      });
    },
    /** 删除 */
    handleDel(row) {
      this.$confirm("确认删除吗?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
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
    handleDetail(params) {
      this.$router.push({
        name: "accountDetail",
        query: { id: params.id },
      });
    },
    /** 导出 */
    handelExport: function () {
      let searchData = {};
      let depid = this.paramsForm.deptno;
      searchData.deptno = depid.replace("dep", "");
      searchData.msname = this.paramsForm.msname;
      searchData.wbsid = this.paramsForm.wbsid;
      let params = common.paramsFormat(searchData);
      const self = this;
      exportExcel(params).then((res) => {
        common.download(res.data, self, "面试人员.xls");
      });
    },
    // 导入数据
    handleImport(file, fileList) {
      var formData = new FormData();
      formData.append("file", file.file);
      let data = formData;
      importExcel(data).then((res) => {
        if (res.status == "200") {
          this.$message.success("导入成功");
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
      });
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

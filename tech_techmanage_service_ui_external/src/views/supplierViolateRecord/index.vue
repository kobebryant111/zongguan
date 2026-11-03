<!-- 单价调整列表 -->
<template>
  <div class="list-container interviewList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form
        :model="paramsForm"
        ref="formRef"
        :label-position="'left'"
        label-width="100px"
      >
        <el-row type="flex" justify="space-between" :gutter="20">
          <el-col :span="6">
            <el-form-item prop label="供应商名称：">
              <el-select
                placeholder="请选择"
                v-model="paramsForm.vendorCode"
                filterable
              >
                <el-option
                  v-for="item in outsourceno_options"
                  :key="item.id"
                  :label="item.outsourcname"
                  :value="item.orgcode"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="end">
          <el-col :span="12">
            <el-row type="flex" justify="end">
              <clean-btn :size="sizes" :forms="this.paramsForm" />
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

    <div class="table_box">
      <div class="requireContainer_head">
        <div class="requireContainer_head_en"></div>
        <el-row type="flex" justify="end">
          <el-col>
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
        height="510"
        fixed="left"
        @select-all="onSelectTable"
        @select="onSelectTable"
      >
        <el-table-column
          label="处罚时间"
          prop="deptName"
          width="200"
          show-overflow-tooltip
        />
        <el-table-column
          label="处罚供应商"
          width="100"
          prop="prjMgrName"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column label="处罚供应商" prop="position" width="100" />
        <el-table-column label="处罚类型" prop="serviceYear" width="100" />
        <el-table-column label="处罚原因" prop="vendorName" width="200"  />
        <el-table-column label="对应措施" prop="vendorName" width="200"  />
        <el-table-column label="登记人" prop="vendorName" width="200"  />
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <!-- <el-link
              type="warning"
              :underline="false"
              @click="handleDetail(scope.row)"
              >修改</el-link
            >
            <el-link
              type="warning"
              :underline="false"
              @click="handleDetail(scope.row)"
              >删除</el-link
            > -->
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
import { queryPager } from "@/api/priceAdjustment/priceAdjustment";
import { queryRole } from "@/api/kqManager/calendar";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
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
      auth: false, // 权限
      paramsForm: {
        contractNo: null, // 合同编号
        contractName: null, // 合同名称
        vendor: null, // 供应商
        settlementFrequency: null, // 结算频率
      },
      crewData: [], // 列表分页数据
      drawer: false, // 默认不显示
      peo: "zh",
      buttonAdd: "新 增", //新增
      buttonImport: "导入供应商",
      buttonPrice: "查看价格信息",
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
      buttonExport: "导 出", //导出按钮
      tem_detp_id: "", // 临时部门编号
      rysx_options: [], //人员属性集合
      isAuthView: false, // 是否有权限查看单价 默认没哟
    };
  },
  created() {
    this.getWbcom();
    this.filDep();
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
          console.log(res, "响应数据");
          self.crewData = res.data.results;
          self.totalNumber = res.data.total;
        }
      });
    },
    /** 获取权限 */
    getPermission() {
      queryRole().then((res) => {
        if (res.data.status == 200) {
          let roles = res.data.results;
          console.log(res.data.results, "99999权限");
          let auth = false;
          roles.forEach(function (item) {
            if (item.roleId == "PERMANAGE") {
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
      // params.size = this.size;
      // params.index = this.index;
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
        name: "violateRecord",
        // params: {
        //   formScene: "lcfqApply",
        // },
      });
    },
    /** 导入 */
    handleImport() {
      console.log("导入");
    },
    /** 导出 */
    handleExport() {
      console.log("导出");
    },
    /** 查看价格信息 */
    handlePrice() {
      this.$router.push({
        name: "supplierPriceInfo",
      });
    },
    /** 修改 */
    handleUpdate(params) {
      this.$router.push({
        name: "outsrcingPriceChg",
        params: { id: params.id },
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
          delContract(row.id)
            .then((res) => {
              if (res.data.status == 200) {
                this.loadInit();
              } else {
                this.$message.error(res.data.message);
              }
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
        name: "contractDetail",
        params: { id: params.id },
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
  },
};
</script>
<style scoped>
.interviewList {
  width: 100%;
  /* height: 100%; */
  padding: 15px;
}
.form_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px 18px 0 18px;
}
.table_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px;
}
.requireContainer_head {
  float: right;
}
.margin_bottom {
  margin-bottom: 15px;
}
/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
</style>

<style>
.el-button {
  margin-left: 15px;
}
.el-row {
  margin-bottom: 15px;
}
</style>


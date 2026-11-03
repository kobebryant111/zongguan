<!-- 费用结算管理列表 -->
<template>
  <div class="list-container interviewList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form
        :model="paramsForm"
        :label-position="'left'"
        ref="formRef"
        label-width="97px"
      >
        <el-row type="flex" justify="space-between">
          <el-col :span="6">
            <el-form-item prop label="供应商名称：">
              <div>
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
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-row type="flex" justify="end">
              <clean-btn
                :size="sizes"
                :forms="this.paramsForm"
                class="cleanbtn"
              />
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
          <!-- <el-col :span="11">
                        <button-common :buttonName="buttonExport" :size="sizes" :fns="handelExport"></button-common>
          </el-col>-->
          <el-col :span="12">
            <button-orange
              :buttonName="buttonAdd"
              :size="sizes"
              :fns="handleEdit"
            ></button-orange>
          </el-col>
        </el-row>
      </div>
      <el-table
        :data="crewData"
        highlight-current-row
        height="510"
        @select-all="onSelectTable"
        @select="onSelectTable"
      >
        <el-table-column label="供应商名称" prop="vendorName" />
        <el-table-column label="应付总金额" prop="amount">
          <template slot-scope="scope">
            <span>{{ scope.row.amount.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="税率" prop="taxRate">
          <template slot-scope="scope">
            <span>{{ (scope.row.taxRate * 100).toFixed(0) + "%" }}</span>
          </template>
        </el-table-column>
        <el-table-column label="实付金额(含税)" prop="amountTax" v-if="false">
          <template slot-scope="scope">
            <span>{{ scope.row.amountTax.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column
          label="实付金额（不含税）"
          prop="amount"
          width="150"
          show-overflow-tooltip
        /> -->
        <el-table-column label="项目经理" prop="proManagerName" width="200" show-overflow-tooltip />
        <el-table-column label="操作" width="122">
          <template slot-scope="scope">
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
import { getCrewPager } from "@/api/userApi/bankUser";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import { queryPager } from "@/api/payConfirm/expenseSettle";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept, getWbCompany } from "@/api/integrated/wbryzrlc";
import Moment from "moment";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "Crew",
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramsForm: {
        vendorCode: null,
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
      buttonExport: "导 出", //导出按钮
      tem_detp_id: "", // 临时部门编号
      rysx_options: [], //人员属性集合
      isAuthView: false, // 是否有权限查看单价 默认没哟
    };
  },
  created() {
    // this.filDep();
    this.getWbcom();
  },
  mounted() {
    this.loadInit();
    this.searchEntry();
  },
  methods: {
    /**
                初始化
               */
    init: function (params) {
      let data = common.paramsFormat(params);
      getCrewPager(data).then((res) => {
        this.crewData = res.data.results;
        this.totalNumber = res.data.total;
      });
    }, // 初始化 end

    // 查询业务字典
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

    /**
     * 分页初始化方法
     */
    loadInit: function () {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      this.getPagerList(params);
    }, // end

    // 获取部门
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
        }); // end
    },

    // 查询外包商
    getWbcom() {
      getListAll()
        .then((res) => {
          this.outsourceno_options = res.data.results;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },

    // 获取列表数据
    getPagerList(params) {
      let self = this;
      queryPager(params).then((res) => {
        if (res.data.status == 200) {
          self.crewData = res.data.results;
          self.totalNumber = res.data.total;
          if (self.totalNumber <= 0) {
            return false;
          }
        }
      });
    },

    //  时间格式化
    createtimeFormat: function (row, cloumn) {
      let time = "";
      if (row.createtime != null) {
        // time = common.dateFormat(row.createtime, "yyyy年MM月dd日");
        time = Moment(row.createtime).format("YYYY-MM-DD");
      }
      return time;
    },

    // 部门显示内容
    deptFormat(row, cloumn) {
      let dep = "";
      const reg = /([^-]+)$/;
      if (row.deptname != null) {
        dep = row.deptname.match(reg)[1];
      }
      return dep;
    },

    /**
     * 搜索事件
     */
    submitBtn: function () {
      let params = {};
      params.vendorCode = this.paramsForm.vendorCode;
      params.size = this.size;
      params.index = this.index;
      this.getPagerList(params); //bin
    }, // 搜索事件 end

    /**
     * 点击table行
     */
    onSelectTable: function (selection, row) {},

    /**
     * 分页触发
     */
    getPager: function (size, index) {
      let params = {};
      let depid = this.paramsForm.deptno;
      params.deptno = depid.replace("dep", "");
      params.msname = this.paramsForm.msname;
      params.wbsid = this.paramsForm.wbsid;
      // params.depid = this.paramsForm.depid;
      // params.usertype = this.paramsForm.usertype;
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      this.getPagerList(params);
    }, // end

    // 导出
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
    handleDetail(params) {
      this.$router.push({
        path: "expenseSettleDetail",
        query: params,
      });
    },
    // ntko
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

    handleEdit(params) {
      this.$router.push({
        name: "expenseSettleInfo",
        params: params,
      });
    },

    // 分组
    groupBy(items, f) {
      let groups = {};
      items.forEach((i) => {
        let group = JSON.stringify(f(i));
        groups[group] = groups[group] || [];
        groups[group].push(i);
      });
      return Object.keys(groups).map((group) => {
        return groups[group];
      });
    },

    // 发起面试
    // startInterview() {
    //     this.$router.push({
    //         name: 'wbryms',
    //         params: {
    //             formScene: "interviewAdd"
    //         }
    //     });
    // }
  },
};
</script>
<style scoped>
.interviewList {
  padding: 15px;
}

.form_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px;
}
.cleanbtn {
  margin-right: 10px;
}
.query_head .query_box {
  width: 90%;
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

.query_head_right_orange {
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
.interviewList .el-input__prefix {
  left: 8px;
  color: #262626;
  font-size: 14px;
}

/* element表单修改 */
.interviewList .el-form-item {
  margin-bottom: 0px;
}

/* 表头背景色 */
.interviewList .el-table th {
  background: #fafafa;
}
</style>
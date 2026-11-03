<!-- 新增费用结算 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">费用合并</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common
                :buttonName="buttonClose"
                :size="sizes"
                :fns="close"
              ></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <div class="list-container interviewList">
      <div class="form_box">
        <el-form :model="paramsForm" ref="formRef" label-width="120px">
          <el-row type="flex" justify="space-between">
            <el-col :span="6">
              <el-form-item prop label="合同名称：">
                <el-input v-model="paramsForm.contractName"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop label="供应商名称：">
                <div>
                  <el-select
                    placeholder="请选择"
                    v-model="paramsForm.vendor"
                    filterable
                  >
                    <el-option
                      v-for="item in outsourceno_options"
                      :key="item.id"
                      :label="item.outsourcname"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop label="开始日期">
                <el-date-picker
                  v-model="paramsForm.effectiveDate"
                  value-format="yyyy-MM-dd"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  @change="changeEffectiveDate"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop label="结束日期">
                <el-date-picker
                  v-model="paramsForm.expiryDate"
                  value-format="yyyy-MM-dd"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  @change="changeExpiryDate"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="end" class="searchButton">
            <el-col :span="12">
              <el-row type="flex" justify="end">
                <clean-btn
                  :size="sizes"
                  :forms="this.paramsForm"
                  class="cleabBtn"
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
      <!-- 修改后 -->

      <div class="requireContainer">
        <div class="tips">
          Tips:请选择相同行号、账号、供应商名称、税率的支付确认记录进行合并。
        </div>
        <div class="requireContainer_head">
          <div class="requireContainer_head_en"></div>
          <el-row type="flex" justify="end">
            <!-- <el-col :span="11">
                        <button-common :buttonName="buttonExport" :size="sizes" :fns="handelExport"></button-common>
          </el-col>-->
            <el-col :span="12" style="marginright: 18px">
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
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            type="selection"
            width="55"
            align="center"
          ></el-table-column>
          <el-table-column label="项目经理" prop="proManagerName" width="125" />
          <el-table-column
            prop="contractNo"
            label="合同编号"
            width="150"
          ></el-table-column>
          <el-table-column
            label="合同名称"
            prop="contractName"
            width="200"
            show-overflow-tooltip
          />
          <el-table-column
            label="供应商名称"
            prop="vendorName"
            width="200"
            show-overflow-tooltip
          />
          <el-table-column
            label="费用开始日期"
            prop="feeStartDate"
            width="180"
          />
          <el-table-column label="费用结束日期" prop="feeEndDate" width="125" />
          <el-table-column
            label="支付确认日期"
            prop="paymentSubmitDate"
            width="125"
          />
          <el-table-column label="应付总金额" prop="amount" width="125">
            <template slot-scope="scope">
              <span>{{ scope.row.amount.toFixed(2) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="税率" prop="taxRate" width="125">
            <template slot-scope="scope">
              <span>{{ (scope.row.taxRate * 100).toFixed(0) + "%" }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实付金额(含税)" prop="amountTax" width="125" v-if="false">
            <template slot-scope="scope">
              <span>{{ scope.row.amountTax.toFixed(2) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            fixed="right"
            width="122"
            align="center"
          >
            <template slot-scope="scope">
              <!-- <el-link type="warning" :underline="false" @click="handleEdit(scope.row)">修改</el-link> -->
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
  </div>
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
import { getCrewPager } from "@/api/userApi/bankUser";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import { queryPager, exportExcel } from "@/api/integrated/wbms";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept, getWbCompany } from "@/api/integrated/wbryzrlc";
import { queryForSettle } from "@/api/payConfirm/payConfirm";
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
        contactName: null,
        vendor: null,
        effectiveTime: null,
        expiryTime: null,
        feeStartDate: null, // 生效开始时间
        feeStartDateE: null,
        feeEndDate: null,
        feeEndDateE: null,
      },
      crewData: [], // 列表分页数据
      drawer: false, // 默认不显示
      peo: "zh",
      buttonAdd: "合并费用", //支付确认
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      deptno: "",
      depData: [],
      deptno_options: [], //部门数据
      outsourceno_options: [], //外包商
      buttonExport: "导 出", //导出按钮
      buttonClose: "关 闭", //关闭按钮名字
      rysx_options: [], //人员属性集合
      isAuthView: false, // 是否有权限查看单价 默认没哟

      multiple: true,
      single: false,
      selectData: [],
      isBankCode: "",
      isAccount: "",
      isVendor: "",
      isTaxRate: "",
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
      queryForSettle(params).then((res) => {
        if (res.data.status == 200) {
          self.crewData = res.data.results;
          self.totalNumber = res.data.total;
          if (self.totalNumber <= 0) {
            return false;
          }
          let type = self.crewData[0].roleType;
          if (1 === type || 4 === type) {
            self.isAuthView = true;
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
      params.contactName = this.paramsForm.contactName;
      params.vendor = this.paramsForm.vendor;
      params.feeStartDate = this.paramsForm.feeStartDate;
      params.feeStartDateE = this.paramsForm.feeStartDateE;
      params.feeEndDate = this.paramsForm.feeEndDate;
      params.feeEndDateE = this.paramsForm.feeEndDateE;
      // params.size = this.size;
      // params.index = this.index;
      this.getPagerList(params); //bin
    }, // 搜索事件 end

    /**
     * 点击table行
     */
    onSelectTable: function (selection, row) {
      console.log(row, "12121212121");
    },
    handleSelectionChange(selection) {
      let { bankCode, account, vendor, taxRate } = selection[0];
      this.isBankCode = selection.every((item) => item.bankCode == bankCode);
      this.isAccount = selection.every((item) => item.account == account);
      this.isVendor = selection.every((item) => item.vendor == vendor);
      this.isTaxRate = selection.every((item) => item.taxRate == taxRate);
      this.selectData = selection;
      this.single = selection.length > 1;
      console.log(
        this.isBankCode,
        this.isAccount,
        this.isVendor,
        this.isTaxRate,
        "行号"
      );
    },
    /**
     * 分页触发
     */
    getPager: function (size, index) {
      let params = {};
      params.contactName = this.paramsForm.contactName;
      params.vendor = this.paramsForm.vendor;
      params.feeStartDate = this.paramsForm.feeStartDate;
      params.feeStartDateE = this.paramsForm.feeStartDateE;
      params.feeEndDate = this.paramsForm.feeEndDate;
      params.feeEndDateE = this.paramsForm.feeEndDateE;
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      this.getPagerList(params);
    }, // end

    /**
     * 行员详情
     */

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
    /** 合并费用 */
    handleEdit(params) {
      if (
        this.isBankCode &&
        this.isAccount &&
        this.isVendor &&
        this.isTaxRate
      ) {
        this.$router.push({
          name: "mergeInfo",
          params: { selectData: this.selectData },
        });
      } else {
        this.$message.warning(
          "请选择相同行号、账号、供应商名称、税率的支付确认记录进行合并。"
        );
        return;
      }
      // this.$router.push({
      //   name: "mergeInfo",
      //   params: {'selectData':this.selectData}
      // });
    },
    handleDetail(params) {
      // this.$router.push({
      //   name: "payConfirmDetail",
      //   params: params,
      // });
      let text = this.$router.resolve({
        path: "payConfirmDetail",
        query: { id: params.id },
      });
      window.open(text.href, "_blank");
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
    // 关闭按钮
    close() {
      this.$router.push({ name: "expenseSettle" });
    },

    changeEffectiveDate() {
      if (this.paramsForm.effectiveDate != null) {
        (this.paramsForm.feeStartDate = this.paramsForm.effectiveDate[0]),
          (this.paramsForm.feeStartDateE = this.paramsForm.effectiveDate[1]);
      }
    },
    changeExpiryDate() {
      if (this.paramsForm.expiryDate != null) {
        (this.paramsForm.feeEndDate = this.paramsForm.expiryDate[0]),
          (this.paramsForm.feeEndDateE = this.paramsForm.expiryDate[1]);
      }
    },
  },
};
</script>
<style scoped>
.interviewList {
  padding: 15px;
}

/* 导航菜单栏 */
.nav {
  width: 100%;
  /* height: 30px; */
  background-color: #ffffff;
}

.nav .nav_name {
  width: 180px;
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

.wrapper_right_bottom_1_zb {
  margin-right: 10px;
}

.query_head {
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
.table_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px;
}
.tips {
  display: inline-block;
  margin-top: 22px;
  color: red;
}
.searchButton {
  padding: 10px 0px;
}
.searchButton .cleabBtn {
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
.el-date-editor .el-range-separator {
  padding: 0 18px;
}
</style>
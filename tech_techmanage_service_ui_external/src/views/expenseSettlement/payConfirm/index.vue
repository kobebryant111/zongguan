<!-- 附件管理列表 -->
<template>
  <div class="list-container payConfirList">
    <div class="form_box">
      <el-form
        :model="paramsForm"
        ref="formRef"
        :label-position="'left'"
        label-width="110px"
      >
        <el-row
          type="flex"
          justify="space-between"
          style="margin-bottom: 15px"
          :gutter="20"
        >
          <el-col :span="6">
            <el-form-item prop label="合同名称：">
              <el-input v-model="paramsForm.contractName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="供应商名称：">
              <el-select
                placeholder="请选择"
                v-model="paramsForm.vendor"
                filterable
              >
                <el-option
                  v-for="item in outsourceno_options"
                  :key="item.orgcode"
                  :label="item.outsourcname"
                  :value="item.orgcode"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="支付提交日期：">
              <el-date-picker
                v-model="paramsForm.paymentSubmitDate"
                type="date"
                value-format="yyyy-MM-dd"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="审批状态：">
              <el-select
                placeholder="请选择"
                v-model="paramsForm.approvalStatus"
              >
                <el-option
                  v-for="item in approve_options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row type="flex" justify="end">
                    <el-col :span="12">
                        <el-row type="flex" justify="end">
                            <clean-btn :size="sizes" :forms="this.paramsForm" />
                            <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
                        </el-row>                        
                    </el-col>
                </el-row> -->
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
          <div class="interviewList_head_right_orange">
            <button-orange
              :buttonName="buttonAdd"
              :size="sizes"
              :fns="handleUpdate"
            ></button-orange>
          </div>
        </el-row>
      </div>
      <el-table
        ref="multipleTable"
        :data="crewData"
        highlight-current-row
        height="510"
        @select="select"
        @row-click="rowClick"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          label="合同编号"
          prop="contractNo"
          width="150"
        ></el-table-column>
        <el-table-column
          label="合同名称"
          prop="contractName"
          width="200"
        ></el-table-column>
        <el-table-column
          label="供应商名称"
          prop="vendorName"
          width="200"
        ></el-table-column>
        <el-table-column
          label="费用开始日期"
          prop="feeStartDate"
          width="120"
        ></el-table-column>
        <el-table-column
          label="费用结束日期"
          prop="feeEndDate"
          width="120"
        ></el-table-column>
        <el-table-column
          label="支付提交日期"
          prop="paymentSubmitDate"
          width="120"
        ></el-table-column>
        <el-table-column label="审批状态" prop="approvalStatus" width="125">
          <template slot-scope="scope">
            <span v-if="scope.row.approvalStatus == '01'">审批通过</span>
            <span v-else-if="scope.row.approvalStatus == '02'">退回</span>
            <span v-else-if="scope.row.approvalStatus == '03'">撤回</span>
            <span v-else-if="scope.row.approvalStatus == '04'">审批中</span>
            <span v-else-if="scope.row.approvalStatus == '05'">草稿</span>
          </template>
        </el-table-column>
        <el-table-column
          label="应付总金额"
          prop="amount"
          width="150"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span>{{ scope.row.amount.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="税率" prop="taxRate" width="125">
          <template slot-scope="scope">
            <span>{{ (scope.row.taxRate * 100).toFixed(0) + "%" }}</span>
          </template>
        </el-table-column>
        <el-table-column label="实付金额（含税）" prop="amountTax" width="140" v-if="false">
          <template slot-scope="scope">
            <span>{{ scope.row.amountTax.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column
          label="实付金额（不含税）"
          prop="amountTax"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.amountTax.toFixed(2) }}</span>
          </template>
        </el-table-column> -->
        <el-table-column label="项目经理" prop="proManagerName" width="125" />
        <el-table-column label="操作" fixed="right" width="122" align="center">
          <template slot-scope="scope">
            <el-link
              type="warning"
              :underline="false"
              @click="handleUpdate(scope.row)"
              v-if="['05'].includes(scope.row.approvalStatus)"
              >修改</el-link
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
import { getCrewPager } from "@/api/userApi/bankUser";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import { queryPager } from "@/api/payConfirm/payConfirm";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { getWbCompany } from "@/api/integrated/wbryzrlc";
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
        contractName: null,
        vendor: null,
        paymentSubmitDate: null,
        approvalStatus: null,
      },
      crewData: [], // 列表分页数据
      drawer: false, // 默认不显示
      peo: "zh",
      buttonAdd: "发起支付确认", //支付确认
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
      selectData: [],
      approve_options: [
        {
          value: "01",
          label: "审批通过",
        },
        {
          value: "02",
          label: "退回",
        },
        {
          value: "03",
          label: "撤回",
        },
        {
          value: "04",
          label: "审批中",
        },
        {
          value: "05",
          label: "草稿",
        },
      ],
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
    /** 初始化 */
    init: function (params) {
      let data = common.paramsFormat(params);
      getCrewPager(data).then((res) => {
        this.crewData = res.data.results;
        this.totalNumber = res.data.total;
      });
    }, // 初始化 end

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

    /**
     * 分页初始化方法
     */
    loadInit: function () {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      this.getPagerList(params);
    }, // end

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
      params.contractName = this.paramsForm.contractName;
      params.vendor = this.paramsForm.vendor;
      params.paymentSubmitDate = this.paramsForm.paymentSubmitDate;
      params.approvalStatus = this.paramsForm.approvalStatus;
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

    /**
     * 行员详情
     */

    onUserDetail: function (userId) {
      this.drawer = true;
      this.$nextTick((_) => {
        let item = this.$refs.cwdt;
        item.userId = userId;
        item.init();
      });
    },
    /** 详情 */
    handleDetail(params) {
      this.$router.push({
        path: "payConfirmDetail",
        query: { id: params.id },
      });
    },
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

    handleUpdate(params) {
      this.$router.push({
        path: "payConfirmEdit",
        query: params,
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
    select(selection, row) {
      this.$refs.multipleTable.clearSelection();
      if (selection.length == 0) return;
      this.$refs.multipleTable.toggleRowSelection(row, true);
    },
    handleSelectionChange(val) {
      console.log(val, "p88888888888");
      this.selectData = val;
      console.log(this.selectData, "ppppppp2");
    },
    rowClick(row, column) {
      const selectData = this.selectData;
      this.$refs.multipleTable.clearSelection();
      if (selectData.length == 1) {
        selectData.forEach((item) => {
          if (item == row) {
            this.$refs.multipleTable.toggleRowSelection(row, false);
          } else {
            this.$refs.multipleTable.toggleRowSelection(row, true);
          }
        });
      } else {
        this.$refs.multipleTable.toggleRowSelection(row, true);
      }
      console.log(this.selectData, "ppppppp1");
    },
  },
};
</script>
<style scoped>
.payConfirList {
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
.el-date-editor .el-range-separator {
  padding: 0 18px;
}
</style>

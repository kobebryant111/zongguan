<!-- 附件管理列表 -->
<template>
  <div class="list-container interviewList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form :model="paramsForm" ref="formRef"  :label-position="'left'"
        label-width="75px">
        <el-row type="flex" justify="space-between" style="margin-bottom: 15px" :gutter="20">
          <el-col :span="6">
            <el-form-item prop label="文件名称:">
              <el-input v-model="paramsForm.fileName" placeholder="请输入">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="文件类型:">
              <el-select v-model="paramsForm.fileType" placeholder="请选择">
                <el-option
                  v-for="item in fileData"
                  :key="item.key"
                  :label="item.value"
                  :value="item.key"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="生效日期:">
              <el-date-picker
                v-model="paramsForm.effectiveDate"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                @change="changeEffectiveDate"
                @input="$forceUpdate()"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="失效日期:">
              <el-date-picker
                v-model="paramsForm.expiryDate"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                @change="changeExpiryDate"
                @input="$forceUpdate()"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="end">
          <el-col :span="12">
            <el-row type="flex" justify="end">
              <clean-btn :size="sizes" :forms="this.paramsForm" :call='resetForm'/>
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
          <!-- <el-col :span="11">
                        <button-common :buttonName="buttonExport" :size="sizes" :fns="handelExport"></button-common>
          </el-col>-->
          <div class="interviewList_head_right_orange">
            <button-orange
              :buttonName="buttonAdd"
              :size="sizes"
              :fns="handleAdd"
              v-if="auth"
            ></button-orange>
          </div>
        </el-row>
      </div>
      <el-table
        :data="crewData"
        highlight-current-row
        height="510"
        @select-all="onSelectTable"
        @select="onSelectTable"
      >
        <el-table-column
          label="编号"
          prop="fileNo"
          width="150"
        ></el-table-column>
        <el-table-column label="文件名称" prop="fileName" width="180"  show-overflow-tooltip/>
        <el-table-column label="生效日期" prop="startDate" width="180">
          <template slot-scope="scope">
            <span>{{
              scope.row.fileType !== "0" ? "/" : scope.row.startDate
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="失效日期" prop="endDate" width="125">
          <template slot-scope="scope">
            <span>{{
              scope.row.fileType !== "0" ? "/" : scope.row.endDate
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="总人数" prop="totals" width="125">
          <template slot-scope="scope">
            <span>{{
              scope.row.fileType !== "0" ? "/" : scope.row.totals
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="可用人数" prop="availableCount" width="125">
          <template slot-scope="scope">
            <span>{{
              scope.row.fileType !== "0" ? "/" : scope.row.availableCount
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="已用人数" prop="numberUsed" width="125">
          <template slot-scope="scope">
            <span>{{
              scope.row.fileType !== "0" ? "/" : scope.row.numberUsed
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="文件类型" prop="fileType" width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.fileType == '0'">总办议案</span>
            <span v-else-if="scope.row.fileType == '1'">发票</span>
            <span v-else-if="scope.row.fileType == '2'">其他</span>
          </template>
        </el-table-column>
        <el-table-column label="文件状态" prop="status" width="80" />
        <el-table-column label="操作" width="122">
          <template slot-scope="scope">
            <el-link
              type="warning"
              :underline="false"
              @click="handleUpdate(scope.row, false)"
              v-if="auth"
              >修改</el-link
            >
            <el-link
              type="warning"
              :underline="false"
              v-if="scope.row.status == '未生效'"
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
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import {
  queryPager,
  exportExcel,
  delFile,
} from "@/api/supplierManager/fileManage";
import { queryRole } from "@/api/kqManager/calendar";
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
      auth: false, // 权限
      paramsForm: {
        fileName: null, // 姓名
        fileType: null, // 文件类型
        effectiveDate: [], //搜索时间
        startDate: null, // 生效开始时间
        startDateE: null,
        endDate: null,
        endDateE: null,
        expiryDate: [],
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
      fileData: [
        { key: 0, value: "总办议案" },
        { key: 1, value: "发票" },
        { key: 2, value: "其他" },
      ],
    };
  },
  created() {
    // this.filDep();
    this.getWbcom();
    this.getPermission();
  },
  mounted() {
    this.loadInit();
    this.searchEntry();
  },
  methods: {
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
      let paramsQuery = window.localStorage.getItem('queryParams');
      let queryParams = null;
      if (typeof paramsQuery == "undefined" ){
        queryParams = JSON.parse(paramsQuery);
      }
      //window.localStorage.getItem('queryParams')
      // let queryParams = JSON.parse("undefined")
      console.log("queryParams", queryParams);
      if(queryParams == null) {
        params.size = this.size;
        params.index = this.index;
      } else {
        this.paramsForm = queryParams;
        if(queryParams.startDate && queryParams.startDateE) {
          this.paramsForm.effectiveDate[0] = queryParams.startDate
          this.paramsForm.effectiveDate[1] = queryParams.startDateE
        }
        // this.paramsForm.effectiveDate.splice(0,1, queryParams.startDate)
        // this.paramsForm.effectiveDate.splice(1,1, queryParams.startDateE)
        console.log('this.paramsForm.effectiveDate', this.paramsForm.effectiveDate)
        if(queryParams.endDate && queryParams.endDateE) {
          this.paramsForm.expiryDate[0] = queryParams.endDate
          this.paramsForm.expiryDate[1] = queryParams.endDateE
        }
        // this.paramsForm.expiryDate.splice(0,1, queryParams.endDate)
        // this.paramsForm.expiryDate.splice(1,1, queryParams.endDateE)
        // this.paramsForm.expiryDate = [queryParams.endDate, queryParams.endDateE]
        params = this.paramsForm;
      }
      this.getPagerList(params);
    }, // end

    // 重置
    resetForm() {
      console.log('this.paramsForm', this.paramsForm)
      Object.keys(this.paramsForm).forEach(key => (this.paramsForm[key] = ''));
      this.paramsForm.effectiveDate = ''
      this.paramsForm.expiryDate = '';
      // this.$set(this.paramsForm.effectiveDate, )
      this.$refs['formRef'].resetFields();
      this.$forceUpdate()
      window.localStorage.clear('queryParams')
    },

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
        }
      });
    },

    /** 获取权限 */
    getPermission() {
      queryRole().then((res) => {
        if (res.data.status == 200) {
          let roles = res.data.results;
          console.log(res.data.results, "权限");
          let auth = false;
          roles.forEach(function (item) {
            if (item.roleId == "PERMANAGE" || item.roleId == "00000001") {
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
      params.fileName = this.paramsForm.fileName;
      params.fileType = this.paramsForm.fileType;
      params.startDate = this.paramsForm.startDate;
      params.startDateE = this.paramsForm.startDateE;
      params.endDate = this.paramsForm.endDate;
      params.endDateE = this.paramsForm.endDateE;
      window.localStorage.setItem('queryParams', JSON.stringify(params));
      // params.size = this.size;
      // params.index = this.index;
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
    },
    /** 新增 **/
    handleAdd() {
      this.$router.push({
        name: "fileEdit",
      });
    },
    /** 修改 */
    handleUpdate(params, detailDis) {
      this.$router.push({
        path: "fileEdit",
        query: { id: params.id, detailDis },
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
          const loading = common.loading(this);
          delFile(row.id)
            .then((res) => {
              loading.close();
              if (res.data.status == 200) {
                this.$message.success("删除成功");
                this.loadInit();
              } else {
                this.$message.error("删除失败");
              }
            })
            .catch((error) => {
              loading.close();
              this.$message.error(error);
            });
        })
        .catch((error) => {});
    },
    /** 详情 */
    handleDetail(params) {
      this.$router.push({
        path: "fileDetail",
        query: { id: params.id },
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
    changeEffectiveDate(val) {
      console.log('valu',val)
      if (this.paramsForm.effectiveDate != null) {
        (this.paramsForm.startDate = this.paramsForm.effectiveDate[0]),
          (this.paramsForm.startDateE = this.paramsForm.effectiveDate[1]);
      }
    },
    changeExpiryDate() {
      if (this.paramsForm.expiryDate != null) {
        (this.paramsForm.endDate = this.paramsForm.expiryDate[0]),
          (this.paramsForm.endDateE = this.paramsForm.expiryDate[1]);
      }
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
 .el-date-editor .el-range-separator{
   padding: 0 18px;
 }
</style>

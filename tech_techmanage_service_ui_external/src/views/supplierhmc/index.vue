<!-- 供应商花名册 -->
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
                v-model="paramsForm.outsourcname"
                filterable
              >
                <el-option
                  v-for="item in outsourceno_options"
                  :key="item.id"
                  :label="item.outsourcname"
                  :value="item.outsourcname"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="供应商编号：">
              <el-input v-model="paramsForm.orgcode" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
          </el-col>
        </el-row>
        <!-- <el-row type="flex" justify="end">
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
        </el-row> -->
      </el-form>
    </div>

    <div class="table_box">
      <div class="requireContainer_head">
        <!-- <div class="table_box_top"> -->
        <el-row type="flex" justify="end" class="margin_bottom">
          <!-- <el-col> -->
              <el-button size="small" @click="handleDownload">导入模板下载</el-button>
          <!-- </el-col> -->
           <!-- <el-col > -->
            <el-upload
              action="Fake Action"
              :show-file-list="false"
              :http-request="changeUpload"
              :file-list="fileList">
                <el-button size="small">导入供应商</el-button>
            </el-upload>
          <!-- </el-col> -->
          <!-- <el-col> -->
            <button-orange
              :buttonName="buttonAdd"
              :size="sizes"
              :fns="handleAdd"
            ></button-orange>
          <!-- </el-col>   -->
          <!-- <el-col> -->
            <button-common
              :buttonName="buttonExport"
              :size="sizes"
              :fns="handleExport"
            ></button-common>
          <!-- </el-col> -->
           <!-- <el-col> -->
          <!-- </el-col> -->
        </el-row>
        <!-- </div> -->
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
          label="编号"
          prop="orgcode"
          show-overflow-tooltip
          align="center"
        />
        <el-table-column
          label="外包供应商名称"
          prop="outsourcname"
          align="center"
        ></el-table-column>
        <!-- <el-table-column label="考核类型" prop="position" width="100" />
        <el-table-column label="考核年份" prop="serviceYear" width="100" />
        <el-table-column label="考核季度" prop="vendorName" width="200"  />
        <el-table-column label="考核人数" prop="vendorName" width="200"  /> -->
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-link
              type="warning"
              :underline="false"
              @click="handleEdit(scope.row)"
              >修改</el-link
            >
            <el-link
              type="warning"
              :underline="false"
              @click="handleDetail(scope.row)"
              >删除</el-link
            >
            <el-link
              type="warning"
              :underline="false"
              @click="handleInfo(scope.row)"
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
import { queryPager, toDelete, importRoster, exportRoster } from "@/api/supplierhmc/index";
import { queryRole } from "@/api/kqManager/calendar";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import Moment from "moment";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
import { getDownloadUrl } from '@/api/common/flowApi';
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
      isdel: "0",
      auth: false, // 权限
      paramsForm: {
        outsourcname: "", // 供应商
        orgcode: "", // 供应商编号
      },
      crewData: [], // 列表分页数据
      drawer: false, // 默认不显示
      peo: "zh",
      buttonAdd: "新 增", //新增
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
      fileList: [],
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
      params.isdel = this.isdel;
      this.getPagerList(params);
      this.getPermission();
    },

    /** 获取列表数据 */
    getPagerList(params) {
        let data = common.paramsFormat(params);
      let self = this;
      queryPager(data).then((res) => {
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
      params = this.paramsForm;
      params.size = this.size;
      params.index = this.index;
      params.isdel = this.isdel;
      this.getPagerList(this.paramsForm); //bin
    },

    /** 点击table行 */
    onSelectTable: function (selection, row) {},

    /** 分页触发 */
    getPager: function (size, index) {
      let params = {};
      let depid = this.paramsForm.deptno;
      params.size = size;
      params.index = index;
      params.isdel = this.isdel;
      this.size = size;
      this.index = index;
      this.getPagerList(params);
    },
    /** 新增 **/
    handleAdd() {
      this.$router.push({
        name: "supplierhmcAdd",
      });
    },
    // 导入模板下载
    handleDownload() {
      const loading = common.loading(this);
      // 此处添加key
      getDownloadUrl({key: "PPM_SUPPLIER_IMPORT"}).then(res => {
        loading.close();
        if(res.data.status === 200) {
          const self = this;
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error('获取下载地址失败')
        }
      })
    },
    /** 导入 */
    changeUpload(file, fileList) {
      console.log("导入", file, fileList);
      var formData = new FormData();
      formData.append('file', file.file);
      let data = formData;
      importRoster(data).then(res => {
        console.log('导入的响应数据', res);
        if(res.data.status === 200) {
          this.$message.success('导入成功');
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
        // let params = {};
        // params.size = this.size;
        // params.index = this.index;
        // params.isdel = this.isdel;
        // this.getPagerList()
        // this.loadInit()
      })
    },
    /** 导出 */
    handleExport() {
      console.log("导出");
      // var searchData = this.paramsForm;
      // let params = common.paramsFormat(searchData)
      const self = this;
      // console.log(params,'1212121')
      exportRoster(this.paramsForm).then(res => {
          console.log(res)
          common.download(res.data, self, '供应商花名册.xls')
      })
    },
    /** 查看价格信息 */
    handlePrice() {
      this.$router.push({
        name: "supplierPriceInfo",
      });
    },
    // 编辑
    handleEdit(row) {
      console.log('编辑', row);
      this.$router.push({
        name: "supplierhmcEdit",
        params: row
      })
    },
    // 删除
    handleDetail(row) {
      this.$confirm('确认删除该条吗?', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {
              // 删除流程
            toDelete({ id: row.id }).then(res => {
              if (res.data.status == 200) {
                this.$message.success('删除成功');
                let params = {}
                params.size = this.size;
                params.index = this.index;
                params.isdel = this.isdel;
                this.getPagerList(params);
              } else {
                this.$message.error(res.data.message);
              } 
            });
          }).catch((e) => {
            console.log(e)
          })
    },
    // 详情
    handleInfo(row) {
      // console.log('详情', row);
      this.$router.push({
        name: "supplierhmcInfo",
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
/* .requireContainer_head {
  float: right;
} */
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


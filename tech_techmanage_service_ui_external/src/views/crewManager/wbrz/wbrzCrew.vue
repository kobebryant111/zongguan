<template>
  <div class="list-container hmcList">
    <!-- 修改后 -->
    <el-form inline>
      <div class="hmcList_head_zb">
        <div class="head-left">
          <el-row>
            <el-col :span="8">
              <el-form-item label="外包工号：">
                <el-input
                  placeholder="工号"
                  size="mini"
                  v-model="paramsForm.badge"
                  style="width: 109%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="外包姓名：">
                <el-input placeholder="姓名" size="mini" v-model="paramsForm.username"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="人员状态：">
                <div>
                  <el-select placeholder="状态" size="mini" v-model="paramsForm.state">
                    <el-option
                      v-for="item in ryzt_options"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    />
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="所属部门：">
                <el-select placeholder="部门" size="mini" v-model="paramsForm.depid" filterable>
                  <el-option
                    v-for="item in depData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="项目经理：">
                <el-input placeholder="项目经理" size="mini" v-model="paramsForm.empname"/>
              </el-form-item>
            </el-col>
            <!-- <el-col :span="8">
              <el-form-item label="在场情况：">
                <el-select placeholder="在场情况" size="mini" v-model="paramsForm.presenceState">
                  <el-option
                    v-for="item in zcState"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col> -->

            <el-col :span="8">
            <div class="searchClass">
            <el-form-item label="入场时间：" label-width="80">
              <el-date-picker
                v-model="paramsForm.entryTime"
                type="daterange"
                size="mini"
                value-format="yyyy-MM-dd"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              />
            </el-form-item>
            </div>
          </el-col>

          </el-row>
          <el-row>
          <!-- <el-col :span="8">
            <div class="searchClass">
            <el-form-item label="入场时间：" label-width="80">
              <el-date-picker
                v-model="paramsForm.entryTime"
                type="daterange"
                size="mini"
                value-format="yyyy-MM-dd"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              />
            </el-form-item>
            </div>
          </el-col> -->
          <el-col :span="8">
            <div class="searchClass">
            <el-form-item label="离场时间：" label-width="80">
              <el-date-picker
                v-model="paramsForm.leavingTime"
                type="daterange"
                size="mini"
                value-format="yyyy-MM-dd"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              />
            </el-form-item>
            </div>
          </el-col>
          <el-col :span="8">
              <el-form-item label="供应商名称：">
                <el-select placeholder="隶属供应商" size="mini" v-model="paramsForm.orgcode" filterable>
                  <el-option
                    v-for="item in supplier_options"
                    :key="item.orgcode"
                    :label="item.outsourcname"
                    :value="item.orgcode"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          <el-col :span="8">
            <el-row type="flex" justify="space-between">
              <el-col :span="15">
                <el-row type="flex" justify="end">
                  <el-col :span="6">
                    <clean-btn :size="sizes" :forms="this.paramsForm" />
                  </el-col>
                  <el-col :span="5.5">
                    <buttonOrange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn" style="margin-left:10px"></buttonOrange>
                  </el-col>
                </el-row>
              </el-col>
              <el-col :span="8"></el-col>
            </el-row>
          </el-col>
        </el-row>
        </div>
      </div>
    </el-form>

    <div class="data-container">
      <div class="data-content-head">
        <div>
          <h3 class="data-content-head-title-h5">{{depName}}</h3>
          <i class="el-icon-user data-content-head-title-i">{{totalNumber}}</i>
        </div>
        <div>
          <!-- <buttonCommon
            v-if="auth != 'common'"
            :buttonName="buttonExport"
            :size="sizes"
            class="buttonExport"
            :fns="handelExport"
          ></buttonCommon> -->
        </div>
        <div>
          <buttonCommon :buttonName="buttonExport" :size="sizes" class="buttonExport"  :fns="handelExport" ></buttonCommon>
          <buttonOrange :buttonName="outAccButton" :size="sizes" :fns="sponsorRlrz"></buttonOrange>
          <!-- <buttonOrange :buttonName="buttonRlrz" :size="sizes" :fns="sponsorRlrz"></buttonOrange> -->
          <!-- <buttonOrange :buttonName="buttonXmrz" :size="sizes" :fns="sponsorXmrz"></buttonOrange> -->
          <!-- <buttonOrange :buttonName="buttonXxbg" :size="sizes" :fns="xxbgSponsor"></buttonOrange> -->
          <buttonOrange :buttonName="buttonXxbg" :size="sizes" :fns="newxxbgSponsor"></buttonOrange>
          <!-- <buttonOrange :buttonName="buttonRyyj" :size="sizes" :fns="sponsorRyyj"></buttonOrange> -->
        </div>
      </div>
      <el-table
        :data="crewData"
        highlight-current-row
        :height="tableHeight"
        @select-all="onSelectTable"
        @select="onSelectTable"
        @row-dblclick="dbTable"
      >
        <el-table-column label="系统编号" prop="badge" width="80"/>
        <el-table-column label="姓名" prop="username" width="90"/>
        <el-table-column label="级别" width="90" show-overflow-tooltip>
          <template slot-scope="scope">
              <span
                v-for="item in level_options"
                :key="item.dictid"
                v-if="item.dictid == scope.row.evaluationLevel"
              >{{item.dictname}}</span>
            </template>
        </el-table-column>
        <el-table-column label="电话" prop="mobiletelphone" width="120" show-overflow-tooltip/>
        <el-table-column label="供应商" prop="outsourcname" width="250" show-overflow-tooltip/>
        <el-table-column label="状态" width="60" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span
              v-for="item in ryzt_options"
              :key="item.dictid"
              v-if="item.dictid == scope.row.state"
            >{{item.dictname}}</span>
          </template>
        </el-table-column>
        <el-table-column label="入场时间" prop="entryTime" width="100" show-overflow-tooltip/>
        <el-table-column label="离场时间" prop="leavingTime" width="100" show-overflow-tooltip/>
        <!-- <el-table-column label="在场情况" width="80" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span
              v-for="item in zcState"
              :key="item.dictid"
              v-if="item.dictid == scope.row.presenceState"
            >{{item.dictname}}</span>
          </template>
        </el-table-column> -->
        <el-table-column label="所属部门" prop="chargeName" width="120" show-overflow-tooltip/>
        <el-table-column label="项目经理" prop="empName" width="80" show-overflow-tooltip/>
        <el-table-column label="操作" width="140">
          <template slot-scope="scope">
            <div class="crew-oper">
              <span @click="onUserDetail(scope.row.badge, scope.row.state) ">离场申请</span>
              <span @click="lookArchives(scope.row.badge)" v-if="scope.row.roleType != 0 ">查看详情</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pager-zb">
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager"/>
      </div>
    </div>
  </div>
</template>
<script>
import { getCrewPager } from "@/api/userApi/bankUser";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon";
import {
  importAchievement,
  importAccessCard,
  exportOutsourcing
} from "@/api/integrated/newWbrz";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { getDicEntry } from "@/api/eosDictEntry";
import {
  queryCrewPage,
  queryRole,
  queryPermission
} from "@/api/integrated/hmcDa";
import { isExist } from "@/api/integrated/wblc";
import { getListAll } from "@/api/common/workflowApi";
export default {
  name: "Crew",
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramsForm: {
        badge: null, // 工号
        empname: null, // 行内负责人
        username: null, // 姓名
        depid: "", //部门编号
        dep: null, //小组编号
        state: "0", //状态
        entryTime: null, //入职时间
        leavingTime: null, //离职时间
        orgcode: "", //供应商编码
        presenceState: null //在场情况
      },
      depName: "金融科技部",
      crewData: [], // 列表分页数据
      fileList: [], //文件上传
      fileList1: [], //文件上传
      peo: "zh",
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      buttonUpdate: "导入/更新",
      buttonExport: "导 出",
      buttonStart: "发起流程",
      buttonXxbg: "信息变更申请",
      outAccButton: "外包人员准入申请",
      // buttonRlrz: "外包人员准入申请",
      // buttonXmrz: "项目准入申请",
      buttonRyyj: "人员移交",
      depData: [], // 部门数据
      ryzt_options: [], // 人员状态
      level_options: [], // 人员级别
      supplier_options: [], // 供应商列表
      tableHeight: null, //表格高度
      auth: "", //查询权限
      zcState: [] //在场情况
    };
  },
  computed: {},
  created() {
    this.getDic();
    this.filDep();
    this.getSupplierList();
    this.getPermission();
    this.tableHeight = window.innerHeight - 283;
  },
  mounted() {
    this.loadInit();
    window.onresize = function() {
      // this.tableHeight = window.innerHeight - (265 * 12) / 100
      this.tableHeight = window.innerHeight - 283;
    }.bind(this);
  },
  methods: {
    /**
          初始化
         */
    init: function(params) {
      let data = common.paramsFormat(params);
      queryCrewPage(data).then(res => {
        var reg = /([^-]+)$/;
        this.crewData = res.data.results.filter(li => {
           if(li.hasOwnProperty("chargeName")){
            li.chargeName = li.chargeName.match(reg)[1];
            if (params.depid !== ""){
              this.depName = li.chargeName;
            }else{
              this.depName = "金融科技部";
            }
          return li;
          }
        });
        this.totalNumber = res.data.total;
      });
    },

    // 查询业务字典
    getDic() {
      getDicEntry({
        dicttypeid: "NEWKG_RYZT,XKG_ZCSTATE,EVALUATION_LEVEL"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //状态
        this.ryzt_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_RYZT";
        });
        // 在场情况
        this.zcState = data.filter(item => {
          return item.dicttypeid == "XKG_ZCSTATE";
        });
        // 级别
        this.level_options = data.filter(item => {
          return item.dicttypeid == "EVALUATION_LEVEL";
        });
        return data;
      });
    },

    /**
     * 分页初始化方法
     */
    loadInit: function() {
      this.init(this.paramsForm);
    },
    /**
     * 搜索事件
     */
    submitBtn: function() {
      let params = {};
      params.badge = this.paramsForm.badge;
      params.username = this.paramsForm.username;
      params.empname = this.paramsForm.empname;
      params.state = this.paramsForm.state;
      params.entryTime = this.paramsForm.entryTime;
      params.leavingTime = this.paramsForm.leavingTime;
      params.presenceState = this.paramsForm.presenceState;
      let depid = this.paramsForm.depid;
      params.depid = depid.replace("dep", "");
      params.dep = this.paramsForm.dep;
      params.orgcode = this.paramsForm.orgcode;
      this.init(params);
    },

    /**
     * 点击table行
     */
    onSelectTable: function(selection, row) {},
    // 导入成绩
    changeZxFile(file, fileList) {
      var formData = new FormData();
      formData.append("file", file.file);
      let data = formData;
      importAchievement(data).then(res => {
        if (res.data.status == "200") {
          this.$message.success("导入成功");
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    // 导入门禁卡编号
    changeAccessCard(file, fileList1) {
      var formData = new FormData();
      formData.append("file", file.file);
      let data = formData;
      importAccessCard(data).then(res => {
        if (res.data.status == "200") {
          this.$message.success("导入成功");
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params.badge = this.paramsForm.badge;
      params.username = this.paramsForm.username;
      params.empname = this.paramsForm.empname;
      params.state = this.paramsForm.state;
      params.entryTime = this.paramsForm.entryTime;
      params.leavingTime = this.paramsForm.leavingTime;
      params.presenceState = this.paramsForm.presenceState;
      let depid = this.paramsForm.depid;
      params.depid = depid.replace("dep", "");
      params.dep = this.paramsForm.dep;
      params.orgcode = this.paramsForm.orgcode;
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      this.init(params);
    },

    // 获取部门
    filDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          let fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter(tt => {
            if (tt.text !== "宁银消金金融科技部-总经理室"){
              tt.text = tt.text.replace(name + "-", "");
            tt.id = tt.id.replace("dep", "");
            return tt;
            }

          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    /** 查询外包商 */
    getSupplierList() {
      getListAll()
        .then((res) => {
          this.supplier_options = res.data.results;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },

    // 离场跳转
    onUserDetail: function(jobid, state) {
      this.lcIsEx(jobid, state);
    },

    // 获取部门
    lcIsEx(jobid, state) {
      // console.log("state->",state);
      const loading = common.loading(this);
      let params = {};
      params.jobid = jobid;
      let data = common.paramsFormat(params);
      isExist(data)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            if (res.data.message == "true") {
              this.$message.error("流程已存在");
            } else if (res.data.message == "false") {
              if (state == 0) {
                this.$router.push({
                  name: "wbrylc",
                  query: {
                    formScene: "wblcApply",
                    jobid: jobid
                  }
                });
              } else if (state == 1) {
                this.$message.error("已离岗");
              } else if (state == 2) {
                this.$message.warning("待入场状态");
              }
            }
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch(error => {
          this.$message.error("获取部门失败");
        });
    },

    // 导出
    handelExport: function() {
      let searchData = {};
      searchData.badge = this.paramsForm.badge;
      searchData.username = this.paramsForm.username;
      searchData.empname = this.paramsForm.empname;
      searchData.state = this.paramsForm.state;
      searchData.entryTime = this.paramsForm.entryTime;
      searchData.leavingTime = this.paramsForm.leavingTime;
      searchData.presenceState = this.paramsForm.presenceState;
      let depid = this.paramsForm.depid;
      searchData.depid = depid.replace("dep", "");
      searchData.dep = this.paramsForm.dep;
      searchData.orgcode = this.paramsForm.orgcode;
      let params = common.paramsFormat(searchData);
      const self = this;
      exportOutsourcing(params).then(res => {
        common.download(res.data, self, "外包花名册.xlsx");
      });
    },

    // 获取权限
    getPermission(id) {
      queryPermission().then(res => {
        if (res.data.status == 200) {
          this.auth = res.data.results;
        } else {
          this.$message.error("查询权限失败");
        }
      });
    },

    // 查看档案
    lookArchives(v) {
      this.$router.push({
        name: "hmcArchives",
        query: {
          param: v
        }
      });
    },

    // 外包入职发起流程(人力)
    sponsorRlrz() {
      this.$router.push({
        name: "wbryzr",
        query: {
          formScene: "wbrzAdd",
          char: 0
        }
      });
    },

    // 外包入职发起流程(项目)
    sponsorXmrz() {
      this.$router.push({
        name: "wbryzr",
        query: {
          formScene: "wbrzAdd",
          char: 1
        }
      });
    },

    // 新信息变更发起
    newxxbgSponsor() {
      this.$router.push({
        name: "xxbgsq",
        query: {
          formScene: "xxbgApply"
        }
      });
    },

    // 人员移交发起
    sponsorRyyj() {
      this.$router.push({
        name: "ryyjsq",
        query: {
          formScene: "ryyjApply"
        }
      });
    },

    // 双击表格
    dbTable(val) {
      if (val.roleType != 0) {
        this.lookArchives(val.badge);
    }
    }
  }
};
</script>
<style scoped>
.hmcList {
  background: #f0f2f5;
}

.top {
  background: #ffffff;
  padding: 15px;
  margin-bottom: 15px;
}
.hmcList_head_zb {
  background: #ffffff;
  padding: 15px;
  margin-bottom: 15px;
  display: -webkit-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
  -webkit-box-pack: justify;
  -ms-flex-pack: justify;
  -webkit-justify-content: space-between;
  justify-content: space-between;
}

.head-left {
  flex: 1;
}

.head-right {
  display: -webkit-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  padding-right: 15px;
  width: 145px;
  display: flex;
}

.head-right-clean {
  flex: 1;
}

.head-right-search {
  padding-left: 10px;
  flex: 1;
}

.buttonExport {
  margin-right: 10px;
}

.buttonXmrz {
  margin: 0 10px;
}

.data-container {
  background: #ffffff;
  padding: 15px 15px 27px 15px;
}

.data-content-head {
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.data-content-head-title-h5 {
  margin-right: 8px;
  display: inline;
}

.data-content-head-title-i {
  color: #909399;
}

.crew-oper span {
  color: rgba(251, 164, 47, 1);
  cursor: pointer;
}
</style>

<style>
/* el-input修改 */
.hmcList .el-input--prefix .el-input__inner {
  padding-left: 50px;
}

.hmcList .el-input__prefix {
  left: 8px;
  color: #262626;
  font-size: 14px;
}

/* 在场情况 */
.hmc-head-left-zcqk .el-input--prefix .el-input__inner {
  padding-left: 72px;
}

/* 行内负责人输入框 */
.fuzheng_zb .el-input--prefix .el-input__inner {
  padding-left: 90px;
}

/* element表单修改 */
.hmcList .el-form-item {
  margin-bottom: 0px;
}

/* 警告按钮 */
.buttonOrange .el-button:focus {
  color: #ffffff;
  background-color: #ebb563;
  border-color: #ebb563;
}

.buttonOrange .el-button:hover {
  color: #ffffff;
  background-color: #ebb563;
  border-color: #ebb563;
}

.buttonOrange .el-button {
  background-color: rgba(251, 164, 47, 1);
  border-color: rgba(251, 164, 47, 1);
}
.searchClass .el-form-item__content {
  width: 65% !important;
}
</style>

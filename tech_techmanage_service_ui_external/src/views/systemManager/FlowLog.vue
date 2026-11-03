<!-- 流程日志维护 -->
<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="flowForm" inline>
        <el-form-item prop="flowdraftno" label="流程名称">
          <el-input placeholder v-model="flowForm.flowdraftno"/>
        </el-form-item>
        <el-form-item prop="flowcaseno" label="流程id">
          <el-input placeholder v-model="flowForm.flowcaseno"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch(size, index)">搜索</el-button>
          <clean-btn :forms="this.flowForm"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="button-container">
      <el-button type="primary" class="el-icon-plus" @click="handelToAdd">添加</el-button>
      <el-button type="warning" class="el-icon-edit" @click="onShowUpdate()">修改</el-button>
      <el-button type="danger" class="el-icon-delete" @click="onDelete()">删除</el-button>
      <el-button type="warning" class="el-icon-plus" @click="onToHis()">转历史</el-button>
    </div>
    <div class="data1-container">
      <el-table ref="multipleTable" :data="FlowLogData" stripe highlight-current-row border>
        <el-table-column type="selection" width="55"/>
        <el-table-column width="80" prop="flowcaseno" label="流程id" align="center"/>
        <el-table-column width="80" prop="flowstatus" label="流程状态" align="center"/>
        <el-table-column width="200" prop="flowdraftno" label="流程实例名称" align="center"/>
        <el-table-column width="150" label="处理人" align="center">
          <template slot-scope="scope">
            {{scope.row.userName}}
            <label v-if="scope.row.isfalse === '01'">(处理中)</label>
          </template>
        </el-table-column>
        <el-table-column width="150" prop="tel" label="联系方式" align="center"/>
        <el-table-column width="300" prop="deptname" label="部门" align="center"/>
        <el-table-column width="150" prop="applydesc" label="流程节点" align="center"/>
        <el-table-column width="100" prop="conclusion" label="选择决策" align="center">
          <template slot-scope="scope">
            <span v-html="conclusionrender(scope.row.conclusion,clData)"></span>
          </template>
        </el-table-column>
        <el-table-column width="180" prop="suggest" label="处理意见" align="center"/>
        <el-table-column
          width="160"
          prop="createtime"
          label="提交时间"
          align="center"
          :formatter="dataFormat"
        />
        <el-table-column width="150" prop="hisType" label="是否历史" align="center"/>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"></pager>
    </div>
    <!-- 添加 修改 dialog-->
    <div>
      <el-dialog
        width="45%"
        :title="title"
        :visible.sync="visibleClose"
        v-if="visibleClose"
        :before-close="onCancel"
      >
        <el-form v-model="logForm" ref="logForm">
          <table width="100%" border="0" class="jtk-table">
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>流程实例名称：
              </td>
              <td class="td-left">
                <el-input type="text" v-model="logForm.flowdraftno"/>
              </td>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>流程id：
              </td>
              <td class="td-left">
                <el-input type="text" v-model="logForm.flowcaseno"/>
              </td>
            </tr>

            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>审批人：
              </td>
              <td class="td-left">
                <el-input
                  v-model="logForm.username"
                  placeholder="审批人"
                  suffix-icon="el-icon-search"
                  readonly
                  @focus="showUserDialog"
                />
              </td>
              <td class="td-right">提交时间：</td>
              <td class="td-left">
                <el-date-picker type="datetime" v-model="logForm.createtime" placeholder="选择日期时间"/>
              </td>
            </tr>
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>部门：
              </td>
              <td class="td-left">
                <el-input type="text" v-model="logForm.deptname" disabled="disabled"/>
              </td>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>联系方式：
              </td>
              <td class="td-left">
                <el-input type="text" v-model="logForm.tel" disabled="disabled"/>
              </td>
            </tr>
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>选择决策：
              </td>
              <td class="td-left">
                <el-select
                  v-model="logForm.conclusion"
                  placeholder="请选择"
                  :value-key="logForm.conclusion"
                >
                  <el-option
                    v-for="item of clData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
              <td class="td-right">意见：</td>
              <td class="td-left">
                <el-input type="text" v-model="logForm.suggest"/>
              </td>
            </tr>
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>审批节点：
              </td>
              <td class="td-left">
                <el-input type="text" v-model="logForm.applydesc"/>
              </td>
              <td class="td-right">处理中：</td>
              <td class="td-left">
                <el-select v-model="logForm.isfalse" placeholder="请选择" :value-key="logForm.isfalse">
                  <el-option
                    v-for="item of isData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </td>
            </tr>
            <tr>
              <td class="td-right">历史类型：</td>
              <td class="td-left">
                <el-select v-model="logForm.hisType" placeholder="请选择" :value-key="logForm.hisType">
                  <el-option
                    v-for="item of typeData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </td>
            </tr>
          </table>
          <div class="dialog-buttion-container">
            <el-button type="primary" @click="onSave()">确定</el-button>
            <el-button @click="onCancel()">取消</el-button>
          </div>
        </el-form>
        <!-- dialog begin -->
      </el-dialog>
      <div class="dialog-user-container-div">
        <template>
          <el-dialog title="用户列表" :visible.sync="dialogVisible" width="85%" top="10vh">
            <employee :roleData="dialogUserData" :selectedRow="getDialogUserRow" ref="employee"></employee>
            <div>
              <el-button type="primary" @click="selectedUser">确定</el-button>
              <el-button @click="dialogCancel">取消</el-button>
            </div>
          </el-dialog>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import moment from "moment";
import employee from "./common/Employee";
import { getDicEntry } from "@/api/eosDictEntry";
import {
  getPagerData,
  getDateil,
  doUpdate,
  doDelete,
  addToHis,
  doHisUpdate,
  doHisDelete
} from "@/api/flowlog";
import eosSelect from "@/components/EosSelect";
import cleanBtn from "@/components/CleanBtn";
export default {
  components: {
    pager,
    employee,
    eosSelect,
    cleanBtn
  },
  data() {
    return {
      totalNumber: 0,
      size: 10, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      flowForm: {
        //查询表单
        flowdraftno: "",
        flowcaseno: ""
      },
      logForm: {
        // 添加||修改表单
        style: "",
        deptname: "",
        deptid: "",
        id: ""
      },
      FlowLogData: [], // 列表数据
      clData: [], // 选择决策数据
      isData: [{ id: "", text: "" }, { id: "01", text: "是" }], //
      dialogUserData: [], // dialog 用户数据
      visibleClose: false, // dialog 是否关闭
      dialogVisible: false, // dialog 显示 默认不显示
      title: "", // dialog 标题
      eosClear: false, // eos-select 是否清空
      typeData: [{ id: "历史", text: "历史" }, { id: "非历史", text: "非历史" }]
    };
  },
  mounted() {
    this.packageInit();
  },
  methods: {
    /**
     * 初始化
     */
    init: function(param) {
      console.log("param", param);
      let data = common.paramsFormat(param);
      getPagerData(data).then(res => {
        this.FlowLogData = res.data.results;
        console.log("FlowLogData", this.FlowLogData);
        this.totalNumber = res.data.total;
      });
    }, // 初始 end
    dataFormat: function(row, column) {
      var date = row[column.property];
      if (date == undefined) return "";
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    },
    packageInit: function() {
      let param = {};
      param.size = this.size;
      param.index = this.index;
      this.init(param);
      // 业务字典查询
      getDicEntry({
        dicttypeid: "GLOBAL_FLOW_DECISION"
      })
        .then(res => {
          if (res.data.status !== 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          let data = res.data.results;
          this.initData = data;
          // 选择决策数据
          this.clData = data.filter(item => {
            return item.dicttypeid == "GLOBAL_FLOW_DECISION";
          });
        })
        .catch(err => {
          this.$message.error("连接超时，请刷新页面");
        }); // 业务字段查询 end

      // dialog 用户数据
      request({
        url: "/commOrganization/queryOrgTree",
        method: "get"
      }).then(res => {
        this.dialogUserData = res.data.results;
      });
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params.flowdraftno = this.flowForm.flowdraftno;
      params.flowcaseno = this.flowForm.flowcaseno;
      params.size = size;
      params.index = index;
      this.init(params);
    },
    /**
     * 搜索
     */
    onSearch: function(size, index) {
      let params = {};
      params.flowdraftno = this.flowForm.flowdraftno;
      params.flowcaseno = this.flowForm.flowcaseno;
      params.size = size;
      params.index = index;
      this.init(params);
    },
    conclusionrender: (obj, clData) => {
      let name = obj;
      if (clData.length > 0) {
        for (let i = 0; i < clData.length; i++) {
          if (name == clData[i].dictid) {
            name = clData[i].dictname;
          }
        }
      }
      return name;
    },
    /*createtimerender:(obj)=>{
        let time = obj;
        
        if(clData.length>0){
            for(let i=0;i<clData.length;i++){
                if(name == clData[i].dictid){
                    name = clData[i].dictname;
                }
            }
        }
        return name;
    },*/
    /**
     * 显示添加dialog
     */
    handelToAdd: function() {
      this.visibleClose = true;
      this.eosClear = true;
      this.title = "添加";
    }, // 显示添加dialog end
    /**
     * dialog 确定事件
     */
    onSave: function() {
      let falg = this.vaildate();
      if (!falg) {
        return false;
      }
      //  this.logForm.lastmodtime = new Date();
      //  this.logForm.examine = new Date();

      //  if(typeof this.logForm.createtime =="string"){
      //    this.logForm.createtime = new Date(this.logForm.createtime);
      //  }
      //alert(typeof this.logForm.createtime);
      let params = common.paramsFormat(this.logForm);
      console.log("logForm", this.logForm);
      if ("历史" === this.logForm.hisType) {
        doHisUpdate(params).then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
          } else {
            this.$message.success(res.data.message);
            this.onSearch(10, 1);
            this.visibleClose = false;
          }
        });
      } else {
        doUpdate(params).then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
          } else {
            this.$message.success(res.data.message);
            this.onSearch(10, 1);
            this.visibleClose = false;
          }
        });
      }
    }, // dialog 确定事件 end
    /**
     * 表单验证
     */
    vaildate: function() {
      if (this.logForm.flowdraftno == "" || this.logForm.flowdraftno == null) {
        this.$message.error("flowid不能为空");
        return false;
      }
      if (this.logForm.userid == "" || this.logForm.userid == null) {
        this.$message.error("审批人不能为空");
        return false;
      }
      if (this.logForm.flowcaseno == "" || this.logForm.flowcaseno == null) {
        this.$message.error("流程id不能为空");
        return false;
      }
      if (this.logForm.conclusion == "") {
        this.$message.error("选择决策不能为空");
        return false;
      }
      if (this.logForm.suggest == "" || this.logForm.suggest == null) {
        this.$message.error("意见不能为空");
        return false;
      }
      return true;
    }, // 表单验证 end
    /**
     * dialog 取消事件
     */
    onCancel: function() {
      common.cleanFrom(this.logForm);
      this.visibleClose = false;
      //this.eosClear = false;
      this.title = "";
      this.onSearch(10, 1);
    }, // dialog 取消事件 end
    /**
     * 显示用户信息
     */
    showUserDialog: function() {
      this.dialogVisible = true;
    }, // 显示用户信息 end
    /**
     *  选中dialog 用户 表行
     */
    getDialogUserRow: function(row, column, event) {
      this.dialogBadge = row.badge;
      this.dialogDeptname = row.chargeName;
      this.dialogTel = row.mobiletelphone;
      this.dialogName = row.empname;
      this.dialogDeptno = row.depid;
    }, // 选中dialog 用户 表行 end
    /**
     * 选择dialog 用户
     */
    selectedUser: function() {
      this.logForm.userid = this.dialogBadge;
      this.logForm.username = this.dialogName + "(" + this.dialogBadge + ")";
      this.logForm.deptname = this.dialogDeptname;
      this.logForm.tel = this.dialogTel;
      this.logForm.deptno = this.dialogDeptno;
      this.dialogCancel();
      //this.logForm.depName = this.dialogName;
    }, // 选择dialog 用户 end
    /**
     *
     */
    dialogCancel: function() {
      this.dialogVisible = false;
      let userForm = this.$refs.employee.paramForm;
      Object.keys(userForm).forEach(key => {
        userForm[key] = "";
      });
    },
    /**
     * 更新
     */
    onShowUpdate: function() {
      var logdata = this.$refs.multipleTable.selection;
      if (logdata.lenght > 1) {
        this.$message.error("只能勾选一条数据");
        return false;
      }

      var logdata = this.$refs.multipleTable.selection;
      console.log('logdata::', logdata)
      this.title = "修改";
      let item = logdata[0];
      if ("历史" === item.hisType) {
        this.logForm = item;
      } else {
        var id = logdata[0].id;
      var flowid = logdata[0].flowid;
        getDateil({ id: id }).then(res => {
          if (res.data.status != 200) {
            this.$message.error(res.data.message);
            return false;
          }
          console.log(res.data.results);
          this.logForm = res.data.results;
          this.logForm.flowid = flowid;
        });
      }

      this.visibleClose = true;
    },
    /**
     * 删除
     */
    onDelete: function() {
      var id = "";
      var logdata = this.$refs.multipleTable.selection;
      for (var i = 0; i < logdata.length; i++) {
        id += logdata[i].id + ",";
      }
      if (id.indexOf(",") > -1) {
        id = id.substring(0, id.length - 1);
      }

      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        if ("历史" === this.logForm.hisType) {
          doHisDelete({ ids: id }).then(res => {
            if (res.data.status != 200) {
              this.$message.error(res.data.message);
              return false;
            } else {
              this.$message.success(res.data.message);
              this.onSearch(10, 1);
            }
          });
        } else {
          doDelete({ ids: id }).then(res => {
            if (res.data.status != 200) {
              this.$message.error(res.data.message);
              return false;
            } else {
              this.$message.success(res.data.message);
              this.onSearch(10, 1);
            }
          });
        }
      });
    }, // end
    /**
     * 一键历史
     */
    onToHis() {
      var logdata = this.$refs.multipleTable.selection;
      if (logdata.lenght > 1) {
        this.$message.error("至少勾选一条数据");
        return false;
      }
      let data = {};
      data.flowcaseno = logdata[0].flowcaseno;
      let params = common.paramsFormat(data);
      addToHis(params).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message.success(res.data.message);
      });
    } // end
  }
};
</script>
<style lang="scss" scoped>
.button-container {
  margin-bottom: 10px;
}

.jkt-input {
  width: 40%;
}
.jkt-tag-red {
  color: red;
}
.dialog-buttion-container {
  margin-top: 10px;
  text-align: right;
}
.excel-import {
  float: left;
  margin: 0 10px 0 3px;
}
</style>


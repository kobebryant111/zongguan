<template>
  <div class="employee-container">
    <div class="param-form-container">
      <el-form :model="paramForm" inline>
        <el-form-item prop="badge" label="员工工号">
          <el-input v-model="paramForm.badge" placeholder="员工工号"/>
        </el-form-item>
        <el-form-item prop="empname" label="人员名称">
          <el-input v-model="paramForm.empname" placeholder="人员名称"/>
        </el-form-item>
        <el-form-item label="所属机构">
          <el-input
            v-model="paramForm.compName"
            placeholder="所属机构"
            suffix-icon="el-icon-search"
            @focus="showOrg"
          />
          <organization :roleData="roleData" ref="organization" :fns="getOrgNode"></organization>
        </el-form-item>
        <el-form-item label="所属部门">
          <el-input
            v-model="paramForm.depName"
            placeholder="所属部门"
            suffix-icon="el-icon-search"
            readonly
            @focus="showDep"
          />
          <department ref="department" :fns="getDeptNode" ></department>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
        <el-form-item>
          <el-button @click="addEmploy" icon="el-icon-plus" :disabled="multiple" type="primary">添加</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addEmployeeAll">添加所有</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table stripe :data="employData" fit border height="400" highlight-current-row stripe @row-click="selectedRow" 
                @selection-change="handleSelectionChange" :row-key="getRowkey" ref="employ">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="员工工号" prop="badge" align="center" ></el-table-column>
        <el-table-column label="人员名称" prop="empname" align="center"></el-table-column>
        <el-table-column label="员工手机" prop="mobiletelphone" align="center"></el-table-column>
        <el-table-column label="姓名全称" prop="fullname" align="center"></el-table-column>
        <el-table-column label="姓名简称" prop="spellname" align="center"></el-table-column>
        <el-table-column label="所属机构" prop="compName" align="center"></el-table-column>
        <el-table-column label="所属部门" prop="chargeName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="所属岗位" prop="comorgName" align="center"></el-table-column>
        <!-- <el-table-column label="所属条线" prop="comorgName" align="center"></el-table-column> -->
        <!-- <el-table-column label="职务" prop="comorgName" align="center"></el-table-column> -->
      </el-table>
      <el-row type="flex" justify="space-between">
        <el-col :span="12">
          <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
        </el-col>
        <el-col :span="4">
          <el-button :disabled="resetPwdVisible" @click="resetPwd" style="margin: 10px 0 0 0" :style="{ display: resetPwdVisible?'none':''}">密码重置</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import organization from "@/views/plugins/systemPlug/Organization";
import department from "@/views/plugins/systemPlug/Department";
import cleanBtn from '@/components/CleanBtn'
import { resetUserPwd } from '@/api/login';
export default {
  components: { pager, organization, department,cleanBtn },
  props: {
    roleData: {
      type: [Array, Object],
      required: true
    },
    resetPwdVisible : {
      type: Boolean,
      default: false
    },
    roleIdOld: {
      type: String
    }
  },
  data() {
    return {
      single: true,
      multiple: true,
      ids: [],
      roleId: "",
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      nodeKey: "rolId",
      paramForm: {
        badge: "",
        empname: "",
        compid: '',
        depid: "",
        depName: "",
        compName: ""
      },
      employData: [],
      dialogVisible: false, // dailog 是否显示
      selectedRowUserId: "",
      // resetPwdVisible: true
    };
  },
  mounted() {
    this.init({ size: this.size, index: this.index });
  },
  methods: {
    // 初始化
    init: function(param) {
      let data = common.paramsFormat(param);
      request({
        url: "/commEmployee/queryResearchManagerEmployee",
        method: "post",
        data: data
      }).then(res => {
        this.employData = res.data.results;
        this.totalNumber = res.data.total;
      });
    },

    getRowkey(row) {
      return row.id;
    },
   

    handleSelectionChange(selection){
      this.ids = selection.map(item => item.badge).join(); 
      this.roleId = this.roleIdOld;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    addEmploy() {
      request({
        url:
          "/imUserRoleRel/addOperaUserRoleBatch?ids=" +
          this.ids+
          "&roleId=" +
          this.roleIdOld,
        method: "post"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message({
          message: res.data.message,
          type: "success",
          center: "true"
        });
        //将勾选框置空
        this.ids = [];
      })
      .catch(_ => { });
      this.$refs.employ.clearSelection();
    },
      
      
    // 分页触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.badge = this.paramForm.badge;
      params.empname = this.paramForm.empname;
      params.compid = this.paramForm.compid;
      params.depid = this.paramForm.depid;
      this.init(params);
    },
    // 搜索
    submitBtn: function() {
      let params = {};
      // params.size = this.size;
      // params.index = this.index;
      params.badge = this.paramForm.badge;
      params.empname = this.paramForm.empname;
      params.compid = this.paramForm.compid;
      params.depid = this.paramForm.depid;
      this.init(params);
    },

    addEmployeeAll() {
      request({
        url: "/imUserRoleRel/addOperaUserRoleAll?badge="+this.paramForm.badge + 
        "&empname=" + this.paramForm.empname + 
        "&compid=" + this.paramForm.compid +
        "&roleId=" + this.roleIdOld +
        "&depid=" + this.paramForm.depid ,
        method: "post"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message({
          message: res.data.message,
          type: "success",
          center: "true"
        });
      });
    },

    // 机构列表
    showOrg: function() {
      this.$refs.organization.falg = true;
      this.$refs.organization.filterText = "";
    },
    // 部门列表
    showDep: function() {
      if (this.paramForm.compid ) {
        this.$refs.department.dialogClose = true;
        this.$refs.department.filterText = "";
        this.$refs.department.init({'compid' : this.paramForm.compid,'size':this.$refs.department.size,'index':this.$refs.department.index});
      }
    },
    // 获取机构节点信息
    getOrgNode: function(node) {
      this.paramForm.compName = node.location;
      this.paramForm.compid = node.compid;
      this.$refs.department.init({'compid' : node.compid,'size':this.$refs.department.size,'index':this.$refs.department.index});
    },
    // 获取部门节点信息
    getDeptNode: function(node) {
      this.paramForm.depName = node.location;
      this.paramForm.depid = node.depid;
    },
    //选中行
    selectedRow(row,column,event) {
        console.log('selectedRow', row,column,event);
        this.selectedRowUserId = row.badge
        this.$emit('childevent',this.selectedRowUserId)
    },
    // 重置密码
    resetPwd() {
      console.log('重置密码', this.selectedRowUserId);
      if (this.selectedRowUserId === ''){
        this.$message({
          type: "warning",
          message: "请选择用户"
        })
        return
      }

      this.$confirm("该操作将重置选中角色密码，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        let params = {};
        params.userid = this.selectedRowUserId
        resetUserPwd(params).then(res => {
          console.log('1111111111111', res);
          if(res.data.status == 200) {
            this.$message({
              type: "success",
              message: "重置成功"
            })
          } else {
            this.$message.error(res.data.message);
          }
        })
      }).catch(() => {
        this.$message({
          type: "info",
          message: "取消重置"
        })
      })
    }
  }
};
</script>
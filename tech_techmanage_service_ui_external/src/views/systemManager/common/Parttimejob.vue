<template>
  <div class="parttimejob-contariner">
    <div class="param-form-container">
      <el-form :model="paramForm" inline>
        <el-form-item prop="badge" label="人员员工">
          <el-input v-model="paramForm.badge" placeholder="人员员工" />
        </el-form-item>
        <el-form-item prop="empname" label="人员名称">
          <el-input v-model="paramForm.empname" placeholder="人员名称" />
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
          <department ref="department" :fns="getDeptNode"></department>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn" size="mini">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <!-- table -->
    <div>
      <el-table :data="partjobData" fit border height="400">
        <el-table-column label="人员工号" prop="badge" align="center"></el-table-column>
        <el-table-column label="人员名称" prop="empname" align="center"></el-table-column>
        <el-table-column label="兼职机构" prop="orgName" align="center"></el-table-column>
        <el-table-column label="兼职部门" prop="depName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="兼职岗位" prop="jobName" align="center"></el-table-column>
        <el-table-column label="兼职开始时间" prop="begindate" align="center" :formatter="beginDateFormat"></el-table-column>
        <el-table-column label="兼职结束时间" prop="enddate" align="center" :formatter="endDateFormat"></el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
  </div>
</template>
<script>
import request from "@/utils/request";
import common from "@/api/common";
import pager from "@/components/Pager";
import organization from "@/views/plugins/systemPlug/Organization";
import department from "@/views/plugins/systemPlug/Department";
import cleanBtn from '@/components/CleanBtn'
export default {
    name : 'Parttimejob',
    components : {pager,organization,department,cleanBtn},
    props : {
        roleData : {
            type : [Array,Object],
            required : true
        }
    },
    data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramForm: {
        badge: "",
        empname: "",
        compid: -1,
        depid: "",
        depName: "",
        compName: ""
      },
      partjobData: [],
      dialogVisible: false, // dailog 是否显示
      clearable : false
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
        url: "/commParttimejob/queryPager",
        method: "post",
        data: data
      }).then(res => {
        this.partjobData = res.data.results;
        this.totalNumber = res.data.total;
      });
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
      params.size = this.size;
      params.index = this.index;
      params.badge = this.paramForm.badge;
      params.empname = this.paramForm.empname;
      params.compid = this.paramForm.compid;
      params.depid = this.paramForm.depid;
      this.init(params);
    },
    // 机构列表
    showOrg: function() {
      this.$refs.organization.falg = true;
      this.$refs.organization.filterText = "";
    },
    // 部门列表
    showDep: function() {
      if (this.paramForm.compid && this.paramForm.compid != '-1') {
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
    // 时间格式化
    beginDateFormat : function(row,column){ 
      let str  = common.dateFormat(row.begindate,'yyyy-mm-dd');
      return str;
    },
    // 时间格式化
    endDateFormat: function(row,column){
      let str1  = common.dateFormat(row.enddate,'yyyy-mm-dd');
      return str1;
    },
  }
};
</script>

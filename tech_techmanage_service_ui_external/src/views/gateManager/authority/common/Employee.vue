<template>
  <div class="employee-container">
    <div class="param-form-container">
      <el-form :model="paramForm" inline>
        <el-form-item prop="badge" label="工号">
          <el-input v-model="paramForm.badge" placeholder="工号"/>
        </el-form-item>
        <el-form-item prop="empname" label="名称">
          <el-input v-model="paramForm.empname" placeholder="名称"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table :data="employData" fit border height="400" @selection-change="selectedRows" highlight-current-row stripe >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="工号" prop="badge" align="center" ></el-table-column>
        <el-table-column label="名称" prop="empname" align="center"></el-table-column>
        <el-table-column label="手机号" prop="mobiletelphone" align="center"></el-table-column>
        <el-table-column label="所属机构" prop="compName" align="center"></el-table-column>
        <el-table-column label="所属部门" prop="chargeName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="所属岗位" prop="comorgName" align="center"></el-table-column> 
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import cleanBtn from '@/components/CleanBtn'
export default {
  components: { pager,cleanBtn },
  props: {     
    userData: {
      type: [Object],
      required: true
    },
    //选中行
    selectedRows : {
      type : Function
    }
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      employData: [], 
      depid: this.userData.depid,
      paramForm: {
        depid: "",
        badge: "",
        empname: ""
      }
    };
  },
  mounted() {
    let params = {};
    params.size = this.size;
    params.index = this.index; 
    params.depid = this.depid;
    this.init(params);
  },
  methods: {
    // 初始化
    init: function(param) {
      let data = common.paramsFormat(param);
      request({
        url: "/commEmployee/pageList",
        method: "post",
        data: data
      }).then(res => {
        var reg = /([^-]+)$/;
        this.employData = res.data.results.filter(li => {
            li.chargeName = li.chargeName.match(reg)[1];
            return li;
        });
        this.totalNumber = res.data.total;
      });
    },
    // 分页触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.depid = this.depid;
      params.badge = this.paramForm.badge;
      params.empname = this.paramForm.empname;
      this.init(params);
    },
    // 搜索
    submitBtn: function() {
      let params = {}; 
      params.depid = this.depid;
      params.badge = this.paramForm.badge;
      params.empname = this.paramForm.empname;
      this.init(params);
    }
  }
};
</script>
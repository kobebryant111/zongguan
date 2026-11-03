<template>
  <div class="app-container">
    <!-- 查询区域 -->
    <div class="param-form-container">
      <el-form ref="paramForm" :model="paramForm" inline>
        <el-form-item label="用户号" prop="badge">
          <el-input placeholder="用户号" v-model="paramForm.badge"/>
        </el-form-item>
        <el-form-item label="用户名称" prop="operatorname">
          <el-input placeholder="用户名称" v-model="paramForm.operatorname"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <!-- 查询区域 end -->
    <!-- table -->
    <el-table :data="userData" fit border height="560" max-height="560">
      <el-table-column label="用户号" prop="userid" align="center"></el-table-column>
      <el-table-column label="用户名称" prop="operatorname" align="center"></el-table-column>
      <el-table-column label="角色编号" prop="roleid" align="center"></el-table-column>
      <el-table-column label="角色名称" prop="rolename" align="center"></el-table-column>
      <el-table-column label="是否兼职" prop="dictname" align="center"></el-table-column>
    </el-table>
    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import common from "@/api/common";
import request from "@/utils/request";
import cleanBtn from '@/components/CleanBtn'
export default {
  components: { pager,cleanBtn },
  data() {
    return {
      size: 50,
      index: 1,
      totalNumber: 0,
      userData: [],
      paramForm: {
        operatorname: "",
        badge: ""
      }
    };
  },
  mounted() {
    this.init({
      size: this.size,
      index: this.index
    });
  },
  methods: {
    // 初始化
    init: function(param) {
      let data = common.paramsFormat(param);
      request({
        url: "/commOperator/queryPager",
        method: "post",
        data: data
      }).then(res => {
        this.userData = res.data.results;
        this.totalNumber = res.data.total;
      });
    },
    // 分页触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.operatorname = this.paramForm.operatorname;
      params.badge = this.paramForm.badge;
      this.init(params);
    },
    // 搜索
    submitBtn: function() {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      params.operatorname = this.paramForm.operatorname;
      params.badge = this.paramForm.badge;
      this.init(params);
    }
  }
};
</script>

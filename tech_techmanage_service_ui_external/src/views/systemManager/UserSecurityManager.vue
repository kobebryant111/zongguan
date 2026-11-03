<template>
  <div class="app-container">
    <!-- 查询区域 -->
    <div class="param-form-container">
      <el-form ref="paramForm" :model="paramForm" inline>
        <el-form-item label="用户号" prop="userId">
          <el-input placeholder="用户号" v-model="paramForm.userId"/>
        </el-form-item>
        <el-form-item label="用户名称" prop="operatorName">
          <el-input placeholder="用户名称" v-model="paramForm.operatorName"/>
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
      <el-table-column label="用户号" prop="userId" align="center"></el-table-column>
      <el-table-column label="用户名称" prop="operatorName" align="center"></el-table-column>
      <el-table-column label="所属机构" prop="orgName" align="center"></el-table-column>
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
        operatorName: "",
        userId: ""
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
        url: "/commRole/queryPager",
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
      params.operatorName = this.paramForm.operatorName;
      params.userId = this.paramForm.userId;
      this.init(params);
    },
    // 搜索
    submitBtn: function() {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      params.operatorName = this.paramForm.operatorName;
      params.userId = this.paramForm.userId;
      this.init(params);
    }
  }
};
</script>
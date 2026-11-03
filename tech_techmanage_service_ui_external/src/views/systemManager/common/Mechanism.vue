<template>
  <div class="mechanism-container">
    <!-- 查询区域 -->
    <div class="param-form-container">
      <el-form ref="paramForm" :model="paramForm" inline>
        <el-form-item label="机构编号(十位)" prop="compcode">
          <el-input placeholder="机构编号(十位)" v-model="paramForm.compcode"/>
        </el-form-item>
        <el-form-item label="机构名称" prop="location">
          <el-input label="机构名称" v-model="paramForm.location"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <!-- 查询区域 end -->
    <!-- table -->
    <el-table :data="orgData" fit border height="450" max-height="480">
      <el-table-column label="机构" prop="compid" align="center"></el-table-column>
      <el-table-column label="机构编号(十位)" prop="compcode" align="center"></el-table-column>
      <el-table-column label="机构名称" prop="location" align="center"></el-table-column>
      <el-table-column label="机构级别" prop="compgrade" align="center"></el-table-column>
      <el-table-column label="上级机构" prop="parentName" align="center"></el-table-column>
      <el-table-column label="机构负责人" prop="compcharge" align="center"></el-table-column>
      <el-table-column label="显示序号" prop="comorgid" align="center"></el-table-column>
    </el-table>
    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
  </div>
</template>
<script>
import pager from "@/components/Pager"
import request from "@/utils/request"
import common from '@/api/common'
import cleanBtn from '@/components/CleanBtn'
export default {
  components: { pager,cleanBtn },
  data() {
    return {
      totalNumber: 0,
      size : 50,        // 初始值 默认：50
      index : 1,        // 初始值 默认：1
      paramForm: {
        compcode: "",
        location: ""
      },
      orgData: [] // 机构数据
    };
  },
  mounted() {
    this.init({size:this.size,index:this.index});
  },
  methods: {
    init: function(param) {
      let params = common.paramsFormat(param);
      request({
        url: "/commOrganization/queryPager",
        method: "post",
        data: params
      }).then(res => {
        this.orgData = res.data.results;
        this.totalNumber = res.data.total;
      });
    }, // 页面初始加载
    // 搜索提交
    submitBtn: function() {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      params.location = this.paramForm.location;
      params.compcode = this.paramForm.compcode
      this.init(params)
    }, // 搜索提交 end
    // 分页时触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.location = this.paramForm.location;
      params.compcode = this.paramForm.compcode
      this.init(params)
    }, // 分页时触发 end
  }
};
</script>



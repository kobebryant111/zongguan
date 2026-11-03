<template>
  <div class="subor-job-container">
    <!-- 查询区域 -->
    <div class="param-form-container">
      <el-form ref="paramForm" :model="paramForm" inline>
        <el-form-item label="岗位编号" prop="jobid">
          <el-input placeholder="岗位编号" v-model="paramForm.jobid"/>
        </el-form-item>
        <el-form-item label="岗位名称" prop="location">
          <el-input label="岗位名称" v-model="paramForm.location"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <!-- 查询区域 end -->
    <!-- table -->
    <el-table :data="jobData" fit border height="450" max-height="480">
      <el-table-column label="岗位编号" prop="jobid" align="center"></el-table-column>
      <el-table-column label="岗位名称" prop="location" align="center"></el-table-column>
      <el-table-column label="机构" prop="orgName" align="center"></el-table-column>
      <el-table-column label="部门" prop="deptName" align="center"></el-table-column>
    </el-table>
    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import cleanBtn from '@/components/CleanBtn'
export default {
  name: "SubordingateJob",
  components: { pager,cleanBtn },
  props: {
    checkedNode: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      jobData: [], // 列表数据
      paramForm: {
        jobid: "",
        location: ""
      }
    };
  },
  mounted() {
    this.init({
      size: this.size,
      index: this.index,
      compid:
        this.checkedNode.compid == undefined ? -1 : this.checkedNode.compid
    });
  },
  methods: {
    // 初始化
    init: function(param) {
      let data = common.paramsFormat(param);
      request({
        url: "/commJob/queryPager",
        method: "post",
        data: data
      }).then(res => {
        this.jobData = res.data.results;
        this.totalNumber = res.data.total;
      });
    },
    // 分页时触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.location = this.paramForm.location;
      params.jobid = this.paramForm.jobid;
      this.init(params);
    }, // 分页时触发 end
    // 搜索
    submitBtn: function() {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      params.location = this.paramForm.location;
      params.jobid = this.paramForm.jobid;
      this.init(params);
    }
  }
};
</script>

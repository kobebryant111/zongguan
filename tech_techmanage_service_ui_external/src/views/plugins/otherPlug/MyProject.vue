<!-- 我担任业务经理项目 -->
<template>
  <div class="app-container">
    <!-- 搜索-->
    <div class="search-container">
      <el-form :model="form" ref="form" label-width="80px" inline>
        <el-form-item label="项目编号">
          <el-input v-model="form.projectno"></el-input>
        </el-form-item>
        <el-form-item label="项目名称">
          <el-input v-model="form.projectName"></el-input>
        </el-form-item>

        <el-form-item>
          <button-orange :buttonName="searchText" :size="sizes" :fns="searchBtn"></button-orange>
          <clean-btn :size="sizes" :forms="this.form"/>
        </el-form-item>
      </el-form>
    </div>
    <!-- 列表 -->
    <div class="content-container">
      <el-table :data="data" border stripe highlight-current-row @row-click="hadlenTableRow">
        <el-table-column prop="projectNo" label="项目编号"></el-table-column>
        <el-table-column prop="projectName" label="项目名称"></el-table-column>
        <el-table-column prop="techDeptName" label="实施部门"></el-table-column>
        <el-table-column prop="techUserName" label="科技项目经理"></el-table-column>
        <el-table-column prop="busiDeptName" label="业务部门"></el-table-column>
        <el-table-column prop="busiUserName" label="业务项目经理"></el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager"/>
    </div>
  </div>
</template>

<script>

import { queryOnlinePager } from "@/api/branchIntentionApi/yyxmbg";
import common from "@/api/common";
import cleanBtn from "@/components/CleanBtn";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
export default {
  name: "MyProject",
  components: { cleanBtn, pager, buttonOrange },
  data() {
    return {
      sizes: "mini", // cleanBtn 大小
      searchText: "搜 索", // 按钮值
      form: {
        projectno: "", // 项目编号
        projectName: "", // 项目名称
        userid : '',       // 工号
      },
      // 分页
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      data: [], // 分页数据
      rowData: {} // table选中行数据
    };
  },
  methods: {
    /**
     * 页面加载
     */
    init(param) {
      let self = this;
      let params = param;
      self.form = param
      params.index = self.index;
      params.size = self.size;
      self.pager(params);
    },
    /**
     * 搜索
     */
    searchBtn() {
        let self = this;
        let params = self.form;
        params.index = 1;
        params.size = self.size
        self.pager(params)
    },
    /**
     * 分页方法
     */
    pager(params) {
      let self = this;
      console.log('params',params)
      let data = common.paramsFormat(params);
      queryOnlinePager(data).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        console.log('项目上线111',res.data.results)
        let result = res.data.results
        result = result.filter(ii=>{
            ii.busiUserName = ii.busiUserName + '(' + ii.busiUserId + ')'
            ii.techUserName = ii.techUserName + '(' + ii.techUserId + ')'
            return ii
        })
        
        self.data = result;
        self.totalNumber = res.data.total;
      }); // pagerList
    },
    /**
     * 分页
     */
    getPager(size, index) {
      let self = this;
      let params = self.form;
      params.size = size;
      params.index = index;
      self.size = size;
      self.index = index;
      if (
        undefined != params.date &&
        "" != params.date &&
        params.date.length > 0
      ) {
        params.startDate = params.date[0];
        params.endDate = params.date[1];
      }
      self.pager(params);
    }, // end
    /**
     * 选择行
     */
    hadlenTableRow(row,column,event) {
        console.log(row,column,event)
        this.rowData = row
    },
  }
};
</script>


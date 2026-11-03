<!-- 生产缺陷列表 -->
<template>
  <div class="app-container">
    <!-- 搜索-->
    <div class="search-container">
      <el-form :model="form" ref="form" label-width="80px" inline>
        <el-form-item label="缺陷编号">
          <el-input v-model="form.pacthNo"></el-input>
        </el-form-item>
        <el-form-item label="缺陷名称">
          <el-input v-model="form.pacthTitle"></el-input>
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
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column prop="pacthNo" label="生产编号"></el-table-column>
        <el-table-column prop="pacthTitle" label="生产缺陷"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column prop="planOnlineDate" label="计划上线时间"></el-table-column>
        <el-table-column prop="severity" label="严重程度"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>

import { getProBus } from "@/api/branchIntentionApi/yyxmbg";
import { getDicEntry } from "@/api/eosDictEntry";
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
        pacthNo: "", // 项目编号
        pacthTitle: "", // 项目名称
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
      // FJHBUGSTATUS
      self.pager(param)
    },
    /**
     * 搜索
     */
    searchBtn() {
        let self = this;
        let data ;
       if(self.form.pacthNo) {
        data = self.data.filter(ii => {
          return  self.form.pacthNo == ii.pacthNo
        })
      }
      if(self.form.pacthTitle && data) {
        data.filter(ii=> {return ii.pacthTitle.indexOf(self.form.pacthTitle) != -1 })
      } else if(self.form.pacthTitle) {
        data = self.data.filter(ii => {
          return  ii.pacthTitle.indexOf(self.form.pacthTitle) != -1
        })
      } 
      if(!self.form.pacthNo && !self.form.pacthTitle) {
        self.pager()
      }
      self.data = data
        
    },
    /**
     * 分页方法
     */
    pager(params) {
      let self = this;
      let data = common.paramsFormat(params);
      getProBus(data).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        console.log('结果',res.data.results)
        self.data = res.data.results;
      }); // pagerList
    },
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


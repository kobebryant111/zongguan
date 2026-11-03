<!-- 菜单日志 -->
<template>
  <div class="app-container">
    <!-- 搜索 -->
    <div class="search-container">
      <el-form :model="form" ref="form" label-width="80px" inline>
        <el-form-item label="姓名">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="时间">
          <el-date-picker
            v-model="form.createTime"
            type="daterange"
            range-separator="至"
            start-placeholde="开始时间"
            end-placeholde="结束时间"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <buttonOrange :buttonName="submitText" :fns="submitBtn"></buttonOrange>
          <clean-btn :forms="this.form"/>
        </el-form-item>
      </el-form>
    </div>
    <!-- 主体 -->
    <div class="content-container">
      <el-table :data="data" border stripe
      :cell-style="{textAlign : 'center'}"
      :header-cell-style="{textAlign : 'center'}">
        <el-table-column label="姓名">
            <template slot-scope="scope">
                {{scope.row.userName}}({{scope.row.userId}})
            </template>
        </el-table-column>
        <el-table-column prop="createTime" label="时间"></el-table-column>
        <el-table-column prop="ip" label="IP"></el-table-column>
        <el-table-column prop="name" label="菜单名称"></el-table-column>
        <el-table-column prop="code" label="菜单编号"></el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager"/>
    </div>
  </div>
</template>
<script>
import menu from "@/api/menuApi";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon";
import Moment from "moment";
export default {
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      // 分页
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      data: [], // 分页数据
      submitText: "搜 索",
      form: {
        userName: "",
        createTime: ""
      }
    };
  },
  mounted() {
      this.init()
  },
  methods: {
    init() {
      let self = this;
      let params = self.form;
      params.size = self.size;
      params.index = self.index;
      self.pager(params);
    },
    /**
     * 分页方法
     */
    pager(params) {
      let self = this;
      let data = common.paramsFormat(params);
      menu.menuLogPage(data).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        res.data.results.forEach((v, k) => {
          v.createTime = Moment(v.createTime).format("YYYY-MM-DD HH:mm:ss");
        });
        self.data = res.data.results;
        self.totalNumber = res.data.total;
      }); // pagerList
    },
    /**
     * 搜索
     */
    submitBtn() {
      let self = this;
      let params = self.form;
      params.index = 1;
      self.index = 1;
      params.size = self.size;
      if (params.createTime && params.createTime.length > 0) {
        params.startTime = params.createTime[0];
        params.endTime = params.createTime[1] + " 23:59:59";
      }
      self.pager(params);
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
      if (params.createTime && params.createTime.length > 0) {
        params.startTime = params.createTime[0];
        params.endTime = params.createTime[1] + " 23:59:59";
      }
      self.pager(params);
    }
  }
};
</script>


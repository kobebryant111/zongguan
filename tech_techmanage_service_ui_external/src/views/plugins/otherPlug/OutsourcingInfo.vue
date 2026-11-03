<!-- 外包公司列表 -->
<template>
  <div class="app-container">
    <div class="param-container">
      <el-form :model="paramForm" ref="form" label-width="80px">
        <el-form-item label="外包公司名">
          <el-input v-model="paramForm.outsourcname"></el-input>
        </el-form-item>
        <el-form-item label="组织机构代码">
          <el-input v-model="paramForm.orgcode"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
          <clean-btn :forms="this.paramForm"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="main-container">
      <el-table :data="data" border stripe @row-click="selectedLine">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-colum prop="outsourcname" label="外包公司名称" align="center"></el-table-colum>
        <el-table-colum prop="orgcode" label="组织机构代码" align="center"></el-table-colum>
        <el-table-colum prop="contactsname" label="联系人" align="center"></el-table-colum>
        <el-table-colum prop="telephone" label="联系电话" align="center"></el-table-colum>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
  </div>
</template>
<script>
import cleanBtn from "@/components/CleanBtn";
import pager from "@/components/Pager";
import common from "@/api/common";
import { getPagerList } from "@/api/otherApi/cutsourcingInfo.js";
export default {
  name: "OutsourcingInfo",
  props : {
      selectedRow : {
          type : Function,
          required : true,
          default : function(row,column,event){}
      }
  },
  components: {
    cleanBtn,
    pager
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      data: [],
      paramForm: {
        outsourcname: "",
        orgcode: ""
      }
    };
  },
  mounted() {
    this.packageInit();
  },
  methods: {
    /**
     * 封装初始化方法
     */
    packageInit: function() {
      let param = {};
      param.size = this.size;
      param.index = this.index;
      this.init(param);
    }, // end
    // 页面初始化
    init: function(params) {
      let data = common.paramsFormat(param);
      getPagerList(data)
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          this.data = res.data.results;
          this.totalNumber = res.data.total;
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
    }, // end

    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params.outsourcname = this.paramForm.outsourcname;
      params.orgcode = this.paramForm.orgcode;
      params.size = size;
      params.index = index;
      this.init(params);
    },
    selectedLine : function() {

    }
  }
};
</script>


<!-- 分行意向 分页列表 -->
<template>
  <div class="app-container">
    <!-- 搜索 -->
    <div class="search-contarion">
      <el-form :model="searchForm" ref="searchForm" label-width="80px" inline >
        <el-form-item label="意向编号">
          <el-input v-model="searchForm.demandno"></el-input>
        </el-form-item>
        <el-form-item label="意向名称">
          <el-input v-model="searchForm.demandname"></el-input>
        </el-form-item>
        
        <el-form-item label="意向状态">
          <!-- BUSI_DEMAND_STATUS-->
          <el-select v-model="searchForm.state" placeholder="请选择" 
          multiple>
            <el-option
              v-for="item in statusData"
              :key="item.dictid"
              :label="item.dictname"
              :value="item.dictid"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提交时间">
          <el-date-picker v-model="searchForm.bringupdate" 
          type="daterange" 
          range-separator="至"
          start-placeholde="开始日期"
          end-placeholde="结束日期"
          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="规模">
          <el-select v-model="searchForm.guiMo" placeholder="请选择" 
          multiple>
            <el-option
              v-for="item in gmData"
              :key="item.dictid"
              :label="item.dictname"
              :value="item.dictid"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">搜索</el-button>
          <clean-btn :size="sizes" :forms="searchForm"/>
        </el-form-item>
      </el-form>
    </div>
   
    <!-- 主体 -->
    <div class="main-contarion">
      <el-table :data="data" border stripe
      highlight-current-row
      @row-dblclick="showItem">
        <el-table-column prop="demandno" label="意向编号" show-overflow-tooltip></el-table-column>
        <el-table-column prop="demandname" label="意向名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="busiUserName" label="业务项目经理"></el-table-column>
        <el-table-column prop="demandDeptName" label="牵头业务部门"></el-table-column>
        <el-table-column prop="sysName" label="关联主系统" show-overflow-tooltip></el-table-column>
        <el-table-column prop="proscale" label="规模" width="80px"></el-table-column>
        <el-table-column prop="bringupdate" label="提出日期"></el-table-column>
        <el-table-column prop="busiexpect" label="期望上线日期"></el-table-column>
        <el-table-column prop="status" label="当前状态"></el-table-column>
        <el-table-column prop="projectStatus" label="项目状态"></el-table-column>
        <el-table-column prop="projectNo" label="项目信息"></el-table-column>
        <el-table-column prop="majorproject" label="是否重点项目"></el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager"/>
    </div>
    <!-- dialog -->
    <el-dialog
      title="详情"
      :visible.sync="visibleClose"
      width="80%"
      @close="dialogClose">
      <gd-detail  ref="gdDetail" v-if="visibleClose"></gd-detail>
    </el-dialog>
    
  </div>
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
import common from "@/api/common";
import Moment from "moment";
import cleanBtn from "@/components/CleanBtn";
import pager from "@/components/Pager";
import {pagerList} from '@/api/branchIntentionApi/fhyx'
import buttonOrange from '@/components/eleButton/buttonOrange'; //引入按钮组件Orange
import gdDetail from '@/views/branchIntention/GdDetail'
export default {
  components: {
    cleanBtn,
    pager,
    buttonOrange,
    gdDetail
  },
  data() {
    return {
      // 分页
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      data: [], // 分页数据
      searchForm: {
        demandno: "",
        demandname: "",
        state: "",
        bringupdate : '',
        guiMo : ''
      },
      gmData : [],    // 规模数据
      statusData : [],  // 状态数据
      zdData : [],    // 是否重点数据
      options : [],   // 意向装态数据
      sizes : 'medium',
      visibleClose : false,   // dialog 是否显示 默认 false
    };
  },
  mounted() {
    let self = this
    let eos = {
        dicttypeid:
          "GLOBAL_PROJECT_SCALE,GLOBAL_PROJECT_MAJOR,BUSI_DEMAND_STATUS"
      }
      getDicEntry(eos).then(res => {
          // 规模
          self.gmData = res.data.results.filter(ii => {
            return ii.dicttypeid === 'GLOBAL_PROJECT_SCALE'
          })
          // 是否重点项目
          self.zdData = res.data.results.filter(ii => {
            return ii.dicttypeid === 'GLOBAL_PROJECT_MAJOR'
          })
          // 当前状态
           self.statusData = res.data.results.filter(ii => {
            return ii.dicttypeid === 'BUSI_DEMAND_STATUS'
          })
      })
    this.init()
  },
  methods: {
    /**
     * 初始化加载
     */
    init() {
      this.pager(this.searchForm)
    },
    /**
     * 搜索
     */
    onSubmit() {
      const self = this
      
      self.searchForm.size = self.size
      self.searchForm.index = 1
      if(self.searchForm.state != '') {
        self.searchForm.status = self.searchForm.state.join(',')
      }
      if(self.searchForm.guiMo != '') {
        self.searchForm.proscale = self.searchForm.guiMo.join(',')
      }
      
      if(self.searchForm.bringupdate != undefined && self.searchForm.bringupdate != '') {
        self.searchForm.startTime = self.searchForm.bringupdate[0]
        self.searchForm.endTime = self.searchForm.bringupdate[1]
      }
      
      console.log('searchForm:', self.searchForm)
      self.pager(self.searchForm)
    },
    /**
     * 分页方法
     */
    pager(params) {
      let self = this;
      let data = common.paramsFormat(params);
      
      pagerList(data).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        console.log('data:',res.data.results)
        res.data.results.forEach((v, k) => {
          v.bringupdate = Moment(v.bringupdate).format("YYYY-MM-DD");
          if(v.busiexpect != undefined) {
            v.busiexpect = Moment(v.busiexpect).format("YYYY-MM-DD");
          }
          // 重点
          let majorproject = self.zdData.filter(ii => {
            return ii.dictid === v.majorproject
          })
          v.majorproject = majorproject[0].dictname 
          // 规模
          let proscale = self.gmData.filter(ii => {
            return ii.dictid === v.proscale
          })
          v.proscale = proscale[0].dictname 
          // 当前状态
           let status = self.statusData.filter(ii => {
            return ii.dictid === v.status
          })
          v.status = status[0].dictname 
        });
        self.data = res.data.results;
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
    },    // end
    /**
     * 双击查看详情
     */
   showItem(row,column,event) {
     const self = this
     self.visibleClose = true
     console.log(row,column,event)
     
     self.$nextTick(_=>{
       self.$refs.gdDetail.init(row)
     })
     
   },
   /**
    * dialog 关闭方法
    */
   dialogClose() {
     this.visibleClose = false
   }
  }
};
</script>

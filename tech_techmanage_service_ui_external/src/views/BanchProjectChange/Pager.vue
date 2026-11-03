<!-- 分页 -->
<template>
  <div class="app-container">
    <div class="opation-container">
      <el-row>
        <el-col :span="22">&nbsp;</el-col>
        <el-col :span="2">
          <button-orange :buttonName="sendFlowText" :size="sizes" :fns="sendFlowBtn"></button-orange>
        </el-col>
      </el-row>
      
    </div>
    <div class="page-container">
      <el-table :data="data" border stripe>
        <el-table-column prop="processInstName" label="流程名称"></el-table-column>
        <el-table-column prop="workItemName" label="流程节点"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <!-- <el-table-column  label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="performBtn(scope.row)">执行</el-button>
          </template>
        </el-table-column> -->
      </el-table>
    </div>
    <el-dialog
      :title="dialogTitile"
      :visible.sync="visibleClose"
      width="80%"
      @close="dialogClose"
      top="5vh"
      v-if="visibleClose"
      :before-close="handleClose">
      <pager-one></pager-one>
    </el-dialog>
    
  </div>
</template>
<script>
import common from "@/api/common";
import Moment from "moment";
import { getDb } from "@/api/common/flowApi";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import pagerOne from '@/views/BanchProjectChange/pager1'
export default {
  components: {
    buttonOrange,
    pagerOne
  },
  data() {
    return {
      data: [], // 分页数据
      sizes : 'medium',
      sendFlowText : '发起流程',
      visibleClose : false,   // dialog 是否显示，默认关闭
      dialogTitile : '',      // dialog 标题
    };
  },
  mounted(){
    this.pager({});
  },
  methods: {
    pager(params) {
      let self = this;
      params.processDefName = "com.example.ppm.bps.fhyyxtbg";
      let data = common.paramsFormat(params);
      getDb(data).then(res => {
        console.log("data:", res);
        self.data = res.data.results
      });
    },
    
    /**
     * 执行操作
     */
    performBtn(item) {},
    /**
     * 发起流程按钮
     */
    sendFlowBtn(){
      const self = this
      console.log('dd')
      self.visibleClose = true
      self.dialogTitile = "分行项目变更申请"
    },
    /**
     * dialog 关闭事件
     */
    dialogClose() {
      this.visibleClose = false
    },
    /**
     * 关闭dialog提示
     */
    handleClose(done) {
        this.$confirm('确定关闭').then(_=>{
          done()
        })
    },
  }
};
</script>
<style scoped>
.opation-container{
  margin-bottom: 10px;
}
</style>


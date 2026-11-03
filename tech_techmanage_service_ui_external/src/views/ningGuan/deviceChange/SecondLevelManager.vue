<!-- 二级部经理审批 -->
<template>
  <div class>
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="3">
              <p class="nav_name">网点设备变更流程</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <el-button @click="closeRouter">关 闭</el-button>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <div class="deveice-dateil-container">
      <deveice-detail :data="ngData" ref="deveiceDetail"></deveice-detail>
    </div>
    <div class="czLog app-container">
      <flow-logs :processInstId="flowItem.processInstID" ref="log"></flow-logs>
    </div>
    <!-- 底部栏样式2 -->
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flowItem="flowItem" v-if="flowItem.status == 'db'"></approve-bottom>
      <yb-revoke :flowItem="flowItem" v-else></yb-revoke>
    </div>
  </div>
</template>
<script>
  import common from "@/api/common";
  import DeveiceDetail from "@/views/ningGuan/deviceChange/common/Detail";
  import approveBottom from "@/views/plugins/flowPlug/approveBottom";
  import { queryDetail, ministryManager } from "@/api/ningGuanApi/deviceChange";
  import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
  import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
  import Moment from 'moment';
  export default {
    components: { DeveiceDetail, approveBottom, flowLogs, ybRevoke },
    data() {
      return {
        ngData: {}, // 详情
        flowItem: {} // bps 对象
      };
    },
    methods: {
      /**
       * 页面加载
       */
      init(params) {
        let self = this;
        self.flowItem = params;
        self.$refs.deveiceDetail.init();
        this.$refs.log.init(params.processInstID);
        self.$nextTick(_ => {
          let params = { instanceId: self.flowItem.processInstID };
          queryDetail(params).then(res => {
            console.log("res:", res.data.results);
            self.ngData = res.data.results;
            // self.ngData.createDate = common.dateFormat(
            //   self.ngData.createDate,
            //   "yyyy-MM-dd HH:mm:ss"
            // );
            self.ngData.createDate = Moment(self.ngData.createDate).format('YYYY-MM-DD HH:mm:ss');
          });
        });
      },
      /**
       * 流转
       */
      liuzhuan(params) {
        const loading = common.loading(this);
        this.flowItem.conclusion = params.conclusion;
        this.flowItem.suggest = params.suggest;
        let folw = {};
        folw.bps = this.flowItem;
        folw.badge = this.ngData.badge
        console.log("folw", folw);
        return ministryManager(folw)
          .then(res => {
            loading.close();
            return res.data;
          })
          .catch(error => {
            this.$alert(error, { type: "error" });
          });
      },
      /**
       * 流转
       */
      proving(data) {
        let status = new Promise(() => { });
        status = this.liuzhuan(data).then(val => {
          return val;
        });
        return status;
      },
      /**
       *  关闭当前路由
       */
      closeRouter() {
        this.$router.push({ name: 'deviceChange' });
      },
    }
  };
</script>
<style scoped>
  .nav {
    width: 100%;
    background-color: #ffffff;
  }

  .nav .nav_name {
    width: 150px;
    height: 24px;
    font-size: 16px;
    font-weight: 600;
    color: rgba(38, 38, 38, 1);
    line-height: 24px;
    display: inline-block;
  }

  .nav .wrapper {
    margin-left: 25px;
    margin-right: 25px;
    display: flex;
    justify-content: space-between;
  }

  .wrapper_left,
  .wrapper_right {
    display: flex;
    align-items: center;
  }
</style>
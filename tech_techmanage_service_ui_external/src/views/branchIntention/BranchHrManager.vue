<!-- 分行人力资源部总经理审批  -->
<template>
  <div class="global-container">
    <detail ref="detail"></detail>
    <div class="app-container"></div>
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flowItem="bps" v-if="isDb"></approve-bottom>
      <yb-revoke :flowItem="bps" v-else></yb-revoke>
    </div>
  </div>
</template>
<script>
import { getDateil,humanResources } from "@/api/branchIntentionApi/fhyx";
import common from "@/api/common";
import detail from "@/views/branchIntention/comm/Detail";
import flowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
export default {
  components: {
    detail,
    ybRevoke,
    approveBottom
  },
  data() {
    return {
      data: [],
      bps: {}, // bps
      isDb: true // 是否待办，默认:true
    };
  },
  methods: {
    init(params) {
      let self = this;
      let param = {};
      self.bps = params;
      param.instanceid = params.processInstID;
      if (params.status !== "db") {
        self.isDb = false;
      }
      getDateil(param).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.messge, { type: "error" });
          return false;
        }
        console.log("1 ");
        self.data = res.data.results;
        self.$refs.detail.showDateil(self.data, params);
      });
    }, // end
    /**
     * 提交
     */
    proving(data) {
      let self = this;
      let status = new Promise(() => {});
      status = self.liuzhuan(data).then(val => {
        return val;
      });
      return status;
    },
    /**
     * 流转
     */
    liuzhuan(data) {
      let parms = {};
      let self = this;
      const loading = common.loading(self);
      self.bps.conclusion = data.conclusion;
      self.bps.suggest = data.suggest;
      parms.bps = self.bps;
      console.log("parms", parms);
      return humanResources(parms).then(res => {
        loading.close()
        return res.data;
      });
    }
  }
};
</script>
<style scoped>
.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  z-index: 999;
}
</style>

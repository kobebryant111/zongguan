<!--分行研发部/IT支持部经理审批 -->
<template>
  <div>
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="3">
              <p class="nav_name">分行应用变更</p>
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
    <detail ref="detail" >
      <template slot="logSlot">
        <!-- 操作日志 -->
        <div class="app-container">
          <flow-logs ref="log"></flow-logs>
          <div class="app-container">
            <div>&nbsp;</div>
            <div>&nbsp;</div>
          </div>
        </div>
      </template>
      <template slot="buttonSlot">
        <div class="nav-bottom">
          <approve-bottom :proving="proving" :flowItem="bps" v-if="isDb"></approve-bottom>
          <yb-revoke :flowItem="bps" v-else></yb-revoke>
        </div>
      </template>
    </detail>
  </div>
</template>
<script>
import { getFhChangeDateil, banchDeve } from "@/api/branchIntentionApi/yyxmbg";
import Detail from "@/views/BanchProjectChange/common/Detail";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import common from "@/api/common";
export default {
  name: "pagerTwo",
  components: {
    Detail,
    ybRevoke,
    approveBottom,
    flowLogs
  },
  data() {
    return {
      bps: {}, // bps 对象
      isDb: true // 是否待办 默认 true
    };
  },
  methods: {
    init(item) {
      const self = this;
      self.bps = item;
      //
      if (item.status !== "db") {
        self.isDb = false;
      }
      self.$refs.detail.ifRead = true;
      self.$refs.log.init(self.bps.processInstID);

      let param = {};
      param.instanceid = item.processInstID;
      self.$nextTick(_ => {
        getFhChangeDateil(param).then(res => {
          console.log("详情：", res.data);
          let res_itenm = res.data.results;
          self.$refs.detail.form = res_itenm;
          self.$refs.detail.form1 = res_itenm;
          self.$refs.detail.form5 = res_itenm;
          self.$refs.detail.lData = res_itenm.systemList;
          let list = res_itenm.pwdList.filter(ii => {
              ii.userid = ii.username
              ii.systemid = ii.systemname
                return ii
          });
          self.$refs.detail.mgData = list;
          self.$refs.detail.docData = res_itenm.docList;
          self.changetype = res_itenm.changetype
          let ispad = res_itenm.istopwd
          self.$refs.detail.sysName = res_itenm.systemList[0].sysName
          if("是" === ispad) {
            self.$refs.detail.mgTableShow = true
          } else {
              self.$refs.detail.mgTableShow = false
          }
          // 数据维护
          if(res_itenm.changetype !== '03') {
            self.$refs.detail.sjwhShow = false
          }
        });
      });
    },

    /**
     * 提交流程
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
      let self = this;
      let load = common.loading(self)
      let param = {};
      self.bps.conclusion = data.conclusion;
      self.bps.suggest = data.suggest;
      param.bps = self.bps;
      
      // 发起流程
      return banchDeve(param).then(res => {
        load.close()
        return res.data;
      });
    },

    /**
     *  关闭当前路由
     */
    closeRouter() {
      this.$router.push({ name: "dashboard" });
    }
  } // end
};
</script>
<style scoped>
.fu-title {
  margin-left: 10px;
}
.footer-center-div {
  text-align: center;
}

.footer-right-div {
  text-align: right;
  padding-top: 10px;
}
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
.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  z-index: 999;
}
</style>


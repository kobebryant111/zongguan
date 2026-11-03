<!-- 主办人-->
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

    <detail ref="detail" :ifOrganizer="true">
      <!-- 密管获取密码 -->
      <template slot="mgPwdSlot">
       
        <el-row>
          <el-col :span="24">
            <el-form-item label>
              
              <el-table
                :data="mgData"
                border
                stripe
                highlight-current-row
              >
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column prop="systemname" label="系统"></el-table-column>
                <el-table-column prop="ip" label="设备ip"></el-table-column>
                <el-table-column prop="usertype" label="用户类型"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="applyer" label="申请人"></el-table-column>
                <el-table-column prop="pwd" label="密码"></el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </template>
      <template slot="logSlot">
        <!-- 操作日志 -->
        <div class="app-container">
          <flow-logs-gd ref="log"></flow-logs-gd>
          <div class="app-container">
            <div>&nbsp;</div>
            <div>&nbsp;</div>
          </div>
        </div>
      </template>
      <template slot="buttonSlot">
        <div class="nav-bottom">
          
        </div>
      </template>
    </detail>

    
  </div>
</template>
<script>
import {
  getFhChangeDateil,
  hostHandle,
  deleteShebei,
  getPwdList,
  exportExcel,
  scywsjgd,
  queryPasswordIs,
  queryPwdModel,
  querySavePasswd
} from "@/api/branchIntentionApi/yyxmbg";
import Detail from "@/views/BanchProjectChange/common/Detail";
import flowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import MgShebeiPager from "@/views/plugins/otherPlug/MgShebeiPager";
import common from "@/api/common";
import Moment from "moment";
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import { mapGetters } from "vuex";
export default {
  name: "pagerTwo",
  components: {
    Detail,
    ybRevoke,
    approveBottom,
    flowLogsGd,
    MgShebeiPager,
    buttonOrange,
    buttonCommon
  },
  data() {
    return {
      bps: {}, // bps 对象
      isDb: true, // 是否待办 默认 true
      mgData: [], // 密管
      addText: "添加",
      deleteText: "删除",
      mgRowData: {}, // 密管列表选中对象
      sysName: "", // 系统名称
      visibleClose: false, // dialog 是否显示 默认false
      dialogWidth: "50%", // dialog 默认宽度 50%
      ifYwsjBtn: false, // 是否禁用运维审计按钮
      resData: {}, // 后台返回的详情信息
      addAndDelBtnShow: true // 添加和删除按钮是否显示，默认显示
    };
  },
  computed: {
    ...mapGetters(["userId", "name"])
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
      self.$refs.log.init2(self.bps);

      let param = {};
      param.instanceid = item.instanceid;
      self.$nextTick(_ => {
        getFhChangeDateil(param).then(res => {
          console.log("详情：", res.data);
          let res_itenm = res.data.results;
          self.resData = res_itenm;
          self.$refs.detail.form = res_itenm;
          self.$refs.detail.form1 = res_itenm;
          self.$refs.detail.form5 = res_itenm;
          self.$refs.detail.lData = res_itenm.systemList;
          let list = res_itenm.pwdList.filter(ii => {
            ii.userid = ii.username;
            ii.systemid = ii.systemname;
            return ii;
          });
          // self.$refs.detail.mgData = list;
          self.$refs.detail.docData = res_itenm.docList;
          let ispad = res_itenm.istopwd;
          self.$refs.detail.sysName = res_itenm.systemList[0].sysName;
          if ("是" === ispad) {
            self.$refs.detail.mgTableShow = true;
          } else {
            self.$refs.detail.mgTableShow = false;
          }
          // 数据维护
          if(res_itenm.changetype !== '03') {
            self.$refs.detail.sjwhShow = false
          }
        });
        getPwdList(param).then(res => {
          console.log("设备", res.data);
          let data = res.data.results;
          data.forEach((ii, key) => {
            if (ii.endtime) {
              ii.endtime = Moment(ii.endtime).format("YYYY-MM-DD");
            }
          });
          self.mgData = data;
        });
        
      });
    },    // end
     /**
     *  关闭当前路由
     */
    closeRouter() {
      this.$router.push({ name: "pager" });
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
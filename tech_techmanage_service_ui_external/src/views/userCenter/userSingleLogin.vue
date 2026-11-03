<template></template>

<script>
import {
  queryWorkItem,
  queryGdDetail,
  getFlowStatus,
  queryInstanceidByOnlinenum,
  queryInstanceidByIssueId,
  delOaNotice
} from "@/api/common/flowApi.js";
export default {
  name: "userSingleLogin",
  data() {
    return {
      verifyType: null, // 校验类型 oa:需要校验，如果流程已经不再当前人，就提示
      instanceid: null, // 流程id
      workItemId: null, // 工作项id
      userid: null // 用户id
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    // 初始化
    init: function(param) {
      // this.$msgbox({
      //     showClose : false,
      //     showConfirmButton:false,
      //     title : '正在登录中...',
      //     iconClass : 'el-icon-loading'
      // })
      let self = this;
      // 用户id
      self.userid = self.$route.query.userId;
      // 调转的路径
      let type = self.$route.query.redirect;
      // token
      let token = self.$route.query.token;
      let ip = self.$route.query.ip;
      // gd 归档，yb：已办，db：待办
      self.status = self.$route.query.status;
      // 流程id
      self.instanceid = self.$route.query.instanceid || "";
      // 工作项id
      self.workItemId = self.$route.query.workItemID;
      // 验证登录类型 1：仅当天生效 默认：1，2:永远有效
      let loginType = self.$route.query.loginType;
      // 奋进号id
      let issueId = self.$route.query.issueId;
      //奋进号上线标号
      let onlinenum = self.$route.query.onlinenum;
      // 校验类型 oa:需要校验，如果流程已经不再当前人，就提示
      self.verifyType = self.$route.query.vType;
      
      console.warn("warn submit!!", self.$route.query);
      let query = {
        userId: self.userid,
        token: token,
        ip: ip,
        loginType: loginType
        // sysCode:'a',
        // extensions:'a'
      };
      this.$store
        .dispatch("SingleLoginByUsername", query)
        .then(response => {
          self.loading = false;
          
          self.userid = response.data.results.userid;
          // 奋进号分行业务意向单点
          if (issueId) {
            console.log("进入奋进号1：", issueId);
            // 根据奋进号id 查询流程编号
            queryInstanceidByIssueId({ issueid: issueId }).then(res => {
              console.log("进入奋进号2：", res.data.results);
              self.instanceid = res.data.results.instanceid;
              self.queryFlowStatus();
            });
          } else if (onlinenum != null && onlinenum != "") {
            console.log("奋进号进入3：", onlinenum);
            // 根据奋进号上线编号 查询流程编号
            queryInstanceidByOnlinenum({ onlineno: onlinenum }).then(res => {
              console.log("进入奋进号4：", res.data.results);
              self.instanceid = res.data.results.instanceid;
              self.queryFlowStatus();
            });
          } else {
            // 没有流程编号直接点跳转首页
            if (!self.instanceid) {
              self.$router.push({ path: type || "/" });
            } else if (self.instanceid) {
              // 查询流程
              self.queryFlowStatus();
            }
          }
        })
        .catch(error => {
          this.loading = false;
          console.error(error);
        });
    }, // end
    /**
     * 查询流程状态以及业务处理
     */
    queryFlowStatus() {
      let self = this;
      let flowStatus = "";
      getFlowStatus({
        processInstID: self.instanceid,
        userId: self.userid
      }).then(res => {
        console.log("查询流程状态：", res.data);
        if (res.data.status != 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        if (res.data.results) {
          flowStatus = "yjs";
        } else {
          flowStatus = "wjs";
        }
        self.jumpView(flowStatus);
      });
    },
    /**
     * 跳转路径处理
     */
    jumpView(flowStatus) {
      const self = this;
      if ("gd" === self.status) {
        queryGdDetail({ processInstID: self.instanceid }).then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          let bps = res.data.results;
          bps.status = self.status;
          bps.flowStatus = flowStatus;
          let names = bps.processDefName.split(".");
          let processName = names[names.length - 1];
          let val = processName.replace(/\"/g, "");
          this.$router.push({
            name: val,
            params: bps
            // name: val,
            // params: {
            //   actionURL: "interviewScore",
            //   row: row
            // }
          });
        });
        return false;
      }
      
      // 如果是oa通知待办则校验
      // 待办 已办
      queryWorkItem({
        processInstID: self.instanceid,
        userId: self.userid
      }).then(res => {
        if (self.verifyType === "oa") {
          if (res.data.results && res.data.results.participant) {
            // 流程存在时
            if (res.data.results.participant !== self.userid) {
              // 删除oa通知待办
              delOaNotice({ bps: res.data.results }).then(res => {
                console.warn("删除oa通知待办", res.data);
              });
            }
          } else {
            // 如果没有查询到记录，说明已经删除，则直接根据workItemID去删除oa
            // 删除oa通知待办
            let bpss = {workItemID:self.workItemId}
              delOaNotice({ bps: bpss }).then(res => {
                console.warn("删除oa通知待办", res.data);
              });
          }
          self.$alert("当前流程已审批!", { type: "error" });
          self.$router.push({ name: "dashboard" });
          return false;
        }
        self.workItemCl(res, flowStatus);
      });
    }, // end
    /**
     * 处理 待办 已办 返回的结果
     */
    workItemCl(res, flowStatus) {
      const self = this;
      if (res.data.status != 200) {
        this.$alert(res.data.message, { type: "error" });
        return false;
      }
      let bps = res.data.results;
      bps.status = self.status || "db";
      bps.flowStatus = flowStatus;
      let names = bps.processDefName.split(".");
      let processName = names[names.length - 1];
      let val = processName.replace(/\"/g, "");
      this.$router.push({
        name: val,
        params: bps
        // name: val,
        // params: {
        //   actionURL: "interviewScore",
        //   row: row
        // }
      });
    } // workItemCl end
  }
};
</script>
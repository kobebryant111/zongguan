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
        <div v-if="mgTableShow">
          <el-row>
          <el-col :span="5">&nbsp;</el-col>
          <el-col :span="14">
            <el-button type="primary" @click="getPassword">获取密码</el-button>
            <el-button type="primary" @click="exportExcel" :disabled="ifExport">导出</el-button>
            <el-button type="primary" @click="generateYwOrder" :disabled="ifYwsjBtn">生成运维审计工单</el-button>
          </el-col>
          <el-col :span="5">&nbsp;</el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label>
              <div>
                <button-orange :buttonName="addText" 
                :fns="addSheBeiBtn" 
                v-show="addAndDelBtnShow"></button-orange>
                <button-common
                  :buttonName="deleteText"
                  :fns="deleteSheBeiBtn"
                  v-show="addAndDelBtnShow"
                ></button-common>
                <button-orange
                  :buttonName="generalText"
                  :fns="generalBtn"
                ></button-orange>
              </div>
              <el-table
                :data="mgData"
                border
                stripe
                highlight-current-row
                @row-click="hadleTableRow"
              >
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column prop="systemname" label="系统"></el-table-column>
                <el-table-column prop="ip" label="设备ip"></el-table-column>
                <el-table-column prop="usertype" label="用户类型"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="applyer" label="申请人"></el-table-column>
                <el-table-column prop="pwd" label="密码"></el-table-column>
                <el-table-column prop="pwdStatus" label="状态"></el-table-column>
                <el-table-column prop="endtime" label="到期时间"></el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        </div>
      </template>
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

    <!-- dialog -->
    <el-dialog
      title="选择列表"
      :visible.sync="visibleClose"
      :width="dialogWidth"
      :lock-scroll="false"
      :modal="false"
      @close="dialogClose"
    >
      <!-- 密管设备列表 -->
      <mg-shebei-pager ref="shebei" :busSystemShow="false" :systemName="sysName"></mg-shebei-pager>

      <span slot="footer">
        <el-button @click="dialogClose">取 消</el-button>
        <el-button type="primary" @click="selectedNode">确 定</el-button>
      </span>
    </el-dialog>
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
  querySavePasswd,
  getGeneralPassworkKey,
  ifHiddenButton
} from "@/api/branchIntentionApi/yyxmbg";
import Detail from "@/views/BanchProjectChange/common/Detail";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
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
    flowLogs,
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
      generalText : '通用密码',
      mgRowData: {}, // 密管列表选中对象
      sysName: "", // 系统名称
      visibleClose: false, // dialog 是否显示 默认false
      dialogWidth: "50%", // dialog 默认宽度 50%
      ifYwsjBtn: true, // 是否禁用运维审计按钮
      ifExport : true, // 导出按钮
      resData: {}, // 后台返回的详情信息
      addAndDelBtnShow: true, // 添加和删除按钮是否显示，默认显示
      mgTableShow : true,   // 密管模块是否显示， 默认：显示
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
      self.$refs.log.init(self.bps.processInstID);

      let param = {};
      param.instanceid = item.processInstID;
      self.$nextTick(_ => {
        getFhChangeDateil(param).then(res => {
          console.log("详情：", res.data);
          let res_itenm = res.data.results;
          self.resData = res_itenm;
           // 使用时长 
          if(!res_itenm.usehour) {
            res_itenm.usehour = res_itenm.changehour
          }
          self.$refs.detail.form = res_itenm;
          self.$refs.detail.form1 = res_itenm;
          self.$refs.detail.form5 = res_itenm;
          self.$refs.detail.lData = res_itenm.systemList;
          let list = res_itenm.pwdList.filter(ii => {
            ii.userid = ii.username;
            ii.systemid = ii.systemname;
            return ii;
          });
         
          // 密码是否到期
          if(res_itenm.useendtime) {
            self.addAndDelBtnShow = false
            self.ifExport = false
            self.ifYwsjBtn = false
          }
          
          // 数据维护
          if(res_itenm.changetype !== '03') {
            self.$refs.detail.sjwhShow = false
          }
          self.$refs.detail.docData = res_itenm.docList;
          let ispad = res_itenm.istopwd;
          let sysidTepm = []
          res_itenm.systemList.forEach((val,key)=>{
            sysidTepm.push(val.sysName)
          })
          self.sysName = sysidTepm.join(',')
          if ("是" === ispad) {
            self.$refs.detail.mgTableShow = true;
            self.mgTableShow = true
          } else {
            self.$refs.detail.mgTableShow = false;
            self.mgTableShow = false
          }
        });
        getPwdList(param).then(res => {
          console.log("设备", res.data);
          let data = res.data.results;
          data.forEach((ii, key) => {
            if (ii.endtime) {
              ii.endtime = Moment(ii.endtime).format("YYYY-MM-DD HH:mm:ss");
            }
          });
          self.mgData = data;
        });
        
      });
    },
    /**
     * 获取密码
     */
    getPassword() {
      // 获取使用时长 
      let self = this;
      let usehour = self.$refs.detail.form1.usehour;
      var pattern = /^[0-9]{0,2}$/;
      if (usehour > 24 || usehour <= 0 || !pattern.test(usehour)) {
        alert("使用时长字段请填写1-24的整数");
        return false;
      }
      
      // 获取密码模式
      let model;
      // 是否直接获取代码
      let par = { instanceid: self.bps.processInstID };
      queryPasswordIs(par).then(res => {
        console.log("是否直接获取代码", res.data);
        // 01 不能获取，02：需要进一步验证，03，04：直接获取
        let status = res.data.results;
        if ("01" === status) {
          if (self.mgData.length <= 0) {
            self.$alert("设备及用户选择不能为空!", { type: "error" });
            return false;
          }
          let message =
            "请确认已申请微调整并通过审批，若审批通过，则可发起通用密码获取流程，由值班经理审批。若审批未通过，则不允许申请密码实施变更";
          let title = "与变更时间不符，无法获取密码";
          self.$alert(message, title, { type: "warning" });
          return false;
        } else if ("02" === status) {
          if (self.mgData.length <= 0) {
            self.$alert("设备及用户选择不能为空!", { type: "warning" });
            return false;
          }
          let param = { applycode: self.resData.applycode };
          queryPwdModel(param).then(res => {
            console.log("流程密码获取模式", res.data);
            let state = res.data.results;
            // 1：可直接获取密码，2：审批模式但未发起申请
            // 3：已发起申请但未开始，4：已发起申请且已开始
            if (1 === state || 4 === state) {
              let ts =
                "密码开始使用时间为" +
                self.resData.usestarttime +
                "，使用时长为" +
                usehour +
                "小时！";
              self.$message.warning(ts);
              // 保存并获取密码
              self.saveAndGetPwd(usehour,'03');
            } else if (2 === state) {
              // 暂时不涉及
            } else if (3 === state) {
              self.$alter("值班经理暂未审批，请及时联系值班经理！", {
                type: "warning"
              });
              return false;
            }
          });
        } else if ("03" === status || "04" === status) { 
          let dataParm = {};
          dataParm.instanceid = self.bps.processInstID;
          dataParm.usehour = usehour;
          dataParm.applyuser = self.resData.applyuser;
          dataParm.status = status
          querySavePasswd(dataParm).then(res => {
            console.log("保存并密码", res.data);
            // 重新获取设备列表
            let paramsw = {};
            paramsw.instanceid = self.bps.processInstID;
            paramsw.showPwd = true;
            paramsw.usehour = usehour;
            paramsw.applyuser = self.resData.applyuser;
            getPwdList(paramsw).then(res => {
              console.log("设备列表", res.data);
              let resData = res.data.results
              resData.forEach((val,key) => {
                val.endtime = Moment(val.endtime).format("YYYY-MM-DD");
              })
              self.mgData = resData
            });
            // 是否隐藏按钮
            let pams1 = {instanceid:self.bps.processInstID}
            ifHiddenButton(pams1).then(res => {
                console.log("是否隐藏按钮", res.data)
                let temData = res.data.results
                // 添加 删除 按钮
                self.addAndDelBtnShow = !temData.update
                // 禁用 运维审计
                self.ifYwsjBtn = !temData.daochu;
                // 导出按钮
                self.ifExport = !temData.daochu
                
            })
          });
        }
      });
    },
    /**
     * 保存并获取密码
     */
    saveAndGetPwd(usehour,status) {
      let self = this;
      let pwsList = self.mgData;
      let msgs = [];
      pwsList.forEach((item, k) => {
        if (
          item.pwdStatus === "已检出" &&
          (item.applyer && item.applyer.indexOf(self.userId) != -1)
        ) {
          msgs.push(
            "已申请IP：" +
              item.ip +
              "，用户：" +
              item.username +
              "，不可重复申请。\n"
          );
        } else if (item.pwdStatus === "待重置") {
          msgs.push(
            "IP：" +
              item.ip +
              "，用户：" +
              item.username +
              "为待重置，不可申请，请联系密码管理系统负责人。\n"
          );
        } else if (item.pwdStatus === "用户不存在") {
          msgs.push(
            "IP：" +
              item.ip +
              "，用户：" +
              item.username +
              "用户不存在，不可申请，请联系密码管理系统负责人。\n"
          );
        }
      });
      if (msgs.length > 0) {
          self.$alert(msgs.join(""), { type: "warning" });
          return false;
        }
        let params = {};
        params.pwdList = self.mgData;
        params.instanceid = self.bps.processInstID;
        params.usehour = usehour
        params.status = status
        console.log('params', params)
        querySavePasswd(params).then(res => {
          console.log("保存big获取密码", res.data);
          self.addAndDelBtnShow = false;
           self.ifExport = false
            self.ifYwsjBtn = false
          if(res.data.status === 200) {
            // 重新获取设备列表
            let paramsw = {};
            paramsw.instanceid = self.bps.processInstID;
            paramsw.showPwd = true;
            paramsw.usehour = usehour;
            paramsw.applyuser = self.resData.applyuser;
            getPwdList(paramsw).then(res => {
              console.log("设备列表", res.data);
              let resData = res.data.results
              resData.forEach((val,key) => {
                val.endtime = Moment(val.endtime).format("YYYY-MM-DD");
              })
              self.mgData = resData
            });
          }
        });
    },
    /**
     * 添加设备
     */
    addSheBeiBtn() {
      let self = this;
      self.dialogWidth = "50%";
      self.visibleClose = true;
      self.$nextTick(_ => {
        console.log('系统名称:', self.sysName) 
        self.$refs.shebei.init({ systemname: self.sysName });
      });
    },
    /**
     * 密管列表选中行数据
     */
    hadleTableRow(row,cloumn,event) {
      this.mgRowData = row;
    },
    /**
     * 删除设备
     */
    deleteSheBeiBtn() {
      let self = this;
      console.log('mgRowData',self.mgRowData)
      // 删除
      self.mgData.splice(
        self.mgData.findIndex(item => {
          return self.mgRowData.systemname == item.systemname && self.mgRowData.ip == item.ip
        && self.mgRowData.usertype == item.usertype && self.mgRowData.username == item.username
        }),
        1
      );
      if (self.mgRowData.id) {
        let item = {};
        item.id = self.mgRowData.id;
        deleteShebei(item).then(res => {
          console.log("密管删除", res.data);
          if (200 != res.data.status) {
            self.$message.error(res.data.message);
          }
        });
      }
    },
    dialogClose() {
      this.visibleClose = false;
    },
    /**
     * dialog 确定事件
     */
    selectedNode() {
      let self = this;
      // 设备列表
      console.log("设备列表", self.$refs.shebei.rowData);
      let newData = self.$refs.shebei.rowData;
      newData.forEach((item, key) => {
        item.systemname = item.systemid;
        item.username = item.userid;
        item.pwdStatus = item.status;
        self.mgData.push(item);
      });
      console.log("设备列表1", self.mgData);
      self.dialogClose();
    },
    /**
     * 提交流程
     */
    proving(data) {
      let self = this;
      let status = new Promise(() => {});
      console.log("表单", self.form);
      if(data.conclusion === '02') {
        status = self.liuzhuan(data).then(val => {
          return val;
        });
        return status
      }
      const f1 = new Promise((resolve, reject) => {
        self.$refs.detail.$refs.form.validate(vaild => {
          if (vaild) {
            resolve();
          }
        });
      });

      const f3 = new Promise((resolve, reject) => {
        if(self.resData.changetype !== '03' && self.resData.istopwd == '是') {
          self.$refs.detail.$refs.form1.validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
        } else {
          resolve();
        }
      });
      // 关联系统验证 状态验证
      let sysData = self.$refs.detail.lData
      let sysFalg = true
      sysData.forEach((val,key)=>{
        if(!val.ischange) {
          sysFalg = false
        }
      })
      if(!sysFalg) {
        self.$message.warning('变更关联系统，变更状态不能为空')
        return false
      }
     if(self.resData.changetype !== '03' && self.resData.istopwd == '是') {
        if(!self.mgData || self.mgData.length <=0 ) {
          self.$message.warning('请选中一条记录')
          return false
        }
      }
      let pval = async () => {
        return await Promise.all([f1, f3]).then(res => {
          return self.liuzhuan(data).then(val => {
            return val;
          });
        });
      };
      
      status = pval().then(res => {
        return res;
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
      param.sysList = self.$refs.detail.lData
      console.log("流程提交", param);
      // 发起流程
      return hostHandle(param).then(res => {
        load.close()
        return res.data;
      });
    },

    /**
     *  关闭当前路由
     */
    closeRouter() {
      this.$router.push({ name: "dashboard" });
    },
    /**
     * 导出
     */
    exportExcel() {
      let self = this;
      let data = { instanceid: self.bps.processInstID,showPwd:true };
      data = common.paramsFormat(data);

      exportExcel(data).then(res => {
        common.download(res.data, self, "设备列表.xls");
      });
    },
    /**
     * 生成运维工单
     */
    generateYwOrder() {
      let self = this;
      let data = {};
      data.pwdList = self.mgData;
      data.instanceid = self.bps.processInstID;
      if(!self.mgData || self.mgData.length <=0 ) {
        self.$message.warning('请选中一条记录')
        return false
      }
      let bol_falg = true
      self.mgData.forEach((val)=>{
        if(!val.pwd) {
          bol_falg = false
        }
      })
      if(!bol_falg) {
        self.$message.warning('获取到的密码不能为空，请先点击“密码获取”按钮后再上传运维审计！')
        return false
      }
      scywsjgd(data).then(res => {
          console.log("生成运维工单", res.data);
          if(200 !== res.data.status) {
            self.$message.warning(res.data.message)
            return false
          }
          self.$message.success(res.data.message)
      });
      
    },
    /**
     * 通用密码获取
     */
    generalBtn() {
      let self = this
      if(!self.mgData || self.mgData.length <= 0) {
        self.$message.warning('至少选择一个系统设备')
        return false
      }
      // 异步请求参数获取密钥
      getGeneralPassworkKey().then(res =>{
        let param = '/com.example.ppm.project.other.pwd.loginForPwdApplyFh.flow?md5key=' + res.data.results.token
          + '&userid=' + res.data.results.applyuser
          console.log("url:",res.data.results.tyUrl + param)
        window.open(res.data.results.tyUrl + param)
      })
      
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


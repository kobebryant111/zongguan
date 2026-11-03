<!-- 分行业务意向上线-二级部经理审批 -->
<template>
  <div>
    <div class="head-container">
      <!-- bin -->
      <div class="nav">
        <el-row>
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="7">
                <p class="nav_name">分行业务意向上线申请审批(归档)</p>
              </el-col>
            </div>
            <div class="wrapper_right">
              <el-col :span="1.5">
                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
              </el-col>
            </div>
          </div>
        </el-row>
      </div>
    </div>
    <!-- bin改 -->
    <div class="contentMainPrice">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">上线申请</p>
      </div>
      <div class="content">
        <el-form :model="onlineinfo" ref="formYpz" size="small">
          <div class="row_1">
            <el-row type="flex">
              <el-col :span="10">
                <p>上线申请人:</p>
                <el-form-item>
                  <el-input autosize v-model="onlineinfo.empname" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="5">
              </el-col>
              <el-col :span="10">
                <p>申请日期:</p>
                <el-form-item>
                  <el-date-picker type="datetime" autosize v-model="onlineinfo.apply" value-format="yyyy-MM-dd HH:mm:ss" :disabled="true"></el-date-picker >
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex">
              <el-col :span="24">
                <p>相关项目:</p>
                <el-form-item>
                  <el-input autosize v-model="onlineinfo.projectname" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex">
              <el-col :span="10">
                <p>关联主系统:</p>
                <el-form-item>
                  <el-input autosize v-model="onlineinfo.sysname" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
               <el-col :span="5">
              </el-col>
              <el-col :span="10">
                <p>牵头业务部门:</p>
                <el-form-item>
                  <el-input autosize v-model="onlineinfo.location" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex">
              <el-col :span="10">
                <p>上线范围:</p>
                <el-form-item>
                  <el-select autosize v-model="onlineinfo.orgarea" placeholder="上线范围" :disabled="true">
                    <el-option v-for="item in orgareaData" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
               <el-col :span="5">
              </el-col>
              <el-col :span="10">
                <p>上线日期:</p>
                <el-form-item>
                  <el-date-picker autosize v-model="onlineinfo.planonline" value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex">
              <el-col :span="24">
                <p>上线内容概述:</p>
                <el-form-item>
                  <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="onlineinfo.content" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex">
              <el-col :span="24">
                <p>备注:</p>
                <el-form-item>
                  <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="onlineinfo.testconclusion" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 申请信息 -->
          <div class="row_2 row_2_interview_resultEntry">
            <div class="contentTwo_title">
              <p class="contentTwo_title_p">申请信息</p>
            </div>
            <el-row>
              <el-col :span="24">
                <div class="row_2_content">
                  <el-table :data="projectData">
                    <el-table-column type="index" label="序号" width="50" align="center" header-align="center"></el-table-column>
                    <el-table-column prop="subprojectno" label="项目编号" width="80" align="center" header-align="center"></el-table-column>
                    <el-table-column prop="subprojectname" label="关联系统" width="200" align="center" header-align="center"></el-table-column>
                    <el-table-column prop="vel" label="运维等级" width="50" align="center" header-align="center"></el-table-column>
                    <el-table-column prop="subtype" label="项目类型" width="100" align="center" header-align="center" :formatter="subtypeFormatter"></el-table-column>
                    <el-table-column prop="coupledsysname" label="是否配合类" width="50" align="center" header-align="center"></el-table-column>
                    <el-table-column prop="safetytestname" label="互联网端功能变更场景" width="80" align="center" header-align="center"></el-table-column>
                    <el-table-column prop="empname" label="项目经理" width="80" align="center" header-align="center" :formatter="empnameFormatter"></el-table-column>
                    <el-table-column prop="org" label="上线范围" width="80" align="center" header-align="center" :formatter="subOrgareaFormatter"></el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>

          <!-- 测试结果 -->
          <div class="row_2 row_2_interview_resultEntry">
            <div class="contentTwo_title">
              <p class="contentTwo_title_p">测试报告</p>
            </div>
            <div class="row_1">
              <el-row type="flex">
                <el-col :span="24">
                  <p>测试内容:</p>
                  <el-form-item>
                    <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="onlineinfo.testcontent" :disabled="true"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row type="flex">
                <el-col :span="24">
                  <p>测试结论:</p>
                  <el-form-item>
                    <el-select autosize v-model="onlineinfo.teststatus" placeholder="测试结论" :disabled="true">
                      <el-option v-for="item in teststatusData" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <el-row>
              <el-col :span="24">
                <div class="row_2_content">
                  <el-table :data="testFileList">
                    <el-table-column prop="docname" label="文件名称" width="80" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="creators" label="上传人" width="50"></el-table-column>
                    <el-table-column prop="createtime" label="上传时间" :show-overflow-tooltip="true" width="150"></el-table-column>
                    <el-table-column prop="cz" label="操作" width="76">
                      <template slot-scope="scope">
                        <a href="javascript:void();" @click="openNtkoWindow(scope.row.documentno, 'testfile')" style="color: rgba(251, 164, 47, 1)">预览</a>
                        <a href="javascript:void();" @click="jlDownliad(scope.row.documentno)" style="color: rgba(251, 164, 47, 1)">下载</a>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>

      <!-- 操作日志 -->
      <div class="czLog">
        <flow-logs-gd :processInstId="flowItem.instanceid" ref="log"></flow-logs-gd>
      </div>
    </div>

    <!-- 空格占位 -->
    <div class="blank"></div>

    <!-- 预览图片弹窗 -->
    <el-dialog title="" :visible.sync="ylDialog">
        <div class="ylDialogCon">
            <img :src="ylPhotoUrl"  class="ylPhoto">
        </div>
    </el-dialog>
  </div>
</template>
<script>
  import flowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
  import buttonCommon from '@/components/eleButton/buttonCommon'
  import approveBottom from "@/views/plugins/flowPlug/approveBottom";
  import Moment from 'moment';
  import { getDicEntry } from "@/api/eosDictEntry";
  import common from "@/api/common";
  let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
  import {getOnlineInfo, queryPpmProjectList, approveFlowByEJBManagers } from "@/api/onlineApi/newfhxmsxFJH.js"
  export default {
    name: "onlinefhGd",
    components: { flowLogsGd, buttonCommon, approveBottom },
    data() {
      return {
        onlineinfo: {
           userid: '',
           apply: '',
           projectname: '',
           sysname: '',
           busideptname: '',
           orgarea: '',
           planonline: '',
           content: '',
           testconclusion: '',
          meet: '',
          remarks: '',
          fileList: [],
          testcontent: '',
          teststatus: '',
        },
        flowItem: {}, // 流程对象
        buttonClose: '关 闭', //取消按钮名字
        size: 'mini',  //按钮大小
        successMessage: '', // 成功消息提示
        errorMessage: '', //失败消息提示
        orgareaData: [], //上线范围
        teststatusData: [], //测试结论
        projectData: [],//项目列表
        testFileList: [], //测试文件列表
        ylDialog: false, //是否显示预览图片弹窗
        ylPhotoUrl: '', //预览图片地址
        subOrgareaData:[],
      };
    },
    methods: {
      init(params) {
        console.log("params:", params);
        const self = this;
        self.flowItem = params;

        // 流程日志
        self.$refs.log.init2(params);

        self.$nextTick(_ => {
          getOnlineInfo({ instanceid: params.instanceid}).then(res => {
            if (res.data.status != 200) {
              self.$alert(res.data.message, { type: "error" });
              return false;
            }
            self.onlineinfo = res.data.results;
            self.testFileList = res.data.results.doclist;

            let data = common.paramsFormat(self.onlineinfo);
            queryPpmProjectList(data).then(res => {
            if (res.data.status != 200) {
              self.$alert(res.data.message, { type: "error" });
              return false;
            }

            self.projectData = res.data.results;
          });
          });
        });

        let eos = { dicttypeid: 'BUSI_ORGAREA,ONLINE_TESTSTATUS,BUSI_SUB_ORGAREA' }
          getDicEntry(eos).then(res => {
              let data = res.data.results
              //上线范围
              self.orgareaData = data.filter(ii => {
                  return ii.dicttypeid === "BUSI_ORGAREA";
              });
              // 测试结论
              self.teststatusData = data.filter(ii => {
                  return ii.dicttypeid === "ONLINE_TESTSTATUS";
              });
              //子项目上线范围
              self.subOrgareaData = data.filter(ii => {
                  return ii.dicttypeid === "BUSI_SUB_ORGAREA";
              });
          }) 
      }, // init end

      // 数组去重
      filArr(ele) {
        let priceArr = [];
        let eleFilter = [];
        if (ele == []) {
          return;
        } else {
          for (var i = 0; i < ele.length; i++) {
            if (!(eleFilter.includes(ele[i].wbsid + '-' + ele[i].position))) {
              eleFilter.push(ele[i].wbsid + '-' + ele[i].position);
              priceArr.push(ele[i].upData);
            }
          }

        }
        this.priceConsult = priceArr;
      },

    /**
     * 项目类型转化
     */
    subtypeFormatter: function(row, cloumn) {
      let resVal = "";
      var prono = row.subprojectno.split('_');
      if(prono[1] == "001"){
        resVal = "主项目";
      }else{
        resVal = "子项目";
      }
          
			return resVal;
    },
    /**
     * 上线范围转化
     */
    subOrgareaFormatter: function(row, cloumn) {
      const self = this;
      let obj = {};
      obj = self.subOrgareaData.find((item)=>{
        return item.dictid = row.org;
      });
      
      let dictname = obj != null ? obj.dictname : "";
      return obj.dictname;
    },
    empnameFormatter: function(row, cloumn) {
      return row.empname + "(" + row.manauserid + ")";
    },
    
      /**
       * 流转
       */
      liuzhuan: function (params) {
        const loading = common.loading(this);
        const thit = this;
        thit.flowItem.conclusion = params.conclusion;
        thit.flowItem.suggest = params.suggest;
        let folw = { bps: thit.flowItem };
        let newObj = Object.assign(folw);
        return approveFlowByEJBManagers(newObj)
          .then(res => {
            loading.close();
            return res.data
          })
          .catch(error => {
            thit.$alert(error, { type: "error" });
          });
      }, // 流转 end

      // 流转并验证
      proving(data) {
        let status = new Promise(() => { });
        if (data.conclusion == "01") {
          this.$refs['formYpz'].validate((valid) => {
            if (valid) {
              status = this.liuzhuan(data).then((val) => {
                return val;
              });
            } else {
              this.errorMessage = '操作失败,请填写必填项'
              this.messageError();
              return false;
            }
          })
        } else {
          status = this.liuzhuan(data).then((val) => {
            return val;
          });
        }
        return status;
      },

      // 文档下载
      jlDownliad(docId) {
        const self = this;
        common.downloadJl(docId, self);
      },

      // 成功消息提示
      messageSuccess() {
        this.$message({
          message: this.successMessage,
          type: 'success'
        })
      },

      // 失败消息提示
      messageError() {
        this.$message.error(this.errorMessage);
      },

      //ntko插件预览
      openNtkoWindow(objectId, mark) {
          if (mark == 'testfile') {
              let formJudge = this.testFileList.docname.split('.')[this.testFileList.docname.split('.').length - 1];
              if (formJudge == 'jpg' || formJudge == 'png' || formJudge == 'PNG') {
                  this.ylDialog = true;
                  let pos = this.testFileList.tag1.lastIndexOf('/');
                  let str = this.testFileList.tag1.substr(0, pos);
                  if (this.testFileList.tag2 != undefined) {
                      this.ylPhotoUrl = str + '/' + this.testFileList.tag2;
                  } else if (this.testFileList.documentno != undefined) {
                      this.ylPhotoUrl = str + '/' + this.testFileList.documentno;
                  } else {
                      this.ylPhotoUrl = this.testFileList.tag1;
                  }
              } else if (formJudge == 'zip' || formJudge == 'rar' || formJudge == 'txt') {
                  this.$message.error('不能预览压缩文件或txt文件格式');
              } else {
                  this.ntkoYl(objectId);
              }
          }
      },

      // ntko预览方法
      ntkoYl(objectId) {
          var url = window.location.href;
          let ntkoed = ntkoBrowser.ExtensionInstalled();
          if (ntkoed) {
              ntkoBrowser.openWindow(
                  url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
              );
          } else {
              window.open(
                  url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
              );
          }
          // 注册回调方法
          window.ntkoCloseEvent = function () {
              alert("跨浏览器插件应用程序窗口已关闭!");
          };
      },

      // 关闭按钮
      close() {
        // this.$router.back();
        this.$router.push({ name: 'dashboard' });
      }
    }
  };
</script>

<style scoped>
  /* bin修改 */
  .contentMainPrice {
    width: 100%;
    height: 100%;
    font-size: 16px;
    padding: 10px 20px;
  }

  /* 导航菜单栏 */
  .nav {
    width: 100%;
    /* height: 30px; */
    background-color: #ffffff;
  }

  .nav .nav_name {
    width: 240px;
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

  /* 操作日志 */
  .contentLog {
    margin-top: 15px;
  }

  .content {
    width: 100%;
    font-size: 16px;
    /* background-color: #ffffff; */
    /* padding: 15px 20px; */
  }

  .content p {
    color: #262626;
    font-size: 14px;
    /* margin: 12px 0; */
  }

  .content .row_2_detail {
    font-size: 16px;
    font-weight: 600;
  }

  .contentTwo_title {
    height: 60px;
    line-height: 60px;
    background-color: #ffffff;
    padding: 0 25px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.15);
  }

  .content .contentTwo_title_p {
    color: #262626;
    font-size: 16px;
    font-weight: 600;
  }

  .contentMainPrice .row_1 {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    /* padding: 0 20px; */
  }

  .contentMainPrice .row_2_content {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    /* margin-top: 15px; */
  }

  .contentMainPrice .row_2_content_2 {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    /* margin-top: 15px; */
  }

  .czLog {
    overflow: hidden;
  }

  .contentTwo_title_2 {
    height: 60px;
    line-height: 60px;
    background-color: #ffffff;
    padding: 0 25px;
  }

  .contentTwo_title_2 .contentTwo_title_p_2 {
    color: #262626;
    font-size: 16px;
    font-weight: 600;
    margin: 0;
  }

  /* 空格占位 */
  .blank {
    width: 100%;
    height: 100px;
  }

  .bottom_zb {
    position: absolute;
    bottom: 0;
    width: 100%;
  }

  /* 授权弹窗 */
  .power_dia {
    font-size: 16px;
    color: #262626;
  }

  .approv_dia {
    font-size: 14px;
    margin-top: 8px;
  }

  .approv_dia_con1,
  .approv_dia_con2 {
    margin-top: 20px;
    color: #262626;
  }

  .ryzrApprove_dialog {
    overflow: hidden;
  }

  .dia_button {
    float: right;
    margin: 85px 0 15px 0;
  }

  .butt_cancel {
    margin-right: 10px;
  }

  /* 退回弹窗 */
  .retreatCon {
    display: flex;
    justify-content: space-around;
    /* flex-direction: row; */
  }

  .retreatCon_2 {
    width: 87%;
  }

  .dia_button_type2_zb {
    float: right;
    margin: 25px 0 15px 0;
  }

  .retreat_dia_titlte_zb {
    font-size: 16px;
    color: #262626;
    margin-top: 4px;
  }

  .retreat_dia_con_zb {
    margin-top: 20px;
  }

  /* 退回弹窗element警告图标 */
  .box_retreat_zb {
    color: rgba(251, 164, 47, 1);
    font-size: 180%;
  }

  /* 预览弹窗内容居中 */
    .ylDialogCon {
        text-align: center;
    }

    /* 预览图片大小 */
    .ylPhoto {
        width: 600px;
        width: 500px;
    }
</style>

<style>
  /* 禁用的输入框 */
  .contentMainPrice .el-select {
    display: block;
  }

  /* table表格修改 */

  .row_2_content .el-table th {
    background: rgba(250, 250, 250, 1);
  }

  .row_2_content .el-table thead {
    font-size: 14px;
    color: rgba(38, 38, 38, 1);
    font-weight: 400;
  }

  .row_2_content .el-table--medium th {
    padding: 0;
  }

  .row_2_content .el-table--medium td {
    padding: 0;
  }


  .row_2_content .el-table th {
    padding: 10px 0;
  }

  .row_2_content_2 .el-table thead {
    font-size: 14px;
    color: rgba(38, 38, 38, 1);
    font-weight: 400;
  }

  .row_2_content_2 .el-table th {
    background: rgba(250, 250, 250, 1);
  }

  /* form表单样式修改 */
  .row_2_content .el-form-item {
    margin: 16px 0;
  }

  /* 计数器修改 */
  .row_2_interview_resultEntry .el-input-number--medium {
    width: 130px;
  }
  
</style>
<!-- 分行业务意向上线-二级部经理审批 -->
<template>
  <div>
    <div class="head-container">
      <!-- bin -->
      <div class="nav">
        <el-row>
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="5">
                <p class="nav_name">分行业务意向上线验证审批</p>
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
        <p class="contentTwo_title_p">上线验证</p>
      </div>
      <div class="content">
        <el-form :model="reviewinfo" ref="formYpz" size="small">
          <div class="row_1">
            <el-row type="flex">
              <el-col :span="24">
                <p>相关项目:</p>
                <el-form-item>
                  <el-input autosize v-model="reviewinfo.projectname" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex">
              <el-col :span="10">
                <p>上线日期:</p>
                <el-form-item>
                  <el-date-picker autosize v-model="reviewinfo.chgdate" value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="5">
              </el-col>
              <el-col :span="10">
                <p>上线后验证<span class="jkt-tag-red">*</span>:</p>
                <el-form-item>
                  <el-select autosize v-model="reviewinfo.reviewres" placeholder="上线后验证" :disabled="true" @change="onReviewsValueChange">
                    <el-option v-for="item in reviewresData" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex" v-show="isshow">
              <el-col :span="10">
                <p>计划验证日期<span class="jkt-tag-red">*</span>:</p>
                <el-form-item>
                  <el-date-picker autosize v-model="reviewinfo.jhyzdate" value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="15">
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <p>备注:</p>
                <el-form-item>
                  <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="reviewinfo.remark" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 项目评价 -->
          <div class="row_2 row_2_interview_resultEntry">
            <div class="contentTwo_title">
              <p class="contentTwo_title_p">项目评价</p>
            </div>
            <div class="row_1">
              <el-row>
                <el-col :span="24">
                  <p>对本项目总体评价<span class="jkt-tag-red">*</span>:</p>
                  <el-form-item prop="xmpj">
                    <el-radio-group v-model="reviewinfo.xmpj" :disabled="true">
                      <el-radio label="01" key="01">好</el-radio>
                      <el-radio label="02" key="02">中</el-radio>
                      <el-radio label="03" key="03">差</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row type="flex">
                <el-col :span="24">
                  <p>评价内容:</p>
                  <el-form-item>
                    <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="reviewinfo.pjcont" :disabled="true"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>

          <!-- 项目经理评价 -->
          <div class="row_2 row_2_interview_resultEntry">
            <div class="contentTwo_title">
              <p class="contentTwo_title_p">项目经理评价</p>
            </div>
            <div class="row_1">
              <el-row>
                <el-col :span="24">
                  <p>对项目经理总体评价<span class="jkt-tag-red">*</span>:</p>
                  <el-form-item prop="xmjlpj">
                    <el-radio-group v-model="reviewinfo.xmjlpj" :disabled="true">
                      <el-radio label="01">好</el-radio>
                      <el-radio label="02">中</el-radio>
                      <el-radio label="03">差</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row type="flex">
                <el-col :span="24">
                  <p>评价内容:</p>
                  <el-form-item>
                    <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="reviewinfo.jlpjcont" :disabled="true"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
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

    <!-- 底部操作栏 -->
    <div class="bottom_zb">
    </div>

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
  import buttonCommon from "@/components/eleButton/buttonCommon";
  import approveBottom from "@/views/plugins/flowPlug/approveBottom";
  import Moment from 'moment';
  import { getDicEntry } from "@/api/eosDictEntry";
  import common from "@/api/common";
  let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
  import {queryDetailByInstanceid, approveReviewByBusjl } from "@/api/onlineApi/newfhsxyzFJH.js"
  export default {
    name: "onlinefhGd",
    components: { flowLogsGd, buttonCommon, approveBottom },
    data() {
      return {
        reviewinfo: {
          projectname: '',
          chgdate: '',
          reviewres: '',
          jhyzdate: '',
          remark: '',
          xmpj: '',
          pjcont: '',
          xmjlpj: '',
          jlpjcont: ''
        },
        flowItem: {}, // 流程对象
        buttonClose: '关 闭', //取消按钮名字
        size: 'mini',  //按钮大小
        successMessage: '', // 成功消息提示
        errorMessage: '', //失败消息提示
        ylDialog: false, //是否显示预览图片弹窗
        ylPhotoUrl: '', //预览图片地址
        reviewresData:[//上线后验证
          {
            dictid: '01',
            dictname: '通过'
          },
          {
            dictid: '02',
            dictname: '不通过'
          },
          {
            dictid: '03',
            dictname: '延期验证'
          }
        ],
        isshow:false
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
          queryDetailByInstanceid({ instanceid: params.instanceid}).then(res => {
            if (res.data.status != 200) {
              self.$alert(res.data.message, { type: "error" });
              return false;
            }
            self.reviewinfo = res.data.results;

            if(self.reviewinfo.reviewres == "03") {
              self.isshow = true;
            }
          });
        });
        
        let eos = { dicttypeid: 'BUSI_ORGAREA,ONLINE_TESTSTATUS' }
          getDicEntry(eos).then(res => {
              let data = res.data.results
              //上线范围
              self.orgareaData = data.filter(ii => {
                  return ii.dicttypeid === "BUSI_ORGAREA";
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
       * 流转
       */
      liuzhuan: function (params) {
        const loading = common.loading(this);
        const self = this;
        let folw = { bps: self.flowItem };
        let newObj = Object.assign(folw, self.reviewinfo);
        return approveReviewByBusjl(newObj)
          .then(res => {
            loading.close();
            return res.data
          })
          .catch(error => {
            self.$alert(error, { type: "error" });
          });
      }, // 流转 end

      /**保存 */
      save:function (params) {

      },

      /**授权 */
      shouquan:function (params) {

      },

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

      onReviewsValueChange(value) {
        if (value == "03") {
          this.isshow = true;
        } else {
          this.isshow = false;
        }
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
    width: 220px;
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
    padding-left: 20px;
    padding-bottom: 10px;
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

  .jkt-tag-red {
    color: red;
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
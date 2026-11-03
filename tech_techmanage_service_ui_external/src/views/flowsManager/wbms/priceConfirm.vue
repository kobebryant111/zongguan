<!-- 单价确认 -->
<template>
  <div>
    <!-- 头部 -->
    <div class="head-container">
      <!-- <div>
        <span>人力需求</span>
      </div>-->
      <!-- 头部 -->
      <!-- bin -->
      <div class="nav">
        <el-row>
          <!-- <el-col :span="24"> -->
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="1.5">
                <p class="nav_name">外包面试审批</p>
              </el-col>
            </div>
            <div class="wrapper_right">
              <el-col :span="1.5">
                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
              </el-col>
            </div>
          </div>
          <!-- </el-col> -->
        </el-row>
      </div>
    </div>
    <!-- 主体 -->
    <div class="main-app-container"></div>
    <!-- 单价确认 -->
    <div class="contentMainPrice">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">面试信息</p>
      </div>
      <div class="content">
        <!-- 表第一行 -->
        <el-form :model="tableModel" ref="formYpz" size="small">
          <div class="row_1">
            <el-row type="flex" justify="space-between">
              <el-col :span="7">
                <p>部门</p>
                <el-select v-model="msgdeptname" disabled>
                  <el-option :value="msgdeptname"></el-option>
                </el-select>
              </el-col>
              <el-col :span="7">
                <p>人力需求</p>
                <el-select v-model="rlIds" placeholder="请选择人力需求" :disabled="true">
                  <el-option :value="rlIds"></el-option>
                </el-select>
              </el-col>
              <el-col :span="7">
                <p>面试官</p>
                <el-select v-model="msgNames" placeholder="请选择面试官" disabled>
                  <el-option :value="msgNames"></el-option>
                </el-select>
              </el-col>
            </el-row>

            <el-row type="flex" justify="space-between">
              <el-col :span="7">
                <p>是否符合要求 *</p>
                <el-form-item prop="meet" :rules="[{ required: true, message: '请选择是否符合要求', trigger: 'change'}]">
                  <el-radio-group v-model="tableModel.meet" :disabled="true">
                    <el-radio v-for="item in meet_options" :label="item.label" :key="item.label">{{item.value}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <div v-if="tableModel.meet == '0'" key="1">
                  <p>原因 *</p>
                  <el-form-item prop="remarks" :rules="[{ required: true, message: '请填写原因', trigger: 'blur'}]">
                    <el-input type="textarea" autosize v-model="tableModel.remarks" :disabled="true"></el-input>
                  </el-form-item>
                </div>
                <div v-else key="2">
                  <p>原因</p>
                  <el-form-item>
                    <el-input type="textarea" autosize v-model="tableModel.remarks" :disabled="true"></el-input>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="7"></el-col>
            </el-row>
          </div>

          <!-- 应聘者信息 -->
          <div class="row_2 row_2_interview_resultEntry">
            <div class="contentTwo_title">
              <p class="contentTwo_title_p">应聘者信息</p>
            </div>
            <el-row>
              <el-col :span="24">
                <div class="row_2_content">
                  <el-table :data="tableModel.ypzData">
                    <el-table-column prop="msname" label="姓名" width="80" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="sexName" label="性别" width="50"></el-table-column>
                    <el-table-column prop="birthday" label="出生日期" width="100"></el-table-column>
                    <el-table-column prop="outsourcName" label="外包商" :show-overflow-tooltip="true" width="220"></el-table-column>
                    <el-table-column prop="positionName" label="人员类型" width="100"></el-table-column>
                    <el-table-column prop="positionName" label="人员属性" width="80">
                      <template slot-scope="scope">
                        <span v-if="scope.row.researchers == 1">人力外包</span>
                        <span v-else-if="scope.row.researchers == 2">项目人力</span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="workyears" label="年限" width="50"></el-table-column>
                    <el-table-column prop="suggestlevel" label="等级" width="100">
                      <template slot-scope="scope">
                          <span v-if="scope.row.suggestlevel == 1">1 (1-3年)</span>
                          <span v-else-if="scope.row.suggestlevel == 2">2 (3-5年)</span>
                          <span v-else-if="scope.row.suggestlevel == 3">3 (5-7年)</span>
                          <span v-else-if="scope.row.suggestlevel == 4">4 (7年以上)</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="简历" width="100">
                      <template slot-scope="scope">
                        <a href="javascript:;" @click="openNtkoWindow(scope.row.jl)" style="color: rgba(251, 164, 47, 1)">预览</a>
                        <a href="javascript:;" @click="jlDownliad(scope.row.jl)" style="color: rgba(251, 164, 47, 1)">下载</a>
                      </template>
                    </el-table-column>
                    <el-table-column prop="mspy" label="评语" width="200" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="score" label="成绩" width="60"></el-table-column>
                    <el-table-column prop="interviewresult" label="结果" width="80">
                      <template slot-scope="scope">
                        <span v-for="(item, index) of scoreData" :key="index">
                          <span v-if="item.dictid == scope.row.interviewresult">{{ item.dictname}}</span>
                        </span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="suggestprice" width="150">
                      <template slot="header" slot-scope="scope">
                        <span>建议单价 *</span>
                        <el-tooltip placement="top">
                          <i class="el-icon-question" style="cursor: pointer;margin-left: 6px;"></i>
                          <div slot="content">人员初始定价规则：
                            <br>人月单价根据工作年限（1/3/5/7年以上）分为四级，每级由最低和最高价格组成区间价格。
                            <br>金融科技部人力外包入场初始定价规则如下：
                            <br>⑴如无特殊情况，人员的初始等级主要由相关工作年限决定，1/2/3/4级分别对应1-3/3-5/5-7/7年以上，人员初始定价为对应等级对应岗位的区间最低价。
                            <br>⑵如供应商提出提高人员初始定价，若该人员较优秀，或工作年限接近下一等级，经面试官一致通过，则其初始定价可以由当前等级最低价上浮，但需低于当前等级的上限。
                            <br>⑶对于实际水平较低的人员，经面试官一致通过，其初始等级可以降低一级或几级。
                          </div>
                        </el-tooltip>
                      </template>
                      <template slot-scope="scope">
                        <!-- 等级1 -->
                        <el-form-item :prop="`ypzData.${scope.$index}.suggestprice`" :rules="scope.row.interviewresult == 1 ? [{ required: false, message: '请选择单价'}] : rulePrice.suggestprice"
                          v-if="scope.row.suggestlevel == '1'">
                          <el-input-number size="medium" v-model="scope.row.suggestprice" controls-position="right"
                            :step="500" :disabled="scope.row.interviewresult == '0' || scope.row.researchers == '2'" @change="inputNumchange(scope.$index, scope.row.suggestprice, scope.row.suggestprice1)"></el-input-number>
                        </el-form-item>

                        <!-- 等级2 -->
                        <el-form-item :prop="`ypzData.${scope.$index}.suggestprice`" :rules="scope.row.interviewresult == 1 ? [{ required: false, message: '请选择单价'}] : rulePrice.suggestprice"
                          v-else-if="scope.row.suggestlevel == '2'">
                          <el-input-number size="medium" v-model="scope.row.suggestprice" controls-position="right"
                            :step="500" :disabled="scope.row.interviewresult == '0' || scope.row.researchers == '2'" @change="inputNumchange(scope.$index, scope.row.suggestprice, scope.row.suggestprice1)"></el-input-number>
                        </el-form-item>
                        <!-- 等级3 -->
                        <el-form-item :prop="`ypzData.${scope.$index}.suggestprice`" :rules="scope.row.interviewresult == 1 ? [{ required: false, message: '请选择单价'}] : rulePrice.suggestprice"
                          v-else-if="scope.row.suggestlevel == '3'">
                          <el-input-number size="medium" v-model="scope.row.suggestprice" controls-position="right"
                            :step="500" :disabled="scope.row.interviewresult == '0' || scope.row.researchers == '2'" @change="inputNumchange(scope.$index, scope.row.suggestprice, scope.row.suggestprice1)"></el-input-number>
                        </el-form-item>
                        <!-- 等级4 -->
                        <el-form-item :prop="`ypzData.${scope.$index}.suggestprice`" :rules="scope.row.interviewresult == 1 ? [{ required: false, message: '请选择单价'}] : rulePrice.suggestprice"
                          v-else>
                          <el-input-number size="medium" v-model="scope.row.suggestprice" controls-position="right"
                            :step="500" :disabled="scope.row.interviewresult == '0' || scope.row.researchers == '2'" @change="inputNumchange(scope.$index, scope.row.suggestprice, scope.row.suggestprice1)"></el-input-number>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="reason" label="修改理由">
                      <template slot-scope="scope">
                        <div v-if="scope.row.interviewresult == 1 && scope.row.suggestprice != scope.row.suggestprice1 && scope.row.researchers == '1'"
                          key="1">
                          <el-form-item :prop="`ypzData.${scope.$index}.reason`" :rules="[{ required: true, message: '请输入修改理由', trigger: 'change' }]">
                            <el-input v-model="scope.row.reason" placeholder="请输入"></el-input>
                          </el-form-item>
                        </div>
                        <div v-else key="2">
                          <el-form-item>
                            <el-input v-model="scope.row.reason" placeholder :disabled="true"></el-input>
                          </el-form-item>
                        </div>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
            <div class="contentTwo_title_2 flex_box">
              <p class="contentTwo_title_p_2">单价参考</p>
            <el-link type="warning" :underline="false" style="margin-left:10px" @click="download">下载</el-link>
            </div>
            <el-row>
              <el-col>
                <div class="row_2_content_2">
                  <el-table :data="priceConsult" style="width: 100%">
                    <el-table-column prop="outsourcname" label="公司" :show-overflow-tooltip="true" width="220"></el-table-column>
                    <el-table-column prop="positionName" label="人员类型" :show-overflow-tooltip="true" width="90"></el-table-column>
                    <el-table-column label="一档(1-3年)" :show-overflow-tooltip="true" width="170">
                      <template slot-scope="scope">
                        <span>{{scope.row.firststartprie}} - {{scope.row.firstendprie}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="二档(3-5年)" width="170" :show-overflow-tooltip="true">
                      <template slot-scope="scope">
                        <span>{{scope.row.secondstartprie}} - {{scope.row.secondendprie}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="三档(5-7年)" width="170" :show-overflow-tooltip="true">
                      <template slot-scope="scope">
                        <span>{{scope.row.thirdstartprie}} - {{scope.row.thirdendprie}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="四档(7年以上)" width="170" :show-overflow-tooltip="true">
                      <template slot-scope="scope">
                        <span>{{scope.row.fourthstartprie}} - {{scope.row.fourthendprie}}</span>
                      </template>
                    </el-table-column>

                    <el-table-column></el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>

      <!-- 操作日志 -->
      <div class="czLog">
        <flow-logs :processInstId="instanceid" ref="log"></flow-logs>
      </div>
    </div>

    <!-- 空格占位 -->
    <div class="blank"></div>

    <!-- 底部操作栏 -->
    <div class="bottom_zb">
      <approve-bottom :proving="proving" :flowItem="flowItem"></approve-bottom>
    </div>
  </div>
</template>
<script>
  import flowLogs from "@/views/plugins/camunda/FlowLogs";
  import {
    getInterviewScore,
    getOutsourcPrice,
    doPriceConfirm,
    queryManpowerdemands,
    download,
    rlxqQueryDetail,
    priceDownload
  } from "@/api/integrated/wbms";
  import departmentTwo from "@/views/plugins/components/DepartmentTwo";
  import employee from "@/views/plugins/components/Employee";
  import { getDicEntry } from "@/api/eosDictEntry";
  import flowBtn from "@/views/plugins/camunda/FlowBtn";
  import common from "@/api/common";
  import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
  import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
  import approveBottom from "@/views/plugins/camunda/approveBottom";
  import Moment from "moment";
  let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
    .ntkoBrowser;
  import { checkedIsBlack } from "@/api/integrated/wbHmd";
  import { checkedExistEntry } from "@/api/integrated/newWbrz";
  export default {
    name: "PriceConfirm",
    components: {
      flowLogs,
      departmentTwo,
      employee,
      flowBtn,
      buttonOrange,
      buttonCommon,
      approveBottom
    },
    data() {
      return {
        ypzData: [], // 应聘者数据集合
        tableModel: {
          meet: "",
          remarks: "",
          ypzData: []
        },
        msgData: [], // 面试官数据集合
        msgdeptname: "", //部门名字
        scoreData: [], // 面试结果数据集合
        pcData: [], // 报价单位
        instanceid:"",
        flowItem: {}, // 流程对象
        objectcode: "6000000300",
        objectgrade: "2",
        mpOpts: [], // 人力需求集合数据
        rlIds: "", //人力需求绑定值
        msgdeptno: "", // 部门编号
        msgNames: "", // 面试官名称
        buttonClose: "关 闭", //取消按钮名字
        size: "mini", //按钮大小
        successMessage: "", // 成功消息提示
        errorMessage: "", //失败消息提示
        priceConsult: [], //单价参考
        rulePrice: {
          suggestprice: [{ required: false, message: "", trigger: "blur" }]
        },
        meet_options: [
          {
            value: "是",
            label: "1"
          },
          {
            value: "否",
            label: "0"
          }
        ] //是否符合要求
      };
    },
    methods: {
      init(params) {
        const self = this;
        self.flowItem = params; 
        self.instanceid = params.processInstanceId;
        self.flowItem.processInstId = self.instanceid;
        self.$nextTick(_ => {
          getInterviewScore({ instanceid: self.instanceid }).then(res => {
            if (res.data.status != 200) {
              self.$alert(res.data.message, { type: "error" });
              return false;
            }
            const data = res.data.results;
            var reg = /([^-]+)$/;
            self.msgdeptname = data.erList[0].msgdeptname.match(reg)[1];
            self.getRlxq(data.eeList[0].rlId);
            self.msgdeptno = data.erList[0].msgdeptno;

            let msgId = [],
              msgName = [];
            // 获取面试官id和名称
            data.erList.forEach(ii => {
              // msgId.push(ii.msgid);
              msgName.push(ii.msgname);
            });
            // self.msgIds = msgId.join(",");
            self.msgIds = data.erList[0].msgid;
            self.msgNames = msgName.join(",");
            let temps = [];
            let counts = 0;
            // 外包商单价
            data.eeList.forEach(item => {
              setTimeout(() => {
                this.searchMsinfo(item.idcard, item.msname);
              }, 0);
              item.reason = "";
              let params = { id: item.wbsid, position: item.position };
              getOutsourcPrice(params)
                .then(res => { 
                  const priceData = res.data.results; 
                  if (priceData !== undefined) {
                    item.upData = priceData;
                    temps.push(item);
                  }
                })
                .then(() => {
                  counts++;
                  self.tableModel.ypzData = data.eeList;
                  self.tableModel.meet = data.meet.toString();
                  self.tableModel.remarks = data.remarks;
                  if (counts == data.eeList.length) {
                    this.$nextTick(() => {
                      this.filArr(temps);
                    });
                  }
                });
              item.suggestprice1 = Number(item.suggestprice);
            });
          });

          // 流程日志
          self.$refs.log.init(self.instanceid);
          // 业务字典 面试结果
          getDicEntry({ dicttypeid: "NEW_BUSI_OUTSOURCE_INTERVRESULT" }).then(
            res => {
              if (res.data.status !== 200) {
                self.alert(res.data.message, { type: "error" });
                return false;
              }
              self.scoreData = res.data.results;
            }
          ); // end
        });
      }, // init end

      // 查询面试信息流程ID
      searchMsinfo(id, name) {
        checkedExistEntry(id)
          .then(res => {
            if (res.data.status == 200) {
              this.checkHmd(id, res.data.results, name);
            } else {
              this.$message.error("查询面试信息失败");
            }
          })
          .catch(error => {
            this.$message.error(error);
          });
      },

      // 检查是否在黑名单中
      checkHmd(id, ifCf, name) {
        checkedIsBlack({ icard: id }).then(res => {
          if (res.data.status == 200) {
            console.log("黑名单", res.data);
            if (ifCf != undefined && res.data.results == true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + "重复面试/已被列入黑名单",
                type: "error"
              });
            } else if (ifCf != undefined && res.data.results != true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + "重复面试",
                type: "error"
              });
            } else if (ifCf == undefined && res.data.results == true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + "已被列入黑名单",
                type: "error"
              });
            }
          } else {
            this.$message.error("查询黑名单信息失败");
          }
        });
      },

      // 获取人力需求
      getRlxq(val) {
        let params = {};
        params.id = val;
        rlxqQueryDetail(params)
          .then(res => {
            if (res.data.status == 200) {
              let rlIdData = res.data.results;
              var reg = /([^-]+)$/;
              this.rlIds =
                rlIdData.username +
                rlIdData.depname.match(reg)[1] +
                Moment(rlIdData.supplytime).format("YYYY-MM-DD") +
                rlIdData.usertypename;
            } else {
              this.errorMessage = "获取数据失败";
              this.messageError();
            }
          })
          .catch(error => {
            this.$message.error(error);
          });
      },

      // 数组去重
      filArr(ele) {
        let priceArr = [];
        let eleFilter = [];
        if (ele == []) {
          return;
        } else {
          for (var i = 0; i < ele.length; i++) {
            if (!eleFilter.includes(ele[i].wbsid + "-" + ele[i].position)) {
              eleFilter.push(ele[i].wbsid + "-" + ele[i].position);
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
        const thit = this;
        thit.flowItem.conclusion = params.conclusion;
        thit.flowItem.suggest = params.suggest;
        thit.tableModel.ypzData.filter(li => {
          delete li.suggestprice1;
          return li;
        });
        let wbData = { eeList: thit.tableModel.ypzData };
        let folw = { bps: thit.flowItem };
        let newObj = Object.assign(folw, wbData);
        newObj.msgdeptno = thit.msgdeptno.replace(/[a-z]/g, "");
        newObj.meet = thit.tableModel.meet;
        newObj.remarks = thit.tableModel.remarks;
        return doPriceConfirm(newObj)
          .then(res => {
            loading.close();
            return res.data;
          })
          .catch(error => {
            thit.$alert(error, { type: "error" });
          });
      }, // 流转 end

      // 流转并验证
      proving(data) {
        let status = new Promise(() => { });
        let priceStr = [];
        let self = this
        this.tableModel.ypzData.forEach((v, k) => {
          if (v.researchers == "1") {
            priceStr.push(v.msname + "单价为" + v.suggestprice);
          }
        });
        if (data.conclusion == "01") {
          this.$refs["formYpz"].validate(valid => {
            if (valid) {
              status = this.$confirm("是否确认" + priceStr.join(","), "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(() => {
                return this.liuzhuan(data).then(val => {
                  console.log('val', val);
                  return val;
                });
              });
            } else {
              this.errorMessage = "操作失败,请正确填写";
              this.messageError();
              return false;
            }
          });
        } else {
          status = this.liuzhuan(data).then(val => {
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

      // 计数器改变弹窗提示
      inputNumchange(index, currentValue, priceCount) {
        if (currentValue == priceCount) {
          this.tableModel.ypzData[index].reason = "";
        }

        if (this.tableModel.ypzData[index].suggestlevel == "1") {
          if (currentValue > this.tableModel.ypzData[index].upData.firstendprie) {
            this.inputNumBox();
          }
        } else if (this.tableModel.ypzData[index].suggestlevel == "2") {
          if (
            currentValue > this.tableModel.ypzData[index].upData.secondendprie
          ) {
            this.inputNumBox();
          }
        } else if (this.tableModel.ypzData[index].suggestlevel == "3") {
          if (currentValue > this.tableModel.ypzData[index].upData.thirdendprie) {
            this.inputNumBox();
          }
        } else if (this.tableModel.ypzData[index].suggestlevel == "4") {
          if (
            currentValue > this.tableModel.ypzData[index].upData.fourthendprie
          ) {
            this.inputNumBox();
          }
        }
      },

      // 计数器改变弹窗提示
      inputNumBox() {
        this.$confirm("该外包人员单价已超出对应档位，请请示分管总经理", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => { })
          .catch(() => { });
      },

      // 成功消息提示
      messageSuccess() {
        this.$message({
          message: this.successMessage,
          type: "success"
        });
      },

      // 失败消息提示
      messageError() {
        this.$message.error(this.errorMessage);
      },

      // ntko
      openNtkoWindow(objectId) {
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
        this.$router.push({ name: "dashboard" });
      },
       // 下载
      download() {
        priceDownload().then(res => {
          console.log('下载', res);
          common.download(res.data, this, '单价参考.xls')
        })
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
    width: 100px;
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
    .flex_box {
    display: flex;
    align-content: center;
    justify-content: flex-start;
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
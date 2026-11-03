<!--
面试打分
-->
<template>
  <div>
    <!-- 头部 -->
    <div class="head-container container_zb">
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
    <div class="main-app-container">
      <!-- 面试官 -->
    </div>


    <!-- bin改 -->
    <!-- 面试打分 -->
    <div class="contentMainScore">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">面试信息</p>
      </div>
      <div class="content">
        <!-- 表第一行 -->
        <div class="row_1">
          <el-row type="flex" justify="space-between">
            <el-col :span="7">
              <p>部门</p>
              <el-select v-model="msgdeptname" disabled>
                <el-option :value="msgdeptname">
                </el-option>
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
        </div>

        <!-- 应聘者信息 -->
        <div class="row_2">
          <div class="contentTwo_title">
            <p class="contentTwo_title_p">应聘者信息</p>
          </div>
          <el-row>
            <el-col :span="24">
              <div class="row_2_content">
                <!-- <p class="row_2_detail">应聘者信息</p> -->
                <el-form :model="tableModel" ref="formYpz" size="small">
                  <el-table :data="tableModel.ypzData" style="width: 100%">
                    <el-table-column prop="msname" label="姓名" width="80" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="sexName" label="性别" width="50"></el-table-column>
                    <el-table-column prop="birthday" label="出生日期" width="100"></el-table-column>
                    <el-table-column prop="outsourcName" label="外包商" width="220" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="positionName" label="人员类型" width="80"></el-table-column>
                    <el-table-column prop="positionName" label="人员属性" width="80">
                      <template slot-scope="scope">
                        <span v-if="scope.row.researchers == 1">人力外包</span>
                        <span v-else-if="scope.row.researchers == 2">项目人力</span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="workyears" label="年限" width="50"></el-table-column>
                    <el-table-column prop="suggestlevel" label="等级" width="80">
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
                    <el-table-column prop="msDate" label="面试日期 *" width="155">
                      <template slot-scope="scope">
                        <el-form-item :prop="`ypzData.${scope.$index}.msDate`" :rules="[{ required: true, message: '请选择面试时间'}]">
                          <el-date-picker v-model="scope.row.msDate" type="date" placeholder="选择日期" format="yyyy-MM-dd"
                            :editable="false"></el-date-picker>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="education" label="学历 *" width="155">
                      <template slot-scope="scope">
                        <el-form-item :prop="`ypzData.${scope.$index}.education`" :rules="[{ required: true, message: '请选择学历'}]">
                          <el-select v-model="scope.row.education" placeholder="学历">
                            <el-option v-for="item in educationData" :label="item.dictname" :key="item.dictid" :value="item.dictid"></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="experience" label="项目经验 *" width="155">
                      <template slot-scope="scope">
                        <el-form-item :prop="`ypzData.${scope.$index}.experience`" :rules="[{ required: true, message: '请选择经验', trigger: 'change' }]">
                          <el-select v-model="scope.row.experience" placeholder="经验">
                            <el-option v-for="item in experienceData" :label="item.dictname" :key="item.dictid" :value="item.dictid"></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="technology" label="技术 *" width="155">
                      <template slot-scope="scope">
                         <el-form-item :prop="`ypzData.${scope.$index}.technology`" :rules="[{ required: true, message: '请填写技术' }]">
                            <el-input-number v-model="scope.row.technology" size="medium"  placeholder="技术"
                                controls-position="right" :min="0" :max="4" :step="0.5" :precision="1"></el-input-number>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="comprehensive" label="综合 *" width="155">
                      <template slot-scope="scope">
                        <el-form-item :prop="`ypzData.${scope.$index}.comprehensive`" :rules="[{ required: true, message: '请填写综合'}]">
                          <el-input-number v-model="scope.row.comprehensive" size="medium"  placeholder="综合"
                                controls-position="right" :min="0" :max="2" :step="0.5" :precision="1"></el-input-number>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="remark" label="评语 *" width="155">
                      <template slot-scope="scope">
                        <el-form-item :prop="`ypzData.${scope.$index}.remark`" :rules="[{ required: true, message: '请填写评语' }]">
                          <el-input v-model="scope.row.remark" placeholder="评语"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form>
              </div>
            </el-col>
          </el-row>
        </div>
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
  import { getInterviewScore, doInterviewScore, queryManpowerdemands, download, rlxqQueryDetail } from "@/api/integrated/wbms";
  import departmentTwo from "@/views/plugins/components/DepartmentTwo";
  import employee from "@/views/plugins/components/Employee";
  import { getDicEntry } from "@/api/eosDictEntry";
  import flowBtn from "@/views/plugins/camunda/FlowBtn";
  import approveBottom from "@/views/plugins/camunda/approveBottom";
  import common from '@/api/common'
  import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
  import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
  import { doAuth } from '@/api/common/workflowApi'
  import Moment from 'moment'
  import { mapGetters } from "vuex";
  import store from "@/store";
  let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
  import { checkedIsBlack } from "@/api/integrated/wbHmd";
  import { checkedExistEntry } from "@/api/integrated/newWbrz";
  export default {
    components: { flowLogs, departmentTwo, employee, flowBtn, buttonOrange, buttonCommon, approveBottom },
    data() {
      return {
        ypzData: [], // 应聘者
        tableModel: {
          ypzData: []
        },
        msgData: [], // 面试官
        processInstId: "", // 流程实例id
        objectcode: "6000000300",
        objectgrade: "2",
        msgdeptno: "", // 部门编号
        msgdeptname: "", //部门名字
        rlIds: '', //人力需求绑定值
        mpOpts: [], // 人力需求集合数据
        busData: [], // 业务字段-评分
        educationData: [], // 业务字段-学历
        experienceData: [], // 业务字段-项目经验
        instanceid:"",
        flowItem: {},
        msgIds: '',      // 面试官id
        msgNames: '',    // 面试官名称
        buttonClose: '关 闭', //取消按钮名字
        size: 'mini',  //按钮大小
        whetherBan: false, //是否禁用
        statusCode: '', //退回或者流转状态
        successMessage: '', // 成功消息提示
        errorMessage: '', //失败消息提示
        timer: null
      };
    },
    computed: {
      ...mapGetters(["name", "userId"])
    },
    methods: {
      // 数据初始化
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
            self.getRlxq(data.eeList[0].rlId);
            self.msgdeptno = data.erList[0].msgdeptno;
            var reg = /([^-]+)$/;
            self.msgdeptname = data.erList[0].msgdeptname.match(reg)[1]
            self.msgData = data.erList;
            self.$refs.log.init(self.instanceid);
            let msgId = [], msgName = []
            //获取面试官id和名称
            data.erList.forEach(ii => {
              msgName.push(ii.msgname)
            });
            self.msgIds = data.erList[0].msgid;
            self.msgNames = msgName.join(',');
            if (data.eeList[0].scoreList.length == 0) {
              data.eeList = data.eeList.filter(li => {
                li.msDate = Moment(li.msDate).format('YYYY-MM-DD');
                return li;
              })
              self.tableModel.ypzData = data.eeList;
            } else {
              for (var i = 0; i < data.eeList[0].scoreList.length; i++) {
                if (data.eeList[0].scoreList[i].msgid == this.userId) {
                  data.eeList = data.eeList.filter(li => {
                    li.msDate = Moment(li.msDate).format('YYYY-MM-DD');
                    if (li.scoreList.length != 0) {
                      li.education = li.scoreList[i].education;
                      li.experience = li.scoreList[i].experience;
                      li.comprehensive = li.scoreList[i].comprehensive;
                      li.technology = li.scoreList[i].technology;
                      li.remark = li.scoreList[i].remark;
                    }
                    return li;
                  })
                  self.tableModel.ypzData = data.eeList;
                } else {
                  data.eeList = data.eeList.filter(li => {
                    li.msDate = Moment(li.msDate).format('YYYY-MM-DD');
                    return li;
                  })
                  self.tableModel.ypzData = data.eeList;
                }
              }
            }
            data.eeList.forEach(li => {
              setTimeout(() => {
                this.searchMsinfo(li.idcard, li.msname)
              }, 0)
            })
          });

          // 获取字典
          getDicEntry({ dicttypeid: "INTERVIEW_MARKING,MSDF_EDUCATION,MSXM_LEVEL" }).then(res => {
            if (res.data.status !== 200) {
                this.alter(res.data.message, { type: "error" });
                return false;
            }
            let data = res.data.results;
            this.educationData = data.filter(item => {
                return item.dicttypeid == "MSDF_EDUCATION";
            });
            this.experienceData = data.filter(item => {
                return item.dicttypeid == "MSXM_LEVEL";
            });
            this.busData = data.filter(item => {
                return item.dicttypeid == "INTERVIEW_MARKING";
            });
            return data;
          });

        });
      },

      // 查询面试信息流程ID
      searchMsinfo(id, name) {
        checkedExistEntry(id).then(res => {
          if (res.data.status == 200) {
            this.checkHmd(id, res.data.results, name)
          } else {
            this.$message.error('查询面试信息失败')
          }

        }).catch(error => {
          this.$message.error(error)
        })
      },

      // 检查是否在黑名单中
      checkHmd(id, ifCf, name) {
        // setTimeout(() => {
        checkedIsBlack({ icard: id }).then(res => {
          if (res.data.status == 200) {
            console.log("黑名单", res.data)
            if (ifCf != undefined && res.data.results == true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + '重复面试/已被列入黑名单',
                type: 'error'
              })
            } else if (ifCf != undefined && res.data.results != true) {
              // this.$nextTick(() => {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + '重复面试',
                type: 'error'
              })
              // })
            } else if (ifCf == undefined && res.data.results == true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + '已被列入黑名单',
                type: 'error'
              })
            }
          } else {
            this.$message.error('查询黑名单信息失败');
          }
        })
        // }, 20)

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
              this.rlIds = rlIdData.username + rlIdData.depname.match(reg)[1] + Moment(rlIdData.supplytime).format('YYYY-MM-DD') + rlIdData.usertypename;
            } else {
              this.errorMessage = '获取数据失败'
              this.messageError();
            }
          })
          .catch(error => {
            this.$message.error(error)
          });
      },

      onCancel() { },
      onSubmit() { },

      /**
       * 流转
       */
      liuzhuan: function (params) {
        const loading = common.loading(this);
        // console.log('this.tableData', this.tableModel);
        var educationSum = null;
        let experience = this.tableModel.ypzData[0].experience;
        let technology = this.tableModel.ypzData[0].technology;
        let comprehensive = this.tableModel.ypzData[0].comprehensive;
        if(this.tableModel.ypzData[0].education == 'A') {
          educationSum = 2;
        } else if(this.tableModel.ypzData[0].education == 'B') {
          educationSum = 2;
        } else if(this.tableModel.ypzData[0].education == 'C') {
          educationSum = 1.5;
        } else if(this.tableModel.ypzData[0].education == 'D') {
          educationSum = 1;
        }
        let sum = educationSum*1 + experience*1 + technology*1 + comprehensive*1;
        // console.log('sum', sum);
        if(sum < 6) {
          loading.close();
          this.$message.error("面试分数不得低于6分");
          return false
        }
        this.flowItem.conclusion = params.conclusion;
        this.flowItem.suggest = params.suggest;
        let ivsList = this.tableModel.ypzData.filter(ii => {
          ii.pid = ii.id;
          ii.msgid = this.userId;
          ii.msgname = this.name + '(' + this.userId + ')';
          return ii
        })
        let wbData = { "ivsList": ivsList }
        let folw = { "bps": this.flowItem }
        let newObj = Object.assign(folw, wbData);
        return doInterviewScore(newObj)
          .then(res => {
            loading.close();
            return res.data
          })
          .catch(error => {
            this.$alert(error, { type: "error" });
          });

      },

      // 流转并验证
      proving(data) {
        let status = new Promise(() => { });
        if (data.conclusion == "01") {
          this.$refs['formYpz'].validate((valid, val) => {
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
        return status
      },

      // 文档下载
      jlDownliad(docId) {
        const self = this;
        common.downloadJl(docId, self);
      },

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

      // 关闭按钮
      close() {
        this.$router.push({ name: 'dashboard' });
      },
    }
  };
</script>

<style scoped>
  /* bin修改 */
  .contentMainScore {
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
    margin: 12px 0;
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

  .contentMainScore .row_1 {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    /* padding: 0 20px; */
  }

  .contentMainScore .row_2_content {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    /* margin-top: 15px; */
  }

  .czLog {
    overflow: hidden;
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
</style>

<style>
  /* 禁用的输入框 */
  .contentMainScore .el-select {
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

  /* form表单样式修改 */
  .row_2_content .el-form-item {
    margin: 16px 0;
  }
</style>
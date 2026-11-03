<!-- 归还门禁卡流程 审批 页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
                <p class="nav_name">门禁卡归还确认</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
                <el-button size="mini" @click="toClose">关闭</el-button>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <!-- 主体 -->
    <div class="main-app-container">
      <!-- 面试信息 -->
    </div>

    <!-- bin改 -->
    <div class="contentMain contentMain_Add">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">基本信息</p>
      </div>
      <div class="content">
        <el-form :model="formData" ref="formData" >
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="7">
                  <div>
                    <p>申请人部门 *</p>
                    <el-form-item prop="deptName">
                      <el-input v-model="formData.applyerDeptName" :disabled="disableds"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                  <div>
                    <p>申请人 *</p>
                    <el-form-item prop="msgid" ref="msgForm">
                      <el-input v-model="formData.applyerName" :disabled="disableds"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7"></el-col>
              </el-row>
            </div>
          </div>
          <!-- 表格 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <!-- <span class="add_row" @click="eduExperAdd(1)">新增一行</span> -->
                  <p class="row_2_detail">还卡人员信息</p>
                  <el-table :data="formData.accessCardApplyInfoList" style="width: 100%">
                    <el-table-column label="序号" align="left" type="index"></el-table-column>
                    <el-table-column label="姓名" align="left" width="100" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.userName`">
                          <el-input v-model="scope.row.userName" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="身份证号" width="160" align="left">
                      <template slot-scope="scope">
                        <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.idCardNo`">
                            <el-tooltip :content="scope.row.idCardNo" placement="top" effect="dark" :disabled="scope.row.idCardNo == null || scope.row.idCardNo == ''">
                                <el-input v-model="scope.row.idCardNo" disabled></el-input>
                            </el-tooltip>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="电话" width="150" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.telephone`">
                                <el-input v-model="scope.row.telephone" disabled></el-input>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="供应商" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.outsourceName`">
                                <el-tooltip :content="scope.row.outsourceName" placement="top" effect="dark" :disabled="scope.row.outsourceName == null || scope.row.outsourceName == ''">
                                    <el-input v-model="scope.row.outsourceName" disabled></el-input>
                                </el-tooltip>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="所属部门" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.deptName`">
                                <el-tooltip :content="scope.row.deptName" placement="top" effect="dark" :disabled="scope.row.deptName == null || scope.row.deptName == ''">
                                    <el-input v-model="scope.row.deptName" disabled></el-input>
                                </el-tooltip>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="人员编号" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.virtualMachineId`">
                                <el-input v-model="scope.row.virtualMachineId" disabled></el-input>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <!-- <el-table-column label="是否需要制卡" width="120" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.ifMakeCard`">
                                <el-select placeholder="请选择" v-model="scope.row.ifMakeCard" disabled>
                                    <el-option v-for="item in makeCard_options" :key="item.value" :label="item.label" :value="item.value" />
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-table-column> -->
                    <el-table-column label="门禁卡编号 *" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.accessCardNumber`">
                                <el-input v-model="scope.row.accessCardNumber" disabled ></el-input>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="是否已领用" width="120" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.ifReceiveCard`">
                                <!-- <el-input v-model="scope.row.ifReceiveCard"></el-input> -->
                                <el-select placeholder="请选择" v-model="scope.row.ifReceiveCard" disabled>
                                    <el-option v-for="item in receiveCard_options" :key="item.value" :label="item.label" :value="item.value" />
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="领用时间" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.receiveTime`">
                                <el-date-picker v-model="scope.row.receiveTime" type="date" placeholder="选择日期" disabled format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
                                <!-- <el-input v-model="scope.row.receiveTime"></el-input> -->
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="是否已归还 *" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.ifReturnCard`" :rules="[{ required: true, message: '请选择'}]">
                                <el-select placeholder="请选择" v-model="scope.row.ifReturnCard">
                                    <el-option v-for="item in returnCard_options" :key="item.value" :label="item.label" :value="item.value" />
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="归还时间 *" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.returnTime`" :rules="[{ required: true, message: '请选择', trigger: 'blur'}]">
                                <el-date-picker v-model="scope.row.returnTime" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
                                <!-- <el-input v-model="scope.row.receiveTime"></el-input> -->
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="备注" width="160" align="left">
                        <template slot-scope="scope">
                            <el-form-item :prop="`accessCardApplyInfoList.${scope.$index}.remark`">
                                <el-tooltip :content="scope.row.remark" placement="top" effect="dark" :disabled="scope.row.remark == null || scope.row.remark == ''">
                                    <el-input v-model="scope.row.remark"></el-input>
                                </el-tooltip>
                            </el-form-item>
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
          <flow-logs :processInstId="flowItem.processInstanceId" :flowItem="flowItem" ref="log"></flow-logs>
      </div>
    </div>

    <div class="blank"></div>

    <!-- 底部操作栏 -->
    <div class="nav bottom">
      <approve-bottom :proving="proving" :flowItem="flowItem"></approve-bottom>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
import { initFlow, queryDetail, gmApproval, monitorApproval, finishFlow, queryUserDept} from "@/api/accountManager/accessCardApply";
import { isDate } from 'moment';
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import approveBottom from "@/views/plugins/camunda/approveBottom";
import { getDicEntry } from "@/api/eosDictEntry";

  export default {
    name: 'ghlcAffirm',
    components: { buttonOrange, buttonCommon, flowLogs, approveBottom },
    data() {
      return {
        formData:{
          applyerDeptId: '', // 部门编号
          applyerDeptName: "", // 部门名称
          applyerUserId: '', // 申请人编号
          applyerUserName: '', // 申请人名称
          accessCardApplyInfoList: [ // 外包人员集合信息
            {
              userName: '', // 姓名
              deptName: '', // 所属部门
              jobId: "", // 工号
              idCardNo: '', // 身份证号
              telephone: '', // 电话
              outsourceName: '', // 外包商名称
              virtualMachineId: '', // 人员编号 
              ifMakeCard: '', // 是否需要制卡 
              accessCardNumber: '', // 门禁卡编号
              ifReceiveCard: '', // 是否领用门禁卡
              receiveTime: '', // 领用时间
              ifReturnCard: '', // 是否归还门禁卡
              returnTime: '', //归还时间
              remark: '' //备注
            }
          ]
        },
        buttonName: "提交",
        buttonSave: '草稿',
        size: "mini",
        errorMessage: '', //失败消息提示,
        objectcode: "6000000300",
        objectgrade: "2",
        disableds: true,
        receiveCard_options: [
          {
            value: "Y",
            label: "已领用",
          },
          {
              value: "N",
              label: "未领用",
          },
        ],
        makeCard_options: [
          {
              value: "Y",
              label: "是",
          },
          {
              value: "N",
              label: "否",
          },
          {
              value: "W",
              label: "是",
          },
        ],
        returnCard_options: [
          {
            value: "Y",
            label: "已归还",
          },
          {
            value: "E",
            label: "遗失",
          },
          {
            value: "N",
            label: "未归还",
          },
        ],
        currentRow: {}, //当前行
        flowItem: {},
        instanceid: "",
        isFlagList: [],
        }
    },
    computed: {
    },
    watch: {
    },
    created() {
    },
    mounted() {
    },

    methods: {
      init(params) {
          // console.log(params)
          this.$nextTick(_ => {
              this.flowItem = params;
              this.instanceid = params.processInstanceId;
              this.formScene = params.formScene;
              this.taskId = params.taskId;
              this.getAccessCardDetail(params.processInstanceId);
              this.$refs.log.init(params.processInstanceId);
          })
      },

      // 获取详情
      getAccessCardDetail(val) {
          let params = {};
          params.instanceId = val;
          queryDetail(params).then(res => {
              if (res.data.status == 200) {
                  this.formData = res.data.results;
              }
          }).catch(error => {
              this.$message.error(error);
          });
      },
          // 流转或者退回
      liuzhuan(data) {
        const loading = common.loading(this);
        let params = {};
        params = this.formData;
        let bps = {};
        this.flowItem.taskId = this.taskId;
        this.flowItem.processInstId = this.instanceid;
        this.flowItem.conclusion = data.conclusion;
        this.flowItem.suggest = data.suggest;
        bps = this.flowItem;
        params.bps = bps;

        // console.log(params,'params')
        if (this.formScene == "ghmjaffirm") {
          console.log("params----------------",params);
            return finishFlow(params).then(res => {
                loading.close()
                // console.log(res,'监保部确认')
                return res.data;
            });
        } 
      },

      //提交
      proving(data) {
        let status = new Promise(() => {});
        this.$refs["formData"].validate(valid => {
          if (valid || data.conclusion != '01') {
            status = this.liuzhuan(data).then(val => {
              return val;
            });
          } else {
            this.$message({
              showClose: true,
              message: "请填写必填项",
              type: "error"
            });
            return false;
          }
        });
        return status;
      },

      // 获取申请人部门 
      getapplyerData() {
        queryUserDept().then(res => {
          console.log(res, '获取申请人部门')
          const { message, results, status } = res.data;
          if(status === 200) {
            this.formData.applyerUserName = results.userName;
            this.formData.applyerDeptName = results.deptName;
            this.formData.applyerDeptId = results.deptNo;
          } else {
            this.$message.error('获取数据失败')
          }
        })
      },
          
      // 关闭
      toClose() {
        this.$router.push({
        name: 'dashboard'
        })
      },
      // 失败提示消息
      saveError() {
        this.$message({
            showClose: true,
            message: this.errorMessage,
            type: 'error'
        })
      },
      //消息提示
      messageNotify(messageVal) {
        let messageSec = messageVal.slice(2).join("，");
        this.$notify({
          title: '提示',
          dangerouslyUseHTMLString: true,
          message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
          duration: 0
        })
      },
    },
  };
</script>
<style scoped>
  /* bin修改 */
  .contentMain {
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

  .contentTwo_title_p {
    color: #262626;
    font-weight: 600;
  }

  .content .row_1 {
    background-color: #ffffff;
    /* padding: 0 20px 15px 20px; */
    padding: 0 20px;

  }

  .contentMain .row_2 {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    margin-top: 15px;
  }

  .add_row {
    color: #faad14;
    font-size: 14px;
    font-weight: 500;
    margin-left: 10px;
    cursor: pointer;
  }

  /* 空格占位 */
  .blank {
    width: 100%;
    height: 160px;
  }

  .bottom {
    position: absolute;
    bottom: 0;
    padding: 15px 0;
  }

  .upload_scss div {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
    cursor: pointer;
  }

  /* 重传 */
  .againUpLoad {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
  }

  .wrapper_right_bottom_1_zb {
    margin-right: 10px;
  }
</style>

<style>
  /* 级联选择器 */
  .contentMain_Add .el-cascader {
    display: block;
  }

  /* 下拉框 */
  .contentMain_Add .el-select {
    display: block;
  }

  /* 表格删除按钮 */
  .contentMain_Add .el-icon-delete {
    width: 11px;
    height: 11px;
    color: #f56c6c;
  }

  .operation_add .el-button {
    border: 1px solid #ffffff;
    padding: 0;
  }

  /* table表格修改 */

  .row_2_add .el-table th {
    background: rgba(250, 250, 250, 1);
  }

  .row_2_add .el-table thead {
    font-size: 14px;
    color: rgba(38, 38, 38, 1);
    font-weight: 400;
  }

  .row_2_add .el-table--medium th {
    padding: 0;
  }

  .row_2_add .el-table--medium td {
    padding: 0;
  }

  .row_2_add .el-table th {
    padding: 10px 0;
  }

  /* form表单样式修改 */
  .contentMain_Add .el-form-item {
    margin: 16px 0;
  }

  /* 计数器 */
  .contentMain_Add .el-input-number--medium {
    width: 100px;
  }

  /* 上传文件按钮 */

  .tableCon .el-button--primary {
    color: #000000;
    background-color: #ffffff;
    border-color: rgba(0, 0, 0, 0.15);
  }

  /* 重传 */
  .againUpLoad .el-upload--picture-card:hover {
    color: rgba(251, 164, 47, 1);
  }

  .againUpLoad .el-upload:focus {
    color: rgba(251, 164, 47, 1);
  }
</style>3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
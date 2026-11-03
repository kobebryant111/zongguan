<!-- 提前入场申请新增页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">提前入场申请</p>
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
        <p class="contentTwo_title_p">申请人信息</p>
      </div>
      <div class="content">
        <el-form :model="formData" ref="formData">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="7">
                  <div>
                    <p>申请人部门 *</p>
                    <el-form-item prop="deptName">
                      <el-input v-model="formData.deptName" :disabled="disableds"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                  <div>
                    <p>申请人 *</p>
                    <el-form-item prop="msgid" ref="msgForm">
                      <el-input v-model="formData.applyer" :disabled="disableds"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 表格 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">提前入场者信息</p>
                  <el-table :data="formData.applyInfoDtos"  style="width: 100%" >
                        <el-table-column label="序号" align="center" type="index"></el-table-column>
                        <el-table-column label="姓名 *" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`applyInfoDtos.${scope.$index}.userName`">
                                    <el-input v-model="scope.row.userName" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="人员类型" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`applyInfoDtos.${scope.$index}.position`">
                                    <el-input v-model="scope.row.position" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="工作年限" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`applyInfoDtos.${scope.$index}.workyears`">
                                    <el-input v-model="scope.row.workyears" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="预计单价" width="200" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`applyInfoDtos.${scope.$index}.suggestprice`">
                                    <el-input v-model="scope.row.suggestprice" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="供应商" width="200" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`applyInfoDtos.${scope.$index}.outSourceName`">
                                    <el-input v-model="scope.row.outSourceName" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="提前入场时间" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`applyInfoDtos.${scope.$index}.earlyEntryTime`">
                                    <!-- <el-input v-model="scope.row.earlyEntryTime"></el-input> -->
                                  <el-date-picker v-model="scope.row.earlyEntryTime" type="date" placeholder="请选择日期" value-format="yyyy-MM-dd" disabled></el-date-picker>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否补全入场流程" width="150" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`applyInfoDtos.${scope.$index}.isComplement`">
                                    <!-- <el-input v-model="scope.row.isComplement"></el-input> -->
                                    <el-select v-model="scope.row.isComplement" disabled>
                                      <el-option 
                                        v-for="item in isComplementOption"
                                        :key="item.id"
                                        :label="item.label"
                                        :value="item.value"
                                      ></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <!-- <el-table-column label="操作" width="80" align="center" class="margin0">
                            <template slot-scope="scope">
                                <div class="operation_add">
                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.applyInfoDtos, scope.row)" disabled>
                                        <i class="el-icon-delete"></i>
                                    </el-button>
                                </div>
                            </template>
                        </el-table-column> -->
                    </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>
      <!-- 操作日志 -->
      <div class="czLog">
          <flow-logs :processInstId="flowItem.processInstanceId" ref="log"></flow-logs>
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
import { queryUserDept, createFlow, interView, initFlow, queryDetail, dhApproval, gmApproval } from "@/api/tqrcApplyfor";
import { isDate } from 'moment';
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import approveBottom from "@/views/plugins/camunda/approveBottom";


  export default {
    name: 'Add',
    components: { buttonOrange, buttonCommon, flowLogs, approveBottom },
    data() {
        return {
          formData:{
            deptNo: '', // 部门编号
            deptName: "", // 部门名称
            applyerId: '', // 申请人编号
            applyer: '', // 申请人名称
            applyInfoDtos: [ // 外包人员集合信息
                {
                  // id: '',
                  interviewId: "", // 关联面试ID
                  jobId: "", // 工号
                  userName: '', // 姓名
                  position: '', // 人员类型
                  workyears: '', // 工作年限
                  suggestprice: '', // 预计单价
                  outSourceName: '', // 所属供应商
                  earlyEntryTime: "", // 提前入场时间
                  // remarks: "", // 备注
                  isComplement: "" // 是否补全入场流程
                }
            ]
          },
          buttonName: "提交",
          buttonSave: '草稿',
          size: "mini",
          errorMessage: '', //失败消息提示,
          objectcode: "6000000300",
          objectgrade: "2",
          depData: [],
          disableds: true,
          isComplementOption: [
            {
              id: "0",
              label: '否',
              value: 'N'
            },
            {
              id: "1",
              label: '是',
              value: 'Y'
            }
          ],
          applyData: [], // 面试者信息
          currentRow: {}, //当前行
          flowItem: {},
          instanceid: '',
          formScene: '', //场景
          taskId: '',
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
            //console.log(params)
            this.$nextTick(_ => {
              this.flowItem = params;
              this.instanceid = params.processInstanceId;
              this.formScene = params.formScene;
              this.taskId = params.taskId;
              this.getqueryDetail(this.instanceid);
              this.$refs.log.init(this.instanceid)
            })
        },
      // 获取详情
      getqueryDetail(id) {
        let params = {}
        params.instanceid = id;
        let data = common.paramsFormat(params)
        queryDetail(data).then(res => {
          //console.log('获取详情', res)
          if(res.data.status === 200) {
            this.formData = res.data.results;
          } else {
            this.$message.error("获取数据失败")
          }
        })
      },
      // 验证必填项
      proving(data) {
        // console.log(111111, data)
        let status = new Promise(() => {});
        status = this.liuzhuan(data).then(val => {
          // console.log(val, 'val')
          return val;
        });
        // console.log(status, 'status')
        return status;
      },
      // 流转或者退回
      liuzhuan(data) {
        //console.log('data', data)
        // debugger;
        let params = {};
        params = this.formData;
        let bps = {}
        this.flowItem.taskId = this.taskId;
        this.flowItem.processInstId = this.instanceid;
        this.flowItem.conclusion = data.conclusion;
        this.flowItem.suggest = data.suggest;
        bps = this.flowItem;
        // params.applyInfoDtos = this.formData.applyInfoDtos;
        params.bps = bps;

       // console.log(params,'params')
        if (this.formScene == "ertqrcsqConfirm") {
          return dhApproval(params).then(res => {
            // loading.close()
           // console.log(res,'二级部经理审批')
            return res.data;
          });
        } else if (this.formScene == "zhtqrcsqConfirm"){
          return gmApproval(params).then(res => {
            // loading.close()
           // console.log(res,'一级部经理审批')
            return res.data;
          });
        }
      },
      // 关闭
      toClose() {
        this.$router.push('/dashboard')
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
</style>
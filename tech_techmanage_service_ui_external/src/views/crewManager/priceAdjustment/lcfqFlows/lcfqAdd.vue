<!-- 单价调整新增页面 (未使用)-->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">单价调整</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common
                :buttonName="buttonClose"
                :size="size"
                :fns="toClose"
              ></button-common>
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
        <el-form :model="formData" ref="formData">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="start" :gutter="20">
                <el-col :span="7">
                  <div>
                    <p>申请人部门 *</p>
                    <el-form-item prop="deptName">
                      <el-input v-model="formData.deptName" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                  <div>
                    <p>申请人 *</p>
                    <el-form-item prop="msgid" ref="msgForm">
                      <el-input v-model="formData.applierName" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 表格 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">
                    人员信息<span class="add_row" @click="eduExperAdd(1)"
                      >新增一行</span
                    >
                  </p>
                  <el-table
                    :data="formData.outsrcingPriceChgList"
                    style="width: 100%"
                    @row-click="rowClick"
                  >
                    <el-table-column prop="userCode" label="姓名 *" width="125">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.userCode`"
                          :rules="[{ required: true, message: '请选择姓名' }]"
                        >
                          <el-select
                            v-model="scope.row.userCode"
                            placeholder="请选择"
                            @change="changeUserName($event, scope.$index)"
                          >
                            <el-option
                              v-for="item in applyData"
                              :key="item.id"
                              :label="item.userName"
                              :value="item.jobId"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="deptName"
                      label="所属部门 *"
                      width="200"
                      show-overflow-tooltip
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.deptName`"
                        >
                          <el-select
                            v-model="scope.row.deptName"
                            placeholder="请选择部门"
                            disabled
                          >
                            <el-option
                              v-for="item in deptmentData"
                              :key="item.id"
                              :label="item.text"
                              :value="item.id"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="prjMgrName"
                      label="项目经理 *"
                      width="160"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.prjMgrName`"
                        >
                          <el-input
                            v-model="scope.row.prjMgrName"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="position" label="岗位 *" width="135">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.position`"
                        >
                          <el-select v-model="scope.row.position">
                            <el-option
                              v-for="item in position_options"
                              :key="item.dictid"
                              :id="item.dictid"
                              :label="item.dictname"
                              :value="item.dictid"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="serviceYear"
                      label="等级 *"
                      width="150"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.serviceYear`"
                          :rules="[
                            {
                              required: true,
                              message: '请选择等级',
                              trigger: 'change'
                            },
                          ]"
                        >
                          <el-select
                            v-model="scope.row.serviceYear"
                            placeholder="请选择"
                          >
                            <el-option
                              v-for="item in workLevelOption"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="vendorName"
                      label="所属供应商 *"
                      width="200"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.vendorName`"
                          :rules="[{ required: true, message: '请选择' }]"
                        >
                          <el-input
                            v-model="scope.row.vendorName"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="price"
                      label="调整前价格 *"
                      width="150"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.price`"
                        >
                          <el-input
                            v-model="scope.row.price"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="postPrice"
                      label="调整后价格 *"
                      width="150"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.postPrice`"
                          :rules="[
                            { required: true, message: '请输入调整后价格' },
                          ]"
                        >
                          <el-input
                            v-model="scope.row.postPrice"
                            placeholder="请输入"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="chgDate"
                      label="变更开始时间 *"
                      width="160"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.chgDate`"
                          :rules="[
                            {
                              required: true,
                              message: '请选择变更时间',
                              trigger: 'blur',
                            },
                          ]"
                        >
                          <el-date-picker
                            v-model="scope.row.chgDate"
                            type="date"
                            placeholder="请选择日期"
                            value-format="yyyy-MM-dd"
                            :editable="false"
                          ></el-date-picker>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="remarks" label="备注 " width="120">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcingPriceChgList.${scope.$index}.remarks`"
                          :rules="[{ required: false, message: '请填写备注' }]"
                        >
                          <el-input
                            v-model="scope.row.remarks"
                            placeholder="请输入"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="operation" label="操作">
                      <template slot-scope="scope">
                        <div class="operation_add">
                          <el-button
                            @click.native.prevent="
                              deleteRow(
                                scope.$index,
                                formData.outsrcingPriceChgList,
                                scope.row
                              )
                            "
                          >
                            <i class="el-icon-delete"></i>
                          </el-button>
                        </div>
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
        <flow-logs
          :processInstId="flowItem.processInstanceId"
          :flowItem="flowItem"
          ref="log"
        ></flow-logs>
      </div>
    </div>

    <div class="blank"></div>

    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <!-- <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete> -->
              <el-button @click="delBox" size="mini">删除</el-button>
            </el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5">
                <!-- <button-common :buttonName="buttonSave" :size="size" :fns="saveInterview"></button-common> -->
              </el-col>
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange
                  :buttonName="buttonName"
                  :size="size"
                  :fns="onSubmit"
                ></button-orange>
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import {
  queryUserDept,
  createFlow,
  interView,
  initFlow,
  queryDetail,
  toDelete,
} from "@/api/tqrcApplyfor";
import {
  deleteFlow,
  queryProcessDetail,
  approvalCirculate,
} from "@/api/priceAdjustment/priceAdjustmentApply";
import { getDicEntry } from "@/api/eosDictEntry";
import { getOutsBasicInfo } from "@/api/projectTransfer/projectTransfer";
import flowLogs from "@/views/plugins/camunda/FlowLogs";

export default {
  name: "Add",
  components: { buttonOrange, buttonCommon, flowLogs },
  data() {
    return {
      // 表单
      formData: {
        outsrcingPriceChgList: [
          {
            userName: null, // 用户姓名
            deptId: null, // 部门
            prjMgrCode: null, // 项目经理
            prjMgrName: null,
            position: null, // 人员类型
            serviceYear: null, // 工作年限
            price: null, // 预计单价(元/人月)
            chgDate: null, // 变更时间
            vendorName: null, //
            remark: null,
          },
        ],
      },
      restaurants: [], // 查询数据
      buttonName: "提交",
      buttonSave: "草稿",
      buttonClose: "关 闭", //关闭按钮名字
      size: "mini",
      errorMessage: "", //失败消息提示,
      objectcode: "6000000300",
      objectgrade: "2",
      depData: [],
      deptmentData: {},
      position_options: [], // 岗位
      disableds: true,
      workLevelOption: [
        {
          label: "1 (1-3年)",
          value: "1",
        },
        {
          label: "2 (3-5年)",
          value: "2",
        },
        {
          label: "3 (5-7年)",
          value: "3",
        },
        {
          label: "4 (7年以上)",
          value: "4",
        },
      ],
      applyData: [], // 面试者信息
      currentRow: {}, //当前行
      flowItem: {},
      instanceid: "",
      workItemID: "",
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getapplyerData();
    this.getPosition();
  },
  mounted() {},

  methods: {
    init(params) {
      console.log("单价调整", params);
      this.flowItem = params;
      console.log(this.flowItem, "item112121");
      this.instanceid = params.processInstanceId;
      this.workItemID = params.businessKey;
      this.$nextTick((_) => {
        this.getqueryDetail();
        this.$refs.log.init(this.instanceid);
      });
    },
    // 获取详情
    getqueryDetail() {
      let loading = common.loading(this);
      let params = {};
      params.instanceId = this.instanceid;
      let data = common.paramsFormat(params);
      queryProcessDetail(data).then((res) => {
        loading.close();
        console.log("获取详情", res);
        this.getapplyerData();
        if (res.data.status === 200) {
          this.formData = JSON.parse(JSON.stringify(res.data.results));     
        } else {
          this.$message.error("数据获取失败，请稍后重试");
        }
      });
    },
    // 获取申请人部门
    getapplyerData() {
      queryUserDept().then(async (res) => {
        console.log(res, "获取申请人部门");
        const { message, results, status } = await res.data;
        if (status === 200) {
          console.log(results,'结果是')
          this.formData.applier = results.userId;
          this.formData.applierName = results.userName;     
          console.log(this.formData.applierName,'applierawrqr')
          this.formData.deptName = results.deptName;
          this.formData.deptId = results.deptNo;
          this.getOutsBasicinfo(results.userId);
          this.$forceUpdate();
        } else {
          this.errorMessage = message;
          this.saveError();
        }
      });
    },
    // 获取岗位
    getPosition() {
      getDicEntry({
        dicttypeid: "BUSI_OUTSOURCE_POSITION",
      }).then((res) => {
        console.log("岗位", res);
        if (res.data.status === 200) {
          this.position_options = res.data.results;
        }
      });
    },
    /** 获取人员信息 */
    getOutsBasicinfo(userId) {
      const data = {
        personInCharge: userId,
        presenceState: "1",
        //  deptId:this.formData.deptId
      };
      getOutsBasicInfo(data).then((res) => {
        console.log(res.data, "data外包人员信息数据");
        this.applyData = res.data.results;
      });
    },
    changeUserName(value, index) {
      let userList = this.applyData.find((item) => item.jobId == value);
      /**姓名 */
      this.formData.outsrcingPriceChgList[index].userName = userList.userName;
      this.formData.outsrcingPriceChgList[index].userCode = userList.jobId; // 工号
      /** 清空 */
       if(this.formData.outsrcingPriceChgList[index].id){
            delete this.formData.outsrcingPriceChgList[index].id;
      }
      this.$set(this.formData.outsrcingPriceChgList[index],'price','');
      this.$set(this.formData.outsrcingPriceChgList[index],'postPrice','');
      this.$set(this.formData.outsrcingPriceChgList[index],'remarks','');
      getOutsBasicInfo({ jobId: value }).then((res) => {
        let item = res.data.results[0];
        console.log('item数据',item)
        /** 部门 */
        this.formData.outsrcingPriceChgList[
          index
        ].deptId = this.formData.deptId;
        this.formData.outsrcingPriceChgList[
          index
        ].deptName = this.formData.deptName;
        /** 项目经理 */
        this.formData.outsrcingPriceChgList[index].prjMgrCode =
          item.personInCharge;
        this.formData.outsrcingPriceChgList[
          index
        ].prjMgrName = this.formData.applierName;
        this.formData.outsrcingPriceChgList[index].outsourceName =
          item.outsourceType;
        /** 岗位 */
        this.formData.outsrcingPriceChgList[index].position = item.postId?item.postId:'';
        this.formData.outsrcingPriceChgList[index].serviceYear = item.grade?item.grade:'';
        /** 单价 */
        if(item.price && item.price != 'null'){
          this.formData.outsrcingPriceChgList[index].price = parseFloat(item.price); //item.price; 单价
        }      
        /** 供应商 */
        this.formData.outsrcingPriceChgList[index].vendorCode =
          item.outsourceNo?item.outsourceNo:'';
        this.formData.outsrcingPriceChgList[index].vendorName =
          item.outsourcname?item.outsourcname:'';
      });
    },
    // 获取入场者信息
    getrcInfo() {
      let params = {};
      params.deptNo = this.formData.deptNo;
      interView(params).then((res) => {
        console.log(res, "获取入场者信息");
        const { message, results, status } = res.data;
        if (status === 200) {
          this.applyData = results;
        }
      });
    },
    // 改变面试者操作
    // handleChange(row, index) {
    //   console.log(row, index, "被上天选择的人");
    //   let arr = JSON.parse(JSON.stringify(this.applyData));
    //   let loading = common.loading(this);
    //   arr.forEach((item) => {
    //     if (item.userName === row.userName) {
    //       loading.close();
    //       console.log("改变面试者操作", item);
    //       this.$set(this.formData.applyInfoDtos, index, item);
    //     }
    //   });
    // },
    // 新增遗憾
    eduExperAdd(n) {
      // console.log(n)
      var list;
      list = {
        userName: null, // 用户姓名
        deptId: null, // 部门
        prjMgrCode: null, // 项目经理
        prjMgrName: null,
        position: null, // 人员类型
        serviceYear: null, // 工作年限
        price: null, // 预计单价(元/人月)
        chgDate: null, // 变更时间
        vendorName: null, //
        remark: null,
        instanceId: this.instanceid,
      };
      this.formData.outsrcingPriceChgList.push(list);
    },
    // 打开删除messageBox窗口
    delBox() {
      this.$confirm("确认删除该流程吗?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 删除流程
          // delPro() {
          let params = {};
          params.id = this.workItemID;
          console.log(this.workItemID, "workItemId");
          params.instanceId = this.instanceid;
          // let data = common.paramsFormat(params);
          deleteFlow(params).then((res) => {
            if (res.data.status == 200) {
              this.successMessage = "流程删除成功";
              // this.saveSuccess();
              this.$message.success("流程删除成功");
              this.$router.push({ name: "dashboard" });
            } else {
              this.$message.error(res.data.message);
            }
          });
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // 表格行被点击时触发
    rowClick(row) {
      this.currentRow = row;
    },
    // 删除行
    deleteRow(index, rows, row) {
      // console.log(index, rows, row)
      if (rows.length !== 1 && row.id == "") {
        rows.splice(index, 1);
      } else if (rows.length != 1 && row.id != "") {
        // this.delTableData(index, rows, row);
        rows.splice(index, 1);
      } else {
        this.errorMessage = "不能少于一行";
        this.saveError();
      }
    },
    // 删除表中数据
    delTableData(index, rows, row) {
      let params = {};
      params.id = row.id;
      let data = common.paramsFormat(params);
    },
    // 保存
    saveInterview() {
      const loading = common.loading(this);
      console.log("onSubmit", this.formData);
      // this.formData.applyInfoDtos = this.formData.applyInfoDtos.map(item => {
      //   delete item.id
      //   return item;
      // })
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          createFlow(this.formData).then((res) => {
            console.log(res, "保存");
            loading.close();
            if (res.data.status == "200") {
              this.$nextTick(() => {
                this.$message.error("保存成功");
                this.$router.push({ name: "tqrcApplyfor" });
              });
            } else {
              this.$message.error(res.data.message);
            }
          });
        } else {
          loading.close();
          this.$message.error("保存失败");
          return;
        }
      });
    },
    // 提交
    onSubmit() {
      const loading = common.loading(this);
      console.log("onSubmit", this.formData);
      // this.formData.applyInfoDtos = this.formData.applyInfoDtos.map(item => {
      //   delete item.id
      //   return item;
      // })
      let data = {};
      data = this.formData;
      data.bps = this.flowItem;
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          approvalCirculate(this.formData).then((res) => {
            console.log(res, "提交");
            loading.close();
            if (res.data.status == "200") {
              let message = common.getFlowMessgeNew(res.data.message);
              let messageArr = message.split(",");
              this.$nextTick(() => {
                this.messageNotify(messageArr);
                // 此处更换路径
                this.$router.push({ name: "dashboard" });
              });
            } else {
              this.$message.error(res.data.message);
            }
          });
        } else {
          loading.close();
          this.$message.error("提交失败");
          return;
        }
      });
    },
    // 关闭
    toClose() {
      this.$router.push("/dashboard");
    },
    // 失败提示消息
    saveError() {
      this.$message({
        showClose: true,
        message: this.errorMessage,
        type: "error",
      });
    },
    //消息提示
    messageNotify(messageVal) {
      let messageSec = messageVal.slice(2).join("，");
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
          "<div><div>" +
          messageVal[1] +
          "</div><div>" +
          messageSec +
          "</div></div>",
        duration: 0,
      });
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
  width: 350px;
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
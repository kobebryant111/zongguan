<!-- 单价调整已办/归档页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">单价调整信息</p>
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
                    人员信息
                    <!-- <span class="add_row" @click="eduExperAdd(1)"
                      >新增一行</span
                    > -->
                  </p>
                  <el-table :data="formData.outsrcingPriceChgList" style="width: 100%" >
                    <el-table-column prop="userName" label="姓名 *" width="125">
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.userName`" :rules="[{ required: true, message: '请选择姓名' }]" >
                          <!-- <el-select v-model="scope.row.userName" placeholder="请选择" disabled >
                            <el-option v-for="item in applyData" :key="item.id" :label="item.userName" :value="item.jobId" />
                          </el-select> -->
                          <el-input v-model="scope.row.userName" disabled ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="deptName" label="所属部门 *" width="180" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.deptName`" >
                          <!-- <el-select v-model="scope.row.deptName" placeholder="请选择部门" disabled >
                            <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                          </el-select> -->
                          <el-input v-model="scope.row.deptName" disabled ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="prjMgrName" label="项目经理 *" width="125" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.prjMgrName`" >
                          <el-input v-model="scope.row.prjMgrName" placeholder="请输入" disabled />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="position" label="岗位 *" width="140">
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.position`" >
                          <el-select v-model="scope.row.position" disabled>
                            <el-option v-for="item in position_options" :key="item.dictid" :id="item.dictid" :label="item.dictname" :value="item.dictid" ></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="serviceYear" label="等级 *" width="125" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.serviceYear`" :rules="[{ required: true, message: '请选择等级', trigger: 'change' }]">
                          <el-select v-model="scope.row.serviceYear" placeholder="请选择" disabled >
                            <el-option v-for="item in workLevelOption" :key="item.value" :label="item.label" :value="item.value" />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="vendorName" label="所属供应商 *" width="200" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.vendorName`" >
                          <el-tooltip :content="scope.row.vendorName" placement="top" effect="dark" :disabled="scope.row.vendorName == null" >
                            <el-input v-model="scope.row.vendorName" disabled ></el-input>
                          </el-tooltip>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="price" label="调整前价格 *" width="140" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.price`" >
                          <el-input v-model="scope.row.price" placeholder="请输入" disabled />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="postPrice" label="调整后价格 *" width="140" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.postPrice`" >
                          <el-input v-model="scope.row.postPrice" placeholder="请输入" disabled />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="chgDate" label="变更开始时间 *" width="160" >
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.chgDate`" >
                          <el-date-picker v-model="scope.row.chgDate" type="date" placeholder="请选择日期" value-format="yyyy-MM-dd" disabled ></el-date-picker>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="remarks" label="备注 *" width="200">
                      <template slot-scope="scope">
                        <el-form-item :prop="`outsrcingPriceChgList.${scope.$index}.remarks`" >
                          <el-input type="textarea" v-model="scope.row.remarks" placeholder="请输入" disabled :autosize="{ minRows: 1,maxRows: 8}"/>
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
        <flow-logs-gd ref="log"></flow-logs-gd>
      </div>
    </div>

    <div class="blank"></div>
    <div class="nav-bottom" v-if="flowItem.status == 'yb'">
      <yb-revoke :flowItem="flowItem"></yb-revoke>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import {
  queryProcessDetail,
} from "@/api/priceAdjustment/priceAdjustmentApply";
import { getEmployUser } from "@/api/common/flowApi";
import { getDicEntry } from "@/api/eosDictEntry";
import approveBottom from "@/views/plugins/camunda/approveBottom";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import ybRevoke from "@/views/plugins/camunda/ybRevoke";

export default {
  name: "Add",
  components: { buttonOrange, buttonCommon, FlowLogsGd, ybRevoke },
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
      buttonName: "提交",
      buttonSave: "草稿",
      buttonClose: "关 闭", //关闭按钮名字
      size: "mini",
      errorMessage: "", //失败消息提示,
      objectcode: "6000000300",
      objectgrade: "2",
      depData: [],
      position_options: [],
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
      deptmentData: {},
      disableds: true,
      applyData: [], // 面试者信息
      currentRow: {}, //当前行
      flowItem: {},
      instanceid: "",
      formScene: "", //场景
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getPosition();
  },
  mounted() {},

  methods: {
    init(params) {
      console.log(params);
      // this.getapplyerData();
      this.$nextTick((_) => {
        this.flowItem = params;
        this.instanceid = params.instanceid || params.processInstanceId;
        this.formScene = params.formScene;
        this.taskId = params.taskId;
        this.getqueryDetail(this.instanceid);
        this.$refs.log.init(params);
      });
    },
    // 获取详情
    getqueryDetail(id) {
      let params = {};
      params.instanceId = id;
      let data = common.paramsFormat(params);
      queryProcessDetail(data).then((res) => {
        console.log("获取详情", res);
        if (res.data.status === 200) {
          this.formData = res.data.results;
           this.reqSearch(this.formData.applier);
        } else {
          this.$message.error("获取数据失败");
        }
      });
    },
    // 申请人的默认值
    reqSearch(kerword) {
      getEmployUser(kerword)
        .then((res) => {
          if (res.data.status == 200) {
            this.formData.applierName = res.data.results[0].empname;
            this.$forceUpdate();
          }
        })
        .catch((error) => {
          this.$message.error(error);
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
.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: absolute;
  bottom: 0;
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
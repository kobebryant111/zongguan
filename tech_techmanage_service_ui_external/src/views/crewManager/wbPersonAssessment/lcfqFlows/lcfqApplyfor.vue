<!-- 人员绩效考核新增页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">外包人员考核</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common
                :buttonName="buttonClose"
                :size="size"
                :fns="close"
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
                <el-col :span="6">
                  <div>
                    <p>申请人部门 *</p>
                    <el-form-item prop="deptName">
                      <el-input v-model="formData.deptName" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div>
                    <p>申请人 *</p>
                    <el-form-item>
                      <el-input
                        v-model="formData.prjMgrName"
                        disabled
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>

              <el-row type="flex" justify="start" :gutter="20">
                <el-col :span="6">
                  <div>
                    <p>考核类型 *</p>
                    <el-form-item prop="msgid">
                      <el-select
                        v-model="formData.assessType"
                        placeholder="请选择"
                        @change="changeAssessType"
                      >
                        <el-option
                          v-for="item in assessment_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div>
                    <p>考核年份 *</p>
                    <el-form-item prop="assessYear" :rules="{ required: true, message: '请选择', trigger: 'change' }">
                      <el-select
                        v-model="formData.assessYear"
                        placeholder="请选择"
                        @change="changeYear"
                      >
                        <el-option
                          v-for="item in year_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6" v-if="formData.assessType == 0">
                  <div>
                    <p>考核季度 *</p>
                    <el-form-item prop="assessAnnual" :rules="{ required: true, message: '请选择', trigger: 'change' }">
                      <el-select
                        v-model="formData.assessAnnual"
                        placeholder="请选择"
                        @change="changeAnnual"
                        :disabled="formData.assessType == '1'"
                      >
                        <el-option
                          v-for="item in annual_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6" v-else>
                  <div>
                    <p>考核季度</p>
                    <el-form-item prop="assessAnnual">
                      <el-select
                        v-model="formData.assessAnnual"
                        placeholder="请选择"
                        @change="changeAnnual"
                        :disabled="formData.assessType == '1'"
                      >
                        <el-option
                          v-for="item in annual_options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
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
                    :data="formData.outsrcEmpAssessList"
                    style="width: 100%"
                    max-height="450"
                    @row-click="rowClick"
                  >
                    <el-table-column prop="userCode" label="姓名 *" width="180" v-if="formData.assessType == 0">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.userCode`"
                        >
                          <el-select
                            v-model="scope.row.userCode"
                            placeholder="请选择"
                            @change="changeUserName($event, scope.row,`${scope.$index}`)"
                          >
                            <el-option
                              v-for="(item) in applyData"
                              :key="item.id"
                              :label="item.userName"
                              :value="item.jobId"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="userCode" label="姓名 *" width="180" v-else>
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.userCode`"
                        >
                          <el-select
                            v-model="scope.row.userCode"
                            placeholder="请选择"
                            @change="changeUserName($event, scope.row, `${scope.$index}`)"
                          >
                            <el-option
                              v-for="(item, index) in applyYearData"
                              :key="index"
                              :label="item.userName"
                              :value="item.jobId"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="系统编号 *" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.jobId" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="deptName"
                      label="所属部门 *"
                      width="200"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.deptName`"
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
                          :prop="`outsrcEmpAssessList.${scope.$index}.prjMgrName`"
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
                          :prop="`outsrcEmpAssessList.${scope.$index}.position`"
                        >
                          <el-select v-model="scope.row.position" disabled>
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
                      prop="vendorName"
                      label="所属供应商 *"
                      width="200"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.vendorName`"
                        >
                          <el-input
                            v-model="scope.row.vendorName"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <!-- <el-table-column
                      prop="assessYear"
                      label="考核年份 *"
                      width="150"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.assessYear`"
                        >
                          <el-select
                            v-model="scope.row.assessYear"
                            placeholder="请选择"
                          >
                            <el-option
                              v-for="item in year_options"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column> -->
                    <!-- <el-table-column
                      prop="assessAnnual"
                      label="考核季度 *"
                      width="150"
                      v-if="formData.assessType == '0'"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.assessAnnual`"
                          :rules="[
                            {
                              required: true,
                              message: '请输入考核季度',
                              trigger: 'change',
                            },
                          ]"
                        >
                          <el-select
                            v-model="scope.row.assessAnnual"
                            placeholder="请选择"
                          >
                            <el-option
                              v-for="item in annual_options"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column> -->
                    <el-table-column
                      prop="avgScore"
                      label="平均分 *"
                      width="150"
                      v-if="formData.assessType == '1'"

                    >
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input-number controls-position="right" :max="10" :min="1" v-model="scope.row.avgScore" disabled></el-input-number>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="assessLevel"
                      label="考评得分 *"
                      width="140"
                      v-if="formData.assessType == '0'"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.assessLevel`"
                          :rules="[
                              { required: true, message: '评分不能为空', trigger: 'blur'},
                              { type: 'number', message: '评分必须为数字值'}
                            ]"
                        >
                          <el-input type="age" v-model.number="scope.row.assessLevel" placeholder="请输入1-10" @change="changeInput(scope.row, `${scope.$index}`)"  onkeyup="value=value.replace(/[^\d]/g, '')"></el-input>
                          <!-- <el-select
                            v-model="scope.row.assessLevel"
                            placeholder="请选择"
                          >
                            <el-option
                              v-for="item in assessment_level"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select> -->
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="evaluation"
                      label="综合评价 *"
                      width="320"
                      :render-header="showHelp"
                    >
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`outsrcEmpAssessList.${scope.$index}.evaluation`"
                          :rules="[
                            { required: true, message: '请填写综合评价' },
                          ]"
                        >
                        <el-tooltip class="item" effect="dark" :content="scope.row.evaluation" placement="top">
                          <el-input
                            v-model="scope.row.evaluation"
                            placeholder="请输入"
                          />
                        </el-tooltip>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="operation" label="操作">
                      <template slot-scope="scope">
                        <div class="operation_add">
                          <!-- <el-button
                            v-if="formData.assessType == '1'"
                            @click.native.prevent="
                              detailRow(scope.$index, scope.row)
                            "
                          >
                            <i class="el-icon-info"></i>
                          </el-button> -->
                          <el-button
                            @click.native.prevent="
                              deleteRow(
                                scope.$index,
                                formData.outsrcEmpAssessList,
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
    </div>

    <div class="blank"></div>

    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <!-- <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete> -->
            </el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5" v-if="formData.assessType == '0'">
                <button-common :buttonName="buttonSave" :size="size" :fns="saveInterview"></button-common>
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
// import { queryUserDept, createFlow, interView, initFlow } from "@/api/tqrcApplyfor";
import {
  add,
  createFlow,
  approvalCirculate,
  getOutsBasicInfo,
} from "@/api/wbPersonAssessment/wbPersonAssessmentApply";
import {
  getWbCompany,
  queryEmployeeTree,
  queryResourceDept,
} from "@/api/integrated/wbryzrlc";
import { doDel, getListAll, getTwoDepartDetail } from "@/api/common/flowApi";
import { queryUserDept } from "@/api/tqrcApplyfor";
import { getDicEntry } from "@/api/eosDictEntry";
import helpHint from "@/components/helpHint";
import Moment from "moment";

export default {
  name: "Add",
  components: { buttonOrange, buttonCommon, helpHint },
  data() {
    return {
      // 表单
      formData: {
        assessType: "0",
        assessAnnual: null,
        assessYear: null,
        deptName: null,
        prjMgrName: null,
        outsrcEmpAssessList: [
          {
            userCode: null, // 姓名
            userName: null,
            jobId: null,
            deptId: null, // 部门
            deptName: null,
            prjMgrCode: null, // 项目经理
            prjMgrName: null,
            position: null, // 岗位
            vendorName: null, // 供应商
            vendorCode: null,
            price: null, // 调整前价格
            postPrice: "0", // 调整后价格
            chgDate: Moment(new Date()).format("YYYY-MM-DD"), // 变更时间
            remarks: null, // 备注
            assessLevel: "", //考评得分
          },
        ],
      },
      restaurants: [], // 查询数据
      buttonName: "提交",
      buttonSave: "存草稿",
      buttonClose: "关 闭", //关闭按钮名字
      size: "mini",
      errorMessage: "", //失败消息提示,
      objectcode: "6000000300",
      objectgrade: "2",
      deptmentData: {},
      currentYear: null, // 当前年份
      position_options: [], // 岗位
      assessment_options: [
        {
          value: "0",
          label: "季度考核",
        },
        {
          value: "1",
          label: "年度考核",
        },
      ],
      year_options: [],
      annual_options: [
        {
          value: "1",
          label: "第一季度",
        },
        {
          value: "2",
          label: "第二季度",
        },
        {
          value: "3",
          label: "第三季度",
        },
        {
          value: "4",
          label: "第四季度",
        },
      ],
      assessment_level: [
        {
          value: "10",
          label: "A",
        },
        {
          value: "9",
          label: "B+",
        },
        {
          value: "7",
          label: "B",
        },
        {
          value: "5",
          label: "C",
        },
      ],
      applyData: [],
      applyYearData: [],
      currentRow: {}, //当前行
      flowItem: {},
      instanceid: "",
    };
  },
  computed: {},
  watch: {},
  created() {
    this.initYears();
    this.getDeptments();
    this.getapplyerData();
  },
  mounted() {
    // if (this.deptId != undefined) {
    //   this.formData.msgdeptno = "dep" + this.deptId;
    // }
    // let userId = ["emp" + this.userId];
    // this.msgids = userId;
    //   this.$refs.cascader.dropDownVisible = false;
    this.getAllWbs();
    this.getPosition();
  },

  methods: {
    init(params) {
      console.log(params);

      this.$nextTick((_) => {
        this.getapplyerData();
      });
    },
    // 获取申请人部门
    getapplyerData() {
      queryUserDept().then(async (res) => {
        console.log(res, "获取申请人部门");
        const { message, results, status } = await res.data;
        if (status === 200) {
          this.formData.applier = results.userId;
          this.formData.prjMgrName = results.userName;
          this.formData.deptName = results.deptName;
          this.formData.deptId = results.deptNo;
          // this.getOutsBasicinfo(results.userId);
          this.$forceUpdate();
        } else {
          this.errorMessage = message;
          this.saveError();
        }
      });
    },
    // 申请人的默认值
    reqSearch(kerword) {
      getEmployUser(kerword)
        .then((res) => {
          if (res.data.status == 200) {
            console.log(res, "1212121212212");
            this.formData.prjMgrName = res.data.results[0].empname;
            this.$forceUpdate();
          }
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    changeInput(row, index) {
      console.log('val', row, index);
      if(row.assessLevel > 10) {
        this.formData.outsrcEmpAssessList[index].assessLevel = 10;
      }
    },
    //获取部门
    getDeptments() {
      let params = {};
      params.auth = true;
      params.objectgrade = "2";
      params.allowDept = "80000122";
      params.objectcode = "6000000300";
      queryResourceDept(params)
        .then((res) => {
          if (res.data.status == 200) {
            var fild = res.data.results;
            console.log(fild, "file文件1111");
            if (res.data.results[0].children) {
              var fild = res.data.results[0].children;
            }
            var reg = /([^-]+)$/;
            this.deptmentData = fild.filter((li) => {
              li.id = li.id.replace("dep", "");
              li.text = li.text.match(reg)[1] + "(" + li.id + ")";
              return li;
            });
          } else {
            this.$message.error("获取部门数据失败");
          }
        })
        .catch((error) => {
          this.$message.error("获取部门数据失败");
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
    /** 设置日期列表 */
    initYears() {
      var myDate = new Date();
      var year = myDate.getFullYear();
      this.initSelectYear(year);
    },
    initSelectYear(year) {
      console.log('year', year);
      this.year_options = [];
      for (let i = 0; i < 2; i++) {
        this.year_options.push({ value: year - i, label: year - i + "年" });
      }
    },
    /** 获取当前季度 */
    getMonth() {
      var today = new Date();
      var month = today.getMonth() + 1;
      if (month >= 1 && month <= 3) {
        this.annual_options = [
          {
            value: "1",
            label: "第一季度",
          },
          {
            value: "4",
            label: "第四季度",
          },
        ];
      } else if (month >= 4 && month <= 6) {
        this.annual_options = [
          {
            value: "1",
            label: "第一季度",
          },
          {
            value: "2",
            label: "第二季度",
          },
        ];
      } else if (month >= 7 && month <= 9) {
        this.annual_options = [
          {
            value: "2",
            label: "第二季度",
          },
          {
            value: "3",
            label: "第三季度",
          },
        ];
      } else {
        this.annual_options = [
          {
            value: "3",
            label: "第三季度",
          },
          {
            value: "4",
            label: "第四季度",
          },
        ];
      }
    },
    /** change考核类型 */
    changeAssessType(value) {
      this.$set(this.formData, "assessAnnual", "");
      if (this.formData.assessType == "1" && this.formData.assessYear) {
        this.getYearBasic();
      };
    },
    /** change年份 */
    changeYear(value) {
      var today = new Date();
      this.currentYear = today.getFullYear();
      this.$set(this.formData, "assessAnnual", "");
      if (this.formData.assessType == "1") {
        this.getYearBasic();
      };
      // if (value == this.currentYear) {
      //   this.getMonth();
      // } else {
      //   this.annual_options = [
      //     {
      //       value: "4",
      //       label: "第四季度",
      //     },
      //   ];
      // }
    },
    /** change季度 */
    changeAnnual(val) {
      console.log('val', val);
      if(this.formData.assessYear){
        this.getAnnualBasic();
      }
    },
    /** 获取年度人员信息 */
    getYearBasic() {
      const data = {
        prjMgrCode: this.formData.applier,
        assessType: this.formData.assessType,
        assessYear: this.formData.assessYear,
      };
      const loading = common.loading(this);
      getOutsBasicInfo(data).then((res) => {
        console.log(res.data, "data外包人员信息数据");
        loading.close();
        this.applyYearData = res.data.results.map((item) => {
          return {
            ...item,
            deptId: this.formData.deptId,
            deptName: this.formData.deptName,
            // userCode: item.userCode,
            userName: item.userName,
            prjMgrName: item.prjMgrName,
            prjMgrCode: this.formData.applier,
            // position: item.position,
            // vendorName: item.vendorName,
            // vendorCode: item.vendorCode,
            assessYear: this.formData.assessYear,
            assessAnnual: '',
            // avgScore: item.avgScore,
            // assessLevel: item.assessLevel,
            jobId: item.userCode
          };
        });;
        this.formData.outsrcEmpAssessList = res.data.results.map((item) => {
          return {
            deptId: this.formData.deptId,
            deptName: this.formData.deptName,
            userCode: item.userCode,
            userName: item.userName,
            prjMgrName: item.prjMgrName,
            prjMgrCode: this.formData.applier,
            position: item.position,
            vendorName: item.vendorName,
            vendorCode: item.vendorCode,
            assessYear: this.formData.assessYear,
            assessAnnual: '',
            avgScore: item.avgScore,
            assessLevel: item.assessLevel,
            jobId: item.userCode
          };
        });
      });
    },
    /** 获取季度人员信息 */
    getAnnualBasic(val) {
      const data = {
        deptName: this.formData.deptName,
        prjMgrCode: this.formData.applier,
        assessType: this.formData.assessType,
        assessYear: this.formData.assessYear,
        assessAnnual: this.formData.assessAnnual,
      };
      // console.log('获取季度人员信息', data);
      const loading = common.loading(this);
      getOutsBasicInfo(data).then((res) => {
        console.log(res.data, "data外包人员信息数据");
        loading.close();
        this.applyData = res.data.results
        this.applyData.forEach((item) => {
          item.deptName = this.formData.deptName,
          item.userCode = item.jobId,
          item.prjMgrName = this.formData.prjMgrName,
          item.prjMgrCode = this.formData.applier,
          item.position = item.postId,
          item.vendorName = item.outsourcname,
          item.vendorCode = item.outsourceNo,
          item.assessYear = this.formData.assessYear,
          item.assessAnnual = this.formData.assessAnnual
        })
        console.log('this.applyData', this.applyData);
        this.formData.outsrcEmpAssessList = res.data.results.map((item) => {
          return {
            ...item,
            deptId: this.formData.deptId,
            deptName: this.formData.deptName,
            userCode: item.jobId,
            // userName: item.userName,
            prjMgrName: this.formData.prjMgrName,
            prjMgrCode: this.formData.applier,
            position: item.postId,
            vendorName: item.outsourcname,
            vendorCode: item.outsourceNo,
            assessYear: this.formData.assessYear,
            assessAnnual: this.formData.assessAnnual,
          };
        });
        console.log(this.formData.outsrcEmpAssessList, "考核季度11212121211");
      });
    },
    // 更改人员
    changeUserName(value, row, index) {
      console.log('this.formData.assessType', this.formData.assessType);
      console.log('更改人员', value, row, index, this.applyData, this.applyYearData);
      this.$set(this.formData.outsrcEmpAssessList, index, {})
      // 当考核类型为季度时
      if(this.formData.assessType == 0) {
      let arr = JSON.parse(JSON.stringify(this.applyData));
        // let userList = this.applyData.find((item) => item.jobId == value);
        arr.forEach(item => {
          if(item.jobId == value) {
            console.log("item", item);
            this.formData.outsrcEmpAssessList[index] = item;
            this.$set(this.formData.outsrcEmpAssessList, index, item);
          }
        })
        
      } else {
      // 当考核类型为年度时
      let arr = JSON.parse(JSON.stringify(this.applyYearData));
        let userList = arr.find((item) => item.jobId == value);
        console.log('userList', userList);
        this.formData.outsrcEmpAssessList[index] = userList;
        this.$set(this.formData.outsrcEmpAssessList, index, userList)
      }
    },
    // 查询外包商
    getAllWbs() {
      getListAll()
        .then((res) => {
          this.outsourceno_options = res.data.results;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    // 新增一行
    eduExperAdd(v) {
      var list;
      (list = {
        userCode: null,
        userName: null,
        deptId: null,
        deptName: null,
        prjMgrCode: null,
        prjMgrName: null,
        position: null,
        serviceYear: null,
        vendorName: null,
        vendorCode: null,
        price: null,
        postPrice: null,
        chgDate: Moment(new Date()).format("YYYY-MM-DD"), // 变更时间
        remarks: null,
        assessLevel: "", //考评得分
      }),
        this.formData.outsrcEmpAssessList.push(list);
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
    // 查看详情
    detailRow(index, row) {
      if (row.userCode) {
        let text = this.$router.resolve({
          path: "annualAssessRecord",
          query: {
            userCode: row.userCode,
            assessType: row.assessType,
            assessYear: row.assessYear,
          },
        });
        window.open(text.href, "_blank");
      } else {
        this.errorMessage = "请先选择人员";
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
                this.$message.success("保存成功");
                this.$router.push({ name: "wbPersonAssessment" });
              });
            } else {
              this.$message.info(res.data.message);
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
      // delete this.formData.deptName;
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          if (this.formData.assessType == "0") {
            approvalCirculate(this.formData).then((res) => {
              console.log(res, "提交");
              loading.close();
              if (res.data.status == "200") {
                let message = common.getFlowMessgeNew(res.data.message);
                let messageArr = message.split(",");
                this.$nextTick(() => {
                  this.messageNotify(messageArr);
                  // 此处更换路径
                  this.$router.push({ name: "wbPersonAssessment" });
                });
              } else {
                this.$message.info(res.data.message);
                return;
              }
            });
          } else {
            add(this.formData).then((res) => {
              loading.close();
              if (res.data.status == "200") {
                this.$message.success("考核提交成功");
                this.$router.push({ name: "wbPersonAssessment" });
              } else {
                this.$message.info(res.data.message);
                return;
              }
            });
          }
        } else {
          loading.close();
          this.$message.error("提交失败");
          return;
        }
      });
    },
    // 关闭
    toClose() {
      this.$router.push({
        name: "wbPersonAssessment",
      });
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
    // 关闭按钮
    close() {
      this.$router.push({ name: "wbPersonAssessment" });
    },
    // 自定义需求进度表头
    showHelp() {
      return this.$createElement("helpHint", {
        props: {
          content: "请从人员能力、礼仪规范、执行力等方面进行评价。",
          name: "综合评价",
        },
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
 .el-tooltip__popper {
  width: 300px;
  word-wrap: break-word;
  word-break: normal;
}
/* 级联选择器 */
.contentMain_Add .el-cascader {
  display: block;
}

/* 下拉框 */
.contentMain_Add .el-select {
  display: block;
}

/* 表格删除按钮 */
.contentMain_Add .el-icon-delete,
.el-icon-info {
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
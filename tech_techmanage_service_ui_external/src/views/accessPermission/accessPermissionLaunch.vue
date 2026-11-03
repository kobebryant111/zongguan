<!-- 数据中心门禁权限申请-员工发起 -->
<template>
  <div class="container container_zb">
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">数据中心门禁权限申请-员工发起</p>
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
    <!-- 发起面试 -->
    <div class="contentMain contentMain_Add handlerFixed">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">
          申请信息
          <span class="tips">请在收到工牌后发起申请!</span>
        </p>
      </div>
      <div class="content">
        <el-form :model="applyInfo" ref="applyInfo">
          <div class="row_1">
            <div>
              <el-row justify="space-around" :gutter="30">
                <el-col :span="8">
                  <div>
                    <el-form-item label="发起人:" label-width="120px" prop="launchBy">
                      <el-input
                        :title="applyInfo.launchByName"
                        v-model="applyInfo.launchByName"
                        disabled
                        style="width: 250px;"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <el-form-item label="发起时间:" label-width="120px" prop="launchDate">
                      <el-input
                        :title="applyInfo.launchDate"
                        v-model="applyInfo.launchDate"
                        disabled
                        style="width: 250px;"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <el-form-item label="发起部门:" label-width="120px" prop="launchDept">
                      <el-input
                        :title="applyInfo.launchDeptName"
                        v-model="applyInfo.launchDeptName"
                        disabled
                        style="width: 250px;"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <el-form-item
                      label="申请地点:"
                      label-width="120px"
                      prop="applyAddr"
                      :rules="[{ required: true, message: '请选择申请地点', trigger: 'change' }]"
                    >
                      <el-select
                        placeholder="请选择"
                        v-model="applyInfo.applyAddr"
                        style="width: 250px;"
                      >
                        <el-option
                          v-for="item in apply_addr_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item.dictid"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="16">
                  <div style="float: left;">
                    <el-form-item
                      label="预计开卡时间:"
                      label-width="120px"
                      prop="openCardDate"
                      :rules="[{ required: true, message: '请选择预计开卡时间', trigger: 'change' }]"
                    >
                      <el-date-picker
                        v-model="applyInfo.openCardDate"
                        value-format="yyyy-MM-dd"
                        :picker-options="pickerOptions"
                        style="width: 250px;"
                      ></el-date-picker>
                    </el-form-item>
                  </div>
                  <div style="float: left; padding-top: 16px; width: 300px;margin-left: 10px;">
                    <span class="tips" style="word-warp: break-word;overflow: hidden;">{{MJNote}}</span>
                  </div>
                </el-col>
                <el-col :span="8" v-if="showManager">
                  <div>
                    <el-form-item
                      label="分管总经理:"
                      label-width="120px"
                      prop="managerUserName"
                      :rules="[{ required: true, message: '请选择分管总经理', trigger: ['change','blur'] }]"
                    >
                      <el-input
                        v-model="applyInfo.managerUserName"
                        readonly="true"
                        style="width: 250px"
                        :title="applyInfo.managerUserName"
                        @focus="showDialog"
                        suffix-icon="el-icon-search"
                      >
                        <!-- <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button> -->
                      </el-input>
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
                  <div class="contentTwo_title">
                    <p class="row_2_detail">申请人信息
                      <!-- <span class="add_row" @click="eduExperAdd(1)">新增一行</span> -->
                      <!-- 申请表单 -->
                    </p>
                  </div>
                  <el-row justify="space-around" :gutter="30">
                    <el-col :span="24">
                      <el-form-item label="人员类型：" prop="userType">
                        <el-radio-group v-model="applyInfo.userType" @change="userTypeChange">
                          <el-radio label="行员"></el-radio>
                          <el-radio label="外包"></el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row justify="space-around" :gutter="30">
                    <div class="requireList_head_right_orange_common">
                      <button-common :buttonName="buttonAdd" :size="size" :fns="addUser"></button-common>
                    </div>
                    <div class="requireList_head_right_orange_common">
                      <button-common :buttonName="buttonDel" :size="size" :fns="delUser"></button-common>
                    </div>
                    <div class="requireList_head_right_orange_common">
                      <button-common :buttonName="buttonimp" :size="size" :fns="importUser"></button-common>
                    </div>
                  </el-row>
                  <el-table
                    ref="applyerInfo"
                    :data="applyInfo.applyerList"
                    style="width: 100%"
                    :header-cell-style="{'text-align':'center'}"
                    :cell-style="{'text-align':'center'}"
                    @selection-change="handleSelectionChange"
                  >
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column label="序号" type="index" width="50"></el-table-column>
                    <el-table-column prop="applyName" label="申请人姓名(工号)" width="135">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`applyerList.${scope.$index}.applyName`"
                          :rules="[{ required: true, message: '请填写申请人', trigger: 'blur' }]"
                        >
                          <el-input
                            :title="scope.row.applyName"
                            v-model="scope.row.applyName"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="applyDeptName" label="申请人部门" width="240">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`applyerList.${scope.$index}.applyDeptName`"
                          :rules="[{ required: true, message: '请选择', trigger: 'change' }]"
                        >
                          <el-input
                            :title="scope.row.applyDeptName"
                            v-model="scope.row.applyDeptName"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="applyType" label="申请类型" width="135">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`applyerList.${scope.$index}.applyType`"
                          :rules="[{ required: true, message: '请选择申请类型', trigger: 'change' }]"
                        >
                          <el-select v-model="scope.row.applyType" placeholder="请选择">
                            <el-option
                              v-for="item in apply_type_options"
                              :key="item.dictid"
                              :label="item.dictname"
                              :value="item.dictid"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="permissionsType" label="权限类别" width="135">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`applyerList.${scope.$index}.permissionsType`"
                          :rules="[{ required: true, message: '请选择权限类别', trigger: 'change' }]"
                        >
                          <el-input
                            :title="scope.row.permissionsType"
                            v-model="scope.row.permissionsType"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="reason" label="事由" width="135">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`applyerList.${scope.$index}.reason`"
                          :rules="[{ required: true, message: '请填写事由', trigger: 'blur' }]"
                        >
                          <!-- <el-input
                            :title="scope.row.reason"
                            v-model="scope.row.reason"
                            placeholder="请输入"
                          />-->
                          <el-input
                            type="textarea"
                            :title="scope.row.reason"
                            :autosize="{ minRows: 1, maxRows: 3}"
                            v-model="scope.row.reason"
                            maxlength="200"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="companyId" label="公司" width="210" v-if="showMoreCol">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`applyerList.${scope.$index}.companyName`"
                          :rules="[{ required: true, message: '请输入公司名称', trigger: 'change' }]"
                        >
                          <el-input
                            :title="scope.row.companyName"
                            v-model="scope.row.companyName"
                            placeholder="请输入"
                            disabled
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="applyTerm" label="申请时长" width="120" v-if="showMoreCol">
                      <template slot-scope="scope">
                        <el-form-item
                          :prop="`applyerList.${scope.$index}.applyTerm`"
                          :rules="[{ required: true, message: '请选择申请时长', trigger: 'change'}]"
                        >
                          <el-select v-model="scope.row.applyTerm" placeholder="请选择">
                            <el-option
                              v-for="item in apply_term_options"
                              :key="item.dictid"
                              :label="item.dictname"
                              :value="item.dictid"
                            ></el-option>
                          </el-select>
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
    </div>

    <el-dialog title="选择人员" :visible.sync="showUserDialog" @close="userClose">
      <morekeji-bank ref="add" :userType="applyInfo.userType" :fns="returnUser"></morekeji-bank>
    </el-dialog>
    <el-dialog title="上传文件" :visible.sync="ifShowEnDialog" @close="scClose">
      <div style="height: 70px">
        <div class="requireList_head_right_orange_common">
          <el-upload
            action="Fake Action"
            accept=".xlsx, .xls"
            :file-list="drList"
            :show-file-list="true"
            :http-request="changeUpFile"
            :on-change="handleChange"
            :on-remove="handleRemove"
          >
            <el-button size="small" type="primary">
              <i class="el-icon-upload2"></i>
              <span>{{drList.length == 0 ? '上传文件' : '重新上传'}}</span>
            </el-button>
          </el-upload>
        </div>
        <div class="requireList_head_right_orange_common">
          <el-button @click="downloadModle">下载模板</el-button>
        </div>

        <div class="dia-bottom">
          <el-row :gutter="10" type="flex" justify="end">
            <el-col :span="1.5">
              <button-common buttonName="取 消" size="mini" :fns="cancelEnter"></button-common>
            </el-col>
            <el-col :span="1.5">
              <button-orange buttonName="确 定" size="mini" :fns="sureEnter"></button-orange>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-dialog>
    <el-dialog title="选择分管总经理" :visible.sync="showManagerDialog" width="40%">
      <single-bank-user-for-ap :fns="returnToLaunch"></single-bank-user-for-ap>
    </el-dialog>

    <div class="nav-bottom">
      <div class="wrapper">
        <div class="wrapper_left"></div>
        <div class="wrapper_right">
          <div class="requireList_head_right_orange_common">
            <button-common :buttonName="buttonKeep" :size="size" :fns="save"></button-common>
          </div>
          <div class="requireList_head_right_orange_common">
            <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import morekejiBank from "@/views/accessPermission/add";
import { getDicEntry } from "@/api/eosDictEntry";
import {
  getAccessControlInfoByUserId,
  saveDraft,
  flowStartAndRun,
  importExceltAccessControl,
  queryDetailForDept
} from "@/api/dataCenter/dataCenAccessPermission";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import common from "@/api/common";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonDelete from "@/components/eleButton/delete"; //引入删除按钮
import singleBankUserForAp from "./singleBankUserForAp";

import { getdeptIdForUsers } from "@/api/userApi/bankUser";
import wbs from "@/views/plugins/components/WaiBaoShang";
import departmentTwo from "@/views/plugins/components/DepartmentTwo";
import employee from "@/views/plugins/components/Employee";
import { getDeptTwo } from "@/api/systemApi/department";
import {
  getWbCompany,
  queryWorkItem,
  queryEmployeeTree
} from "@/api/integrated/wbryzrlc";
// import helpHint from '@/views/crewManager/common/helpHint';
import helpHint from "@/components/helpHint";
import customBr from "@/components/helpHint/customBr";
import { doDel, getListAll, getDownloadUrl } from "@/api/common/flowApi";
import XLSX from "xlsx";
import { mapGetters } from "vuex";
import store from "@/store";
import Moment from "moment";
import request from "@/utils/request";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
import { checkedIsBlack } from "@/api/integrated/wbHmd";
import { checkedExistEntry } from "@/api/integrated/newWbrz";
export default {
  name: "accessPermissionLaunch",
  components: {
    morekejiBank,
    wbs,
    departmentTwo,
    employee,
    buttonOrange,
    buttonCommon,
    buttonDelete,
    helpHint,
    customBr,
    singleBankUserForAp
  },
  data() {
    return {
      size: "small",
      totalNumber: 0,
      showUserDialog: false, //选择人员
      buttonAdd: "增加", //增加按钮
      buttonDel: "删除", //删除按钮
      buttonimp: "导入", //导入按钮
      buttonClose: "关闭", //关闭按钮
      buttonKeep: "存草稿", //保存按钮
      buttonCommit: "提交", //提交按钮
      depData: [], //部门数据
      selectRows: [], //选中行
      dept: "", //登录人所在一级部门id

      // 申请表单
      applyInfo: {
        launchBy: "", //发起人
        launchByName: "", //发起人名称
        launchDate: "", //发起时间
        launchDept: "", //发起部门
        launchDeptName: "", //发起部门名称
        applyAddr: "", //申请地点
        openCardDate: "", //预计开卡时间
        userType: "", //人员类型
        managerUserid: "", //分管总经理id
        managerUserName: "", //分管总经理名称
        applyerList: [
          // {
          //   applyName: "", //申请人
          //   applyUserid: "", //申请人工号
          //   applyDept: "", //申请人部门
          //   applyDeptName: "", //申请人部门名称
          //   applyType: "", //申请类型
          //   permissionsType: "", //权限类别
          //   reason: "", //事由
          //   companyId: "", //公司
          //   applyTerm: "" //申请时长
          // }
        ]
      },
      bps: {}, //bps数据
      drFileData: {}, //导入文件信息
      drList: [], //导入列表
      ifShowEnDialog: false, //是否显示导入弹窗
      apply_addr_options: [], //申请地点列表
      apply_type_options: [], //申请类型列表
      apply_term_options: [], //申请时长列表
      pickerOptions: {},
      showMoreCol: false, //显示公司和开卡时长
      MJNote: "", //备注
      showManager: false, //显示分管总经理
      showManagerDialog: false
    };
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  watch: {
    mpOpts(newVal, oldVal) {
      if (oldVal.length != 0) {
        this.applyInfo.rlId = "";
      }
    }
  },
  beforeUpdate() {},
  created() {},
  mounted() {
    this.init();
  },

  methods: {
    init() {
      this.applyInfo.launchBy = this.userId;
      this.applyInfo.launchByName = this.name + "(" + this.userId + ")";
      this.applyInfo.launchDept = this.deptId;
      this.applyInfo.launchDate = Moment(new Date()).format(
        "YYYY-MM-DD HH:mm:ss"
      );
      this.getDep();
      this.getDict();
      this.getManager();
      this.pickerOptions = {
        // 选择当天之后的日期
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7; //
        }
      };
    },

    showDialog() {
      this.showManagerDialog = true;
    },

    returnToLaunch(val) { 
      this.applyInfo.managerUserid = val.id;
      this.applyInfo.managerUserName = val.text;
      this.showManagerDialog = false;
    },

    //分管总经理
    getManager() {
      let params = {};
      params.depid = this.deptId;
      queryDetailForDept(params).then(res => { 
        if (res.data.status == 200) {
          let data = res.data.results; 
          this.applyInfo.launchDeptName = res.data.results.location;
          if (data.adminid != "130") {
            this.showManager = true;
          }
        }
      });
    },

    addUser() {
      if (
        this.applyInfo.userType == undefined ||
        this.applyInfo.userType == ""
      ) {
        this.$message({
          showClose: true,
          message: "请选择人员类型！",
          type: "error"
        });
      } else {
        this.showUserDialog = true;
      }
    },

    returnUser(val, operate) {
      this.showUserDialog = false; 
      if (operate == "确定") {
        let userString = "";
        if (val.length > 0) {
          for (var i = 0; i < val.length; i++) {
            userString = userString + val[i].id + ",";
          }
          if (userString.length > 0) {
            userString = userString.substring(0, userString.length - 1);
            let params = {};
            params.userIds = userString;
            params.userType = this.applyInfo.userType;
            getAccessControlInfoByUserId(params).then(res => { 
              if (res.data.status == 200) {
                let list = res.data.results.applyerList;
                for (var i = 0; i < list.length; i++) {
                  this.applyInfo.applyerList.push(list[i]);
                }
              }
            });
          }
        }
      }
    },

    userTypeChange(val) {
      if (val == "行员") {
        this.showMoreCol = false;
        this.applyInfo.applyerList = [];
      }
      if (val == "外包") {
        this.showMoreCol = true;
        this.applyInfo.applyerList = [];
      }
    },

    handleSelectionChange(val, index) {
      this.selectRows = val; 
    },

    // 获取部门
    getDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          if (res.data.status == 200) {
            var fild = res.data.results[0].children;
            let name = res.data.results[0].text;
            this.depData = fild.filter(tt => {
              tt.text = tt.text.replace(name + "-", "");
              return tt;
            });
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    getDict() {
      // 查询业务字典
      getDicEntry({
        dicttypeid:
          "PPM_SJZXMJ_APPLYDD,PPM_ACCESS_APPLY_STATE,PPM_ACCE_APPLYTIME,PPM_SJZXMJ_BZ"
      }).then(res => {
        if (res.data.status !== 200) {
          this.alter(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // 申请地点
        this.apply_addr_options = data.filter(item => {
          return item.dicttypeid == "PPM_SJZXMJ_APPLYDD";
        });

        //申请类型
        this.apply_type_options = data.filter(item => {
          return item.dicttypeid == "PPM_ACCESS_APPLY_STATE";
        });

        // 申请时长
        this.apply_term_options = data.filter(item => {
          return item.dicttypeid == "PPM_ACCE_APPLYTIME";
        });

        // 备注
        this.MJNote = data.filter(item => {
          return item.dicttypeid == "PPM_SJZXMJ_BZ";
        })[0].dictname;

        return data;
      });
    },

    delUser() {
      let list = this.selectRows;
      if (list.length > 0) {
        let index = "";
        for (var i = 0; i < list.length; i++) {
          index = this.applyInfo.applyerList.findIndex(item => {
            return item.applyUserid == list[i].applyUserid;
          });
          this.applyInfo.applyerList.splice(index, 1);
        }
      }
    },

    //保存
    save() {
      if (
        this.applyInfo.applyAddr == undefined ||
        this.applyInfo.applyAddr == ""
      ) {
        this.$message.error("请先选择申请地点！");
      } else {
        const loading = common.loading(this);
        saveDraft(this.applyInfo)
          .then(res => {
            loading.close();
            if (res.data.status == 200) {
              this.instanceId = res.data.results;
              this.getWorkItem(res.data.results);
              this.$message.success("保存草稿成功");
              this.$router.push({ name: "dashboard" });
            } else {
              this.$message.error("保存草稿失败");
            }
          })
          .catch(error => {
            this.$message.error(error);
          });
      }
    },

    //提交
    proving() {
      this.$refs["applyInfo"].validate(valid => {
        if (valid) {
          if (
            this.applyInfo.applyerList != undefined &&
            this.applyInfo.applyerList.length > 0
          ) {
            if (this.applyInfo.userType == "外包") {
              this.$confirm(
                "外包属于强监管人员，流程发起人负有连带责任，确认给此外包人员申请数据中心门禁权限吗?",
                {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "warning"
                }
              )
                .then(() => {
                  this.commit();
                })
                .catch(error => {});
            } else if (this.applyInfo.userType == "行员") {
              this.commit();
            }
          } else {
            this.$message({
              showClose: true,
              message: "请添加申请人信息！",
              type: "error"
            });
            return false;
          }
        } else {
          this.$message({
            showClose: true,
            message: "操作失败,请填写必填项",
            type: "error"
          });
          return false;
        }
      });
    },

    commit() {
      const loading = common.loading(this);
      flowStartAndRun(this.applyInfo).then(res => {
        loading.close();
        if (res.data.status == 200) {
          let message = common.getFlowMessge(res.data.message);
          let messageArr = message.split(",");
          messageArr[2] = messageArr[2].split(")")[0] + ")";
          this.messageNotifyMore(messageArr);
          this.$router.push({ name: "dashboard" });
        }
      });
    },

    importUser() {
      if (
        this.applyInfo.userType == undefined ||
        this.applyInfo.userType == ""
      ) {
        this.$message({
          showClose: true,
          message: "请选择人员类型！",
          type: "error"
        });
      } else {
        this.ifShowEnDialog = true;
      }
    },

    userClose() {
      this.$refs.add.searchUser = "";
      this.$refs.add.loadDefaultNode();
      this.$refs.add.flClean();
    },

    // 关闭导入框
    scClose() {
      this.drList = [];
    },

    // 取消导入
    cancelEnter() {
      this.ifShowEnDialog = false;
    },

    // 确认导入
    sureEnter() {
      if (this.drList.length == 0) {
        this.$message.error("请上传文件");
        return;
      }
      const loading = common.loading(this);
      var formData = new FormData();
      formData.append("file", this.drFileData);
      formData.append("userType", this.applyInfo.userType);
      importExceltAccessControl(formData)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.$message.success("导入成功");
            this.ifShowEnDialog = false;
            let list = res.data.results.applyerList;
            for (var i = 0; i < list.length; i++) {
              this.applyInfo.applyerList.push(list[i]);
            }
          } else {
            this.$message.error("导入失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //下载模板
    downloadModle() {
      const loading = common.loading(this);
      getDownloadUrl({ key: "SJZXMJQX_USER_NEW" }).then(res => {
        loading.close();
        if (res.data.status == 200) { 
          const self = this;
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
      });
    },

    // 上传变化
    changeUpFile(file) {
      this.drFileData = file.file;
    },

    // on-change
    handleChange(file, fileList) {
      this.drList = [fileList[fileList.length - 1]];
    },

    // on-remove
    handleRemove() {
      this.drList = [];
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: "accessPermission" });
    },

    //获取待办工作项详情-单条
    getWorkItem(val) {
      const loading = common.loading(this);
      let params = {};
      params.processInstID = val;
      queryWorkItem(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.bps = res.data.results;
            this.delBtnIf = true;
          } else {
            this.$message.error("获取bps失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 消息提示(多节点)
    messageNotifyMore(messageVal) {
      if (messageVal.length > 3) {
        messageVal[messageVal.length - 1] =
          messageVal[messageVal.length - 1].split(")")[0] + ")";
      }
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
        duration: 0
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
        duration: 0
      });
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
    }
  }
};
</script>
<style scoped>
.handlerFixed {
  margin-bottom: 80px;
}

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
  width: 300px;
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

.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  margin-top: 20px;
  padding: 15px 0;
  z-index: 999;
}

.nav-bottom .wrapper {
  width: 80%;
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

.requireList_head_right_orange_common {
  margin: 0 5px 10px 5px;
  float: left;
}

.tips {
  color: red;
  font-size: 14px;
  font-weight: 500;
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
<style>
.el-tooltip__popper {
  max-width: 500px;
}
</style>
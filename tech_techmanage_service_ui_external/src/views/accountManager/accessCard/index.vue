<!-- 监保部台账管理列表 -->
<template>
  <div class="list-container accountList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form :model="paramsForm" ref="formRef" label-width="110px" :label-position="'left'">
        <el-row type="flex" justify="space-between" :gutter="20">
          <el-col :span="6">
            <el-form-item prop label="姓名：">
              <el-input placeholder="请输入" v-model="paramsForm.userName" filterable />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="是否需要制卡：">
              <!-- <el-select v-model="paramsForm.deptVal" filterable>
                <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id" ></el-option>
              </el-select> -->
              <!-- <el-input v-model="paramsForm.ifMakeCard" placeholder="请输入" filterable></el-input> -->
              <el-select placeholder="请选择" v-model="paramsForm.ifMakeCard">
                <el-option v-for="item in makeCard_options" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="是否已领用：">
              <!-- <el-input placeholder="请输入" v-model="paramsForm.ifReceiveCard" filterable /> -->
              <el-select placeholder="请选择" v-model="paramsForm.ifReceiveCard">
                <el-option v-for="item in receiveCard_options" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="是否发起归还：">
              <!-- <el-input placeholder="请输入" v-model="paramsForm.state" filterable /> -->
              <el-select placeholder="请选择" v-model="paramsForm.ifCreateReturn">
                <el-option v-for="item in makeCards_options" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-between" :gutter="20">
          <el-col :span="6">
            <el-form-item prop label="是否已归还：">
              <!-- <el-input placeholder="请输入" v-model="paramsForm.state" filterable /> -->
              <el-select placeholder="请选择" v-model="paramsForm.ifReturnCard">
                <el-option v-for="item in returnCard_options" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-row type="flex" justify="end">
              <clean-btn :size="sizes" :forms="this.paramsForm" class="cleanbtn" />
              <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn" ></button-orange>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="requireContainer">
      <div class="requireContainer_head">
        <div class="requireContainer_head_en"></div>
        ·<el-row type="flex" justify="end" >
          <el-col :span="12" class="el_col_button">
          <el-upload style="display: inline-block" action="Fake Action" :file-list="fileList" :show-file-list="false" :http-request="changeUpload" v-if="auth">
            <el-button size="medium">导入</el-button>
          </el-upload>
          </el-col>
          <el-col :span="12" class="el_col_button">
          <el-button type="primary" :buttonName="buttonReceive" size="medium" @click="flowInit(1)" v-if="auth">制卡</el-button>
          </el-col>
          <el-col :span="12" class="el_col_button">
          <el-button type="warning" :buttonName="buttonReturn" size="medium" @click="flowInit(2)" v-if="auth">归还</el-button>
            <!-- <el-button :buttonName="buttonUpdates" size="medium" @click="updates()" v-if="auth">批量修改</el-button> -->
          </el-col>
        </el-row>
      </div>
      <el-table :data="crewData" highlight-current-row height="660" fixed="left" tooltip-effect="dark" @selection-change="handleSelectionChange" >
        <el-table-column type="selection" width="55" ></el-table-column>
        <el-table-column label="id" prop="id" width="80" v-if="false"/>
        <el-table-column label="姓名" prop="userName" width="80" />
        <el-table-column label="性别" prop="sex" width="80" >
          <template slot-scope="scope">
            <span v-if="scope.row.sex == '0'">男</span>
            <span v-else-if="scope.row.sex == '1'">女</span>
          </template>
        </el-table-column>
        <el-table-column label="身份证号" prop="idCardNo" width="150" show-overflow-tooltip/>
        <el-table-column label="电话" prop="telephone" width="110" show-overflow-tooltip />
        <el-table-column label="供应商" prop="outsourceName" width="150" show-overflow-tooltip/>
        <el-table-column label="所属部门" prop="deptName" width="150" show-overflow-tooltip/>
        <el-table-column label="人员编号" prop="virtualMachineId" width="150" show-overflow-tooltip/>
        <el-table-column label="是否需要制卡" prop="ifMakeCard" width="110" show-overflow-tooltip >
          <template slot-scope="scope">
            <span v-if="scope.row.ifMakeCard == 'Y'">是</span>
            <span v-else-if="scope.row.ifMakeCard == 'N'">否</span>
            <span v-else-if="scope.row.ifMakeCard == 'W'">是</span>
          </template>
        </el-table-column>
        <el-table-column label="是否发起归还" prop="ifCreateReturn" width="110" show-overflow-tooltip >
          <template slot-scope="scope">
            <span v-if="scope.row.ifCreateReturn == 'Y'">是</span>
            <span v-else-if="scope.row.ifCreateReturn == 'N'">否</span>
          </template>
        </el-table-column>
        <el-table-column label="门禁卡编号" prop="accessCardNumber" width="140" show-overflow-tooltip />
        <el-table-column label="是否已领用" prop="ifReceiveCard" width="90" show-overflow-tooltip >
          <template slot-scope="scope">
            <span v-if="scope.row.ifReceiveCard == 'Y'">已领用</span>
            <span v-else-if="scope.row.ifReceiveCard == 'N'">未领用</span>
          </template>
        </el-table-column>
        <el-table-column label="领用日期" prop="receiveTime" width="100" show-overflow-tooltip />
        <el-table-column label="是否已归还" prop="ifReturnCard" width="90" show-overflow-tooltip >
          <template slot-scope="scope">
            <span v-if="scope.row.ifReturnCard == 'E'">遗失</span>
            <span v-else-if="scope.row.ifReturnCard == 'Y'">已归还</span>
            <span v-else-if="scope.row.ifReturnCard == 'N'">未归还</span>
            <span v-else-if="scope.row.ifReceiveCard == 'N' && scope.row.ifReturnCard == undefined">无需归还</span>
          </template>
        </el-table-column>
        <el-table-column label="归还日期" prop="returnTime" width="100" show-overflow-tooltip />
        <el-table-column label="银行卡号" prop="creditCardNumbers" width="100" show-overflow-tooltip />
        <el-table-column label="户名" prop="accountNumber" width="100" show-overflow-tooltip />
        <el-table-column label="开户地所在省份" prop="placeOfAccount" width="100" show-overflow-tooltip />
        <el-table-column label="转出卡银行名称" prop="outBankName" width="100" show-overflow-tooltip />>
        <el-table-column label="备注" prop="remark" show-overflow-tooltip />
        <el-table-column label="操作" width="120"  v-if="auth">
          <template slot-scope="scope">
            <!-- <el-link type="warning" :underline="false" @click="onUserDetail(scope.row.badge)">详情</el-link> -->
            <el-link type="warning" :underline="false" @click="handleUpdate(scope.row)" >修改</el-link >
            <!-- <el-link type="warning" :underline="false" @click="handleDel(scope.row)" >删除</el-link > -->
            <!-- <el-link type="warning" :underline="false" @click="handleDetail(scope.row)" >详情</el-link > -->
          </template>
        </el-table-column>
      </el-table>
      <div class="pager_zb">
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index" v-model="$attrs" ref="pager" />
      </div>
    </div>
    <!-- 弹框  -->
    <el-dialog title="批量修改" :visible.sync="dialogVisibleUpdates" width="50%" :before-close="handleClose" >
      <div>
        <el-form :model="updFormData0" ref="updFormData0">
          
          <el-row type="flex" justify="space-between">
            <!-- <el-col :span="11">
              <el-form-item label="门禁卡编号" prop="accessCardNumber">
                <el-input v-model="updFormData0.accessCardNumber" placeholder="门禁卡编号"></el-input>
              </el-form-item>
            </el-col> --> 
            <el-col :span="11">
              <el-form-item label="领用日期" prop="receiveTime">
                <!-- <el-input v-model="updFormData0.receiveTime" placeholder="领用日期"></el-input> -->
                <el-date-picker v-model="updFormData0.receiveTime" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"/>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <div>
                <p>是否已归还</p>
                <el-form-item prop="ifReturnCard">
                  <!-- <el-input v-model="updFormData2.ifReturnCard" placeholder="是否已归还"></el-input> -->
                <el-select v-model="updFormData0.ifReturnCard" placeholder="请选择" >
                  <el-option v-for="item in returnCard_options" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled"/>
                </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="归还日期" prop="returnTime" >
                <!-- <el-input v-model="updFormData0.returnTime" placeholder="归还日期"></el-input> -->
                <el-date-picker v-model="updFormData0.returnTime" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"/>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="备注" prop="remarkJbb" >
                <el-input v-model="updFormData2.remarkJbb" placeholder="备注"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- <el-row type="flex" justify="space-between">
            
            <el-col :span="11">
            </el-col>
          </el-row> -->
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleUpdates = false">取 消</el-button>
        <el-button type="primary" @click="onSubmitExt(3)">确 定</el-button>
      </span>
    </el-dialog>
    
    <el-dialog title="修改信息" :visible.sync="dialogVisibleJbb" width="50%" :before-close="handleClose" >
      <div>
        <el-form :model="updFormData2" ref="updFormData2">
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="姓名" prop="userName">
                <el-input v-model="updFormData2.userName" placeholder="姓名" disabled ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="所属部门" prop="deptName">
                <el-input v-model="updFormData2.deptName" placeholder="所属部门" disabled ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="门禁卡编号" prop="accessCardNumber">
                <el-input v-model="updFormData2.accessCardNumber" placeholder="门禁卡编号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="领用日期" prop="receiveTime">
                <el-date-picker v-model="updFormData2.receiveTime" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <div>
                <p>是否已归还</p>
                <el-form-item prop="ifReturnCard">
                  <!-- <el-input v-model="updFormData2.ifReturnCard" placeholder="是否已归还"></el-input> -->
                <el-select v-model="updFormData2.ifReturnCard" placeholder="请选择" >
                  <el-option v-for="item in returnCard_options" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="11">
              <el-form-item label="归还日期" prop="returnTime" >
                <el-date-picker v-model="updFormData2.returnTime" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="银行卡号" prop="creditCardNumbers" >
                <el-input v-model="updFormData2.creditCardNumbers" placeholder="银行卡号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="户名" prop="accountNumber" >
                <el-input v-model="updFormData2.accountNumber" placeholder="户名"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="开户地所在省份" prop="placeOfAccount" >
                <el-input v-model="updFormData2.placeOfAccount" placeholder="开户地所在省份"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="转出卡银行名称" prop="outBankName" >
                <el-input v-model="updFormData2.outBankName" placeholder="转出卡银行名称"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="备注" prop="remarkJbb" >
                <el-input v-model="updFormData2.remarkJbb" placeholder="备注"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleJbb = false">取 消</el-button>
        <el-button type="primary" @click="onSubmitExt(2)">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改信息" :visible.sync="dialogVisibleYgb" width="50%" :before-close="handleClose" >
      <div>
        <el-form :model="updFormData1" ref="updFormData1">
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="姓名" prop="userName">
                <el-input v-model="updFormData1.userName" placeholder="姓名" disabled ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="所属部门" prop="deptName">
                <el-input v-model="updFormData1.deptName" placeholder="所属部门" disabled ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- <el-row type="flex" justify="space-between">
            <el-col :span="22">
              <div>
                <p>是否制卡</p>
                <el-form-item prop="ifMakeCard">
                  <el-select v-model="updFormData1.ifMakeCard" placeholder="请选择" >
                    <el-option v-for="item in makeCards_options" :key="item.value" :label="item.label" :value="item.value" />
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row> -->
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="银行卡号" prop="creditCardNumbers" >
                <el-input v-model="updFormData1.creditCardNumbers" placeholder="银行卡号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="户名" prop="accountNumber" >
                <el-input v-model="updFormData1.accountNumber" placeholder="户名"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="开户地所在省份" prop="placeOfAccount" >
                <el-input v-model="updFormData1.placeOfAccount" placeholder="开户地所在省份"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="转出卡银行名称" prop="outBankName" >
                <el-input v-model="updFormData1.outBankName" placeholder="转出卡银行名称"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="备注" prop="remarkYgb" >
                <el-input v-model="updFormData1.remarkYgb" placeholder="备注"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onSubmitReturn()" >取 消</el-button>
        <el-button type="primary" @click="onSubmitExt(1)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
import { queryPager, queryDetail, addLedger, updateLedger, updateLedgers, importInfo } from "@/api/accountManager/accessCardLedger";
import { initFlow } from "@/api/accountManager/accessCardApply";
import { queryRole } from "@/api/kqManager/calendar";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept, getWbCompany } from "@/api/integrated/wbryzrlc";
import Moment from "moment";
import { getFlowData, getListAll } from "@/api/common/workflowApi";
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
export default {
  name: "accessCardIndex",
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      totalNumber: 0,
      size: 10, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramsForm: {
        userName: "",
        ifMakeCard: "",
        ifReceiveCard: "",
        ifReturnCard: "",
        ifCreateReturn: ""
      },
      updFormData0: {
        accessCardNumber: "",
        receiveTime: "",
        ifReceiveCard: "",
        returnTime: "",
        ifReturnCard: "",
        jobId: "",
        id: null,
      },
      updFormData2: {
        accessCardNumber: "",
        receiveTime: "",
        returnTime: "",
        creditCardNumbers: "",
        accountNumber: "",
        placeOfAccount: "",
        outBankName: "",
        remarkJbb: "",
        jobId: "",
        id: null,
      },
      updFormData1: {
        accessCardNumber: "",
        receiveTime: "",
        placeOfAccount: "",
        returnTime: "",
        creditCardNumbers: "",
        accountNumber: "",
        outBankName: "",
        remarkYgb: "",
        jobId: "",
        id: null,
      },
      fileList: [], // 文件上传
      crewData: [], // 列表分页数据
      crewDataCopy: [],
      drawer: false, // 默认不显示
      peo: "zh",
      buttonReceive: "制 卡", //新增
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      deptno: "",
      depData: [],
      outsourceno_options: [], //外包商
      buttonImport: "导 入", //导出按钮
      rysx_options: [], //人员属性集合
      auth: false, // 是否有权限查看单价
      buttonReturn: '归 还', //归还按钮
      buttonUpdates: '批量修改', //批量修改
      dialogVisibleJbb: false,
      dialogVisibleYgb: false,
      dialogVisibleUpdates: false,
      selectRows: [], //选中行
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
          disabled: true,
        }
      ],
      makeCard_options: [
        {
          value: "1",
          label: "是",
        },
        {
          value: "0",
          label: "否",
        },
      ],
      makeCards_options: [
        {
          value: "Y",
          label: "是",
        },
        {
          value: "N",
          label: "否",
        },
      ],
    };
  },
  created() {
    this.filDep();
    this.loadInit();
  },

  methods: {

    /** 分页初始化方法 */
    loadInit: function () {
      let params = this.paramsForm;
      params.size = this.size;
      params.index = this.index;
      this.getPagerList(params);
      this.getPermission();
    },

    /** 获取列表数据 */
    getPagerList(params) {
      let data = common.paramsFormat(params);
      queryPager(data).then((res) => {
        if (res.data.status == 200) {
          this.crewData = res.data.results;
          console.log(this.crewData, "台账数据");
          this.totalNumber = res.data.total;
        }
      });
    },
    /** 获取部门 */
    filDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then((res) => {
          var fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter((tt) => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
          console.log(this.depData,"-1-1-1-1--1")
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },

    // 导入门禁卡编号
    changeUpload(file, fileList) {
      var formData = new FormData();
      formData.append("file", file.file);
      let data = formData;
      importInfo(data).then(res => {
        if (res.data.status == "200") {
          this.$message.success("导入成功");
        } else {
          this.$message.error(res.data.message);
        }
        console.log("ref")
        this.refPager();
      });
    },

    /** 搜索事件 */
    submitBtn: function () {
      let params = this.paramsForm;
      params.size = this.size;
      params.index = 1;
      this.index = 1;
      this.getPagerList(params); //bin
    },

    /** 点击table行 */
    onSelectTable: function (selection, row) {

    },

    handleSelectionChange(val, index) {
      this.selectRows = val; 
      console.log("------",this.selectRows);
    },
    /** 分页触发 */
    getPager: function (size, index) {
      // console.log("getPager:size->",size ,";index->",index)
      let params = this.paramsForm;
      this.size = size;
      this.index = index;
      params.size = size;
      params.index = index;
      this.getPagerList(params);
    },
    //刷新分页
    refPager(){
      let params = this.paramsForm;
      params.size = this.size;
      params.index = this.index;
      this.getPagerList(params);
    },

    /** 修改 */
    handleUpdate(row) {
      // this.updFormData2 = row
      console.log(row)
      if (row.ifUpdateRoles == 0){
        //需要将row数据转换成一个新对象，赋值
        this.updFormData1 = JSON.parse(JSON.stringify(row));
        this.dialogVisibleYgb = true;
      }else if (row.ifUpdateRoles == 1){
        this.updFormData2 = JSON.parse(JSON.stringify(row));
        this.dialogVisibleJbb = true;
      }else {
        this.$message.error("无权限修改此信息！");
      }
    },
    onSubmitReturn(row){
      this.dialogVisibleYgb = false;
    },
    /** 提交 */
    onSubmitExt(val){
      // const loading = common.loading(this);
      let formDateVal;
      if (val == 1) {
        formDateVal = this.updFormData1;
      }else if (val == 2) {
        formDateVal = this.updFormData2;
      }else if (val == 3) {
        formDateVal = this.updFormData0;
        this.updLedgers(formDateVal);
        return;
      }

      if (!formDateVal.id) {
        console.log("id",false);
        this.saveLedger(formDateVal);
      } else {
        this.updLedger(formDateVal);
        console.log("id",true);
      }
    },

    /** 修改台账 */
    updLedgers(val) {
      let idList = '';
      this.selectRows.forEach(obj =>{
        idList += obj.id + ","
      })
      val.idList = idList;
      console.log("val",val);
      const loading = common.loading(this);
      updateLedgers(val).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.dialogVisibleUpdates = false;
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },

    /** 修改台账 */
    updLedger(val) {
      const loading = common.loading(this);
      updateLedger(val).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.dialogVisibleYgb = false;
          this.dialogVisibleJbb = false;
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },

    /** 保存台账 */
    saveLedger(val) {
      const loading = common.loading(this);
      addLedger(val).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.dialogVisibleYgb = false;
          this.dialogVisibleJbb = false;
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    //批量修改台账
    updates(){
      if(this.selectRows.length == 0){
        this.$message.error("请选择");
        return;
      }
      this.dialogVisibleUpdates = true;
    },
    // 制卡流程发起
    flowInit(flowType) {
      if(this.selectRows.length == 0){
        this.$message.error("请选择");
        return;
      }

      if(flowType == 1){
        let flag = true;
        let flags = true;
        let flagt = true;
        let str = '';
        this.selectRows.forEach(obj =>{
          console.log("ifMakeCard:ifReturnCard",obj.ifMakeCard,"-1----",obj.ifReturnCard);
          //当条件满足是否制卡不等于N 或是否归还不等于遗失的情况 提示
          if (obj.ifMakeCard != 'N' && obj.ifReturnCard != 'E'){
            str += obj.userName + ",";
            flag = false;
          }

          if (obj.ifReturnCard == 'E'){
            flags = false;
          }

          if (obj.state == 'OLD'){
            flagt = false;
          }
        });

        if(flag){
          if (!flags){
            if (flagt){
              this.$confirm("该人员已发起过制卡流程，是否重新发起?", {
                confirmButtonText: "是",
                cancelButtonText: "否",
                type: "warning",
              }).then(() => {
                this.selectRows.forEach(obj =>{
                  obj.ifMakeCard = 'N';
                });
                initFlow({"accessCardApplyInfoList": this.selectRows ,"type": "Receive"}).then((res) => {
                  this.loadInit();
                  this.$message.success("重新发起制卡成功");
                }).catch((error) => {
                  this.$message.error(error);
                });
              }).catch((error) => {});
            }else{
              this.$message.error(str + "已发起再制卡流程,无法再次发起制卡流程");
            }
          }else {
            this.$confirm("是否确认发起制卡?", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
              this.selectRows.forEach(obj =>{
                obj.ifMakeCard = 'N';
              });
              initFlow({"accessCardApplyInfoList": this.selectRows ,"type": "Receive"}).then((res) => {
                this.loadInit();
                this.$message.success("发起制卡成功");
              }).catch((error) => {
                this.$message.error(error);
              });
              }).catch((error) => {});
            }
        }else{
          this.$message.error(str + "已发起制卡流程,无法再次发起制卡流程");
        }
      }else if (flowType == 2){ 
        let flag = true;
        let str = '';
        this.selectRows.forEach(obj =>{
          if (obj.ifReceiveCard != 'Y' || obj.ifReturnCard != 'N' || obj.ifCreateReturn == 'Y'){
            str += obj.userName + ",";
            flag = false;
          }
        });
        if(flag){
          this.$confirm("是否确认发起归还?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          initFlow({"accessCardApplyInfoList": this.selectRows, "type": "Return"}).then((res) => {
              this.loadInit();
              this.$message.success("发起归还成功");
            }).catch((error) => {
              this.$message.error(error);
            });
        })
        .catch((error) => {});
        }else {
          this.$message.error(str + "未领用门禁卡或已归还门禁卡,无法发起归还流程");
        }
      }
      
      // this.$router.push({
      //   name: "lymjsq",
      //   query: {
      //     formScene: "lymjApply"
      //   }
      // });
    },
    // 关闭弹框
    handleClose() {
      this.dialogVisibleJbb = false;
      this.dialogVisibleYgb = false;
      this.dialogVisibleUpdates = false;
    },
    /** 删除 */
    handleDel(row) {
      this.$confirm("确认删除吗?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          delAccount(row.id)
            .then((res) => {
              this.loadInit();
            })
            .catch((error) => {
              this.$message.error(error);
            });
        })
        .catch((error) => {});
    },
    /** 详情 */
    handleDetail(row) {
      console.log(row,"******row")
      this.$router.push({
        name: "personDetail",
        params: row
      })
    },
    /** ntko */

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
     /** 获取权限 */
    getPermission() {
      queryRole().then((res) => {
        if (res.data.status == 200) {
          let roles = res.data.results;
          console.log(res.data.results, "99999权限");
          let auth = false;
          roles.forEach(function (item) {
            if (item.roleId == "00000001" || item.roleId == "PERMANAGE") {
              auth = true;
            }
          });
          this.auth = auth;
          console.log("auth", this.auth);
        } else {
          this.$message.error("查询权限失败");
        }
      });
    },
  },
};
</script>
<style scoped>

.margin_bottom {
  margin-bottom: 15px
}

.el-button--medium {
  padding: 8px 16px;
}

.accountList {
  width: 100%;
  padding: 15px;
}

.contractList_head {
  background-color: #ffffff;
  padding: 18px;
  display: flex;
  justify-content: space-between;
}
.form_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px 18px 0 18px;
}
.cleanbtn{
 margin-right: 10px;
}
.requireContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 15px;
}

.requireContainer_head {
  float: right;
  margin-bottom: 10px;
}

.el_col_button {
  margin-right: 2px;
}

.requireContainer_head_en {
  width: 160px;
}

.requireList_head_right {
  float: right;
}

.requireList_head_right div {
  display: inline-block;
}

.requireList_head_right_clean {
  margin-right: 10px;
}

.head_right_orange {
  margin: 0 6px 0 10px;
}

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
</style>

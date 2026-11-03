<!-- 小组管理列表 -->
<template>
  <div class="list-container accountList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form :model="paramsForm" ref="paramsForm" label-width="70px" :label-position="'left'">
        <el-row type="flex" justify="space-between" :gutter="20">
          <el-col :span="6">
            <el-form-item prop label="姓名：">
              <el-input placeholder="请输入" v-model="paramsForm.userName" filterable />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="类型">
              <el-select placeholder="请选择" v-model="paramsForm.outsourceType">
                <el-option v-for="item in outsourceTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="状态">
              <el-select placeholder="请选择" v-model="paramsForm.state">
                <el-option v-for="item in stateOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop label="小组名称">
              <el-input placeholder="请输入" v-model="paramsForm.groupName" filterable />
            </el-form-item>
          </el-col>
          <!-- <el-col :span="6">
            <el-row type="flex" justify="end">
              <clean-btn :size="sizes" :forms="this.paramsForm" class="cleanbtn" />
              <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn" ></button-orange>
            </el-row>
          </el-col> -->
        </el-row>
        <el-row type="flex" justify="end">
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
        <el-row type="flex" justify="end" class="margin_bottom">
            <el-button :buttonName="buttonExport" size="medium" @click="handelExport()" v-if="auth">导出</el-button>
            <el-button type="warning" :buttonName="buttonManager" size="medium" @click="handleManager()" v-if="auth">小组管理</el-button>
        </el-row>
      </div>
      <el-table :data="crewData" highlight-current-row height="510" fixed="left" tooltip-effect="dark" @selection-change="handleSelectionChange" >
        <!-- <el-table-column type="selection" width="55" ></el-table-column> -->
        <el-table-column label="id" prop="id" width="80" v-if="false"/>
        <el-table-column label="系统编号" prop="jobId" width="120" />
        <el-table-column label="姓名" prop="userName" width="80" />
        <el-table-column label="虚拟机号" prop="vmAccount" width="120" ></el-table-column>
        <el-table-column label="供应商" prop="outsourceName" width="150" show-overflow-tooltip/>
        <el-table-column label="岗位" prop="postName" width="80" />
        <el-table-column label="类型" prop="outsourceTypeName" width="100" />
        <el-table-column label="状态" prop="stateName" width="80" />
        <el-table-column label="入场时间" prop="entryTime" width="110" show-overflow-tooltip/>
        <el-table-column label="离场时间" prop="leavingTime" width="110" show-overflow-tooltip ></el-table-column>
        <el-table-column label="所属部门" prop="deptName" width="110" show-overflow-tooltip ></el-table-column>
        <el-table-column label="小组名称" prop="groupName" width="110" show-overflow-tooltip />
        <el-table-column label="小组长姓名" prop="groupLeaderName" width="90" show-overflow-tooltip ></el-table-column>
        <el-table-column label="小组长虚拟机号" prop="groupLeaderCard" width="120" show-overflow-tooltip ></el-table-column>
        <el-table-column label="是否星级人员" prop="ifStarPerson" width="110" show-overflow-tooltip >
          <template slot-scope="scope">
            <span v-if="scope.row.ifStarPerson == 'Y'">是</span>
            <span v-else-if="scope.row.ifStarPerson == 'N'">否</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="remark" show-overflow-tooltip />
        <el-table-column label="操作" width="120"  v-if="auth">
          <template slot-scope="scope">
            <el-link type="warning" :underline="false" @click="handleUpdate(scope.row)" >修改</el-link >
          </template>
        </el-table-column>
      </el-table>
      <div class="pager_zb">
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index"  v-model="$attrs" ref="pager" />
      </div>
    </div>

    <el-dialog title="修改信息" :visible.sync="dialogVisible" width="50%" :before-close="handleClose" :close-on-press-escape="false" 
      :close-on-click-modal="false">
      <div>
        <el-form :model="updFormData" ref="updFormData">
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="系统编号" prop="jobId">
                <el-input v-model="updFormData.jobId" placeholder="系统编号" disabled ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="姓名" prop="userName">
                <el-input v-model="updFormData.userName" placeholder="姓名" disabled ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <div>
                <p style="font-weight: bold">小组名称</p>
                <el-form-item prop="groupId">
                  <el-select v-model="updFormData.groupId" placeholder="请选择" filterable @change="changeGroup(updFormData.groupId)">
                    <el-option v-for="item in developGroupOptions" :key="item.id" :label="item.groupName" :value="item.id" />
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="11">
              <p style="font-weight: bold">小组长姓名</p>
              <el-form-item prop="groupLeaderName">
                <el-input v-model="updFormData.groupLeaderName" placeholder="小组长姓名" disabled></el-input>              
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <div>
                <p style="font-weight: bold">小组长虚拟机号</p>
                <el-form-item prop="groupLeaderCard">
                  <el-input v-model="updFormData.groupLeaderCard" placeholder="小组长虚拟机号" disabled></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="11">
              <div>
                <p style="font-weight: bold">是否星级人员</p>
                <el-form-item prop="groupType" >
                  <el-select v-model="updFormData.ifStarPerson" placeholder="请选择" >
                    <el-option v-for="item in starPersonOptions" :key="item.value" :label="item.label" :value="item.value" />
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="24">
              <el-form-item label="备注" prop="remark" >
                <el-input v-model="updFormData.remark" placeholder="备注"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmitExt()">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 弹框 -->
    <el-dialog title="小组管理" :visible.sync="dialogVisibleManager" width="75%" 
      :before-close="handleClose" 
      :close-on-press-escape="false" 
      :close-on-click-modal="false">
    <!-- 表单 -->
      <div>
        <el-form inline ref="groupInfoParams" :model="groupInfoParams">
          <el-form-item label="部门" prop="deptId" type="flex" justify="start">
            <el-select v-model="groupInfoParams.deptId" clearable placeholder="请选择">
              <el-option
                  v-for="item in depData"
                  :key="item.id"
                  :value="item.id"
                  :label="item.text"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="小组名称" prop="groupId">
            <!-- <el-input v-model="groupInfoParams.groupName" placeholder="模糊查询" ></el-input> -->
            <el-select v-model="groupInfoParams.id" clearable placeholder="请选择" filterable >
              <el-option v-for="item in developGroupOptions" :key="item.id" :label="item.groupName" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="小组性质" prop="groupType">
            <el-select v-model="groupInfoParams.groupType" clearable placeholder="请选择">
              <el-option
                  v-for="item in groupTypeOptions"
                  :key="item.value"
                  :value="item.value"
                  :label="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item >
              <el-button size="medium" @click="submitClearBtn">重置</el-button>
              <el-button size="medium" type="warning" @click="submitSearchBtn">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <div class="requireContainer_head">
        <div class="requireContainer_head_en"></div>
          <el-row type="flex" justify="end" class="margin_bottom">
            <el-button  @click="handleInfoExport">导出</el-button>
            <el-button type="warning"  @click="handleInfoAdd">新增</el-button>
          </el-row>
        </div>
      <!-- 树 -->
      <!-- <div class="dept-table-container"> -->
        <el-table :data="groupInfoList" fit border height="400" highlight-current-row ref="groupInfoTable" >
          <el-table-column label="部门" prop="deptName"></el-table-column>
          <el-table-column label="小组名称" prop="groupName"></el-table-column>
          <el-table-column label="小组长姓名" prop="groupLeaderName"></el-table-column>
          <el-table-column label="小组长虚拟机号" prop="groupLeaderCard"></el-table-column>
          <el-table-column label="小组性质" prop="groupType">
            <template slot-scope="scope">
            <span v-if="scope.row.groupType == '1'">研发</span>
            <span v-else-if="scope.row.groupType == '2'">测试</span>
          </template>
          </el-table-column>
          <el-table-column label="操作" width="120"  v-if="auth">
            <template slot-scope="scope">
              <el-link type="warning" :underline="false" @click="handleInfoUpdate(scope.row)" >修改</el-link >
              <el-link type="warning" :underline="false" @click="handleInfoDelete(scope.row)" >删除</el-link >
            </template>
          </el-table-column>
        </el-table>
        <pager :fns="getInfoPager" :total="groupInfoTotalNumber" :pageSize="groupInfoSize" :currentPage="groupInfoIndex" :v-model="$attrs"/>
      </div>
      <!-- 底部 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleManager = false">关 闭</el-button>
        <!-- <el-button type="primary" @click="determine">确 定</el-button> -->
      </span>
    </el-dialog>

    <el-dialog title="小组信息编辑" 
      :visible.sync="dialogVisibleHandle" 
      width="50%" 
      :before-close="handleCloseHandle" 
      :close-on-press-escape="false" 
      :close-on-click-modal="false">
      <div>
        <el-form :model="groupManager" ref="groupManager">
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <div>
                <p style="font-weight: bold">小组名称</p>
                <el-form-item prop="groupName">
                  <el-input 
                    v-model="groupManager.groupName" 
                    placeholder="小组名称" 
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="11">
              <div>
                <p style="font-weight: bold">部门</p>
                <el-form-item prop="deptId">
                  <el-select 
                    v-model="groupManager.deptId" 
                    placeholder="请选择部门"
                    @change="changeDept(groupManager.deptId)">
                    <el-option
                        v-for="item in depData"
                        :key="item.id"
                        :value="item.id"
                        :label="item.text"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <div>
                <p style="font-weight: bold">小组长姓名</p>
                <el-form-item prop="groupLeaderId">
                  <el-select v-model="groupManager.groupLeaderId" placeholder="请选择" filterable @change="changeGroupManager(groupManager.groupLeaderId)">
                    <el-option v-for="item in groupLeaderList" :key="item.id" :label="item.text" :value="item.id" />
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="11">
              <p style="font-weight: bold">小组长虚拟机号</p>
              <el-form-item prop="groupLeaderCard">
                <el-input v-model="groupManager.groupLeaderCard" placeholder="小组长虚拟机号" disabled></el-input>              
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <div>
                <p style="font-weight: bold">小组性质</p>
                <el-form-item prop="groupType">
                  <el-select v-model="groupManager.groupType" placeholder="请选择" filterable >
                    <el-option v-for="item in groupTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="11">
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCloseHandle">取 消</el-button>
        <el-button type="primary" @click="onSubmitInfo()">确 定</el-button>
      </span>
    </el-dialog>
    
  </div>
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
import { 
  queryPager, 
  queryDetail, 
  addLedger, 
  updateLedger, 
  queryLedgerList, 
  exportExcel,
  queryGroupInfoPager,
  addGroupInfo,
  updateGroupInfo,
  deleteGroupInfo,
  exportGroupInfo} from "@/api/accountManager/developGroupLedger";
import { queryRole } from "@/api/kqManager/calendar";
import common from "@/api/common";
import pager from "@/components/Pager";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept, queryEmployeeTree } from "@/api/integrated/wbryzrlc";

export default {
  name: "developGroupIndex",
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      totalNumber: 0,
      size: 10, // 初始值 默认：10
      index: 1, // 初始值 默认：1
      paramsForm: {
        userName: "",
        outsourceType: "",
        state: "",
        groupLeaderName: ""
      },
      updFormData: {
        id: "",
        jobId: "",
        userName: "",
        groupName: "",
        groupLeaderName: "",
        groupLeaderId: "",
        groupLeaderCard: "",
        groupType: "",
        remark: ""
      },
      groupInfoParams: {
        deptId: "",
        groupName: "",
        groupType: ""
      },
      groupInfoFormData: {
        id: "",
        deptId: "",
        groupName: "",
        groupType: ""
      },
      groupManager: {
        id: "",
        deptId: "",
        deptName: "",
        groupName: "",
        groupLeaderId: "",
        groupLeaderName: "",
        groupLeaderCard: "",
        groupType:""
      },
      crewData: [], // 列表分页数据
      crewDataCopy: [],
      drawer: false, // 默认不显示
      peo: "zh",
      buttonExport: "导 出", //新增
      buttonManager: "小组管理", //新增
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      deptno: "",
      groupInfoList: [],
      groupInfoTotalNumber: 0,
      groupInfoSize: 10, // 初始值 默认：10
      groupInfoIndex: 1, // 初始值 默认：1
      auth: false, // 是否有权限查看单价
      dialogVisible: false,
      dialogVisibleManager: false,
      dialogVisibleHandle: false,
      selectRows: [], //选中行
      developGroupOptions: [],
      groupLeaderList: [],
      depData: [],// 部门数据
      outsourceTypeOptions: [
        { value: "1", label: "人力外包", },
        { value: "0", label: "项目外包", },
      ],
      stateOptions: [
        { value: "0", label: "在岗",   },
        { value: "1", label: "离岗",   },
        { value: "2", label: "待入场", }
      ],
      groupTypeOptions: [
        { value: "1", label: "研发", },
        { value: "2", label: "测试", }
      ],
      starPersonOptions: [
        { value: "Y", label: "是", },
        { value: "N", label: "否", }
      ]
    }
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
      this.getGroupLedger();
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

    /** 小组信息 */
    getGroupLedger() {
      queryLedgerList().then((res) => {
        this.developGroupOptions = res.data.results;
      }).catch((error) => {
        this.$message.error(error);
      });
    },

    // 获取分页信息
    getGroupInfo(params) {
      let data = common.paramsFormat(params);
      queryGroupInfoPager(data).then((res) => {
        this.groupInfoList = res.data.results;
        this.groupInfoTotalNumber = res.data.total;
      }).catch((error) => {
        this.$message.error(error);
      });
    },

    // 导出
    handelExport() {
      var searchData = this.paramsForm;
      // console.log(searchData)
      let params = common.paramsFormat(searchData)
      const self = this;
      // console.log(params,'1212121')
      exportExcel(params).then(res => {
          // console.log(res)`
          common.download(res.data, self, '小组导出台账.xls')
      })
    },

    /** 小组信息 */
    changeGroup(id) {
      let options = this.developGroupOptions;
      let flag = false;
      options.forEach(opt => {
        if(opt.id === id){
          this.updFormData.groupName = opt.groupName;
          this.updFormData.groupLeaderId = opt.groupLeaderId;
          this.updFormData.groupLeaderName = opt.groupLeaderName;
          this.updFormData.groupLeaderCard = opt.groupLeaderCard;
          flag = true;
          return;
        }
      })

      if(!flag){
        this.updFormData.groupName = '';
        this.updFormData.groupLeaderId = '';
        this.updFormData.groupLeaderName = '';
        this.updFormData.groupLeaderCard = '';
      }
      console.log("11515111511",id);
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
    /** */
    handleSelectionChange(val, index) {
      this.selectRows = val; 
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

    /** 分页触发 */
    getInfoPager: function (size, index) {
      console.log("getInfoPager:size->",size ,";index->",index)
      let groupInfoParams = this.groupInfoParams;
      this.groupInfoSize = size;
      this.groupInfoIndex = index;
      groupInfoParams.size = size;
      groupInfoParams.index = index;
      this.getGroupInfo(groupInfoParams);
    },

    /** 小组管理 显示*/
    handleManager() {
      this.groupInfoParams = {}
      let params = {};
      params.size = this.groupInfoSize;
      params.index = this.groupInfoIndex;
      this.getGroupInfo(params);
      //需要将row数据转换成一个新对象，赋值
      this.dialogVisibleManager = true;
    },

    // 小组管理 重置
    submitClearBtn() {
      this.groupInfoParams = {};
    },

    // 小组管理 搜索
    submitSearchBtn() {
      let params = this.groupInfoParams;
      params.size = this.groupInfoSize;
      params.index = 1;
      this.groupInfoIndex = params.index;
      this.getGroupInfo(params);
    },

    /** 修改 */
    handleUpdate(row) {
      this.updFormData = JSON.parse(JSON.stringify(row));
      this.getGroupLedger();
      this.dialogVisible = true;
    },

    /** 小组信息 修改 */
    handleInfoAdd() {
      this.dialogVisibleHandle = true;
      this.groupManager = {};
    },

    /**
     * 小组信息 导出
     */
    handleInfoExport() {
      var searchData = this.groupInfoParams;
      let params = common.paramsFormat(searchData)
      const self = this;
      exportGroupInfo(params).then(res => {
        common.download(res.data, self, '小组信息导出.xls')
      })
    },
    /** 小组管理 修改 */
    handleInfoUpdate(row) {
      row.deptId = "dep"+row.deptId.replace("dep","");
      this.groupManager = JSON.parse(JSON.stringify(row));
      this.dialogVisibleHandle = true;
      this.getEmployees(row.deptId)
    },

    /** 小组管理 删除 */
    handleInfoDelete(row) {
      let flag = false;
      this.$confirm('确认删除吗?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).then(() => {
        let params = {};
        params.id = row.id;
        deleteGroupInfo(params).then(res => {
          if (res.data.status == 200) {
            this.$message.success("删除成功")
            flag = true;
          } else {
            this.$message.error(res.data.message);
          }
          if(flag){
            this.getGroupLedger();
            this.submitSearchBtn();
          }
        });
        
      }).catch(() => {

      })
      
    },
    onSubmitReturn(row){
      this.dialogVisible = false;
    },
    /** 提交 */
    onSubmitExt(){
      // const loading = common.loading(this);
      let formDateVal = this.updFormData;

      if (!formDateVal.id) {
        console.log("id",false);
        this.saveLedger(formDateVal);
      } else {
        this.updLedger(formDateVal);
        console.log("id",true);
      }
    },

    /** 提交 */
    onSubmitInfo(){
      // const loading = common.loading(this);
      let groupManagerData = this.groupManager;
      groupManagerData.deptId = groupManagerData.deptId.replace("dep","");
      if (!groupManagerData.id) {
        console.log("id",false);
        this.saveGroupInfo(groupManagerData);
      } else {
        this.updGroupInfo(groupManagerData);
        console.log("id",true);
      }
      this.getGroupLedger();
      this.submitSearchBtn();
    },

    /** 修改小组信息 */
    updGroupInfo(val) {
      const loading = common.loading(this);
      updateGroupInfo(val).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.dialogVisibleHandle = false;
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },

    /** 保存小组信息 */
    saveGroupInfo(val) {
      const loading = common.loading(this);
      addGroupInfo(val).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.dialogVisibleHandle = false;
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
          this.dialogVisible = false;
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
          this.dialogVisible = false;
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },

    /** 修改部门 */
    changeDept(deptId) {
      console.log("deptId",deptId);
      let options = this.depData;
      let optionData = {};
      options.forEach(obj =>{
        if (obj.id === deptId) {
          console.log("*************22",obj);
          optionData = obj;
          return;
        }
      })
      this.groupManager.groupLeaderId = '';
      this.groupManager.groupLeaderName = '';
      this.groupManager.groupLeaderCard = '';
      this.groupManager.deptName = optionData.text;
      this.groupManager.deptId = deptId;
      console.log(this.groupManager,"manager")
      this.getEmployees(deptId);
      // this.groupManager.$forceUpdate();
    },
    /**  改变小组长信息 */
    changeGroupManager(badge){
      console.log("badge",badge);
      let options = this.groupLeaderList;
      let optionData = {};
      options.forEach(obj =>{
        if (obj.id === badge) {
          console.log("*************11",obj);
          optionData = obj;
          return;
        }
      })
      this.groupManager.groupLeaderName = optionData.text.replace("("+badge+")","");
      this.groupManager.groupLeaderCard = badge;
      this.$forceUpdate();
    },
    // 查询部门人员
    getEmployees(deptId) {
      if(deptId){
        deptId = deptId.replace("dep","");
      } 

      queryEmployeeTree(deptId).then(res => {
        if (res.data.status == 200) {
          console.log("111111111111111")
            var fild = res.data.results[0].children;  
            this.groupLeaderList = fild.filter(li => { 
              li.id = li.id.replace("emp", "");
              return li;
            });
        } else {
            this.$message.error("获取部门数据失败");
        } 
      }).catch(error => {
          this.$message.error("当前部门下面无数据!");
      });
    },

    // 关闭弹框
    handleClose() {
      this.dialogVisible = false;
      this.dialogVisibleManager = false;
    },

    //关闭小组管理弹窗
    handleCloseHandle() {
      this.dialogVisibleHandle = false;
      this.groupManager = {};
      this.groupLeaderList = [];
    },

     /** 获取权限 */
    getPermission() {
      queryRole().then((res) => {
        if (res.data.status == 200) {
          let roles = res.data.results;
          console.log(res.data.results, "99999权限");
          let auth = false;
          roles.forEach(function (item) {
            if (item.roleId == "00000001" || item.roleId == "PERMANAGE" || item.roleId == "YF_MANAGER") {
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

.requireContainer_head_en {
  width: 160px;
}

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
</style>

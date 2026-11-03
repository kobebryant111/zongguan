<!-- 人员移交 申请 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">人员移交申请</p>
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
                    <el-form-item prop="applyerDeptName">
                      <el-input v-model="formData.applyerDeptName" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                  <div>
                    <p>申请人 *</p>
                    <el-form-item>
                      <el-input v-model="formData.applyerName" disabled></el-input>
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
                  <p class="row_2_detail">移交人员信息<span class="add_row" @click="eduExperAdd(1)">新增一行</span></p>
                  <el-table :data="formData.transferInfoDtos"  style="width: 100%" >
                        <el-table-column label="序号" align="left" type="index"></el-table-column>
                        <el-table-column label="姓名 *" width="120" align="left">
                            <template slot-scope="scope">
                                <el-form-item :prop="`transferInfoDtos.${scope.$index}.userName`">
                                    <el-select v-model="scope.row.userName" @change="handleChange(scope.row,`${scope.$index}`)" filterable>
                                      <el-option
                                        v-for="item in applyData"
                                        :key="item.id"
                                        :value="item.userName"
                                        :label="item.userName"
                                      ></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="所属部门" width="160" align="left">
                            <template slot-scope="scope">
                                <el-form-item :prop="`transferInfoDtos.${scope.$index}.depname`">
                                    <el-input v-model="scope.row.depname" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="外包方式" width="120" align="left">
                            <template slot-scope="scope">
                                <el-form-item :prop="`transferInfoDtos.${scope.$index}.outsourceType`" :rules="[{ required: true, message: '请选择'}]">
                                    <!-- <el-select v-model="scope.row.outsourceType" disabled>
                                      <el-option
                                        v-for="item in outsourceType_options"
                                        :key="item.id"
                                        :value="item.value"
                                        :label="item.label"
                                      ></el-option>
                                    </el-select> -->
                                    <el-input v-model="scope.row.outsourceTypename" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="移交前项目经理" width="120" align="left">
                          <template slot-scope="scope">
                            <el-form-item :prop="`transferInfoDtos.${scope.$index}.personInChargename`">
                              <el-input v-model="scope.row.personInChargename" disabled></el-input>
                              <!-- <el-input v-model="scope.row.personInChargename" placeholder="请选择"></el-input> -->
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="供应商名称" width="220" align="left">
                          <template slot-scope="scope">
                            <el-form-item :prop="`transferInfoDtos.${scope.$index}.outsourcname`">
                              <el-tooltip :content="scope.row.outsourcname" placement="top" effect="dark" :disabled="scope.row.outsourcname == null || scope.row.outsourcname == ''" >
                                <el-input v-model="scope.row.outsourcname" disabled></el-input>
                              </el-tooltip>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="移交时间 *" width="160" align="left">
                          <template slot-scope="scope">
                            <el-form-item :prop="`transferInfoDtos.${scope.$index}.transferTime`"  :rules="[{ required: true, message: '请选择'}]">
                              <el-date-picker
                                v-model="scope.row.transferTime"
                                type="date"
                                placeholder="选择日期"
                              >
                              </el-date-picker>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="移交状态" width="160" align="left">
                            <template slot-scope="scope">
                                <el-form-item :prop="`transferInfoDtos.${scope.$index}.transferState`">
                                    <!-- <el-input v-model="scope.row.transferState" disabled></el-input> -->
                                    <el-select v-model="scope.row.transferState" disabled>
                                      <el-option
                                        v-for="(item, index) in yjStateDic_options"
                                        :key="index"
                                        :label="item.dictname"
                                        :value="item.dictid"
                                      ></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="系统名称" width="200" align="left">
                            <template slot-scope="scope">
                                <el-form-item>
                                    <!-- <el-input v-model="scope.row.systemId"></el-input> -->
                                    <el-tooltip :content="getTooltipValue(scope.row.systemId,1,scope.$index)" :disabled="scope.row.systemId == null || scope.row.systemId == ''" placement="top" effect="dark">
                                      <el-select @change="handleSystem(scope.row, `${scope.$index}`)" v-model="scope.row.systemId" :disabled="isFlagList[`${scope.$index}`] == 0? true: false">
                                        <el-option
                                          v-for="item in systemList_options"
                                          :key="item.id"
                                          :label="item.name"
                                          :value="item.id"
                                        ></el-option>
                                      </el-select>
                                    </el-tooltip>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="项目名称" width="200" align="left">
                            <template slot-scope="scope">
                                <el-form-item>
                                    <!-- <el-input v-model="scope.row.projectId"></el-input> -->
                                    <el-tooltip :content="getTooltipValue(scope.row.projectId,2,scope.$index)" :disabled="scope.row.projectId == null || scope.row.projectId == ''" placement="top" effect="dark">
                                      <el-select filterable @change="handleProject(scope.row, `${scope.$index}`)" v-model="scope.row.projectId" :disabled="isFlagList[`${scope.$index}`] == 1? true: false">
                                        <el-option
                                          v-for="item in projectList_options"
                                          :key="item.id"
                                          :label="item.name"
                                          :value="item.id"
                                        ></el-option>
                                      </el-select>
                                    </el-tooltip>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="移交后项目经理 *" width="150" align="left">
                            <template slot-scope="scope">
                                <el-form-item :prop="`transferInfoDtos.${scope.$index}.pmIdAfterName`">
                                  <!-- <el-input v-model="scope.row.pmIdAfterName" disabled></el-input> -->
                                  <el-select v-model="scope.row.pmIdAfter"  @change="changeDeptAfter(scope.row, `${scope.$index}`,scope.row.pmIdAfter)">
                                    <el-option 
                                      v-for="(item, index) in pmIdAfter_options[`${scope.$index}`]"
                                      :key="index"
                                      :label="item.empName"
                                      :value="item.badge"
                                    ></el-option>
                                  </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="移交后所属部门 *" width="150" align="left">
                            <template slot-scope="scope">
                                <el-form-item :prop="`transferInfoDtos.${scope.$index}.deptIdAfterName`">
                                  <el-input v-model="scope.row.deptIdAfterName" disabled></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80" align="left" class="margin0">
                            <template slot-scope="scope">
                                <div class="operation_add">
                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.transferInfoDtos, scope.row)">
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
          <flow-logs :processInstId="flowItem.processInstanceId" ref="log"></flow-logs>
      </div>
    </div>

    <div class="blank"></div>

    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <button-delete :buttonName="buttonDel" :size="size" :fns="delBox"></button-delete>
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
                <button-orange :buttonName="buttonName" :size="size" :fns="onSubmit"></button-orange>
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>

    <!-- <div class="nav-bottom" v-if="flowItem.status == 'yb'">
      <yb-revoke :flowItem="flowItem"></yb-revoke>
    </div> -->
  </div>
</template>
<script>
import common from "@/api/common";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
import buttonDelete from "@/components/eleButton/delete"; //引入删除按钮
import { queryUserDept } from "@/api/tqrcApplyfor";
import { initStaff, initSystemInfo, initProjectInfo, deleteFlows,dhApproval, queryDetail, createFlow, interView, initFlow } from "@/api/personTransfer/index";
// import { isDate } from 'moment';
import ybRevoke from "@/views/plugins/camunda/ybRevoke";
import { getDicEntry } from "@/api/eosDictEntry";
import flowLogs from "@/views/plugins/camunda/FlowLogs";

  export default {
    name: 'Add',
    components: { buttonOrange, buttonCommon, flowLogs, ybRevoke, buttonDelete },
    data() {
        return {
          formData:{
            // applyerDeptId: '', // 部门编号
            // applyerDeptName: "", // 部门名称
            // applyerId: '', // 申请人编号
            // applyerName: '', // 申请人名称
            // transferInfoDtos: [ // 外包人员集合信息
            //     {
            //       userName: '', // 姓名
            //       depname: '', // 所属部门
            //       outsourcname: '', // 供应商名称
            //       transferTime: '', // 移交时间
            //       transferState: '', // 移交状态
            //       personInChargename: '', // 移交前项目经理 
            //       systemId: '', // 移交后系统编号 
            //       pmIdAfter: '', // 移交后项目经理
            //       deptIdAfterName: '', // 移交后所属部门
            //       deptIdAfter: '', // 移交后所属部门id
            //     }
            // ]
          },
          buttonName: "提交",
          buttonDel: "删除",
          buttonSave: '草稿',
          size: "mini",
          errorMessage: '', //失败消息提示,
          objectcode: "6000000300",
          objectgrade: "2",
          systemList_options: [], // 系统名称列表
          projectList_options: [], // 项目名称列表
          deptIdAfter_options: [], // 移交后部门
          pmIdAfter_options: [], // 移交后项目经理列表
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
          outsourceType_options: [
            {
              id: '0',
              value: "0",
              label: "项目外包",
            },
            {
              id: '1',
              value: "1",
              label: "人力外包",
            },
          ],
          applyData: [], // 面试者信息
          currentRow: {}, //当前行
          flowItem: {},
          instanceid: "",
          yjStateDic_options: [], // 移交状态字典
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
      //气泡
      getTooltipValue(id,type,index){
        if (id == null) {
          return
        }
        //1:系统名称
        if (type === 1) {
          for (const dict of this.systemList_options){
            if (dict.id === id){
              return dict.name
            }
          }
        }
        //2:项目名称
        if (type === 2) {
          for (const dict of this.projectList_options){
            if (dict.id === id){
              return dict.name
            }
          }
        }
      },
      init(params) {
        // console.log('人员移交', params);
        this.flowItem = params;
        this.instanceid = params.processInstanceId;
        this.$nextTick(_ => {
            this.getapplyerData();
            this.getyjState();
            this.getinitSystemInfo();
            this.getinitProjectInfo()
            // this.getinitStaff();
            this.$refs.log.init(this.instanceid);
        })
      },
      // 查询详情
      toqueryDetail(id) {
          let params = {}
          params.instanceid = id;
          let data = common.paramsFormat(params)
          queryDetail(data).then(res => {
              console.log('查询详情', res);
              if(res.data.status === 200) {
                this.formData = res.data.results;
                let arr = JSON.parse(JSON.stringify(res.data.results.transferInfoDtos))
                arr.forEach((item, index) => {
                  // 添加属性
                  Object.assign(item, { depname: item.deptIdBeforeName })
                  Object.assign(item, { personInChargename: item.pmIdBeforeName })
                  Object.assign(item, { outsourcname: item.supplierName })
                  // this.handleSystem(item, index)
                  // console.log('数据循环1', item.pmIdAfter, item.systemId);
                  // console.log(this.systemList_options)
                  this.handleSystem(item, index);
                  this.handleProject(item, index);
                  if(item.outsourceType == 0){
                    this.isFlagList[index] = 0
                  } else if(item.outsourceType == 1) {
                    this.isFlagList[index] = 1
                  }
                });
                this.formData.transferInfoDtos = arr;
                // console.log('this.formData', this.formData)
              } else {
                  this.$message.error(res.data.message);
              }
          })
      },
      // 查询可选人员
      getinitStaff(val) {
        let personInCharge = val;
        initStaff(personInCharge).then(res => {
        //   console.log('查询可选人员', res)
          if(res.data.status === 200) {
            this.applyData = res.data.results;
          }
        })
      },
      // 查询可选系统名称
      getinitSystemInfo() {
        initSystemInfo().then(res => {
          // console.log('查询可选系统名称', res);
          if(res.data.status === 200) {
            this.systemList_options = res.data.results;
            this.toqueryDetail(this.instanceid);

          } else {
            this.$message.error("获取系统名称失败，请稍后重试");
          }
        })
      },
      // 查询可选项目名称
      getinitProjectInfo() {
        initProjectInfo().then(res => {
          console.log('查询可选项目名称', res)
          if(res.data.status === 200) {
            this.projectList_options = res.data.results;
          } else {
            this.$message.error("获取系统名称失败，请稍后重试");
          }
        })
      },
      // 获取申请人部门 
      getapplyerData() {
        queryUserDept().then(res => {
        //   console.log(res, '获取申请人部门')
          const { message,results, status } = res.data;
          if(status === 200) {
            this.formData.applyer = results.userName;
            this.formData.deptName = results.deptName;
            this.formData.applyerDeptId = results.deptNo;
            this.getinitStaff(results.userId);
            this.getinitSystemInfo();
            this.getinitProjectInfo()
          } else {
            this.$message.error('获取数据失败')
          }
        })
      },
      // 查询移交状态字典
      getyjState() {
        getDicEntry({dicttypeid: "STAFF_TRANSFER_STATE"}).then(res => {
          // console.log('查询移交状态字典', res);
          if(res.data.status === 200) {
            this.yjStateDic_options = res.data.results;
          } else {
            this.$message.error(res.data.message);
          }
        })
      },
      // 改变面试者操作
      handleChange(row, index) {
        // console.log(row, index, '被上天选择的人')
        // const loading = common.loading(this);
        let arr = JSON.parse(JSON.stringify(this.applyData));
        // 数组循环对比
        arr.forEach(item => {
          if(item.userName === row.userName) {
            // console.log('改变面试者操作', item);
            // let val = item.systemId || '';
            // this.handleSystem(item, index);
            // 外包方式为人力时禁用项目名称,为项目时禁用系统名称
            if(item.outsourceType == 0){
              this.isFlagList[index] = 0
            } else if(item.outsourceType == 1) {
              this.isFlagList[index] = 1
            }
            // 强制刷新
            this.$set(this.formData.transferInfoDtos,index,item);
          }
          // loading.close();
        })
      },
      // 新增一行
      eduExperAdd(n) {
        // console.log(n)
        var list;
        list = {
                  userName: '', // 姓名
                  depname: '', // 所属部门
                  outsourcname: '', // 供应商名称
                  transferTime: '', // 移交时间
                  transferState: '', // 移交状态
                  personInChargename: '', // 移交前项目经理 
                  systemId: '', // 移交后系统编号 
                  pmIdAfter: '', // 移交后项目经理
                  deptIdAfterName: '', // 移交后所属部门
                  deptIdAfter: '', // 移交后所属部门id
                }
        this.formData.transferInfoDtos.push(list)
      },
      // 删除行
      deleteRow(index, rows, row) {
        // console.log(index, rows, row)
        if(rows.length !== 1 && row.id == '') {
            rows.splice(index, 1);
        } else if(rows.length != 1 && row.id != '') {
            // this.delTableData(index, rows, row);
            rows.splice(index, 1);
            this.pmIdAfter_options.splice(index,1);
        } else {
            this.errorMessage = '不能少于一行'
            this.saveError();
        }
      },
      // 删除表中数据
      delTableData(index, rows, row) {
        let params = {};
        params.id = row.id;
        let data = common.paramsFormat(params);
      },
      // 选择系统操作
      handleSystem(row, index) {
        // console.log("选择系统操作", row, index);
        // 深拷贝
        let arr = JSON.parse(JSON.stringify(this.systemList_options));
        // 遍历
        arr.forEach(item => {
          // 如果系统id等于某一个对象的id
          if(row.systemId === item.id) {
            // console.log('选中的item', item);
            // 强制刷新移交后部门
            this.$set(this.formData.transferInfoDtos[index], 'deptIdAfterName', item.depName);
            // 给移交后部门id赋值
            this.formData.transferInfoDtos[index].deptIdAfter = item.depId;
            this.$set(this.formData.transferInfoDtos[index], 'pmIdAfter', null);
            // console.log('pmIdAfter', this.formData.transferInfoDtos[index].pmIdAfter)
            // 给每一项的移交后项目经理的选项optian赋值
            this.pmIdAfter_options[index] = item.userInfoList;
          }
        })
      },
      // 选择项目操作
      handleProject(row, index) {
        console.log("选择项目操作", row, index);
        // 深拷贝
        let arr = JSON.parse(JSON.stringify(this.projectList_options));
        // 遍历
        arr.forEach(item => {
          // 如果系统id等于某一个对象的id
          if(row.projectId === item.id) {
            console.log('选中的item', item);
            if(this.formData.transferInfoDtos[index].outsourceType == "1"){
              // 强制刷新移交后部门
              this.$set(this.formData.transferInfoDtos[index], 'deptIdAfterName', item.depName);
              // 给移交后部门id赋值
              this.formData.transferInfoDtos[index].deptIdAfter = item.depId;
            }else{
              this.$set(this.formData.transferInfoDtos[index], 'deptIdAfterName', null);
              this.formData.transferInfoDtos[index].deptIdAfter = '';
            }
            // this.formData.transferInfoDtos[index].pmIdAfter = '';
            this.$set(this.formData.transferInfoDtos[index], 'pmIdAfter', null);
            // 给每一项的移交后项目经理的选项optian赋值
            this.pmIdAfter_options[index] = item.userInfoList;
          }
        })
      },
      changeDeptAfter(row, index,val) {
        console.log("val",val);
        // 深拷贝
        console.log("选择移交后项目经理操作111",this.formData.transferInfoDtos[index].outsourceType);
        if(this.formData.transferInfoDtos[index].outsourceType == "1"){
          return;
        }
        // 遍历
        let options = this.pmIdAfter_options[index];
        console.log("选择移交后项目经理操作222",options);
        let i = 0;
        options.forEach(item => {
          console.log("选择移交后项目经理操作333",item);
          // 如果系统id等于某一个对象的id
          console.log("pmIdAfter-item.badge",val , item.badge);
          if(val == item.badge) {
            // 强制刷新移交后部门
            this.$set(this.formData.transferInfoDtos[index], 'deptIdAfterName', item.deptName);
            // 给移交后部门id赋值
            this.formData.transferInfoDtos[index].deptIdAfter = item.deptId;
          }
          i++;
        })
      },
      // 删除流程
      delBox() {
        this.$confirm('确认删除该流程吗?', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {
              // 删除流程
            // delPro() {
            let params = {};
            // params.id = this.flowItem.workItemID; 
            params.instanceid = this.instanceid;
            // let data = common.paramsFormat(params);
            deleteFlows(params).then(res => {
                if (res.data.status == 200) {
                    // this.successMessage = '流程删除成功'
                    // this.saveSuccess();
                    this.$message.success("流程删除成功")
                    this.$router.push({ name: 'dashboard' });
                } else {
                  this.$message.error(res.data.message);
                }

            });
        // },
          }).catch(() => {

          })
      },
      // 保存
      saveInterview() {
       const loading = common.loading(this);
        // console.log('saveInterview',this.formData)
      },
      // 提交
      onSubmit() {
        // const loading = common.loading(this);
        // console.log('onSubmit',this.formData);
        this.$refs['formData'].validate(valid => {
          if(valid) {
            this.formData.transferInfoDtos.forEach(item => {
              item.supplierId = item.outsourceNo
              item.personInCharge = item.personInCharge
              item.systemId = item.systemId
            })
            this.formData.bps = this.flowItem;
            // console.log('this.formData', this.formData)
            initFlow(this.formData).then(res => {
              if(res.data.status === 200) {
                let message = common.getFlowMessgeNew(res.data.message);
                let messageArr = message.split(",");
                this.$nextTick(() => {
                  this.messageNotify(messageArr);
                  this.$router.push({name: "wbrzManage"})
                })
              } else {
                return this.$message.error(res.data.message);
              }
            })
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
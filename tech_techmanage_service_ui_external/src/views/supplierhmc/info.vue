<!-- 供应商花名册 编辑页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">供应商花名册</p>
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
                    <p>外包供应商名称 *</p>
                    <el-form-item prop="outsourcname">
                      <el-input v-model="formData.outsourcname" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                
              </el-row>
            </div>
          </div>

          <!-- 表格 - 详细信息 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">
                    详细信息
                  </p>
                  <el-table :data="formData.supplierRosterInfoDtos" style="width: 100%" @row-click="rowClick" >
                    <el-table-column label="用工部门" width="200px">
                      <template slot-scope="scope">
                        <el-form-item>
                          <!-- <el-input v-model="scope.row.deptId"></el-input> -->
                          <el-select placeholder="请选择" filterable v-model="scope.row.deptId" @change="handleDdptChange"  disabled>
                            <el-option 
                              v-for="item in depData" 
                              :key="item.id" 
                              :label="item.text" 
                              :value="item.id"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    
                    <el-table-column label="服务方式(是否驻场)" width="200px">
                      <template slot-scope="scope">
                        <el-form-item>
                          <!-- <el-input v-model="scope.row.serviceMode"></el-input> -->
                          <el-select v-model="scope.row.serviceMode" disabled>
                              <el-option
                                v-for="item in serviceMode_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.value"
                              ></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="是否分包或转包" width="200px">
                      <template slot-scope="scope">
                        <el-form-item>
                            <el-select v-model="scope.row.isSubcontract" disabled>
                              <el-option
                                v-for="item in sever_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.value"
                              ></el-option>
                      </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="服务系统名称" width="200px">
                      <template slot-scope="scope">
                        <el-form-item>
                            <el-select v-model="scope.row.systemId" disabled>
                                <el-option
                                    v-for="item in rlSystemId_options"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="服务项目类型" width="250px" prop="serviceTypeArray">

                      <template slot-scope="scope">
                      <div class="elselect">
                        <el-form-item>
                          <el-select v-model="scope.row.serviceTypeArray" multiple disabled>
                              <el-option
                                v-for="item in service_options"
                                :key="item.dictid"
                                :label="item.dictname"
                                :value="item.dictid"
                              ></el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="服务起始时间" width="200px">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.startTime" disabled></el-input>
                          <!-- <el-date-picker
                              v-model="scope.row.startTime"
                              type="date"
                              placeholder="选择日期"
                              value-format="yyyy-MM-dd"
                            ></el-date-picker>   -->
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="服务结束时间" width="200px">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.endTime" disabled></el-input>
                          <!-- <el-date-picker
                              v-model="scope.row.endTime"
                              type="date"
                              placeholder="选择日期"
                              value-format="yyyy-MM-dd"
                            ></el-date-picker>  -->
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="尽职调查报告" prop="fileNo" width="120" fixed="right">
                      <template slot-scope="scope">
                        <el-upload
                          class="upload-demo"
                          action="Fake Action"
                          accept=".xlsx,.xls"
                          :file-list="fileList"
                          :show-file-list="false"
                          :http-request="uploadFile"
                          :on-change="changeFile"
                          v-if="
                            scope.row.fileNo == '' ||
                            scope.row.fileNo == undefined
                              ? true
                              : false
                          "
                          disabled
                        >
                          <el-button size="small" type="primary">
                            <i class="el-icon-upload2"></i>上传文件
                          </el-button>
                        </el-upload>
                        <div class="upload_scss" v-else>
                          <div @click="openNtkoWindow(fileList)">预览</div>
                          <div @click="jlDownliad(scope.row.fileNo)">下载</div>
                          <!-- <el-upload
                            class="upload-demo againUpLoad"
                            action="Fake Action"
                            accept=".xlsx,.xls"
                            :file-list="fileList"
                            :show-file-list="false"
                            :http-request="uploadFile"
                            :on-change="changeFile"
                          >
                            重传
                          </el-upload> -->
                        </div>
                      </template>
                    </el-table-column>
                    <!-- <el-table-column label="操作" fixed="right">
                      <template slot-scope="scope">
                        <div class="operation_add">
                          <el-button
                            @click.native.prevent="
                              deleteRow(
                                scope.$index,
                                formData.supplierRosterInfoDtos,
                                scope.row
                              )
                            "
                          >
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

          <!-- 表格 - 价格信息 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">
                    价格信息
                  </p>
                  <el-table
                    :data="formData.outsourcingInfoPriceDtos"
                    style="width: 100%"
                    @row-click="rowClick"
                  >
                    <el-table-column label="岗位" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <!-- <el-input v-model="scope.row.position"></el-input> -->
                          <el-select v-model="scope.row.position" disabled>
                              <el-option
                                v-for="item in position_options"
                                :key="item.dictid"
                                :label="item.dictname"
                                :value="item.dictid"
                              ></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="一档(1-3年) 低" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.firststartprie" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="一档(1-3年) 高" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.firstendprie" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="二档(3-5年) 低" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.secondstartprie" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="二档(3-5年) 高" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.secondendprie" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="三档(5-7年) 低" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.thirdstartprie" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="三档(5-7年) 高" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.thirdendprie" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="四档(7年以上) 低" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.fourthstartprie" disabled></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="四档(7年以上) 高" width="200">
                      <template slot-scope="scope">
                        <el-form-item>
                          <el-input v-model="scope.row.fourthendprie" disabled></el-input>
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
    <!-- 预览图片弹窗 -->
    <el-dialog title="" :visible.sync="ylDialog">
        <div class="ylDialogCon">
            <el-image :src="ylPhotoUrl" style="width:600px;height:400px" :fit="photoFit" :preview-src-list="ylPhotoUrl.split(',')"/>
        </div>
    </el-dialog>
  </div>
</template>
<script>
import common from "@/api/common";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { queryListSyste, queryListProject, updateRoster, queryRoster } from "@/api/supplierhmc/index";
import { uploadExcel } from "@/api/supplierManager/fileManage";
import { queryUserDept } from "@/api/tqrcApplyfor";
import { getDicEntry } from "@/api/eosDictEntry";
import helpHint from "@/components/helpHint";
import Moment from "moment";
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
import { preview } from '@/api/common/flowApi'


export default {
  name: "applyfor",
  components: { buttonOrange, buttonCommon, helpHint },
  data() {
    return {
      // 表单
      formData: {
        outsourcname: "", // 供应商名称
        isFeat: "",  // 是否合作
        outsourcingInfoPriceDtos: [
          {
            position: "", // 岗位
            firststartprie: '', // 一档 低
            firstendprie: '', // 一档 高
            secondstartprie: '', // 二档 低
            secondendprie: '', // 二档 高
            thirdstartprie: '', // 三档 低
            thirdendprie: '', // 三档 高
            fourthstartprie: '', // 四档 低
            fourthendprie: '', // 四档 高
          }
        ],
        supplierRosterInfoDtos: [
          {
            deptId: "", // 用工部门
            supplierType: "", // 供应商类型
            serviceMode: "", // 服务方式
            isSubcontract: "", // 是否分包或转包
            systemId: "", // 服务系统名称(人力)
            projectId: "", // 服务项目名称(人力)
            serviceTypeArray: [], // 服务项目名称类型
            startTime: "", // 服务起始时间
            endTime: "", // 服务结束时间
            supplierYear: "", // 考核年份
            accessResult: "", // 考核结果
            fileName: "", // 文件名称
            fileNo: "", // 尽职调查报告
          },
        ],
      },
      restaurants: [], // 查询数据
      buttonName: "修改",
      // buttonSave: "草稿",
      buttonClose: "关 闭", //关闭按钮名字
      size: "mini",
      errorMessage: "", //失败消息提示,
      objectcode: "6000000300",
      objectgrade: "2",
      depData: [],
      deptmentData: {},
      service_options: [], // 服务类型
      position_options: [], // 岗位
      fileList: [], //上传文件列表
      outsourceno_options: [], //外包商
      supplier_options: [
        {
          id: '0',
          value: "0",
          label: "项目",
        },
        {
          id: '1',
          value: "1",
          label: "人力",
        },
      ],
      year_options: [],
      sever_options: [
        {
          id: "0",
          value: "N",
          label: "否",
        },
        {
          id: "1",
          value: "Y",
          label: "是",
        },
      ],
      assessment_level: [
        {
          value: "0",
          label: "A",
        },
        {
          value: "1",
          label: "B+",
        },
        {
          value: "2",
          label: "B",
        },
        {
          value: "3",
          label: "C",
        },
      ],
      serviceMode_options: [
          {
            id: "0",
            label: "非驻场",
            value: "N"
          },
          {
            id: "1",
            label: "驻场",
            value: "Y"
          },
      ],
      serviceType_options: [
          {
            id: "0",
            label: "需求",
            value: "0"
          },
          {
            id: "1",
            label: "开发",
            value: "1"
          },
          {
            id: "2",
            label: "测试",
            value: "2"
          },
      ],
      applyData: [],
      rlSystemId_options: [], //服务系统名称(人力)配置项
      xmProjectId_options: [], //服务系统名称(项目)配置项
      remark_options: [
          {
            id: "0",
            label: "否",
            value: "0"
          },
          {
            id: "1",
            label: "是",
            value: "1"
          },
      ], 
      isRl: null,
      isXm: null,
      isFlag: null,
      ylDialog: false, //是否显示预览图片弹窗
      ylPhotoUrl: "", //预览图片地址
      photoFit:"scale-down",
    };
  },
  watch: {},
  created() {
    console.log('详情', this.$route.params);
    
    if(Object.keys(this.$route.params).length) {
      this.getDetails(this.$route.params.id)
      window.localStorage.setItem('initData', JSON.stringify(this.$route.params));
    } else {
      let initData = JSON.parse(window.localStorage.getItem('initData'));
      this.getDetails(initData.id)
    }
    this.filDep();
  },
  mounted() {
    this.getService();
    this.getPosition();
  },

  methods: {
    // 获取详情
    getDetails(id) {
      const loading = common.loading(this);
      queryRoster({id: id}).then(res => {
        console.log('获取详情', res);
        loading.close();
        if(res.data.status === 200) {
          let arr = res.data.results;
          arr.supplierRosterInfoDtos.forEach(item => {
            item.deptId = 'dep' + item.deptId;
            this.getqueryListSyste(item.deptId);
            this.getqueryListProject(item.deptId);
            if(item.fileNo) {
              this.getFileName(item.fileNo)
            }
          })
          this.formData = arr;
        } else {
          this.$message.error('获取数据失败，请稍后重试');
        }
      })
    },
    // 获取申请人部门
    getapplyerData() {
      queryUserDept().then(async (res) => {
        console.log(res, "获取申请人部门");
        const { message, results, status } = await res.data;
        if (status === 200) {
          this.formData.applier = results.userId;
          this.formData.applierName = results.userName;
          this.formData.deptName = results.deptName;
          this.formData.deptId = results.deptNo;
          this.$forceUpdate();
        } else {
          this.errorMessage = message;
          this.saveError();
        }
      });
    },
    // 改变用工部门事件
    handleDdptChange(value) {
        console.log('改变用工部门事件', value)
        this.getqueryListSyste(value);
        this.getqueryListProject(value);

    },
    // 查询系统名称
    getqueryListSyste(value) {
        let id = value.replace('dep', '');
        // console.log('id', id);
        // let data = {};
        // data.depId = id;
        // let params = JSON.stringify(data);
        queryListSyste(id).then(res => {
            console.log('查询系统名称', res);
            this.rlSystemId_options = res.data.results;
        })
    },
    // 查询服务名称
    getqueryListProject(value) {
        let id = value.replace('dep', '');
        // console.log('id', id);
        // let data = {};
        // data.depId = id;
        // let params = JSON.stringify(data);
        queryListProject(id).then(res => {
            console.log('查询服务名称', res);
            this.xmProjectId_options = res.data.results;
        })
    },
    // 供应商类型选择
    handleSupplierTypeSelect(value) {
      console.log('供应商类型选择', value);
      this.isFlag = value;
      // // 选择项目
      // if(value === '0') {
      //   this.isRl = true;
      //   this.isXm = false;
      //   // 选择人力
      // } else if(value === '1') {
      //   this.isRl = false;
      //   this.isXm = true;
      // }
    },
    // 获取服务类型
    getService() {
      getDicEntry({
        dicttypeid: "BUSI_OUTSOURCE_POSITION",
      }).then((res) => {
        console.log("岗位", res);
        if (res.data.status === 200) {
          this.service_options = res.data.results;
        }
      });
    },
    // 获取岗位
    getPosition() {
      getDicEntry({
        dicttypeid: "RLWBXQLX",
      }).then((res) => {
        console.log("岗位", res);
        if (res.data.status === 200) {
          this.position_options = res.data.results;
        }
      });
    },
    // 新增一行
    eduExperAdd(v) {
      console.log('v', v)
      var listOne;
      var listTwo;
      var listThree;
      if(v === 1) {
        listOne = {
          deptId: "", // 用工部门
            supplierType: "", // 供应商类型
            serviceMode: "", // 服务方式
            isSubcontract: "", // 是否分包或转包
            systemId: "", // 服务系统名称(人力)
            projectId: "", // 服务项目名称(人力)
            serviceTypeArray: [], // 服务项目名称类型
            startTime: "", // 服务起始时间
            endTime: "", // 服务结束时间
            supplierYear: "", // 考核年份
            accessResult: "", // 考核结果
            fileName: "", // 文件名称
            fileNo: "", // 尽职调查报告
        } 
        this.formData.supplierRosterInfoDtos.push(listOne);
      } else if(v === 2) {
        listTwo = {
            position: "", // 岗位
            firststartprie: '', // 一档 低
            firstendprie: '', // 一档 高
            secondstartprie: '', // 二档 低
            secondendprie: '', // 二档 高
            thirdstartprie: '', // 三档 低
            thirdendprie: '', // 三档 高
            fourthstartprie: '', // 四档 低
            fourthendprie: '', // 四档 高
          }
          this.formData.outsourcingInfoPriceDtos.push(listTwo)
      }
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
    // 人力服务系统名称选择
    handleRlChange(value) {
        console.log('人力服务系统名称选择', value)
    },
    // 项目服务系统名称选择
    handlexMChange(value) {
        console.log('项目服务系统名称选择', value)
    },
    // 修改
    onSubmit() {
      const loading = common.loading(this);
      console.log("onSubmit", this.formData);
      let arr = this.formData;
      arr.supplierRosterInfoDtos.forEach(item => {
        item.deptId = item.deptId.replace('dep', '')
      })
      this.formData = arr;
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          let data = JSON.stringify(this.formData);
          updateRoster(data).then((res) => {
            console.log(res, "提交");
            loading.close();
            if (res.data.status == 200) {
              this.$message.success(res.data.message);
            //   let message = common.getFlowMessgeNew(res.data.message);
            //   let messageArr = message.split(",");
              this.$nextTick(() => {
            //     this.messageNotify(messageArr);
            //     // 此处更换路径
                this.$router.push({ name: "supplierhmc" });
              });
            } else {
              this.$message.error(res.data.message);
              return;
            }
          });
        } else {
          loading.close();
          this.$message.error("提交失败");
          return;
        }
      });
    },
    // 上传文件
    uploadFile(file) {},

    // 上传文件状态改变
    changeFile(file, fileList) {
      this.fileList = [];
      let nowDate = new Date();
      // let objectNo = Moment(nowDate.toLocaleString()).format('YYMMDDHH');
      let objectNo = nowDate.getTime();
      var formData = new FormData();
      formData.append("file", file.raw);
      formData.append("objectNo", objectNo); //唯一编号
      formData.append("type", "RLWBXQ");
      formData.append("catalogNo", "CATALOG2022121200101");
      let data = formData;
      const loading = common.loading(this);
      uploadExcel(data).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.ifFinish = false;
          this.docmId = res.data.results;
          console.log(res,'上传附件')
          this.currentRow.fileNo = res.data.results;
          this.successMessage = "文件上传成功";
          this.$message.success("文件上传成功");
        } else {
          this.errorMessage = "文件上传失败";
          this.messageError();
        }
      });
    },
    /** 获取文件名称 */
    getFileName(id) {
      preview(id).then((res) => {
        console.log("获取文件名称", res);
        if (res.data.status == 200) {
          this.fileList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2,
          });
        }
      });
    },
    //ntko插件预览
      openNtkoWindow(fileList, mark) {
        console.log('this.fileList', fileList)
        let obj = this.fileList[0]
        let formJudge = obj.docname.split(".")[
            obj.docname.split(".").length - 1
        ];
        // console.log('formJudge', formJudge);
        if (formJudge == "jpg" || formJudge == "JPG" ||
            formJudge == "jpeg" || formJudge == "JPEG" || 
            formJudge == "png" || formJudge == "PNG") {
            this.ylDialog = true;
            let pos = obj.tag1.lastIndexOf("/");
            let str = obj.tag1.substr(0, pos);
            if (obj.tag2 != undefined) {
                this.ylPhotoUrl = str + "/" + obj.tag2;
            } else {
                this.ylPhotoUrl = obj.tag1;
            }
        } else if (formJudge == "zip" || formJudge == "rar" ||  formJudge == "txt") {
            this.$message.error("不能预览压缩文件或txt文件格式");
        } else {
          // // console.log('obj.docid', obj.docid);
          this.ntkoYl(obj.docid)
        }
      },

    // ntko
    ntkoYl(objectId) {
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
    // 文档下载(改)
    jlDownliad(docId) {
      const self = this;
      common.downloadJl(docId, self);
    },
    // 关闭
    toClose() {
      this.$router.push({
        name: "projectTransfer",
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
      this.$router.push({ name: "supplierhmc" });
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
    // 获取用工部门
    filDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
            console.log('获取用工部门', res);
          var fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter(tt => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });;
        })
        .catch(error => {
          this.$message.error(error);
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
.elselect >>> .el-select__tags {
  flex-wrap: nowrap;
  overflow: hidden;
}
.elselect >>> .el-tag {
  float: left;
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
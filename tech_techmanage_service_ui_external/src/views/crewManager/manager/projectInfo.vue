<!-- 添加项目信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">{{title}}项目</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common :buttonName="buttonClose" :size="sizes" :fns="close"></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>

    <!-- 项目信息 -->
    <div class="contentMain">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">项目信息</p>
      </div>
      <div class="content">
        <el-form :model="formData" ref="formData">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                 <!-- 
                   <el-col :span="11">
                  <div>
                    <p>项目编码 *</p>
                    <el-form-item prop="code" :rules="[{ required: true, message: '请填写项目编码', trigger: 'blur' }]" ref="msgForm">
                       <el-input placeholder="请输入" v-model="formData.code"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                 -->
                 <el-col :span="11">
                  <div>
                    <p>项目名称 *</p>
                    <el-form-item prop="name" >
                       <!-- <el-input placeholder="请输入" v-model="formData.name"></el-input> -->
                      <!-- <el-select placeholder="请选择" v-model="formData.name" @change="handleSelect" filterable :disabled="isEdit">
                        <el-option
                          v-for="item in project_options"
                          :key="item.id"
                          :value="item.xmmc + '+' + item.yzbm"
                          :label="item.xmmc +'('+ item.yzbm + ')'"
                        ></el-option>
                      </el-select> -->
                      <el-autocomplete v-model="formData.name" :fetch-suggestions="querySearch" placeholder="请输入内容" @select="handleSelectProject" :popper-append-to-body="false" style="width: 100%"></el-autocomplete>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>归属部门 *</p> 
                     <el-form-item prop="depId" :rules="[{ required: true, message: '请选择部门', trigger: 'change' }]">
                        <el-select v-model="formData.depId" placeholder="请选择部门" @change="getEmployees">
                            <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                
                <el-col :span="11">
                  <div>
                    <p>科技项目经理 *</p>
                    <el-form-item prop="badge" :rules="[{ required: true, message: '请选择科技项目经理', trigger: 'change' }]">
                        <el-select v-model="formData.badge" placeholder="请选择科技项目经理">
                            <el-option v-for="item in managerData" :key="item.id" :label="item.text" :value="item.id" />
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>业务项目经理 *</p>
                    <el-form-item prop="busiBadgeName">
                      <el-input
                        v-model="formData.busiBadgeName"
                        placeholder="请选择业务项目经理" 
                        suffix-icon="el-icon-search"
                        readonly
                        @focus="showDep(1)"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>测试项目经理</p>
                    <el-form-item>
                      <!-- <el-input v-model="formData.projectEndTime"> </el-input>-->
                        <el-input v-model="formData.testBadgeName" placeholder="请选择测试项目经理" suffix-icon="el-icon-search" readonly @focus="showDep(2)" ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>项目结束时间</p>
                    <el-form-item>
                      <!-- <el-input v-model="formData.projectEndTime"> </el-input>-->
                        <el-date-picker
                          v-model="formData.projectEndTime"
                          type="date"
                          placeholder="选择日期"
                          format="yyyy-MM-dd"
                        >
                        </el-date-picker>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>项目编号</p>
                    <el-form-item>
                      <el-input v-model="formData.projectNumber" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>项目来源 *</p>
                    <el-form-item prop="" :rules="[{ required: true, message: '请选择项目来源', trigger: 'change' }]">
                      <el-select v-model="formData.projectSource" placeholder="请选择项目来源" disabled>
                        <el-option
                          v-for="item in projectSource_options"
                          :key="item.id"
                          :value="item.dictid"
                          :label="item.dictname"
                        ></el-option>
                      </el-select>
                      <!-- <el-input v-model=""></el-input> -->
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="8">
                  <div>
                      <h5 style="margin-bottom: 6px; display: inline-block">
                        附件 
                      </h5>
                      <span
                        prop="doc"
                        :rules="[{ required: true, message: '请上传附件' }]"
                      >
                        <el-upload
                          class="upload-demo"
                          action="Fake Action"
                          :file-list="fileList"
                          :show-file-list="false"
                          :http-request="changeFile"
                          :headers="headers"
                          multiple
                        >
                          <!-- <el-button size="small" type="default">
                            <i class="el-icon-upload2"></i>上传文件
                          </el-button> -->
                        </el-upload>
                        <div
                          v-for="(item, index) in fileList"
                          :key="index"
                          style="margin-bottom: 5px"
                        >
                          {{ item.docname }}
                          <span
                            class="preview"
                            @click="openNtkoWindow(item, 'baomi')"
                            >预览</span
                          >
                          <span
                            class="download"
                            @click="downloadFile(item.docid)"
                            >下载</span
                          >
                          <!-- <span class="download" @click="delUpload(item.docid)"
                            >删除</span
                          > -->
                        </div>
                      </span>
                    </div>
                </el-col>
              </el-row>
              <el-row></el-row>
            </div>
          </div>
        </el-form>
      </div>
    </div> 
    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
         <div class="wrapper_left">
            <el-col :span="1.5">
              
            </el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5">
                <button-common :buttonName="buttonCancel" :size="sizes" :fns="close"/>
              </el-col>
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonCommit" :size="sizes" :fns="onSubmit" />
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>

    <!-- 弹框 -->
    <el-dialog
      title="选择项目经理"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
    <!-- 表单 -->
      <div>
        <el-form inline ref="deptForm" :model="deptForm">
          <el-form-item label="员工工号" prop="badge">
            <el-input v-model="deptForm.badge" placeholder="员工工号"></el-input>
          </el-form-item>
          <el-form-item label="员工姓名" prop="empname">
            <el-input v-model="deptForm.empname" placeholder="员工姓名"></el-input>
          </el-form-item>
          <el-form-item label="所属部门" prop="chargeName">
            <el-input v-model="deptForm.chargeName" placeholder="所属部门"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="submitSearchBtn">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!-- 树 -->
      <div class="dept-table-container">
        <el-table 
          :data="employeeList"  
          fit 
          border 
          height="400" 
          highlight-current-row 
          @current-change="handleTable"
          :row-style="rowClass"
          :row-class-name="tableRowClassName"
        >
          <el-table-column label="项目经理编号" prop="badge"></el-table-column>
          <el-table-column label="项目经理名称" prop="empname"></el-table-column>
          <el-table-column label="所属部门" prop="chargeName"></el-table-column>
        </el-table>
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
      </div>
      <!-- 底部 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="determine">确 定</el-button>
      </span>
    </el-dialog>

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
import {queryResourceDept,queryEmployeeTree} from "@/api/integrated/wbryzrlc";
import {save, queryProjectList, queryProjectListUpdate} from '@/api/manager/project';
import pager from "@/components/Pager"; 
import departmentTwo from "@/views/plugins/components/DepartmentTwo";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
import Moment from 'moment';
// import pager from "@/components/Pager";
import { queryPagerForEmployee } from "@/api/dataCenter/dataCenAccessPermission";
import { getDicEntry } from '@/api/eosDictEntry'
import { upload } from '@/api/integrated/newWbrz'
import { preview } from "@/api/common/workflowApi"; 
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser; // 引入ntko

export default {
    name: 'ProjectInfo',
    components: {departmentTwo,buttonOrange, buttonCommon, pager },
    data() {
        return {
          // 分页信息
          totalNumber: 0,
          size: 50,
          index: 1,
          depId: null,
          formData:this.$route.params,
          managerData:{},
          deptmentData:{},
          sizes: 'mini',  //按钮大小
          title: '添加',
          buttonClose: '关 闭', //关闭按钮名字
          buttonDel: '删 除',
          buttonCancel: '取 消',
          buttonCommit: '提 交',
          buttonSure: '确 定', 
          dialogVisible: false,
          deptForm: {
            badge: "", // 业务经理工号
            empname: "" // 业务经理名称
          },
          employeeList: [], // 行员列表
          selectEmployee: {}, // table当前选中行员info
          selectIndex: 0,
          project_options: [], // 项目名称
          projectSource_options: [], // 项目来源
          fileList: [], //上传文件列表
          showDepType: '',
          headers: { "Content-Type": "multipart/form-data" },
          // isEdit: null,
          ylDialog: false, //是否显示预览图片弹窗
          ylPhotoUrl: "", //预览图片地址
          photoFit:"scale-down",

        };
    }, 
    created() {  
      // console.log('this.$route.params', this.$route.params, Object.keys(this.$route.params).length == 0);
      // 点击'新增'按钮进来
      if(Object.keys(this.$route.params).length == 0) {
        this.getqueryProjectList();
        if(window.localStorage.getItem('initData')) {
          this.formData = JSON.parse(window.localStorage.getItem("initData"));
        }
      // 点击'修改'按钮进来
      } else if(Object.keys(this.$route.params).length !== 0 || Object.hasOwn(this.$router.params, 'projectNumber')) {
        // console.log("this.$router.params.projectNumber", 1);
        // 如果本地没数据
        if(!window.localStorage.getItem('initData')) {
          window.localStorage.setItem('initData', JSON.stringify(this.$route.params))
        } else {
          this.formData = JSON.parse(window.localStorage.getItem("initData"));
          // console.log('this.formDataaaaaaaaaaaaaaaaaaaaaa', this.formData)      
        }
      }
        this.getDeptments();
        this.getProjectSorceDoc();
        this.loadInit();
        if(this.formData.depId){ 
            this.title = "修改";
            this.depId = this.formData.depId;
            let depId = String(this.formData.depId);
            this.formData.depId = depId;
            this.getEmployees(depId);
            this.getqueryProjectListUpdate();
        }
    }, 
    computed: {
      isEdit() {
        return this.title == "修改" ? true: false
      }
    },
    methods: {
      querySearch(queryString, cb) {
        const project = this.project_options
        const projectList = [] 
        for (let i = 0;i < project.length ;i++){
          projectList.push({'value': project[i].xmmc,'projectNo': project[i].yzbm})
        }
        const results = queryString ? projectList.filter(this.createFilter(queryString)) : projectList

        cb(results);
        if(results == null || results.length < 1){
          // console.log("+++++++++++",results)
          this.formData.projectNumber = ""
          this.formData.projectSource = "02"
        }
        // 调用 callback 返回建议列表的数据
      },
      handleSelectProject(item){
        this.formData.name = item.value
        this.formData.projectNumber = item.projectNo
        this.formData.projectSource = "01"
      },
      createFilter(queryString) {
        // console.log('queryString', queryString)
        return (project) => {
          return (project.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
        };
      },
        //获取部门
        getDeptments() {
            let params = {};
            params.auth = true;
            params.objectgrade = "2";
            params.allowDept = "80000122";
            params.objectcode = "6000000300";
            queryResourceDept(params).then(res => {
                if (res.data.status == 200) {
                  var fild = res.data.results;
                  if(res.data.results[0].children){
                    var fild = res.data.results[0].children;
                  }
                    var reg = /([^-]+)$/;
                    this.deptmentData = fild.filter(li => {
                        li.id = li.id.replace("dep", "");
                        li.text = li.text.match(reg)[1] + "("+li.id+")";
                        return li;
                    });  
                } else {
                    this.$message.error("获取部门数据失败");
                }
            }).catch(error => {
                this.$message.error("获取部门数据失败");
            }); 
        },
        // 查询部门人员
        getEmployees(depId) {
            if(this.depId && depId != this.depId  ){ 
              this.$set(this.formData,'badge', '');
            }
            queryEmployeeTree(depId).then(res => {
                if (res.data.status == 200) {
                    this.depId = depId; 
                    var fild = res.data.results[0].children;  
                    this.managerData = fild.filter(li => { 
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
        // 获取项目信息 
        getqueryProjectList() {
          queryProjectList().then(res => {
            // console.log('获取项目信息', res);
            this.project_options = res.data.results;
            if(this.formData.depId) {
              this.handleSelect(this.formData.name);
            }
          })
        },
        // 进去修改页面后获取的项目信息 
        getqueryProjectListUpdate() {
          let params = {};
          params.xmmc = this.$route.params.name;
          params.yzbm = this.$route.params.projectNumber;
          queryProjectListUpdate(params).then(res => {
            // console.log('进去修改页面后获取的项目信息', res);
            this.project_options = res.data.results;
            if(this.formData.depId) {
              this.handleSelect(this.formData.name+ "+" + this.formData.projectNumber);
            }
          })
        },
        // 选择项目名称带出项目编号/项目来源/文件
        handleSelect(row) {
          // 选中的项目
          // console.log('000row', row);
          let arr = [];
          this.fileList = []
          // 遍历项目名称从而选中该项目
          // console.log('this.project_options', this.project_options)
          this.project_options.forEach(item => {
            let str = row.substring(row.indexOf('+')+1, row.length);
            // console.log('str', str);
            if(item.yzbm === str) {
              // console.log('item.yzbm', item);
              this.formData.projectNumber = item.yzbm;
              // 声明个arr数组来接受item的文件数组
              arr = item.supplierSyncFiles;
              // 利用接口获取附件名字
              for(let i in arr) {
                // console.log('利用接口获取附件名字', i, arr[i])
                this.getFileName(arr[i]);
              }
            }
          })
        },
        // 获取项目来源字典
        getProjectSorceDoc() {
          getDicEntry({dicttypeid:'PROJECT_SOURCE_DICT'}).then(res=> {
            // console.log('获取项目来源字典', res)
            if(res.data.status === 200) {
              this.projectSource_options = res.data.results;
            } else {
              this.$message.error(res.data.message);
            }
          })
        },
        // 显示弹框
        showDep(type) {
          // console.log('showDep');
          this.showDepType = type;
          this.dialogVisible = true;
          this.deptForm = {};

          let params = {};
          params.size = this.size;
          params.index = this.index;
          params.empstatus = "Norm" 
          if (type == 2) {
            params.adminid = '7004';
          }else{
            params.adminid = '';
          }
          this.getEmployeeInfo(params);
        },
        // table 选中触发
        handleTable(row) {
          console.log('row', row);
          this.selectIndex = row.badge;
          this.selectEmployee = row;
        },
        // 当前选中行的样式
        rowClass: function({ row, index }) {
          if (this.selectIndex === row.badge) {
            return { "background-color": "rgba(185,221,249,0.75)" };
          }
        },
        // 把每一行的索引放进row
        tableRowClassName: function({ row, rowIndex }) {
          row.index = rowIndex;
        },
        // 搜索
        submitSearchBtn() {
          console.log(this.showDepType,"!1111111111111")
          let params = this.deptForm;
          params.empstatus = "Norm" 
          params.size = this.size;
          params.index = 1;
          this.index = params.index;
          if (this.showDepType == 2) {
            params.adminid = '7004';
          }else{
            params.adminid = '';
          }
          this.getEmployeeInfo(params);
        },
        // 关闭弹框
        handleClose() {
          this.dialogVisible = false;
        },
        /**
         * 分页初始化方法
        */
        loadInit: function () {
          let params = {};
          params.size = this.size;
          params.index = this.index;
          params.empstatus = "Norm" 
          this.getEmployeeInfo(params);
        },
      // 获取行员信息
      getEmployeeInfo(params) {
        let data = common.paramsFormat(params);
        queryPagerForEmployee(data).then(res => {
          // console.log('获取行员信息', res);
          if(res.data.status === 200) {
            this.employeeList = res.data.results;
            this.totalNumber = res.data.total;
            if(this.totalNumber <= 0) {
              return false
            }
          } else {
            this.$message.error(res.data.message);
          }
        })
      },
      // 确定
      determine() {
        if (this.showDepType == 1) {
          // console.log('this.selectEmployee', this.selectEmployee, Object.keys(this.selectEmployee).length)
          if(Object.keys(this.selectEmployee).length == 0) return this.$message.error('请选择业务项目经理')
          this.formData.busiBadgeName = this.selectEmployee.empname;
          this.formData.busiBadge = this.selectEmployee.badge;
          this.dialogVisible = false;
          // this.$refs.busiBadgeNameInput.bulr()
        }

        if (this.showDepType == 2) {
          // console.log('this.selectEmployee', this.selectEmployee, Object.keys(this.selectEmployee).length)
          if(Object.keys(this.selectEmployee).length == 0) return this.$message.error('请选择测试项目经理')
          this.formData.testBadgeName = this.selectEmployee.empname;
          this.formData.testBadge = this.selectEmployee.badge;
          this.formData.testBadgeDept = this.selectEmployee.depid;
          this.formData.testBadgeDeptName = this.selectEmployee.chargeName;
          this.dialogVisible = false;
          // this.$refs.busiBadgeNameInput.bulr()
        }
      },
      /**
       * 提交
       */
        onSubmit() {
          // console.log('this.formData', this.formData);
          // console.log(this.formData)
          if(this.formData.busiBadgeName == '' || this.formData.busiBadgeName == undefined) {
            return this.$message.error('请选择业务项目经理')
          }
          // 测试项目经理必填校验
          // if(this.formData.testBadgeName == '' || this.formData.testBadgeName == undefined) {
          //   return this.$message.error('请选择测试项目经理')
          // }
          this.formData.name = this.formData.name.substring(0, this.formData.name.indexOf('+')) || this.$route.params.name;
            this.$refs["formData"].validate(valid => {
            if(valid){
                const loading = common.loading(this); 
                save(this.formData).then(res => {
                    loading.close();
                    if (res.data.status == "200") {
                        this.$message.success("操作成功！");  
                        this.$router.push({ name: 'projectMgr' });
                        window.localStorage.clear('initData');
                    } else {
                        this.$message.error(res.data.message); 
                    }
                });
            }
        });
       },
      // 关闭按钮
      close() {
        window.localStorage.clear('initData')
        this.$router.push({ name: 'projectMgr' });
      },
      // 分页触发
      getPager: function(size, index) {
        let params = {};
        params.size = size;
        params.index = index;
        this.size = size;
        this.index = index; 
        params.empstatus = "Norm" 
        if (this.showDepType == 2) {
          params.adminid = '7004';
        }else{
          params.adminid = '';
        }
        this.getEmployeeInfo(params);
      },
      // 获取文件名称
    getFileName(id) {
      preview(id).then((res) => {
        console.log('获取文件名称', res)
        if (res.data.status == 200) {
          this.fileList.push({
            "docid": id,
            "docname": res.data.results.docname,
            "tag1": res.data.results.tag1,
            "tag2": res.data.results.tag2,
          });
          console.log(this.fileList, "文件名称");
        }
      });
    },
      // 上传文件状态改变
    changeFile(file, fileList) {
      let nowDate = new Date();
      let objectNo = nowDate.getTime();
      var formData = new FormData();
      formData.append("objectNo", objectNo); //唯一编号
      formData.append("type", "RLWBXQ");
      formData.append("version", "release");
      formData.append("catalogNo", "CATALOG2022121200101");
      formData.append("file", file.file);
      let data = formData;
      upload(data).then((res) => {
        console.log(res, "上传结果");
        if (res.data.status == "200") {
          this.getFileName(res.data.results);
          this.cleanData();
          this.$message.success("上传成功");
        } else {
          this.$message.error("上传失败");
        }
      });
    },
    // 删除上传
    delUpload(docid) {
      let fileList = this.fileList.filter((item) => item.docid != docid);
      this.fileList = fileList;
      this.cleanData();
    },
    // 清洗数据
    cleanData() {
      let fileList = [];
      this.fileList.forEach((item) => {
        fileList.push(item.docid);
      });
      this.$set(this.formData, "doc", fileList);
      this.$refs["formData"].validateField("fileList");
    },
    /** 下载文件 **/
    downloadFile(docId) {
      const self = this;
      common.downloadDoc(docId, self);
    },
      /** ntko插件预览 **/
    openNtkoWindow(obj, mark) {
      console.log(obj,'887999544')
      let formJudge = obj.docname.split(".")[obj.docname.split(".").length - 1];
      console.log('formJudge', formJudge);
      if (
        formJudge == "jpg" ||
        formJudge == "JPG" ||
        formJudge == "jpeg" ||
        formJudge == "JPEG" ||
        formJudge == "png" ||
        formJudge == "PNG"
      ) {
        this.ylDialog = true;
        // let pos = obj.tag1.lastIndexOf("/");
        // let str = obj.tag1.replace("docname", tag2);
        // console.log('pos', pos)
        // let str = obj.tag1.substr(0, pos);
        // console.log('str', str);
        if (obj.tag2 != undefined) {
          // this.ylPhotoUrl = str + "/" + obj.tag2;
          this.ylPhotoUrl = obj.tag1.replace(obj.docname, obj.tag2);
        } else {
          this.ylPhotoUrl = obj.tag1;
        }
      } else if (
        formJudge == "zip" ||
        formJudge == "rar" ||
        formJudge == "txt"
      ) {
        this.$message.error("不能预览压缩文件或txt文件格式");
      } else {
        this.ntkoYl(obj.docid);
      }
    },
    // ntko预览方法
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
    }
};
</script>
<style scoped>
  
  /deep/ .el-autocomplete-suggestion {
    width:auto !important;
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
    width: 180px;
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

  .wrapper_right_bottom_1_zb {
    margin-right: 10px;
  }
  .content {
    width: 100%;
    font-size: 16px; 
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
  .form-container {
  margin-bottom: 10px;
}
.upload-demo {
  display: inline-block;
  margin-bottom: 6px;
}
.preview,
.download {
  color: #fba42f;
  font-size: 14px;
  margin-left: 5px;
  cursor: pointer;
}
</style>
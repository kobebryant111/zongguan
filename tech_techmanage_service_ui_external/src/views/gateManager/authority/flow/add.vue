<!-- 闸机权限申请 -->
<template>
    <div class="rlxqContent">
        <div class="nav">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="1.5">
                            <p class="nav_name">发起闸机权限申请</p>
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
        <div class="contentMain contentMain_rlxqAdd">
            <div class="contentTwo_title">
                <p class="contentTwo_title_p">基础信息</p>
            </div>
            <div class="content">
                <el-form :model="formData" ref="formData" >
                    <div class="row_1">
                        <div>
                            <el-row type="flex" justify="space-between">                                
                                <el-col :span="7">
                                    <div>
                                        <p>申请部门 *</p> 
                                        <el-form-item prop="depid" :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]">
                                            <el-select v-model="formData.depid" placeholder="请选择部门">
                                                <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>申请楼层权限(可多选) * </p> 
                                        <el-form-item prop="floorinfo" :rules="[{ required: true, message: '请选择申请楼层', trigger: 'change'}]">
                                            <el-select v-model="formData.floorinfo" @focus="cueMessage" placeholder="可多选楼层" multiple>
                                               <!-- <el-option v-for="item in floorData" 
                                                    :key="item.indexCode" 
                                                    :label="item.name" 
                                                    :value="item.indexCode"/> -->

                                                <el-option
                                                    v-for="item in floor"
                                                    :key="item.dictname"
                                                    :label="item.dictid"
                                                    :value="item.dictname"
                                                ></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>开通人员类型 *</p> 
                                        <el-form-item prop="bizType" :rules="[{ required: true, message: '请选择开通人员类型', trigger: 'change'}]">
                                             <el-select v-model="formData.bizType" @focus="cueMessage" @change="bizTypeChangeEvent">
                                                <el-option v-for="item in bizTypeData" 
                                                    :key="item.value" 
                                                    :label="item.label" 
                                                    :value="item.value"/>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                             <el-row type="flex" justify="space-between">
                                <el-col :span="7">
                                    <div>
                                        <p>申请类型 *</p>
                                        <el-form-item prop="applyType" :rules="[{ required: true, message: '请选择申请类型', trigger: 'change'}]">
                                            <el-select v-model="formData.applyType" disabled @change="applyTypeChangeEvent">
                                               <el-option v-for="item in applyTypeData" 
                                                    :key="item.value" 
                                                    :label="item.label" 
                                                    :value="item.value"/>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>开始时间 *</p> 
                                        <el-form-item prop="beginDate" :rules="[{ required: true, message: '请选择开始时间', trigger: 'change'}]">
                                            <el-date-picker v-model="formData.beginDate" disabled type="date" placeholder="请选择日期"
                                                value-format="yyyy-MM-dd"></el-date-picker>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>结束时间 *</p> 
                                        <el-form-item prop="endDate" :rules="[{ required: true, message: '请选择结束时间', trigger: 'change'}]">
                                            <el-date-picker v-model="formData.endDate" disabled type="date" placeholder="请选择日期"
                                                value-format="yyyy-MM-dd"></el-date-picker>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>

                    <!-- 临时人员权限 -->
                    <div class="row_require table_rlxqAdd" v-if="this.formData.bizType == -1">
                        <el-row>
                            <el-col :span="24">
                                <div class="contentTwo_title">
                                    <p class="row_2_detail">临时人员<span class="add_row" @click="addRow(1)">新增一行</span></p>
                                </div>
                                <div class="row_table">
                                    <el-table :data="formData.authorities" style="width: 100%">
                                        <el-table-column type="index" label="序号" width="50">
                                        </el-table-column>
                                        <el-table-column prop="badge" label="工号 *" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.badge`" :rules="[{ required: true, message: '请填写工号', trigger: 'blur'}]">
                                                     <el-input v-model="scope.row.badge" size="medium"  placeholder="请填写工号"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="empname" label="姓名" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.empname`" :rules="[{ required: true, message: '请填写姓名', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.empname" size="medium"  placeholder="请填写姓名"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="certificateNumber" label="证件号码" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.certificateNumber`" :rules="[{ required: true, message: '请填写证件号码', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.certificateNumber" size="medium"  placeholder="请填写证件号码"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="depid" label="部门" width="300">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.depid`" :rules="[{ required: true, message: '请选择部门', trigger: 'blur'}]">
                                                     <el-select v-model="scope.row.depid" disabled placeholder="请选择部门">
                                                        <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>                                         
                                        <el-table-column prop="operation" label="操作">
                                            <template slot-scope="scope">
                                                <div class="operation">
                                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.authorities,scope.row)">
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
                    <!-- 行员权限 -->
                    <div class="row_require table_rlxqAdd" v-else-if="this.formData.bizType == 0">
                        <el-row>
                            <el-col :span="24">
                                <div class="contentTwo_title">
                                    <p class="row_2_detail">行员信息<span class="add_row" @click="showEmployeeDialog">请选择</span></p>
                                </div>
                                <div class="row_table">
                                    <el-table :data="formData.authorities" style="width: 100%">
                                        <el-table-column type="index" label="序号" width="50"></el-table-column>
                                        <el-table-column prop="badge" label="工号 *" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.badge`" :rules="[{ required: true, message: '请填写工号', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.badge" size="medium" disabled placeholder="请填写工号"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="empname" label="姓名" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.empname`"  :rules="[{ required: true, message: '请填写姓名', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.empname" size="medium" disabled placeholder="请填写姓名"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="certificateNumber" label="证件号码" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.certificateNumber`" :rules="[{ required: true, message: '请填写证件号码', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.certificateNumber" size="medium" disabled placeholder="请填写证件号码"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="depname" label="部门" width="300">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.depid`" :rules="[{ required: true, message: '请选择部门', trigger: 'blur'}]">
                                                     <el-select v-model="scope.row.depid" disabled placeholder="请选择部门">
                                                        <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>                                         
                                        <el-table-column prop="operation" label="操作">
                                            <template slot-scope="scope">
                                                <div class="operation">
                                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.authorities,scope.row)">
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
                    <!-- 外包权限 -->
                    <div class="row_require table_rlxqAdd" v-else-if="this.formData.bizType == 1">
                        <el-row>
                            <el-col :span="24">
                                <div class="contentTwo_title">
                                    <p class="row_2_detail">外包信息<span class="add_row" @click="showOutsourcingDialog">请选择</span></p>
                                </div>
                                <div class="row_table">
                                    <el-table :data="formData.authorities" style="width: 100%">
                                        <el-table-column type="index" label="序号" width="50"></el-table-column>
                                        <el-table-column prop="badge" label="工号 *" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.badge`" :rules="[{ required: true, message: '请填写工号', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.badge" size="medium" disabled placeholder="请填写工号"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="empname" label="姓名" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.empname`"  :rules="[{ required: true, message: '请填写姓名', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.empname" size="medium" disabled placeholder="请填写姓名"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="certificateNumber" label="证件号码" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.certificateNumber`" :rules="[{ required: true, message: '请填写证件号码', trigger: 'blur'}]">
                                                    <el-input v-model="scope.row.certificateNumber" size="medium" disabled placeholder="请填写证件号码"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="depname" label="部门" width="300">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.depid`" :rules="[{ required: true, message: '请选择部门', trigger: 'blur'}]">
                                                    <el-select v-model="scope.row.depid" disabled placeholder="请选择部门">
                                                        <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>                                         
                                        <el-table-column prop="operation" label="操作">
                                            <template slot-scope="scope">
                                                <div class="operation">
                                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.authorities,scope.row)">
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
                            <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete>
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <div class="wrapper_right_bottom_1_zb">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonKeep" :size="size" :fns="saveDraft" :disabled="avoidRepeat"></button-common>
                            </el-col>
                        </div>
                        <div>
                            <el-col :span="1.5">
                                <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
                            </el-col>
                        </div>
                    </div>
                </div>
            </el-row>
        </div>
        <!-- 行员选择 -->
        <el-dialog title="行员选择" v-if="dialogEmployeeVisible" :visible.sync="dialogEmployeeVisible" width="85%" top="10vh">            
            <employee :userData="userData" :selectedRows="getDialogEmployeeRows" ref="employee"></employee>
            <div>
                <el-button type="primary" @click="selectedEmployee">确定</el-button>
                <el-button @click="cancelEmpolyee">取消</el-button>
            </div>
        </el-dialog>
        <!-- 外包选择 -->
        <el-dialog title="外包选择" v-if="dialogOutsourcingVisible" :visible.sync="dialogOutsourcingVisible" width="85%" top="10vh">            
            <outsourcing :userData="userData" :selectedRows="getDialogOutsourcingRows" ref="outsourcing"></outsourcing>
            <div>
                <el-button type="primary" @click="selectedOutsourcing">确定</el-button>
                <el-button @click="cancelOutsourcing">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common"; 
    import employee from "../common/Employee";
    import outsourcing from "../common/Outsourcing";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮 
    import { getDicEntry } from "@/api/eosDictEntry";
    import {save,doApply,doDel,queryResourceDept,queryEmployee,
    queryEmployeeTree,queryZjMgr} from "@/api/gateManager/authority";

    export default {
        name: 'authorityAdd',
        components: { buttonOrange, buttonCommon, buttonDelete,employee,outsourcing },
        data() {
            return { 
                size: 'mini',  //按钮大小
                buttonClose: '关 闭', //关闭按钮名字
                buttonDel: '删 除',
                buttonKeep: '存草稿',
                buttonCommit: '提 交',
                buttonCancel: '取 消',
                buttonSure: '确 定', 
                currentRow: '', //点击的当前行
                successMessage: '', // 成功消息提示
                errorMessage: '', //失败消息提示
                instanceid: '', //流程ID 
                workitemid:'', //工作项ID
                selectedEmployeeData:[],
                userData: {}, // dialog 用户数据
                dialogEmployeeVisible: false, //人员选择
                selectedOutsourcingData:[],
                dialogOutsourcingVisible: false, //人员选择
                delBtnIf: false, //删除按钮是否出现
                avoidRepeat: false, //避免重复点击存草稿
                deptmentData:[], //部门列表
                employeeData:[], //行员列表
                floorData:[], // 闸机信息
                floor:[],//楼层信息
                nodeVals: [], //选中的label值 
                flowItem: {}, //bps数据
                formData: { //验证字段 
                    depid : "",
                    authorities : [{
                        id: '', //表ID
                        depid: '', //部门ID
                        badge: '', //工号
                        certificateType: '', //证件类型
                        certificateNumber: '', //证件号码
                        depname: '', //部门名称
                        empname: '', //姓名
                        empstatus: ''
                    }]
                },
                bizTypeData:[{
                    value: 0,
                    label: "行员"
                },{
                    value: 1,
                    label: "外包"
                },{
                    value: -1,
                    label: "临时人员"
                }],
                applyTypeData:[{
                    value: 0,
                    label: "临时"
                },{
                    value: 1,
                    label: "短期"
                },{
                    value: 2,
                    label: "长期"
                }]
            };
        },
        methods: { 
             // 初始化
            init(params) {
                this.flowItem = params; 
                this.$nextTick(_ => {
                    this.getDeptments();
                    this.getFloorManagers();
                    this.getDic();
                 });
            },
            //获取部门
            getDeptments() {
                let params = {};
                params.auth = false;
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
                    }
                }).catch(error => {
                    this.$message.error("获取部门数据失败");
                }); 
            },
            // 取闸机清单
            getFloorManagers(){
                let params = {};
                queryZjMgr(params).then(res => {
                    if (res.data.status == 200) {  
                        this.floorData = res.data.results;
                        console.log("this.floorData值",this.floorData);
                    }
                }).catch(error => {
                    this.$message.error("获取闸机失败");
                }); 
            },
            // 取行员信息
            getEmpInfo(empId,index){
                queryEmployee({"badge":empId}).then((res) => {
                    if (res.data.status == 200) { 
                        this.formData.authorities[index].depid = res.data.results.depid;
                        this.formData.authorities[index].empname = res.data.results.empname; 
                    } 
                }).catch((err) => {
                    this.$message.error("获取部门数据失败");
                });
            },
            // 业务类型变更事件
            bizTypeChangeEvent(val){
                if(val == 0){
                    this.formData.applyType = 2;
                }else if(val == 1){
                    this.formData.applyType = 1;
                }else{
                    this.formData.applyType = 0;                    
                } 
                this.formData.authorities = [{
                        id: '', //表ID
                        depid: this.formData.depid, //部门ID
                        badge: '', //工号
                        certificateType: '001', //证件类型
                        certificateNumber: '', //证件号码
                        depname: '', //部门名称
                        empname: '', //姓名
                        empstatus: '在岗'
                    }];
                this.applyTypeChangeEvent(this.formData.applyType);                
            },
            // 申请类型变更事件
            applyTypeChangeEvent(val){
                let nowDate = new Date();
                if(val == 2){
                    // 长期
                    this.formData.beginDate = new Date();
                    this.formData.endDate = new Date("2099-01-01");
                }else if(val == 1){
                    //短期
                    this.formData.beginDate = new Date();
                    this.formData.endDate = new Date(nowDate.setMonth(nowDate.getMonth()+ 6));
                }else{
                    //临时
                    this.formData.beginDate = new Date(); 
                    this.formData.endDate = new Date(nowDate.setDate(nowDate.getDate() + 7)); 
                }
            },
            // 打开删除messageBox窗口
            delBox() {
                this.$confirm('确认删除该流程吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.delPro();
                }).catch(() => {

                })
            },
            // 新增一行
            addRow(val) { 
                this.formData.authorities.push({
                    id: '', //表ID
                    depid: this.formData.depid, //部门ID
                    badge: '', //工号
                    certificateType: '001', //证件类型
                    certificateNumber: '', //证件号码
                    depname: '', //部门名称
                    empname: '', //姓名
                    empstatus: '在岗'
                });
            },

            // 删除行
            deleteRow(index, rows, row) {
                if (rows.length != 1) {
                    rows.splice(index, 1);
                } else {
                    this.errorMessage = '不能少于一行'
                    this.saveError();
                }
            },
            /**
            * 显示行员选择页面
            */
            showEmployeeDialog: function () {
                this.selectedEmployeeData = [];
                this.dialogEmployeeVisible = true;
                this.userData.depid = this.formData.depid;
            },
            /**
            * 显示外包选择页面
            */
            showOutsourcingDialog: function () {
                this.selectedOutsourcingData = [];
                this.dialogOutsourcingVisible = true;
                this.userData.depid = this.formData.depid;
                this.userData.state = 0;
            },
            // 取选择的行员
            getDialogEmployeeRows(val){
                this.selectedEmployeeData = val; 
            },
            // 取选择的外包
            getDialogOutsourcingRows(val){
                this.selectedOutsourcingData = val; 
            },
            /**
            * 选择行员
            */
            selectedEmployee: function () {     
                let data = this.selectedEmployeeData;
                let authorities = this.formData.authorities; 
                let tmpObj = authorities.findIndex(item =>{
                    if(item.badge == "" && item.certificateNumber == ""){
                        return true;
                    }
                }); 
                if(tmpObj >= 0){
                    authorities.splice(tmpObj, 1);
                }
                data.forEach(function (item,index){
                    authorities.push({
                        id: '', //表ID
                        depid: item.depid, //部门ID
                        badge: item.badge, //工号
                        certificateType: item.certificateType, //证件类型
                        certificateNumber: item.certificateNumber, //证件号码
                        empname: item.empname, //姓名
                        empstatus: '在岗'
                    });
                });
                this.formData.authorities = authorities;
                this.dialogEmployeeVisible = false; 
                this.$refs['formData'].validate();
            },
            /**
            * 选择外包
            */
            selectedOutsourcing: function () {     
                let data = this.selectedOutsourcingData;
                let authorities = this.formData.authorities; 
                let tmpObj = authorities.findIndex(item =>{
                    if(item.badge == "" && item.certificateNumber == ""){
                        return true;
                    }
                }); 
                if(tmpObj >= 0){
                    authorities.splice(tmpObj, 1);
                }
                data.forEach(function (item,index){
                    authorities.push({
                        id: '', //表ID
                        depid: item.depid, //部门ID
                        badge: item.badge, //工号
                        certificateType: item.certificateType, //证件类型
                        certificateNumber: item.certificateNumber, //证件号码
                        empname: item.username, //姓名
                        empstatus: '在岗'
                    });
                });
                this.formData.authorities = authorities;
                this.dialogOutsourcingVisible = false; 
                this.$refs['formData'].validate();
            },
            /**
            * 取消行员选择
            */
            cancelEmpolyee: function () {
                this.dialogEmployeeVisible = false; 
            },
            /**
            * 取消外包选择
            */
            cancelOutsourcing: function () {
                this.dialogOutsourcingVisible = false; 
            },
            //没有选择部门时的提示信息
            cueMessage() {
                if (this.formData.depid == "") {
                    this.$message("请先选择部门");
                }
            },

            // 验证必填项
            proving() {
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        this.commit();
                    } else {
                        this.$message({
                            showClose: true,
                            message: '操作失败,请填写必填项',
                            type: 'error'
                        })
                        return false;
                    }
                })
            },

            // 保存草稿
            saveDraft() {
                this.avoidRepeat = true;
                let applyInfo = this.formData;
                applyInfo.id = this.workitemid;
                applyInfo.instanceid = this.instanceid;
                const loading = common.loading(this);                 
                save(applyInfo).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.workitemid = res.data.results.id; 
                        this.instanceid = res.data.results.instanceid; 
                        this.delBtnIf = true;
                        this.successMessage = '保存草稿成功'
                        this.saveSuccess();
                        this.avoidRepeat = false; 
                    } else {
                        this.errorMessage = res.data.message;
                        this.saveError();
                    }
                });

            },

            // 提交本页面数据
            commit() {
                const loading = common.loading(this);
                this.formData.ppmZjId = this.formData.floorinfo.join(",");
                let applyInfo = this.formData;
                console.log("ceshi",this.formData); 
                applyInfo.id = this.workitemid;
                applyInfo.instanceid = this.instanceid;
                doApply(applyInfo).then(res => {
                    // 关闭loading
                    loading.close();
                    if (res.data.status == 200) {
                        let message = common.getFlowMessgeNew(res.data.message);
                        let messageArr = message.split(",");
                        this.$nextTick(() => {
                            this.messageNotify(messageArr);
                            this.$router.push({ name: 'authority' });
                        })
                    } else {
                        this.errorMessage = res.data.message;
                        this.saveError();
                    }
                });

            },
 
            // 删除流程
            delPro() {
                let params = {}; 
                params.instanceid = this.instanceid;
                let data = common.paramsFormat(params);
                doDel(data).then(res => {
                    if (res.data.status == 200) {
                        this.successMessage = '流程删除成功'
                        this.saveSuccess();
                        this.$router.push({ name: 'authority' });
                    } else {
                        this.errorMessage = '流程删除失败'
                        this.saveError();
                    }

                });
            }, 
            //小于2位时前端补0
            checkMonth(i){
                if(i < 10){
                    i = "0" + i;
                }
                return i;
            },
            //消息提示
            messageNotify(messageVal) {
                this.$notify({
                    title: '提示',
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageVal[2] + '</div></div>',
                    duration: 0
                })
            },

            // 成功消息提示
            saveSuccess() {
                this.$message({
                    showClose: true,
                    message: this.successMessage,
                    type: 'success'
                })
            },

            // 失败消息提示
            saveError() {
                this.$message({
                    showClose: true,
                    message: this.errorMessage,
                    type: 'error'
                });
            },
            // 关闭按钮
            close() {
                this.$router.push({ name: 'authority' });
            },

            // 查询业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "FLOOR_PERMINSSIONS"
                }).then(res => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: "error" });
                    return false;
                }
                let data = res.data.results;
                // 在场情况
                this.floor = data.filter(item => {
                    return item.dicttypeid == "FLOOR_PERMINSSIONS";
                });
                return data;
                });
            }
        }
    };
</script>
<style scoped>
    .contentMain {
        width: 100%;
        height: 100%;
        font-size: 16px;
        padding: 10px 20px;
    }

    /* 去掉ul默认样式 */
    body ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }


    .nav {
        width: 100%;
        /* height: 30px; */
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 200px;
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

    .content .add_row {
        font-size: 15px;
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

    .contentMain .row_require {
        background-color: #ffffff;
        /* padding: 0 20px 15px 20px; */
        margin-top: 15px;
    }

    .row_table {
        padding: 12px 20px 15px 20px;
    }

    .add_row {
        color: #faad14;
        font-weight: 500;
        margin-left: 20px;
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
 
</style>

<style>
    .contentMain_rlxqAdd .el-icon-delete {
        width: 11px;
        height: 11px;
        color: #f56c6c;
    }

    .operation .el-button {
        border: 1px solid #ffffff;
        padding: 0;
    }

    /* table表格修改 */

    .contentMain_rlxqAdd .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .contentMain_rlxqAdd .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    /* .contentMain .el-table--medium td,
        .el-table--medium th {
            padding: 0;
        } */

    .table_rlxqAdd .el-table--medium th {
        padding: 0;
    }

    .table_rlxqAdd .el-table--medium td {
        padding: 0;
    }


    .table_rlxqAdd .el-table th {
        padding: 10px 0;
    }

    /* form表单样式修改 */
    .contentMain_rlxqAdd .el-form-item {
        margin: 16px 0;
    }

    /* 禁止点击输入框 */
    .rlxqContent .el-input.is-disabled .el-input__inner {
        cursor: pointer;
    }


    /* 计数器 */
    .contentMain_rlxqAdd .el-input-number--medium {
        width: 100px;
    }

    .contentMain_rlxqAdd .el-input-number__decrease:hover,
    .el-input-number__increase:hover {
        color: rgba(251, 164, 47, 1);
    }

    .contentMain_rlxqAdd .el-input-number__decrease:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled),
    .el-input-number__increase:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled) {
        border-color: rgba(251, 164, 47, 1);
    } 
    
    /* 级联框修改 */
    .contentMain_rlxqAdd .el-cascader {
        display: block;
    }

    /* el-input获得焦点样式修改 */
    .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    /* 禁用的输入框 */
    .contentMain_rlxqAdd .el-select {
        display: block;
    }

    /* 日期选择器 */
    .contentMain_rlxqAdd .el-date-editor.el-input,
    .el-date-editor.el-input__inner {
        width: 100%;
    }

    .el-date-table td.today span {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-table td.current:not(.disabled) span {
        background-color: rgba(251, 164, 47, 1);
    }

    .el-date-table td.available:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-picker-panel__icon-btn:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-picker__header-label.active,
    .el-date-picker__header-label:hover {
        color: rgba(251, 164, 47, 1);
    }

    /* 下拉框获得焦点样式修改 */
    .el-select .el-input.is-focus .el-input__inner {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-select .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-select-dropdown__item.selected {
        color: rgba(251, 164, 47, 1);
    }

    /* 级联选择器 */

    .el-cascader {
        display: block;
    }

    .el-cascader .el-input .el-input__inner:focus,
    .el-cascader .el-input.is-focus .el-input__inner {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-cascader-node.in-active-path,
    .el-cascader-node.is-active,
    .el-cascader-node.is-selectable.in-checked-path {
        color: rgba(251, 164, 47, 1);
    }

    .el-cascader__suggestion-item.is-checked {
        color: rgba(251, 164, 47, 1);
    }

    /* MessageBox弹窗 */
    .el-message-box__content {
        color: #262626;
        font-size: 16px;
    }

    .el-message-box__headerbtn {
        display: none;
    }

    /* MessageBox弹窗按钮 */
    .el-message-box__btns .el-button:focus {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .el-message-box__btns .el-button:hover {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .el-message-box__btns .el-button--primary {
        background-color: rgba(251, 164, 47, 1);
        border-color: rgba(251, 164, 47, 1);
    }

    .el-message-box__btns .el-button--primary:focus {
        color: #ffffff;
        background-color: #ebb563;
        border-color: #ebb563;
    }

    .el-message-box__btns .el-button--primary:hover {
        color: #ffffff;
        background-color: #ebb563;
        border-color: #ebb563;
    }

</style>
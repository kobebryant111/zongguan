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
                                            <el-select v-model="formData.depid" disabled placeholder="请选择部门">
                                                <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>申请楼层权限 *</p> 
                                        <el-form-item prop="ppmZjId">
                                            <el-select v-model="formData.ppmZjIdArrays" disabled multiple>
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
                                             <el-select v-model="formData.bizType" disabled @change="bizTypeChangeEvent">
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

                    <!-- 人员权限 -->
                    <div class="row_require table_rlxqAdd">
                        <el-row>
                            <el-col :span="24">
                                <div class="contentTwo_title">
                                    <p v-if="this.formData.bizType == -1" class="row_2_detail">临时人员</p>
                                    <p v-else-if="this.formData.bizType == 0" class="row_2_detail">行员信息</p>
                                    <p v-else-if="this.formData.bizType == 1" class="row_2_detail">外包信息</p>
                                </div>
                                <div class="row_table">
                                    <el-table :data="formData.authorities" style="width: 100%">
                                        <el-table-column type="index" label="序号" width="50">
                                        </el-table-column>
                                        <el-table-column prop="badge" label="工号 *" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.badge`" :rules="[{ required: true, message: '请填写工号', trigger: 'blur'}]">
                                                     <el-input v-model="scope.row.badge" size="medium" disabled placeholder="请填写工号"/>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="empname" label="姓名" width="250">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.empname`" :rules="[{ required: true, message: '请填写姓名', trigger: 'blur'}]">
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
                                        <el-table-column prop="depid" label="部门" width="300">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`authorities.${scope.$index}.depid`" :rules="[{ required: true, message: '请选择部门', trigger: 'blur'}]">
                                                     <el-select v-model="scope.row.depid" disabled placeholder="请选择部门">
                                                        <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
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
            
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs :processInstId="flowItem.processInstanceId" ref="log"></flow-logs>
            </div>
        </div>
        <div class="blank"></div>
        <!-- 底部操作栏 -->
        <div class="nav-bottom">
            <approve-bottom :proving="proving" :flowItem="flowItem"></approve-bottom>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮 
    import {getLogDetail, getAuthList, doAuth, drawback } from '@/api/common/flowApi';
    import { getDicEntry } from "@/api/eosDictEntry";
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    import approveBottom from "@/views/plugins/camunda/approveBottom";
    import {save,doApply,queryResourceDept,
        queryEmployee,queryEmployeeTree,doDel,topManagerAudit,
        queryDetail,queryZjMgr} from "@/api/gateManager/authority";
    import common from "@/api/common"; 

    export default {
        name: 'authorityAdd',
        components: { buttonOrange, buttonCommon, buttonDelete,flowLogs,approveBottom },
        data() {
            return { 
                size: 'mini',  //按钮大小
                buttonClose: '关 闭', //关闭按钮名字
                buttonCommit: '提 交',
                buttonCancel: '取 消',
                buttonSure: '确 定', 
                currentRow: '', //点击的当前行
                successMessage: '', // 成功消息提示
                errorMessage: '', //失败消息提示
                instanceid: '', //流程ID  
                workitemid: '', //工作项ID   
                avoidRepeat: false, //避免重复点击存草稿
                deptmentData:[], //部门列表
                employeeData:[], //行员列表
                floorData:[], // 闸机信息
                floor: [],//楼层信息
                nodeVals: [], //选中的label值 
                flowItem: {}, //bps数据
                formData: {},
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
                this.instanceid = params.processInstanceId;
                this.$nextTick(_ => {
                    this.getDeptments();
                    this.getFloorManagers();
                    this.getApplyDetail(params.processInstanceId);
                    // 流程日志
                    this.$refs.log.init(params.processInstanceId);
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
                    }
                }).catch(error => {
                    this.$message.error("获取闸机失败");
                }); 
            },            
            //取申请详情
            getApplyDetail(val){
                let params = {instanceid:val};
                queryDetail(params).then(res => {
                    if (res.data.status == 200) { 
                        var fild = res.data.results;
                        this.formData = fild;
                        this.workitemid = fild.id; 
                        this.getDic();
                    }
                }).catch(error => {
                    this.$message.error("获取申请信息失败");
                }); 
            }, 
            // 业务类型变更事件
            bizTypeChangeEvent(val){
                let authorities = []; 
                authorities.push({
                    id: '', //表ID
                    depid: '', //部门ID
                    badge: '', //工号
                    depname: '', //部门名称
                    empname: '', //姓名
                    empstatus: '在岗'
                });
                this.formData.authorities = authorities;
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
            // 验证必填项
            proving(data) {
                let status = new Promise(() => { });
                status = this.liuzhuan(data).then((val) => {
                    return val;
                });
                return status

            },
           
            // 流转或者退回
            liuzhuan(data) {
                const loading = common.loading(this);
                this.formData.ppmZjId = this.formData.ppmZjIdArrays.join(",");
                let params = this.formData;
                params.id = this.workitemid;
                params.instanceid = this.instanceid;
                this.flowItem.conclusion = data.conclusion;
                this.flowItem.suggest = data.suggest;
                params.bps = this.flowItem;
                return topManagerAudit(params).then(res => {
                    loading.close();
                    return res.data
                });
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
                this.$router.push({ name: 'dashboard' });
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
                let data1 = [];
                data.forEach(element => {
                    console.log("业务字典11111111",element.dictname);
                    console.log("this.formData值2",this.formData.ppmZjId);
                    const str = this.formData.ppmZjId;
                    console.log("str值2",str);
                    if(str.includes(element.dictname)) {
                        data1.push(element);
                    }
                });
                data = data1;
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
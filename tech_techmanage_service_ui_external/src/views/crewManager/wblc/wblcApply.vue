<!-- 离场申请-发起 -->
<template>
    <div>
        <div class="nav">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="3">
                            <p class="nav_name">外包人员离场申请</p>
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
        <div class="contentMain">
            <div class="grxx-content">
                <div class="grxx-title">
                    <p>个人信息</p>
                </div>
                <div>
                    <table class="grxx-table">
                        <tr class="grxx-table-tr">
                            <div>
                                <td>姓名</td>
                                <td>{{wblcData.userName == undefined ? '无' : wblcData.userName}}</td>
                            </div>
                            <div>
                                <td>身份证号</td>
                                <td>{{wblcData.certificateNumber == undefined ? '无' : wblcData.certificateNumber}}</td>
                            </div>
                             <div>
                                <td>供应商</td>
                                <td class="showSpot">{{wblcData.outsourcname == undefined ? '无' : wblcData.outsourcname}}</td>
                            </div>
                            <div>
                                <td>所属部门</td>
                                <td>{{wblcData.depname == undefined ? '无' : wblcData.depname}}</td>
                            </div>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="grxx-content">
                <div class="grxx-title">
                    <p>离场信息</p>
                </div>
                <div style="margin:20px;"></div>
                <div class="lcxx-mian">
                    <el-form ref="wblcForm" :label-position="'left'" :model="wblcEditData" :inline="true">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label-width="180px" prop="lgsj" label="申请离场时间" :rules="[{ required: true, message: '请选择申请离场时间', trigger: 'change' }]">
                                    <el-date-picker v-model="wblcEditData.lgsj" type="date" placeholder="请选择日期"
                                        value-format="yyyy-MM-dd"
                                        ></el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label-width="180px" prop="gzjj" label="是否做好工作交接" :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select style="width:130px;"  v-model="wblcEditData.gzjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.gzjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="gzjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.gzjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.gzjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col> 
                            <el-col :span="6">
                                <el-form-item label-width="180px" prop="bgsbjj" label="办公设备等是否收回" :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select  style="width:130px;" v-model="wblcEditData.bgsbjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.bgsbjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="bgsbjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.bgsbjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.bgsbjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="jfqxjj" label="查询机房权限是否收回" :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select  style="width:130px;" v-model="wblcEditData.jfqxjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.jfqxjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="jfqxjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.jfqxjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.jfqxjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="gpjj" label="工号牌、门禁卡是否收回 " :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select  style="width:130px;" v-model="wblcEditData.gpjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.gpjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="gpjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.gpjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.gpjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col> 
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="svnjj" label="SVN账号是否收回" :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select  style="width:130px;" v-model="wblcEditData.svnjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.svnjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="svnjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.svnjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.svnjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="xljjj" label="开发虚拟机账号是否收回" :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select  style="width:130px;" v-model="wblcEditData.xljjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.xljjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="xljjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.xljjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.xljjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label-width="180px" prop="vpnjj" label="VPN等相关移动开发配置是否收回" 
                                    :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select  style="width:130px;" v-model="wblcEditData.vpnjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.vpnjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="vpnjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.vpnjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0"> 
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.vpnjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col> 
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="xtqxjj" label="相关系统权限是否收回" :rules="[{ required: true, message: '请选择交接状态', trigger: 'change' }]">
                                    <el-select  style="width:130px;" v-model="wblcEditData.xtqxjj" placeholder="">
                                        <el-option v-for="(item, index) in jjOption" 
                                            :key="item.value" 
                                            :label="item.label" 
                                            :value="item.value">
                                        </el-option> 
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                               <div v-if="wblcEditData.xtqxjj == 1" key="1">
                                    <el-form-item label-width="80px" prop="xtqxjjRy" label="交接人" :rules="[{ required: true, message: '请填写交接人员', trigger: 'blur' }]">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.xtqxjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.xtqxjjRy"
                                            placeholder="请填写交接人员"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
            </div>

        </div>

        <div class="nav bottom">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="1.5">
                            <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete>
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <div class="wrapper-right-keep">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonKeep" :size="size" :fns="saveRequire"></button-common>
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
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
    import { queryWorkItem } from "@/api/integrated/wbryzrlc";
    import { save, queryDetail, getBaseInfo, outLeaveStart, del } from "@/api/integrated/wblc";
    import common from "@/api/common";
    import { doDel } from '@/api/common/workflowApi';
    import Moment from 'moment'
    import { mapGetters } from "vuex";
    export default {
        name: 'wblcApply',
        components: { buttonOrange, buttonCommon, buttonDelete },
        data() {
            return {
                buttonClose: '关 闭', //取消按钮名字
                size: 'mini',  //按钮大小
                buttonDel: '删 除',
                buttonKeep: '存草稿',
                buttonCommit: '提 交',
                wblcEditData: {},
                jjOption:[{
                    value: 0,
                    label:"无需交接"
                },{
                    value: 1,
                    label:"已交接"
                }],
                wblcData: {},
                delBtnIf: false, //删除按钮是否出现
                workItemID: '',
                processDefName: '', //流程定义名称
                delDialog: false, //是否显示删除弹窗
                bps: {},
                id: '', //主键ID
                instanceid: '', //流程ID
            };
        },
        computed: {
            ...mapGetters(["userId"])
        },
        created() {
        },
        mounted() {
        },
        methods: {
            // 初始化
            init(item) {
                console.log("离场页面", item)
                this.getWblcDetail(item.jobid);
            },

            // 获取离场详细信息
            getWblcDetail(id) {
                let params = {};
                params.jobid = id;
                let data = common.paramsFormat(params);
                getBaseInfo(data)
                    .then(res => {
                        if (res.data.status == 200) {
                            let wblcDetail = res.data.results.baseinfo;
                            if (wblcDetail.depname != undefined) {
                                var reg = /([^-]+)$/;
                                wblcDetail.depname = wblcDetail.depname.match(reg)[1];
                            }
                            if (wblcDetail.beginTime != undefined) {
                                wblcDetail.beginTime = Moment(wblcDetail.beginTime).format('YYYY-MM-DD');
                            }
                            this.wblcData = wblcDetail;
                        } else {
                            this.$message.error('获取数据失败');
                        }

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
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

            // 保存需求信息
            saveRequire() {
                const loading = common.loading(this);
                this.wblcEditData.jobid = this.wblcData.jobId;
                this.wblcEditData.username = this.wblcData.userName;
                this.wblcEditData.id = this.id;
                this.wblcEditData.instanceid = this.instanceid;
                let data = common.paramsFormat(this.wblcEditData);
                save(data).then(res => {
                    // 关闭loading
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('保存成功');
                        this.getWorkItem(res.data.results.instanceid);
                        this.id = res.data.results.id;
                        this.instanceid = res.data.results.instanceid;
                        this.delBtnIf = true;
                    } else {
                        this.$message.error(res.data.message);
                    }
                });

            },

            //获取待办工作项详情-单条
            getWorkItem(val) {
                const loading = common.loading(this)
                let params = {};
                params.processInstID = val;
                queryWorkItem(params).then(res => {
                    loading.close();
                    if (res.data.status = 200) {
                        this.bps = res.data.results;
                        this.workItemID = res.data.results.workItemID;
                        this.processDefName = res.data.results.processDefName;
                    } else {
                        this.$message.error('获取待办工作项详情失败');
                    }
                });
            },

            // 提交本页面数据
            commit() {
                const loading = common.loading(this);
                this.wblcEditData.jobid = this.wblcData.jobId;
                this.wblcEditData.username = this.wblcData.userName;
                this.wblcEditData.id = this.id;
                this.wblcEditData.instanceid = this.instanceid;
                let data = {};
                data.lzsq = this.wblcEditData;
                data.bps = this.bps;
                outLeaveStart(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        let message = common.getFlowMessgeNew(res.data.message);
                        let messageArr = message.split(",");
                        // this.messageNotify(messageArr);
                        messageArr[2] = messageArr[2].split(")")[0] + ')';
                        this.messageNotifyHnfzr(messageArr);
                        let finishData = {};
                        finishData.userName = this.wblcData.userName;
                        finishData.jobId = this.wblcData.jobId;
                        finishData.outsourcname = this.wblcData.outsourcname;
                        finishData.depname = this.wblcData.depname;
                        finishData.personInChargename = this.wblcData.personInChargename;
                        this.$router.push({
                            name: 'wblcFinish',
                            params: {
                                finishData: finishData
                            }
                        });
                    } else {
                        this.$message.error(res.data.message);
                    }
                });
            },

            // 验证必填项并提交
            proving() {
                this.$refs['wblcForm'].validate((valid) => {
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

            // 删除流程
            delPro() {
                const loading = common.loading(this)
                let params = {};
                params.processInstID = this.instanceid;
                params.workItemID = this.workItemID;
                params.processDefName = this.processDefName;
                let data = common.paramsFormat(params);
                doDel(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('流程删除成功');
                        this.$router.back();
                    } else {
                        this.$message.error('流程删除失败');
                    }

                });
            },

            // 删除按钮
            deleteProcess() {
                this.delDialog = true;
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'wbrzManage' });
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
            
            // 消息提示(多节点)
             messageNotifyHnfzr(messageVal) {
                if (messageVal.length > 3) {
                    messageVal[messageVal.length - 1] =  messageVal[messageVal.length - 1].split(")")[0] + ')';
                }
                let messageSec = messageVal.slice(2).join("，");
                this.$notify({
                    title: '提示',
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
                    duration: 0
                })
            },
        }
    };
</script>
<style scoped>
    .contentMain {
        width: 100%;
        height: 100%;
        font-size: 16px;
        padding: 0 20px;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 130px;
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

    .wrapper-right-keep {
        margin-right: 10px;
    }

    .grxx-title {
        height: 60px;
        line-height: 60px;
        padding: 0 25px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .grxx-title>p {
        color: #262626;
        font-weight: 600;
    }

    .grxx-content {
        background-color: #ffffff;
    }

    .grxx-table {
        width: 100%;
        padding: 10px;
        table-layout: fixed;
    }

    .grxx-table td {
        width: 100%;
        font-size: 15px;
        padding: 5px 0;
    }

    .grxx-table-tr {
        display: flex;
        justify-content: space-between;
    }

    .grxx-table-tr div {
        display: inline-block;
    }

    .grxx-table-tr td:nth-child(odd) {
        color: #909399;
        display: inline-block;
        white-space: nowrap;
    }

    .grxx-table-tr td:nth-child(even) {
        color: #262626;
        display: inline-block;
    }

    .lcxx-mian {
        padding: 0px 18px;
    }

    .lcxx-mian-cell-title {
        color: #262626;
        font-size: 14px;
    }

    .bottom {
        margin-top: 20px;
        padding: 15px 0;
    }

    /* .grxx-table .showSpot {
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        white-space: nowrap;
        margin: 0;
    } */
</style>
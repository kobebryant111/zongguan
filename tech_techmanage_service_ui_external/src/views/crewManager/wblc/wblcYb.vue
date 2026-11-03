<!-- 离场申请-发起 -->
<template>
    <div class="wblc-container">
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
                                <td>证件号码</td>
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
                    <el-form ref="wblcForm" disabled :label-position="'left'" :model="wblcEditData" :inline="true">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label-width="180px" prop="lgsj" label="申请离场时间" :rules="[{ required: true, message: '请选择申请离场时间', trigger: 'blur' }]">
                                    <el-date-picker v-model="wblcEditData.lgsj" type="date" placeholder="请选择日期"
                                        value-format="yyyy-MM-dd"
                                        ></el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label-width="180px" prop="gzjj" label="是否做好工作交接" :rules="[{ required: true, message: '请选择是否做好工作交接', trigger: 'blur' }]">
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
                                           ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.gzjjRy"
                                           ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col> 
                            <el-col :span="6">
                                <el-form-item label-width="180px" prop="bgsbjj" label="办公设备等是否收回" :rules="[{ required: true, message: '请选择办公设备等是否收回', trigger: 'blur' }]">
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
                                           ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.bgsbjjRy"
                                           ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="jfqxjj" label="查询机房权限是否收回" :rules="[{ required: true, message: '请选择查询机房权限是否收回', trigger: 'blur' }]">
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
                                            ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.jfqxjjRy"
                                            ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="gpjj" label="工号牌、门禁卡是否收回 " :rules="[{ required: true, message: '请选择工号牌、门禁卡是否收回', trigger: 'blur' }]">
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
                                            ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.gpjjRy"
                                            ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col> 
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="svnjj" label="SVN账号是否收回" :rules="[{ required: true, message: '请选择SVN账号是否收回', trigger: 'blur' }]">
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
                                            ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.svnjjRy"
                                            ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="xljjj" label="开发虚拟机账号是否收回" :rules="[{ required: true, message: '请选择开发虚拟机账号是否收回', trigger: 'blur' }]">
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
                                            ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.xljjjRy"
                                          ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label-width="180px" prop="vpnjj" label="VPN等相关移动开发配置是否收回" 
                                    :rules="[{ required: true, message: '请选择VPN等相关移动开发配置是否收回', trigger: 'blur' }]">
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
                                            ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0"> 
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.vpnjjRy"
                                            ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col> 
                            <el-col :span="6">
                                <el-form-item  label-width="180px" prop="xtqxjj" label="相关系统权限是否收回" :rules="[{ required: true, message: '请选择相关系统权限是否收回', trigger: 'blur' }]">
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
                                            ></el-input>
                                    </el-form-item>
                                </div>
                                <div v-else key="0">
                                    <el-form-item label-width="80px" label="交接人">
                                        <el-input type="text"  style="width:200px;"  v-model="wblcEditData.xtqxjjRy"
                                            ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
            </div>

            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs-gd ref="log"></flow-logs-gd>
            </div>
        </div>

        <div class="nav-bottom" v-if="bps.status == 'yb'">
            <yb-revoke :flowItem="bps"></yb-revoke>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
    import { getBaseInfo } from "@/api/integrated/wblc";
    import common from "@/api/common";
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
    import ybRevoke from "@/views/plugins/camunda/ybRevoke";
    import Moment from 'moment'
    export default {
        name: 'wblcYb',
        components: { buttonOrange, buttonCommon, buttonDelete, flowLogs, ybRevoke, FlowLogsGd },
        data() {
            return {
                buttonClose: '关 闭', //取消按钮名字
                size: 'mini',  //按钮大小
                wblcEditData: {},
                wblcData: {},
                jjOption:[{
                    value: 0,
                    label:"无需交接"
                },{
                    value: 1,
                    label:"已交接"
                }],
                bps: {},
            };
        },
        created() {
        },
        mounted() {
        },
        methods: {
            // 初始化
            init(item) {
                console.log("离场页面", item);
                this.bps = item;
                if (item.status == 'yb') {
                    this.getWblcDetail(item.processInstanceId);
                    this.$refs.log.init(item);
                } else if (item.status == 'gd') {
                    this.getWblcDetail(item.processInstanceId);
                    this.$refs.log.init(item);
                }

            },

            // 获取离场详细信息
            getWblcDetail(id) {
                let params = {};
                params.instanceid = id;
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
                            this.wblcEditData = res.data.results.lzsq;
                        } else {
                            this.$message.error('获取数据失败');
                        }

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },

        }
    };
</script>
<style scoped>
    .wblc-container {
        padding-bottom: 85px;
    }

    .contentMain {
        width: 100%;
        height: 100%;
        font-size: 16px;
        padding: 0 20px 60px 20px;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
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

    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }
</style>
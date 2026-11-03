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
                    <p>个人信息 <span class="ckdaBt">
                            <buttonOrange :buttonName="buttonGrda" :size="size" :fns="searchDa"></buttonOrange>
                        </span></p>
                </div>
                <div>
                    <table class="grxx-table">
                        <tr class="grxx-table-tr">
                            <div>
                                <td>姓名</td>
                                <td>{{wblcData.userName == undefined ? '无' : wblcData.userName}}</td>
                            </div>
                            <div>
                                <td>性别</td>
                                <td>{{wblcData.sexname == undefined ? '无' : wblcData.sexname}}</td>
                            </div>
                            <div>
                                <td>联系电话</td>
                                <td>{{wblcData.mobile == undefined ? '无' : wblcData.mobile}}</td>
                            </div>
                            <div>
                                <td>供应商</td>
                                <td>{{wblcData.outsourcname == undefined ? '无' : wblcData.outsourcname}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>证件类型</td>
                                <td>{{wblcData.certificateTypename == undefined ? '无' : wblcData.certificateTypename}}</td>
                            </div>
                            <div>
                                <td>证件号码</td>
                                <td>{{wblcData.certificateNumber == undefined ? '无' : wblcData.certificateNumber}}</td>
                            </div>
                            <div>
                                <td>生日</td>
                                <td>{{wblcData.birthday == undefined ? '无' : wblcData.birthday}}</td>
                            </div>
                            <div>
                                <td>籍贯</td>
                                <td>{{wblcData.hometown == undefined ? '无' : wblcData.hometown}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>毕业院校</td>
                                <td>{{wblcData.college == undefined ? '无' : wblcData.college}}</td>
                            </div>
                            <div>
                                <td>专业信息</td>
                                <td>{{wblcData.profession == undefined ? '无' : wblcData.profession}}</td>
                            </div>
                            <div>
                                <td>学历</td>
                                <td>{{wblcData.educationname == undefined ? '无' : wblcData.educationname}}</td>
                            </div>
                            <div>
                                <td>婚姻状况</td>
                                <td>{{wblcData.maritalStatusname == undefined ? '无' : wblcData.maritalStatusname}}</td>
                            </div>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="grxx-content">
                <div class="grxx-title">
                    <p>登记信息</p>
                </div>
                <div>
                    <table class="grxx-table">
                        <tr class="grxx-table-tr">
                            <div>
                                <td>外包方式</td>
                                <td>{{wblcData.outsourceTypename == undefined ? '无' : wblcData.outsourceTypename}}</td>
                            </div>
                            <div>
                                <td>所属合同</td>
                                <td>{{wblcData.htmc == undefined ? '合同暂未签订' : wblcData.htmc}}</td>
                            </div>
                            <div>
                                <td>结算方式</td>
                                <td>{{wblcData.userTypename == undefined ? '无' : wblcData.userTypename}}</td>
                            </div>
                            <div>
                                <td>入职时间</td>
                                <td>{{wblcData.beginTime == undefined ? '无' : wblcData.beginTime}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>所属部门</td>
                                <td>{{wblcData.depname == undefined ? '无' : wblcData.depname}}</td>
                            </div>
                            <div>
                                <td>所在小组</td>
                                <td>{{wblcData.groupname == undefined ? '无' : wblcData.groupname}}</td>
                            </div>
                            <div>
                                <td>行内负责人</td>
                                <td>{{wblcData.personInChargename == undefined ? '无' : wblcData.personInChargename}}</td>
                            </div>
                            <div>
                                <td>岗位</td>
                                <td>{{wblcData.postname == undefined ? '无' : wblcData.postname}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>项目名称</td>
                                <td>{{wblcData.projectname == undefined ? '无' : wblcData.projectname}}</td>
                            </div>
                            <div>
                                <td>项目经理</td>
                                <td>{{wblcData.projectmanagername == undefined ? '无' : wblcData.projectmanagername}}</td>
                            </div>
                            <div>
                                <td>系统名称</td>
                                <td>{{wblcData.systemnames == undefined ? '无' : wblcData.systemnames}}</td>
                            </div>
                            <div>
                                <td>系统负责人</td>
                                <td>{{wblcData.systemprincipalnames == undefined ? '无' :
                                    wblcData.systemprincipalnames}}</td>
                            </div>
                        </tr>
                        <tr class="grxx-table-tr">
                            <div>
                                <td>工号</td>
                                <td>{{wblcData.jobId == undefined ? '无' : wblcData.jobId}}</td>
                            </div>
                            <div>
                            </div>
                            <div>
                            </div>
                            <div>
                            </div>
                        </tr>
                    </table>
                </div>
            </div>

            <el-form :model="wblcEditData" ref="wblcForm">
                <div class="grxx-content">
                    <div class="grxx-title">
                        <p>离场信息</p>
                    </div>
                    <div class="lcxx-mian">
                        <el-row type="flex" justify="space-between">
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">实际离职时间</p>
                                <el-form-item>
                                    <el-date-picker v-model="wblcEditData.yjlgsj" type="date" placeholder="请选择日期"
                                        value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">离场说明</p>
                                <el-form-item>
                                    <el-input type="textarea" autosize v-model="wblcEditData.leaveexplain" placeholder="请填写离场的原因，及对部门或当前项目组的影响"
                                        :disabled="true"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">工作交接情况</p>
                                <el-form-item>
                                    <el-input type="textarea" autosize v-model="wblcEditData.workhandover" placeholder="填写撤离后的应对措施，替换人员的技能概况及到位时间"
                                        :disabled="true"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row type="flex" justify="space-between">
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">外包商领导意见</p>
                                <el-form-item>
                                    <el-input type="textarea" autosize v-model="wblcEditData.organizersuggestion"
                                        placeholder="" :disabled="true"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <p class="lcxx-mian-cell-title">项目经理意见</p>
                                <el-form-item>
                                    <el-input type="textarea" autosize v-model="wblcEditData.managersuggestion"
                                        placeholder="" :disabled="true"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7"></el-col>
                        </el-row>
                    </div>
                </div>
                <div class="grxx-content">
                    <div class="grxx-title">
                        <p>账号信息</p>
                    </div>
                    <div class="lcxx-mian">
                        <el-row type="flex" justify="space-between">
                            <el-col :span="5">
                                <div v-if="ifYwsj" key="1">
                                    <p class="lcxx-mian-cell-title">运维审计系统账号</p>
                                    <el-form-item prop="ywsjzh">
                                        <el-radio-group v-model="wblcEditData.ywsjzh" :disabled="true">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">运维审计系统账号 *</p>
                                    <el-form-item prop="ywsjzh" :rules="[{ required: true, message: '请选择运维审计系统账号', trigger: 'change' }]">
                                        <el-radio-group v-model="wblcEditData.ywsjzh">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                    <buttonOrange buttonName="确认注销" :size="size" :fns="ywsjLogoutBtn"></buttonOrange>
                                </div>
                            </el-col>
                            <el-col :span="5">
                                <div v-if="ifSVN" key="1">
                                    <p class="lcxx-mian-cell-title">SVN(或其他版本管理器)账号</p>
                                    <el-form-item>
                                        <el-radio-group v-model="wblcEditData.svnzh" :disabled="true">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">SVN(或其他版本管理器)账号 *</p>
                                    <el-form-item prop="svnzh" :rules="[{ required: true, message: '请选择SVN', trigger: 'change' }]">
                                        <el-radio-group v-model="wblcEditData.svnzh">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="5">
                                <div v-if="wblcData.personInCharge != userId" key="1">
                                    <p class="lcxx-mian-cell-title">OA账号</p>
                                    <el-form-item>
                                        <el-radio-group v-model="wblcEditData.oazh" :disabled="true">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">OA账号 *</p>
                                    <el-form-item prop="oazh" :rules="[{ required: true, message: '请选择OA账号', trigger: 'change' }]">
                                        <el-radio-group v-model="wblcEditData.oazh">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="5">
                                <div v-if="ifJfmj" key="1">
                                    <p class="lcxx-mian-cell-title">机房门禁</p>
                                    <el-form-item>
                                        <el-radio-group v-model="wblcEditData.llzh" :disabled="true">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">机房门禁 *</p>
                                    <el-form-item prop="llzh" :rules="[{ required: true, message: '请选择机房门禁', trigger: 'change' }]">
                                        <el-radio-group v-model="wblcEditData.llzh">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row type="flex" justify="space-between">
                            <el-col :span="5">
                                <div v-if="ifXnj" key="1">
                                    <p class="lcxx-mian-cell-title">虚拟机账号及开发域用户账号</p>
                                    <el-form-item>
                                        <el-radio-group v-model="wblcEditData.xnjzh" :disabled="true">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">虚拟机账号及开发域用户账号 *</p>
                                    <el-form-item prop="xnjzh" :rules="[{ required: true, message: '请选择虚拟机账号及开发域用户账号', trigger: 'change' }]">
                                        <el-radio-group v-model="wblcEditData.xnjzh">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="5">
                                <div v-if="wblcData.personInCharge != userId" key="1">
                                    <p class="lcxx-mian-cell-title">其他账号</p>
                                    <el-form-item>
                                        <el-radio-group v-model="wblcEditData.qtzh" :disabled="true">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                                <div v-else key="2">
                                    <p class="lcxx-mian-cell-title">其他账号 *</p>
                                    <el-form-item prop="qtzh" :rules="[{ required: true, message: '请选择其他账号', trigger: 'change' }]">
                                        <el-radio-group v-model="wblcEditData.qtzh">
                                            <el-radio v-for="(item,index) in ywsj_options" :label="item.label" :key="index">{{item.name}}</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="5">
                                <div>
                                    <p class="lcxx-mian-cell-title">其他账号信息</p>
                                    <el-input type="textarea" autosize v-model="wblcEditData.qtzhxx" placeholder="请输入"
                                        :disabled="wblcData.personInCharge != userId"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="5"></el-col>
                        </el-row>
                    </div>
                </div>
            </el-form>
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs ref="log"></flow-logs>
            </div>
        </div>

        <!-- 底部操作栏 -->
        <div class="nav-bottom">
            <approve-bottom :proving="proving" :flowItem="bps" :ifEmpower='true'></approve-bottom>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
    import { getBaseInfo, accountLogOut, logoutBej } from "@/api/integrated/wblc";
    import common from "@/api/common";
    import { getDicEntry } from "@/api/eosDictEntry";
    import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
    import approveBottom from "@/views/plugins/flowPlug/approveBottom";
    import { mapGetters } from "vuex";
    import Moment from 'moment'
    export default {
        name: 'wblcApply',
        components: { buttonOrange, buttonCommon, buttonDelete, flowLogs, approveBottom },
        data() {
            return {
                buttonClose: '关 闭', //取消按钮名字
                size: 'mini',  //按钮大小
                buttonGrda: '查看档案',
                wblcEditData: {},
                wblcData: {},
                bps: {},
                ywsj_options: [
                    {
                        label: 0,
                        name: '已注销'
                    },
                    {
                        label: 1,
                        name: '无此用户'
                    }
                ],
                ifJfmj: true, //机房门禁是否可选
                ifYwsj: true, //运维审计是否可选
                ifXnj: true, //虚拟机是否可选
                ifOA: true, //OA账号是否可选
                ifSVN: true, //SVN账号是否可选
            };
        },
        computed: {
            ...mapGetters(["userId"])
        },
        methods: {
            // 初始化
            init(item) {
                console.log("离场页面", item);
                this.bps = item;
                this.getWblcDetail(item.processInstID);
                this.getDic();
                this.$refs.log.init(item.processInstID);
            },

            // 查询业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "ZXQRRY_NEW"
                })
                    .then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        //账号注销节点配置
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].dictname == this.userId) {
                                if (data[i].dictid == 'oa账号') {
                                    this.ifOA = false;
                                } else if (data[i].dictid == '机房门禁') {
                                    this.ifJfmj = false;
                                } else if (data[i].dictid == '虚拟机') {
                                    this.ifXnj = false;
                                } else if (data[i].dictid == '运维审计系统帐号') {
                                    this.ifYwsj = false;
                                } else if (data[i].dictid == 'svn账号') {
                                    this.ifSVN = false;
                                }
                            }
                        }
                        return data;
                    });
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

            // 查看档案
            searchDa() {
                this.$router.push({
                    name: 'hmcArchives',
                    params: {
                        param: this.wblcData.jobId
                    }
                });
            },

            // 提交本页面数据
            commit(jljy) {
                const loading = common.loading(this);
                let data = {};
                data.lzsq = this.wblcEditData;
                this.bps.conclusion = jljy.conclusion;
                this.bps.suggest = jljy.suggest;
                data.bps = this.bps;
                return accountLogOut(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        return res.data;
                    } else {
                        this.$message.error('操作失败');
                    }
                });
            },

            // 流转并验证
            proving(data) {
                let status = new Promise(() => { });
                if (data.conclusion == "01") {
                    this.$refs['wblcForm'].validate((valid, val) => {
                        if (valid) {
                            status = this.commit(data).then((val) => {
                                return val;
                            });
                        } else {
                            this.$message.error('操作失败,请填写必填项');
                            return false;
                        }
                    })
                } else {
                    this.$message.error('该节点不能退回');
                    // status = this.commit(data).then((val) => {
                    //     return val;
                    // });
                }
                return status
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },
            /**
             * 运维审计账号注销
             */
            ywsjLogoutBtn() {
                console.log('wblcEditData::', this.wblcEditData)
                let data = common.paramsFormat({ badge: this.wblcEditData.jobid });
                logoutBej(data).then(res => {
                    let falg = res.data.results
                    if (falg) {
                        this.$set(this.wblcEditData, "ywsjzh", 0)
                    } else {
                        this.$set(this.wblcEditData, "ywsjzh", 1)
                    }
                })
            },  // end
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
        padding: 0 20px;
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
        width: 100px;
        white-space: nowrap;
    }

    .grxx-table-tr td:nth-child(even) {
        color: #262626;
        width: 261px;
    }

    .lcxx-mian {
        padding: 0px 18px;
    }

    .lcxx-mian-cell-title {
        color: #262626;
        font-size: 14px;
    }

    .ckdaBt {
        margin-left: 15px;
    }

    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }
</style>
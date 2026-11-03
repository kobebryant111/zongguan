<!-- SVN申请审批 -->
<template>
    <div class="approveTwoCon">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="8">
                                <p class="nav_name">SVN申请审批</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="8">
                                <buttonCommon :buttonName="closeName" :size="sizes" :fns="close"></buttonCommon>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="mainCon">
            <div class="First First_other">
                <div class="contentFirst_title">
                    <p class="contentTwo_title_p">基本信息</p>
                </div>
                <div class="contentFirst">
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>姓名</p>
                                    <el-input v-model="approveData.userName" :disabled="true"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>工号</p>
                                    <el-input v-model="approveData.jobId" :disabled="true"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>性别</p>
                                    <el-radio-group v-model="approveData.sex">
                                        <el-radio v-for="item in sex_options" :label="item.dictid" :key="item.dictid"
                                            disabled>{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>籍贯</p>
                                    <el-input v-model="approveData.nativeplace" :disabled="true"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="picker_sty_fir">
                                    <p>生日</p>
                                    <el-date-picker type="date" :disabled="true" v-model="approveData.birthday"></el-date-picker>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>婚姻状况</p>
                                    <el-radio-group v-model="approveData.maritalStatus">
                                        <el-radio v-for="(item,index) in marriage_options" :label="item.dictid" :key="index"
                                            :disabled="true">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>毕业院校</p>
                                    <el-input :disabled="true" v-model="approveData.school"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>专业</p>
                                    <el-input :disabled="true" v-model="approveData.specialty"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>学历</p>
                                    <el-radio-group v-model="approveData.education">
                                        <el-radio v-for="item in edu_options" :label="item.dictid" :key="item.dictid"
                                            :disabled="true">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>联系方式</p>
                                    <el-input :disabled="true" v-model="approveData.mobile"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <!-- 公司名称 -->
                                    <p>外包商</p>
                                    <el-select v-model="approveData.outsourceNo" filterable placeholder="" disabled>
                                        <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname"
                                            :value="item.orgcode">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>外包方式</p>
                                    <el-radio-group v-model="approveData.outsourceType">
                                        <el-radio v-for="(item, index) in outsourcetype_options" :label="item.dictid"
                                            :key="item.index" :disabled="true">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p class="deptno_p">所属部门</p>
                                    <el-select v-model="approveData.deptno" placeholder="请选择" disabled>
                                        <el-option v-for="(item,index) in deptno_options" :key="index" :value="item.id"
                                            :label="item.text"></el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <!--
                            <el-col :span="8">
                                <div>
                                    <p>所在小组</p>
                                    <el-select v-model="approveData.groupId" disabled>
                                        <el-option v-for="item in groupid_options" :key="item.id" :label="item.groupname"
                                            :value="item.id">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            -->
                            <el-col :span="8">
                                &nbsp;
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>结算方式</p>
                                    <el-radio-group v-model="approveData.userType">
                                        <el-radio v-for="item in usertype_options" :label="item.dictid" :key="item.dictid"
                                            disabled>{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>岗位</p>
                                    <el-select v-model="approveData.position" placeholder="请选择" disabled>
                                        <el-option v-for="item in position_options" :key="item.dictid" :label="item.dictname"
                                            :value="item.dictid"></el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>工作内容</p>
                                    <el-input type="textarea" autosize :disabled="true" v-model="approveData.workContent"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="picker_sty">
                                    <p>入职日期</p>
                                    <el-date-picker type="date" :disabled="true" v-model="approveData.entryTime"></el-date-picker>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="wbrzxmmc">
                                    <p>项目名称</p>
                                    <el-cascader v-if="approveData.projectno != undefined" ref="xmName" :options="projectno_options"
                                        :props="projectnoParams" v-model="approveData.projectno" :show-all-levels="false"
                                        :disabled="true"></el-cascader>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                    <!-- <el-checkbox v-model="approveData.isOtherProject" :disabled="true">项目未立项</el-checkbox> -->
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>项目经理</p>
                                    <el-input v-if="approveData.managerUserid != undefined" :disabled="true" v-model="managerUsername"></el-input>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>行内负责人</p>
                                    <el-select v-model="approveData.organizers" placeholder="请选择" disabled>
                                        <el-option v-for="(item, index) in organizers_options" :key="item.index" :label="item.text"
                                            :value="item.pcode"></el-option>
                                    </el-select>
                                </div>
                            </el-col>
                        </el-row>
                    </div>


                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>系统名称</p>
                                    <el-cascader v-if="approveData.systemnos != undefined" ref="systemName" :options="systemnos_options"
                                        :props="systemParams" v-model="approveData.systemnos" :show-all-levels="false"
                                        :disabled="true"></el-cascader>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>项目经理</p>
                                    <el-input v-if="approveData.systemPrincipal != undefined" :disabled="true" v-model="systemPrincipalname"></el-input>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>采购项目</p>
                                    <el-select v-if="approveData.purchaseItem != undefined" v-model="approveData.purchaseItem"
                                        :disabled="true">
                                        <el-option v-for="(item,index) in purchaseItem_options" :key="index" :label="item.label"
                                            :value="item.value">
                                        </el-option>
                                    </el-select>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>

            <!-- 配置信息 -->
            <div class="approveTwo-modu">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">配置信息</p>
                </div>
                <div class="content">
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>考勤类型</p>
                                    <el-select v-model="approveData.attendanceType" disabled placeholder="请选择">
                                        <el-option v-for="(item,index) in kqtype_options" :key="index" :value="item.kqType"
                                            :label="item.name">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>考勤时间</p>
                                    <el-input v-model="approveData.attendanceTime" :disabled="true"></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>虚拟桌面</p>
                                    <el-radio-group v-model="approveData.iscreditquery">
                                        <el-radio v-for="item in xnzm_options" :disabled="true" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>svn申请</p>
                                    <el-radio-group v-model="approveData.svnLimit">
                                        <el-radio v-for="item in sf_options" :disabled="true" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>特殊权限</p>
                                    <el-radio-group v-model="approveData.specialAuth">
                                        <el-radio v-for="item in sf_options" :disabled="true" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_approve">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                    <p>虚拟桌面配置</p>
                                    <el-input v-if="approveData.applyreg != undefined" type="textarea" autosize v-model="approveData.applyreg"
                                        :disabled="true"></el-input>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>SVN地址</p>
                                    <el-input v-if="approveData.svnRoad != undefined" type="textarea" autosize v-model="approveData.svnRoad"
                                        :disabled="true"></el-input>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>权限描述</p>
                                    <el-input v-if="approveData.authDes != undefined" type="textarea" autosize v-model="approveData.authDes"
                                        :disabled="true"></el-input>
                                    <el-input v-else placeholder="无" disabled></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs ref="log"></flow-logs>
            </div>
        </div>

        <!-- 底部栏样式2 -->
        <div class="nav-bottom" v-if="flowItem.status == 'db'">
            <approve-bottom :proving="proving" :flowItem="flowItem" :ifConclusion="false"></approve-bottom>
        </div>
    </div>

</template>

<script>
    import { getTree, queryResourceDept, queryGroup, queryEmployeeTree, querySystemTree, queryKqTypeList } from "@/api/integrated/wbryzrlc";
    import { getSvnDateil, svnApprove, getCheckedIdCard } from "@/api/integrated/newWbrz";
    import { getInterviewScore } from '@/api/integrated/wbms';
    import { getEmployUser, preview, getListAll } from '@/api/common/workflowApi';
    import common from "@/api/common";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    import approveBottom from "@/views/plugins/camunda/approveBottom";
    import { getDicEntry } from "@/api/eosDictEntry";
    import Moment from 'moment';
    import { checkedIsBlack } from "@/api/integrated/wbHmd";
    export default {
        components: { buttonOrange, buttonCommon, flowLogs, approveBottom },
        data() {
            return {
                approveData: {}, //审批页面信息
                genferData: [],
                sex_options: [],
                edu_options: [],
                outsourcetype_options: [],
                usertype_options: [],
                deptno_options: [],
                groupid_options: [],
                position_options: [],
                marriage_options: [],
                projectno_options: [],
                projectnoParams: { //项目名称配置信息
                    label: 'projectname',
                    value: 'projectno',
                    children: 'children',
                    emitPath: false,
                },
                organizers_options: [],
                systemnos_options: [],
                systemParams: {  //系统名称配置信息
                    label: 'text',
                    value: 'pcode',
                    children: 'children',
                    emitPath: false,
                },
                purchaseItem_options: [],
                kqtype_options: [],
                sf_options: [], //是否
                iscreditquery_options: [],
                svnlimit_options: [],
                eduGenre_options: [],
                sizes: 'mini',
                closeName: '关闭', // 关闭按钮
                flowItem: {}, //bps数据
                outsourceno_options: [],
                xnzm_options: [{ //虚拟桌面
                    dictid: '01',
                    dictname: '是'
                }, {
                    dictid: '02',
                    dictname: '否'
                }],
                managerUsername: '', //项目经理名称
                systemPrincipalname: '', //系统负责人名称
            }
        },
        watch: {

        },
        created() {
        },
        mounted() {
            console.log('SVN申请审批', this.$route.params)
            this.init(this.$route.params);
        },
        methods: {
            // 初始化
            init(item) {
                let lcId;
                if (item.instanceid != undefined) {
                    lcId = item.instanceid;
                    item.status = 'gd'
                } else {
                    lcId = item.processInstID
                    item.status = 'db'
                }
                this.flowItem = item;
                this.flowItem.processInstId = item.processInstanceId;
                this.$refs.log.init(lcId);
                this.getDic();
                queryKqTypeList() //查询考勤类型
                    .then(res => {
                        this.kqtype_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
                this.getApproveDetail(lcId);
                this.filDep();
                this.getProjectName();
                this.searchSystem();
                this.getWbcom();
            },

            // 获取审批页面信息
            getApproveDetail(val) {
                let params = {};
                getSvnDateil({ processInstId: val }).then(res => {
                    if (res.data.status == 200) {
                        res.data.results.education = res.data.results.education.toString();
                        res.data.results.entryTime = Moment(res.data.results.entryTime).format('YYYY-MM-DD');
                        if (res.data.results.purchaseItem == undefined) {
                            res.data.results.purchaseItem = '无';
                        }
                        if (res.data.results.isOtherProject != undefined) {
                            res.data.results.isOtherProject = eval(res.data.results.isOtherProject.toLowerCase());
                        }
                        if (res.data.results.deptno != undefined && res.data.results.deptno.indexOf('dep') == -1) {
                            res.data.results.deptno = 'dep' + res.data.results.deptno;
                        }
                        if (res.data.results.systemPrincipal != undefined) {
                            this.searchSystemPeo(res.data.results.systemPrincipal);
                        }
                        if (res.data.results.managerUserid != undefined) {
                            this.searchManager(res.data.results.managerUserid);
                        }
                        this.approveData = res.data.results;
                        this.$nextTick(() => {
                            this.searchIfCfrz(this.approveData.certifNumber, this.approveData.userName);
                            this.searchGroup(this.approveData.deptno);
                            this.searchEpmlo(this.approveData.deptno);
                        })
                    } else {
                        this.$message.error("获取数据失败")
                    }
                })
            },

            // 查询是否重复入职
            searchIfCfrz(id, name) {
                const loading = common.loading(this);
                getCheckedIdCard({ certifNumber: id, userName: name }).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.checkHmd(id, res.data.results.entry)
                    } else {
                        this.$message.error('查询入职信息失败');
                    }
                })
            },

            // 检查是否在黑名单中
            checkHmd(id, ifCf) {
                checkedIsBlack({ icard: id }).then(res => {
                    if (res.data.status == 200) {
                        console.log("黑名单", res.data)
                        if (ifCf == "重复入职" && res.data.results == true) {
                            this.$message({
                                showClose: true,
                                duration: 0,
                                message: '该外包人员重复入职/已被列入黑名单',
                                type: 'error'
                            })
                        } else if (ifCf == "重复入职" && res.data.results != true) {
                            this.$message({
                                showClose: true,
                                duration: 0,
                                message: '该外包人员重复入职',
                                type: 'error'
                            })
                        } else if (ifCf != "重复入职" && res.data.results == true) {
                            this.$message({
                                showClose: true,
                                duration: 0,
                                message: '该外包人员已被列入黑名单',
                                type: 'error'
                            })
                        }
                    } else {
                        this.$message.error('查询黑名单信息失败');
                    }
                })
            },

            // 查询系统负责人
            searchSystemPeo(val) {
                getEmployUser(val)
                    .then(res => {
                        if (res.data.status == 200) {
                            this.systemPrincipalname = res.data.results[0].empname;
                        } else {
                            this.$message.error('查询项目经理失败');
                        }
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 查询项目经理
            searchManager(val) {
                getEmployUser(val).then(res => {
                    if (res.data.status == 200) {
                        this.managerUsername = res.data.results[0].empname;
                    } else {
                        this.$message.error('查询项目经理失败');
                    }
                }).catch(error => {
                    this.$message.error(error);
                })
            },

            // 获取业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "BUSI_OUTSOURCE_GENFER,BUSI_OUTSOURCE_MARITALSTATUS,NEWKG_WBFS,NEWKG_JSFS,BUSI_OUTSOURCE_POSITION,NEWYY_EDUCATION,NEWKG_RZSF"
                })
                    .then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        // 性别
                        this.sex_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
                        });
                        // 婚姻状况
                        this.marriage_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_MARITALSTATUS";
                        });
                        //外包方式
                        this.outsourcetype_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_WBFS";
                        });
                        //结算方式
                        this.usertype_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_JSFS";
                        });
                        //岗位
                        this.position_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_POSITION";
                        });
                        //学历
                        this.eduGenre_options = data.filter(item => {
                            return item.dicttypeid == "NEWYY_EDUCATION";
                        });
                        this.edu_options = this.eduGenre_options;
                        //是否
                        this.sf_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_RZSF";
                        });
                        return data;
                    });
            },

            // 查询所有外包商
            getWbcom() {
                getListAll()
                    .then(res => {
                        this.outsourceno_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            //获取所属部门内容
            filDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            var fild = res.data.results[0].children;
                            var reg = /([^-]+)$/;
                            this.deptno_options = fild.filter(li => {
                                li.text = li.text.match(reg)[1];
                                return li;
                            })
                        } else {
                            this.$message.error("获取部门数据失败");
                        }

                    })
                    .catch(error => {
                        this.$message.error(error);
                    });  // end
            },

            //查询小组名称
            searchGroup(val) {
                let params = {};
                params.depid = val.replace("dep", "");
                queryGroup(params)
                    .then(res => {
                        this.groupid_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 查询项目名称
            getProjectName() {
                getTree()
                    .then(res => {
                        if (res.data.status == 200) {
                            var reg = /([^-]+)$/;
                            this.projectno_options = res.data.results.filter(li => {
                                li.projectname = li.projectname.match(reg)[1];
                                return li;
                            });
                        } else {
                            this.$message.error("获取项目名称数据失败");
                        }
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 查询行内负责人
            searchEpmlo(val) {
                let params = '';
                params = val.replace("dep", "");
                queryEmployeeTree(params)
                    .then(res => {
                        this.organizers_options = res.data.results[0].children;
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 查询系统名称
            searchSystem() {
                let params = '';
                params = '6000000300';
                querySystemTree(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            this.systemnos_options = res.data.results;
                        } else {
                            this.$message.error("获取系统名称失败");
                        }

                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 流转
            liuzhuan: function (params) {
                const loading = common.loading(this);
                this.flowItem.conclusion = params.conclusion;
                this.flowItem.suggest = params.suggest;
                return svnApprove(this.flowItem)
                    .then(res => {
                        loading.close();
                        return res.data
                    })
                    .catch(error => {
                        this.$alert(error, { type: "error" });
                    });

            },

            // 流转并验证
            proving(data) {
                let status = new Promise(() => { });
                status = this.liuzhuan(data).then((val) => {
                    return val;
                });
                return status
            },

            //关闭
            close() {
                this.$router.push({ name: 'dashboard' });
            },
        }
    }
</script>
<style scoped>
    .approveTwoCon {
        color: rgba(38, 38, 38, 1);
        padding-bottom: 85px;
    }

    .nav {
        width: 100%;
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
        padding: 0 15px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .mainCon {
        padding: 0 15px;
    }

    /* first页面样式 */
    .contentFirst_title {
        height: 60px;
        line-height: 60px;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentFirst {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px 15px 15px;
    }

    /* second页面样式 */
    .approveTwo-modu {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        margin-top: 20px;
    }

    .contentTwo_title_p {
        color: #262626;
        font-weight: 600;
    }

    .contentTwo_title {
        height: 60px;
        line-height: 60px;
        background-color: #ffffff;
        padding: 0 15px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentTwo_inte {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px 15px 15px;
    }

    .row_approve p {
        font-size: 14px;
    }

    /* 简历信息页面样式 */
    .content {
        padding: 0 15px 15px 15px;
    }

    /* 底部栏 */
    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }

    /* 人脸照片 */
    .facePhoto {
        width: 90px;
        height: 90px;
    }

    /* 入职材料 */
    .preview,
    .download {
        color: #FBA42F;
        font-size: 14px;
        margin-left: 5px;
        cursor: pointer;
    }

    /* 表格中显示全部内容处理 */
    .table_supply_w {
        width: 100%;
        height: 36px;
        line-height: 36px;
        font-size: inherit;
        border: 1px solid #E4E7ED;
        background-color: #F5F7FA;
        border-radius: 4px;
        padding: 0 15px;
        margin: 0;
        cursor: pointer;
    }

    /* 预览弹窗内容居中 */
    .ylDialogCon {
        text-align: center;
    }

    /* 预览图片大小 */
    .ylPhoto {
        width: 600px;
        width: 500px;
    }

    .mstable {
        margin-top: 20px;
    }

    .contentTwo_title .yjButt {
        font-size: 14px;
        font-weight: normal;
    }
</style>
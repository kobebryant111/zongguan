<!-- 人员结算准入申请之配置信息和面试信息 -->
<template>
    <div class="contentFive">
        <div class="contentFive_2">
            <div>
                <img src="@/assets/finish2.png" class="finish_img" alt="">
            </div>
            <div>
                <p class="contentFive_2_p1">申请提交成功</p>
                <p class="contentFive_2_p2">请耐心等待流程</p>
            </div>
            <div class="contentFive_d">
                <div>
                    <el-row :gutter="100">
                        <el-col :span="12">
                            <div class="contentFive_d_con">
                                <p>姓名：</p>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div>
                                <p class="con_val">{{userName}}</p>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div>
                    <el-row :gutter="100">
                        <el-col :span="12">
                            <div class="contentFive_d_con">
                                <p>工号：</p>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div>
                                <p class="con_val">{{jobId}}</p>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div>
                    <el-row :gutter="100">
                        <el-col :span="12">
                            <div class="contentFive_d_con">
                                <p>供应商：</p>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div>
                                <p class="con_val">{{outsourceNo}}</p>
                            </div>
                        </el-col>
                    </el-row>
                </div>

                <div>
                    <el-row :gutter="100">
                        <el-col :span="12">
                            <div class="contentFive_d_con">
                                <p>所属部门：</p>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div>
                                <p class="con_val">{{depName}}</p>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div>
                    <el-row :gutter="100">
                        <el-col :span="12">
                            <div class="contentFive_d_con">
                                <p>项目经理：</p>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div>
                                <p class="con_val">{{organizers}}</p>
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </div>
            <!-- 继续申请 -->
            <div class="fin-conti">
                <buttonOrange :buttonName="buttonSure" :size="size" :fns="contiApply"></buttonOrange>
            </div>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { searchDept, getWbrzDetail } from "@/api/integrated/newWbrz";
    import { getDepartmentDetail, getEmployUser, getListAll } from '@/api/common/workflowApi';
    export default {
        components: { buttonOrange },
        data() {
            return {
                size: 'mini',  //按钮大小
                buttonSure: '继续申请',
                userName: '',
                jobId: '',
                outsourceNo: '',
                depName: '',
                organizers: '',
            }
        },
        props: {
            instanceId: {
                type: String,
                required: true
            },
        },
        watch: {
            'instanceId': function () {
                this.getDatail();
            },
        },
        mounted() {
            if (this.instanceId != '') {
                this.getDatail();
            }
        },
        methods: {
            getDatail() {
                getWbrzDetail({ instanceId: this.instanceId }).then(res => {
                    if (res.data.status == 200) {
                        this.userName = res.data.results.userName;
                        this.jobId = res.data.results.jobId;
                        // this.outsourceNo = res.data.results.outsourceNo;
                        this.getWbcom(res.data.results.outsourceNo);
                        this.userSearch(res.data.results.organizers);
                        this.departmentSearch(res.data.results.deptno);
                    } else {
                        this.$message.error("获取数据失败")
                    }
                })
            },

            // 查询所有外包商
            getWbcom(v) {
                getListAll()
                    .then(res => {
                        if (res.data.status == 200) {
                            for (var i = 0; i < res.data.results.length; i++) {
                                if (res.data.results[i].orgcode == v) {
                                    this.outsourceNo = res.data.results[i].outsourcname;
                                    return;
                                }
                            }
                        } else {
                            this.$message.error("查询外包商失败")
                        }

                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 行内负责人查询
            userSearch(id) {
                getEmployUser(id).then(res => {
                    if (res.data.status == 200) {
                        this.organizers = res.data.results[0].empname
                    } else {
                        this.$message.error('查询行内负责人失败');
                    }

                }).catch(error => {
                    this.$message.error(error);
                })
            },

            // 部门查询
            departmentSearch(id) {
                let depid = id.replace('dep', '');
                getDepartmentDetail({ depid: depid }).then(res => {
                    if (res.data.status == 200) {
                        this.depName = res.data.results.location.replace('总行金融科技部-', '');
                    } else {
                        this.$message.error('查询部门失败');
                    }

                }).catch(error => {
                    this.$message.error(error);
                })
            },

            // 继续申请
            contiApply() {
                // location.reload();
                this.$router.push({ name: 'wbrzManage' });
            }
        }
    }
</script>
<style scoped>
    .contentFive {
        width: 100%;
        background-color: #ffffff;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .finish_img {
        width: 250px;
        height: 210px;
        margin-top: 30px;
    }

    .contentFive_2 {
        text-align: center;
    }

    .contentFive_2_p1 {
        font-size: 24px;
        color: #000000;
        margin: 0;
    }

    .contentFive_2_p2 {
        font-size: 14px;
    }

    .contentFive_d {
        width: 556px;
        background: #F5F5F5;
        border-radius: 2px;
        color: rgba(0, 0, 0, 0.85);
    }

    .contentFive_2 .con_val {
        color: rgba(0, 0, 0, 0.65);
        text-align: left;
    }

    .contentFive .contentFive_d_con {
        text-align: left;
        padding-left: 130px;
    }

    .fin-conti {
        text-align: center;
        margin: 20px 0;
    }
</style>
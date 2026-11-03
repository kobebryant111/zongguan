<!-- 我的待办 -->
<template>
    <div class="mydb-container">
        <div v-if="ifShowDb">
            <div class="mydb-top" v-if="dbData.role == 'COMM_USER' || dbData.role == 'DEP_MANAGERS'">
                <p class="mydb-title">考核制定/自评</p>
                <div>
                    <el-row :gutter="10" v-if="dbData.role == 'COMM_USER'">
                        <el-col :span="6" v-if="dbData.emp_formulate_kpis_first_half != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u105" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">员工{{dbData.tyear}}上半年KPI制定</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-content-bottom-left">已制定<span>{{dbData.emp_formulate_kpis_first_half}}</span>项</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpizd('上半年')">前往制定></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="6" v-if="dbData.emp_formulate_kpis_last_half != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u105" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">员工{{dbData.tyear}}下半年KPI制定</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-content-bottom-left">已制定<span>{{dbData.emp_formulate_kpis_last_half}}</span>项</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpizd('下半年')">前往制定></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="6" v-if="dbData.emp_self_eval_kpis_first_half != 0 && dbData.emp_self_eval_kpis_first_half != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u106" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">员工{{dbData.tyear}}上半年KPI自评</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-content-bottom-left">待自评<span>{{dbData.emp_self_eval_kpis_first_half}}</span>项</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpizp('上半年')">前往自评></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="6" v-if="dbData.emp_self_eval_kpis_last_half != 0 && dbData.emp_self_eval_kpis_last_half != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u106" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">员工{{dbData.tyear}}下半年KPI自评</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-content-bottom-left">待自评<span>{{dbData.emp_self_eval_kpis_last_half}}</span>项</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpizp('下半年')">前往自评></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                    <div class="mydb-bottom-row" v-if="dbData.role == 'DEP_MANAGERS'">
                        <el-row :gutter="10">
                            <el-col :span="6" v-if="dbData.managers_formulate_kpis != -1">
                                <div class="mydb-card">
                                    <svg-icon class-name="svg-icon" icon-class="u105" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">经理{{dbData.tyear}}年度KPI制定</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-content-bottom-left">已制定<span>{{dbData.managers_formulate_kpis}}</span>项</div>
                                            <div class="mydb-card-content-bottom-right" @click="toKpizd('全年度')">前往制定></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6" v-if="dbData.managers_self_eval_kpis_half_year != 0 && dbData.managers_self_eval_kpis_half_year != -1">
                                <div class="mydb-card">
                                    <svg-icon class-name="svg-icon" icon-class="u105" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">经理{{dbData.tyear}}半年度KPI自评</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-content-bottom-left">待自评<span>{{dbData.managers_self_eval_kpis_half_year}}</span>项</div>
                                            <div class="mydb-card-content-bottom-right" @click="toKpizp('半年度')">前往自评></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6" v-if="dbData.managers_self_eval_kpis != 0 && dbData.managers_self_eval_kpis != -1">
                                <div class="mydb-card">
                                    <svg-icon class-name="svg-icon" icon-class="u105" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">经理{{dbData.tyear}}年度KPI自评</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-content-bottom-left">待自评<span>{{dbData.managers_self_eval_kpis}}</span>项</div>
                                            <div class="mydb-card-content-bottom-right" @click="toKpizp('全年度')">前往自评></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>
            <div class="mydb-bottom">
                <p class="mydb-title">考核审批/评价</p>
                <div>
                    <el-row :gutter="10">
                        <el-col :span="6" v-if="dbData.emp_kpis_approve_first_half != 0 && dbData.emp_kpis_approve_first_half != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u114" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">员工{{dbData.tyear}}上半年考核审批</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.emp_kpis_approve_first_half}}</span>人待审批</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpisp('上半年')">前往审批></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="6" v-if="dbData.emp_kpis_approve_last_half != 0 && dbData.emp_kpis_approve_last_half != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u114" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">员工{{dbData.tyear}}下半年考核审批</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.emp_kpis_approve_last_half}}</span>人待审批</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpisp('下半年')">前往审批></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="6" v-if="dbData.managers_kpis_approve != 0 && dbData.managers_kpis_approve != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u131" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">经理{{dbData.tyear}}年度考核审批</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.managers_kpis_approve}}</span>人待审批</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpisp('全年度')">前往审批></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="6" v-if="dbData.managers_eval != 0 && dbData.managers_eval != -1">
                            <div class="mydb-card">
                                <svg-icon class-name="svg-icon" icon-class="u114" />
                                <div class="mydb-card-content">
                                    <p class="mydb-card-content-top">经理{{dbData.tyear}}年度考核评价</p>
                                    <div class="mydb-card-content-bottom">
                                        <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.managers_eval}}</span>人待评价</div>
                                        <div class="mydb-card-content-bottom-right" @click="toKpipj('全年度')">前往评价></div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                    <div class="mydb-bottom-row">
                        <el-row :gutter="10">
                            <el-col :span="6" v-if="dbData.emp_eval_first_half != 0 && dbData.emp_eval_first_half != -1">
                                <div class="mydb-card">
                                    <svg-icon class-name="svg-icon" icon-class="u114" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">员工{{dbData.tyear}}上半年考核评价</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.emp_eval_first_half}}</span>人待评价</div>
                                            <div class="mydb-card-content-bottom-right" @click="toKpipj('上半年')">前往评价></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6" v-if="dbData.emp_eval_last_half != 0 && dbData.emp_eval_last_half != -1">
                                <div class="mydb-card">
                                    <svg-icon class-name="svg-icon" icon-class="u114" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">员工{{dbData.tyear}}下半年考核评价</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.emp_eval_last_half}}</span>人待评价</div>
                                            <div class="mydb-card-content-bottom-right" @click="toKpipj('下半年')">前往评价></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6" v-if="dbData.subordinate_evaluation != 0 && dbData.subordinate_evaluation != -1">
                                <div class="mydb-card">
                                    <svg-icon class-name="svg-icon" icon-class="u123" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">{{dbData.tyear}}履职评估</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.subordinate_evaluation}}</span>人待评估</div>
                                            <div class="mydb-card-content-bottom-right" @click="toLzpg">前往评估></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="mydb-bottom-row">
                        <el-row :gutter="10">
                            <el-col :span="6">
                                <div class="mydb-card" v-if="dbData.depYearData_confirm != 0">
                                    <svg-icon class-name="svg-icon" icon-class="u115" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">部门{{dbData.tyear}}年度评级</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.depYearData_confirm}}</span>人待评级</div>
                                            <div class="mydb-card-content-bottom-right" @click="toYearpj('dep')">前往评级></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="mydb-card" v-if="dbData.centerYearData_first_eval != 0">
                                    <svg-icon class-name="svg-icon" icon-class="u115" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">中心{{dbData.tyear}}年度评级</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.centerYearData_first_eval}}</span>人待评级</div>
                                            <div class="mydb-card-content-bottom-right" @click="toYearpj('center')">前往评级></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="mydb-card" v-if="dbData.tecYearData_second_eval != 0">
                                    <svg-icon class-name="svg-icon" icon-class="u115" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">科技部{{dbData.tyear}}年度评级</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-approve-content-bottom-left"><span>{{dbData.tecYearData_second_eval}}</span>人待评级</div>
                                            <div class="mydb-card-content-bottom-right" @click="toYearpj('tec')">前往评级></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>
        </div>
        <div v-else class="noneDb">当前没有待办</div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { getMyDb } from "@/api/hykh/myDb";
    import { mapGetters } from "vuex";
    export default {
        name: 'myKpiDb',
        components: {},
        data() {
            return {
                dbData: {},
                ifShowDb: false
            }
        },
        computed: {
            ...mapGetters(["deptId"])
        },
        mounted() {
            console.log("部门", this.deptId, typeof this.deptId)
            this.getDbData();
        },
        methods: {
            // 获取我的待办数据
            getDbData() {
                const loading = common.loading(this);
                getMyDb().then(res => {
                    loading.close();
                    console.log('查询我的待办数据', res.data)
                    if (res.data.status == 200) {
                        this.dbData = res.data.results;
                        let dbData = JSON.parse(JSON.stringify(res.data.results));
                        for (let k in dbData) {
                            if ((k == 'emp_formulate_kpis_first_half' || k == 'emp_formulate_kpis_last_half') && dbData[k] != -1 && dbData.role == 'COMM_USER') {
                                console.log('循环对象', k, dbData[k])
                                this.ifShowDb = true;
                                break
                            } else if (k == 'managers_formulate_kpis' && dbData.role == 'DEP_MANAGERS' && dbData[k] != -1) {
                                this.ifShowDb = true;
                                break
                            } else if (k != 'eid' && k != 'role' && k != 'term' && k != 'tyear' && dbData[k] != -1 && dbData[k] != 0) {
                                this.ifShowDb = true;
                                break
                            }
                        }
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 跳转KPI制定
            toKpizd(val) {
                console.log("跳转KPI制定", val);
                let params = {};
                if (val != undefined) {
                    params.term = val
                } else {
                    params.term = null;
                }
                params.tyear = this.dbData.tyear;
                params.eid = this.dbData.eid;
                this.$router.push({
                    name: 'kpiEstablish',
                    params
                })
            },

            // 跳转KPI审批
            toKpisp(val) {
                console.log("跳转KPI制定", val);
                let params = {};
                if (val != '全年度') {
                    params.term = val;
                    params.role = 'COMM_USER';
                } else {
                    params.term = val;
                    params.role = 'DEP_MANAGERS'
                }
                params.tyear = this.dbData.tyear;
                params.check = this.dbData.eid;
                sessionStorage.setItem('hykh_db_kpisp_jump_data', JSON.stringify(params));
                this.$router.push({
                    name: 'kpispList',
                    params
                })
            },

            //跳转自评
            toKpizp(val) {
                let params = {};
                if (val != undefined) {
                    if (val == '半年度') {
                        params.juStatus = val;
                        params.term = '全年度'
                    } else {
                        params.term = val
                    }
                } else {
                    params.term = null
                }
                params.tyear = this.dbData.tyear;
                params.eid = this.dbData.eid;
                this.$router.push({
                    name: 'kpiZp',
                    params
                })
            },

            // 跳转KPI评价
            toKpipj(val) {
                let params = {};
                if (val != '全年度') {
                    params.term = val;
                    params.role = 'COMM_USER';
                } else {
                    // params.role = 'DEP_MANAGERS'
                    params.roles = "'DEP_MANAGERS','DEP_MANAGER'";
                    params.term = val;
                }
                params.tyear = this.dbData.tyear;
                sessionStorage.setItem('hykh_db_kpiEval_jump_data', JSON.stringify(params));
                this.$router.push({
                    name: 'dbkpiPjList',
                    params
                })
            },

            // 跳转下级评上级履职评估
            toLzpg() {
                let params = {};
                params.tyear = this.dbData.tyear;
                sessionStorage.setItem('hykh_db_sxLzpg_jump_data', JSON.stringify(params));
                this.$router.push({
                    name: 'lzpgListXia',
                    params
                })
            },

            // 跳转评级
            toYearpj(val) {
                this.dbData.pjType = val;
                this.$router.push({
                    name: 'yearKhpj',
                    params: this.dbData
                })
            }
        }
    }
</script>
<style scoped>
    .mydb-container {
        padding: 20px;
    }

    .mydb-title {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 18px;
    }

    .mydb-card {
        position: relative;
        border-width: 0px;
        width: 100%;
        height: 148px;
        background: inherit;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 10px;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        padding: 25px;
    }

    .svg-icon {
        height: 73px;
        width: 73px;
        border-width: 0px;
        position: absolute;
        left: 0px;
        top: 0px;
        opacity: 0.1;
    }

    .mydb-card-content-top {
        border-width: 0px;
        white-space: nowrap;
        text-transform: none;
        box-shadow: none;
        font-family: 'Nunito Sans Bold', 'Nunito Sans Regular', 'Nunito Sans', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 18px;
        text-align: left;
        margin: 0;
    }

    .mydb-card-content-bottom {
        display: flex;
        justify-content: space-between;
        font-weight: 400;
        font-style: normal;
        margin-top: 25px;
    }

    .mydb-card-content-bottom-left,
    .mydb-card-approve-content-bottom-left {
        font-family: 'Nunito Sans', sans-serif;
        font-size: 16px;
        color: #9B9B9B;
        white-space: nowrap;
    }

    .mydb-card-content-bottom-left span {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-size: 40px;
        color: #017CFF;
        margin: 0 8px;
        display: inline-block;
        transform: translateY(10%);
    }

    .mydb-card-approve-content-bottom-left span {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-size: 40px;
        color: #FF0160;
        margin: 0 8px;
        display: inline-block;
        transform: translateY(10%);
    }

    .mydb-card-content-bottom-right {
        font-size: 14px;
        color: #017CFF;
        font-family: 'Arial Normal', 'Arial', sans-serif;
        cursor: pointer;
        line-height: 65px;
        white-space: nowrap;
    }

    .mydb-bottom {
        margin-top: 50px;
    }

    .mydb-bottom-row {
        margin-top: 20px;
    }

    .noneDb {
        height: calc(100vh - 162px);
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 50px;
        color: #C4C4C4;
        display: flex;
        align-items: center;
        justify-content: center;
    }
</style>
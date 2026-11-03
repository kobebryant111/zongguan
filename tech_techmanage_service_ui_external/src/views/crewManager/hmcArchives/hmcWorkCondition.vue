<!-- 花名册配置工作情况 -->
<template>
    <div class="archives-container-workcondition">
        <div class="archives-main">
            <div class="archives-main-year">
                <el-date-picker v-model="currentYear" type="year" placeholder="" value-format="yyyy" @change="yearChange"></el-date-picker>
            </div>
            <div class="archives-modular">
                <div class="archives-title">
                    <span class="xkg-color-black archives-title-name">考核评级</span>
                </div>
                <div>
                    <el-table :data="khpjData" style="width: 100%">
                        <el-table-column prop="jan" label="1月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="feb" label="2月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="mar" label="3月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="apr" label="4月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="may" label="5月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="jun" label="6月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="jul" label="7月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="aug" label="8月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="sep" label="9月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="oct" label="10月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="nov" label="11月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="dec" label="12月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="year" label="年度"></el-table-column>
                    </el-table>
                </div>
                <div>
                    <p>月均评级超过二级部内<span>{{khRatio.depRate}}</span>%员工，超过供应商内<span>{{khRatio.outsourcenoRate}}</span>%员工，超过科技部内<span>{{khRatio.tecRate}}</span>%员工</p>
                </div>
            </div>
            <div class="archives-modular">
                <div class="archives-title">
                    <span class="xkg-color-black archives-title-name">奋斗积分（小时）</span>
                </div>
                <div>
                    <el-table :data="jiabanData" style="width: 100%">
                        <el-table-column prop="jan" label="1月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="feb" label="2月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="mar" label="3月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="apr" label="4月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="may" label="5月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="jun" label="6月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="jul" label="7月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="aug" label="8月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="sep" label="9月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="oct" label="10月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="nov" label="11月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="dec" label="12月" width="75" :show-overflow-tooltip="true"></el-table-column>
                    </el-table>
                </div>
                <div>
                    <p>月均奋斗积分超过二级部内<span>{{jiabanRatio.depRate}}</span>%员工，超过供应商内<span>{{jiabanRatio.outsourcenoRate}}</span>%员工，超过科技部内<span>{{jiabanRatio.tecRate}}</span>%员工</p>
                </div>
            </div>
            <!-- <div class="archives-modular">
                <div class="archives-title">
                    <span class="xkg-color-black archives-title-name">产出</span>
                </div>
                <div>
                    <el-table :data="workConditionData.khpjList" style="width: 100%">
                        <el-table-column prop="" label="1月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="2月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="3月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="4月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="5月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="6月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="7月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="8月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="9月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="10月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="11月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="" label="12月" width="75" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="年度"></el-table-column>
                    </el-table>
                </div>
                <div>
                    <p>月均产出超过二级部内<span>80%</span>员工，超过供应商内<span>80%</span>员工，超过科技部内<span>80%</span>员工</p>
                </div>
            </div> -->

        </div>

    </div>
</template>

<script>
    import { getOutEvalMonthData, getOutEvalOrderData, getOutOvertimeMonthData, getOutOvertimeOrderData } from "@/api/integrated/hmcDa";
    export default {
        name: 'hmcWorkCondition',
        data() {
            return {
                workConditionData: {},
                currentYear: '',
                khpjData: [], //考核评级数据
                jiabanData: [], //加班时间数据
                khRatio: {}, //考核评级比例
                jiabanRatio: {}, //加班时间比例
            }
        },
        props: {
            jibenDetail: {
                type: Object,
                required: true
            },
        },
        watch: {
            jibenDetail(val) {
                this.workConditionData = val;
                this.getKhpjData();
                this.getKhBlData();
                this.getJiaBanData();
                this.getJiaBanBlData();
            }
        },
        created() {
        },
        mounted() {
            this.init();
        },
        methods: {
            // 页面初始化
            init() {
                let date = new Date();
                let currentMonth = date.getMonth() + 1;
                this.currentYear = date.getFullYear() + '-' + currentMonth + '-' + date.getDate();
            },

            // 获取考核评级数据
            getKhpjData() {
                let params = {};
                params.deptno = this.workConditionData.deptId.replace("dep", "");
                params.jobid = this.workConditionData.jobId;
                params.outsourceno = this.workConditionData.outsourceNo;
                params.year = this.currentYear.split('-')[0];
                getOutEvalMonthData(params).then(res => {
                    if (res.data.status == 200) {
                        this.khpjData[0] = res.data.results;
                        this.khpjData.splice(0, 0);
                    } else {
                        this.$message.error('查询外包考核月度统计数据失败');
                    }
                })
            },

            // 获取考核评级比例数据
            getKhBlData() {
                let params = {};
                params.deptno = this.workConditionData.deptId.replace("dep", "");
                params.jobid = this.workConditionData.jobId;
                params.outsourceno = this.workConditionData.outsourceNo;
                params.year = this.currentYear.split('-')[0];
                getOutEvalOrderData(params).then(res => {
                    if (res.data.status == 200) {
                        this.$set(this.khRatio, 'depRate', 100 - res.data.results.depRate);
                        this.$set(this.khRatio, 'outsourcenoRate', 100 - res.data.results.outsourcenoRate);
                        this.$set(this.khRatio, 'tecRate', 100 - res.data.results.tecRate);
                    } else {
                        this.$message.error('查询外包考核月度比例数据失败');
                    }
                })
            },

            // 获取加班数据
            getJiaBanData() {
                let params = {};
                params.deptno = this.workConditionData.deptId.replace("dep", "");
                params.jobid = this.workConditionData.jobId;
                params.outsourceno = this.workConditionData.outsourceNo;
                params.year = this.currentYear.split('-')[0];
                getOutOvertimeMonthData(params).then(res => {
                    if (res.data.status == 200) {
                        this.jiabanData[0] = res.data.results;
                        this.jiabanData.splice(0, 0);
                    } else {
                        this.$message.error('查询外包加班月度统计数据失败');
                    }
                })
            },

            // 获取加班比例数据
            getJiaBanBlData() {
                let params = {};
                params.deptno = this.workConditionData.deptId.replace("dep", "");
                params.jobid = this.workConditionData.jobId;
                params.outsourceno = this.workConditionData.outsourceNo;
                params.year = this.currentYear.split('-')[0];
                getOutOvertimeOrderData(params).then(res => {
                    if (res.data.status == 200) {
                        this.$set(this.jiabanRatio, 'depRate', 100 - res.data.results.depRate);
                        this.$set(this.jiabanRatio, 'outsourcenoRate', 100 - res.data.results.outsourcenoRate);
                        this.$set(this.jiabanRatio, 'tecRate', 100 - res.data.results.tecRate);
                    } else {
                        this.$message.error('查询外包加班月度比例数据失败');
                    }
                })
            },

            // 年份改变
            yearChange(val) {
                this.getKhpjData();
                this.getKhBlData();
                this.getJiaBanData();
                this.getJiaBanBlData();
            }
        }
    }
</script>

<style scoped>
    .archives-main-year {
        width: 23%;
    }

    .archives-title {
        margin: 20px 0 10px 0;
    }

    .archives-title-name {
        font-size: 15px;
        font-weight: 600;
    }

    .archives-title-second {
        margin-top: 15px;
    }
</style>

<style>

</style>
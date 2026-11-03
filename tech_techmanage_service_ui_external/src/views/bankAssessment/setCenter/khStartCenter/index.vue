<!-- 考核发起中心-聚合页 -->
<template>
    <div class="szzx-container">
        <div class="khfq-head">
            <el-row>
                <el-col :span="6">
                    <el-select v-model="currentYear">
                        <el-option v-for="item in yearArr" :key="item" :label="item" :value="item"></el-option>
                        <template slot="prefix">年份：</template>
                    </el-select>
                </el-col>
                <el-col :span="12">
                    <div class="szzx-fqButt">
                        <el-button type="warning" size="small" @click="start">考核表发起</el-button>
                        <el-button size="small" @click="tbButt">同 步</el-button>
                    </div>
                </el-col>
            </el-row>
        </div>
        <el-tabs type="border-card">
            <el-tab-pane label="考核发起">
                <khfq ref="sdfq"></khfq>
            </el-tab-pane>
            <el-tab-pane label="评价发起">
                <pingjia-fq ref="zdfq"></pingjia-fq>
            </el-tab-pane>
            <el-tab-pane label="评级发起">
                <pingji-fq></pingji-fq>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>
<script>
    import Khfq from "@/views/bankAssessment/setCenter/khStartCenter/khfq";
    import PingjiaFq from "@/views/bankAssessment/setCenter/khStartCenter/pingjiaFq";
    import PingjiFq from "@/views/bankAssessment/setCenter/khStartCenter/pingjiFq";
    import { launch, updatePpmKpiPm } from "@/api/hykh/khStart";
    import common from "@/api/common";
    export default {
        name: 'khStartCenter',
        components: { Khfq, PingjiaFq, PingjiFq },
        data() {
            return {
                yearArr: [],
                currentYear: 2021,
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            init() {
                let date = new Date();
                this.currentYear = date.getFullYear();
                this.yearArr = [date.getFullYear(), date.getFullYear() - 1];
            },

            // 发起
            start() {
                const loading = common.loading(this);
                let params = {};
                params.tyear = this.currentYear;
                let data = common.paramsFormat(params);
                launch(data).then(res => {
                    loading.close();
                    console.log('发起', res.data)
                    if (res.data.status == 200 || res.data.results == true) {
                        this.$message.success('发起成功');
                        this.$refs.sdfq.seaTableData();
                        this.$refs.zdfq.seaTableData();
                    } else {
                        this.$message.error('发起失败');
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 更新手动发起列表
            updateSd() {
                this.$refs.sdfq.seaTableData();
            },

            // 更新自动发起列表
            updateZd() {
                this.$refs.zdfq.seaTableData();
            },

            // 同步
            tbButt() {
                const loading = common.loading(this);
                let params = {};
                params.tyear = this.currentYear;
                let data = common.paramsFormat(params);
                updatePpmKpiPm(data).then(res => {
                    loading.close();
                    console.log('同步', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('同步成功');
                        this.$refs.sdfq.seaTableData();
                        this.$refs.zdfq.seaTableData();
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },
        }
    }
</script>
<style scoped>
    .szzx-container {
        padding: 15px;
    }

    .khfq-head {
        margin-bottom: 15px;
    }

    .szzx-fqButt {
        margin-left: 15px;
        line-height: 36px;
    }
</style>
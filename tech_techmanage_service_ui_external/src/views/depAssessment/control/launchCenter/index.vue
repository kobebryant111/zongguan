<!-- 部门考核发起中心-聚合页 -->
<template>
    <div class="szzx-container">
        <div class="khfq-head">
            <el-row>
                <el-col :span="6">
                    <el-select v-model="currentYear" @change="yearChange">
                        <el-option v-for="item in yearArr" :key="item" :label="item" :value="item"></el-option>
                        <template slot="prefix">年份：</template>
                    </el-select>
                </el-col>
                <el-col :span="12">
                    <div class="szzx-fqButt">
                        <el-button type="warning" size="small" @click="start">发 起</el-button>
                    </div>
                </el-col>
            </el-row>
        </div>
        <el-tabs type="border-card">
            <el-tab-pane label="发起中心">
               <launch-center ref="lc" @updateSd="updateSd" :currentYear="currentYear"></launch-center>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>
<script>
    import launchCenter from "@/views/depAssessment/control/launchCenter/launchCenter";
    import { createPpmDepKpiLaunchCenter } from "@/api/depAssessment/launchCenter";
    import common from "@/api/common";
    export default {
        name: 'fqCenter',
        components: { launchCenter },
        data() {
            return {
                yearArr: [],
                currentYear: null,
            }
        },
        created() {
            this.init();
        },
        mounted() {
            
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
                createPpmDepKpiLaunchCenter(data).then(res => {
                    loading.close();
                    console.log('发起', res.data)
                    if (res.data.status == 200 || res.data.results == true) {
                        this.$message.success('发起成功');
                        this.$refs.lc.seaTableData(this.currentYear);
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
                this.$refs.lc.seaTableData(this.currentYear);
            },

            yearChange(){
                this.$refs.lc.seaTableData(this.currentYear);
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
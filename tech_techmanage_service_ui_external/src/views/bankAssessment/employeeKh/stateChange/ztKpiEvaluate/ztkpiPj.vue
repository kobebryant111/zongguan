<!-- kpi评价-状态修改-聚合页 -->
<template>
    <div class="kpizd-container">
        <div class="kpizd-body">
            <div class="kpizd-body-content">
                <el-tabs type="border-card">
                    <el-tab-pane label="KPI指标">
                        <ztkpi-pj-kh ref="kpisp" @zbChange="zbChange"></ztkpi-pj-kh>
                    </el-tab-pane>
                    <el-tab-pane label="知识技能提升">
                        <ztkpi-pjzhnl-promote ref="kpizhnl" @zhjnChange="zhjnChange">
                        </ztkpi-pjzhnl-promote>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { getPpmKpiPmInfo } from "@/api/hykh/myDb";
    import ztkpiPjKh from '@/views/bankAssessment/employeeKh/stateChange/ztKpiEvaluate/ztkpiPjKh';
    import ztkpiPjzhnlPromote from '@/views/bankAssessment/employeeKh/stateChange/ztKpiEvaluate/ztkpiPjzhnlPromote';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    export default {
        name: 'ztkpiPj',
        components: { ztkpiPjKh, buttonOranges, ztkpiPjzhnlPromote },
        data() {
            return {
                lcData: {},
            }
        },
        mounted() {
            console.log('路由传值考核', this.$route.params)
            if (this.$route.params.term == undefined) {
                this.$route.params.term = null
            }
            this.$nextTick(() => {
                this.getLcData(this.$route.params);
            })
        },
        methods: {
            // 获取流程信息
            getLcData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat({ eid: val.eid, tyear: val.tyear, term: val.term });
                getPpmKpiPmInfo(data).then(res => {
                    loading.close();
                    console.log('获取流程信息', res.data)
                    if (res.data.status == 200) {
                        this.lcData = res.data.results;
                        this.$refs.kpisp.getZbData(res.data.results);
                        this.$refs.kpizhnl.getZbData(res.data.results);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 刷新知识技能提升
            zbChange() {
                this.$refs.kpizhnl.getZbData(this.lcData);
            },

            // 刷新KPI指标
            zhjnChange() {
                this.$refs.kpisp.getZbData(this.lcData);
            }

        }
    }
</script>
<style scoped>
    .kpizd-body {
        margin-top: 10px;
    }

    .lastJc {
        font-family: '微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑', sans-serif;
        font-weight: 700;
        color: #414141;
    }

    .crumbOther {
        font-family: '微软雅黑', sans-serif;
        font-weight: 400;
        color: #939393;
    }
</style>
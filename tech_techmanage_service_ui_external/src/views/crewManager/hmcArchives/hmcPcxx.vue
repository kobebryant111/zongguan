<!-- 排查信息 -->
<template>
    <div class="archives-modular">
        <el-table :data="pcxxData" style="width: 100%">
            <el-table-column prop="risktype" label="排查类型" width="150" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="riskdate" label="最近排查时间" width="150" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="explainstate" label="异常情况" width="280" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="risksuggest" label="处理情况"></el-table-column>
        </el-table>
    </div>
</template>

<script>
    import { getOutRiskResults } from "@/api/integrated/hmcDa";
    import Moment from 'moment'
    export default {
        name: 'hmcPcxx',
        data() {
            return {
                pcxxData: []
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
                this.getPcxxData(val.jobId);
            }
        },
        methods: {
            // 查询排查信息
            getPcxxData(v) {
                getOutRiskResults({ jobid: v }).then(res => {
                    if (res.data.status == 200) {
                        this.pcxxData = res.data.results.filter(li => {
                            li.riskdate = Moment(li.riskdate).format('YYYY-MM-DD');
                            return li;
                        });
                    } else {
                        this.$message.error('查询排查信息失败');
                    }
                })
            },
        }
    }
</script>

<style scoped>

</style>
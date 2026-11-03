<!-- 培训成绩和历经采购项目 -->
<template>
    <div class="archives-container-pxcg">
        <div class="archives-main">
            <div class="archives-modular">
                <div class="archives-title">
                    <span class="xkg-color-black archives-title-name">考试信息</span>
                </div>
                <el-table :data="pxcgData" style="width: 100%">
                    <el-table-column prop="traintime" label="时间" width="150" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="project" label="项目" width="280" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="score" label="分数"></el-table-column>
                </el-table>
            </div>

            <!-- <div class="archives-modular">
                <div class="archives-title archives-title-second">
                    <span class="xkg-color-black archives-title-name">历经采购项目</span>
                </div>
                <el-table :data="pxcgData" style="width: 100%">
                    <el-table-column prop="" label="项目名称" width="200" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="" label="开始时间" width="150" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="" label="结束时间" width="150" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="" label="所在二级部" :show-overflow-tooltip="true"></el-table-column>
                </el-table>
            </div> -->
        </div>
    </div>
</template>

<script>
    import { getOutTrainList } from "@/api/integrated/hmcDa";
    export default {
        name: 'hmcPxcg',
        data() {
            return {
                pxcgData: [],
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
                this.getPxData(val.jobId);
            }
        },
        created() {
        },
        methods: {
            // 查询培训成绩
            getPxData(v) {
                getOutTrainList({ jobid: v }).then(res => {
                    if (res.data.status == 200) {
                      this.pxcgData = res.data.results;
                    } else {
                        this.$message.error('查询培训成绩失败');
                    }
                })
            },
        }
    }
</script>

<style scoped>
    .archives-title {
        margin-bottom: 15px;
    }

    .archives-title-name {
        font-size: 15px;
        font-weight: 600;
    }

    .archives-title-second {
        margin-top: 15px;
    }
</style>
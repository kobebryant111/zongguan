<!-- kpi评价-待办-知识技能提升 -->
<template>
    <div class="kpisp-container">
        <div class="kpisp-body">
            <div class="kpisp-body-operate">
                <div class="kpisp-body-operate-left">
                    <i class="el-icon-view" @click="showHi">
                        <span class="xkg-marginleft-10px">{{ifShow ? '显示所有内容' : '隐藏所有内容'}}</span>
                    </i>
                </div>
            </div>
            <div class="kpisp-body-content" :class="{ifWarp: !ifShow}">
                <el-table :data="khData">
                    <el-table-column label="序号" type="index" min-width="5%"></el-table-column>
                    <el-table-column label="待提升的知识技能" prop="itemdescribe" min-width="10%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="提升目标" prop="plandescribe" min-width="20%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="发展/学习活动计划" prop="workremark" min-width="20%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="完成时间" prop="finishdate" min-width="15%"></el-table-column>
                    <el-table-column label="状态" prop="itemstatusname" min-width="5%"></el-table-column>
                    <el-table-column v-if="lcData.status == '4'" key="1" label="自评" prop="evalself" min-width="20%"
                        :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column v-else key="2" label="自评" prop="halfyearevalself" min-width="20%"
                        :show-overflow-tooltip="ifShow"></el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
    import { getPpmKpiPmDetails } from "@/api/hykh/myDb";
    import common from "@/api/common";
    import Moment from 'moment';
    export default {
        name: 'dbkpiPjzhnlPromote',
        data() {
            return {
                ifShow: true,
                khData: [], //列表数据
                lcData: {},
            }
        },
        methods: {
            // 显示or隐藏内容
            showHi() {
                this.ifShow = !this.ifShow
            },

            // 获取考核信息
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '5' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.khData = res.data.results.filter(li => {
                            if (li.finishdate != undefined) {
                                li.finishdate = Moment(li.finishdate).format('YYYY-MM-DD');
                            } else {
                                li.finishdate = ''
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 初始化
            getZbData(lc) {
                this.lcData = lc;
                this.$nextTick(() => {
                    this.seaTableData();
                })
            },
        }
    }
</script>
<style scoped>
    .kpisp-body-operate {
        margin-bottom: 15px;
    }

    .kpisp-body-operate-left {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        line-height: 32px;
    }

    .el-icon-view {
        font-size: 12px;
        color: #8E8E8E;
        cursor: pointer;
    }
</style>
<style>
    .ifWarp .el-table .cell {
        white-space: pre-wrap;
    }
</style>
<!-- kpi评价-状态修改-知识技能提升 -->
<template>
    <div class="kpisp-container">
        <div class="kpisp-body">
            <div class="kpisp-body-operate">
                <div class="kpisp-body-operate-left">
                    <i class="el-icon-view" @click="showHi">
                        <span class="xkg-marginleft-10px">{{ifShow ? '显示所有内容' : '隐藏所有内容'}}</span>
                    </i>
                </div>
                <div>
                    <!-- <button-oranges buttonName="提交" size="small" :fns="approval"></button-oranges> -->
                </div>
            </div>
            <div class="kpisp-body-content" :class="{ifWarp: !ifShow}">
                <el-table :data="khData">
                    <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                    <el-table-column label="待提升的知识技能" prop="itemdescribe" min-width="15%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="提升目标" prop="plandescribe" min-width="15%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="发展/学习活动计划" prop="workremark" min-width="20%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="完成时间" prop="finishdate" min-width="15%"></el-table-column>
                    <el-table-column label="主状态" prop="statusname" min-width="10%"></el-table-column>
                    <!-- <el-table-column label="状态" prop="itemstatusname" min-width="5%"></el-table-column> -->
                    <el-table-column label="状态" min-width="15%">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.itemstatus" @change="statusChange(scope.row)">
                                <el-option v-for="(item, index) in state_options" :key="index" :label="item.dictname"
                                    :value="item.dictid" :disabled="item.dictid == 1 || item.dictid == 5 || item.dictid == 6"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
    import { getDetailItemList, backStageUpdateListStatus, backStageUpdateStatus } from "@/api/hykh/ztxg";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import common from "@/api/common";
    import Moment from 'moment';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'ztkpiPjzhnlPromote',
        components: { buttonOranges },
        data() {
            return {
                ifShow: true,
                khData: [], //列表数据
                state_options: [], //评价数据
                lcData: {},
            }
        },
        mounted() {
            this.getDic();
        },
        methods: {
            // 显示or隐藏内容
            showHi() {
                this.ifShow = !this.ifShow
            },

            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "BACKSTAGE_UPDATE_DETAIL_KPI" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //状态
                    this.state_options = data.filter(item => {
                        item.dictid = Number(item.dictid);
                        return item.dicttypeid == "BACKSTAGE_UPDATE_DETAIL_KPI";
                    })
                });
            },

            // 获取考核信息
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '5' });
                getDetailItemList(data).then(res => {
                    loading.close();
                    console.log('获取考核信息', res.data)
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

            // 提交弹窗
            approval(val) {
                this.$confirm('是否确认提交？', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.commit();
                }).catch(error => { })
            },

            // 提交
            commit() {
                let khDataBf = JSON.parse(JSON.stringify(this.khData));
                let ppmKpiPmDetailDtos = [];
                khDataBf.forEach(li => {
                    if (li.itemstatus != li.itemstatusBf) {
                        let ppmItem = {};
                        ppmItem.id = li.id;
                        ppmItem.itemstatus = li.itemstatus;
                        ppmItem.kpitype = li.kpitype;
                        ppmItem.pid = li.pid;
                        ppmKpiPmDetailDtos.push(ppmItem)
                    }
                })
                let params = {};
                params.ppmKpiPmDetailDtos = ppmKpiPmDetailDtos;
                const loading = common.loading(this);
                backStageUpdateListStatus(params).then(res => {
                    loading.close();
                    console.log('提交', res.data)
                    if (res.data.status == 200) {
                        this.seaTableData();
                        this.$message.success(res.data.message);
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 状态改变
            statusChange(v) {
                let params = {};
                params.id = v.id;
                params.itemstatus = v.itemstatus;
                params.kpitype = v.kpitype;
                params.pid = v.pid;
                let data = common.paramsFormat(params);
                const loading = common.loading(this);
                backStageUpdateStatus(data).then(res => {
                    loading.close();
                    console.log('单条提交', res.data)
                    if (res.data.status == 200) {
                        this.seaTableData();
                        this.$emit('zhjnChange');
                        this.$message.success(res.data.message);
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            }
        }
    }
</script>
<style scoped>
    .kpisp-body-operate {
        margin-bottom: 15px;
        display: flex;
        justify-content: space-between;
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
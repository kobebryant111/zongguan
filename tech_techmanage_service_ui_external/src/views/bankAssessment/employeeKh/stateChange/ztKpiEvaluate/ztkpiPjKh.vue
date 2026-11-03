<!-- 状态修改-考核项 -->
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
                    <el-table-column label="指标项" prop="itemdescribe" min-width="10%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="工作内容" prop="plandescribe" min-width="15%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="工作目标" prop="workremark" min-width="15%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="计划完成时间" prop="finishdate" min-width="15%"></el-table-column>
                    <el-table-column label="分值" prop="value" min-width="10%"></el-table-column>
                    <el-table-column label="主状态" prop="statusname" min-width="10%"></el-table-column>
                    <el-table-column label="状态" min-width="15%">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.itemstatus" @change="statusChange(scope.row)">
                                <el-option v-for="(item, index) in state_options" :key="index" :label="item.dictname"
                                    :value="item.dictid" :disabled="item.dictid == 1"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
    import { judgeStatus } from "@/api/hykh/kpiEval";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import { getDetailItemList, backStageUpdateListStatus, backStageUpdateStatus } from "@/api/hykh/ztxg";
    import common from "@/api/common";
    import Moment from 'moment';
    import { mapGetters } from 'vuex';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'ztkpiPjKh',
        components: { buttonOranges },
        data() {
            return {
                ifShow: true,
                routerData: {},
                khData: [], //列表数据
                lcData: {},
                state_options: [], //评价数据
                judgeStatus: null, //判断状态
            }
        },
        computed: {
            ...mapGetters(['userId'])
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
                    });
                });
            },

            // 获取考核信息
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '1' });
                getDetailItemList(data).then(res => {
                    loading.close();
                    console.log('获取考核信息', res.data)
                    if (res.data.status == 200) {
                        this.khData = res.data.results.filter(li => {
                            li.itemstatusBf = li.itemstatus;
                            if (li.finishdate != undefined) {
                                li.finishdate = Moment(li.finishdate).format('YYYY-MM-DD');
                            } else {
                                li.finishdate = null
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
                this.seaTableData()
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
                        ppmItem.item = li.item;
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
                params.item = v.item;
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
                        this.$emit('zbChange');
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

    .hmddialog-bottom {
        text-align: right;
    }

    .hmddialog-bottoms {
        text-align: center;
    }

    .grxx-table {
        width: 100%;
        padding: 10px;
        table-layout: fixed;
    }

    .grxx-table td {
        width: 100%;
        font-size: 15px;
        padding: 5px 0;
    }

    .grxx-table-tr {
        display: flex;
        justify-content: space-between;
    }

    .grxx-table-tr div {
        display: inline-block;
    }

    .grxx-table-tr td:nth-child(odd) {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        width: 100px;
        white-space: nowrap;
    }

    .zpContent {
        width: 100%;
    }

    .grxx-table-tr td:nth-child(even) {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        width: 261px;
    }

    .zpContent-zp {
        padding: 20px 15px 1px 15px;
        background-color: rgba(242, 242, 242, 1);
        margin-bottom: 15px;
    }

    .zpContent-zp .grxx-table {
        padding: 10px 0;
    }

    .zpContent-zp-title {
        margin: 0 0 10px 0;
        text-align: center;
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 16px;
    }

    .tdWarp {
        white-space: pre-wrap;
    }
</style>

<style>
    .el-table .pink-row {
        background: rgba(255, 218, 223, 0.972549019607843);
    }

    .form-none-marginbottom .el-form-item {
        margin-bottom: 0;
    }

    .zpContent .el-step__head.is-process {
        color: #C0C4CC;
        border-color: #C0C4CC;
    }

    .zpContent .el-step__title.is-process {
        color: #C0C4CC;
    }

    .zpContent .el-step__description.is-process {
        color: #C0C4CC;
    }

    .ifWarp .el-table .cell {
        white-space: pre-wrap;
    }
</style>
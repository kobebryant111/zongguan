<!-- 状态修改-综合能力评估 -->
<template>
    <div class="ygkhzhnlpg-container">
        <!-- <div class="ygkhzhnlpg-head">
            <button-oranges buttonName="提交" class="xkg-marginleft-10px" size="small" :fns="approval"></button-oranges>
        </div> -->
        <div class="target-body">
            <div class="yggl-body-table">
                <el-table :data="targetData">
                    <el-table-column type="index" label="序号" min-width="10%"></el-table-column>
                    <el-table-column label="评估项" min-width="25%" prop="itemdescribe" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="子评估项" min-width="30%" prop="plandescribe" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="分值" min-width="10%">
                        <template slot-scope="scope">
                            {{scope.row.weight * 100}}
                        </template>
                    </el-table-column>
                    <el-table-column label="主状态" min-width="10%">
                        <template slot-scope="scope">
                            <span v-for="item in mainstatus_options" :key="item.dictid" v-if="item.dictid == scope.row.status">{{item.dictname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" min-width="15%" prop="itemstatusname" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.itemstatus" @change="statusChange(scope.row)">
                                <el-option v-for="(item, index) in state_options" :key="index" :label="item.dictname"
                                    :value="item.dictid" :disabled="item.dictid == 2"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>

    </div>
</template>
<script>
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import common from "@/api/common";
    import { getPpmKpiPmComprehensiveAbilityEvalInfo, judgeStatus } from "@/api/hykh/kpiEval";
    import { getPpmKpiPmInfo } from "@/api/hykh/myDb";
    import Moment from 'moment';
    import { mapGetters } from 'vuex';
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPpmKpiPmDetailInfoGroup, backStageUpdateListStatus, backStageUpdateStatus } from "@/api/hykh/ztxg";
    export default {
        name: 'ztZhnlAssessment',
        components: { buttonOranges },
        data() {
            return {
                targetData: [],
                judgeStatus: null, //判断状态
                compreData: {},
                state_options: [], //状态数据
                mainstatus_options: [], //主状态
                lcData: {},
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        mounted() {
            console.log('路由传值', this.$route.params)
            this.init(this.$route.params);
            this.getDic();
            this.$nextTick(() => {
                this.getLcData(this.$route.params);
            })
        },
        methods: {
            // 初始化
            init(val) {
                console.log('初始化综合评估', val)
                this.compreData = val;
            },

            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "BACKSTAGE_UPDATE_DETAIL_OTHER,KPI_MAIN_STATUS" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //状态
                    this.state_options = data.filter(item => {
                        item.dictid = Number(item.dictid);
                        return item.dicttypeid == "BACKSTAGE_UPDATE_DETAIL_OTHER";
                    });
                    //主状态
                    this.mainstatus_options = data.filter(item => {
                        if (item.dicttypeid == "KPI_MAIN_STATUS") {
                            return item
                        }
                    });
                });
            },

            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '2' });
                queryPpmKpiPmDetailInfoGroup(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.targetData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取流程信息
            getLcData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat({ eid: val.eid, tyear: val.tyear, term: val.term });
                getPpmKpiPmInfo(data).then(res => {
                    loading.close();
                    console.log('获取流程信息', res.data)
                    if (res.data.status == 200) {
                        this.lcData = res.data.results;
                        this.seaTableData()
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
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
                let khDataBf = JSON.parse(JSON.stringify(this.targetData));
                let ppmKpiPmDetailDtos = [];
                khDataBf.forEach(li => {
                    if (li.itemstatus != li.itemstatusBf) {
                        let ppmItem = {};
                        ppmItem.item = li.item;
                        ppmItem.itemstatus = li.itemstatus;
                        ppmItem.kpitype = '2';
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
                params.item = v.item;
                params.itemstatus = v.itemstatus;
                params.kpitype = '2';
                params.pid = v.pid;
                let data = common.paramsFormat(params);
                const loading = common.loading(this);
                backStageUpdateStatus(data).then(res => {
                    loading.close();
                    console.log('单条提交', res.data)
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
            }

        }
    }
</script>
<style scoped>
    .ygkhzhnlpg-head {
        text-align: right
    }

    .target-body {
        margin-top: 10px;
        padding: 15px;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 10px;
    }
</style>
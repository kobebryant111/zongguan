<!-- 我的考核-履职评估更多详情 -->
<template>
    <div class="mykhzhnlpg-container">
        <div class="mykhlzpg-bread">
            <div class="mykhlzpg-bread-butt">
                <el-button size="small" icon="el-icon-arrow-left" @click="lastPage">返回</el-button>
            </div>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-for="(item,index) in routesMeta" :key="index">
                    <span :class="[routesMeta.length-1 == index ? 'lastJc' : 'crumbOther']">{{item}}</span>
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="mykhzhnlpg-head">
            <div class="mykhzhnlpg-head-left">{{compreData.tyear}}{{compreData.term}}年履职评估</div>
            <div class="mykhzhnlpg-head-right">得分<span>{{compreData.performanceevaluationscore
                    != undefined ?
                    compreData.performanceevaluationscore : 0}}</span>分</div>
        </div>
        <div class="target-body">
            <div class="yggl-body-table">
                <el-table :data="targetData">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="考核指标" width="260" prop="itemdescribe" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="考核主体" width="150">
                        <template slot-scope="scope">
                            <span v-for="item in main_options" :key="item.dictid" v-if="item.dictid == scope.row.mainpart">{{item.dictname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="考核项" width="100" prop="evalitems"></el-table-column>
                    <el-table-column label="分值" width="200">
                        <template slot-scope="scope">
                            {{scope.row.weight * 100}}%
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" width="150" prop="itemstatusname" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-for="item in itemstatus_options" :key="item.dictid" v-if="item.dictid == scope.row.itemstatus">{{item.dictname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="初评分" prop="firstevalavgscore" width="100"></el-table-column>
                    <el-table-column label="终评分" prop="secondevalavgscore" width="100"></el-table-column>
                    <el-table-column label="操作" width="140">
                        <template slot-scope="scope">
                            <button-commons buttonName="查 看" size="mini" :parentData="scope.row" :fns="corrSingle"></button-commons>
                        </template>
                    </el-table-column>
                    <el-table-column></el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 评分 -->
        <el-dialog title="履职评估项" :visible.sync="ifShowXgSingDialog">
            <div class="xgContent">
                <div>
                    <el-form label-position="left" label-width="80px">
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <el-form-item label="评估项">
                                            <el-input type="textarea" autosize placeholder="评估项" v-model="updateSingData.itemdescribe"
                                                disabled>
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <el-form-item label="分值">
                                            <el-input placeholder="分值" v-model="updateSingData.weight" disabled>
                                                <!-- <template slot="append">%</template> -->
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-form>
                    <el-form :model="updateSingData" ref="formXgSing">
                        <div class="hmd-row-block zhnlAss-block">
                            <el-row :gutter="50">
                                <el-col :span="24">
                                    <div>
                                        <el-table :data="updateSingData.list" style="width: 100%" border>
                                            <el-table-column type="index" label="序号" width="55"></el-table-column>
                                            <el-table-column label="行为描述" width="320" prop="plandescribe"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <!-- <el-table-column label="行为标准" width="200" prop="workremark"
                                                :show-overflow-tooltip="true"></el-table-column> -->
                                            <el-table-column label="初评" width="150">
                                                <template slot-scope="scope">
                                                    <el-rate :max='maxScore' v-model="scope.row.firstevalscore"
                                                        disabled></el-rate>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label="终评" width="150">
                                                <template slot-scope="scope">
                                                    <el-rate :max='maxScore' v-model="scope.row.secondevalscore"
                                                        disabled></el-rate>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-form>
                </div>
                <div class="hmddialog-bottom">
                    <el-button type="primary" @click="sureXgSing">关 闭</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonCommons from '@/components/eleButton/buttonCommons';
    import common from "@/api/common";
    import { queryComprehensiveAbilityEvalInfo, queryPpmKpiPmDetailEvalGroup, submitComprehensiveAbilityEval } from "@/api/hykh/kpiEval";
    import { getPpmKpiPmPostEvaluationEvalInfo } from "@/api/hykh/lzpgApi";
    import Moment from 'moment';
    import { mapGetters } from 'vuex';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'mykhLzpgDetail',
        components: { buttonCommons },
        data() {
            return {
                centerData: [],
                targetData: [],
                ifShowXgSingDialog: false, //修改单人弹窗
                updateSingData: {}, //修改单人数据
                pid: null,
                maxScore: 5,
                compreData: {},
                routesMeta: [],
                itemstatus_options: [], //状态数据
                main_options: [], //考核主体
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "myKh") {
                    vm.routesMeta = ['我的考核', '履职评估']
                } else if (from.name == "ygkhDetail") {
                    vm.routesMeta = ['员工考核', to.params.eidname, '履职评估']
                }
            })
        },
        mounted() {
            console.log('路由传值', this.$route.params)
            this.init(this.$route.params);
            this.getDic();
        },
        methods: {
            // 初始化
            init(val) {
                console.log('初始化综合评估', val)
                if (val.term == undefined) {
                    val.term = ''
                }
                this.compreData = val;
                this.seaTableData(val)
            },

            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_ITEM_STATUS,KPI_MAIN" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //状态
                    this.itemstatus_options = data.filter(item => {
                        return item.dicttypeid == "KPI_ITEM_STATUS";
                    });
                    //考核主体
                    this.main_options = data.filter(item => {
                        return item.dicttypeid == "KPI_MAIN";
                    });
                });
            },

            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.compreData.id, kpitype: '3' });
                getPpmKpiPmPostEvaluationEvalInfo(data).then(res => {
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

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
                this.searchRowDetail(val.item);
            },

            // 查询修改行详情
            searchRowDetail(item) {
                const loading = common.loading(this);
                queryComprehensiveAbilityEvalInfo({ item: item, pid: this.compreData.id }).then(res => {
                    loading.close();
                    console.log("查询修改行详情", res.data)
                    if (res.data.status == 200) {
                        this.updateSingData = res.data.results;
                        this.updateSingData.weight = res.data.results.weight * 100;
                        this.updateSingData.list = this.updateSingData.list;
                        this.updateSingData.list.forEach(li => {
                            if (li.firstevalscore != undefined && li.firstevalscore != null) {
                                // li.firstevalscore = Number(li.firstevalscore) / 20
                                li.firstevalscore = common.accDiv(Number(li.firstevalscore), 20)
                            } else {
                                li.firstevalscore = null
                            }
                            if (li.secondevalscore != undefined && li.secondevalscore != null) {
                                // li.secondevalscore = Number(li.secondevalscore) / 20
                                li.secondevalscore = common.accDiv(Number(li.secondevalscore), 20)
                            } else {
                                li.secondevalscore = null
                            }
                        })
                        console.log('测试分数', this.updateSingData.list)
                        this.ifShowXgSingDialog = true;
                    } else {
                        this.$message.error('查询失败')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 修改单人确定
            sureXgSing() {
                this.ifShowXgSingDialog = false;
            },

            // 返回
            lastPage() {
                this.$router.back()
            }
        }
    }
</script>
<style scoped>
    .mykhzhnlpg-container {
        padding: 0 15px;
    }

    .mykhzhnlpg-head {
        display: flex;
        justify-content: space-between;
        background-color: rgba(255, 255, 255, 1);
        border-radius: 10px;
        padding: 20px 15px;
    }

    .mykhzhnlpg-head-left {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-size: 24px;
        line-height: 49px;
        color: #000333;
    }

    .mykhzhnlpg-head-right {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-size: 14px;
        font-weight: 400;
        color: #333333;
    }

    .mykhzhnlpg-head-right span {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-size: 40px;
        font-weight: 400;
        color: #333333;
        padding: 0 8px;
    }

    .target-body {
        margin-top: 10px;
        padding: 15px;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 10px;
    }

    .hmddialog-bottom {
        text-align: center;
        margin-top: 15px;
    }

    .hmd-row-block p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        margin: 8px 0;
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

    .mykhlzpg-bread {
        height: 32px;
        margin: 5px 0;
    }

    .mykhlzpg-bread-butt {
        height: 32px;
        line-height: 32px;
        float: left;
        margin-right: 10px;
    }
</style>

<style>
    .zhnlAss-block .el-rate {
        height: 100%;
    }

    .zhnlAss-block .el-form-item {
        margin-bottom: 16px;
    }

    .mykhlzpg-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
    }
</style>
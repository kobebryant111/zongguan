<!-- 待办-履职评估-下级评上级 -->
<template>
    <div class="lzpgshang-container">
        <div class="kh-back-bread">
            <div class="kh-back-bread-butt">
                <el-button size="small" icon="el-icon-arrow-left" @click="lastPage">返回</el-button>
            </div>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-for="(item,index) in routesMeta" :key="index">
                    <span :class="[routesMeta.length-1 == index ? 'lastJc' : 'crumbOther']">{{item}}</span>
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="target-body">
            <div class="target-body-head">
                <button-oranges buttonName="提交" class="xkg-marginleft-10px" size="small" :fns="approval"></button-oranges>
            </div>
            <div class="yggl-body-table lzpgxia-form">
                <el-form :model="tableForm" ref="formXgSing">
                    <el-table :data="tableForm.targetData">
                        <el-table-column type="index" label="序号" width="55"></el-table-column>
                        <el-table-column label="考核项" width="160" prop="itemdescribe" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="行为描述" width="400" prop="plandescribe" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="考核主体" width="150" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span v-for="item in main_options" :key="item.dictid" v-if="item.dictid == scope.row.mainpart">{{item.dictname}}</span>
                            </template>
                        </el-table-column>
                        <!-- <el-table-column label="分值" width="100">
                            <template slot-scope="scope">
                                {{scope.row.weight * 100}}%
                            </template>
                        </el-table-column> -->
                        <el-table-column label="状态" width="150" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span v-for="item in itemstatus_options" :key="item.dictid" v-if="item.dictid == scope.row.itemstatus">{{item.dictname}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="评分" width="200">
                            <template slot-scope="scope">
                                <el-form-item :prop="`targetData.${scope.$index}.secondevalscore`" :rules="rules.secondevalscore">
                                    <el-rate :max='maxScore' v-model="scope.row.secondevalscore" @change="zpScoreChange(scope.row)"
                                        :disabld="scope.row.itemstatus != 6"></el-rate>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column></el-table-column>
                    </el-table>
                </el-form>
            </div>
        </div>
    </div>
</template>
<script>
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import common from "@/api/common";
    import { queryPpmKpiPostevaluationInfos, updateSinglePpmKpiPostevaluation, submitPpmKpiPostevaluation } from "@/api/hykh/kpiEval";
    import { mapGetters } from 'vuex';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'lzpgXia',
        components: { buttonOranges },
        data() {
            var checkPf = (rule, value, callback) => {
                if (value == 0 || value == undefined) {
                    return callback(new Error('请选择评分'))
                } else {
                    callback()
                }
            }
            return {
                tableForm: {
                    targetData: []
                },
                maxScore: 5,
                compreData: {},
                rules: {
                    secondevalscore: [
                        { validator: checkPf, trigger: 'change' }
                    ],
                },
                itemstatus_options: [], //状态数据
                main_options: [], //考核主体
                lcData: {},
                judgeStatus: null,
                routesMeta: [],
                routerData: {}
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "lzpgListXia") {
                    vm.routesMeta = ['我的待办', '经理' + vm.routerData.tyear + '年度考核', vm.routerData.eidname + '的履职评估详情']
                }
            })
        },
        computed: {
            ...mapGetters(['userId'])
        },
        mounted() {
            console.log('路由传值', this.$route.params)
            this.routerData = this.$route.params;
            this.init(this.$route.params);
            this.getDic();
        },
        methods: {
            // 初始化
            init(val) {
                console.log('初始化综合评估', val)
                this.compreData = val;
                this.seaTableData();
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
                let data = common.paramsFormat({ eid: this.compreData.eid, tyear: this.compreData.tyear, kpitype: '3', check: this.userId, mainpart: 2 });
                queryPpmKpiPostevaluationInfos(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.tableForm.targetData = res.data.results;
                        this.tableForm.targetData.forEach(li => {
                            if (li.secondevalscore != undefined && li.secondevalscore != null) {
                                li.secondevalscore = Number(li.secondevalscore) / 20
                            } else {
                                li.secondevalscore = null
                            }
                        })
                        // if (res.data.results.length != 0) {
                        //     this.judgeStatus = res.data.results[0].itemstatus;
                        // }
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 终评分数改变
            zpScoreChange(row) {
                console.log("终评分数改变", row)
                const loading = common.loading(this);
                let params = {};
                params.id = row.id;
                params.secondevalscore = common.accMul(row.secondevalscore, 20);
                let data = common.paramsFormat(params);
                updateSinglePpmKpiPostevaluation(data).then(res => {
                    loading.close();
                    console.log("保存", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('保存成功');
                        this.$nextTick(() => {
                            this.seaTableData();
                        })
                    } else {
                        this.$message.error('保存失败')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 提交弹窗
            approval(val) {
                this.$refs['formXgSing'].validate((valid) => {
                    if (valid) {
                        this.$confirm('是否确认提交？', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            this.commit();
                        }).catch(error => { })
                    } else {
                        this.$message.error('请填写必填项')
                        return false;
                    }
                })
            },

            // 提交
            commit() {
                let data = common.paramsFormat({ eid: this.compreData.eid, tyear: this.compreData.tyear, itemstatus: 6, kpitype: '3', secondeval: this.userId, mainpart: 2 })
                const loading = common.loading(this);
                submitPpmKpiPostevaluation(data).then(res => {
                    loading.close();
                    console.log('提交', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('提交成功');
                        this.$router.back();
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 返回
            lastPage() {
                this.$router.back()
            }

        }
    }
</script>
<style scoped>
    .lzpgshang-container {
        padding: 8px 15px 15px 15px;
    }

    .dblzpg-head {
        display: flex;
        justify-content: space-between;
        background-color: rgba(255, 255, 255, 1);
        border-radius: 10px;
        padding: 20px 15px;
    }

    .lzpg-head-left {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-size: 24px;
        line-height: 49px;
        color: #000333;
    }

    .lzpg-head-right {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-size: 14px;
        font-weight: 400;
        color: #333333;
    }

    .lzpg-head-right span {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-size: 40px;
        font-weight: 400;
        color: #333333;
        padding: 0 8px;
    }

    .target-body-head {
        text-align: right
    }

    .target-body {
        padding: 15px;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 10px;
    }

    .kh-back-bread {
        height: 32px;
        margin-bottom: 8px;
    }

    .kh-back-bread-butt {
        height: 32px;
        line-height: 32px;
        float: left;
        margin-right: 10px;
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

<style>
    .zhnlAss-block .el-rate {
        height: 100%;
    }

    .lzpgxia-form .el-form-item {
        margin-bottom: 16px;
    }

    .kh-back-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
    }
</style>
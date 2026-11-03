<!-- 待办-履职评估-上级评下级 -->
<template>
    <div class="lzpgshang-container">
        <!-- <div class="dblzpg-head">
            <div class="lzpg-head-left">履职评估</div>
            <div class="lzpg-head-right">得分<span>0</span>分</div>
        </div> -->
        <div class="target-body">
            <div class="target-body-head">
                <button-oranges buttonName="提交" class="xkg-marginleft-10px" size="small" :fns="approval"></button-oranges>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :row-class-name="tableRowClassName">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="考核项" width="260" prop="itemdescribe" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="考核主体" width="250" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-for="item in main_options" :key="item.dictid" v-if="item.dictid == scope.row.mainpart">{{item.dictname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="分值" width="200">
                        <template slot-scope="scope">
                            {{scope.row.weight * 100}}%
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" width="150" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-for="item in itemstatus_options" :key="item.dictid" v-if="item.dictid == scope.row.itemstatus">{{item.dictname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="初评分" prop="firstevalavgscore" width="100"></el-table-column>
                    <el-table-column label="终评分" prop="secondevalavgscore" width="100"></el-table-column>
                    <el-table-column label="操作" width="140">
                        <template slot-scope="scope">
                            <button-oranges buttonName="评 分" size="mini" :parentData="scope.row" :fns="corrSingle"></button-oranges>
                        </template>
                    </el-table-column>
                    <el-table-column></el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 评分 -->
        <el-dialog title="履职评估项" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <div>
                    <el-form label-position="left" label-width="80px">
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <el-form-item label="评估项">
                                            <el-input type="textarea" autosize v-model="updateSingData.itemdescribe"
                                                disabled>
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <el-form-item label="考核主体">
                                            <el-input v-model="updateSingData.mainpartname" disabled></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <el-form-item label="分值">
                                            <el-input v-model="updateSingData.weight" disabled>
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
                            <el-table :data="updateSingData.list" style="width: 100%" border>
                                <el-table-column type="index" label="序号" width="55"></el-table-column>
                                <el-table-column label="行为描述" width="320" prop="plandescribe" :show-overflow-tooltip="true"></el-table-column>
                                <!-- <el-table-column label="行为标准" width="200" prop="workremark" :show-overflow-tooltip="true"></el-table-column> -->
                                <el-table-column label="初评" width="150">
                                    <template slot-scope="scope">
                                        <el-form-item v-if="updateSingData.firsteval == userId" :prop="`list.${scope.$index}.firstevalscore`"
                                            :rules="rules.firstevalscore">
                                            <el-rate :max='maxScore' v-model="scope.row.firstevalscore" @change="cpScoreChange(scope.row)"></el-rate>
                                        </el-form-item>
                                        <el-form-item v-else>
                                            <el-rate :max='maxScore' v-model="scope.row.firstevalscore" disabled></el-rate>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                                <el-table-column label="终评" width="150">
                                    <template slot-scope="scope">
                                        <el-form-item v-if="updateSingData.secondeval == userId" :prop="`list.${scope.$index}.secondevalscore`"
                                            :rules="rules.secondevalscore">
                                            <el-rate :max='maxScore' v-model="scope.row.secondevalscore" @change="zpScoreChange(scope.row)"></el-rate>
                                        </el-form-item>
                                        <el-form-item v-else>
                                            <el-rate :max='maxScore' v-model="scope.row.secondevalscore" disabled></el-rate>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-form>
                </div>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelXgSing">取 消</el-button>
                    <el-button type="primary" @click="saveXgSing">保 存</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import common from "@/api/common";
    import { queryPostEvaluationEvalEvalInfo, getPpmKpiPmPostEvaluationEvalInfo, submitTotalPostEvaluationEval, savePostEvaluationEval } from "@/api/hykh/kpiEval";
    import { getPpmKpiPmInfo } from "@/api/hykh/myDb";
    import Moment from 'moment';
    import { mapGetters } from 'vuex';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'lzpgShang',
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
                centerData: [],
                targetData: [],
                ifShowXgSingDialog: false, //修改单人弹窗
                updateSingData: {}, //修改单人数据
                pid: null,
                maxScore: 5,
                compreData: {},
                rules: {
                    firstevalscore: [
                        { validator: checkPf, trigger: 'change' }
                    ],
                    secondevalscore: [
                        { validator: checkPf, trigger: 'change' }
                    ],
                },
                itemstatus_options: [], //状态数据
                main_options: [], //考核主体
                lcData: {},
                judgeStatus: null
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
            // 设置表格行颜色
            tableRowClassName(row, rowIndex) {
                if (row.row.itemstatus == 5 && row.row.firstevalavgscore != undefined && row.row.firstevalavgscore != null && row.row.firstevalavgscore != 0) {
                    return 'pink-row'
                } else if (row.row.itemstatus == 6 && row.row.secondevalavgscore != undefined && row.row.secondevalavgscore != null && row.row.secondevalavgscore != 0) {
                    return 'pink-row'
                }
                return ''
            },

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
                let data = common.paramsFormat({ pid: this.compreData.id, kpitype: '3', check: this.userId, mainpart: 1 });
                getPpmKpiPmPostEvaluationEvalInfo(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.targetData = res.data.results;
                        if (res.data.results.length != 0) {
                            this.judgeStatus = res.data.results[0].itemstatus;
                        }
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
                this.searchRowDetail(val);
            },

            // 查询修改行详情
            searchRowDetail(val) {
                const loading = common.loading(this);
                queryPostEvaluationEvalEvalInfo({ item: val.item, pid: this.compreData.id, itemstatus: val.itemstatus }).then(res => {
                    loading.close();
                    console.log("查询修改行详情", res.data)
                    if (res.data.status == 200) {
                        this.updateSingData = res.data.results;
                        this.updateSingData.weight = res.data.results.weight * 100;
                        this.updateSingData.list = this.updateSingData.list;
                        this.updateSingData.list.forEach(li => {
                            if (li.firstevalscore != undefined && li.firstevalscore != null) {
                                li.firstevalscore = Number(li.firstevalscore) / 20
                            } else {
                                li.firstevalscore = null
                            }
                            if (li.secondevalscore != undefined && li.secondevalscore != null) {
                                li.secondevalscore = Number(li.secondevalscore) / 20
                            } else {
                                li.secondevalscore = null
                            }
                        })
                        this.ifShowXgSingDialog = true;
                    } else {
                        this.$message.error('查询失败')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 提交单人确定
            sureXgSing() {
                console.log("修改单人确定", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        let pfData = JSON.parse(JSON.stringify(this.updateSingData));
                        if (this.userId == pfData.firsteval) {
                            pfData.list.forEach(li => {
                                // li.tscore = li.firstevalscore = common.accMul(li.firstevalscore, 20)
                                if (li.firstevalscore != undefined && li.firstevalscore != 0) {
                                    li.tscore = li.firstevalscore = common.accMul(li.firstevalscore, 20)
                                } else {
                                    li.tscore = li.firstevalscore = null
                                }
                                if (li.secondevalscore != undefined && li.secondevalscore != 0) {
                                    li.secondevalscore = common.accMul(li.secondevalscore, 20)
                                } else {
                                    li.secondevalscore = null
                                }
                            })
                        } else if (this.userId == pfData.secondeval) {
                            pfData.list.forEach(li => {
                                // li.tscore = li.secondevalscore = common.accMul(li.secondevalscore, 20)
                                if (li.secondevalscore != undefined && li.secondevalscore != 0) {
                                    li.tscore = li.secondevalscore = common.accMul(li.secondevalscore, 20)
                                } else {
                                    li.tscore = li.secondevalscore = null
                                }
                                if (li.firstevalscore != undefined && li.firstevalscore != 0) {
                                    li.firstevalscore = common.accMul(li.firstevalscore, 20)
                                } else {
                                    li.firstevalscore = null
                                }
                            })
                        } else {
                            this.$message.error('您不是初评人或终评人，不能评分');
                            return
                        }
                        const loading = common.loading(this);
                        let params = {};
                        params = pfData;
                        submitTotalPostEvaluationEval(params).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$emit('getTongJi', 'zhnlpg');
                                this.$message.success('更新成功');
                                this.$nextTick(() => {
                                    this.seaTableData();
                                })
                                this.ifShowXgSingDialog = false;
                            } else {
                                this.$message.error('更新失败')
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    } else {
                        this.$message.error('请填写必填项');
                        return false;
                    }
                });
            },

            // 修改单人取消
            cancelXgSing() {
                this.ifShowXgSingDialog = false;
            },

            // 修改单人弹窗关闭回调
            xgSingClose() {
                this.$refs["formXgSing"].clearValidate();
            },

            // 初评分数改变
            cpScoreChange(row) {
                console.log("初评分数改变", row)
                // row.tscore = row.firstevalscore;
                this.updateSingData.list.splice(0, 0);
            },

            // 终评分数改变
            zpScoreChange(row) {
                console.log("终评分数改变", row)
                // row.tscore = row.secondevalscore;
                this.updateSingData.list.splice(0, 0);
            },

            // 保存
            saveXgSing() {
                console.log('保存', this.updateSingData)
                let pfData = JSON.parse(JSON.stringify(this.updateSingData));
                if (this.userId == pfData.firsteval) {
                    pfData.list.forEach(li => {
                        if (li.firstevalscore != undefined && li.firstevalscore != 0) {
                            li.tscore = li.firstevalscore = common.accMul(li.firstevalscore, 20)
                        } else {
                            li.tscore = li.firstevalscore = null
                        }
                        if (li.secondevalscore != undefined && li.secondevalscore != 0) {
                            li.secondevalscore = common.accMul(li.secondevalscore, 20)
                        } else {
                            li.secondevalscore = null
                        }
                    })
                } else if (this.userId == pfData.secondeval) {
                    pfData.list.forEach(li => {
                        if (li.secondevalscore != undefined && li.secondevalscore != 0) {
                            li.tscore = li.secondevalscore = common.accMul(li.secondevalscore, 20)
                        } else {
                            li.tscore = li.secondevalscore = null
                        }
                        if (li.firstevalscore != undefined && li.firstevalscore != 0) {
                            li.firstevalscore = common.accMul(li.firstevalscore, 20)
                        } else {
                            li.firstevalscore = null
                        }
                    })
                } else {
                    this.$message.error('您不是初评人或终评人，不能评分');
                    return
                }
                const loading = common.loading(this);
                let params = {};
                params = pfData;
                savePostEvaluationEval(params).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$message.success('保存成功');
                        this.$nextTick(() => {
                            this.seaTableData();
                        })
                        this.ifShowXgSingDialog = false;
                    } else {
                        this.$message.error('保存失败')
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
                let data = null;
                if (this.judgeStatus == 5) {
                    data = common.paramsFormat({ pid: this.lcData.id, itemstatus: this.judgeStatus, kpitype: '3', firsteval: this.userId, mainpart: 1 })
                } else if (this.judgeStatus == 6) {
                    data = common.paramsFormat({ pid: this.lcData.id, itemstatus: this.judgeStatus, kpitype: '3', secondeval: this.userId, mainpart: 1 })
                } else {
                    return
                }
                const loading = common.loading(this);
                submitTotalPostEvaluationEval(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$emit('tongji', 'lzpg');
                        this.$message.success('提交成功');
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

        }
    }
</script>
<style scoped>
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
</style>

<style>
    .zhnlAss-block .el-rate {
        height: 100%;
    }

    .zhnlAss-block .el-form-item {
        margin-bottom: 16px;
    }

    .el-table .pink-row {
        background: rgba(255, 218, 223, 0.972549019607843);
    }
</style>
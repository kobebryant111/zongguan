<!-- 评级设置中心 -->
<template>
    <div class="pjsz-container">
        <div class="pjsz-body">
            <h3 class="pjsz-body-title">评级设置中心</h3>
            <div class="pjsz-body-operate">
                <el-button type="warning" size="small" @click="addTo">添 加</el-button>
            </div>
            <div class="pjsz-body-cont">
                <el-table :data="pjszData">
                    <!-- <el-table-column label="序号" prop="sortno" width="55"></el-table-column> -->
                    <el-table-column label="排序" prop="sort" min-width="15%"></el-table-column>
                    <el-table-column label="级别" prop="dictname" min-width="20%"></el-table-column>
                    <el-table-column label="人数百分比" min-width="20%">
                        <template slot-scope="scope">
                            <span>{{scope.row.dictid}}%</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="退位/进位" prop="prule" min-width="20%">
                        <template slot-scope="scope">
                            <span v-for="item in jt_options" v-if="item.dictid == scope.row.prule">{{item.dictname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" min-width="25%">
                        <template slot-scope="scope">
                            <div>
                                <button-deepskyblue size="mini" buttonName="修 改" :parentData="scope.row" :fns="corrSingle"></button-deepskyblue>
                                <button-oranges buttonName="删 除" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="delSingle"></button-oranges>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 添加 -->
        <el-dialog title="评级配分设置" :visible.sync="ifShowTjDialog" @close="addClose">
            <div class="xgContent">
                <el-form :model="updateTjData" ref="formAdd">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>评级 *</p>
                                        <el-form-item prop="dictname" :rules="[{ required: true, message: '请填写评级', trigger: 'blur'}]">
                                            <el-input type="textarea" autosize placeholder="评级" v-model="updateTjData.dictname"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>人数百分比 *</p>
                                        <el-form-item prop="dictid" :rules="[{ required: true, message: '请填写人数百分比', trigger: 'blur'}]">
                                            <el-input placeholder="人数百分比" v-model="updateTjData.dictid">
                                                <!-- <template slot="append">%</template> -->
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>退位/进位 *</p>
                                        <el-form-item prop="prule" :rules="[{ required: true, message: '请填写退位/进位', trigger: 'blur'}]">
                                            <el-radio-group v-model="updateTjData.prule">
                                                <el-radio v-for="item in jt_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                            </el-radio-group>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>排序 *</p>
                                        <el-form-item prop="sort" :rules="[{ required: true, message: '请填写序号', trigger: 'blur'}]">
                                            <el-input type="textarea" autosize placeholder="排序" v-model="updateTjData.sort"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelAdd"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" size="mini" :fns="sureAdd"></button-orange>
                </div>
            </div>
        </el-dialog>
        <!-- 修改单人 -->
        <el-dialog title="评价配分设置" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <el-form :model="updateSingData" ref="formXgSing">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>评价 *</p>
                                        <el-form-item prop="dictname" :rules="[{ required: true, message: '请填写评价', trigger: 'blur'}]">
                                            <el-input type="textarea" autosize placeholder="评价" v-model="updateSingData.dictname"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>得分系数 *</p>
                                        <el-form-item prop="dictid" :rules="[{ required: true, message: '请填写得分系数', trigger: 'blur'}]">
                                            <el-input placeholder="得分系数" v-model="updateSingData.dictid" :disabled="true">
                                                <!-- <template slot="append">%</template> -->
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>退位/进位 *</p>
                                        <el-form-item prop="prule" :rules="[{ required: true, message: '请填写退位/进位', trigger: 'blur'}]">
                                            <el-radio-group v-model="updateSingData.prule">
                                                <el-radio v-for="item in jt_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                            </el-radio-group>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>排序 *</p>
                                        <el-form-item prop="sort" :rules="[{ required: true, message: '请填写序号', trigger: 'blur'}]">
                                            <el-input type="textarea" autosize placeholder="排序" v-model="updateSingData.sort"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelXgSing"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" size="mini" :fns="sureXgSing"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { queryLists, addPpmKpiParams, updateRateGrade, delRateGradeSet } from "@/api/hykh/rgSet";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'pingjiSet',
        components: { buttonOranges, buttonDeepskyblue, buttonCommon, buttonOrange },
        data() {
            return {
                pjszData: [],
                ifShowXgSingDialog: false, //修改单人弹窗
                ifShowTjDialog: false, //是否显示添加弹窗
                updateSingData: {}, //修改单人数据
                updateTjData: {
                    dictid: '',
                    dictname: '',
                    prule: '',
                    sort: ''
                }, //添加数据
                pj_options: [],
                jt_options: [],
            }
        },
        mounted() {
            this.seaTableData();
            this.getDic();
        },
        methods: {
            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_PARAMS_RULES" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //进位/退位
                    this.jt_options = data.filter(item => {
                        return item.dicttypeid == "KPI_PARAMS_RULES";
                    });
                    return data;
                });
            },

            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ dicttype: 'KPI_LEVEL_RATE' });
                queryLists(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.pjszData = res.data.results.filter(li => {
                            // li.dictid = Number(li.dictid) * 100;
                            li.dictid = common.accMul(li.dictid, 100);
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 删除（单）
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    delRateGradeSet({ dicttype: 'KPI_LEVEL_RATE', id: val.id }).then(res => {
                        loading.close();
                        console.log("删除", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.seaTableData();
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
                this.updateSingData = JSON.parse(JSON.stringify(val));
                this.ifShowXgSingDialog = true;
            },

            // 修改单人确定
            sureXgSing() {
                console.log("修改单人确定", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.id = this.updateSingData.id;
                        // params.dictid = this.updateSingData.dictid / 100;
                        params.dictname = this.updateSingData.dictname;
                        params.prule = this.updateSingData.prule;
                        params.sort = this.updateSingData.sort;
                        params.ptype = 'level';
                        params.dicttype = "KPI_LEVEL_RATE";
                        let data = common.paramsFormat(params);
                        updateRateGrade(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.ifShowXgSingDialog = false;
                                this.seaTableData();
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

            // 添加
            addTo() {
                this.ifShowTjDialog = true;
            },

            // 添加确定
            sureAdd() {
                console.log("添加确定", this.updateTjData)
                this.$refs["formAdd"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.dictid = this.updateTjData.dictid / 100;
                        params.dictname = this.updateTjData.dictname;
                        params.prule = this.updateTjData.prule;
                        params.sort = this.updateTjData.sort;
                        params.ptype = 'level';
                        params.dicttype = "KPI_LEVEL_RATE";
                        let data = common.paramsFormat(params);
                        addPpmKpiParams(data).then(res => {
                            loading.close();
                            console.log("添加确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowTjDialog = false;
                                this.seaTableData();
                            } else {
                                this.$message.error('添加失败')
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

            // 添加取消
            cancelAdd() {
                this.ifShowTjDialog = false;
            },

            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formAdd"].resetFields();
            },
        }
    }
</script>
<style scoped>
    .pjsz-head {
        padding-bottom: 15px;
    }

    .pjsz-body {
        background: #FFFFFF;
    }

    .pjsz-body-title {
        font-size: 16px;
    }

    .pjsz-body-operate {
        text-align: right;
        margin-bottom: 10px;
    }

    .hmddialog-bottom {
        text-align: right;
    }

    .hmd-row-block p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
    }
</style>
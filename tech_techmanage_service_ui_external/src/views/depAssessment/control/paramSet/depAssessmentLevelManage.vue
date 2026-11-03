<!-- 评价设置中心 -->
<template>
    <div class="pjsz-container">
        <div class="pjsz-body">
            <h3 class="pjsz-body-title">档位评分设置中心</h3>
            <div class="pjsz-body-operate">
                <el-button type="warning" size="small" @click="addTo">添 加</el-button>
            </div>
            <div class="pjsz-body-cont">
                <el-table :data="pjszData">
                    <el-table-column label="序号" prop="sortno" min-width="15%"></el-table-column>
                    <el-table-column label="评价" prop="dictname" min-width="40%"></el-table-column>
                    <el-table-column label="得分系数" min-width="20%">
                        <template slot-scope="scope">
                            <span>{{scope.row.dictid}}</span>
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
        <el-dialog title="档位评分设置" :visible.sync="ifShowTjDialog" @close="addClose">
            <div class="xgContent">
                <el-form :model="updateTjData" ref="formAdd">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="24">
                                    <div>
                                        <p>分档描述 *</p>
                                        <el-form-item prop="dictname" :rules="[{ required: true, message: '请填写评价', trigger: 'blur'}]">
                                            <el-input type="textarea" autosize placeholder="分档描述" maxlength="10" v-model="updateTjData.dictname"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>系数 *</p>
                                        <el-form-item prop="dictid" :rules="[{ required: true, message: '请填写系数', trigger: 'blur'}]">
                                            <el-input type="number" placeholder="系数" maxlength="5" v-model="updateTjData.dictid"
                                            @input="(val) => {updateTjData.dictid = val < 0 || val > 50 ? 0 : val}">
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>排序 *</p>
                                        <el-form-item prop="sortno" :rules="[{ required: true, message: '请填写序号', trigger: 'blur'}]">
                                            <el-input type="number" autosize max placeholder="排序" 
                                            v-model="updateTjData.sortno" @input="(val) => {updateTjData.sortno = val.replace('-', '')}"></el-input>
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
        <!-- 修改单个 -->
        <el-dialog title="档位评分设置" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <el-form :model="updateSingData" ref="formXgSing">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="24">
                                    <div>
                                        <p>分档描述 *</p>
                                        <el-form-item prop="dictname" :rules="[{ required: true, message: '请填写分档描述', trigger: 'blur'}]">
                                            <el-input type="textarea" autosize placeholder="分档描述" maxlength="10" v-model="updateSingData.dictname"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>系数 *</p>
                                        <el-form-item prop="dictid" :rules="[{ required: true, message: '请填写得分系数', trigger: 'blur'}]">
                                            <el-input type="number" placeholder="得分系数" v-model="updateSingData.dictid" :disabled="true">
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>排序 *</p>
                                        <el-form-item prop="sortno" :rules="[{ required: true, message: '请填写序号', trigger: 'blur'}]">
                                            <el-input type="number" autosize placeholder="排序" v-model="updateSingData.sortno"
                                            @input="(val) => {updateTjData.sortno = val.replace('-', '')}"></el-input>
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
    import { getEosDictEntrys, addEvalSet, updateEvalSet, delEvalSet } from "@/api/hykh/pjsz";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    export default {
        name: 'pjszCenter',
        components: { buttonOranges, buttonDeepskyblue, buttonCommon, buttonOrange },
        data() {
            return {
                pjszData: [],
                ifShowXgSingDialog: false, //修改单个弹窗
                ifShowTjDialog: false, //是否显示添加弹窗
                updateSingData: {}, //修改单个数据
                updateTjData: {
                    dictid: '',
                    dictname: '',
                    sortno: ''
                }, //添加数据
                pj_options: []
            }
        },
        mounted() {
            this.seaTableData();
        },
        methods: {
            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ dicttypeid: 'DEP_KPI_EVAL_SET' });
                getEosDictEntrys(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.pjszData = res.data.results.filter(li => {
                            // li.dictid = li.dictid * 100;
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
                    delEvalSet({ dicttypeid: 'DEP_KPI_EVAL_SET', dictid: val.dictid / 100 }).then(res => {
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

            // 修改单个
            corrSingle(val) {
                console.log('修改单个', val)
                this.updateSingData = JSON.parse(JSON.stringify(val));
                this.ifShowXgSingDialog = true;
            },

            // 修改单个确定
            sureXgSing() {
                console.log("修改单个确定", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.dictid = this.updateSingData.dictid / 100;
                        params.dictname = this.updateSingData.dictname;
                        params.sortno = this.updateSingData.sortno;
                        params.dicttypeid = "DEP_KPI_EVAL_SET";
                        let data = common.paramsFormat(params);
                        updateEvalSet(data).then(res => {
                            loading.close();
                            console.log("修改单个确定", res.data)
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

            // 修改单个取消
            cancelXgSing() {
                this.ifShowXgSingDialog = false;
            },

            // 修改单个弹窗关闭回调
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
                        params.sortno = this.updateTjData.sortno;
                        params.dicttypeid = "DEP_KPI_EVAL_SET";
                        let data = common.paramsFormat(params);
                        addEvalSet(data).then(res => {
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
    .pjsz-container {
        padding: 15px;
        background: #FFFFFF;
    }

    .pjsz-head {
        padding-bottom: 15px;
    }

    /* .pjsz-body {
        background: #FFFFFF;
    } */

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
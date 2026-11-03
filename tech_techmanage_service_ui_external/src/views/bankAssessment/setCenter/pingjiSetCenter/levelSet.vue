<!-- 级别设置 -->
<template>
    <div class="pjsz-container">
        <div class="pjsz-body">
            <div class="pjsz-body-operate">
                <el-button type="warning" size="small" @click="addTo">添 加</el-button>
                <el-button size="small" @click="tongbu">同 步</el-button>
            </div>
            <div class="pjsz-body-cont">
                <el-table :data="pjszData">
                    <el-table-column label="序号" prop="sortno" min-width="15%"></el-table-column>
                    <el-table-column label="级别" prop="dictname" min-width="60%"></el-table-column>
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
        <el-dialog title="考核级别设置" :visible.sync="ifShowTjDialog" @close="addClose">
            <div class="xgContent">
                <el-form label-position="left" label-width="70px" :model="updateTjData" ref="formAdd">
                    <el-form-item label="级别" prop="dictname" :rules="[{ required: true, message: '请填写级别', trigger: 'blur'}]">
                        <el-input v-model="updateTjData.dictname"></el-input>
                    </el-form-item>
                    <el-form-item label="序号" prop="sortno" :rules="[{ required: true, message: '请填写序号', trigger: 'blur'}]">
                        <el-input v-model="updateTjData.sortno"></el-input>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelAdd">取 消</el-button>
                    <el-button type="primary" @click="sureAdd">提 交</el-button>
                </div>
            </div>
        </el-dialog>
        <!-- 修改单人 -->
        <el-dialog title="考核级别设置" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <el-form label-position="left" label-width="70px" :model="updateSingData" ref="formXgSing">
                    <!-- <el-form-item label="级别" prop="dictname" :rules="[{ required: true, message: '请填写级别', trigger: 'blur'}]">
                        <el-input v-model="updateSingData.dictname"></el-input>
                    </el-form-item> -->
                    <el-form-item label="序号" prop="sortno" :rules="[{ required: true, message: '请填写序号', trigger: 'blur'}]">
                        <el-input v-model="updateSingData.sortno"></el-input>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelXgSing">取 消</el-button>
                    <el-button type="primary" @click="sureXgSing">提 交</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { getEosDictEntrys, addKpiLevelPerson, updateEvalSet, deleteKpiLevelPerson, syncLevelPersonData } from "@/api/hykh/pjsz";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'pingjiSet',
        components: { buttonOranges, buttonDeepskyblue },
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
                level_options: []
            }
        },
        mounted() {
            this.seaTableData();
        },
        methods: {
            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_LEVEL" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //级别
                    this.level_options = data.filter(item => {
                        return item.dicttypeid == "KPI_LEVEL";
                    });
                    return data;
                });
            },

            // 同步
            tongbu() {
                const loading = common.loading(this);
                syncLevelPersonData().then(res => {
                    loading.close();
                    if (res.data.status != 200) {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ dicttypeid: 'KPI_LEVEL' });
                getEosDictEntrys(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.pjszData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 删除
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let data = common.paramsFormat({ dicttypeid: 'KPI_LEVEL', dictid: val.dictid });
                    deleteKpiLevelPerson(data).then(res => {
                        loading.close();
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
                this.updateSingData = JSON.parse(JSON.stringify(val));
                this.ifShowXgSingDialog = true;
            },

            // 修改单人确定
            sureXgSing() {
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.dictname = this.updateSingData.dictname;
                        params.dictid = this.updateSingData.dictid;
                        params.sortno = this.updateSingData.sortno;
                        params.dicttypeid = "KPI_LEVEL";
                        let data = common.paramsFormat(params);
                        updateEvalSet(data).then(res => {
                            loading.close();
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
                this.$refs["formAdd"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.dictid = params.dictname = this.updateTjData.dictname;
                        params.sortno = this.updateTjData.sortno;
                        params.dicttypeid = "KPI_LEVEL";
                        let data = common.paramsFormat(params);
                        addKpiLevelPerson(data).then(res => {
                            loading.close();
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowTjDialog = false;
                                this.seaTableData();
                                this.getDic();
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
        border-radius: 4px;
    }

    .pjsz-body-operate {
        text-align: right;
        margin-bottom: 10px;
    }

    .hmddialog-bottom {
        text-align: center;
    }
</style>
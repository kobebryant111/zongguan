<!-- 级别设置 -->
<template>
    <div class="web-container">
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-operation">
                <el-button size="small" @click="addTo">添 加</el-button>
            </div>
            <div class="web-body-table">
                <el-table :data="tableData" height="500" @selection-change="handleSelectionChange">
                    <el-table-column label="序号" prop="indexnum" min-width="20%"></el-table-column>
                    <el-table-column label="级别" prop="grade"></el-table-column>
                    <el-table-column label="操作" min-width="30%" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <button-deepskyblue size="mini" buttonName="修 改" :parentData="scope.row" :fns="update"></button-deepskyblue>
                                <button-oranges buttonName="删 除" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="delSingle"></button-oranges>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
        <!-- 添加/修改 -->
        <el-dialog :visible.sync="ifShowDialog" @close="addClose" width="40%">
            <div class="dialogContent">
                <el-form class="dialog-form" :model="updateData" ref="formAdd" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateData.id"></el-input></el-form-item>
                    <el-form-item label="序号：" prop="indexnum" :rules="[{ required: true, message: '序号不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateData.indexnum"></el-input>
                    </el-form-item>
                    <el-form-item label="级别：" prop="grade" :rules="[{ required: true, message: '级别不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateData.grade"></el-input>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelAddOrUpdate"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" :fns="sureAdd" size="mini"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import buttonFuscia from '@/components/eleButton/buttonFuscia';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import { getEosDictEntrys } from "@/api/hykh/pjsz";
    import { queryGradePagerList, addGrade, updateGrade, deleteGrade } from "@/api/zzkhApi/grade";
    export default {
        name: 'gradeSet',
        components: { cleanBtn, buttonOrange, buttonFuscia, buttonDeepskyblue, buttonOranges, pager, buttonCommon },
        data() {
            return {
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 总数
                ifShowDialog: false, //是否显示添加弹窗
                tableData: [],
                addFlag: 0,
                updateData: {
                    id: null,
                    indexnum: null,
                    grade: null
                }, //添加数据
            }
        },
        props:{
            sign: Number 
        },
        mounted() {
            this.submitBtn();
        },
        methods: {
            // 查询列表数据
            seaTableData(val) {
                if(!val){
                    val = {};
                }
                val.sign = this.sign;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryGradePagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.tableData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.size = size;
                params.indexnum = indexnum;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.size = this.size;
                params.index = this.index;
                this.seaTableData(params);
            },

            // 重置
            resetForm() {
            },

            // 添加
            addTo() {
                this.ifShowDialog = true;
                this.addFlag = 0;
                this.updateData.indexnum = null;
                this.updateData.grade = null;
            },
            // 修改
            update(val) {
                this.ifShowDialog = true;
                this.updateData = JSON.parse(JSON.stringify(val));
                this.addFlag = 1;   
            },
            // 添加确定
            sureAdd() {
                this.$refs["formAdd"].validate(valid => {
                    if (!valid) {
                        this.$message.error('请填写必填项');
                        return false;
                    } 
                    const loading = common.loading(this);
                    let params = {};
                    params.sign = this.sign;
                    params.indexnum = this.updateData.indexnum;
                    params.grade = this.updateData.grade;
                    params = JSON.parse(JSON.stringify(params));
                    if(this.addFlag === 0){
                        let data = common.paramsFormat(params);
                        addGrade(data).then(res => {
                            loading.close();
                            console.log("添加确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowDialog = false;
                                this.$refs["formAdd"].resetFields();
                                this.seaTableData();
                            } else {
                                this.$message.error(res.data.message);
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }else{
                        params.id = this.updateData.id;
                        let data = common.paramsFormat(params);
                        updateGrade(data).then(res => {
                            loading.close();
                            console.log("修改确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('修改成功');
                                this.ifShowDialog = false;
                                this.seaTableData();
                                this.$refs["formAdd"].resetFields();
                            } else {
                                this.$message.error(res.data.message);
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }
                });
            },

            // 添加/修改取消
            cancelAddOrUpdate() {
                this.ifShowDialog = false;
                this.$refs["formAdd"].resetFields();
            },

            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formAdd"].resetFields();
            },

            // 表格选中变化
            handleSelectionChange(val) {
                this.selectRow = val;
            },

            // 删除（单）
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdStr = val.id.toString();
                    deleteGrade({ id: jlIdStr }).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.seaTableData();
                        } else {
                            this.$message.error(res.data.message);
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },
        },
    }
</script>
<style scoped>
    .web-head-content {
        background: #FFFFFF;
        padding: 15px 0 15px 15px;
        margin-bottom: 15px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        -webkit-justify-content: space-between;
        justify-content: space-between;
    }

    .head-left {
        flex: 1;
    }

    .head-right {
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        padding-right: 15px;
        width: 145px;
        display: flex;
    }

    .head-right-clean {
        flex: 1;
    }

    .head-right-search {
        padding-left: 10px;
        flex: 1;
    }

    .web-body {
        padding: 15px;
        background: #FFFFFF;
    }

    .web-body-operation {
        text-align: right;
        margin-bottom: 10px;
    }

    .pager-zj {
        text-align: center;
    }

    .hmddialog-bottom {
        text-align: center;
        margin-top: 10px;
    }

    /* .hmd-row-block p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        margin: 8px 0;
    } */
</style>

<style>
    .web-head-content .el-input--prefix .el-input__inner {
        padding-left: 55px;
    }

    .web-head-content .el-form-item {
        margin-bottom: 0;
    }

    .upload-butt .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }

    .dialogContent .el-cascader {
        display: block;
    }
    .form-title .el-form-item__label{
        font-size: 16px;
    }
</style>
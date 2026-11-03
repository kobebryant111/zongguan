<!-- 模板管理 -->
<template>
    <div class="yggl-container">
        <div class="back-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item v-if="this.sign == 1" :to="{ path: '/ejbModel'}">模板分配</el-breadcrumb-item>
                <el-breadcrumb-item v-if="this.sign == 2" :to="{ path: '/zxModel'}">模板分配</el-breadcrumb-item>
                <el-breadcrumb-item>模板库</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="yggl-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="考核项模板:">
                                     <el-input v-model="paramsForm.name"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean" @click="resetForm">
                            <clean-btn size="small" :forms="this.paramsForm" />
                        </div>
                        <div class="head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>

        <div class="yggl-body xkg-borderradius-4px">
            <div class="yggl-body-operation">
                <el-button size="small" @click="addTo">+添加模板</el-button>
            </div>
            <div class="yggl-body-table">
                <el-table :data="tableData" height="510" @selection-change="handleSelectionChange">
                    <el-table-column label="考核项模板" width="220" prop="name"></el-table-column>
                    <el-table-column label="备注" width="280" prop="remark"></el-table-column>
                    <el-table-column label="基准分" width="100" prop="basepoint"></el-table-column>
                    <el-table-column label="提交状态" width="100" prop="commitflag">
                        <template slot-scope="scope">
                            <span v-if="scope.row.commitflag == 1">生效</span>
                            <span v-else>草稿</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="最后修改时间" width="200" prop="lastmodtime"></el-table-column>
                    <el-table-column label="操作" width="300" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <el-button type="primary" class="xkg-marginleft-10px" size="mini" @click="indicatorItemsJump(scope.row)">考核项配置</el-button>
                                <el-button type="success" class="xkg-marginleft-10px" size="mini" @click="update(scope.row)">修改</el-button>
                                <el-button type="danger" class="xkg-marginleft-10px" size="mini" @click="deleteSingle(scope.row.id)">删除</el-button>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column></el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
        <!-- 添加/修改 -->
        <el-dialog title="添加模板" :visible.sync="ifShowTjDialog" @close="addClose" width="40%">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateData" ref="formAdd" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateData.id"></el-input></el-form-item>
                    <el-form-item label="模板名称：" prop="name" :rules="[{ required: true, message: '模板名称不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateData.name"></el-input>
                    </el-form-item>
                     <el-form-item label="备注：" prop="remark" :rules="[{ required: true, message: '备注不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateData.remark"></el-input>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelAddOrUpdate"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" size="mini" :fns="sureAdd"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import common from "@/api/common";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPagerList, add, update, deleteTarget } from "@/api/zzkhApi/target";
    import { queryModelPagerList, addModel, updateModel, deleteModel } from "@/api/zzkhApi/model";
    import pager from "@/components/Pager";
    export default {
        name: 'modelManager',
        components: { pager ,buttonOrange, cleanBtn},
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 总数
                ifShowTjDialog: false, //是否显示添加弹窗
                sign: null, //所属为部门
                addFlag: 0, // 新增修改标志：0新增，1修改
                paramsForm: { //搜索框表单
                    name: null
                },
                tableData: [],
                updateData: { //添加数据
                    id: null,
                    name: null,
                    remark: null
                },
            }
        },
        props:{
            level: Number
        },
        beforeCreate(){
            if(!this.$route.query.sign){
                this.$router.back(-1)
            }
            
        },
        created(){
            this.sign = this.$route.query.sign;
        },
        mounted() {
            this.submitBtn();
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.back(-1)
            },

            // 查询列表数据
            seaTableData(val) {
                if(!val){
                    val = {};
                }
                val.sign = this.sign;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryModelPagerList(data).then(res => {
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
                params.index = index;
                params.name = this.paramsForm.name;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.name = this.paramsForm.name;
                params.size = this.size;
                params.index = this.index;
                this.seaTableData(params);
            },

            // 重置
            resetForm() {
            },

             // 指标项配置页面跳转
            indicatorItemsJump(val) {
                this.$router.push({ path: '/targetAssign', query: {id: val.id, sign: this.sign, name: val.name, type: 1}});
            },

            // 新增
            addTo(){
                this.ifShowTjDialog = true;
                this.addFlag = 0;
            },

            // 修改
            update(val) {
                this.updateData = JSON.parse(JSON.stringify(val));
                this.ifShowTjDialog = true;
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
                    params.name = this.updateData.name;
                    params.remark = this.updateData.remark;
                    if(this.addFlag === 0){
                        let data = common.paramsFormat(params);
                        addModel(data).then(res => {
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
                    }else{
                        params.id = this.updateData.id;
                        let data = common.paramsFormat(params);
                        updateModel(data).then(res => {
                            loading.close();
                            console.log("修改确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('修改成功');
                                this.ifShowTjDialog = false;
                                this.seaTableData();
                            } else {
                                this.$message.error('修改失败')
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
                this.ifShowTjDialog = false;
                this.$refs["formAdd"].resetFields();
            },

            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formAdd"].resetFields();
                this.updateData = { 
                    id: null,
                    name: null,
                    remark: null
                };
            },

            // 表格选中变化
            handleSelectionChange(val) {
                // console.log("分页表格选中", val)
                this.selectRow = val;
            },
            
            // 删除
            deleteSingle(id) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    deleteModel({ id: id }).then(res => {
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
        },
    }
</script>
<style scoped>
    .yggl-container {
        padding: 15px;
    }

    .yggl-head-content {
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

    .back-bread {
        margin-bottom: 15px;
    }

    .back-bread .el-breadcrumb {
        display: inline-block;
        vertical-align: middle;
        margin-left: 10px;
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

    .yggl-body {
        padding: 15px;
        background: #FFFFFF;
    }

    .yggl-body-operation {
        text-align: right;
        margin-bottom: 10px;
    }

    .pager-zj {
        text-align: center;
    }

    .hmddialog-bottom {
        text-align: right;
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
    .yggl-head-content .el-input--prefix .el-input__inner {
        padding-left: 55px;
    }

    .yggl-head-content .el-form-item {
        margin-bottom: 0;
    }

    .upload-butt .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }

    .xgContent .el-cascader {
        display: block;
    }
</style>
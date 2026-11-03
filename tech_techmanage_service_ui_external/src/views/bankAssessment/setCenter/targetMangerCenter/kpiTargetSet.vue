<!-- KPI指标配置 -->
<template>
    <div class="kpi-container">
        <div class="target-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left organization-head-left">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item>
                                    <div class="target-center">
                                        <el-select filterable v-model="paramsForm.centerid">
                                            <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                :value="item.id"></el-option>
                                            <template slot="prefix">中心：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item>
                                    <div class="target-zbx">
                                        <el-input v-model="paramsForm.item">
                                            <template slot="prefix">指标项：</template>
                                        </el-input>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
                            <clean-btn size="small" :forms="this.paramsForm" />
                        </div>
                        <div class="head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div class="target-body">
            <div class="yggl-body-operation">
                <el-button size="small" @click="enterEx">导 入</el-button>
                <el-button size="small" @click="exportEx">导 出</el-button>
                <el-button size="small" @click="delMo">删 除</el-button>
                <button-oranges buttonName="添加指标" class="xkg-marginleft-10px" size="small" :fns="addTo"></button-oranges>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" height="510" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="45"></el-table-column>
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="指标项" width="200" prop="item" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="使用角色" width="250" prop="userolename"></el-table-column>
                    <el-table-column label="中心" width="200" prop="centername"></el-table-column>
                    <el-table-column label="操作时间" width="160" prop="uptdate"></el-table-column>
                    <el-table-column label="操作" width="160" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <button-deepskyblue size="mini" buttonName="修 改" :parentData="scope.row" :fns="corrSingle"></button-deepskyblue>
                                <button-oranges buttonName="删 除" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="delSingle"></button-oranges>
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
        <!-- 添加 -->
        <el-dialog title="KPI指标配置" :visible.sync="ifShowTjDialog" @close="addClose">
            <div class="xgContent">
                <el-form :model="updateTjData" ref="formAdd">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>指标项 *</p>
                                        <el-form-item prop="item" :rules="[{ required: true, message: '请填写指标项', trigger: 'change'}]">
                                            <el-input placeholder="指标项" v-model="updateTjData.item">
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>中心 *</p>
                                        <el-form-item prop="centerid" :rules="[{ required: true, message: '请选择中心', trigger: 'change'}]">
                                            <el-select placeholder="中心" filterable v-model="updateTjData.centerid">
                                                <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                    :value="item.id"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>角色 *</p>
                                        <el-form-item prop="userole" :rules="[{ required: true, message: '请选择角色', trigger: 'change'}]">
                                            <el-checkbox-group v-model="updateTjData.userole">
                                                <el-checkbox v-for="item in role_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-checkbox>
                                            </el-checkbox-group>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>

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
        <el-dialog title="上传文件" :visible.sync="ifShowEnDialog" @close="scClose">
            <div>
                <div class="upload-butt">
                    <el-upload action="Fake Action" accept=".xlsx, .xls" :file-list="drList" :show-file-list="true"
                        :http-request="changeUpFile" :on-change="handleChange" :on-remove="handleRemove">
                        <el-button size="small" type="primary">
                            <i class="el-icon-upload2"></i>
                            <span>{{drList.length == 0 ? '上传文件' : '重新上传'}}</span>
                        </el-button>
                    </el-upload>
                </div>
                <div class="dia-bottom">
                    <el-row :gutter="10" type="flex" justify="end">
                        <el-col :span="1.5">
                            <button-common buttonName="取 消" size="mini" :fns="cancelEnter"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange buttonName="确 定" size="mini" :fns="sureEnter"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
        <!-- 修改单人 -->
        <el-dialog title="组织管理" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <el-form :model="updateSingData" ref="formXgSing">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>指标项 *</p>
                                        <el-form-item prop="item" :rules="[{ required: true, message: '请填写指标项', trigger: 'change'}]">
                                            <el-input placeholder="指标项" v-model="updateSingData.item">
                                            </el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>中心 *</p>
                                        <el-form-item prop="centerid" :rules="[{ required: true, message: '请选择中心', trigger: 'change'}]">
                                            <el-select placeholder="中心" filterable v-model="updateSingData.centerid">
                                                <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                    :value="item.id"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>角色 *</p>
                                        <el-form-item prop="userole" :rules="[{ required: true, message: '请选择角色', trigger: 'change'}]">
                                            <el-checkbox-group v-model="updateSingData.userole">
                                                <el-checkbox v-for="item in role_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-checkbox>
                                            </el-checkbox-group>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>

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
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import pager from "@/components/Pager";
    import common from "@/api/common";
    import { queryList } from "@/api/integrated/newEmBmfp";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPagerList_kpi, exportKpiItems, importExcel_kpi, deletePpmKpiItemList_kpi, updatePpmKpiItemDto_kpi, addPpmKpiItemDto_kpi } from "@/api/hykh/targetLibrary/kpiDeploy";
    import Moment from 'moment';
    export default {
        name: 'kpiTargetSet',
        components: { cleanBtn, buttonOrange, buttonOranges, buttonCommon, buttonDeepskyblue, pager },
        data() {
            return {
                paramsForm: {
                    centerid: null,
                    item: null,
                },
                centerData: [],
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                ifShowEnDialog: false, //是否显示导入弹窗
                drFileData: {}, //导入文件信息
                drList: [], //导入列表
                selectRow: [], //表格选中行
                ifShowTjDialog: false, //是否显示添加弹窗
                updateTjData: {
                    item: '',
                    centerid: '',
                    userole: [],
                }, //添加数据
                targetData: [],
                ifShowXgSingDialog: false, //修改单人弹窗
                updateSingData: {}, //修改单人数据
                role_options: [],
            }
        },
        mounted() {
            this.seaTableData();
            this.getCenter();
            this.getDic();
        },
        methods: {
            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_ITEM_USE_ROLE" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //角色
                    this.role_options = data.filter(item => {
                        return item.dicttypeid == "KPI_ITEM_USE_ROLE";
                    });
                    return data;
                });
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    console.log('中心', res.data)
                    if (res.data.status == 200) {
                        this.centerData = res.data.results.filter(li => {
                            return li.id != 61
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.item = this.paramsForm.item;
                params.centerid = this.paramsForm.centerid;
                this.seaTableData(params);
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.item = this.paramsForm.item;
                params.centerid = this.paramsForm.centerid;
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryPagerList_kpi(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.targetData = res.data.results.filter(li => {
                            if (li.uptdate != undefined) {
                                li.uptdate = Moment(li.uptdate).format('YYYY-MM-DD');
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 导入
            enterEx() {
                this.ifShowEnDialog = true;
            },

            // 取消导入
            cancelEnter() {
                this.ifShowEnDialog = false;
            },

            // 关闭导入框
            scClose() {
                this.drList = [];
            },

            // 确认导入
            sureEnter() {
                if (this.drList.length == 0) {
                    this.$message.error('请上传文件');
                    return
                }
                const loading = common.loading(this);
                var formData = new FormData();
                formData.append("file", this.drFileData);
                importExcel_kpi(formData).then(res => {
                    loading.close();
                    console.log("确认导入", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('导入成功');
                        this.ifShowEnDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error('导入失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 上传变化
            changeUpFile(file) {
                console.log('上传批次变化', file, this.drList)
                this.drFileData = file.file;
            },

            // on-change
            handleChange(file, fileList) {
                console.log('on-change', fileList);
                this.drList = [fileList[fileList.length - 1]];
            },

            // on-remove
            handleRemove() {
                this.drList = [];
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let params = {};
                params.item = this.paramsForm.item;
                params.centerid = this.paramsForm.centerid;
                let data = common.paramsFormat(params);
                const self = this;
                exportKpiItems(data).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "KPI指标配置.xls");
                });
            },

            // 表格选中变化
            handleSelectionChange(val) {
                console.log("分页表格选中", val)
                this.selectRow = val;
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
                        params.item = this.updateTjData.item;
                        params.centerid = this.updateTjData.centerid;
                        params.userole = this.updateTjData.userole;
                        let userolenameArr = [];
                        params.userole.forEach(li => {
                            this.role_options.forEach(lis => {
                                if (li == lis.dictid) {
                                    userolenameArr.push(lis.dictname)
                                }
                            })
                        })
                        if (params.userole.length > 0) {
                            params.userolename = userolenameArr.join(',');
                            params.userole = params.userole.join(',');
                        } else {
                            params.userolename = '';
                            params.userole = '';
                        }
                        let data = common.paramsFormat(params);
                        addPpmKpiItemDto_kpi(data).then(res => {
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

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
                val.centerid = Number(val.centerid);
                if (typeof val.userole == 'string') {
                    val.userole = val.userole.split(',');
                }
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
                        params.item = this.updateSingData.item;
                        params.centerid = this.updateSingData.centerid;
                        params.userole = this.updateSingData.userole;
                        let userolenameArr = [];
                        params.userole.forEach(li => {
                            this.role_options.forEach(lis => {
                                if (li == lis.dictid) {
                                    userolenameArr.push(lis.dictname)
                                }
                            })
                        })
                        if (params.userole.length > 0) {
                            params.userolename = userolenameArr.join(',');
                            params.userole = params.userole.join(',');
                        } else {
                            params.userolename = '';
                            params.userole = '';
                        }
                        let data = common.paramsFormat(params);
                        updatePpmKpiItemDto_kpi(data).then(res => {
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

            // 删除（多）
            delMo() {
                if (this.selectRow.length == 0) {
                    this.$message.error('请至少选中一行');
                    return
                }
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdAr = [];
                    this.selectRow.forEach(li => {
                        jlIdAr.push(li.id)
                    })
                    let jlIdStr = jlIdAr.join(',');
                    deletePpmKpiItemList_kpi({ ids: jlIdStr }).then(res => {
                        loading.close();
                        console.log("删除（多）", res.data)
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

            // 删除（单）
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdStr = val.id.toString();
                    deletePpmKpiItemList_kpi({ ids: jlIdStr }).then(res => {
                        loading.close();
                        console.log("删除（多）", res.data)
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
        }
    }
</script>
<style scoped>
    .target-head {
        padding-bottom: 15px;
    }

    .yggl-head-content {
        background: #FFFFFF;
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
        width: 135px;
        display: flex;
    }

    .head-right-clean {
        flex: 1;
    }

    .head-right-search {
        padding-left: 10px;
        flex: 1;
    }

    .yggl-body-operation {
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
        margin: 8px 0;
    }

    .dia-bottom {
        margin-top: 30px;
    }

    .pager-zj {
        text-align: center;
    }
</style>

<style>
    .target-center .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .target-zbx .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }
</style>
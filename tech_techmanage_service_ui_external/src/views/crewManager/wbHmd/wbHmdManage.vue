<!-- 外包黑名单页面 -->
<template>
    <div class="hmd-container">
        <div class="hmd-title">
            <el-form inline>
                <div class="hmdList-head">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-input placeholder="姓名" v-model="paramsForm.userName">
                                        <template slot="prefix">姓名：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item>
                                    <el-select placeholder="部门" filterable v-model="paramsForm.depId">
                                        <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                        <template slot="prefix">部门：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item>
                                    <div class="gongyingshang-hmd">
                                        <el-select placeholder="供应商" filterable v-model="paramsForm.orgCode">
                                            <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname"
                                                :value="item.orgcode"></el-option>
                                            <template slot="prefix">供应商：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
                            <!-- <clean-btn :size="sizes" :forms="this.paramsForm" /> -->
                            <button-common :buttonName="buttonReset" :size="sizes" :fns="resetFn"></button-common>
                        </div>
                        <div class="head-right-search">
                            <buttonOrange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div class="hmd-main">
            <div class="hmd-main-operation">
                <button-common :buttonName="buttonExport" :size="sizes" :fns="exportFn"></button-common>
                <button-common :buttonName="buttonAdd" :size="sizes" :fns="addFn" class="hmd-main-operation-enter"></button-common>
            </div>
            <el-table :data="hmdData" height="510" @row-dblclick="dbTable">
                <el-table-column prop="userName" label="姓名" width="100"></el-table-column>
                <el-table-column prop="icard" label="身份证号" width="200"></el-table-column>
                <el-table-column label="二级部" width="200">
                    <template slot-scope="scope">
                        <el-select placeholder="" v-model="scope.row.depId" :disabled="true">
                            <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="供应商" width="300">
                    <template slot-scope="scope">
                        <el-select placeholder="" v-model="scope.row.orgCode" :disabled="true">
                            <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname"
                                :value="item.orgcode"></el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column prop="officeDate" label="离职时间" width="150" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="why" label="离职原因" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click="del(scope.row, scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pager-hmd">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
            </div>
        </div>

        <el-dialog title="黑名单" :visible.sync="ifShowHmdDialog" class="">
            <div>
                <el-form :model="hmdUpdateData" ref="formHmd">
                    <div class="hmd-row-block">
                        <el-row :gutter="50">
                            <el-col :span="12">
                                <div>
                                    <p>姓名 *</p>
                                    <el-form-item prop="userName" :rules="[{ required: true, message: '请填写姓名', trigger: 'blur'}]">
                                        <el-input v-model="hmdUpdateData.userName"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p>身份证号 *</p>
                                    <el-form-item prop="icard" :rules="[{ required: true, message: '请填写身份证号', trigger: 'blur'}]">
                                        <el-input v-model="hmdUpdateData.icard"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="hmd-row-block">
                        <el-row :gutter="50">
                            <el-col :span="12">
                                <div>
                                    <p>二级部</p>
                                    <el-form-item>
                                        <el-select placeholder="部门" filterable v-model="hmdUpdateData.depId">
                                            <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p>供应商</p>
                                    <el-form-item>
                                        <el-select placeholder="供应商" filterable v-model="hmdUpdateData.orgCode">
                                            <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname"
                                                :value="item.orgcode"></el-option>
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
                                    <p>离职时间</p>
                                    <el-form-item>
                                        <el-date-picker v-model="hmdUpdateData.officeDate" type="date" placeholder=""
                                            value-format="yyyy-MM-dd"></el-date-picker>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p>离职原因</p>
                                    <el-form-item>
                                        <el-input type="textarea" autosize v-model="hmdUpdateData.why"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-orange :buttonName="sureName" :size="sizes" :fns="surePeo"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { hmdDelete, queryPager, update, exportExcel } from "@/api/integrated/wbHmd";
    import { getListAll } from "@/api/common/flowApi";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import Moment from 'moment'
    export default {
        name: 'wbHmdManage',
        components: { buttonCommon, cleanBtn, buttonOrange, pager },
        data() {
            return {
                sizes: 'mini',  //按钮大小
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                paramsForm: {
                    userName: null,  // 姓名
                    depId: null, // 部门
                    orgCode: null, // 供应商
                },
                buttonSearch: '查 询', //查询按钮
                buttonExport: '导 出',
                buttonAdd: '新 增',
                buttonReset: '重 置',
                hmdData: [], //黑名单数据
                outsourceno_options: [], //外包商
                depData: [], // 部门数据
                ifShowHmdDialog: false, //是否展示弹窗
                sureName: '确 定',
                hmdUpdateData: {
                    userName: '',
                    icard: '',
                    depId: '',
                    orgCode: '',
                    officeDate: '',
                    why: '',
                }, //黑名单更新数据
            }
        },
        mounted() {
            this.getWbcom();
            this.getDep();
            this.searchHmdData();
        },
        methods: {
            // 导出
            exportFn() {
                const loading = common.loading(this);
                let searchData = {};
                searchData.userName = this.paramsForm.userName;
                if (this.paramsForm.depId != null) {
                    let depid = this.paramsForm.depId;
                    searchData.depId = depid.replace("dep", "");
                }
                searchData.orgCode = this.paramsForm.orgCode;
                let params = common.paramsFormat(searchData);
                const self = this;
                exportExcel(params).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "黑名单.xls");
                });
            },

            // 新增
            addFn() {
                this.hmdUpdateData = {
                    userName: '',
                    icard: '',
                    depId: '',
                    orgCode: '',
                    officeDate: '',
                    why: '',
                };
                this.ifShowHmdDialog = true;
            },

            // 确定
            surePeo() {
                this.$refs["formHmd"].validate(valid => {
                    if (valid) {
                        var reg = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
                        if (reg.test(this.hmdUpdateData.icard)) {
                            if (common.checkSfzhLast(this.hmdUpdateData.icard)) {
                                this.hmdUndate()
                            } else {
                                this.$message.error('身份证号错误，请检查');
                                return
                            }
                        } else {
                            this.$message.error('身份证号格式有误');
                            return
                        }
                    } else {
                        this.$message.error('请填写必填项');
                        return false;
                    }
                });
            },

            // 查询
            submitBtn() {
                let searchData = {};
                searchData.userName = this.paramsForm.userName;
                if (this.paramsForm.depId != null) {
                    let depid = this.paramsForm.depId;
                    searchData.depId = depid.replace("dep", "");
                }
                searchData.orgCode = this.paramsForm.orgCode;
                this.searchHmdData(searchData);
            },

            // 重置
            resetFn() {
                Object.keys(this.paramsForm).forEach(key => (this.paramsForm[key] = null));
            },

            // 分页
            getPager: function (size, index) {
                let searchData = {};
                searchData.userName = this.paramsForm.userName;
                if (this.paramsForm.depId != null) {
                    let depid = this.paramsForm.depId;
                    searchData.depId = depid.replace("dep", "");
                }
                searchData.orgCode = this.paramsForm.orgCode;
                searchData.size = size;
                searchData.index = index;
                this.size = size;
                this.index = index;
                this.searchHmdData(searchData);
            },

            // 删除
            del(val, index) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    hmdDelete({ id: val.id }).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.hmdData.splice(index, 1);
                            this.$message.success('删除成功')
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
                }).catch(error => {
                    // this.$message.error(error);
                })
            },

            // 更新
            hmdUndate() {
                const loading = common.loading(this);
                let searchData = {};
                searchData.userName = this.hmdUpdateData.userName;
                if (this.hmdUpdateData.depId != undefined) {
                    let depid = this.hmdUpdateData.depId;
                    searchData.depId = depid.replace("dep", "");
                }
                if (this.hmdUpdateData.id != undefined) {
                    searchData.id = this.hmdUpdateData.id;
                }
                searchData.icard = this.hmdUpdateData.icard;
                searchData.officeDate = this.hmdUpdateData.officeDate;
                if (this.hmdUpdateData.why != undefined) {
                    searchData.why = this.hmdUpdateData.why;
                }
                if (this.hmdUpdateData.orgCode != undefined) {
                    searchData.orgCode = this.hmdUpdateData.orgCode;
                }
                let data = common.paramsFormat(searchData)
                update(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        console.log("更新", res.data)
                        this.$message.success('更新成功');
                        this.ifShowHmdDialog = false;
                        this.searchHmdData();
                    } else {
                        this.$message.error('更新失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 查询黑名单数据
            searchHmdData(val) {
                let param = common.paramsFormat(val);
                const loading = common.loading(this);
                queryPager(param).then(res => {
                    console.log("查询黑名单数据", res.data)
                    loading.close();
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        res.data.results = res.data.results.filter(li => {
                            if (li.officeDate != undefined && li.officeDate != '') {
                                li.officeDate = Moment(li.officeDate).format('YYYY-MM-DD');
                            }
                            return li
                        })
                        this.hmdData = res.data.results;
                    } else {
                        this.$message.error('查询数据失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查询外包商
            getWbcom() {
                getListAll().then(res => {
                    if (res.data.status == 200) {
                        this.outsourceno_options = res.data.results;
                    } else {
                        this.$message.error('查询外包商失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 获取部门
            getDep(id) {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    console.log("获取部门", res.data)
                    if (res.data.status == 200) {
                        var fild = res.data.results[0].children;
                        this.depData = fild.filter(tt => {
                            tt.text = tt.text.replace('总行金融科技部-', "");
                            tt.id = tt.id.replace('dep', "");
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 双击表格
            dbTable(val) {
                console.log('双击', val)
                let lsRow = {};
                lsRow.depId = val.depId;
                lsRow.depName = val.depName;
                lsRow.icard = val.icard;
                lsRow.id = val.id;
                lsRow.isdel = val.isdel;
                lsRow.officeDate = val.officeDate;
                lsRow.orgCode = val.orgCode;
                lsRow.supplier = val.supplier;
                lsRow.userName = val.userName;
                lsRow.why = val.why;
                this.hmdUpdateData = lsRow;
                this.ifShowHmdDialog = true;
            },
        }
    }
</script>
<style scoped>
    .hmd-container {
        padding: 15px 15px 0 15px;
    }

    .hmdList-head {
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
        height: 36px;
        line-height: 36px;
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

    .hmd-main {
        background-color: #ffffff;
        padding: 15px;
    }

    .hmd-main-operation {
        text-align: right;
        padding-bottom: 15px;
    }

    .hmd-main-operation-enter {
        margin-left: 15px;
    }

    .pager-hmd {
        width: 100%;
        text-align: center;
    }

    .hmddialog-bottom {
        text-align: right;
    }

    .hmd-row-block {
        margin-bottom: 15px;
    }

    .hmd-row-block p {
        color: #262626;
        font-size: 14px;
        margin: 8px 0;
    }
</style>

<style>
    .hmdList-head .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    /* 供应商输入框 */
    .gongyingshang-hmd .el-input--prefix .el-input__inner {
        padding-left: 65px;
    }

    .hmdList-head .el-form-item {
        margin-bottom: 0;
    }
</style>
<template>
    <div class="depAss-container">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="5">
                                <p class="nav_name">新员工部门分配报表</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="depAss-main">
            <el-form inline>
                <div class="depAss-main-head">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="5">
                                <el-form-item>
                                    <el-select placeholder="部门" filterable v-model="paramsForm.depId">
                                        <el-option v-for="item in depAllData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                        <template slot="prefix">部门：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input placeholder="年份" v-model="paramsForm.year">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
                            <clean-btn :size="size" :forms="this.paramsForm" />
                        </div>
                        <div class="head-right-search">
                            <buttonOrange :buttonName="buttonSearch" :size="size" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div class="depAss-baob">
            <div class="depAss-main-table">
                <div class="depAss-baob-title">部门分配报表
                    <span class="mbxz" @click="mbDownload">模板下载</span>
                </div>
                <div class="depAss-baob-operation">
                    <button-common :buttonName="buttonEnter" :size="size" :fns="biaobEnter"></button-common>
                </div>
                <el-table :data="baobiaoData" height="510">
                    <el-table-column label="部门" width="230">
                        <template slot-scope="scope">
                            <el-select placeholder="" v-model="scope.row.depId" :disabled="true">
                                <el-option v-for="item in depAllData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column prop="depNum" label="各部门总编制数" width="120"></el-table-column>
                    <el-table-column prop="researchNum" label="研发" width="60"></el-table-column>
                    <el-table-column prop="testNum" label="测试" width="60"></el-table-column>
                    <el-table-column prop="productNum" label="产品经理" width="80"></el-table-column>
                    <el-table-column prop="appSuportNum" label="应用支持" width="80"></el-table-column>
                    <el-table-column prop="systemNum" label="系统运维" width="80"></el-table-column>
                    <el-table-column prop="functionsifNum" label="职能管理"></el-table-column>
                </el-table>
                <div class="pager_zb">
                    <pager :fns="getBbPager" :total="bbTotal" :pageSize="bbPageSize" v-model="$attrs" ref="pagerBb" />
                </div>
            </div>
        </div>
        <el-dialog title="选择年份和上传文件" :visible.sync="ifShowExDialog">
            <div>
                <el-date-picker v-model="batchNo" type="year" value-format="yyyy" format="yyyy"></el-date-picker>
                <div class="upload-batch">
                    <el-upload action="Fake Action" accept=".xlsx, .xls" :file-list="drList" :show-file-list="true"
                        :http-request="changeBatchFile" :on-change="handleChange" :on-remove="handleRemove">
                        <el-button size="small" type="primary">
                            <i class="el-icon-upload2"></i>
                            <span>{{drList.length == 0 ? '上传文件' : '重新上传'}}</span>
                        </el-button>
                    </el-upload>
                </div>

                <div class="dia-bottom">
                    <el-row :gutter="10" type="flex" justify="end">
                        <el-col :span="1.5">
                            <button-common :buttonName="buttonCancel" :size="size" :fns="cancelSel"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange :buttonName="buttonSure" :size="size" :fns="sureSel"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange'
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import cleanBtn from "@/components/CleanBtn"
    import { getAuthList, getDownloadUrl } from '@/api/common/flowApi';
    import { importExcel, queryPager, queryDeptList, queryList, update, liuzhuan, exportExcel, queryCenter, getOperatAuth, getViewAuth, authUpdate, delRow, baobiaoImport, baobiaoPager } from "@/api/integrated/newEmBmfp";
    import Moment from 'moment';
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    export default {
        components: { buttonOrange, buttonCommon, cleanBtn, pager },
        name: 'bmfpReportForm',
        data() {
            return {
                size: 'mini',  //按钮大小
                buttonClose: '关 闭', //关闭按钮名字
                buttonSearch: '查 询', //查询按钮
                buttonExport: '导 出',
                buttonEnter: '导 入',
                buttonCancel: '取 消',
                buttonSure: '确 定',
                ylCloseNa: '关 闭',
                paramsForm: {
                    depId: null,
                    year: null,
                },
                depData: [],
                ifShowExDialog: false, //是否显示导出弹窗
                batchNo: '', //批次
                batchFile: {}, //导入文件信息
                drList: [], //导入列表
                operAuth: 0, //是否有操作权限
                baobiaoData: [], //报表数据
                bbTotal: 0, //报表分页总数
                depAllData: [], //所有部门
                bbPageSize: 50, //报表分页初始值
                bbIndex: 1, // 初始值 默认：1
            }
        },
        mounted() {
            this.init();
            this.getOpeAuth();
            this.searchBaob();
            this.getAllDep();
        },
        methods: {
            init() {
                let date = new Date();
                this.paramsForm.year = date.getFullYear();
                this.batchNo = date.getFullYear().toString();
            },

            // 获取部门
            getDep(id) {
                const loading = common.loading(this);
                queryDeptList({ centerId: id })
                    .then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.depData = fild.filter(tt => {
                                tt.depName = tt.depName.replace('总行金融科技部-', "");
                                tt.id = tt.id.replace('dep', '')
                                return tt;
                            });
                        }
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },

            // 查询
            submitBtn() {
                let params = {};
                params.depId = this.paramsForm.depId;
                params.year = this.paramsForm.year;
                this.searchBaob(params);
            },

            // 删除
            delFn() {

            },

            // 导出
            exportFn() {
                let searchData = {};

                exportExcel(params).then(res => {
                    console.log("导出", res.data)
                    common.download(res.data, self, "新员工部门分配");
                });
            },

            // 取消导入
            cancelSel() {
                this.ifShowExDialog = false;
            },

            // 确认导入
            sureSel() {
                if (this.batchNo == '' || this.batchNo == null) {
                    this.$message.error('请选择年份');
                    return
                } else if (this.drList.length == 0) {
                    this.$message.error('请上传文件');
                    return
                } else {
                    const loading = common.loading(this);
                    let batchNo = Moment(this.batchNo).format('YYYY');
                    var formData = new FormData();
                    formData.append("batchNo", batchNo);
                    formData.append("file", this.batchFile);
                    baobiaoImport(formData).then(res => {
                        loading.close();
                        console.log("报表导入", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('导入成功');
                            this.ifShowExDialog = false;
                            this.searchBaob();
                        } else {
                            this.$message.error('导入失败')
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    })
                }
            },

            // 上传批次变化
            changeBatchFile(file) {
                console.log('上传批次变化', file, this.drList)
                this.batchFile = file.file;
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

            // 获取操作权限
            getOpeAuth() {
                getOperatAuth().then(res => {
                    if (res.data.status == 200) {
                        this.operAuth = res.data.results;
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 报表导入
            biaobEnter() {
                this.drList = [];
                this.batchFile = {};
                this.ifShowExDialog = true;
            },

            // 报表分页查询
            searchBaob(val) {
                let param = common.paramsFormat(val);
                const loading = common.loading(this);
                baobiaoPager(param).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.bbTotal = res.data.total;
                        this.baobiaoData = res.data.results;
                        this.baobiaoData.splice(0, 0)
                        console.log("分页查询报表", this.baobiaoData)
                    } else {
                        this.$message.error('查询数据失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 获取所有部门
            getAllDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            this.depAllData = res.data.results[0].children.filter(tt => {
                                tt.id = tt.id.replace('dep', '')
                                tt.text = tt.text.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 报表分页
            getBbPager: function (size, index) {
                let params = {};
                params.depId = this.paramsForm.depId;
                params.year = this.paramsForm.year;
                params.size = size;
                params.index = index;
                this.bbPageSize = size;
                this.bbIndex = index;
                this.searchBaob(params);
            },

            // 模板下载
            mbDownload(val) {
                const loading = common.loading(this);
                getDownloadUrl({ key: 'PPM_ORIENTATION_REPORT' }).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        const self = this;
                        common.downloadJl(res.data.results.documentno, self);
                    } else {
                        this.$message.error("获取下载地址失败");
                    }
                })

            },

        }

    }
</script>
<style scoped>
    .depAss-main {
        padding: 17px;
    }

    .depAss-main-head {
        border-radius: 4px;
        background: #FFFFFF;
        padding: 15px 0 15px 15px;
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

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
        height: 24px;
        font-size: 16px;
        font-weight: 600;
        color: rgba(38, 38, 38, 1);
        line-height: 24px;
        display: inline-block;
    }

    .nav .wrapper {
        margin-left: 25px;
        margin-right: 25px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .wrapper-right-keep {
        margin-right: 10px;
    }

    .depAss-main-table {
        border-radius: 4px;
        padding: 18px;
        background-color: #ffffff;
    }

    .depAss-main-operation {
        margin-bottom: 15px;
    }

    .dia-bottom {
        margin-top: 30px;
    }

    .ylButt {
        color: red;
        text-decoration: underline;
        margin-left: 10px;
        cursor: pointer;
    }

    .upload-batch {
        margin-top: 20px;
    }

    .pager_zb {
        text-align: center;
    }

    .qxgl-bottom {
        margin-top: 15px;
        text-align: right;
    }

    .depAss-baob {
        padding: 0 17px 17px 17px;
    }

    .depAss-baob-title {
        font-size: 16px;
        font-weight: 600;
        margin-bottom: 5px;
    }

    .depAss-baob-operation {
        text-align: right;
        margin-bottom: 15px;
    }

    .mbxz {
        color: rgba(251, 164, 47, 1);
        font-size: 12px;
        cursor: pointer;
        font-weight: 500;
        font-family: sans-serif;
    }
</style>
<style>
    /* el-input修改 */
    .depAss-main-head .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .depAss-main-head .el-input__prefix {
        left: 8px;
        color: #262626;
        font-size: 14px;
    }

    /* element表单修改 */
    .depAss-main-head .el-form-item {
        margin-bottom: 0px;
    }

    .depAss-main-head .head-ID .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }

    .upload-batch .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }
</style>
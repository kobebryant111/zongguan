<!-- 主持经理考核评级 -->
<template>
    <div class="lzpg-container">
        <div class="target-head">
            <el-form inline>
                <div class="target-head-content xkg-borderradius-4px">
                    <div class="head-left target-head-left">
                        <el-row>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input v-model="paramsForm.tyear">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <!-- <el-col :span="4">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.centerid" @change="seaCenterChange">
                                        <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col> -->
                            <el-col :span="4">
                                <el-form-item>
                                    <div class="target-head-left-threeword">
                                        <el-select filterable v-model="paramsForm.depid">
                                            <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                            <template slot="prefix">二级部：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input v-model="paramsForm.eid">
                                        <template slot="prefix">工号：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input v-model="paramsForm.eidname">
                                        <template slot="prefix">姓名：</template>
                                    </el-input>
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
        <div ref="ygkhbody" class="target-body">
            <div class="yggl-body-operation">
                <el-button size="small" type="warning" @click="commit">提 交</el-button>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
                    <el-table-column label="二级部" width="200" prop="depname"></el-table-column>
                    <el-table-column label="工号" width="100" prop="eid"></el-table-column>
                    <el-table-column label="姓名" width="120" prop="eidname"></el-table-column>
                    <el-table-column label="角色" width="130" prop="rolename"></el-table-column>
                    <el-table-column label="初始总分" width="80" prop="initialtscore"></el-table-column>
                    <el-table-column label="初始排名" width="80" prop="initialorder"></el-table-column>
                    <el-table-column label="加分" width="80" prop="addscore"></el-table-column>
                    <el-table-column label="备注" width="200" prop="depremark"></el-table-column>
                    <el-table-column label="调整后总分" width="100" prop="deptscore"></el-table-column>
                    <el-table-column label="调整后排名" width="100" prop="deporder"></el-table-column>
                    <el-table-column label="操作" width="100" fixed="right">
                        <template slot-scope="scope">
                            <el-button @click="addScore(scope.row)">加 分</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index" v-model="$attrs"
                        ref="pager" />
                </div>
            </div>
        </div>
        <el-dialog title="加分" :visible.sync="ifShowJfDialog" center @close="addClose">
            <el-form label-position="left" label-width="80px" :model="updateSingData" ref="formXgSing" :rules="rules">
                <el-form-item label="加分" prop="addscore">
                    <el-input v-model="updateSingData.addscore" @input="jfChange" oninput="value=value.replace(/[^0-9.]/g, '')"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="depremark">
                    <el-input type="textarea" maxlength="300" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                        v-model="updateSingData.depremark"></el-input>
                </el-form-item>
            </el-form>
            <div class="hmddialog-bottom">
                <el-button @click="cancelXgSing">取 消</el-button>
                <el-button type="primary" @click="sureXgSing">保 存</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import pager from "@/components/Pager";
    import common from "@/api/common";
    import { getDepYearDataPager, addScore, submitDepYearConfirm } from "@/api/hykh/yearPj";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    export default {
        name: 'hostManagerKhpj',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            const validateAdd = (rule, value, callback) => {
                if (typeof value == 'number') {
                    value = value.toString()
                }
                if (!value) {
                    callback(new Error('请填写加分'))
                } else if (value.indexOf(".") != -1 && value.split('.').length > 2) {
                    callback(new Error('请输入正确格式'))
                } else if (value.indexOf(".") != -1 && value.split('.')[1].length > 2) {
                    callback(new Error('最多输入两位小数'))
                } else {
                    callback()
                }
            }
            return {
                paramsForm: {
                    tyear: null,
                    depid: null,
                    eid: null,
                    eidname: null,
                },
                depData: [],
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                targetData: [],
                tableHeight: null,
                ifShowJfDialog: false,
                updateSingData: {
                    addscore: null,
                    depremark: null
                }, //加分
                rules: {
                    addscore: [
                        { required: true, trigger: 'change', validator: validateAdd }
                    ],
                    depremark: [
                        { required: true, message: '请填写备注', trigger: 'blur' }
                    ]
                }
            }
        },
        mounted() {
            console.log('路由传值--主持经理', this.$route.params)
            this.getYear();
            let params = {};
            params.tyear = this.$route.params.tyear;
            params.term = '年度';
            params.role = 'COMM_USER';
            this.seaTableData(params);
            this.getKjDep();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ygkhbody.offsetHeight * 0.82
            })
        },
        methods: {
            // 获取年份
            getYear() {
                let date = new Date();
                this.paramsForm.tyear = date.getFullYear();
            },

            // 获取金融科技部门
            getKjDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    if (res.data.status == 200) {
                        var fild = res.data.results[0].children;
                        this.depData = fild.filter(tt => {
                            tt.text = tt.text.replace('总行金融科技部-', "");
                            tt.id = tt.id.replace('dep', '');
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params = this.paramsForm;
                params.term = '年度';
                params.role = 'COMM_USER';
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.tyear = this.paramsForm.tyear;
                params.depid = this.paramsForm.depid;
                params.term = '年度';
                params.role = 'COMM_USER';
                params.eid = this.paramsForm.eid;
                params.eidname = this.paramsForm.eidname;
                this.index = params.index = 1;
                params.size = this.size;
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getDepYearDataPager(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.targetData = res.data.results.filter(li => {
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', '')
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 加分
            addScore(val) {
                let rowData = JSON.parse(JSON.stringify(val));
                if (rowData.addscore != undefined) {
                    this.updateSingData.addscore = rowData.addscore
                }
                if (rowData.depremark != undefined) {
                    this.updateSingData.depremark = rowData.depremark
                }
                this.updateSingData.id = rowData.id
                this.ifShowJfDialog = true;
            },

            // 加分
            sureXgSing() {
                console.log("加分", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        if (this.updateSingData.addscore > 5) {
                            this.$message.error('加分不能超过5分');
                            return
                        }
                        const loading = common.loading(this);
                        let params = {};
                        params.id = this.updateSingData.id;
                        params.addscore = this.updateSingData.addscore;
                        params.depremark = this.updateSingData.depremark;
                        let data = common.paramsFormat(params);
                        addScore(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success(res.data.message);
                                this.ifShowJfDialog = false;
                                let params = {};
                                params.tyear = this.paramsForm.tyear;
                                params.depid = this.paramsForm.depid;
                                params.term = '年度';
                                params.role = 'COMM_USER';
                                params.eid = this.paramsForm.eid;
                                params.eidname = this.paramsForm.eidname;
                                this.index = params.index = 1;
                                params.size = this.size;
                                this.seaTableData(params);
                            } else {
                                this.$message.error(res.data.message)
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

            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formXgSing"].resetFields();
            },

            // 修改单人取消
            cancelXgSing() {
                this.ifShowJfDialog = false;
            },

            //加分变化
            jfChange(value) {
                console.log(value)
            },

            //提交
            commit() {
                const loading = common.loading(this);
                submitDepYearConfirm().then(res => {
                    loading.close();
                    console.log("提交", res.data)
                    if (res.data.status == 200) {
                        this.$message.success(res.data.message);
                        this.ifShowJfDialog = false;
                        let params = {};
                        params.tyear = this.paramsForm.tyear;
                        params.depid = this.paramsForm.depid;
                        params.term = '年度';
                        params.role = 'COMM_USER';
                        params.eid = this.paramsForm.eid;
                        params.eidname = this.paramsForm.eidname;
                        this.index = params.index = 1;
                        params.size = this.size;
                        this.seaTableData(params);
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            }
        }
    }
</script>
<style scoped>
    .lzpg-container {
        height: 100%;
        padding: 8px 15px 15px 15px;
    }

    .target-head {
        padding-bottom: 15px;
        border-radius: 4px;
    }

    .target-head-content {
        background: #FFFFFF;
        padding: 15px;
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
        line-height: 36px;
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

    .target-body {
        padding: 15px;
        background-color: #ffffff;
        height: calc(100vh - 195px);
        border-radius: 4px;
    }

    .hmddialog-bottom {
        text-align: center;
    }

    .pager-zj {
        text-align: center;
    }
</style>
<style>
    .target-head-left .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .target-head-left-threeword .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }

    .target-head-left .el-form-item {
        margin-bottom: 0;
    }
</style>
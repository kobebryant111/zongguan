<!-- 设置中心-平衡计分考核 -->
<template>
    <div class="phjfKh-container">
        <div class="target-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left ygkh-head-left">
                        <el-row>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-input v-model="paramsForm.tyear">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item>
                                    <div class="ygkh-head-left-threeword">
                                        <el-select filterable v-model="paramsForm.depid">
                                            <el-option v-for="(item, index) in depData" :key="index" :label="item.text"
                                                :value="item.id"></el-option>
                                            <template slot="prefix">二级部：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-input v-model="paramsForm.eid">
                                        <template slot="prefix">工号：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
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
                <el-button size="small" @click="exportEx">导 出</el-button>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
                    <el-table-column label="考核时间" width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.tyear}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="工号" width="100" prop="eid" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="姓名" width="100" prop="eidname"></el-table-column>
                    <el-table-column label="中心" width="230" prop="centername"></el-table-column>
                    <el-table-column label="二级部" width="230" prop="depname"></el-table-column>
                    <el-table-column label="角色" width="110" prop="rolename"></el-table-column>
                    <el-table-column label="扣分项" width="100" prop="decitems"></el-table-column>
                    <el-table-column label="扣分合计" width="100" prop="decvalues"></el-table-column>
                    <el-table-column label="操作" fixed="right">
                        <template slot-scope="scope">
                            <button-deep-pink buttonName="扣 分" size="mini" :parentData="scope.row" :fns="pointDed"></button-deep-pink>
                            <button-commons buttonName="扣分详情" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                :fns="enterDetail"></button-commons>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
        <!-- 扣分 -->
        <el-dialog title="平衡计分扣分" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <el-form label-position="left" label-width="120px" :model="updateSingData" ref="formXgSing">
                    <el-form-item label="考核项" prop="khxItem" :rules="[{ required: true, message: '请选择考核项', trigger: 'change'}]">
                        <el-select placeholder="考核项" v-model="updateSingData.khxItem" @change="khxChange" value-key="item">
                            <el-option v-for="(item, index) in phjfItemData" :key="index" :label="item.itemdescribe"
                                :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="考核内容" prop="khnrPlan" :rules="[{ required: true, message: '请选择考核内容', trigger: 'change'}]">
                        <el-select placeholder="考核内容" v-model="updateSingData.khnrPlan" @change="khnrChange" value-key="plan">
                            <el-option v-for="(item, index) in phjfItemDetailData" :key="index" :label="item.plandescribe"
                                :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="评分标准" prop="workremark" :rules="[{ required: true, message: '请填写评分标准', trigger: 'blur'}]">
                        <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 5}" placeholder="评分标准" v-model="updateSingData.workremark"></el-input>
                    </el-form-item>
                    <el-form-item label="扣分理由" prop="deducescorereason" :rules="[{ required: true, message: '请填写扣分理由', trigger: 'blur'}]">
                        <el-input type="textarea" maxlength="300" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                            placeholder="扣分理由" v-model="updateSingData.deducescorereason"></el-input>
                    </el-form-item>
                    <el-form-item label="扣分" prop="value" :rules="[{ required: true, message: '请填写分值', trigger: 'blur'}]">
                        <el-input placeholder="分值" v-model="updateSingData.value"></el-input>
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
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import Moment from 'moment';
    import pager from "@/components/Pager";
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import common from "@/api/common";
    import { getBalanceSummaryInfoPage, exportBalanceSummary, getBalanceItem, getBalancePlan, addKfItem } from "@/api/hykh/phjf";
    import { mapGetters } from 'vuex';
    import buttonCommons from '@/components/eleButton/buttonCommons';
    import ButtonDeepPink from '@/components/eleButton/ButtonDeepPink';
    export default {
        name: 'phjfKh',
        components: { cleanBtn, buttonOrange, buttonCommon, pager, buttonCommons, ButtonDeepPink },
        data() {
            return {
                paramsForm: {
                    tyear: null,
                    depid: null,
                    eid: null,
                    eidname: null
                },
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                depData: [],
                tableHeight: null,
                targetData: [],
                currentYear: null, //当前年份
                phjfItemData: [],
                phjfItemDetailData: [],
                ifShowXgSingDialog: false,
                updateSingData: {},
                khxItem: {},
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        mounted() {
            this.seaTableData();
            this.getKjDep();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ygkhbody.offsetHeight * 0.82
                console.log('测试高度', this.tableHeight)
            })
        },
        methods: {
            // 获取年份
            getYear() {
                let date = new Date();
                this.currentYear = date.getFullYear();
                let params = {};
                params.tyear = this.currentYear;
                params.eid = this.userId;
            },
            // 获取金融科技部门
            getKjDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    let fild = res.data.results[0].children;
                    this.depData = fild.filter(tt => {
                        tt.text = tt.text.replace('总行金融科技部-', "");
                        tt.id = tt.id.replace('dep', '');
                        return tt;
                    });
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.tyear = this.paramsForm.tyear;
                params.depid = this.paramsForm.depid;
                params.eid = this.paramsForm.eid;
                params.eidname = this.paramsForm.eidname;
                this.seaTableData(params);
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params = this.paramsForm;
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
                getBalanceSummaryInfoPage(data).then(res => {
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

            // 导出
            exportEx() {
                const loading = common.loading(this);
                // let params = {};
                // params.item = this.paramsForm.item;
                // params.centerid = this.paramsForm.centerid;
                let data = common.paramsFormat(this.paramsForm);
                const self = this;
                exportBalanceSummary(data).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "平衡计分.xls");
                });
            },

            // 查询考核项目表里面平衡计分项
            phjfItem(val) {
                const loading = common.loading(this);
                let params = {};
                params.eid = val.eid;
                params.tyear = val.tyear;
                let data = common.paramsFormat(params);
                getBalanceItem(data).then(res => {
                    loading.close();
                    console.log('查询考核项目表里面平衡计分项', res.data)
                    if (res.data.status == 200) {
                        this.phjfItemData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 考核项改变
            khxChange(val) {
                this.updateSingData.item = val.item;
                this.updateSingData.itemdescribe = val.itemdescribe;
                console.log('考核项改变', val, this.updateSingData)
                const loading = common.loading(this);
                let params = {};
                params.eid = this.updateSingData.eid;
                params.tyear = this.updateSingData.tyear;
                params.item = val.item;
                let data = common.paramsFormat(params);
                getBalancePlan(data).then(res => {
                    loading.close();
                    console.log('考核项改变', res.data)
                    if (res.data.status == 200) {
                        this.phjfItemDetailData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 考核内容改变
            khnrChange(val) {
                this.updateSingData.plan = val.plan;
                this.updateSingData.plandescribe = val.plandescribe;
                this.updateSingData.workremark = val.workremark;
                console.log('考核内容改变', val, this.updateSingData)
            },

            // 扣分
            pointDed(val) {
                this.updateSingData = JSON.parse(JSON.stringify(val));
                console.log("扣分", this.updateSingData)
                this.phjfItem(val);
                this.ifShowXgSingDialog = true;
            },

            // 扣分确定
            sureXgSing() {
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        if (this.updateSingData.value > 0) {
                            this.$message.error('扣分请输入负数');
                            return
                        } else if (-this.updateSingData.value > 100) {
                            this.$message.error('扣分最多100分');
                            return
                        }
                        const loading = common.loading(this);
                        let params = {};
                        params.tyear = this.updateSingData.tyear;
                        params.eid = this.updateSingData.eid;
                        params.item = this.updateSingData.item;
                        params.itemdescribe = this.updateSingData.itemdescribe;
                        params.plan = this.updateSingData.plan;
                        params.workremark = this.updateSingData.workremark;
                        params.plandescribe = this.updateSingData.plandescribe;
                        params.deducescorereason = this.updateSingData.deducescorereason;
                        params.value = this.updateSingData.value;
                        // params.value = params.value * -1;
                        params.value = params.value;
                        let data = common.paramsFormat(params);
                        addKfItem(data).then(res => {
                            loading.close();
                            console.log("扣分确定", res.data)
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

            // 进入详情
            enterDetail(val) {
                this.$router.push({
                    name: 'phjfKhDetail',
                    params: val
                })
            },

        }
    }
</script>
<style scoped>
    .phjfKh-container {
        padding: 15px;
    }

    .target-head {
        padding-bottom: 15px;
        border-radius: 4px;
    }

    .yggl-head-content {
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

    .pager-zj {
        text-align: center;
    }

    .target-body {
        padding: 15px;
        border-radius: 4px;
        background-color: #ffffff;
        height: calc(100vh - 162px);
    }

    .hmddialog-bottom {
        text-align: center;
    }
</style>
<style>
    .ygkh-head-left .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .ygkh-head-left-threeword .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }

    .ygkh-head-left .el-form-item {
        margin-bottom: 0;
    }
</style>
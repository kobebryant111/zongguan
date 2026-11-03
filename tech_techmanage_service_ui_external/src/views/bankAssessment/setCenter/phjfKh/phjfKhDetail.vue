<!-- 设置中心-平衡计分考核详情 -->
<template>
    <div class="phjfKh-container">
        <div ref="ygkhbody" class="target-body">
            <div class="yggl-body-operation">
                <div class="yggl-body-operation-left">平衡计分扣分详情</div>
                <div>
                    <el-button size="small" @click="exportEx">导 出</el-button>
                    <button-deep-pink class="xkg-marginleft-10px" buttonName="扣 分" size="small" :fns="pointDedAdd"></button-deep-pink>
                </div>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
                    <el-table-column label="考核项" min-width="15%" prop="itemdescribe"></el-table-column>
                    <el-table-column label="考核内容" min-width="15%" prop="plandescribe"></el-table-column>
                    <el-table-column label="评分标准" min-width="15%" prop="workremark"></el-table-column>
                    <el-table-column label="扣分" min-width="10%" prop="value"></el-table-column>
                    <el-table-column label="扣分理由" min-width="15%" prop="deducescorereason"></el-table-column>
                    <el-table-column label="扣分时间" min-width="15%" prop="regtime"></el-table-column>
                    <el-table-column label="操作" min-width="15%">
                        <template slot-scope="scope">
                            <button-commons buttonName="修 改" size="mini" :parentData="scope.row" :fns="pointDed"></button-commons>
                            <button-oranges buttonName="删 除" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                :fns="delSingle"></button-oranges>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 修改扣分项 -->
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
        <!-- 添加扣分项 -->
        <el-dialog title="平衡计分扣分" :visible.sync="ifShowTjDialog" @close="addClose">
            <div class="xgContent">
                <el-form label-position="left" label-width="120px" :model="updateTjData" ref="formAdd">
                    <el-form-item label="考核项" prop="khxItem" :rules="[{ required: true, message: '请选择考核项', trigger: 'change'}]">
                        <el-select placeholder="考核项" v-model="updateTjData.khxItem" @change="addkhxChange" value-key="item">
                            <el-option v-for="(item, index) in phjfItemData" :key="index" :label="item.itemdescribe"
                                :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="考核内容" prop="khnrPlan" :rules="[{ required: true, message: '请选择考核内容', trigger: 'change'}]">
                        <el-select placeholder="考核内容" v-model="updateTjData.khnrPlan" @change="addkhnrChange" value-key="plan">
                            <el-option v-for="(item, index) in phjfItemDetailData" :key="index" :label="item.plandescribe"
                                :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="评分标准" prop="workremark" :rules="[{ required: true, message: '请填写评分标准', trigger: 'blur'}]">
                        <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 5}" placeholder="评分标准" v-model="updateTjData.workremark"></el-input>
                    </el-form-item>
                    <el-form-item label="扣分理由" prop="deducescorereason" :rules="[{ required: true, message: '请填写扣分理由', trigger: 'blur'}]">
                        <el-input type="textarea" maxlength="300" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                            placeholder="扣分理由" v-model="updateTjData.deducescorereason"></el-input>
                    </el-form-item>
                    <el-form-item label="扣分" prop="value" :rules="[{ required: true, message: '请填写分值', trigger: 'blur'}]">
                        <el-input placeholder="分值" v-model="updateTjData.value"></el-input>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelAdd">取 消</el-button>
                    <el-button type="primary" @click="sureAdd">提 交</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import Moment from 'moment';
    import pager from "@/components/Pager";
    import cleanBtn from "@/components/CleanBtn";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import common from "@/api/common";
    import { exportBalanceDetail, getBalanceItem, getBalancePlan, getBalanceDeduceItem, del, update, addKfItem } from "@/api/hykh/phjf";
    import buttonCommons from '@/components/eleButton/buttonCommons';
    import ButtonDeepPink from '@/components/eleButton/ButtonDeepPink';
    export default {
        name: 'phjfKhDetail',
        components: { cleanBtn, buttonOranges, buttonCommon, pager, buttonCommons, ButtonDeepPink },
        data() {
            return {
                tableHeight: null,
                targetData: [],
                phjfItemData: [],
                phjfItemDetailData: [],
                ifShowXgSingDialog: false,
                ifShowTjDialog: false, //是否显示添加弹窗
                updateSingData: {
                    item: null,
                    plan: null,
                    deducescorereason: null,
                    value: null,
                    khnrPlan: null,
                    workremark: null
                },
                updateTjData: {
                    item: null,
                    plan: null,
                    deducescorereason: null,
                    value: null,
                    workremark: null
                }, //添加数据
                routerData: {},
            }
        },
        mounted() {
            console.log('路由传值平衡计分', this.$route.params)
            this.routerData = this.$route.params;
            this.seaTableData();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ygkhbody.offsetHeight * 0.86
                console.log('测试高度', this.tableHeight)
            })
        },
        methods: {
            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let params = {};
                params.tyear = this.routerData.tyear;
                params.eid = this.routerData.eid;
                let data = common.paramsFormat(params);
                getBalanceDeduceItem(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.targetData = res.data.results.filter(li => {
                            if (li.regtime != undefined) {
                                li.regtime = Moment(li.regtime).format('YYYY-MM-DD');
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
                const self = this;
                // let params = {};
                // params.tyear = this.routerData.tyear;
                // params.eid = this.routerData.eid;
                let data = common.paramsFormat({ eid: this.routerData.eid, tyear: this.routerData.tyear });
                exportBalanceDetail(data).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "平衡计分扣分详情.xls");
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

            // 修改考核项改变
            khxChange(val) {
                this.updateSingData.item = val.item;
                this.updateSingData.itemdescribe = val.itemdescribe;
                console.log('考核项改变', val, this.updateSingData)
                const loading = common.loading(this);
                let params = {};
                params.eid = this.routerData.eid;
                params.tyear = this.routerData.tyear;
                params.item = val.item;
                let data = common.paramsFormat(params);
                getBalancePlan(data).then(res => {
                    loading.close();
                    console.log('考核项改变', res.data)
                    if (res.data.status == 200) {
                        this.phjfItemDetailData = res.data.results;
                        this.phjfItemDetailData.splice(0, 0)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 考核内容改变
            khnrChange(val) {
                console.log('考核内容改变', val, this.updateSingData.khnrPlan)
                this.updateSingData.plan = val.plan;
                this.updateSingData.plandescribe = val.plandescribe;
                this.updateSingData.workremark = val.workremark;
                this.$forceUpdate()
            },

            // 扣分
            pointDed(val) {
                this.updateSingData = JSON.parse(JSON.stringify(val));
                this.updateSingData.khxItem = {};
                this.updateSingData.khxItem.item = this.updateSingData.item;
                this.updateSingData.khnrPlan = {};
                this.updateSingData.khnrPlan.plan = this.updateSingData.plan;
                console.log("扣分", this.updateSingData)
                // if (this.updateSingData.value != undefined) {
                //     this.updateSingData.value = this.updateSingData.value * -1
                // }
                this.phjfItem(val);
                this.khxChange(val);
                this.ifShowXgSingDialog = true;
            },

            // 修改单人确定
            sureXgSing() {
                console.log("修改单人确定", this.updateSingData)
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
                        params.tyear = this.routerData.tyear;
                        params.eid = this.routerData.eid;
                        params.id = this.updateSingData.id;
                        params.item = this.updateSingData.item;
                        params.itemdescribe = this.updateSingData.itemdescribe;
                        params.plan = this.updateSingData.plan;
                        params.workremark = this.updateSingData.workremark;
                        params.plandescribe = this.updateSingData.plandescribe;
                        params.deducescorereason = this.updateSingData.deducescorereason;
                        params.value = this.updateSingData.value;
                        // params.value = params.value * -1;
                        let data = common.paramsFormat(params);
                        update(data).then(res => {
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

            // 添加扣分项
            pointDedAdd() {
                this.phjfItem(this.routerData);
                this.ifShowTjDialog = true;
            },

            // 添加考核项改变
            addkhxChange(val) {
                this.updateTjData.item = val.item;
                this.updateTjData.itemdescribe = val.itemdescribe;
                console.log('考核项改变', val, this.updateTjData)
                const loading = common.loading(this);
                let params = {};
                params.eid = this.routerData.eid;
                params.tyear = this.routerData.tyear;
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
            addkhnrChange(val) {
                this.updateTjData.plan = val.plan;
                this.updateTjData.plandescribe = val.plandescribe;
                this.updateTjData.workremark = val.workremark;
                console.log('考核内容改变', val, this.updateTjData)
            },

            // 添加确定
            sureAdd() {
                console.log("添加确定", this.updateTjData)
                this.$refs["formAdd"].validate(valid => {
                    if (valid) {
                        if (this.updateTjData.value > 0) {
                            this.$message.error('扣分请输入负数');
                            return
                        } else if (-this.updateTjData.value > 100) {
                            this.$message.error('扣分最多100分');
                            return
                        }
                        const loading = common.loading(this);
                        let params = {};
                        params.tyear = this.routerData.tyear;
                        params.eid = this.routerData.eid;
                        params.item = this.updateTjData.item;
                        params.itemdescribe = this.updateTjData.itemdescribe;
                        params.plan = this.updateTjData.plan;
                        params.workremark = this.updateTjData.workremark;
                        params.plandescribe = this.updateTjData.plandescribe;
                        params.deducescorereason = this.updateTjData.deducescorereason;
                        params.value = this.updateTjData.value;
                       // params.value = params.value * -1;
                        let data = common.paramsFormat(params);
                        addKfItem(data).then(res => {
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

            // 删除（单）
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    del(val).then(res => {
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
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
    }

    .yggl-body-operation-left {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-size: 16px;
        color: #000333;
        line-height: 33px;
    }

    .pager-zj {
        text-align: center;
    }

    .target-body {
        padding: 15px;
        border-radius: 4px;
        background-color: #ffffff;
        height: calc(100vh - 90px);
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
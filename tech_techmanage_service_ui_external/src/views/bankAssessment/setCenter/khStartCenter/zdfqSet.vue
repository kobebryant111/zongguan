<!-- 自动发起项配置 -->
<template>
    <div class="khfq-container">
        <div class="khfq-head">
            <el-row>
                <el-col :span="6">
                    <el-select v-model="currentYear" @change="cliTag">
                        <el-option v-for="item in yearArr" :key="item" :label="item" :value="item"></el-option>
                        <template slot="prefix">年份：</template>
                    </el-select>
                </el-col>
            </el-row>
        </div>
        <div class="khfq-body">
            <h3 class="khfq-body-title">{{currentYear}}年考核发起</h3>
            <div class="khfq-body-cont">
                <el-table :data="khfqData">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="时间" prop="term" width="150"></el-table-column>
                    <el-table-column label="考核内容" prop="content" width="200"></el-table-column>
                    <el-table-column label="发起日期" prop="finishtime" width="200"></el-table-column>
                    <el-table-column label="结束日期" prop="endtime" width="105"></el-table-column>
                    <el-table-column label="开启状态" width="150">
                        <template slot-scope="scope">
                            <el-switch v-if="scope.row.isvisible == 0" v-model="scope.row.isswitch" active-color="#13ce66"
                                inactive-color="#ff4949" @change="swChange(scope.row)" :active-value="activeValue"
                                :inactive-value="inactiveValue"></el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <button-deepskyblue v-if="scope.row.itemtype != '12'" size="mini" buttonName="修 改"
                                :parentData="scope.row" :fns="corrSingle"></button-deepskyblue>
                        </template>
                    </el-table-column>
                    <el-table-column></el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 修改单人 -->
        <el-dialog :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <h3 class="jzTitle">{{currentYear}}考核发起</h3>
                <el-form :model="updateSingData" ref="formXgSing">
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>考核项</p>
                                        <el-form-item prop="content">
                                            <el-input type="textarea" autosize v-model="updateSingData.content"
                                                :disabled="true"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>半年度</p>
                                        <el-form-item prop="term">
                                            <el-input v-model="updateSingData.term" :disabled="true"></el-input>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>发起日期 *</p>
                                        <el-form-item prop="finishtime" :rules="[{ required: true, message: '请选择发起日期', trigger: 'change'}]">
                                            <el-date-picker v-model="updateSingData.finishtime" type="date" placeholder="请选择日期"
                                                value-format="yyyy-MM-dd"></el-date-picker>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>结束日期 *</p>
                                        <el-form-item prop="endtime" :rules="[{ required: true, message: '请选择结束日期', trigger: 'change'}]">
                                            <el-date-picker v-model="updateSingData.endtime" type="date" placeholder="请选择日期"
                                                value-format="yyyy-MM-dd"></el-date-picker>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelXgSing"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="确 定" size="mini" :fns="sureXgSing"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common";
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { getPpmKpiItemSwitchList, updatePpmKpiItemSwitch } from "@/api/hykh/khStart";
    export default {
        name: 'zdfqSet',
        components: { buttonDeepskyblue, buttonCommon, buttonOrange },
        data() {
            return {
                yearArr: [],
                currentYear: 2021,
                khfqData: [],
                activeValue: 1,
                inactiveValue: 0,
                updateSingData: {}, //修改单人数据
                ifShowXgSingDialog: false, //修改单人弹窗
            }
        },
        mounted() {
            this.init();
            this.seaTableData();
        },
        methods: {
            init() {
                let date = new Date();
                this.currentYear = date.getFullYear();
                this.yearArr = [date.getFullYear(), date.getFullYear() - 1];
            },
            // 点击tag
            cliTag() {
                this.seaTableData();
            },

            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ tyear: this.currentYear, itype: '1' });
                getPpmKpiItemSwitchList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.khfqData = res.data.results;
                        this.khfqData.splice(0, 0);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 开关变化
            swChange(val) {
                const loading = common.loading(this);
                let params = val;
                let data = common.paramsFormat(params);
                updatePpmKpiItemSwitch(data).then(res => {
                    loading.close();
                    console.log('开关变化', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('成功');
                        this.seaTableData();
                        this.$emit('updateSd');
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
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
                        params.finishtime = this.updateSingData.finishtime;
                        params.endtime = this.updateSingData.endtime;
                        let data = common.paramsFormat(params);
                        updatePpmKpiItemSwitch(data).then(res => {
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
        }
    }
</script>
<style scoped>
    .khfq-container {
        padding: 15px;
    }

    .khfq-head {
        padding-bottom: 15px;
    }

    .khfq-body {
        background: #FFFFFF;
        padding: 15px;
    }

    .khfq-body-title {
        font-size: 16px;
    }

    .hmd-row-block p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
    }

    .jzTitle {
        text-align: center;
    }

    .hmddialog-bottom {
        text-align: right;
    }
</style>
<style>
    .khfq-head .el-input__prefix {
        line-height: 36px;
    }

    .khfq-head .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }
</style>
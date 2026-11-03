<!-- 部门考核发起 -->
<template>
    <div class="khfq-container">
        <div class="khfq-body">
            <div class="khfq-body-cont">
                <el-table :data="khfqData">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="考核内容" prop="content" width="300"></el-table-column>
                    <el-table-column label="发起时间" prop="launchtime" width="105"></el-table-column>
                    <el-table-column label="当前状态" width="100">
                        <template slot-scope="scope">
                            <span v-if="scope.row.islaunch == 1">已发起</span>
                            <span v-else-if="scope.row.islaunch == 0">未发起</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope" v-if="scope.row.isdisabled==1">
                            <!-- <el-button type="primary" size="mini" @click="setTable(scope.row)">定时器配置</el-button> -->
                            <el-button type="warning" size="mini" @click="launchTable(scope.row)">发 起</el-button>
                            <!-- <el-button type="danger" size="mini" @click="closeTable(scope.row)">关 闭</el-button> -->
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>

        <el-dialog title="考核发起配置" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
                <div class="xgContent">
                    <el-form :model="updateSingData" ref="formXgSing">
                        <div>
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="24">
                                        <div>
                                            <p>考核内容 *</p>
                                            <el-form-item prop="content" >
                                                <el-input type="textarea" autosize placeholder="分档描述" v-model="updateSingData.content" :disabled="true"></el-input>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                                <el-row :gutter="50">
                                        <el-col :span="24">
                                            <div>
                                                <p>发起时间*</p>
                                                <el-form-item prop="autolaunchtime" >
                                                    <el-date-picker v-model="updateSingData.autolaunchtime"  type="date" placeholder="选择日期"></el-date-picker>
                                                </el-form-item>
                                            </div>
                                        </el-col>
                                </el-row>
                                <el-row :gutter="50">
                                        <el-col :span="24">
                                            <div>
                                                <p>发起周期 *</p>
                                                <el-form-item prop="launchtype" >
                                                    <el-radio-group v-model="updateSingData.launchtype">
                                                        <el-radio :label="0">只发起一次</el-radio>
                                                        <el-radio :label="1">每年</el-radio>
                                                        <el-radio :label="2">不固定时间</el-radio>
                                                    </el-radio-group>
                                                </el-form-item>
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
    import common from "@/api/common";
    import { getPpmDepKpiDepRelList, updatePpmDepKpiLaunchCenter,launchUrl} from "@/api/depAssessment/launchCenter";
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import Moment from 'moment';
    export default {
        name: 'launchCenter',
        components: { buttonCommon, buttonOrange},
        data() {
            return {
                ifShowXgSingDialog: false, //修改弹窗
                khfqData: [],
                updateSingData: {}, //修改数据
            }
        },
        props:{
            currentYear:{
                type:Number,
                required:true
            },
            
        },
        mounted() {
            this.seaTableData(this.currentYear);
        },
        methods: {
            // 查询列表数据
            seaTableData(val) {
                console.log('年份', val)
                const loading = common.loading(this);
                let data = common.paramsFormat({ tyear: val });
                getPpmDepKpiDepRelList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.khfqData = res.data.results.filter(li => {
                            if (li.launchtime != undefined) {
                                li.launchtime = Moment(li.launchtime).format('YYYY-MM-DD');
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },
                // 关闭
            closeTable(val) {
                const loading = common.loading(this);
                let params = val;
                params.isdisabled=0;
                console.log('关闭的行数据', params)
                let data = common.paramsFormat(params);
                updatePpmDepKpiLaunchCenter(data).then(res => {
                    loading.close();
                    console.log('开关变化', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('成功');
                        this.seaTableData(this.currentYear);
                        this.$emit('updateSd');
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },
            // 发起
            launchTable(val) {
                const loading = common.loading(this);
                let params = val;
                let url=params.launchurl;
                console.log('发起方法', url)
                let data = common.paramsFormat(params);
                launchUrl(data,url).then(res => {
                    loading.close();
                    console.log('发起返回', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('成功');
                        this.seaTableData(this.currentYear);
                        this.$emit('updateSd');
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 修改弹窗关闭回调
            xgSingClose() {
                this.$refs["formXgSing"].clearValidate();
            },
                // 修改取消
            cancelXgSing() {
                    this.ifShowXgSingDialog = false;
            },
                // 修改单人
            setTable(val) {
                console.log('修改', val)
                this.updateSingData = JSON.parse(JSON.stringify(val));
                this.ifShowXgSingDialog = true;
            },
                // 修改单人确定
            sureXgSing() {
                console.log("修改确定", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.id = this.updateSingData.id;
                        params.autolaunchtime = Moment(this.updateSingData.autolaunchtime).format('YYYY-MM-DD');
                        params.launchtype = this.updateSingData.launchtype;
                        let data = common.paramsFormat(params);
                        console.log("转换修改确定", data)
                        updatePpmDepKpiLaunchCenter(data).then(res => {
                            loading.close();
                            console.log("修改确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.ifShowXgSingDialog = false;
                                this.seaTableData(this.currentYear);
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


        }
    }
</script>
<style scoped>
    .khfq-container {
        padding: 15px;
    }

    .khfq-body {
        background: #FFFFFF;
    }

    .khfq-body-title {
        font-size: 16px;
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
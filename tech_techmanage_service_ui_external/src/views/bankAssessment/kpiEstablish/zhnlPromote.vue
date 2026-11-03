<!-- kpi制定-知识技能提升 -->
<template>
    <div class="kpisp-container">
        <div class="kpisp-body">
            <div class="kpisp-body-operate">
                <div class="kpisp-body-operate-left">
                    <i class="el-icon-view" @click="showHi">
                        <span class="xkg-marginleft-10px">{{ifShow ? '显示所有内容' : '隐藏所有内容'}}</span>
                    </i>
                </div>
                <div>
                    <el-button v-if="ifShowButt" size="small" @click="addTo">+添加指标</el-button>
                </div>
            </div>
            <div class="kpisp-body-content" :class="{ifWarp: !ifShow}">
                <el-table :data="khData">
                    <el-table-column label="序号" type="index" min-width="5%"></el-table-column>
                    <el-table-column label="待提升的知识技能" prop="itemdescribe" min-width="10%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="提升目标" prop="plandescribe" min-width="15%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="发展/学习活动计划" prop="workremark" min-width="20%" :show-overflow-tooltip="ifShow"></el-table-column>
                    <el-table-column label="计划完成时间" prop="finishdate" min-width="15%"></el-table-column>
                    <el-table-column label="状态" prop="itemstatusname" min-width="10%"></el-table-column>
                    <el-table-column label="操作" min-width="20%">
                        <template slot-scope="scope">
                            <!-- <div v-if="ifShowButt"> -->
                            <!-- <el-button v-if="scope.row.itemstatus == 0 || scope.row.itemstatus == 2" size="mini"
                                    @click="corrSingle(scope.row)">修 改</el-button>
                                <el-button size="mini" @click="delSingle(scope.row)">删 除</el-button> -->
                            <el-button size="mini" @click="corrSingle(scope.row)" v-if="scope.row.itemstatus == 0 || scope.row.itemstatus == 2 || scope.row.itemstatus == 3 || scope.row.itemstatus == 4">修
                                改</el-button>
                            <el-button size="mini" @click="delSingle(scope.row)" v-if="scope.row.itemstatus == 0 || scope.row.itemstatus == 2 || scope.row.itemstatus == 3 || scope.row.itemstatus == 4 || scope.row.itemstatus == 7">删
                                除</el-button>
                            <!-- </div> -->
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 添加 -->
        <el-dialog title="KPI配置" :visible.sync="ifShowTjDialog" @close="addClose">
            <div class="xgContent">
                <el-form label-position="left" label-width="145px" :model="updateTjData" ref="formAdd">
                    <el-form-item label="待提升的知识技能" prop="itemdescribe" :rules="[{ required: true, message: '请填写待提升的知识技能', trigger: 'blur'}]">
                        <el-input maxlength="10" show-word-limit placeholder="待提升的知识技能" v-model="updateTjData.itemdescribe"></el-input>
                    </el-form-item>
                    <el-form-item label="提升目标" prop="plandescribe" :rules="[{ required: true, message: '请填写提升目标', trigger: 'blur'}]">
                        <el-input type="textarea" maxlength="100" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                            placeholder="提升目标" v-model="updateTjData.plandescribe"></el-input>
                    </el-form-item>
                    <el-form-item label="发展/学习活动计划" prop="workremark" :rules="[{ required: true, message: '请填写发展/学习活动计划', trigger: 'blur'}]">
                        <el-input type="textarea" maxlength="100" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                            placeholder="发展/学习活动计划" v-model="updateTjData.workremark"></el-input>
                    </el-form-item>
                    <el-form-item label="计划完成时间" prop="finishdate" :rules="[{ required: true, message: '请选择计划完成时间', trigger: 'blur'}]">
                        <el-date-picker v-model="updateTjData.finishdate" type="date" placeholder="请选择日期" value-format="yyyy-MM-dd"
                            :picker-options="timeRange"></el-date-picker>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelAdd">取 消</el-button>
                    <el-button type="primary" @click="sureAdd">保 存</el-button>
                </div>
            </div>
        </el-dialog>
        <!-- 修改单人 -->
        <el-dialog title="知识技能提升" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <el-form label-position="left" label-width="145px" :model="updateSingData" ref="formXgSing">
                    <el-form-item label="待提升的知识技能" prop="itemdescribe" :rules="[{ required: true, message: '请填写待提升的知识技能', trigger: 'blur'}]">
                        <el-input maxlength="10" show-word-limit placeholder="待提升的知识技能" v-model="updateSingData.itemdescribe"
                            @blur="isSave"></el-input>
                    </el-form-item>
                    <el-form-item label="提升目标" prop="plandescribe" :rules="[{ required: true, message: '请填写提升目标', trigger: 'blur'}]">
                        <el-input type="textarea" maxlength="100" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                            placeholder="提升目标" v-model="updateSingData.plandescribe" @blur="isSave"></el-input>
                    </el-form-item>
                    <el-form-item label="发展/学习活动计划" prop="workremark" :rules="[{ required: true, message: '请填写发展/学习活动计划', trigger: 'blur'}]">
                        <el-input type="textarea" maxlength="100" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                            placeholder="发展/学习活动计划" v-model="updateSingData.workremark" @blur="isSave"></el-input>
                    </el-form-item>
                    <el-form-item label="计划完成时间" prop="finishdate" :rules="[{ required: true, message: '请选择计划完成时间', trigger: 'blur'}]">
                        <el-date-picker v-model="updateSingData.finishdate" type="date" placeholder="请选择日期"
                            value-format="yyyy-MM-dd" :picker-options="timeRange" @change="isSave"></el-date-picker>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelXgSing">取 消</el-button>
                    <el-button type="primary" @click="sureXgSing" :disabled="ifSave">保 存</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { getPpmKpiPmDetails, getKpiItems, addPpmKpiPmDetailItemFormulate, updatePpmKpiPmDetailFormulate, delKpi } from "@/api/hykh/myDb";
    import common from "@/api/common";
    import Moment from 'moment';
    export default {
        name: 'zhnlPromote',
        components: { buttonCommon, buttonOrange },
        data() {
            return {
                ifShow: true,
                ifShowXgSingDialog: false, //修改单人弹窗
                ifShowTjDialog: false, //是否显示添加弹窗
                updateSingData: {}, //修改单人数据
                updateTjData: {
                    itemdescribe: '',
                    plandescribe: '',
                    workremark: '',
                    finishdate: ''
                }, //添加数据
                khData: [], //列表数据
                zbData: [], //指标项数据
                lcData: {},
                startTime: '',
                endTime: '',
                timeRange: {
                    disabledDate: time => {
                        return time.getTime() < new Date(this.startTime).getTime() || time.getTime() > new Date(this.endTime).getTime();
                    }
                },
                routerData: {},
                ifSave: true
            }
        },
        props: {
            ifShowButt: {
                type: Boolean,
                required: true
            }
        },
        mounted() {
            console.log('路由传值子组件', this.$route.params)
            this.routerData = this.$route.params;
        },
        methods: {
            // 获取日期范围
            getDateRange(val) {
                if (val.term == '上半年') {
                    this.startTime = val.tyear + '-01-01';
                    this.endTime = val.tyear + '-06-30';
                } else if (val.term == '下半年') {
                    this.startTime = val.tyear + '-06-30';
                    this.endTime = val.tyear + '-12-31';
                } else {
                    this.startTime = val.tyear + '-01-01';
                    this.endTime = val.tyear + '-12-31';
                }
            },

            // 显示or隐藏内容
            showHi() {
                this.ifShow = !this.ifShow
            },

            // 获取考核信息
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '5' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取考核信息', res.data)
                    if (res.data.status == 200) {
                        this.khData = res.data.results.filter(li => {
                            if (li.finishdate != undefined) {
                                li.finishdate = Moment(li.finishdate).format('YYYY-MM-DD');
                            } else {
                                li.finishdate = ''
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取指标项
            getZbData(val) {
                this.getDateRange(val);
                this.lcData = val;
                this.$nextTick(() => {
                    this.seaTableData();
                })
            },

            // 删除（单）
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    delKpi({ id: val.id }).then(res => {
                        loading.close();
                        console.log("删除", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            // this.seaTableData();
                            this.$emit('getLcData', this.routerData)
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val);
                val.item = Number(val.item);
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
                        params.pid = this.lcData.id;
                        if (this.lcData.term != undefined) {
                            params.term = this.lcData.term;
                        }
                        params.tyear = this.lcData.tyear;
                        params.eid = this.lcData.eid;
                        params.kpitype = 5;
                        params.itemdescribe = this.updateSingData.itemdescribe;
                        params.plandescribe = this.updateSingData.plandescribe;
                        params.workremark = this.updateSingData.workremark;
                        params.finishdate = this.updateSingData.finishdate;
                        let data = common.paramsFormat(params);
                        updatePpmKpiPmDetailFormulate(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.ifShowXgSingDialog = false;
                                // this.seaTableData();
                                this.$emit('getLcData', this.routerData)
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
                this.ifSave = true;
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
                        params.pid = this.lcData.id;
                        if (this.lcData.term != undefined) {
                            params.term = this.lcData.term;
                        }
                        params.tyear = this.lcData.tyear;
                        params.eid = this.lcData.eid;
                        params.kpitype = 5;
                        params.itemdescribe = this.updateTjData.itemdescribe;
                        params.plandescribe = this.updateTjData.plandescribe;
                        params.workremark = this.updateTjData.workremark;
                        params.finishdate = this.updateTjData.finishdate;
                        let data = common.paramsFormat(params);
                        addPpmKpiPmDetailItemFormulate(data).then(res => {
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

            // 修改保存按钮是否可用
            isSave() {
                this.ifSave = false;
            }
        }
    }
</script>
<style scoped>
    .kpisp-body-operate {
        display: flex;
        justify-content: space-between;
        margin-bottom: 15px;
    }

    .kpisp-body-operate-left {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        line-height: 32px;
    }

    .el-icon-view {
        font-size: 12px;
        color: #8E8E8E;
        cursor: pointer;
    }

    .hmddialog-bottom {
        text-align: center;
    }
</style>
<style>
    .ifWarp .el-table .cell {
        white-space: pre-wrap;
    }
</style>
<!-- 一级部管理 -->
<template>
    <div>
        <el-table :data="oneData" height="510" @selection-change="handleSelectionChange">
            <el-table-column type="selection" min-width="10%"></el-table-column>
            <el-table-column type="index" label="序号" min-width="10%"></el-table-column>
            <el-table-column label="一级部" min-width="20%" prop="depname"></el-table-column>
            <el-table-column label="制定人工号" min-width="10%" prop="markername">
                <template slot-scope="scope">
                    <div v-if="scope.row.marker">{{(scope.row.markername ? scope.row.markername : '') + '(' + scope.row.marker +')'}}</div>
                </template>
            </el-table-column>
            <el-table-column label="初评/初审人" min-width="10%" prop="firstcheckname">
                <template slot-scope="scope">
                    <div v-if="scope.row.firstcheck">{{(scope.row.firstcheckname ? scope.row.firstcheckname : '') + '(' + scope.row.firstcheck +')'}}</div>
                </template>
            </el-table-column>
            <el-table-column label="终评/终审人" min-width="10%" prop="secondcheckname">
                <template slot-scope="scope">
                    <div v-if="scope.row.secondcheck">{{(scope.row.secondcheckname ? scope.row.secondcheckname : '') + '(' + scope.row.secondcheck +')'}}</div>
                </template>
            </el-table-column>
            <el-table-column label="操作" min-width="20%" fixed="right">
                <template slot-scope="scope">
                    <div>
                        <button-deepskyblue size="mini" buttonName="修 改" :parentData="scope.row" :fns="corrSingle"></button-deepskyblue>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog title="组织管理" :visible.sync="ifShowXgSingDialog" @close="xgSingClose" width="30%">
            <div class="onePzManage-xgdia">
                <el-form :model="updateSingData" ref="formXgSing" >
                    <div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>部门名称*</p>
                                        <el-input disabled v-model="updateSingData.depname"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>考核制定人*</p>
                                        <el-form-item prop="marker" :rules="[{ required: true, message: '请选择考核制定人', trigger: 'change'}]">
                                            <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                v-model="updateSingData.marker" :show-all-levels="false"
                                                filterable></el-cascader>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="hmd-row-block">
                            <el-row :gutter="50">
                                <el-col :span="12">
                                    <div>
                                        <p>初审/初评人</p>
                                        <el-form-item prop="firstcheck" :rules="[{ required: false, message: '请选择初审/初评人', trigger: 'change'}]">
                                            <el-cascader placeholder="姓名" ref="cascader" :options="kjPeo_options"
                                                :props="kjPeoParams" v-model="updateSingData.firstcheck" clearable
                                                :show-all-levels="false" filterable></el-cascader>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="12">
                                    <div>
                                        <p>终审/终评人*</p>
                                        <el-form-item prop="secondcheck" :rules="[{ required: true, message: '请选择终审/终评人', trigger: 'change'}]">
                                            <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                v-model="updateSingData.secondcheck" :show-all-levels="false"
                                                filterable></el-cascader>
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
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
    import common from "@/api/common";
    import { queryPagerList } from "@/api/depAssessment/depAssessmentControl";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import { updatePpmDepKpiDepRelDtos } from "@/api/depAssessment/depAssessmentControl";
    export default {
        name: 'onePzManage',
        components: { buttonDeepskyblue, buttonOrange, buttonCommon },
        data() {
            return {
                oneData: [],
                selectRow: [], //表格选中行
                ifShowXgSingDialog: false, //修改单人弹窗
                updateSingData: {}, //修改单人数据
                kjPeoParams: { //级联选择器配置信息
                    label: 'text',
                    value: 'pcode',
                    children: 'children',
                    emitPath: false,
                },
                kjPeo_options: [],
            }
        },
        mounted() {
            this.searchEpmlo();
            this.seaTableData({});
        },
        methods: {
            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                val.sign = '3';
                let data = common.paramsFormat(val);
                queryPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.oneData = res.data.results.filter(li => {
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', '');
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 表格选中变化
            handleSelectionChange(val) {
                console.log("分页表格选中", val)
                this.selectRow = val;
            },

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
                val.sign = val.sign.toString();
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
                        params.ids = this.updateSingData.id;
                        params.marker = this.updateSingData.marker;
                        params.firstcheck = this.updateSingData.firstcheck;
                        params.secondcheck = this.updateSingData.secondcheck;
                        params.sign = 3;
                        let data = common.paramsFormat(params);
                        updatePpmDepKpiDepRelDtos(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.ifShowXgSingDialog = false;
                                this.seaTableData({});
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

            // 查询行员
            searchEpmlo() {
                let params = '';
                params = "7004";
                queryEmployeeTree(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            this.kjPeo_options = res.data.results[0].children.filter(tt => {
                                tt.text = tt.text.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
            },
        }
    }
</script>
<style scoped>
    .hmddialog-bottom {
        text-align: center;
    }
</style>
<style>
    .onePzManage-xgdia .el-cascader {
        display: block;
    }
</style>
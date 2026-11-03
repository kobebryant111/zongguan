<!-- 一级部管理 -->
<template>
    <div>
        <el-table :data="oneData" height="510" @selection-change="handleSelectionChange">
            <el-table-column type="selection" min-width="10%"></el-table-column>
            <el-table-column type="index" label="序号" min-width="10%"></el-table-column>
            <el-table-column label="一级部" min-width="20%" prop="depname"></el-table-column>
            <el-table-column label="一级部总经理工号" min-width="20%" prop="director"></el-table-column>
            <el-table-column label="一级部总经理" min-width="20%" prop="directorname"></el-table-column>
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
                <el-form :model="updateSingData" ref="formXgSing" label-position="left" label-width="60px">
                    <el-form-item label="名称">
                        <el-input disabled v-model="updateSingData.depname"></el-input>
                    </el-form-item>
                    <el-form-item label="总经理">
                        <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams" v-model="updateSingData.director"
                            :show-all-levels="false" filterable></el-cascader>
                    </el-form-item>
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
    import { queryPagerList } from "@/api/integrated/hykhOrg";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import { updatePpmKpiRoleDtos } from "@/api/integrated/hykhOrg";
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
                        params.director = this.updateSingData.director;
                        params.sign = this.updateSingData.sign;
                        let data = common.paramsFormat(params);
                        updatePpmKpiRoleDtos(data).then(res => {
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
<!-- 评价设置中心 -->
<template>
    <div class="grade-container">
        <div class="grade-body">
            <h3 class="grade-body-title">层级设置中心</h3>
            <div class="grade-body-operate">
                <el-button type="warning" size="small" @click="save">保 存</el-button>
            </div>
            <el-form ref="form" :model="eosDepKpiGrade" label-width="200px" label-position="right">
                <el-form-item label="二级部框架层级" :rules="[{ required: true, message: '二级部框架层级设置', trigger: 'blur'}]" style="text-align: left;">
                    <el-input-number v-model="eosDepKpiGrade.dep" :min="1" :max="10" style="width:200px" label="二级部框架层级设置" size="medium"></el-input-number>
                </el-form-item>

                <el-form-item label="中心框架层级" :rules="[{ required: true, message: '中心框架层级', trigger: 'blur'}]" style="text-align: left;">
                    <el-input-number v-model="eosDepKpiGrade.cen" :min="1" :max="10" style="width:200px" label="中心框架层级设置" size="medium"></el-input-number>
                </el-form-item>

                <el-form-item label="一级部框架层级" :rules="[{ required: true, message: '一级部框架层级', trigger: 'blur'}]" style="text-align: left;">
                    <el-input-number v-model="eosDepKpiGrade.tec" :min="1" :max="10"  style="width:200px" label="一级部框架层级设置" size="medium"></el-input-number>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { saveEosDepKpiGrade, getEosDepKpiGrade } from "@/api/depAssessment/depAssessmentControl";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    export default {
        name: 'gradeCenter',
        components: { buttonOranges, buttonDeepskyblue, buttonCommon, buttonOrange },
        data() {
            return {
                eosDepKpiGrade :{},
            }
        },
        mounted() {
            this.seaTableData();
        },
        methods: {
            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                getEosDepKpiGrade().then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.eosDepKpiGrade=res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

             // 添加确定
             save() {
                console.log("保存", this.eosDepKpiGrade)
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let data = common.paramsFormat(this.eosDepKpiGrade);
                        saveEosDepKpiGrade(data).then(res => {
                            loading.close();
                            console.log("保存确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('保存成功');
                                this.seaTableData();
                            } else {
                                this.$message.error('保存失败')
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
    .grade-container {
        padding: 15px;
        background: #FFFFFF;
    }

    .grade-head {
        padding-bottom: 15px;
    }

    /* .pjsz-body {
        background: #FFFFFF;
    } */

    .grade-body-title {
        font-size: 16px;
    }

    .grade-body-operate {
        text-align: right;
        margin-bottom: 10px;
    }

    .hmddialog-bottom {
        text-align: right;
    }

    .hmd-row-block p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
    }
 
</style>
<!-- 部门模板分配 -->
<template>
    <div class="web-container">
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-table">
                <el-table :data="tableData" height="470" @selection-change="handleSelectionChange">
                    <el-table-column label="一级部" width="300" prop="depname"></el-table-column>
                    <el-table-column label="提交状态" width="100" prop="commitflag">
                        <template slot-scope="scope">
                            <span v-if="scope.row.commitflag == 1">生效</span>
                            <span v-else>草稿</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="220" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <el-button type="primary" size="mini" @click="indicatorItemsJump(scope.row)">指标项配置</el-button>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column></el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { queryFirDep } from "@/api/zzkhApi/depModel";
    import pager from "@/components/Pager";
    export default {
        name: 'depModel',
        components: { },
        data() {
            return {
                sign: 3, // 所属为一级部
                tableData: [],
            }
        },
        created(){},
        mounted() {
            this.seaTableData(); // 获取表格数据
        },
        methods: {
            // 查询列表数据
            seaTableData(val) {
                if (!val) {
                    val = {};
                }
                val.sign = this.sign;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryFirDep(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.tableData = res.data.results.filter(tt => {
                            if(tt.depname){
                                tt.depname = tt.depname.replace('总行金融科技部-', "");
                            }
                            return tt;
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 表格选中变化
            handleSelectionChange(val) {
                // console.log("分页表格选中", val)
                this.selectRow = val;
            },

            // 指标项配置页面跳转
            indicatorItemsJump(val) {
                this.$router.push({ path: '/yjbTargetAssign' });
            },
        },
        watch: {},
    }
</script>
<style scoped>
    .web-container {
        padding: 15px;
    }

    .web-head-content {
        background: #FFFFFF;
        padding: 15px 0 15px 15px;
        margin-bottom: 15px;
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

    .web-body {
        padding: 15px;
        background: #FFFFFF;
    }

    .web-body-operation {
        text-align: right;
        margin-bottom: 10px;
    }

    .pager-zj {
        text-align: center;
    }

    .hmddialog-bottom {
        text-align: right;
    }

</style>

<style>
    .web-head-content .el-input--prefix .el-input__inner {
        padding-left: 55px;
    }

    .web-head-content .el-form-item {
        margin-bottom: 0;
    }

    .upload-butt .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }

    .xgContent .el-cascader {
        display: block;
    }
    .butt-deepskyblue button {
        width: 76px !important;
    }
</style>
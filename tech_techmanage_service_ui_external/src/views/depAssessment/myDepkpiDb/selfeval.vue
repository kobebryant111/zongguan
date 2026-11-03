<!-- 模板管理 -->
<template>
    <div class="web-container">
        <div class="back-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
        </div>
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-table">
                <el-table :data="tableData" height="510">
                    <el-table-column label="部门" width="220" prop="depname"></el-table-column>
                    <el-table-column label="操作" width="150" fixed="right" align="center">
                        <template slot-scope="scope">
                            <div>
                                <el-button class="xkg-marginleft-10px" size="mini" @click="indicatorItemsJump(scope.row)">自评</el-button>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { queryZdyzbzpPagerList } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import pager from "@/components/Pager";
    export default {
        name: 'selfeval',
        components: { pager },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 总数
                tableData: [],
                sign: null,
            }
        },
        props:{},
        created(){
            this.sign = this.$route.query.sign;
            this.tyear = this.$route.query.tyear;
        },
        mounted() {
            this.submitBtn();
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.push({path:"/myDepKpiDb"});
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.size = this.size;
                params.index = this.index;
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData() {
                var val = {};
                val.sign = this.sign;
                val.tyear = this.tyear;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryZdyzbzpPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.tableData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

             // 指标项配置页面跳转
            indicatorItemsJump(val) {
                this.$router.push({ path: '/selfevaldetail', query: {id: val.depid, sign: this.sign, name: val.depname, tyear: this.tyear}});
            }
        },
    }
</script>
<style scoped>
    .web-container {
        padding: 25px;
    }

    .web-head-content {
        background: #FFFFFF;
        padding: 15px;
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

    .back-bread {
        margin-bottom: 15px;
    }

    .back-bread .el-breadcrumb {
        display: inline-block;
        vertical-align: middle;
        margin-left: 10px;
    }

    .web-body {
        padding: 15px;
        background: #FFFFFF;
    }

    .pager-zj {
        text-align: center;
    }

    ::v-deep .el-table__empty-block {
        width: 100%;
        min-width: 100%;
        max-width: 100%;
    }
</style>
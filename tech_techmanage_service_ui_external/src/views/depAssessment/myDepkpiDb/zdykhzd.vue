<!-- 模板管理 -->
<template>
    <div class="web-container">
        <div class="back-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
        </div>
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-table">
                <el-table :data="tableData" height="510" @selection-change="handleSelectionChange">
                    <el-table-column label="部门" width="260" prop="depname">
                        <template slot-scope="scope">
                            <div>
                                <span>{{scope.row.depname}}</span>
                                <span v-if="scope.row.suggest" class="back-text">退回</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="待配置项" width="800" prop="targetnamelist" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="操作" width="150" fixed="right" align="center">
                        <template slot-scope="scope">
                            <div>
                                <el-button class="xkg-marginleft-10px" size="mini" @click="indicatorItemsJump(scope.row)">内容配置</el-button>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column></el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import common from "@/api/common";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPagerList, add, update, deleteTarget } from "@/api/zzkhApi/target";
    import { queryModelPagerList, addModel, updateModel, deleteModel } from "@/api/zzkhApi/model";
    import { queryZdyzbzdPagerList } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import pager from "@/components/Pager";
    export default {
        name: 'zdykhzd',
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
                queryZdyzbzdPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        if(res.data.results.length == 0){
                            this.$router.push({path:"/myDepKpiDb"});
                        }
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
                this.$router.push({ path: '/khzdmx', query: {id: val.depid, sign: this.sign, name: val.depname, tyear: this.tyear, suggest: val.suggest}});
            },

            // 表格选中变化
            handleSelectionChange(val) {
                // console.log("分页表格选中", val)
                this.selectRow = val;
            },
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
    .back-text {
        color: white;
        background-color: red;
        border-radius: 3px;
        padding: 2px;
        font-size: 10px;
    }
</style>
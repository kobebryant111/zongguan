<!-- 模板管理 -->
<template>
    <div class="web-container">
        <div class="back-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
        </div>
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-operation">
                <el-button size="small" @click="exportEx">导 出</el-button>
            </div>
            <div class="web-body-table">
                <el-table :data="tableData" height="510">
                    <el-table-column v-if="this.sign == 2" label="中心" width="220" prop="centername"></el-table-column>
                    <el-table-column v-if="this.sign == 1" label="部门" width="220" prop="depname"></el-table-column>
                    <el-table-column label="状态" width="220">
                        <template slot-scope="scope"><span v-if="scope.row">评价中</span></template>
                    </el-table-column>
                    <el-table-column label="得分" width="220" prop="ggpoint"></el-table-column>
                    <el-table-column label="操作" width="150" fixed="right" align="center">
                        <template slot-scope="scope">
                            <div>
                                <el-button size="mini" @click="indicatorItemsJump(scope.row)">详情</el-button>
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
    import { queryPjPagerList, exportPjPagerList } from "@/api/zzkhApi/ppmDepkpiDepCheck";
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
                val.targettype = 1;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryPjPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.tableData = res.data.results.filter(li => {
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', '')
                            }
                            if (li.centername != undefined) {
                                li.centername = li.centername.replace('总行金融科技部-', '')
                            }
                            return li
                        });
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
                let id;
                if(this.sign == 1){
                    id = val.depid;
                }else {
                    id = val.centerid;
                }
                this.$router.push({ path: '/ggevaldetail', query: {id: id, sign: this.sign, tyear: this.tyear}});
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let val = {};
                val.sign = this.sign;
                val.tyear = this.tyear;
                val.targettype = 1;
                let params = common.paramsFormat(val);
                const self = this;
                exportPjPagerList(params).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "公共评价.xls");
                });
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

    .web-body-operation {
        float: right;
    }
</style>
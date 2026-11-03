<!-- 模板管理 -->
<template>
    <div class="web-container">
        <div class="back-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
        </div>
        <div class="web-head">
            <el-form inline>
                <div class="web-head-content xkg-borderradius-4px">
                    <div class="web-head-left">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item>
                                    <el-input v-model="paramsForm.name">
                                        <template slot="prefix">考核项：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="web-head-right">
                        <div class="web-head-right-clean" @click="resetForm">
                            <clean-btn size="small" :forms="this.paramsForm" />
                        </div>
                        <div class="web-head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-table">
                <el-table :data="tableData" height="510" @selection-change="handleSelectionChange">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="id" width="60" prop="targetid" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="考核项" width="300" prop="name" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <div>
                                <span>{{scope.row.name}}</span>
                                <span v-if="scope.row.suggest" class="back-text">退回</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="基准分" width="200" prop="basepoint"></el-table-column>
                    <el-table-column label="指标使用部门" width="300" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            {{scope.row.depnamelist.join(',').replace(/总行金融科技部-/g, '')}}
                        </template>
                    </el-table-column>
                    <el-table-column label="评价方式" width="100" prop="evaltype">
                        <template slot-scope="scope">
                            <span v-if="!scope.row.evaltype">-</span>
                            <span v-else-if="scope.row.evaltype == 1">档位评分</span>
                            <span v-else-if="scope.row.evaltype == 2">任意分值</span>
                            <span v-else-if="scope.row.evaltype == 3">其他（加分/倒扣分）</span>
                        </template>
                    </el-table-column>
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
    import common from "@/api/common";
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryGgzbzdPagerList } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import pager from "@/components/Pager";
    export default {
        name: 'ggkhzd',
        components: { pager, cleanBtn, buttonOrange },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 总数
                tableData: [],
                sign: null,
                tyear: null,
                paramsForm: {
                    name:  null
                }
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
                params.name = this.paramsForm.name;
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData(val) {
                val.sign = this.sign;
                val.tyear = this.tyear;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryGgzbzdPagerList(data).then(res => {
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

            // 重置
            resetForm() {
            },

             // 指标项配置页面跳转
            indicatorItemsJump(val) {
                this.$router.push({ 
                    path: '/ggkhzdmx', 
                    query: {
                        targetid: val.targetid, 
                        basepoint: val.basepoint,
                        evaltype: val.evaltype,
                        maxpoint: val.maxpoint,
                        minpoint: val.minpoint,
                        detailidlist: JSON.stringify(val.idlist),
                        suggest: val.suggest, 
                        sign: this.sign, 
                        name: val.name, 
                        tyear: this.tyear
                    }
                });
            },

            // 表格选中变化
            handleSelectionChange(val) {
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

    .web-head-left {
        flex: 1;
    }
    .web-head-right {
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        padding-right: 15px;
        width: 145px;
        display: flex;
    }

    .web-head-right-clean {
        flex: 1;
    }

    .web-head-right-search {
        padding-left: 10px;
        flex: 1;
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

<style>
    .web-head-left .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .web-head-left-threeword .el-input--prefix .el-input__inner {
        padding-left: 85px;
    }

    .web-head-left .el-form-item {
        margin-bottom: 0;
    }
</style>
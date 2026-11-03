<!-- 待办KPI评价列表 -->
<template>
    <div class="pjlist-container">
        <div class="kh-back-bread">
            <div class="kh-back-bread-butt">
                <el-button size="small" icon="el-icon-arrow-left" @click="lastPage">返回</el-button>
            </div>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-for="(item,index) in routesMeta" :key="index">
                    <span :class="[routesMeta.length-1 == index ? 'lastJc' : 'crumbOther']">{{item}}</span>
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="target-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left ygkh-head-left">
                        <el-row>
                            <!-- <el-col :span="4">
                                <el-form-item>
                                    <el-input placeholder="年份" v-model="paramsForm.tyear">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col> -->
                            <!-- <el-col :span="4">
                                <el-form-item>
                                    <el-select placeholder="周期" filterable v-model="paramsForm.term">
                                        <el-option v-for="(item, index) in termData" :key="index" :label="item.term"
                                            :value="item.term"></el-option>
                                        <template slot="prefix">周期：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col> -->
                            <el-col :span="5">
                                <el-form-item>
                                    <div class="ygkh-head-left-threeword">
                                        <el-select placeholder="二级部" filterable v-model="paramsForm.depid">
                                            <el-option v-for="(item, index) in depData" :key="index" :label="item.text"
                                                :value="item.id"></el-option>
                                            <template slot="prefix">二级部：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-input placeholder="工号" v-model="paramsForm.eid">
                                        <template slot="prefix">工号：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-input placeholder="姓名" v-model="paramsForm.eidname">
                                        <template slot="prefix">姓名：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
                            <clean-btn size="small" :forms="this.paramsForm" />
                        </div>
                        <div class="head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div ref="ygkhbody" class="target-body">
            <div style="text-align: right">
                <buttonOrange buttonName="全部提交" size="small" :fns="allSubmit"></buttonOrange>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight" :row-class-name="tableRowClassName">
                    <!-- <el-table-column type="index" label="序号" width="55"></el-table-column> -->
                    <el-table-column label="年份" width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.tyear}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="周期" width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.term != undefined ? scope.row.term : '年度'}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="中心" width="160" prop="centername"></el-table-column>
                    <el-table-column label="二级部" width="200" prop="depname"></el-table-column>
                    <el-table-column label="工号" width="80" prop="eid"></el-table-column>
                    <el-table-column label="姓名" width="100" prop="eidname"></el-table-column>
                    <el-table-column label="角色" width="110" prop="rolename" v-if="routerData.role != 'COMM_USER'"></el-table-column>
                    <!-- <el-table-column label="待评价项" width="100" prop="evalitems"></el-table-column> -->
                    <el-table-column label="KPI考核" width="100" prop="kpiscore"></el-table-column>
                    <el-table-column label="综合能力评估" width="110" prop="comprehensivescore" v-if="routerData.role == 'COMM_USER'"></el-table-column>
                    <el-table-column label="履职评估" width="100" prop="performanceevaluationscore" v-if="routerData.role != 'COMM_USER'"></el-table-column>
                    <el-table-column label="年度总分" width="110" prop="expectscore"></el-table-column>
                    <el-table-column label="操作" width="150" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <el-button size="mini" @click="enterDetail(scope.row)">进入评价</el-button>
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
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import pager from "@/components/Pager";
    import { getPpmKpiPmEvalInfoPage, totalSubmit } from "@/api/hykh/kpiEval";
    import common from "@/api/common";
    export default {
        name: 'dbkpiPjList',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    // tyear: null,
                    // term: null,
                    depid: null,
                    eid: null,
                    eidname: null,
                },
                // termData: [
                //     { term: '上半年' },
                //     { term: '下半年' },
                //     { term: '年度' },
                // ],
                depData: [],
                tableHeight: null,
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                targetData: [],
                routesMeta: [],
                routerData: {}
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "myKpiDb" || from.name == "comprehensivePpp") { 
                    if (vm.routerData.term == "全年度") {
                        vm.routesMeta = ['我的待办', '经理' + vm.routerData.tyear + '年度考核']
                    } else {
                        vm.routesMeta = ['我的待办', '员工' + vm.routerData.tyear + vm.routerData.term + '考核']
                    }
                }
            })
        },
        mounted() { 
            let valueTrans = {};
            if (JSON.stringify(this.$route.params) == '{}') {
                valueTrans = JSON.parse(sessionStorage.getItem('hykh_db_kpiEval_jump_data'))
            } else {
                valueTrans = this.$route.params;
            }
            // if (valueTrans.term == undefined) {
            //     valueTrans.term = ''
            // }
            this.routerData = valueTrans;
            this.$nextTick(() => {
                this.seaTableData(valueTrans);
                this.getKjDep();
            })
        },
        methods: {
            // 设置表格行颜色
            tableRowClassName(row) {
                if (row.row.nofillitem == 0) {
                    return 'pink-row'
                }
                return ''
            },

            // 获取金融科技部门
            getKjDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    let fild = res.data.results[0].children;
                    this.depData = fild.filter(tt => {
                        tt.text = tt.text.replace('总行金融科技部-', "");
                        tt.id = tt.id.replace('dep', '');
                        return tt;
                    });
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 查询
            submitBtn: function () {
                let params = {};
                // params.tyear = this.paramsForm.tyear;
                // params.term = this.paramsForm.term;
                // params.depid = this.paramsForm.depid;
                // params.eid = this.paramsForm.eid;
                // params.eidname = this.paramsForm.eidname;
                params = this.paramsForm;
                if (this.routerData.role != undefined) {
                    params.role = this.routerData.role;
                } else {
                    params.roles = this.routerData.roles;
                }
                params.tyear = this.routerData.tyear;
                params.term = this.routerData.term;
                this.seaTableData(params);
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params = this.paramsForm;
                if (this.routerData.role != undefined) {
                    params.role = this.routerData.role;
                } else {
                    params.roles = this.routerData.roles;
                }
                params.tyear = this.routerData.tyear;
                params.term = this.routerData.term;
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getPpmKpiPmEvalInfoPage(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.targetData = res.data.results.filter(li => {
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', '')
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 进入评价
            enterDetail(val) {
                this.$router.push({
                    name: 'comprehensivePpp',
                    params: val,
                })
            },

            // 返回
            lastPage() {
                this.$router.back()
            },

            // 全部提交
            allSubmit() {
                const loading = common.loading(this);
                let params = JSON.parse(JSON.stringify(this.routerData))
                let data = common.paramsFormat(params);
                totalSubmit(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$message.success('提交成功');
                        this.$router.back()
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            }
        }
    }
</script>
<style scoped>
    .pjlist-container {
        height: 100%;
        padding: 8px 15px 15px 15px;
    }

    .target-head {
        padding-bottom: 15px;
        border-radius: 4px;
        margin-top: 8px;
    }

    .yggl-head-content {
        background: #FFFFFF;
        padding: 15px;
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
        width: 135px;
        display: flex;
    }

    .head-right-clean {
        flex: 1;
    }

    .head-right-search {
        padding-left: 10px;
        flex: 1;
    }

    .pager-zj {
        text-align: center;
    }

    .target-body {
        padding: 15px;
        background-color: #ffffff;
        height: calc(100vh - 162px);
    }

    .lastJc {
        font-family: '微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑', sans-serif;
        font-weight: 700;
        color: #414141;
    }

    .crumbOther {
        font-family: '微软雅黑', sans-serif;
        font-weight: 400;
        color: #939393;
    }

    .kh-back-bread {
        height: 32px;
        margin-bottom: 8px;
    }

    .kh-back-bread-butt {
        height: 32px;
        line-height: 32px;
        float: left;
        margin-right: 10px;
    }
</style>
<style>
    .el-table .pink-row {
        background: rgba(255, 218, 223, 0.972549019607843);
    }

    .ygkh-head-left .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .ygkh-head-left-threeword .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }

    .ygkh-head-left .el-form-item {
        margin-bottom: 0;
    }

    .kh-back-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
    }
</style>
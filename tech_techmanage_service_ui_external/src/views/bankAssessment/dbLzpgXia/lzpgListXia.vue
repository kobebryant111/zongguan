<!-- 待办履职评估-下级评上级列表 -->
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
                            <el-col :span="4">
                                <el-form-item>
                                    <el-input placeholder="年份" v-model="paramsForm.tyear">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
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
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
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
                    <el-table-column label="工号" width="80" prop="eid" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="姓名" width="130" prop="eidname"></el-table-column>
                    <el-table-column label="角色" width="130" prop="rolename"></el-table-column>
                    <el-table-column label="待评价项" width="100" prop="evalitems"></el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <div>
                                <el-button size="mini" @click="enterDetail(scope.row)">进入评价</el-button>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index" v-model="$attrs"
                        ref="pager" />
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
    import { getPostEvalInfoSummaryPager } from "@/api/hykh/kpiEval";
    import common from "@/api/common";
    import { mapGetters } from "vuex";
    export default {
        name: 'lzpgListXia',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    tyear: null,
                    depid: null,
                    eid: null,
                    eidname: null,
                },
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
        computed: {
            ...mapGetters(['userId'])
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "myKpiDb" || from.name == "lzpgXia") {
                    vm.routesMeta = ['我的待办', '经理' + vm.routerData.tyear + '年度考核']
                }
            })
        },
        mounted() {
            console.log('路由传值', this.$route.params)
            let valueTrans = {};
            if (JSON.stringify(this.$route.params) == '{}') {
                valueTrans = JSON.parse(sessionStorage.getItem('hykh_db_sxLzpg_jump_data'))
            } else {
                valueTrans = this.$route.params;
            }
            valueTrans.kpitype = '3';
            valueTrans.itemstatus = '6';
            this.routerData = valueTrans;
            this.paramsForm.tyear = this.routerData.tyear
            this.$nextTick(() => {
                this.seaTableData(valueTrans);
                this.getKjDep();
            })
        },
        methods: {
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
                params.tyear = this.paramsForm.tyear;
                params.depid = this.paramsForm.depid;
                params.eid = this.paramsForm.eid;
                params.eidname = this.paramsForm.eidname;
                params.kpitype = '3';
                params.itemstatus = '6';
                this.seaTableData(params);
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params = this.paramsForm;
                params.kpitype = '3';
                params.itemstatus = '6';
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
                getPostEvalInfoSummaryPager(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
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
                    name: 'lzpgXia',
                    params: val,
                })
            },

            // 返回
            lastPage() {
                this.$router.back()
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
    }

    .kh-back-bread-butt {
        height: 32px;
        line-height: 32px;
        float: left;
        margin-right: 10px;
    }
</style>
<style>
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
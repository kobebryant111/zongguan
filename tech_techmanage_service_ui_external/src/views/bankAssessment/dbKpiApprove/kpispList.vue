<!-- 我的待办-kpi审批列表 -->
<template>
    <div class="dbsp-container">
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
        <div class="yggl-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="5">
                                <el-form-item>
                                    <div class="dbsp-head-dept">
                                        <el-select placeholder="二级部" filterable v-model="paramsForm.depid">
                                            <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
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
        <div class="dbsp-body">
            <el-table :data="spList" height="580">
                <el-table-column label="年份" min-width="15%">
                    <template slot-scope="scope">
                        <div>{{routerData.tyear}}{{routerData.term}}</div>
                    </template>
                </el-table-column>
                <el-table-column label="中心" prop="centername" min-width="15%"></el-table-column>
                <el-table-column label="二级部" prop="depname" min-width="15%"></el-table-column>
                <el-table-column label="工号" prop="eid" min-width="10%"></el-table-column>
                <el-table-column label="姓名" prop="eidname" min-width="10%"></el-table-column>
                <el-table-column label="角色" prop="rolename" min-width="10%"></el-table-column>
                <el-table-column label="状态" prop="statusname" min-width="10%"></el-table-column>
                <el-table-column label="操作" min-width="25%">
                    <template slot-scope="scope">
                        <div>
                            <el-button size="mini" @click="enterDetail(scope.row)">进入详情</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pager-zj">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
            </div>
        </div>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import pager from "@/components/Pager";
    import { queryApprovePager } from "@/api/hykh/myDb";
    import common from "@/api/common";
    export default {
        name: 'kpispList',
        components: { cleanBtn, buttonOrange, pager },
        data() {
            return {
                paramsForm: {
                    depid: null,
                    eid: null,
                    eidname: null,
                }, //搜索条件
                spList: [], //表格数据
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                depData: [],
                routerData: {},
                routesMeta: []
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "myKpiDb" || from.name == "kpiEstablishApprove") {
                    vm.routesMeta = ['我的待办', 'KPI审批']
                }
            })
        },
        // beforeRouteLeave(to, from, next) {
        //     if (to.name == "kpiEstablishApprove") {
        //         this.$store.commit('SET_KEEP_ALIVES', ['kpispList']);
        //     } else if (to.name == "myKpiDb") {
        //         this.$store.commit('SET_KEEP_ALIVES', []);
        //     }
        //     next();
        // },
        mounted() {
            console.log('路由传值', this.$route.params)
            let valueTrans = {};
            if (JSON.stringify(this.$route.params) == '{}') {
                valueTrans = JSON.parse(sessionStorage.getItem('hykh_db_kpisp_jump_data'))
            } else {
                valueTrans = this.$route.params;
            }
            // if (valueTrans.term == undefined) {
            //     valueTrans.term = null
            // }
            this.routerData = valueTrans;
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
                    if (res.data.status == 200) {
                        var fild = res.data.results[0].children;
                        this.depData = fild.filter(tt => {
                            tt.text = tt.text.replace('总行金融科技部-', "");
                            tt.id = tt.id.replace('dep', '');
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 进入详情
            enterDetail(val) {
                this.$router.push({
                    name: 'kpiEstablishApprove',
                    params: val,
                })
            },

            // 查询
            submitBtn: function () {
                let paramsL = {};
                paramsL.depid = this.paramsForm.depid;
                paramsL.eid = this.paramsForm.eid;
                paramsL.eidname = this.paramsForm.eidname;
                let params = Object.assign(this.routerData, paramsL);
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryApprovePager(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.spList = res.data.results.filter(li => {
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', "");
                            }
                            return li;
                        });;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.depid = this.paramsForm.depid;
                params.eid = this.paramsForm.eid;
                params.eidname = this.paramsForm.eidname;
                params.size = size;
                params.index = index;
                let data = Object.assign(this.routerData, params);
                this.size = size;
                this.index = index;
                this.seaTableData(data);
            },

            // 返回
            lastPage() {
                this.$router.back()
            }
        }
    }
</script>
<style scoped>
    .dbsp-container {
        padding: 8px 15px 15px 15px;
    }

    .yggl-head-content {
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

    .dbsp-body {
        padding-bottom: 15px;
        background-color: #ffffff;
        border-radius: 5px;
    }

    .pager-zj {
        text-align: center;
        margin-top: 15px;
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
    .yggl-head-content .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .yggl-head-content .el-form-item {
        margin-bottom: 0;
    }

    .dbsp-head-dept .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }

    .kh-back-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
    }
</style>
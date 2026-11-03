<!-- KPI指标配置 -->
<template>
    <div class="ygkh-container">
        <div class="target-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left ygkh-head-left">
                        <el-row>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input v-model="paramsForm.tyear">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <!-- <el-col :span="3">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.term">
                                        <el-option v-for="(item, index) in termData" :key="index" :label="item.term"
                                            :value="item.term"></el-option>
                                        <template slot="prefix">周期：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col> -->
                            <el-col :span="4">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.centerid" @change="seaCenterChange">
                                        <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <div class="ygkh-head-left-threeword">
                                        <el-select filterable v-model="paramsForm.depid">
                                            <el-option v-for="item in depData" :key="item.depId" :label="item.depName"
                                                :value="item.depId"></el-option>
                                            <template slot="prefix">二级部：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.role">
                                        <el-option v-for="item in role_options" :key="item.dictid" :label="item.dictname"
                                            :value="item.dictid"></el-option>
                                        <template slot="prefix">角色：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input v-model="paramsForm.eid">
                                        <template slot="prefix">工号：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input v-model="paramsForm.eidname">
                                        <template slot="prefix">姓名：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.status">
                                        <el-option v-for="item in state_options" :key="item.dictid" :label="item.dictname"
                                            :value="item.dictid"></el-option>
                                        <template slot="prefix">状态：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
                            <!-- <clean-btn size="small" :call="resetForm" :forms="this.paramsForm" /> -->
                            <el-button @click="resetForm" size="small">重置</el-button>
                        </div>
                        <div class="head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div ref="ygkhbody" class="target-body">
            <div class="yggl-body-operation">
                <el-button v-if="ifShowDel" size="small" @click="changeStatus">修改状态</el-button>
                <el-button size="small" @click="exportEx">导 出</el-button>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight" v-loading="loading">
                    <!-- <el-table-column type="index" label="序号" width="55"></el-table-column> -->
                    <el-table-column label="考核时间" width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.tyear}}年度</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="工号" width="80" prop="eid" fixed="left"></el-table-column>
                    <el-table-column label="姓名" width="100" prop="eidname" fixed="left"></el-table-column>
                    <el-table-column label="中心" width="160" prop="centername"></el-table-column>
                    <el-table-column label="二级部" width="200" prop="depname"></el-table-column>
                    <el-table-column label="角色" width="110" prop="rolename"></el-table-column>
                    <el-table-column label="状态" width="110" prop="statusname"></el-table-column>
                    <el-table-column label="KPI考核" width="80" prop="kpiscore">
                        <template slot-scope="scope">
                            <div v-if="scope.row.kpiscore != 0">{{scope.row.kpiscore}}</div>
                            <div v-else>-</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="综合能力" width="80" prop="comprehensivescore">
                        <template slot-scope="scope">
                            <div v-if="scope.row.comprehensivescore != 0">{{scope.row.comprehensivescore}}</div>
                            <div v-else>-</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="履职评分" width="80" prop="performanceevaluationscore">
                        <template slot-scope="scope">
                            <div v-if="scope.row.performanceevaluationscore != 0">{{scope.row.performanceevaluationscore}}</div>
                            <div v-else>-</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="二级部评分" width="100" prop="depscore">
                        <template slot-scope="scope">
                            <div v-if="scope.row.depscore != 0">{{scope.row.depscore}}</div>
                            <div v-else>-</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="平衡计分" width="80" prop="balancescore">
                        <template slot-scope="scope">
                            <div v-if="scope.row.balancescore != 0">{{scope.row.balancescore}}</div>
                            <div v-else>-</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="总分" width="80" prop="tscore"></el-table-column>
                    <el-table-column label="评级" width="70" prop="tgrade"></el-table-column>
                    <!-- <el-table-column label="操作" width="180" fixed="right" v-if="ifShowDel">
                        <template slot-scope="scope">
                            <div>
                                <el-button size="mini" @click="enterDetail(scope.row)">进入详情</el-button>
                                <el-button size="mini" type="danger" @click="delRow(scope.row)">删 除</el-button>
                            </div>
                        </template>
                    </el-table-column> -->
                    <el-table-column label="操作" width="100" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <el-button size="mini" @click="enterDetail(scope.row)">进入详情</el-button>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager v-if="ifRouterAlive" :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index"
                        v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import pager from "@/components/Pager";
    import common from "@/api/common";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { exportEmpKpiDatas, del, getEmpKpiDataPager } from "@/api/hykh/ygKh";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { mapGetters } from 'vuex';
    export default {
        name: 'ygKh',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    tyear: null,
                    centerid: null,
                    depid: null,
                    role: null,
                    eid: null,
                    eidname: null,
                    status: null,
                },
                termData: [
                    { term: '上半年' },
                    { term: '下半年' },
                    { term: '年度' },
                ],
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                selectRow: [], //表格选中行
                targetData: [],
                depData: [],
                tableHeight: null,
                ifShowDel: false,
                role_options: [], //角色
                state_options: [], //状态
                centerData: [], //中心
                ifRouterAlive: false,
                currentYear: null,
                loading: true
            }
        },
        computed: {
            ...mapGetters(['roles'])
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "ygkhDetail" || from.name == "kpiPj") {
                    if (JSON.parse(sessionStorage.getItem('ygkh_serch_data')) != null) {
                        vm.paramsForm = JSON.parse(sessionStorage.getItem('ygkh_serch_data'));
                        if (vm.paramsForm.size != undefined) {
                            vm.size = vm.paramsForm.size;
                        }
                        if (vm.paramsForm.index != undefined) {
                            vm.index = vm.paramsForm.index;
                        }
                    }
                }
                vm.ifRouterAlive = true;
                vm.seaTableData(vm.paramsForm);
            })
        },
        created() {

        },
        mounted() {
            this.getYear();
            // this.seaTableData(this.paramsForm);
            this.getSeaDep();
            this.getCenter();
            this.getDic();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ygkhbody.offsetHeight * 0.82
            })
            for (var i = 0; i < this.roles.length; i++) {
                if (this.roles[i].roleId == 'KPIADMIN') {
                    this.ifShowDel = true
                    break
                }
            }
        },
        methods: {
            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_ROLE,KPI_MAIN_STATUS" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //角色
                    this.role_options = data.filter(item => {
                        return item.dicttypeid == "KPI_ROLE";
                    });
                    //状态
                    this.state_options = data.filter(item => {
                        if (item.dicttypeid == "KPI_MAIN_STATUS") {
                            item.dictid = Number(item.dictid);
                            return item
                        }
                    });
                    return data;
                });
            },

            // 获取年份
            getYear() {
                let date = new Date();
                this.paramsForm.tyear = date.getFullYear();
                this.currentYear = date.getFullYear();
            },

            // 修改状态
            changeStatus() {
                this.$router.push({
                    name: 'statexgList',
                })
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
                        return tt
                    });
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    console.log('中心', res.data)
                    if (res.data.status == 200) {
                        this.centerData = res.data.results.filter(li => {
                            return li.id != 61
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 搜索中心改变
            seaCenterChange(val) {
                console.log('中心改变', val)
                this.paramsForm.depid = null;
                this.getSeaDep(val);
            },

            // 根据中心获取部门
            getSeaDep(id) {
                const loading = common.loading(this);
                queryDeptList({ centerId: id }).then(res => {
                    loading.close();
                    console.log("获取部门", res.data)
                    if (res.data.status == 200) {
                        var fild = res.data.results;
                        this.depData = fild.filter(tt => {
                            tt.depName = tt.depName.replace('总行金融科技部-', "");
                            return tt
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.tyear = this.paramsForm.tyear;
                params.centerid = this.paramsForm.centerid;
                params.depid = this.paramsForm.depid;
                params.role = this.paramsForm.role;
                params.eid = this.paramsForm.eid;
                params.eidname = this.paramsForm.eidname;
                params.status = this.paramsForm.status;
                this.index = params.index = this.paramsForm.index = 1;
                params.size = this.size;
                this.seaTableData(params);
                // sessionStorage.setItem('ygkh_serch_data', JSON.stringify(this.paramsForm));
            },

            // 重置
            resetForm() {
                this.paramsForm = {
                    tyear: this.currentYear,
                    centerid: null,
                    depid: null,
                    role: null,
                    eid: null,
                    eidname: null,
                    status: null,
                };
                this.getSeaDep(null);
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params = this.paramsForm;
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
                // sessionStorage.setItem('ygkh_serch_data', JSON.stringify(this.paramsForm));
            },

            // 查询列表数据
            seaTableData(val) {
                // const loading = common.loading(this);
                this.loading = true;
                let data = common.paramsFormat(val);
                getEmpKpiDataPager(data).then(res => {
                    // loading.close();
                    this.loading = false;
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
                    // loading.close();
                    this.$message.error(error)
                })
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                // let params = {};
                // params.item = this.paramsForm.item;
                // params.centerid = this.paramsForm.centerid;
                let data = common.paramsFormat(this.paramsForm);
                const self = this;
                exportEmpKpiDatas(data).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "员工考核.xls");
                });
            },

            // 修改单人
            enterDetail(val) {
                sessionStorage.setItem('ygkh_serch_data', JSON.stringify(this.paramsForm));
                this.$router.push({
                    name: 'ygkhDetail',
                    params: val
                })
            },

            // 删除行
            delRow(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    del({ id: val.id }).then(res => {
                        loading.close();
                        console.log("删除行", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.seaTableData();
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },

            // 排序
            sortChange(column) {
                console.log('排序', column)
                let params = {};
                params.sortField = column.prop;
                if (column.order == 'ascending') {
                    params.sortOrder = 'asc'
                } else if (column.order == 'descending') {
                    params.sortOrder = 'desc'
                } else {
                    params.sortOrder = null
                }
                this.paramsForm.sortField = params.sortField;
                this.paramsForm.sortOrder = params.sortOrder;
                params.tscore = this.paramsForm.tscore;
                this.seaTableData(params);
            }

        }
    }
</script>
<style scoped>
    .ygkh-container {
        height: 100%;
        padding: 15px;
    }

    .target-head {
        padding-bottom: 15px;
        border-radius: 4px;
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
        line-height: 36px;
        display: flex;
    }

    .head-right-clean {
        flex: 1;
    }

    .head-right-search {
        /* padding-left: 10px; */
        flex: 1;
    }

    .yggl-body-operation {
        text-align: right;
        margin-bottom: 10px;
    }

    .pager-zj {
        text-align: center;
    }

    .target-body {
        padding: 15px;
        background-color: #ffffff;
        height: calc(100vh - 162px);
        border-radius: 4px;
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
</style>
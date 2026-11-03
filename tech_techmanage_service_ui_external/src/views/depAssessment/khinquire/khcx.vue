<!-- 考核查询 -->
<template>
    <div class="ejbkh-container">
        <div class="target-head">
            <el-form inline>
                <div class="ejbgl-head-content xkg-borderradius-4px">
                    <div class="head-left ejbkh-head-left">
                        <el-row>
                            <el-col :span="3">
                                <el-form-item>
                                    <!-- <el-input v-model="paramsForm.tyear">
                                        <template slot="prefix">年份：</template>
                                    </el-input> -->
                                    <el-select v-model="paramsForm.tyear" @change="yearChange">
                                        <el-option v-for="item in yearArr" :key="item" :label="item" :value="item"></el-option>
                                        <template slot="prefix">年份：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <template v-if="this.sign == 1">
                                <el-col :span="4">
                                    <el-form-item>
                                        <el-select filterable clearable v-model="paramsForm.centerid" @change="seaCenterChange">
                                            <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                :value="item.id"></el-option>
                                            <template slot="prefix">中  心：</template>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="4">
                                    <el-form-item>
                                            <el-select filterable clearable v-model="paramsForm.depid">
                                                <el-option v-for="item in depData" :key="item.depId" :label="item.depName"
                                                    :value="item.depId"></el-option>
                                                <template slot="prefix">二级部：</template>
                                            </el-select>
                                    </el-form-item>
                                </el-col>
                            </template>
                            <template v-if="this.sign == 2">
                                <el-col :span="6">
                                    <el-form-item>
                                        <el-select filterable clearable v-model="paramsForm.depid">
                                            <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                :value="item.id"></el-option>
                                            <template slot="prefix">中心：</template>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            </template>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean" @click="resetForm">
                            <clean-btn size="small" :forms="this.paramsForm" />
                        </div>
                        <div class="head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div ref="ejbkhbody" class="target-body">
            <h3 class="ejbkh-body-title">考核查询</h3>
            <div class="ejbgl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column v-if="sign != 3" label="中心" width="160" prop="centername"></el-table-column>
                    <el-table-column v-if="sign != 2" label="部门" width="200" prop="depname"></el-table-column>
                    <el-table-column label="状态" width="110" prop="status" align="center">
                        <template slot-scope="scope">
                            <div>{{getStatusName(scope.row.status)}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="sign != 3" label="总分" width="70" prop="sumpoint" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column v-if="sign != 3" label="评级" width="150" prop="grade" :show-overflow-tooltip="true"></el-table-column>
                    <!-- <el-table-column label="排名" width="70" prop="ranknum"></el-table-column> -->
                    <el-table-column label="操作" width="200" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <el-button size="mini" @click="enterDetail(scope.row)">查看内容</el-button>
                                <el-button size="mini" v-if="sign != 3" @click="enterUpdateStatus(scope.row)" style="background-color:rgba(52, 205, 177, 1)"><span style="color:white">修改状态</span></el-button>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index"
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
    import { getEmpKpiDataPager } from "@/api/hykh/ygKh";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { queryCheckPage } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    export default {
        name: 'khcx',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    tyear: null,
                    centerid: null,
                    depid: null,
                },
                yearArr: "",
                totalNumber: 0,
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                selectRow: [], //表格选中行
                targetData: [],
                depData: [],
                tableHeight: null,
                state_options: [], //状态
                centerData: [], //中心
                sign: null,
            }
        },
        computed: {},
        created() {
            if(this.$route.name == "ejbkhcx"){
                this.sign = 1
            }else if(this.$route.name == "zxkhcx"){
                this.sign = 2
            }else if(this.$route.name == "yjbkhcx"){
                this.sign = 3
            }
            var sessionParamStr = window.sessionStorage.getItem("khcx");
            if(sessionParamStr){
                let sessionParam = JSON.parse(sessionParamStr);
                this.paramsForm = sessionParam;
                this.size = sessionParam.size ? sessionParam.size : 10; // 初始值 默认：50
                this.index = sessionParam.index ? sessionParam.index :  1; // 初始值 默认：1
            }
        },
        mounted() {
            this.getDic();
            this.getYear();
            this.seaTableData(this.paramsForm);
            this.getSeaDep();
            this.getCenter();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ejbkhbody.offsetHeight * 0.82
            })
        },
        methods: {
            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "DEP_KPI_MAIN_STATUS" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                  
                    //状态
                    this.state_options = data.filter(item => {
                        if (item.dicttypeid == "DEP_KPI_MAIN_STATUS") {
                            item.dictid = Number(item.dictid);
                            return item
                        }
                    });
                    return data;
                });
            },

            getStatusName(id){
                var item = this.state_options.find(item => item.dictid == Number(id));
                if(item){
                    return item.dictname;
                }else{
                    return "-";
                }
            },

            // 获取年份
            getYear() {
                let date = new Date();
                this.yearArr = [date.getFullYear(), date.getFullYear() - 1];
                this.paramsForm.tyear = this.paramsForm.tyear ? this.paramsForm.tyear : date.getFullYear();
            },

            yearChange(){
                this.submitBtn();
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
                this.index = params.index = this.paramsForm.index = 1;
                params.size = this.size;
                this.seaTableData(params);
            },

            // 重置
            resetForm() {
                this.paramsForm = {
                    tyear: null,
                    centerid: null,
                    depid: null,
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
            },

            // 查询列表数据
            seaTableData(val) {
                if(!val){
                    val = {
                        size: this.size,
                        index: this.index
                    };
                }
                val.sign = this.sign;
                window.sessionStorage.setItem("khcx",JSON.stringify(val));
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryCheckPage(data).then(res => {
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

          

            // 查看内容
            enterDetail(val) {
                var id;
                var path = '/khdetail';
                if(this.sign == 1 ) {
                    id = val.depid;
                } else if (this.sign == 2){
                    id = val.centerid;
                } else if(this.sign == 3) {
                    id = val.depid;
                    path = 'yjbkhdetail';
                } else {
                    return;
                }
                this.$router.push({ 
                    path: path, 
                    query: { 
                        id: id, 
                        type: 1, 
                        tyear: this.paramsForm.tyear, 
                        sign: this.sign
                    }
                })
            },

            enterUpdateStatus(val) {
                let id;
                if(this.sign == 1 || this.sign == 3 ) {
                    id = val.depid;
                } else if (this.sign == 2){
                    id = val.centerid;
                } else {
                    return;
                }

                this.$router.push({ 
                    path: '/khxpz', 
                    query: { 
                        id: id, 
                        tyear: this.paramsForm.tyear, 
                        sign: this.sign
                    }
                })
            },

           

        }
    }
</script>
<style scoped>
    .ejbkh-body-title {
        font-size: 16px;
    }
    .ejbkh-container {
        height: 100%;
        padding: 15px;
    }

    .target-head {
        padding-bottom: 15px;
        border-radius: 4px;
    }

    .ejbgl-head-content {
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

    .ejbgl-body-operation {
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
    .ejbkh-head-left .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }
    .ejbkh-head-left-threeword .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }
    .ejbkh-head-left .el-form-item {
        margin-bottom: 0;
    }
    .el-table__empty-block {
        width: 100%;
        min-width: 100%;
        max-width: 100%;
    }
</style>
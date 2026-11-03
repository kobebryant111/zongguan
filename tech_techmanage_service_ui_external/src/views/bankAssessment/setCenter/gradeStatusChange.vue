<!-- 评级状态修改中心 -->
<template>
    <div class="pjzt-container">
        <div class="target-head">
            <el-form inline>
                <div class="target-head-content xkg-borderradius-4px">
                    <div class="head-left target-head-left">
                        <el-row>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input v-model="paramsForm.tyear">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.centerid">
                                        <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item>
                                    <div class="target-head-left-threeword">
                                        <el-select filterable v-model="paramsForm.depid">
                                            <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                            <template slot="prefix">二级部：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.status">
                                        <el-option v-for="(item, index) in state_options" :key="index" :label="item.dictname"
                                            :value="item.dictid"></el-option>
                                        <template slot="prefix">状态：</template>
                                    </el-select>
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
                    <el-table-column label="考核时间" width="150" prop="tyear">
                        <template slot-scope="scope">
                            {{scope.row.tyear}}{{scope.row.term}}
                        </template>
                    </el-table-column>
                    <el-table-column label="中心" width="160" prop="centername"></el-table-column>
                    <el-table-column label="二级部" width="180" prop="depname"></el-table-column>
                    <el-table-column label="修改流程状态" width="230" fixed="right">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.status" @change="finalGrade(scope.row)">
                                <el-option v-for="(item, index) in state_options" :key="index" :label="item.dictname"
                                    :value="item.dictid"></el-option>
                            </el-select>
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
    import pager from "@/components/Pager";
    import common from "@/api/common";
    import { getDepStatusPager, updateDepStatus } from "@/api/hykh/yearPj";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import { queryList } from "@/api/integrated/newEmBmfp";
    import { getDicEntry } from "@/api/eosDictEntry";
    export default {
        name: 'gradeStatusChange',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    tyear: null,
                    depid: null,
                    centerid: null,
                    status: null,
                },
                depData: [],
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                targetData: [],
                tableHeight: null,
                centerData: [], //中心
                state_options: [], //评级
            }
        },
        mounted() {
            this.getYear();
            let params = {};
            params.tyear = this.paramsForm.tyear;
            params.term = '年度';
            this.seaTableData(params);
            this.getDic();
            this.getCenter();
            this.getKjDep();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ygkhbody.offsetHeight * 0.82
            })
        },
        methods: {
            // 获取年份
            getYear() {
                let date = new Date();
                this.paramsForm.tyear = date.getFullYear();
            },

            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_MAIN_STATUS" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //状态
                    this.state_options = data.filter(item => {
                        if (item.dicttypeid == "KPI_MAIN_STATUS") {
                            item.dictid = Number(item.dictid);
                            return item
                        }
                    });
                });
            },

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

            // 分页
            getPager: function (size, index) {
                let params = {};
                params = this.paramsForm;
                params.term = '年度';
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.tyear = this.paramsForm.tyear;
                params.depid = this.paramsForm.depid;
                params.centerid = this.paramsForm.centerid;
                params.term = '年度';
                params.status = this.paramsForm.status;
                this.index = params.index = 1;
                params.size = this.size;
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getDepStatusPager(data).then(res => {
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

            // 修改评级
            finalGrade(val) {
                console.log("评级", val)
                const loading = common.loading(this);
                let params = {};
                params.depid = val.depid;
                params.tyear = val.tyear;
                params.term = val.term;
                params.status = val.status;
                let data = common.paramsFormat(params);
                updateDepStatus(data).then(res => {
                    loading.close();
                    console.log("修改单人确定", res.data)
                    if (res.data.status == 200) {
                        this.$message.success(res.data.message);
                        let params = {};
                        params.tyear = this.paramsForm.tyear;
                        params.depid = this.paramsForm.depid;
                        params.centerid = this.paramsForm.centerid;
                        params.term = '年度';
                        params.status = this.paramsForm.status;
                        this.index = params.index = 1;
                        params.size = this.size;
                        this.seaTableData(params);
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },
        }
    }
</script>
<style scoped>
    .pjzt-container {
        height: 100%;
        padding: 8px 15px 15px 15px;
    }

    .target-head {
        padding-bottom: 15px;
        border-radius: 4px;
    }

    .target-head-content {
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
        padding-left: 10px;
        flex: 1;
    }

    .target-body {
        padding: 15px;
        background-color: #ffffff;
        height: calc(100vh - 195px);
        border-radius: 4px;
    }

    .hmddialog-bottom {
        text-align: center;
    }

    .pager-zj {
        text-align: center;
    }
</style>
<style>
    .target-head-left .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .target-head-left-threeword .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }

    .target-head-left .el-form-item {
        margin-bottom: 0;
    }
</style>
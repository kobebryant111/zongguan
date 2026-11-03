<!-- KPI指标配置 -->
<template>
    <div class="ejbkh-container">
        <div class="target-head">
            <div class="web-head-back-bread">
                <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
            </div>
            <el-form inline>
                <div class="ejbgl-head-content xkg-borderradius-4px">
                    <div class="head-left ejbkh-head-left">
                        <el-row>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-input v-model="paramsForm.name">
                                        <template slot="prefix">考核项：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
                            <el-button @click="resetForm" size="small">重置</el-button>
                        </div>
                        <div class="head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <div ref="ejbkhbody" class="target-body">
            <h3 class="ejbkh-body-title">考核项配置</h3>
            <div class="yggl-body-table">
                <el-table :data="targetData" height="480">
                    <el-table-column type="index" label="序号" min-width="10%"></el-table-column>
                    <el-table-column label="考核项" min-width="20%" prop="name" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="备注" min-width="20%" prop="remark" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="基准分" prop="basepoint" min-width="15%"></el-table-column>
                    <el-table-column label="指标制定类型" min-width="20%" prop="targettype">
                        <template slot-scope="scope">
                           <span v-if="scope.row.targettype == 2">自定义指标</span>
                           <span v-else-if="scope.row.targettype == 1">公共指标</span>
                           <span v-else>-</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="指标制定部门" prop="markerdepname" min-width="15%">
                         <template slot-scope="scope">
                           <span v-if="scope.row.targettype == 1">{{scope.row.markerdepname}}</span>
                           <span v-else>-</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" min-width="20%">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.status" @change="statusChange(scope.row)" style="text-align: center;">
                                <el-option v-for="(item, index) in state_options" :key="index" :label="item.dictname"
                                    :value="item.dictid" ></el-option>
                            </el-select>
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
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { getListPage, upCheckStatus } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    export default {
        name: 'khxpz',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    name: null
                },
                totalNumber: 0,
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                selectRow: [], //表格选中行
                targetData: [],
                tableHeight: null,
                state_options: [], //状态
            }
        },
        computed: {},
        created() {},
        mounted() {
            this.getDic();
            this.submitBtn();
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.back(-1)
            },
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
                });
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.name = this.paramsForm.name;
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
                val.levelnum = 1;
                val.sign = this.$route.query.sign;
                val.tyear = this.$route.query.tyear;
                val.depid = this.$route.query.id;
                val = JSON.parse(JSON.stringify(val));
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getListPage(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.targetData = res.data.results.filter(li => {
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', '')
                            }
                            if (li.markerdepname != undefined) {
                                li.markerdepname = li.markerdepname.replace('总行金融科技部-', '')
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },
            
            // 状态改变
            statusChange(v) {
                let params = {};
                params.id = v.id;
                params.status = v.status;
                params.sign = this.$route.query.sign;
                let data = common.paramsFormat(params);
                const loading = common.loading(this);
                upCheckStatus(data).then(res => {
                    loading.close();
                    console.log('单条提交', res.data)
                    if (res.data.status == 200) {
                        this.submitBtn();
                        this.$message.success("成功");
                    } else {
                        this.$message.error(res.data.message);
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
    .web-head-back-bread {
        margin-bottom: 10px;
    }
    .web-head-back-bread .el-breadcrumb {
        display: inline-block;
        vertical-align: middle;
        margin-left: 10px;
    }
</style>

<style>
    .ejbkh-head-left .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .ejbkh-head-left-threeword .el-input--prefix .el-input__inner {
        padding-left: 75px;
    }

    .ejbkh-head-left .el-form-item {
        margin-bottom: 0;
    }
    
</style>
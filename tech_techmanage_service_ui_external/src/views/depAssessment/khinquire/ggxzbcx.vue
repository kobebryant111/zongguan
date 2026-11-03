<!-- 公共指标查询 -->
<template>
    <div class="ejbkh-container">
        <div class="target-head">
            <el-form inline>
                <div class="ejbgl-head-content xkg-borderradius-4px">
                    <div class="head-left ejbkh-head-left">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item>
                                    <el-input v-model="paramsForm.name">
                                        <template slot="prefix">考核项：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item>
                                    <div class="ejbkh-head-left-threeword">  
                                        <el-select filterable clearable v-model="paramsForm.depid">
                                            <el-option v-for="item in depDataAll" :key="item.depId" :label="item.depName"
                                                :value="parseInt(item.depId)"></el-option>
                                                <template v-if="this.sign == 1" slot="prefix">使用二级部：</template>
                                                <template v-if="this.sign == 2" slot="prefix">使用中心：</template>
                                        </el-select>
                                    </div>
                                </el-form-item>
                            </el-col>
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
            <h3 class="ejbkh-body-title">公共项指标查询</h3>
            <div class="ejbgl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="考核项" width="160" prop="name" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="备注项" width="200" prop="remark" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="操作" width="200" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <el-button size="mini" @click="enterDetail(scope.row)">查看内容</el-button>
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
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { queryGgCheckPage } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    export default {
        name: 'ggxzbcx',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    name: null,
                    centerid: null,
                    depid: null,
                },
                totalNumber: 0,
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                selectRow: [], //表格选中行
                targetData: [],
                depDataAll: [],
                tableHeight: null,
                sign: null,
            }
        },
        created(){
            if(this.$route.name == "ggxzbcx"){
                this.sign = 1
            }else if(this.$route.name == "zxggxzbcx"){
                this.sign = 2
            }else if(this.$route.name == "yjbggxzbcx"){
                this.sign = 3
            }

            var sessionParamStr = window.sessionStorage.getItem("ggxzbcx");
            if(sessionParamStr){
                let sessionParam = JSON.parse(sessionParamStr);
                this.paramsForm = sessionParam;
                this.size = sessionParam.size ? sessionParam.size : 10; // 初始值 默认：50
                this.index = sessionParam.index ? sessionParam.index :  1; // 初始值 默认：1
            }
        },
        mounted() {
            this.seaTableData(this.paramsForm);
            this.getDep();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ejbkhbody.offsetHeight * 0.82
            })
           
        },
        methods: {      
            // 获取所有部门
            getDep() {
                const loading = common.loading(this);
                if(this.sign == 1){
                    queryDeptList({}).then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.depDataAll = fild.filter(tt => {
                                tt.depName = tt.depName.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
                } else {
                    queryList({}).then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.depDataAll = fild.filter(tt => {
                                tt.depName = tt.centername.replace('总行金融科技部-', "");
                                tt.depId = tt.id
                                return tt.id != 61;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
                }
            },



            // 查询
            submitBtn: function () {
                let params = {};
                params.depid = this.paramsForm.depid;
                params.centerid = this.paramsForm.centerid;
                params.name = this.paramsForm.name;
                params.size = this.size;
                this.seaTableData(params);
            },

            // 重置
            resetForm() {
                this.paramsForm = {
                    name: null,
                    depid: null,
                    centerid: null,
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
                if(!val){
                    val = {
                        size: this.size,
                        index: this.index
                    };
                }
                val.sign = this.sign;
                val.targettype = 1;
                window.sessionStorage.setItem("ggxzbcx",JSON.stringify(val));
                val = JSON.parse(JSON.stringify(val));
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryGgCheckPage(data).then(res => {
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
                this.$router.push({ name: 'ggzbdetail', query: { sign: this.sign, targetid: val.targetid} })
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
        padding-left: 100px;
    }

    .ejbkh-head-left .el-form-item {
        margin-bottom: 0;
    }
    .el-table/deep/.el-table__empty-block {
        width: 100%;
        min-width: 100%;
        max-width: 100%;
    }
</style>
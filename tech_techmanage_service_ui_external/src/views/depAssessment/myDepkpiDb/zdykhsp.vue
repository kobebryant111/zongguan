<!-- 模板管理 -->
<template>
    <div class="web-container">
        <div class="back-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
        </div>
        <div class="web-head">
            <el-form inline>
                <div class="web-head-content xkg-borderradius-4px">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="5">
                                <el-form-item>
                                    <el-select filterable clearable v-model="paramsForm.centerid" @change="seaCenterChange">
                                        <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col v-if="this.sign == 1" :span="5">
                                <el-form-item>
                                    <el-select filterable clearable v-model="paramsForm.depid">
                                        <el-option v-for="item in depData" :key="item.depId" :label="item.depName"
                                            :value="item.depId"></el-option>
                                        <template slot="prefix">二级部：</template>
                                    </el-select>
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
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-operation">
                <button-orange buttonName="通过审批" size="small" :fns="batch"></button-orange>
            </div>
            <div class="web-body-table">
                <el-table :data="tableData" height="500" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="50"></el-table-column>
                    <el-table-column label="中心" width="220" prop="centername"></el-table-column>
                    <el-table-column v-if="this.sign == 1" label="二级部" width="220" prop="depname"></el-table-column>
                    <el-table-column label="初审人" width="220" prop="firstcheck"></el-table-column>
                    <el-table-column label="终审人" width="220" prop="secondcheck"></el-table-column>
                    <el-table-column label="状态" width="110" prop="status" align="center">
                        <template slot-scope="scope">
                            <div>{{getStatusName(scope.row.status)}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150" fixed="right" align="center">
                        <template slot-scope="scope">
                            <div>
                                <el-button class="xkg-marginleft-10px" size="mini" @click="indicatorItemsJump(scope.row)">审批</el-button>
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
    import common from "@/api/common";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPagerList, add, update, deleteTarget } from "@/api/zzkhApi/target";
    import { queryModelPagerList, addModel, updateModel, deleteModel } from "@/api/zzkhApi/model";
    import { querySpPagerList, spCommit } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import pager from "@/components/Pager";
    export default {
        name: 'zdykhsp',
        components: { pager, cleanBtn, buttonOrange },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 总数
                tableData: [],
                centerData: [],
                depData: [],
                state_options: [],
                sign: null,
                targettype: 2,
                paramsForm: { // 搜索框表单
                    centerid: null,
                    depid: null
                },
            }
        },
        props:{},
        created(){
            this.sign = this.$route.query.sign;
            this.tyear = this.$route.query.tyear;
        },
        mounted() {
            this.getSeaDep(); // 获取所有二级部门
            this.getCenter(); // 获取所有中心
            this.getDic();
            this.submitBtn();
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.push({path:"/myDepKpiDb"});
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
                    return data;
                });
            },

            // 搜索中心改变
            seaCenterChange(val) {
                this.paramsForm.depid = null;
                this.getSeaDep(val);
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    if (res.data.status == 200) {
                        this.centerData = res.data.results.filter(li => {
                            return li.id != 61
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 根据中心获取部门
            getSeaDep(id) {
                var param = {};
                if(id){
                    param.centerId = id;
                }
                const loading = common.loading(this);
                queryDeptList(param).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        var fild = res.data.results;
                        this.depData = fild.filter(tt => {
                            tt.depName = tt.depName.replace('总行金融科技部-', "");
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 重置
            resetForm() {
                this.getSeaDep();
            },

            // 查询
            submitBtn: function () {
                let params = {};
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData() {
                var val = {};
                val.centerid = this.paramsForm.centerid;
                val.depid = this.paramsForm.depid;
                val.targettype = this.targettype;
                val.sign = this.sign;
                val.tyear = this.tyear;
                val.size = this.size;
                val.index = this.index;
                val = JSON.parse(JSON.stringify(val));
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                querySpPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        // if(res.data.results.length == 0){
                        //     this.$router.push({path:"/myDepKpiDb"});
                        // }
                        this.tableData = res.data.results.filter(li => {
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

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            getStatusName(id){
                var item = this.state_options.find(item => item.dictid == Number(id));
                if(item){
                    return item.dictname;
                }else{
                    return "-";
                }
            },

             // 指标项配置页面跳转
            indicatorItemsJump(val) {
                let name;
                let id;
                if(this.sign == 2){
                    name = val.centername
                    id = val.centerid;
                }else{
                    name = val.depname;
                    id = val.depid;
                }
                this.$router.push({ path: '/khspmx', query: {id: id, sign: this.sign, name: name, tyear: this.tyear}});
            },

            // 表格选中变化
            handleSelectionChange(val) {
                console.log("分页表格选中", val)
                this.selectRow = val;
            },

            batch(){
                this.$confirm('确认批量审核吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    var idlist = [];
                    if(this.selectRow.length == 0){
                        loading.close();
                        this.$message.error('未选择数据');
                        return;
                    }
                    this.selectRow.forEach(item => {
                        if(this.sign == 2){
                            idlist.push(item.centerid);
                        }else if(this.sign == 1) {
                            idlist.push(item.depid);
                        }
                    })
                    const loading = common.loading(this);
                    let data = common.paramsFormat({
                        depidlist: idlist.join(","),
                        tyear: this.tyear
                    });
                    spCommit(data).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('审核成功');
                            this.submitBtn();
                        } else {
                            this.$message.error(res.data.message);
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            }
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

    .web-head-content::v-deep .el-input--prefix .el-input__inner {
        padding-left: 55px;
    }

    .web-head-content::v-deep .el-form-item {
        margin-bottom: 0;
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

    .web-body-operation {
        float: right;
    }

    .pager-zj {
        text-align: center;
    }
</style>
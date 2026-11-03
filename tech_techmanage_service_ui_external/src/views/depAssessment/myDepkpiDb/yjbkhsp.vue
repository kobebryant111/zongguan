<!-- 模板管理 -->
<template>
    <div class="web-container">
        <div class="back-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
        </div>
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-operation">
                <button-orange buttonName="通过审批" :fns="batch"></button-orange>
            </div>
            <div class="web-body-table">
                <el-table :data="tableData" height="500" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="50"></el-table-column>
                    <el-table-column label="考核项" width="220" prop="name"></el-table-column>
                    <el-table-column label="备注" width="220" prop="remark"></el-table-column>
                    <el-table-column label="基准分" width="100" prop="basepoint"></el-table-column>
                    <el-table-column label="制定人" width="220" prop="markeruser">
                        <template slot-scope="scope">
                            <div>{{scope.row.markerusername + '(' + scope.row.markeruser + ')'}}</div>
                        </template>
                    </el-table-column>
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
    import { queryYjbSpPagerList, spCommit } from "@/api/zzkhApi/ppmDepkpiDepCheck";
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
                selectRow: [],
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
            
            // 重置
            resetForm() {
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
                val.sign = this.sign;
                val.tyear = this.tyear;
                val.size = this.size;
                val.index = this.index;
                val = JSON.parse(JSON.stringify(val));
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryYjbSpPagerList(data).then(res => {
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
                this.$router.push({ path: '/yjbkhspmx', query: {id: val.id, depid: val.depid, sign: this.sign, tyear: this.tyear}});
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
                        idlist.push(item.id);
                    })
                    const loading = common.loading(this);
                    let data = common.paramsFormat({
                        sign: 3,
                        idlist: idlist.join(','),
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
<!-- 评级 -->
<template>
    <div class="web-container">
        <div class="web-bread">
            <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返 回</el-button>
            <div>
                <el-button type="defalut" size="small" @click="exportEx">导 出</el-button>
                <el-button type="warning" size="small" @click="commit">提 交</el-button>
            </div>
        </div>
        <div class="web-body xkg-borderradius-4px">
            <div v-if="this.sign == 1" class="web-body-head">
                <div v-for="item in gradeScale" class="web-body-item" :key="item.centerid">
                    <div class="web-body-item-dep"><span>{{item.centername}}</span></div>
                    <div v-for="gradeItem in item.gradelist" :key="gradeItem.grade" 
                    :class="[gradeItem.data.num > gradeItem.data.total ? 'web-body-item-sacle red' : 'web-body-item-sacle']">
                        <span class="web-body-item-sacle-grade">{{gradeItem.grade}}</span>
                        <span class="web-body-item-sacle-num">{{gradeItem.data.num}}</span>
                        <span class="web-body-item-sacle-s">/</span>
                        <span class="web-body-item-sacle-total">{{gradeItem.data.total}}</span>
                    </div>
                </div>
            </div>
            <div v-if="this.sign == 2" class="web-body-head">
                <div class="web-body-item" >
                    <div v-for="item in gradeScale" :key="item.grade" 
                    :class="[item.data.secondnum > item.data.total ? 'web-body-item-sacle red' : 'web-body-item-sacle']">
                        <span class="web-body-item-sacle-grade">{{item.grade}}</span>
                        <span class="web-body-item-sacle-num">{{item.data.secondnum}}</span>
                        <span class="web-body-item-sacle-s">/</span>
                        <span class="web-body-item-sacle-total">{{item.data.total}}</span>
                    </div>
                </div>
            </div>
            <div class="web-body-table">
                <el-table :data="tableData" height="510">
                    <el-table-column label="中心" width="150" prop="centername"></el-table-column>
                    <el-table-column v-if="this.sign == 1" label="二级部" width="150" prop="depname"></el-table-column>
                    <el-table-column label="总分" width="80" prop="point" align="center">
                        <template slot-scope="scope">
                            <div class="sumpoint">{{scope.row.point}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="名次" width="80" prop="ranking" align="center">
                        <template slot-scope="scope">
                            <div class="ranking">{{scope.row.ranking}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="默认评级" width="80" prop="defaultgrade" align="center">
                        <template slot-scope="scope">
                            <div class="defaultgrade">{{scope.row.defaultgrade}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="sign == 1" label="中心备注" width="200" prop="firstgraderemark" align="center"></el-table-column>
                    <el-table-column v-if="sign == 1" label="中心建议评级" width="80" prop="firstgrade" align="center">
                        <template slot-scope="scope">
                            <div class="defaultgrade">{{scope.row.firstgrade}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="isSecond" label="确认评级" width="150" prop="secondgrade" class-name="secondgrade" align="center">
                        <template slot-scope="scope">
                            <el-select v-if="sign == 1" v-model="scope.row.secondgrade" value-key="id" filterable @change="changeGrade(scope.row)">
                                <template v-for="item in oriGradeList">
                                    <el-option v-if="item.centerid == scope.row.centerid" :key="item.id" :label="item.grade" :value="item.grade"></el-option>
                                </template>
                            </el-select>
                            <el-select v-if="sign == 2" v-model="scope.row.secondgrade" value-key="id" filterable @change="changeGrade(scope.row)">
                                <el-option v-for="item in oriGradeList" :key="item.id" :label="item.grade" :value="item.grade"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="!isSecond" label="操作" width="150" align="center">
                        <template slot-scope="scope">
                            <div>
                                <el-button class="xkg-marginleft-10px" size="mini" @click="updateDepGarde(scope.row)">修改</el-button>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
        <!-- 添加/修改 -->
        <el-dialog title="评价" :visible.sync="ifShowDialog" @close="cancelUpdate" width="40%">
            <div class="dialog-content">
                <el-form class="dialog-form" :model="updateData" ref="formGrade" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateData.id"></el-input></el-form-item>
                    <el-form-item label="评级：" prop="firstgrade" :rules="[{ required: true, message: '请选择评级', trigger: 'change'}]">
                        <el-select v-model="updateData.firstgrade" value-key="id" filterable>
                            <el-option v-for="item in gradeList" :key="item.id" :label="item.grade" :value="item.grade"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="备注:" prop="firstgraderemark" :rules="[{ required: true, message: '请填写备注', trigger: 'blur'}]">
                        <el-input v-model="updateData.firstgraderemark" type="textarea" maxlength="200" 
                        :autosize="{minRows: 3, maxRows: 5}"></el-input>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelUpdate"></button-common>
                    <button-orange buttonName="保 存" size="mini" :fns="sureUpdate"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import common from "@/api/common";
    import { queryDepGradePagerList, updateDepGrade, queryDepGradeScale, queryCenterGradeScale, gradeCommit } from "@/api/zzkhApi/depGrade";
    import { queryGradeList, exportPjGradePagerList} from "@/api/zzkhApi/grade";
    import pager from "@/components/Pager";
    import { mapGetters } from "vuex";
    export default {
        name: 'gradeList',
        components: { pager, cleanBtn, buttonOrange, buttonCommon },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 总数
                ifShowDialog: false,
                tableData: [],
                oriGradeList: [],
                gradeList: [],
                gradeScale: [],
                checkNumRes: true,
                overGrade: "",
                sign: null,
                isSecond: false,
                updateData: {
                    id: null,
                    firstgrade: null,
                    firstgraderemark: null,
                }
            }
        },
        computed: {
            ...mapGetters([
                'userId'
            ])
        },
        props:{},
        created(){
            this.sign = this.$route.query.sign;
            this.tyear = this.$route.query.tyear;
        },
        mounted() {
            this.submitBtn();
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.push({path:"/myDepKpiDb"});
            },

            // 查询
            submitBtn: function () {
                this.seaTableData();
                this.queryGradeList();
            },

            // 查询列表数据
            seaTableData(params) {
                var val = {};
                val.size = this.size;
                val.index = this.index;
                val.sign = this.sign;
                val.tyear = this.tyear;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryDepGradePagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.tableData = res.data.results.filter(li => {
                            if(this.userId == li.secondgradeuser ){
                                this.isSecond = true;
                            }
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', '')
                            }
                            if (li.centername != undefined) {
                                li.centername = li.centername.replace('总行金融科技部-', '')
                            }
                            return li
                        });
                        this.queryDepGradeScale();
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

            // 级别比例查询
            queryDepGradeScale(){
                var val = {};
                val.sign = this.sign;
                val.tyear = this.tyear;
                let data = common.paramsFormat(val);
                const loading = common.loading(this);
                if(val.sign == 1){
                    queryDepGradeScale(data).then(res => {
                        loading.close();
                        console.log('级别比例查询', res.data.results)
                        if (res.data.status == 200) {
                            this.checkNumRes = true;
                            this.overGrade = "";
                            this.gradeScale = res.data.results;
                            if(this.isSecond){
                                res.data.results.forEach(element => {
                                    element.gradelist.forEach(gradeItem => {
                                        if (gradeItem.data.num > gradeItem.data.total) {
                                            this.checkNumRes = false;
                                            this.overGrade = element.centername + gradeItem.grade;
                                            return false;
                                        }
                                    });
                                    if(!this.checkNumRes){
                                        return false;
                                    }
                                });
                            }
                           
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                } else {
                    queryCenterGradeScale(data).then(res => {
                        loading.close();
                        console.log('级别比例查询', res.data.results)
                        if (res.data.status == 200) {
                            this.checkNumRes = true;
                            this.overGrade = "";
                            this.gradeScale = res.data.results;
                            res.data.results.forEach(element => {
                                if (element.data.secondnum > element.data.total) {
                                    this.checkNumRes = false;
                                    this.overGrade = element.grade;
                                    return false;
                                }
                            });
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }   
            },

            // 级别列表查询
            queryGradeList(){
                var val = {};
                val.sign = this.sign;
                if(this.sign == 1){
                    val.type = 2;
                }else {
                    val.type = 1;
                }
                let data = common.paramsFormat(val);
                const loading = common.loading(this);
                queryGradeList(data).then(res => {
                    loading.close();
                    console.log('级别列表查询', res.data.results)
                    if (res.data.status == 200) {
                        this.oriGradeList = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            updateDepGarde(val) {
                this.updateData.id = val.id;
                this.updateData.firstgrade = val.firstgrade;
                this.updateData.firstgraderemark = val.firstgraderemark;
                this.gradeList = [];
                this.gradeList = this.oriGradeList.filter(item => item.centerid == val.centerid)
                this.ifShowDialog = true;
            },

            cancelUpdate() {
                this.ifShowDialog = false;
                this.updateData = {
                    id: null,
                    firstgrade: null,
                    firstgraderemark: null,
                }
            },

            sureUpdate() {
                this.updateData;
                this.$refs["formGrade"].validate(valid => {
                    if (!valid) {
                        this.$message.error('请正确填写必填项');
                        return false;
                    } 
                    const loading = common.loading(this);
                    var params = {};
                    params.id = this.updateData.id;
                    params.firstgrade = this.updateData.firstgrade;
                    params.firstgraderemark = this.updateData.firstgraderemark;
                    params = JSON.parse(JSON.stringify(params));
                    let data = common.paramsFormat(params);
                    updateDepGrade(data).then(res => {
                        loading.close();
                        console.log("修改确定", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('修改成功');
                            this.$refs["formGrade"].resetFields();
                            this.ifShowDialog = false;
                            this.seaTableData();
                            // this.queryDepGradeScale();
                        } else {
                            this.$message.error(res.data.message);
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                });
            },

            changeGrade(val) {
                const loading = common.loading(this);
                var params = {};
                params.id = val.id;
                params.secondgrade = val.secondgrade;
                params = JSON.parse(JSON.stringify(params));
                let data = common.paramsFormat(params);
                updateDepGrade(data).then(res => {
                    loading.close();
                    console.log("修改确定", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('修改成功');
                        this.seaTableData();
                        // this.queryDepGradeScale();
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            commit(){
                if(!this.checkNumRes){
                    this.$message.error(this.overGrade +  '评级人数超出，请调整后再次提交！');
                    return false;
                }
                this.$confirm('确认提交吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    var data = { 
                        sign: this.sign,
                        tyear: this.tyear
                    };
                    data = common.paramsFormat(data);
                    gradeCommit(data).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('提交成功');
                            this.goBack();
                        } else {
                            this.$message.error(res.data.message)
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => {})
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let val = {};
                val.sign = this.sign;
                val.tyear = this.tyear;
                let params = common.paramsFormat(val);
                const self = this;
                exportPjGradePagerList(params).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "部门评级.xls");
                });
            },

        },
    }
</script>
<style scoped lang="scss">
    .web-container {
        padding: 25px;
    }

    .web-bread {
        /* padding: 15px 25px 5px 25px; */
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 15px;
    }

    .web-bread .el-breadcrumb {
        display: inline-block;
        vertical-align: middle;
        margin-left: 10px;
    }

    .web-body {
        padding: 15px;
        background: #FFFFFF;
    }

    .web-body-head {
        .web-body-item {
            color: rgb(93, 120, 255);
            margin-bottom: 5px;
            .web-body-item-dep {
                display: inline-block;
                font-size: 16px;
                font-weight: 700;
                color: black;
                margin-right: 10px;
                margin-left: 8px;
            }
            .web-body-item-sacle {
                display: inline-block;
                background-color: rgb(224, 235, 254);
                border-radius: 3px;
                padding: 5px;
                margin-left: 5px;
                .web-body-item-sacle-grade {
                    font-size: 16px;
                    font-weight: 700;
                    margin-right: 5px;
                }
            }
            .red {
                color: rgb(255, 93, 93) !important;
                background-color: rgb(254, 228, 224);
            }
        }
    }


    .pager-zj {
        text-align: center;
    }
    
    .hmddialog-bottom {
        text-align: center;
        margin-top: 19px;
    }

    ::v-deep .el-table__empty-block {
        width: 100%;
        min-width: 100%;
        max-width: 100%;
    }

    ::v-deep .otherpoint {
        font-weight: 700;
        font-style: normal;
        font-size: 14px;
    }

    ::v-deep .sumpoint {
        font-weight: 700;
        font-style: normal;
        font-size: 14px;
        color: rgb(64, 158, 255);
    }
    ::v-deep .defaultgrade {
        font-weight: 800;
    }
    ::v-deep .secondgrade .el-input__inner{
        font-weight: 800;
        color: #5D78FF;
    }
    ::v-deep .secondgrade .el-select{
        width: 30%;
        margin: auto;
        min-width: 60px;
    }
</style>
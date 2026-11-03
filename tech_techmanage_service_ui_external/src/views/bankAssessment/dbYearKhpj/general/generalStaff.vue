<!-- 分管总-员工 -->
<template>
    <div class="lzpg-container">
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
                            <el-col :span="4">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.centerid">
                                        <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <div class="target-head-left-threeword">
                                        <el-select filterable v-model="paramsForm.depid">
                                            <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                            <template slot="prefix">二级部：</template>
                                        </el-select>
                                    </div>
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
            <div class="target-body-head-wtj">未提交二级部还有&nbsp;<span style="color: #FF0422">{{noneDep}}</span>&nbsp;个</div>
            <div class="yggl-body-operation">
                <div>
                    <div v-for="(item, index) in tipGroup" :key="index" class="gys-home-middle-list">
                        <dl>
                            <dt>{{item.centerName}}：</dt>
                            <dd>
                                <span v-for="(items, indexs) in item.data" :key="indexs" class="operation-block" :class="[items.currentPersons > items.setPersons && items.setPersons != 0 ? 'operation-block-red' : 'operation-block-blue']"><span
                                        class="operation-block-left">{{items.tlevel}}</span><span class="operation-block-right">{{items.currentPersons}}/{{items.setPersons}}人</span></span>
                            </dd>
                        </dl>
                    </div>
                </div>
                <div>
                    <el-button size="small" type="warning" @click="commit">提 交</el-button>
                </div>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
                    <el-table-column label="中心" width="160" prop="centername"></el-table-column>
                    <el-table-column label="二级部" width="160" prop="depname"></el-table-column>
                    <el-table-column label="工号" width="100" prop="eid" fixed="left"></el-table-column>
                    <el-table-column label="姓名" width="120" prop="eidname" fixed="left"></el-table-column>
                    <el-table-column label="角色" width="130" prop="rolename"></el-table-column>
                    <el-table-column label="初始总分" width="80" prop="initialtscore"></el-table-column>
                    <el-table-column label="初始二级部排名" width="80" prop="initialorder"></el-table-column>
                    <el-table-column label="加分" width="80" prop="addscore"></el-table-column>
                    <el-table-column label="二级部备注" width="200" prop="depremark"></el-table-column>
                    <el-table-column label="调整后总分" width="100" prop="deptscore"></el-table-column>
                    <el-table-column label="调整后二级部排名" width="100" prop="deporder"></el-table-column>
                    <el-table-column label="二级部建议评级" width="110" prop="deptlevel"></el-table-column>
                    <el-table-column label="中心备注" width="200" prop="centerremark"></el-table-column>
                    <el-table-column label="中心建议评级" width="105" prop="centertlevel"></el-table-column>
                    <el-table-column label="操作" width="100" fixed="right">
                        <template slot-scope="scope">
                            <el-button @click="addScore(scope.row)">修 改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index" v-model="$attrs"
                        ref="pager" />
                </div>
            </div>
        </div>
        <el-dialog title="评级" :visible.sync="ifShowJfDialog" center @close="addClose">
            <el-form label-position="left" label-width="80px" :model="updateSingData" ref="formXgSing" :rules="rules">
                <el-form-item label="评级" prop="centertlevel">
                    <el-select v-model="updateSingData.centertlevel">
                        <el-option v-for="(item, index) in grade_options" :key="index" :label="item.dictname" :value="item.dictid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注" prop="centerremark">
                    <el-input type="textarea" maxlength="300" show-word-limit :autosize="{ minRows: 3, maxRows: 5}"
                        v-model="updateSingData.centerremark"></el-input>
                </el-form-item>
            </el-form>
            <div class="hmddialog-bottom">
                <el-button @click="cancelXgSing">取 消</el-button>
                <el-button type="primary" @click="sureXgSing">保 存</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import pager from "@/components/Pager";
    import common from "@/api/common";
    import { getCenterYearDataPager, updateCenterTlevel, submitCenterYearEval, getCenterLevelPersonTip, getNoSubmitDepTip } from "@/api/hykh/yearPj";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryList } from "@/api/integrated/newEmBmfp";
    export default {
        name: 'generalStaff',
        components: { cleanBtn, buttonOrange, buttonCommon, pager },
        data() {
            return {
                paramsForm: {
                    tyear: null,
                    centerid: null,
                    depid: null,
                    eid: null,
                    eidname: null,
                },
                depData: [],
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                targetData: [],
                tableHeight: null,
                ifShowJfDialog: false,
                updateSingData: {
                    centertlevel: null,
                    centerremark: null
                }, //评级
                rules: {
                    centertlevel: [
                        { required: true, message: '请选择评级', trigger: 'change' }
                    ],
                    centerremark: [
                        { required: true, message: '请填写备注', trigger: 'blur' }
                    ]
                },
                grade_options: [], //评级
                tipGroup: [],
                centerData: [], //中心
                noneDep: 0
            }
        },
        mounted() {
            console.log('路由传值--主持经理', this.$route.params)
            this.getYear();
            let params = {};
            params.tyear = this.$route.params.tyear;
            params.term = '年度';
            params.role = 'COMM_USER';
            this.seaTableData(params);
            this.getNoneDep();
            this.getCenter();
            this.getKjDep();
            this.getDic();
            this.getGradeTips();
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
                getDicEntry({ dicttypeid: "KPI_LEVEL" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //状态
                    this.grade_options = data.filter(item => {
                        return item.dicttypeid == "KPI_LEVEL";
                    });
                });
            },

            //获取等级标签
            getGradeTips() {
                const loading = common.loading(this);
                let params = {};
                params.Otype = '2';
                params.role = 'COMM_USER';
                let data = common.paramsFormat(params);
                getCenterLevelPersonTip(data).then(res => {
                    loading.close();
                    console.log('获取等级标签', res.data)
                    if (res.data.status == 200) {
                        this.tipGroup = this.arrayGroup(res.data.results, 'centerid')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            //获取未提交部门
            getNoneDep() {
                const loading = common.loading(this);
                let params = {};
                params.Otype = '2';
                params.role = 'COMM_USER';
                let data = common.paramsFormat(params);
                getNoSubmitDepTip(data).then(res => {
                    loading.close();
                    console.log('获取未提交部门', res.data)
                    if (res.data.status == 200) {
                        this.noneDep = res.data.results.currentDeps
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 数组分类
            arrayGroup(arr, key) {
                let map = {}, dest = [];
                for (let i = 0; i < arr.length; i++) {
                    let ai = arr[i];
                    if (!map[ai[key]]) {
                        dest.push({
                            key: ai[key],
                            centerName: ai['centerName'],
                            data: [ai]
                        });
                        map[ai[key]] = ai;
                    } else {
                        for (let j = 0; j < dest.length; j++) {
                            let dj = dest[j];
                            if (dj.key == ai[key]) {
                                dj.data.push(ai);
                                break;
                            }
                        }
                    }
                }
                return dest
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
                params.role = 'COMM_USER';
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
                params.role = 'COMM_USER';
                params.eid = this.paramsForm.eid;
                params.eidname = this.paramsForm.eidname;
                this.index = params.index = 1;
                params.size = this.size;
                this.seaTableData(params);
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getCenterYearDataPager(data).then(res => {
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

            // 评级
            addScore(val) {
                let rowData = JSON.parse(JSON.stringify(val));
                if (rowData.centertlevel != undefined) {
                    this.updateSingData.centertlevel = rowData.centertlevel
                }
                if (rowData.centerremark != undefined) {
                    this.updateSingData.centerremark = rowData.centerremark
                }
                this.updateSingData.id = rowData.id
                this.ifShowJfDialog = true;
            },

            // 评级
            sureXgSing() {
                console.log("评级", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.id = this.updateSingData.id;
                        params.centertlevel = this.updateSingData.centertlevel;
                        params.centerremark = this.updateSingData.centerremark;
                        let data = common.paramsFormat(params);
                        updateCenterTlevel(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success(res.data.message);
                                this.ifShowJfDialog = false;
                                let params = {};
                                params.tyear = this.paramsForm.tyear;
                                params.depid = this.paramsForm.depid;
                                params.centerid = this.paramsForm.centerid;
                                params.term = '年度';
                                params.role = 'COMM_USER';
                                params.eid = this.paramsForm.eid;
                                params.eidname = this.paramsForm.eidname;
                                this.index = params.index = 1;
                                params.size = this.size;
                                this.seaTableData(params);
                                this.getGradeTips();
                            } else {
                                this.$message.error(res.data.message)
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    } else {
                        this.$message.error('请填写必填项');
                        return false;
                    }
                });
            },

            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formXgSing"].resetFields();
            },

            // 修改单人取消
            cancelXgSing() {
                this.ifShowJfDialog = false;
            },

            //提交
            commit() {
                const loading = common.loading(this);
                let params = {};
                params.role = 'COMM_USER';
                let data = common.paramsFormat(params);
                submitCenterYearEval(data).then(res => {
                    loading.close();
                    console.log("提交", res.data)
                    if (res.data.status == 200) {
                        this.$message.success(res.data.message);
                        let params = {};
                        params.tyear = this.paramsForm.tyear;
                        params.depid = this.paramsForm.depid;
                        params.centerid = this.paramsForm.centerid;
                        params.term = '年度';
                        params.role = 'COMM_USER';
                        params.eid = this.paramsForm.eid;
                        params.eidname = this.paramsForm.eidname;
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
            }
        }
    }
</script>
<style scoped>
    .lzpg-container {
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

    .yggl-body-operation {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;
    }

    .operation-block {
        box-sizing: border-box;
        border-width: 1px;
        border-style: solid;
        border-radius: 3px;
        padding: 5px 7px;
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        margin-right: 15px;
    }

    .operation-block-red {
        background-color: rgba(254, 228, 224, 1);
        border-color: rgba(254, 224, 224, 1);
        color: #FF5D5D;
    }

    .operation-block-blue {
        background-color: rgba(224, 235, 254, 1);
        border-color: rgba(224, 235, 254, 1);
        color: #5D78FF;
    }

    .operation-block-left {
        font-size: 16px;
        font-weight: 700;
        margin-right: 7px;
    }

    .operation-block-right {
        font-size: 14px;
    }

    .gys-home-middle-list dl {
        margin: 0;
        overflow: hidden;
    }

    .gys-home-middle-list dt {
        float: left;
        font-weight: 400;
        height: 40px;
        line-height: 40px;
    }

    .gys-home-middle-list dd {
        margin-left: 0px;
        float: left;
        line-height: 40px;
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

    .target-body-head-wtj {
        font-size: 16px;
    color: #181C32;
    font-family: 'Arial Normal', 'Arial', sans-serif;
    font-weight: 400;
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
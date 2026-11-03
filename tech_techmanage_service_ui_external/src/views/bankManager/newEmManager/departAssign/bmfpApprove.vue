<template>
    <div class="depAss-container">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="5">
                                <p class="nav_name">新员工部门分配</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="depAss-main">
            <div class="depAss-main-table">
                <div class="depAss-main-table-title">如有问题，请联系发起人
                    <span>{{fqPeo}}</span>
                </div>
                <el-table :data="depAssData.list">
                    <el-table-column fixed label="序号" width="90">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.sequence" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="姓名" width="90">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.name" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="中心" width="150" prop="">
                        <template slot-scope="scope">
                            <el-select placeholder="" v-model="scope.row.center" @change="tableCenterChange(scope.$index)">
                                <el-option v-for="item in centerData" :key="item.id" :label="item.centername" :value="item.id"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="部门" width="240" prop="">
                        <template slot-scope="scope">
                            <!-- <el-select placeholder="" v-model="scope.row.deptId">
                                <el-option v-for="item in depData" :key="item.depId" :label="item.depName" :value="item.depId"></el-option>
                            </el-select> -->
                            <center-dept :centerId="scope.row.center" :input="scope.row.depName" :tableIndex="scope.$index"
                                @depChange='deptZjChange' :isEdit="isEdit" :depAllDataProp="depAllData"></center-dept>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="方向" width="120" prop="">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.direction" placeholder="" @change="tableDataChange(scope.$index)">
                                <el-option v-for="item in dire_options" :key="item.dictid" :label="item.dictname"
                                    :value="item.dictid">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="等级" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.grade" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="简历ID" width="90" prop="resumeId"></el-table-column>
                    <el-table-column label="性别" width="90" prop="">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.sex" placeholder="" @change="tableDataChange(scope.$index)">
                                <el-option v-for="item in sex_options" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="总分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.totalScore" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="技术面试成绩" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.technicalScore" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="技术面试记分" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.technicalScoring" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="技术面试评语" width="600" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.technicalComments"
                                @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="行政面试成绩" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.administrativeScore" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="行政面试记分" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.administrativeScoring" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="行政面试评语" width="600" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.administrativeComments"
                                @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="培训成绩" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.trainingScore" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="培训记分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.trainingScoring" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="笔试成绩" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.writtenScore" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="笔试记分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.writtenScoring" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="其他成绩" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.otherScore" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="其他记分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.otherScoring" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="备注" width="300" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.note" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="出生日期" width="160" prop="">
                        <template slot-scope="scope">
                            <el-date-picker v-model="scope.row.bornDate" value-format="yyyy-MM-dd" @change="tableDataChange(scope.$index)">
                            </el-date-picker>
                        </template>
                    </el-table-column>
                    <el-table-column label="籍贯" width="300" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.natives" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制最高学历" width="120" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.highest" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制本科院校" width="120" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.coursesCollege" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制本科专业" width="160" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.coursesProfessional" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制硕士院校" width="120" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.mastersCollege" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制硕士专业" width="160" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.degreeProfessional" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="批次" width="135" prop="">
                        <template slot-scope="scope">
                            <el-date-picker v-model="scope.row.batch" value-format="yyyy-MM" type="month" @change="tableDataChange(scope.$index)">
                            </el-date-picker>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager_zb">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="pageSize" v-model="$attrs" ref="pager" />
                </div>
            </div>
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs ref="log"></flow-logs>
            </div>
        </div>

        <div class="nav-bottom">
            <approve-bottom :proving="proving" :flowItem="bps"></approve-bottom>
        </div>
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPagerNotAuth, queryDeptList, queryList, update, processApproval, queryDetail, queryCenter } from "@/api/integrated/newEmBmfp";
    import Moment from 'moment';
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import centerDept from "@/views/bankManager/newEmManager/departAssign/centerDeptDialog";
    import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
    import approveBottom from "@/views/plugins/flowPlug/approveBottom";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    export default {
        components: { buttonCommon, pager, centerDept, flowLogs, approveBottom },
        name: 'bmfpApprove',
        data() {
            return {
                size: 'mini',  //按钮大小
                buttonClose: '关 闭', //关闭按钮名字
                depData: [],
                depAssData: {
                    list: []
                },
                sex_options: [], // 性别数据集合
                dire_options: [], //方向集合
                centerData: [], //中心数据集合
                totalNumber: 0, //分页总条数
                pageSize: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                bps: {},
                fqPeo: '', //发起人
                isEdit: true,
                depAllData: [], //所有部门
            }
        },
        mounted() {
            this.getDic();
            this.getCenter();
            this.getAllDep();
        },
        methods: {
            init(item) {
                console.log("部门分配审批", item);
                this.bps = item;
                this.getDetail(item.processInstID);
                this.$refs.log.init(item.processInstID);
            },

            // 查询详情
            getDetail(val) {
                queryDetail({ instanceId: val }).then(res => {
                    console.log('获取详情', res.data)
                    if (res.data.status == 200) {
                        this.fqPeo = res.data.results.initiateName + '(' + res.data.results.initiateId + ')';
                        let params = {};
                        params.mid = res.data.results.id;
                        this.searchUpLoad(params);
                    } else {
                        this.$message.error('查询详情失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查询业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "BUSI_OUTSOURCE_GENFER,NEWKG_DIRECTION"
                })
                    .then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        // 性别
                        this.sex_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
                        });
                        // 方向
                        this.dire_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_DIRECTION";
                        });
                        return data;
                    });
            },

            // 获取部门
            getDep(id) {
                const loading = common.loading(this);
                queryDeptList({ centerId: id })
                    .then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.depData = fild.filter(tt => {
                                tt.depName = tt.depName.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    console.log('中心', res.data)
                    if (res.data.status == 200) {
                        // this.centerData = res.data.results;
                        this.centerData = res.data.results.filter(li => {
                            if (li.id != 2) {
                                return li
                            }
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 中心改变
            centerChange(val) {
                console.log('中心改变', val)
                this.getDep(val);
            },

            // 表格中中心改变
            tableCenterChange(index) {
                if (this.depAssData.list[index].depName != undefined) {
                    this.depAssData.list[index].depName = '';
                }
                let bmfpData = this.depAssData.list[index];
                let data = common.paramsFormat(bmfpData);
                update(data).then(res => {
                    console.log('更新中心', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');

                    } else {
                        this.$message.error('更新失败');
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 表格中数据改变
            tableDataChange(index) {
                let bmfpData = this.depAssData.list[index];
                let data = common.paramsFormat(bmfpData);
                update(data).then(res => {
                    console.log('更新中心', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');

                    } else {
                        this.$message.error('更新失败');
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查中心
            searchCenter(val, index) {
                queryCenter({ depId: val }).then(res => {
                    console.log('中心', res.data)
                    if (res.data.status == 200) {
                        this.depAssData.list[index].center = res.data.results.id;
                        this.depAssData.list.splice(0, 0);
                        this.$nextTick(() => {
                            this.tableDataChange(index);
                        })
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 部门变化
            deptZjChange(val, index) {
                let dep;
                if (val.depId != undefined) {
                    dep = val.depId.replace('dep', '');
                    this.depAssData.list[index].depName = val.depName;
                } else {
                    dep = val.id.replace('dep', '');
                    this.depAssData.list[index].depName = val.text;
                }
                this.searchCenter(dep, index);
                this.depAssData.list[index].deptId = dep;
                let bmfpData = this.depAssData.list[index];
                let data = common.paramsFormat(bmfpData);
                update(data).then(res => {
                    console.log('更新中心', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');
                    } else {
                        this.$message.error('更新失败');
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },

            // 查询上传数据
            searchUpLoad(val) {
                let param = common.paramsFormat(val);
                const loading = common.loading(this);
                queryPagerNotAuth(param).then(res => {
                    console.log("查询上传数据", res.data)
                    loading.close();
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        res.data.results = res.data.results.filter(li => {
                            if (li.depName != undefined) {
                                li.depName = li.depName.replace('总行金融科技部-', '');
                            }
                            li.bornDate = Moment(li.bornDate).format('YYYY-MM-DD');
                            return li
                        })
                        this.depAssData.list = res.data.results;
                    } else {
                        this.$message.error('查询数据失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.size = size;
                params.index = index;
                this.pageSize = size;
                this.index = index;
                this.searchUpLoad(params);
            },

            // 提交
            proving(data) {
                let status = new Promise(() => { });
                status = this.liuzhuan(data).then((val) => {
                    return val;
                });
                return status
            },

            liuzhuan(params) {
                const loading = common.loading(this);
                this.bps.conclusion = params.conclusion;
                this.bps.suggest = params.suggest;
                let folw = { "bps": this.bps };
                return processApproval(folw).then(res => {
                    loading.close();
                    return res.data
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 获取所有部门
            getAllDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    if (res.data.status == 200) {
                        this.depAllData = res.data.results[0].children.filter(tt => {
                            tt.text = tt.text.replace('总行金融科技部-', "");
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error);
                });
            },
        }

    }
</script>
<style scoped>
    .depAss-container {
        padding-bottom: 85px;
    }

    .depAss-main {
        padding: 17px;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
        height: 24px;
        font-size: 16px;
        font-weight: 600;
        color: rgba(38, 38, 38, 1);
        line-height: 24px;
        display: inline-block;
    }

    .nav .wrapper {
        margin-left: 25px;
        margin-right: 25px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .depAss-main-table {
        border-radius: 4px;
        padding: 18px;
        background-color: #ffffff;
    }

    .pager_zb {
        text-align: center;
    }

    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }

    .depAss-main-table-title {
        margin-bottom: 5px;
    }
</style>
<style>

</style>
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
                <el-table :data="depAssData.list">
                    <el-table-column fixed label="序号" width="60" prop="sequence"></el-table-column>
                    <el-table-column fixed label="姓名" prop="name" width="90"></el-table-column>
                    <el-table-column fixed label="中心" width="150" prop="" :disabled="true">
                        <template slot-scope="scope">
                            <el-select placeholder="" v-model="scope.row.center" :disabled="true">
                                <el-option v-for="item in centerData" :key="item.id" :label="item.centername" :value="item.id"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="部门" width="150" prop="depName"></el-table-column>
                    <el-table-column fixed label="方向" width="135" prop="" :disabled="true">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.direction" placeholder="" :disabled="true">
                                <el-option v-for="item in dire_options" :key="item.dictid" :label="item.dictname"
                                    :value="item.dictid">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="等级" width="90" prop="grade"></el-table-column>
                    <el-table-column label="简历ID" width="90" prop="resumeId"></el-table-column>
                    <el-table-column label="性别" width="90" prop="" :disabled="true">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.sex" placeholder="" :disabled="true">
                                <el-option v-for="item in sex_options" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="总分" width="90" prop="totalScore"></el-table-column>
                    <el-table-column label="技术面试成绩" width="105" prop="technicalScore"></el-table-column>
                    <el-table-column label="技术面试记分" width="105" prop="technicalScoring">
                    </el-table-column>
                    <el-table-column label="技术面试评语" width="105" prop="technicalComments" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="行政面试成绩" width="105" prop="administrativeScore"></el-table-column>
                    <el-table-column label="行政面试记分" width="105" prop="administrativeScoring"></el-table-column>
                    <el-table-column label="行政面试评语" width="105" prop="administrativeComments" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="培训成绩" width="90" prop="trainingScore"></el-table-column>
                    <el-table-column label="培训记分" width="90" prop="trainingScoring"></el-table-column>
                    <el-table-column label="笔试成绩" width="90" prop="writtenScore"></el-table-column>
                    <el-table-column label="笔试记分" width="90" prop="writtenScoring"></el-table-column>
                    <el-table-column label="其他成绩" width="90" prop="otherScore"></el-table-column>
                    <el-table-column label="其他记分" width="90" prop="otherScoring"></el-table-column>
                    <el-table-column label="备注" width="90" prop="note" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="出生日期" width="120" prop="bornDate"></el-table-column>
                    <el-table-column label="籍贯" width="90" prop="natives" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="全日制最高学历" width="120" prop="highest"></el-table-column>
                    <el-table-column label="全日制本科院校" width="120" prop="coursesCollege" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="全日制本科专业" width="120" prop="coursesProfessional" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="全日制硕士院校" width="120" prop="mastersCollege" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="全日制硕士专业" width="120" prop="degreeProfessional" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="批次" width="120" prop="batch"></el-table-column>
                </el-table>
                <div class="pager_zb">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="pageSize" v-model="$attrs" ref="pager" />
                </div>
            </div>
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs-gd ref="log"></flow-logs-gd>
            </div>
        </div>

        <div class="nav-bottom" v-if="bps.status == 'yb'">
            <yb-revoke :flowItem="bps"></yb-revoke>
        </div>
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPagerNotAuth, queryDeptList, queryList, queryDetail } from "@/api/integrated/newEmBmfp";
    import Moment from 'moment';
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import FlowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
    import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
    export default {
        components: { buttonCommon, pager, FlowLogsGd, ybRevoke },
        name: 'bmfpYbGd',
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
            }
        },
        mounted() {
            this.getDic();
            this.getCenter();
        },
        methods: {
            init(item) {
                console.log("部门分配审批", item);
                this.bps = item;
                if (item.status == 'yb') {
                    this.getDetail(item.processInstID);
                    this.$refs.log.init(item);
                } else if (item.status == 'gd') {
                    this.getDetail(item.instanceid);
                    this.$refs.log.init(item);
                }
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
                        this.centerData = res.data.results;
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
                        this.$message.error('查询导入数据失败')
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
</style>
<style>

</style>
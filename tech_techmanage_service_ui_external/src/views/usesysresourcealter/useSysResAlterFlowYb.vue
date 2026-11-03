<!-- 资产统计报表 -->
<template>
    <div class="app-container">
        <el-dialog :title="titleName" :visible.sync="ybDialog" fullscreen center :before-close="button_cancel">
            <div class="app-main-container">
                <el-form :model="usesysForm" ref="usesysForm" inline label-width="120px">
                    <el-input v-model="usesysForm.appsysno" v-if="false"></el-input>
                    <el-row>
                        <el-col :span="10">
                            <el-form-item label="应用系统：" prop="appsysname">
                                <el-input v-model="usesysForm.appsysname" size="mini" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="10">
                            <el-form-item label="流程编号：" prop="flowno">
                                <el-input v-model="usesysForm.flowno" size="mini" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>

                <el-table :data="flowData" height="200px" border stripe style="width:100%;">
                    <el-table-column type="index" label="序号" width="50">
                    </el-table-column>
                    <el-table-column prop="deloradd" label="动作" :show-overflow-tooltip="true" align="center">
                        <template slot-scope="scope">
                            <span v-for="(item, index) of delOrAddData" :key="index">
                                <span v-if="item.dictid == scope.row.deloradd">{{ item.dictname}}</span>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ciclassifykey" label="分类" :show-overflow-tooltip="true" align="center">
                        <template slot-scope="scope">
                            <span v-for="(item, index) of classifyData" :key="index">
                                <span v-if="item.dictid == scope.row.ciclassifykey">{{ item.dictname}}</span>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ciname" cell-mouse-enter="" label="资源名称" :show-overflow-tooltip="true" align="center">
                        <template slot-scope="scope">
                            <span>{{flowData[scope.$index].ciname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="localip" label="本地IP" align="center"></el-table-column>
                    <el-table-column prop="osversion" label="操作系统版本" align="center"></el-table-column>
                    <el-table-column prop="logicalresourcestats" label="逻辑设备状态" align="center">
                        <template slot-scope="scope">
                            <span v-for="(item, index) of logicalStatsData" :key="index">
                                <span v-if="item.dictid == scope.row.logicalresourcestats">{{ item.dictname}}</span>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="physicalresourcestats" label="物理设备状态" align="center">
                        <template slot-scope="scope">
                            <span v-for="(item, index) of physicalStatsData" :key="index">
                                <span v-if="item.dictid == scope.row.physicalresourcestats">{{ item.dictname}}</span>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="usertype" label="用户类型" align="center"></el-table-column>
                    <el-table-column prop="explain" label="特例说明" align="center"></el-table-column>
                    <el-table-column prop="serialno" label="序列号" align="center"></el-table-column>
                </el-table>

            </div>
            <div class="czLog">
                <flow-logs-gd ref="log"></flow-logs-gd>
            </div>
            <div class="nav-bottom" v-show="flowItem.status == 'yb'">
                <yb-revoke :flowItem="flowItem"></yb-revoke>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import common from '@/api/common'
import { getDicEntry } from '@/api/eosDictEntry'
import ybRevoke from '@/views/plugins/flowPlug/ybRevoke'
import flowLogsGd from '@/views/resource/flow/flowLogsGd'
import { queryResUseSysAlterData } from '@/api/usesysresourcealter/usesysresourcealter'
export default {
    components: { flowLogsGd, ybRevoke },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 10, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },

            usesysForm: {
                appsysno: '',
                appsysname: '',
                flowno: ''
            },
            ybDialog: false,
            show: false,
            titleName: '',
            classifyData: [], // 资源分类
            logicalStatsData: [], //逻辑设备状态
            physicalStatsData: [], //物理设备状态
            delOrAddData: [
                { dictid: 1, dictname: '新增' },
                { dictid: 2, dictname: '删除' },
                { dictid: 3, dictname: '修改' }
            ], //动作
            conclusionData: [
                { dictid: '01', dictname: '同意' },
                { dictid: '02', dictname: '不同意' }
            ],
            flowData: [],
            flowItem: {}
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {},
    methods: {
        init(row) {
            this.ybDialog = true
            this.flowItem = row 
            this.queryResUseSysAlterData()
        },
        queryResUseSysAlterData() {
            let param = {
                processInstID: this.flowItem.processInstID
            }
            let data = common.paramsFormat(param)
            queryResUseSysAlterData(data).then((res) => {
                if (res.data.status == 200) {
                    this.flowData = res.data.results
                    this.usesysForm.appsysname = this.flowData[0].appsysname
                    this.usesysForm.flowno = this.flowData[0].flowno
                    this.$nextTick(() => {
                        // 流程日志
                        this.$nextTick(() => {
                            let item = {}
                            if (this.flowData[0].status == 4) {
                                item.status = 'gd'
                                item.flowstatus = '已结束'
                                item.instanceid = this.flowItem.processInstID
                            } else if (['3', '5'].indexOf(this.flowData[0].status) > -1) {
                                item.processInstID = this.flowItem.processInstID
                            } else {
                                item.processInstID = 'eeee'
                            }
                            this.$refs.log.init(item)
                        })
                    })
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
        },
        button_cancel() {
            if (this.flowItem.status == 'yb') {
                this.$router.push({
                    path: 'dashboard'
                })
            } else {
                this.ybDialog = false
            }
        },
        // 查询业务字典
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_CLASSIFY,RES_LOGICAL_STATS,RES_PHYSICAL_STATS'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //资源分类
                this.classifyData = data.filter((item) => {
                    return item.dicttypeid == 'RES_CLASSIFY'
                })
                //逻辑设备状态
                this.logicalStatsData = data.filter((item) => {
                    return item.dicttypeid == 'RES_LOGICAL_STATS'
                })
                //物理设备状态
                this.physicalStatsData = data.filter((item) => {
                    return item.dicttypeid == 'RES_PHYSICAL_STATS'
                })
                return data
            })
        }
    }
}
</script>
<style lang="scss">
.el-form-item {
    margin-bottom: 5px;
}
.hidden {
    display: none;
}
.nav-bottom {
    width: 100%;
    background-color: #ffffff;
    position: fixed;
    bottom: 0;
}
</style>


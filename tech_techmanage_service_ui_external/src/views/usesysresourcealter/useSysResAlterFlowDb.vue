<!-- 资产统计报表 -->
<template>
    <div class="app-container">
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
                <el-table-column label="操作" width="100" align="center" v-if="show">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="onDelete(scope.row)">清 除</el-button>
                    </template>
                </el-table-column>

            </el-table>

            <div class="czLog app-container">
                <flow-logs :processInstId="flowItem.processInstID" ref="log"></flow-logs>
            </div>

            <div style="padding-left:300px;width:70%">
                <el-form ref="sumbitForm" inline label-width="120px" :model="sumbitForm" :rules="sumbitRules">

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="审批决策" prop="conclusion">
                                <el-select placeholder="" v-model="sumbitForm.conclusion" size="mini" :disabled="this.sumbitForm.activityDefID =='sysmanager'">
                                    <el-option v-for="item in conclusionData" :key="item.dictid" :label="item.dictname" :value="item.dictid+''"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item class="areaCss" label="审批意见" prop="audit">
                                <el-input type="textarea" v-model="sumbitForm.audit" :rows="3" size="mini" style="width: 100%"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>

            <div align="center">
                <el-button @click="button_cancel" size="mini">取 消</el-button>
                <el-button type="primary" @click="button_save" size="mini">审 批</el-button>
            </div>
        </div>
    </div>
</template>
<script>
import common from '@/api/common'
import { getDicEntry } from '@/api/eosDictEntry'
import flowLogs from '@/views/plugins/flowPlug/FlowLogs'
import { queryResUseSysAlterData, delUseSysAlterData, submitWorkItem, updateUseSysFlowData } from '@/api/usesysresourcealter/usesysresourcealter'
export default {
    components: { flowLogs },
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
            sumbitForm: {
                id: '',
                conclusion: '01',
                audit: '',
                workItemId: '',
                processInstId: '',
                applydesc: '',
                activityDefID: ''
            },
            sumbitRules: {
                conclusion: [{ required: true, message: '审批决策必填', trigger: 'blur' }],
                audit: [
                    { required: false, message: '审批意见必填', trigger: 'blur' },
                    { max: 100, message: '审批意见不能超过100个字符', trigger: 'blur' }
                ]
            },
            show: false,
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
        init(item) {
            this.flowItem = item
            this.sumbitForm.workItemId = this.flowItem.workItemID
            this.sumbitForm.processInstId = this.flowItem.processInstID
            this.sumbitForm.activityDefID = this.flowItem.activityDefID
            this.sumbitForm.applydesc = this.flowItem.workItemName
            this.queryResUseSysAlterData()
            // 流程日志
            this.$refs.log.init(this.flowItem.processInstID)
        },
        queryResUseSysAlterData(flag) {
            let param = {
                processInstID: this.flowItem.processInstID
            }
            let data = common.paramsFormat(param)
            queryResUseSysAlterData(data).then((res) => {
                if (res.data.status == 200) {
                    this.usesysForm.appsysname = res.data.results[0].appsysname
                    this.usesysForm.flowno = res.data.results[0].flowno
                    this.sumbitForm.id = res.data.results[0].resusesysid
                    this.sumbitForm.depid = res.data.results[0].depid
                    // 清除数据不回滚
                    // if (flag != 1) {
                    //     this.rollbackData()
                    // }

                    this.flowData = res.data.results
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
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
        },
        onDelete(row) {
            this.$confirm('确定删除吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then((res) => {
                let param = {}
                param.id = row.id
                param.activitydefid = this.sumbitForm.activityDefID
                let data = common.paramsFormat(param)
                delUseSysAlterData(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$message.error(res.data.message)
                        return false
                    } else {
                        this.$message.success(res.data.message)
                        this.queryResUseSysAlterData(1)
                    }
                })
            })
            //this.flowData.splice(index, 1)
        },
        button_save() {
            //提交工作流审批
            //需要数据1流程实例ID2工作项ID3节点描述4审批结论

            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            })
            submitWorkItem(this.sumbitForm).then((res) => {
                loading.close()
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                //处理返程，返回首页
                if (res.data.message.indexOf('已结束') == -1) {
                    let message = common.getFlowMessge(res.data.message)
                    let messageArr = message.split(',')
                    this.$nextTick(() => {
                        this.messageNotify(messageArr)
                        this.$router.push({
                            path: 'dashboard'
                        })
                    })
                } else {
                    this.$alert('流程已结束', { type: 'success' })
                    this.$router.push({
                        path: 'dashboard'
                    })
                }
            })
        },
        messageNotify(messageVal) {
            if (messageVal.length > 3) {
                messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(')')[0] + ')'
            }
            let messageSec = messageVal.slice(2).join('，')
            console.log('提示信息', messageSec, messageVal)
            this.$alert(messageVal[1] + ' ' + messageSec, { type: 'success' })
        },
        rollbackData() {
            console.log('数据回滚参数', this.sumbitForm)
            let param = {
                activitydefid: this.sumbitForm.activityDefID,
                resusesysid: this.sumbitForm.id
            }
            let data = common.paramsFormat(param)

            updateUseSysFlowData(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                } else {
                    this.$message({
                        type: 'success',
                        message: res.data.message
                    })
                }
            })
        },
        button_cancel() {
            this.$router.push({
                path: 'dashboard'
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
</style>


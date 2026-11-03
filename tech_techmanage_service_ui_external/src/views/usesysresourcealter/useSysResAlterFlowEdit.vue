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
                <el-row>
                    <el-col :span="10">
                        <el-form-item label="变更内容：">
                            <span>资源</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="10">
                        <el-form-item label="资源分类：" prop="resclassify">
                            <el-select placeholder="请选择" v-model="usesysForm.resclassify" size="mini" @change="onChangeType">
                                <el-option v-for="item in typeData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-button-group>
                <el-button type="success" size="mini" @click="addRows">添 加</el-button>
                <el-button type="danger" size="mini" @click="delRows">删 除</el-button>
                <el-button type="primary" size="mini" @click="editRows">修 改</el-button>
            </el-button-group>
            <el-table :data="tempData.filter((item)=>{
                        return item.deloradd!=2 && item.deloradd!=3
                    })" ref="tempData" height="300px" border stripe style="width:100%;margin-bottom:15px">
                <el-table-column type="selection" width="35"></el-table-column>
                <el-table-column prop="deloradd" label="动作" :show-overflow-tooltip="true" v-if="show"></el-table-column>
                <el-table-column prop="ciclassifykey" label="分类key" :show-overflow-tooltip="true" align="center" v-if="show"></el-table-column>
                <el-table-column prop="ciclassifyname" label="分类" :show-overflow-tooltip="true" align="center"></el-table-column>
                <el-table-column prop="ciname" label="资源名称" :show-overflow-tooltip="true" align="center"></el-table-column>
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
                <el-table-column prop="ciclassifyname" label="分类" :show-overflow-tooltip="true" align="center">
                    <template slot-scope="scope">
                        <span v-for="(item, index) of classifyData" :key="index">
                            <span v-if="item.dictid == scope.row.ciclassifykey">{{ item.dictname}}</span>
                        </span>
                    </template>

                </el-table-column>
                <el-table-column prop="ciname" cell-mouse-enter="" label="资源名称" :show-overflow-tooltip="true" align="center">
                    <template slot-scope="scope">
                        <el-input v-if="(flowData[scope.$index].ciclassifykey =='C_VM' || flowData[scope.$index].ciclassifykey =='C_machine') && flowData[scope.$index].deloradd =='3'" v-model="flowData[scope.$index].ciname"></el-input>
                        <span v-else>{{flowData[scope.$index].ciname}}</span>
                        <!-- </div> -->

                    </template>
                </el-table-column>
                <el-table-column prop="localip" label="本地IP" align="center"></el-table-column>
                <el-table-column prop="osversion" label="操作系统版本" align="center"></el-table-column>
                <el-table-column prop="logicalresourcestats" label="逻辑设备状态" align="center">
                    <template slot-scope="scope">
                        <el-select v-if="flowData[scope.$index].ciclassifykey =='C_VM' && flowData[scope.$index].deloradd =='3'" placeholder="请选择" v-model="scope.row.logicalresourcestats">
                            <el-option v-for="item in logicalStatsData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                        </el-select>
                        <span v-else v-for="(item, index) of logicalStatsData" :key="index">
                            <span v-if="item.dictid == scope.row.logicalresourcestats">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="physicalresourcestats" label="物理设备状态" align="center">
                    <template slot-scope="scope">
                        <el-select v-if="flowData[scope.$index].ciclassifykey =='C_machine' && flowData[scope.$index].deloradd =='3'" placeholder="请选择" v-model="scope.row.physicalresourcestats">
                            <el-option v-for="item in physicalStatsData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                        </el-select>
                        <span v-else v-for="(item, index) of physicalStatsData" :key="index">
                            <span v-if="item.dictid == scope.row.physicalresourcestats">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="usertype" label="用户类型" align="center"></el-table-column>
                <el-table-column prop="explain" label="特例说明" align="center"></el-table-column>
                <el-table-column prop="serialno" label="序列号" align="center"></el-table-column>
                <el-table-column label="操作" width="100" align="center">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="onDelete(scope.$index)">清 除</el-button>
                    </template>
                </el-table-column>

            </el-table>

            <div class="czLog app-container">
                <flow-logs :processInstId="flowItem.processInstID" ref="log"></flow-logs>
            </div>
            <div slot="footer" class="dialog-footer" align="center">
                <el-button @click="button_cancel" size="mini">取 消</el-button>
                <el-button type="primary" @click="toSumbit" size="mini">重新提交</el-button>
            </div>
        </div>
        <use-sys-add-flow-data ref="useSysAddFlowData"></use-sys-add-flow-data>
    </div>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import common from '@/api/common'
import pager from '@/components/Pager'
import flowLogs from '@/views/plugins/flowPlug/FlowLogs'
import { getDicEntry } from '@/api/eosDictEntry'
import relaSysTree from '@/views/resource/added/relaSysTree'
import repDeptTree from '@/views/resource/resourceReport/repDeptTree'
import useSysAddFlowData from '@/views/usesysresourcealter/useSysAddFlowData'
import { queryUseSysList, queryResUseSysAlterData, reSubmitWorkFlow } from '@/api/usesysresourcealter/usesysresourcealter'
export default {
    components: { cleanBtn, pager, relaSysTree, repDeptTree, queryUseSysList, flowLogs, useSysAddFlowData },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 10, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            usesysForm: {
                id: '',
                appsysno: '',
                appsysname: '',
                depid: '',
                alter: [],
                flowno: '',
                resclassify: ''
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
            ids: '', //过滤选择的数据
            show: false,
            classifyData: [], // 资源分类
            logicalStatsData: [], //逻辑设备状态
            physicalStatsData: [], //物理设备状态
            delOrAddData: [
                { dictid: 1, dictname: '新增' },
                { dictid: 2, dictname: '删除' },
                { dictid: 3, dictname: '修改' }
            ], //动作
            // conclusionData: [
            //     { dictid: '01', dictname: '同意' },
            //     { dictid: '02', dictname: '不同意' }
            // ],
            tempData: [],
            flowData: [], //
            typeData: [],
            editable: [],
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

        queryResUseSysAlterData() {
            let param = {
                processInstID: this.flowItem.processInstID
            }
            let data = common.paramsFormat(param)
            queryResUseSysAlterData(data).then((res) => {
                if (res.data.status == 200) {
                    this.flowData = res.data.results 
                    this.usesysForm.appsysname = this.flowData[0].appsysname
                    this.usesysForm.appsysno = this.flowData[0].appsysno
                    this.usesysForm.flowno = this.flowData[0].flowno
                    this.usesysForm.depid = this.flowData[0].depid
                    this.sumbitForm.id = this.flowData[0].resusesysid
                    this.usesysForm.id = this.flowData[0].resusesysid

                    //获取已经选择的配件ID
                    let ids = ''
                    this.flowData.forEach((item, index) => {
                        ids = ids + item.ciid + ','
                    })
                    this.ids = ids.substring(0, ids.length - 1)
                    // 获取应用系统数据
                    this.onChangeUseSys()
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
        },

        onChangeUseSys() {
            let param = {
                ...this.usesysForm,
                ids: this.ids
            } 
            let data = common.paramsFormat(param)
            queryUseSysList(data).then((res) => {
                if (res.data.status == 200) {
                    this.tempData = res.data.results
                    // 清空资源分类
                    this.typeData = []
                    this.usesysForm.resClassify = ''
                    this.classifyData.forEach((item) => {
                        this.tempData.forEach((res) => {
                            if (res.ciclassifykey == item.dictid && this.typeData.indexOf(item) == -1) {
                                this.typeData.push(item)
                            }
                        })
                    })
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
        },

        onChangeType() {
            //获取已经选择的配件ID
            let ids = ''
            this.flowData.forEach((item, index) => {
                ids = ids + item.ciid + ','
            })
            this.ids = ids.substring(0, ids.length - 1)
            let param = {
                ...this.usesysForm,
                ids: this.ids
            }
            let data = common.paramsFormat(param)
            queryUseSysList(data).then((res) => {
                if (res.data.status == 200) {
                    this.tempData = res.data.results

                    this.tempData = this.tempData.filter((res) => {
                        return res.ciclassifykey == this.usesysForm.resclassify
                    })
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
        },

        addRows() {
            this.$nextTick(() => {
                //获取已经选择的配件ID
                let ids = ''
                this.flowData.forEach((item, index) => {
                    ids = ids + item.ciid + ','
                })
                ids = ids.substring(0, ids.length - 1)

                this.$refs.useSysAddFlowData.init(ids)
            })
        },
        delRows() {
            let useSysData = this.$refs.tempData.selection
            if (useSysData.length == 0) {
                this.$message.error('请选中至少一条数据')
                return false
            }
            for (var i = 0; i < useSysData.length; i++) {
                if (this.flowData.indexOf(useSysData[i]) < 0) {
                    useSysData[i].deloradd = '2'
                    this.flowData.push(useSysData[i])
                }
            }
        },
        addUseSysData(obj) {
            let useSysData = obj
            for (var i = 0; i < useSysData.length; i++) {
                if (this.flowData.indexOf(useSysData[i]) < 0) {
                    useSysData[i].deloradd = '1'
                    this.flowData.push(useSysData[i])
                }
            }
        },
        onDelete(index) {
            this.flowData.splice(index, 1)

            //获取已经选择的配件ID
            let ids = ''
            this.flowData.forEach((item, index) => {
                ids = ids + item.ciid + ','
            })
            this.ids = ids.substring(0, ids.length - 1)
            // 获取应用系统数据
            this.onChangeUseSys()
        },
        editRows() {
            let useSysData = this.$refs.tempData.selection
            if (useSysData.length == 0) {
                this.$message.error('请选中至少一条数据')
                return false
            }
            for (var i = 0; i < useSysData.length; i++) {
                var ciclassifykey = useSysData[i].ciclassifykey
                if ('C_VM,C_machine'.indexOf(ciclassifykey) < 0) {
                    this.$message.error('仅物理机和虚拟机可以修改')
                    return false
                }
            }
            for (var i = 0; i < useSysData.length; i++) {
                if (this.flowData.indexOf(useSysData[i]) < 0) {
                    useSysData[i].deloradd = '3'
                    this.flowData.push(useSysData[i])
                }
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
        },
        toSumbit() {
            if (this.flowData.length == 0) {
                this.$message.error('待处理资源不能为空')
                return false
            }
            this.sumbitForm.list = this.usesysForm
            this.sumbitForm.subList = this.flowData 
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            })
            reSubmitWorkFlow(this.sumbitForm).then((res) => {
                loading.close()
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let message = common.getFlowMessge(res.data.message)
                let messageArr = message.split(',')
                this.$nextTick(() => {
                    this.messageNotify(messageArr)
                    this.$router.push({
                        path: 'dashboard'
                    })
                })
            })
        },
        messageNotify(messageVal) {
            if (messageVal.length > 3) {
                messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(')')[0] + ')'
            }
            let messageSec = messageVal.slice(2).join('，') 
            this.$alert(messageVal[1] + ' ' + messageSec, { type: 'success' })
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


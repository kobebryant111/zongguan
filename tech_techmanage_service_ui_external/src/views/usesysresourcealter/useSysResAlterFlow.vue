<!-- 资产统计报表 -->
<template>
    <div class="app-container">
        <div class="app-main-container">
            <el-dialog :title="titleName" :visible.sync="flDialog" fullscreen center>
                <el-form :model="usesysForm" ref="usesysForm" inline label-width="120px">
                    <el-input v-model="usesysForm.appsysno" v-if="false"></el-input>
                    <el-input v-model="usesysForm.id" v-if="false"></el-input>
                    <el-input v-model="usesysForm.depid" v-if="false"></el-input>
                    <el-row>
                        <el-col :span="10">
                            <el-form-item label="选择应用系统：" prop="appsysname">
                                <rela-sys-tree ref="relasystree" v-model="usesysForm.appsysname" @change="getChildrenData" size="mini"></rela-sys-tree>
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
                <el-button-group style="margin-bottom:15px">
                    <el-button type="success" size="mini" @click="addRows">添 加</el-button>
                    <el-button type="danger" size="mini" @click="delRows">删 除</el-button>
                    <el-button type="primary" size="mini" @click="editRows">修 改</el-button>
                </el-button-group>
                <div style="margin-bottom:5px">
                    <span style="font-weight:bold">资源列表</span>
                </div>
                <el-table :data="tempData.filter((item)=>{
                        return item.deloradd!=2 && item.deloradd!=3
                    })" ref="tempData" height="300px" border stripe style="width:100%;margin-bottom:15px">
                    <el-table-column type="selection" width="35"></el-table-column>
                    <el-table-column prop="deloradd" label="动作" :show-overflow-tooltip="true" v-if="show"></el-table-column>
                    <el-table-column prop="ciid" label="ciid" v-if="show" :show-overflow-tooltip="true"></el-table-column>
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

                <div style="margin-bottom:5px">
                    <span style="font-weight:bold">待确认的资源</span>
                </div>
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
                            <el-input v-if="(flowData[scope.$index].ciclassifykey =='C_VM' || flowData[scope.$index].ciclassifykey =='C_machine') && flowData[scope.$index].deloradd =='3'" v-model="flowData[scope.$index].ciname"></el-input>
                            <span v-else>{{flowData[scope.$index].ciname}}</span>
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
                <div slot="footer" class="dialog-footer" align="center">
                    <el-button type="primary" @click="button_save" size="mini">保 存</el-button>
                    <el-button type="primary" @click="toSumbit" size="mini">提 交</el-button>
                </div>
            </el-dialog>
            <use-sys-add-flow-data ref="useSysAddFlowData"></use-sys-add-flow-data>
        </div>
    </div>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import common from '@/api/common'
import pager from '@/components/Pager'
import { getDicEntry } from '@/api/eosDictEntry'
import relaSysTree from '@/views/resource/added/relaSysTree'
import repDeptTree from '@/views/resource/resourceReport/repDeptTree'
import useSysAddFlowData from '@/views/usesysresourcealter/useSysAddFlowData'

import { queryUseSysList, getFlowId, saveUseSysData, submitResUseSysData, queryResUseSysAlterData, querySystemUser } from '@/api/usesysresourcealter/usesysresourcealter'
export default {
    components: { cleanBtn, pager, relaSysTree, repDeptTree, queryUseSysList, useSysAddFlowData },
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
                sysuser: '',
                alter: [],
                flowno: '',
                resclassify: ''
            },
            ids: '', //过滤选择的数据
            flDialog: false, //是否打开发起页面
            show: false,
            titleName: '应用系统资源变更流程发起页面', //大标题名字
            classifyData: [], // 资源分类
            logicalStatsData: [], //逻辑设备状态
            physicalStatsData: [], //物理设备状态
            delOrAddData: [
                { dictid: 1, dictname: '新增' },
                { dictid: 2, dictname: '删除' },
                { dictid: 3, dictname: '修改' }
            ], //动作
            tempData: [],
            flowData: [], //
            typeData: [],
            editable: []
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {},
    methods: {
        init(id, flowno) {
            if (id != '' && id != null) {
                this.queryResUseSysAlterData(id), (this.usesysForm.flowno = flowno)
            } else {
                let param = {
                    ...this.usesysForm
                    //...this.page
                }
                let data = common.paramsFormat(param)
                getFlowId(data).then((res) => {
                    if (res.data.status == 200) {
                        this.usesysForm.flowno = res.data.results
                    } else {
                        this.$alert(res.data.message, { type: 'error' })
                        return false
                    }
                })
            }

            this.usesysForm = {
                id: '',
                appsysno: '',
                appsysname: '',
                depid: '',
                sysuser: '',
                alter: [],
                flowno: '',
                resclassify: ''
            }
            this.$nextTick(() => {
                //清空应用系统
                this.$refs.relasystree.clearValue()
                this.$refs.relasystree.packageInit()
            })
            this.typeData = []
            this.tempData = []
            this.flowData = []
            this.flDialog = true
        },
        onChangeUseSys(flag) {
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
                    this.usesysForm.resclassify = ''
                    this.classifyData.forEach((item) => {
                        this.tempData.forEach((res) => {
                            if (res.ciclassifykey == item.dictid && this.typeData.indexOf(item) == -1) {
                                this.typeData.push(item)
                            }
                        })
                    })
                    if (flag != '1') {
                        this.flowData = []
                    }
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
            querySystemUser(data).then((res) => {
                if (res.data.status == 200) {
                    this.usesysForm.sysuser = res.data.results
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
        },
        queryResUseSysAlterData(id) {
            let param = {
                id: id
            }
            let data = common.paramsFormat(param)
            queryResUseSysAlterData(data).then((res) => {
                if (res.data.status == 200) {
                    this.flowData = res.data.results
                    //给应用系统赋值
                    this.$refs.relasystree.clearValue(this.flowData[0].appsysname)
                    this.usesysForm.appsysname = this.flowData[0].appsysname
                    this.usesysForm.appsysno = this.flowData[0].appsysno
                    this.usesysForm.flowno = this.flowData[0].flowno
                    this.usesysForm.depid = this.flowData[0].depid
                    this.usesysForm.id = id
                    //获取已经选择的配件ID
                    let ids = ''
                    this.flowData.forEach((item, index) => {
                        ids = ids + item.ciid + ','
                    })
                    this.ids = ids.substring(0, ids.length - 1)
                    // 获取应用系统数据
                    this.onChangeUseSys(1)
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
        getChildrenData(args, flag) {
            this.$refs.relasystree.packageInit()
            //关联项目树
            this.usesysForm.appsysno = args.ciid //系统ID
            this.usesysForm.appsysname = args.text //系统ID
            this.usesysForm.depid = args.tecDep //系统ID
            this.onChangeUseSys()
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
        addUseSysData(obj) {
            let useSysData = obj
            for (var i = 0; i < useSysData.length; i++) {
                if (this.flowData.indexOf(useSysData[i]) < 0) {
                    useSysData[i].deloradd = '1'
                    this.flowData.push(useSysData[i])
                }
            }
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
        onDelete(index) {
            this.flowData.splice(index, 1)

            //获取已经选择的配件ID
            let ids = ''
            this.flowData.forEach((item, index) => {
                ids = ids + item.ciid + ','
            })
            this.ids = ids.substring(0, ids.length - 1)
            // 获取应用系统数据
            this.onChangeUseSys(1)
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

        button_save() {
            if (this.flowData.length == 0) {
                this.$message.error('待处理资源不能为空')
                return false
            }
            saveUseSysData({ list: this.usesysForm, subList: this.flowData }).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                } else {
                    //操作父页面，提示成功，并刷页面
                    this.$parent.afterSave()
                    this.flDialog = false
                }
            })
        },
        toSumbit() {
            if (this.flowData.length == 0) {
                this.$message.error('待处理资源不能为空')
                return false
            }
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            })
            submitResUseSysData({ list: this.usesysForm, subList: this.flowData }).then((res) => {
                loading.close()
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                if (res.data.results == '9999') {
                    this.$alert(' 下一节点审核人员不能为空！', { type: 'error' })
                    return false
                }
                let message = common.getFlowMessge(res.data.results)
                let messageArr = message.split(',')
                this.$nextTick(() => {
                    this.messageNotify(messageArr)
                    //操作父页面，提示成功，并刷页面
                    this.$parent.afterSave()
                    this.flDialog = false
                })
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
        /**
         * 分页触发
         */
        getPager: function (size, index) {
            this.page.size = size
            this.page.index = index
            this.init()
        } // end
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


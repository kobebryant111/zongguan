<!-- 资源新增，编辑，查看显示页面 -->
<template>
    <div>
        <el-form ref="form" inline label-width="120px">
            <el-row>
                <el-col :span="6">
                    <el-form-item label="应用名称：">
                        <el-input v-model="form.appName" size="mini" disabled clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span=" 6">
                    <el-form-item label="项目名称：">
                        <el-input v-model="form.projectName" size="mini" disabled clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="预计上线时间：">
                        <el-date-picker v-model="form.expectedOnlineTime" size="mini" style="width:82%" value-format="yyyy-MM-dd" disabled></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6">
                    <el-form-item label="补录流程编号：">
                        <el-input v-model="form.reNewProcessNo" size="mini" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="压力测试报告：" prop="docmId" :rules="{required:true,message:'请上传',trigger:'change'}">
                        <div class="upload_scss">
                            <!-- <div @click="openNtkoWindow(form.docmId)">预览</div> -->
                            <div @click="jlDownliad(form.docmId)">下载</div>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row class="row-box">
                <el-col :span="24">
                    <el-form-item v-for=" (dynamicForm, index) in form.dynamicForms" :key="dynamicForm.key">
                        <!-- 动态表格_Begin -->
                        <el-card body-style="background-color:#f0f2f5" class="el-card">
                            <el-card>
                                <el-badge :value="index+1" class="item"></el-badge>
                                <el-form :key="'dynamicForm'+index" :model="dynamicForm" :ref="'dynamicForm'+index" inline label-width="130px">
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="节点类型：" prop="nodeType">
                                                <el-select placeholder="请选择" v-model="dynamicForm.nodeType" size="mini" disabled>
                                                    <el-option v-for="item in nodeTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="选型类型：" prop="modelType">
                                                <el-select ref="modelType" placeholder="请选择" v-model="dynamicForm.modelType" size="mini" disabled>
                                                    <el-option v-for="item in modelTypeData = preSetConfigData.filter((item) => { return item.NODETYPE == dynamicForm.nodeType })" :key="item.DICTID+''" :label="item.DICTNAME" :value="item.DICTID+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="选型配置：" prop="modelConfigName">
                                                <el-input v-model="dynamicForm.modelConfigName" size="mini" disabled clearable>
                                                </el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="节点名称：" prop="nodeName">
                                                <el-input v-model="dynamicForm.nodeName" size="mini" disabled clearable></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="所属中心：" prop="ownCenter">
                                                <el-select placeholder="请选择" v-model="dynamicForm.ownCenter" size="mini" disabled>
                                                    <el-option v-for="item in ownCenterData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="网络区域：" prop="networkAreaName">
                                                <el-input v-model="dynamicForm.networkAreaName" size="mini" disabled clearable></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="操作系统：" prop="os">
                                                <el-select placeholder="请选择" v-model="dynamicForm.os" size="mini" disabled>
                                                    <el-option v-for="item in osData" :key="item.ID" :label="item.SOFTNAME+(item.SOFTVERSION==null?'':''+item.SOFTVERSION)" :value="item.ID"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="节点架构：" prop="nodeArch">
                                                <el-select placeholder="请选择" v-model="dynamicForm.nodeArch" size="mini" disabled>
                                                    <el-option v-for="item in nodeArchData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="数据库容量：" prop="dbSize">
                                                <el-input-number v-model.number="dynamicForm.dbSize" clearable size="mini" type="number" :min=0 style="width:87%" disabled></el-input-number>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="是否挂载NAS：" prop="isNas">
                                                <el-select placeholder="请选择" v-model="dynamicForm.isNas" size="mini" disabled>
                                                    <el-option v-for="item in mountData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="挂载NAS类型：" prop="mountType">
                                                <el-select placeholder="请选择" v-model="dynamicForm.mountType" size="mini" disabled>
                                                    <el-option v-for="item in nasTypeData" :key="item.ID" :label="item.APPLYSCENE" :value="item.ID"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="挂载NAS大小：" prop="nasSize">
                                                <el-input-number v-model.number="dynamicForm.nasSize" clearable size="mini" type="number" :min=0 disabled style="width:87%"></el-input-number>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>

                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="是否挂载SAN：" prop="isStorage">
                                                <el-select placeholder="请选择" v-model="dynamicForm.isStorage" size="mini" disabled>
                                                    <el-option v-for="item in mountData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="挂载SAN大小：" prop="storageSize" size="mini">
                                                <el-input-number v-model.number="dynamicForm.storageSize" size="mini" type="number" :min=0 style="width:87%" disabled></el-input-number>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </el-form>
                            </el-card>
                            <div style="margin-top:10px"></div>
                            <el-card v-if="dynamicForm.modelConfigName!='' && ((dynamicForm.modelType!=3 && dynamicForm.modelType!=4 && dynamicForm.nodeType!=4) || (dynamicForm.nodeType==4 && dynamicForm.modelType==1))">
                                <label>配件信息：</label>
                                <el-table :data="dynamicForm.hardWareData" border stripe highlight-current-row>
                                    <el-table-column prop="resConfigId" label="配件分类" align="center" width='180'>
                                        <!-- <template slot-scope="scope">
                                            <el-select placeholder="请选择" v-model="scope.row.resConfigId" clearable size="mini" disabled>
                                                <el-option v-for="item in resConfigData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                            </el-select>
                                        </template> -->

                                        <template slot-scope="scope">
                                            <span v-for="item of resConfigData = (dynamicForm.modelType==1?resConfigPhyData:resConfigVmData)" :key="item.dictid">
                                                <span v-if="item.dictid == scope.row.resConfigId">{{item.dictname}}</span>
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="describe" label="配置" align="center" width='180'></el-table-column>
                                    <el-table-column prop="applyScene" label="使用场景" align="center" width='180' v-if="dynamicForm.modelType==1"></el-table-column>
                                    <el-table-column prop="price" label="单价" align="center" width='180'></el-table-column>
                                    <el-table-column prop="num" label="数量" align="center" width='180'></el-table-column>
                                    <el-table-column prop="partId" label="配件ID" align="center" width='180' v-if="false"></el-table-column>
                                </el-table>
                            </el-card>
                            <div style="margin-top:10px"></div>
                            <el-card v-if="dynamicForm.modelConfigName!=''">
                                <label>软件版本：</label>
                                <el-table :data="dynamicForm.softWareData" border stripe style="width:100%" highlight-current-row>
                                    <el-table-column prop="type" label="软件类型" align="center" width='180'>
                                        <template slot-scope="scope">
                                            <span v-for="item of softTypeData" :key="item.dictid">
                                                <span v-if="item.dictid == scope.row.type">{{item.dictname}}</span>
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="name" label="软件名称" align="center" width='180'></el-table-column>
                                    <el-table-column prop="version" label="软件版本" align="center" width='180'>

                                    </el-table-column>
                                </el-table>
                            </el-card>
                            <div style="margin-top:10px" align="right">
                                <label>总价：{{
                                    dynamicForm.price
                                }}元</label>
                            </div>
                        </el-card>
                        <!-- 动态表格_End -->
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24" align="right">
                    <el-form-item label="总价：">
                        <el-input v-model="form.amount" size="mini" disabled></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

        <div class="czLog app-container">
            <flow-logs :processInstId="processInstId" ref="log"></flow-logs>
        </div>
        <div style="padding-left:300px;width:70%">
            <el-form ref="sumbitForm" inline label-width="120px" :model="sumbitForm" :rules="sumbitRules">
                <el-row v-if="activityDefID=='manualActivity3'">
                    <el-col :span="12">
                        <el-form-item label="预算编号" prop="budgetNo">
                            <el-input v-model="sumbitForm.budgetNo" size="mini"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="activityDefID=='manualActivity2'">
                    <el-col :span="12">
                        <el-form-item label="技评编号" prop="evaluateNo">
                            <el-input v-model="sumbitForm.evaluateNo" width="100%" size="mini"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="技评地址URL" prop="evaluateUrl">
                            <el-input v-model="sumbitForm.evaluateUrl" width="100%" size="mini"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="activityDefID!='manualActivity4'">
                    <el-col :span="12">
                        <el-form-item label="审批决策" prop="conclusion">
                            <el-select placeholder="" v-model="sumbitForm.conclusion" size="mini">
                                <el-option v-for="item in conclusionData" :key="item.dictid" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="activityDefID!='manualActivity4'">
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
            <el-button v-if="activityDefID!='manualActivity4'" type="primary" @click="button_save" size="mini">审 批</el-button>
            <el-button v-if="activityDefID=='manualActivity4'" type="primary" @click="button_save" size="mini">确 认</el-button>
        </div>
    </div>
</template>
<script>
import { getDicEntry } from '@/api/eosDictEntry'
import { addResource, querySelectionConfig, queryHardWareRela, querySoftWareRela, queryStorageComboData, queryResAddedConfig } from '@/api/resource/added/resourceManager'
import flowLogs from '@/views/plugins/flowPlug/FlowLogs'
import { submitWorkItem } from '@/api/resource/expand/resourceExpand'
import common from '@/api/common'

// 引入ntko
let ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min').ntkoBrowser

export default {
    components: { flowLogs },
    props: {
        busId: {
            type: String
        },
        workItemId: {
            type: Number
        },
        processInstId: {
            type: Number
        },
        applydesc: {
            type: String
        },
        activityDefID: {
            type: String
        },
        sourceType: {
            type: String
        }
    },
    data() {
        return {
            form: {
                id: '',
                appNo: '',
                appName: '',
                projectNo: '',
                projectName: '',
                sysOperLevel: '',
                isFundTrans: '',
                busiDeptName: '',
                techDeptName: '',
                busiDeptId: '',
                techDeptId: '',
                ciid: '',
                sanSpecialId: '',
                sanSpecialPrice: '',
                sanSpecialRatio: '',
                expectedOnlineTime: '',
                reNewProcessNo: '',
                docmId: '',
                dynamicForms: [
                    {
                        modelConfig: '',
                        networkArea: '',
                        comboTotalPrice: 0,
                        cpuNum: 0,
                        cpuCode: 0,
                        osPrice: 0,
                        osCountModel: '',
                        osTotalPrice: 0,
                        nasPrice: 0,
                        hardWareTotalPrice: 0,
                        softWareTotalPrice: 0,
                        nasTotalPrice: 0,
                        dbPrice: 0,
                        dbTotalPrice: 0,
                        sanTotalPrice: 0,
                        sanPrice: 0,
                        sanRatio: 0,
                        sanSpecialId: '',
                        sanSpecialPrice: 0,
                        sanSpecialRatio: 0,
                        sanSpecialSize: 0,

                        nodeType: '',
                        nodeName: '',
                        ownCenter: '',
                        modelType: '',
                        os: '',
                        networkAreaName: '',
                        modelConfigName: '',
                        nodeArch: '',
                        dbSize: 0,
                        isNas: '',
                        mountType: '',
                        nasSize: 0,
                        isStorage: '',
                        storageSize: 0,
                        hardWareData: [], //硬件列表数据
                        softWareData: [], //软件列表数据
                        price: 0
                    }
                ]
            },
            hardWareData: [{ resConfigId: '', applyScene: '', describe: '', price: 0, num: 0 }], //硬件列表数据
            softWareData: [{ type: '', name: '', version: '' }], //软件列表数据
            inputContent: this.input,
            flDialog: false, //是否选择变更人员弹窗
            titleName: '', //大标题名字
            nodeTypeData: [], //节点类型字典数据
            ownCenterData: [], //所属中心字典数据
            osData: [], //操作系统数据
            nodeArchData: [], //节点架构字典数据
            mountData: [], //SAN/NAS是否挂载
            BooleanData: [], //'是否'字典数据
            storageData: [], //存储套餐数据
            nasTypeData: [], //NAS分类数据
            sanSpicalComboData: {}, //配比用的SAN数据
            preSetConfigData: [], //预设的14种搭配
            resConfigData: [], //配件分类数据
            resConfigPhyData: [], //物理机配件数据
            resConfigVmData: [], //虚拟机配件数据
            softWareRelaData: [], //软件版本数据
            hardWareRelaData: [], //配件属性中的关联关系数据
            groupprice: 0,
            softTypeData: [
                { dictid: 1, dictname: '通用' },
                { dictid: 2, dictname: '其它' }
            ],
            sumbitForm: {
                id: '',
                conclusion: '01',
                audit: '',
                workItemId: '',
                processInstId: '',
                applydesc: '',
                sourceType: '',
                activityDefID: '',
                budgetNo: '' //预算编号
            },
            sumbitRules: {
                conclusion: [{ required: true, message: '审批决策必填', trigger: 'blur' }],
                audit: [
                    { required: false, message: '审批意见必填', trigger: 'blur' },
                    { max: 100, message: '审批意见不能超过100个字符', trigger: 'blur' }
                ],
                budgetNo: [
                    { required: true, message: ' ', trigger: 'blur' },
                    { max: 25, message: '预算编号不能超过25个字符', trigger: 'blur' }
                ],
                evaluateNo: [{ max: 25, message: '技评编号不能超过25个字符', trigger: 'blur' }],
                evaluateUrl: [{ max: 100, message: '技评地址URL不能超过100个字符', trigger: 'blur' }]
            },
            conclusionData: [
                { dictid: '01', dictname: '同意' },
                { dictid: '02', dictname: '不同意' }
            ]
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {
        //统一的数据准备方法
        this.getPreSetData()
        this.edit(this.busId)
        // 流程日志
        this.$refs.log.init(this.processInstId)
    },
    methods: {
        //字段数据查询
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_NODETYPE,RES_OWNCENTER,RES_NODEARCH,RES_YESORNO,RES_SUBCATEGORY,RES_VM_TYPE,RES_ISMOUNT'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //节点类型
                this.nodeTypeData = data.filter((item) => {
                    return item.dicttypeid == 'RES_NODETYPE'
                })
                //所属中心
                this.ownCenterData = data.filter((item) => {
                    return item.dicttypeid == 'RES_OWNCENTER'
                })
                //节点架构
                this.nodeArchData = data.filter((item) => {
                    return item.dicttypeid == 'RES_NODEARCH'
                })
                //是否
                this.BooleanData = data.filter((item) => {
                    return item.dicttypeid == 'RES_YESORNO'
                })
                //虚拟机配件数据
                this.resConfigVmData = data.filter((item) => {
                    return item.dicttypeid == 'RES_VM_TYPE'
                })
                //NAS/SAN是否挂载
                this.mountData = data.filter((item) => {
                    return item.dicttypeid == 'RES_ISMOUNT'
                })
                //小类
                let subcategoryData = data.filter((item) => {
                    return item.dicttypeid == 'RES_SUBCATEGORY'
                })
                //------------对小类进行再次筛选------------
                this.resConfigPhyData = subcategoryData.filter((item) => {
                    return item.parentid == '2'
                })
                return data
            })
        },
        //获取初始化的数据
        getPreSetData() {
            //节点类型，选型类型，选型配置的14中搭配方案
            querySelectionConfig().then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.preSetConfigData = res.data.results
            })
            //配件类型的关联关系表
            queryHardWareRela().then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.hardWareRelaData = res.data.results
            })
            //软件类型的关系表(7:操作系统软件 8:数据库软件 9:应用中间件 10:消息中间件)
            querySoftWareRela().then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.softWareRelaData = res.data.results
                this.osData = this.softWareRelaData.filter((item) => {
                    return item.TYPE == '7'
                })
            })
            //查询存储套餐
            queryStorageComboData().then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.storageData = res.data.results
                this.nasTypeData = this.storageData.filter((item) => {
                    return item.SUBCATEGORY == '52'
                })
                //获取特殊的配比SAN套餐
                this.sanSpicalComboData = this.storageData.filter((item) => {
                    return item.SUBCATEGORY == '51' && item.ISSPECIAL == 1
                })
                console.log(this.sanSpicalComboData[0])
            })
        },
        edit(id) {
            this.loadDataEve()
            this.titleName = '资源配置编辑'

            let param = {}
            param.id = id
            let data = common.paramsFormat(param)
            queryResAddedConfig(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                } else {
                    let form = res.data.results
                    this.form = form
                }
            })
            this.flDialog = true
        },
        loadDataEve() {
            this.form = {
                id: '',
                appNo: '',
                appName: '',
                projectNo: '',
                projectName: '',
                sysOperLevel: '',
                isFundTrans: '',
                busiDeptName: '',
                techDeptName: '',
                busiDeptId: '',
                techDeptId: '',
                ciid: '',
                sanSpecialId: '',
                sanSpecialPrice: '',
                sanSpecialRatio: '',
                expectedOnlineTime: '',
                reNewProcessNo: '',
                dynamicForms: [
                    {
                        modelConfig: '',
                        networkArea: '',
                        comboTotalPrice: 0,
                        cpuNum: 0,
                        cpuCode: 0,
                        osPrice: 0,
                        osCountModel: '',
                        osTotalPrice: 0,
                        nasPrice: 0,
                        hardWareTotalPrice: 0,
                        softWareTotalPrice: 0,
                        nasTotalPrice: 0,
                        dbPrice: 0,
                        dbTotalPrice: 0,
                        sanTotalPrice: 0,
                        sanPrice: 0,
                        sanRatio: 0,
                        sanSpecialId: '',
                        sanSpecialPrice: 0,
                        sanSpecialRatio: 0,
                        sanSpecialSize: 0,

                        nodeType: '',
                        nodeName: '',
                        ownCenter: '',
                        modelType: '',
                        os: '',
                        networkAreaName: '',
                        modelConfigName: '',
                        nodeArch: '',
                        dbSize: 0,
                        isNas: '',
                        mountType: '',
                        nasSize: 0,
                        isStorage: '',
                        storageSize: 0,
                        hardWareData: [], //硬件列表数据
                        softWareData: [], //软件列表数据
                        price: 0
                    }
                ]
            }
        },
        button_cancel() {
            this.$router.push({
                path: 'dashboard'
            })
        },
        button_save() {
            this.$refs['sumbitForm'].validate((valid) => {
                if (valid) {
                    //综合部确认，通过的，预算编号不能为空
                    if ('manualActivity3' == this.activityDefID && this.sumbitForm.conclusion == '01' && (undefined == this.sumbitForm.budgetNo || '' == this.sumbitForm.budgetNo)) {
                        this.$alert('预算编号不能为空', { type: 'error' })
                        return false
                    }
                    //提交工作流审批
                    //需要数据1流程实例ID2工作项ID3节点描述4审批结论
                    this.sumbitForm.workItemId = this.workItemId
                    this.sumbitForm.processInstId = this.processInstId
                    this.sumbitForm.applydesc = this.applydesc
                    this.sumbitForm.activityDefID = this.activityDefID
                    this.sumbitForm.sourceType = this.sourceType
                    this.sumbitForm.id = this.busId

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
                } else {
                    return false
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
        // ntko
        openNtkoWindow(objectId) {
            var url = window.location.href
            let ntkoed = ntkoBrowser.ExtensionInstalled()
            if (ntkoed) {
                ntkoBrowser.openWindow(url.substring(0, url.indexOf('#')) + '#/ntko/?id=' + objectId)
            } else {
                window.open(url.substring(0, url.indexOf('#')) + '#/ntko/?id=' + objectId)
            }
            // 注册回调方法
            window.ntkoCloseEvent = function () {
                alert('跨浏览器插件应用程序窗口已关闭!')
            }
        },
        // 文档下载(改)
        jlDownliad(docmId) {
            console.log(docmId)
            const self = this
            common.downloadDoc(docmId, self)
        }
    }
}
</script>
<style scoped>
/* -------文件上传部分样式------- */
.upload_scss div {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
    cursor: pointer;
}
/* 重传 */
.againUpLoad {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
}
/* ----------------------------- */
</style>
<style>
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}
</style>

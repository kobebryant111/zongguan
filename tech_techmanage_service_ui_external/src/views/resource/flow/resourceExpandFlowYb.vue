<template>
    <div>
        <div align="right">
            <el-button @click="button_cancel" size="mini">关 闭</el-button>
        </div>
        <div align="center">
            <H2>资源扩容审批(当前节点:{{applydesc}})</H2>
        </div>

        <el-form ref="form" inline label-width="120px" :model="expandForm" :rules="expandRules">
            <el-row>
                <el-col :span="6">
                    <el-form-item label="价格申请单号：" prop="priceReqNo">
                        <el-input v-model="expandForm.priceReqNo" size="mini" readonly style="width: 100%"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="应用名称：" prop="appNo">
                        <el-input v-model="expandForm.appName" size="mini" readonly style="width: 100%"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="项目名称：" prop="projectName">
                        <el-input v-model="expandForm.projectName" size="mini" readonly style="width: 100%"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="预计上线时间:" prop="expectedOnlineTime">
                        <el-date-picker v-model="expandForm.expectedOnlineTime" readonly value-format="yyyy-MM-dd" size="mini"></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6">
                    <el-form-item label="压力测试报告：" prop="docmId" :rules="{required:true,message:'请上传',trigger:'change'}">
                        <div class="upload_scss">
                            <!-- <div @click="openNtkoWindow(form.docmId)">预览</div> -->
                            <div @click="jlDownliad(expandForm.docmId)">下载</div>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <div v-for=" (dynamicForm, index) in expandForm.resExpandItemList" :key="'dynamicForm'+index">
                <el-card body-style="background-color:#f0f2f5" style="margin-top:8px;">
                    <el-form :key="'dynamicForm'+index" :ref="'dynamicForm'+index" :model="dynamicForm" :rules="expandRules">
                        <el-row>
                            <el-col :span="5">
                                <el-form-item label="IP" prop="ip">
                                    <el-input v-model="dynamicForm.ip" size="mini" readonly style="width: 100%"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="节点名称：" prop="ciName">
                                    <el-input v-model="dynamicForm.ciName" style="width:100%" size="mini" readonly></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="所属中心：" prop="ownCenter">
                                    <el-input v-model="dynamicForm.ownCenter" size="mini" readonly style="width: 100%"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="资源类型" prop="resType">
                                    <el-input v-model="dynamicForm.resType" size="mini" readonly style="width: 100%"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>

                    <div style="margin-top:10px"></div>
                    <el-card>
                        <el-table :data="dynamicForm.resExpandItemPartsList" border stripe style="width:100%" highlight-current-row>

                            <el-table-column prop="category" label="套餐大类" align="center" width='180'>
                                <template slot-scope="scope">
                                    <span v-for="(item, index) of categoryData" :key="index">
                                        <span v-if="item.dictid == scope.row.category">{{ item.dictname}}</span>
                                    </span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="subCategory" label="套餐小项" align="center" width='180'>
                                <template slot-scope="scope">
                                    <span v-for="(item, index) of subCategoryData" :key="index" v-if="scope.row.category!='4'">
                                        <span v-if="item.dictid == scope.row.subCategory">{{ item.dictname}}</span>
                                    </span>
                                    <span v-for="(item, index) of vmTypeData" :key="index" v-if="scope.row.category=='4'">
                                        <span v-if="item.dictid == scope.row.subCategory">{{ item.dictname}}</span>
                                    </span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="describe" label="资源配置" align="center" width='180'>
                                <template slot-scope="scope">
                                    <span v-if="scope.row.subCategory=='51'  &&'1'!=scope.row.isSpecial">
                                        {{ scope.row.describe}}<span style='color:red'>(归档数据备份存储按{{ scope.row.ratio}}:{{ scope.row.sanRelateRatio}}配置)</span>
                                    </span>
                                    <span v-else-if="scope.row.category=='7'||scope.row.category=='8' ||scope.row.category=='9' ||scope.row.category=='10' ">
                                        <span v-if="dynamicForm.resType=='物理机'">
                                            {{ scope.row.describe}}<span style='color:red'>({{dynamicForm.actCpuNum}}*{{dynamicForm.actCpuCore}}C)</span>
                                        </span>
                                        <span v-else>
                                            {{ scope.row.describe}}<span style='color:red'>({{dynamicForm.actCpuCore}}C)</span>
                                        </span>

                                    </span>
                                    <span v-else>
                                        {{ scope.row.describe}}
                                    </span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="num" label="数量" align="center" width='180'>
                            </el-table-column>
                            <el-table-column prop="price" label="单价(元)" align="center" width='180'>
                            </el-table-column>
                        </el-table>
                        <div align="right">
                            <label>总价：{{dynamicForm.price}}元</label>
                        </div>
                    </el-card>
                </el-card>
            </div>
            <div align="right">
                <label>总价：{{expandForm.amount}}元</label>
            </div>
        </el-form>
        <div class="czLog app-container">
            <flow-logs :processInstId="processInstId" ref="log"></flow-logs>
        </div>
        <div class="nav-bottom" v-show="flowItem.status == 'yb'">
            <yb-revoke :flowItem="flowItem"></yb-revoke>
        </div>
        <!-- <div style="padding-left:300px;width:70%">
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
        <userSelect ref="userSelect"></userSelect> -->
    </div>
</template>
<script>
import common from '@/api/common'
import { getDicEntry } from '@/api/eosDictEntry'
import relaSysTree from '@/views/resource/added/relaSysTree'
import relaProgramTree from '@/views/resource/added/relaProgramTree'
import selectConfigItem from '@/views/resource/expand/selectConfigItem'
import userSelect from '@/views/resource/flow/userSelect'
import { submitWorkItem, queryResourceExpand } from '@/api/resource/expand/resourceExpand'
import flowLogs from '@/views/plugins/flowPlug/FlowLogs'
import ybRevoke from '@/views/plugins/flowPlug/ybRevoke'
import { Alert } from 'element-ui'
export default {
    components: { relaSysTree, relaProgramTree, selectConfigItem, userSelect, flowLogs, ybRevoke },
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
            flowItem: {},
            expandForm: {
                id: '',
                priceReqNo: '',
                appNo: '',
                appName: '',
                busiDeptName: '',
                busiDeptNo: '',
                techDeptName: '',
                techDeptNo: '',
                sysOperLevel: '',
                isFundTrans: '',
                projectNo: '',
                projectName: '',
                renewProcessNo: '',
                expectedOnlineTime: '',
                amount: '',
                status: '0',
                docmId: '',
                resExpandItemList: [
                    {
                        price: '0',
                        resExpandItemPartsList: []
                    }
                ]
            },
            conclusionData: [
                { dictid: '01', dictname: '同意' },
                { dictid: '02', dictname: '不同意' }
            ],
            resExpandItemPartsList: [{ category: '1' }],
            expandRules: {
                projectName: [{ required: true, message: '项目名称必填', trigger: 'blur' }],
                appNo: [{ required: true, message: '应用名称必填', trigger: 'blur' }],
                expectedOnlineTime: [{ required: true, message: '预计上线时间必填', trigger: 'blur' }],
                ip: [{ required: true, message: 'IP必填', trigger: 'blur' }]
            },
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
                budgetNo: [{ max: 25, message: '预算编号不能超过25个字符', trigger: 'blur' }],
                evaluateNo: [{ max: 25, message: '技评编号不能超过25个字符', trigger: 'blur' }],
                evaluateUrl: [{ max: 100, message: '技评地址URL不能超过100个字符', trigger: 'blur' }]
            },
            tempData: [],
            inputContent: this.input,
            chooseData: [],
            flDialog: false, //是否选择变更人员弹窗
            viewFlag: '0', //查看标识
            titleName: '', //大标题名字
            showDel: false,
            ipData: [{ dictid: '127.0.0.1', dictname: '127.0.0.1' }], //IP地址
            ownCenterData: [], //中心数据
            resTypeData: [], //资源类型数据
            BooleanData: [], //'是否'字典数据
            categoryData: [], //大类
            subCategoryData: [], //小类,
            vmTypeData: [] // 配件分类
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {
        this.initEdit(this.busId, '1')
        this.$refs.log.init(this.processInstId)
    },
    methods: {
        // 初始化
        init(item) {
            this.flowItem = item
            // 流程日志
            this.$refs.log.init(this.processInstId)
        },
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_OWNCENTER,RES_YESORNO,RES_RESTYPE,RES_CATEGORY,RES_SUBCATEGORY,RES_VM_TYPE'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //所属中心
                this.ownCenterData = data.filter((item) => {
                    return item.dicttypeid == 'RES_OWNCENTER'
                })
                //资源类型
                this.resTypeData = data.filter((item) => {
                    return item.dicttypeid == 'RES_RESTYPE'
                })
                //是否
                this.BooleanData = data.filter((item) => {
                    return item.dicttypeid == 'RES_YESORNO'
                })
                //大类
                this.categoryData = data.filter((item) => {
                    return item.dicttypeid == 'RES_CATEGORY'
                })
                //小项
                this.subCategoryData = data.filter((item) => {
                    return item.dicttypeid == 'RES_SUBCATEGORY'
                })
                //分类
                this.vmTypeData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_VM_TYPE'
                })
                return data
            })
        },
        /**
         * 显示用户树
         */
        showUserTree(index) {
            this.$refs.userSelect.showUserTree(1)
        }, // end
        initEdit(id, viewFlag) {
            //清空校验信息
            this.titleName = '资源扩容编辑'
            this.flDialog = true
            //查看标识
            this.viewFlag = viewFlag
            this.$nextTick(() => {
                //初始化数据
                let data = common.paramsFormat({ id: id })
                queryResourceExpand(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$alert(res.data.message, { type: 'error' })
                        return false
                    }
                    this.expandForm = res.data.results
                })
            })
        },
        button_cancel() {
            this.$router.push({
                path: 'dashboard'
            })
        },
        saveAuditUser(userNo, userName) {
            this.sumbitForm.auditUserId = userNo
            this.sumbitForm.auditUser = userName
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
                            this.$alert(res.data.message, { type: 'success' })
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
        // 文档下载(改)
        jlDownliad(docmId) {
            console.log(docmId)
            const self = this
            common.downloadDoc(docmId, self)
        }
    }
}
</script>

<style>
.el-form-item {
    width: 100%;
}

.areaCss .el-form-item__content {
    width: 80%;
}
.el-dialog__wrapper {
    overflow: hidden !important;
}
.el-dialog.is-fullscreen {
    overflow: hidden;
}
.el-dialog__body {
    overflow-y: auto !important;
    max-height: 88%;
}
.nav-bottom {
    width: 100%;
    background-color: #ffffff;
    position: fixed;
    bottom: 0;
}
</style>
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


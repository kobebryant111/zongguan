    <template>
    <div>
        <div align="center">
            <H2>资源扩容审批(当前节点:{{applydesc}})</H2>
        </div>
        <el-form ref="form" inline label-width="120px" :model="expandForm" :rules="expandRules">
            <el-row>
                <el-col :span="8">
                    <el-form-item label="应用名称：" prop="appNo">
                        <el-input v-model="expandForm.appNo" size="mini" readonly style="width: 100%" v-if="false"></el-input>
                        <el-input v-model="expandForm.appName" size="mini" readonly style="width: 100%"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="项目名称：" prop="projectName">
                        <rela-program-tree ref="relaprogramtree" size="mini" v-model="expandForm.projectName" @change="getChildrenData"></rela-program-tree>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="预计上线时间:" prop="expectedOnlineTime">
                        <el-date-picker v-model="expandForm.expectedOnlineTime" value-format="yyyy-MM-dd" size="mini"></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="压力测试报告：" prop="docmId" :rules="{required:true,message:'请上传',trigger:'change'}">
                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList" :show-file-list="false" :http-request="uploadFile" :on-change="changeFile" v-if="expandForm.docmId=='' || expandForm.docmId==undefined">
                            <el-button size="mini" type="primary">
                                <i class="el-icon-upload2"></i>上传文件
                            </el-button>
                        </el-upload>
                        <div class="upload_scss" v-else>
                            <div @click="jlDownliad(expandForm.docmId)">下载</div>
                            <el-upload class="upload-demo againUpLoad" action="Fake Action" :file-list="fileList" :show-file-list="false" :http-request="uploadFile" :on-change="changeFile">
                                重传
                            </el-upload>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <div v-for=" (dynamicForm, index) in expandForm.resExpandItemList" :key="'dynamicForm'+index">
                <el-button icon="el-icon-circle-plus" v-if="viewFlag=='0'" type="primary" size="mini" @click="addGroup()" circle></el-button>
                <el-button icon="el-icon-remove" type="danger" size="mini" v-if="viewFlag=='0' && expandForm.resExpandItemList.length>1" @click="delGroup(index)" circle></el-button>
                <el-card body-style="background-color:#f0f2f5" style="margin-top:8px;">
                    <el-form :key="'dynamicForm'+index" :ref="'dynamicForm'+index" :model="dynamicForm" :rules="expandRules">
                        <el-row>
                            <el-col :span="5">
                                <el-form-item label="IP" prop="ip">
                                    <el-select placeholder="请选择" v-model="dynamicForm.ip" style="width:100%" @change="getIPChange(dynamicForm.ip,index)" size="mini">
                                        <el-option v-for="(item,index) in ipData" :key="index+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="节点名称：" prop="ciName">
                                    <el-input v-model="dynamicForm.ciName" style="width:100%" size="mini" readonly></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="5">
                                <el-form-item label="所属中心：" prop="ownCenter">
                                    <el-input v-model="dynamicForm.ownCenter" style="width:100%" size="mini" readonly></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="资源类型" prop="resType">
                                    <el-input v-model="dynamicForm.resType" style="width:100%" size="mini" readonly></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-button icon="el-icon-plus" v-if="viewFlag=='0'" type="success" size="mini" @click="sourceSelect(dynamicForm.resType,index)">添加</el-button>
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
                            <el-table-column prop="num" label="数量(件)/存储容量(GB)" align="center" width='180'>
                                <template slot-scope="scope">
                                    <el-input-number v-model="scope.row.num" @change="handleChange(scope,index)" :min="1" size="mini"></el-input-number>
                                </template>
                            </el-table-column>
                            <el-table-column prop="price" label="单价(元)" align="center" width='180'>
                            </el-table-column>
                            <el-table-column type="index" label="操作" align="center" width="120">
                                <template slot-scope="scope">
                                    <el-button type="danger" v-if="viewFlag=='0'" icon="el-icon-remove" size="mini" @click="delRow(scope,index)">刪除</el-button>
                                </template>
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
        <div slot="footer" class="dialog-footer" align="center">
            <el-button @click="button_cancel" size="mini">取 消</el-button>
            <el-button v-if="viewFlag=='0'" type="primary" @click="toSumbit" size="mini">重新提交</el-button>
        </div>
        <select-config-item ref="selectConfigItem"></select-config-item>
        <userSelect ref="userSelect"></userSelect>
    </div>
</template>
    <script>
import common from '@/api/common'
import resCommon from '@/api/resource/added/common'
import { getDicEntry } from '@/api/eosDictEntry'
import { uploadExcel } from '@/api/integrated/wbms'
import relaSysTree from '@/views/resource/added/relaSysTree'
import relaProgramTree from '@/views/resource/added/relaProgramTree'
import selectConfigItem from '@/views/resource/expand/selectConfigItem'
import { querySanInfo, insertResourceExpand, submitResourceExpand, queryResourceExpand, queryCmdbResourceList, queryCmdbRelateResourceList, reSubmitWorkFlow, validOutofDateData } from '@/api/resource/expand/resourceExpand'
import { Alert } from 'element-ui'
import userSelect from '@/views/resource/flow/userSelect'
export default {
    components: { relaSysTree, relaProgramTree, selectConfigItem, userSelect },
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
                        ciName: '',
                        ciId: '',
                        resExpandItemPartsList: []
                    }
                ]
            },
            resExpandItemPartsList: [{ category: '1' }],
            expandRules: {
                appNo: [{ required: true, message: '应用名称必填', trigger: 'blur' }],
                expectedOnlineTime: [{ required: true, message: '预计上线时间必填', trigger: 'blur' }],
                ip: [{ required: true, message: 'IP必填', trigger: 'blur' }]
            },
            tempData: [],
            inputContent: this.input,
            chooseData: [],
            flDialog: false, //是否选择变更人员弹窗
            viewFlag: '0', //查看标识
            titleName: '', //大标题名字
            showDel: false,
            ipData: [], //IP地址
            resourceData: [], //根据应用获取的资源数组
            ownCenterData: [], //中心数据
            resTypeData: [], //资源类型数据
            BooleanData: [], //'是否'字典数据
            categoryData: [], //大类
            subCategoryData: [], //小类,
            vmTypeData: [], // 配件分类
            fileList: [] //文件列表
        }
    },
    mounted() {
        this.getDictData()
        this.initEdit(this.busId, '0')
    },
    methods: {
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
        },
        saveAuditUser(userNo, userName) {
            this.expandForm.auditUserId = userNo
            this.auditUser = userName
        },
        clickEve() {
            this.fns()
        },
        add() {
            //清空历史数据
            this.expandForm = {
                id: '',
                priceReqNo: '',
                appNo: '',
                appName: '',
                busiDeptName: '',
                busiDeptNo: '',
                techDeptName: '',
                techDeptNo: '',
                ciid: '',
                sysOperLevel: '',
                isFundTrans: '',
                projectNo: '',
                projectName: '',
                renewProcessNo: '',
                expectedOnlineTime: '',
                amount: '0',
                status: '',
                docmId: '',
                resExpandItemList: [
                    {
                        price: '0',
                        ciName: '',
                        ciId: '',
                        resExpandItemPartsList: []
                    }
                ]
            }

            this.$nextTick(() => {
                //清楚校验信息
                this.$refs.form.clearValidate()
                //清空应用系统
                //this.$refs.relasystree.clearValue()
                //清空项目
                this.$refs.relaprogramtree.clearValue()

                this.viewFlag = '0'
            })

            //清空校验信息
            this.titleName = '资源扩容新增'
            this.flDialog = true
        },
        initEdit(id, viewFlag) {
            //清空校验信息
            this.titleName = '资源扩容编辑'
            this.flDialog = true
            //查看标识
            this.viewFlag = viewFlag
            this.$nextTick(() => {
                //清楚校验信息
                this.$refs.form.clearValidate()
                //清空应用系统
                //this.$refs.relasystree.clearValue()
                //清空项目
                this.$refs.relaprogramtree.clearValue()
                //初始化数据
                let data = common.paramsFormat({ id: id })
                queryResourceExpand(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$alert(res.data.message, { type: 'error' })
                        return false
                    }
                    this.expandForm = res.data.results
                    //给应用系统赋值
                    //this.$refs.relasystree.clearValue(res.data.results.appName)
                    //给项目赋值
                    this.$refs.relaprogramtree.clearValue(res.data.results.projectName)
                    //根据用印熊值，初始化IP
                    //获取IP列表
                    let data = common.paramsFormat({ ciid: this.expandForm.ciid })
                    queryCmdbResourceList(data).then((res) => {
                        if (res.data.status != 200) {
                            return false
                        }
                        this.resourceData = res.data.results
                        let resourceDataAttr = this.resourceData
                        this.ipData = []
                        for (var i = 0; i < resourceDataAttr.length; i++) {
                            let tmp = resourceDataAttr[i]
                            this.ipData.push({ dictid: tmp.ip, dictname: tmp.ip })
                        }
                    })
                })
            })
        },
        addGroup() {
            this.expandForm.resExpandItemList.push({
                price: '0',
                resExpandItemPartsList: []
            })
        },
        delGroup(index) {
            this.expandForm.resExpandItemList.splice(index, 1)
            //计算全部总价
            var totalPrice = 0
            this.expandForm.resExpandItemList.forEach((item, index) => {
                totalPrice = totalPrice + parseFloat(item.price)
            })
            this.expandForm.amount = totalPrice
        },
        button_cancel() {
            this.$router.push({
                path: 'dashboard'
            })
        },
        button_save() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    let _self = this
                    let newArr = []
                    this.expandForm.resExpandItemList.forEach((item, index) => {
                        checkForm(index)
                    })
                    function checkForm(index) {
                        var par = 'dynamicForm' + index
                        var result = new Promise(function (resolve, reject) {
                            _self.$refs[par][0].validate((valid) => {
                                if (valid) {
                                    resolve()
                                }
                            })
                        })
                        newArr.push(result)
                    }
                    Promise.all(newArr).then(function () {
                        //校验数量不能为空
                        //this.expandForm.resExpandItemList.forEach((item,index)=>{
                        //    item.resExpandItemPartsList.forEach((it,inx)=>{
                        //
                        //    });
                        //});
                        insertResourceExpand(_self.expandForm).then((res) => {
                            if (res.data.status != 200) {
                                _self.$alert(res.data.message, { type: 'error' })
                                return false
                            }
                            //操作父页面，提示成功，并刷页面
                            _self.$parent.afterSave()
                            _self.flDialog = false
                        })
                    })
                } else {
                    return false
                }
            })
        },
        //发布
        toSumbit() {
            validOutofDateData(this.expandForm).then((res) => {
                if (res.data.status !== 200) {
                    this.$confirm('您的配置中存在已经作废的数据?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '下载错误文本',
                        type: 'warning'
                    })
                        .then(() => {})
                        .catch(() => {
                            this.errorText(res.data.message)
                        })
                    return
                } else {
                    this.$refs['form'].validate((valid) => {
                        if (valid) {
                            let _self = this
                            let newArr = []
                            this.expandForm.resExpandItemList.forEach((item, index) => {
                                checkForm(index)
                            })
                            function checkForm(index) {
                                var par = 'dynamicForm' + index
                                var result = new Promise(function (resolve, reject) {
                                    _self.$refs[par][0].validate((valid) => {
                                        if (valid) {
                                            resolve()
                                        }
                                    })
                                })
                                newArr.push(result)
                            }
                            Promise.all(newArr).then(function () {
                                //添加工作流信息
                                _self.expandForm.workItemId = _self.workItemId
                                _self.expandForm.processInstId = _self.processInstId
                                _self.expandForm.applydesc = _self.applydesc
                                _self.expandForm.activityDefID = _self.activityDefID
                                _self.expandForm.sourceType = _self.sourceType
                                //审批结论默认通过
                                _self.expandForm.conclusion = '01'
                                //审批意见默认为空
                                _self.expandForm.audit = ''
                                const loading = _self.$loading({
                                    lock: true,
                                    text: 'Loading',
                                    spinner: 'el-icon-loading',
                                    background: 'rgba(0, 0, 0, 0.7)'
                                })
                                reSubmitWorkFlow(_self.expandForm).then((res) => {
                                    loading.close()
                                    if (res.data.status != 200) {
                                        _self.$alert(res.data.message, { type: 'error' })
                                        return false
                                    }
                                    let message = common.getFlowMessge(res.data.message)
                                    let messageArr = message.split(',')
                                    _self.$nextTick(() => {
                                        _self.messageNotify(messageArr)
                                        _self.$router.push({
                                            path: 'dashboard'
                                        })
                                    })
                                })
                            })
                        } else {
                            return false
                        }
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
        getChildrenData(args, flag) {
            console.log(args, flag)
            if (flag == 'sysTree') {
                //判断如果应用系统不变，则返回
                if (this.expandForm.appNo == args.id) {
                    return
                }
                //关联系统树
                this.expandForm.appNo = args.id //系统ID
                this.expandForm.appName = args.text //系统名称
                this.expandForm.sysOperLevel = args.omLevel //系统运维等级
                this.expandForm.isFundTrans = args.refMoney //是否涉资
                this.expandForm.busiDeptName = args.busDepName //s所属业务部门名称
                this.expandForm.techDeptName = args.tecDepName //所属技术部门名称
                this.expandForm.busiDeptNo = args.busDep //所属业务部ID
                this.expandForm.techDeptNo = args.tecDep //所属技术部ID
                this.expandForm.ciid = args.ciid //CMDB系统唯一识别编号
                //清空历史数据
                this.expandForm.resExpandItemList = [
                    {
                        price: '0',
                        resExpandItemPartsList: []
                    }
                ]
                this.expandForm.amount = 0
                //获取IP列表
                let data = common.paramsFormat({ ciid: args.ciid })
                queryCmdbResourceList(data).then((res) => {
                    if (res.data.status != 200) {
                        return false
                    }
                    this.resourceData = res.data.results
                    let resourceDataAttr = this.resourceData
                    this.ipData = []
                    for (var i = 0; i < resourceDataAttr.length; i++) {
                        let tmp = resourceDataAttr[i]
                        this.ipData.push({ dictid: tmp.ip, dictname: tmp.ip })
                    }
                })
            } else if (flag == 'proTree') {
                //关联项目树
                this.expandForm.projectNo = args.id //项目ID
                this.expandForm.projectName = args.text //项目名称
            }
        },
        sourceSelect(selectType, index) {
            this.$nextTick(() => {
                if (undefined == selectType || '' == selectType) {
                    this.$alert('资源类型为空，无法配套资源', { type: 'error' })
                    return
                }
                if ('虚拟机' == selectType) {
                    selectType = '2'
                } else if ('物理机' == selectType) {
                    selectType = '1'
                } else {
                    selectType = '3' //数据库实例
                }
                let isFundTransStr = this.expandForm.isFundTrans
                //获取已经选择的配件ID
                let ids = ''
                this.expandForm.resExpandItemList[index].resExpandItemPartsList.forEach((item, index) => {
                    ids = ids + item.resConfigId + ','
                })
                ids = ids.substring(0, ids.length - 1)
                this.$refs.selectConfigItem.toSelect(selectType, index, this.expandForm.sysOperLevel, isFundTransStr, ids)
            })
        },
        //添加组件
        async initAddItemParts(index, obj) {
            //SAN套餐
            if ('5' == obj.CATEGORY && '51' == obj.SUBCATEGORY && '1' != obj.ISSPECIAL) {
                //查询计算SAN的总价
                let data = common.paramsFormat({ id: obj.ID, space: '1' })
                const res = await querySanInfo(data).then((res) => {
                    if (res.data.status != 200) {
                        return false
                    }
                    return res
                })
                //设置数据

                this.expandForm.resExpandItemList[index].resExpandItemPartsList.push({
                    category: obj.CATEGORY,
                    subCategory: obj.SUBCATEGORY,
                    describe: obj.DESCRIBE,
                    price: obj.PRICE,
                    resConfigId: obj.ID,
                    num: '1',
                    billingMode: obj.BILLINGMODE, //软件DB的计费方式
                    isSpecial: obj.ISSPECIAL,
                    memPrice: obj.MEMPRICE,
                    ratio: obj.RATIO,
                    sanRelateId: res.data.results.SANRELATEID,
                    sanRelateNum: res.data.results.SANRELATENUM,
                    sanRelatePrice: res.data.results.SANRELATEPRICE,
                    sanRelateRatio: res.data.results.SANRELATERATIO
                })
            } else {
                this.expandForm.resExpandItemList[index].resExpandItemPartsList.push({
                    category: obj.CATEGORY,
                    subCategory: obj.SUBCATEGORY,
                    describe: '52' == obj.SUBCATEGORY ? obj.APPLYSCENE : obj.DESCRIBE,
                    price: obj.PRICE,
                    resConfigId: obj.ID,
                    num: '1',
                    billingMode: obj.BILLINGMODE, //软件DB的计费方式
                    isSpecial: obj.ISSPECIAL,
                    memPrice: obj.MEMPRICE
                })
            }

            //重新计算当前组件下的总价
            var currentTotal = 0
            var actCpuNum = this.expandForm.resExpandItemList[index].actCpuNum
            var actCpuCore = this.expandForm.resExpandItemList[index].actCpuCore
            //如果是新增的虚拟机CPU 则更新总CPU数据
            //软件CPU配置，重新设置实际CPU数量，用于重新计算软件价格
            if ('4' == obj.CATEGORY && '1' == obj.SUBCATEGORY) {
                this.expandForm.resExpandItemList[index].actCpuNum = resCommon.numAdd(Number(actCpuNum), 1)
                this.expandForm.resExpandItemList[index].actCpuCore = resCommon.numAdd(Number(actCpuCore), 1)
            }
            actCpuNum = this.expandForm.resExpandItemList[index].actCpuNum
            actCpuCore = this.expandForm.resExpandItemList[index].actCpuCore
            this.expandForm.resExpandItemList[index].resExpandItemPartsList.forEach((item, index) => {
                var num = item.num
                var price = item.price

                if ('5' == item.category && '51' == item.subCategory && '1' != item.isSpecial) {
                    //存储套餐-SAN存储-非归档
                    var sanRelateNum = item.sanRelateNum
                    var sanRelatePrice = item.sanRelatePrice
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.numAdd(resCommon.accMul(resCommon.accDiv(num, 1000), price), resCommon.accMul(resCommon.accDiv(sanRelateNum, 1000), sanRelatePrice)))
                } else if ('5' == item.category && '51' == item.subCategory && '1' == item.isSpecial) {
                    //存储套餐-SAN存储-归档
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), price))
                } else if ('5' == item.category && '52' == item.subCategory) {
                    //存储套餐-NAS存储
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), price))
                } else if ('2' == item.category || '4' == item.category) {
                    //物理机配件,虚拟机报价
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(num, price))
                } else if ('7' == item.category || '8' == item.category || '9' == item.category || '10' == item.category) {
                    //软件
                    if (item.billingMode == '1') {
                        //cpu数量

                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accMul(Number(actCpuNum), price), num))
                    } else if (item.billingMode == '2') {
                        //cpu核数
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accMul(Number(actCpuCore), price), num))
                    } else {
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(num, price))
                    }
                } else if ('6' == item.category && '61' == item.subCategory) {
                    //DB云 租户价格+容量/1000*容量单价
                    //20211029 k4886 paas套餐不在统计租户的价格
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), item.memPrice))
                }
            })
            this.expandForm.resExpandItemList[index].price = currentTotal
            //计算全部总价
            var totalPrice = 0
            this.expandForm.resExpandItemList.forEach((item, index) => {
                totalPrice = resCommon.numAdd(totalPrice, parseFloat(item.price))
            })
            this.expandForm.amount = totalPrice
        },
        delRow(scope, index) {
            this.expandForm.resExpandItemList[index].resExpandItemPartsList.splice(scope.$index, 1)
            //重新计算当前组件下的总价
            var currentTotal = 0
            var actCpuNum = this.expandForm.resExpandItemList[index].actCpuNum
            var actCpuCore = this.expandForm.resExpandItemList[index].actCpuCore
            //删除的是虚拟机CPU，重新计算CPU总数
            if ('4' == scope.row.category && '1' == scope.row.subCategory) {
                this.expandForm.resExpandItemList[index].actCpuNum = resCommon.numSub(Number(actCpuNum), scope.row.num)
                this.expandForm.resExpandItemList[index].actCpuCore = resCommon.numSub(Number(actCpuCore), scope.row.num)
            }
            actCpuNum = this.expandForm.resExpandItemList[index].actCpuNum
            actCpuCore = this.expandForm.resExpandItemList[index].actCpuCore
            this.expandForm.resExpandItemList[index].resExpandItemPartsList.forEach((item, index) => {
                var num = item.num
                var price = item.price

                if ('5' == item.category && '51' == item.subCategory && '1' != item.isSpecial) {
                    //存储套餐-SAN存储-非归档
                    var sanRelateNum = item.sanRelateNum
                    var sanRelatePrice = item.sanRelatePrice
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.numAdd(resCommon.accMul(resCommon.accDiv(num, 1000), price), resCommon.accMul(resCommon.accDiv(sanRelateNum, 1000), sanRelatePrice)))
                } else if ('5' == item.category && '51' == item.subCategory && '1' == item.isSpecial) {
                    //存储套餐-SAN存储-归档
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), price))
                } else if ('5' == item.category && '52' == item.subCategory) {
                    //存储套餐-NAS存储
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), price))
                } else if ('2' == item.category || '4' == item.category) {
                    //物理机配件,虚拟机报价
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(num, price))
                } else if ('7' == item.category || '8' == item.category || '9' == item.category || '10' == item.category) {
                    //软件
                    if (item.billingMode == '1') {
                        //cpu数量
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accMul(Number(actCpuNum), price), num))
                    } else if (item.billingMode == '2') {
                        //cpu核数
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accMul(Number(actCpuCore), price), num))
                    } else {
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(num, price))
                    }
                } else if ('6' == item.category && '61' == item.subCategory) {
                    //DB云 租户价格+容量/1000*容量单价
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), item.memPrice))
                }
            })
            this.expandForm.resExpandItemList[index].price = currentTotal
            //计算全部总价
            var totalPrice = 0
            this.expandForm.resExpandItemList.forEach((item, index) => {
                totalPrice = resCommon.numAdd(totalPrice, parseFloat(item.price))
            })
            this.expandForm.amount = totalPrice
        },
        async getIPChange(ip, index) {
            let resourceDataAttr = this.resourceData

            for (var i = 0; i < resourceDataAttr.length; i++) {
                let tmp = resourceDataAttr[i]
                if (ip == tmp.ip) {
                    //清空列表数据
                    this.expandForm.resExpandItemList[index].resExpandItemPartsList = []
                    //节点CIID
                    this.expandForm.resExpandItemList[index].ciId = tmp.ciId
                    //设置节点名称
                    this.expandForm.resExpandItemList[index].ciName = tmp.ciName
                    //设置CPU核数
                    this.expandForm.resExpandItemList[index].cpuCore = tmp.cpuCore
                    //设置实际CPU核数（用来存储增加CPU后的实际总核数）
                    this.expandForm.resExpandItemList[index].actCpuCore = tmp.cpuCore
                    //设置CPU个数
                    if ('虚拟机' == tmp.resType) {
                        //虚拟机，数量用核数设置
                        this.expandForm.resExpandItemList[index].cpuNum = tmp.cpuCore
                        this.expandForm.resExpandItemList[index].actCpuNum = tmp.cpuCore
                    } else {
                        this.expandForm.resExpandItemList[index].cpuNum = tmp.cpuNum
                        this.expandForm.resExpandItemList[index].actCpuNum = tmp.cpuNum
                    }
                    this.expandForm.resExpandItemList[index].resType = tmp.resType
                    this.expandForm.resExpandItemList[index].ownCenter = tmp.ownCenter
                    this.expandForm.resExpandItemList[index].price = 0

                    //如果是虚拟机，则获取对应物理机所属中心
                    if ('虚拟机' == tmp.resType) {
                        let data = common.paramsFormat({ ciid: tmp.ciId })
                        const res = await queryCmdbRelateResourceList(data).then((res) => {
                            if (res.data.status != 200) {
                                return false
                            }
                            return res
                        })
                        this.expandForm.resExpandItemList[index].ownCenter = res.data.results.ownCenter
                        let list = this.expandForm.resExpandItemList
                        this.expandForm.resExpandItemList = []
                        this.expandForm.resExpandItemList = list
                    }
                }
            }
            //计算全部总价
            var totalPrice = 0
            this.expandForm.resExpandItemList.forEach((item, index) => {
                totalPrice = resCommon.numAdd(totalPrice, parseFloat(item.price))
            })
            this.expandForm.amount = totalPrice
        },
        //数量修改事件
        handleChange(scope, index) {
            //重新计算当前组件下的总价
            var currentTotal = 0
            var cpuNum = 0
            if (this.expandForm.resExpandItemList[index].cpuNum != '' && this.expandForm.resExpandItemList[index].cpuNum != undefined) {
                cpuNum = this.expandForm.resExpandItemList[index].cpuNum
            }
            var cpuCore = 0
            if (this.expandForm.resExpandItemList[index].cpuCore != '' && this.expandForm.resExpandItemList[index].cpuCore != undefined) {
                cpuCore = this.expandForm.resExpandItemList[index].cpuNum
            }
            var category = scope.row.category
            var subCategory = scope.row.subCategory
            //软件CPU配置，重新设置实际CPU数量，用于重新计算软件价格
            var cpuNumTmp = cpuNum
            var cpuCoreTmp = cpuCore
            this.expandForm.resExpandItemList[index].resExpandItemPartsList.forEach((item, index) => {
                var num = 0
                if (item.num != '' && item.num != undefined) {
                    num = item.num
                } else {
                    num = 1
                }
                if ('4' == item.category && '1' == item.subCategory) {
                    cpuNumTmp = resCommon.numAdd(Number(cpuNumTmp), num)
                    cpuCoreTmp = resCommon.numAdd(Number(cpuCoreTmp), num)
                }
            })
            this.expandForm.resExpandItemList[index].actCpuNum = cpuNumTmp
            this.expandForm.resExpandItemList[index].actCpuCore = cpuCoreTmp

            //重新计算总价
            this.expandForm.resExpandItemList[index].resExpandItemPartsList.forEach((item, index) => {
                if (item.num == undefined || item.num == '') {
                    item.num = 1
                }
                var num = item.num
                var price = item.price
                if ('5' == item.category && '51' == item.subCategory && '1' != item.isSpecial) {
                    //存储套餐-SAN存储-非归档
                    //SAN存储量修改了，同步修改归档的存储量
                    var ratio = item.ratio
                    var sanRelateRatio = item.sanRelateRatio
                    var newSanRelateNum = Math.ceil(resCommon.accDiv(resCommon.accMul(num, sanRelateRatio), ratio))
                    item.sanRelateNum = newSanRelateNum
                    var sanRelatePrice = item.sanRelatePrice
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.numAdd(resCommon.accMul(resCommon.accDiv(num, 1000), price), resCommon.accMul(resCommon.accDiv(newSanRelateNum, 1000), sanRelatePrice)))
                } else if ('5' == item.category && '51' == item.subCategory && '1' == item.isSpecial) {
                    //存储套餐-SAN存储-归档
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), price))
                } else if ('5' == item.category && '52' == item.subCategory) {
                    //存储套餐-NAS存储

                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), price))
                } else if ('2' == item.category || '4' == item.category) {
                    //物理机配件,虚拟机报价

                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(num, price))
                } else if ('7' == item.category || '8' == item.category || '9' == item.category || '10' == item.category) {
                    //软件
                    if (item.billingMode == '1') {
                        //cpu数量
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accMul(Number(cpuNumTmp), price), num))
                    } else if (item.billingMode == '2') {
                        //cpu核数
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accMul(Number(cpuCoreTmp), price), num))
                    } else {
                        currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(num, price))
                    }
                } else if ('6' == item.category && '61' == item.subCategory) {
                    //DB云 租户价格+容量/1000*容量单价
                    currentTotal = resCommon.numAdd(currentTotal, resCommon.accMul(resCommon.accDiv(num, 1000), item.memPrice))
                }
            })
            this.expandForm.resExpandItemList[index].price = currentTotal
            //计算全部总价
            var totalPrice = 0
            this.expandForm.resExpandItemList.forEach((item, index) => {
                totalPrice = resCommon.numAdd(totalPrice, parseFloat(item.price))
            })
            this.expandForm.amount = totalPrice
        },
        //下载提示文本
        errorText(errorMessage) {
            const element = document.createElement('a')
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(errorMessage))
            element.setAttribute('download', '错误详情')
            element.style.display = 'none'
            element.click()
        },
        // 上传文件状态改变
        changeFile(file, fileList) {
            let nowDate = new Date()
            let objectNo = nowDate.getTime()
            var formData = new FormData()
            formData.append('file', file.raw)
            formData.append('objectNo', objectNo) //唯一编号
            formData.append('type', 'RESADD')
            formData.append('catalogNo', 'CATALOG2021121400001')
            let data = formData
            const loading = common.loading(this)
            uploadExcel(data).then((res) => {
                loading.close()
                if (res.data.status == '200') {
                    this.ifFinish = false
                    this.expandForm.docmId = res.data.results
                    this.$nextTick(() => {
                        this.$refs['form'].clearValidate(['docmId'])
                    })
                    this.$message.success('文件上传成功！')
                } else {
                    this.$message.error('文件上传失败！')
                }
            })
        },
        // 文档下载(改)
        jlDownliad(docmId) {
            console.log(docmId)
            const self = this
            common.downloadDoc(docmId, self)
        },
        // excel上传
        // 上传文件
        uploadFile(file) {}
    }
}
</script>
    <style lang="scss">
.el-dialog__header {
    padding: 10px 10px 10px;
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


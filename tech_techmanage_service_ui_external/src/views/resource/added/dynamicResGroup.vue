<!-- 资源新增嵌入的配置表单 -->
<template>
    <el-card body-style="background-color:#f0f2f5">
        <el-card>
            <el-form :model="dynamicForm" ref="dynamicForm" :rules="rules" inline label-width="130px">

                <el-input v-model="dynamicForm.modelConfig" class="hidden"></el-input>
                <el-input v-model="dynamicForm.networkArea" class="hidden"></el-input>
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="节点类型：" prop="nodeType">
                            <el-select placeholder="请选择" v-model="dynamicForm.nodeType" clearable @change="getRelaModelType" size="mini">
                                <el-option v-for="item in nodeTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="节点名称：" prop="nodeName">
                            <el-input v-model="dynamicForm.nodeName" clearable size="mini"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="所属中心：" prop="ownCenter">
                            <el-select placeholder="请选择" v-model="dynamicForm.ownCenter" clearable size="mini">
                                <el-option v-for="item in ownCenterData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row style="margin-top:15px">
                    <el-col :span="8">
                        <el-form-item label="选型类型：" prop="modelType">
                            <el-select ref="modelType" placeholder="请选择" v-model="dynamicForm.modelType" clearable @change="getRelaModelConfig" size="mini">
                                <el-option v-for="item in modelTypeData" :key="item.DICTID+''" :label="item.DICTNAME" :value="item.DICTID+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="操作系统：" prop="os">
                            <el-select placeholder="请选择" v-model="dynamicForm.os" clearable size="mini" @change="getOsPrice">
                                <el-option v-for="item in osData" :key="item.ID" :label="item.SOFTNAME+(item.SOFTVERSION==null?'':''+item.SOFTVERSION)" :value="item.ID"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="网络区域：" prop="networkAreaName">
                            <net-address-list v-model="dynamicForm.networkAreaName" size="mini" @change="getChildrenData" style="width:82%"></net-address-list>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row style="margin-top:15px">
                    <el-col :span="8">
                        <el-form-item label="选型配置：" prop="modelConfigName">
                            <combo-choice-list v-model="dynamicForm.modelConfigName" :comboType="comboType" size="mini" @change="getChildrenData" style="width:82%"></combo-choice-list>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="节点架构：" prop="nodeArch">
                            <el-select placeholder="请选择" v-model="dynamicForm.nodeArch" clearable size="mini">
                                <el-option v-for="item in nodeArchData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="数据库容量：" prop="dbSize">
                            <el-input v-model.number="dynamicForm.dbSize" clearable size="mini"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row style="margin-top:15px">
                    <el-col :span="8">
                        <el-form-item label="是否挂载NAS：" prop="isNas">
                            <el-select placeholder="请选择" v-model="dynamicForm.isNas" clearable size="mini" @change="changeValiRules()">
                                <el-option v-for="item in BooleanData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="挂在NAS类型：" prop="mountType">
                            <el-select placeholder="请选择" v-model="dynamicForm.mountType" clearable size="mini" :disabled="dynamicForm.isNas!='1'">
                                <el-option v-for="item in nasTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="挂在NAS大小：" prop="nasSize">
                            <el-input v-model.number="dynamicForm.nasSize" clearable size="mini" :disabled="dynamicForm.isNas!='1'"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row style="margin-top:15px">
                    <el-col :span="8">
                        <el-form-item label="是否挂载SAN：" prop="isStorage">
                            <el-select placeholder="请选择" v-model="dynamicForm.isStorage" clearable size="mini" @change="changeValiRules()">
                                <el-option v-for="item in BooleanData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="挂在SAN大小：" prop="storageSize" size="mini">
                            <el-input v-model.number="dynamicForm.storageSize" clearable :disabled="dynamicForm.isStorage!='1'"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
        <div style="margin-top:15px"></div>
        <el-card>
            <label>配件信息：</label>
            <el-table :data="hardWareData" border highlight-current-row style="width:100%;">
                <el-table-column align="center" width='180'>
                    <template slot-scope="scope" slot="header">
                        <el-button type="primary" icon="el-icon-circle-plus" size="mini" @click="addRow(scope.row,1)">增加</el-button>
                    </template>
                    <template slot-scope="scope">
                        <el-button type="danger" icon="el-icon-remove" size="mini" @click="delRow(scope,1)">刪除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="resConfigId" label="配件分类" align="center" width='180'>
                    <template slot-scope="scope">
                        <el-select placeholder="请选择" v-model="scope.row.resConfigId" clearable size="mini" @change="clearSelectHardWare(scope.row)">
                            <el-option v-for="item in resConfigData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column prop="applyScene" label="使用场景" align="center" width='180'>
                    <template slot-scope="scope">
                        <el-select placeholder="请选择" v-model="scope.row.applyScene" clearable @change="showRowRelaData(scope.row)" size="mini">
                            <el-option v-for="(item,i) in applySceneData = hardWareRelaData.filter((item)=>{ return item.DICTID == scope.row.resConfigId})" :key="i" :label="item.APPLYSCENE" :value="item.APPLYSCENE"></el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column prop="describe" label="配置" align="center" width='180'>
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.describe" disabled size="mini"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="单价" align="center" width='180'>
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.price" type="number" disabled size="mini"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="num" label="数量" align="center" width='180'>
                    <template slot-scope="scope">
                        <el-input-number v-model="scope.row.num" type="number" size="mini"></el-input-number>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <div style="margin-top:10px"></div>
        <el-card>
            <label>软件版本：</label>
            <el-table :data="softWareData" border stripe :rules="rules" highlight-current-row style="width:100%;">
                <el-table-column align="center" width='180'>
                    <template slot-scope="scope" slot="header">
                        <el-button type="primary" icon="el-icon-circle-plus" size="mini" @click="addRow(scope.row,2)">增加</el-button>
                    </template>
                    <template slot-scope="scope">
                        <el-button type="danger" icon="el-icon-remove" size="mini" @click="delRow(scope,2)">刪除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="软件类型" align="center" width='180'>
                    <template slot-scope="scope">
                        <el-select placeholder="请选择" v-model="scope.row.type" clearable @change="clearSelectSoftWare(scope.row,'1')" size="mini">
                            <el-option v-for="item in softTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="软件名称" align="center" width='180'>
                    <template slot-scope="scope">
                        <el-select placeholder="请选择" v-model="scope.row.name" clearable v-if="scope.row.type==1" @change="clearSelectSoftWare(scope.row,'2')" size="mini">
                            <el-option v-for="item in softWareNameData = softWareRelaData.filter((item)=>{return item.TYPE == '7'})" :key="item.SOFTNAME" :label="item.SOFTNAME" :value="item.SOFTNAME"></el-option>
                        </el-select>
                        <el-input v-model="scope.row.name" v-if="scope.row.type==2" size="mini"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="version " label="软件版本" align="center" width="20">
                    <template slot-scope="scope">
                        <el-select placeholder="请选择" v-model="scope.row.version" clearable v-if="scope.row.type==1" size="mini">
                            <el-option v-for="(item,i) in softWareVersionData = softWareRelaData.filter((item)=>{return (item.TYPE == '7' && item.SOFTNAME == scope.row.name)})" :key="i" :label="item.SOFTVERSION" :value="item.SOFTVERSION"></el-option>
                        </el-select>
                        <el-input v-model="scope.row.version" v-if="scope.row.type==2" size="mini"></el-input>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <div align="right">
            <label>总价：{{groupprice}}元</label>
        </div>
    </el-card>
</template>
<script>
import { getDicEntry } from '@/api/eosDictEntry'
import { querySelectionConfig, queryHardWareRela, querySoftWareRela } from '@/api/resource/added/resourceManager'
import netAddressList from '@/views/resource/added/netAddressList.vue'
import comboChoiceList from '@/views/resource/added/comboChoiceList.vue'
export default {
    components: { netAddressList, comboChoiceList },
    props: {
        rules: {
            type: Object
        }
    },
    data() {
        return {
            dynamicForm: {
                modelConfig: '',
                networkArea: '',
                nodeType: '',
                nodeName: '',
                ownCenter: '',
                modelType: '',
                os: '',
                networkAreaName: '',
                modelConfig: '',
                nodeArch: '',
                dbSize: 0,
                isNas: '',
                mountType: '',
                nasSize: 0,
                isStorage: '',
                storageSize: 0
            },
            nodeTypeData: [], //节点类型字典数据
            modelTypeData: [], //选型类型数据
            ownCenterData: [], //所属中心字典数据
            osData: [], //操作系统数据
            nodeArchData: [], //节点架构字典数据
            BooleanData: [], //'是否'字典数据
            nasTypeData: [], //NAS分类数据
            preSetConfigData: [], //预设的14种搭配
            hardWareData: [], //硬件列表数据
            softWareData: [], //软件列表数据
            resConfigData: [], //配件分类数据
            softWareRelaData: [], //软件版本数据
            hardWareRelaData: [], //配件属性中的关联关系数据
            groupprice: 0,
            softTypeData: [
                { dictid: 1, dictname: '通用' },
                { dictid: 2, dictname: '其他' }
            ],
            comboType: '', //套餐类型
            comboPrice: 0, //套餐价格
            osPrice: 0 //操作系统价格
            // 数据库价格
            // NAS价格
            // SAN价格
            // 配件价格
            // 软件价格
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {
        //统一的数据准备方法
        this.getPreSetData()
    },
    methods: {
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_NODETYPE,RES_OWNCENTER,RES_NODEARCH,RES_YESORNO,RES_SUBCATEGORY'
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
                //小类
                let subcategoryData = data.filter((item) => {
                    return item.dicttypeid == 'RES_SUBCATEGORY'
                })
                //------------对小类进行再次筛选------------
                this.resConfigData = subcategoryData.filter((item) => {
                    return item.parentid == '2'
                })
                return data
            })
        },
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
            //软件类型的关系表(7:操作系统软件 8:数据库软件)
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
        },
        getRelaModelType() {
            let nodeType = this.dynamicForm.nodeType
            this.modelTypeData = this.preSetConfigData.filter((item) => {
                return item.NODETYPE == nodeType
            })
            this.$set(this.dynamicForm, 'modelType', '')
        },
        getRelaModelConfig() {
            this.$set(this.dynamicForm, 'modelConfig', '')
            let modelType = this.dynamicForm.modelType
            let modelConfig = this.modelTypeData.filter((item) => {
                return item.DICTID == modelType
            })
            if (modelConfig.length == 1) {
                this.comboType = modelConfig[0].MODELCONFIGTYPE
            } else {
                this.comboType = ''
            }
        },
        delRow(scope, type) {
            if (type == 1) {
                this.hardWareData.splice(scope.$index, 1)
            } else if (type == 2) {
                this.softWareData.splice(scope.$index, 1)
            }
        },
        addRow(row, type) {
            if (type == 1) {
                this.hardWareData.push({
                    resConfigId: '',
                    applyScene: '',
                    describe: '',
                    price: '',
                    num: 0
                })
            } else if (type == 2) {
                this.softWareData.push({
                    type: '',
                    name: '',
                    version: ''
                })
            }
        },
        showRowRelaData(row) {
            if (row.resConfigId != '' && row.resConfigId != undefined && row.applyScene != '' && row.applyScene != undefined) {
                let data = this.applySceneData.filter((item) => {
                    return item.DICTID == row.resConfigId && item.APPLYSCENE == row.applyScene
                })
                row.price = data[0].PRICE
                row.describe = data[0].DESCRIBE
            } else {
                row.price = ''
                row.describe = ''
            }
        },
        clearSelectHardWare(row) {
            this.$set(row, 'applyScene', '')
            this.showRowRelaData(row)
        },
        clearSelectSoftWare(row, type) {
            if (type == '1' || type == '2') {
                if (type == '1') {
                    this.$set(row, 'name', '')
                }
                this.$set(row, 'version', '')
            }
        },
        getChildrenData(args, flag) {
            if (flag == 'netList') {
                this.dynamicForm.networkArea = args.id
            } else if (flag == 'comboList') {
                this.dynamicForm.modelConfig = args.id
                this.comboPrice = args.comboPrice
            }
        },
        getOsPrice() {
            let osDataOne = this.osData.filter((item) => {
                return item.ID == this.dynamicForm.os
            })
            console.log(osDataOne)
            if (osDataOne.length == 1) {
                this.osPrice = osDataOne[0].PRICE
            } else {
                this.osPrice = 0
            }
        },
        test() {
            var result = false
            this.$refs['dynamicForm'].validate((valid) => {
                if (!valid) {
                    result = false
                } else {
                    result = true
                }
            })
            return result
        },
        changeValiRules() {
            if (this.dynamicForm.isNas != 1) {
                this.rules.mountType = ''
                this.rules.nasSize = ''
            } else {
                this.rules.mountType = [{ required: true, message: '请选择挂载NAS类型', trigger: 'blur' }]
                this.rules.nasSize = [
                    { required: true, message: '请输入NAS容量', trigger: 'blur' },
                    { type: 'number', message: '只能填写数字' }
                ]
            }
            if (this.dynamicForm.isStorage != 1) {
                this.rules.storageSize = ''
            } else {
                this.rules.storageSize = [
                    { required: true, message: '请选择节点类型', trigger: 'blur' },
                    { type: 'number', message: '只能填写数字' }
                ]
            }
        }
    }
}
</script>
<style>
.hidden {
    display: none;
}
</style>
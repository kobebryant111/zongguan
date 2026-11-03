<!-- 资源新增，编辑，查看显示页面 -->
<template>
    <div>
        <el-dialog class="dialogFull" :title="titleName" :visible.sync="flDialog" fullscreen center :before-close="button_cancel">
            <el-row>
                <el-col :span="24">
                    <el-form ref="form" inline label-width="120px" :model="form" :disabled="!viewFlag">

                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="应用名称：" prop="appNo" :rules="{required: true, message: ' '}">
                                    <rela-sys-tree :disabled="!viewFlag" ref="relaSysTree" size="mini" @change="getChildrenData" style="width:82%"></rela-sys-tree>
                                </el-form-item>
                            </el-col>
                            <el-col :span=" 6">
                                <el-form-item label="项目名称：">
                                    <rela-program-tree ref="relaProgramTree" size="mini" @change="getChildrenData" style="width:82%"></rela-program-tree>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="预计上线时间：" prop="expectedOnlineTime" :rules="{required: true, message: ' '}">
                                    <el-date-picker v-model="form.expectedOnlineTime" size="mini" style="width:82%" value-format="yyyy-MM-dd"></el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="补录流程编号：">
                                    <el-input v-model="form.reNewProcessNo" disabled size="mini" style="width:82%">
                                        <el-button slot="append" icon="el-icon-more" @click="chooseAdditional"></el-button>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="压力测试报告：" prop="docmId" :rules="{required:true,message:'请上传',trigger:'change'}">
                                    <el-upload class="upload-demo" action="Fake Action" :file-list="fileList" :show-file-list="false" :http-request="uploadFile" :on-change="changeFile" v-if="form.docmId=='' || form.docmId==undefined">
                                        <el-button size="mini" type="primary">
                                            <i class="el-icon-upload2"></i>上传文件
                                        </el-button>
                                    </el-upload>
                                    <div class="upload_scss" v-else>
                                        <div @click="jlDownliad(form.docmId)">下载</div>
                                        <el-upload class="upload-demo againUpLoad" action="Fake Action" :file-list="fileList" :show-file-list="false" :http-request="uploadFile" :on-change="changeFile" v-if="viewFlag">
                                            重传
                                        </el-upload>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="20">
                    <el-card style="margin-top:20px">
                        <div slot='header'>
                            <el-button-group v-if="viewFlag">
                                <el-button icon="el-icon-plus" type="primary" size="mini" @click="newCurrent">新增</el-button>
                                <el-button icon="el-icon-document" type="info" size="mini" @click="copyCurrent" v-if="dynamicForm.id!==undefined && dynamicForm.id!=''">复制</el-button>
                                <el-button icon="el-icon-delete" type="danger" size="mini" @click="deleteCurrent(dynamicForm.id)" v-if="dynamicForm.id!==undefined && dynamicForm.id!=''">删除</el-button>
                                <el-button icon="el-icon-goods" type="success" size="mini" @click="putIntoShop">加入资源列表</el-button>
                            </el-button-group>
                        </div>
                        <el-form :model="dynamicForm" ref="dynamicForm" inline label-width="150px" :disabled="!viewFlag">
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="节点类型：" prop="nodeType" :rules="{required: true, message: ' '}">
                                        <el-select placeholder="请选择" v-model="dynamicForm.nodeType" clearable size="mini" @change="clearOther('nodeType')">
                                            <el-option v-for="item in nodeTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="选型类型：" prop="modelType" :rules="{required: true, message: ' '}">
                                        <el-select ref="modelType" placeholder="请选择" v-model="dynamicForm.modelType" clearable size="mini" @change="hardWareClear()">
                                            <el-option v-for="item in modelTypeData = preSetConfigData.filter((item) => { return item.NODETYPE == dynamicForm.nodeType })" :key="item.DICTID+''" :label="item.DICTNAME" :value="item.DICTID+''"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="选型配置：" prop="modelConfigName" :rules="{required: true, message: ' '}">
                                        <el-input v-model="dynamicForm.modelConfigName" size="mini" readonly style="width:82%" placeholder="点击选择套餐->">
                                            <el-button slot="append" icon="el-icon-more" @click="chooseCombo(dynamicForm)"></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="节点名称：" prop="nodeName" :rules="{required: true, message: ' '}">
                                        <el-input v-model="dynamicForm.nodeName" clearable size="mini"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="所属中心：" prop="ownCenter" :rules="{required: true, message: ' '}">
                                        <el-select placeholder="请选择" v-model="dynamicForm.ownCenter" clearable size="mini">
                                            <el-option v-for="item in ownCenterData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="网络区域：" prop="networkAreaName" :rules="{required: true, message: ' '}">
                                        <el-input v-model="dynamicForm.networkAreaName" readonly size="mini" style="width:82%">
                                            <el-button slot="append" icon="el-icon-more" @click="chooseNetWorkArea()"></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="操作系统：" prop="os" :rules="dynamicForm.modelConfigName==''?{}:{required: true, message: ' '}">
                                        <el-select placeholder="请选择" v-model="dynamicForm.os" clearable size="mini" @change="getOsPrice()" :disabled="dynamicForm.modelConfigName==''">
                                            <el-option v-for="item in osData" :key="item.ID" :label="item.SOFTNAME+(item.SOFTVERSION==null?'':''+item.SOFTVERSION)" :value="item.ID"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="节点架构：" prop="nodeArch" :rules="{required: true, message: ' '}">
                                        <el-select placeholder="请选择" v-model="dynamicForm.nodeArch" clearable size="mini">
                                            <el-option v-for="item in nodeArchData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="数据库容量：" prop="dbSize" :rules="dynamicForm.nodeType!=3 || dynamicForm.modelType!=3 || dynamicForm.modelConfigName==''?{type: 'number', message: ' '}: [{required: true, message: ' ' },{ type: 'number', message: '只能填写数字' }]">
                                        <el-input-number v-model.number="dynamicForm.dbSize" clearable size="mini" type="number" :min=0 :disabled="dynamicForm.nodeType!=3 || dynamicForm.modelType!=3 || dynamicForm.modelConfigName==''" style="width:87%" @change="countDbPrice()"></el-input-number>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="是否挂载NAS：" prop="isNas" :rules="{required: true, message: ' '}">
                                        <el-select placeholder="请选择" v-model="dynamicForm.isNas" clearable size="mini" @change="changeValiRules('nas')">
                                            <el-option v-for="item in mountData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="挂载NAS类型：" prop="mountType" :rules="dynamicForm.isNas==1?{ required: true, message: ' '}:{}">
                                        <el-select placeholder="请选择" v-model="dynamicForm.mountType" clearable size="mini" :disabled="dynamicForm.isNas!='1'" @change="getNasPrice()">
                                            <el-option v-for="item in nasTypeData" :key="item.ID" :label="item.APPLYSCENE" :value="item.ID"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="挂载NAS大小(G)：" prop="nasSize" :rules="dynamicForm.isNas==1?[{ required: true, message: ' '},{ type: 'number', message: '只能填写数字' }]:{ type: 'number', message: ' ' }">
                                        <el-input-number v-model.number="dynamicForm.nasSize" clearable size="mini" type="number" :min=0 :disabled="dynamicForm.isNas!=1 || dynamicForm.mountType==''" style="width:87%" @change="countNasPrice()"></el-input-number>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="是否挂载SAN：" prop="isStorage" :rules="form.appNo=='' ||( dynamicForm.nodeType==3 && (dynamicForm.modelType==2 || dynamicForm.modelType==3)) || dynamicForm.nodeType=='' || dynamicForm.modelType==''?{}:{required: true, message: ' '}">
                                        <el-select placeholder="请选择" v-model="dynamicForm.isStorage" clearable size="mini" @change="changeValiRules('san')" :disabled="form.appNo=='' ||( dynamicForm.nodeType==3 && (dynamicForm.modelType==2 || dynamicForm.modelType==3)) || dynamicForm.nodeType=='' || dynamicForm.modelType==''">
                                            <el-option v-for="item in mountData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="挂载SAN大小(G)：" prop="storageSize" size="mini" :rules="dynamicForm.isStorage==1?[{ required: true, message: ' '},{ type: 'number', message: '只能填写数字' }]:{ type: 'number', message: ' ' }">
                                        <el-input-number v-model.number="dynamicForm.storageSize" clearable size="mini" type="number" :min=0 :disabled="dynamicForm.isStorage!='1'" style="width:87%" @change="countSanPrice()"></el-input-number>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-table style="width:100%;margin-top:10px" :data=" dynamicForm.hardWareData" border stripe highlight-current-row v-if="dynamicForm.modelConfigName!='' && ((dynamicForm.modelType!=3 && dynamicForm.modelType!=4 && dynamicForm.nodeType!=4) || (dynamicForm.nodeType==4 && dynamicForm.modelType==1))">
                                <el-table-column type="index" align="left" width="120" v-if="viewFlag">
                                    <template slot-scope="scope" slot="header">
                                        <el-button type="primary" icon="el-icon-circle-plus" size="mini" @click="addRow(scope.row,1)">增加</el-button>
                                    </template>
                                    <template slot-scope="scope">
                                        <el-button type="danger" icon="el-icon-remove" size="mini" @click="delRow(scope,1)">刪除</el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="resConfigId" label="配件分类" align="center" width='180'>
                                    <template slot-scope="scope">
                                        <el-form-item :prop="`hardWareData.${scope.$index}.resConfigId`" :rules="{required: true, message: ' '}">
                                            <el-select placeholder="请选择" v-model="scope.row.resConfigId" clearable size="mini" @change="clearSelectHardWare(scope.row)">
                                                <el-option v-for="item in resConfigData = (dynamicForm.modelType==1?resConfigPhyData:resConfigVmData)" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="describe" label="配置" align="center" width='180'>
                                    <template slot-scope="scope">
                                        <el-form-item :prop="`hardWareData.${scope.$index}.describe`" :rules="{required: true, message: ' '}">
                                            <el-select placeholder="请选择" v-model="scope.row.describe" clearable @change="showRowRelaData(scope.row)" size="mini">
                                                <el-option v-for="(item,i) in applySceneData = hardWareRelaData.filter((item)=>{ return item.DICTID == scope.row.resConfigId})" :key="i" :label="item.DESCRIBE" :value="item.DESCRIBE"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="applyScene" label="使用场景" align="center" width='180' v-if="dynamicForm.modelType==1">
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.applyScene" disabled size="mini"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="price" label="单价" align="center" width='180'>
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.price" disabled size="mini"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="num" label="数量" align="center" width='180'>
                                    <template slot-scope="scope">
                                        <el-input-number v-model="scope.row.num" type="number" size="mini" :min=1 v-if="scope.row.price" @change="countHardWarePirce()"></el-input-number>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="partId" label="配件ID" align="center" width='180' v-if="false">
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.partId" size="mini"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="partCategory" label="配件大类" align="center" width='180' v-if="false">
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.partCategory" size="mini"></el-input>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <el-table :data="dynamicForm.softWareData" border stripe style="width:100%;margin-top:10px" highlight-current-row v-if="dynamicForm.modelConfigName!=''">
                                <el-table-column type="index" align="left" width="90" v-if="viewFlag">
                                    <template slot-scope="scope" slot="header">
                                        <el-button type="primary" icon="el-icon-circle-plus" size="mini" @click="addRow(scope.row,2)">增加</el-button>
                                    </template>
                                    <template slot-scope="scope">
                                        <el-button type="danger" icon="el-icon-remove" size="mini" @click="delRow(scope,2)">刪除</el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="type" label="软件类型" align="center" width='180'>
                                    <template slot-scope="scope">
                                        <el-form-item :prop="`softWareData.${scope.$index}.type`" :rules="{required: true, message: ' '}">
                                            <el-select placeholder="请选择" v-model="scope.row.type" clearable @change="clearSelectSoftWare(scope.row,'1')" size="mini">
                                                <el-option v-for=" item in softTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="name" label="软件名称" align="center" width='180'>
                                    <template slot-scope="scope">
                                        <el-form-item :prop="`softWareData.${scope.$index}.name`" :rules="(scope.row.type==1 || scope.row.type==2)?{required: true, message: ' '}:{}">
                                            <el-select placeholder="请选择" v-model="scope.row.name" clearable v-if="scope.row.type==1" @change="clearSelectSoftWare(scope.row,'2')" size="mini">
                                                <el-option v-for="(item,i) in softWareNameData = softWareRelaData.filter((item)=>{return item.TYPE != '7'})" :key="i" :label="item.SOFTNAME" :value="item.SOFTNAME"></el-option>
                                            </el-select>
                                            <el-input v-model="scope.row.name" v-if="scope.row.type==2" size="mini"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="version" label="软件版本" align="center" width='180'>
                                    <template slot-scope="scope">
                                        <el-form-item :prop="`softWareData.${scope.$index}.version`" :rules="(scope.row.type==1 || scope.row.type==2)?{required: true, message: ' '}:{}">
                                            <el-select placeholder="请选择" v-model="scope.row.version" clearable v-if="scope.row.type==1" size="mini" @change="getSoftWarePrice(scope.row)">
                                                <el-option v-for="(item,i) in softWareVersionData = softWareRelaData.filter((item)=>{return (item.TYPE != '7' && item.SOFTNAME == scope.row.name)})" :key="i" :label="item.SOFTVERSION" :value="item.SOFTVERSION"></el-option>
                                            </el-select>
                                            <el-input v-model="scope.row.version" v-if="scope.row.type==2" size="mini"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="price" label="软件价格" align="center" width='180' v-if="false">
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.price" size="mini"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="countModel" label="计费模式" align="center" width='180' v-if="false">
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.countModel" size="mini"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="softId" label="软件ID" align="center" width='180' v-if="false">
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.softId" size="mini"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="softCategory" label="软件大类" align="center" width='180' v-if="false">
                                    <template slot-scope="scope">
                                        <el-input v-model="scope.row.softCategory" size="mini"></el-input>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-form>
                        <div style="margin-top:10px" align="right">
                            <label>总价：{{
                                    dynamicForm.price
                                }}元</label>
                        </div>
                        <div class="czLog" v-if="!viewFlag">
                            <flow-logs-gd ref="log"></flow-logs-gd>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="4">
                    <el-card style="margin-left:20px;margin-top:20px">
                        <el-table height="500" :data="menuData" @row-click="dealMenu" highlight-current-row>
                            <el-table-column prop="menuName" label="资源列表:" width="180">
                            </el-table-column>
                        </el-table>
                        <div style="margin-top:10px" align="right">
                            <label>总价：{{
                                    form.amount
                                }}元</label>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <div slot="footer" class="dialog-footer" style="position:fixed;bottom:1%;width:100%;align:center" v-if="viewFlag">
                <el-button icon="el-icon-document" type="primary" @click="button_save" size="mini">保 存</el-button>
                <el-button icon="el-icon-check" type="primary" @click="button_submit" size="mini">提 交</el-button>
            </div>
        </el-dialog>
        <!-- 子组件 -->
        <combo-choice-list ref="comboChoiceList"></combo-choice-list>
        <net-address-list ref="netAddressList"></net-address-list>
        <additional-no-list ref="additionalNoList"></additional-no-list>
    </div>
</template>
<script>
import { getDicEntry } from '@/api/eosDictEntry'
import relaSysTree from '@/views/resource/added/relaSysTree'
import relaProgramTree from '@/views/resource/added/relaProgramTree'
import { uploadExcel } from '@/api/integrated/wbms'
import { addResource, querySelectionConfig, queryHardWareRela, querySoftWareRela, queryStorageComboData, queryResAddedConfig, submitResourceAdded, putIntoShop, showMenuItem, deleteItemByItemId, refreshMenu, deleteDynamicForms, saveForm, validOutofDateData } from '@/api/resource/added/resourceManager'
import netAddressList from '@/views/resource/added/netAddressList'
import comboChoiceList from '@/views/resource/added/comboChoiceList'
import additionalNoList from '@/views/resource/added/additionalNoList'
import common from '@/api/common'
import flowLogsGd from '@/views/resource/flow/flowLogsGd'
import calculate from '@/api/resource/added/common'

// 引入ntko
let ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min').ntkoBrowser

export default {
    components: {
        relaSysTree,
        relaProgramTree,
        netAddressList,
        comboChoiceList,
        additionalNoList,
        flowLogsGd
    },
    data() {
        return {
            viewFlag: true, //查看标识
            menuData: [], //购物车数据
            isCollapse: false,
            form: {
                id: '',
                priceReqNo: '',
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
                amount: 0,
                docmId: '',
                dynamicForm: []
            },
            hardWareData: [
                {
                    resConfigId: '',
                    applyScene: '',
                    describe: '',
                    price: 0,
                    num: 0
                }
            ], //硬件列表数据
            softWareData: [
                {
                    type: '',
                    name: '',
                    version: ''
                }
            ], //软件列表数据
            dynamicForm: {
                softId: 0,
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
                sanId: '',
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
            },
            hardWareData: [{ resConfigId: '', applyScene: '', describe: '', price: 0, num: 0 }], //硬件列表数据
            softWareData: [{ type: '', name: '', version: '' }], //软件列表数据
            activeNames: ['1'],
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
            softTypeData: [
                { dictid: 1, dictname: '通用' },
                { dictid: 2, dictname: '其它' }
            ],
            fileList: [] //上传文件列表
        }
    },
    created() {
        //统一的数据准备方法
        this.getPreSetData()
        this.getDictData()
    },
    mounted() {},
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
                this.hardWareRelaData = res.data.results.filter((item) => {
                    if (this.viewFlag) {
                        return item.ISCANCEL == 0
                    } else {
                        return item
                    }
                })
            })
            //软件类型的关系表(7:操作系统软件 8:数据库软件 9:应用中间件 10:消息中间件)
            querySoftWareRela().then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.softWareRelaData = res.data.results.filter((item) => {
                    if (this.viewFlag) {
                        return item.ISCANCEL == 0
                    } else {
                        return item
                    }
                })
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
                this.storageData = res.data.results.filter((item) => {
                    if (this.viewFlag) {
                        return item.ISCANCEL == 0
                    } else {
                        return item
                    }
                })
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
        add() {
            //页面初始化
            this.loadDataEve()
            this.getPreSetData()
            this.titleName = '资源新增发起'
            this.flDialog = true
        },
        edit(id) {
            this.loadDataEve()
            this.getPreSetData()
            this.titleName = '资源新增编辑'
            let param = {}
            param.id = id
            let data = common.paramsFormat(param)
            queryResAddedConfig(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                } else {
                    this.$nextTick(() => {
                        let form = res.data.results
                        //给关联系统赋值
                        this.$refs.relaSysTree.clearValue(form.appName)
                        //给项目赋值
                        this.$refs.relaProgramTree.clearValue(form.projectName)
                        //合并初始化的form和后端查询的form
                        Object.assign(this.form, form)
                        this.refreshMenu(form.id)
                    })
                }
            })
            this.flDialog = true
        },
        view(row) {
            this.loadDataEve()
            this.edit(row.id)
            this.titleName = '资源新增查看'
            this.viewFlag = false
            // 流程日志
            this.$nextTick(() => {
                let item = {}
                if (row.status == 4) {
                    item.status = 'gd'
                    item.flowstatus = '已结束'
                    item.instanceid = row.processInstID
                } else if (['2', '3', '5'].indexOf(row.status) > -1) {
                    item.processInstID = row.processInstID
                } else {
                    item.processInstID = 'eeee' //随便传入的字符，非空jiux
                }
                this.$refs.log.init(item)
                this.getPreSetData()
            })
        },
        //初始化页面
        loadDataEve() {
            this.form = {
                id: '',
                priceReqNo: '',
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
                amount: 0,
                docmId: ''
            }
            this.clearDynamicForm() //1.清空表单中的值
            this.viewFlag = true
            this.menuData = []
            this.$nextTick(() => {
                //给应用系统赋值
                this.$refs.relaSysTree.clearValue('')
                //给项目赋值
                this.$refs.relaProgramTree.clearValue('')
                //重置所有的校验
                this.clearValidateForm()
            })
        },
        //取消，关闭
        button_cancel() {
            this.flDialog = false
            this.$parent.packageInit()
        },
        //获取子组件的返回值
        getChildrenData(args, flag) {
            if (flag == 'sysTree') {
                //关联系统树
                this.form.appNo = args.id //系统ID
                this.form.appName = args.text //系统名称
                this.form.sysOperLevel = args.omLevel //系统运维等级
                this.form.isFundTrans = args.refMoney //是否涉资
                this.form.busiDeptName = args.busDepName //s所属业务部门名称
                this.form.techDeptName = args.tecDepName //所属技术部门名称
                this.form.busiDeptId = args.busDep //所属业务部ID
                this.form.techDeptId = args.tecDep //所属技术部ID
                this.form.ciid = args.ciid //CMDB系统唯一识别编号
                //系统改变时，清空购物车以及页面上数据
                this.clearDynamicForm() //1.清空表单中的值
                if (this.form.id != '' && this.form.id != undefined) {
                    //已经保存过
                    if (this.menuData.length > 0) {
                        //菜单中有值
                        this.$confirm('此操作会清空当前数据及资源列表，确定继续吗?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then((res) => {
                            let param = { id: this.form.id }
                            let data = common.paramsFormat(param)
                            deleteDynamicForms(data).then(() => {
                                this.refreshMenu(this.form.id)
                                this.$message.success('修改成功！')
                            })
                        })
                    }
                }
            } else if (flag == 'proTree') {
                //关联项目树
                this.form.projectNo = args.id //项目ID
                this.form.projectName = args.text //项目名称
            } else if (flag == 'netList') {
                this.dynamicForm.networkArea = args.id
                this.dynamicForm.networkAreaName = args.netWorkArea
            } else if (flag == 'comboList') {
                //先清空
                this.clearOther('modelConfigName')
                //再赋值
                this.dynamicForm.modelConfig = args.id
                this.dynamicForm.comboTotalPrice = args.comboPrice
                this.dynamicForm.cpuNum = args.cpuNum
                this.dynamicForm.cpuCode = args.cpuCode
                this.dynamicForm.modelConfigName = args.describe
                this.dynamicForm.dbPrice = args.dbPrice

                //算钱
                this.countCardAll()
            } else if (flag == 'additionalList') {
                this.form.reNewProcessNo = args.id //补录编号
            }
        },
        clearOther(type) {
            let nodeType = this.dynamicForm.nodeType
            let modelType = this.dynamicForm.modelType
            let modelConfigName = this.dynamicForm.modelConfig
            var softId = this.dynamicForm.softId
            let dynamicForm = {
                softId: softId,
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
                sanId: '',
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
            if (type == 'nodeType') {
                dynamicForm.nodeType = nodeType
            } else if (type == 'modelType') {
                dynamicForm.nodeType = nodeType
                dynamicForm.modelType = modelType
            } else if (type == 'modelConfigName') {
                dynamicForm.nodeType = nodeType
                dynamicForm.modelType = modelType
            }
            Object.keys(this.dynamicForm).forEach((key) => (this.dynamicForm[key] = dynamicForm[key]))
        },
        hardWareClear(index) {
            this.clearOther('modelType')
        },
        chooseCombo(dynamicForm) {
            //选择套餐
            this.$nextTick(() => {
                if (dynamicForm.nodeType == undefined || dynamicForm.nodeType == '') {
                    this.$alert('节点类型为空，无法选择套餐', {
                        type: 'error'
                    })
                    return
                }
                if (dynamicForm.modelType == undefined || dynamicForm.modelType == '') {
                    this.$alert('选型类型为空，无法选择套餐', {
                        type: 'error'
                    })
                    return
                }
                let comboTypeData = this.preSetConfigData.filter((item) => {
                    return item.DICTID == dynamicForm.modelType && item.NODETYPE == dynamicForm.nodeType
                })
                let comboType = comboTypeData[0].MODELCONFIGTYPE
                let nodeType = this.dynamicForm.nodeType
                this.$refs.comboChoiceList.toSelect(nodeType, comboType)
            })
        },
        // 补录编号选择
        chooseAdditional() {
            this.$nextTick(() => {
                this.$refs.additionalNoList.toSelect()
            })
        },
        chooseNetWorkArea() {
            //选择网络区域
            this.$nextTick(() => {
                this.$refs.netAddressList.toSelect()
            })
        },
        //获取操作系统价格,计费模式
        getOsPrice() {
            let osDataOne = this.osData.filter((item) => {
                return item.ID == this.dynamicForm.os
            })
            if (osDataOne.length == 1 && osDataOne != null) {
                this.dynamicForm.osPrice = osDataOne[0].PRICE //单格
                this.dynamicForm.osCountModel = osDataOne[0].BILLINGMODE //计费模式
                this.dynamicForm.osTotalPrice = this.calculateByCountModel(osDataOne[0].BILLINGMODE, osDataOne[0].PRICE)
            } else {
                this.dynamicForm.osPrice = 0
                this.dynamicForm.osCountModel = '-1'
                this.dynamicForm.osTotalPrice = 0
            }
            this.countCardAll()
        },
        calculateByCountModel(countModel, price) {
            //根据计费模式计算价格
            let cpuNum = 0
            let cpuCode = 0
            if (this.dynamicForm.cpuNum != undefined || this.dynamicForm.cpuNum != '') {
                cpuNum = this.dynamicForm.cpuNum
            }
            if (this.dynamicForm.cpuCode != undefined || this.dynamicForm.cpuCode != '') {
                cpuCode = this.dynamicForm.cpuCode
            }
            console.log('cpu数' + cpuNum + ',cpu核数' + cpuCode + ',计算模式:' + countModel + ',单价:' + price)
            var totalCount = 0
            if (countModel == 1) {
                //按CPU数量
                totalCount = parseFloat(calculate.accMul(cpuNum, price))
            } else if (countModel == 2) {
                totalCount = parseFloat(calculate.accMul(cpuCode, price))
            } else if (countModel == 3) {
                totalCount = price
            }
            return totalCount
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
                    this.form.docmId = res.data.results
                    this.$nextTick(() => {
                        this.$refs['form'].clearValidate(['docmId'])
                    })
                    this.$message.success('文件上传成功！')
                } else {
                    this.$message.error('文件上传失败！')
                }
            })
        },
        countDbPrice() {
            //计算数据库容量总价
            let dbSize = 0
            let dbPrice = 0
            let totalPrice = 0
            if (this.dynamicForm.dbSize != undefined && this.dynamicForm.dbSize != '') {
                dbSize = this.dynamicForm.dbSize
            }
            if (this.dynamicForm.dbPrice != undefined && this.dynamicForm.dbPrice != '') {
                dbPrice = this.dynamicForm.dbPrice
            }
            totalPrice = parseFloat(calculate.accMul(calculate.accDiv(dbSize, 1000), dbPrice))
            console.log('数据库容量为:' + dbSize + ',数据库单价:' + dbPrice + ',总费用为:' + totalPrice)
            this.dynamicForm.dbTotalPrice = totalPrice
            this.countCardAll()
        },
        changeValiRules(type) {
            if (type == 'nas') {
                if (!this.valiIsShare('NAS')) {
                    this.$alert('是否挂载NAS至少有一条数据选择是')
                    this.$set(this.dynamicForm, 'isNas', '')
                }
                this.$set(this.dynamicForm, 'mountType', '')
                this.$set(this.dynamicForm, 'nasSize', 0)

                this.countNasPrice()
                this.countCardAll()
            } else if (type == 'san') {
                if (!this.valiIsShare('SAN')) {
                    this.$alert('是否挂载SAN至少有一条数据选择是')
                    this.$set(this.dynamicForm, 'isStorage', '')
                }
                this.$set(this.dynamicForm, 'storageSize', 0)

                this.countSanPrice()
                this.countCardAll()
            }
        },
        countNasPrice() {
            //计算NAS的总价
            let nasSize = 0
            let nasPrice = 0
            if (this.dynamicForm.nasSize != undefined && this.dynamicForm.nasSize != '') {
                nasSize = this.dynamicForm.nasSize
            }
            if (this.dynamicForm.nasPrice != undefined && this.dynamicForm.nasPrice != '') {
                nasPrice = this.dynamicForm.nasPrice
            }
            //计算NAS的总价
            console.log('nas容量:' + nasSize + ',nas单价:' + nasPrice)
            this.dynamicForm.nasTotalPrice = parseFloat(calculate.accMul(nasPrice, calculate.accDiv(nasSize, 1000)))
            this.countCardAll()
        },
        //获取nas存储价格
        getNasPrice() {
            let nasDataOne = this.nasTypeData.filter((item) => {
                return item.ID == this.dynamicForm.mountType
            })
            if (nasDataOne.length == 1 && nasDataOne != null) {
                this.dynamicForm.nasPrice = nasDataOne[0].PRICE
            } else {
                this.dynamicForm.nasPrice = 0
            }
            this.countNasPrice()
            this.countCardAll()
        },
        countNasPrice() {
            //计算NAS的总价
            let nasSize = 0
            let nasPrice = 0
            if (this.dynamicForm.nasSize != undefined && this.dynamicForm.nasSize != '') {
                nasSize = this.dynamicForm.nasSize
            }
            if (this.dynamicForm.nasPrice != undefined && this.dynamicForm.nasPrice != '') {
                nasPrice = this.dynamicForm.nasPrice
            }
            //计算NAS的总价
            console.log('nas容量:' + nasSize + ',nas单价:' + nasPrice)
            this.dynamicForm.nasTotalPrice = parseFloat(calculate.accMul(nasPrice, calculate.accDiv(nasSize, 1000)))
            this.countCardAll()
        },
        countSanPrice() {
            if (this.sanSpicalComboData != null && this.sanSpicalComboData.length > 0) {
                this.dynamicForm.sanSpecialId = this.sanSpicalComboData[0].ID
                this.dynamicForm.sanSpecialPrice = this.sanSpicalComboData[0].PRICE
                this.dynamicForm.sanSpecialRatio = this.sanSpicalComboData[0].RATIO
            } else {
                this.$alert('没有获取到对应的SAN归档数据', { type: 'error' })
                //没有查询到关联数据时，不允许挂载SAN
                this.$set(this.dynamicForm, 'storageSize', 0)
                this.dynamicForm.isStorage = ''
                return
            }
            console.log('系统运维等级:' + this.form.sysOperLevel + ',是否涉资:' + this.form.isFundTrans)
            //获取SAN套餐信息
            let sanComboData = this.storageData.filter((item) => {
                return item.DEVOPSLEVEL == this.form.sysOperLevel && item.ISRELATEFUND == this.form.isFundTrans && item.SUBCATEGORY == '51'
            })
            let price = 0
            let ratio = 0
            let sanId = ''
            if (sanComboData != null && sanComboData.length > 0) {
                //根据是否涉资和系统运维等级获取的san套餐数据
                price = sanComboData[0].PRICE
                ratio = sanComboData[0].RATIO
                sanId = sanComboData[0].ID
            } else {
                if (this.dynamicForm.isStorage != 0) {
                    this.$alert('没有获取到对应的SAN套餐信息', { type: 'error' })
                    //没有查询到关联数据时，不允许挂载SAN
                    this.$set(this.dynamicForm, 'storageSize', 0)
                    this.dynamicForm.isStorage = ''
                }
                return
            }

            //计算san的总价
            let sanSize = 0
            if (this.dynamicForm.storageSize != undefined && this.dynamicForm.storageSize != '') {
                sanSize = this.dynamicForm.storageSize
            }

            this.dynamicForm.sanPrice = price
            this.dynamicForm.sanRatio = ratio
            this.dynamicForm.sanId = sanId

            //获取配比的san归档数据
            let specialPrice = this.dynamicForm.sanSpecialPrice
            let specialRatio = this.dynamicForm.sanSpecialRatio
            let sanSpecialSize = Math.ceil(calculate.accDiv(calculate.accMul(sanSize, specialRatio), ratio))
            this.dynamicForm.sanSpecialSize = sanSpecialSize
            let totalPrice = parseFloat(calculate.accMul(calculate.accDiv(sanSize, 1000), price))
            let specialTotalPrice = parseFloat(calculate.accMul(calculate.accDiv(sanSpecialSize, 1000), specialPrice))

            console.log('san容量:' + sanSize + ',san单价:' + price + ',san比例:' + ratio + ',san总价' + totalPrice)
            console.log('归档san容量:' + sanSpecialSize + ',归档san单价:' + specialPrice + ',归档san比例:' + specialRatio + ',san总价' + specialTotalPrice)
            let sum = parseFloat(calculate.numAdd(totalPrice, specialTotalPrice))
            if ((sum + '').indexOf('.') > 0) {
                this.dynamicForm.sanTotalPrice = parseFloat(parseFloat(sum).toFixed(1))
            } else {
                this.dynamicForm.sanTotalPrice = sum
            }
            this.countCardAll()
        },
        delRow(scope, type) {
            if (type == 1) {
                this.dynamicForm.hardWareData.splice(scope.$index, 1)
                this.countHardWarePirce()
            } else if (type == 2) {
                this.dynamicForm.softWareData.splice(scope.$index, 1)
                this.countSoftWarePrice()
            }
            this.countCardAll() //计算card的总价
        },
        addRow(row, type) {
            if (this.dynamicForm.modelConfig == undefined || this.dynamicForm.modelConfig == '') {
                this.$alert('请先选择选型配置')
                return
            }
            if (type == 1) {
                this.dynamicForm.hardWareData.push({
                    resConfigId: '',
                    applyScene: '',
                    describe: '',
                    price: 0,
                    num: 1
                })
            } else if (type == 2) {
                this.dynamicForm.softWareData.push({
                    type: '',
                    name: '',
                    version: ''
                })
            }
        },
        clearSelectHardWare(row) {
            this.$set(row, 'applyScene', '')
            this.$set(row, 'describe', '')
            this.$set(row, 'price', 0)
            this.$set(row, 'num', 0)

            this.countHardWarePirce()
            this.countCardAll()
        },
        clearSelectSoftWare(row, type) {
            if (type == '1' || type == '2') {
                if (type == '1') {
                    this.$set(row, 'name', '')
                }
                this.$set(row, 'version', '')
                this.$set(row, 'price', 0)
                this.$set(row, 'countModel', '')

                this.countSoftWarePrice()
                this.countCardAll()
            }
        },
        countSoftWarePrice() {
            //计算所有的软件版本列表的价格
            let softWareTotalPrice = 0
            if (this.dynamicForm.softWareData.length > 0) {
                this.dynamicForm.softWareData.forEach((item) => {
                    softWareTotalPrice += this.calculateByCountModel(item.countModel, item.price)
                })
            }
            this.dynamicForm.softWareTotalPrice = parseFloat(softWareTotalPrice)
        },
        countHardWarePirce() {
            //计算所有的配件列表的价格
            let hardWareTotalPrice = 0
            if (this.dynamicForm.hardWareData.length > 0) {
                this.dynamicForm.hardWareData.forEach((item, softNo) => {
                    let price = 0
                    let num = 0
                    if (item.price != undefined && item.price != '') {
                        price = item.price
                    }
                    if (item.num != undefined && item.num != '') {
                        num = item.num
                    }
                    console.log('第' + softNo + '行的单价为：' + price + ',数量为：' + num)
                    hardWareTotalPrice = calculate.numAdd(hardWareTotalPrice, calculate.accMul(price, num))
                })
            }
            console.log(hardWareTotalPrice)
            this.dynamicForm.hardWareTotalPrice = hardWareTotalPrice
            this.countCardAll() //计算card的总价
        },
        getSoftWarePrice(row) {
            let softWareDataOne = this.softWareRelaData.filter((item) => {
                return item.TYPE != '7' && item.SOFTNAME == row.name && item.SOFTVERSION == row.version
            })
            if (softWareDataOne != null && softWareDataOne.length > 0) {
                row.price = softWareDataOne[0].PRICE
                row.countModel = softWareDataOne[0].BILLINGMODE
                row.softId = softWareDataOne[0].ID
                row.softCategory = softWareDataOne[0].TYPE
            } else {
                row.price = 0
                row.countModel = -1
                row.softId = ''
                row.softCategory = ''
            }
            this.countSoftWarePrice()
            this.countCardAll() //计算card的总价
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
        showRowRelaData(row) {
            if (row.resConfigId != '' && row.resConfigId != undefined && row.describe != '' && row.describe != undefined) {
                let data = this.hardWareRelaData.filter((item) => {
                    return item.DICTID == row.resConfigId && item.DESCRIBE == row.describe
                })
                row.price = data[0].PRICE
                row.applyScene = data[0].APPLYSCENE
                row.num = 1
                row.partId = data[0].ID
                row.partCategory = data[0].CATEGORY
            } else {
                row.price = ''
                row.applyScene = ''
                row.num = 0
                row.partId = ''
                row.partCategory = ''
            }
            this.countHardWarePirce()
        },
        countHardWarePirce() {
            //计算所有的配件列表的价格
            let hardWareTotalPrice = 0
            if (this.dynamicForm.hardWareData.length > 0) {
                this.dynamicForm.hardWareData.forEach((item, softNo) => {
                    let price = 0
                    let num = 0
                    if (item.price != undefined && item.price != '') {
                        price = item.price
                    }
                    if (item.num != undefined && item.num != '') {
                        num = item.num
                    }
                    console.log('第' + softNo + '行的单价为：' + price + ',数量为：' + num)
                    hardWareTotalPrice = calculate.numAdd(hardWareTotalPrice, calculate.accMul(price, num))
                })
            }
            console.log(hardWareTotalPrice)
            this.dynamicForm.hardWareTotalPrice = hardWareTotalPrice
            this.countCardAll() //计算card的总价
        },
        countCardAll() {
            let otherPrice = this.countOtherPrice()
            //计算单个卡片的总价
            this.dynamicForm.price =
                parseFloat(this.dynamicForm.hardWareTotalPrice == undefined ? 0 : this.dynamicForm.hardWareTotalPrice) + //配件总价
                parseFloat(this.dynamicForm.softWareTotalPrice == undefined ? 0 : this.dynamicForm.softWareTotalPrice) + //软件总价
                parseFloat(this.dynamicForm.osTotalPrice == undefined ? 0 : this.dynamicForm.osTotalPrice) + //操作系统总价格
                parseFloat(this.dynamicForm.comboTotalPrice == undefined ? 0 : this.dynamicForm.comboTotalPrice) + //套餐价格
                parseFloat(this.dynamicForm.nasTotalPrice == undefined ? 0 : this.dynamicForm.nasTotalPrice) + //nas存储总价格
                parseFloat(this.dynamicForm.dbTotalPrice == undefined ? 0 : this.dynamicForm.dbTotalPrice) + //数据库容量总价格
                parseFloat(this.dynamicForm.sanTotalPrice == undefined ? 0 : this.dynamicForm.sanTotalPrice) + //san存储总价格
                parseFloat(otherPrice) //虚拟机配件加了CPU产生的额外价格
        },
        countOtherPrice() {
            //计算由于虚拟机配件中增加了CPU引发的额外的软件的钱
            let otherPrice = 0
            let cpuNum = 0
            //1.计算出增加的cpu数量
            if (this.dynamicForm.hardWareData.length > 0) {
                this.dynamicForm.hardWareData.forEach((item) => {
                    if (item.resConfigId == 1) {
                        let everyNum = 0
                        if (item.num != undefined && item.num != '') {
                            everyNum = item.num
                        }
                        cpuNum = calculate.numAdd(cpuNum, parseFloat(everyNum))
                    }
                })
            }
            if (cpuNum != 0) {
                //2.计算操作系统软件
                if (this.dynamicForm.osCountModel == 1 || this.dynamicForm.osCountModel == 2) {
                    var price = this.dynamicForm.osPrice
                    otherPrice = calculate.numAdd(otherPrice, parseFloat(calculate.accMul(cpuNum, price)))
                    console.log('操作系统单价:' + price + '，操作系统额外增加的钱:' + otherPrice)
                }
                //3.计算软件列表
                if (this.dynamicForm.softWareData.length > 0) {
                    this.dynamicForm.softWareData.forEach((item) => {
                        if (item.countModel == 1 || item.countModel == 2) {
                            otherPrice = calculate.numAdd(otherPrice, parseFloat(calculate.accMul(cpuNum, item.price)))
                        }
                    })
                }
            }
            console.log('额外增加的CPU数量:' + cpuNum + ',额外增加的钱:' + otherPrice)
            return otherPrice
        },
        validateForm() {
            let result = []
            this.$refs['form'].validate((valid) => {
                if (!valid) {
                    result.push(false)
                } else {
                    result.push(true)
                }
            })
            result.push(validateDynamicForm)
            var totalResult = true
            result.forEach((item) => {
                totalResult = item && totalResult
            })
            return totalResult
        },
        validateDynamicForm() {
            let result = []
            this.$refs['dynamicForm'].validate((valid) => {
                if (!valid) {
                    result.push(false)
                } else {
                    result.push(true)
                }
            })
            var totalResult = true
            result.forEach((item) => {
                totalResult = item && totalResult
            })
            return totalResult
        },
        //重置校验结果
        clearValidateForm() {
            this.$refs['form'].clearValidate()
            this.$refs['dynamicForm'].clearValidate()
        },
        putIntoShop() {
            let formResult = true
            //对应用名称进行校验
            this.$refs['form'].validateField('appNo', (valid) => {
                //此处valid返回的时rules里面的message,故已经输入时则检验不到
                if (valid) {
                    formResult = false
                }
            })
            //校验当前配置单的必填
            let result = this.validateDynamicForm()
            if (!result || !formResult) {
                this.$alert('请确认必输字段')
                return
            }
            this.dynamicForm.softId = this.menuData.length
            this.form.dynamicForm = this.dynamicForm
            putIntoShop(this.form).then((res) => {
                if (res.data.status != 200) {
                    this.$message.error(res.data.message)
                    return false
                } else {
                    this.form.id = res.data.message
                    this.$nextTick(() => {
                        //清空表单
                        this.clearDynamicForm()
                        //清除校验结果
                        this.clearValidateForm()
                        //刷新菜单列表
                        this.refreshMenu(res.data.message)
                    })
                }
            })
        },
        // 补录编号选择
        chooseAdditional() {
            this.$nextTick(() => {
                this.$refs.additionalNoList.toSelect()
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
        uploadFile(file) {},
        valiIsShare(type) {
            if (this.menuData.length == 0) {
                if (this.dynamicForm.isNas == 2 || this.dynamicForm.isStorage == 2) {
                    return false
                }
            }
            let shareNum = 0
            let yesNum = 0
            this.menuData.forEach((item) => {
                if (this.dynamicForm.id != '' && this.dynamicForm.id != undefined) {
                    if (item.id != this.dynamicForm.id) {
                        if (type == 'NAS') {
                            if (item.isNas == 1) {
                                yesNum = calculate.numAdd(yesNum, 1)
                            }
                        } else {
                            if (item.isStorage == 1) {
                                yesNum = calculate.numAdd(yesNum, 1)
                            }
                        }
                    }
                } else {
                    if (type == 'NAS') {
                        if (item.isNas == 1) {
                            yesNum = calculate.numAdd(yesNum, 1)
                        }
                    } else {
                        if (item.isStorage == 1) {
                            yesNum = calculate.numAdd(yesNum, 1)
                        }
                    }
                }
            })
            if (yesNum == 0 && (this.dynamicForm.isNas == 2 || this.dynamicForm.isStorage == 2)) {
                return false
            } else {
                return true
            }
        },
        //购物车处理选中事件
        dealMenu(row) {
            let param = {}
            param.id = row.id
            let data = common.paramsFormat(param)
            showMenuItem(data).then((res) => {
                if (res.data.status != 200) {
                    this.$message.error(res.data.message)
                    return false
                } else {
                    this.dynamicForm = res.data.results
                    if (this.viewFlag) {
                        this.clearOutOfDateDate()
                    }
                }
            })
        },
        //复制当前配置
        copyCurrent() {
            this.dynamicForm.id = ''
            this.$message.success('复制当前数据成功')
        },
        //删除当前配置
        deleteCurrent(id) {
            if (id == '' || id == undefined) {
                this.$alert('请先选择删除的数据！')
            }
            this.$confirm('此操作将删除当前数据，确定继续吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then((res) => {
                let param = { id: id }
                let data = common.paramsFormat(param)
                deleteItemByItemId(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$message.error('删除数据失败')
                        return false
                    } else {
                        this.$message.success('删除当前数据成功')
                        //清空表单
                        this.clearDynamicForm()
                        //刷新菜单
                        this.refreshMenu(this.form.id)
                        //清除校验结果
                        this.clearValidateForm()
                    }
                })
            })
        },
        //新建一个新的申请订单
        newCurrent() {
            this.$confirm('此操作为清空当前表单，确定继续吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then((res) => {
                this.clearDynamicForm()
                //清除校验结果
                this.clearValidateForm()
            })
        },
        //刷新购物车组件
        refreshMenu(id) {
            let param = { id: id }
            let data = common.paramsFormat(param)
            refreshMenu(data).then((res) => {
                if (res.data.status != 200) {
                    this.$message.error('数据查询失败')
                    return false
                } else {
                    let amount = 0
                    let menuData = res.data.results
                    if (menuData.length > 0) {
                        menuData.forEach((item) => {
                            amount = calculate.numAdd(amount, parseFloat(item.price == undefined || item.price == '' ? 0 : item.price))
                            let nodeTypeOne = this.nodeTypeData.filter((item1) => {
                                return item1.dictid == item.nodeType
                            })
                            let modelTypeOne = this.preSetConfigData.filter((item2) => {
                                return item2.DICTID == item.modelType && item2.NODETYPE == item.nodeType
                            })
                            item.menuName = nodeTypeOne[0].dictname + '-' + modelTypeOne[0].DICTNAME + '-' + item.nodeName
                        })
                    }
                    this.menuData = menuData
                    this.form.amount = amount
                }
            })
        },
        //清除表单中的值
        clearDynamicForm() {
            this.dynamicForm = {
                softId: 0,
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
                sanId: '',
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
        },
        //提购物车中的订单，发起流程
        button_submit() {
            if (this.menuData.length == 0) {
                this.$alert('至少加入一条数据进入资源列表', { type: 'error' })
                return
            }
            //校验是否存在作废数据
            let param = { id: this.form.id }
            let data = common.paramsFormat(param)
            validOutofDateData(data).then((res) => {
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
                    this.$confirm('此操作仅提交资源列表中数据，确定继续吗?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then((res) => {
                        this.$refs['form'].validate((valid) => {
                            if (valid) {
                                const loading = this.$loading({
                                    lock: true,
                                    text: 'Loading',
                                    spinner: 'el-icon-loading',
                                    background: 'rgba(0, 0, 0, 0.7)'
                                })
                                //保存RES_ADDED主表数据
                                submitResourceAdded(this.form).then((res) => {
                                    loading.close()
                                    if (res.data.status == 200) {
                                        if (res.data.results.indexOf('已结束') == -1) {
                                            let message = common.getFlowMessge(res.data.results)
                                            console.log('message', message)
                                            let messageArr = message.split(',')
                                            this.$nextTick(() => {
                                                this.messageNotify(messageArr)
                                                this.$parent.packageInit()
                                                this.flDialog = false
                                            })
                                        }
                                    } else {
                                        this.$alert('提交资源申请失败', { type: 'error' })
                                    }
                                })
                            } else {
                                this.$alert('请输入必填项')
                            }
                        })
                    })
                }
            })
        },
        button_save() {
            saveForm(this.form).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert('保存数据失败', { type: 'error' })
                    return false
                } else {
                    this.$message.success('保存数据成功！')
                }
            })
        },
        //流程成功提示信息
        messageNotify(messageVal) {
            if (messageVal.length > 3) {
                messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(')')[0] + ')'
            }
            let messageSec = messageVal.slice(2).join('，')
            console.log('提示信息', messageSec, messageVal)
            this.$alert(messageVal[1] + ' ' + messageSec, { type: 'success' })
        },
        //下载提示文本
        errorText(errorMessage) {
            const element = document.createElement('a')
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(errorMessage))
            element.setAttribute('download', '错误详情')
            element.style.display = 'none'
            element.click()
        },
        countTotal() {
            count = 0
            this.menuData.forEach((item) => {
                count = calculate.numAdd(count, item.price)
            })
            return count
        },
        //清空已经作废的数据，反显为空
        clearOutOfDateDate() {
            //1.校验操作系统
            let osOne = this.osData.filter((item2) => {
                return this.dynamicForm.os == item2.ID
            })
            if (osOne.length == 0 || osOne == null) {
                this.dynamicForm.os = ''
            }
            //2.校验NAS
            if (item.isNas == 1) {
                let nasTypeOne = this.nasTypeData.filter((item3) => {
                    return item.mountType == item3.ID
                })
                if (nasTypeOne.length == 0 || nasTypeOne == null) {
                    item.mountType = ''
                }
            }
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



<!-- 资源新增，编辑，查看显示页面 -->
<template>
    <div>
        <el-form ref="form" inline label-width="120px">
            <!-- 隐藏域 -->
            <el-input v-model="form.id" class="hidden"></el-input> <!-- 资源申请ID -->
            <el-input v-model="form.priceReqNo" class="hidden"></el-input> <!-- 价格申请单号 -->
            <el-input v-model="form.appNo" class="hidden"></el-input> <!-- 应用编号 -->
            <el-input v-model="form.appName" class="hidden"></el-input> <!-- 应用名称 -->
            <el-input v-model="form.projectNo" class="hidden"></el-input> <!-- 项目编号 -->
            <el-input v-model="form.projectName" class="hidden"></el-input> <!-- 项目名称 -->
            <el-input v-model="form.sysOperLevel" class="hidden"></el-input> <!-- 系统运维等级 -->
            <el-input v-model="form.isFundTrans" class="hidden"></el-input> <!-- 是否涉及资金交易 -->
            <el-input v-model="form.busiDeptName" class="hidden"></el-input> <!-- 所属业务部名称 -->
            <el-input v-model="form.techDeptName" class="hidden"></el-input> <!-- 所属技术部名称 -->
            <el-input v-model="form.busiDeptId" class="hidden"></el-input> <!-- 所属业务部ID -->
            <el-input v-model="form.techDeptId" class="hidden"></el-input> <!-- 所属技术部ID -->
            <el-input v-model="form.ciid" class="hidden"></el-input> <!-- CMDB系统唯一识别编号 -->
            <el-input v-model="form.docmId" class="hidden"></el-input><!-- 文件上传唯一标识 -->
            <el-row>
                <el-col :span="6">
                    <el-form-item label="应用名称：">
                        <el-input v-model="form.appName" disabled></el-input>
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
                            <el-button size="small" type="primary">
                                <i class="el-icon-upload2"></i>上传文件
                            </el-button>
                        </el-upload>
                        <div class="upload_scss" v-else>
                            <div @click="jlDownliad(form.docmId)">下载</div>
                            <el-upload class="upload-demo againUpLoad" action="Fake Action" :file-list="fileList" :show-file-list="false" :http-request="uploadFile" :on-change="changeFile">
                                重传
                            </el-upload>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row class="row-box">
                <el-col :span="24">
                    <el-form-item v-for=" (dynamicForm, index) in form.dynamicForms" :key="dynamicForm.key">
                        <el-tooltip class="item" effect="dark" content="新增" placement="bottom">
                            <el-button icon="el-icon-plus" type="primary" size="mini" @click="addGroup()" circle></el-button>
                        </el-tooltip>
                        <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
                            <el-button icon="el-icon-minus" type="danger" size="mini" v-if="form.dynamicForms.length>1" @click="delGroup(index)" circle></el-button>
                        </el-tooltip>
                        <el-tooltip class="item" effect="dark" content="复制" placement="bottom">
                            <el-button icon="el-icon-edit" type="info" size="mini" @click="copyGroup(index)" circle></el-button>
                        </el-tooltip>
                        <!-- 动态表格_Begin -->
                        <el-card body-style="background-color:#f0f2f5" class="el-card">
                            <el-card>
                                <el-badge :value="index+1" class="item"></el-badge>
                                <el-form :key="'dynamicForm'+index" :v-model="'dynamicForm'+index" :model="dynamicForm" :ref="'dynamicForm'+index" inline label-width="150px">
                                    <!-- 隐藏域 -->
                                    <el-input v-model="dynamicForm.modelConfig" class="hidden"></el-input> <!-- 套餐ID -->
                                    <el-input v-model="dynamicForm.networkArea" class="hidden"></el-input> <!-- 网络区域ID -->
                                    <el-input v-model="dynamicForm.comboTotalPrice" class="hidden"></el-input> <!-- 套餐价格 -->
                                    <el-input v-model="dynamicForm.cpuNum" class="hidden"></el-input> <!-- 套餐CPU数 -->
                                    <el-input v-model="dynamicForm.cpuCode" class="hidden"></el-input> <!-- 套餐CPU核数 -->
                                    <el-input v-model="dynamicForm.osPrice" class="hidden"></el-input> <!-- 操作系统单价 -->
                                    <el-input v-model="dynamicForm.osCountModel" class="hidden"></el-input> <!-- 操作系统的计费模式 -->
                                    <el-input v-model="dynamicForm.osTotalPrice" class="hidden"></el-input> <!-- 操作系统的总价 -->
                                    <el-input v-model="dynamicForm.nasPrice" class="hidden"></el-input> <!-- NAS存储单价 -->
                                    <el-input v-model="dynamicForm.hardWareTotalPrice" class="hidden"></el-input> <!-- 配件总价 -->
                                    <el-input v-model="dynamicForm.softWareTotalPrice" class="hidden"></el-input> <!-- 软件总价 -->
                                    <el-input v-model="dynamicForm.nasTotalPrice" class="hidden"></el-input> <!-- NAS存储总价格 -->
                                    <el-input v-model="dynamicForm.dbPrice" class="hidden"></el-input> <!-- 数据库存储单价 -->
                                    <el-input v-model="dynamicForm.dbTotalPrice" class="hidden"></el-input> <!-- 数据库存储总价 -->
                                    <el-input v-model="dynamicForm.sanTotalPrice" class="hidden"></el-input> <!-- san存储总价格 -->
                                    <el-input v-model="dynamicForm.sanId" class="hidden"></el-input><!-- san存储的ID -->
                                    <el-input v-model="dynamicForm.sanPrice" class="hidden"></el-input> <!-- san存储的单价 -->
                                    <el-input v-model="dynamicForm.sanRatio" class="hidden"></el-input> <!-- san比例 -->

                                    <el-input v-model="dynamicForm.sanSpecialId" class="hidden"></el-input> <!-- SAN特殊套餐/归档数据ID -->
                                    <el-input v-model="dynamicForm.sanSpecialPrice" class="hidden"></el-input> <!-- SAN特殊套餐/归档数据价格 -->
                                    <el-input v-model="dynamicForm.sanSpecialRatio" class="hidden"></el-input> <!-- SAN特殊套餐/归档数据比例 -->
                                    <el-input v-model="dynamicForm.sanSpecialSize" class="hidden"></el-input> <!-- SAN特殊套餐/归档数据容量 -->
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="节点类型：" prop="nodeType" :rules="{required: true, message: ' '}">
                                                <el-select placeholder="请选择" v-model="dynamicForm.nodeType" clearable size="mini" @change="clearOther(index,'nodeType')">
                                                    <el-option v-for="item in nodeTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="选型类型：" prop="modelType" :rules="{required: true, message: ' '}">
                                                <el-select ref="modelType" placeholder="请选择" v-model="dynamicForm.modelType" clearable size="mini" @change="hardWareClear(index)">
                                                    <el-option v-for="item in modelTypeData = preSetConfigData.filter((item) => { return item.NODETYPE == dynamicForm.nodeType })" :key="item.DICTID+''" :label="item.DICTNAME" :value="item.DICTID+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="选型配置：" prop="modelConfigName" :rules="{required: true, message: ' '}">
                                                <el-input v-model="dynamicForm.modelConfigName" size="mini" readonly style="width:82%" placeholder="点击选择套餐->">
                                                    <el-button slot="append" icon="el-icon-more" @click="chooseCombo(dynamicForm,index)"></el-button>
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
                                                    <el-button slot="append" icon="el-icon-more" @click="chooseNetWorkArea(index)"></el-button>
                                                </el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="操作系统：" prop="os" :rules="dynamicForm.modelConfigName==''?{}:{required: true, message: ' '}">
                                                <el-select placeholder="请选择" v-model="dynamicForm.os" clearable size="mini" @change="getOsPrice(index)" :disabled="dynamicForm.modelConfigName==''">
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
                                                <el-input-number v-model.number="dynamicForm.dbSize" clearable size="mini" type="number" :min=0 :disabled="dynamicForm.nodeType!=3 || dynamicForm.modelType!=3 || dynamicForm.modelConfigName==''" style="width:87%" @change="countDbPrice(index)"></el-input-number>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="是否挂载NAS：" prop="isNas" :rules="{required: true, message: ' '}">
                                                <el-select placeholder="请选择" v-model="dynamicForm.isNas" clearable size="mini" @change="changeValiRules(index,'nas')">
                                                    <el-option v-for="item in mountData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="挂载NAS类型：" prop="mountType" :rules="dynamicForm.isNas==1?{ required: true, message: ' '}:{}">
                                                <el-select placeholder="请选择" v-model="dynamicForm.mountType" clearable size="mini" :disabled="dynamicForm.isNas!='1'" @change="getNasPrice(index)">
                                                    <el-option v-for="item in nasTypeData" :key="item.ID" :label="item.APPLYSCENE" :value="item.ID"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="挂载NAS大小(G)：" prop="nasSize" :rules="dynamicForm.isNas==1?[{ required: true, message: ' '},{ type: 'number', message: '只能填写数字' }]:{ type: 'number', message: ' ' }">
                                                <el-input-number v-model.number="dynamicForm.nasSize" clearable size="mini" type="number" :min=0 :disabled="dynamicForm.isNas!=1 || dynamicForm.mountType==''" style="width:87%" @change="countNasPrice(index)"></el-input-number>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>

                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="是否挂载SAN：" prop="isStorage" :rules="form.appNo=='' ||( dynamicForm.nodeType==3 && (dynamicForm.modelType==2 || dynamicForm.modelType==3)) || dynamicForm.nodeType=='' || dynamicForm.modelType==''?{}:{required: true, message: ' '}">
                                                <el-select placeholder="请选择" v-model="dynamicForm.isStorage" clearable size="mini" @change="changeValiRules(index,'san')" :disabled="form.appNo=='' ||( dynamicForm.nodeType==3 && (dynamicForm.modelType==2 || dynamicForm.modelType==3)) || dynamicForm.nodeType=='' || dynamicForm.modelType==''">
                                                    <el-option v-for="item in mountData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="挂载SAN大小(G)：" prop="storageSize" size="mini" :rules="dynamicForm.isStorage==1?[{ required: true, message: ' '},{ type: 'number', message: '只能填写数字' }]:{ type: 'number', message: ' ' }">
                                                <el-input-number v-model.number="dynamicForm.storageSize" clearable size="mini" type="number" :min=0 :disabled="dynamicForm.isStorage!='1'" style="width:87%" @change="countSanPrice(index)"></el-input-number>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </el-form>
                            </el-card>
                            <div style="margin-top:10px"></div>
                            <el-card v-if="dynamicForm.modelConfigName!='' && ((dynamicForm.modelType!=3 && dynamicForm.modelType!=4 && dynamicForm.nodeType!=4) || (dynamicForm.nodeType==4 && dynamicForm.modelType==1))">
                                <label>配件信息：</label>
                                <el-form :key="'hardWareData'+index" :v-model="'hardWareData'+index" :model="dynamicForm" :ref="'hardWareData'+index">
                                    <el-table :data="dynamicForm.hardWareData" border stripe highlight-current-row>
                                        <el-table-column type="index" align="center" width="120">
                                            <template slot-scope="scope" slot="header">
                                                <el-button type="primary" icon="el-icon-circle-plus" size="mini" @click="addRow(scope.row,1,index)">增加</el-button>
                                            </template>
                                            <template slot-scope="scope">
                                                <el-button type="danger" icon="el-icon-remove" size="mini" @click="delRow(scope,1,index)">刪除</el-button>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="resConfigId" label="配件分类" align="center" width='180'>
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`hardWareData.${scope.$index}.resConfigId`" :rules="{required: true, message: ' '}">
                                                    <el-select placeholder="请选择" v-model="scope.row.resConfigId" clearable size="mini" @change="clearSelectHardWare(scope.row,index)">
                                                        <el-option v-for="item in resConfigData = (dynamicForm.modelType==1?resConfigPhyData:resConfigVmData)" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="describe" label="配置" align="center" width='180'>
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`hardWareData.${scope.$index}.describe`" :rules="{required: true, message: ' '}">
                                                    <el-select placeholder="请选择" v-model="scope.row.describe" clearable @change="showRowRelaData(scope.row,index)" size="mini">
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
                                                <el-input-number v-model="scope.row.num" type="number" size="mini" :min=1 v-if="scope.row.price" @change="countHardWarePirce(index)"></el-input-number>
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
                                </el-form>
                            </el-card>
                            <div style="margin-top:10px"></div>
                            <el-card v-if="dynamicForm.modelConfigName!=''">
                                <label>软件版本：</label>
                                <el-form :key="'softWareData'+index" :v-model="'softWareData'+index" :model="dynamicForm" :ref="'softWareData'+index">
                                    <el-table :data="dynamicForm.softWareData" border stripe style="width:100%" highlight-current-row>
                                        <el-table-column type="index" align="center" width="120">
                                            <template slot-scope="scope" slot="header">
                                                <el-button type="primary" icon="el-icon-circle-plus" size="mini" @click="addRow(scope.row,2,index)">增加</el-button>
                                            </template>
                                            <template slot-scope="scope">
                                                <el-button type="danger" icon="el-icon-remove" size="mini" @click="delRow(scope,2,index)">刪除</el-button>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="type" label="软件类型" align="center" width='180'>
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`softWareData.${scope.$index}.type`" :rules="{required: true, message: ' '}">
                                                    <el-select placeholder="请选择" v-model="scope.row.type" clearable @change="clearSelectSoftWare(scope.row,'1',index)" size="mini">
                                                        <el-option v-for="item in softTypeData" :key="item.dictid+''" :label="item.dictname" :value="item.dictid+''"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="name" label="软件名称" align="center" width='180'>
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`softWareData.${scope.$index}.name`" :rules="(scope.row.type==1 || scope.row.type==2)?{required: true, message: ' '}:{}">
                                                    <el-select placeholder="请选择" v-model="scope.row.name" clearable v-if="scope.row.type==1" @change="clearSelectSoftWare(scope.row,'2',index)" size="mini">
                                                        <el-option v-for="(item,i) in softWareNameData = softWareRelaData.filter((item)=>{return item.TYPE != '7'})" :key="i" :label="item.SOFTNAME" :value="item.SOFTNAME"></el-option>
                                                    </el-select>
                                                    <el-input v-model="scope.row.name" v-if="scope.row.type==2" size="mini"></el-input>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="version" label="软件版本" align="center" width='180'>
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`softWareData.${scope.$index}.version`" :rules="(scope.row.type==1 || scope.row.type==2)?{required: true, message: ' '}:{}">
                                                    <el-select placeholder="请选择" v-model="scope.row.version" clearable v-if="scope.row.type==1" size="mini" @change="getSoftWarePrice(scope.row,index)">
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
            <div style="margin-top:10px" align="right">
                <label>总价：{{
                        form.amount
                    }}元</label>
            </div>
        </el-form>
        <div class="czLog app-container">
            <flow-logs :processInstId="processInstId" ref="log"></flow-logs>
        </div>
        <div slot="footer" class="dialog-footer" align="center">
            <el-button @click="button_cancel" size="mini">取 消</el-button>
            <el-button type="primary" @click="reSubmit" size="mini">重新提交</el-button>
        </div>
        <!-- 子组件 -->
        <combo-choice-list ref="comboChoiceList"></combo-choice-list>
        <net-address-list ref="netAddressList"></net-address-list>
        <additional-no-list ref="additionalNoList"></additional-no-list>
    </div>
</template>
<script>
import { getDicEntry } from '@/api/eosDictEntry'
import relaProgramTree from '@/views/resource/added/relaProgramTree'
import { uploadExcel } from '@/api/integrated/wbms'
import flowLogs from '@/views/plugins/flowPlug/FlowLogs'
import { addResource, querySelectionConfig, queryHardWareRela, querySoftWareRela, queryStorageComboData, queryResAddedConfig, reSubmitWorkFlow, validOutofDateResubmit } from '@/api/resource/added/resourceManager'
import netAddressList from '@/views/resource/added/netAddressList.vue'
import comboChoiceList from '@/views/resource/added/comboChoiceList.vue'
import additionalNoList from '@/views/resource/added/additionalNoList'
import common from '@/api/common'
import calculate from '@/api/resource/added/common'
// 引入ntko
let ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min').ntkoBrowser

export default {
    components: { relaProgramTree, netAddressList, comboChoiceList, flowLogs, additionalNoList },
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
                dynamicForms: [
                    {
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
                ]
            },
            hardWareData: [{ resConfigId: '', applyScene: '', describe: '', price: 0, num: 0 }], //硬件列表数据
            softWareData: [{ type: '', name: '', version: '' }], //软件列表数据
            tempData: [],
            inputContent: this.input,
            chooseData: [],
            showDel: false,
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
            fileList: [] //上传文件列表
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {
        this.getPreSetData()
        //统一的数据准备方法
        this.edit(this.busId)
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
                    return item.ISCANCEL == 0
                })
            })
            //软件类型的关系表(7:操作系统软件 8:数据库软件 9:应用中间件 10:消息中间件)
            querySoftWareRela().then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.softWareRelaData = res.data.results.filter((item) => {
                    return item.ISCANCEL == 0
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
                    return item.ISCANCEL == 0
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
        delRow(scope, type, index) {
            if (type == 1) {
                this.form.dynamicForms[index].hardWareData.splice(scope.$index, 1)
                this.countHardWarePirce(index)
            } else if (type == 2) {
                this.form.dynamicForms[index].softWareData.splice(scope.$index, 1)
                this.countSoftWarePrice(index)
                this.countCardAll(index) //计算card的总价
            }
        },
        addRow(row, type, index) {
            if (this.form.dynamicForms[index].modelConfig == undefined || this.form.dynamicForms[index].modelConfig == '') {
                this.$alert('请先选择选型配置')
                return
            }
            if (type == 1) {
                this.form.dynamicForms[index].hardWareData.push({
                    resConfigId: '',
                    applyScene: '',
                    describe: '',
                    price: 0,
                    num: 1
                })
            } else if (type == 2) {
                this.form.dynamicForms[index].softWareData.push({
                    type: '',
                    name: '',
                    version: ''
                })
            }
        },
        showRowRelaData(row, index) {
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
                row.num = 1
                row.partId = ''
                row.partCategory = ''
            }
            this.countHardWarePirce(index)
        },
        clearSelectHardWare(row, index) {
            this.$set(row, 'applyScene', '')
            this.$set(row, 'describe', '')
            this.$set(row, 'price', 0)
            this.$set(row, 'num', 0)

            this.countHardWarePirce(index)
            this.countCardAll(index)
        },
        clearSelectSoftWare(row, type, index) {
            if (type == '1' || type == '2') {
                if (type == '1') {
                    this.$set(row, 'name', '')
                }
                this.$set(row, 'version', '')
                this.$set(row, 'price', 0)
                this.$set(row, 'countModel', '')

                this.countSoftWarePrice(index)
                this.countCardAll(index)
            }
        },
        //获取操作系统价格,计费模式
        getOsPrice(index) {
            let osDataOne = this.osData.filter((item) => {
                return item.ID == this.form.dynamicForms[index].os
            })
            if (osDataOne.length == 1 && osDataOne != null) {
                this.form.dynamicForms[index].osPrice = osDataOne[0].PRICE //单格
                this.form.dynamicForms[index].osCountModel = osDataOne[0].BILLINGMODE //计费模式
                this.form.dynamicForms[index].osTotalPrice = this.calculateByCountModel(osDataOne[0].BILLINGMODE, osDataOne[0].PRICE, index)
            } else {
                this.form.dynamicForms[index].osPrice = 0
                this.form.dynamicForms[index].osCountModel = '-1'
                this.form.dynamicForms[index].osTotalPrice = 0
            }
            this.countCardAll(index)
        },
        //获取nas存储价格
        getNasPrice(index) {
            let nasDataOne = this.nasTypeData.filter((item) => {
                return item.ID == this.form.dynamicForms[index].mountType
            })
            if (nasDataOne.length == 1 && nasDataOne != null) {
                this.form.dynamicForms[index].nasPrice = nasDataOne[0].PRICE
            } else {
                this.form.dynamicForms[index].nasPrice = 0
            }
            this.countNasPrice(index)
            this.countCardAll(index)
        },
        changeValiRules(index, type) {
            if (type == 'nas') {
                if (!this.valiIsShare('NAS')) {
                    this.$alert('是否挂载NAS至少有一条数据选择是')
                    this.$set(this.form.dynamicForms[index], 'isNas', '')
                }
                this.$set(this.form.dynamicForms[index], 'mountType', '')
                this.$set(this.form.dynamicForms[index], 'nasSize', 0)

                this.countNasPrice(index)
                this.countCardAll(index)
            } else if (type == 'san') {
                if (!this.valiIsShare('SAN')) {
                    this.$alert('是否挂载SAN至少有一条数据选择是')
                    this.$set(this.form.dynamicForms[index], 'isStorage', '')
                }
                this.$set(this.form.dynamicForms[index], 'storageSize', 0)

                this.countSanPrice(index)
                this.countCardAll(index)
            }
        },
        edit(id) {
            this.loadDataEve()

            let param = {}
            param.id = id
            let data = common.paramsFormat(param)
            queryResAddedConfig(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                } else {
                    let form = res.data.results

                    //给项目赋值
                    this.$refs.relaProgramTree.clearValue(form.projectName)
                    //清空已经作废的数据，反显为空
                    this.form = form
                    this.clearOutOfDateDate()

                    //抵消由于前后端计算额外增加CPU后的算法不一致，导致了部分的钱重复计算的问题
                    //重新计算涉及计费模式的部分，重新统计总价
                    this.form.dynamicForms.forEach((item, index) => {
                        this.getOsPrice(index)
                        this.countSoftWarePrice(index)
                        this.countCardAll(index)
                    })
                }
            })
        },
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
                docmId: '',
                dynamicForms: [
                    {
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
                ]
            }
            this.$nextTick(() => {
                //给项目赋值
                this.$refs.relaProgramTree.clearValue('')
                //重置所有的校验
                this.clearValidateForm()
            })
        },
        addGroup(flag) {
            let index = this.form.dynamicForms.length
            this.form.dynamicForms.push({
                softId: index,
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
            })
            if (flag != 'sysTreeChange') {
                this.$message.success('新增成功！')
            }
        },
        delGroup(index) {
            this.form.dynamicForms.splice(index, 1)
            this.$message.success('删除成功！')
            //删除之后算费
            this.countAount()
        },
        copyGroup(index) {
            this.$confirm('是否复制当前所有的数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then((res) => {
                    // 使用JSON.parse(JSON.stringify())取消数据的双向绑定
                    let nowGroup = JSON.parse(JSON.stringify(this.form.dynamicForms[index]))
                    this.form.dynamicForms.push(nowGroup)
                    //复制之后算费
                    this.$message.success('复制成功！')
                    this.countAount()
                })
                .catch((error) => {})
        },
        button_cancel() {
            this.$router.push({
                path: 'dashboard'
            })
        },
        getChildrenData(args, flag, index) {
            console.log(args, flag)
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

                //改变时初始化所有的子项card
                this.form.dynamicForms.splice(0)
                this.addGroup('sysTreeChange')
            } else if (flag == 'proTree') {
                //关联项目树
                this.form.projectNo = args.id //项目ID
                this.form.projectName = args.text //项目名称
            } else if (flag == 'netList') {
                this.form.dynamicForms[index].networkArea = args.id
                this.form.dynamicForms[index].networkAreaName = args.netWorkArea
            } else if (flag == 'comboList') {
                //先清空
                this.clearOther(index, 'modelConfigName')
                //再赋值
                this.form.dynamicForms[index].modelConfig = args.id
                this.form.dynamicForms[index].comboTotalPrice = args.comboPrice
                this.form.dynamicForms[index].cpuNum = args.cpuNum
                this.form.dynamicForms[index].cpuCode = args.cpuCode
                this.form.dynamicForms[index].modelConfigName = args.describe
                this.form.dynamicForms[index].dbPrice = args.dbPrice

                //算钱
                this.countCardAll(index)
            } else if (flag == 'additionalList') {
                this.form.reNewProcessNo = args.id //补录编号
            }
        },
        getSoftWarePrice(row, index) {
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
            this.countSoftWarePrice(index)
            this.countCardAll(index) //计算card的总价
        },
        calculateByCountModel(countModel, price, index) {
            //根据计费模式计算价格
            let cpuNum = 0
            let cpuCode = 0
            if (this.form.dynamicForms[index].cpuNum != undefined || this.form.dynamicForms[index].cpuNum != '') {
                cpuNum = this.form.dynamicForms[index].cpuNum
            }
            if (this.form.dynamicForms[index].cpuCode != undefined || this.form.dynamicForms[index].cpuCode != '') {
                cpuCode = this.form.dynamicForms[index].cpuCode
            }
            console.log('cpu数' + cpuNum + ',cpu核数' + cpuCode + ',计算模式:' + countModel + ',单价:' + price)
            var totalCount = 0
            if (countModel == 1) {
                //按CPU数量
                totalCount = parseFloat(calculate.accMul(cpuNum, price))
                //totalCount = parseFloat(cpuNum * price)
            } else if (countModel == 2) {
                totalCount = parseFloat(calculate.accMul(cpuCode, price))
                //totalCount = parseFloat(cpuCode * price)
            } else if (countModel == 3) {
                totalCount = price
            }
            return totalCount
        },
        hardWareClear(index) {
            this.clearOther(index, 'modelType')
        },
        countSoftWarePrice(index) {
            //计算所有的软件版本列表的价格
            let softWareTotalPrice = 0
            if (this.form.dynamicForms[index].softWareData.length > 0) {
                this.form.dynamicForms[index].softWareData.forEach((item) => {
                    softWareTotalPrice += this.calculateByCountModel(item.countModel, item.price, index)
                })
            }
            this.form.dynamicForms[index].softWareTotalPrice = parseFloat(softWareTotalPrice)
        },
        countHardWarePirce(index) {
            //计算所有的配件列表的价格
            let hardWareTotalPrice = 0
            if (this.form.dynamicForms[index].hardWareData.length > 0) {
                this.form.dynamicForms[index].hardWareData.forEach((item, softNo) => {
                    let price = 0
                    let num = 0
                    if (item.price != undefined && item.price != '') {
                        price = item.price
                    }
                    if (item.num != undefined && item.num != '') {
                        num = item.num
                    } else {
                        if (item.describe != '' && item.describe != undefined) {
                            this.form.dynamicForms[index].hardWareData[softNo].num = 1
                            num = 1
                        }
                    }
                    console.log('第' + softNo + '行的单价为：' + price + ',数量为：' + num)
                    hardWareTotalPrice = calculate.numAdd(hardWareTotalPrice, calculate.accMul(price, num))
                    //hardWareTotalPrice += parseFloat(price * num)
                })
            }
            console.log(hardWareTotalPrice)
            this.form.dynamicForms[index].hardWareTotalPrice = hardWareTotalPrice
            this.countCardAll(index) //计算card的总价
        },
        countNasPrice(index) {
            //计算NAS的总价
            let nasSize = 0
            let nasPrice = 0
            if (this.form.dynamicForms[index].nasSize != undefined && this.form.dynamicForms[index].nasSize != '') {
                nasSize = this.form.dynamicForms[index].nasSize
            }
            if (this.form.dynamicForms[index].nasPrice != undefined && this.form.dynamicForms[index].nasPrice != '') {
                nasPrice = this.form.dynamicForms[index].nasPrice
            }
            //计算NAS的总价
            console.log('nas容量:' + nasSize + ',nas单价:' + nasPrice)
            //let totalPrice = (nasSize / 1000) * nasPrice
            this.form.dynamicForms[index].nasTotalPrice = parseFloat(calculate.accMul(nasPrice, calculate.accDiv(nasSize, 1000)))
            this.countCardAll(index)
        },
        countOtherPrice(index) {
            //计算由于虚拟机配件中增加了CPU引发的额外的软件的钱
            let otherPrice = 0
            let cpuNum = 0
            //1.计算出增加的cpu数量
            if (this.form.dynamicForms[index].hardWareData.length > 0) {
                this.form.dynamicForms[index].hardWareData.forEach((item) => {
                    if (item.resConfigId == 1) {
                        let everyNum = 0
                        if (item.num != undefined && item.num != '') {
                            everyNum = item.num
                        }
                        cpuNum = calculate.numAdd(cpuNum, parseFloat(everyNum))
                        //cpuNum += parseFloat(item.num)
                    }
                })
            }
            if (cpuNum != 0) {
                //2.计算操作系统软件
                if (this.form.dynamicForms[index].osCountModel == 1 || this.form.dynamicForms[index].osCountModel == 2) {
                    var price = this.form.dynamicForms[index].osPrice
                    otherPrice = calculate.numAdd(otherPrice, parseFloat(calculate.accMul(cpuNum, price)))
                    //otherPrice += parseFloat(cpuNum * price)
                    console.log('操作系统单价:' + price + '，操作系统额外增加的钱:' + otherPrice)
                }
                //3.计算软件列表
                if (this.form.dynamicForms[index].softWareData.length > 0) {
                    this.form.dynamicForms[index].softWareData.forEach((item) => {
                        if (item.countModel == 1 || item.countModel == 2) {
                            otherPrice = calculate.numAdd(otherPrice, parseFloat(calculate.accMul(cpuNum, item.price)))
                            //otherPrice += parseFloat(cpuNum * item.price)
                        }
                    })
                }
            }
            console.log('额外增加的CPU数量:' + cpuNum + ',额外增加的钱:' + otherPrice)
            return otherPrice
        },
        countSanPrice(index) {
            if (this.sanSpicalComboData != null && this.sanSpicalComboData.length > 0) {
                this.form.dynamicForms[index].sanSpecialId = this.sanSpicalComboData[0].ID
                this.form.dynamicForms[index].sanSpecialPrice = this.sanSpicalComboData[0].PRICE
                this.form.dynamicForms[index].sanSpecialRatio = this.sanSpicalComboData[0].RATIO
            } else {
                this.$alert('没有获取到对应的SAN归档数据', { type: 'error' })
                //没有查询到关联数据时，不允许挂载SAN
                this.$set(this.form.dynamicForms[index], 'storageSize', 0)
                this.form.dynamicForms[index].isStorage = ''
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
                if (this.form.dynamicForms[index].isStorage != 0) {
                    this.$alert('没有获取到对应的SAN套餐信息', { type: 'error' })
                    //没有查询到关联数据时，不允许挂载SAN
                    this.$set(this.form.dynamicForms[index], 'storageSize', 0)
                    this.form.dynamicForms[index].isStorage = ''
                }
                return
            }

            //计算san的总价
            let sanSize = 0
            if (this.form.dynamicForms[index].storageSize != undefined && this.form.dynamicForms[index].storageSize != '') {
                sanSize = this.form.dynamicForms[index].storageSize
            }

            this.form.dynamicForms[index].sanPrice = price
            this.form.dynamicForms[index].sanRatio = ratio
            this.form.dynamicForms[index].sanId = sanId

            //获取配比的san归档数据
            let specialPrice = this.form.dynamicForms[index].sanSpecialPrice
            let specialRatio = this.form.dynamicForms[index].sanSpecialRatio
            let sanSpecialSize = Math.ceil(calculate.accDiv(calculate.accMul(sanSize, specialRatio), ratio))
            //let sanSpecialSize = Math.ceil((sanSize * specialRatio) / ratio)
            this.form.dynamicForms[index].sanSpecialSize = sanSpecialSize
            let totalPrice = parseFloat(calculate.accMul(calculate.accDiv(sanSize, 1000), price))
            //let totalPrice = parseFloat((sanSize / 1000) * price)
            let specialTotalPrice = parseFloat(calculate.accMul(calculate.accDiv(sanSpecialSize, 1000), specialPrice))
            //let specialTotalPrice = parseFloat((sanSpecialSize / 1000) * specialPrice)

            console.log('san容量:' + sanSize + ',san单价:' + price + ',san比例:' + ratio + ',san总价' + totalPrice)
            console.log('归档san容量:' + sanSpecialSize + ',归档san单价:' + specialPrice + ',归档san比例:' + specialRatio + ',san总价' + specialTotalPrice)
            let sum = parseFloat(calculate.numAdd(totalPrice, specialTotalPrice))
            //let sum = parseFloat(totalPrice + specialTotalPrice)
            if ((sum + '').indexOf('.') > 0) {
                this.form.dynamicForms[index].sanTotalPrice = parseFloat(parseFloat(sum).toFixed(1))
            } else {
                this.form.dynamicForms[index].sanTotalPrice = sum
            }
            this.countCardAll(index)
        },
        countDbPrice(index) {
            //计算数据库容量总价
            let dbSize = 0
            let dbPrice = 0
            let totalPrice = 0
            if (this.form.dynamicForms[index].dbSize != undefined && this.form.dynamicForms[index].dbSize != '') {
                dbSize = this.form.dynamicForms[index].dbSize
            }
            if (this.form.dynamicForms[index].dbPrice != undefined && this.form.dynamicForms[index].dbPrice != '') {
                dbPrice = this.form.dynamicForms[index].dbPrice
            }
            totalPrice = parseFloat(calculate.accMul(calculate.accDiv(dbSize, 1000), dbPrice))
            //totalPrice = (dbSize / 1000) * dbPrice
            console.log('数据库容量为:' + dbSize + ',数据库单价:' + dbPrice + ',总费用为:' + totalPrice)
            this.form.dynamicForms[index].dbTotalPrice = totalPrice
            this.countCardAll(index)
        },
        countCardAll(index) {
            let otherPrice = this.countOtherPrice(index)
            //计算单个卡片的总价
            this.form.dynamicForms[index].price =
                parseFloat(this.form.dynamicForms[index].hardWareTotalPrice == undefined ? 0 : this.form.dynamicForms[index].hardWareTotalPrice) + //配件总价
                parseFloat(this.form.dynamicForms[index].softWareTotalPrice == undefined ? 0 : this.form.dynamicForms[index].softWareTotalPrice) + //软件总价
                parseFloat(this.form.dynamicForms[index].osTotalPrice == undefined ? 0 : this.form.dynamicForms[index].osTotalPrice) + //操作系统总价格
                parseFloat(this.form.dynamicForms[index].comboTotalPrice == undefined ? 0 : this.form.dynamicForms[index].comboTotalPrice) + //套餐价格
                parseFloat(this.form.dynamicForms[index].nasTotalPrice == undefined ? 0 : this.form.dynamicForms[index].nasTotalPrice) + //nas存储总价格
                parseFloat(this.form.dynamicForms[index].dbTotalPrice == undefined ? 0 : this.form.dynamicForms[index].dbTotalPrice) + //数据库容量总价格
                parseFloat(this.form.dynamicForms[index].sanTotalPrice == undefined ? 0 : this.form.dynamicForms[index].sanTotalPrice) + //san存储总价格
                parseFloat(otherPrice) //虚拟机配件加了CPU产生的额外价格

            //复制之后算费
            this.countAount()
        },
        countAount() {
            //计算整个页面上的价格
            let amount = 0
            this.form.dynamicForms.forEach((item) => {
                amount = calculate.numAdd(amount, parseFloat(item.price))
                //amount += parseFloat(item.price)
            })
            this.form.amount = amount
        },
        chooseCombo(dynamicForm, index) {
            //选择套餐
            this.$nextTick(() => {
                if (dynamicForm.nodeType == undefined || dynamicForm.nodeType == '') {
                    this.$alert('节点类型为空，无法选择套餐', { type: 'error' })
                    return
                }
                if (dynamicForm.modelType == undefined || dynamicForm.modelType == '') {
                    this.$alert('选型类型为空，无法选择套餐', { type: 'error' })
                    return
                }
                let comboTypeData = this.preSetConfigData.filter((item) => {
                    return item.DICTID == dynamicForm.modelType && item.NODETYPE == dynamicForm.nodeType
                })
                let comboType = comboTypeData[0].MODELCONFIGTYPE
                let nodeType = this.form.dynamicForms[index].nodeType
                this.$refs.comboChoiceList.toSelect(nodeType, comboType, index)
            })
        },
        chooseNetWorkArea(index) {
            //选择网络区域
            this.$nextTick(() => {
                this.$refs.netAddressList.toSelect(index)
            })
        },
        clearOther(index, type) {
            let nodeType = this.form.dynamicForms[index].nodeType
            let modelType = this.form.dynamicForms[index].modelType
            let modelConfigName = this.form.dynamicForms[index].modelConfig
            var softId = this.form.dynamicForms[index].softId
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
            console.log(this.form.dynamicForms[index])
            Object.keys(this.form.dynamicForms[index]).forEach((key) => (this.form.dynamicForms[index][key] = dynamicForm[key]))
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
        reSubmit() {
            var totalResult = this.validateForm()
            if (totalResult) {
                validOutofDateResubmit(this.form).then((res) => {
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
                        //添加工作流信息
                        this.form.workItemId = this.workItemId
                        this.form.processInstId = this.processInstId
                        this.form.applydesc = this.applydesc
                        this.form.activityDefID = this.activityDefID
                        this.form.sourceType = this.sourceType
                        //审批结论默认通过
                        this.form.conclusion = '01'
                        //审批意见默认为空
                        this.form.audit = ''
                        const loading = this.$loading({
                            lock: true,
                            text: 'Loading',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        })
                        reSubmitWorkFlow(this.form).then((res) => {
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
                    }
                })
            } else {
                this.$alert('请输入必填项')
            }
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
            this.form.dynamicForms.forEach((item, index) => {
                var every = 'dynamicForm' + index
                this.$refs[every][0].validate((valid) => {
                    if (!valid) {
                        result.push(false)
                    } else {
                        result.push(true)
                    }
                })
                if (this.form.dynamicForms[index].hardWareData.length > 0) {
                    var every = 'hardWareData' + index
                    console.log(this.$refs[every])
                    this.$refs[every][0].validate((valid) => {
                        if (!valid) {
                            result.push(false)
                        } else {
                            result.push(true)
                        }
                    })
                }

                if (this.form.dynamicForms[index].softWareData.length > 0) {
                    var every = 'softWareData' + index
                    this.$refs[every][0].validate((valid) => {
                        if (!valid) {
                            result.push(false)
                        } else {
                            result.push(true)
                        }
                    })
                }
            })
            var totalResult = true
            result.forEach((item) => {
                totalResult = item && totalResult
            })
            return totalResult
        },
        messageNotify(messageVal) {
            if (messageVal.length > 3) {
                messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(')')[0] + ')'
            }
            let messageSec = messageVal.slice(2).join('，')
            console.log('提示信息', messageSec, messageVal)
            this.$alert(messageVal[1] + ' ' + messageSec, { type: 'success' })
        },
        valiIsShare(type) {
            let shareNum = 0
            let yesNum = 0
            this.form.dynamicForms.forEach((item) => {
                if (type == 'NAS') {
                    if (item.isNas == 1) {
                        yesNum = calculate.numAdd(yesNum, 1)
                    } else if (item.isNas == 2) {
                        shareNum = calculate.numAdd(shareNum, 1)
                    }
                } else {
                    if (item.isStorage == 1) {
                        yesNum = calculate.numAdd(yesNum, 1)
                    } else if (item.isStorage == 2) {
                        shareNum = calculate.numAdd(shareNum, 1)
                    }
                }
            })
            if (shareNum > 0 && yesNum == 0) {
                return false
            } else {
                return true
            }
        },
        //重置校验结果
        clearValidateForm() {
            this.$refs['form'].clearValidate()
            this.form.dynamicForms.forEach((item, index) => {
                var every = 'dynamicForm' + index
                this.$refs[every][0].clearValidate()
            })
        },
        //下载提示文本
        errorText(errorMessage) {
            const element = document.createElement('a')
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(errorMessage))
            element.setAttribute('download', '错误详情')
            element.style.display = 'none'
            element.click()
        },
        //清空已经作废的数据，反显为空
        clearOutOfDateDate() {
            this.form.dynamicForms.forEach((item) => {
                //1.校验操作系统
                let osOne = this.osData.filter((item2) => {
                    return item.os == item2.ID
                })
                if (osOne.length == 0 || osOne == null) {
                    item.os = ''
                }
                if (item.isNas == 1) {
                    //2.校验NAS
                    let nasTypeOne = this.nasTypeData.filter((item3) => {
                        return item.mountType == item3.ID
                    })
                    if (nasTypeOne.length == 0 || nasTypeOne == null) {
                        item.mountType = ''
                    }
                }
            })
        }
    }
}
</script>
<style scoped>
.hidden {
    display: none;
}
.row-box {
    display: flex;
    flex-flow: wrap;
}
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
/* 解决el-row中的el-card显示异常问题 */
.row-box .el-card {
    min-width: 100%;
    height: 100%;
    border: 0px;
}
/* ----------------------------- */

/* ------- 页面自适应滚动 ------- */
.dialogFull .el-dialog__wrapper {
    overflow: hidden !important;
}
.dialogFull .el-dialog.is-fullscreen {
    overflow: hidden;
}
.dialogFull .el-dialog__body {
    overflow-y: auto !important;
    max-height: 88%;
}
/* ----------------------------- */
</style>


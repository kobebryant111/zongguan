<!-- 添加系统信息 -->
<template>
  <div class="container">
    <!-- 合同详情 -->
    <div class="nav">
      <p class="nav_name">合同详情</p>
      <div class="wrapper_right"  >
        <el-button :size="size" type="primary" class="topRightButton" @click="deleteContract" v-show='formContractBudget&&formContractBudget.pkId' v-if='isEdit!=0'
          v-hasFunc="['1231_LICENCE']">删 除</el-button>
        <!-- <el-button :size="size" class="lastTopRightButton" @click="back">返 回</el-button> -->
      </div>
    </div>

    <!-- 合同预算信息 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">合同预算信息</p>
          <p class="title_tips">!   表示该项非必填，但后续需完善</p>
        </div>
        <div v-if="isEdit!=0" class="wrapper_right">
          <el-button :disabled="!unchangeableContractBudget" :size="size" type="primary" class="topRightButton module_button" @click="edit('formContractBudget')"
            v-hasFunc="['12321_LICENCE']">编 辑</el-button>
          <el-button
            v-hasFunc="['12321_LICENCE']"
            v-if="!unchangeableContractBudget"
            :size="size"
            type="primary"
            class="topRightButton module_button"
            @click="cancel('formContractBudget')"
          >取 消</el-button>
          <el-button :disabled="unchangeableContractBudget" :size="size" class="topRightButton module_button" @click="save('formContractBudget')"
            v-hasFunc="['12322_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formContractBudget" :model="formContractBudget" :rules="rules" label-position="left" label-width="120px" >
          <el-form-item label="预算编号" prop="budgetCode">
            <div class="projectCodeInput">
              <el-input v-model="formContractBudget.budgetCode" :disabled="unchangeableContractBudget||!budgetCodeFlag ||formContractBudget.pkId"
              placeholder='格式为上会年份+季度+编号' class="basicInfo_input_box"/>
              <div class="projectCodeTips">预算编号保存后不可再修改！</div>
            </div>
          </el-form-item>
          <el-form-item label="预算名称" prop="budgetName">
            <el-input v-model="formContractBudget.budgetName" :disabled="unchangeableContractBudget" class="basicInfo_input_box"/>
          </el-form-item>
          <el-form-item label="预算金额(万元)" prop="budgetAmount">
            <el-input v-model="formContractBudget.budgetAmount" :disabled="unchangeableContractBudget" :min="0" type="number" class="basicInfo_input_box"/>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select :disabled="unchangeableContractBudget" v-model="formContractBudget.type" class="basicInfo_input_box" placeholder="请选择项目状态">
              <el-option label="系统开发" value="kaifa"/>
              <el-option label="系统测试" value="ceshi"/>
              <el-option label="运行维护" value="yunwei"/>
              <el-option label="设备采购" value="caigou"/>
              <el-option label="科技项目咨询" value="zixun"/>
              <el-option label="外购成品软件" value="waigou"/>
              <el-option label="其他" value="qita"/>
            </el-select>
          </el-form-item>
          <el-form-item label="供应商(计划)" prop="supplier">
            <el-input :disabled="unchangeableContractBudget" v-model="formContractBudget.supplier" class="basicInfo_input_box"/>
          </el-form-item>
          <el-form-item label="审批通过时间" prop="approvedDate">
            <el-date-picker :disabled="unchangeableContractBudget" v-model="formContractBudget.approvedDate" value-format="yyyy-MM-dd"   class="basicInfo_input_box" placeholder="请选择审批通过时间"/>
          </el-form-item>
          <el-form-item label="合同总人月" prop="totalManpower">
            <el-input v-model="formContractBudget.totalManpower" readonly :disabled="unchangeableContractBudget" class="basicInfo_input_box"/>
          </el-form-item>
          <el-form-item label="软件研发人月" prop="softwareManpower">
            <el-input-number v-model="formContractBudget.softwareManpower" :precision="2" :step="0.01" :min="0" placeholder="请输入软件研发人月" @change="calculateTotalManpower" :disabled="unchangeableContractBudget" class="basicInfo_input_box"/>
          </el-form-item>
          <el-form-item label="辅助研发人月" prop="assistManpower">
            <el-input-number v-model="formContractBudget.assistManpower"  :precision="2" :step="0.01" :min="0" placeholder="请输入辅助研发人月" @change="calculateTotalManpower" :disabled="unchangeableContractBudget" class="basicInfo_input_box"/>
          </el-form-item>
          <el-form-item label="风控研发人月" prop="riskManagementManpower">
            <el-input-number v-model="formContractBudget.riskManagementManpower" :precision="2" :step="0.01" :min="0" placeholder="请输入风控研发人月" @change="calculateTotalManpower" :disabled="unchangeableContractBudget" class="basicInfo_input_box"/>
          </el-form-item>
          <div class="tableApprovalMaterials table">
            <div class="upload_box">
              <p>审批资料上传</p>
              <el-upload
                :on-success="handleApprovalSuccess"
                :file-list="formContractBudget.tableApprovalMaterials"
                class="uploadApprovalMaterials"
                :show-file-list="false"
                :action="uploadUrl"
                :data='fileInfo'
                list-type="text"
                :before-upload="handleBeforeUpload"
              >
                <el-button slot="trigger" v-if="!unchangeableContractBudget" size="small" type="primary">上传文件</el-button>
              </el-upload>
            </div>
            <el-table
              :data="fileList"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="200"
              border
              style="width:750px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="fileName" label="文件名称" width="300"/>
              <!-- <el-table-column prop="createDate" label="上传时间" width="200"/> -->
              <el-table-column label="操作" >
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="showUpdateFileName(scope.row)" :disabled="unchangeableContractBudget"
                    v-hasFunc="['12323_LICENCE']">修改</el-button>
                  <el-button :disabled="unchangeableContractBudget" type="text" size="small" @click="deleteFile('10',scope.row,scope.$index)"
                    v-hasFunc="['12324_LICENCE']">删除</el-button>
                  <el-button :disabled="unchangeableContractBudget" type="text" size="small" @click="downloadFile('10',scope.row,scope.$index)"
                    v-hasFunc="['12325_LICENCE']">下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="space"/>
        </el-form>
      </div>
    </div>
    <!-- 研发项目关联 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">研发项目关联</p>
          <p class="title_tips">!   表示该项非必填，但后续需完善</p>
        </div>
        <div class="wrapper_right" v-if='isEdit!=0'>
          <el-button :disabled="!unchangeableProAssociation" :size="size" type="primary" class="topRightButton module_button" @click="edit('formProAssociation')"
            v-hasFunc="['12331_LICENCE']">编 辑</el-button>
            <el-button
            v-hasFunc="['12321_LICENCE']"
            v-if="!unchangeableProAssociation"
            :size="size"
            type="primary"
            class="topRightButton module_button"
            @click="cancel('formProAssociation')"
          >取 消</el-button>
          <el-button :disabled="unchangeableProAssociation" :size="size" class="topRightButton module_button" @click="save('formProAssociation')"
            v-hasFunc="['12332_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formProAssociation" :model="formContractBudget" :rules="rules" label-position="left" label-width="120px" >
          <el-form-item label="是否为研发项目" prop="isResearchProject">
            <el-radio :disabled="unchangeableProAssociation" v-model="formContractBudget.isResearchProject" label= "Y">是</el-radio>
            <el-radio :disabled="unchangeableProAssociation" v-model="formContractBudget.isResearchProject" label= "N">否</el-radio>
          </el-form-item>

          <div v-if='formContractBudget.isResearchProject=="Y"' class="tableProjectInside table">

            <div class="upload_box">
              <div class="upload_box">
                <p style="color: rgb(255,110,110)">*</p>
                <p>关联研发项目</p>
                <el-button v-if="!unchangeableProAssociation" :size="size" type="primary" class="topRightButton module_button" @click="showProjectList()">关 联</el-button>
              </div>
            </div>
            <el-table
              :data="relationProjectList"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="300"
              border
              style="width:700px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="projectCode" label="项目代码" width="150"/>
              <el-table-column prop="projectName" label="项目名称" width="150"/>
              <el-table-column prop="ratio" label="占比(%)" :formatter="formatRatio" width="150"/>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="showUpdateProject(scope.row)" :disabled="unchangeableProAssociation"
                  v-hasFunc="['12333_LICENCE']">修改</el-button>
                  <el-button type="text" size="small" @click="deleteProject(scope.row)" :disabled="unchangeableProAssociation"
                  v-hasFunc="['12334_LICENCE']">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-form ref="formProAssociation" :model="formContractBudget" :rules="rules" label-position="top" label-width="400px">
            <el-form-item label="科技项目关键技术点/创新点" prop="techInnoPoint">
              <div class="red_tip">
                <el-input :disabled="unchangeableProAssociation" v-model="formContractBudget.techInnoPoint" placeholder="请输入，限制1000字" type="textarea" show-word-limit maxlength="1000" class="techInnoPoint_input_box"/>
                <div v-if="!formContractBudget.techInnoPoint" class="tips">!</div>
              </div>
            </el-form-item>
          </el-form>
          <div class="space"/>
        </el-form>
      </div>
    </div>
    <!-- 合同及支付信息 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">合同及支付信息</p>
          <p class="title_tips">!   表示该项非必填，但后续需完善</p>
        </div>
        <div class="wrapper_right" v-if='isEdit!=0'>
          <el-button :disabled="!unchangeableContractPayInfo" :size="size" type="primary" class="topRightButton module_button" @click="edit('formContractPayInfo')"
            v-hasFunc="['12341_LICENCE']">编 辑</el-button>
          <el-button
            v-hasFunc="['12321_LICENCE']"
            v-if="!unchangeableContractPayInfo"
            :size="size"
            type="primary"
            class="topRightButton module_button"
            @click="cancel('formContractPayInfo')"
          >取 消</el-button>
          <el-button :disabled="unchangeableContractPayInfo" :size="size" class="topRightButton module_button" @click="save('formContractPayInfo')"
            v-hasFunc="['12342_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formContractPayInfo" :model="formContractBudget" :rules="formContractPayInfoRules" label-position="left" label-width="130px" >
          <el-form-item label="合同编号" prop="contractCode">
            <!-- :disabled="unchangeableContractPayInfo|| (contractCodeSaveFlag)" -->
            <div class="red_tip">
              <el-input :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractCode" class="basicInfo_input_box" placeholder="请输入"/>
              <div v-if="!formContractBudget.contractCode" class="tips">!</div>
              <el-button :disabled="unchangeableContractPayInfo" type="primary" icon="el-icon-search" style="margin-left: 5px;" @click="purchaseDirect = true"/>
            </div>
          </el-form-item>
          <el-form-item label="合同名称" prop="contractName">
            <div class="red_tip">
              <el-input :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractName" class="basicInfo_input_box" placeholder="请输入"/>
              <div v-if="!formContractBudget.contractName" class="tips">!</div>
            </div>
          </el-form-item>
          <div class="flex_layout">
            <el-form-item label="合同开始时间" prop="contractStartDateFlag">
              <div class="red_tip">
                <div>
                  <el-radio :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractStartDateFlag" label= "Y">明确</el-radio>
                  <el-radio :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractStartDateFlag" label= "N">未明确</el-radio>
                </div>
                <div v-if="!formContractBudget.contractStartDateFlag" class="tips">!</div>
              </div>
            </el-form-item>
            <el-form-item prop="contractStartDate" v-if='formContractBudget.contractStartDateFlag=="Y"'>
              <div class="red_tip">
                <el-date-picker :disabled="unchangeableContractPayInfo"   value-format="yyyy-MM-dd"  v-model="formContractBudget.contractStartDate"   class="basicInfo_input_box" placeholder="请选择项目开始时间"/>
                <div v-if="!formContractBudget.contractStartDate" class="tips">!</div>
              </div>
            </el-form-item>
          </div>
          <div class="flex_layout">
            <el-form-item label="合同结束时间" prop="contractEndDateFlag">
              <div class="red_tip">
                <div>
                  <el-radio :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractEndDateFlag" label= "Y">明确</el-radio>
                  <el-radio :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractEndDateFlag" label= "N">未明确</el-radio>
                </div>
                <div v-if="!formContractBudget.contractStartDateFlag" class="tips">!</div>
              </div>
            </el-form-item>
            <el-form-item label="" prop="contractEndDate"  v-if="formContractBudget.contractEndDateFlag=='Y'" >
              <div class="red_tip">
                <el-date-picker :disabled="unchangeableContractPayInfo"   v-model="formContractBudget.contractEndDate" value-format="yyyy-MM-dd" class="basicInfo_input_box" placeholder="请选择项目结束时间"/>
                <div v-if="!formContractBudget.contractEndDate" class="tips">!</div>
              </div>
            </el-form-item>
          </div>
          <div class="flex_layout">
            <el-form-item label="维保开始时间">
              <el-date-picker :disabled="unchangeableContractPayInfo" v-model="formContractBudget.maintenanceStartDate" value-format="yyyy-MM-dd" class="basicInfo_input_box" placeholder="请选择维保开始时间"/>
            </el-form-item>
          </div>
          <div class="flex_layout">
            <el-form-item label="维保结束时间">
              <el-date-picker :disabled="unchangeableContractPayInfo" v-model="formContractBudget.maintenanceEndDate" value-format="yyyy-MM-dd" class="basicInfo_input_box" placeholder="请选择维保结束时间"/>
            </el-form-item>
          </div>
          <div class="flex_layout">
            <el-form-item label="合同金额(万元)(含税)" prop="contractAmount" label-width="160px">
              <div class="red_tip">
                <el-input :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractAmount" :min="0" type="number"  @change="chectContractAmount"  class="basicInfo_input_box" placeholder="请输入"/>
                <div v-if="!formContractBudget.contractAmount" class="tips">!</div>
              </div>
            </el-form-item>
            <el-form-item class="taxPoints" label="合同税点" prop="contractTaxPoints">
              <div class="red_tip">
                <el-select :disabled="unchangeableContractPayInfo" v-model="formContractBudget.contractTaxPoints" class="basicInfo_input_box" placeholder="请选择合同税点">
                  <el-option label="0%" value="0"/>
                  <el-option label="1%" value="1"/>
                  <el-option label="6%" value="6"/>
                  <el-option label="13%" value="13"/>
                </el-select>
                <div v-if="!formContractBudget.contractTaxPoints" class="tips">!</div>
              </div>
            </el-form-item>
          </div>
          <el-form-item label="发票类型" prop="contractTaxPoints">
            <div class="red_tip">
              <el-select :disabled="unchangeableContractPayInfo" v-model="formContractBudget.invoiceType" class="basicInfo_input_box" placeholder="请选择发票类型">
                <el-option label="增值税专用发票" value="P"/>
                <el-option label="增值税普通发票" value="Z"/>
              </el-select>
              <div v-if="!formContractBudget.invoiceType" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="供应商全称" prop="supplierName">
            <div class="red_tip">
              <el-input :disabled="unchangeableContractPayInfo" v-model="formContractBudget.supplierName" class="basicInfo_input_box" placeholder="请输入"/>
              <div v-if="!formContractBudget.supplierName" class="tips">!</div>
            </div>
          </el-form-item>
          <div class="flex_layout">
            <div class="flex_layout">
              <div class="contract_payment">支付进度</div>
              <div class="contract_payment_result width35">{{ paymentScheduling }}%</div>
              <div class="contract_payment_tips">(系统自动计算=∑下表金额/合同总金额)</div>
            </div>
            <div class="flex_layout margin-left120">
              <div class="contract_payment">验收状态</div>
              <div class="contract_payment_result">{{ paymentScheduling>=90?'验证完毕':'尚未验收完毕' }}</div>
            </div>
          </div>
          <div class="tableApprovalMaterials table">
            <div class="upload_box">
              <p>合同上传</p>
              <el-upload
                :on-success="handleApprovalSuccess"
                :file-list="formContractBudget.tableApprovalMaterials"
                class="uploadApprovalMaterials"
                :show-file-list="false"
                :action="uploadUrl"
                :data='fileInfo1'
                list-type="text"
                :before-upload="handleBeforeUpload1"
              >
                <el-button slot="trigger" v-if="!unchangeableContractPayInfo" size="small" type="primary">上传文件</el-button>
              </el-upload>
            </div>
            <el-table
              :data="contractList"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="200"
              border
              style="width:750px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="fileName" label="文件名称" width="250"/>
              <!-- <el-table-column prop="createDate" label="上传时间" width="150"/> -->
              <el-table-column prop="comment" label="备注" width="150"/>
              <el-table-column label="操作" >
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="showUpdateFileName(scope.row)" :disabled="unchangeableContractPayInfo"
                    v-hasFunc="['12343_LICENCE']">修改</el-button>
                  <el-button :disabled="unchangeableContractPayInfo" type="text" size="small" @click="deleteFile('10',scope.row,scope.$index)"
                    v-hasFunc="['12344_LICENCE']">删除</el-button>
                  <el-button  type="text" size="small" @click="downloadFile('10',scope.row,scope.$index)"
                    v-hasFunc="['12345_LICENCE']">下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div class="tableProjectInside table">

            <div class="upload_box">
              <div class="upload_box">
                <p>支付明细</p>
                <el-button v-if="!unchangeableContractPayInfo" :size="size" type="primary" class="topRightButton module_button" @click="showUpdatePay()">新 增</el-button>
              </div>
            </div>
            <el-table
              :data="payList"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="300"
              border
              style="width:850px"
            >
              <!-- <el-table-column label="序号" type="index" fixed width="50"/> -->
              <el-table-column prop="serial" label="付款编号" width="150"/>
              <el-table-column prop="payNum" label="支付次数" width="150" :formatter="(row, column, cellValue, index)=>getDictName('RD_PAY_NUMS',cellValue)"/>
              <el-table-column :formatter="(row, column, cellValue, index)=>getDictName('RD_PAY_MENT',cellValue)" prop="payment" label="支付状态" width="150"/>
              <el-table-column :formatter="payFormatter" prop="payAmount" label="支付金额（万元）" width="150"/>
              <el-table-column prop="comptime" label="完成时间" width="150"/>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="showUpdatePay(scope.row)" :disabled="unchangeableContractPayInfo"
                  v-hasFunc="['12346_LICENCE']">修改</el-button>
                  <el-button type="text" size="small" @click="deletePay(scope.row)" :disabled="unchangeableContractPayInfo"
                  v-hasFunc="['12347_LICENCE']">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div class="space"/>
        </el-form>
      </div>
      <div>
        <el-dialog
          :visible.sync = "purchaseDirect"
          :before-close="handleClose"
          width="45%"
        >
          <el-form :inline="true" :model="contract" >
            <el-row :gutter="10">
              <el-form-item label="合同编号：">
                <el-input v-model="contract.contractCode" placeholder="请填入"/>
              </el-form-item>
              <el-form-item label="合同名称：">
                <el-input v-model="contract.contractName" placeholder="请填入"/>
              </el-form-item>
              <el-form-item>
                <el-button :size="size" type="primary" @click="submitBtn">查 询</el-button>
                <el-button :size="size" @click="clearContract">重 置</el-button>
              </el-form-item>
            </el-row>
          </el-form>
          <el-table
            v-if="contractDataTable"
            ref="singleTable"
            :data="contractData"
            :row-class-name="tableRowClassName"
            highlight-current-row
            @current-change = "handleCurrentChange"
          >
            <el-table-column property = "contractCode" label="合同编号"/>
            <el-table-column property = "contractName" label = "合同名称"/>
            <el-table-column property = "contractAmount" label = "合同金额(万元)"/>
            <el-table-column property = "contractStartDate" label = "开始时间"/>
            <el-table-column property = "contractEndDate" label = "结束时间"/>
          </el-table>
          <div class="pager_zb">
            <pager ref="pager" :fns="getPager" :total="totalNumber" :page-size="sizel" v-model="$attrs" />
          </div>
          <div v-if="!contractDataTable" class="centered-box">
            未查询到符合条件的合同
          </div>
          <el-row :gutter="10" style="margin-top: 10px; text-align: right;">
            <el-button :size="size" @click="purchaseDirect = false">取 消</el-button>
            <el-button :size="size" type="primary" @click="contractImport">确认导入</el-button>
          </el-row>
        </el-dialog>
      </div>
    </div>
    <!-- 财务核算信息 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">财务核算信息</p>
          <p class="title_tips">!   表示该项非必填，但后续需完善</p>
        </div>
        <div class="wrapper_right" v-if='isEdit!=0'>
          <el-button :disabled="!unchangeableFinancialInfo" :size="size" type="primary" class="topRightButton module_button" @click="edit('formFinancialAccountInfo')"
            v-hasFunc="['12351_LICENCE']">编 辑</el-button>
          <el-button
            v-hasFunc="['12321_LICENCE']"
            v-if="!unchangeableFinancialInfo"
            :size="size"
            type="primary"
            class="topRightButton module_button"
            @click="cancel('formFinancialAccountInfo')"
          >取 消</el-button>
          <el-button :disabled="unchangeableFinancialInfo" :size="size" class="topRightButton module_button" @click="save('formFinancialAccountInfo')"
            v-hasFunc="['12352_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formFinancialAccountInfo" :model="formContractBudget" :rules="formContractBudgetRules" label-position="left" label-width="120px" >
          <el-form-item label="费用类型" prop="expenseType">
            <div class="red_tip">
              <el-input :disabled="unchangeableFinancialInfo" v-model="formContractBudget.expenseType" class="basicInfo_input_box" placeholder="请输入"/>
              <div v-if="!formContractBudget.expenseType" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="报销项目" prop="reimbursementItem">
            <div class="red_tip">
              <el-input :disabled="unchangeableFinancialInfo" v-model="formContractBudget.reimbursementItem" class="basicInfo_input_box" placeholder="请输入"/>
              <div v-if="!formContractBudget.reimbursementItem" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="对应会计科目" prop="accountSubject">
            <div class="red_tip">
              <el-input :disabled="unchangeableFinancialInfo" v-model="formContractBudget.accountSubject" class="basicInfo_input_box" placeholder="请输入"/>
              <div v-if="!formContractBudget.accountSubject" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="是否取尾款发票" prop="isInvoice">
            <div class="red_tip">
              <div>
                <el-radio :disabled="unchangeableFinancialInfo" v-model="formContractBudget.isInvoice" label= "true">是</el-radio>
                <el-radio :disabled="unchangeableFinancialInfo" v-model="formContractBudget.isInvoice" label= "false">否</el-radio>
              </div>
              <div v-if="!formContractBudget.isInvoice" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="备注栏" prop="remarkLan">
            <div class="red_tip">
              <el-input :disabled="unchangeableFinancialInfo" v-model="formContractBudget.remarkLan" :rows="3" type="textarea" maxlength="200" show-word-limit class="basicInfo_input_box" placeholder="请输入"/>
            </div>
          </el-form-item>
          <div class="space"/>
        </el-form>
      </div>
    </div>

    <el-dialog
      title="修改文件名称"
      :visible.sync="isShowUpdateFileNameFlag"
      width="30%"
      @before-close="onFileNameCancel"
    >
      <el-form :model="currFileInfo" ref="currFileInfo" :rules="currFileInfoRules" label-width="100px">
        <el-form-item label="原文件名称" prop="deptName">
          <el-input v-model="currFileInfo.fileName" placeholder="原文件名称" disabled ></el-input>
        </el-form-item>
        <el-form-item label="新文件名称" prop="newFileName">
          <el-input v-model="currFileInfo.newFileName" placeholder="新文件名称"></el-input>
        </el-form-item>


      </el-form>

      <span slot="footer">
        <el-button @click="onFileNameCancel()">取 消</el-button>
        <el-button type="primary" @click="onFileNameConfirm()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="支付明细"
      :visible.sync="isShowUpdatePayFlag"
      width="30%"
      @before-close="onPayCancel"
    >
      <el-form :model="currPayInfo" ref="payForm" :rules="payRules" label-width="180px">
        <el-form-item label="付款编号" prop="serial">
          <el-input v-model="currPayInfo.serial" maxlength="30" placeholder="付款编号" style='width:200px'/>
        </el-form-item>
        <el-form-item label="支付次数" prop="payNum">
        <me-select v-model="currPayInfo.payNum"
                :dicttypeid="'RD_PAY_NUMS'"   class="basicInfo_input_box" placeholder="支付次数" style='width:200px'/>
        </el-form-item>
        <el-form-item label="支付金额（万元）(含税)" prop="payAmount">
          <el-input v-model="currPayInfo.payAmount" type='number' :min="0" placeholder="支付金额（万元）" style='width:200px'></el-input>
        </el-form-item>
        <el-form-item label="支付状态" prop="">
          <el-select v-model="currPayInfo.payment" style="width:200px">
            <el-option label="付款完成" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label="完成时间" prop="remark">
          <el-date-picker v-model="currPayInfo.comptime" type="date" value-format="yyyy-MM-dd" style="width:200px"/>
        </el-form-item>
      </el-form>

      <span slot="footer">
        <el-button @click="onPayCancel()">取 消</el-button>
        <el-button type="primary" @click="onPayConfirm()">确 定</el-button>
      </span>
    </el-dialog>


    <el-dialog
      title="关联项目"
      :visible.sync="isShowUpdateProjectFlag"
      width="30%"
      @before-close="onProjectCancel"
    >
      <el-form :model="currProject" ref="ratioForm" :rules="projectRules" label-width="150px">
        <el-form-item label="项目代码" prop="projectCode">
          <el-input v-model="currProject.projectCode" placeholder="项目代码" disabled></el-input>
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="currProject.projectName" placeholder="项目名称" disabled></el-input>
        </el-form-item>
        <el-form-item label="占比(%)"  prop="ratio">
          <el-input v-model="currProject.ratio" :min="0" :max="100" placeholder="占比(%)"  type='number'>
            <template slot="append">%</template>
          </el-input>
        </el-form-item>

      </el-form>

      <span slot="footer">
        <el-button @click="onProjectCancel()">取 消</el-button>
        <el-button type="primary" @click="onProjectConfirm()">确 定</el-button>
      </span>
    </el-dialog>


    <el-dialog
      title="关联项目"
      :visible.sync="isShowProjectListFlag"
      width="50%"
      @before-close="onProjectListCancel"
    >
      <el-form inline>
        <div class="interviewList_head">
          <div>
            <el-form :inline="true" :model="searchForm" class="project-search-inline">
              <el-form-item label="项目编号：">
                <el-input v-model="searchForm.projectCode" placeholder="请填入"/>
              </el-form-item>
              <el-form-item label="项目名称：">
                <el-input v-model="searchForm.projectName" placeholder="请填入"/>
              </el-form-item>
               <el-form-item label="">
                <el-button  :size="size" type="primary" class="interviewList_head_right_button" @click="queryProject">查 询</el-button>
              </el-form-item>
              <el-form-item label="">
                  <el-button  :size="size"  type="primary" class="interviewList_head_right_button" @click="clearSearch">重 置</el-button>
              </el-form-item>
            </el-form>
          </div>


        </div>
      </el-form>
      <div class="projectContainer">
        <el-table ref='projectTable' :data="projectList" highlight-current-row height="510" style="width:100%">
          <el-table-column :show-overflow-tooltip="true" type="selection" width="40" />
          <el-table-column :show-overflow-tooltip="true" label="项目编号" prop="projectCode" width="200"/>
          <el-table-column :show-overflow-tooltip="true" label="项目名称" prop="projectName" width="250"/>
          <el-table-column :show-overflow-tooltip="true" label="主管部门" prop="competentName" width="200"/>
          <el-table-column :show-overflow-tooltip="true" label="关联系统" prop="sysName" width="250"/>
          <el-table-column :show-overflow-tooltip="true" label="项目开始时间" prop="startDate" width="250"/>
          <el-table-column :show-overflow-tooltip="true" label="项目状态" prop="projectState" width="150"
          :formatter="(row, column, cellValue, index)=>getDictName('RD_PROJECT_STATE',cellValue)" />

        </el-table>
        <div>
            <el-form :model="currProject" ref="ratioForm2" :rules="projectRules" label-width="150px">
              <el-form-item label="占比(%)" prop="ratio">
                <el-input v-model="currProject.ratio" placeholder="占比(%)" type='number' width='150'></el-input>
              </el-form-item>

            </el-form>
          </div>
      </div>

      <span slot="footer">
        <el-button   :size="size" @click="onProjectListCancel()">取 消</el-button>
        <el-button   :size="size" type="primary" @click="onProjectListConfirm()">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>
<script>
import pager from '@/components/Pager'
import meSelect from '@/components/MeSelect'
import common from '@/api/common'
import department from '@/views/plugins/systemPlug/Department'
import { queryPager } from '@/api/rdmanager/rdProject'
import { saveRdProjectContract,deleteRdProjectContract,queryRdProjectContract,
updateContractInfo,updateContractResearchProject,updateContractPayInfo,updateContractFinancialInfo ,queryByRdProjectContractId, queryPager as queryPagerContract, directConnection, contractPurchaseImport } from '@/api/rdmanager/rdProjectContract'
import { updateFileName,deleteRdProjectFile,queryRdProjectFile ,downloadRdProjectFile} from '@/api/rdmanager/rdProjectFile'
import {saveRdContractPayRecord,deleteRdContractPayRecord,queryRdContractPayRecord} from '@/api/rdmanager/rdContractPayRecord'
import {saveRdProjectContractRelation,deleteRdProjectContractRelation,queryRdProjectContractRelation} from '@/api/rdmanager/rdProjectContractRelation'
export default {
  name: 'ContractManagementEdit',
  components: { department, meSelect, pager },
  data() {
    return {
      purchaseDirect: false,
      searchForm: {
        projectCode: '', // 项目编号
        projectName: '', // 项目名称
        competentDept: '', // 主管部门
        sysName: '' // 关联系统
      },
      contract: {
        contractCode: '', // 合同编号
        contractName: '' // 合同名称
      },
      contractDataTable: true,
      contractInto: {}, // 采购信息导入参数
      contractData: [],
      totalNumber: 0,
      isShowProjectListFlag:false,//项目列表选择框
      isShowUpdateProjectFlag:false,//显示项目关联修改框
      isShowUpdatePayFlag:false,//显示支付明细
      isShowUpdateFileNameFlag:false,//显示文件名称
      fileType:'10',
      uploadUrl:process.env.BASE_API + "/rdProjectFile/upload",
      budgetCodeFlag: true, // 预算编号是否可修改 true可修改 false不可修改
      unchangeableContractBudget: true, // 合同预算信息 是否可修改，true 不可修改 false 可修改
      unchangeableProAssociation: true, // 研发项目关联 是否可修改，true 不可修改 false 可修改
      unchangeableContractPayInfo: true, // 合同及支付信息 是否可修改，true 不可修改 false 可修改
      unchangeableFinancialInfo: true, // 财务核算信息 是否可修改，true 不可修改 false 可修改
      size: 'small', // 按钮大小
      sizel: 20, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paymentScheduling: '0', // 支付进度
      acceptancePhase: '尚未验收完毕', // 验收状态
      payList:[],//支付历史对象
      currPayInfo:{},//当前支付对象
      currProject:{},//当前编辑项目
      projectList:[],//项目
      relationProjectList:[],//关联项目
      // 合同预算信息备份
      formContractBudgetBu: {
        budgetCode: '', // 预算编号
        budgetName: '', // 预算名称
        budgetAmount: '', // 预算金额（元）
        type: '', // 类型
        supplier: '', // 供应商(计划)
        approvedDate: '', // 审批通过时间
        totalManpower: '', // 合同总人月
        softwareManpower: '', // 软件研发人月
        assistManpower: '', // 辅助研发人月
        riskManagementManpower: '', // 风控研发人月

        isResearchProject: '', // 是否为研发项目
        tableResearchProject: [], //  关联研发项目
        techInnoPoint: '', // 科技项目技术创新点

        contractCode: '', // 合同编号
        contractName: '', // 合同名称
        createDate: '', // 合同开始时间
        endDate: '', // 合同结束时间
        contractAmount: '', // 合同金额(元)(含税)
        contractTaxPoints: '', // 合同税点
        supplierName: '', // 供应商全称

        expenseType: '', // 费用类型
        reimbursementItem: '', // 报销项目
        accountSubject: '', // 对应会计科目
        isInvoice: '', // 是否取尾款发票
        contractCodeSaveFlag: false // 是否可以编辑合同编码
      },
      // 合同预算信息
      formContractBudget: {
        budgetCode: '', // 预算编号
        budgetName: '', // 预算名称
        budgetAmount: '', // 预算金额（元）
        type: '', // 类型
        supplier: '', // 供应商(计划)
        approvedDate: '', // 审批通过时间
        totalManpower: '', // 合同总人月
        softwareManpower: '', // 软件研发人月
        assistManpower: '', // 辅助研发人月
        riskManagementManpower: '', // 风控研发人月

        isResearchProject: '', // 是否为研发项目
        tableResearchProject: [], //  关联研发项目
        techInnoPoint: '', // 科技项目技术创新点

        contractCode: '', // 合同编号
        contractName: '', // 合同名称
        createDate: '', // 合同开始时间
        endDate: '', // 合同结束时间
        contractAmount: '', // 合同金额(元)(含税)
        contractTaxPoints: '', // 合同税点
        supplierName: '', // 供应商全称

        expenseType: '', // 费用类型
        reimbursementItem: '', // 报销项目
        accountSubject: '', // 对应会计科目
        isInvoice: '' ,// 是否取尾款发票
        contractCodeSaveFlag:false,//是否可以编辑合同编码




        // // 审批资料
        // tableApprovalMaterials: [
        //   {
        //     fileName: '关于xxx的延时', // 文件名称
        //     uploadedDate: '2024-01-01' // 上传时间
        //   },
        //   {
        //     fileName: '审批通过截图', // 文件名称
        //     uploadedDate: '2024-01-02' // 上传时间
        //   }
        // ]
      },
      fileInfo:{},
      fileInfo1:{type:'11'},
      //审批资料
      fileList:[],
      contractList:[],
      currFileInfo:{},
      // // 研发项目关联
      // formProAssociation: {
      //   isResearchProject: '', // 是否为研发项目
      //   tableResearchProject: [], //  关联研发项目
      //   techInnoPoint: '' // 科技项目技术创新点
      // },
      // 合同及支付信息
      // formContractPayInfo: {
      //   contractCode: '', // 合同编号
      //   contractName: '', // 合同名称
      //   createDate: '', // 合同开始时间
      //   endDate: '', // 合同结束时间
      //   contractAmount: '', // 合同金额(元)(含税)
      //   contractTaxPoints: '', // 合同税点
      //   supplierName: '' // 供应商全称
      // },
      // 财务核算信息
      // formFinancialAccountInfo: {
      //   expenseType: '', // 费用类型
      //   reimbursementItem: '', // 报销项目
      //   accountSubject: '', // 对应会计科目
      //   isInvoice: '' // 是否取尾款发票
      // },
      rules: {
        budgetCode: [
          { required: true, message: '请输入预算编号', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        budgetName: [
          { required: true, message: '请输入预算名称', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        budgetAmount: [
          { required: true, message: '请输入预算金额(元)', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'},
          {trigger: 'blur',validator:(rule,value,callback)=>{
            if(value<0){
              callback(new Error("预算金额不能为负数"))
            }else{
              callback();
            }
          }}
        ],
        approvedDate: [
          {   required: true, message: '请选择审批通过时间', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'change' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        supplier: [
          { required: true, message: '请选择供应商(计划)', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        isResearchProject: [
          { required: true, message: '请选择是否为研发项目', trigger: 'change' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        relationProjectList: [
          { required: true, message: '请选择关联研发项目', trigger: 'change' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ]
      },
      currFileInfoRules:{
        newFileName: [
          { required: true, message: '请输入新文件名称', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
      },
      payRules:{
        payNum: [
          { required: true, message: '请输入支付笔数', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        serial: [
          { required: true, message: '请输入编号', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ],
        payAmount: [
          { required: true, message: '请输入支付金额', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'},
          {trigger: 'blur',validator:(rule,value,callback)=>{
            if(value<0){
              callback(new Error("支付金额不能为负数"))
            }else{
              callback();
            }
          }}
        ],
      },
      projectRules:{
          ratio: [
            { required: true, message: '请输入占比', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
          ],
      },
      formContractPayInfoRules:{
        contractCode: [
            { required: true, message: '合同编码不能为空', trigger: 'blur' },
            {pattern: /.*\S+.*/,message:'合同编码不能为空格',trigger:'blur'}
        ],
        contractName: [
            { required: true, message: '合同名称不能为空', trigger: 'blur' },
            {pattern: /.*\S+.*/,message:'合同名称不能为空格',trigger:'blur'}
        ],
        contractStartDateFlag: [
            { required: true, message: '是否有合同开始时间不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        contractStartDate: [
          { trigger: 'blur', validator: (rule, value, callback) => {
            if (this.formContractBudget.contractStartDateFlag=='Y' && !value) {
              callback(new Error('合同开始时间不能为空'));
            } else if (this.formContractBudget.contractEndDateFlag == 'Y' &&this.formContractBudget.contractEndDate
              && this.formContractBudget.contractStartDateFlag=='Y'&&this.formContractBudget.contractStartDate) {
                    var endDate = Number(this.formContractBudget.contractEndDate.replace(/\-/g,''));
                    var startDate = Number(this.formContractBudget.contractStartDate.replace(/\-/g,''));
                    if(startDate>endDate){
                      callback(new Error('合同开始时间不能大于合同结束时间'));
                    }
                }else{
                  callback();
                }
            } }
        ],
        contractEndDateFlag: [
            { required: true, message: '是否有合同结束时间不能为空', trigger: 'blur' }
        ],
        contractEndDate: [
            {   trigger: 'blur',validator:(rule,value,callback)=>{
                if(this.formContractBudget.contractEndDateFlag=='Y'&&!value){
                   callback(new Error('合同结束时间不能为空'));
                }else if(this.formContractBudget.contractEndDateFlag=='Y'&&this.formContractBudget.contractEndDate
                  &&this.formContractBudget.contractStartDateFlag=='Y'&&this.formContractBudget.contractStartDate){
                    var endDate = Number(this.formContractBudget.contractEndDate.replace(/\-/g,''));
                    var startDate = Number(this.formContractBudget.contractStartDate.replace(/\-/g,''));
                    if(startDate>endDate){
                      callback(new Error('合同开始时间不能大于合同结束时间'));
                    }
                }else{
                  callback();
                }
            } }
        ],
        contractAmount: [
          { required: true, message: '合同金额不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'},
          {   trigger: 'blur',validator:(rule,value,callback)=>{
            if(value<0){
              callback(new Error("合同金额不能为负数"))
            }else{
              callback();
            }
          }}
        ],
        taxPoints: [
            { required: true, message: '合同税点不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        supplierName: [
            { required: true, message: '供应商全称不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        invoiceType: [
            { required: true, message: '发票类型不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],



      },
      formContractBudgetRules:{
        expenseType: [
            { required: true, message: '费用类型不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        reimbursementItem: [
            { required: true, message: '报销项目不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        accountSubject: [
            { required: true, message: '对应会计科目不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        isInvoice: [
            { required: true, message: '是否取尾款发票不能为空', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ]

      },

      // pickerOptionsStart: {
      //   disabledDate: (time) => {
      //     const endDateVal = this.formContractPayInfo.endDate
      //     if (endDateVal) {
      //       return (
      //         time.getTime() > new Date(endDateVal).getTime() - 1 * 24 * 60 * 60 * 1000
      //       )
      //     }
      //   }
      // },
      // pickerOptionsEnd: {
      //   disabledDate: (time) => {
      //     const beginDateVal = this.formContractPayInfo.createDate
      //     if (beginDateVal) {
      //       return (
      //         time.getTime() < new Date(beginDateVal).getTime() + 1 * 24 * 60 * 60 * 1000
      //       )
      //     }
      //   }
      // }
    }
  },
  created() {
    this.init()


    // const contractParams = this.$route.query.contractParams

    // if(parseParams){
    //   this.formContractBudgetBu = JSON.parse(JSON.stringify(parseParams));
    //   this.formContractBudget = JSON.parse(JSON.stringify(parseParams));
    //   if(this.formContractBudget.contractCode){
    //     this.contractCodeSaveFlag = true
    //   }
    //   // this.fileInfo1.rdProjectId = this.$route.params.projectParams.pkId;
    //   // this.fileInfo2.rdProjectId = this.$route.params.projectParams.pkId;
    //   // this.fileInfo3.rdProjectId = this.$route.params.projectParams.pkId;
    //   this.queryFile();
    //   this.queryRdContractPayRecord();
    //   this.queryRdProjectContractRelation();
    // }

  },
  methods: {
    init() {
      if (this.formContractBudget.projectCode) {
        this.budgetCodeFlag = false
      }
      this.isEdit = this.$route.query.isEdit;
      const params = {}
      let contractParams = {}
      params.budgetCode = JSON.parse(this.$route.query.contractParams)
      // const data = params
      const data = common.paramsFormat(params)
      queryPagerContract(data).then(res => {
        if (res.data.status === 200) {
          contractParams = res.data.results[0]

        }
        const parseParams = contractParams ? contractParams : null
        console.log(parseParams, '==============================+++')
        if(Object.keys(parseParams).length !== 0){
          this.formContractBudgetBu = JSON.parse(JSON.stringify(parseParams));
          this.formContractBudget = JSON.parse(JSON.stringify(parseParams));
          if(this.formContractBudget.contractCode){
            this.contractCodeSaveFlag = true
          }
          this.calculateTotalManpower();
          this.queryFile();
          this.queryRdContractPayRecord();
          this.queryRdProjectContractRelation();
        }
      })
    },
    /**
     * 金额
     */
    payFormatter(row,column,cellValue) {
      if ( cellValue !== undefined && cellValue !== null) {
        return parseFloat(cellValue).toFixed(2)
      }
    },
    /**
     * 占比%
     */
    formatRatio(row,column,cellValue){
      return `${(cellValue).toFixed(2)}%`
    },
    /**
     * 合同金额校验
     */
    chectContractAmount(){
      if (Number(this.formContractBudget.contractAmount)<0) {
        this.$message.error('金额不能为负数！')
      }
      if(Number(this.formContractBudget.contractAmount)>Number(this.formContractBudget.budgetAmount)){
        this.$message.error('金额不能超过预算金额')
      }else if ((Number(this.formContractBudget.budgetAmount) -Number(this.formContractBudget.contractAmount))/Number(this.formContractBudget.budgetAmount) > 0.2){
        this.$message.warning('金额与预算金额偏差超过20%')
      }
    },
    // 右上角总删除按钮
    deleteContract() {
      if(!this.formContractBudget||!this.formContractBudget.pkId){
        this.$message.error('数据不存在')
        return ;
      }
      this.$confirm('确认删除该合同的全部记录吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        deleteRdProjectContract({ pkId: this.formContractBudget.pkId }).then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('删除成功')
            this.back()
          } else {
            this.$message.error(res.data.message?res.data.message:'删除失败')
          }
        }).catch(error => {
          loading.close()
          this.$message.error(error)
        })
      }).catch(error => { console.log(error) })
    },
    handleBeforeUpload(file){
      if(!this.checkContractId(this.fileInfo)){
          return false;
      }
      this.fileInfo.rdProjectId = this.fileInfo.rdProjectContractId;
      this.fileInfo.type = this.fileType;
    },
    handleBeforeUpload1(file){
       if(!this.checkContractId(this.fileInfo)){
          return false;
        }
        this.fileInfo1.rdProjectId = this.fileInfo.rdProjectContractId;
        this.fileInfo1.type = '11';
    },

    queryFile(){
      console.info(this.formContractBudget,'queryFile')
      var qry = {type:this.fileType};
      if(!this.formContractBudget||!this.formContractBudget.pkId){
            return false;
      }
      qry.rdProjectId = this.formContractBudget.pkId;
      queryRdProjectFile(qry).then(res => {
        if (res.data.status === 200) {
          var files = res.data.results;
          var fileList1 = [];
          var fileList2 = [];
          for(var i=0;i<files.length;i++){
              var tmp = files[i];
              if(tmp.type=='10'){
                fileList1.push(tmp);
              }else if(tmp.type=='11'){
                fileList2.push(tmp);
              }
          }
          this.fileList = fileList1;
          this.contractList = fileList2;
        }else{
          this.$alert(res.data.message, { type: 'error' });
        }
      })
    },
    deleteFile(type,row,index){
      this.$confirm('确认删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        deleteRdProjectFile({ pkId: row.pkId }).then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('删除成功')
            this.queryFile(type);
          } else {
            this.$message.error(res.data.message?res.data.message:'删除失败')
          }
        }).catch(error => {
          loading.close()
          this.$message.error(error)
        })
      }).catch(error => { console.log(error) })
    },
    showUpdateFileName(fileInfo){
       this.currFileInfo = Object.assign({},fileInfo);;
       this.isShowUpdateFileNameFlag = true;
    },
    onFileNameCancel(){
      this.isShowUpdateFileNameFlag = false;
    },
    onFileNameConfirm(){
      var flag = false;
      this.$refs.currFileInfo.validate((valid)=>{
                flag =  valid;
      })
      if(!flag){
        return;
      }
      this.$confirm('确认修改名称吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        updateFileName({ pkId:  this.currFileInfo.pkId,rdProjectId:this.currFileInfo.rdProjectId,fileName: this.currFileInfo.newFileName }).then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('修改成功')
            this.isShowUpdateFileNameFlag = false;
            this.queryFile(this.currFileInfo.type);
          } else {
            this.$message.error(res.data.message?res.data.message:'修改失败')
          }
        }).catch(error => {
          loading.close()
          this.$message.error(error)
        })
      }).catch(error => { console.log(error) })
    },
    //文件下载
    downloadFile(type,row,index) {
      // rdProjectId:this.formProjectBasicInfo.pkId,type:type
       this.$confirm('是否'+ row.sourceFileName,'是否导出'+ row.sourceFileName,{confirmButtonText:'是',cancelButtonText:'否',type:'warning'}).then(_=>{

           downloadRdProjectFile({rdProjectFileId: row.pkId,_isDown:'Y'}).then(res => {
            common.download(res.data,self,row.sourceFileName)
      //   })

           })
        })

      // downloadRdProjectFile({rdProjectFileId: row.pkId}).then(res => {

      //   this.$confirm('是否'+ row.sourceFileName,'是否导出'+ row.sourceFileName,{confirmButtonText:'是',cancelButtonText:'否',type:'warning'}).then(_=>{

      //     common.download(res.data,self,row.sourceFileName)
      //   })
      // })
    },
    //查询支付信息
    queryRdContractPayRecord(){
        var qry = {};
        if(!this.formContractBudget||!this.formContractBudget.pkId){
              return false;
        }
        qry.rdProjectContractId = this.formContractBudget.pkId;
        queryRdContractPayRecord(qry).then(res => {
          if (res.data.status === 200) {
            this.payList = res.data.results;

            if(this.payList&&this.payList.length>0){
              var sumPayAmount = 0;
              for(var i=0;i<this.payList.length;i++){
                var item = this.payList[i];
                if(item.payAmount){
                    sumPayAmount = sumPayAmount+Number(item.payAmount)
                }
              }
            }
           if(!this.payList||this.payList.length==0){
              this.paymentScheduling = 0;
            }else if(this.formContractBudget&&this.formContractBudget.contractAmount&&this.formContractBudget.contractAmount>0){
                this.paymentScheduling = Number(sumPayAmount*100/Number(this.formContractBudget.contractAmount)).toFixed(2);
            }else{
              this.paymentScheduling = 0;
            }

          }else{
            this.$alert(res.data.message, { type: 'error' });
          }
        })
    },
    //支付明细 payInfo 空说明
    showUpdatePay(payInfo){
      if(payInfo){
        this.currPayInfo = Object.assign({},payInfo);
      }else{
        this.currPayInfo = {};
      }
      if(!this.checkContractId(this.currPayInfo) ){
          return ;
      }

       this.isShowUpdatePayFlag = true;
    },

    deletePay(row,index){
      this.$confirm('确认删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        deleteRdContractPayRecord({ pkId: row.pkId }).then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('删除成功')
            this.queryRdContractPayRecord();
          } else {
            this.$message.error(res.data.message?res.data.message:'删除失败')
          }
        }).catch(error => {
          loading.close()
          this.$message.error(error)
        })
      }).catch(error => { console.log(error) })
    },

    onPayCancel(){
      this.isShowUpdatePayFlag = false;
    },
    onPayConfirm(){
      var flag = false;
      this.$refs.payForm.validate((valid)=>{
                flag =  valid;
      })
      if(!flag){
        return;
      }
      const loading = common.loading(this)
      saveRdContractPayRecord(this.currPayInfo).then(res => {
        loading.close()
        if (res.data.status === 200) {
          this.$message.success('保存成功')
          this.isShowUpdatePayFlag = false;
          this.queryRdContractPayRecord();
        } else {
          this.$message.error(res.data.message?res.data.message:'保存失败')
        }
      }).catch(error => {
        loading.close()
        this.$message.error(error)
      })
    },


    //查询关联项目
    queryProject(){
        var params =  this.searchForm;
        params.notRdProjectContractId = this.formContractBudget.pkId;
        params.size = 20
        const data = common.paramsFormat(params)
        queryPager(data).then(res => {
          if (res.data.status === 200) {
            this.projectList = res.data.results
          }
        })
    },
    queryRdProjectContractRelation(){
        var qry = {};
        if(!this.formContractBudget||!this.formContractBudget.pkId){
              return false;
        }
        qry.rdProjectContractId = this.formContractBudget.pkId;
        queryRdProjectContractRelation(qry).then(res => {
          if (res.data.status === 200) {
            this.relationProjectList = res.data.results;
          }else{
            this.$alert(res.data.message, { type: 'error' });
          }
        })
    },
    //显示项目
    showProjectList(){

      if(!this.checkContractId() ){
          return ;
      }
      var params =  this.searchForm;
      params.notRdProjectContractId = this.formContractBudget.pkId;
      params.size = 20
      const data = common.paramsFormat(params)
      queryPager(data).then(res => {
        if (res.data.status === 200) {
          this.projectList = res.data.results
          this.currProject = {};
          this.isShowProjectListFlag = true;
        }
      })
    },
    onProjectListCancel(){
      this.isShowProjectListFlag = false;
    },
    onProjectListConfirm(){
      var selectProject = this.$refs.projectTable.selection
      if(!selectProject||selectProject.length!=1){
          this.$message.error('请选择一条数据')
          return;
      }
      var flag = false;
      this.$refs.ratioForm2.validate((valid)=>{
          flag =  valid;
      })
      if(!flag){
        return;
      }

      const loading = common.loading(this)
      saveRdProjectContractRelation({rdProjectId:selectProject[0].pkId,rdProjectContractId:this.formContractBudget.pkId,ratio:this.currProject.ratio}).then(res => {
        loading.close()
        if (res.data.status === 200) {
          this.$message.success('保存成功')
          this.isShowProjectListFlag = false;
          this.queryRdProjectContractRelation();
        } else {
          this.$message.error(res.data.message?res.data.message:'保存失败')
        }
      }).catch(error => {
        loading.close()
        this.$message.error(error)
      })
    },
    clearSearch(){
      this.searchForm =  {
        projectCode: '', // 项目编号
        projectName: '', // 项目名称
        competentDept: '', // 主管部门
        sysName: '' // 关联系统
      }
    },



    showUpdateProject(projectInfo){

      if(!this.checkContractId() ){
          return ;
      }
      this.currProject = Object.assign({},projectInfo);

       this.isShowUpdateProjectFlag = true;
    },

    deleteProject(row,index){
      this.$confirm('确认删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        deleteRdProjectContractRelation({ pkId: row.pkId }).then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('删除成功')
            this.queryRdProjectContractRelation();
          } else {
            this.$message.error(res.data.message?res.data.message:'删除失败')
          }
        }).catch(error => {
          loading.close()
          this.$message.error(error)
        })
      }).catch(error => { console.log(error) })
    },

    onProjectCancel(){
      this.isShowUpdateProjectFlag = false;
    },
    onProjectConfirm(){
      var flag = false;
      this.$refs.ratioForm.validate((valid)=>{
                flag =  valid;
      })
      if(!flag){
        return;
      }
      const loading = common.loading(this)
      saveRdProjectContractRelation(this.currProject).then(res => {
        loading.close()
        if (res.data.status === 200) {
          this.$message.success('保存成功')
          this.isShowUpdateProjectFlag = false;
          this.queryRdProjectContractRelation();
        } else {
          this.$message.error(res.data.message?res.data.message:'保存失败')
        }
      }).catch(error => {
        loading.close()
        this.$message.error(error)
      })
    },





    // 返回按钮
    back() {
      this.$router.push({ name: 'contract' })
    },
    // 编辑按钮
    edit(formName) {
      if (formName === 'formContractBudget') {
        this.unchangeableContractBudget = false
      } else if (formName === 'formProAssociation') {
        this.unchangeableProAssociation = false
      } else if (formName === 'formContractPayInfo') {
        this.unchangeableContractPayInfo = false
      } else if (formName === 'formFinancialAccountInfo') {
        this.unchangeableFinancialInfo = false
      }
    },
    // 取消按钮
    cancel(formName) {
      this.$confirm('是否放弃当前编辑',{
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
         if (formName === 'formContractBudget') {
            this.unchangeableContractBudget = true
            this.formContractBudget.budgetCode =  this.formContractBudgetBu.budgetCode
            this.formContractBudget.budgetName =  this.formContractBudgetBu.budgetName
            this.formContractBudget.budgetAmount =  this.formContractBudgetBu.budgetAmount
            this.formContractBudget.type =  this.formContractBudgetBu.type
            this.formContractBudget.supplier =  this.formContractBudgetBu.supplier
            this.formContractBudget.approvedDate =  this.formContractBudgetBu.approvedDate
            this.formContractBudget.softwareManpower =  this.formContractBudgetBu.softwareManpower
            this.formContractBudget.assistManpower =  this.formContractBudgetBu.assistManpower
            this.formContractBudget.riskManagementManpower =  this.formContractBudgetBu.riskManagementManpower
            this.calculateTotalManpower();
          } else if (formName === 'formProAssociation') {
            this.unchangeableProAssociation = true
            this.formContractBudget.isResearchProject =  this.formContractBudgetBu.isResearchProject
            this.formContractBudget.techInnoPoint = this.formContractBudgetBu.techInnoPoint
          } else if (formName === 'formContractPayInfo') {
            this.formContractBudget.contractCode =  this.formContractBudgetBu.contractCode
            this.formContractBudget.contractName =  this.formContractBudgetBu.contractName
            this.formContractBudget.contractStartDate =  this.formContractBudgetBu.contractStartDate
            this.formContractBudget.contractEndDate =  this.formContractBudgetBu.contractEndDate

            this.formContractBudget.maintenanceStartDate =  this.formContractBudgetBu.maintenanceStartDate
            this.formContractBudget.maintenanceEndDate =  this.formContractBudgetBu.maintenanceEndDate

            this.formContractBudget.supplierName =  this.formContractBudgetBu.supplierName
            this.formContractBudget.contractTaxPoints =  this.formContractBudgetBu.contractTaxPoints
            this.formContractBudget.paymentScheduling =  this.formContractBudgetBu.paymentScheduling
            this.formContractBudget.acceptancePhase =  this.formContractBudgetBu.acceptancePhase

            this.formContractBudget.contractAmount =  this.formContractBudgetBu.contractAmount
            this.formContractBudget.invoiceType =  this.formContractBudgetBu.invoiceType
            this.formContractBudget.contractStartDateFlag =  this.formContractBudgetBu.contractStartDateFlag
            this.formContractBudget.contractEndDateFlag =  this.formContractBudgetBu.contractEndDateFlag

            this.unchangeableContractPayInfo = true
          } else if (formName === 'formFinancialAccountInfo') {
            this.formContractBudget.accountSubject =  this.formContractBudgetBu.accountSubject
            this.formContractBudget.expenseType =  this.formContractBudgetBu.expenseType
            this.formContractBudget.reimbursementItem =  this.formContractBudgetBu.reimbursementItem
            this.formContractBudget.isInvoice =  this.formContractBudgetBu.isInvoice
            this.formContractBudget.remarkLan =  this.formContractBudgetBu.remarkLan
            this.unchangeableFinancialInfo = true
          }

        }).catch(() => {

        })

    },
    // 保存按钮
    save(formName) {
      if (formName !== 'formContractBudget') {
        if(!this.checkContractId() ){
              return ;
        }
      }

      if (formName === 'formContractBudget') {

        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.formContractBudget.pkId){
               this.formContractBudget.rdProjectContractId = this.formContractBudget.pkId;
            }
             updateContractInfo(this.formContractBudget).then(res => {
              if (res.data.status === 200) {
                this.unchangeableContractBudget = true
                this.budgetCodeFlag = false
                this.formContractBudget.pkId = res.data.results.pkId;
                this.formContractBudget.rdProjectContractId = res.data.results.pkId;
                this.$message.success("已保存合同预算信息！")
                console.info(this.formContractBudget)

                this.formContractBudgetBu.budgetCode =  this.formContractBudget.budgetCode
                this.formContractBudgetBu.budgetName =  this.formContractBudget.budgetName
                this.formContractBudgetBu.budgetAmount =  this.formContractBudget.budgetAmount
                this.formContractBudgetBu.type =  this.formContractBudget.type

			          this.formContractBudgetBu.supplier =  this.formContractBudget.supplier
                this.formContractBudgetBu.approvedDate =  this.formContractBudget.approvedDate

                this.formContractBudgetBu.totalManpower =  this.formContractBudget.totalManpower
                this.formContractBudgetBu.softwareManpower =  this.formContractBudget.softwareManpower
                this.formContractBudgetBu.assistManpower =  this.formContractBudget.assistManpower
                this.formContractBudgetBu.riskManagementManpower =  this.formContractBudget.riskManagementManpower
              }else{
                  this.$alert(res.data.message, { type: 'error' });
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      } else if (formName === 'formProAssociation') {
        if (this.formContractBudget.isResearchProject === "Y" && this.relationProjectList.length <= 0) {
          this.$alert('关联项目不能为空',{ type: 'error' })
          return
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateContractResearchProject(this.formContractBudget).then(res => {
              if (res.data.status === 200) {
                this.unchangeableProAssociation = true
                this.$message.success("已保存研发项目关联！")


                this.formContractBudgetBu.isResearchProject =  this.formContractBudget.isResearchProject
                this.formContractBudgetBu.techInnoPoint =  this.formContractBudget.techInnoPoint

              }else{
                  this.$alert(res.data.message, { type: 'error' });
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      } else if (formName === 'formContractPayInfo') {
        var checkFlag = true;
        this.$refs['formContractPayInfo'].validate((valid) => {
           checkFlag = valid;
         })
        if(!checkFlag){
          return;
        }

        if (this.formContractBudget.contractCode === "" || this.formContractBudget.contractCode.trim().length == 0) {
          this.$alert('合同编码不能为空',{ type: 'error' })
          return
        }
        if (this.formContractBudget.contractName === "" || this.formContractBudget.contractName.trim().length == 0) {
          this.$alert('合同名称不能为空',{ type: 'error' })
          return
        }
        if (this.formContractBudget.contractAmount === "" || this.formContractBudget.contractAmount.trim().length == 0) {
          this.$alert('金额不能为空!',{ type: 'error' })
          return
        }else if (Number(this.formContractBudget.contractAmount)<0) {
          this.$alert('金额不能为负数!',{ type: 'error' })
          return
        }
        if(Number(this.formContractBudget.contractAmount)>Number(this.formContractBudget.budgetAmount)){
          this.$alert('金额不能超过预算金额',{ type: 'error' })
          return
        }else if ((Number(this.formContractBudget.budgetAmount) -Number(this.formContractBudget.contractAmount))/Number(this.formContractBudget.budgetAmount) > 0.2){
          this.$alert('金额与预算金额偏差超过20%',{type: 'warning'})
        }
        updateContractPayInfo(this.formContractBudget).then(res => {
          if (res.data.status === 200) {
            this.unchangeableContractPayInfo = true
            this.$message.success("已保存合同及支付信息!")
            this.contractCodeSaveFlag = true

            this.formContractBudgetBu.contractCode =  this.formContractBudget.contractCode
            this.formContractBudgetBu.contractName =  this.formContractBudget.contractName
            this.formContractBudgetBu.contractStartDate =  this.formContractBudget.contractStartDate
            this.formContractBudgetBu.contractEndDate =  this.formContractBudget.contractEndDate

            this.formContractBudgetBu.maintenanceStartDate =  this.formContractBudget.maintenanceStartDate
            this.formContractBudgetBu.maintenanceEndDate =  this.formContractBudget.maintenanceEndDate

            this.formContractBudgetBu.supplierName =  this.formContractBudget.supplierName
            this.formContractBudgetBu.contractTaxPoints =  this.formContractBudget.contractTaxPoints
            this.formContractBudgetBu.paymentScheduling =  this.formContractBudget.paymentScheduling
            this.formContractBudgetBu.acceptancePhase =  this.formContractBudget.acceptancePhase

            this.formContractBudgetBu.contractAmount =  this.formContractBudget.contractAmount
            this.formContractBudgetBu.invoiceType =  this.formContractBudget.invoiceType
            this.formContractBudgetBu.contractStartDateFlag =  this.formContractBudget.contractStartDateFlag
            this.formContractBudgetBu.contractEndDateFlag =  this.formContractBudget.contractEndDateFlag

          }else{
              this.$alert(res.data.message, { type: 'error' });
          }
        })
      } else if (formName === 'formFinancialAccountInfo') {
      var checkFlag = true;
        this.$refs['formFinancialAccountInfo'].validate((valid) => {
           checkFlag = valid;
         })
         if(!checkFlag){
           return;
         }
        updateContractFinancialInfo(this.formContractBudget).then(res => {
          if (res.data.status === 200) {
            this.unchangeableFinancialInfo = true
            this.$message.success("已保存财务核算信息!")

            this.formContractBudgetBu.accountSubject =  this.formContractBudget.accountSubject
            this.formContractBudgetBu.expenseType =  this.formContractBudget.expenseType
            this.formContractBudgetBu.reimbursementItem =  this.formContractBudget.reimbursementItem
            this.formContractBudgetBu.isInvoice =  this.formContractBudget.isInvoice
            this.formContractBudgetBu.remarkLan =  this.formContractBudget.remarkLan


          }else{
              this.$alert(res.data.message, { type: 'error' });
          }
        })
      }
    },
    checkContractId(obj){
        if(!this.formContractBudget||!this.formContractBudget.pkId){
            this.$alert('请先保存合同预算信息', { type: 'error' });
            return false;
        }
        if(obj){
          obj.rdProjectContractId = this.formContractBudget.pkId;
        }
        this.formContractBudget.rdProjectContractId = this.formContractBudget.pkId

        return true;
    },
    // 合同预算信息 审批资料 上传
    handleApprovalSuccess(response, file, fileList) {
      // 处理上传成功的回调函数
      this.queryFile();
      // this.formProjectApprovalMat.tableApprovalMaterials = fileList.map(item => {
      //   return {
      //     fileName: item.name,
      //     uploadedDate: new Date().toLocaleString()
      //   }
      // })
    },
    // 查询采购系统
    submitBtn() {
      let params = {}
      params.contractCode = this.contract.contractCode
      params.contractName = this.contract.contractName
      params.size = this.sizel
      params.index = this.index
      this.getPagerList(params)
    },
    getPagerList(params) {
      const data = common.paramsFormat(params)
      directConnection(data).then(res => {
        if (res.data.status === 200) {
          this.contractData = res.data.results
          this.totalNumber = res.data.total
          if (this.totalNumber == 0) {
            this.contractDataTable = false
          } else {
            this.contractDataTable = true
          }
          console.log(res.data.results, '==============getPagerList')
        } else {
          this.$message(res.data.message,{ type: 'error' })
        }
      })
    },
    // 关闭弹框
    handleClose() {
      this.purchaseDirect = false
      this.contractData = []
      this.contractDataTable = true
    },
    clearContract() {
      this.contract.contractCode = ''
      this.contract.contractName = ''
    },
    /**
      * 分页触发
    */
    getPager: function(size, index) {
      const params = {}
      params.contractCode = this.contract.contractCode
      params.contractName = this.contract.contractName
      params.size = size
      params.index = index
      this.sizel = size
      this.index = index
      this.getPagerList(params)
    },
    // 确认导入
    contractImport() {
      this.contractInto.budgetCode = this.formContractBudget.budgetCode
      this.contractInto.pkId = this.formContractBudget.pkId
      console.log(this.contractInto,"========contractInto=======")
      let params = {}
      params = this.contractInto
      contractPurchaseImport(params).then(res => {
        if (res.data.status === 200) {
            this.$message.success("导入成功!")
            this.handleClose()
            this.init()
          }else{
            this.$message(res.data.message, { type: 'error' });
          }
      })
    },
    // 选中状态
    tableRowClassName({row}) {
      if (row && row.budgetName !== undefined) {
        return 'row-disabled';
      }
      return ''; // 明确返回空字符串，避免返回 undefined
    },
    // 单选
    handleCurrentChange(val) {
      if (val && val.budgetName !== undefined) {
        this.$message("该合同已被-"+val.budgetName + "-预算使用", { type: 'error' });
      } else {
        this.contractInto = val
        console.log(this.contractInto,"===============")
      }

    },
    // 合同预算信息 审批资料 删除
    tableApprovalMaterialsDelete() {

    },
    // 计算合同总人月
    calculateTotalManpower() {
      this.formContractBudget.totalManpower = (
        (Number(this.formContractBudget.softwareManpower) || 0) + 
        (Number(this.formContractBudget.assistManpower) || 0) + 
        (Number(this.formContractBudget.riskManagementManpower) || 0)
      ).toFixed(2)
    },
    // 研发项目资源管理 项目外部资源 详情按钮
    tableProjectOutsideDetail() {
    }
  }
}
</script>
<style scoped>

.row-disabled {
  color: #999;
  background-color: #f5f5f5;
  pointer-events: none;
}
/* bin修改 */
.contentMain {
  width: 100%;
  height: 100%;
  font-size: 16px;
  padding: 10px 20px;
}
/* 导航菜单栏 */
.nav {
  width: 100%;
  /* height: 30px; */
  background-color: #ffffff;
  display: flex;
  justify-content: space-between;
  padding: 0 25px;
}
.nav_name {
  width: 180px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}
.wrapper_right {
  display: flex;
  align-items: center;
}
.topRightButton{
  margin-left: 10px;
}
.lastTopRightButton{
  margin-left: 60px;
}
.module_button{
  position: relative;
  right: 0;
}
.module{
  width: 100%;
  background-color: #ffffff;
  padding: 0 25px;
  margin-top: 5px;
}
.module_title{
  width: 100%;
  border-bottom: 1px solid #000;
  display: flex;
  justify-content: space-between;
}
.module_name{
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}
.title_tips{
  height: 24px;
  font-size: 15px;
  color: red;
  line-height: 24px;
  margin-left:30px;
  display: inline-block;
}
.red_tip{
  display: flex;
}
.tips{
  margin-left: 10px;
  color: red;
  font-size: 20px;
}
.upload_box{
  display: flex;
  align-items: center;
}
.space{
  height: 1px;
  margin-top: 19px;
}
.end_date{
  margin-left: 100px;
}
.taxPoints{
  margin-left: 100px;
}
.projectCodeInput{
  display: flex;
}
.projectCodeTips{
  margin-left: 10px;
  color: red;
  font-size: 14px;
}
.basicInfo_input_box{
  width: 300px;
}
.basicInfo_input_boxR{
  width: 300px;
  height: 100px;
  line-height: 100px
}
.resourceMge_input_box{
  width: 300px;
}
.techInnoPoint_input_box{
  width: 900px;
}
.table p{
  /* width: 180px; */
  height: 24px;
  font-size: 14px;
  font-weight: 700;
  color: #606266;
  line-height: 24px;
  display: inline-block;
}
.el-table--border, .el-table--group {
  border: 1px solid #a9a9a9;
}
.uploadApprovalMaterials{
  margin-left: 30px;
}
.uploadProjectResult{
  margin-left: 30px;
}
.flex_layout{
  display: flex;
}
.contract_payment{
  width: 130px;
  height: 36px;
  line-height: 36px;
  vertical-align: middle;
  float: left;
  font-size: 14px;
  color: #606266;
  font-weight: 700;
}
.contract_payment_result{
  height: 36px;
  line-height: 36px;
  vertical-align: middle;
  float: left;
  font-size: 14px;
  color: #000;
  font-weight: 700;
}
.contract_payment_tips{
  margin-left: 20px;
  height: 36px;
  line-height: 36px;
  vertical-align: middle;
  float: left;
  font-size: 14px;
  color: red;
  font-weight: 400;
}
.margin-left120{
  margin-left: 120px;
}
.width35{
  width:35px;
}
/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
.centered-box {
  text-align: center; /* 文本居中 */
  margin: 0 auto;     /* 块级元素水平居中（需设置宽度） */
  width: 700px;
  border: 1px solid #b6b6b6; /* 添加边框 */
  padding: 10px;
  border-radius: 0px; /* 可选：圆角边框 */
}

</style>

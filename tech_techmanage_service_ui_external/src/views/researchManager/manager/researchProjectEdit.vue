<!-- 添加系统信息 -->
<template>
  <div class="container">
    <!-- 项目详情 -->
    <div class="nav">
      <p class="nav_name">项目详情</p>
      <div class="wrapper_right">
        <el-button :size="size" type="primary" class="topRightButton" v-show='formProjectBasicInfo&&formProjectBasicInfo.pkId'
        @click="deleteProject"  v-if='isEdit!=0' v-hasFunc="['1151_LICENCE']">删 除</el-button>
        <!-- <el-button :size="size" class="lastTopRightButton" @click="back">返 回</el-button> -->
      </div>
    </div>

    <!-- 项目基础信息 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">研发项目基础信息</p>
          <p class="title_tips">!   表示该项非必填，后续需完善</p>
        </div>
        <div class="wrapper_right" v-if='isEdit!=0'>
          <el-button :disabled="!unchangeableBasicInfo" :size="size" type="primary" class="topRightButton module_button" @click="edit('formProjectBasicInfo')"
                      v-hasFunc="['11521_LICENCE']">编 辑</el-button>
          <el-button
            v-hasFunc="['11521_LICENCE']"
            v-if="!unchangeableBasicInfo"
            :size="size"
            type = "primary"
            class="topRightButton module_button"
            @click="cancel('formProjectBasicInfo')"
          >取 消</el-button>
          <el-button :disabled="unchangeableBasicInfo" :size="size" class="topRightButton module_button" @click="save('formProjectBasicInfo')"
                      v-hasFunc="['11522_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formProjectBasicInfo" :model="formProjectBasicInfo" :rules="rules" label-position="left" label-width="120px" >
          <el-form-item label="项目编号" prop="projectCode">
            <div class="projectCodeInput">
              <el-input v-model="formProjectBasicInfo.projectCode"  placeholder="格式为RD+开始年份+项目序号"
              :disabled="unchangeableBasicInfo||(formProjectBasicInfo.pkId)" class="basicInfo_input_box"/>
              <div class="projectCodeTips">项目编号保存后不可再修改！</div>
            </div>
          </el-form-item>
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="formProjectBasicInfo.projectName" :disabled="unchangeableBasicInfo" class="basicInfo_input_box"/>
          </el-form-item>
          <div class="project_date">
            <el-form-item label="项目开始时间" prop="startDate">
              <el-date-picker :disabled="unchangeableBasicInfo" @change="chectStartDate" :picker-options="pickerOptionsStart" value-format="yyyy-MM-dd" v-model="formProjectBasicInfo.startDate"   class="basicInfo_input_box" placeholder="请选择项目开始时间"/>
            </el-form-item>
            <el-form-item class="end_date" label="项目结束时间" prop="endDate">
              <div class="red_tip">
                <el-date-picker :disabled="unchangeableBasicInfo" @change="chectEndDate" :picker-options="pickerOptionsEnd" value-format="yyyy-MM-dd" v-model="formProjectBasicInfo.endDate"  class="basicInfo_input_box" placeholder="请选择项目结束时间"/>
                <div v-if="!formProjectBasicInfo.endDate" class="tips">!</div>
              </div>
            </el-form-item>
          </div>
          <el-form-item label="项目状态" prop="projectState">
            <!-- <me-select v-model="formProjectBasicInfo.projectState"
                :dicttypeid="'RD_PROJECT_STATE'"  :disabled="unchangeableBasicInfo"  class="basicInfo_input_box" placeholder="项目状态"/> -->
            <el-select v-model="formProjectBasicInfo.projectState" :disabled="unchangeableBasicInfo" class="basicInfo_input_box" placeholder="项目状态">
              <el-option
                v-for="item in getTypeDicts('RD_PROJECT_STATE')"
                :key="item.dictId"
                :label="item.dictName"
                :value="item.dictId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="关联系统" prop="sysName">
            <div class="red_tip">
              <el-input :disabled="unchangeableBasicInfo" v-model="formProjectBasicInfo.sysName" class="basicInfo_input_box" maxlength="300"/>
              <div v-if="!formProjectBasicInfo.sysName" class="tips">!</div>
            </div>
          </el-form-item>
          <div class="space"/>
        </el-form>
      </div>
    </div>
    <!-- 研发项目资源管理 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">研发项目资源管理</p>
          <p class="title_tips">!   表示该项非必填，但后续需完善</p>
        </div>
        <div class="wrapper_right"  v-if='isEdit!=0'>
          <el-button :disabled="!unchangeableResourceMge" :size="size" type="primary" class="topRightButton module_button" @click="edit('formProjectResourceMge')"
          v-hasFunc="['11531_LICENCE']">编 辑</el-button>
          <el-button
            v-hasFunc="['11531_LICENCE']"
            v-if="!unchangeableResourceMge"
            :size="size"
            type = "primary"
            class="topRightButton module_button"
            @click="cancel('formProjectResourceMge')"
          >取 消</el-button>
          <el-button :disabled="unchangeableResourceMge" :size="size" class="topRightButton module_button" @click="save('formProjectResourceMge')"
          v-hasFunc="['11532_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formProjectResourceMge" :model="formProjectResourceMge" :rules="rules" label-position="left" label-width="120px" >
          <el-form-item label="项目主管部门" prop="competentDept">
            <div class="red_tip">
              <!-- <el-input :disabled="unchangeableResourceMge" v-model="formProjectBasicInfo.competentDept" class="resourceMge_input_box"/> -->
              <!-- <el-cascader filterable v-model="formProjectBasicInfo.competentDept" clearable :options="orgTreeData" :disabled="unchangeableResourceMge"
               :props="defaultProp" :show-all-levels="ture" class="resourceMge_input_box" @change="orgChange"/> -->
               <el-select v-model="formProjectBasicInfo.competentDept" filterable @change="orgChange" :disabled="unchangeableResourceMge" clearable placeholder="请选择">
                  <el-option
                    v-for="item in orgDepartment"
                    :key="item.depid"
                    :label="item.location"
                    :value="item.depid">
                  </el-option>
                </el-select>
              <div v-if="!formProjectBasicInfo.competentDept" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="项目参与部门" prop="participatingDate">
            <div class="red_tip">
              <!-- <el-input :disabled="unchangeableResourceMge" v-model="formProjectBasicInfo.participatingDept" class="resourceMge_input_box"/> -->
              <!-- <el-cascader filterable v-model="formProjectBasicInfo.participatingDate" clearable :options="orgTreeData" :disabled="unchangeableResourceMge"
               :props="participatingDefaultProp" :show-all-levels="ture"  class="resourceMge_input_box" @change="participatingOrgChange"/> -->
              <el-select v-model="formProjectBasicInfo.participatingDate" @change="participatingOrgChange" multiple filterable :disabled="unchangeableResourceMge"  clearable placeholder="请选择">
                  <el-option
                    v-for="item in orgDepartment"
                    :key="item.depid"
                    :label="item.location"
                    :value="item.depid">
                  </el-option>
                </el-select>
              <div v-if="!formProjectBasicInfo.participatingDate || formProjectBasicInfo.participatingDate.length==0 " class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="项目负责人" prop="headUser">
            <div class="red_tip">
              <el-input :disabled="unchangeableResourceMge" v-model="formProjectBasicInfo.headUser" class="resourceMge_input_box"/>
              <div v-if="!formProjectBasicInfo.headUser" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="研发负责人" prop="devUser">
            <div class="red_tip">
              <el-input :disabled="unchangeableResourceMge" v-model="formProjectBasicInfo.devUser" class="resourceMge_input_box"/>
              <div v-if="!formProjectBasicInfo.devUser" class="tips">!</div>
            </div>
          </el-form-item>
          <div class="tableProjectInside table">

            <div class="upload_box">
              <div class="upload_box">
                <p>项目内部资源</p>
                <!-- <el-button v-if="!unchangeableResourceMge" :size="size" type="primary" class="topRightButton module_button" @click="showAddUser()">新 增</el-button> -->
              </div>
            </div>
            <el-table
              :data="userList"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="300"
              border
              style="width:700px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="chargeName" label="部门" width="150"/>
              <el-table-column prop="badge" label="工号" width="150"/>
              <el-table-column prop="empname" label="姓名" width="150"/>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <!-- <el-button type="text" size="small" @click="showUpdateUser(scope.row)" :disabled="unchangeableResourceMge">详情</el-button> -->
                  <el-button type="text" size="small" @click="delUser(scope.row)" >详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="tableProjectOutside table">

            <div class="upload_box">
              <p>项目外部资源</p>
              <!--el-button v-if="!unchangeableResourceMge" :size="size" type="primary" class="topRightButton module_button" @click="showAddContract()">新 增</el-button-->
            </div>
            <el-table
              :data="contractList"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="300"
              border
              style="width:1400px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="budgetCode" label="预算编号" width="150"/>
              <el-table-column prop="budgetName" label="预算名称" width="150"/>
              <el-table-column prop="contractCode" label="合同编号" width="150"/>
              <el-table-column prop="contractName" label="合同名称" width="150"/>
              <el-table-column prop="contractStartDate" label="合同开始时间" width="150"/>
              <el-table-column prop="contractAmount" label="合同金额（万元）" width="150"/>
              <el-table-column prop="paymentScheduling" label="支付进度(%)" :formatter="formatPaymentScheduling" width="150"/>
              <el-table-column prop="projectAllocationRatio" label="项目分摊比例(%)" :formatter="formatProjectAllocationRatio" width="150"/>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <!--el-button type="text" size="small" @click="showUpdateContract(scope.row)" :disabled="unchangeableResourceMge">修改</el-button>
                  <el-button type="text" size="small" @click="delContract(scope.row)" :disabled="unchangeableResourceMge">删除</el-button-->
                  <el-button type="text" size="small" @click="showContactDetails(scope.row)" >详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="space"/>
        </el-form>
      </div>
    </div>
    <!-- 研发项目立项资料 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">研发项目立项资料</p>
          <p class="title_tips">!   表示该项非必填，但后续需完善</p>
        </div>
        <div class="wrapper_right"  v-if='isEdit!=0'>
          <el-button :disabled="!unchangeableApprovalMat" :size="size" type="primary" class="topRightButton module_button" @click="edit('formProjectApprovalMat')"
                      v-hasFunc="['11542_LICENCE']">编 辑</el-button>
          <el-button
            v-hasFunc="['11542_LICENCE']"
            v-if="!unchangeableApprovalMat"
            :size="size"
            type = "primary"
            class="topRightButton module_button"
            @click="cancel('formProjectApprovalMat')"
          >取 消</el-button>
          <el-button :disabled="unchangeableApprovalMat" :size="size" class="topRightButton module_button" @click="save('formProjectApprovalMat')"
                      v-hasFunc="['11541_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formProjectApprovalMat" :model="formProjectApprovalMat" :rules="rules" label-position="top" label-width="120px" >
          <el-form-item label="业务创新点" prop="busInnova">
            <div class="red_tip">
              <el-input :disabled="unchangeableApprovalMat" v-model="formProjectBasicInfo.busInnova" placeholder="请输入，限制2000字" type="textarea" show-word-limit maxlength="2000" class="approvalMat_input_box"/>
              <div v-if="!formProjectBasicInfo.busInnova" class="tips">!</div>
            </div>
          </el-form-item>
          <el-form-item label="技术创新点" prop="techInnova">
            <div class="red_tip">
              <el-input :disabled="unchangeableApprovalMat" v-model="formProjectBasicInfo.techInnova" placeholder="请输入，限制2000字" type="textarea" show-word-limit maxlength="2000" class="approvalMat_input_box"/>
              <div v-if="!formProjectBasicInfo.techInnova" class="tips">!</div>
            </div>
          </el-form-item>
          <div class="tableProjectFeasibility table">
            <div class="upload_box">
              <p>项目可行性分析报告</p>
              <el-upload v-if='!unchangeableApprovalMat'
                :on-success="handleFeasibilitySuccess"
                :file-list="formProjectApprovalMat.tableProjectFeasibility"
                :show-file-list="false"
                class="uploadButton"
                :action="uploadUrl"
                :data='fileInfo1'
                list-type="text"
                :before-upload="handleBeforeUpload1"
              >
                <el-button slot="trigger"   size="small" type="primary">上传文件</el-button>
              </el-upload>
            </div>
            <el-table
              :data="fileList1"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="200"
              border
              style="width:750px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="fileName" label="文件名称" width="300"/>
              <!-- <el-table-column prop="createDate" label="上传时间" width="180"/> -->
              <el-table-column label="操作" >
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="showUpdateFileName(scope.row)" :disabled="unchangeableApprovalMat"
                  v-hasFunc="['11546_LICENCE']">修改</el-button>
                  <el-button :disabled="unchangeableApprovalMat" type="text" size="small" @click="deleteFile('1',scope.row,scope.$index)"
                  v-hasFunc="['11545_LICENCE']">删除</el-button>
                  <el-button  type="text" size="small" @click="downloadFile('1',scope.row,scope.$index)"
                  v-hasFunc="['11548_LICENCE']">下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="tableProjectProposal table">
            <div class="upload_box">
              <p>项目立项计划书</p>
              <el-upload v-if="!unchangeableApprovalMat"
                :on-success="handleProposalSuccess"
                :file-list="formProjectApprovalMat.tableProjectProposal"
                :show-file-list="false"
                class="uploadButton"
                :action="uploadUrl"
                list-type="text"
                :data='fileInfo2'
                :before-upload="handleBeforeUpload2"
              >
                <el-button slot="trigger" size="small" type="primary">上传文件</el-button>
              </el-upload>
            </div>
            <el-table
              :data="fileList2"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="200"
              border
              style="width:750px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="fileName" label="文件名称" width="300"/>
              <!-- <el-table-column prop="createDate" label="上传时间" width="180"/> -->
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="showUpdateFileName(scope.row)" :disabled="unchangeableApprovalMat"
                  v-hasFunc="['11544_LICENCE']">修改</el-button>
                  <el-button :disabled="unchangeableApprovalMat" type="text" size="small" @click="deleteFile('2',scope.row,scope.$index)"
                  v-hasFunc="['11543_LICENCE']">删除</el-button>
                  <el-button  type="text" size="small" @click="downloadFile('2',scope.row,scope.$index)"
                  v-hasFunc="['11547_LICENCE']">下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="space"/>
        </el-form>
      </div>
    </div>
    <!-- 研发项目成果资料 -->
    <div class="module">
      <div class="module_title">
        <div>
          <p class="module_name">研发项目成果资料</p>
          <p class="title_tips">!   表示该项非必填，但后续需完善</p>
        </div>
        <div class="wrapper_right"  v-if='isEdit!=0'>
          <el-button :disabled="!unchangeableProjectResult" :size="size" type="primary" class="topRightButton module_button" @click="edit('formProjectResult')"
                      v-hasFunc="['11552_LICENCE']">编 辑</el-button>
          <el-button
            v-hasFunc="['11552_LICENCE']"
            v-if="!unchangeableProjectResult"
            :size="size"
            type = "primary"
            class="topRightButton module_button"
            @click="cancel('formProjectResult')"
          >取 消</el-button>
          <el-button :disabled="unchangeableProjectResult" :size="size" class="topRightButton module_button" @click="save('formProjectResult')"
                      v-hasFunc="['11551_LICENCE']">保 存</el-button>
        </div>
      </div>
      <div class="space"/>
      <div class="module_input">
        <el-form ref="formProjectResult" :model="formProjectResult" :rules="rules" label-position="top" label-width="120px" >
          <div class="tableProjectResult table">
            <div class="upload_box">
              <p>项目成果</p>
              <el-upload v-if="!unchangeableProjectResult"
                :on-success="handleResultlSuccess"
                :file-list="formProjectResult.tableProjectResult"
                :show-file-list="false"
                class="uploadButton"
                :action="uploadUrl"
                list-type="text"
                :data='fileInfo3'
                :before-upload="handleBeforeUpload3"
              >
                <el-button slot="trigger"  size="small" type="primary">上传文件</el-button>
              </el-upload>
            </div>
            <el-table
              :data="fileList3"
              :cell-style="{borderColor:'#a9a9a9',padding:'0'}"
              :header-cell-style="{borderColor:'#a9a9a9'}"
              :row-style="{height:'20px'}"
              height="200"
              border
              style="width:750px"
            >
              <el-table-column label="序号" type="index" fixed width="50"/>
              <el-table-column prop="fileName" label="文件名称" width="300"/>
              <el-table-column prop="createDate" label="上传时间" width="180"/>
              <el-table-column label="操作"  v-if='isEdit!=0'>
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="showUpdateFileName(scope.row)" :disabled="unchangeableProjectResult"
                  v-hasFunc="['11554_LICENCE']">修改</el-button>
                  <el-button :disabled="unchangeableProjectResult" type="text" size="small" @click="deleteFile('3',scope.row,scope.$index)"
                  v-hasFunc="['11553_LICENCE']">删除</el-button>
                  <el-button  type="text" size="small" @click="downloadFile('3',scope.row,scope.$index)"
                  v-hasFunc="['11555_LICENCE']">下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="space"/>
        </el-form>
      </div>
    </div>

    <el-dialog
      title="人员"
      :visible.sync="isShowAddUserFlag"
      width="30%"
      @before-close="onUserCancel"
    >
      <el-form :model="userInfo" ref="userForm" :rules="userRules" label-width="100px">
        <el-form-item label="部门" prop="deptName">
          <el-input v-model="userInfo.deptName" placeholder="部门"></el-input>
        </el-form-item>
        <el-form-item label="工号" prop="userId">
          <el-input v-model="userInfo.userId" placeholder="工号"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="userInfo.userName" placeholder="姓名"></el-input>
        </el-form-item>

      </el-form>

      <span slot="footer">
        <el-button @click="onUserCancel()">取 消</el-button>
        <el-button type="primary" @click="onUserConfirm()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="合同"
      :visible.sync="isShowAddContractFlag"
      width="30%"
      @before-close="onContractCancel"
    >
      <el-form :model="contractInfo" ref="contractForm" :rules="contractRules" label-width="150px">
        <el-form-item label="预算编号" prop="budgetCode">
          <el-input v-model="contractInfo.budgetCode" placeholder="预算编号"></el-input>
        </el-form-item>
        <el-form-item label="预算名称" prop="budgetName">
          <el-input v-model="contractInfo.budgetName" placeholder="预算名称"></el-input>
        </el-form-item>
        <el-form-item label="合同编号" prop="contractCode">
          <el-input v-model="contractInfo.contractCode" placeholder="合同编号"></el-input>
        </el-form-item>
         <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="contractInfo.contractName" placeholder="合同名称"></el-input>
        </el-form-item>
        <el-form-item label="合同开始时间" prop="contractStartDate">
          <el-date-picker :disabled="unchangeableBasicInfo"   value-format="yyyy-MM-dd" v-model="contractInfo.contractStartDate"   class="basicInfo_input_box" placeholder="合同开始时间"/>
        </el-form-item>
        <el-form-item label="合同金额" prop="contractAmount">
          <el-input v-model="contractInfo.contractAmount" type="number"  placeholder="合同金额"></el-input>
        </el-form-item>
        <el-form-item label="支付进度" prop="paymentSche">
          <el-input v-model="contractInfo.paymentSche" placeholder="支付进度"></el-input>
        </el-form-item>
        <el-form-item label="项目分摊比例" prop="projectAllocationRatio">
          <el-input v-model="contractInfo.projectAllocationRatio" placeholder="项目分摊比例"></el-input>
        </el-form-item>

      </el-form>

      <span slot="footer">
        <el-button @click="onContractCancel()">取 消</el-button>
        <el-button type="primary" @click="onContractConfirm()">确 定</el-button>
      </span>
    </el-dialog>

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
  </div>
</template>
<script>
import common from '@/api/common'
import department from '@/views/plugins/systemPlug/Department'
import { saveBaseInfo,updateInnerInfo,updateInnova,getDetails,uploadFile,doDelete, queryPager } from '@/api/rdmanager/rdProject'
import { saveRdProjectUser,deleteRdProjectUser,queryRdProjectUser,queryRdProjectUserNew } from '@/api/rdmanager/rdProjectUser'
import { saveRdProjectContract,deleteRdProjectContract,queryRdProjectContract } from '@/api/rdmanager/rdProjectContract'
import { updateFileName,deleteRdProjectFile,queryRdProjectFile,downloadRdProjectFile } from '@/api/rdmanager/rdProjectFile'
import PersonTable from '@/api/researchManager/rpersonTable'
import meSelect from '@/components/MeSelect'
import { getTypeDicts } from '@/utils/dict'
export default {
  name: 'ResearchProjectEdit',
  components: { department ,meSelect},
  props: {

  },
  data() {
    return {
      orgDepartment: [],
      isShowUpdateFileNameFlag:false,//显示文件名称
      isShowAddUserFlag:false,//显示用户添加框
      isShowAddContractFlag:false,//显示合同添加框
      uploadUrl:process.env.BASE_API + "/rdProjectFile/upload",
      fileInfo1:{type:'1'},//项目可行性分析报告
      fileInfo2:{type:'2'},//项目立项计划书
      fileInfo3:{type:'3'},//项目成果
      fileList1:[],
      fileList2:[],
      fileList3:[],
      currFileInfo:{},
      orgTreeData:[],
      isEdit:'',// 0 查看 1 修改  2 新增
      projectCodeFlag: true, // 项目编号是否可修改 true可修改 false不可修改
      unchangeableBasicInfo: true, // 研发项目基础信息 是否可修改，true 不可修改 false 可修改
      unchangeableResourceMge: true, // 研发项目资源管理 是否可修改，true 不可修改 false 可修改
      unchangeableApprovalMat: true, // 研发项目立项资料 是否可修改，true 不可修改 false 可修改
      unchangeableProjectResult: true, // 研发项目成果资料 是否可修改，true 不可修改 false 可修改
      size: 'small', // 按钮大小
      userInfo:{
        pkId:'',
        deptName: '', // 部门
        userId: '', // 工号
        userName: '' // 姓名
      },
      contractInfo:{
        pkId:'',
        budgetCode: '', // 预算编号
        budgetName: '', // 预算名称
        contractCode: '', // 合同编号
        contractName: '', // 合同名称
        contractStartDate: '', // 合同开始时间
        contractAmount: '', // 合同金额（万元）
        paymentSche: '', // 支付进度
        projectAllocationRatio: '' // 项目分摊比例
      },
      userList:[],  //人员列表
      contractList:[],//合同列表
      // 研发项目基础信息模块
      formProjectBasicInfo: {
        pkId:null,
        projectCode: null, // 项目编号
        projectName: '', // 项目名称
        startDate: '', // 项目开始时间
        endDate: '', // 项目结束时间
        projectState: '', // 项目状态
        sysName: '', // 关联系统

        competentDept: '', // 项目主管部门
        participatingDate:[],// 项目参与部门
        participatingDept: '', // 项目参与部门
        headUser: '', // 项目负责人
        devUser: '', // 研发负责人

        busInnova: '', // 业务创新点
        techInnova: '', // 技术创新点
      },
      // 研发项目资源管理模块
      formProjectResourceMge: {
        competentDept: '', // 项目主管部门
        participatingDept: '', // 项目参与部门
        headUser: '', // 项目负责人
        devUser: '', // 研发负责人
        // 项目内部资源
        tableProjectInside: [
          // {
          //   deptName: '金融科技部', // 部门
          //   userId: '234566', // 工号
          //   userName: '张三' // 姓名
          // }, {
          //   deptName: '产品开发部',
          //   userId: '234542',
          //   userName: '李四'
          // }, {
          //   deptName: '授信部',
          //   userId: '225452',
          //   userName: '王五'
          // }
        ],
        // 项目外部资源
        tableProjectOutside: [
          // {
          //   budgetCode: 'YS2024Q101', // 预算编号
          //   budgetName: 'xx平台一季度', // 预算名称
          //   contractCode: 'NYXJ9810MX01', // 合同编号
          //   contractName: 'xx平台一期', // 合同名称
          //   contractStartDate: '2024-01-01', // 合同开始时间
          //   contractAmount: '20', // 合同金额（万元）
          //   paymentSche: '30%', // 支付进度
          //   projectAllocationRatio: '100%' // 项目分摊比例
          // }, {
          //   budgetCode: 'YS2024Q102',
          //   budgetName: 'xx平台二季度',
          //   contractCode: 'NYXJ9810MX02',
          //   contractName: 'xx平台二期',
          //   contractStartDate: '2024-02-01',
          //   contractAmount: '20',
          //   paymentSche: '30%',
          //   projectAllocationRatio: '20%'
          // }, {
          //   budgetCode: 'YS2024Q103',
          //   budgetName: 'xx平台三季度',
          //   contractCode: 'NYXJ9810MX03',
          //   contractName: 'xx平台三期',
          //   contractStartDate: '2024-03-01',
          //   contractAmount: '20',
          //   paymentSche: '30%',
          //   projectAllocationRatio: '100%'
          // }
        ]
      },
      // 研发项目立项资料
      formProjectApprovalMat: {
        busInnova: '', // 业务创新点
        techInnova: '', // 技术创新点
        // 项目可行性分析报告
        tableProjectFeasibility: [
          // {
          //   fileName: '项目可行性分析报告1', // 文件名称
          //   uploadedDate: '2024-01-01' // 上传时间
          // }
        ],
        // 项目立项计划书
        tableProjectProposal: [
          // {
          //   fileName: '项目立项计划书1', // 文件名称
          //   uploadedDate: '2024-01-01' // 上传时间
          // }, {
          //   fileName: '项目立项计划书2', // 文件名称
          //   uploadedDate: '2024-01-02' // 上传时间
          // }, {
          //   fileName: '项目立项计划书3', // 文件名称
          //   uploadedDate: '2024-01-03' // 上传时间
          // }
        ]
      },
      // 研发项目成果资料
      formProjectResult: {
        // 项目成果
        tableProjectResult: [
          {
            fileName: 'xx系统软件著作权证书', // 文件名称
            uploadedDate: '2024-01-01' // 上传时间
          }
        ]
      },
      rules: {
        projectCode: [
          { required: true, message: '请输入项目编号', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'项目编号不能为空格',trigger:'blur'}
        ],
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'项目名称不能为空格',trigger:'blur'}
        ],
        // startDate: [
        //   {  required: true, message: '请选择开始时间', trigger: 'change' }
        // ],
        projectState: [
          { required: true, message: '请选择项目状态', trigger: 'change' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ]
      },
      userRules:{
        deptName: [
          { required: true, message: '请输入部门', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        userId: [
          { required: true, message: '请输入工号', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        userName: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
      },
      contractRules:{

        budgetCode: [
          { required: true, message: '请输入预算编号', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        budgetName: [
          { required: true, message: '请输入预算名称', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        contractCode: [
          { required: true, message: '请输入合同编号', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        contractName: [
          { required: true, message: '请输入合同名称', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        contractStartDate: [
          { required: true, message: '请输入合同开始时间', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        contractAmount: [
          { required: true, message: '请输入合同金额', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        paymentSche: [
          { required: true, message: '请输入支付进度', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
        projectAllocationRatio: [
          { required: true, message: '请输入项目分摊比例', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
      },
      currFileInfoRules:{
        newFileName: [
          { required: true, message: '请输入新文件名称', trigger: 'blur' },
          {pattern: /.*\S+.*/,message:'不能为空格',trigger:'blur'}
        ],
      },
      pickerOptionsStart: {
        disabledDate: (time) => {
          const endDateVal = this.formProjectBasicInfo.endDate
          if (endDateVal) {
            return (
              time.getTime() > new Date(endDateVal).getTime() - 1 * 24 * 60 * 60 * 1000
            )
          }
        }
      },
      pickerOptionsEnd: {
        disabledDate: (time) => {
          const beginDateVal = this.formProjectBasicInfo.startDate
          if (beginDateVal) {
            return (
              time.getTime() < new Date(beginDateVal).getTime() + 1 * 24 * 60 * 60 * 1000
            )
          }
        }
      },
      defaultProp: {
          expandTrigger:'hover',
          value:'id',
          label: "text"
      },
      participatingDefaultProp: {
          multiple: true,
          expandTrigger:'hover',
          value:'id',
          label: "text"
      }
    }
  },
  created() {
    PersonTable.quertOrgDepartment().then(res=>{
      if(res.data.status == 200){
            this.orgDepartment = res.data.results
        } else {
            this.$message.error(res.data.message)
        }
    })

    if (this.formProjectBasicInfo.projectCode) {
      this.projectCodeFlag = false
    }
    this.isEdit = this.$route.query.isEdit
    // const projectParams = this.$route.query.projectParams
    const params = {}
    let projectParams = {}
    params.projectCode = JSON.parse(this.$route.query.projectParams)
    // const data = params
    const data = common.paramsFormat(params)
    queryPager(data).then(res => {
      if (res.data.status === 200) {
        projectParams = res.data.results[0]
        console.log(projectParams, '111111111111111111111==+++')
      }
      const parseParams = projectParams || null
      console.log(parseParams, '==============================+++')
      if (Object.keys(parseParams).length !== 0) {
        this.formProjectBasicInfo = JSON.parse(JSON.stringify(parseParams))
        this.formProjectBasicInfoBu = JSON.parse(JSON.stringify(parseParams))
        if (this.formProjectBasicInfo.hasOwnProperty('participatingDept')) {
          if (this.formProjectBasicInfo.participatingDept.length !== 0) {
            this.$set(this.formProjectBasicInfo, 'participatingDate', JSON.parse(this.formProjectBasicInfo.participatingDept))
            this.$set(this.formProjectBasicInfoBu, 'participatingDate', JSON.parse(this.formProjectBasicInfo.participatingDept))
          }
        }

        // this.$set(this.formProjectBasicInfo,'participatingDate',[['10682','10885']])
        this.fileInfo1.rdProjectId = parseParams.pkId
        this.fileInfo2.rdProjectId = parseParams.pkId
        this.fileInfo3.rdProjectId = parseParams.pkId
        this.queryUserNew()
        this.queryContract()
        this.queryFile()
      }
    })

    // this.queryUser();

    let param = {}
    PersonTable.queryOrgTree(param).then(res =>{
        if(res.data.status == 200){
            this.orgTreeData = res.data.results[0].children
        } else {
            this.$message.error(res.data.message)
        }

    })

  },
  mounted(){




  },
  methods: {
    /**
     * 支付进度%
     */
    formatPaymentScheduling(row, column, cellValue) {
      if (cellValue) {
        return `${cellValue.toFixed(2)}%`
      }
    },
    formatProjectAllocationRatio(row, column, cellValue) {
      if (cellValue) {
        return `${(cellValue * 1).toFixed(2)}%`
      }
    },
    /**
     * 校验项目开始时间是否早于合同最早开始时间
     */
    chectStartDate(){
      const projectStartDate = this.formProjectBasicInfo.startDate
      const pStartDate =new Date(projectStartDate).getTime()
      this.contractList.some(e=>{
        const cStartDate = new Date(e.contractStartDate).getTime()
        if(pStartDate > cStartDate){
          this.$message.error('项目下存在早于该时间的合同，请重新选择日期')
          console.log(this.formProjectBasicInfo.startDate,e.contractStartDate)
          this.formProjectBasicInfo.startDate = ''
          return true
        }
        return false
      })
    },
    /**
     * 校验项目结束时间不得早于当日（存在合同状态为未交付的合同）
     */
    chectEndDate() {
      const contractChectList = this.contractList.filter(e=>e.paymentScheduling<90)
      if(contractChectList.length>0){
        const currentTime= new Date().getTime()
        const contractEndDate = this.formProjectBasicInfo.endDate
        const pStartDate =new Date(contractEndDate).getTime()
        if(pStartDate<currentTime){
          this.$message.error('该项目存在尚未交付的合同，请重新选择日期')
          this.formProjectBasicInfo.endDate = ''
          console.log(this.formProjectBasicInfo.endDate,new Date().toLocaleDateString,new Date(),'================')
        }
      }
    },
    onUserCancel(){
      this.isShowAddUserFlag = false;
    },
    onUserConfirm(){
        if(!this.checkRdProjectId(this.userInfo)){
          return;
        }
        this.$refs['userForm'].validate((valid) => {
          if (valid) {
            saveRdProjectUser(this.userInfo).then(res => {
              if (res.data.status === 200) {
                this.isShowAddUserFlag = false
                this.$message.success("人员添加成功!")
                this.queryUser();
              }else{
                  this.$alert(res.data.message, { type: 'error' });
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
    },
    showAddUser(){//显示添加用户弹框
       this.isShowAddUserFlag = true;
       this.userInfo = {
          deptName: '', // 部门
          userId: '', // 工号
          userName: '' // 姓名
        };
    },
    showUpdateUser(row){
      this.userInfo = Object.assign({},row);
      this.isShowAddUserFlag = true;
    },
    // delUser(row){
    //   this.$confirm('确认删除吗?', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     const loading = common.loading(this)
    //     deleteRdProjectUser({ pkId: row.pkId }).then(res => {
    //       loading.close()
    //       if (res.data.status === 200) {
    //         this.$message.success('删除成功')
    //         this.queryUser();
    //       } else {
    //         this.$message.error(res.data.message?res.data.message:'删除失败')
    //       }
    //     }).catch(error => {
    //       loading.close()
    //       this.$message.error(error)
    //     })
    //   }).catch(error => { console.log(error) })
    // },
    delUser(row){
      const params = row
      // this.$router.push({
      //   name: "RProjectAllocation",
      //   params
      // });
        const routeData = this.$router.resolve({
        name: 'RProjectAllocation',
        query: {
          params: JSON.stringify(params)
        }
      })
      // console.log(routeData.href)
      window.open(routeData.href, '_blank')
    },
    queryUser(){
      if(!this.formProjectBasicInfo||!this.formProjectBasicInfo.pkId){
          return;
      }
      queryRdProjectUser({rdProjectId:this.formProjectBasicInfo.pkId}).then(res => {
        if (res.data.status === 200) {
          this.userList = res.data.results;
          console.log(this.userList, '========+++============+++')
        }else{
            this.$alert(res.data.message, { type: 'error' });
        }
      })
    },
    queryUserNew(){
      if(this.isEdit==2)return
      queryRdProjectUserNew({projectCode:this.formProjectBasicInfo.projectCode}).then(res=>{
        if (res.data.status === 200) {
          this.userList = res.data.results;
        }else{
            this.$alert(res.data.message, { type: 'error' });
        }
      })
    },

    onContractCancel(){
      this.isShowAddContractFlag = false;
    },
    onContractConfirm(){
        if(!this.checkRdProjectId(this.contractInfo)){
          return;
        }
        this.$refs['contractForm'].validate((valid) => {
          if (valid) {
            saveRdProjectContract(this.contractInfo).then(res => {
              if (res.data.status === 200) {
                this.isShowAddContractFlag = false
                this.$message.success("合同添加成功!")
                this.queryContract();
              }else{
                  this.$alert(res.data.message, { type: 'error' });
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
    },
    showAddContract(){//显示添加用户弹框
       this.isShowAddContractFlag = true;
       this.contractInfo = {
          pkId:'',
          budgetCode: '', // 预算编号
          budgetName: '', // 预算名称
          contractCode: '', // 合同编号
          contractName: '', // 合同名称
          contractStartDate: '', // 合同开始时间
          contractAmount: '', // 合同金额（万元）
          paymentSche: '', // 支付进度
          projectAllocationRatio: '' // 项目分摊比例
        };
    },
    showUpdateContract(row){
      this.contractInfo = Object.assign({},row);
      this.isShowAddContractFlag = true;
    },
    showContactDetails(row){
      // this.$router.push({
      //   name: 'contractManagementEdit',
      //   query: {
      //     isEdit: 0,
      //     contractParams: JSON.stringify(row)
      //   }
      // })
      const routeData = this.$router.resolve({
        name: 'contractManagementEdit',
        query: {
          isEdit: 0,
          contractParams: JSON.stringify(row)
        }
      })
      window.open(routeData.href, '_blank')
    },
    delContract(row){
      this.$confirm('确认删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        deleteRdProjectContract({ pkId: row.pkId }).then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('删除成功')
            this.queryContract();
          } else {
            this.$message.error(res.data.message?res.data.message:'删除失败')
          }
        }).catch(error => {
          loading.close()
          this.$message.error(error)
        })
      }).catch(error => { console.log(error) })
    },
    queryContract(){
      if(!this.formProjectBasicInfo||!this.formProjectBasicInfo.pkId){
          return;
      }
      queryRdProjectContract({rdProjectId:this.formProjectBasicInfo.pkId}).then(res => {
        if (res.data.status === 200) {
          this.contractList = res.data.results;
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
            this.queryFile();
          } else {
            this.$message.error(res.data.message?res.data.message:'删除失败')
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
        })
      })
      // downloadRdProjectFile({rdProjectFileId: row.pkId}).then(res => {

      //   this.$confirm('是否'+ row.sourceFileName,'是否导出'+ row.sourceFileName,{confirmButtonText:'是',cancelButtonText:'否',type:'warning'}).then(_=>{

      //     common.download(res.data,self,row.sourceFileName)
      //   })
      // })
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

    onFileCancel(){
      this.isShowAddFileFlag = false;
    },
    onFileConfirm(){
        if(!this.checkRdProjectId(this.fileInfo)){
          return;
        }
        this.$refs['fileForm'].validate((valid) => {
          if (valid) {
            saveRdProjectFile(this.fileInfo).then(res => {
              if (res.data.status === 200) {
                this.isShowAddFileFlag = false
                this.$message.success("文件修改成功!")
                this.queryFile();
              }else{
                  this.$alert(res.data.message, { type: 'error' });
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
    },

    handleBeforeUpload1(file){
      if(!this.checkRdProjectId(this.fileInfo1)){
          return false;
      }
    },
    handleBeforeUpload2(file){
      if(!this.checkRdProjectId(this.fileInfo2)){
          return false;
      }
    },
    handleBeforeUpload3(file){
      if(!this.checkRdProjectId(this.fileInfo3)){
          return false;
      }
    },
    showUpdateFile(type){
      this.contractInfo = Object.assign({},row);
      this.isShowAddFileFlag = true;
    },
    queryFile(type){
      console.info('------------------------------------')
      if(!this.formProjectBasicInfo||!this.formProjectBasicInfo.pkId){
          return;
      }
      console.info({rdProjectId:this.formProjectBasicInfo.pkId,type:type},'-------------f444444444444444444-----------------------')
      queryRdProjectFile({rdProjectId:this.formProjectBasicInfo.pkId,type:type}).then(res => {
        if (res.data.status === 200) {
          var files = res.data.results;
          var fileList1 = [];
          var fileList2 = [];
          var fileList3 = [];
          for(var i=0;i<files.length;i++){
              var tmp = files[i];
              if(tmp.type=='1'){
                fileList1.push(tmp);
              }else if(tmp.type=='2'){
                fileList2.push(tmp);
              }else if(tmp.type=='3'){
                fileList3.push(tmp);
              }
          }
          this.fileList1 = fileList1;
          this.fileList2 = fileList2;
          this.fileList3 = fileList3;
        }else{
            this.$alert(res.data.message, { type: 'error' });
        }
      })
    },
    checkRdProjectId(obj){
        if(!this.formProjectBasicInfo||!this.formProjectBasicInfo.pkId){
            this.$alert('请先保存研发项目基础信息', { type: 'error' });
            return false;
        }
        if(obj){
            obj.rdProjectId = this.formProjectBasicInfo.pkId;
        }
        return true;
    },
    // 右上角总删除按钮
    deleteProject() {
      if(!this.formProjectBasicInfo||!this.formProjectBasicInfo.pkId){
        this.$message.error('数据不存在')
        return ;
      }
      this.$confirm('确认删除该研发项目的全部记录吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        doDelete({ pkId: this.formProjectBasicInfo.pkId }).then(res => {
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
    // 右上角返回按钮
    back() {
        this.$router.push({ name: 'researchProject' })
    },

    // 取消按钮
    cancel(formName) {
      this.$confirm('是否放弃当前编辑',{
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
      }).then(() => {
          if (formName === 'formProjectBasicInfo') {
            this.unchangeableBasicInfo = true
            this.formProjectBasicInfo.projectCode = this.formProjectBasicInfoBu.projectCode
            this.formProjectBasicInfo.projectName = this.formProjectBasicInfoBu.projectName
            this.formProjectBasicInfo.startDate = this.formProjectBasicInfoBu.startDate
            this.formProjectBasicInfo.endDate = this.formProjectBasicInfoBu.endDate
            this.formProjectBasicInfo.projectState = this.formProjectBasicInfoBu.projectState
            this.formProjectBasicInfo.sysName = this.formProjectBasicInfoBu.sysName
          } else if (formName === 'formProjectResourceMge') {
            this.unchangeableResourceMge = true
            this.formProjectBasicInfo.competentDept = this.formProjectBasicInfoBu.competentDept
            this.formProjectBasicInfo.participatingDate = JSON.parse(JSON.stringify(this.formProjectBasicInfoBu.participatingDate))
            this.formProjectBasicInfo.headUser = this.formProjectBasicInfoBu.headUser
            this.formProjectBasicInfo.devUser = this.formProjectBasicInfoBu.devUser

          } else if (formName === 'formProjectApprovalMat') {
            this.unchangeableApprovalMat = true
            this.formProjectBasicInfo.busInnova = this.formProjectBasicInfoBu.busInnova
            this.formProjectBasicInfo.techInnova = this.formProjectBasicInfoBu.techInnova

          } else if (formName === 'formProjectResult') {
            this.unchangeableProjectResult = true
          }
      })
    },
    // 编辑按钮
    edit(formName) {
      if (formName === 'formProjectBasicInfo') {//基本信息
        this.unchangeableBasicInfo = false
      } else if (formName === 'formProjectResourceMge') {
        this.unchangeableResourceMge = false
      } else if (formName === 'formProjectApprovalMat') {
        this.unchangeableApprovalMat = false
      } else if (formName === 'formProjectResult') {
        this.unchangeableProjectResult = false
      }
    },
    // 保存按钮
    save(formName) {
      if (formName === 'formProjectBasicInfo') {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            saveBaseInfo(this.formProjectBasicInfo).then(res => {
              if (res.data.status === 200) {

                // this.unchangeableResourceMge = true
                this.unchangeableBasicInfo = true
                this.projectCodeFlag = false;
                this.formProjectBasicInfo.pkId = res.data.results.pkId
                this.$message.success("已保存研发项目基础信息")
                this.formProjectBasicInfoBu.projectCode = this.formProjectBasicInfo.projectCode
                this.formProjectBasicInfoBu.projectName = this.formProjectBasicInfo.projectName
                this.formProjectBasicInfoBu.startDate = this.formProjectBasicInfo.startDate
                this.formProjectBasicInfoBu.endDate = this.formProjectBasicInfo.endDate
                this.formProjectBasicInfoBu.projectState = this.formProjectBasicInfo.projectState
                this.formProjectBasicInfoBu.sysName = this.formProjectBasicInfo.sysName

              }else{
                this.$alert(res.data.message, { type: 'error' });
              }
            })

          } else {
            console.log('error submit!!')
            return false
          }
        })
      } else if (formName === 'formProjectResourceMge') {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateInnerInfo(this.formProjectBasicInfo).then(res => {
              if (res.data.status === 200) {

                this.unchangeableResourceMge = true
                // this.unchangeableBasicInfo = true
                this.projectCodeFlag = false
                this.$message.success("已保存研发项目资源管理")

                this.formProjectBasicInfoBu.competentDept = this.formProjectBasicInfo.competentDept
                this.formProjectBasicInfoBu.participatingDate = JSON.parse(JSON.stringify(this.formProjectBasicInfo.participatingDate))
                this.formProjectBasicInfoBu.headUser = this.formProjectBasicInfo.headUser
                this.formProjectBasicInfoBu.devUser = this.formProjectBasicInfo.devUser
              }else{
                this.$alert(res.data.message, { type: 'error' });
              }
            })

          } else {
            console.log('error submit!!')
            return false
          }
        })
        // alert('已保存研发项目资源管理!')
      } else if (formName === 'formProjectApprovalMat') {
        this.unchangeableApprovalMat = true
        // alert('已保存研发项目立项资料!')
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateInnova(this.formProjectBasicInfo).then(res => {
              if (res.data.status === 200) {
                // this.unchangeableResourceMge = true
                // this.unchangeableBasicInfo = true
                this.projectCodeFlag = false
                this.$message.success("已保存研发项目立项资料！")

                this.formProjectBasicInfoBu.busInnova = this.formProjectBasicInfo.busInnova
                this.formProjectBasicInfoBu.techInnova = this.formProjectBasicInfo.techInnova

              }else{
                  this.$alert(res.data.message, { type: 'error' });
              }
            })

          } else {
            console.log('error submit!!')
            return false
          }
        })

      } else if (formName === 'formProjectResult') {
        this.unchangeableProjectResult = true
        alert('已保存研发项目成果资料!')

      }
    },
    // 研发项目资源管理 项目内、外部资源 详情按钮
    toDetail(table, index) {
      if (table === 'tableProjectInside') { // 研发项目资源管理 项目内部资源 详情按钮

      } else if (table === 'tableProjectOutside') { // 研发项目资源管理 项目外部资源 详情按钮

      }
    },
    // 研发项目立项资料 项目可行性分析报告 上传
    handleFeasibilitySuccess(response, file, fileList) {
      // // 处理上传成功的回调函数
      // this.formProjectApprovalMat.tableProjectFeasibility = fileList.map(item => {
      //   return {
      //     fileName: item.name,
      //     uploadedDate: new Date().toLocaleString()
      //   }
      // })
      this.queryFile();
    },
    // 研发项目立项资料 项目立项计划数 上传
    handleProposalSuccess(response, file, fileList) {
      // 处理上传成功的回调函数
      // this.formProjectApprovalMat.tableProjectProposal = fileList.map(item => {
      //   return {
      //     fileName: item.name,
      //     uploadedDate: new Date().toLocaleString()
      //   }
      // })
      this.queryFile();
    },
    // 研发项目成果资料 项目成果 上传
    handleResultlSuccess(response, file, fileList) {
      // // 处理上传成功的回调函数
      // this.formProjectResult.tableProjectResult = fileList.map(item => {
      //   return {
      //     fileName: item.name,
      //     uploadedDate: new Date().toLocaleString()
      //   }
      // })
      this.queryFile();
    },
    // 上传文件删除按钮
    delete(table, index) {
      if (table === 'tableProjectFeasibility') { // 项目可行性分析报告

      } else if (table === 'tableProjectProposal') { // 项目立项计划数

      } else if (table === 'tableProjectResult') { // 项目成果

      }
    },
    orgChange(value){

      if(value != null){

        this.formProjectBasicInfo.competentDept = value
      }else {
        this.formProjectBasicInfo.competentDept = ''
      }
      console.log(this.paramForm);
     },

     participatingOrgChange(value){

      if(value != null){
        // this.formProjectBasicInfo.participatingDate=value
        this.$set(this.formProjectBasicInfo,'participatingDate',value)
        this.formProjectBasicInfo.participatingDept = JSON.stringify(value,null,2)

      }else {
        this.formProjectBasicInfo.participatingDate = []
      }
      console.log(this.paramForm);
     }
  }
}
</script>
<style scoped>
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
.project_date{
  display: flex;
}
.end_date{
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
.resourceMge_input_box{
  width: 300px;
}
.approvalMat_input_box{
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
.uploadButton{
  margin-left: 30px;
}
</style>

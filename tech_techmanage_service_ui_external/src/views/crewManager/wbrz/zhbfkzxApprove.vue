<template>
    <div class="approveTwoCon">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="8">
                                <p class="nav_name">外包人员准入审批</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="8">
                                <buttonCommon :buttonName="closeName" :size="sizes" :fns="close"></buttonCommon>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="mainCon">
            <el-form :model="approveData" ref="ruleFkpc">
                <div class="First First_other">
                    <div class="contentFirst_title">
                        <p class="contentTwo_title_p">基本信息</p>
                    </div>
                    <div class="contentFirst">
                        <!-- layout布局1 -->
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>姓名</p>
                                        <el-input v-model="approveData.userName" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>工号</p>
                                        <el-input v-model="approveData.jobId" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>性别</p>
                                        <el-radio-group v-model="approveData.sex">
                                            <el-radio v-for="item in sex_options" :label="item.dictid" :key="item.dictid"
                                                disabled>{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>籍贯</p>
                                        <el-input v-model="approveData.nativeplace" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div class="picker_sty_fir">
                                        <p>生日</p>
                                        <el-date-picker type="date" :disabled="true" v-model="approveData.birthday"></el-date-picker>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>婚姻状况</p>
                                        <el-radio-group v-model="approveData.maritalStatus">
                                            <el-radio v-for="(item,index) in marriage_options" :label="item.dictid"
                                                :key="index" :disabled="true">{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>毕业院校</p>
                                        <el-input :disabled="true" v-model="approveData.school"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>专业</p>
                                        <el-input :disabled="true" v-model="approveData.specialty"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>学历</p>
                                        <el-radio-group v-model="approveData.education">
                                            <el-radio v-for="item in edu_options" :label="item.dictid" :key="item.dictid"
                                                :disabled="true">{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>联系方式</p>
                                        <el-input :disabled="true" v-model="approveData.mobile"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <!-- 公司名称 -->
                                        <p>外包商</p>
                                        <el-select v-model="approveData.outsourceNo" filterable placeholder="" disabled>
                                            <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname"
                                                :value="item.orgcode">
                                            </el-option>
                                        </el-select>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>外包方式</p>
                                        <el-radio-group v-model="approveData.outsourceType">
                                            <el-radio v-for="(item, index) in outsourcetype_options" :label="item.dictid"
                                                :key="item.index" :disabled="true">{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p class="deptno_p">所属部门</p>
                                        <el-select v-model="approveData.deptno" disabled>
                                            <el-option v-for="(item,index) in deptno_options" :key="index" :value="item.id"
                                                :label="item.text"></el-option>
                                        </el-select>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <!-- &nbsp; -->
                                    <div class="picker_sty">
                                        <p>入职日期</p>
                                        <el-date-picker type="date" :disabled="true" v-model="approveData.entryTime"></el-date-picker>
                                    </div>
                                </el-col>
                                <!--
                                <el-col :span="8">
                                    <div>
                                        <p>所在小组</p>
                                        <el-select v-model="approveData.groupId" disabled>
                                            <el-option v-for="item in groupid_options" :key="item.id" :label="item.groupname"
                                                :value="item.id">
                                            </el-option>
                                        </el-select>
                                    </div>
                                </el-col>
                                -->
                                <el-col :span="8">
                                    <div>
                                        <p>结算方式</p>
                                        <el-radio-group v-model="approveData.userType">
                                            <el-radio v-for="item in usertype_options" :label="item.dictid" :key="item.dictid"
                                                disabled>{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>岗位</p>
                                        <el-select v-model="approveData.position" disabled>
                                            <el-option v-for="item in position_options" :key="item.dictid" :label="item.dictname"
                                                :value="item.dictid"></el-option>
                                        </el-select>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>工作内容</p>
                                        <el-input type="textarea" autosize :disabled="true" v-model="approveData.workContent"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <!-- <div class="picker_sty">
                                        <p>入职日期</p>
                                        <el-date-picker type="date" :disabled="true" v-model="approveData.entryTime"></el-date-picker>
                                    </div> -->
                                </el-col>
                            </el-row>
                        </div>
                        <div class="row_approve" v-if="this.approveData.userType == 1">
                          <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="wbrzxmmc">
                                <p>项目名称</p>
                                <el-select v-model="approveData.projectno" disabled>
                                    <el-option v-for="item in projectno_options" :key="item.id" :label="item.name" :value="item.id"></el-option>
                                </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                <p>项目经理</p>
                                <el-select v-model="approveData.organizers" disabled>
                                    <el-option v-for="item in organizers_project_options" :key="item.badge" :label="item.empname+'('+item.badge+')'" :value="item.badge"></el-option>
                                </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <!-- <div>
                                <p>采购项目</p>
                                <el-select v-if="approveData.purchaseItem != undefined" v-model="approveData.purchaseItem" :disabled="true">
                                    <el-option v-for="(item,index) in purchaseItem_options" :key="index" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                                <el-input v-else placeholder="无" disabled></el-input>
                                </div> -->
                            </el-col>
                            </el-row>
                        </div>

                        <div class="row_approve" v-else>
                          <el-row :gutter="60">
                            <el-col :span="8">
                                <div>
                                <p>系统名称</p>
                                <el-select v-model="approveData.systemnos" disabled>
                                    <el-option v-for="item in systemnos_options" :key="item.id" :label="item.name" :value="item.id"></el-option>
                                </el-select>
                                </div> 
                            </el-col>
                            <el-col :span="8">
                                <div>
                                <p>项目经理</p>
                                <el-select v-model="approveData.organizers" disabled>
                                    <el-option v-for="item in organizers_options" :key="item.pcode" :label="item.text" :value="item.pcode"></el-option>
                                </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <!-- <div>
                                <p>采购项目</p>
                                <el-select v-if="approveData.purchaseItem != undefined" v-model="approveData.purchaseItem" :disabled="true">
                                    <el-option 
                                    v-for="(item,index) in purchaseItem_options"
                                    :key="index" 
                                    :label="item.label" 
                                    :value="item.value"
                                    ></el-option>
                                </el-select>
                                <el-input v-else placeholder="无" disabled></el-input>
                                </div> -->
                            </el-col>
                            </el-row>
                        </div>

                        <!-- 表第九行 -->
                    <div class="row_approve" >
                      <el-row :gutter="50">
                        <el-col :span="8">
                          <p>总办议案 *</p>
                           
                              <el-select 
                                v-model="approveData.ceneralOfficeMotion"
                                value-key="id"
                                disabled
                              >
                                <el-option
                                  v-for="(item) in ceneralOfficeMotion_options"
                                  :key="item.id"
                                  :label="item.label"
                                  :value="item.id"
                                ></el-option>
                              </el-select>
                        </el-col>
                        <el-col :span="8">
                          <p>合同</p>
                         
                            <el-select
                              v-model="approveData.newContractId"
                              filterable
                              placeholder="请选择"
                              value-key="id"
                              disabled
                            >
                              <el-option
                                v-for="(item) in newContractId_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="8">
                        </el-col>
                      </el-row>
                    </div>
                    </div>

                </div>
                
                <!-- 社保信息 -->
                <div class="approveTwo-modu">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">社保信息</p>
                    </div>
                    <div class="content">
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>社保缴纳</p>
                                        <div class="picker_sty">
                                            <el-radio-group v-model="approveData.sociaiSecrityPayment">
                                                <el-radio v-for="item in sbjn_options" :label="item.dictid" :key="item.dictid"
                                                    disabled>{{item.dictname}}</el-radio>
                                            </el-radio-group>
                                        </div>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>
                <div class="approveTwo-modu">
                    <!-- 面试信息 -->
                    <div>
                        <div class="contentTwo_title">
                            <p class="contentTwo_title_p">面试信息</p>
                        </div>
                        <div class="contentTwo_inte">
                            <div class="row_approve">
                                <el-row :gutter="60">
                                    <el-col :span="8">
                                        <div>
                                            <p>面试总成绩</p>
                                            <el-input v-model="approveData.interviewSumAchievement" :disabled="true"></el-input>
                                        </div>
                                    </el-col>
                                    <el-col :span="8">
                                        <div>
                                            <p>面试结果</p>
                                            <el-radio-group v-model="approveData.interviewResult">
                                                <el-radio v-for="item in intervresult_options" :label="item.dictid"
                                                    :key="item.dictid" :disabled="true">{{item.dictname}}</el-radio>
                                            </el-radio-group>
                                        </div>
                                    </el-col>
                                    <el-col :span="8">
                                        <div>
                                            <p>人员单价</p>
                                            <el-input v-model="unitPrice" :disabled="true"></el-input>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                            <div class="row_approve mstable">
                                <el-table :data="scoreList" style="width: 100%">
                                    </el-table-column>
                                    <el-table-column prop="msgname" label="面试官" width="140"></el-table-column>
                                    <el-table-column prop="msgDepname" label="部门" width="180" :show-overflow-tooltip="true"></el-table-column>
                                    <el-table-column prop="technology" label="技术" width="185">
                                        <template slot-scope="scope">
                                            <el-input-number v-model="scope.row.technology" size="medium"  placeholder="技术"
                                                controls-position="right" :min="0" :max="4" :step="0.5" :disabled="true"/>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="comprehensive" label="综合" width="185">
                                        <template slot-scope="scope">
                                            <el-input-number v-model="scope.row.comprehensive" size="medium"  placeholder="综合"
                                                controls-position="right" :min="0" :max="2" :step="0.5" :disabled="true"/>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="experience" label="项目经验" width="185">
                                        <template slot-scope="scope">
                                            <el-select v-model="scope.row.experience" placeholder="经验" :disabled="true">
                                            <el-option v-for="item in experienceData" :label="item.dictname" :key="item.dictid" :value="item.dictid"></el-option>
                                            </el-select>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="education" label="学历" width="185">
                                        <template slot-scope="scope">
                                            <el-select v-model="scope.row.education" placeholder="学历" :disabled="true">
                                            <el-option v-for="item in educationData" :label="item.dictname" :key="item.dictid" :value="item.dictid"></el-option>
                                            </el-select>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="remark" label="评语" :show-overflow-tooltip="true"></el-table-column>
                                    <el-table-column label=""></el-table-column>
                                </el-table>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 征信和风控查询信息 -->
                <div class="approveTwo-modu">
                    <div>
                        <div class="contentTwo_title">
                            <p class="contentTwo_title_p">征信和风控查询信息</p>
                        </div>
                        <div class="contentTwo_inte">
                            <div class="row_approve">
                                <el-row :gutter="60">
                                    <el-col :span="6">
                                        <div>
                                            <p>征信和风控结果 *</p>
                                            <el-form-item prop="queryresult" :rules="[{ required: true, message: '请选择征信和风控结果', trigger: 'change'}]">
                                                <el-radio-group v-model="approveData.queryresult">
                                                    <el-radio v-for="item in zxfk_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="6">
                                        <div>
                                            <p>个人征信 *</p>
                                            <el-form-item prop="personcredit" :rules="[{ required: true, message: '请选择个人征信', trigger: 'change'}]">
                                                <el-radio-group v-model="approveData.personcredit">
                                                    <el-radio v-for="item in personcredit_options" :label="item.dictid"
                                                        :key="item.dictid">{{item.dictname}}</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="6">
                                        <div>
                                            <p>是否重复入职 *</p>
                                            <el-form-item prop="iseverentry" :rules="[{ required: true, message: '请选择是否重复入职', trigger: 'change'}]">
                                                <el-radio-group v-model="approveData.iseverentry" :disabled="true">
                                                    <el-radio v-for="item in iseverentry_options" :label="item.dictid"
                                                        :key="item.dictid">{{item.dictname}}</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <!-- 
                                    <el-col :span="6">
                                        <div>
                                            <p>公安检查 *</p>
                                            <el-form-item prop="policecheck" :rules="[{ required: true, message: '请选择公安检查', trigger: 'change'}]">
                                                <el-radio-group v-model="approveData.policecheck" :disabled="true">
                                                    <el-radio v-for="item in gahc_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    -->
                                </el-row>
                            </div>

                            <!-- 表第五行 -->
                            <div class="row_approve">
                                <el-row :gutter="60">
                                    <el-col :span="8">
                                        <div>
                                            <p>异常信息 *</p>
                                            <el-form-item prop="courtinfo" :rules="[{ required: true, message: '请填写异常信息', trigger: 'blur'}]">
                                                <el-input type="textarea" autosize v-model="approveData.courtinfo"></el-input>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="8">
                                        <!-- <div>
                                            <p>工商信息 *</p>
                                            <el-form-item prop="businessinfo" :rules="[{ required: true, message: '请填写工商信息', trigger: 'blur'}]">
                                                <el-input type="textarea" autosize v-model="approveData.businessinfo"></el-input>
                                            </el-form-item>
                                        </div> -->
                                    </el-col>
                                </el-row>
                            </div>

                        </div>
                    </div>
                </div>

                <!-- 准入要求 -->
                <div class="approveTwo-modu">
                    <div>
                        <div class="contentTwo_title">
                            <p class="contentTwo_title_p">准入要求</p>
                        </div>
                        <div class="contentTwo_inte">

                            <div class="row_approve">
                                <el-row :gutter="60">
                                    <el-col :span="8">
                                        <div>
                                            <p>是否符合准入要求 *</p>
                                            <el-form-item prop="whetherMeetsClaim" :rules="[{ required: true, message: '请选择准入要求', trigger: 'change'}]">
                                                <el-radio-group v-model="approveData.whetherMeetsClaim">
                                                    <el-radio v-for="item in whetherMeetsClaim_options" :label="item.dictid"
                                                        :key="item.dictid">{{item.dictname}}</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="8">
                                        <div v-if="approveData.whetherMeetsClaim == '0'" key="1">
                                            <p>原因 *</p>
                                            <el-form-item prop="meetRequirementsReason" :rules="[{ required: true, message: '请填写原因', trigger: 'blur'}]">
                                                <el-input type="textarea" autosize v-model="approveData.meetRequirementsReason"></el-input>
                                            </el-form-item>
                                        </div>
                                        <div v-else key="2">
                                            <p>原因</p>
                                            <el-form-item>
                                                <el-input type="textarea" autosize v-model="approveData.meetRequirementsReason"></el-input>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>

                        </div>
                    </div>
                </div>

                <!-- 简历信息 -->
                <div class="approveTwo-modu">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">简历信息</p>
                    </div>
                    <div class="content">
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>证件类型</p>
                                        <el-radio-group v-model="approveData.certifType">
                                            <el-radio v-for="item in certiftype_options" :label="item.dictid" :key="item.dictid"
                                                disabled>{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>证件号码</p>
                                        <el-input v-model="approveData.certifNumber" disabled></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>政治面貌</p>
                                        <el-radio-group v-model="approveData.politicalStatus">
                                            <el-radio v-for="item in politiccontenance_options" :label="item.dictid"
                                                disabled :key="item.dictid" size="medium">{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <!-- 表第二行 -->
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>当前住址</p>
                                        <el-input v-if="approveData.address != undefined" type="textarea" autosize
                                            v-model="approveData.address" disabled placeholder="请输入"></el-input>
                                        <el-input v-else placeholder="无" disabled></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>配偶姓名</p>
                                        <el-input v-if="approveData.partnerName != undefined" v-model="approveData.partnerName"
                                            disabled placeholder="请输入"></el-input>
                                        <el-input v-else placeholder="无" disabled></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>配偶证件号码</p>
                                        <el-input v-if="approveData.idcardNumber != undefined" v-model="approveData.idcardNumber"
                                            disabled placeholder="请输入"></el-input>
                                        <el-input v-else placeholder="无" disabled></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>所获证书</p>
                                        <el-input v-if="approveData.certificate != undefined" type="textarea" autosize
                                            v-model="approveData.certificate" disabled placeholder="请输入"></el-input>
                                        <el-input v-else placeholder="无" disabled></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="row_approve study-select">
                            <el-row>
                                <el-col :span="24">
                                    <div>
                                        <p>学习经历(大专/本科起)</p>
                                        <el-table :data="approveData.learnExperience" style="width: 100%">
                                            <el-table-column prop="studyDate" label="起止时间" width="270">
                                                <template slot-scope="scope">
                                                    <el-date-picker v-model="scope.row.studyDate" type="monthrange"
                                                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                                                        :disabled="true"></el-date-picker>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="schoolName" label="学校" width="270">
                                                <template slot-scope="scope">
                                                    <el-input v-model="scope.row.schoolName" placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="professName" label="专业" width="270">
                                                <template slot-scope="scope">
                                                    <el-input v-model="scope.row.professName" type="textarea" autosize
                                                        placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="eduGenre" label="教育类别" width="270">
                                                <template slot-scope="scope">
                                                    <el-select v-model="scope.row.eduGenre" placeholder="" :disabled="true">
                                                        <el-option v-for="(item,index) in eduGenre_options" :key="item.index"
                                                            :label="item.dictname" :value="item.dictid"></el-option>
                                                    </el-select>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="studyForm" label="学习形式" width="270">
                                                <template slot-scope="scope">
                                                    <el-select v-model="scope.row.studyForm" placeholder="" :disabled="true">
                                                        <el-option v-for="(item,index) in studyForm_options" :key="item.index"
                                                            :label="item.dictname" :value="item.dictid"></el-option>
                                                    </el-select>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label="">

                                            </el-table-column>
                                        </el-table>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <div class="row_approve">
                            <el-row>
                                <el-col :span="24">
                                    <div class="table">
                                        <p>主要工作经历(入职前)</p>
                                        <el-table :data="approveData.mainJobs" style="width: 100%">
                                            <el-table-column prop="workDate" label="起止时间" width="270">
                                                <template slot-scope="scope">
                                                    <el-date-picker v-model="scope.row.workDate" type="monthrange"
                                                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                                                        :disabled="true"></el-date-picker>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="comName" label="公司名称" width="270">
                                                <template slot-scope="scope">
                                                    <el-input v-model="scope.row.comName" type="textarea" autosize
                                                        placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="mainWork" label="主要工作" width="810">
                                                <template slot-scope="scope">
                                                    <el-input v-model="scope.row.mainWork" type="textarea" autosize
                                                        placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label=""></el-table-column>
                                        </el-table>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="row_approve">
                            <el-row>
                                <el-col :span="24">
                                    <div class="table">
                                        <p>主要项目经历(入职前)</p>
                                        <el-table :data="approveData.mainItem" style="width: 100%">
                                            <el-table-column prop="projDate" label="起止时间" width="270">
                                                <template slot-scope="scope">
                                                    <el-date-picker v-model="scope.row.projDate" type="monthrange"
                                                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                                                        :disabled="true"></el-date-picker>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="projName" label="项目名称" width="270">
                                                <template slot-scope="scope">
                                                    <el-input type="textarea" autosize v-model="scope.row.projName"
                                                        placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="mainWork" label="主要工作" width="810">
                                                <template slot-scope="scope">
                                                    <el-input type="textarea" autosize v-model="scope.row.mainWork"
                                                        placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label="">

                                            </el-table-column>
                                        </el-table>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <div class="row_approve">
                            <el-row>
                                <el-col :span="24">
                                    <div class="table">
                                        <p>主要奖励记录</p>
                                        <el-table :data="approveData.mainReward" style="width: 100%">
                                            <el-table-column prop="prizeDate" label="获奖时间" width="270">
                                                <template slot-scope="scope">
                                                    <div class="picker_sty">
                                                        <el-date-picker v-model="scope.row.prizeDate" type="date"
                                                            placeholder="" :disabled="true"></el-date-picker>
                                                    </div>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="prizeName" label="奖励名称" width="270">
                                                <template slot-scope="scope">
                                                    <el-input type="textarea" autosize v-model="scope.row.prizeName"
                                                        placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="prizeGrade" label="奖励级别" width="270">
                                                <template slot-scope="scope">
                                                    <el-input v-model="scope.row.prizeGrade" placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="prizeCause" label="奖励原因" width="540">
                                                <template slot-scope="scope">
                                                    <el-input type="textarea" autosize v-model="scope.row.prizeCause"
                                                        placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label="">

                                            </el-table-column>
                                        </el-table>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="row_approve">
                            <el-row>
                                <el-col :span="24">
                                    <div class="table">
                                        <p>主要家庭成员</p>
                                        <el-table :data="approveData.mainFamily" style="width: 100%">
                                            <el-table-column prop="familyName" label="姓名" width="270">
                                                <template slot-scope="scope">
                                                    <el-input v-model="scope.row.familyName" placeholder="" :disabled="true"></el-input>
                                                </template>
                                            </el-table-column>

                                            <el-table-column prop="feminist" label="称谓" width="270">
                                                <template slot-scope="scope">
                                                    <el-select v-model="scope.row.feminist" placeholder="" :disabled="true">
                                                        <el-option v-for="(item,index) in feminist_options" :key="item.index"
                                                            :label="item.dictname" :value="item.dictid"></el-option>
                                                    </el-select>
                                                </template>
                                            </el-table-column>
                                            <el-table-column :disabled="true" prop="familyBirthday" label="出生年月日" width="270">
                                                <template slot-scope="scope">
                                                    <div class="picker_sty">
                                                        <el-date-picker :disabled="true" v-model="scope.row.familyBirthday"
                                                            type="date" placeholder=""></el-date-picker>
                                                    </div>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="unit" label="工作单位及职务" width="540">
                                                <template slot-scope="scope">
                                                    <el-input type="textarea" autosize :disabled="true" v-model="scope.row.unit"
                                                        placeholder=""></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label=""></el-table-column>
                                        </el-table>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>

                <!-- 礼仪考试 -->
                <div class="approveTwo-modu">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">考试成绩</p>
                    </div>
                    <div class="content">
                        
                        <!-- 表第一行 -->
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>礼仪考试时间</p>
                                        <div class="picker_sty">
                                            <el-date-picker v-model="approveData.eitquetteTime" type="date"
                                                value-format="yyyy-MM-dd" disabled></el-date-picker>
                                        </div>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>礼仪考试成绩</p>
                                        <el-input v-model="approveData.eitquetteAchievement" disabled></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <!-- 表第二行 -->
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>资质考试时间</p>
                                        <div class="picker_sty">
                                            <el-date-picker v-model="approveData.qualityTime" type="date"
                                                value-format="yyyy-MM-dd" disabled></el-date-picker>
                                        </div>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>资质考试成绩</p>
                                        <el-input v-model="approveData.qualityAchievement" disabled></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>

                <!-- 入职材料 -->
                <div class="approveTwo-modu">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">入职材料<span class="xkg-color-yellow xkg-marginleft-5px xkg-cursor-pointer yjButt"
                                @click="yjDownload">一键下载</span></p>
                    </div>
                    <div class="contentTwo_inte">
                    <!-- 表第四行 -->
                        <div class="row_approve upload">
                            <el-row :gutter="20">
                                <el-col :span="8">
                                    <div>
                                        <p>
                                            征信报告
                                        </p>
                                        <div v-for="item in creditReport">
                                            {{item.docname}}
                                            <span  class="preview" @click="openNtkoWindow(item, 'zhengxin')">预览</span>
                                            <span class="download" @click="downloadFile(item.id)">下载</span>
                                        </div>
                                    </div>
                                    <div>
                                        <p>
                                            保密承诺书、服务提供/外包员工背景调查表/厂商承诺书
                                        </p>
                                        <div v-for="item in confidentialityCommitment">
                                            {{item.docname}}
                                            <span  class="preview" @click="openNtkoWindow(item, 'baomi')">预览</span>
                                            <span class="download" @click="downloadFile(item.id)">下载</span>
                                        </div>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>
                                            社保缴纳证明/劳动合同/纳税证明
                                        </p>
                                        <div v-for="item in sociaiSecrityPaymentProve">
                                            {{item.docname}}
                                            <span  class="preview" @click="openNtkoWindow(item, 'shebao')">预览</span>
                                            <span class="download" @click="downloadFile(item.id)">下载</span>
                                        </div>
                                    </div>
                                    <div>
                                        <p>
                                            身份证正反面
                                        </p>
                                        <div v-for="item in otherFile">
                                            {{item.docname}}
                                            <span  class="preview" @click="openNtkoWindow(item, 'other')">预览</span>
                                            <span class="download" @click="downloadFile(item.id)">下载</span>
                                        </div>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>人脸照片</p>
                                        <div> 
                                            <el-image :src="facePhotoUrl" :preview-src-list="facePhotoUrl.split(',')" class="facePhoto"/>
                                        </div>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>

                <!-- 配置信息 -->
                <div class="approveTwo-modu">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">配置信息</p>
                    </div>
                    <div class="content">
                        <!-- 表第一行 
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>考勤类型</p>
                                        <el-select v-model="approveData.attendanceType" disabled>
                                            <el-option v-for="(item,index) in kqtype_options" :key="index" :value="item.kqType"
                                                :label="item.name">
                                            </el-option>
                                        </el-select>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>考勤时间</p>
                                        <el-input v-model="approveData.attendanceTime" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        -->

                        <!-- 表第二行 -->
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>虚拟桌面</p>
                                        <el-radio-group v-model="approveData.iscreditquery">
                                            <el-radio v-for="item in xnzm_options" :disabled="true" :label="item.dictid"
                                                :key="item.dictid">{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>svn申请</p>
                                        <el-radio-group v-model="approveData.svnLimit">
                                            <el-radio v-for="item in sf_options" :disabled="true" :label="item.dictid"
                                                :key="item.dictid">{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>特殊权限</p>
                                        <el-radio-group v-model="approveData.specialAuth">
                                            <el-radio v-for="item in sf_options" :disabled="true" :label="item.dictid"
                                                :key="item.dictid">{{item.dictname}}</el-radio>
                                        </el-radio-group>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <!-- 表第三行 -->
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div>
                                        <p>虚拟桌面配置</p>
                                        <el-input v-if="approveData.applyreg != undefined" type="textarea" autosize
                                            v-model="approveData.applyreg" :disabled="true"></el-input>
                                        <el-input v-else placeholder="无" disabled></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>SVN地址</p>
                                        <el-input v-if="approveData.svnRoad != undefined" type="textarea" autosize
                                            v-model="approveData.svnRoad" :disabled="true"></el-input>
                                        <el-input v-else placeholder="无" disabled></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div>
                                        <p>权限描述</p>
                                        <el-input v-if="approveData.authDes != undefined" type="textarea" autosize
                                            v-model="approveData.authDes" :disabled="true"></el-input>
                                        <el-input v-else placeholder="无" disabled></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <!-- 表第四行 -->
                        <div class="row_approve">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                <div>
                                    <p>虚拟机帐户</p>
                                    <el-input
                                        type="input"
                                        autosize
                                        placeholder
                                        :disabled="true"
                                        v-model="approveData.vmAccount"
                                    ></el-input>
                                </div>
                                </el-col>
                                <el-col :span="8">
                                <div>
                                    <p>门禁卡编号</p>
                                    <el-input
                                        type="input"
                                        autosize
                                        placeholder
                                        :disabled="true"
                                        v-model="approveData.entranceCard" 
                                    ></el-input>
                                </div>
                                </el-col>
                                <el-col :span="8">
                                <div>
                                    
                                </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>
                <!-- 操作日志 -->
                <div class="czLog">
                    <flow-logs :processInstId="flowItem.processInstanceId" ref="log"></flow-logs>
                </div>
            </el-form>
        </div>

        <!-- 底部栏样式2 -->
        <div class="nav-bottom">
            <approve-bottom :proving="proving" :flowItem="flowItem"></approve-bottom>
        </div>

        <!-- 预览图片弹窗 -->
        <el-dialog title="" :visible.sync="ylDialog">
            <div class="ylDialogCon">
                <el-image :src="ylPhotoUrl" style="width:600px;height:400px" :fit="photoFit" :preview-src-list="ylPhotoUrl.split(',')"/>
            </div>
        </el-dialog>
    </div>

</template>

<script>
    import { getTree, 
     queryResourceDept,
     queryGroup, 
     querySystemList,
     queryProjectList,
     queryEmployeeTree,
     querySystemTree,
     queryApplyList,
     queryProjectEmp,
     queryKqTypeList,
     validMotion,
     validContract
     } from "@/api/integrated/wbryzrlc";
    import { zhbfkzxAudit, getWbrzDetail, getImageUrl, getWindControl, checkedExistEntry, upload, getCheckedIdCard, getCheckedSecurity } from "@/api/integrated/newWbrz";
    import { getInterviewScore } from '@/api/integrated/wbms';
    import { getEmployUser, preview, getListAll } from '@/api/common/workflowApi';
    import common from "@/api/common";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    import approveBottom from "@/views/plugins/camunda/approveBottom";
    import { getDicEntry } from "@/api/eosDictEntry";
    import Moment from 'moment';
    // 引入ntko
    let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
    import { checkedIsBlack } from "@/api/integrated/wbHmd";
    export default {
        components: { buttonOrange, buttonCommon, flowLogs, approveBottom },
        data() {
            return {
                approveData: {}, //审批页面信息
                genferData: [],
                sex_options: [],
                edu_options: [],
                outsourcetype_options: [],
                usertype_options: [],
                deptno_options: [],
                groupid_options: [],
                position_options: [],
                marriage_options: [],
                politiccontenance_options: [],
                certiftype_options: [],
                intervresult_options: [],
                projectno_options: [],
                projectnoParams: { //项目名称配置信息
                    label: 'projectname',
                    value: 'projectno',
                    children: 'children',
                    emitPath: false,
                },
                organizers_options: [],
                organizers_project_options: [],
                systemnos_options: [],
                systemParams: {  //系统名称配置信息
                    label: 'text',
                    value: 'pcode',
                    children: 'children',
                    emitPath: false,
                },
                purchaseItem: '', //采购项目（没有）
                purchaseItem_options: [],
                kqtype_options: [],
                zxfk_options: [], //征信和风控结果
                educationData: [], // 业务字段-学历
                experienceData: [], // 业务字段-项目经验
                gahc_options: [], //公安核查
                personcredit_options: [], //个人征信
                iseverentry_options: [], //是否重复入职
                sbjn_options: [], //社保缴纳
                whetherMeetsClaim_options: [], //是否符合准入
                sf_options: [], //是否
                iscreditquery_options: [],
                svnlimit_options: [],
                eduGenre_options: [],
                studyForm_options: [],
                feminist_options: [],
                otherFile:[],
                creditReport:[],
                sociaiSecrityPaymentProve:[],
                confidentialityCommitment:[],
                processInstID: '', //流程ID
                sizes: 'mini',
                photoFit:"scale-down",  
                closeName: '关闭', // 关闭按钮
                flowItem: {}, //bps数据
                facePhotoUrl: '', //人脸图片地址
                ylDialog: false, //是否显示预览图片弹窗
                ylPhotoUrl: '', //预览图片地址
                scoreList: [], //面试打分数据
                fileList: [], //文件上传
                unitPrice: '', //人员单价
                outsourceno_options: [],
                xnzm_options: [{ //虚拟桌面
                    dictid: '01',
                    dictname: '是'
                }, {
                    dictid: '02',
                    dictname: '否'
                }],
                managerUsername: '', //项目经理名称
                systemPrincipalname: '', //系统负责人名称
                ceneralOfficeMotion_options: [], // 总办议案
                newContractId_options: [], // 合同
            }
        },
        created() {
            console.log("综合部风控审核");
        },
        mounted() {

        },
        methods: {
            // 初始化
            init(item) {
                this.getDic();
                this.flowItem = item;
                this.flowItem.processInstId = item.processInstanceId;
                this.$refs.log.init(item.processInstanceId);
                this.processInstID = item.processInstanceId;
                queryKqTypeList() //查询考勤类型
                    .then(res => {
                        this.kqtype_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
                this.getApproveDetail(item.processInstanceId);
                this.filDep();
                this.getWbcom();
                this.getvalidMotion()
                this.getvalidContract()
            },

            // 获取审批页面信息
            getApproveDetail(val) {
                let params = {};
                getWbrzDetail({ instanceId: val }).then(res => {
                    if (res.data.status == 200) {
                        res.data.results.education = res.data.results.education.toString();
                        res.data.results.entryTime = Moment(res.data.results.entryTime).format('YYYY-MM-DD');
                        if (res.data.results.learnExperience != undefined) {
                            res.data.results.learnExperience = JSON.parse(res.data.results.learnExperience);
                        } else {
                            res.data.results.learnExperience = []
                        }
                        if (res.data.results.mainJobs != undefined) {
                            res.data.results.mainJobs = JSON.parse(res.data.results.mainJobs).filter(li => {
                                //var strcheck = new RegExp("[\u4E00-\u9FA5]+");
                                for (var i = 0; i < li.workDate.length; i++) {
                                    if(li.workDate[i] !== null){
                                        if(Moment(li.workDate[i].toString()).format('YYYY-MM') == 'Invalid date'){
                                            li.workDate[i] = '';
                                        }else{
                                            li.workDate[i] = Moment(li.workDate[i].toString()).format('YYYY-MM');
                                        }
                                    }else{
                                        li.workDate[i] = '';
                                    }
                                }
                                return li;
                            });
                        } else {
                            res.data.results.mainJobs = []
                        }
                        if (res.data.results.mainItem != undefined) {
                            res.data.results.mainItem = JSON.parse(res.data.results.mainItem).filter(li => {
                                //var strcheck = new RegExp("[\u4E00-\u9FA5]+");
                                for (var i = 0; i < li.projDate.length; i++) {
                                    if(li.projDate[i] !== null){
                                        if(Moment(li.projDate[i].toString()).format('YYYY-MM') == 'Invalid date'){
                                            li.projDate[i] = '';
                                        }else{
                                            li.projDate[i] = Moment(li.projDate[i].toString()).format('YYYY-MM');
                                        }
                                        //li.projDate[i] = Moment(li.projDate[i].toString()).format('YYYY-MM');
                                    }else{
                                        li.projDate[i] = '';
                                    }
                                }
                                return li;
                            });
                        } else {
                            res.data.results.mainItem = []
                        }
                        if (res.data.results.mainReward != undefined) {
                            res.data.results.mainReward = JSON.parse(res.data.results.mainReward);
                        } else {
                            res.data.results.mainReward = []
                        }
                        if (res.data.results.mainFamily != undefined) {
                            res.data.results.mainFamily = JSON.parse(res.data.results.mainFamily);
                        } else {
                            res.data.results.mainFamily = []
                        }
                        if (res.data.results.isOtherProject != undefined) {
                            res.data.results.isOtherProject = eval(res.data.results.isOtherProject.toLowerCase());
                        }
                        if (res.data.results.deptno != undefined && res.data.results.deptno.indexOf('dep') == -1) {
                            res.data.results.deptno = 'dep' + res.data.results.deptno;
                        }
                        this.approveData = res.data.results;
                        this.searchMsinfo(res.data.results.certifNumber);
                        this.$nextTick(() => {
                            // if (this.approveData.iseverentry == undefined) {
                            this.searchIfCfrz(this.approveData.certifNumber, this.approveData.userName);
                            this.searchGongAn(this.approveData.certifNumber, this.approveData.userName);
                            this.searchFkzx(this.approveData.certifNumber, this.approveData.userName);
                            // }
                            if (
                                res.data.results.creditReport != undefined &&
                                res.data.results.creditReport != " "
                            ) {
                                let creditReport = res.data.results.creditReport.split(",");
                                creditReport.forEach(a =>{
                                    this.getFileName(a, 'zhengxin');
                                }); 
                            }
                            if (
                                res.data.results.sociaiSecrityPaymentProve != undefined &&
                                res.data.results.sociaiSecrityPaymentProve != " "
                            ) { 
                                let sociaiSecrityPaymentProve = res.data.results.sociaiSecrityPaymentProve.split(",");
                                sociaiSecrityPaymentProve.forEach(a =>{
                                    this.getFileName(a, 'shebao');
                                }); 
                            }
                            if (
                                res.data.results.confidentialityCommitment != undefined &&
                                res.data.results.confidentialityCommitment != " "
                            ) {
                                let confidentialityCommitment = res.data.results.confidentialityCommitment.split(",");
                                confidentialityCommitment.forEach(a =>{
                                    this.getFileName(a, 'baomi');
                                }); 
                            }
                            if (
                                res.data.results.otherFile != undefined &&
                                res.data.results.otherFile != " "
                            ) {
                                let otherFile = res.data.results.otherFile.split(",");
                                otherFile.forEach(a =>{
                                    this.getFileName(a, 'other');
                                });
                            }
                            if (res.data.results.faceimage != undefined) {
                                this.getPhotoUrl(this.approveData.faceimage);
                            }
                            this.searchGroup(this.approveData.deptno);
                            this.searchEpmlo(this.approveData.deptno);
                            this.searchProject();
                            this.searchSystem();
                            this.searchProjectEmp();
                        })
                    } else {
                        this.$message.error("获取数据失败")
                    }
                })
            },

            // 查询行内负责人
            searchEpmlo(val) {
                let params = '';
                params = val.replace("dep", "");
                queryEmployeeTree(params).then(res => {
                    let data = res.data.results;
                    this.organizers_options = data[0].children;
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            //查询项目经理
            searchProjectEmp(val) {
                if (this.approveData.projectno != null || this.approveData.projectno != undefined){
                let params = {};
                params.id = this.approveData.projectno;
                queryProjectEmp(params).then(res => {
                    this.organizers_project_options = res.data.results;
                }).catch(error => {
                    this.$message.error("查询项目经理失败");
                });
                }
            },

            // 获取业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "BUSI_OUTSOURCE_GENFER,BUSI_OUTSOURCE_MARITALSTATUS,NEWKG_WBFS,MSDF_EDUCATION,MSXM_LEVEL,NEWKG_JSFS,BUSI_OUTSOURCE_POSITION,NEWKG_CERTIFTYPE,NEWKG_POLITICAL,NEW_BUSI_OUTSOURCE_INTERVRESULT,SBJN,BUSI_OUTSOURCE_RESULT,NEWYY_GAHC,BUSI_OUTSOURCE_RESULT,NEWKG_ZRYQ,NEWYY_SFCFRZ,NEWYY_EDUCATION,LEARNMODALITY,NEWKG_CHENGWEI,NEWKG_RZSF"
                })
                    .then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        // 性别
                        this.sex_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
                        });
                        // 婚姻状况
                        this.marriage_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_MARITALSTATUS";
                        });
                        //外包方式
                        this.outsourcetype_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_WBFS";
                        });
                        //结算方式
                        this.usertype_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_JSFS";
                        });
                        //岗位
                        this.position_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_POSITION";
                        });
                        this.educationData = data.filter(item => {
                            return item.dicttypeid == "MSDF_EDUCATION";
                        });
                        this.experienceData = data.filter(item => {
                            return item.dicttypeid == "MSXM_LEVEL";
                        });
                        // 证件类型
                        this.certiftype_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_CERTIFTYPE";
                        });
                        //政治面貌
                        this.politiccontenance_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_POLITICAL";
                        });
                        // 面试结果
                        this.intervresult_options = data.filter(item => {
                            return item.dicttypeid == "NEW_BUSI_OUTSOURCE_INTERVRESULT";
                        }).reverse();
                        //征信和风控查询
                        this.zxfk_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_RESULT";
                        }).reverse();
                        //个人征信
                        this.personcredit_options = this.zxfk_options;
                        //公安核查
                        this.gahc_options = data.filter(item => {
                            return item.dicttypeid == "NEWYY_GAHC";
                        });
                        //是否重复入职
                        this.iseverentry_options = data.filter(item => {
                            return item.dicttypeid == "NEWYY_SFCFRZ";
                        });
                        //社保缴纳
                        this.sbjn_options = data.filter(item => {
                            return item.dicttypeid == "SBJN";
                        });
                        //是否符合准入
                        this.whetherMeetsClaim_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_ZRYQ";
                        }).reverse();
                        //学历
                        this.eduGenre_options = data.filter(item => {
                            return item.dicttypeid == "NEWYY_EDUCATION";
                        });
                        this.edu_options = this.eduGenre_options;
                        //学习形式
                        this.studyForm_options = data.filter(item => {
                            return item.dicttypeid == "LEARNMODALITY";
                        });
                        //称谓
                        this.feminist_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_CHENGWEI";
                        });
                        //是否
                        this.sf_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_RZSF";
                        });
                        return data;
                    });
            },

            // 查询面试信息流程ID
            searchMsinfo(id) {
                checkedExistEntry(id).then(res => {
                    if (res.data.status == 200 && res.data.results != undefined) {
                        this.getInterviewInfos(res.data.results.instanceid);
                    } else {
                        this.$message.error('查询面试流程ID失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查询面试信息
            getInterviewInfos(lcId) {
                let params = {};
                params.instanceid = lcId;
                getInterviewScore(params)
                    .then(res => {
                        var detailData = res.data.results;
                        if (res.data.status == 200) {
                            detailData.eeList = detailData.eeList.filter(li => li.idcard == this.approveData.certifNumber);
                            this.approveData.interviewSumAchievement = Number(detailData.eeList[0].score);
                            this.approveData.interviewResult = detailData.eeList[0].interviewresult;
                            this.scoreList = detailData.eeList[0].scoreList.filter(li => li.idcard == this.approveData.certifNumber);
                            this.unitPrice = detailData.eeList[0].suggestprice;
                            this.scoreList.forEach((item, index) => {
                                this.userSearch(item.msgid, index);
                            })
                        } else {
                            this.$message.error('查询面试信息失败');
                        }

                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 查询所有外包商
            getWbcom() {
                getListAll()
                    .then(res => {
                        this.outsourceno_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error);
                    });
            },

            // 行员查询
            userSearch(id, index) {
                getEmployUser(id).then(res => {
                    if (res.data.status == 200) {
                        let data = res.data.results[0];
                        this.scoreList[index].msgDepname = data.chargeName.split("-")[1];
                        this.scoreList.splice(0, 0);
                    } else {
                        this.$message.error('行员查询失败');
                    }

                }).catch(error => {
                    this.$message.error(error);
                })
            },

            // 获取图片地址
            getPhotoUrl(id) {
                getImageUrl({ documentno: id }).then(res => {
                    if (res.data.status == 200) {
                        this.facePhotoUrl = res.data.results.documentno;
                    } else {
                        this.$message.error('获取图片地址失败');
                    }
                })
            },

            // 查询风控征信
            searchFkzx(id, name) {
                const loading = common.loading(this);
                getWindControl({ certifNumber: id, userName: name }).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        // this.$set(this.approveData, 'courtinfo', res.data.results.court);
                        // this.$set(this.approveData, 'businessinfo', res.data.results.business);
                        if ((res.data.results.court == undefined || res.data.results.court == '无') && (res.data.results.business == undefined || res.data.results.business == '无')) {
                            this.$set(this.approveData, 'courtinfo', '无');
                        } else if ((res.data.results.court != undefined || res.data.results.court != '无') && (res.data.results.business == undefined || res.data.results.business == '无')) {
                            this.$set(this.approveData, 'courtinfo', '法院信息：' + res.data.results.court);
                        } else if ((res.data.results.court == undefined || res.data.results.court == '无') && (res.data.results.business != undefined || res.data.results.business != '无')) {
                            this.$set(this.approveData, 'courtinfo', '工商信息：' + res.data.results.business);
                        } else if ((res.data.results.court != undefined || res.data.results.court != '无') && (res.data.results.business != undefined || res.data.results.business != '无')) {
                            this.$set(this.approveData, 'courtinfo', '法院信息：' + res.data.results.court + '，工商信息：' + res.data.results.business);
                        }
                    } else {
                        this.$message.error('查询风控征信信息失败');
                    }
                })
            },

            // 查询是否重复入职
            searchIfCfrz(id, name) {
                // const loading = common.loading(this);
                getCheckedIdCard({ certifNumber: id, userName: name }).then(res => {
                    // loading.close();
                    if (res.data.status == 200) {
                        if (res.data.results.entry == "重复入职") {
                            // this.$message({
                            //     showClose: true,
                            //     duration: 0,
                            //     message: '该员工重复入职',
                            //     type: 'error'
                            // })
                            this.$set(this.approveData, 'iseverentry', '1');
                        } else {
                            this.$set(this.approveData, 'iseverentry', '2');
                        }
                        this.checkHmd(id, res.data.results.entry)
                    } else {
                        this.$message.error('查询入职信息失败');
                    }
                })
            },

            // 检查是否在黑名单中
            checkHmd(id, ifCf) {
                checkedIsBlack({ icard: id }).then(res => {
                    if (res.data.status == 200) {
                        console.log("黑名单", res.data)
                        if (ifCf == "重复入职" && res.data.results == true) {
                            this.$message({
                                showClose: true,
                                duration: 0,
                                message: '该外包人员重复入职/已被列入黑名单',
                                type: 'error'
                            })
                        } else if (ifCf == "重复入职" && res.data.results != true) {
                            this.$message({
                                showClose: true,
                                duration: 0,
                                message: '该外包人员重复入职',
                                type: 'error'
                            })
                        } else if (ifCf != "重复入职" && res.data.results == true) {
                            this.$message({
                                showClose: true,
                                duration: 0,
                                message: '该外包人员已被列入黑名单',
                                type: 'error'
                            })
                        }
                    } else {
                        this.$message.error('查询黑名单信息失败');
                    }
                })
            },

            // 查询公安信息
            searchGongAn(id, name) {
                // const loading = common.loading(this);
                getCheckedSecurity({ certifNumber: id, userName: name }).then(res => {
                    // loading.close();
                    if (res.data.status == 200) {
                        if (res.data.results.publicSecurity == "问题") {
                            this.$set(this.approveData, 'policecheck', '2');
                        } else {
                            this.$set(this.approveData, 'policecheck', '1');
                        }
                    } else {
                        this.$message.error('查询公安信息失败');
                    }
                })
            },


           // 获取文件名称
            getFileName(id, mark) {
                preview(id)
                .then(res => {
                    if (res.data.status == 200) {
                    if (mark == "zhengxin") { 
                        this.creditReport.push({"id":id,"docname": res.data.results.docname, 
                        "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                    } else if (mark == "shebao") {
                        this.sociaiSecrityPaymentProve.push({"id":id,"docname": res.data.results.docname,
                        "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                    } else if (mark == "baomi") {
                        this.confidentialityCommitment.push({"id":id,"docname": res.data.results.docname,
                        "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                    } else if (mark == "other") {
                        this.otherFile.push({"id":id,"docname": res.data.results.docname,
                        "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                    }
                    } else {
                    this.$message.error("获取文件名失败");
                    }
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 一键下载
            yjDownload() {
                if (
                this.approveData.creditReport == undefined &&
                this.approveData.confidentialityCommitment == undefined &&
                this.approveData.sociaiSecrityPaymentProve == undefined &&
                this.approveData.otherFile == undefined &&
                this.approveData.otherFile != " "
                ) {
                this.$message("没有文件可下载");
                return;
                }
                const self = this;
                common.onKeyDownload(this.approveData.instanceId, self);
            },

            //获取所属部门内容
            filDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            var fild = res.data.results[0].children;
                            var reg = /([^-]+)$/;
                            this.deptno_options = fild.filter(li => {
                                li.text = li.text.match(reg)[1];
                                return li;
                            })
                        } else {
                            this.$message.error("获取部门数据失败");
                        }

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
            },

            //查询小组名称
            searchGroup(val) {
                let params = {};
                params.depid = val.replace("dep", "");
                queryGroup(params)
                    .then(res => {
                        this.groupid_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },
            //查询项目名称
            searchProject() {
                let params = {};
                let depId = this.approveData.deptno;
                params.depId = depId.replace("dep", "");
                queryApplyList(params).then(res => {
                    let data = res.data.results;
                    this.projectno_options = data.filter(item => {
                    item.id = String(item.id);
                    return item;
                    });
                }).catch(error => {
                    this.$message.error("查询项目名称失败");
                });
            },
            //查询系统名称
            searchSystem() {
                let params = {};
                let depId = this.approveData.deptno;
                params.depId = depId.replace("dep", "");
                querySystemList(params).then(res => {
                    let data = res.data.results;
                    this.systemnos_options = data.filter(item => {
                    item.id = String(item.id);
                    return item;
                    });
                }).catch(error => {
                this.$message.error("查询系统名称失败");
                });
            },

            // 下载文件
            downloadFile(docId) {
                const self = this;
                common.downloadDoc(docId, self);
            },

            //ntko插件预览
            openNtkoWindow(obj, mark) { 
                let formJudge = obj.docname.split(".")[
                    obj.docname.split(".").length - 1
                ];
                if (formJudge == "jpg" || formJudge == "JPG" ||
                    formJudge == "jpeg" || formJudge == "JPEG" || 
                    formJudge == "png" || formJudge == "PNG") {
                    this.ylDialog = true;
                    let pos = obj.tag1.lastIndexOf("/");
                    let str = obj.tag1.substr(0, pos);
                    if (obj.tag2 != undefined) {
                        this.ylPhotoUrl = str + "/" + obj.tag2;
                    } else {
                        this.ylPhotoUrl = obj.tag1;
                    }
                } else if (formJudge == "zip" || formJudge == "rar" ||  formJudge == "txt") {
                    this.$message.error("不能预览压缩文件或txt文件格式");
                } else {
                    this.ntkoYl(obj.id);
                }
            },

            // ntko预览方法
            ntkoYl(objectId) {
                var url = window.location.href;
                let ntkoed = ntkoBrowser.ExtensionInstalled();
                if (ntkoed) {
                    ntkoBrowser.openWindow(
                        url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
                    );
                } else {
                    window.open(
                        url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
                    );
                }
                // 注册回调方法
                window.ntkoCloseEvent = function () {
                    alert("跨浏览器插件应用程序窗口已关闭!");
                };
            },

            // 其他文件状态改变
            changeOtherFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.otherFileName = file.file.name;
                        this.$set(this.approveData, 'otherFile', res.data.results);
                        this.getFileName(res.data.results, 'other');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },

            // 流转
            liuzhuan: function (params) {
                const loading = common.loading(this);
                this.flowItem.conclusion = params.conclusion;
                this.flowItem.suggest = params.suggest;
                let folw = {};
                let depid = this.approveData.deptno;
                folw.deptno = depid.replace("dep", "");
                folw.bps = this.flowItem;
                folw.queryresult = this.approveData.queryresult;
                folw.policecheck = this.approveData.policecheck;
                folw.personcredit = this.approveData.personcredit;
                folw.iseverentry = this.approveData.iseverentry;
                folw.courtinfo = this.approveData.courtinfo;
                // folw.businessinfo = this.approveData.businessinfo;
                folw.whetherMeetsClaim = this.approveData.whetherMeetsClaim;
                folw.meetRequirementsReason = this.approveData.meetRequirementsReason;
                folw.sociaiSecrityPayment = this.approveData.sociaiSecrityPayment;
                folw.otherFile = this.approveData.otherFile; //增加重新上传其他材料
                folw.id = this.approveData.id;
                return zhbfkzxAudit(folw)
                    .then(res => {
                        loading.close();
                        return res.data

                    })
                    .catch(error => {
                        this.$alert(error, { type: "error" });
                    });

            },

            // 流转并验证
            proving(data) {
                let status = new Promise(() => { });
                if (data.conclusion == "01") {
                    if (this.approveData.queryresult != 0 && this.approveData.policecheck != 2 && this.approveData.personcredit != 0) {
                        this.$refs['ruleFkpc'].validate((valid) => {
                            if (valid) {
                                status = this.liuzhuan(data).then((val) => {
                                    return val;
                                });
                            } else {
                                this.$message.error('操作失败,请填写必填项');
                                // return false;
                            }
                        })
                    } else {
                        if (this.approveData.policecheck == 2) {
                            this.$message.error('公安检查不通过');
                        } else if (this.approveData.personcredit == 0) {
                            this.$message.error('个人征信不通过');
                        } else if (this.approveData.queryresult == 0) {
                            this.$message.error('征信和风控不通过');
                        }
                    }
                } else {
                    status = this.liuzhuan(data).then((val) => {
                        return val;
                    });
                }
                return status;
            },


            //关闭
            close() {
                this.$router.push({ name: 'dashboard' });
            },

            // 总办议案
    getvalidMotion() {
      validMotion({fileType:0}).then(res => {
        console.log(res, '总办议案')
        const {results, status} = res.data;
        if(status === 200) {
          this.ceneralOfficeMotion_options = results.map(item => {
            item.label = item.fileName + "("+item.fileNo+")"
            return item;
          })
        }
      })
    },
    // 合同
    getvalidContract() {
      validContract().then(res => {
        console.log(res);
        const {results, status} = res.data;
        if(status === 200) {
          this.newContractId_options = results.map(item => {
            item.label = item.contractName + "(" + item.contractNo +")"
            return item
          })
        }
      })
    }
        }
    }
</script>
<style scoped>
    .approveTwoCon {
        color: rgba(38, 38, 38, 1);
        padding-bottom: 85px;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 200px;
        height: 24px;
        font-size: 16px;
        font-weight: 600;
        color: rgba(38, 38, 38, 1);
        line-height: 24px;
        display: inline-block;
    }

    .nav .wrapper {
        padding: 0 15px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .mainCon {
        padding: 0 15px;
    }

    /* first页面样式 */
    .contentFirst_title {
        height: 60px;
        line-height: 60px;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentFirst {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px 15px 15px;
    }

    /* second页面样式 */
    .approveTwo-modu {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        margin-top: 20px;
    }

    .contentTwo_title_p {
        color: #262626;
        font-weight: 600;
    }

    .contentTwo_title {
        height: 60px;
        line-height: 60px;
        background-color: #ffffff;
        padding: 0 15px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentTwo_inte {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px 15px 15px;
    }

    .row_approve p {
        font-size: 14px;
    }

    /* 简历信息页面样式 */
    .content {
        padding: 0 15px 15px 15px;
    }

    /* 底部栏 */
    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }

    /* 人脸照片 */
    .facePhoto {
        width: 90px;
        height: 90px;
    }

    /* 入职材料 */
    .preview,
    .download {
        color: #FBA42F;
        font-size: 14px;
        margin-left: 5px;
        cursor: pointer;
    }

    /* 表格中显示全部内容处理 */
    .table_supply_w {
        width: 100%;
        height: 36px;
        line-height: 36px;
        font-size: inherit;
        border: 1px solid #E4E7ED;
        background-color: #F5F7FA;
        border-radius: 4px;
        padding: 0 15px;
        margin: 0;
        cursor: pointer;
    }

    /* 预览弹窗内容居中 */
    .ylDialogCon {
        text-align: center;
    }

    /* 预览图片大小 */
    .ylPhoto {
        width: 600px;
        width: 500px;
    }

    .mstable {
        margin-top: 20px;
    }

    .againUpLoad {
        font-size: 14px;
        margin-left: 10px;
        color: #faad14;
        display: inline-block;
    }

    .contentTwo_title .yjButt {
        font-size: 14px;
        font-weight: normal;
    }
</style>
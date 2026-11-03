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
                <buttonCommon :button-name="closeName" :size="sizes" :fns="close"/>
              </el-col>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="mainCon">
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
                  <el-input v-model="approveData.userName" :disabled="true"/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>工号</p>
                  <el-input v-model="approveData.jobId" :disabled="true"/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>性别</p>
                  <el-radio-group v-model="approveData.sex">
                    <el-radio v-for="item in sex_options" :label="item.dictid" :key="item.dictid" disabled>{{ item.dictname }}</el-radio>
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
                  <el-input v-model="approveData.nativeplace" :disabled="true"/>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="picker_sty_fir">
                  <p>生日</p>
                  <el-date-picker :disabled="true" v-model="approveData.birthday" type="date"/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>婚姻状况</p>
                  <el-radio-group v-model="approveData.maritalStatus">
                    <el-radio v-for="(item,index) in marriage_options" :label="item.dictid" :key="index" :disabled="true">{{ item.dictname }}</el-radio>
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
                  <el-input :disabled="true" v-model="approveData.school"/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>专业</p>
                  <el-input :disabled="true" v-model="approveData.specialty"/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>学历</p>
                  <el-radio-group v-model="approveData.education">
                    <el-radio v-for="item in edu_options" :label="item.dictid" :key="item.dictid" :disabled="true">{{ item.dictname }}</el-radio>
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
                  <el-input :disabled="true" v-model="approveData.mobile"/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <!-- 公司名称 -->
                  <p>外包商</p>
                  <el-select v-model="approveData.outsourceNo" filterable placeholder disabled>
                    <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname" :value="item.orgcode"/>
                  </el-select>
                </div>
              </el-col>
              <el-col :span="8"/>
            </el-row>
          </div>

          <div class="row_approve">
            <el-row :gutter="60">
              <el-col :span="8">
                <div>
                  <p class="deptno_p">所属部门</p>
                  <el-select v-model="approveData.deptno" placeholder="请选择" disabled>
                    <el-option v-for="(item,index) in deptno_options" :key="index" :value="item.id" :label="item.text"/>
                  </el-select>
                </div>
              </el-col>
              <el-col :span="8">
                <!-- &nbsp; -->
                <div class="picker_sty">
                  <p>入职日期</p>
                  <el-date-picker :disabled="true" v-model="approveData.entryTime" type="date"/>
                </div>
              </el-col>
              <el-col :span="8"/>
            </el-row>
          </div>
          <div class="row_approve">
            <el-row :gutter="60">
              <el-col :span="8">
                <div>
                  <p>岗位</p>
                  <el-select v-model="approveData.position" placeholder="请选择" disabled>
                    <el-option v-for="item in position_options" :key="item.dictid" :label="item.dictname" :value="item.dictid"/>
                  </el-select>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>工作内容</p>
                  <el-input :disabled="true" v-model="approveData.workContent" type="textarea" autosize/>
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
          <!-- eslint-disable-next-line vue/this-in-template-->
          <div v-if="this.approveData.userType == 1" class="row_approve">
            <el-row :gutter="60">
              <el-col :span="8">
                <div class="wbrzxmmc">
                  <p>项目名称</p>
                  <el-select v-model="approveData.projectno" disabled>
                    <el-option v-for="item in projectno_options" :key="item.id" :label="item.name" :value="item.id"/>
                  </el-select>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>项目经理</p>
                  <el-select v-model="approveData.organizers" disabled>
                    <el-option v-for="item in organizers_project_options" :key="item.badge" :label="item.empname+'('+item.badge+')'" :value="item.badge"/>
                  </el-select>
                </div>
              </el-col>
              <el-col :span="8"/>
            </el-row>
          </div>

          <div v-else class="row_approve">
            <el-row :gutter="60">
              <el-col :span="8">
                <div>
                  <p>系统名称</p>
                  <el-select v-model="approveData.systemnos" disabled>
                    <el-option v-for="item in systemnos_options" :key="item.id" :label="item.name" :value="item.id"/>
                  </el-select>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>项目经理</p>
                  <el-select v-model="approveData.organizers" disabled>
                    <el-option v-for="item in organizers_options" :key="item.pcode" :label="item.text" :value="item.pcode"/>
                  </el-select>
                </div>
              </el-col>
              <el-col :span="8"/>
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
          <!-- 人力结算 -->
          <div class="contentTwo_inte">
            <div class="row_approve mstable">
              <el-table :data="scoreList" style="width: 100%">

                <el-table-column :show-overflow-tooltip="true" label="角色" prop="msgRole" width="150" >
                  <template slot-scope="scope">
                    <span v-if="!scope.row.edited">{{ getDictName(msgRoleType,scope.row.msgRole) }}</span>
                    <el-select v-if="scope.row.edited" v-model="scope.row.msgRole" >
                      <el-option
                        v-for="item in getMsgRoleType"
                        :key="item.dictId"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </template>
                </el-table-column>

                <el-table-column prop="msgname" label="面试官" width="140"/>
                <el-table-column :show-overflow-tooltip="true" prop="deptname" label="部门" width="180"/>
                <el-table-column :show-overflow-tooltip="true" label="面试角色" prop="msemployRole" width="150" >
                  <template slot-scope="scope">
                    <span v-if="!scope.row.edited">{{ getDictName(msemployRoleType,scope.row.msemployRole) }}</span>
                    <el-select v-if="scope.row.edited" v-model="scope.row.msemployRole" >
                      <el-option
                        v-for="item in getMsemployRoleType"
                        :key="item.dictId"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </template>
                </el-table-column>

                <el-table-column
                  v-if="ifProjectScale"
                  :show-overflow-tooltip="true"
                  label="项目规模"
                  prop="projectScale"
                  width="150" >
                  <template slot-scope="scope">
                    <span v-if="!scope.row.edited">{{ getDictName(interGradeType,scope.row.projectScale) }}</span>
                    <el-select v-if="scope.row.edited" v-model="scope.row.projectScale" >
                      <el-option
                        v-for="item in getInterGradeType"
                        :key="item.dictId"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </template>
                </el-table-column>

                <el-table-column
                  v-if="ifProjectScaleXmjl"
                  :show-overflow-tooltip="true"
                  label="项目规模"
                  prop="projectScaleXmjl"
                  width="150" >
                  <template slot-scope="scope">
                    <span v-if="!scope.row.edited">{{ getDictName(projectScaleXmjlType,scope.row.projectScaleXmjl) }}</span>
                    <el-select v-if="scope.row.edited" v-model="scope.row.projectScaleXmjl" >
                      <el-option
                        v-for="item in getProjectScaleXmjlType"
                        :key="item.dictId"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </template>
                </el-table-column>

                <el-table-column
                  v-if="ifProjectScaleJcjl"
                  :show-overflow-tooltip="true"
                  label="项目规模"
                  prop="projectScaleJcjl"
                  width="150" >
                  <template slot-scope="scope">
                    <span v-if="!scope.row.edited">{{ getDictName(projectScaleJcjlType,scope.row.projectScaleJcjl) }}</span>
                    <el-select v-if="scope.row.edited" v-model="scope.row.projectScaleJcjl" >
                      <el-option
                        v-for="item in getProjectScaleJcjlType"
                        :key="item.dictId"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </template>
                </el-table-column>

                <el-table-column
                  v-if="ifUniversalToolUse"
                  :show-overflow-tooltip="true"
                  prop="universalToolUse"
                  label="通用工具使用"
                  width="180"/>
                <el-table-column
                  v-if="ifProjectManageKnowledge"
                  :show-overflow-tooltip="true"
                  prop="projectManageKnowledge"
                  label="项目管理知识"
                  width="180"/>
                <el-table-column :show-overflow-tooltip="true" prop="commExpression" label="沟通表达" width="180"/>
                <el-table-column :show-overflow-tooltip="true" prop="projectExperience" label="项目经验" width="180"/>
                <el-table-column
                  v-if="ifCodeDevelopment"
                  :show-overflow-tooltip="true"
                  prop="codeDevelopment"
                  label="代码研发"
                  width="180"/>
                <el-table-column
                  v-if="ifArchitectureDesign"
                  :show-overflow-tooltip="true"
                  prop="architectureDesign"
                  label="架构设计"
                  width="180"/>
                <el-table-column
                  v-if="ifProfesKnowledge"
                  :show-overflow-tooltip="true"
                  prop="profesKnowledge"
                  label="专业知识"
                  width="180"/>
                <el-table-column
                  v-if="ifStandCondition"
                  :show-overflow-tooltip="true"
                  prop="standCondition"
                  label="驻场情况"
                  width="180"/>
                <el-table-column
                  v-if="ifProductAbility"
                  :show-overflow-tooltip="true"
                  prop="productAbility"
                  label="产品能力"
                  width="180"/>
                <el-table-column :show-overflow-tooltip="true" prop="scoreResult" label="评分结果" width="180"/>
              </el-table>
            </div>
          </div>
        </div>
        <!-- 定级信息 -->
        <div>
          <div class="contentTwo_title">
            <p class="contentTwo_title_p">定级信息</p>
          </div>
          <div class="content">
            <div class="row_approve">
              <el-row :gutter="60">
                <el-col :span="8">
                  <div>
                    <p>级别</p>
                    <el-input v-model="levelValue.dictname" disabled/>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <p>定级人员</p>
                    <el-input v-model="evaluatorName" disabled/>
                  </div>
                </el-col>
              </el-row>
              <el-row :gutter="60">
                <el-col :span="8">
                  <div>
                    <p>定级理由</p>
                    <el-input v-model="approveData.levelReason" type="textarea" :autosize="true" :disabled="true" resize="none" style="width: 100%;"/>
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
                    <el-radio v-for="item in certiftype_options" :label="item.dictid" :key="item.dictid" disabled>{{ item.dictname }}</el-radio>
                  </el-radio-group>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>证件号码</p>
                  <el-input v-model="approveData.certifNumber" disabled/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>政治面貌</p>
                  <el-radio-group v-model="approveData.politicalStatus">
                    <el-radio
                      v-for="item in politiccontenance_options"
                      :label="item.dictid"
                      :key="item.dictid"
                      disabled
                      size="medium">{{ item.dictname }}</el-radio>
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
                  <el-input
                    v-if="approveData.address != undefined"
                    v-model="approveData.address"
                    type="textarea"
                    autosize
                    disabled
                    placeholder="请输入"/>
                  <el-input v-else placeholder="无" disabled/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>配偶姓名</p>
                  <el-input
                    v-if="approveData.partnerName != undefined"
                    v-model="approveData.partnerName"
                    disabled
                    placeholder="请输入"/>
                  <el-input v-else placeholder="无" disabled/>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>配偶证件号码</p>
                  <el-input
                    v-if="approveData.idcardNumber != undefined"
                    v-model="approveData.idcardNumber"
                    disabled
                    placeholder="请输入"/>
                  <el-input v-else placeholder="无" disabled/>
                </div>
              </el-col>
            </el-row>
          </div>

          <div class="row_approve">
            <el-row :gutter="60">
              <el-col :span="8">
                <div>
                  <p>所获证书</p>
                  <el-input
                    v-if="approveData.certificate != undefined"
                    v-model="approveData.certificate"
                    type="textarea"
                    autosize
                    disabled
                    placeholder="请输入"/>
                  <el-input v-else placeholder="无" disabled/>
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
                        <el-date-picker
                          v-model="scope.row.studyDate"
                          :disabled="true"
                          type="monthrange"
                          range-separator="-"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="schoolName" label="学校" width="270">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.schoolName" :disabled="true" placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="professName" label="专业" width="270">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.professName" :disabled="true" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="eduGenre" label="教育类别" width="270">
                      <template slot-scope="scope">
                        <el-select v-model="scope.row.eduGenre" :disabled="true" placeholder>
                          <el-option
                            v-for="(item,index) in eduGenre_options"
                            :key="item.index"
                            :label="item.dictname"
                            :value="item.dictid"/>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column prop="studyForm" label="学习形式" width="270">
                      <template slot-scope="scope">
                        <el-select v-model="scope.row.studyForm" :disabled="true" placeholder>
                          <el-option
                            v-for="(item,index) in studyForm_options"
                            :key="item.index"
                            :label="item.dictname"
                            :value="item.dictid"/>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column label/>
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
                        <el-date-picker
                          v-model="scope.row.workDate"
                          :disabled="true"
                          type="monthrange"
                          range-separator="-"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="comName" label="公司名称" width="270">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.comName" :disabled="true" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="mainWork" label="主要工作" width="810">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.mainWork" :disabled="true" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column label/>
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
                        <el-date-picker
                          v-model="scope.row.projDate"
                          :disabled="true"
                          type="monthrange"
                          range-separator="-"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="projName" label="项目名称" width="270">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.projName" :disabled="true" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="mainWork" label="主要工作" width="810">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.mainWork" :disabled="true" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column label/>
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
                          <el-date-picker v-model="scope.row.prizeDate" :disabled="true" type="date" placeholder/>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="prizeName" label="奖励名称" width="270">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.prizeName" :disabled="true" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="prizeGrade" label="奖励级别" width="270">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.prizeGrade" :disabled="true" placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column prop="prizeCause" label="奖励原因" width="540">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.prizeCause" :disabled="true" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column label/>
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
                        <el-input v-model="scope.row.familyName" :disabled="true" placeholder/>
                      </template>
                    </el-table-column>

                    <el-table-column prop="feminist" label="称谓" width="270">
                      <template slot-scope="scope">
                        <el-select v-model="scope.row.feminist" :disabled="true" placeholder>
                          <el-option
                            v-for="(item,index) in feminist_options"
                            :key="item.index"
                            :label="item.dictname"
                            :value="item.dictid"/>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column :disabled="true" prop="familyBirthday" label="出生年月日" width="270">
                      <template slot-scope="scope">
                        <div class="picker_sty">
                          <el-date-picker :disabled="true" v-model="scope.row.familyBirthday" type="date" placeholder/>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="unit" label="工作单位及职务" width="540">
                      <template slot-scope="scope">
                        <el-input :disabled="true" v-model="scope.row.unit" type="textarea" autosize placeholder/>
                      </template>
                    </el-table-column>
                    <el-table-column label/>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>

      <!-- 入职材料 -->
      <div class="approveTwo-modu">
        <div class="contentTwo_title">
          <p class="contentTwo_title_p">
            入职材料
            <!-- <span class="xkg-color-yellow xkg-marginleft-5px xkg-cursor-pointer yjButt" @click="yjDownload">一键下载</span> -->
          </p>
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
                  <div v-for="(item, index) in creditReport" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'zhengxin')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
                <div>
                  <p>
                    保密承诺书、服务提供/外包员工背景调查表/厂商承诺书
                  </p>
                  <div v-for="(item, index) in confidentialityCommitment" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'baomi')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
                <div>
                  <p>
                    学历、学位证书
                  </p>
                  <div v-for="(item, index) in dueDiligenceReport" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'jdbg')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
                <div>
                  <p>
                    宁银消金外包人员日常行为规范承诺书
                  </p>
                  <div v-for="(item, index) in letterOfCommitment" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'cnsp')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>
                    社保缴纳证明/劳动合同/纳税证明
                  </p>
                  <div v-for="(item, index) in sociaiSecrityPaymentProve" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'shebao')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
                <div>
                  <p>
                    身份证正反面
                  </p>
                  <div v-for="(item, index) in otherFile" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'other')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
                <div>
                  <p>其他(无社保缴纳证明、社保代缴、学籍在线报告等)</p>
                  <div v-for="(item,index) in qtFile" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'qtfj')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
                <div>
                  <p>
                    案件防控和安全管理目标责任书
                  </p>
                  <div v-for="(item, index) in liabilityStatement" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'lsfj')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <p>
                    体检报告
                  </p>
                  <div v-for="(item, index) in medicalExamination" :key="index">
                    {{ item.docname }}
                    <span class="preview" @click="openNtkoWindow(item, 'mefj')">预览</span>
                    <span class="download" @click="downloadFile(item.id)">下载</span>
                  </div>
                </div>
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

      <!-- 操作日志 -->
      <div class="czLog">
        <flow-logs ref="log" :process-inst-id="flowItem.processInstanceId"/>
      </div>
    </div>

    <!-- 底部栏样式2 -->
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flow-item="flowItem"/>
    </div>

    <!-- 预览图片弹窗 -->
    <el-dialog :visible.sync="ylDialog" title>
      <div class="ylDialogCon">
        <el-image :src="ylPhotoUrl" :fit="photoFit" :preview-src-list="ylPhotoUrl.split(',')" style="width:600px;height:400px"/>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  // eslint-disable-next-line no-unused-vars
  getTree,
  queryResourceDept,
  queryGroup,
  querySystemList,
  // eslint-disable-next-line no-unused-vars
  queryProjectList,
  queryProjectEmp,
  queryApplyList,
  queryEmployeeTree,
  // eslint-disable-next-line no-unused-vars
  querySystemTree,
  queryKqTypeList,
  // eslint-disable-next-line no-unused-vars
  validMotion,
  // eslint-disable-next-line no-unused-vars
  validContract
} from '@/api/integrated/wbryzrlc'
import {
  // zhbfkzxAudit,
  twoManagerAudit,
  getWbrzDetail,
  getImageUrl,
  // eslint-disable-next-line no-unused-vars
  checkedExistEntry,
  getCheckedIdCard
} from '@/api/integrated/newWbrz'
import { queryInterDataList } from '@/api/integrated/wbms'
import { getEmployUser, preview, getListAll } from '@/api/common/workflowApi'
import common from '@/api/common'
import buttonOrange from '@/components/eleButton/buttonOrange' // 引入按钮组件1
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import flowLogs from '@/views/plugins/camunda/FlowLogs'
import approveBottom from '@/views/plugins/camunda/approveBottom'
import { getDicEntry } from '@/api/eosDictEntry'
import Moment from 'moment'
// eslint-disable-next-line no-unused-vars
import { getDictName, getTypeDicts } from '@/utils/dict'
// 引入ntko
const ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min')
  .ntkoBrowser
import { checkedIsBlack } from '@/api/integrated/wbHmd'
export default {
  components: { buttonOrange, buttonCommon, flowLogs, approveBottom },
  data() {
    return {
      msgRoleType: 'MSG_ROLE_TYPE',
      msemployRoleType: 'MSEMPLOY_ROLE_TYPE',
      interGradeType: 'INTER_GRADE_TYPE',
      projectScaleXmjlType: 'PROJECT_SCALE_XMJL_TYPE',
      projectScaleJcjlType: 'PROJECT_SCALE_JCJL_TYPE',
      ifUniversalToolUse: false, // 通用工具使用
      ifProjectManageKnowledge: false, // 项目管理知识
      ifProjectScaleXmjl: false, // 项目规模-项目经理
      ifProjectScaleJcjl: false, // 项目规模-测试经理
      ifProjectScale: false, // 项目规模
      ifCodeDevelopment: false, // 代码研发
      ifArchitectureDesign: false, // 架构设计
      ifProfesKnowledge: false, // 专业知识
      ifStandCondition: false, // 驻场情况
      ifProductAbility: false, // 产品能力
      approveData: {}, // 审批页面信息
      levelValue: {},        //定级级别
      evaluatorName: '',        //定级级别
      level_options: [],
      genferData: [],
      sex_options: [],
      edu_options: [],
      sbjn_options: [],
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
      organizers_options: [],
      organizers_project_options: [],
      systemnos_options: [],
      purchaseItem: '', // 采购项目（没有）
      purchaseItem_options: [],
      kqtype_options: [],
      educationData: [], // 业务字段-学历
      experienceData: [], // 业务字段-项目经验
      sf_options: [], // 是否
      iscreditquery_options: [],
      svnlimit_options: [],
      eduGenre_options: [],
      studyForm_options: [],
      feminist_options: [],
      otherFile: [],
      photoFit: 'scale-down',
      creditReport: [],
      sociaiSecrityPaymentProve: [],
      confidentialityCommitment: [],
      dueDiligenceReport: [],
      letterOfCommitment: [], // 行为规范承诺书
      liabilityStatement: [], // 案件防控和安全管理目标责任书
      medicalExamination: [], // 体检报告
      qtFile: [],
      processInstID: '', // 流程ID
      sizes: 'mini',
      closeName: '关闭', // 关闭按钮
      flowItem: {}, // bps数据
      facePhotoUrl: '', // 人脸图片地址
      ylDialog: false, // 是否显示预览图片弹窗
      ylPhotoUrl: '', // 预览图片地址
      scoreList: [], // 面试打分数据
      unitPrice: '', // 人员单价
      outsourceno_options: [],
      xnzm_options: [
        {
          // 虚拟桌面
          dictid: '01',
          dictname: '是'
        },
        {
          dictid: '02',
          dictname: '否'
        }
      ],
      managerUsername: '', // 项目经理名称
      systemPrincipalname: '', // 系统负责人名称
      ceneralOfficeMotion_options: [], // 总办议案
      newContractId_options: [] // 合同
    }
  },
  computed: {
    getMsgRoleType() {
      return getTypeDicts(this.msgRoleType)
    },
    getMsemployRoleType() {
      return getTypeDicts(this.msemployRoleType)
    },
    getInterGradeType() {
      return getTypeDicts(this.interGradeType)
    },
    getProjectScaleXmjlType() {
      return getTypeDicts(this.projectScaleXmjlType)
    },
    getProjectScaleJcjlType() {
      return getTypeDicts(this.projectScaleJcjlType)
    }
  },
  created() { },
  mounted() { },
  methods: {
    // 初始化
    init(item) {
      console.log('二级部', item)
      this.flowItem = item
      this.flowItem.processInstId = item.processInstanceId
      this.$refs.log.init(item.processInstanceId)
      this.processInstID = item.processInstanceId
      this.getDic()
      queryKqTypeList() // 查询考勤类型
        .then(res => {
          this.kqtype_options = res.data.results
        })
        .catch(error => {
          this.$message.error(error)
        }) // end
      this.getApproveDetail(item.processInstanceId)
      this.filDep()
      this.getWbcom()
    },

    // 获取审批页面信息
    getApproveDetail(val) {
      // eslint-disable-next-line no-unused-vars
      const params = {}
      getWbrzDetail({ instanceId: val }).then(res => {
        if (res.data.status === 200) {
          res.data.results.education = res.data.results.education.toString()
          res.data.results.entryTime = Moment(
            res.data.results.entryTime
          ).format('YYYY-MM-DD')
          if (res.data.results.purchaseItem === undefined) {
            res.data.results.purchaseItem = '无'
          }
          if (res.data.results.learnExperience !== undefined) {
            res.data.results.learnExperience = JSON.parse(
              res.data.results.learnExperience
            )
          } else {
            res.data.results.learnExperience = []
          }
          if (res.data.results.mainJobs !== undefined) {
            res.data.results.mainJobs = JSON.parse(res.data.results.mainJobs).filter(li => {
              // var strcheck = new RegExp("[\u4E00-\u9FA5]+");
              for (var i = 0; i < li.workDate.length; i++) {
                if (li.workDate[i] !== null) {
                  if (Moment(li.workDate[i].toString()).format('YYYY-MM') === 'Invalid date') {
                    li.workDate[i] = ''
                  } else {
                    li.workDate[i] = Moment(li.workDate[i].toString()).format('YYYY-MM')
                  }
                } else {
                  li.workDate[i] = ''
                }
              }
              return li
            })
          } else {
            res.data.results.mainJobs = []
          }
          if (res.data.results.mainItem !== undefined) {
            res.data.results.mainItem = JSON.parse(res.data.results.mainItem).filter(li => {
              // var strcheck = new RegExp("[\u4E00-\u9FA5]+");
              for (var i = 0; i < li.projDate.length; i++) {
                if (li.projDate[i] !== null) {
                  if (Moment(li.projDate[i].toString()).format('YYYY-MM') === 'Invalid date') {
                    li.projDate[i] = ''
                  } else {
                    li.projDate[i] = Moment(li.projDate[i].toString()).format('YYYY-MM')
                  }
                  // li.projDate[i] = Moment(li.projDate[i].toString()).format('YYYY-MM');
                } else {
                  li.projDate[i] = ''
                }
              }
              return li
            })
          } else {
            res.data.results.mainItem = []
          }
          if (res.data.results.mainReward !== undefined) {
            res.data.results.mainReward = JSON.parse(
              res.data.results.mainReward
            )
          } else {
            res.data.results.mainReward = []
          }
          if (res.data.results.mainFamily !== undefined) {
            res.data.results.mainFamily = JSON.parse(
              res.data.results.mainFamily
            )
          } else {
            res.data.results.mainFamily = []
          }
          if (res.data.results.isOtherProject !== undefined) {
            // eslint-disable-next-line no-eval
            res.data.results.isOtherProject = eval(
              res.data.results.isOtherProject.toLowerCase()
            )
          }
          if (
            res.data.results.deptno !== undefined &&
              res.data.results.deptno.indexOf('dep') === -1
          ) {
            res.data.results.deptno = 'dep' + res.data.results.deptno
          }
          this.approveData = res.data.results
          this.getInterviewInfos(res.data.results.id)
          if(this.approveData.evaluatorId) {
            this.searchLevel(this.approveData)
          }
          this.$nextTick(() => {
            this.searchIfCfrz(
              this.approveData.certifNumber,
              this.approveData.userName
            )
            if (
              res.data.results.creditReport !== undefined &&
                res.data.results.creditReport !== ' '
            ) {
              const creditReport = res.data.results.creditReport.split(',')
              creditReport.forEach(a => {
                this.getFileName(a, 'zhengxin')
              })
            }
            if (
              res.data.results.sociaiSecrityPaymentProve !== undefined &&
                res.data.results.sociaiSecrityPaymentProve !== ' '
            ) {
              const sociaiSecrityPaymentProve = res.data.results.sociaiSecrityPaymentProve.split(',')
              sociaiSecrityPaymentProve.forEach(a => {
                this.getFileName(a, 'shebao')
              })
            }
            if (
              res.data.results.confidentialityCommitment !== undefined &&
                res.data.results.confidentialityCommitment !== ' '
            ) {
              const confidentialityCommitment = res.data.results.confidentialityCommitment.split(',')
              confidentialityCommitment.forEach(a => {
                this.getFileName(a, 'baomi')
              })
            }
            // 行为规范承诺书
            if (
              res.data.results.letterOfCommitment !== undefined &&
                res.data.results.letterOfCommitment !== ' '
            ) {
              const letterOfCommitment = res.data.results.letterOfCommitment.split(',')
              letterOfCommitment.forEach(a => {
                this.getFileName(a, 'cnsp')
              })
            }
            // 案件防控和安全管理目标责任书
            if (
              res.data.results.liabilityStatement !== undefined &&
                res.data.results.liabilityStatement !== ' '
            ) {
              const liabilityStatement = res.data.results.liabilityStatement.split(',')
              liabilityStatement.forEach(a => {
                this.getFileName(a, 'lsfj')
              })
            }
            // 体检报告
            if (
              res.data.results.medicalExamination !== undefined &&
                res.data.results.medicalExamination !== ' '
            ) {
              const medicalExamination = res.data.results.medicalExamination.split(',')
              medicalExamination.forEach(a => {
                this.getFileName(a, 'mefj')
              })
            }
            if (
              res.data.results.dueDiligenceReport !== undefined &&
                  res.data.results.dueDiligenceReport !== ' '
            ) {
              const dueDiligenceReport = res.data.results.dueDiligenceReport.split(',')
              dueDiligenceReport.forEach(a => {
                this.getFileName(a, 'jdbg')
              })
            }
            if (
              res.data.results.qtFile !== undefined &&
                  res.data.results.qtFile !== ' '
            ) {
              const qtFile = res.data.results.qtFile.split(',')
              qtFile.forEach(a => {
                this.getFileName(a, 'qtfj')
              })
            }
            if (
              res.data.results.otherFile !== undefined &&
                res.data.results.otherFile !== ' '
            ) {
              const otherFile = res.data.results.otherFile.split(',')
              otherFile.forEach(a => {
                this.getFileName(a, 'other')
              })
            }
            if (res.data.results.faceimage !== undefined) {
              this.getPhotoUrl(this.approveData.faceimage)
            }
            this.searchGroup(this.approveData.deptno)
            this.searchEpmlo(this.approveData.deptno)
            this.searchProject()
            this.searchSystem()
            this.searchProjectEmp()
          })
        } else {
          this.$message.error('获取数据失败')
        }
      })
    },

    // 查询是否重复入职
    searchIfCfrz(id, name) {
      const loading = common.loading(this)
      getCheckedIdCard({ certifNumber: id, userName: name }).then(res => {
        loading.close()
        if (res.data.status === 200) {
          this.checkHmd(id, res.data.results.entry)
        } else {
          this.$message.error('查询入职信息失败')
        }
      })
    },

    // 检查是否在黑名单中
    checkHmd(id, ifCf) {
      checkedIsBlack({ icard: id }).then(res => {
        if (res.data.status === 200) {
          console.log('黑名单', res.data)
          if (ifCf === '重复入职' && res.data.results === true) {
            this.$message({
              showClose: true,
              duration: 0,
              message: '该外包人员重复入职/已被列入黑名单',
              type: 'error'
            })
          } else if (ifCf === '重复入职' && res.data.results !== true) {
            this.$message({
              showClose: true,
              duration: 0,
              message: '该外包人员重复入职',
              type: 'error'
            })
          } else if (ifCf !== '重复入职' && res.data.results === true) {
            this.$message({
              showClose: true,
              duration: 0,
              message: '该外包人员已被列入黑名单',
              type: 'error'
            })
          }
        } else {
          this.$message.error('查询黑名单信息失败')
        }
      })
    },

    // 查询行内负责人
    searchEpmlo(val) {
      let params = ''
      params = val.replace('dep', '')
      queryEmployeeTree(params).then(res => {
        const data = res.data.results
        this.organizers_options = data[0].children
      }).catch(error => {
        this.$message.error(error)
      })
    },

    // 查询项目经理
    searchProjectEmp(val) {
      if (this.approveData.projectno != null || this.approveData.projectno !== undefined) {
        const params = {}
        params.id = this.approveData.projectno
        queryProjectEmp(params).then(res => {
          this.organizers_project_options = res.data.results
          // this.jibenData.organizers = res.data.results[0].badge;
        // eslint-disable-next-line handle-callback-err
        }).catch(error => {
          this.$message.error('查询项目经理失败')
        })
      }
    },
    // 获取业务字典
    getDic() {
      getDicEntry({
        dicttypeid:
            'BUSI_OUTSOURCE_GENFER,BUSI_OUTSOURCE_MARITALSTATUS,SBJN,MSDF_EDUCATION,MSXM_LEVEL,BUSI_OUTSOURCE_POSITION,NEWKG_CERTIFTYPE,NEWKG_POLITICAL,NEW_BUSI_OUTSOURCE_INTERVRESULT,NEWYY_EDUCATION,LEARNMODALITY,NEWKG_CHENGWEI,NEWKG_RZSF'
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        const data = res.data.results
        // 性别
        this.sex_options = data.filter(item => {
          return item.dicttypeid === 'BUSI_OUTSOURCE_GENFER'
        })
        // 社保缴纳
        this.sbjn_options = data.filter(item => {
          return item.dicttypeid === 'SBJN'
        })
        // 婚姻状况
        this.marriage_options = data.filter(item => {
          return item.dicttypeid === 'BUSI_OUTSOURCE_MARITALSTATUS'
        })
        // 岗位
        this.position_options = data.filter(item => {
          return item.dicttypeid === 'BUSI_OUTSOURCE_POSITION'
        })
        this.educationData = data.filter(item => {
          return item.dicttypeid === 'MSDF_EDUCATION'
        })
        this.experienceData = data.filter(item => {
          return item.dicttypeid === 'MSXM_LEVEL'
        })
        // 证件类型
        this.certiftype_options = data.filter(item => {
          return item.dicttypeid === 'NEWKG_CERTIFTYPE'
        })
        // 政治面貌
        this.politiccontenance_options = data.filter(item => {
          return item.dicttypeid === 'NEWKG_POLITICAL'
        })
        // 面试结果
        this.intervresult_options = data
          .filter(item => {
            return item.dicttypeid === 'NEW_BUSI_OUTSOURCE_INTERVRESULT'
          })
          .reverse()
          // 学历
        this.eduGenre_options = data.filter(item => {
          return item.dicttypeid === 'NEWYY_EDUCATION'
        })
        this.edu_options = this.eduGenre_options
        // 学习形式
        this.studyForm_options = data.filter(item => {
          return item.dicttypeid === 'LEARNMODALITY'
        })
        // 称谓
        this.feminist_options = data.filter(item => {
          return item.dicttypeid === 'NEWKG_CHENGWEI'
        })
        // 是否
        this.sf_options = data.filter(item => {
          return item.dicttypeid === 'NEWKG_RZSF'
        })
        return data
      })
    },
    // 查询级别
    searchLevel(val) {
      getDicEntry({
        dicttypeid: 'EVALUATION_LEVEL'
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        const data = res.data.results
          // 级别
        this.level_options = data.filter(item => {
          return item.dicttypeid === 'EVALUATION_LEVEL'
        })
        this.levelValue = this.level_options.find(ele => ele.dictid === val.evaluationLevel) || {};
        return data
      })
      getEmployUser(val.evaluatorId)
        .then(res => {
          if (res.data.status === 200) {
            const data = res.data.results[0]
            this.evaluatorName = data.empname;
          } else {
            this.$message.error('行员查询失败')
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    // 查询面试信息
    getInterviewInfos(lcId) {

      const params = {}
      params.id = lcId
      queryInterDataList(params)
        .then(res => {
          var detailData = res.data.results
          if (res.data.status === 200) {
            this.scoreList = detailData.scoreList
            this.selectMsempRole(0)
          } else {
            this.$message.error('查询面试信息失败')
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    // 根据不同的角色进行选择展示的列
    selectMsempRole(index) {
      const msRole = this.scoreList[index].msemployRole
      const num = this.scoreList.length
      if (msRole === 'projl') {
        for (var j1 = 0; j1 < num; j1++) {
          this.clearPro()
        }
        this.ifUniversalToolUse = true
        this.ifProjectManageKnowledge = true
        this.ifProjectScaleXmjl = true
      } else if (msRole === 'techjl') {
        for (var j2 = 0; j2 < num; j2++) {
          this.clearPro()
        }
        this.ifCodeDevelopment = true
        this.ifArchitectureDesign = true
        this.ifProjectScaleJcjl = true
      } else if (msRole === 'testjl') {
        for (var j3 = 0; j3 < num; j3++) {
          this.clearPro()
        }
        this.ifProfesKnowledge = true
        this.ifProjectManageKnowledge = true
        this.ifStandCondition = true
        this.ifProjectScaleJcjl = true
      } else if (msRole === 'pmjl') {
        for (var j4 = 0; j4 < num; j4++) {
          this.clearPro()
        }
        this.ifUniversalToolUse = true
        this.ifProductAbility = true
      } else if (msRole === 'dev') {
        // 判断角色是否是开发人员
        for (var j5 = 0; j5 < num; j5++) {
          this.clearPro()
        }
        this.ifCodeDevelopment = true
        this.ifArchitectureDesign = true
      } else if (msRole === 'testps') {
        for (var j6 = 0; j6 < num; j6++) {
          this.clearPro()
        }
        this.ifProfesKnowledge = true
        this.ifStandCondition = true
      } else if (msRole === 'needps') {
        for (var j7 = 0; j7 < num; j7++) {
          this.clearPro()
        }
        this.ifUniversalToolUse = true
        this.ifProductAbility = true
      }
    },
    /**
     * 重置动态显示项
     */
    clearPro() {
      this.ifUniversalToolUse = false // 通用工具使用
      this.ifProjectManageKnowledge = false // 项目管理知识
      this.ifProjectScaleXmjl = false // 项目规模-项目经理
      this.ifProjectScaleJcjl = false // 项目规模-测试经理
      this.ifProjectScale = false // 项目规模
      this.ifCodeDevelopment = false // 代码研发
      this.ifArchitectureDesign = false // 架构设计
      this.ifProfesKnowledge = false // 专业知识
      this.ifStandCondition = false // 驻场情况
      this.ifProductAbility = false // 产品能力
    },

    // 查询所有外包商
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results
        })
        .catch(error => {
          this.$message.error(error)
        })
    },

    // 行员查询
    userSearch(id, index) {
      getEmployUser(id)
        .then(res => {
          if (res.data.status === 200) {
            const data = res.data.results[0]
            this.scoreList[index].msgDepname = data.chargeName.split('-')[1]
            this.scoreList.splice(0, 0)
          } else {
            this.$message.error('行员查询失败')
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },

    // 获取图片地址
    getPhotoUrl(id) {
      getImageUrl({ documentno: id }).then(res => {
        if (res.data.status === 200) {
          this.facePhotoUrl = res.data.results.documentno
        } else {
          this.$message.error('获取图片地址失败')
        }
      })
    },

    // 获取文件名称
    getFileName(id, mark) {
      preview(id)
        .then(res => {
          if (res.data.status === 200) {
            if (mark === 'zhengxin') {
              this.creditReport.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'shebao') {
              this.sociaiSecrityPaymentProve.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'baomi') {
              this.confidentialityCommitment.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'jdbg') {
              this.dueDiligenceReport.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'qtfj') {
              this.qtFile.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'other') {
              this.otherFile.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'cnsp') {
              this.letterOfCommitment.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'lsfj') {
              this.liabilityStatement.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            } else if (mark === 'mefj') {
              this.medicalExamination.push({ 'id': id, 'docname': res.data.results.docname,
                'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
            }
          } else {
            this.$message.error('获取文件名失败')
          }
        }).catch(error => {
          this.$message.error(error)
        })
    },

    // 一键下载
    yjDownload() {
      if (
        this.approveData.creditReport === undefined &&
          this.approveData.confidentialityCommitment === undefined &&
          this.approveData.sociaiSecrityPaymentProve === undefined &&
          this.approveData.qtFile === undefined &&
          this.approveData.otherFile === undefined &&
          this.approveData.otherFile === undefined &&
          this.approveData.otherFile !== ' '
      ) {
        this.$message('没有文件可下载')
        return
      }
      const self = this
      common.onKeyDownload(this.approveData.instanceId, self)
    },

    // 获取所属部门内容
    filDep() {
      const params = {}
      params.objectcode = '6000000300'
      params.objectgrade = '2'
      queryResourceDept(params)
        .then(res => {
          if (res.data.status === 200) {
            var fild = res.data.results[0].children
            var reg = /([^-]+)$/
            this.deptno_options = fild.filter(li => {
              li.text = li.text.match(reg)[1]
              return li
            })
          } else {
            this.$message.error('获取部门数据失败')
          }
        })
        .catch(error => {
          this.$message.error(error)
        }) // end
    },

    // 查询小组名称
    searchGroup(val) {
      const params = {}
      params.depid = val.replace('dep', '')
      queryGroup(params)
        .then(res => {
          this.groupid_options = res.data.results
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    // 查询项目名称
    searchProject() {
      const params = {}
      const depId = this.approveData.deptno
      params.depId = depId.replace('dep', '')
      queryApplyList(params).then(res => {
        const data = res.data.results
        this.projectno_options = data.filter(item => {
          item.id = String(item.id)
          return item
        })
      // eslint-disable-next-line handle-callback-err
      }).catch(error => {
        this.$message.error('查询项目名称失败')
      })
    },
    // 查询系统名称
    searchSystem() {
      const params = {}
      const depId = this.approveData.deptno
      params.depId = depId.replace('dep', '')
      querySystemList(params).then(res => {
        const data = res.data.results
        this.systemnos_options = data.filter(item => {
          item.id = String(item.id)
          return item
        })
      // eslint-disable-next-line handle-callback-err
      }).catch(error => {
        this.$message.error('查询系统名称失败')
      })
    },

    // 下载文件
    downloadFile(docId) {
      const self = this
      common.downloadDoc(docId, self)
    },

    // ntko插件预览
    openNtkoWindow(obj, mark) {
      const formJudge = obj.docname.split('.')[
        obj.docname.split('.').length - 1
      ]
      if (formJudge === 'jpg' || formJudge === 'JPG' ||
              formJudge === 'jpeg' || formJudge === 'JPEG' ||
              formJudge === 'png' || formJudge === 'PNG') {
        this.ylDialog = true
        const pos = obj.tag1.lastIndexOf('/')
        const str = obj.tag1.substr(0, pos)
        if (obj.tag2 !== undefined) {
          this.ylPhotoUrl = str + '/' + obj.tag2
        } else {
          this.ylPhotoUrl = obj.tag1
        }
      } else if (formJudge === 'zip' || formJudge === 'rar' || formJudge === 'txt') {
        this.$message.error('不能预览压缩文件或txt文件格式')
      } else {
        this.ntkoYl(obj.id)
      }
    },

    // ntko预览方法
    ntkoYl(objectId) {
      var url = window.location.href
      const ntkoed = ntkoBrowser.ExtensionInstalled()
      if (ntkoed) {
        ntkoBrowser.openWindow(
          url.substring(0, url.indexOf('#')) + '#/ntko/?id=' + objectId
        )
      } else {
        window.open(
          url.substring(0, url.indexOf('#')) + '#/ntko/?id=' + objectId
        )
      }
      // 注册回调方法
      window.ntkoCloseEvent = function() {
        alert('跨浏览器插件应用程序窗口已关闭!')
      }
    },

    // 流转
    liuzhuan: function(params) {
      const loading = common.loading(this)
      this.flowItem.conclusion = params.conclusion
      this.flowItem.suggest = params.suggest
      const folw = { bps: this.flowItem }
      folw.id = this.approveData.id
      // return zhbfkzxAudit(folw)
      return twoManagerAudit(folw)
        .then(res => {
          loading.close()
          return res.data
        })
        .catch(error => {
          this.$alert(error, { type: 'error' })
        })
    },
    // 流转并验证
    proving(data) {
      const status = this.liuzhuan(data)
        .then(val => {
          console.log('val', val)
          return val
        })
      return status
    },

    // 关闭
    close() {
      this.$router.push({ name: 'dashboard' })
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
    color: #fba42f;
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
    border: 1px solid #e4e7ed;
    background-color: #f5f7fa;
    border-radius: 4px;
    padding: 0 15px;
    margin: 0;
    cursor: pointer;
  }

  .table_supply {
    white-space: nowrap;
    text-overflow: ellipsis;
    color: #c0c4cc;
    overflow: hidden;
  }

    /* 预览弹窗内容居中 */
    .ylDialogCon {
        text-align: center;
    }

  .mstable {
    margin-top: 20px;
  }

  .contentTwo_title .yjButt {
    font-size: 14px;
    font-weight: normal;
  }
</style>

<!-- 人员结算准入申请之配置信息和面试信息 -->
<template>
  <div class="contentPzms">
    <el-form ref="rulePzms" :model="pzmsData" label-position="left">
      <!-- 面试信息 -->
      <div class="contentTwo_bottom">
        <div key="2" class="contentTwo_inte">
          <div class="row_pzms">
            <p class="contentTwo_title_p">面试信息</p>
            <span class="add_row" @click="eduExperAdd(1)">新增一行</span>
            <el-table :data="pzmsData.scoreList" style="width: 100%">
              <el-table-column prop="msgRole" label="角色 *" width="200">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.msgRole`"
                    :rules="[{ required: true, message: '请选择角色', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.msgRole" filterable placeholder="请选择" @change="searchMsgByRole(scope.$index)">
                      <el-option
                        v-for="(item, index) in getMsgRoleType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column prop="msgno" label="面试官 *" width="200">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.msgno`"
                    :rules="[{ required: true, message: '请选择面试官', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.msgno" filterable placeholder="请选择" @change="searchDepByemp(scope.$index)">
                      <el-option
                        v-for="(item,index) in scope.row.msg_options"
                        :key="index"
                        :value="item.pcode"
                        :label="item.text"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="deptno" label="部门 *" width="200">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.deptno`"
                    :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]"
                  >
                    <el-select
                      v-model="scope.row.deptno"
                      :disabled = "scope.row.disabled"
                      filterable
                      placeholder="请选择"
                      @focus="judgRole(scope.$index)"
                      @change="searchEpmloNew(scope.$index)">
                      <el-option
                        v-for="(item,index) in deptno_options"
                        :key="index"
                        :value="item.id"
                        :label="item.text"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column prop="msemployRole" label="面试角色 *" width="200">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.msemployRole`"
                    :rules="[{ required: true, message: '面试角色', trigger: 'change'}]"
                  >
                    <el-select
                      v-model="scope.row.msemployRole"
                      filterable
                      placeholder="请选择"
                      @change="selectMsempRole(scope.$index)">
                      <el-option
                        v-for="(item, index) in getMsemployRoleType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifUniversalToolUse" prop="universalToolUse" label="通用工具使用 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.universalToolUse`"
                    :rules="[{ message: '通用工具使用', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.universalToolUse" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifProjectManageKnowledge" prop="projectManageKnowledge" label="项目管理知识 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.projectManageKnowledge`"
                    :rules="[{ message: '项目管理知识', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.projectManageKnowledge" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column prop="commExpression" label="沟通表达 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.commExpression`"
                    :rules="[{ message: '沟通表达', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.commExpression" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column prop="projectExperience" label="项目经验 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.projectExperience`"
                    :rules="[{ message: '项目经验', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.projectExperience" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifCodeDevelopment" prop="codeDevelopment" label="代码研发 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.codeDevelopment`"
                    :rules="[{ message: '代码研发', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.codeDevelopment" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifArchitectureDesign" prop="architectureDesign" label="架构设计 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.architectureDesign`"
                    :rules="[{ message: '架构设计', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.architectureDesign" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifProfesKnowledge" prop="profesKnowledge" label="专业知识 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.profesKnowledge`"
                    :rules="[{ message: '专业知识', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.profesKnowledge" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifStandCondition" prop="standCondition" label="驻场情况 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.standCondition`"
                    :rules="[{ message: '驻场情况', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.standCondition" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifProductAbility" prop="productAbility" label="产品能力 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.productAbility`"
                    :rules="[{ message: '专业知识', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.productAbility" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifProjectScaleXmjl" prop="projectScaleXmjl" label="项目规模 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.projectScaleXmjl`"
                    :rules="[{ message: '项目规模', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.projectScaleXmjl" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getProjectScaleXmjlType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifProjectScaleJcjl" prop="projectScaleJcjl" label="项目规模 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.projectScaleJcjl`"
                    :rules="[{ message: '项目规模', trigger: 'change'}]"
                  >
                    <el-select v-model="scope.row.projectScaleJcjl" filterable placeholder="请选择" @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getProjectScaleJcjlType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column v-if="ifProjectScale" prop="projectScale" label="项目规模 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.projectScale`"
                    :rules="[{ message: '项目规模', trigger: 'change'}]"
                  >
                    <el-select
                      v-model="scope.row.projectScale"
                      filterable
                      placeholder="请选择"
                      @change="JudgmentScore(scope.$index)">
                      <el-option
                        v-for="(item, index) in getInterGradeType"
                        :key="index"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column prop="scoreResult" label="评分结果 *" width="130">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`scoreList.${scope.$index}.scoreResult`"
                    :rules="[{ required: true, message: '评分结果', trigger: 'change'}]"
                  >
                    <el-input v-model="scope.row.scoreResult" filterable placeholder="评分结果"/>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column label="操作">
                <template slot-scope="scope">
                  <div class="operation_add">
                    <el-button
                      @click.native.prevent="deleteRow(scope.$index, pzmsData.scoreList, scope.row)">
                      <i class="el-icon-delete"/>
                    </el-button>
                  </div>
                </template>
              </el-table-column>
            </el-table>
           <!-- 新增面试定级信息栏 -->
          <div class="row_pzms" style="margin-top: 20px;">
            <p class="contentTwo_title_p">定级信息</p>
              <el-row :gutter="20">
                <!-- 人员级别 -->
                <el-col :span="8">
                  <el-form-item label="级别 *" label-width="75px">
                    <el-select v-model="levelValue" filterable placeholder="请选择级别" value-key="dictid" :disabled="false" style="width: 50%;">
                        <el-option
                          v-for="item in level_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item"
                        />
                      </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="定级人员">
                    <el-input
                      v-model="evaluatorValue.empname"
                      :disabled="true"
                      placeholder="自动填充"
                      style="width: 80%;"
                    />
                  </el-form-item>
                </el-col>
                <!-- 定级理由 -->
                <el-col :span="24">
                  <el-form-item label="定级理由 *" >
                    <el-input
                      v-model="levelReason"
                      type="textarea"
                      :rows="3"
                      placeholder="请输入定级理由，不超过200字"
                      maxlength="200"
                      show-word-limit
                      style="width: 90%;"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>
<script>
// eslint-disable-next-line no-unused-vars
import common from '@/api/common'
import {
  getJobCardId,
  queryEmployeeList,
  queryMsgList,
  queryDep,
  queryWorkItem,
  queryResourceDept
} from '@/api/integrated/wbryzrlc'
import { getDicEntry } from '@/api/eosDictEntry'
import { saveDraft, getScore, getWbrzDetail } from '@/api/integrated/newWbrz'
import { queryInterDataList, delData } from '@/api/integrated/wbms'
import { getTypeDicts } from '@/utils/dict'
export default {
  props: {
    instanceId: {
      type: String,
      required: true
    },
    evaluatorValue: {
      type: Object,
      required: true
    }
  },
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
      pzmsData: {
        id: '',
        certifNumber: '',
        evaluationLevel: '',
        evaluatorId: '',
        levelReason: '',
        evaluationTime: {},   // 定级时间
        scoreList: [
          {
            disabled: false,
            msgRole: '', // 面试官角色
            msgno: '', // 面试官编号
            deptno: '', // 部门编号
            msemployRole: '', // 面试角色
            universalToolUse: '', // 通用工具使用
            projectManageKnowledge: '', // 项目管理知识
            commExpression: '', // 沟通表达
            projectExperience: '', // 项目经验
            projectScaleXmjl: '', // 项目规模-项目经理
            projectScaleJcjl: '', // 项目规模-测试经理
            projectScale: '', // 项目规模
            scoreResult: '', // 评分结果
            codeDevelopment: '', // 代码研发
            architectureDesign: '', // 架构设计
            profesKnowledge: '', // 专业知识
            standCondition: '', // 驻场情况
            productAbility: '', // 产品能力
            technology: '',
            comprehensive: '',
            experience: '',
            education: '',
            remark: '',
            msg_options: [],
            msgid: new Date().getTime()
          }
        ] // 面试打分数据
      },
      educationData: [], // 业务字段-学历
      experienceData: [], // 业务字段-项目经验
      msg_options: [], // 面试官数据
      deptno_options: [],
      levelValue: {}, //定级级别
      levelReason: '', //定级理由
      level_options: [], // 级别
      optionsArray: []
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
  watch: {
    instanceId: function() {
      this.getDatail()
    }
  },

  created() {
    this.char = this.$route.query.char
    this.filDep()
  },
  mounted() {
    this.init()
  },
  methods: {
    eduExperAdd() {
      const obj = {
        disabled: false,
        msgno: '', // 面试官编号
        deptno: '', // 部门编号
        msemployRole: '', // 面试角色
        universalToolUse: '', // 通用工具使用
        projectManageKnowledge: '', // 项目管理知识
        commExpression: '', // 沟通表达
        projectExperience: '', // 项目经验
        projectScaleXmjl: '', // 项目规模-项目经理
        projectScaleJcjl: '', // 项目规模-测试经理
        projectScale: '', // 项目规模
        scoreResult: '', // 评分结果
        codeDevelopment: '', // 代码研发
        architectureDesign: '', // 架构设计
        profesKnowledge: '', // 专业知识
        standCondition: '', // 驻场情况
        productAbility: '', // 产品能力
        technology: '',
        comprehensive: '',
        experience: '',
        education: '',
        remark: '',
        msgid: new Date().getTime(),
        id: ''
      }
      this.pzmsData.scoreList.push(obj)
    },

    // 页面初始化
    init() {
      // 业务字典查询
      getDicEntry({
        dicttypeid: 'MSDF_EDUCATION,MSXM_LEVEL,EVALUATION_LEVEL'
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        const data = res.data.results
        this.educationData = data.filter(item => {
          return item.dicttypeid === 'MSDF_EDUCATION'
        })
        this.experienceData = data.filter(item => {
          return item.dicttypeid === 'MSXM_LEVEL'
        })
        // 级别
        this.level_options = data.filter(item => {
          return item.dicttypeid == "EVALUATION_LEVEL";
        });
        return data
      })
      if (this.instanceId !== '') {
        this.getDatail()
      }
    },

    // 获取详情
    getDatail() {
      getWbrzDetail({ instanceId: this.instanceId }).then(res => {
        if (res.data.status === 200) {
          this.pzmsData.id = res.data.results.id
          this.pzmsData.certifNumber = res.data.results.certifNumber
          this.getInterviewInfos(res.data.results.id)
          if(res.data.results.evaluationLevel) {
            this.searchLevel(res.data.results.evaluationLevel)
          }
          this.levelReason = res.data.results.levelReason
        } else {
          this.$message.error('获取数据失败')
        }
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
        this.levelValue = this.level_options.find(ele => ele.dictid === val) || {};
        return data
      })
    },
    // 取门禁卡号
    getCardId(idCard) {
      const params = {}
      params.instanceId = this.instanceId
      getJobCardId(params)
        .then(res => {
          if (res.data.status === 200) {
            this.pzmsData.entranceCard = res.data.results
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    // 获取待办工作项详情-单条
    getWorkItem(id) {
      const params = {}
      params.processInstID = id
      queryWorkItem(params).then(res => {
        if (res.data.status === 200) {
          this.bps = res.data.results
        } else {
          this.$message.error('获取待办工作项详情失败')
        }
      })
    },

    // 查询面试信息
    getInterviewInfos(lcId) {
      const params = {}
      params.id = lcId
      // 查询重要角色
      this.optionsArray = ''
      queryMsgList()
        .then(resOption => {
          this.optionsArray = resOption.data.results
        })

      queryInterDataList(params)
        .then(res => {
          if (res.data.status === 200) {
            if (res.data.results.scoreList.length === 0) {
              this.$message.error('未查询到面试信息')
              return
            }
            this.pzmsData.scoreList = res.data.results.scoreList
            this.scoreList = this.pzmsData.scoreList

            for (var i = 0; i < this.pzmsData.scoreList.length; i++) {
              // this.pzmsData.scoreList[i].msgno = this.pzmsData.scoreList[i].msgname + '(' + this.pzmsData.scoreList[i].msgno + ')'
              if (this.pzmsData.scoreList[i].msgRole === '1') {
                this.$set(this.pzmsData.scoreList[i], 'msg_options', this.optionsArray)
                this.$set(this.pzmsData.scoreList[i], 'disabled', true)
              } else if (this.pzmsData.scoreList[i].msgRole === '0') {
                this.$set(this.pzmsData.scoreList[i], 'disabled', false)
              }
              this.searchEpmlo(i)
            }
            this.selectMsempRole(0)
          } else {
            this.$message.error('查询面试信息失败')
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },

    // 获取所有下拉框内容
    filDep() {
      const params = {}
      params.objectcode = '6000000300'
      params.objectgrade = '2'
      queryResourceDept(params)
        .then(res => {
          var fild = res.data.results[0].children
          var reg = /([^-]+)$/
          this.deptno_options = fild.filter(li => {
            li.text = li.text.match(reg)[1]
            return li
          })
        // eslint-disable-next-line handle-callback-err
        }).catch(error => {
          this.$message.error('获取部门数据失败')
        }) // end
    },

    // 根据部门查询行员
    searchEpmlo(index) {
      const params = this.pzmsData.scoreList[index].deptno.replace('dep', '')
      queryEmployeeList(params)
        .then(res => {
          const params = this.pzmsData.scoreList[index].msgRole
          if (params === '0') {
            this.$set(this.pzmsData.scoreList[index], 'msg_options', res.data.results)
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    // 根据部门查询行员
    searchEpmloNew(index) {
      // scope.row.deptno
      const params = this.pzmsData.scoreList[index].deptno.replace('dep', '')
      queryEmployeeList(params)
        .then(res => {
          const params = this.pzmsData.scoreList[index].msgRole
          if (params === '0') {
            this.$set(this.pzmsData.scoreList[index], 'msgno', '')
          }
          this.$set(this.pzmsData.scoreList[index], 'msg_options', res.data.results)
        })
        .catch(error => {
          this.$message.error(error)
        })
    },

    // 选择角色根据角色进行选择面试官逻辑
    searchMsgByRole(index) {
      const params = this.pzmsData.scoreList[index].msgRole
      if (params === '1') {
        queryMsgList()
          .then(res => {
            this.$set(this.pzmsData.scoreList[index], 'disabled', true)
            this.$set(this.pzmsData.scoreList[index], 'msg_options', res.data.results)
            this.$set(this.pzmsData.scoreList[index], 'msgno', '')
            this.$set(this.pzmsData.scoreList[index], 'deptno', '')
          })
      } else if (params === '0') {
        this.msg_options = ''
        this.$set(this.pzmsData.scoreList[index], 'disabled', false)
        this.$set(this.pzmsData.scoreList[index], 'msg_options', '')
        this.$set(this.pzmsData.scoreList[index], 'msgno', '')
        this.$set(this.pzmsData.scoreList[index], 'deptno', '')
      }
    },

    searchDepByemp(index) {
      const params = {}
      params.empId = this.pzmsData.scoreList[index].msgno
      if (this.pzmsData.scoreList[index].msgRole === '1') {
        queryDep(params)
          .then(res => {
            var fild = res.data.results[0]
            // var reg = /([^-]+)$/
            // var depName = fild.location.match(reg)[1]
            var depNo = fild.depid
            this.pzmsData.scoreList[index].deptno = 'dep' + depNo
          })
        // eslint-disable-next-line handle-callback-err
          .catch(error => {
            this.$message.error('获取部门数据失败')
          }) // end
      }
    },

    judgRole(index) {
      if (this.pzmsData.scoreList[index].msgRole !== '0' && this.pzmsData.scoreList[index].msgno === '') {
        this.$message.warning('请先选择面试官')
        return false
      }
    },

    // 根据不同的角色进行选择展示的列
    selectMsempRole(index) {
      const msRole = this.pzmsData.scoreList[index].msemployRole
      const num = this.pzmsData.scoreList.length
      if (msRole === 'projl') {
        for (var j1 = 0; j1 < num; j1++) {
          this.$set(this.pzmsData.scoreList[j1], 'msemployRole', 'projl')
          this.clearPro(j1, msRole)
        }
        this.ifUniversalToolUse = true
        this.ifProjectManageKnowledge = true
        this.ifProjectScaleXmjl = true
      } else if (msRole === 'techjl') {
        for (var j2 = 0; j2 < num; j2++) {
          this.$set(this.pzmsData.scoreList[j2], 'msemployRole', 'techjl')
          this.clearPro(j2, msRole)
        }
        this.ifCodeDevelopment = true
        this.ifArchitectureDesign = true
        this.ifProjectScaleJcjl = true
      } else if (msRole === 'testjl') {
        for (var j3 = 0; j3 < num; j3++) {
          this.$set(this.pzmsData.scoreList[j3], 'msemployRole', 'testjl')
          this.clearPro(j3, msRole)
        }
        this.ifProfesKnowledge = true
        this.ifProjectManageKnowledge = true
        this.ifStandCondition = true
        this.ifProjectScaleJcjl = true
      } else if (msRole === 'pmjl') {
        for (var j4 = 0; j4 < num; j4++) {
          this.$set(this.pzmsData.scoreList[j4], 'msemployRole', 'pmjl')
          this.clearPro(j4, msRole)
        }
        this.ifUniversalToolUse = true
        this.ifProductAbility = true
      } else if (msRole === 'dev') {
        // 判断角色是否是开发人员
        for (var j5 = 0; j5 < num; j5++) {
          this.$set(this.pzmsData.scoreList[j5], 'msemployRole', 'dev')
          this.clearPro(j5, msRole)
        }
        this.ifCodeDevelopment = true
        this.ifArchitectureDesign = true
      } else if (msRole === 'testps') {
        for (var j6 = 0; j6 < num; j6++) {
          this.$set(this.pzmsData.scoreList[j6], 'msemployRole', 'testps')
          this.clearPro(j6, msRole)
        }
        this.ifProfesKnowledge = true
        this.ifStandCondition = true
      } else if (msRole === 'needps') {
        for (var j7 = 0; j7 < num; j7++) {
          this.$set(this.pzmsData.scoreList[j7], 'msemployRole', 'needps')
          this.clearPro(j7, msRole)
        }
        this.ifUniversalToolUse = true
        this.ifProductAbility = true
      }
    },

    // 删除行
    deleteRow(index, rows, row) {
      if (rows.length !== 2 && row.id === '') {
        rows.splice(index, 1)
      } else if (rows.length !== 2 && row.id !== '') {
        this.delTableData(index, rows, row)
      } else {
        this.$message.error('不能少于两行')
      }
    },

    // 删除表中数据
    delTableData(index, rows, row) {
      const params = {}
      params.id = row.id
      params.msgId = row.msgid
      delData(params).then(res => {
        if (res.data.status === 200) {
          rows.splice(index, 1)
        } else {
          this.$message.error('删除失败')
        }
      })
    },

    // 保存当前组件信息
    savePzms() {
      this.pzmsData.instanceId = this.instanceId
      this.pzmsData.bps = this.bps
      const arr = this.pzmsData.scoreList
      // 先校验定级信息
      if (!this.levelValue.dictid) {
        this.$message.error('请选择人员级别')
        return
      }
      if (!this.levelReason || this.levelReason.trim().length === 0) {
        this.$message.error('请填写定级理由')
        return
      }
      if (this.levelReason.length > 200) {
        this.$message.error('定级理由不能超过200字')
        return
      }
      this.pzmsData.evaluationLevel = this.levelValue.dictid
      this.pzmsData.evaluatorId = this.evaluatorValue.badge
      this.pzmsData.levelReason = this.levelReason
      this.pzmsData.evaluationTime = new Date()
      if (arr.length < 2) {
        this.$message.error('至少需要两名面试官!')
        return
      }
      const isNameEqual = arr.some((item, index) => {
        return arr.some((obj, ind) => {
          if (ind !== index) {
            return obj.msgno === item.msgno
          }
        })
      })
      if (isNameEqual) {
        this.$message.error('面试官重复')
        return
      }
      const isZyRole = arr.find(item => item.msgRole === '1')
      if (!isZyRole) {
        this.$message.error('至少需要两位面试官，且至少包含一名重要角色!')
        return
      }
      for (var i = 0; i < arr.length; i++) {
        if (this.pzmsData.scoreList[i].msgRole === '0') {
          const scoreResultPt = this.pzmsData.scoreList[i].scoreResult
          if (Number(scoreResultPt) < 60) {
            this.$message.error('面试评分不满足入场要求，请重新核对!')
            return
          }
        } else if (this.pzmsData.scoreList[i].msgRole === '1') {
          const scoreResultZy = this.pzmsData.scoreList[i].scoreResult
          if (Number(scoreResultZy) < 70) {
            this.$message.error('面试评分不满足入场要求，请重新核对!')
            return
          }
        }
      }
      return saveDraft(this.pzmsData).then(res => {
        if (res.data.status === 200) {
          this.$message.success('保存草稿成功')
          return res.data
        } else {
          this.$message.error('保存草稿失败')
        }
      })
    },

    // 判断计算评分结果
    JudgmentScore(index) {
      const msRole = this.pzmsData.scoreList[index].msemployRole
      const _universalToolUse = this.pzmsData.scoreList[index].universalToolUse
      const _projectManageKnowledge = this.pzmsData.scoreList[index].projectManageKnowledge
      const _commExpression = this.pzmsData.scoreList[index].commExpression
      const _projectExperience = this.pzmsData.scoreList[index].projectExperience
      const _projectScaleXmjl = this.pzmsData.scoreList[index].projectScaleXmjl
      const _projectScaleJcjl = this.pzmsData.scoreList[index].projectScaleJcjl
      const _codeDevelopment = this.pzmsData.scoreList[index].codeDevelopment
      const _architectureDesign = this.pzmsData.scoreList[index].architectureDesign
      const _profesKnowledge = this.pzmsData.scoreList[index].profesKnowledge
      const _standCondition = this.pzmsData.scoreList[index].standCondition
      const _productAbility = this.pzmsData.scoreList[index].productAbility

      if (msRole === 'projl' && _universalToolUse !== '' && _projectManageKnowledge !== '' &&
          _commExpression !== '' && _projectExperience !== '' && _projectScaleXmjl !== '') {
        this.queryScore(index)
      } else if (msRole === 'techjl' && _codeDevelopment !== '' && _architectureDesign !== '' &&
          _commExpression !== '' && _projectExperience !== '' && _projectScaleJcjl !== '') {
        this.queryScore(index)
      } else if (msRole === 'testjl' && _profesKnowledge !== '' && _commExpression !== '' &&
          _projectManageKnowledge !== '' && _projectExperience !== '' &&
          _standCondition !== '' && _projectScaleJcjl !== '') {
        this.queryScore(index)
      } else if (msRole === 'pmjl' && _universalToolUse !== '' && _commExpression !== '' &&
          _productAbility !== '' && _projectExperience !== '') {
        this.queryScore(index)
      } else if (msRole === 'dev' && _codeDevelopment !== '' && _architectureDesign !== '' &&
          _commExpression !== '' && _projectExperience !== '') {
        this.queryScore(index)
      } else if (msRole === 'testps' && _profesKnowledge !== '' && _commExpression !== '' &&
          _projectExperience !== '' && _standCondition !== '') {
        this.queryScore(index)
      } else if (msRole === 'needps' && _universalToolUse !== '' && _commExpression !== '' &&
          _productAbility !== '' && _projectExperience !== '') {
        this.queryScore(index)
      }
    },

    queryScore(index) {
      const result = {}
      result.universalToolUse = this.pzmsData.scoreList[index].universalToolUse
      result.projectManageKnowledge = this.pzmsData.scoreList[index].projectManageKnowledge
      result.commExpression = this.pzmsData.scoreList[index].commExpression
      result.projectExperience = this.pzmsData.scoreList[index].projectExperience
      result.projectScaleXmjl = this.pzmsData.scoreList[index].projectScaleXmjl
      result.projectScaleJcjl = this.pzmsData.scoreList[index].projectScaleJcjl
      result.codeDevelopment = this.pzmsData.scoreList[index].codeDevelopment
      result.architectureDesign = this.pzmsData.scoreList[index].architectureDesign
      result.profesKnowledge = this.pzmsData.scoreList[index].profesKnowledge
      result.standCondition = this.pzmsData.scoreList[index].standCondition
      result.productAbility = this.pzmsData.scoreList[index].productAbility
      result.msemployRole = this.pzmsData.scoreList[index].msemployRole
      getScore(result).then(res => {
        if (res.data.status === 200) {
          this.pzmsData.scoreList[index].scoreResult = res.data.results
        }
      })
    },

    // 验证必填项
    verify() {
      this.$refs['rulePzms'].validate(valid => {
        this.$emit('verifyPzms', valid)
      })
    },

    /**
     * 重置动态显示项
     */
    clearPro(index, msRole) {
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

      if (msRole === 'projl') {
        this.pzmsData.scoreList[index].projectScaleJcjl = ''
        this.pzmsData.scoreList[index].codeDevelopment = ''
        this.pzmsData.scoreList[index].architectureDesign = ''
        this.pzmsData.scoreList[index].profesKnowledge = ''
        this.pzmsData.scoreList[index].standCondition = ''
        this.pzmsData.scoreList[index].productAbility = ''
      } else if (msRole === 'techjl') {
        this.pzmsData.scoreList[index].universalToolUse = ''
        this.pzmsData.scoreList[index].projectManageKnowledge = ''
        this.pzmsData.scoreList[index].projectScaleXmjl = ''
        this.pzmsData.scoreList[index].profesKnowledge = ''
        this.pzmsData.scoreList[index].standCondition = ''
        this.pzmsData.scoreList[index].productAbility = ''
      } else if (msRole === 'testjl') {
        this.pzmsData.scoreList[index].universalToolUse = ''
        this.pzmsData.scoreList[index].projectScaleXmjl = ''
        this.pzmsData.scoreList[index].codeDevelopment = ''
        this.pzmsData.scoreList[index].architectureDesign = ''
        this.pzmsData.scoreList[index].productAbility = ''
      } else if (msRole === 'pmjl') {
        this.pzmsData.scoreList[index].projectManageKnowledge = ''
        this.pzmsData.scoreList[index].projectScaleXmjl = ''
        this.pzmsData.scoreList[index].projectScaleJcjl = ''
        this.pzmsData.scoreList[index].codeDevelopment = ''
        this.pzmsData.scoreList[index].architectureDesign = ''
        this.pzmsData.scoreList[index].profesKnowledge = ''
        this.pzmsData.scoreList[index].standCondition = ''
      } else if (msRole === 'dev') {
        this.pzmsData.scoreList[index].universalToolUse = ''
        this.pzmsData.scoreList[index].projectManageKnowledge = ''
        this.pzmsData.scoreList[index].projectScaleXmjl = ''
        this.pzmsData.scoreList[index].projectScaleJcjl = ''
        this.pzmsData.scoreList[index].profesKnowledge = ''
        this.pzmsData.scoreList[index].standCondition = ''
        this.pzmsData.scoreList[index].productAbility = ''
      } else if (msRole === 'testps') {
        this.pzmsData.scoreList[index].universalToolUse = ''
        this.pzmsData.scoreList[index].projectManageKnowledge = ''
        this.pzmsData.scoreList[index].projectScaleXmjl = ''
        this.pzmsData.scoreList[index].projectScaleJcjl = ''
        this.pzmsData.scoreList[index].codeDevelopment = ''
        this.pzmsData.scoreList[index].architectureDesign = ''
        this.pzmsData.scoreList[index].productAbility = ''
      } else if (msRole === 'needps') {
        this.pzmsData.scoreList[index].projectManageKnowledge = ''
        this.pzmsData.scoreList[index].projectScaleXmjl = ''
        this.pzmsData.scoreList[index].projectScaleJcjl = ''
        this.pzmsData.scoreList[index].codeDevelopment = ''
        this.pzmsData.scoreList[index].architectureDesign = ''
        this.pzmsData.scoreList[index].profesKnowledge = ''
        this.pzmsData.scoreList[index].standCondition = ''
      }
    }
  }
}
</script>
<style scoped>
.content {
  width: 100%;
  /* height: 600px; */
  font-size: 16px;
  background-color: #ffffff;
  padding: 15px 20px;
}

p {
  color: #262626;
  font-size: 14px;
  margin: 8px 0;
}

.contentTwo_title {
  height: 60px;
  line-height: 60px;
  background-color: #ffffff;
  padding: 0 25px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.contentTwo_title_p {
  color: rgba(38, 38, 38, 1);
  font-size: 16px;
  height: 22px;
  line-height: 22px;
  font-weight: 600;
  margin: 0;
  display: inline-block;
}

.contentTwo_bottom {
  margin: 15px 0 10px 0;
}

.contentTwo_inte {
  width: 100%;
  font-size: 16px;
  background-color: #ffffff;
  padding: 15px 20px;
}

.contentPzms .row_pzms {
  margin-bottom: 20px;
}

.add_row {
  color: #faad14;
  font-size: 14px;
  font-weight: 500;
  margin-left: 10px;
  cursor: pointer;
}

.operation_add .el-button {
  border: 1px solid #ffffff;
  padding: 0;
}
</style>

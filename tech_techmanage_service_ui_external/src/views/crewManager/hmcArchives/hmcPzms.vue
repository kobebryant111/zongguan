<!-- 花名册配置面试信息 -->
<template>
  <div class="archives-container-pzms">
    <!-- 无权限 -->
    <div class="archives-main">
      <!-- 人力面试 -->
      <div class="archives-title-second">
        <span class="xkg-color-black archives-title-name">面试信息</span>
      </div>
      <div class="archives-modular archives-pzms-mstable" >
        <el-table :data="interviewData" style="width: 100%">

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
</template>

<script>
import {
  queryKqTypeList,
  queryKqTime,
  // eslint-disable-next-line no-unused-vars
  queryEmployeeTree,
  histPricePager
} from '@/api/integrated/wbryzrlc'
import { getDicEntry } from '@/api/eosDictEntry'
import { updatePpmOutsourcingExt } from '@/api/integrated/hmcDa'
import common from '@/api/common'
import pager from '@/components/Pager'
// eslint-disable-next-line no-unused-vars
import { getDictName, getTypeDicts } from '@/utils/dict'
export default {
  name: 'HmcPzms',
  components: { pager },
  props: {
    tuozhanDetail: {
      type: Object,
      required: true
    },
    interviewDetail: {
      type: Array,
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
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      ifAuth: false,
      pzmsData: {},
      ifEdit: true, // 是否可编辑
      sf_options: [], // 是否
      kqtype_options: [],
      intervresult_options: [],
      peoForm_options: [],
      educationData: [], // 业务字段-学历
      experienceData: [], // 业务字段-项目经验
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
      xmMsDetail: {}, // 项目面试详情
      interviewData: [], // 面试信息
      visibleClose: false, // dialog 显示 默认false 不显示
      histPriceData: [] // 历史单价集合
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
    tuozhanDetail(val) {
      this.pzmsData = val
    },
    interviewDetail(val) {
      this.interviewData = val
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 页面初始化
    init() {
      queryKqTypeList() // 查询考勤类型
        .then(res => {
          this.kqtype_options = res.data.results
        })
        .catch(error => {
          this.$message.error(error)
        }) // end
      // 业务字典查询
      getDicEntry({
        dicttypeid: 'NEWKG_RZSF,MSDF_EDUCATION,MSXM_LEVEL,NEW_BUSI_OUTSOURCE_INTERVRESULT,RLWBXQLX'
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        const data = res.data.results
        // 是否
        this.sf_options = data.filter(item => {
          return item.dicttypeid == 'NEWKG_RZSF'
        })
        // 面试结果
        this.intervresult_options = data
          .filter(item => {
            return item.dicttypeid == 'NEW_BUSI_OUTSOURCE_INTERVRESULT'
          })
          .reverse()
        // 人员类型
        this.peoForm_options = data.filter(item => {
          return item.dicttypeid == 'RLWBXQLX' && item.dictid != 'CZ'
        })
        this.educationData = data.filter(item => {
          return item.dicttypeid == 'MSDF_EDUCATION'
        })
        this.experienceData = data.filter(item => {
          return item.dicttypeid == 'MSXM_LEVEL'
        })
        return data
      })
    },

    // 根据不同的角色进行选择展示的列
    selectMsempRole() {
      const msRole = this.interviewData[0].msemployRole
      const num = this.interviewData.length
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

    kqFil(v) {
      if (v == 'XXXXXX') {
        this.pzmsData.dutyType = '2'
        this.pzmsData.whetherAttendance = '1'
      } else if (v == 'YYYYYY') {
        this.pzmsData.whetherAttendance = '0'
        this.pzmsData.dutyType = '1'
      } else {
        this.pzmsData.dutyType = '1'
        this.pzmsData.whetherAttendance = '1'
      }
      this.searchTime(v)
    },

    // 查询考勤时间
    searchTime(v) {
      if (v == 'XXXXXX' || v == 'YYYYYY') {
        this.pzmsData.attendanceTime = ''
        return
      }
      const params = {}
      params.kqType = v
      queryKqTime(params)
        .then(res => {
          if (res.data.status == 200) {
            var seTime =
              res.data.results.startDate1 + ' ' + res.data.results.endDate1
            this.$set(this.pzmsData, 'attendanceTime', seTime)
          } else {
            this.$message.error('获取考勤时间失败')
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },

    // 编辑
    edit() {
      this.ifEdit = false
    },

    // 虚拟桌面变化
    xnzmChange(v) {
      if (v == '02') {
        this.pzmsData.virtualDesktopSettings = ''
      }
    },

    // SVN变化
    svnChange(v) {
      if (v == '2') {
        this.pzmsData.svnPath = ''
      }
    },

    // 特殊权限变化
    tsqxChange(v) {
      if (v == '2') {
        this.pzmsData.authDes = ''
      }
    },

    // 保存当前页面
    save() {
      const loading = common.loading(this)
      // this.pzmsData.learnExperience = JSON.stringify(this.pzmsData.learnExperience);
      // this.pzmsData.mainJobs = JSON.stringify(this.pzmsData.mainJobs);
      // this.pzmsData.mainItem = JSON.stringify(this.pzmsData.mainItem);
      // this.pzmsData.mainReward = JSON.stringify(this.pzmsData.mainReward);
      // this.pzmsData.mainFamily = JSON.stringify(this.pzmsData.mainFamily);
      Object.keys(this.pzmsData).forEach(key => {
        if (this.pzmsData[key] == undefined) {
          this.pzmsData[key] = null
        }
      })
      const data = common.paramsFormat(this.pzmsData)
      updatePpmOutsourcingExt(data)
        .then(res => {
          loading.close()
          if (res.data.status == 200) {
            this.ifEdit = true
            this.$message.success('保存成功')
          } else {
            this.$message.error('保存失败')
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    /**
     * 点击历史单价
     */
    histPriceBtn() {
      this.visibleClose = true
      const params = { jobid: this.pzmsData.userId }
      const data = common.paramsFormat(params)
      histPricePager(data).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.histPriceData = res.data.results
        this.totalNumber = res.data.total
      })
    },
    /**
     * 关闭dialog
     */
    dialogClose() {
      this.visibleClose = false
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      const params = { jobid: this.pzmsData.userId, size: size, index: index }
      const data = common.paramsFormat(params)
      histPricePager(data).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.histPriceData = res.data.results
      })
    }
  }
}
</script>

<style scoped>
.archives-title-name {
  font-size: 15px;
  font-weight: 600;
}

.archives-title-edit {
  margin-left: 8px;
  cursor: pointer;
}

.archives-title-save {
  cursor: pointer;
}

.archives-title-second {
  margin-top: 15px;
}

.archives-pzms-mstable {
  margin-top: 15px;
}
</style>

<style>
.archives-container-pzms .el-input-number--medium {
  width: 100%;
}

.archives-container-pzms .el-input-number .el-input__inner {
  text-align: left;
}
</style>

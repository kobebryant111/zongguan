<!-- 测试组管理-->
<template>
  <div class="typeset-container">
    <el-tabs type="border-card" @tab-click="handleClickHard">
      <el-tab-pane label="创建测试团队">
        <div>
          <el-form ref="ruleform" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="10">
              <el-col :span="4">
                <el-form-item label="团队名称：" label-width="100px" prop="teamName" >
                  <el-input v-model="form.teamName" style="width: 300px" placeholder="请输入"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4">
                <el-form-item label="项目经理：" label-width="100px" prop="teamMansger" >
                  <el-select v-model="form.teamMansger" filterable multiple clearable style="width: 300px">
                    <el-option
                      v-for="item in teamMansgerList"
                      :key="item.badge"
                      :label="item.empname + '(' + item.badge + ')'"
                      :value="item.badge"/>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="0">
              <el-col :span="10">
                <el-form-item label="团队成员：" label-width="100px" prop="teamMember" >
                  <el-select v-model="form.teamMember" filterable multiple clearable style="width: 500px">
                    <el-option
                      v-for="item in teamMemberList"
                      :key="item.testEmpBadge"
                      :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                      :value="item.testEmpBadge"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-row>
                <el-button type="primary" @click="creatOfTeam">创建</el-button>
              </el-row>
            </el-row>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="创建测试小组">
        <div>
          <el-form ref="formTestGroup" :rules="testGroupRules" :model="formTwo" label-width="120px">
            <el-row :gutter="10">
              <el-col :span="5">
                <el-form-item
                  label="小组名称:"
                  prop="testGroupName">
                  <el-input
                    v-model="formTwo.testGroupName"
                    style="width: 300px"
                    placeholder="请输入"
                    @blur="screGroupName"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="5">
                <el-form-item
                  label="所属团队:"
                  label-width="120px"
                  prop="ownedTestTeam">
                  <el-select
                    v-model="formTwo.ownedTestTeam"
                    style="width: 300px"
                    filterable
                    clearable
                    placeholder="请选择"
                    @change="selectTestGroupEmp(formTwo.ownedTestTeam)">
                    <el-option
                      v-for="item in ownedTestTeam_options"
                      :key="item.teamId"
                      :label="item.teamName"
                      :value="item.teamId"/>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="5">
                <el-form-item
                  label="管理人员:"
                  label-width="120px"
                  prop="testGroupComemployee">
                  <el-select
                    v-model="formTwo.testGroupComemployee"
                    style="width: 300px"
                    filterable
                    clearable
                    placeholder="请选择">
                    <el-option
                      v-for="item in headOfCompany_options"
                      :key="item.badge"
                      :label="item.empname + '(' + item.badge + ')'"
                      :value="item.badge"/>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="5">
                <el-form-item
                  label="小组组长:"
                  prop="testGroupLeader"
                  label-width="120px">
                  <el-select
                    v-model="formTwo.testGroupLeader"
                    style="width: 300px"
                    filterable
                    clearable
                    placeholder="请选择"
                    @change="screenTestEmp">
                    <el-option
                      v-for="item in testGroup_options"
                      :key="item.testEmpBadge"
                      :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                      :value="item.testEmpBadge"/>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="0">
              <el-col :span="10">
                <el-form-item
                  label="小组成员:"
                  prop="testGroupMember"
                  label-width="120px">
                  <el-select
                    v-model="formTwo.testGroupMember"
                    multiple
                    style="width: 500px;"
                    filterable
                    clearable
                    placeholder="请选择">
                    <el-option
                      v-for="item in testGroupMember_options"
                      :disabled="item.eidhr == '0'"
                      :key="item.testEmpBadge"
                      :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                      :value="item.testEmpBadge"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-row>
                <el-button type="primary" @click="creatTestGroup">创建</el-button>
              </el-row>
            </el-row>
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane label="测试团队" name="first">
        <test-team ref="testTeam"/>
      </el-tab-pane>
      <el-tab-pane label="测试小组" name="second">
        <test-group ref= "testGroup"/>
      </el-tab-pane>
      <el-tab-pane label="测试人员池">
        <testemployee-pool ref="testEmployeePool" name="third"/>
      </el-tab-pane>
      <el-tab-pane label="待分配池">
        <undistribution-pool ref="undistributionPool" name="fourth"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import common from '@/api/common'
import cleanBtn from '@/components/CleanBtn'
import pager from '@/components/Pager'
import buttonOrange from '@/components/eleButton/buttonOrange'
import CreatTestTeam from '@/components/creatTestTeam'
import testTeam from '@/views/testManagement/views/TestTeam'
import testGroup from '@/views/testManagement/views/TestGroup'
import testemployeePool from '@/views/testManagement/views/TestEmployeePool'
import undistributionPool from '@/views/testManagement/views/undistributionPool'
import { getHeadOfCompanyList } from '@/api/testManagementApi/testEmployeePool'
import { creatTestGroup,
  creatOfTeam,
  selectTeamMansgerList,
  selectTeamMemberList,
  selectTestGroupEmployee,
  selectGroupName } from '@/api/testManagementApi/testTeamManagement'
import { getOwnedTestTeamList } from '@/api/testManagementApi/testEmployeePool'
export default {
  name: 'TestTeamManagement',
  components: {
    testemployeePool,
    undistributionPool,
    testTeam,
    testGroup,
    buttonOrange,
    cleanBtn,
    CreatTestTeam,
    pager
  },
  data() {
    return {
      activeName: 'first',
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      creatOfGroup: {
        ownedTestGroup: null, // 测试小组
        ownedTestTeam: null, // 测试团队
        testGroupLeader: null, // 测试组长
        headOfCompany: null, // 公司内负责人
        groupMember: null, // 小组成员
        employeeStatus: null, // 员工状态
        badge: null, // 工号
        croupMember: [] // 小组成员
      },
      teamMansgerList: [], // 项目经理列表
      teamMemberList: [], // 团队成员列表
      headOfCompany_options: [], // 公司内负责人下拉框
      ownedTestTeam_options: [], // 所属团队下拉框
      testGroup_options: [], // 测试小组人员
      testGroupMember_options: [], // 测试小组成员
      form: {
      },
      formTwo: {},
      testEmpPoolData: [], // 面试信息详情
      teamNameList: [],
      rules: {
        teamName: [
          { required: true, message: '请输入团队名称', trigger: 'blur' }
        ],
        teamMansger: [
          { required: true, message: '请选择项目经理', trigger: 'blur' }
        ],
        teamMember: [
          { required: true, message: '请选择团队成员', trigger: 'blur' }
        ]
      },
      testGroupRules: {
        testGroupName: [
          { required: true, message: '请输入小组名称', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ],
        ownedTestTeam: [
          { required: true, message: '请选择所属测试团队', trigger: 'blur' }
        ],
        testGroupComemployee: [
          { required: true, message: '请选择管理人员', trigger: 'blur' }
        ],
        testGroupLeader: [
          { required: true, message: '请选择小组组长', trigger: 'blur' }
        ],
        testGroupMember: [
          { required: true, message: '请选择小组成员', trigger: 'blur' }
        ]
      }
    }
  },

  created() {
    // 查询项目经理列表
    const param = {}
    selectTeamMansgerList(param).then(res => {
      if (res.data.status === 200) {
        this.teamMansgerList = res.data.results
      } else {
        this.$message.error(res.data.message)
      }
    })
    // 查询团队成员下拉框
    selectTeamMemberList().then(res => {
      if (res.data.status === 200) {
        this.teamMemberList = res.data.results
      } else {
        this.$message.error(res.data.message)
      }
    })
  },
  mounted() {

  },
  methods: {
    /**
     * 初始化方法
     */
    loadInit: function() {
      this.getDic()
    },
    handleClick: function(tab, event) {
      if (tab.index === '0') {
        this.$refs.testTeam.loadInit()
      } else if (tab.index === '1') {
        this.$refs.testGroup.loadInit()
      } else if (tab.index === '2') {
        this.$refs.testEmployeePool.loadInit()
      } else if (tab.index === '3') {
        this.$refs.undistributionPool.loadInit()
      }
    },
    handleClickHard: function(tab, event) {
      if (tab.index === '0') {
        this.activeName = 'first'
      }
      if (tab.index === '1') {
        this.getDic()
        this.activeName = 'second'
      }
    },
    // 创建测试团队
    creatOfTeam() {
      this.$refs['ruleform'].validate((valid) => {
        if (valid) {
          const param = this.form
          creatOfTeam(param).then(res => {
            if (res.data.status === 200) {
              this.$message.success('团队创建成功!')
              this.form = {}
              this.selectTeamMemberList()
            } else {
              this.$message.error(res.data.message)
            }
          })
        }
      })
    },
    // 创建测试小组
    creatTestGroup() {
      this.$refs['formTestGroup'].validate((valid) => {
        if (valid) {
          const params = this.formTwo
          const data = common.paramsFormat(params)
          creatTestGroup(data).then(res => {
            if (res.data.status === 200) {
              this.$message.success('小组创建成功!')
              this.formTwo = {}
              this.$refs.testEmployeePool.loadInit()
            } else {
              this.$message.error(res.data.message)
            }
          })
        }
      })
    },
    // 查询业务字典
    getDic() {
      getHeadOfCompanyList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.headOfCompany_options = res.data.results
      })
      getOwnedTestTeamList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.ownedTestTeam_options = res.data.results
      })
    },
    selectTestGroupEmp(teamId) {
      const param = { teamId: teamId }
      const data = common.paramsFormat(param)
      selectTestGroupEmployee(data).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.$set(this.formTwo, 'testGroupLeader', '')
        this.$set(this.formTwo, 'testGroupMember', '')
        this.testGroup_options = res.data.results
        this.testGroupMember_options = res.data.results
      })
    },
    screGroupName() {
      const param = { testGroupName: this.formTwo.testGroupName }
      const data = common.paramsFormat(param)
      selectGroupName(data).then(res => {
        if (res.data.status === 5000) {
          this.$alert('该小组已存在,请重新输入。', { type: 'warning' })
          return false
        }
      })
    },
    screenTestEmp() {
      const groupLeader = this.formTwo.testGroupLeader
      this.testGroupMember_options = this.testGroup_options.filter(item => item.testEmpBadge !== groupLeader)
    }
  }
}
</script>

<style scoped>
.typeset-container {
  padding: 10px;
  background-color: #ffffff;
}
.pager-zj {
  text-align: center;
}
.border-card {
  padding: 10px;
  background-color: #ffffff;
}
</style>

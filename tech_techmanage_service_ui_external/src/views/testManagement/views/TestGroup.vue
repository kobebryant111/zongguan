<!-- 测试人员池-->
<template>
  <div class="typeset-container">
    <div class="border-card">
      <el-form :model="form">
        <el-row :gutter="10">
          <el-col :span="4">
            <el-form-item label="测试小组:" label-width="73px">
              <el-select v-model="form.id" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in groupNameList"
                  :key="item.id"
                  :label="item.testGroupName"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="测试团队:" label-width="73px">
              <el-select v-model="form.ownedTestTeam" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in teamNameList"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="项目经理:" label-width="110px">
              <el-select v-model="form.teamMansger" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in teamMansgerList"
                  :key="item.badge"
                  :label="item.empname + '(' + item.badge + ')'"
                  :value="item.badge"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="4">
            <el-form-item label="测试组长:" label-width="73px">
              <el-select v-model="form.testGroupLeader" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in teamMemberList"
                  :key="item.testEmpBadge"
                  :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                  :value="item.testEmpBadge"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="小组成员:" label-width="73px">
              <el-select v-model="form.testGroupMember" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in teamMemberList"
                  :key="item.testEmpBadge"
                  :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                  :value="item.testEmpBadge"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <div style="height: 36px;line-height: 36px;">
              <buttonOrange :fns="submitBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
              <clean-btn :forms="form" size="small" />
              <buttonOrange :fns="submitBtnInfo" button-name="下载" size="mini" />
            </div>
          </el-col>
        </el-row>
      </el-form>
      <el-dialog
        :visible.sync="diaologVisible"
        :before-close="handleClose"
        width="45%"
        title="测试小组编辑"
      >

        <el-form ref="formTestGroup" :model="formGrp" :rules="testGroupRules" label-width="120px">
          <el-row :gutter="10">
            <el-col :span="5">
              <!-- {{ formGrp.testGroupName }} -->
              <el-form-item label="小组名称:" prop="testGroupName">
                <el-input
                  v-model="formGrp.testGroupName"
                  style="width: 300px"
                  placeholder="请输入"
                  @blur="screGroupName"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="所属团队:" prop="ownedTestTeam" label-width="120px">
                <el-select
                  v-model="formGrp.ownedTestTeam"
                  style="width: 300px"
                  placeholder="请选择"
                  @change="selectTestGroupEmp(formGrp.ownedTestTeam)">
                  <el-option
                    v-for="item in teamNameList"
                    :key="item.teamId"
                    :label="item.teamName"
                    :value="item.teamId"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="管理人员:" prop="testGroupComemployee" label-width="120px">
                <el-select
                  v-model="formGrp.testGroupComemployee"
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
              <el-form-item label="小组组长:" prop="testGroupLeader" label-width="120px">
                <el-select
                  v-model="formGrp.testGroupLeader"
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
              <el-form-item label="小组成员:" prop="paramGroup" label-width="120px">
                <el-select
                  v-model="formGrp.paramGroup"
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
            <el-col>
              <el-button @click="calcelDialog" >取消</el-button>
              <el-button type="primary" @click="editTestGroup">确认</el-button>
            </el-col>
          </el-row>
        </el-form>
      </el-dialog>
      <div class="typeset-body">
        <el-table
          v-loading="loading"
          ref="multipleTable"
          :data="crewData"
          border
          height="370"
          element-loading-text="数据加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="gba(0,0,0,0.8)"
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="50" align="center"/>
          <el-table-column prop="testGroupName" label="测试小组" width="120" align="center"/>
          <el-table-column prop="teamName" label="所属测试团队" width="120" align="center"/>
          <el-table-column prop="teamMansger" label="项目经理" width="180" align="center">
            <template slot-scope="scope">
              <el-select v-model ="scope.row.teamMansger" :disabled="!scope.row.edited" multiple clearable filterable >
                <el-option
                  v-for="item in teamMansgerList"
                  :key="item.badge"
                  :label="item.empname"
                  :value="item.badge"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="testEmpName" label="小组组长" width="120" align="center"/>
          <el-table-column prop="groupMemberNo" label="小组成员数" width="120" align="center"/>
          <el-table-column :show-overflow-tooltip="true" prop="groupMember" label="小组成员" width="275" align="center"/>
          <el-table-column label="操作" width="150" align="center">
            <template slot-scope="scope">
              <el-link
                :underline="false"
                type="warning"
                @click="handleUpdate(scope.row)"
              >
                编辑
              </el-link
              >
              <el-link
                :underline="false"
                type="warning"
                @click="handleDelete(scope.row)"
              >
                删除
              </el-link
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="pager-zj">
          <pager
            ref="pager"
            :fns="getPager"
            :total="totalNumber"
            :page-size="size"
            :current-page="index"
            v-model="$attrs"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import common from '@/api/common'
import cleanBtn from '@/components/CleanBtn'
import pager from '@/components/Pager'
import buttonOrange from '@/components/eleButton/buttonOrange'
import CreatTestTeam from '@/components/creatTestTeam'
// eslint-disable-next-line no-unused-vars
import { getDictName, getTypeDicts } from '@/utils/dict'
import { getHeadOfCompanyList } from '@/api/testManagementApi/testEmployeePool'
import { queryPager,
  secltGroup,
  queryPersonListInfo,
  deleteGroupById,
  getGroupDate,
  updateTestGroup
} from '@/api/testManagementApi/TestGroup'
import { selectTeamList,
  selectTeamMansgerList,
  selectTestGroupEmployee,
  selectMemberList,
  selectGroupName
} from '@/api/testManagementApi/testTeamManagement'
export default {
  name: 'TestTeamManagement',
  components: {
    buttonOrange,
    cleanBtn,
    CreatTestTeam,
    pager
  },
  data() {
    return {
      crewData: [], // 列表分页数据
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      form: {},
      textTeamList: [],
      groupNameList: [],
      diaologVisible: false,
      testGroup_options: [],
      formGrp: {
        testGroupName: '',
        ownedTestTeam: '',
        testGroupComemployee: '',
        testGroupLeader: '',
        paramGroup: []
      },
      teamNameList: [],
      teamMemberList: [], // 团队成员列表下拉框
      teamMansgerList: [],
      headOfCompany_options: [],
      testGroupMember_options: [],
      loading: false,
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
        paramGroup: [
          { required: true, message: '请选择小组成员', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // this.loadInit(
    //   this.getInit()
    // )
  },
  mounted() {
  },
  methods: {
    loadInit: function() {
      this.getInit()
      const param = {}
      // 测试小组下拉框 v-for
      secltGroup(param).then(res => {
        if (res.data.status === 200) {
          this.groupNameList = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
      // 查询测试团队下拉框
      selectTeamList(param).then(res => {
        if (res.data.status === 200) {
          this.teamNameList = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
      // 查询项目经理列表下拉框 v-for
      selectTeamMansgerList(param).then(res => {
        if (res.data.status === 200) {
          this.teamMansgerList = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
      // 查询小组成员下拉框
      // 查询团队成员下拉框
      selectMemberList(param).then(res => {
        if (res.data.status === 200) {
          this.teamMemberList = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 分页初始化
    getInit() {
      this.getPager(this.size, this.index)
    },
    // 分页
    getPager: function(size, index) {
      const params = { ...this.form }
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    // 查询
    submitBtn() {
      this.index = 1
      this.getPager(this.size, this.index)
    },
    screenTestEmp() {
      const groupLeader = this.formGrp.testGroupLeader
      this.testGroupMember_options = this.testGroup_options.filter(item => item.testEmpBadge !== groupLeader)
    },
    // 获取列表数据
    getPagerList(params) {
      this.loading = true
      const self = this
      queryPager(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.crewData = res.data.results
          self.crewData.forEach((item) => {
            item.teamMansger = item.teamMansger.split(',')
            // item.testGroupMember = item.testGroupMember.split(',')
          })
          self.totalNumber = res.data.total
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    //  下载
    submitBtnInfo() {
      this.$confirm('是否导出测试小组列表？', '测试小组列表导出确认',
        { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        const data = { ...this.form }
        data.index = this.index
        data.size = this.size
        data._isDown = 'Y'
        queryPersonListInfo(data).then(res => {
          common.download(res.data, self, '测试小组列表.xls')
        })
      })
    },
    // 编辑
    handleUpdate(data) {
      this.diaologVisible = true
      // this.formGrp = data
      this.formGrp.id = data.id
      this.formGrp.testGroupName = data.testGroupName
      this.formGrp.ownedTestTeam = data.ownedTestTeam
      this.formGrp.testGroupComemployee = data.testGroupComemployee
      this.formGrp.testGroupLeader = data.testGroupLeader
      const groupLeader = this.formGrp.testGroupLeader
      if (data.testGroupMember !== undefined) {
        this.formGrp.paramGroup = data.testGroupMember.split(',')
        this.formGrp.paramGroup = this.formGrp.paramGroup
          .filter(item => item !== groupLeader)
      }
      // this.formGrp.testGroupMember = this.formGrp.testGroupMember.split(',')
      // 管理人员下拉框v-for
      getHeadOfCompanyList().then(res => {
        if (res.data.status === 200) {
          this.headOfCompany_options = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
      // 测试小组成员小组下拉框
      getGroupDate(data).then(res => {
        this.testGroup_options = res.data.results
        this.testGroupMember_options = this.testGroup_options
          .filter(item => item.testEmpBadge !== groupLeader)
      }).catch(() => {
        this.$message({ type: 'error', message: '编辑失败！' })
      })
    },
    // 测试小组改变所属团队
    selectTestGroupEmp(data) {
      this.formGrp.testGroupLeader = null
      this.formGrp.paramGroup = null
      // 所属团队改下拉框
      const param = { teamId: data }
      const data1 = common.paramsFormat(param)
      selectTestGroupEmployee(data1).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        // this.testGroup_options = res.data.results
        this.$set(this, 'testGroup_options', res.data.results)
        // this.$set(this, 'testGroupMember_options', res.data.results)
        // this.testGroupMember_options = res.data.results
      })
    },
    // 取消弹窗
    calcelDialog() {
      this.diaologVisible = false
      this.formGrp = {}
    },
    screGroupName() {
      const param = { testGroupName: this.formGrp.testGroupName }
      const data = common.paramsFormat(param)
      selectGroupName(data).then(res => {
        if (res.data.status === 5000) {
          this.$alert('该小组已存在,请重新输入。', { type: 'warning' })
          return false
        }
      })
    },
    // 删除
    handleDelete(data) {
      this.$confirm('是否删除？', '删除确认',
        { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        deleteGroupById(data).then(res => {
          if (res.data.status === 200) {
            this.$message.success('测试小组删除成功!')
          } else {
            this.$message.error(res.data.message)
          }
          this.submitBtn()
        })
      })
    },
    handleClose() {
      this.formGrp = {}
      this.diaologVisible = false
    },
    // dialog确认
    editTestGroup() {
      this.$refs['formTestGroup'].validate((valid) => {
        if (valid) {
          this.diaologVisible = false
          updateTestGroup(this.formGrp).then(res => {
            if (res.data.status === 200) {
              this.$message.success('测试小组修改成功!')
            } else {
              this.$message.error(res.data.message)
            }
            this.submitBtn()
            this.formGrp = {}
          })
        }
      })
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

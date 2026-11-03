<!-- 测试人员池-->
<template>
  <div class="typeset-container">
    <div class="border-card">
      <el-form :model="form">
        <el-row :gutter="10">
          <el-col :span="4">
            <el-form-item label="测试团队:" label-width="73px">
              <el-select v-model="form.teamId" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in teamNameList"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="项目经理:" label-width="120px">
              <el-select v-model="form.badge" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in teamMansgerList"
                  :key="item.badge"
                  :label="item.empname +'('+item.badge+')'"
                  :value="item.badge"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="团队成员：" label-width="100px">
              <el-select v-model="form.testEmpBadge" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in teamMemberList"
                  :key="item.testEmpBadge"
                  :label="item.testEmpName + '('+item.testEmpBadge+')'"
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
      <div class="typeset-body">
        <el-table
          v-loading="loading"
          ref="multipleTable"
          :data="crewData"
          border
          height="390"
          element-loading-text="数据加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="gba(0,0,0,0.8)"
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="50" align="center"/>
          <el-table-column label="团队名称" width="80" align="center">
            <template slot-scope="scope">
              <el-link
                v-if="!scope.row.edited"
                plain
                @click="handleDetail(scope.row)" >{{ formatDept(scope.row.teamName) }}</el-link>
              <el-input v-if="scope.row.edited" v-model="scope.row.teamName" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="项目经理" width="150" prop="teamMansgers" align="center">
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
          <el-table-column prop="teamMemberNo" label="团队成员数" width="50" align="center"/>
          <el-table-column prop="testGroupNo" label="测试小组数" width="50" align="center"/>
          <el-table-column label="操作" width="180" >
            <template slot-scope="scope">
              <el-link
                v-if="!scope.row.edited"
                :underline="false"
                size="small"
                type="primary"
                @click="handleUpdate(scope.row)"
              >
                编辑
              </el-link
              >
              <el-link
                v-if="scope.row.edited"
                :underline="false"
                type="warning"
                size="small"
                @click="handleCancel(scope.row)"
              >
                取消
              </el-link
              >
              <el-link
                v-if="scope.row.edited"
                :underline="false"
                type="warning"
                size="small"
                @click="handleSave(scope.row)"
              >
                保存
              </el-link
              >
              <el-link
                :underline="false"
                size="small"
                type="primary"
                @click="updateTeaMem(scope.row)"
              >
                修改团队成员
              </el-link
              >
              <el-link
                :underline="false"
                size="small"
                type="primary"
                @click="createTeGro(scope.row)"
              >
                创建测试小组
              </el-link
              >
              <el-link
                :underline="false"
                size="small"
                type="primary"
                @click="handleDelete(scope.row)"
              >
                删除
              </el-link
              >
            </template>
          </el-table-column>
        </el-table>
        <el-dialog
          :visible.sync="diaologVisible2"
          width="45%"
          title="测试团队详情"
        >
          <div>
            <el-form ref="ruleform" :model="formTeDail" label-width="100px">
              <el-row :gutter="10">
                <el-col :span="4">
                  <el-form-item label="团队名称：" label-width="100px" >
                    <el-input v-model="formTeDail.teamName" style="width: 300px" placeholder="请输入"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                  <el-form-item label="团队成员：" label-width="100px" >
                    <el-select
                      v-model="formTeDail.testGroupMemberex"
                      multiple
                      style="width: 500px;"
                      filterable
                      clearable
                      disabled
                      placeholder="请选择">
                      <el-option
                        v-for="item in teamMemberAllIdList"
                        :disabled="item.eidhr == '0'"
                        :key="item.testEmpBadge"
                        :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                        :value="item.testEmpBadge"/>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col >
                  <el-table
                    :data="formGrDail"
                    border
                    style="100%"
                  >
                    <el-table-column type="index" label="序号" width="50" align="center"/>
                    <el-table-column prop="testGroupName" label="测试小组" width="100" align="center"/>
                    <el-table-column prop="testGroupMember" label="小组成员" width="120" align="center">
                      <template slot-scope="scope">
                        <el-select v-model ="scope.row.testGroupMember" disabled multiple clearable filterable >
                          <el-option
                            v-for="item in teamMemberAllIdList"
                            :key="item.testEmpBadge"
                            :label="item.testEmpName + '('+ testEmpBadge + ')'"
                            :value="item.testEmpBadge"
                          />
                        </el-select>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </el-dialog>
        <el-dialog
          :visible.sync="diaologVisible1"
          width="45%"
          title="创建测试小组"
        >
          <el-form ref="formTestGroup" :model="formGrp" :rules="testGroupRules" label-width="120px">
            <el-row :gutter="10">
              <el-col :span="5">
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
                    disabled
                    placeholder="请选择">
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
                <el-form-item label="小组成员:" prop="testGroupMember" label-width="120px">
                  <el-select
                    v-model="formGrp.testGroupMember"
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
                <el-button @click="diaologVisible1 = false , formGrp = {}" >取消</el-button>
                <el-button type="primary" @click="creatTestGroup">创建</el-button>
              </el-col>
            </el-row>
          </el-form>
        </el-dialog>
        <el-dialog
          :visible.sync="diaologVisible"
          width="45%"
          title="测试团队详情"
        >
          <el-form :model="formTeam">
            <el-row :gutter="10">
              <el-col :span="4">
                <el-form-item label="团队名称：" label-width="100px" prop="teamName" >
                  <el-input v-model="formTeam.teamName" disabled style="width: 300px" placeholder="请输入"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4">
                <el-form-item label="项目经理：" label-width="100px" prop="teamMansger" >
                  <el-select v-model="formTeam.teamMansger" filterable multiple disabled clearable style="width: 300px">
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
                  <el-select v-model="formTeam.teamMember" filterable multiple clearable style="width: 500px">
                    <el-option
                      v-for="item in teamMemberAllIdList"
                      :key="item.testEmpBadge"
                      :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                      :value="item.testEmpBadge"/>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="diaologVisible = false" >取消</el-button>
            <el-button @click="eidtDiaolog" >确认</el-button>
          </span>
        </el-dialog>
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
import common from '@/api/common'
import cleanBtn from '@/components/CleanBtn'
import pager from '@/components/Pager'
import buttonOrange from '@/components/eleButton/buttonOrange'
import CreatTestTeam from '@/components/creatTestTeam'
// eslint-disable-next-line no-unused-vars
import { getTemName, getTypeDicts, teamNameList } from '@/utils/dict'
// import { teamNameListInfo } from '@/api/testManagementApi/TestTeam'
import { selectTeamMansgerList,
  selectTeamList,
  selectMemberList,
  queryPager,
  updateTestTeam,
  temMebById,
  temMebAllNO,
  eidtTemMen,
  delTemById,
  selectGroupName,
  selectTestGroupEmployee,
  creatTestGroup,
  selectTemDetail,
  queryPersonListInfo
} from '@/api/testManagementApi/testTeamManagement'
import { getHeadOfCompanyList, getOwnedTestTeamList } from '@/api/testManagementApi/testEmployeePool'

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
      loading: false,
      crewData: [], // 列表分页数据
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      form: {},
      teamMansgerList: [],
      teamNameList: [],
      teamMemberList: [],
      diaologVisible: false,
      diaologVisible1: false,
      diaologVisible2: false,
      formTeam: {},
      teamMemberAllIdList: [],
      formGrp: {},
      testGroupMember_options: [],
      formTeDail: {},
      formGrDail: [],
      ownedTestTeam_options: [], // 所属团队下拉框
      testGroup_options: [], // 测试小组人员
      headOfCompany_options: [], // 公司内负责人下拉框
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
  computed: {

    formatDept() {
      return (deptName) => {
        return deptName
      }
    }
  },
  created() {
    this.loadInit()
  },
  mounted() {
    this.getInit()
  },
  methods: {
    /**
     * 分页初始化方法
     */
    loadInit: function() {
    // 查询测试团队下拉框
      const param = {}
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
          })
          self.totalNumber = res.data.total
          // if (self.totalNumber <= 0) {
          //   return false
          // }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    screenTestEmp() {
      const groupLeader = this.formGrp.testGroupLeader
      this.testGroupMember_options = this.testGroup_options.filter(item => item.testEmpBadge !== groupLeader)
    },
    // 编辑
    handleUpdate(data) {
      this.$set(data, 'edited', true)
    },
    // 取消
    handleCancel(data) {
      this.$set(data, 'edited', false)
    },
    // 保持
    handleSave(data) {
      this.$set(data, 'edited', false)
      updateTestTeam(data).then(res => {
        this.$message({ type: 'success', message: '编辑成功！' })
        this.submitBtn()
      }).catch(() => {
        this.$message({ type: 'error', message: '编辑失败！' })
      })
    },
    // 修改团队成员
    updateTeaMem(data) {
      this.diaologVisible = true
      this.formTeam.teamId = data.teamId
      this.formTeam.teamName = data.teamName
      this.formTeam.teamMansger = data.teamMansger
      temMebById(data).then(res => {
        this.formTeam.teamMember = res.data.results.map(item => item.testEmpBadge)
      }).catch(() => {
        this.$message({ type: 'error', message: '查询失败！' })
      })

      // 团队成员下拉框 v-for="item in teamMemberAllIdList"
      temMebAllNO(data).then(res => {
        this.teamMemberAllIdList = res.data.results
      }).catch(() => {
        this.$message({ type: 'error', message: '查询失败！' })
      })
    },
    // 修改团队成员确认
    eidtDiaolog() {
      const param = this.formTeam
      this.diaologVisible = false
      eidtTemMen(param).then(res => {
        if (res.data.status === 200) {
          this.$message({ type: 'success', message: '编辑成功！' })
          this.submitBtn()
        } else {
          this.$message({ type: 'error', message: '编辑失败！' })
        }
      })
    },
    // 删除
    handleDelete(data) {
      this.$confirm('是否删除？', '删除确认',
        { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        delTemById(data).then(res => {
          this.$message({ type: 'success', message: '删除成功！' })
          this.submitBtn()
        }).catch(() => {
          this.$message({ type: 'error', message: '删除失败！' })
        })
      })
    },
    // 创建测试小组
    createTeGro(date) {
      this.diaologVisible1 = true
      this.formGrp.ownedTestTeam = date.teamId
      getHeadOfCompanyList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.headOfCompany_options = res.data.results
      })
      const param = { teamId: date.teamId }
      const data = common.paramsFormat(param)
      selectTestGroupEmployee(data).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.$set(this.formGrp, 'testGroupLeader', '')
        this.$set(this.formGrp, 'testGroupMember', '')
        this.testGroup_options = res.data.results
        console.log('-------------', this.testGroup_options)
      })
      getOwnedTestTeamList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.ownedTestTeam_options = res.data.results
      })
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
    // 创建小组
    creatTestGroup() {
      this.$refs['formTestGroup'].validate((valid) => {
        if (valid) {
          const params = this.formGrp
          const data = common.paramsFormat(params)
          creatTestGroup(data).then(res => {
            if (res.data.status === 200) {
              this.$message.success('小组创建成功!')
              this.formGrp = {}
              this.diaologVisible1 = false
              this.submitBtn()
            } else {
              this.$message.error(res.data.message)
            }
          })
        }
      })
    },
    // 团队详情
    handleDetail(data) {
      this.diaologVisible2 = true
      this.formTeDail.teamName = data.teamName
      selectTemDetail(data).then(res => {
        this.formGrDail = res.data.results
        this.formGrDail.forEach((item) => {
          item.testGroupMember = item.testGroupMember.split(',')
        })
      }).catch(() => {
        this.$message({ type: 'error', message: '团队详情查询失败！' })
      })
      // 团队成员下拉框 v-for="item in teamMemberAllIdList"
      temMebById(data).then(res => {
        this.teamMemberAllIdList = res.data.results
        this.formTeDail.testGroupMemberex = res.data.results
          .filter(item => item.ownedTestGroup === undefined || item.ownedTestGroup === null || item.ownedTestGroup === '')
          .map(item => item.testEmpBadge)
        console.log('===============', this.teamMemberAllIdList)
        console.log('===============', this.formTeDail.testGroupMemberex)
      }).catch(() => {
        this.$message({ type: 'error', message: '成员下拉框查询失败！' })
      })
    },
    // 下载
    submitBtnInfo() {
      this.$confirm('是否导出测试团队表？', '测试团队表导出确认',
        { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        // const data = { size: 10, index: 1, _isDown: 'Y' }
        const data = { ...this.form }
        data.index = this.index
        data.size = this.size
        data._isDown = 'Y'
        queryPersonListInfo(data).then(res => {
          common.download(res.data, self, '测试团队表.xls')
        })
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

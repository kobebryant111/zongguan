<!-- 测试人员池-->
<template>
  <div class="typeset-container">
    <div class="border-card">
      <el-form :model="form">
        <el-row :gutter="12">
          <el-col :span="4">
            <el-form-item label="测试团队:" label-width="73px">
              <el-select v-model="searchForm.ownedTestTeam" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in ownedTestTeam_options"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="测试小组:" label-width="73px">
              <el-select v-model="searchForm.ownedTestGroup" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in ownedTestGroup_options"
                  :key="item.id"
                  :label="item.testGroupName"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="工号/姓名:" label-width="80px">
              <el-input v-model="searchForm.testEmpBadge" style="width: 192px" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="公司内负责人:" label-width="110px">
              <el-select v-model="searchForm.headOfCompany" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in headOfCompany_options"
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
              <el-select v-model="searchForm.testGroupLeader" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in testGroupEmployee_options"
                  :key="item.testEmpBadge"
                  :label="item.testEmpName + '(' + item.testEmpBadge + ')'"
                  :value="item.testEmpBadge"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="员工状态:" label-width="73px">
              <el-select v-model="searchForm.employeeStatus" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in employeeStatus_options"
                  :key="item.dictid"
                  :label="item.dictname"
                  :value="item.dictid"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <div style="height: 36px;line-height: 36px;">
              <buttonOrange :fns="submitBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
              <clean-btn :forms="searchForm" size="small" />
              <buttonOrange :fns="downLoadMsg" button-name="下载" size="mini" />
            </div>
          </el-col>
        </el-row>
      </el-form>
      <div class="typeset-body">
        <el-table
          ref="multipleTable"
          :data="crewData"
          border
          height="300"
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="50" align="center"/>
          <el-table-column prop="testEmpName" label="测试人员" align="center"/>
          <el-table-column prop="testEmpBadge" label="工号" align="center"/>
          <el-table-column
            label="所属公司"
            prop="affilCompany"
            show-overflow-tooltip
            align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ getAffName(affilCompany_options,scope.row.affilCompany) }}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.affilCompany">
                <el-option
                  v-for="item in affilCompany_options"
                  :key="item.orgcode"
                  :label="item.outsourcname"
                  :value="item.orgcode"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="员工状态"
            prop="employeeStatus"
            align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ getDictName(employeeStatusList,scope.row.employeeStatus) }}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.employeeStatus">
                <el-option
                  v-for="item in getEmployeeStatusOptions"
                  :key="item.badge"
                  :label="item.empname + '(' + item.badge + ')'"
                  :value="item.badge"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="公司内负责人"
            prop="headOfCompany"
            align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ getZJDEName(headOfCompany_options,scope.row.headOfCompany) }}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.headOfCompany">
                <el-option
                  v-for="item in headOfCompany_options"
                  :key="item.badge"
                  :label="item.empname + '(' + item.badge + ')'"
                  :value="item.badge"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="所属测试团队"
            prop="ownedTestTeam"
            show-overflow-tooltip
            align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ getownedTestTeamName(ownedTestTeam_options,scope.row.ownedTestTeam) }}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.ownedTestTeam">
                <el-option
                  v-for="item in ownedTestTeam_options"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="所属测试小组"
            prop="ownedTestGroup"
            show-overflow-tooltip
            align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ getownedTestGroupName(ownedTestGroup_options,scope.row.ownedTestGroup) }}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.ownedTestGroup">
                <el-option
                  v-for="item in ownedTestGroup_options"
                  :key="item.id"
                  :label="item.testGroupName"
                  :value="item.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="属性"
            prop="empAttribute"
            align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ getDictName(empAttributeList,scope.row.empAttribute) }}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.empAttribute">
                <el-option
                  v-for="item in getEmpAttributeOptions"
                  :key="item.dictId"
                  :label="item.dictName"
                  :value="item.dictId"
                />
              </el-select>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-link
                :underline="false"
                type="warning"
                @click="handleUpdate(scope.row)"
              >
                编辑
              </el-link>
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

      <!-- dialog -->
      <div>
        <template>
          <el-dialog :title="title" :visible.sync="dialogClose" width="30%">
            <el-form ref="menuForm" :rules="menuFormRules" :model="menuForm" status-icon label-width="100px" >
              <el-input v-if="false" v-model="menuForm.id" style="width: 192px" placeholder="请输入"/>
              <el-input v-if="false" v-model="menuForm.testEmpBadge" style="width: 192px" placeholder="请输入"/>
              <el-input v-if="false" v-model="menuForm.oldTestGroup" style="width: 192px" placeholder="请输入"/>
              <el-form-item label="员工状态:" prop="employeeStatus" label-width="120px">
                <label slot="label">员&nbsp;&nbsp;工&nbsp;&nbsp;&nbsp;状&nbsp;&nbsp;态:</label>
                <el-select v-model="menuForm.employeeStatus" filterable clearable placeholder="请选择" style="width: 300px;">
                  <el-option
                    v-for="item in getEmployeeStatusOptions"
                    :key="item.dictId"
                    :label="item.dictName"
                    :value="item.dictId"/>
                </el-select>
              </el-form-item>

              <el-form-item label="所属测试团队:" prop="ownedTestTeam" label-width="120px">
                <el-select
                  v-model="menuForm.ownedTestTeam"
                  filterable
                  clearable
                  placeholder="请选择"
                  style="width: 300px;"
                  @change="selectTestGroupInfo(menuForm.ownedTestTeam)">
                  <el-option
                    v-for="item in ownedTestTeam_options"
                    :key="item.teamId"
                    :label="item.teamName"
                    :value="item.teamId"/>
                </el-select>
              </el-form-item>

              <el-form-item label="所属测试小组:" prop="ownedTestGroup" label-width="120px">
                <el-select v-model="menuForm.ownedTestGroup" filterable clearable placeholder="请选择" style="width: 300px;">
                  <el-option
                    v-for="item in ownedTestGroup_options_forTeam"
                    :key="item.id"
                    :label="item.testGroupName"
                    :value="item.id"/>
                </el-select>
              </el-form-item>

              <el-form-item label="管理人员:" prop="headOfCompany" label-width="120px">
                <label slot="label">管&nbsp;&nbsp;理&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;员:</label>
                <el-select v-model="menuForm.headOfCompany" filterable clearable placeholder="请选择" style="width: 300px;">
                  <el-option
                    v-for="item in headOfCompany_options"
                    :key="item.badge"
                    :label="item.empname + '(' + item.badge + ')'"
                    :value="item.badge"/>
                </el-select>
              </el-form-item>

            </el-form>
            <div class="dialog-footer">
              <el-button @click="dialogCancel">取消</el-button>
              <el-button type="primary" @click="dialogAddSubmit">保存</el-button>
            </div>
          </el-dialog>
        </template>
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
import { getDictName, getTypeDicts } from '@/utils/dict'
import { getDicEntry } from '@/api/eosDictEntry'
import { queryPager,
  getHeadOfCompanyList,
  exportTestEmpPoolExcel,
  getAffilCompanyList,
  getOwnedTestTeamList,
  getOwnedTestGroupList,
  getTestGroupEmpList,
  dialogAddSubmit,
  selectTestGroupInfoMsg } from '@/api/testManagementApi/testEmployeePool'
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
      title: '', // dialog 标题
      dialogClose: false, // dialog 显示
      employeeStatusList: 'EMPLOYEE_STATUE',
      empAttributeList: 'TEST_EMP_ATTRIBUTE',
      searchForm: {
        ownedTestGroup: null, // 测试小组
        ownedTestTeam: null, // 测试团队
        testGroupLeader: null, // 测试组长
        headOfCompany: null, // 公司内负责人
        groupMember: null, // 小组成员
        employeeStatus: null, // 员工状态
        testEmpBadge: null // 工号
      },
      menuForm: { // 表单
        id: '', // 主键ID
        testEmpBadge: '', // 测试人员工号
        employeeStatus: '', // 员工状态
        affilCompany: '', // 所属公司
        ownedTestGroup: '', // 所属测试小组
        ownedTestTeam: '', // 所属测试团队
        headOfCompany: '', // 公司内负责人
        oldTestGroup: '' // 原来测试小组
      },
      crewData: [], // 列表分页数据
      employeeStatus_options: [], // 员工状态下拉框
      headOfCompany_options: [], // 公司内负责人下拉框
      affilCompany_options: [], // 所属公司
      ownedTestTeam_options: [], // 所属测试团队
      ownedTestGroup_options: [], // 所属测试小组
      ownedTestGroup_options_forTeam: [], // 团队下关联的测试小组信息
      testGroupEmployee_options: [], // 测试小组人员
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      form: {},
      menuFormRules: {
        employeeStatus: [
          { required: true, message: '请选择员工状态', trigger: 'blur' }
        ],
        ownedTestTeam: [
          { required: true, message: '请选择所属测试团队', trigger: 'blur' }
        ],
        ownedTestGroup: [
          { required: true, message: '请选择所属测试小组', trigger: 'blur' }
        ],
        headOfCompany: [
          { required: true, message: '请选择管理人员', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    getEmployeeStatusOptions() {
      return getTypeDicts(this.employeeStatusList)
    },
    getEmpAttributeOptions() {
      return getTypeDicts(this.empAttributeList)
    }
  },

  methods: {
    /**
     * 分页初始化方法
     */
    loadInit: function() {
      this.getDic()
      const params = this.searchForm
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },
    getZJDEName(dicts, dictCode) {
      var result = dictCode
      if (!dicts) {
        return result
      }
      for (var tmp in dicts) {
        if (dicts[tmp].badge === dictCode) {
          result = dicts[tmp].empname + '(' + dicts[tmp].badge + ')'
          break
        }
      }
      return result
    },
    getAffName(dicts, dictCode) {
      var result = dictCode
      if (!dicts) {
        return result
      }
      for (var tmp in dicts) {
        if (dicts[tmp].orgcode === dictCode) {
          result = dicts[tmp].outsourcname
          break
        }
      }
      return result
    },
    getownedTestTeamName(dicts, dictCode) {
      var result = dictCode
      if (!dicts) {
        return result
      }
      for (var tmp in dicts) {
        if (dicts[tmp].teamId === dictCode) {
          result = dicts[tmp].teamName
          break
        }
      }
      return result
    },
    getownedTestGroupName(dicts, dictCode) {
      var result = dictCode
      if (!dicts) {
        return result
      }
      for (var tmp in dicts) {
        if (dicts[tmp].id === dictCode) {
          result = dicts[tmp].testGroupName
          break
        }
      }
      return result
    },
    // 获取列表数据
    getPagerList(params) {
      const data = common.paramsFormat(params)
      const self = this
      queryPager(data).then(res => {
        if (res.data.status === 200) {
          self.crewData = res.data.results
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            this.$message.warning('未查询到相关数据!')
            return false
          }
          this.$message.success('数据查询成功!')
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 查询业务字典
    getDic() {
      getDicEntry({
        dicttypeid: 'EMPLOYEE_STATUE'
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        const data = res.data.results
        // 状态
        this.employeeStatus_options = data.filter(item => {
          return item.dicttypeid === 'EMPLOYEE_STATUE'
        })
        return data
      })
      getHeadOfCompanyList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.headOfCompany_options = res.data.results
      })
      getAffilCompanyList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.affilCompany_options = res.data.results
      })
      getOwnedTestTeamList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.ownedTestTeam_options = res.data.results
      })
      getOwnedTestGroupList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.ownedTestGroup_options = res.data.results
        this.ownedTestGroup_options_forTeam = res.data.results
      })
      getTestGroupEmpList().then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        this.testGroupEmployee_options = res.data.results
      })
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      const params = {}
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    // 导出测试人员池信息表信息
    downLoadMsg() {
      const self = this
      const params = this.searchForm
      params._isDown = 'Y'
      exportTestEmpPoolExcel(params).then(res => {
        common.download(res.data, self, '测试人员池信息表.xls')
      })
    },

    /**
     * 搜索事件
     */
    submitBtn: function() {
      const params = this.searchForm
      this.getPagerList(params)
    },
    // 编辑
    handleUpdate(params) {
      Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' })
      this.title = '编辑信息'
      this.dialogClose = true
      this.menuForm.employeeStatus = params.employeeStatus
      this.menuForm.affilCompany = params.affilCompany
      this.menuForm.headOfCompany = params.headOfCompany
      this.menuForm.ownedTestTeam = params.ownedTestTeam
      this.menuForm.ownedTestGroup = params.ownedTestGroup
      this.menuForm.oldTestGroup = params.ownedTestGroup
      this.menuForm.id = params.id
      this.menuForm.testEmpBadge = params.testEmpBadge
    },
    /**
     * 取消按钮,关闭dialog
     */
    dialogCancel: function() {
      this.dialogClose = false
      Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' })
    },
    /**
     *  提交保存
     */
    dialogAddSubmit: function() {
      this.$refs['menuForm'].validate((valid) => {
        if (valid) {
          dialogAddSubmit(this.menuForm).then(res => {
            if (res.data.status === 200) {
              this.$message.success('测试人员信息修改成功!')
              this.submitBtn()
            } else {
              this.$message.error(res.data.message)
            }
            this.dialogClose = false
          })
        }
      })
    },
    // 获取所属团队下的小组信息
    selectTestGroupInfo(teamId) {
      const param = { teamId: teamId }
      const data = common.paramsFormat(param)
      selectTestGroupInfoMsg(data).then(res => {
        this.$set(this.menuForm, 'ownedTestGroup', '')
        this.ownedTestGroup_options_forTeam = res.data.results
      })
    }
  }
}
</script>

<style scoped>
.el-table .el-table__body td {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.dialog-footer {
    margin-top: 10px;
    text-align: right;
}
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

<!-- 总部安全专员联络清单-->
<template>
  <div class="typeset-container">
    <div class="border-card">
      <div class="typeset-body">
        <el-table
          ref="multipleTable"
          :data="crewData"
          border
          style="width: 100%"
        >
          <el-table-column v-if="false" prop="id" label="主键ID" align="center"/>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="location"
            label="部门"
            align="center">
            <template slot-scope="scope">
              <edit-cell
                :style="{ color: scope.row.safetyStatus === 'Y' ? 'red' : 'black' }"
                :data="scope.row"
                :can-edit="canEdit"
                v-model="scope.row.location"
                :maxleng="50"
                @custom-event="handleCustomEvent"/>
            </template>
          </el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="depManager"
            label="部门经理"
            align="center">
            <template slot-scope="scope">
              <edit-cell
                :data="scope.row"
                :can-edit="canEdit"
                v-model="scope.row.depManager"
                :maxleng="50"
                @custom-event="handleCustomEvent"/>
            </template>
          </el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="safetySpecialist"
            label="安全专员"
            align="center">
            <template slot-scope="scope">
              <edit-cell
                :data="scope.row"
                :can-edit="canEdit"
                v-model="scope.row.safetySpecialist"
                :maxleng="50"
                @custom-event="handleCustomEvent"/>
            </template>
          </el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="badge"
            label="工号"
            align="center">
            <template slot-scope="scope">
              <edit-cell
                :data="scope.row"
                :can-edit="canEdit"
                v-model="scope.row.badge"
                :maxleng="50"
                @focusInput="showUserDialog"/>
            </template>
          </el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="phoneNum"
            label="联系方式"
            align="center">
            <template slot-scope="scope">
              <edit-cell
                :data="scope.row"
                :can-edit="canEdit"
                v-model="scope.row.phoneNum"
                :maxleng="11"
                @custom-event="handleCustomEvent"/>
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
    <!-- dialog begin -->
    <div class="dialog-user-container-div">
      <template>
        <el-dialog :visible.sync="dialogVisible" title="用户列表" width="60%" top="10vh" @close="handleClose">
          <employee ref="employee" :role-data="dialogUserData" :reset-pwd-visible="dialogResetPwd" :table-data="tableData" @chickRow="getDialogUserRow"/>
          <div>
            <el-button type="primary" @click="selectedUser">确定</el-button>
            <el-button @click="dialogCancel">取消</el-button>
          </div>
        </el-dialog>
      </template>
    </div>
  </div>
</template>

<script>
import employee from '@/views/safetyManagement/views/EmployeeMsg'
import request from '@/utils/request'
import EditCell from '@/components/EditCell/index'
import common from '@/api/common'
import cleanBtn from '@/components/CleanBtn'
import pager from '@/components/Pager'
import buttonOrange from '@/components/eleButton/buttonOrange'
// eslint-disable-next-line no-unused-vars
import { getDictName, getTypeDicts } from '@/utils/dict'
// eslint-disable-next-line no-unused-vars
import { getDicEntry } from '@/api/eosDictEntry'
import { queryPager, updateMsg, queryRole } from '@/api/safetyManagementApi/safetyOfficerList'
export default {
  name: 'SafetyOfficerList',
  components: {
    employee,
    buttonOrange,
    cleanBtn,
    pager,
    EditCell
  },
  data() {
    return {
      tempStorageEmployee: {}, // 暂存人员信息
      needUpdateEmployee: {}, // 需要修改的数据
      roleIdC: '',
      dialogResetPwd: true,
      dialogUserData: [], // dialog 用户数据
      dialogBadge: '', // 用于dialog用户选中用户后储存用户id
      dialogVisible: false, // dialog 显示 默认不显示
      title: '', // dialog 标题
      canEdit: false, // 编辑状态
      dialogClose: false, // dialog 显示
      searchForm: {
        location: null // 部门
      },
      crewData: [], // 列表分页数据
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      tableData: {}
    }
  },

  methods: {
    /**
     * 选择dialog 用户
     */
    selectedUser() {
      if (this.needUpdateEmployee === {}) {
        this.$message.error('请选择用户')
        return false
      }
      // 修改数据
      const _obj = {}
      const depcodeMsg = this.tempStorageEmployee.depcode
      if (depcodeMsg === '' && depcodeMsg === undefined) {
        this.$message.error('总部部门编号为空,请联系管理员进行数据维护!')
        return
      }
      _obj.depcode = depcodeMsg
      _obj.badge = this.needUpdateEmployee.badge
      _obj.empname = this.needUpdateEmployee.empname
      _obj.mobiletelphone = this.needUpdateEmployee.mobiletelphone
      _obj.safetyStatus = 'N'

      const _data = common.paramsFormat(_obj)
      request({
        url: '/safetyOfficerList/updateEmployeeMsg',
        method: 'post',
        data: _data
      }).then(res => {
        // 请求后则进行清空暂存数据
        this.tempStorageEmployee = ''
        this.needUpdateEmployee = ''
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
        } else {
          this.loadInit()
          this.$message({
            message: '总部安全专员数据修改成功!',
            type: 'success!',
            center: 'true'
          })
          this.queryUserRoleRel()
        }
      })
      this.dialogCancel()
    },
    handleClose() {
      this.$refs.employee.closeInput()
    },
    /**
     * 取消键
     */
    dialogCancel() {
      this.dialogVisible = false
      this.$refs.employee.employData = []
      const userForm = this.$refs.employee.paramForm
      Object.keys(userForm).forEach(key => {
        userForm[key] = ''
      })
    },
    /**
     * 显示用户信息
     */
    showUserDialog(params) {
      console.log('选中行的原数据:', this, params)
      this.dialogVisible = true
      params.flag = '1'
      this.tableData = params
      this.$nextTick(() => {
        this.$refs.employee.init()
      })
      // 先将选中的信息进行暂存,后续修改后,则暂存区在清空
      this.tempStorageEmployee = params
    }, // 显示用户信息 end
    closeWin() {
      // this.queryUserRoleRel()
      // 菜单选中数据
      // request({
      //   url: '/imPermissions/queryOperaList?rid=' + this.checkedNode.rolId,
      //   method: 'get'
      // }).then(res => {
      //   if (res.data.status !== 200) {
      //     this.$alert(res.data.message, { type: 'error' })
      //     return false
      //   }
      //   this.$refs.menuTree.setCheckedNodes(res.data.results)
      // })
      this.dialogVisible = false
    },
    /**
       *  选中dialog 用户 表行
       */
    getDialogUserRow: function(data) {
      // 将弹窗中选中的数据进行暂存
      this.needUpdateEmployee = data
    }, // 选中dialog 用户 表行 end
    /**
     * 分页初始化方法
     */
    loadInit: function() {
      const params = this.searchForm
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
      this.selectRole()
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
    /**
     * 搜索事件
     */
    submitBtn: function() {
      const params = this.searchForm
      this.getPagerList(params)
    },
    // 查询角色权限信息修改编辑状态
    selectRole() {
      queryRole().then(res => {
        if (res.data.status === 200) {
          const re = res.data.results[0]
          if (res.data.results.length > 0 && re.urrRoleid === 'SAFETY_SPECIALIST') {
            this.canEdit = true
          }
        }
      })
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
    // 修改内容
    handleCustomEvent(params) {
      console.log('Row:', params)
      const data = common.paramsFormat(params)
      updateMsg(data).then(res => {
        if (res.data.status === 200) {
          this.$message.success('数据修改成功!')
        } else {
          this.$message.error(res.data.message)
        }
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

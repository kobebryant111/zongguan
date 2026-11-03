<template>
  <div class="page-container">
    <el-form ref="paramForm" :inline="true" :model="listQuery" class="search-container" size="small">
      <el-form-item label="登录名：">
          <el-input v-model="listQuery.loginNameLike" placeholder="请输入登录名" />
      </el-form-item>
      <el-form-item label="姓名：">
          <el-input v-model="listQuery.realname" placeholder="请输入姓名" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="handleFilter">查询</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="handleReset">重置</el-button>
    </el-form>
    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" @click="showUserDialog">新增</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="editUserMg">修改</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="deleteUser">删除</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="resetUserPwd">重置密码</el-button>
      <el-button type="primary" icon="el-icon-document-checked" @click="enableUser('1')">生效</el-button>
      <el-button type="primary" icon="el-icon-document-delete" @click="enableUser('2')">失效</el-button>
    </el-button-group>
    <ts-multi-table ref="usersTable" :query="listQuery" :options="tableOptions">
      <el-table-column prop="userNum" label="用户编号" />
      <el-table-column prop="loginName" label="登录名称" />
      <el-table-column prop="realname" label="姓名" />
      <el-table-column prop="sexCd" label="性别" width="80">
        <template v-slot="scope">
            <span>{{ scope.row.sexCd | tsConvert('Gnd') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="telephone" label="电话" width="150" />
      <el-table-column prop="statusCd" label="状态" width="80">
        <template v-slot="scope">
            <span>{{ scope.row.statusCd | tsConvert('StCd') }}</span>
        </template>
      </el-table-column>
    </ts-multi-table>
  </div>
</template>

<script>
import Vue from 'vue'
import {
  mapState
} from 'vuex'

Vue.prototype.userStepBus = new Vue()

export default {
  name: 'UserManagementTable',
  data() {
    return {
      visible: false,
      tableOptions: {
        type: 'radio',
        apiName: this.$api.queryUsermg,
        paramsOrdata: 'data'
      },
      selectData: {},
      enableCode: '1',
      disableCode: '2',
      tableData: null,
      total: 0,
      listQuery: {
        clntendId: '',
        createUser: '',
        loginNameLike: '',
        realname: ''
      }
    }
  },
  computed: {
    ...mapState({
      'userInfo': state => state.user.userInfo
    })
  },
  created() {
    this.listQuery.clntendId = this.userInfo.clntendId
    this.listQuery.createUser = this.userInfo.userNum
  },
  methods: {
    getUserList() {
      this.$refs['usersTable'].serchHandel()
    },
    handleFilter() {
      this.$refs['usersTable'].serchHandel()
    },
    handleReset() {
      this.listQuery.loginNameLike = ''
      this.listQuery.realname = ''
    },
    // 新增用户
    showUserDialog() {
      // 写法1 注意加 newtag tp（初始页签来源）
      // let queryOpts = {
      //   newtag: false,
      //   tp:this.$route.path
      //   viewType: 'add',
      // }
      // this.$router.push({
      //   path: '/user/usermg/personAddMsg',
      //   query: queryOpts
      // })
      // 写法2
      const queryOpts = {
        viewType: 'add'
      }
      this.$tsVue.tansunPushNoAddTag(this, '/user/usermg/personAddMsg', queryOpts)
    },

    // 修改用户信息
    editUserMg() {
      this.selectData = this.$refs['usersTable'].getRadioData()
      if (this.selectData) {
        const { userNum } = this.selectData
        // 写法1 注意加 newtag tp（初始页签来源）
        // let queryOpts = {
        //     viewType: 'edit',
        //     tp:this.$route.path,
        //     newtag: false,
        //     userNum
        // }
        // this.$router.push({
        //     path: '/user/usermg/personAddMsg',
        //     query: queryOpts
        // })
        // 写法2
        const queryOpts = {
          viewType: 'edit',
          userNum
        }
        this.$tsVue.tansunPushNoAddTag(this, '/user/usermg/personAddMsg', queryOpts)
      } else {
        this.$alert('请选中一条记录')
      }
    },

    // 删除用户
    deleteUser() {
      this.selectData = this.$refs['usersTable'].getRadioData()
      if (!this.selectData) {
        this.$alert('请选中一条记录')
        return
      }
      const { id, userNum, loginName } = this.selectData
      const params = { id }
      if (userNum === this.userInfo.userNum) {
        this.$alert('用户无法删除自己', '提示', {
          confirmButtonText: '确定'
        })
      }
      this.$confirm(`确认要删除用户 “${loginName}” 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$request({
          url: this.$api.usermgDelete,
          data: params
        }).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getUserList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    // 重置用户密码
    resetUserPwd() {
      this.selectData = this.$refs['usersTable'].getRadioData()
      if (!this.selectData) {
        this.$alert('请选中一条记录')
        return
      }
      const { id, loginName } = this.selectData
      const params = { id }

      this.$confirm(`确认要重置用户 “${loginName}” 的密码吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        this.$request({
          url: this.$api.usermgResetPassword,
          data: params
        }).then(res => {
          this.$message({
            type: 'success',
            message: '重置成功!'
          })
          this.getUserList()
        })
      }).catch(() => {})
    },

    // 生效/失效用户
    enableUser(status) {
      this.selectData = this.$refs['usersTable'].getRadioData()

      if (!this.selectData) {
        this.$alert('请选中一条记录')
        return
      }
      const { id, loginName, realname } = this.selectData
      const tipText = (status === '1') ? '生效' : '失效'
      const params = {
        id,
        loginName,
        realname,
        statusCd: status
      }
      this.$confirm(`确认要${tipText}用户 “${loginName}” 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        this.$request({
          url: this.$api.usermgSave,
          data: params
        }).then(res => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.handleFilter()
        })
      }).catch(() => {})
    }

  }
}
</script>


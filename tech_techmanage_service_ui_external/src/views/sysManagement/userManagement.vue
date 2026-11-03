<template>
  <div class="app-container">
    <!-- Table Filter -->
    <div class="filter-container">
      <el-form ref="filterForm" :model="filterForm" :inline="true">
        <el-form-item prop="userId" label="用户工号:">
          <el-input
            placeholder="用户工号"
            v-model="filterForm.userId"
            clearable
            style="width: 110px;" 
            class="filter-item"
            @keyup.enter.native="getUserList(true)">
          </el-input>
        </el-form-item>
        <el-form-item prop="userName" label="用户姓名:">
          <el-input
            placeholder="用户姓名"
            v-model="filterForm.userName"
            clearable
            style="width: 110px;" 
            class="filter-item"
            @keyup.enter.native="getUserList(true)">
          </el-input>
        </el-form-item>
        <el-form-item prop="userState" label="状态:">
          <el-select 
            v-model="filterForm.userState" 
            placeholder="状态" 
            class="filter-item" 
            style="width: 120px"
            @change="getUserList(true)">
            <el-option label="正常" value="0"/>
            <el-option label="用户停用" value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item
          prop="roleId">
          <el-dialog
            title="选择角色"
            :visible.sync="roleIdFilter">
            <el-tree 
              :data="roleData" 
              :props="roleProp" 
              show-checkbox 
              check-strictly 
              default-expand-all 
              accordion
              ref="tree"
              node-key="roleId">
            </el-tree>
            <span slot="footer" class="dialog-footer">
              <el-button size="mini" type="text" @click="roleIdFilter = false">取消</el-button>
              <el-button size="mini" type="text" @click="confirmRoleIdFilter">确认</el-button>
            </span>
          </el-dialog>
          <el-button @click="roleIdFilter = true">{{ roleIdFilterName }}</el-button>
        </el-form-item>

        <el-form-item
          prop="branchId">
          <el-dialog
            title="选择机构"
            :visible.sync="branchIdFilter">
            <el-tree 
              height="400"
              :data="branchData" 
              :props="branchProp" 
              show-checkbox 
              check-strictly 
              ref="tree2"
              node-key="deptId">
            </el-tree>
            <span slot="footer" class="dialog-footer">
              <el-button size="mini" type="text" @click="branchIdFilter = false">取消</el-button>
              <el-button size="mini" type="text" @click="confirmBranchIdFilter">确认</el-button>
            </span>
          </el-dialog>
          <el-button @click="branchIdFilter = true">{{ branchFilterName }}</el-button>
        </el-form-item>
        <el-button type="primary" class="filter-item" style="margin-left: 10px;" @click="getUserList(true)">确认</el-button>
        <el-button plain class="filter-item" @click="resetFilterAndQuery">重置</el-button>
        <el-button class="add-btn" type="primary" icon="el-icon-edit" @click="newUser">新建</el-button>
      </el-form>
     
      
    </div>

    <!-- Table -->
    <el-table
      :data="userList"
      border
      highlight-current-row
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column
        prop="userId"
        label="用户工号"
        width="100"
        align="center">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="用户姓名"
        align="center">
      </el-table-column>
      <el-table-column
        label="角色名称"
        width="280"
        align="center">
        <template slot-scope="scope">
          <el-tag v-for="role in scope.row.roles" :key="role.roleId" size="mini">{{ role.roleName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="状态"
        width="120"
        align="center">
        <template slot-scope="scope">
          <el-tag type="scope.row.userState == '0' : 'success' : 'warning'">
            {{ scope.row.userState | userState }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="branchName"
        label="机构"
        align="center">
      </el-table-column>
      
      <el-table-column
        label="操作"
        fixed="right"
        width="320"
        align="center"
        class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="changeUserStatus(scope.row)" :disabled="!hasPermission(scope.row)" v-if="scope.row.userState === '2'">启用</el-button>
          <el-button type="danger" size="mini" @click="changeUserStatus(scope.row)" :disabled="!hasPermission(scope.row)" v-if="scope.row.userState === '0'">停用</el-button>
          <el-button type="primary" size="mini" @click="modifyUser(scope.row)" :disabled="!hasPermission(scope.row)">修改</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" :disabled="!hasPermission(scope.row)">删除</el-button>
          <el-button type="warning" size="mini" @click="handleResetPassword(scope.row)" :disabled="!hasPermission(scope.row)">重置密码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- Pagination -->
    <div class="pagination-container">
      <el-pagination 
        :current-page.sync="currentPage" 
        :page-sizes="[5,10,20,30,50]" 
        :page-size="pageSize" 
        :total="totalElements" 
        background 
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="currentPageChange"
        @size-change="pageSizeChange"
        />
    </div>

    
  </div>  
</template>

<style lang="scss" scoped>
  .filter-container {
    position: relative;
    .add-btn {
      position: relative;
      top: 0px;
      right: -10px;
    }
  }
  .el-form--inline .el-form-item {
    margin: 0 10px 0 0;
  }
  .app-container .el-table--medium td{
    padding: 5px 0;
  }
  .app-container .el-tag{
    margin: 0 3px 0 0;
  }
  .app-container .el-dialog__body{
    height: 400px;
    overflow-y: scroll;
  }
  .popoverDiv{
    text-align: right;
    margin: 0;
  }
</style>

<script>
  import { fetchUsers, fetchAllRoles, fetchBranch, deleteUser, resetPassword, modifyUser } from '@/api/sysManagement'
  const Qs = require('qs')

  export default {
    name: 'userManagement',
    components: {},
    data () {
      return {
        userList: [], // 用户列表
        tableLoading: false,
        pageSize: 10, //每页数量
        currentPage: 1, // 当前页
        totalElements: 0, // 总数
        filterForm: {
          userId: '',
          userName: '',
          branchId: '',
          userState: '',
          roleId: ''
        }, // 过滤条件表格
        roleIdFilter: false,
        branchIdFilter: false,
        roleData: [], // 角色树数据
        branchData: [],
        roleProp: {
          children: 'children',
          label: 'roleName'
        },
        branchProp: {
          children: 'children',
          label: 'deptName'
        },
        roleIdFilterName: '角色名称',
        branchFilterName: '机构名称',
      }
    },
    created() {
      this.getUserList(false)
      this.getRoleTree()
      this.getBranch()
    },
    methods: {
      /**
       * 获取分页用户列表
       */
      getUserList(isFliter) {
        let turnPageBeginPos = this.pageSize * (this.currentPage - 1) + 1
        if (isFliter) {
          turnPageBeginPos = 1
        }
        let query = {
          pageNum: this.pageSize * (this.currentPage - 1) + 1,
          pageSize: this.pageSize,
          userId: this.filterForm.userId,
          userName: this.filterForm.userName,
          branchId: this.filterForm.branchId,
          userState: this.filterForm.userState,
          roleId: this.filterForm.roleId
        }
        this.tableLoading = true
        fetchUsers(query).then(res => {
          this.tableLoading = false
          this.userList = res.data.results.rows
          this.totalElements = res.data.results.total
        }).catch(error => {
          console.log(error)
        })
      },

      /**
       * 分页功能 - 检测当前页变化
       */
      currentPageChange(val) {
        this.currentPage = val
        this.getUserList(false)
      },
      /**
       * 分页功能 - 检测PageSize变化
       */
      pageSizeChange(val) {
        this.pageSize = val
        this.getUserList(false)
      },
      /**
       * 获取角色树数据
       */
      getRoleTree() {
        fetchAllRoles().then( res => {
          this.roleData.push(res.data.results)
        }).catch( error => {
          console.log(error)
        })
      },
      /**
       * 确认角色树选择
       */
      confirmRoleIdFilter() {
        let chooseRoles = this.$refs.tree.getCheckedNodes()
        if(chooseRoles.length > 1) {
          this.$message({
            message: '选择一个角色',
            type: 'warning'
          })
          return false
        }
        this.roleIdFilterName = chooseRoles[0].roleName
        this.filterForm.roleId = chooseRoles[0].roleId
        this.roleIdFilter = false
        this.getUserList(true)
      },
      /**
       * 获取机构数据
       */
      getBranch() {
        fetchBranch().then(res => {
          this.branchData.push(res.data.results)
        }).catch( error => {
          console.log(error)
        })
      },
      
      confirmBranchIdFilter() {
        let chooseRoles = this.$refs.tree2.getCheckedNodes()
        if(chooseRoles.length > 1) {
          this.$message({
            message: '选择一个机构',
            type: 'warning'
          })
          return false
        }
        this.branchFilterName = chooseRoles[0].deptName
        this.filterForm.branchId = chooseRoles[0].deptId
        this.branchIdFilter = false
        this.getUserList(true)
      },
      /**
       * 重置
       */
      resetFilterAndQuery(){
        this.$refs['filterForm'].resetFields()
        this.roleIdFilterName = '角色名称'
        this.filterForm.roleId = ''
        this.branchFilterName = '机构名称'
        this.filterForm.branchId = ''
        this.getUserList(true)
      },

      /**
       * 创建新的用户
       */
      newUser() {
        this.$router.push({ path: '/sysManagement/newUser', query: { type: 0 } }) // type 0新建 1修改
        // this.newUserDialog = true
      },
      // 启用/停用状态切换
      changeUserStatus (item) {
        let param = {
          userId: item.userId,
          userName: item.userName,
          branchId: item.branchId,
          userState: item.userState === '0' ? '2' : '0', // 0:正常，2：停用
          roleId: ''
        }
        let rolesData = item.roles
        let roleIdArr = []
        for (let i = 0; i < rolesData.length; i++) {
          roleIdArr.push(rolesData[i].roleId)
        }
        param.roleId = roleIdArr.join(',')
        modifyUser(Qs.stringify(param)).then(response => {
          this.getUserList()
        })
      },
      /**
       * 修改用户
       */
      modifyUser(item) {
        console.log(item)
        this.$router.push({
          path: '/sysManagement/newUser', 
          query: {
            type: 1,
            branchId: item.branchId,
            branchName: item.branchName,
            userId: item.userId,
            userName: item.userName,
            roles: item.roles.map(item => item.roleId).join(','), // 角色ID
            rolesName: item.roles.map(item => item.roleName).join(',') // 角色名称
          }
        })
      },
      /**
       * 处理删除用户按钮
       */
      handleDelete(item) {
        this.$confirm('确认删除用户？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( () => {
          this.deleteUser(item)
        }).catch( () => {})
      },
      /**
       * 删除用户
       */
      deleteUser(item) {
        let form = {}
        form.userId = item.userId
        form.userName = item.userName
        form.branchId = item.branchId
        form.userState = item.userState
        form.roleId = this.spliceRole(item.roles)
        deleteUser(Qs.stringify(form)).then(res => {
          if (res.data.type == '200') {
            this.$message({
              message: res.data.message,
              type: 'success'
            })
          } else {
            this.$message({
              message: res.data.message,
              type: 'error'
            })
          }
          this.getUserList(false)
        }).catch(error => {
          console.log(error)
        })
      },
      /**
       * 重置密码
       */
      handleResetPassword(item) {
        this.$confirm('请确认是否重置该用户密码？', '温馨提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let form = {}
          form.userId = item.userId
          resetPassword(Qs.stringify(form)).then(res => {
            if(res.data.code == '200'){
                this.$notify({
                title: '重置密码成功',
                message: res.data.message,
                type: 'success',
                duration: 0
              })
            }else {
              this.$message({
              message: res.data.message,
              type: 'error'
          })
            }
          })
          .catch(error => {
            console.log(error)
          })
        }).catch( () => {})
      },
      /**
       * 拼接角色
       */
      spliceRole(list) {
        let id = ''
        for (let i=0; i<list.length; i++) {
          if (i == 0){
            id = list[i].roleId
          } else {
            id = id + ',' + list[i].roleId
          }
        }
        return id
      },
      /**
       * 是否有操作权限
       */
      hasPermission(item) {
        
        const roleIndex = this.$store.getters.roleIdx
        const roles = this.$store.getters.roles
        if (roles.length == 0) {
          return false
        }
        if(item.roles[0] && roles[roleIndex] && item.roles[0].roleId  == roles[roleIndex].roleId) {
          return false
        } else {
          return true
        } 
      }
      
    },
    filters: {
      userState(val) {
        if (val === null || val === ''){
          return ''
        }
        switch(val){
          case '0':
            return '正常'
          case '2':
            return '用户停用'
          default:
            return ''
        }
      }
    }
  }
</script>

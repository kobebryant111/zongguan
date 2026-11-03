<template>
  <div class="app-container">
    <!-- <page-header page-title="角色管理"/> -->
    <!-- role tree -->
    <tree-table :data="roleData" :columns="tableColumns" border expandAll>
      <el-table-column label="创建时间" prop="createTime" :formatter="dateFormat">
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="addRole(scope.row)">新增</el-button>
          <el-button type="text" size="mini" @click="alterRole(scope.row)" v-if="!(curRoleId == scope.row.roleId)">修改</el-button>
          <el-button type="text" size="mini" @click="deleteRole(scope.row)" v-if="!(curRoleId == scope.row.roleId)">删除</el-button>
          <el-button type="text" size="mini" @click="authPermission(scope.row)">权限管理</el-button>
        </template>
      </el-table-column>
    </tree-table>
    <!-- dialogForm -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="dialogForm" :rules="formRules" label-width="100px" label-position="right">
        <el-form-item label="角色名称" prop="roleName">
          <el-input 
            v-model="dialogForm.roleName"
            placeholder="请输入角色名称"
            clearable
            style="width: 400px;"
            maxlength="30"  
          ></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDesc">
          <el-input 
            v-model="dialogForm.roleDesc"
            placeholder="请输入角色描述"
            clearable
            style="width: 400px;" 
            maxlength="100" 
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleDialogConfirm">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style scoped>
.app-container .el-table--medium td{
  padding: 5px 0;
}
</style>

<script>
import treeTable from '@/components/TreeTable'
import pageHeader from '@/components/PageHeader'
import treeToArray from './treeTable/customEval'
import { fetchUserRoleTree, newRole, alterRole, deleteRole } from "@/api/sysManagement"
import commonUtils from '@/api/common'
const Qs = require('qs')
export default {
  name: 'roleManagement',
  components: { treeTable, pageHeader },
  data(){
    return {
      // tree table column
      tableColumns: [
        {
          text: '角色名称',
          value: 'roleName'
        },{
          text: '角色描述',
          value: 'roleDes',
          width: '200'
        },{
          text: '角色ID',
          value: 'roleId',
          width: '150'
        },{
          text: '创建人',
          value: 'createUser',
          width: '150'
        }
      ],
      roleData: [], // 角色树数据
      dialogForm: { // 弹框表单数据
        roleId: '',
        roleName: '',
        roleDesc: '',
        parentRoleId: ''
      }, 
      dialogTitle: '', // 弹框标题
      dialogType: 0, // 0 新增角色 1 修改角色
      dialogVisible: false, // 弹框显示
      formRules: { // 表单校验
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur'}
        ],
        roleDesc: [
          { required: true, message: '请输入角色描述', trigger: 'blur'}
        ]
      },
      curRoleId: ''
    }
  },
  created() {
    this.getRoleTree()
    
  },
  methods: {
    /**
     * 获取角色树
     */
    getRoleTree() {
      const roleIndex = this.$store.getters.roleIdx
      const roles = this.$store.getters.roles
      this.curRoleId = roles[roleIndex].roleId
      let query = {
        roleId: this.curRoleId
      }
      fetchUserRoleTree(query).then(res => {
        if(res.data.code == '200'){
            this.roleData = res.data.results
        }
      }).catch(error => {
        console.log(error)
      })
    },

    dateFormat(row,column){
      let str  = commonUtils.dateFormat(row.createTime);
      return str;
    },

    /**
     * 新增角色
     */
    addRole(row) {
      this.dialogForm.parentRoleId = row.roleId
      this.dialogForm.roleId = ''
      this.dialogForm.roleName = ''
      this.dialogForm.roleDesc = ''
      this.dialogTitle = '新增角色'
      this.dialogType = 0
      this.dialogVisible = true
    },
    /**
     * 删除角色
     */
    deleteRole(row) {
      this.$confirm('删除角色将导致角色下用户无效，请确认', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          roleId: row.roleId
        }
        deleteRole(data).then( res => {
          if(res.data.code == 200){
            this.$message({
            message: res.data.message,
            type: 'success'
          })
            this.getRoleTree()
          }else {
              this.$message({
              message: res.data.message,
              type: 'error'
            })
          }
        }).catch(error => {
          console.log(error)
        })
      }).catch(() => {})
    },
    /**
     * 修改角色
     */
    alterRole(row) {
      this.dialogForm.roleId = row.roleId
      this.dialogForm.roleName = row.roleName
      this.dialogForm.roleDesc = row.roleDes
      this.dialogTitle = '修改角色'
      this.dialogType = 1
      this.dialogVisible = true
    },
    /**
     * 跳转权限管理页面
     */
    authPermission(row) {
      this.$router.push({ path: '/sysManagement/menuPermission', query: { roleId: row.roleId, roleName: row.roleName,editable: true } })
    },
    /**
     * 弹框确认按钮
     */
    handleDialogConfirm() {
      if(this.dialogType == 0){ // 新增角色
        let data = {
          roleId: this.dialogForm.roleId,
          roleName: this.dialogForm.roleName,
          roleDes: this.dialogForm.roleDesc,
          parentRoleId: this.dialogForm.parentRoleId
        }
        let parame = data
        data = Qs.stringify(data)
        newRole(data).then(res => {
          if(res.data.code == '200'){
              parame.roleId = res.data.results;
              this.getRoleTree()
              this.$confirm('角色创建成功，是否对该角色进行权限设置？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '稍后设置',
              type: 'success'
            }).then(() => {
              this.dialogVisible = false
              this.authPermission(parame)
              // 显示角色分配弹框
            }).catch(() => {
              this.dialogVisible = false
              this.$message({
                message: '未设置权限角色将无法登陆内管',
                type: 'warning'
              })
            })
          }else{
            this.$message({
              message: res.data.message,
              type: 'error'
            })
          }
        }).catch(error => {
          console.log(error)
        })
      }else{ // 修改角色
        let data = {
          roleId: this.dialogForm.roleId,
          roleName: this.dialogForm.roleName,
          roleDes: this.dialogForm.roleDesc
        }
        data = Qs.stringify(data)
        alterRole(data).then(res => {
          if(res.data.code == '200'){
            this.dialogVisible = false
            this.$message({
              message: res.data.message,
              type: 'success'
            })
            this.getRoleTree()
          }
        }).catch(error => {
          console.log(error)
        })
      }
    }
  }
}
</script>




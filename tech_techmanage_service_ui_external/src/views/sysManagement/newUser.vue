<template>
<div class="app-container">
  <!-- newUserForm -->
  <el-form 
    ref="newForm" 
    :model="newForm" 
    :rules="newFormRules" 
    label-width="140px" 
    label-position="right">
    <el-form-item label="用户工号" prop="userId">
      <el-input 
      :disabled="editType == 1"
      v-model="newForm.userId"
      placeholder="请输入用户工号"
      clearable
      style="width: 180px;" 
      maxlength="8"
      @blur="reCheckUserId"></el-input>
    </el-form-item>
    <el-form-item label="用户姓名" prop="userName">
      <el-input 
      v-model="newForm.userName"
      placeholder="用户姓名"
      :disabled="disabled"
      style="width: 180px;" 
      maxlength="20"></el-input>
    </el-form-item>
    <el-form-item label="机构" prop="branchId">
      <el-dialog
        title="选择机构"
        :visible.sync="branchIdDialog">
        <el-tree 
          height="400"
          :data="branchData" 
          :props="branchProp" 
          show-checkbox 
          check-strictly 
          :default-checked-keys="[newForm.branchId]"
          ref="tree2"
          node-key="deptId">
        </el-tree>
        <span slot="footer" class="dialog-footer">
          <el-button size="mini" type="text" @click="branchIdDialog = false">取消</el-button>
          <el-button size="mini" type="text" @click="confirmBranchId">确认</el-button>
        </span>
      </el-dialog>
      <el-button @click="branchIdDialog = true">{{ branchName }}</el-button>
    </el-form-item>
    <el-form-item label="角色" prop="roleId">
      <el-dialog
        title="选择角色"
        :visible.sync="roleIdDialog">
        <el-tree 
          :data="roleData" 
          :props="roleProp" 
          show-checkbox 
          check-strictly 
          default-expand-all
          :default-checked-keys="newForm.roleId.split(',')"
          accordion
          ref="tree"
          node-key="roleId">
        </el-tree>
        <span slot="footer" class="dialog-footer">
          <el-button size="mini" type="text" @click="roleIdDialog = false">取消</el-button>
          <el-button size="mini" type="text" @click="confirmRoleId">确认</el-button>
        </span>
      </el-dialog>
      <el-button @click="roleIdDialog = true">{{ roleName }}</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleConfirm">确认提交</el-button>
      <el-button @click="returnBack">返回</el-button>
    </el-form-item>
  </el-form>
</div>
</template>

<script>
import { fetchAllRoles, fetchBranch, createUser, modifyUser, checkUserId } from '@/api/sysManagement'
const Qs = require('qs')
export default {
  data() {
    let validatorUserId = (rule, value, callback) => {
      if (value) {
        if (this.newForm.userName) {
          callback();
        } else if(this.some){
          callback(new Error("该工号已存在！"));
        }
      } else {
        this.newForm.userName = ''
        callback(new Error("请输入用户工号"));
      }
    };
    return {
      editType: 0, // 0新建 1修改
      newForm: {
        userId: '',
        userName: '',
        branchId: '',
        roleId: '',
        userState: '',
      },
      some : false,
      disabled : false,
      newFormRules: {
        userId: [
          {required: true, validator: validatorUserId, trigger: 'blur'}
        ]
      },
      roleIdDialog: false,
      branchIdDialog: false,
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
      roleName: '请选择角色',
      branchName: '请选择机构',
    }
  },
  created() {
    // 编辑类型
    this.editType = this.$route.query.type
    if (this.editType == 1) {
      // 反显赋值
      this.newForm.userId = this.$route.query.userId
      this.newForm.userName = this.$route.query.userName
      this.newForm.branchId = this.$route.query.branchId
      // this.spliceRole(this.$route.query.roles)
      this.newForm.roleId = this.$route.query.roles
      this.roleName = this.$route.query.rolesName
      this.branchName = this.$route.query.branchName
      // TODO: 反显机构和角色
    }
    this.getRoleTree()
    this.getBranch()
  },
  methods: {
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
     * 获取机构数据
     */
    getBranch() {
      fetchBranch().then(res => {
        this.branchData.push(res.data.results)
      }).catch( error => {
        console.log(error)
      })
    },
    /**
     * 确认选择的角色
     */
    confirmRoleId() {
      let chooseRoles = this.$refs.tree.getCheckedNodes()
      if(chooseRoles.length > 3 || chooseRoles.length < 1) {
        this.$message({
          message: '请选择至少1个角色，但不多于3个',
          type: 'warning'
        })
        return false
      }
      // this.roleName = chooseRoles[0].roleName
      // this.newForm.roleId = chooseRoles[0].roleId
      this.spliceRole(chooseRoles)
      this.roleIdDialog = false
    },
    /**
     * 拼接角色
     */
    spliceRole(list) {
      let name = ''
      let id = ''
      for (let i=0; i<list.length; i++) {
        if (i == 0){
          name = list[i].roleName
          id = list[i].roleId
        } else {
          name = name + ',' + list[i].roleName
          id = id + ',' + list[i].roleId
        }
      }
      this.roleName = name
      this.newForm.roleId = id
    },
    /**
     * 确认选择的机构
     */
    confirmBranchId() {
      let chooseRoles = this.$refs.tree2.getCheckedNodes()
      if(chooseRoles.length > 1) {
        this.$message({
          message: '选择一个机构',
          type: 'warning'
        })
        return false
      }
      this.branchName = chooseRoles[0] ? chooseRoles[0].deptName : '请选择机构'
      this.newForm.branchId = chooseRoles[0] ? chooseRoles[0].deptId : ''
      this.branchIdDialog = false
    },
    /**
     * 处理提交按钮
     */
    handleConfirm() {
      // 表单校验
      this.$refs['newForm'].validate((valid) => {
        if(valid) {
          if(this.newForm.roleId == '' || this.newForm.branchId == ''){
            this.$message({
              message: '请选择角色和机构',
              type: 'warning'
            })
            return false
          }else{
            if (this.editType == 0) {
              this.$confirm('确认创建用户？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then( () => {
                this.submit()
              }).catch( () => {})
            } else if (this.editType == 1) {
              this.$confirm('确认修改用户信息？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then( () => {
                this.modify()
              }).catch( () => {})
            }
            
          }
        }
      })
    },
    /**
     * 返回按钮
     */
    returnBack() {
      this.$store.dispatch('delView', this.$route)
      this.$router.push({ path: '/sysManagement/userManagement'})
    },
    /**
     * 提交信息
     */
    submit(){
      this.newForm.userState = 0
      console.log("canshu : " + Qs.stringify(this.newForm));
      createUser(Qs.stringify(this.newForm)).then(res => {
        console.log(res)
        if (res.data.type == '0') {
          this.$message({
            message: '用户创建成功',
            type: 'success'
          })
        } else if (res.data.type == '1') {
          this.$message({
            message: '提交用户编辑申请成功，需要下级授权通过',
            type: 'success'
          })
        }
        this.returnBack()
      }).catch(error => {
        console.log(error)
      })
    },
    /**
     * 修改信息
     */
    modify() {
      this.newForm.userState = 0
      modifyUser(Qs.stringify(this.newForm)).then(res => {
        this.$message({
          message: '修改成功',
          type: 'success'
        })
        this.returnBack()
      }).catch(error => {
        console.log(error)
      })
    },
    /**
     * 核查用户ID是否有效
     */
    reCheckUserId () {
      this.some = false
      this.newForm.userName = ''
      if (this.newForm.userId) {
        let param = {
          userId: this.newForm.userId
        }
        checkUserId(param).then(response => {
          if(response.data.code == '200'){
            if (response.data.results) {
              this.newForm.userName = response.data.results
              this.disabled = true
            }else {
              this.disabled = false
            }
          }else {
            this.some = true
            this.disabled = true
          }
          this.$refs.newForm.validateField('userId')
        })
      }
    }
  }
}
</script>


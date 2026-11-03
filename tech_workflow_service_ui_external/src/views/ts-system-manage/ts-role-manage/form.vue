<template>
  <div>
    <el-form ref="roleFormRef" :disabled="model.formDisabled" :model="model" :rules="rules" label-width="80px">
      <el-form-item label="上级角色" prop="parentRoleName">
        <el-input v-model="model.parentRoleName" placeholder="请输入上级角色" :disabled="true">
        </el-input>
      </el-form-item>
      <el-form-item label="名称" prop="roleName">
        <el-input v-model="model.roleName" placeholder="请输入请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="statusCd">
        <ts-multi-select v-model="model.statusCd" :params="StCdType"></ts-multi-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="model.sort" placeholder="请输入排序">
        </el-input>
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input v-model="model.remark" placeholder="请输入描述">
        </el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  components: {},
  name: 'RoleForm',
  data() {
    return {
      StCdType: { key: 'StCd', group: '1', width: '100%', placeholder: '请选择状态' },
      model: {
        parentRoleCode: '',
        parentRoleName: '', // 上级角色
        roleName: '', // 名称
        statusCd: '', // 状态(0=无效，1=有效)
        sort: '', // 排序
        remark: '', // 描述
        clntendId: ''
      },
      rules: {
        parentRoleName: [
          { required: true, message: '请输入上级角色', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        sort: [
          { required: false, message: '请输入排序', trigger: 'blur' },
          { message: '请输入整数格式', pattern: /^[0-9]{1,9}$/ }
        ],
        remark: [
          { required: false, message: '请输入描述', trigger: 'blur' }
        ],
        statusCd: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ]
      }
    }
  },
  methods: {}
}
</script>

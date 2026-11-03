<template>
  <div>
    <el-form ref="depFormRef" :model="model" :rules="rules" label-width="120px">
      <el-form-item label="上级部门编号" prop="parentDeptCode">
        <el-input v-model="model.parentDeptCode" placeholder="请输入上级部门编号" :disabled="true">
        </el-input>
      </el-form-item>
      <el-form-item label="上级部门" prop="parentDeptName">
        <el-input v-model="model.parentDeptName" placeholder="请输入上级部门" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="部门编号" prop="deptCode">
        <el-input v-model="model.deptCode" placeholder="自动生成" :disabled="true">
        </el-input>
      </el-form-item>
      <el-form-item label="部门名称" prop="deptName">
        <el-input v-model="model.deptName" placeholder="请输入部门名称">
        </el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  components: {},
  name: 'Form',
  data() {
    return {
      model: {
        parentDeptCode: '',
        parentDeptName: '',
        deptCode: '',
        deptName: '',
        statusCd: '1'
      },
      rules: {
        parentDeptCode: [
          { required: true, message: '请输入上级部门编号', trigger: 'blur' }
        ],
        parentDeptName: [
          { required: true, message: '请输入上级部门', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '请输入部门名称', trigger: 'blur' },
          { min: 3, max: 30, message: '长度在 3 到 30 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {}
}
</script>


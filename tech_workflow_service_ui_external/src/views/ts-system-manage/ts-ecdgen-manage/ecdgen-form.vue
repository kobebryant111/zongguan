<template>
  <div>
      <el-form ref="ecdgenFormRef" :rules="rules" :model="model" label-width="120px">
        <el-form-item label="编码类型编号" prop="ecdTpId">
          <el-input v-model="model.ecdTpId" placeholder="请输入编码类型编号" clearable>
          </el-input>
        </el-form-item>
        <el-form-item label="编码类型名称" prop="ecdTpNm">
          <el-input v-model="model.ecdTpNm" placeholder="请输入编码类型名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="stCd">
          <ts-multi-select v-model="model.stCd" placeholder="请选择状态" :disabled="model.formDisabled" :params="ecdgenStCd" ></ts-multi-select>
        </el-form-item>
        <el-form-item label="备注" prop="rmrk">
          <el-input v-model="model.rmrk" type="textarea" placeholder="请输入备注"></el-input>
        </el-form-item>
      </el-form>
  </div>
</template>

<script>

export default {
  name: 'EcdgenForm',
  data() {
    return {
      ecdgenStCd: { key: 'StCd', group: '1', width: '100%' },
      model: {
        id: '',
        ecdTpId: '',
        ecdTpNm: '',
        stCd: '',
        rmrk: '',
        formDisabled: false
      },
      rules: {
        ecdTpId: [
          { required: true, message: '请输入编码类型编号', trigger: 'blur' }
        ],
        ecdTpNm: [
          { required: true, message: '请输入编码类型名称', trigger: 'blur' }
        ],
        stCd: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ],
        rmrk: [
          { required: true, message: '请输入备注', trigger: 'change' }
        ]
      }
    }
  },
  methods: {

  }
}
</script>

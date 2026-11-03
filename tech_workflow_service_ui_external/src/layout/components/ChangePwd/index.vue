<!--
 * @Author: liuyang
 * @Date: 2020-04-16 15:49
 * @LastEditors  : liuyang
 * @LastEditTime : 2020-04-16 15:49
 * @Description:
-->
<template>
  <div>
    <el-form ref="pwdForm" :model="model" :rules="rules" label-width="100px">
      <el-form-item prop="password" label="原密码">
        <el-input v-model="model.password" type="password"/>
      </el-form-item>
      <el-form-item prop="newPwd" label="新密码">
        <el-input v-model="model.newPwd" type="password"/>
      </el-form-item>
      <el-form-item prop="renewPwd" label="确认新密码">
        <el-input v-model="model.renewPwd" type="password"/>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Form',
  components: {},
  data() {
    return {
      // 表单
      model: {
        password: '',
        newPwd: '',
        renewPwd: ''
      },
      rules: {
        password: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        newPwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: this.$tsVue.tansunFormValidate('password'), trigger: 'blur' }],
        renewPwd: [
          { required: true, message: '请输入确认新密码', trigger: 'blur' },
          { validator: this.$tsVue.tansunFormValidate('password'), trigger: 'blur' },
          {
            message: '两次输入密码不一致', validator: this.$tsVue.tansunFormValidate('equals', (callback) => {
              callback(this.model.newPwd)
            }), trigger: 'blur'
          }
        ]
      }
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {}
}
</script>

<style lang=scss scoped>

</style>


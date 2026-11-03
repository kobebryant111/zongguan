<template>
  <div class="form-container">
    <el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" label-width="auto">
      <el-form-item label="密码" prop="passWord">
        <el-input v-model="pwdForm.passWord" type="password" show-password placeholder="请输入密码"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassWord">
        <el-input v-model="pwdForm.confirmPassWord" type="password" show-password placeholder="请确认次密码"/>
      </el-form-item>
      <verificationCode :pwd-form="pwdForm" :pwd-title="pwdTitle"/>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <div style="text-align: center">
        <el-button type="primary" @click="handleVerify">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </span>
  </div>
</template>
<script>
import { setAndVerify } from '@/api/rdmanager/rdPasswordMage'
import verificationCode from '@/views/researchManager/manager/current/verificationCode.vue'
export default {
  name: 'SetSecondPassword',
  components: { verificationCode },
  data() {
    var checkRepPassword = (rule, confirmPassword, callback) => {
      if (!(this.pwdForm.passWord === this.pwdForm.confirmPassWord)) {
        callback(('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    const checkNewPassword = (rule, passWord, callback) => {
      if (passWord.length < 8) {
        callback('密码不能少于8位')
      }

      // 是数字
      var isDigit = /^.*[0-9]+.*/
      // isLowerCase 小写字母
      var isLowerCase = /^.*[a-z]+.*/
      // isUpperCase 大写字母
      var isUpperCase = /^.*[A-Z]+.*/
      // 特殊字符
      var regEx = /^.*[^a-zA-Z0-9]+.*/
      // 记录匹配的次数
      let containtype = 0
      if (isDigit.test(passWord)) {
        containtype++
      }
      if (isLowerCase.test(passWord)) {
        containtype++
      }
      if (isUpperCase.test(passWord)) {
        containtype++
      }
      if (regEx.test(passWord)) {
        containtype++
      }
      if (containtype < 3) {
        callback('密码至少包含以下三种：大/小写字母，数字，特殊字符')
      } else {
        callback()
      }
    }
    return {
      pwdForm: {},
      timeLeft: 0,
      pwdTitle: '验证码',
      disable: false,
      timer: null,
      pwdRules: {
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { required: true, validator: checkNewPassword, trigger: 'blur' }
        ],
        confirmPassWord: [
          { required: true, message: '请确认次密码', trigger: 'blur' },
          { required: true, validator: checkRepPassword, trigger: 'blur' }
        ],
        verificationCode: [{ required: true, message: '请输入工作平台验证码', trigger: 'blur' }]
      }
    }
  },

  computed: {
  },
  methods: {
    /**
     * 确认密码
     */
    handleVerify() {
      const params = { ...this.pwdForm }
      this.$refs['pwdForm'].validate((valid) => {
        if (valid) {
          setAndVerify(params).then(res => {
            if (res.data.status === 200) {
              this.$message.success('密码设置成功')
              this.cancelDialog()
            } else {
              this.$message.error(res.data.message)
            }
          })
        }
      })
    },
    // 父组件关闭弹窗
    clearPwdForm() {
      this.pwdForm = {}
    },
    // 取消弹框
    cancelDialog() {
      this.pwdForm = {}
      this.$emit('cancelDialog')
    }
  }
}
</script>
<style scoped>
.form-container {
  width: 90%;
  margin: 0 auto; /* 水平居中 */
}
.form-container .el-form-item {
  width: 100%;
}
.form-container .el-input {
  width: 100%;
}
.blue-border-btn {
  border-color: #409eff;
}
</style>

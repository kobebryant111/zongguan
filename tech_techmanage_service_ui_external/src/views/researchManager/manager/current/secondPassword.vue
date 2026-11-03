<template>
  <div class="form-container">
    <el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" label-width="auto">
      <div >
        <el-form-item prop="passWord">
          <el-input v-model="pwdForm.passWord" placeholder="请输入独立二次密码" type="password" show-password style="width: 89%"/>
        </el-form-item>
        <verificationCode :pwd-form="pwdForm" :pwd-title="pwdTitle"/>
        <el-form-item class="bottom-align-row" style="margin-left: 25%;">
          <el-row :gutter="20" class="bottom-align-row">
            <el-col :span="10">
              <el-button
                class="blue-border"
                @click="handleVerify"
              >
                验证
              </el-button>
            </el-col>
            <el-col :span="10">
              <el-popover content="请联系管理员重置密码" placement="bottom" width="200" trigger="hover">
                <el-button
                  slot="reference"
                  type="text"
                  size="mini"
                  class="text-black-btn"
                >
                  忘记密码？
                </el-button>
              </el-popover>
            </el-col>
          </el-row>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>
<script>
import { testAndVerify } from '@/api/rdmanager/rdPasswordMage'
import verificationCode from '@/views/researchManager/manager/current/verificationCode.vue'
export default {
  name: 'SecondPassword',
  components: { verificationCode },
  data() {
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
      pwdTitle: '',
      systemParams: {},
      pwdRules: {
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { required: true, validator: checkNewPassword, trigger: 'blur' }
        ],
        verificationCode: [{ required: true, message: '请输入工作平台验证码', trigger: 'blur' }]
      }
    }
  },
  computed: {
  },
  methods: {

    handleVerify() {
      const params = { ...this.pwdForm }
      this.$refs['pwdForm'].validate((valid) => {
        if (valid) {
          testAndVerify(params).then(res => {
            if (res.data.status === 200) {
              this.cancelDialog()
              this.$emit('scsVerify', this.systemParams)
              // this.$message.success(res.data.message)
            } else {
              this.$message(
                {
                  message: res.data.message,
                  type: 'warning'
                }
              )
            }
          })
        }
      })
    },
    // 父组件关闭弹窗
    clearPwdForm() {
      this.pwdForm = {}
    },
    sysPparams(params) {
      this.systemParams = params
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
  width: 100%;
  border-color: #409eff;
}
.text-black-btn{
  color: #000;
  text-decoration: underline
}
.blue-border{
  width: 100%;
  border-color: #409eff;
}

</style>

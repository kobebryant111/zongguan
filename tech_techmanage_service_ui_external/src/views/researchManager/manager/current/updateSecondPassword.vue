<!-- 修改密码-->
<template>
  <div class="form-container">
    <el-alert :closable="false" type="info">
      <div>
        <div class="alert-div_font">1、密码不能少于8位</div>
        <div class="alert-div_font">2、密码至少包含以下类型中的三种:大写字母/小写字母/数字/特殊字符</div>
      </div>
      <div slot="title">
        <i class="el-icon-info"/>
        <strong>密码规则</strong>
      </div>
    </el-alert>
    <div class="updatepwd-form">
      <el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" label-width="100px">
        <el-form-item label="原密码" prop="password">
          <el-input v-model="pwdForm.password" type="password" placeholder="请输入原密码" show-password/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="pwdForm.newPassword" type="password" placeholder="请输入新密码" show-password/>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input v-model="pwdForm.confirmPassword" type="password" placeholder="请确认新密码" show-password/>
        </el-form-item>
        <verificationCode :pwd-form="pwdForm" :pwd-title="pwdTitle"/>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <div style="text-align: center">
        <el-button type="primary" @click="updatePwd">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </span>
  </div>
</template>

<script>

import verificationCode from '@/views/researchManager/manager/current/verificationCode.vue'
import { updatePwdAndVerify } from '@/api/rdmanager/rdPasswordMage'
export default {
  name: 'UpdateSecondPassword',
  components: { verificationCode },
  data() {
    var checkRepPassword = (rule, confirmPassword, callback) => {
      // console.log(rule, confirmPassword, callback)
      if (!(confirmPassword === this.pwdForm.newPassword)) {
        callback(('两次输入的密码不一致'))
      } else {
        callback()
      }
    }

    const checkNewPassword = (rule, newPassword, callback) => {
      if (newPassword.length < 8) {
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
      if (isDigit.test(newPassword)) {
        containtype++
      }
      if (isLowerCase.test(newPassword)) {
        containtype++
      }
      if (isUpperCase.test(newPassword)) {
        containtype++
      }
      if (regEx.test(newPassword)) {
        containtype++
      }
      if (containtype < 3) {
        callback('密码至少包含以下三种：大/小写字母，数字，特殊字符')
      } else {
        callback()
      }
    }

    return {
      pwdTitle: '验证码',
      pwdForm: {
        userid: '',
        password: '', // 原密码
        newPassword: '', // 新密码
        confirmPassword: '' // 确认新密码
      },
      pwdRules: {
        password: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '新输入新密码', trigger: 'blur' },
          { required: true, validator: checkNewPassword, trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请重复新密码', trigger: 'blur' },
          { required: true, validator: checkRepPassword, trigger: 'blur' }
        ],
        verificationCode: [
          { required: true, message: '请输入工作平台验证码', trigger: 'blur' }
        ]
      },
      dialogVisible: false // 弹框
    }
  },
  computed: {
  },
  methods: {
    // 确认更改
    updatePwd() {
      if (this.pwdForm.password === this.pwdForm.newPassword) {
        return this.$message.warning('新密码不能与原密码相同')
      }
      this.$refs['pwdForm'].validate((valid) => {
        if (valid) {
          const params = { ...this.pwdForm }
          updatePwdAndVerify(params).then(res => {
            if (res.data.status === 200) {
              this.cancelDialog()
              this.$message(
                {
                  message: res.data.message,
                  type: 'success'
                }
              )
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
    // 取消弹框
    cancelDialog() {
      this.pwdForm = {}
      this.$emit('cancelDialog')
    }
  }

}

</script>

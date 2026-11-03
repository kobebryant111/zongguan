<template>
  <div>
    <el-dialog
      :modal="false"
      :visible.sync="modalVisible"
      :title="titleName"
      :before-close="handleClose"
      width="25%"
      center
    >
      <div class="form-container">
        <el-form ref="form" :model="form" :rules="rules" label-width="auto">
          <el-form-item label="名称" prop="sysName">
            <el-input v-model="form.sysName" maxlength="32" show-word-limit placeholder="请输入名称"/>
          </el-form-item>
          <el-form-item label="密码" prop="psd">
            <el-input v-model="form.psd" type="password" show-password placeholder="请输入密码"/>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <div style="text-align: center">
            <el-button type="primary" @click="submitBtn">确 定</el-button>
          </div>
        </span>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { generateClientKey, deriveAesKeyAndDecrypt, encryptParams } from '@/api/rdmanager/ecdheutil'
import { addecdhefirst, addSysPsdList } from '@/api/rdmanager/rdPasswordMage'

export default {
  name: 'AddSysPsdLit',
  props: {
    modalVisible: {
      type: Boolean,
      default: false //  布尔类型的默认值 false
    },
    systemParams: {
      type: [Object, String], // 允许 Object 或 String
      default: () => ({})
    }
  },
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
      titleName: '',
      form: {
        sysName: '',
        psd: ''
      },
      rules: {
        sysName: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        psd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { required: true, validator: checkNewPassword, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitBtn() {
      this.$refs['form'].validate(async(valid) => {
        const param = { ...this.form }
        param.systemNo = this.systemParams.systemNo
        // debugger
        if (valid) {
          param.rulesTorf = 'Y'
        } else {
          param.rulesTorf = 'N'
        }
        const params = await generateClientKey()
        addecdhefirst({ clientPubKey: params.publicKey }).then(async(res) => {
          if (res.data.status === 200) {
            const aesKey = await deriveAesKeyAndDecrypt(params.privateKey, res.data.results)
            const data = await encryptParams(aesKey, param)
            addSysPsdList(data).then(async(res) => {
              if (res.data.status === 200) {
                this.form = {}
                this.$emit('addSysPsdOk')
                this.$emit('update:modalVisible', false)
              } else {
                this.subfail('', res.data.message)
              }
            })
          } else {
            this.subfail('', res.data.message)
          }
        })
        // console.log(params, '/*****')
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？').then(_ => {
        this.form = {}
        this.$emit('update:modalVisible', false)
      }).catch(_ => {})
    },
    subfail(res, msg) {
      this.$message({
        message: msg,
        type: 'error'
      })
    }
  }
}
</script>

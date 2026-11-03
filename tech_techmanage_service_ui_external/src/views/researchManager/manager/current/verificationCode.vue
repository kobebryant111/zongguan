<template>
  <div v-if="verifyCode">
    <el-form-item :label="pwdTitle" prop="verificationCode">
      <el-row :gutter="10">
        <el-col :span="14">
          <el-input v-model="pwdForm.verificationCode" placeholder="请输入工作平台验证码"/>
        </el-col>
        <el-col :span="3">
          <el-button
            :disabled="disable"
            class="blue-border-btn"
            @click="getVerfication"
          >
            {{ buttonText }}
          </el-button>
        </el-col>
      </el-row>
    </el-form-item>
  </div>
</template>

<script>
import common from '@/api/common'
import request from '@/utils/request'
import { getVerficationCode } from '@/api/rdmanager/rdPasswordMage'
export default {
  name: 'VerificationCode',
  props: {
    pwdForm: {
      type: Object,
      required: true
    },
    pwdTitle: {
      type: [Object, String], // 允许 Object 或 String
      default: () => ({})
    }
  },
  data() {
    return {
      verificationCode: '',
      timeLeft: 0,
      disable: false,
      verifyCode: false,
      timer: null
    }
  },
  computed: {
    buttonText() {
      return this.timeLeft > 0 ? `${this.timeLeft}s` : '获取验证码'
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      const data = common.paramsFormat()
      request({
        url: '/ppmOperatorKey/selectverifyCode',
        method: 'post',
        data: data
      }).then(res => {
        // debugger
        if (!res.data.results) {
          this.verifyCode = false
        } else if (res.data.results === 'N') {
          this.verifyCode = false
        } else {
          this.verifyCode = true
        }
      })
    },

    getVerfication() {
      if (this.disable) return
      this.startCountdown()
    },

    startCountdown() {
      getVerficationCode().then(res => {
        if (res.data.status === 200) {
          this.disable = true
          this.timeLeft = 60
          // this.timeLeft = 0
          this.timer = setInterval(() => {
            this.timeLeft--
            if (this.timeLeft <= 0) {
              clearInterval(this.timer)
              this.timer = null
              this.disable = false
            }
          }, 1000)

          this.$message(
            {
              message: res.data.message,
              type: 'success'
            }
          )
        }
      })
    }
  }
}
</script>

<style>

</style>

<template>
  <div>
    <el-form ref="formRef" :model="model" :rules="rules" :disabled="disabled" class="single-col-form" label-width="auto">
      <el-form-item label="登录名" prop="loginName">
        <el-input v-model="model.loginName" :disabled="editStatus(data)"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="realname">
        <el-input v-model="model.realname"></el-input>
      </el-form-item>
      <el-form-item v-if="showStatus(data)" label="密码" prop="password">
        <el-input v-model="model.password" autocomplete="new-password" show-password></el-input>
      </el-form-item>
      <el-form-item v-if="showStatus(data)" label="确认密码" prop="repassword">
        <el-input v-model="model.repassword" autocomplete="new-password" show-password></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sexCd">
        <ts-multi-select v-model="model.sexCd" placeholder="请选择性别" :params="gndParams"></ts-multi-select>
      </el-form-item>
      <el-form-item label="手机号" prop="telephone">
        <el-input v-model="model.telephone"></el-input>
      </el-form-item>
      <!-- <el-form-item label="状态" prop="select">
        <ts-multi-select v-model="model.select" :params="selectParam"></ts-multi-select>
      </el-form-item> -->
    </el-form>

  </div>
</template>

<script>

export default {
  name: 'UserInfomation',
  props: {
    disabled: { // 接收只读属性，当最后一步生成概览视图会用到
      type: Boolean,
      default: false
    },
    data: { // 接收组件间通信容器的参数data
      type: Object
    },
    callback: { // 自定义按钮时的回调，prevStep和nextStep
      type: Object
    }
  },
  data() {
    return {
      selectParam: { key: 'ArticleStCd' },
      gndParams: { key: 'Gnd', width: '100%' },
      model: {
        loginName: '',
        realname: '',
        password: '',
        repassword: '',
        sexCd: '',
        telephone: '',
        buttonType: 'saveBizInf',
        select: 0
      },
      rules: {
        loginName: [
          { required: true, message: '请输入登录名', trigger: 'blur' },
          { validator: this.$tsVue.tansunFormValidate('noSpecialStr') },
          { max: 20, message: '登录名长度不能超过20个字符', trigger: 'blur' }
        ],
        realname: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { validator: this.$tsVue.tansunFormValidate('noSpecialStr') },
          { max: 20, message: '登录名长度不能超过20个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { trigger: 'blur', validator: this.$tsVue.tansunFormValidate('password') }
        ],
        repassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          {
            message: '两次输入密码不一致', validator: this.$tsVue.tansunFormValidate('equals', (callback) => {
              callback(this.model.password)
            }), trigger: 'blur'
          }
        ],
        sexCd: [
          { required: true, message: '请选择性别' }
        ],
        telephone: [
          { trigger: 'blur', validator: this.$tsVue.tansunFormValidate('mobile') }
        ]
      }
    }
  },
  created() {

  },
  mounted() {
    this.initData()
  },
  methods: {
    editStatus(data) {
      const { viewType } = this.$route.query
      const { userNum } = data
      if (userNum || viewType === 'edit') return true
      return false
    },

    showStatus(data) {
      const { viewType } = this.$route.query
      const { userNum } = data
      if (userNum) return false
      if (viewType === 'add') return true
    },

    testNextStep() {
      this.callback.nextStep && this.callback.nextStep()
    },

    initData() {
      const { viewType, userNum } = this.$route.query

      if (viewType !== 'add') {
        const params = {
          userNum
        }
        this.$request({
          url: this.$api.fetchUsermg,
          data: params
        }).then(res => {
          const { data } = res
          this.model = Object.assign(this.model, data)
        })
      }
    },

    // 命名为'savePage'的方法，在步骤组件的保存按钮点击时会被调用
    savePage() {
      const userNum = this.data.userNum
      const { viewType } = this.$route.query

      // 新增用户后再返回上一步有userName, 这时候跳下一步不做保存
      if (viewType === 'add' && userNum) {
        this.$emit('next')
        return false
      }

      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.$api.usermgSave,
            data: this.model
          }).then(res => {
            // console.log('savestep res:', res)
            this.$message({
              type: 'success',
              message: '保存成功!'
            })
            this.$emit('update', res.data) // 通过$emit('update',{})更新data容器
            this.$emit('next') // 通过$emit('next')通知父组件调到下一步, 当类型为修改'edit'可不用触发
          })
        } else {
          console.log('handleConfirm error submit!!')
          return false
        }
      })
    }
  }
}
</script>

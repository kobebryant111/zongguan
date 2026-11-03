<template>
  <div class="login-container" v-if="!micro">
    <!--<h4 class="login-container-title">天阳前端统一开发平台</h4>-->
    <!--  <h4 class="login-container-title" style="top:0"><img class="pic-tansun-img" src="@/assets/images/login/loginTitle.png"></h4>-->
    <div class="login-container-title">
    </div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" label-position="left">
      <div class="title-container">
        <h3 class="title">账户登录</h3>
      </div>

      <el-form-item prop="tenantName">
      <span class="svg-container">
          <ts-svg-icon icon="user" />
      </span>
        <el-input
            ref="username"
            v-model="loginForm.extraData.tenantName"
            placeholder="租户"
            name="tenantName"
            type="text"
            autocomplete="on"
        />
      </el-form-item>

      <el-form-item prop="username">
      <span class="svg-container">
          <ts-svg-icon icon="user" />
      </span>
        <el-input
            ref="username"
            v-model="loginForm.username"
            placeholder="请输入您的用户名"
            name="username"
            type="text"
            autocomplete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
      <span class="svg-container">
        <ts-svg-icon icon="password" />
      </span>
        <el-input
            :key="passwordType"
            ref="password"
            v-model="loginForm.password"
            :type="passwordType"
            placeholder="请输入您的密码"
            name="password"
            autocomplete="on"
            @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
        <ts-svg-icon :icon="passwordType === 'password' ? 'eye' : 'eye-open'" />
      </span>
      </el-form-item>
      <div style="margin: -10px 0 10px 5px">
        <el-checkbox v-model="loginForm.remember">记住密码</el-checkbox>
      </div>
      <el-button type="primary" style="width:100%;height: 34px;" @click.native.prevent="handleLogin">登录</el-button>
    </el-form>
  </div>
</template>

<script>
    import Cookies from 'js-cookie'
    import { JSEncrypt } from 'jsencrypt'
    import { getPublicKey, fetchInfo } from '@/api/login'
    import defaultSettings from '@/plugins/settings'
    const sessionMode = defaultSettings.enableSession

    export default {
        name: 'Login',
        components: {},
        data() {
            return {
                loginForm: {
                    extraData: {
                        tenantName: ''
                    },
                    username: '',
                    password: '',
                    captcha: '111',
                    msgAuthCode: '111111',
                    remember:false,
                },
                loginRules: {
                    /* username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePassword }] */
                },
                loading: false,
                passwordType: 'password',
                redirect: undefined,

            }
        },
        watch: {
            $route: {
                handler: function(route) {
                    this.redirect = route.query && route.query.redirect
                },
                immediate: true
            }
        },
        mounted() {
            if (sessionMode) { this.resetUserSession() }
            let loginInfo = Cookies.get('loginInfo')
            if(loginInfo){
                this.loginForm = JSON.parse(decodeURIComponent(atob(loginInfo)))
                if(!this.loginForm.remember){
                    this.loginForm.password = ''
                }
            }
        },
        methods: {
            resetUserSession() {
                fetchInfo().then({}).catch(() => {
                    this.$store.dispatch('user/resetSession').then(() => {
                    })
                })
            },
            showPwd() {
                if (this.passwordType === 'password') {
                    this.passwordType = ''
                } else {
                    this.passwordType = 'password'
                }
                this.$nextTick(() => {
                    this.$refs.password.focus()
                })
            },
            handleLogin() {

                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        this.loading = true
                        this.$store.dispatch('user/login', this.loginForm).then(() => {
                            //保存登录信息
                            let loginInfo = btoa(encodeURIComponent(JSON.stringify(this.loginForm)))
                            Cookies.set('loginInfo',loginInfo)
                            // 跳转到首页
                            console.log('this.redirect', this.redirect)
                            // this.$router.push({ path: this.redirect || '/' })
                            this.$router.push({ path: '/', query: { part: 'home' }})
                            this.loading = false
                        }).catch(() => {
                            this.loading = false
                        })
                    } else {
                        console.log('error submit!!')
                        return false
                    }
                })
            }
        }
    }
</script>

<style lang="scss">
  /* 修复input 背景不协调 和光标变色 */
  /* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

  $bg:#ededed;
  $light_gray:#fff;
  $cursor: #242b40;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
      color: $cursor;
    }
  }

  /* reset element-ui css */
  .login-container {
    &-title {
      position: absolute;
      //top: 20%;
      //width: 100%;
      //text-align: center;
      color: #fff;
      letter-spacing: 5px;
      font-size: 32px;
      font-weight: normal;

      top:10px;
      width: 50%;
      display: flex;
      align-items: center;
    }

    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;

      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-button {
      border-radius: 6px;
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: #ededed;
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style lang="scss" scoped>
  $bg:#567086;
  $dark_gray:#333;
  $light_gray:#cccacd;

  .login-container {
    width: 100%;
    height: 100vh;
    background: url('../../assets/images/login/login_bg.jpg') center no-repeat;
    background-size: cover;
    // opacity: 0.9;
    overflow: hidden;

    .login-form {
      position: absolute;
      top: 30%;
      left: 50%;
      width: 340px;
      height: 375px;
      max-width: 100%;
      padding: 30px;
      margin: 0 auto;
      margin-left: -170px;
      overflow: hidden;
      border-radius: 10px;
      background: #ffffff;
    }

    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;

      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .svg-container {
      padding: 0px 5px 0px 15px;
      //color: #3396FE;
      color: #ff8d00;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;

      .title {
        position: relative;
        font-size: 22px;
        color: $dark_gray;
        margin: 0px auto 30px auto;
        text-align: center;
        font-weight: normal;
        &:after {
          content: '';
          position: absolute;
          left: 50%;
          bottom: -8px;
          margin-left: -11px;
          display: inline-block;
          width: 22px;
          height: 4px;
          background-color: rgba(9,127,254,0.2);
          border-radius: 2px;
        }
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }

    /deep/ .el-button{
      background: #ff8d00 !important;
      border: 1px solid #FFFFFF !important;
    }

    /deep/ .el-checkbox__input.is-checked .el-checkbox__inner{
      background-color: #ff8d00 !important;
      border-color: #ff8d00 !important;
    }

    .pic-logo-img{
      height: 35px;
    }
  }
</style>

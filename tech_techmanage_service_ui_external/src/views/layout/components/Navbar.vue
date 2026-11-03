<template>
  <div class="navbar">
    <!-- <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/> -->
    <div class="left-menu">
      <menu-trigger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container" />
      <breadcrumb class="breadcrumb-container" />
      <!-- <span>{{navName}}</span> -->
    </div>

    <div class="right-menu">
      <!-- 全屏 -->
      <!-- <template v-if="device!=='mobile'">
        <div class="right-menu-item">
          <error-log class="errLog-container"/>
          <el-tooltip content="全屏" effect="dark" placement="bottom">
            <screenfull class="screenfull"/>
          </el-tooltip>
        </div>
      </template> -->
      <!-- 消息 -->
      <!-- <div class="right-menu-item">
        <el-popover 
          placement="bottom-end"
          trigger="click"
          content="text">
          <el-badge v-show="badgeNum != 0" slot="reference" :value="badgeNum" />
        </el-popover>
        <svg-icon icon-class="badge" />
      </div> -->
      <!-- 切换用户 -->
      <!-- <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <span>
          <svg-icon icon-class="switch-role" />
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="(role, idx) in roles" :key="role.roleId" :class="{'current_role': idx === roleIdx}">
            <span style="display:block;" @click="changeRole(idx)">{{ role.roleName }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>  -->
      <!-- 用户 -->
      <el-dropdown class="right-menu-item" trigger="click">
        <div class="uesrName">
          <!-- 用户头像 -->
          <!-- 获取头像不存在时，显示默认头像 -->
          <!-- <template v-if="this.avatar == null || this.avatar == ''">
            <img src="@/assets/avatar.png" class="avatar">
          </template>
          <template v-else>
            <img :src="avatar" class="avatar">
          </template> -->
          <!-- 需要更换头像时使用上面代码 -->
          <template>
            <img src="@/assets/LOGO.jpg" class="avatar">
          </template>
          <!-- 用户名 -->
          <div class="role-name">{{name}}（{{userId}}）</div>
        </div>

        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              <span class="expanded-click" @click="goHome">首页</span>
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item>
              <span class="expanded-click" @click="changePwd">修改</span>
            </el-dropdown-item>
          <el-dropdown-item divided>
            <span class="expanded-click" @click="logout">登出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>


      <!-- <el-dropdown class="avatar-container right-menu-item" trigger="hover">
        <span>
          切换角色<i class="el-icon-arrow-down el-icon-right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="role in roles" :key="role.roleId">
            <span style="display:block;">{{ role.roleName }}</span></el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown> -->

      <!-- <el-dropdown class="avatar-container right-menu-item" trigger="hover">
        <div class="avatar-wrapper">
          <img src="@/assets/avatar.png" class="user-avatar">
        </div>

        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              {{ '首页' }}
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">{{ '登出' }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown> -->

    </div>

    <el-dialog
      title="修 改 密 码"
      :visible.sync="dialogVisible"
      width="520px"
      :close-on-click-modal="false"
      class="updatepwd-dialog__body"
      append-to-body
      :before-close="cancelDialog"
      >
          <el-alert type="info" :closable="false">
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
        <el-form :model="pwdForm" :rules="pwdRules" ref="pwdForm" label-width="100px">
          <el-form-item label="原密码" prop="password">
            <el-input type="password" placeholder="请输入原密码" v-model="pwdForm.password"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input type="password" placeholder="请输入新密码" v-model="pwdForm.newPassword" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="confirmPassword">
            <el-input type="password" placeholder="请确认新密码" v-model="pwdForm.confirmPassword" show-password></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <div style="text-align: center">
          <el-button type="primary" @click="updatePwd">确 定</el-button>
          <el-button @click="cancelDialog">取 消</el-button>
        </div>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import Breadcrumb from '@/components/Breadcrumb'
  import Hamburger from '@/components/Hamburger'
  import MenuTrigger from '@/components/MenuTrigger'
  import ErrorLog from '@/components/ErrorLog'
  import Screenfull from '@/components/Screenfull'
  import SizeSelect from '@/components/SizeSelect'
  import LangSelect from '@/components/LangSelect'
  import ThemePicker from '@/components/ThemePicker'
  import store from '@/store'
  import { updateUserPwd } from '@/api/login'
  export default {
    components: {
      Breadcrumb,
      Hamburger,
      ErrorLog,
      Screenfull,
      SizeSelect,
      MenuTrigger,
      LangSelect,
      ThemePicker
    },
    data() {
      var checkRepPassword = (rule, confirmPassword, callback) => {
        // console.log(rule, confirmPassword, callback)
        if (!(confirmPassword === this.pwdForm.newPassword)) {
          callback(("两次输入的密码不一致"));
        } else {
          callback();
        }
      };

      const checkNewPassword = (rule, newPassword, callback) => {
        if(newPassword.length < 8) {
          callback("密码不能少于8位")
        }

        // 是数字
        var isDigit = /^.*[0-9]+.*/;
        // isLowerCase 小写字母
        var isLowerCase = /^.*[a-z]+.*/;
        // isUpperCase 大写字母
        var isUpperCase = /^.*[A-Z]+.*/;
        // 特殊字符
        var regEx = /^.*[^a-zA-Z0-9]+.*/;
        // 记录匹配的次数
        let containtype = 0;
        if (isDigit.test(newPassword)) {
            containtype++;
        }
        if (isLowerCase.test(newPassword)) {
            containtype++;
        }
        if (isUpperCase.test(newPassword)) {
            containtype++;
        }
        if (regEx.test(newPassword)) {
            containtype++;
        }
        if(containtype < 3){
          callback("密码至少包含以下三种：大/小写字母，数字，特殊字符");
        } else {
          callback();
        }
      };

      return {
        roles: [],
        roleIdx: 0,
        badgeNum: 0,
        pwdForm: {
          userid: "",
          password: "", // 原密码
          newPassword: "", // 新密码
          confirmPassword: "", // 确认新密码
        },
        pwdRules: {
          password: [
            { required: true, message: "请输入旧密码", trigger: "blur" }
          ],
          newPassword: [
            { required: true, message: "新输入新密码", trigger: "blur" },
            { required: true, validator: checkNewPassword, trigger: "blur" }
          ],
          confirmPassword: [
            { required: true, message: "请重复新密码", trigger: "blur" },
            { required: true, validator: checkRepPassword, trigger: "blur" }
          ]
        },
        dialogVisible: false, // 弹框
      }
    },
    props:["navName"],
    created() {
      const inval = store.getters.invaldate
      if (inval === undefined) {
        this.dialogVisible = true
        this.$message.warning('初次登陆密码需进行修改')
      }
      const now = new Date()
      const invaldate = new Date(inval)
      console.log(now > invaldate, '===================+++')
      if (now > invaldate) {
        this.dialogVisible = true
        this.$message.warning('密码90天以上需进行修改')
      }
      this.roles = store.getters.roles
      this.roleIdx = store.getters.roleIdx
      // console.log('roles', this.roles)
      // console.log('roleIdx', this.roleIdx)
      // console.log('avatar', this.avatar)
      // console.log('name', this.name)
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'name',
        'avatar',
        'device',
        'userId'
      ])
    },
    methods: {
      toggleSideBar() {
        this.$store.dispatch('toggleSideBar')
      },
      logout() {
        this.$store.dispatch('LogOut').then(() => {
          // this.$router.push({ path: '/login'}) // do not use it before bug fixed
          location.reload()// In order to re-instantiate the vue-router object to avoid bugs
        })
      },
      goHome() {
        this.$router.push('/');
      },
      // 修改账户密码
      updatePwd() {
        if(this.pwdForm.password == this.pwdForm.newPassword) {
          return this.$message.warning('新密码不能与原密码相同')
        }
        this.$refs['pwdForm'].validate((valid) => {
          if(valid) {
            let params = {};
            params = {
              userid: this.$store.getters.userId,
              password: this.pwdForm.password, // 原密码
              newPassword: this.pwdForm.newPassword, // 新密码
              confirmPassword: this.pwdForm.confirmPassword, // 确认新密码
            };
            updateUserPwd(params).then(res => {
              // console.log('res', res)
              if(res.data.status == 200) {
                this.$loading({
                  lock: true,
                  text: "修改成功，即将跳转重新登录...",
                  spinner: 'el-icon-loading',
                  background: 'rgb(255,255,255)'
                })

                setTimeout(() => {
                  this.$store.dispatch('LogOut').then(() => {
                    location.reload()
                  })
                }, 2000)
                this.dialogVisible = false;
              } else {
                return this.$message.error(res.data.message);
              }
            })
          } else {
            return false;
          }
        })
      },
      // 展示弹框
      changePwd() {
      this.dialogVisible = true;
      },
      // 取消弹框
    cancelDialog() {
      this.resetForm()
      const inval = store.getters.invaldate
      const now = new Date()
      const invaldate = new Date(inval)
      console.log(now > invaldate, '===================+++')
      if (inval === undefined) {
        this.$message.warning('初次登陆密码需进行修改')
      } else if (now > invaldate) {
        this.$message.warning('密码90天以上需进行修改')
      } else {
        this.dialogVisible = false
      }
    },
      resetForm() {
      this.$refs['pwdForm'].resetFields();
      this.pwdForm = {
          userid: "",
          password: "", // 原密码
          newPassword: "", // 新密码
          confirmPassword: "", // 确认新密码
        }
      },
      // 切换角色
      changeRole(idx) {
        if (this.roleIdx === idx) return
        // 关闭所有打开的页面，回到首页
        this.$store.dispatch('delAllViews')
        this.$router.push('/')
        this.$store.dispatch('ChangeRole', idx).then(res => {
          this.roleIdx = idx
        }).catch(err => { })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .navbar {
    /* position: relative; */
    height: 50px;
    line-height: 50px;
    border-radius: 0px !important;
    /* box-shadow: 0 1px 4px 0 rgba(0, 21, 41, .1); */
    display: flex;
    justify-content: space-between;
    align-items: center;

    .hamburger-container {
      font-family: normal;
      padding: 0 16px;

      &:hover {
        background-color: rgba(240, 240, 240, 0.2);
      }
    }

    .breadcrumb-container {
      /* width: 90px; */
      height: 25px;
      font-size: 16px;
      font-weight: 500;
      font-family: "PingFang SC";
      color: rgba(38, 38, 38, 1);
      line-height: 25px;
    }

    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }
  }

  /* new */
  .left-menu {
    display: flex;
    align-items: center;
  }

  .right-menu {
    line-height: 50px;
    height: 50px;
    /* overflow: hidden; */
  }

  .right-menu .right-menu-item {
    /* position: relative; */
    height: 50px;
    line-height: 50px;
    cursor: pointer;
    padding: 0 12px;
    /* vertical-align: middle; */
    display: flex;
    align-items: center;
    outline: none;
    border: none;
  }

  .right-menu .uesrName{
    display: flex;
    align-items: center;
  }

  .right-menu .right-menu-item:hover {
    background-color: rgba(240, 240, 240, .2);
  }

  /* .right-menu .right-menu-item .el-badge {
    position: absolute;
    top: -4px;
    right: -2px;
  } */

  /* .right-menu .right-menu-item svg {
    width: 20px;
    height: 20px;
  } */

  .right-menu-item .avatar {
    /* float: left; */
    width: 24px;
    height: 24px;
    margin: 7px 4px;
  }

  .right-menu-item .role-name {
    /* width: 90px; */
    height: 20px;
    font-size: 14px;
    font-weight: 500;
    color: rgba(38, 38, 38, 1);
    line-height: 20px;
  }

  .right-menu-item .screenful {
    padding: 20px 0;
  }

  .user-dropdown .el-dropdown-menu__item {
    padding: 0 50px;
  }

  .el-dropdown-menu--medium .el-dropdown-menu__item.el-dropdown-menu__item--divided:before {
    margin: 0 -50px;
  }

  .current_role {
    background-color: #ecf5ff;
    color: #66b1ff;
  }

  .expanded-click {
    display: block;
    margin: 0 -50px;
    text-align: center;
  }
  .updatepwd-dialog__body {
  padding: 0px 20px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
}
.updatepwd-form {
  padding: 20px;
}
</style>

<style>
  /* 下拉框样式修改 */
  .el-dropdown-menu__item:not(.is-disabled):hover {
    color: rgba(251, 164, 47, 1);
  }
</style>
<template>
  <div class="navbar-workflow" style="min-width: 1100px">
    <div class="left-logo">
      <!--      <ts-svg-icon v-if="nowThemeInfo==='white-theme'" class="pic-tansun-svg" icon="logo" />-->
      <!--      <img v-if="nowThemeInfo==='black-theme'" class="pic-tansun-img" src="@/assets/images/home/tansun-white.png">-->
      <!--      <img v-if="nowThemeInfo==='white-theme'" class="pic-tansun-img" src="@/assets/images/home/tansun-white.png">-->
      <!--      <img v-if="nowThemeInfo==='blue-theme'" class="pic-tansun-img" src="@/assets/images/home/tansun-white.png">-->
      <!--      <img v-if="nowThemeInfo==='black-theme'" class="pic-tansun-img" src="@/assets/images/home/tansun-white.png">-->
    </div>

    <div class="search-menu" style="display:none">
      <el-autocomplete
          class="my-input"
          placeholder="搜索"
          v-model="searchValue"
          @select="handleSelect"
          prefix-icon="el-icon-search"
          :fetch-suggestions="querySearch"
      ></el-autocomplete>
    </div>

    <div class="right-menu">
      <div class="avatar-container">
        <div class="avatar-wrapper" @click="logout">
          <div class="el-icon-switch-button user-info">{{$t('logout')}}</div>
        </div>
      </div>
    </div>

    <div class="right-menu" style="display:none">
      <div class="avatar-container">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            切换语言<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="en">en</el-dropdown-item>
            <el-dropdown-item command="zh">简体中文</el-dropdown-item>
            <el-dropdown-item command="tw">繁體中文</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <div class="right-menu">
      <div class="avatar-container">
        <div class="avatar-wrapper" @click="changePwd">
          <div class="el-icon-edit user-info">修改密码</div>
        </div>
      </div>
    </div>

    <div class="right-menu">
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <div class="el-icon-user-solid user-info">{{userInfo.realname}}</div>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="showInstList">
            <span style="display:block;font-size: 12px">机构：{{userInfo.currInstNm}}</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="showDeptList">
            <span style="display:block;font-size: 12px">部门：{{userInfo.currDeptNm}}</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="showRoleList">
            <span style="display:block;font-size: 12px">角色：{{userInfo.currRlNm}}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <div class="right-menu">
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <div class="el-icon-s-tools user-info">主题切换</div>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="changeTheme('white')">
            <span style="display:block;font-size: 12px">白色主题</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="changeTheme('orange')">
            <span style="display:block;font-size: 12px">橙色主题</span>
          </el-dropdown-item>
          <!--          <el-dropdown-item @click.native="changeTheme('white')">-->
          <!--            <span style="display:block;font-size: 12px">蓝色主题</span>-->
          <!--          </el-dropdown-item>-->
          <!--          <el-dropdown-item @click.native="changeTheme('black')">-->
          <!--            <span style="display:block;font-size: 12px">黑色主题</span>-->
          <!--          </el-dropdown-item>-->
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <div class="right-menu">
      <div class="avatar-container">
        <div class="avatar-wrapper" @click="toHome">
          <div class="el-icon-s-home user-info">首页</div>
        </div>
      </div>
    </div>
    <el-dialog
        title="机构切换"
        :visible.sync="instListVisible"
        width="50%">
      <el-table max-height="200" :data="instList" style="width: 100%" @row-click="instRowClick">
        <el-table-column align="center" prop="orgCode" label="机构代码"/>
        <el-table-column align="center" prop="orgName" label="机构"/>
        <el-table-column align="center" prop="deptCode" label="部门代码"/>
        <el-table-column align="center" prop="deptName" label="部门"/>
      </el-table>
    </el-dialog>
    <el-dialog
        title="部门切换"
        :visible.sync="deptListVisible"
        width="50%">
      <el-table max-height="200" :data="deptList" style="width: 100%" @row-click="deptRowClick">
        <el-table-column align="center" prop="deptCode" label="部门代码"/>
        <el-table-column align="center" prop="deptName" label="部门"/>
        <el-table-column align="center" prop="orgCode" label="机构代码"/>
        <el-table-column align="center" prop="orgName" label="机构"/>
      </el-table>
    </el-dialog>
    <el-dialog
        title="角色切换"
        :visible.sync="roleListVisible"
        width="50%">
      <el-table max-height="200" :data="roleList" style="width: 100%" @row-click="roleRowClick">
        <el-table-column align="center" prop="roleCode" label="代码"/>
        <el-table-column align="center" prop="roleName" label="角色"/>
      </el-table>
    </el-dialog>
    <ts-multi-dialog
        :visible.sync="visible"
        :options="options">
    </ts-multi-dialog>
    <sidebar class="sidebar-container"/>
  </div>
</template>

<script>
    import { JSEncrypt } from 'jsencrypt'
    import Sidebar from '@/layout/components/Sidebar/index'
    import { getPublicKey, updatePwd } from '@/api/login/index'
    import { fetchUserOrgDept, fetchUserChanger } from '@/api/login/systemManagement'
    import { mapState, mapGetters, mapMutations } from 'vuex'
    import ChangePwd from './ChangePwd'
    import defaultSettings from '@/plugins/settings'
    import { setup } from '@/plugins/lang/index'

    const sessionMode = defaultSettings.enableSession

    export default {
        computed: {
            ...mapGetters([
                'sidebar',
                'avatar',
                'routeMenu'
            ]),
            ...mapState({
                'userInfo': state => state.user.userInfo,
                'nowThemeInfo': state => state.theme.nowThemeInfo
            }),
            restaurants() {
                const list = this.loadAll()
                return list.filter(item => {
                    return item.url
                })
            }
        },
        components:{Sidebar},
        data() {
            return {
                themeValue: '',
                themes: ['white', 'black'],
                searchValue: '',
                instListVisible: false,
                deptListVisible: false,
                roleListVisible: false,
                instList: [], // 机构列表
                deptList: [], // 部门列表
                roleList: [], // 角色列表
                // 表单数据
                encrypt: null,
                isPwdEncrypt: false,
                visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
                options: {
                    title: '修改密码', // [必填]
                    beforeClose: (data, done) => {
                        done()
                    }, // [可不填] 关闭右上角弹框前的回调
                    resource: ChangePwd, // [必填] 在弹框中展示的组件页面
                    data: {
                        password: '',
                        newPwd: '',
                        renewPwd: ''
                    },
                    callbacks: [(data, done) => {
                        done()
                    }, this.submit] // [必填] 若取消按钮没有回调，可写默认值null 如[null, this.submit]
                }
            }
        },
        created() {

        },
        methods: {
            handleCommand(lang) {
                this.$i18n.locale = lang
                setup(lang)
                // location.reload() // 为了重新实例化vue-router对象，避免一些bug
            },
            ...mapMutations([
                'SET_THEME'
            ]),
            changeTheme(value) {
                this.SET_THEME(value)
            },
            querySearch(queryString, callbcak) {
                const restaurants = this.restaurants
                const results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
                // 调用 callback 返回建议列表的数据
                callbcak(results)
            },
            createFilter(queryString) {
                return (restaurant) => {
                    if (restaurant.value) {
                        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1)
                    } else {
                        return false
                    }
                }
            },
            loadAll() {
                return this.treeToList(this.routeMenu)
            },
            handleSelect(item) {
                if (item.url && item.url !== '') {
                    const part = item.url.replace(/\//g, '')
                    this.$router.push({
                        path: item.url,
                        query: { part: part }
                    })
                }
            },
            toHome() {
                this.$router.push('/')
            },
            changePwd() {
                this.encodeRsa()
            },
            encodeRsa() {
                getPublicKey().then(res => {
                    const publicKey = res.data.publicKey
                    this.isPwdEncrypt = res.data.isPwdEncrypt
                    if (sessionMode && this.isPwdEncrypt === 'true') {
                        this.encrypt = new JSEncrypt()
                        this.encrypt.setPublicKey(publicKey)
                    } else {
                        this.encrypt = new JSEncrypt()
                        this.encrypt.setPublicKey(publicKey)
                    }
                    this.visible = true
                })
            },
            submit(data, done) {
                let password = data.password
                let newPwd = data.newPwd
                if (this.encrypt != null) {
                    password = this.encrypt.encrypt(data.password)
                    newPwd = this.encrypt.encrypt(data.newPwd)
                }
                const params = {
                    password: password,
                    newPswd: newPwd
                }
                updatePwd(params).then(response => {
                    this.$message.success(response.description)
                    done()
                }).catch(error => {
                    console.log(error)
                })
            },
            showInstList() {
                // 机构列表
                this.instListVisible = true
                fetchUserOrgDept().then((res) => {
                    this.instList = res.data
                })
            },
            showDeptList() {
                // 部门列表
                this.deptList = this.userInfo.deptInfList
                this.deptListVisible = true
            },
            showRoleList() {
                // 角色列表
                this.roleList = this.userInfo.roleInfList
                this.roleListVisible = true
            },
            instRowClick(e) {
                // 机构切换
                const param = e
                param.type = '0'
                this.fetchUserChanger(param)
            },
            deptRowClick(e) {
                // 部门切换
                const param = e
                param.type = '1'
                this.fetchUserChanger(param)
            },
            roleRowClick(e) {
                // 角色切换
                const param = e
                param.type = '2'
                this.fetchUserChanger(param)
            },
            fetchUserChanger(param) {
                fetchUserChanger(param).then((res) => {
                    this.$store.dispatch('tagsView/delAllViews')
                    this.$router.replace('/')
                    window.location.reload()
                })
            },
            toggleSideBar() {
                this.$store.dispatch('app/toggleSideBar')
            },
            async logout() {
                if (sessionMode) {
                    await this.$store.dispatch('user/logout_session')
                } else {
                    await this.$store.dispatch('user/logout_token')
                }
                this.$store.state.permission.addRoutes = []
                await this.$router.push(`/login`)
            },
            // 树转list
            treeToList(tree) {
                const list = [] // 结果lsit
                for (const i in tree) {
                    if (tree[i].menu.length !== 0) { // 遍历树的第一层,只有一个根结点
                        // 第一层加入到list中,因为根结点模块设置为虚拟结点,所以不用加入

                        this.toListDF(tree[i].menu, list, tree[i].id) // 遍历子树,并加入到list中.
                    }
                }
                return list
            },
            /**
             * 深度优先遍历树
             * 一个递归方法
             * @params tree:要转换的树结构数据
             * @params list:保存结果的列表结构数据，初始传list = []
             * @params parentId:当前遍历节点的父级节点id，初始为null(因为根节点无parentId)
             **/
            toListDF(tree, list, parentId) {
                for (const i in tree) { // 遍历最上层
                    // 将当前树放入list中
                    list.push({
                        id: tree[i].id,
                        value: tree[i].text,
                        url: tree[i].url,
                        parentId: parentId
                    })
                    // 如果有子结点,再遍历子结点
                    if (tree[i].menu && tree[i].menu.length !== 0) {
                        this.toListDF(tree[i].menu, list, tree[i].id) // 递归
                    }
                }
            }
        }
    }
</script>

<style lang="scss" type="text/scss" scoped>
  @import "~@/styles/common/theme.scss";

  .navbar-workflow {
    height: 65px;
    /*overflow: hidden;*/
    position: relative;
    @include top-background();

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background .3s;
      -webkit-tap-highlight-color: transparent;

      &:hover {
        background: rgba(0, 0, 0, .025)
      }
    }

    .breadcrumb-container {
      float: left;
    }

    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }

    .left-logo {
      float: left;
      height: 100%;
      padding-top: 3px;

      .pic-tansun-svg {
        float: left;
        margin-left: 10px;
        width: 120px;
        height: 36px;
      }

      .pic-tansun-img {
        float: left;
        margin-left: 20px;
        height: 35px;
        //width: 110px;
        //height: 40px;
      }

      .line {
        float: left;
        width: 0;
        height: 30px;
        border: 0.2px solid rgba(255, 255, 255, 1);
        opacity: 1;
        margin: 6px 5px 0;
      }

      .sys-name {
        float: left;
        margin-top: 3px;
        width: 180px;
        height: 22px;

        .top {
          font-size: 16px;
          font-weight: 400;
          line-height: 20px;
          color: rgba(255, 255, 255, 1);
          opacity: 1;
        }

        .bottom {
          font-size: 12px;
          line-height: 12px;
          color: rgba(255, 255, 255, 1);
          opacity: 1;
        }
      }
    }

    .search-menu {
      float: left;
      height: 100%;
      margin-left: 40px;
      padding-top: 8px;

      .my-input ::v-deep .el-input__inner {
        width: 263px;
        height: 32px;
        opacity: 1;
        border: none !important;
        @include menu-input();
      }

      .my-input ::v-deep .el-input__inner::-webkit-input-placeholder {
        @include menu-input-placehoder();
      }

      .my-input ::v-deep .el-input__icon {
        line-height: 32px;
        @include menu-input-icon();
      }

      .my-input ::v-deep .el-input__suffix {
        @include top-text-color();
      }
    }

    .right-menu {
      float: right;
      height: 100%;
      line-height: 48px;

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          position: relative;

          .user-info {
            cursor: pointer;
            font-size: 12px;
            @include top-text-color();
          }
        }
      }
      .el-dropdown {
        font-size: 12px;
        color: #ffffff;
      }
    }
  }
</style>

import { login, logout, fetchUserInfo, fetchAuthMenu, singlelogin } from '@/api/login'
import { getToken, setToken, removeToken, setSecretToken, removeSecretToken } from '@/utils/auth'
import { Message } from 'element-ui'
import router from '@/router'
import store from '@/store'

const Qs = require('qs')
const user = {
  state: {
    name: '', // 账号名
    status: '', // 账号状态
    roles: [], // 用户角色列表
    lastChangePwdTime: '', // 用户最后一次修改密码时间
    roleIdx: 0, // 当前用户序号
    branchId: '', // 所属机构ID
    deptId: '',  // 部门编号
    userId: '',   // 员工号
    branchLevel: '',
    user: '',
    code: '',
    token: getToken(),
    avatar: '',
    introduction: '',
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_USERID: (state, userId) => {
      state.userId = userId
    },
    SET_DEPTID: (state, deptId) => {
      state.deptId = deptId
    },
    SET_LOCATION: (state, location) => {
      state.location = location
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_STATUS: (state, status) => {
      state.status = status
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_FUNCS: (state, funcs) => {
      state.funcs = funcs
    },
    SET_INVALDATE: (state, invaldate) => {
      state.invaldate = invaldate
    },
    SET_LASTLOGIN: (state, lastlogin) => {
      state.lastlogin = lastlogin
    },
    SET_ROLEIDX: (state, roleIdx) => {
      state.roleIdx = roleIdx
    },
    SET_BRANCHID: (state, branchId) => {
      state.branchId = branchId
    },
    SET_BRANCHLEVEL: (state, branchLevel) => {
      state.branchLevel = branchLevel
    },
    SET_LASTCHANGEPWDTIME: (state, lastChangePwdTime) => {
      state.lastChangePwdTime = lastChangePwdTime
    },
    SET_CODE: (state, code) => {
      state.code = code
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    }
  },

  actions: {

    /**
     * 用户登录
     * @param {*} param0 *
     * @param {*} userInfo 用户账号和密码
     */
    SingleLoginByUsername({ commit, dispatch }, datainfo) {
      return new Promise((resolve, reject) => {
        let data = datainfo

        // data = Qs.stringify(data)
        singlelogin(data).then(res => {
          if (res.data.status == '200') {
            // 保存用户信息
            commit('SET_USERID', res.data.results.userid) // 员工号
            commit('SET_NAME', res.data.results.operatorname)
            commit('SET_AVATAR', res.data.results.avatar) // 头像
            commit('SET_STATUS', res.data.results.userState)
            commit('SET_ROLES', res.data.results.roles)
            commit('SET_FUNCS', res.data.results.funcs)
            commit('SET_INVALDATE', res.data.results.invaldate)
            commit('SET_LASTLOGIN', res.data.results.lastlogin)
            commit('SET_TOKEN', res.data.results.roles[0].roleId)
            commit('SET_LASTCHANGEPWDTIME', res.data.results.lastChangePwdTime)
            commit('SET_BRANCHID', res.data.results.brachId) // 机构ID
            commit('SET_DEPTID', res.data.results.deptId) // 部门ID
            commit('SET_LOCATION', res.data.results.location) // 部门名称
            setToken(res.data.results.userid)
            setSecretToken(res.data.results.secretKey)
            dispatch('GetUserInfo').then(res => {
              resolve(res)
            })
          } else {
            Message({
              message: res.data.message,
              type: 'error',
              duration: 5 * 1000
            })
            resolve(res)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    /**
     * 用户登录
     * @param {*} param0 *
     * @param {*} userInfo 用户账号和密码
     */
    LoginByUsername({ commit, dispatch }, userInfo) {
      return new Promise((resolve, reject) => {
        let data = {
          username: userInfo.username,
          password: userInfo.password
        }
        data = Qs.stringify(data)
        login(data).then(res => {
          if (res.data.status == '200' && res.data.results != null) { 
            // 保存用户信息
            commit('SET_USERID', res.data.results.userid) // 员工号
            commit('SET_NAME', res.data.results.operatorname)
            commit('SET_AVATAR', res.data.results.avatar) // 头像
            commit('SET_STATUS', res.data.results.userState)
            commit('SET_ROLES', res.data.results.roles)
            commit('SET_FUNCS', res.data.results.funcs)
            commit('SET_TOKEN', res.data.results.roles[0].roleId)
            commit('SET_LASTCHANGEPWDTIME', res.data.results.lastChangePwdTime)
            commit('SET_BRANCHID', res.data.results.brachId) // 机构ID
            commit('SET_DEPTID', res.data.results.deptId) // 部门ID
            setToken(res.data.results.userid)
            setSecretToken(res.data.results.secretKey)
            // resolve(res)
            dispatch('GetUserInfo').then(res => {
              resolve(res)
            })
          } else {
            Message({
              message: res.data.message,
              type: 'error',
              duration: 5 * 1000
            })
            resolve(res)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    /**
     * 获取用户信息
     * @param {*} param0
     */
    GetUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        fetchUserInfo().then(res => {
          if (res.data.status == '200' && res.data.results != null) {
            // 保存用户信息
            commit('SET_USERID', res.data.results.userid) // 员工号
            commit('SET_NAME', res.data.results.operatorname)
            commit('SET_AVATAR', res.data.results.avatar) // 头像
            commit('SET_STATUS', res.data.results.userState)
            commit('SET_ROLES', res.data.results.roles)
            commit('SET_FUNCS', res.data.results.funcs)
            commit('SET_INVALDATE', res.data.results.invaldate)
            commit('SET_LASTLOGIN', res.data.results.lastlogin)
            commit('SET_TOKEN', res.data.results.roles[0].roleId)
            commit('SET_LASTCHANGEPWDTIME', res.data.results.lastChangePwdTime)
            commit('SET_BRANCHID', res.data.results.brachId) // 机构ID
            commit('SET_BRANCHLEVEL', res.data.results.branchLevel)
            commit('SET_DEPTID', res.data.results.deptId) // 部门ID
            commit('SET_LOCATION', res.data.results.location) // 部门名称
            setToken(res.data.results.userid)
            setSecretToken(res.data.results.secretKey)
            store.dispatch('loadAllDict').then(res => {
                 resolve(res)
            })
            // resolve(res)
          } else {
            removeToken()
            removeSecretToken()
            // location.reload()
            resolve()
          }
        }).catch(error => {
          resolve()
          // reject(error)
        })
      })
    },

    /**
     * 获取用户授权菜单列表
     * @param {*} param0 *
     */
    GetUserMenu({ commit }, roleId) {
      return new Promise((resolve, reject) => {
        const query = {
          roleId: roleId
        }
        fetchAuthMenu(query).then(res => {
          if (res.data.status !== 200) {
            Message({
              message: res.data.message,
              type: 'warning'
            })
          }
          let roleData = roleId.split(',')
          console.log('菜单权限', res.data, roleId, roleData)
          for (var j = 0; j < roleData.length; j++) {
            if (roleData[j] == 'MANAGER') {
              if (res.data.results.children != undefined) {
                for (var i = 0; i < res.data.results.children.length; i++) {
                  if (res.data.results.children[i].routeid == "H10001") {
                    res.data.results.children[i].children = res.data.results.children[i].children.filter(li => {
                      return li.routeid != 'H30002'
                    })
                    break
                  }
                }
              }
            }
          }
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    ChangeRole({ commit, dispatch, state }, roleIdx) {
      setToken(state.roles[roleIdx].roleId)
      commit("SET_ROLEIDX", roleIdx)
      return new Promise((resolve, reject) => {
        dispatch('GetUserMenu', state.roles[roleIdx].roleId).then(res => {
          const menu = res.data
          if (menu === '') { // 该用户无菜单权限
            reject()
            return false
          }
          store.dispatch('GenerateRoutes', menu).then(res => {
            router.addRoutes(store.getters.addRouters)
            resolve()
          })
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_FUNCS', [])
          removeToken()
          removeSecretToken()
          // location.reload()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        removeSecretToken()
        resolve()
      })
    },

    // 获取用户信息
    GetUserInfo1111111({ commit, state }) {
      return new Promise((resolve, reject) => {
        getUserInfo(state.token).then(res => {
          if (!res.data) { // 由于mockjs 不支持自定义状态码只能这样hack
            reject('error')
          }
          const data = res.data

          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }

          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          commit('SET_INTRODUCTION', data.introduction)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 第三方验证登录
    // LoginByThirdparty({ commit, state }, code) {
    //   return new Promise((resolve, reject) => {
    //     commit('SET_CODE', code)
    //     loginByThirdparty(state.status, state.email, state.code).then(res => {
    //       commit('SET_TOKEN', res.data.token)
    //       setToken(res.data.token)
    //       resolve()
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 动态修改权限
    ChangeRoles({ commit, dispatch }, role) {
      return new Promise(resolve => {
        commit('SET_TOKEN', role)
        setToken(role)
        getUserInfo(role).then(res => {
          const data = res.data
          commit('SET_ROLES', data.roles)
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          commit('SET_INTRODUCTION', data.introduction)
          dispatch('GenerateRoutes', data) // 动态修改权限后 重绘侧边菜单
          resolve()
        })
      })
    }
  }
}

export default user

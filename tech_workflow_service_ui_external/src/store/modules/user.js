/*
@Author: chenhuocan
@Date: 2020-03-17 14:21:33
@LastEditors: Kinson
@LastEditTime: 2020-04-08 11:30:43
@Description: 用户状态管理
*/

import router from '@/router'
import { JSEncrypt } from 'jsencrypt'
import { login, fetchInfo, logout, getPublicKey } from '@/api/login/index'
import { resetRouter } from '@/router'
import common from 'tsvue/lib/utils/ts-common'
import defaultSettings from '@/plugins/settings'
import store from '@/store'

const state = {
  token: common.tansunGetToken(),
  name: '',
  avatar: '',
  userInfo: {},
  session: common.tansunGetUserSession()
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_USERINFO: (state, userInfo) => {
    state.userInfo = userInfo
  },
  SET_SESSION: (state, session) => {
    state.session = session
  }
}

const sessionMode = defaultSettings.enableSession

const actions = {
  /* 用户登录*/
  async login({ commit }, userInfo) {
    store.dispatch('tagsView/delAllViews')
    if (sessionMode) {
      // session模式
      let { password } = userInfo
      const keyResponse = await getPublicKey()
      const publicKey = keyResponse.data.publicKey
      const isPwdEncrypt = keyResponse.data.isPwdEncrypt
      if (!isPwdEncrypt) { // 判断配置文件是否开启加密，没有就返回明文
        const encrypt = new JSEncrypt()
        encrypt.setPublicKey(publicKey)
        const encryptedPwd = encrypt.encrypt(password)
        password = encryptedPwd
      }
      const loginUserInfo = Object.assign({}, userInfo, { password })
      return new Promise((resolve, reject) => {
        login(loginUserInfo).then(async response => {
          await store.dispatch('permission/setRouteMenu', {}, { root: true })
          await store.dispatch('permission/setPermissionResource', {}, { root: true })
          await store.dispatch('common/setDictData', {}, { root: true })
          await store.dispatch('user/getInfo', {}, { root: true })
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    } else {
      // token模式
      const { username, password } = userInfo
      const type = parseInt(3 * Math.random())
      let randompw = password + '_msdp_' + Math.random() + '_msdp_type=' + type
      if (type === 1) {
        randompw = Math.random() + '_msdp_' + password + '_msdp_type=' + type
      } else if (type === 2) {
        randompw = Math.random() + '_msdp_type=' + type + '_msdp_' + password
      }
      const encodePwd = common.tansunEncode64(randompw)
      return new Promise((resolve, reject) => {
        const loginUserInfo = Object.assign({}, userInfo, { username: username.trim(), password: encodePwd })
        login(loginUserInfo).then(async response => {
          const data = response
          commit('SET_TOKEN', data.access_token)
          common.tansunSetToken(data.access_token)
          await store.dispatch('permission/setRouteMenu', {}, { root: true })
          await store.dispatch('permission/setPermissionResource', {}, { root: true })
          await store.dispatch('common/setDictData', {}, { root: true })
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  },

  /* seseion模式-获取用户信息*/
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      fetchInfo(state.token).then(async response => {
        common.tansunGetUserSession()
        const { data } = response

        if (data) {
          commit('SET_SESSION', common.tansunEncode64(data.userNum))
          commit('SET_USERINFO', data)
          commit('SET_NAME', data.currRlNm)
          common.tansunSetUserSession(common.tansunEncode64(data.userNum))

          let accessRoutes = store.getters.addRoutes
          if (!accessRoutes.length) {
            accessRoutes = await store.dispatch('permission/generateRoutes')
          }
          // dynamically add accessible routes
          router.addRoutes(accessRoutes)

          resolve(data)
        } else {
          commit('SET_SESSION', '')
          commit('SET_USERINFO', '')
          common.tansunRemoveUserSession()
          reject('用户未登录或已过期，请重新登录！')
        }
      }).catch(error => {
        commit('SET_SESSION', '')
        commit('SET_USERINFO', '')
        common.tansunRemoveUserSession()
        reject(error)
      })
    })
  },

  /* token模式-获取用户信息*/
  getUserInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      fetchInfo(state.token).then(response => {
        const { data } = response
        if (!data) {
          reject('Verification failed, please Login again.')
        }
        const { loginName } = data
        commit('SET_NAME', loginName)
        commit('SET_USERINFO', data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // session 方式用户注销
  logout_session({ commit, state }) {
    localStorage.removeItem('VISITEDVIEWS')
    commit('SET_NAME', '')
    commit('SET_SESSION', '')
    common.tansunRemoveUserSession()
    resetRouter()

    return new Promise((resolve, reject) => {
      logout().then(() => {
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // token方式用户注销
  logout_token({ commit, state, dispatch }) {
    localStorage.removeItem('VISITEDVIEWS')
    commit('SET_NAME', '')
    commit('SET_TOKEN', '')
    common.tansunRemoveToken()
    resetRouter()
    // return new Promise((resolve, reject) => {
    //   logout(state.token).then(() => {
    //     // 重置已访问的视图和缓存的视图
    //     // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
    //     dispatch('tagsView/delAllViews', null, { root: true })
    //     resolve()
    //   }).catch(error => {
    //     reject(error)
    //   })
    // })
  },

  /* 删除令牌*/
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      common.tansunRemoveToken()
      resolve()
    })
  },

  // remove Session
  resetSession({ commit }) {
    return new Promise(resolve => {
      commit('SET_SESSION', '')
      common.tansunRemoveUserSession()
      resolve()
    })
  }

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

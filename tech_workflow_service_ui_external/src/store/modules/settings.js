/*
 * @Author: amberself
 * @Date: 2020-03-31 16:16:59
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-04-30 15:40:59
 * @Description:
 */
import defaultSettings from '@/plugins/settings'

const { showSettings, tagsView, fixedHeader, sidebarLogo, enableSession, keepAlive } = defaultSettings
const state = {
  showSettings: showSettings,
  tagsView: tagsView,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo,
  enableSession: enableSession,
  keepAlive: keepAlive
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (Object.prototype.hasOwnProperty.call(state, key)) {
      state[key] = value
    }
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}


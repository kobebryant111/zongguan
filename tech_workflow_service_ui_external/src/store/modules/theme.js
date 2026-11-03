/*
@Author: liuyang
@Date: 2020-04-29 14:20:38
@LastEditors: 2020-04-29 14:20:38
@LastEditTime: 2020-04-29 14:20:38
@Description: 主题样式
*/
const state = {
  nowThemeInfo: localStorage.getItem('theme') ? localStorage.getItem('theme') : 'white-theme', // 当前主题
  themes: ['white-theme', 'black-theme'] // 所有主题
}
const mutations = {
  SET_THEME(state, theme) {
    state.nowThemeInfo = theme + '-theme'
    localStorage.setItem('theme', state.nowThemeInfo)
  }
}

export default {
  state,
  mutations
}

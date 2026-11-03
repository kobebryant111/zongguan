/*
 * @Author: Evan-lian
 * @Date: 2020-03-19 15:19:16
 * @LastEditors: your name
 * @LastEditTime: 2020-04-01 10:45:40
 * @Description:
 */
import { fetchDict, fetchUserRouteMenu } from '@/api/login/systemManagement'
import common from 'tsvue/lib/utils/ts-common'

const state = {
  dictData: common.tansunGetStoreDict()
}

const mutations = {
  SET_DICTDATA: (state, data) => {
    state.dictData = data
  }
}

const actions = {
  setDictData({ commit, state }) {
    return new Promise((resolve, reject) => {
      fetchDict().then(response => {
        const { data } = response
        commit('SET_DICTDATA', data)
        common.tansunSetStoreDict(data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  setMenu({ commit, state }) {
    return new Promise((resolve, reject) => {
      fetchUserRouteMenu().then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}


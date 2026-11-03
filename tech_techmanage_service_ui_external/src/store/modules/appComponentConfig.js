import { stat } from "fs";

// app组件配置数据
const appComponentConfig = {
  state: {
    config: {},
    floorList: [],
    syncId: 0
  },
  mutations: {
    SET_CONFIG: (state, config) => {
      state.config = config
    },
    SET_FLOOR_LIST: (state, list) => {
      state.floorList = list
    },
    SET_SYNCID: (state, id) => {
      state.syncId = id
    }
  },
  actions: {
    SetConfig({ commit }, config) {
      commit('SET_CONFIG', config)
    },
    SetFloorList({ commit }, list) {
      commit('SET_FLOOR_LIST', list)
    },
    SetSyncId({ commit }, id) {
      commit('SET_SYNCID', id)
    }
  }
}

export default appComponentConfig
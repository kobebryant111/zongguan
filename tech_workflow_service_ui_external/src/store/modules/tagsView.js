/*
 * @Author: Evan-lian
 * @Date: 2020-05-20 10:53:27
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-08-03 13:44:24
 * @Description:
 */
import { Message } from 'element-ui'
import settings from '@/plugins/settings'
const state = {
  visitedViews: []
}
const reStoreLocalViews = (state) => {
  // 更新本地缓存
  try {
    const newvisitedViews = JSON.stringify(state.visitedViews)
    localStorage.setItem('VISITEDVIEWS', newvisitedViews)
  } catch (error) {
    console.log(error)
    console.log('路由对象循环引用报错')
  }
}
const mutations = {
  // 新增页签
  ADD_VISITED_VIEW: (state, view) => {
    // 修复刷新之后访问链缺失的情况，根据本地缓存反向生成vuex页签缓存
    let dataArray = []
    try {
      dataArray = JSON.parse(localStorage.getItem('VISITEDVIEWS'))
    } catch (error) {
      dataArray = []
    }
    state.visitedViews = dataArray || []
    let isAlreadyExist = false// 默认不存在
    const viewData = Object.assign({}, {
      title: view.title || view.meta.title || view.query.tagName || 'no-name',
      meta: view.meta,
      path: view.path,
      query: view.query,
      fullPath: view.fullPath,
      module: view.query.part || view.path.replace(/\//g, ''),
      name: view.name,
      backCahinPath: [],
      backCahinFullPath: []
    })
    if (view.query.newtag === 'newtag' || view.query.newtag === undefined) {
      // 新增页签 刷新的逻辑
      viewData.backCahinFullPath.push(view.fullPath)
      viewData.backCahinPath.push(view.path)
      let updateFlag = false // 改变tagName的时候，更新visitedViews
      const updateVisitedViews = []
      state.visitedViews.forEach((value, index) => {
        updateVisitedViews[index] = value
        if (value.module === view.query.part) { // 判断一级模块是否存在
          isAlreadyExist = true
          updateFlag = true
          updateVisitedViews[index] = viewData //已存在的一级页签重新打开更新数据
        }
      })
      if (!isAlreadyExist) { // 页签不存在就新增
        state.visitedViews.push(viewData)
      }
      if (updateFlag) { // 页签存在就更新
        state.visitedViews = updateVisitedViews
      }
    } else {
      // 已经创建过页签的逻辑
      state.visitedViews.forEach((value, index) => {
        if (value.module === view.query.part) {
          //保留一级页签的cachedtag、cachedkey
          const meta = {
            cachedtag:value.meta.cachedtag,
            cachedkey:value.meta.cachedkey
          }
          value.path = view.path
          value.fullPath = view.fullPath
          value.query = view.query
          value.name = view.name
          value.meta = Object.assign(view.meta,meta)
          if (view.query.goBack === 'goBack') {
            const backCahinPath = value.backCahinPath
            const newbackCahinPath = backCahinPath.concat()
            const backCahinFullPath = value.backCahinFullPath
            const newbackCahinFullPath = backCahinFullPath.concat()
            newbackCahinPath.pop()// 删除末尾项
            newbackCahinFullPath.pop()
            value.backCahinPath = newbackCahinPath
            value.backCahinFullPath = newbackCahinFullPath
            // 为了保证访问链始终保有顶部的来源，当为空的时候必须加入当前访问地址
            if (newbackCahinPath.length == 0) {
              Message.warning('已经返回至当前页签的最后一步了')
              value.backCahinPath.push(view.path)
              value.backCahinFullPath.push(view.fullPath)
            }
          } else {
            // 只记录和最后一个访问链不同的路径
            if (value.backCahinPath.slice(-1) != view.path) {
              value.backCahinPath.push(view.path)
              value.backCahinFullPath.push(view.fullPath)
            }
          }
        }
      })
    }
    const visitedViews = state.visitedViews
    // 页签数量大于阀值的操作
    if (visitedViews.length > settings.maxTags) {
      // 过滤可以删除的页签
      const unAffixViews = visitedViews.filter((item) => {
        return !item.meta.affix
      })
      // 替换第一条可删除的
      if (unAffixViews.length > 0) {
        state.visitedViews = visitedViews.filter((item) => {
          return item.path !== unAffixViews[0].path
        })
      }
    }
    reStoreLocalViews(state)
  },
  DEL_VISITED_VIEW: (state, view) => {
    for (const [i, v] of state.visitedViews.entries()) {
      if (v.module === view.module) {
        state.visitedViews.splice(i, 1)
        reStoreLocalViews(state)
        break
      }
    }
  },
  DEL_OTHERS_VISITED_VIEWS: (state, view) => {
    state.visitedViews = state.visitedViews.filter(v => {
      return v.meta.affix || v.module === view.module
    })
    reStoreLocalViews(state)
  },
  DEL_ALL_VISITED_VIEWS: state => {
    // 删除除了固定页签以外所有页签
    const affixTags = state.visitedViews.filter(tag => tag.meta.affix)
    state.visitedViews = affixTags
    reStoreLocalViews(state)
  },
  UPDATE_VISITED_VIEW: (state, view) => {
    for (let v of state.visitedViews) {
      if (v.module === view.module) {
        // v = Object.assign(v, view)
        // 改造之后
        v = Object.assign({}, {
          meta: view.meta,
          path: view.path,
          query: view.query,
          fullPath: view.fullPath
        })
        break
      }
    }
  }
}
const actions = {
  addView({ dispatch }, view) {
    dispatch('addVisitedView', view)
  },
  addVisitedView({ commit }, view) {
    commit('ADD_VISITED_VIEW', view)
  },
  delView({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delVisitedView', view)
      resolve({
        visitedViews: [...state.visitedViews]
      })
    })
  },
  delVisitedView({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_VISITED_VIEW', view)
      resolve([...state.visitedViews])
    })
  },
  delOthersViews({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delOthersVisitedViews', view)
      resolve({
        visitedViews: [...state.visitedViews]
      })
    })
  },
  delOthersVisitedViews({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_OTHERS_VISITED_VIEWS', view)
      resolve([...state.visitedViews])
    })
  },
  delAllViews({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delAllVisitedViews', view)
      resolve({
        visitedViews: [...state.visitedViews]
      })
    })
  },
  delAllVisitedViews({ commit, state }) {
    return new Promise(resolve => {
      commit('DEL_ALL_VISITED_VIEWS')
      resolve([...state.visitedViews])
    })
  },
  updateVisitedView({ commit }, view) {
    commit('UPDATE_VISITED_VIEW', view)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

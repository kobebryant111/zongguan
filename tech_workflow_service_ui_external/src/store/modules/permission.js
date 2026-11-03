/*
 * @Author: amberself
 * @Date: 2020-03-26 13:05:01
 * @LastEditTime : 2020-05-19 10:00:23
 * @LastEditors  : Evan-lian
 * @Description: 根据后台接口，返回动态权限菜单
 */
import Layout from '@/layout'
import { constantRoutes, asyncRoutes } from '@/router'
import { fetchUserRouteMenu, fetchUserAsyncRoute } from '@/api/login/systemManagement'
import common from 'tsvue/lib/utils/ts-common'

// menu.json接口的路由参数过滤
function filterAsyncRoutes(data) {
  const perssionRoutes = []
  getMenuItem(data, perssionRoutes)
  // sessionStorage.setItem('perssionRoutes', JSON.stringify(perssionRoutes))
  return perssionRoutes
}

function getMenuItem(route, perssionRoutes) {
  route.forEach(item => {
    const { menu, url } = item
    if (menu) {
      getMenuItem(menu, perssionRoutes)
    } else {
      if (url) {
        const filterUrl = item.url.replace(/\?.*/, '')
        const routeItem = {
          path: filterUrl,
          title: item.text
        }
        perssionRoutes.push(routeItem)
      }
    }
  })
}

// 按钮节点路由接口的参数过滤
function filterBtnRoutes(routes) {
  const validRoute = []
  routes.forEach(item => {
    // -----TODO----- 其中类似'index.invoice.confirm'包含.的路由参数先过滤掉
    const validFlag = item.indexOf('/')
    if (validFlag > -1) {
      const routeItem = {
        path: item,
        title: ''
      }
      validRoute.push(routeItem)
    }
  })
  return validRoute
}

function getRouteParam(item, views) {
  const { path, title } = item
  const childPosition = path.indexOf('/', 1) // 获取第二个/的位置

  let parentPath = ''
  let childPath = ''
  if (childPosition < 0) {
    parentPath = path
  } else {
    parentPath = path.slice(0, childPosition)
    childPath = path.slice(childPosition + 1)
  }

  // 如果有孩子，孩子有孩子 就要重定向 ，否则重定向一个空的页面404
  // 组装数据

  // 一级组装数据
  const routeObject = {
    path: parentPath,
    component: Layout,
    children: [{
      path: childPath,
      name: path.replace(/(\/)/g, ''),
      meta: {
        title,
        cachedtag: ''
      },
      component: views
    }]
  }

  // 如果有孩子 item.child 就向routeObject 孩子 组装数据 ，在判断有没有孩子，有的话 left 设为true

  return routeObject
}

const state = {
  routeMenu: common.tansunGetRouteMenu() || [],
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTEMENU: (state, data) => {
    state.routeMenu = data
  },
  SET_PERMISSIONROUTE: (state, data) => {
    state.addRoutes = data
    state.routes = constantRoutes.concat(data)
  }
}

const actions = {
  setRouteMenu({ commit, state }) {
    // 1、请求获取路由菜单
    return new Promise((resolve, reject) => {
      fetchUserRouteMenu().then(response => {
        const { data } = response
        // console.log('SET_ROUTEMENU:', data)
        commit('SET_ROUTEMENU', data)
        common.tansunSetRouteMenu(data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  setPermissionResource({ commit, state }) {
    // 设置权限路由
    return new Promise((resolve, reject) => {
      fetchUserAsyncRoute().then(response => {
        const { data } = response
        common.tansunSetPermissionResoure(data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 请求获取路由  重点
  generateRoutes({ commit }, roles) {
    return new Promise((resolve, reject) => {
      fetchUserRouteMenu().then(async response => {
        const { data } = response
        // menu.json接口路由
        let accessedPath = filterAsyncRoutes(data)
        // 菜单管理中的按钮节点路由
        const asyncBtnRoute = await fetchUserAsyncRoute()
        common.tansunSetPermissionResoure(asyncBtnRoute.data)
        const { permission } = asyncBtnRoute.data
        const permissionPath = filterBtnRoutes(permission)
        accessedPath = accessedPath.concat(permissionPath)
        const accessedRoutes = []
        accessedPath.forEach(unit => {
          const item = unit.path
          let accessItem = asyncRoutes[item]
          if (!accessItem) {
            // 本地没定义的路由，自动生成映射路由
            // const pcsFile = `@/views${item}`
            accessItem = () => import(`@/views${item}`)
          }
          // 转换路由
          const asyncRouteItem = getRouteParam(unit, accessItem)
          accessedRoutes.push(asyncRouteItem)
        })
        // 404 page must be placed at the end !!!
        const match404 = {
          path: '*',
          redirect: '/404',
          hidden: true
        }
        accessedRoutes.push(match404)
        commit('SET_PERMISSIONROUTE', accessedRoutes)
        resolve(accessedRoutes)
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

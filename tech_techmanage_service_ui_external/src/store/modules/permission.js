import { asyncRouterMap, constantRouterMap } from '@/router'
import { fetchAuthMenu } from '@/api/login'

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.roles) {
//     return roles.some(role => route.meta.roles.includes(role))
//   } else {
//     return true
//   }
// }
/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routes asyncRouterMap
 * @param roles
 */
// function filterAsyncRouter(routes, roles) {
//   const res = []

//   routes.forEach(route => {
//     const tmp = { ...route }
//     if (hasPermission(roles, tmp)) {
//       if (tmp.children) {
//         tmp.children = filterAsyncRouter(tmp.children, roles)
//       }
//       res.push(tmp)
//     }
//   })
//   return res
/**
 * 递归过滤授权的路由和菜单，返回后台授权通过的菜单
 * @param {*} authMenu 后端返回授权菜单
 * @param {*} routers 前端路由配置
 */
function filterAuthorityRouter(authMenu, routes) {
  const res = []
  routes.forEach(route => {
    const tmp = { ...route }
    let hasAuth = false
    let index = 0
    if (!route.needAuth) { // 不需要授权的菜单直接加入显示
      hasAuth = true
    } else {
      for (let i = 0; i < authMenu.length; i++) {
        if (route.id == authMenu[i].routeid && authMenu[i].flag) { // && authMenu[i].flag
          if (!route.hidden && route.meta.title != authMenu[i].name) {
            console.log('[Warning] 前端菜单路由名称和后端配置不同，请开发者修改！前端菜单名=' + route.meta.title + ', 后端返回名=' + authMenu[i].name )
            route.meta.title = authMenu[i].name
          }
          hasAuth = true
          index = i
          break
        }
      }
    }
    if (hasAuth) {
      if (tmp.children) {
        // 根据数据库的菜单编号和本地路由的id是否存在，如果存在则直接调用，如果不存在则使用默认方法 
        try{
          authMenu.forEach((vv,kk) => {
            tmp.children.forEach((v,k)=>{
              if(v.id === vv.routeid) {
                  index = kk
                  throw new Error('for brank')  // 跳出循环
              }
            })
          })
        }catch(e){

        }
        if(authMenu[index].children) {
          tmp.children = filterAuthorityRouter(authMenu[index].children, tmp.children)
        } else {
          tmp.children = tmp.children
        }
      }
      res.push(tmp)
    }
  })
  return res
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, menu) {
      return new Promise(resolve => {
        let accessedRouters = asyncRouterMap
        // 过滤后台授权的菜单和路由
        accessedRouters = filterAuthorityRouter(menu.results.children, accessedRouters)
        // 动态添加路由
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }
  }
}

export default permission

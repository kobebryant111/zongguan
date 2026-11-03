import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { getToken } from '@/utils/auth' // getToken from cookie

NProgress.configure({ showSpinner: false })// NProgress Configuration

// permission judge function
// function hasPermission(roles, permissionRoles) {
//   if (roles.indexOf('admin') >= 0) return true // admin permission passed directly
//   if (!permissionRoles) return true
//   return roles.some(role => permissionRoles.indexOf(role) >= 0)
// }

const whiteList = ['/login', '/auth-redirect','/singleLogin']// no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (getToken()) { // determine if there has token
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.addRouters.length === 0) { // 判断是否后台获取菜单权限
        console.log('store.getters====>', store.getters);
       
        if (store.getters.roles.length <= 0) { // 用户角色信息未获取，跳转登录获取
          // TODO: f5刷新后去重新获取用户数据
          store.dispatch('GetUserInfo').then(()=> {
            if(store.getters.roles.length == 0) {
              next({ path: '/' })
            }
             // 新增多个角色进行拼接
        let roleIds = [];
        store.getters.roles.forEach(element => {
          roleIds.push(element.roleId);
        });
            console.log("1============================>", roleIds)
            store.dispatch('GetUserMenu', roleIds.join(',')).then(res => { // 获取用户菜单权限
              console.log("res.data============================>", res.data)
              const menu = res.data
              if (menu === '') { // 用户菜单权限为空 结束加载
                NProgress.done()
              } else {
                store.dispatch('GenerateRoutes', menu).then(() => { // 根据后台返回生成可访问的路由
                  router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
                  next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
                })
              }
            }).catch((err) => {
              store.dispatch('FedLogOut').then(() => {
              Message.error('验证失败, 请重新登陆！')
              next({ path: '/' })
              })
            })
          })
          // // 用户失效
          // store.dispatch('FedLogOut').then(() => {
          //   next({ path: '/' })
          // })
        } else {
           // 新增多个角色进行拼接
        let roleIds = [];
        store.getters.roles.forEach(element => {
          roleIds.push(element.roleId);
        });
        console.log("2============================>", roleIds)
          store.dispatch('GetUserMenu', roleIds.join(',')).then(res => { // 获取用户菜单权限
            const menu = res.data
            if (menu === '') { // 用户菜单权限为空 结束加载
              NProgress.done()
            } else {
              store.dispatch('GenerateRoutes', menu).then(() => { // 根据后台返回生成可访问的路由
                router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
                next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
              })
            }
          }).catch((err) => {
            store.dispatch('FedLogOut').then(() => {
              Message.error('验证失败, 请重新登陆！')
              next({ path: '/' })
            })
          })
        }
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        // if (hasPermission(store.getters.roles, to.meta.roles)) {
        //   next()
        // } else {
        //   next({ path: '/401', replace: true, query: { noGoBack: true }})
        // }
        // // 可删 ↑
        next()
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      console.log('登陆...')
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})

import router from '@/router'
import store from '@/store'
import { Message, MessageBox } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import settings from './settings'
import common from 'tsvue/lib/utils/ts-common'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const title = process.env.VUE_APP_NAME

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = common.tansunGetPageTitle(to.meta.title, title)

  // determine whether the user has logged in
  const hasToken = common.tansunGetToken()
  const hasUserSession = common.tansunGetUserSession()
  const sessionMode = store.getters.enableSession
  console.log('hasToken',hasToken)
  console.log('hasUserSession',hasUserSession)
  console.log('sessionMode',sessionMode)
  if (sessionMode) {
    if (hasUserSession) {
      if (to.path === '/login') {
        // if is logged in, redirect to the home page
        next({ path: '/' })
        NProgress.done()
      } else {
        const hasGetUserName = store.getters.name
        // store.getters.userSession
        if (hasGetUserName) {
          const visitedViews = store.getters.visitedViews || []

          const filter = visitedViews.filter((item) => {
            return item.path === to.path
          })

          // 页签数量大于8个,且是新增页签,给出提示
          if (visitedViews.length > settings.maxTags - 1 && filter.length === 0 && (to.query.newtag === 'newtag' || to.query.newtag === undefined)) {
            MessageBox.confirm('您打开的标签太多,点击确定后将替换第一条,请谨慎选择!', '提示', {
              callback: (action) => {
                if (action === 'confirm') {
                  next()
                } else {
                  NProgress.done()
                }
              }
            })
          } else {
            next()
          }
        } else {
          try {
            // get user info
            await store.dispatch('user/getInfo')
            // hack method to ensure that addRoutes is complete
            // set the replace: true, so the navigation will not leave a history record
            next({ ...to, replace: true })
          } catch (error) {
            // remove token and go to login page to re-login
            await store.dispatch('user/resetToken')
            Message.error(error || 'Has Error')
            next(`/login?redirect=${to.path}`)
            NProgress.done()
          }
        }
      }
    } else {
      /* has no session*/
      if (whiteList.indexOf(to.path) !== -1) {
        // in the free login whitelist, go directly
        next()
      } else {
        // other pages that do not have permission to access are redirected to the login page.
        Message.error('用户未登录或已过期，请重新登录！')
        next(`/login?redirect=${to.path}`)
        NProgress.done()
      }
    }
  } else {
    if (hasToken) {
      if (to.path === '/login') {
        // if is logged in, redirect to the home page
        next({ path: '/home', query: { part: 'home' }})
        NProgress.done()
      } else {
        const hasGetUserInfo = store.getters.name
        if (hasGetUserInfo) {
          const visitedViews = store.getters.visitedViews

          const filter = visitedViews.filter((item) => {
            return item.path === to.path
          })

          // 页签数量大于8个,且是新增页签,给出提示
          if (visitedViews.length > settings.maxTags - 1 && filter.length === 0 && (to.query.newtag === 'newtag' || to.query.newtag === undefined)) {
            MessageBox.confirm('您打开的标签太多,点击确定后将替换第一条,请谨慎选择!', '提示', {
              callback: (action) => {
                if (action === 'confirm') {
                  next()
                } else {
                  NProgress.done()
                }
              }
            })
          } else {
            next()
          }
        } else {
          try {
            // get user info
            await store.dispatch('user/getUserInfo')

            let accessRoutes = store.getters.addRoutes
            // 添加路由
            if (!accessRoutes.length) {
              accessRoutes = await store.dispatch('permission/generateRoutes')
            }
            router.addRoutes(accessRoutes)
            next({ ...to, replace: true })
          } catch (error) {
            // remove token and go to login page to re-login
            await store.dispatch('user/resetToken')
            Message.error(error || 'Has Error')
            next(`/login?redirect=${to.path}`)
            NProgress.done()
          }
        }
      }
    } else {
      /* has no token*/
      if (whiteList.indexOf(to.path) !== -1) {
        // in the free login whitelist, go directly
        next()
      } else {
        // other pages that do not have permission to access are redirected to the login page.
        next(`/login?redirect=${to.path}`)
        // next()
        NProgress.done()
      }
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})

router.onError(error => {
  NProgress.done()
  Message.error('页面跳转异常')
  console.error(error)
})

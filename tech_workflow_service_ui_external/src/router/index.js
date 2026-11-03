/*
@Author: chenhuocan
@Date: 2020-03-10 17:05:26
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-09 11:02:45
@Description: 配置路由总入口
*/

/**
 * 注意:子菜单只在路由子菜单时出现。长度> = 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                  如果设置为真，项目将不会显示在侧栏(默认为假)
 * alwaysShow: true              如果设置为真，将始终显示根菜单
 *                               如果不设置alwaysShow，当项目有多个子路由时，
 *                               它将成为嵌套模式，否则不显示根菜单
 * redirect: noRedirect          如果设置noRedirect将不会在面包屑中重定向
 * name:'router-name'            该名称由<keep-alive>(必须设置!!)
 * meta : {
    roles: ['admin','editor']    控制页面角色(您可以设置多个角色)
    title: 'title'               名称显示在侧栏和面包屑(推荐设置)
    icon: 'svg-name'             图标显示在侧栏中
    affix: true                  如果设置为真，则标记将附加在标记视图中
    breadcrumb: false           如果设置为false，则该项将隐藏在breadcrumb中(默认为true)
    activeMenu: '/example/list'  i如果设置路径，侧栏将突出显示您设置的路径
    cachedtag:'vue-component-142-TsRoleManage' keepalive生成的页签名称，用于删除页签缓存用
    keepAlive: true              如果设置为真，页面将被缓存(默认读配置文件的设置)
  }
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
/* 第1步----引入各个模块的路由配置*/
import redirect from './redirect/index'
import login from './login/index'
import home from './home/index'
import tsmodules from './tsmodules/index'
import { workflowConstRouter } from './tsmodules/wf/index'
import store from '../store'
/**
 * constantRoutes
 * 没有权限要求的基页
 * 所有角色都可以访问
 */
export const constantRoutes = [
  /* 第2步----聚合路由  当你的路由图太长时，你可以把它分成小模块*/
  redirect,
  login,
  home,
  workflowConstRouter,
  // ...tsmodules, // 天阳---组件例子，系统管理，帮助中心，其他例子的路由配置
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/404', // 404页面
    component: () => import('@/views/error-page/404'),
    hidden: true
  }
]

export const asyncRoutes = Object.assign({}, tsmodules)

/**
 * asyncRoutes
 * 需要根据用户角色动态加载的路由
 */
// export const asyncRoutes = [
//   { path: '*', // 通常用于客户端 404 错误
//     redirect: '/404', // 重定向到/404
//     hidden: true
//   }
// ]
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
const createRouter = () => new VueRouter({
  // mode: 'history', //  使用 history 模式 需要后台配置支持
  base: process.env.BASE_URL,
  routes: constantRoutes,
  scrollBehavior: () => ({ y: 0 })
})
const router = createRouter()
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // 重置路由器
}

Vue.mixin({
  // 解决页签关了之后不刷新，扒了一些keep-alive源码，里面的逻辑 https://juejin.im/post/5b610da4e51d45195c07720d
  beforeRouteLeave: function(to, from, next) {
    let key = ''// keepalive 自动生成的key'117'
    let cache = {}// 缓存对象集合//{115: VNode {tag: "vue-component-115-Dashboard", data: {…}, children: undefined...}
    let keys = []// 缓存的key集合['117','118']
    const vnode = this.$vnode
    const parentVnode = vnode.parent
    const tag = vnode.tag// 会执行两次 先离开当前页签 "vue-component-142-TsRoleManage" 再离开 vue-component-118-Layout
    const fromName = tag && tag.substring(tag.length - 7)// 来自哪个页签
    if (fromName != '-Layout' && tag) {
      Vue.prototype.tpnode = vnode// 临时存储执行环境，记录真正的页签对象
      from.meta.cachedtag = tag // 将keepalive 生成的类似格式页签名‘vue-component-142-TsRoleManage’传入路由信息中
    }
    // 点红色叉叉的逻辑 或者顶部点击同一页签不同路径
    // if (from.meta.stopcache) { // 判断是否需要删除处理已经记忆的缓存。
    //   delete from.meta.stopcache
    //   if (vnode && vnode.data.keepAlive) {
    //     if (parentVnode && parentVnode.componentInstance.cache) {
    //       key = vnode.key == null
    //         ? vnode.componentOptions.Ctor.cid + (vnode.componentOptions.tag ? `::${vnode.componentOptions.tag}` : '')
    //         : vnode.key// '150' 如果AppMain 设置了:key 那么 vnode.key 就会有值对应
    //       cache = parentVnode.componentInstance.cache
    //       keys = parentVnode.componentInstance.keys
    //       // 关闭所有
    //       if (from.meta.stopcacheall) {
    //         delete from.meta.stopcacheall
    //         parentVnode.componentInstance.cache = {}
    //         parentVnode.componentInstance.keys = []
    //       }
    //       // 在当前页,关闭当前
    //       if (from.meta.stopcacheself) {
    //         delete from.meta.stopcacheself
    //         if (cache[key] && keys.length && keys.indexOf(key) > -1) {
    //           keys.splice(keys.indexOf(key), 1)
    //           delete cache[key]
    //         }
    //       }
    //       // 关闭其他
    //       if (from.meta.stopcacheothers) {
    //         delete from.meta.stopcacheothers
    //         if (cache[key]) {
    //           parentVnode.componentInstance.cache = {}// 先清空
    //           parentVnode.componentInstance.cache[key] = cache[key]// 再附上当前的这个页签
    //           parentVnode.componentInstance.keys = [key]
    //         }
    //       }
    //     }
    //   }
    // }
    next()
  },
  beforeRouteEnter: function(to, from, next) {
    if ((to.meta.stopcache && to.meta.stopcacheone) || to.query.needupdate == 'yes') {
      /**
       * 针对红叉叉 在非当前页关闭某个页签的特殊逻辑处理。
      由于页签key只能记录当前页所以路由如果未跳转无法正常删除
      进入的时候 先进入 vue-component-118-Layout 再进入
      vue-component-117-UserManagementTable
      只有 117 真正的页签才有 cachedtag 才需要清缓存
      needupdate 只认‘yes’/‘no’ 如果 needupdate 参数是除了‘yes’之外的字符串都是当作不刷新处理
      **/
      if (to.query.needupdate && to.query.needupdate.length) {
        delete to.query.needupdate // 删除刷新标记
      }
      if (to.meta.cachedtag) {
        const key = to.meta.cachedtag.split('-')[2]
        const vnode = Vue.prototype.tpnode
        clearKeepAlive(vnode, key, to)
      }
    } else {
      if (to.query.keepAlive != undefined) {
        to.query.keepAlive ? to.meta.keepAlive = true : to.meta.keepAlive = false
      } else {
        // 地址栏无值
        if (to.meta.keepAlive == undefined) {
          // 针对刷新无法动态加keepalive 属性的骚操作
          const VISITEDVIEWS = localStorage.getItem('VISITEDVIEWS') || null
          const PASSVIEWS = JSON.parse(VISITEDVIEWS) || []// 刷新之前缓存的页签
          const PASSVIEWSLEN = PASSVIEWS.length
          for (let i = 0; i < PASSVIEWSLEN; i++) {
            const VIEW = PASSVIEWS[i]
            if (VIEW.path === to.path) {
              to.meta.keepAlive = true
              break
            }
          }
        }
      }
    }
    next()
  }
})
/** 删除某个特定keepalive页签的缓存 */
function clearKeepAlive(vnode, key, to) {
  const parentVnode = vnode.parent
  if (parentVnode && parentVnode.componentInstance.cache) {
    const cache = parentVnode.componentInstance.cache
    const keys = parentVnode.componentInstance.keys
    if (cache[key] && keys.length && keys.indexOf(key) > -1) {
      // 只有在真正清除了缓存之后才移除标记位
      delete to.meta.stopcache
      delete to.meta.stopcacheone
      delete to.meta.cachedtag
      keys.splice(keys.indexOf(key), 1)
      delete cache[key]
    }
  }
}
export default router

/**
 *  使用 history 模式 需要后台配置支持
 * 因为我们的应用是个单页客户端应用，如果后台没有正确的配置，
 * 当用户在浏览器直接访问 http://oursite.com/user/id 就会返回 404，这就不好看了。
 *  所以呢，你要在服务端增加一个覆盖所有情况的候选资源：如果 URL 匹配不到任何静态资源
 *  则应该返回同一个 index.html 页面，这个页面就是你 app 依赖的页面。
 *  https://router.vuejs.org/zh/guide/essentials/history-mode.html#后端配置例子
 */


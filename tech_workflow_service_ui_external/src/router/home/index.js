/*
 * @Author: Evan-lian
 * @Date: 2020-05-22 18:17:37
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-23 09:59:52
 * @Description:
 */
import Layout from '@/layout'

const router = {
  path: '/',
  component: Layout,
  redirect: { path: '/home', query: { part: 'home' }},
  children: [{
    path: 'home',
    name: 'Home',
    component: () => import('@/views/home/wfindex'),
    meta: {
      page: '首页',
      title: '首页',
      icon: 'dashboard',
      affix: true
    }
  },
  {
    path: 'emptyPage',
    name: 'emptyPage',
    component: () => import('@/views/empty-page'),
    hidden: true
  }
  ]
}

export default router

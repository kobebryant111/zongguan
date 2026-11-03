/*
@Author: chenhuocan
@Date: 2020-03-11 19:46:21
@LastEditors:
@LastEditTime:
@Description: 重定向路由
*/

import Layout from '@/layout'
const router = {
  path: '/redirect',
  component: Layout,
  hidden: true,
  children: [
    {
      path: '/redirect/:path*',
      component: () => import('@/views/redirect/index')
    }
  ]
}
export default router

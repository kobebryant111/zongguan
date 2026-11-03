/*
@Author: chenhuocan
@Date: 2020-03-10 17:05:48
@LastEditors:
@LastEditTime:
@Description: 登录路由配置
*/
const router = {
  path: '/login',
  component: () => import('@/views/login/index'),
  hidden: true
}
export default router


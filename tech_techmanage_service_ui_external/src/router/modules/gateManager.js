/**
 * 闸机管理
 */
import Layout from '@/views/layout/Layout'
const gateManagerRouter = {
  path: '/gateManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/gateManager',
  name: 'gateManager',
  needAuth: true,
  id: 'ZJ10001',
  meta: {
    title: '闸机权限管理',
    icon: 'form'
  },
  children: [
    {
    path: '/zjAuthority',
    name: 'authority',
    component: () => import('@/views/gateManager/authority/index'),
    needAuth: true,
    id: 'ZJ1000101',
    meta: { title: '权限管理', noCache: true }
    },
    {
      path: '/zjManager',
      name: 'manager',
      component: () => import('@/views/gateManager/manager/index'),
      needAuth: true,
      id: 'ZJ1000102',
      meta: { title: '闸机管理', noCache: true }
      }
  ]
}
export default gateManagerRouter;
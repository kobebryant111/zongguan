/**
 * 综合管理
 */
import Layout from '@/views/layout/Layout'
const testManagementRouter = {
  path: '/testManagement',
  component: Layout,
  alwaysShow: true,
  redirect: '/testManagement',
  name: 'testManagement',
  needAuth: true,
  id: 'CG10001',
  meta: {
    title: '测试综合管理',
    icon: 'user'
  },
  children: [
    {
      path: '/testTeamManagement',
      name: 'TestTeamManagement',
      component: () => import('@/views/testManagement/TestTeamManagement'),
      needAuth: true,
      id: 'CS10011',
      meta: { title: '测试组管理', noCache: true }
    },
    {
      path: '/personnelPortrait',
      name: 'PersonnelPortrait',
      component: () => import('@/views/testManagement/PersonnelPortrait'),
      needAuth: true,
      id: 'RY10012',
      meta: { title: '人员画像', noCache: true }
    },
    {
      path: '/testApectaculars',
      name: 'testApectaculars',
      component: () => import('@/views/testManagement/TestApectaculars'),
      needAuth: true,
      id: 'CS10013',
      meta: { title: '测试看板', noCache: true }
    }
  ]
}
export default testManagementRouter

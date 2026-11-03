/**
 * 综合管理
 */
import Layout from '@/views/layout/Layout'
const safetyManagementRouter = {
  path: '/safetyManagement',
  component: Layout,
  alwaysShow: true,
  redirect: '/safetyManagement',
  name: 'safetyManagement',
  needAuth: true,
  id: 'AQ001',
  meta: {
    title: '安全管理',
    icon: 'user'
  },
  children: [
    {
      path: '/safetyOfficerList',
      name: 'safetyOfficerList',
      component: () => import('@/views/safetyManagement/safetyOfficerList'),
      needAuth: true,
      id: 'QD0011',
      meta: { title: '安全专员清单', noCache: true }
    }
  ]
}
export default safetyManagementRouter

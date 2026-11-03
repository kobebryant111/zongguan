/**
 * 数据中心后督管理
 */
import Layout from '@/views/layout/Layout'
const postSupervisionManagementRouter = {
  path: '/postSupervisionManagement',
  component: Layout,
  alwaysShow: true,
  redirect: '/postSupervisionManagement/postSupervisionManagement',
  name: 'postSupervisionManagement',
  needAuth: true,
  id: 'PS10001',
  meta: {
    title: '数据中心后督管理',
    icon: 'icon'
  },
  children: [
    {
      id: 'PS10002',
      path: 'postSupInfoRecordAndEntry',
      component: () => import('@/views/postSupervisionManagement/postSupInfoRecordAndEntry'),
      // component: () => import('@/views/postSupervisionManagement/confirmAndFeedback'),
      // component: () => import('@/views/postSupervisionManagement/psTwoManager'),
      name: 'postSupManagement',
      needAuth: true,
      meta: { title: '数据中心后督管理', noCache: true }
    }
  ]
}
export default postSupervisionManagementRouter;
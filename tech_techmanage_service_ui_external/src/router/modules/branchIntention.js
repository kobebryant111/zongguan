import Layout from '@/views/layout/Layout'
/**
 * 分行意向审批
 */
const branchIntentionRouter = {
  path: '/fhFlowManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/fhFlowManager',
  name: 'fhFlowManager',
  needAuth: true,
  id: 'FH10001',
  meta: {
    title: '分行项目管理',
    icon: 'people'
  },
  children: [
    {
      path: '/pager',
      name: 'pager',
      component: () => import('@/views/BanchProjectChange/Pager'),
      needAuth: true,
      id: 'FH10003',
      meta: { title: '分行自研项目应用变更', noCache: true },
    },
    {
      path: '/fhwbcgspsz',
      name: 'fhwbcgspsz',
      component: () => import('@/views/fhwbcgspbmpz/Index'),
      needAuth: true,
      id: 'FH10004',
      meta: { title: '分行外包采购审批设置', noCache: true },
    },
  ]
}
export default branchIntentionRouter;
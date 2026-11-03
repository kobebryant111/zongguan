import Layout from '@/views/layout/Layout'
const sourceAlterRouter = {
  path: '/resourceAlter',
  component: Layout,
  alwaysShow: true,
  redirect: '/resourceAlter',
  name: 'resourceAlter',
  needAuth: true,
  id: 'YY10001',
  meta: {
    title: '应用系统资源变更管理',
    icon: 'people'
  },
  children: [
    {
      path: 'useSysResAlterIndex',
      name: 'useSysResAlterIndex',
      component: () => import('@/views/usesysresourcealter/useSysResAlterIndex'),
      alwaysShow: true,
      needAuth: true,
      id: 'YY10011',
      meta: { title: '应用系统资源变更', noCache: true }
    },
    {
      path: 'useSysFlowPeople',
      name: 'useSysFlowPeople',
      component: () => import('@/views/usesysresourcealter/useSysFlowPeople'),
      alwaysShow: true,
      needAuth: true,
      id: 'YY10012',
      meta: { title: '流程人员配置', noCache: true }
    }
  ]
}
export default sourceAlterRouter

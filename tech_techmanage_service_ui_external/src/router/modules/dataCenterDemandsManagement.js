/**
 * 数据中心需求管理
 */

import Layout from '@/views/layout/Layout'
const daCenDemManagementRouter = {
  path: '/dataCenterDemandsManagement',
  component: Layout,
  alwaysShow: true,
  redirect: '/dataCenterDemandsManagement/dataCenterDemandsSearch',
  name: 'dataCenterDemandsManagement',
  needAuth: true,
  id: 'DC10001',
  meta: {
    title: '数据中心需求管理',
    icon: 'icon'
  },
  children: [
    {
      id: 'DC10002',
      path: 'dataCenterDemandsSearch',
      component: () => import('@/views/dataCenterDemandsManagement/dataCenterDemandsSearch'),
      name: 'daCenterDemandManager',
      needAuth: true,
      meta: { title: '数据中心需求管理', noCache: true }
    }
  ]
}
export default daCenDemManagementRouter;
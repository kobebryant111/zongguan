import Layout from '@/views/layout/Layout'
const dataCenterManagementRouter = {
    path: '/dataCenter',
    component: Layout,
    alwaysShow: true,
    redirect: '/dataCenter',
    name: 'dataCenter',
    needAuth: true,
    id: 'DC10001',
    meta: {
      title: '数据中心',
      icon: 'icon'
    },
    children: [
      {
        id: 'DC10002',
        path: '/dataCenterDemandsSearch',
        component: () => import('@/views/dataCenterDemandsManagement/dataCenterDemandsSearch'),
        name: 'dataCenterDemandManager',
        needAuth: true,
        meta: { title: '数据中心需求管理', noCache: true }
      },
      {
        id: 'DC10003',
        path: 'postSupInfoRecordAndEntry',
        component: () => import('@/views/postSupervisionManagement/postSupInfoRecordAndEntry'),
        name: 'postSupManagement',
        needAuth: true,
        meta: { title: '数据中心后督管理', noCache: true }
      },
      {
        id: 'DC10004',
        path: '/securityServices',
        component: () => import('@/views/layout/components/AppMain'),
        name: 'securityServices',
        alwaysShow: true,
        needAuth: true,
        redirect: '/securityServices',
        meta: { title: '安全类服务', noCache: true },
        children: [
          {
            id: 'DC20001',
            path: '/securityServices/accessPermission',
            component: () => import('@/views/accessPermission/accessPermissionSearch'),
            name: 'accessPermission',
            needAuth: true,
            meta: { title: '数据中心门禁权限管理', noCache: true }
          }
        ]
      },
    ]
  }
  export default dataCenterManagementRouter;

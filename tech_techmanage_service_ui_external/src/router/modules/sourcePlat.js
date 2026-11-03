/**
 * 资源申请平台
 */
import Layout from '@/views/layout/Layout'
const bankAssessmentRouter = {
  path: '/resourcePlat',
  component: Layout,
  alwaysShow: true,
  redirect: '/resourcePlat',
  name: 'resourcePlat',
  needAuth: true,
  id: 'ZY10001',
  meta: {
    title: '资源申请平台',
    icon: 'people'
  },
  children: [
    {
      path: 'resourceConfigview',
      name: 'resourceConfigview',
      component: () => import('@/views/resource/config/viewIndex'),
      alwaysShow: true,
      needAuth: true,
      id: 'Z10016',
      meta: { title: '配置查看', noCache: true }
    },
    {
      path: 'resourceConfig',
      name: 'resourceConfig',
      component: () => import('@/views/resource/config/index'),
      alwaysShow: true,
      needAuth: true,
      id: 'Z10011',
      meta: { title: '配置管理', noCache: true }
    },
    {
      path: 'resourceAdd',
      name: 'resourceAdd',
      component: () => import('@/views/resource/added/resourceManageList'),
      alwaysShow: true,
      needAuth: true,
      id: 'Z10012',
      meta: { title: '资源新增', noCache: true }
    },
    {
      path: 'resourceExpand',
      name: 'resourceExpand',
      component: () => import('@/views/resource/expand/index'),
      alwaysShow: true,
      needAuth: true,
      id: 'Z10013',
      meta: { title: '资源扩容', noCache: true }
    },
    {
      path: 'iaasResource',
      name: 'iaasResource',
      component: () => import('@/views/resource/iaas/index'),
      alwaysShow: true,
      needAuth: true,
      id: 'Z10014',
      meta: { title: 'Iaas资源', noCache: true }
    },
    {
      path: 'resourceReport',
      name: 'resourceReport',
      component: () => import('@/views/resource/resourceReport/resourceReportList'),
      alwaysShow: true,
      needAuth: true,
      id: 'Z10015',
      meta: { title: '资产统计报表', noCache: true }
    },
    {
      path: 'resourceFlowPeople',
      name: 'resourceFlowPeople',
      component: () => import('@/views/resource/flow/flowPeople'),
      alwaysShow: true,
      needAuth: true,
      id: 'Z10017',
      meta: { title: '流程人员配置', noCache: true }
    }
  ]
}
export default bankAssessmentRouter

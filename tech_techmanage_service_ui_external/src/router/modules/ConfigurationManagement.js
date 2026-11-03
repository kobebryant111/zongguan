/*
* 配置管理
*/
import Layout from '@/views/layout/Layout'
const ConfigurationManagementRouter = {
  path: '/ConfigurationManagement',
  component: Layout,
  alwaysShow: true,
  redirect: '/ConfigurationManagement',
  name: 'ConfigurationManagement',
  needAuth: true,
  id: 'PZ00001',
  meta: {
    title: '台配置管理',
    icon: 'form'
  },
  children: [
    {
      path: '/InterviewAssessment',
      name: 'InterviewAssessment',
      component: () => import('@/views/InterviewAssessment/index'),
      needAuth: true,
      id: 'PJ00012',
      meta: { title: '面试评价配置表', noCache: true }
    }
  ]
}

export default ConfigurationManagementRouter

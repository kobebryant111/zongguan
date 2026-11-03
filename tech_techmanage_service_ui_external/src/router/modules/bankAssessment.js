/**
 * 行员考核
 */
import Layout from '@/views/layout/Layout'
const bankAssessmentRouter = {
  path: '/bankAssessment',
  component: Layout,
  alwaysShow: true,
  redirect: '/bankAssessment',
  name: 'bankAssessment',
  needAuth: true,
  id: 'H10001',
  meta: {
    title: '行员考核',
    icon: 'people'
  },
  children: [
    {
      path: '/setCenter',
      name: 'setCenter',
      component: () => import('@/views/layout/components/AppMain'),
      redirect: '/setCenter',
      alwaysShow: true,
      needAuth: true,
      id: 'H10002',
      meta: { title: '设置中心', noCache: true },
      children: [
        {
          path: '/pzCenter',
          name: 'pzCenter',
          component: () => import('@/views/bankAssessment/setCenter/pzCenter/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'H10003',
          meta: { title: '配置中心', noCache: true },
        },
        {
          path: '/targetMangerCenter',
          name: 'targetMangerCenter',
          component: () => import('@/views/bankAssessment/setCenter/targetMangerCenter/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'H10004',
          meta: { title: '指标管理中心', noCache: true },
        },
        {
          path: '/khStartCenter',
          name: 'khStartCenter',
          component: () => import('@/views/bankAssessment/setCenter/khStartCenter/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'H10005',
          meta: { title: '考核发起中心', noCache: true },
        },
        {
          path: '/evaluateSetCenter',
          name: 'evaluateSetCenter',
          component: () => import('@/views/bankAssessment/setCenter/evaluateSetCenter/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'H10006',
          meta: { title: '评价设置中心', noCache: true },
        },
        {
          path: '/phjfKh',
          name: 'phjfKh',
          component: () => import('@/views/bankAssessment/setCenter/phjfKh/phjfKh'),
          alwaysShow: true,
          needAuth: true,
          id: 'H10007',
          meta: { title: '平衡计分考核', noCache: true },
        },
        {
          path: '/kjbLzpgList',
          name: 'kjbLzpgList',
          component: () => import('@/views/bankAssessment/setCenter/kjbLzpg/kjbLzpgList'),
          alwaysShow: true,
          needAuth: true,
          id: 'H10008',
          meta: { title: '履职评估考核', noCache: true },
        },
        {
          path: '/gradeSet',
          name: 'gradeSet',
          component: () => import('@/views/bankAssessment/setCenter/pingjiSetCenter/gradeSet'),
          alwaysShow: true,
          needAuth: false,
          id: 'H10009',
          meta: { title: '评级设置中心', noCache: true },
        },
        {
          path: '/gradeStatusChange',
          name: 'gradeStatusChange',
          component: () => import('@/views/bankAssessment/setCenter/gradeStatusChange'),
          alwaysShow: true,
          needAuth: false,
          id: 'H10010',
          meta: { title: '评级状态修改中心', noCache: true },
        }
      ]
    },
    {
      path: 'myKpiDb',
      name: 'myKpiDb',
      component: () => import('@/views/bankAssessment/myKpiDb'),
      alwaysShow: true,
      needAuth: true,
      id: 'H30001',
      meta: { title: '我的待办', noCache: true }
    },
    {
      path: 'myKh',
      name: 'myKh',
      component: () => import('@/views/bankAssessment/employeeKh/myKh'),
      alwaysShow: true,
      needAuth: true,
      id: 'H30002',
      meta: { title: '我的考核', noCache: true }
    },
    {
      path: 'ygKh',
      name: 'ygKh',
      component: () => import('@/views/bankAssessment/employeeKh/ygKh'),
      alwaysShow: true,
      needAuth: true,
      id: 'H30003',
      meta: { title: '员工考核', noCache: true }
    },
  ]
}
export default bankAssessmentRouter;
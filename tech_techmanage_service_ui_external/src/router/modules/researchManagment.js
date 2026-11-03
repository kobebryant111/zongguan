/**
 * 研发综合管理模块
 */
import Layout from '@/views/layout/Layout'
const researchManagerRouter = {
  path: '/researchManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/researchManager',
  name: 'researchManager',
  needAuth: true,
  id: 'YF0001',
  meta: {
    title: '研发综合管理',
    icon: 'sysManagement'
  },
  children: [
    {
      path: '/researchProject',
      name: 'researchProject',
      component: () => import('@/views/researchManager/manager/researchProject'),
      needAuth: true,
      id: 'XM0001',
      meta: { title: '研发项目管理', noCache: true }
    },
    {
      path: '/contract',
      name: 'contract',
      component: () => import('@/views/researchManager/manager/contract'),
      needAuth: true,
      id: 'HT0001',
      meta: { title: '合同管理', noCache: true }
    },
    {
      path: '/passwordManagement',
      name: 'passwordManagement',
      component: () => import('@/views/researchManager/manager/current/passwordManagement'),
      needAuth: true,
      id: 'MG0001',
      meta: { title: '密码管理', noCache: true }
    },
    {
      path: '/RHumanResource',
      name: 'RHumanResource',
      component: () => import('@/views/layout/components/AppMain'),
      needAuth: true,
      id: 'RH0001',
      meta: { title: '研发人力资源管理' },
      children: [
        {
          path: '/RPersonTable',
          name: 'RPersonTable',
          component: () => import('@/views/researchManager/manager/RPersonTable'),
          needAuth: true,
          id: 'RH0002',
          meta: { title: '研发人员总表', noCache: true }
        },
        {
          path: '/RProjectAllocation',
          name: 'RProjectAllocation',
          component: () => import('@/views/researchManager/manager/RProjectAllocation'),
          needAuth: true,
          id: 'RH0003',
          meta: { title: '研发项目分配', noCache: true }
        },
        {
          path: '/RProjectAdministration',
          name: 'RProjectAdministration',
          component: () => import('@/views/researchManager/administration/RProjectAdministration'),
          needAuth: true,
          id: 'RH0007',
          meta: { title: '研发工作管理', noCache: true }
        },
        {

          path: '/attendanceMonthMgr',
          name: 'attendanceMonthMgr',
          component: () => import('@/views/researchManager/manager/attendanceMonthMgr'),
          needAuth: true,
          id: 'RH0004',
          meta: { title: '考勤月份管理', noCache: true }
        },
        {
          path: '/attendanceFillin',
          name: 'attendanceFillin',
          component: () => import('@/views/researchManager/manager/attendanceFillin'),
          needAuth: true,
          id: 'RH0005',
          meta: { title: '考勤填报', noCache: true }
        },
        {
          path: '/attendanceReview',
          name: 'attendanceReview',
          component: () => import('@/views/researchManager/manager/attendanceReview'),
          needAuth: true,
          id: 'RH0006',
          meta: { title: '考勤审核', noCache: true }
        },
        {
          path: '/attendanceView',
          name: 'attendanceView',
          component: () => import('@/views/researchManager/manager/attendanceView'),
          needAuth: true,
          id: 'RH0008',
          meta: { title: '考勤查询', noCache: true }
        }
      ]
    },
    {
      id: 'OA0001',
      path: 'operaAuthManager',
      name: 'operaAuthManager',
      component: () => import('@/views/researchManager/manager/operaAuthManager'),
      needAuth: true,
      meta: { title: '操作权限管理', noCache: true }
    }
  ]
}
export default researchManagerRouter

/**
 * 组织考核
 */
import Layout from '@/views/layout/Layout'
const depAssessment = {
  path: '/depAssessment',
  component: Layout,
  alwaysShow: true,
  redirect: '/depAssessment',
  name: 'depAssessment',
  needAuth: true,
  id: 'ZZ10001',
  meta: {
    title: '组织考核',
    icon: 'people'
  },
  children: [
    {
      path: '/myDepkpiDb',
      name: 'myDepkpiDb',
      component: () => import('@/views/depAssessment/myDepkpiDb/index'),
      alwaysShow: true,
      needAuth: true,
      id: 'ZZ20001',
      meta: { title: '我的待办', noCache: true }
    },
    {
      path: '/depKpiSetCenter',
      name: 'depKpiSetCenter',
      component: () => import('@/views/layout/components/AppMain'),
      redirect: '/depKpiSetCenter',
      alwaysShow: true,
      needAuth: true,
      id: 'ZZ20002',
      meta: { title: '配置中心', noCache: true },
      children: [
        {
          path: '/depRelSet',
          name: 'depRelSet',
          component: () => import('@/views/depAssessment/control/depRelSet/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ200021',
          meta: { title: '组织管理', noCache: true },
        },
        {
          path: '/paramSet',
          name: 'paramSet',
          component: () => import('@/views/depAssessment/control/paramSet/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ200022',
          meta: { title: '考核参数设置', noCache: true },
        },
        {
          path: '/launchCenter',
          name: 'launchCenter',
          component: () => import('@/views/depAssessment/control/launchCenter/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ200023',
          meta: { title: '发起中心', noCache: true },
        },
      ]
    },
    {
      path: '/ejbkh',
      name: 'ejbkh',
      component: () => import('@/views/layout/components/AppMain'),
      redirect: '/ejbkh',
      alwaysShow: true,
      needAuth: true,
      id: 'ZZ20003',
      meta: { title: '二级部考核', noCache: true },
      children: [
        {
          path: '/ejbTarget',
          name: 'ejbTarget',
          component: () => import('@/views/depAssessment/target/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30301',
          meta: { title: '指标配置', noCache: true },
        },
        {
          path: '/ejbModel',
          name: 'ejbModel',
          component: () => import('@/views/depAssessment/model/depModel'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30302',
          meta: { title: '模板分配', noCache: true },
        },
        {
          path: '/ejbGrade',
          name: 'ejbGrade',
          component: () => import('@/views/depAssessment/grade/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30305',
          meta: { title: '评级配置', noCache: true },
        },
        {
          path: '/ejbkhcx',
          name: 'ejbkhcx',
          component: () => import('@/views/depAssessment/khinquire/khcx'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30303',
          meta: { title: '考核查询', noCache: true },
        },
        {
          path: '/ggxzbcx',
          name: 'ggxzbcx',
          component: () => import('@/views/depAssessment/khinquire/ggxzbcx'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30304',
          meta: { title: '公共项指标查询', noCache: true },
        }
      ]
    },
    {
      path: '/zxkh',
      name: 'zxkh',
      component: () => import('@/views/layout/components/AppMain'),
      redirect: '/zxkh',
      alwaysShow: true,
      needAuth: true,
      id: 'ZZ20004',
      meta: { title: '中心考核', noCache: true },
      children: [
        {
          path: '/zxTarget',
          name: 'zxTarget',
          component: () => import('@/views/depAssessment/target/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30401',
          meta: { title: '指标配置', noCache: true },
        },
        {
          path: '/zxModel',
          name: 'zxModel',
          component: () => import('@/views/depAssessment/model/depModel'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30402',
          meta: { title: '模板分配', noCache: true },
        },
        {
          path: '/zxGrade',
          name: 'zxGrade',
          component: () => import('@/views/depAssessment/grade/index'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30405',
          meta: { title: '评级配置', noCache: true },
        },
        {
          path: '/zxkhcx',
          name: 'zxkhcx',
          component: () => import('@/views/depAssessment/khinquire/khcx'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30403',
          meta: { title: '考核查询', noCache: true },
        },
        {
          path: '/zxggxzbcx',
          name: 'zxggxzbcx',
          component: () => import('@/views/depAssessment/khinquire/ggxzbcx'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30404',
          meta: { title: '公共项指标查询', noCache: true },
        }
      ]
    },
    {
      path: '/yjbkh',
      name: 'yjbkh',
      component: () => import('@/views/layout/components/AppMain'),
      redirect: '/yjbkh',
      alwaysShow: true,
      needAuth: true,
      id: 'ZZ20005',
      meta: { title: '一级部考核', noCache: true },
      children: [
        {
          path: '/yjbTarget',
          name: 'yjbTarget',
          component: () => import('@/views/depAssessment/yjb/yjbTarget'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30501',
          meta: { title: '一级部指标配置', noCache: true },
        },
        {
          path: '/yjbkhcx',
          name: 'yjbkhcx',
          component: () => import('@/views/depAssessment/khinquire/khcx'),
          alwaysShow: true,
          needAuth: true,
          id: 'ZZ30502',
          meta: { title: '一级部考核查询', noCache: true },
        }
      ]
    }
  ]
}
export default depAssessment;
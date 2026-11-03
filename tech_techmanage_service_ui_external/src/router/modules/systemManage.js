/**
 * 系统管理
 */
import Layout from '@/views/layout/Layout'
const systemManageRouter = {
  path: '/systemManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/systemManager/OrganizationManager',
  name: 'organizationManager',
  needAuth: true,
  id: 'S10010',
  meta: {
    title: '系统管理',
    icon: 'star'
  },
  children: [
    {
      id: 'S10011',
      path: 'organizationUser',
      component: () => import('@/views/systemManager/OrganizationUser'),
      name: 'organizationUser',
      needAuth: true,
      meta: { title: '机构人员管理', noCache: true }
    },
    {
      id: 'S10012',
      path: 'userRoles',
      component: () => import('@/views/systemManager/UserRoles'),
      name: 'userRoles',
      needAuth: true,
      meta: { title: '用户角色管理', noCache: true }
    },
    {
      id: 'S10013',
      path: 'menusManager',
      component: () => import('@/views/systemManager/MenusManager'),
      name: 'menusManager',
      needAuth: true,
      meta: { title: '菜单管理', noCache: true }
    },
    {
      id: 'S10014',
      path: 'authManager',
      component: () => import('@/views/systemManager/AuthManager'),
      name: 'authManager',
      needAuth: true,
      meta: { title: '权限管理', noCache: true }
    },
    {
      id: 'S10015',
      path: 'kjbpz',
      component: () => import('@/views/systemManager/Kjbpz'),
      name: 'kjbpz',
      needAuth: true,
      meta: { title: '分行科技配置', noCache: true }
    },
    {
      id: 'S10016',
      path: 'flowlog',
      component: () => import('@/views/systemManager/FlowLog'),
      name: 'flowlog',
      needAuth: true,
      meta: { title: '日志维护', noCache: true }
    },
    {
      id: 'S10017',
      path: 'quartzManager',
      component: () => import('@/views/systemManager/QuartzManager'),
      name: 'quartzManager',
      needAuth: true,
      meta: { title: '定时器管理', noCache: true }
    },
    {
      id: 'S10018',
      path: 'relateuser',
      component: () => import('@/views/systemManager/relateuser/PageList'),
      name: 'relateuser',
      needAuth: true,
      meta: { title: '外包账号关联', noCache: true }
    },
    {
      id: 'S10019',
      path: 'paltformFeedback',
      component: () => import('@/views/systemManager/paltformFeedback'),
      name: 'paltformFeedback',
      needAuth: false,
      meta: { title: '平台反馈', noCache: true }
    },
    {
      id: 'S10020',
      path: 'loginLog',
      component: () => import('@/views/newLog/LoginLog'),
      name: 'loginLog',
      needAuth: true,
      meta: { title: '登录日志管理', noCache: true }
    },
    {
      id: 'S10021',
      path: 'menuLog',
      component: () => import('@/views/newLog/MenuLog'),
      name: 'menuLog',
      needAuth: true,
      meta: { title: '菜单日志管理', noCache: true }
    },
    {
      id: 'S10022',
      path: 'dataDictManagement',
      component: () => import('@/views/systemManager/dataDictManagement'),
      name: 'dataDictManagement',
      needAuth: true,
      meta: { title: '字典管理', noCache: true }
    }
  ]
}
export default systemManageRouter

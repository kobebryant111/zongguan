import Layout from '@/views/layout/Layout'
const boolCache = process.env.NODE_ENV !== 'development'

const sysManagerRouter = {
  path: '/sysManagement',
  component: Layout,
  alwaysShow: true,
  redirect: '/sysManagement/roleManagement',
  name: 'sysManagement',
  needAuth: true,
  id: 'M01000',
  meta: {
    title: '系统管理',
    icon: 'sysManagement'
  },
  children: [
    {
      path: 'roleManagement',
      component: () => import('@/views/sysManagement/roleManagement'),
      name: 'roleManagement',
      needAuth: true,
      id: 'M10004',
      meta: { title: '角色管理', noCache: true }
    }, {
      path: 'menuPermission',
      hidden: true,
      component: () => import('@/views/sysManagement/menuPermission'),
      name: 'menuPermission',
      needAuth: true,
      id: 'M10004',
      meta: { title: '菜单权限', noCache: true }
    }, {
      path: 'menuManage',
      hidden: false,
      component: () => import('@/views/sysManagement/menuManage'),
      name: 'menuManage',
      needAuth: boolCache,
      id: 'M10006',
      meta: { title: '菜单管理', noCache: boolCache }
    }, {
      path: 'userManagement',
      component: () => import('@/views/sysManagement/userManagement'),
      name: 'userManagement',
      needAuth: true,
      id: 'M10005',
      meta: { title: '用户管理', noCache: true }
    }, {
      path: 'newUser',
      hidden: true,
      component: () => import('@/views/sysManagement/newUser'),
      name: 'newUser',
      needAuth: false,
      id: '001003',
      meta: { title: '编辑用户', noCache: true }
    }
  ]
}

export default sysManagerRouter

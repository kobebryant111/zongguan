/**
 * 用户中心
 * 用户设置
 */
import Layout from '@/views/layout/Layout'

const userCenterRouter = {
  path: '/userCenter',
  component: Layout,
  alwaysShow: false,
  redirect: '/userCenter/userCenter',
  name: 'userCenter',
  needAuth: false,
  id: '-',
  hidden: true,
  meta: {
    title: '用户设置',
    icon: 'user'
  },
  children: [
    {
      path: 'userCenter',
      component: () => import('@/views/userCenter/userCenter'),
      name: 'userCenter',
      needAuth: false,
      id: '-', 
      meta: { title: '用户设置', noCache: true }
    },{
      id: 'singleLogin01',
        path: 'singleLogin',
        component: () => import('@/views/userCenter/userSingleLogin'),
        name: 'usersingleLogin',
        needAuth: true,
        meta: { 
          requireAuth:false,
         }
    }
  ]
}

export default userCenterRouter

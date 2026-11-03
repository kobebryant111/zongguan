/**
 * 综合管理
 */
import Layout from '@/views/layout/Layout'
const comprehensiveRouter = {
  path: '/comprehensiveManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/comprehensiveManager/wxManager',
  name: 'comprehensiveManager',
  needAuth: true,
  id: 'C10001',
  meta: {
    title: '综合管理',
    icon: 'icon'
  },
  children: [{ 
      path : 'wxManager',
      name : 'wxManager',
      component : () => import('@/views/comprehensiveManager/WxManager/index'),
      redirect: '/comprehensiveManager/wxManager/chickenSoupLib',
      alwaysShow: true,
      needAuth: true,
      id: 'C10002',
      meta: {
        title: '文化宣传管理',
        icon: 'drag'
      },
      children: [{
          id: 'C10003',
          path: 'chickenSoupLib',
          component: () => import('@/views/comprehensiveManager/WxManager/ChickenSoupLib'),
          name: 'chickenSoupLib',
          needAuth: true,
          meta: { title: '鸡汤库管理', noCache: true }
        },
        {
          id: 'C10004',
          path: 'tastBank',
          component: () => import('@/views/comprehensiveManager/WxManager/TastBank'),
          name: 'tastBank',
          needAuth: true,
          meta: { title: '题库管理', noCache: true }
        },]
    },
    // { 
    //   path : 'wbryManager',
    //   name : 'wbryManager',
    //   component : () => import('@/views/flowsManager/wbzr/index'),
    //   alwaysShow: true,
    //   needAuth: false,
    //   id: 'C10005',
    //   meta: {
    //     title: '外包人员准入申请',
    //     icon: 'drag'
    //   }
    // },
   
    // { 
    //   path : 'wbmsManager',
    //   name : 'wbmsManager',
    //   component : () => import('@/views/flowsManager/wbms/interviewManger'),
    //   alwaysShow: true,
    //   needAuth: false,
    //   id: 'C10006',
    //   meta: {
    //     title: '面试管理',
    //     icon: 'drag'
    //   }
    // },

  ]
}
export default comprehensiveRouter;
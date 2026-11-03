/**
 * 信息科技外包管理
 */
import Layout from '@/views/layout/Layout'
const informationTechnologyRouter = {
  path: '/inforTch',
  component: Layout,
  alwaysShow: true,
  redirect: '/inforTch',
  name: 'inforTch',
  needAuth: true,
  id: 'XK0010',
  meta: {
    title: '信息科技外包管理',
    icon: 'people'
  },
  children: [
    {
      path: '/inforTchAply',
      name: 'inforTchAply',
      component: () => import('@/views/inforTch/manager/inforTchAply'),
      needAuth: true,
      id: 'XK0011',
      meta: { title: '信息科技外包申请', noCache: true }
    },
    {
      path: '/inforTchArvl',
      name: 'inforTchArvl',
      component: () => import('@/views/inforTch/manager/inforTchArvl'),
      needAuth: true,
      id: 'XK0012',
      meta: { title: '信息科技外包审批', noCache: true }
    },
    {
      path: '/inforTchSelt',
      name: 'inforTchSelt',
      component: () => import('@/views/inforTch/manager/inforTchSelt'),
      needAuth: true,
      id: 'XK0013',
      meta: { title: '信息科技外包查询', noCache: true }
    }
  ]
}

export default informationTechnologyRouter

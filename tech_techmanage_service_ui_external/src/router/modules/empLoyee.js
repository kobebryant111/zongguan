/**
 * 综合管理
 */
import Layout from '@/views/layout/Layout'
const hangyuanRouter = {
  path: '/bankManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/bankManager',
  name: 'bankManager',
  needAuth: true,
  id: 'HY0001',
  meta: {
    title: '员工管理',
    icon: 'user'
  },
  children: [
    {
      path: '/bankKq',
      name: 'bankKq',
      component: () => import('@/views/kqManager/bankKqInfo/index'),
      needAuth: true,
      id: 'HKQ001',
      meta: { title: '员工考勤', noCache: true }
    }

  ]
}
export default hangyuanRouter

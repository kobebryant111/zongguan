/**
 * 员工调休
 */
import Layout from '@/views/layout/Layout'
const tiaoXiuRouter = {
  path: '/bankTx',
  component: Layout,
  alwaysShow: true,
  redirect: '/bankTx',
  name: 'bankTx',
  needAuth: true,
  id: 'TX0001',
  meta: {
    title: '员工调休',
    icon: 'user'
  },
  children: [
    {
      path: '/integInfo',
      name: 'integInfo',
      component: () => import('@/views/layout/components/AppMain'),
      needAuth: true,
      alwaysShow: true,
      id: 'JF0001',
      meta: { title: '积分详情', noCache: true },
      children: [
        {
          path: '/unrecogInteg',
          name: 'unrecogInteg',
          component: () => import('@/views/bankTx/integInfo/unrecogInteg'),
          needAuth: true,
          id: 'JF0002',
          meta: { title: '未确认积分', noCache: true }
        },
        {
          path: '/approverDetail',
          name: 'approverDetail',
          component: () => import('@/views/bankTx/integInfo/approverDetail'),
          needAuth: true,
          id: 'JF0003',
          meta: { title: '审批中积分', noCache: true }
        },
        {
          path: '/recogDetail',
          name: 'recogDetail',
          component: () => import('@/views/bankTx/integInfo/recogDetail'),
          needAuth: true,
          id: 'JF0004',
          meta: { title: '已确认积分', noCache: true }
        }
      ]
    },

    {
      path: '/adrestInfo',
      name: 'adrestInfo',
      component: () => import('@/views/bankTx/integInfo/adrestInfo'),
      needAuth: true,
      id: 'XQ0001',
      meta: { title: '调休详情', noCache: true }
    },
    {
      path: '/approvalManage',
      name: 'approvalManage',
      redirect: '/approvalManage',
      alwaysShow: true,
      component: () => import('@/views/layout/components/AppMain'),
      needAuth: true,
      id: 'SP0001',
      meta: { title: '审批管理' },
      children: [
        {
          path: '/PointsApproval',
          name: 'PointsApproval',
          component: () => import('@/views/bankTx/integInfo/pointsApproval'),
          needAuth: true,
          id: 'JF0006',
          meta: { title: '积分审批', noCache: true }
        },
        {
          path: '/LeaveApproval',
          name: 'LeaveApproval',
          component: () => import('@/views/bankTx/integInfo/leaveApproval'),
          needAuth: true,
          id: 'TX0002',
          meta: { title: '调休审批', noCache: true }
        }
      ]
    }

  ]

}
export default tiaoXiuRouter

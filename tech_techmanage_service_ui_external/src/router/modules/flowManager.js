/**
 * 流程管理
 */
import Layout from '@/views/layout/Layout'
const flowManagerRouter = {
  path: '/flowManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/flowManager/FlowType',
  name: 'flowManager',
  needAuth: true,
  id: 'F10001',
  meta: {
    title: '流程管理',
    icon: 'form'
  },
  children: [
    {
      id: 'F10002',
      path: 'flowtype',
      component: () => import('@/views/flowManager/flowType'),
      name: 'flowtype',
      needAuth: true,
      meta: { title: '流程调度管理员维护', noCache: true }
    }]
}
export default flowManagerRouter;
/**
 * 合同管理
 */
import Layout from '@/views/layout/Layout'
const expenseSettlementRouter = {
  path: '/expenseSettlement',
  component: Layout,
  alwaysShow: true,
  redirect: '/expenseSettlement',
  name: 'expenseSettlement',
  needAuth: true,
  id: 'FY10001',
  meta: {
    title: '供应商管理',
    icon: 'form'
  }
  // children: [
  //     {
  //       path: '/infoChange',
  //       name: 'infoChange',
  //       component: () => import('@/views/crewManager/wbrz/wbrzInfoChange'),
  //       needAuth: true,
  //       id: 'IC000101',
  //       meta: { title: '信息变更', noCache: true }
  //     },
  // ]
}
export default expenseSettlementRouter

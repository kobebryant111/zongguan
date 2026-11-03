import Layout from '@/views/layout/Layout'
/**
 * 宁波地区管理部流程
 */
export default {
  path: '/ngFlow',
  component: Layout,
  redirect: '/ningGuan/deviceChange',
  name: 'ngFlow',
  meta: { title: '宁波地区管理部流程', noCache: true },
  needAuth: true,
  id: 'NG00001',
  children: [
    {
      path: 'newBranchBankAcceptance',
      component: () => import('@/views/ningGuan/newBranchBankAcceptance/Aggregation'),
      name: 'newBranchBankAcceptance.aggregation',
      meta: { title: '新支行验收流程', noCache: true }
    },
    {
      path: 'newBranchBankAcceptance/initiate',
      component: () => import('@/views/ningGuan/newBranchBankAcceptance/InitiateXzh'),
      name: 'newBranchBankAcceptance.initiate',
      meta: { title: '新支行验收流程-发起', noCache: true }
    },
    {
      path: 'flowQuery',
      component: () => import('@/views/ningGuan/flowQuery/Pagers'),
      name: 'newBranchBankAcceptance.pager',
      meta: { title: '新支行验收流程-查询', noCache: true }
    },
  ]
}
/**
 * 费用支付管理(因为已上线,不好改名字导致有点歧义)
 */
import Layout from '@/views/layout/Layout'
const supplierManagerRouter = {
  path: '/supplierManagerRouter',
  component: Layout,
  alwaysShow: true,
  redirect: '/supplierManagerRouter',
  name: 'supplierManagerRouter',
  needAuth: true,
  id: 'GY10001',
  meta: {
    title: '文档管理',
    icon: 'form'
  },
  children: [
    {
      path: '/fileManager',
      name: 'fileManager',
      component: () => import('@/views/fileManager/index'),
      needAuth: true,
      id: 'ZJ1000102',
      meta: { title: '附件管理', noCache: true }
    },
    {
      path: '/contractManager',
      name: 'contractManager',
      component: () => import('@/views/contractManager/index'),
      needAuth: true,
      id: 'ZJ1000101',
      meta: { title: '合同管理', noCache: true }
    },
    {
      path: '/payConfirm',
      name: 'payConfirm',
      component: () => import('@/views/expenseSettlement/payConfirm/index'),
      needAuth: true,
      id: 'FY1000101',
      meta: { title: '支付确认', noCache: true }
    },
    {
      path: '/expenseSettle',
      name: 'expenseSettle',
      component: () => import('@/views/expenseSettlement/expenseSettle/index'),
      needAuth: true,
      id: 'FY1000102',
      meta: { title: '费用结算', noCache: true }
    },   
    // {
    //   path: '/supplierAssessment',
    //   name: 'supplierAssessment',
    //   component: () => import('@/views/layout/components/AppMain'),
    //   alwaysShow: true,
    //   redirect: '/supplierAssessment',
    //   needAuth: true,
    //   id: 'KH10001',
    //   meta: {
    //     title: '供应商考核',
    //   },
    //   children: [
    //     {
    //       path: '/rlAssessment',
    //       name: 'rlAssessment',
    //       component: () => import("@/views/supplierAssessment/rlAssessment/index"),
    //       needAuth: true,
    //       id: "KH1000101",
    //       meta: { title: '人力外包考核', noCache: true }
    //     },
    //     {
    //       path: '/xmAssessment',
    //       name: 'xmAssessment',
    //       component: () => import("@/views/supplierAssessment/xmAssessment/index"),
    //       needAuth: true,
    //       id: "KH1000102",
    //       meta: { title: "项目外包考核", noCache: true}
    //     }
    //   ]
    // },
    // {
    //   path: '/supplierhmc',
    //   name: 'supplierhmc',
    //   component: () => import('@/views/supplierhmc/index'),
    //   needAuth: true,
    //   id: 'GY1000102',
    //   meta: { title: '供应商花名册', noCache: true }
    // },
    // {
    //   path: '/supplierViolateRecord',
    //   name: 'supplierViolateRecord',
    //   component: () => import('@/views/supplierViolateRecord/index'),
    //   needAuth: true,
    //   id: 'GY1000103',
    //   meta: { title: '供应商违规记录', noCache: true }
    // }, 
    //  {
    //   path: '/riskAssessment',
    //   name: 'riskAssessment',
    //   component: () => import('@/views/riskAssessment/index'),
    //   needAuth: true,
    //   id: 'FX1000101',
    //   meta: { title: '风险评估', noCache: true }
    // },
  ]
}
export default supplierManagerRouter;
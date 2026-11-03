import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/* Router Modules */
import sysManagementRouter from './modules/sysManagement'
import gateManagerRouter from './modules/gateManager'
import userManagerRouter from './modules/userCenter'
import systemManageRouter from './modules/systemManage'
import comprehensiveManagerRouter from './modules/comprehensiveManagers'
import researchManagerRouter from './modules/researchManagment'
import flowManagerRouter from './modules/flowManager'
import bankAssessmentRouter from './modules/bankAssessment'
import sourcePlatRouter from './modules/sourcePlat'
import branchIntentionRouter from './modules/branchIntention'
import depAssessmenRouter from './modules/depAssessment'
import sourceAlterRouter from './modules/resourceAlter'
import dataCenterManagementRouter from './modules/dataCenterManagement'
import supplierManagerRouter from './modules/supplierManagement'
import expenseSettlementRouter from './modules/expenseSettlement'
import expensepaymentManagerRouter from './modules/expensepaymentManagement'
import accountManagerRouter from './modules/accountManager'
import empLoyeeRouter from './modules/empLoyee'
import testManagementRouter from './modules/testManagement'
import safetyManagementRouter from './modules/safetyManagement'
import ConfigurationManagementRouter from './modules/ConfigurationManagement'
import tiaoXiuRouter from './modules/empLeave'
import informationTechnologyRouter from './modules/informationTechnology'
// import proTransferRouter from './modules/proTransfer'
/** note: Submenu only appear when children.length>=1
 *  detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [

  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  },
  {
    path: '/refresh',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '',
        component: () => import('@/views/redirect/refresh')
      }
    ]
  },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/myTask/index'),
        name: 'dashboard',
        meta: { title: '首页', icon: 'component', noCache: true }
      }
    ]
  },
  // 外包入职
  {
    path: '/wbrz',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/wbrz',
        name: 'wbryzr',
        component: () => import('@/views/crewManager/wbrz/index'),
        hidden: true,
        description: '外包入职'
      }
    ]
  },
  {
    path: '/InforTchArvl',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/InforTchArvl',
        name: 'InforTchArvl',
        component: () => import('@/views/inforTch/manager/inforTchArvl'),
        hidden: true,
        description: '信息科技外包审批'
      }
    ]
  },
  {
    path: '/TchSeltDetail',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/TchSeltDetail',
        name: 'TchSeltDetail',
        component: () => import('@/views/inforTch/manager/tchSeltDetail'),
        hidden: true,
        description: '信息科技外包查询详情'
      }
    ]
  },
  {
    path: '/InforTchSelt',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/InforTchSelt',
        name: 'InforTchSelt',
        component: () => import('@/views/inforTch/manager/inforTchSelt'),
        hidden: true,
        description: '信息科技外包查询详情'
      }
    ]
  },
  // 资源申请流程处理页面
  {
    path: '/resourceFlow',
    name: 'resourceFlow',
    component: () => import('@/views/resource/flow/index'),
    hidden: true
  },

  // 应用系统资源变更
  {
    path: '/useSysResourceAlter',
    name: 'useSysResourceAlter',
    component: () => import('@/views/usesysresourcealter/useSysResourceAlter'),
    hidden: true
  },

  // 虚拟桌面申请
  {
    path: '/xnzmsqNew',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/xnzmsqNew',
        name: 'xnzmsqNew',
        component: () => import('@/views/crewManager/wbrz/xnzmApprove'),
        hidden: true,
        description: '虚拟桌面申请'
      }
    ]
  },

  // 闸机权限申请
  {
    path: '/authApply',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/authApply',
        name: 'zjqxlc',
        component: () => import('@/views/gateManager/authority/flow/index'),
        hidden: true,
        description: '闸机权限申请'
      }
    ]
  },

  // 外包需求
  {
    path: '/wbRequire',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/wbRequire',
        name: 'wbrlxq',
        component: () => import('@/views/crewManager/wbRequire/index'),
        hidden: true,
        description: '外包需求'
      }
    ]
  },

  // 外包面试
  {
    path: '/interview',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/interview',
        name: 'wbryms',
        component: () => import('@/views/flowsManager/wbms/index'),
        hidden: true,
        description: '外包面试'
      }
    ]
  },

  // 面试详情(人力需求后台管理)
  {
    path: '/msDetail',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/msDetail',
        name: 'msDetail',
        component: () => import('@/views/flowsManager/wbms/interviewDetail'),
        description: '面试详情'
      }
    ]
  },

  // 面试详情(面试后台管理)
  {
    path: '/interviewJcDetail',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/interviewJcDetail',
        name: 'interviewJcDetail',
        component: () => import('@/views/flowsManager/wbms/interviewJcDetail'),
        description: '面试详情'
      }
    ]
  },

  // 外包离场流程
  {
    path: '/wbrylc',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/wbrylc',
        name: 'wbrylc',
        component: () => import('@/views/crewManager/wblc/index'),
        description: '离场流程'
      }
    ]
  },
  // 信息科技外包立项
  {
    path: '/xxkjwblx',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/xxkjwblx',
        name: 'xxkjwblx',
        component: () => import('@/views/inforTch/approval/index'),
        description: '信息科技外包立项流程'
      }
    ]
  },
  // 外包离场流程完成
  {
    path: '/wblcFinish',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/wblcFinish',
        name: 'wblcFinish',
        component: () => import('@/views/crewManager/wblc/wblcApplyFinish'),
        description: '离场流程完成'
      }
    ]
  },

  // 考勤节假日数据导入
  {
    path: '/jjrsjdr',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/jjrsjdr',
        name: 'jjrsjdr',
        component: () => import('@/views/kqManager/calendar/flow/index'),
        description: '节假日数据导入'
      }
    ]
  },

  // 外包信息变更流程
  {
    path: '/newwbryxxbg',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/newwbryxxbg',
        name: 'newwbryxxbg',
        component: () => import('@/views/crewManager/wbryxxbg/index'),
        description: '外包信息变更流程'
      }
    ]
  },
  // 新外包信息变更流程
  {
    path: '/xxbgsq',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/xxbgsq',
        name: 'xxbgsq',
        component: () => import('@/views/crewManager/wbNewXxbg/index'),
        description: '外包信息变更流程'
      }
    ]
  },

  // 外包信息变更流程完成
  {
    path: '/wbxxbgFinish',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/wbxxbgFinish',
        name: 'wbxxbgFinish',
        component: () => import('@/views/crewManager/wbryxxbg/xxbgApplyFinish'),
        description: '外包信息变更流程完成'
      }
    ]
  },

  // 人员移交申请流程
  {
    path: '/ryyjsq',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/ryyjsq',
        name: 'ryyjsq',
        component: () => import('@/views/crewManager/transferPersonnel/index'),
        description: '人员移交申请流程'
      }
    ]
  },

  // 领用门禁申请流程
  {
    path: '/lymjsq',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/lymjsq',
        name: 'lymjsq',
        component: () => import('@/views/accountManager/accessCard/receiveCard/index'),
        description: '领用门禁申请流程'
      }
    ]
  },

  // 归还门禁申请流程
  {
    path: '/ghmjsq',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/ghmjsq',
        name: 'ghmjsq',
        component: () => import('@/views/accountManager/accessCard/returnCard/index'),
        description: '归还门禁申请流程'
      }
    ]
  },

  // 花名册档案
  {
    path: '/hmcArchives',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/hmcArchives',
        name: 'hmcArchives',
        component: () => import('@/views/crewManager/hmcArchives/hmcArchives'),
        description: '花名册档案'
      }
    ]
  },
  // 修改项目
  {
    path: '/projectEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/projectEdit',
        name: 'projectEdit',
        component: () => import('@/views/crewManager/manager/projectInfo'),
        hidden: true,
        description: '项目信息'
      }
    ]
  },
  // 文档管理
  {
    path: '/contractEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/contractEdit',
        name: 'contractEdit',
        component: () => import('@/views/contractManager/contractAdd'),
        hidden: true,
        description: '合同信息'
      },
      {
        path: '/contractDetail',
        name: 'contractDetail',
        component: () => import('@/views/contractManager/contractDetail'),
        hidden: true,
        description: '合同详情'
      },
      {
        path: '/fileEdit',
        name: 'fileEdit',
        component: () => import('@/views/fileManager/fileAdd'),
        hidden: true,
        description: '附件信息'
      },
      {
        path: '/fileManage',
        name: 'fileManage',
        component: () => import('@/views/fileManager/fileManage'),
        hidden: true,
        description: '附件审批'
      },
      {
        path: '/fileDetail',
        name: 'fileDetail',
        component: () => import('@/views/fileManager/fileDetail'),
        hidden: true,
        description: '附件详情'
      }
    ]
  },
  // 支付确认
  {
    path: '/payConfirmEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/payConfirmEdit',
        name: 'payConfirmEdit',
        component: () => import('@/views/expenseSettlement/payConfirm/payAdd'),
        hidden: true,
        description: '支付确认'
      },
      {
        path: '/payConfirmDetail',
        name: 'payConfirmDetail',
        component: () => import('@/views/expenseSettlement/payConfirm/payDetail'),
        hidden: true,
        description: '支付详情'
      },
      {
        path: '/expenseSettleInfo',
        name: 'expenseSettleInfo',
        component: () => import('@/views/expenseSettlement/expenseSettle/expenseSettleIndex'),
        hidden: true,
        description: '新增费用结算'
      },
      {
        path: '/expenseSettleDetail',
        name: 'expenseSettleDetail',
        component: () => import('@/views/expenseSettlement/expenseSettle/expenseSettleDetail'),
        hidden: true,
        description: '费用详情'
      },
      {
        path: '/mergeInfo',
        name: 'mergeInfo',
        component: () => import('@/views/expenseSettlement/expenseSettle/mergeInfo'),
        hidden: true,
        description: '合并信息'
      },
      {
        path: '/mergeApproval',
        name: 'mergeApproval',
        component: () => import('@/views/expenseSettlement/expenseSettle/mergeApproval'),
        hidden: true,
        description: '合并信息审批'
      },
      {
        path: '/personInfo',
        name: 'personInfo',
        component: () => import('@/views/expenseSettlement/expenseSettle/personInfo'),
        hidden: true,
        description: '个人信息'
      }
    ]
  },
  // 台账
  {
    path: '/accountAdd',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/accountAdd',
        name: 'accountAdd',
        component: () => import('@/views/accountManager/accountAdd'),
        hidden: true,
        description: '支付确认'
      },
      {
        path: '/accountDetail',
        name: 'accountDetail',
        component: () => import('@/views/accountManager/accountDetail'),
        hidden: true,
        description: '合同详情'
      }
    ]
  },
  // 非科技外包人员台账
  {
    path: '/personInfoAdd',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/personInfoAdd',
        name: 'personInfoAdd',
        component: () => import('@/views/accountManager/personLedger/add'),
        hidden: true,
        description: '人员新增'
      },
      {
        path: '/personInfoUpdate',
        name: 'personInfoUpdate',
        component: () => import('@/views/accountManager/personLedger/update'),
        hidden: true,
        description: '人员修改'
      },
      {
        path: '/personDetail',
        name: 'personDetail',
        component: () => import('@/views/accountManager/personLedger/view'),
        hidden: true,
        description: '人员详情'
      }
    ]
  },
  // 个人详情
  // {
  //   path: '/detils',
  //   component: Layout,
  //   hidden: true,
  //   children: [
  //     {
  //       path: '/detils',
  //       name: 'detils',
  //       component: () => import('@/views/kqManager/wbAttendance/info'),
  //       hidden: true,
  //       description: '详情'
  //     }
  //   ]
  // },
  // 考勤机配置修改
  {
    path: '/kpMachineConfigFix',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/kpMachineConfigFix',
        name: 'kpMachineConfigFix',
        component: () => import('@/views/kqManager/kqMachineConfig/info'),
        hidden: true,
        description: '考勤机配置修改'
      }
    ]
  },
  // 修改系统
  {
    path: '/systemEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/systemEdit',
        name: 'systemEdit',
        component: () => import('@/views/crewManager/manager/systemInfo'),
        hidden: true,
        description: '系统信息'
      }
    ]
  },
  // 修改系统
  {
    path: '/calendarEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/calendarEdit',
        name: 'calendarEdit',
        component: () => import('@/views/kqManager/calendar/info'),
        hidden: true,
        description: '节假日信息'
      }
    ]
  },
  // 个人详情
  {
    path: '/detils',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/detils',
        name: 'detils',
        component: () => import('@/views/kqManager/wbAttendance/info'),
        hidden: true,
        description: '详情'
      }
    ]
  },
  // 行员考勤详情
  {
    path: '/bankDetils',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/bankDetils',
        name: 'bankDetils',
        component: () => import('@/views/kqManager/bankKqInfo/info'),
        hidden: true,
        description: '详情'
      }
    ]
  },

  // 合同管理关联外包
  {
    path: '/relateOutsource',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/relateOutsource',
        name: 'relateOutsource',
        component: () => import('@/views/crewManager/supplierContract/info'),
        hidden: true,
        description: '供应商合同关联管理'
      }
    ]
  },

  // 提前入场新增页面
  {
    path: '/tqrcsq',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/tqrcsq',
        name: 'tqrcsq',
        component: () => import('@/views/crewManager/tqrcApplyfor/tqrcshFlows/index'),
        hidden: true,
        description: '提前入场新增'
      }
    ]
  },
  // 人力考核新增页面
  {
    path: '/gyskhrl',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/gyskhrl',
        name: 'gyskhrl',
        component: () => import('@/views/supplierAssessment/rlAssessment/flows/index'),
        hidden: true,
        description: '人力考核新增'
      }
    ]
  },
  // 项目考核新增页面
  {
    path: '/gyskhxm',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/gyskhxm',
        name: 'gyskhxm',
        component: () => import('@/views/supplierAssessment/xmAssessment/flows/index'),
        hidden: true,
        description: '项目考核新增'
      }
    ]
  },
  // 风险评估新增页面
  {
    path: '/fxpgsq',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/fxpgsq',
        name: 'fxpgsq',
        component: () => import('@/views/riskAssessment/flows/index'),
        hidden: true,
        description: '风险评估新增'
      }
    ]
  },

  // 人员属性变更详情
  {
    path: '/projectTransferInfo',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/outsrcingAttrChg',
        name: 'outsrcingAttrChg',
        component: () => import('@/views/crewManager/projectTransfer/lcfqFlows/index'),
        hidden: true,
        description: '新增'
      },
      {
        path: '/outsrcingAttrChgDetail',
        name: 'outsrcingAttrChgDetail',
        component: () => import('@/views/crewManager/projectTransfer/info'),
        hidden: true,
        description: '人员属性变更详情'
      }
    ]
  },
  // 项目单价调整
  {
    path: '/outsrcingPriceChg',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/outsrcingPriceChg',
        name: 'outsrcingPriceChg',
        component: () => import('@/views/crewManager/priceAdjustment/lcfqFlows/index'),
        hidden: true,
        description: '新增'
      },
      {
        path: '/outsrcingPriceChgDetail',
        name: 'outsrcingPriceChgDetail',
        component: () => import('@/views/crewManager/priceAdjustment/info'),
        hidden: true,
        description: '单价调整详情'
      }
    ]
  },
  // 外包人员绩效考核
  {
    path: '/outsrcEmpAssess',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/outsrcEmpAssess',
        name: 'outsrcEmpAssess',
        component: () => import('@/views/crewManager/wbPersonAssessment/lcfqFlows/index'),
        hidden: true,
        description: '外包人员绩效考核新增'
      },
      {
        path: '/outsrcEmpAssessDetail',
        name: 'outsrcEmpAssessDetail',
        component: () => import('@/views/crewManager/wbPersonAssessment/info'),
        hidden: true,
        description: '外包人员绩效考核详情'
      },
      {
        path: '/annualAssessRecord',
        name: 'annualAssessRecord',
        component: () => import('@/views/crewManager/wbPersonAssessment/lcfqFlows/info'),
        hidden: true,
        description: '季度考核记录'
      }
    ]
  },
  // 供应商花名册
  {
    path: '/supplierhmcInfo',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/supplierhmcAdd',
        name: 'supplierhmcAdd',
        component: () => import('@/views/supplierhmc/add'),
        hidden: true,
        description: '供应商花名册新增'
      },
      {
        path: '/supplierhmcEdit',
        name: 'supplierhmcEdit',
        component: () => import('@/views/supplierhmc/edit'),
        hidden: true,
        description: '供应商花名册编辑'
      },
      {
        path: '/supplierhmcInfo',
        name: 'supplierhmcInfo',
        component: () => import('@/views/supplierhmc/info'),
        hidden: true,
        description: '供应商花名册详情'
      }
    // {
    //   path: '/supplierPriceInfo',
    //   name: 'supplierPriceInfo',
    //   component: () => import('@/views/supplierhmc/priceInfo'),
    //   hidden: true,
    //   description: '人力外包价格信息'
    // }
    ]
  },
  // 供应商违规记录 先不做
  // {
  //   path: '/violateRecord',
  //   component: Layout,
  //   hidden: true,
  //   children: [
  //     {
  //       path: '/violateRecord',
  //       name: 'violateRecord',
  //       component: () => import('@/views/supplierViolateRecord/violateRecord'),
  //       hidden: true,
  //       description: '供应商违规记录'
  //     }
  //   ]
  // },

  // 高企研发项目详情、编辑
  {
    path: '/researchProjectEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/researchProjectEdit',
        name: 'researchProjectEdit',
        component: () => import('@/views/researchManager/manager/researchProjectEdit'),
        hidden: true,
        description: '研发项目详情'
      }
    ]
  },
  // 高企合同详情、编辑
  {
    path: '/contractManagementEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/contractManagementEdit',
        name: 'contractManagementEdit',
        component: () => import('@/views/researchManager/manager/contractManagementEdit'),
        hidden: true,
        description: '合同详情'
      }
    ]
  },
  // 高企研发人员添加，编辑
  {
    path: '/RPersonEdit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/RPersonEdit',
        name: 'RPersonEdit',
        component: () => import('@/views/researchManager/manager/RPersonEdit'),
        hidden: true,
        description: '研发人员编辑'
      }
    ]
  },
  // 部门分配
  {
    path: '/onboardDistribution',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/onboardDistribution',
        name: 'onboardDistribution',
        component: () => import('@/views/bankManager/newEmManager/departAssign/index'),
        description: '部门分配'
      }
    ]
  },

  // 行员考核
  {
    path: '/hykh',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/kpiEstablish',
        name: 'kpiEstablish',
        component: () => import('@/views/bankAssessment/kpiEstablish/kpiEstablish'),
        description: 'KPI制定',
        meta: { title: 'KPI制定', noCache: true }
      },
      {
        path: '/kpispList',
        name: 'kpispList',
        component: () => import('@/views/bankAssessment/dbKpiApprove/kpispList'),
        description: 'KPI审批',
        meta: { title: 'KPI审批', noCache: false }
      },
      {
        path: '/kpiEstablishApprove',
        name: 'kpiEstablishApprove',
        component: () => import('@/views/bankAssessment/dbKpiApprove/detail/kpiEstablishApprove'),
        description: 'KPI审批',
        meta: { title: 'KPI审批', noCache: true }
      },
      {
        path: '/kpiZp',
        name: 'kpiZp',
        component: () => import('@/views/bankAssessment/dbKpiSelfAssess/kpiZp'),
        description: 'KPI自评',
        meta: { title: 'KPI自评', noCache: true }
      },
      {
        path: '/ygkhDetail',
        name: 'ygkhDetail',
        component: () => import('@/views/bankAssessment/employeeKh/ygkhDetail'),
        description: '员工考核详情',
        meta: { title: '员工考核详情', noCache: true }
      },
      {
        path: '/mykhZhnlpgDetail',
        name: 'mykhZhnlpgDetail',
        component: () => import('@/views/bankAssessment/employeeKh/mykhZhnlpgDetail'),
        description: '综合能力评估',
        meta: { title: '综合能力评估', noCache: true }
      },
      {
        path: '/kpiPj',
        name: 'kpiPj',
        component: () => import('@/views/bankAssessment/employeeKh/kpiEvaluate/kpiPj'),
        description: 'KPI评价',
        meta: { title: 'KPI考核', noCache: true }
      },
      {
        path: '/dbkpiPjList',
        name: 'dbkpiPjList',
        component: () => import('@/views/bankAssessment/dbKpiPjPgPy/dbKpiEvaluate/dbkpiPjList'),
        description: 'KPI评价',
        meta: { title: 'KPI考核', noCache: true }
      },
      {
        path: '/comprehensivePpp',
        name: 'comprehensivePpp',
        component: () => import('@/views/bankAssessment/dbKpiPjPgPy/comprehensivePpp'),
        description: 'KPI考核',
        meta: { title: 'KPI考核', noCache: true }
      },
      {
        path: '/phjfKhDetail',
        name: 'phjfKhDetail',
        component: () => import('@/views/bankAssessment/setCenter/phjfKh/phjfKhDetail'),
        description: '平衡计分扣分详情',
        meta: { title: '平衡计分扣分详情', noCache: true }
      },
      {
        path: '/ygkhZhnlAssessment',
        name: 'ygkhZhnlAssessment',
        component: () => import('@/views/bankAssessment/employeeKh/ygkhZhnlAssessment'),
        description: '员工考核-综合能力评估',
        meta: { title: '综合能力评估', noCache: true }
      },
      {
        path: '/ygkhPhjf',
        name: 'ygkhPhjf',
        component: () => import('@/views/bankAssessment/employeeKh/ygkhPhjf'),
        description: '员工考核-平衡计分',
        meta: { title: '平衡计分', noCache: true }
      },
      {
        path: '/statexgList',
        name: 'statexgList',
        component: () => import('@/views/bankAssessment/employeeKh/stateChange/statexgList'),
        description: '员工考核-修改状态列表',
        meta: { title: '修改状态列表', noCache: true }
      },
      {
        path: '/comprehensiveZt',
        name: 'comprehensiveZt',
        component: () => import('@/views/bankAssessment/employeeKh/stateChange/comprehensiveZt'),
        description: '员工考核-修改状态',
        meta: { title: '修改状态', noCache: true }
      },
      {
        path: '/mykhLzpgDetail',
        name: 'mykhLzpgDetail',
        component: () => import('@/views/bankAssessment/employeeKh/mykhLzpgDetail'),
        description: '我的考核-履职评估',
        meta: { title: '履职评估', noCache: true }
      },
      {
        path: '/yearKhpj',
        name: 'yearKhpj',
        component: () => import('@/views/bankAssessment/dbYearKhpj/yearKhpj'),
        description: '待办-考核评级',
        meta: { title: '考核评级', noCache: true }
      },
      {
        path: '/lzpgListXia',
        name: 'lzpgListXia',
        component: () => import('@/views/bankAssessment/dbLzpgXia/lzpgListXia'),
        description: '待办-履职评估列表下级评上级',
        meta: { title: '履职评估', noCache: true }
      },
      {
        path: '/lzpgXia',
        name: 'lzpgXia',
        component: () => import('@/views/bankAssessment/dbLzpgXia/lzpgXia'),
        description: '待办-履职评估下级评上级',
        meta: { title: '履职评估', noCache: true }
      },
      {
        path: '/kjbLzpg',
        name: 'kjbLzpg',
        component: () => import('@/views/bankAssessment/setCenter/kjbLzpg/kjbLzpg'),
        description: '科技部履职评估',
        meta: { title: '履职评估', noCache: true }
      }
    ]
  },

  // 部门考核
  {
    path: '/depKh',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/modelManager',
        name: 'modelManager',
        component: () => import('@/views/depAssessment/model/modelManager'),
        description: '模板管理'
      },
      {
        path: '/targetAssign',
        name: 'targetAssign',
        component: () => import('@/views/depAssessment/model/targetAssign'),
        description: '指标项配置'
      },
      {
        path: '/zdykhzd',
        name: 'zdykhzd',
        component: () => import('@/views/depAssessment/myDepkpiDb/zdykhzd'),
        description: '自定义考核制定'
      },
      {
        path: '/khzdmx',
        name: 'khzdmx',
        component: () => import('@/views/depAssessment/myDepkpiDb/khzdmx'),
        description: '自定义考核制定明细'
      },
      {
        path: '/ggkhzdmx',
        name: 'ggkhzdmx',
        component: () => import('@/views/depAssessment/myDepkpiDb/ggkhzdmx'),
        description: '公共考核制定明细'
      },
      {
        path: '/ggkhzd',
        name: 'ggkhzd',
        component: () => import('@/views/depAssessment/myDepkpiDb/ggkhzd'),
        description: '公共考核制定'
      },
      {
        path: '/yjbkhsp',
        name: 'yjbkhsp',
        component: () => import('@/views/depAssessment/myDepkpiDb/yjbkhsp'),
        description: '一级部考核审批'
      },
      {
        path: '/yjbkhspmx',
        name: 'yjbkhspmx',
        component: () => import('@/views/depAssessment/myDepkpiDb/yjbkhspmx'),
        description: '一级部考核审批明细'
      },
      {
        path: '/zdykhsp',
        name: 'zdykhsp',
        component: () => import('@/views/depAssessment/myDepkpiDb/zdykhsp'),
        description: '自定义考核审批'
      },
      {
        path: '/khspmx',
        name: 'khspmx',
        component: () => import('@/views/depAssessment/myDepkpiDb/khspmx'),
        description: '自定义考核审批明细'
      },
      {
        path: '/ggkhspmx',
        name: 'ggkhspmx',
        component: () => import('@/views/depAssessment/myDepkpiDb/ggkhspmx'),
        description: '公共考核审批明细'
      },
      {
        path: '/ggkhsp',
        name: 'ggkhsp',
        component: () => import('@/views/depAssessment/myDepkpiDb/ggkhsp'),
        description: '公共考核审批'
      },
      {
        path: '/selfeval',
        name: 'selfeval',
        component: () => import('@/views/depAssessment/myDepkpiDb/selfeval'),
        description: '自评'
      },
      {
        path: '/selfevaldetail',
        name: 'selfevaldetail',
        component: () => import('@/views/depAssessment/myDepkpiDb/selfevaldetail'),
        description: '自评详情'
      },
      {
        path: '/eval',
        name: 'eval',
        component: () => import('@/views/depAssessment/myDepkpiDb/eval'),
        description: '评价'
      },
      {
        path: '/evaldetail',
        name: 'evaldetail',
        component: () => import('@/views/depAssessment/myDepkpiDb/evaldetail'),
        description: '评价详情'
      },
      {
        path: '/ggeval',
        name: 'ggeval',
        component: () => import('@/views/depAssessment/myDepkpiDb/ggeval'),
        description: '评价'
      },
      {
        path: '/ggevaldetail',
        name: 'ggevaldetail',
        component: () => import('@/views/depAssessment/myDepkpiDb/ggevaldetail'),
        description: '公共评价详情'
      },
      {
        path: '/khxpz',
        name: 'khxpz',
        component: () => import('@/views/depAssessment/khinquire/khxpz'),
        description: '二级部考核配置'
      },
      {
        path: '/khdetail',
        name: 'khdetail',
        component: () => import('@/views/depAssessment/khinquire/khdetail'),
        description: '考核查询明细'
      },
      {
        path: '/ggzbdetail',
        name: 'ggzbdetail',
        component: () => import('@/views/depAssessment/khinquire/ggzbdetail'),
        description: '公共指标明细'
      },
      {
        path: '/gradelist',
        name: 'gradelist',
        component: () => import('@/views/depAssessment/myDepkpiDb/gradeList'),
        description: '评级'
      },
      {
        path: '/yjbTargetAssign',
        name: 'yjbTargetAssign',
        component: () => import('@/views/depAssessment/yjb/yjbTargetAssign'),
        description: '评级'
      },
      {
        path: '/yjbkhdetail',
        name: 'yjbkhdetail',
        component: () => import('@/views/depAssessment/khinquire/yjbkhdetail'),
        description: '考核查询明细'
      }
    ]
  },

  {
    path: '/singleLogin',
    component: () => import('@/views/userCenter/userSingleLogin'),
    hidden: true
  },

  // ntko插件
  {
    path: '/ntko',
    component: () => import('@/components/NtkoComponents/Ntko'),
    hidden: true
  },

  {
    path: '/guide',
    component: Layout,
    redirect: '/guide/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/guide/index'),
        name: 'Guide',
        meta: { title: 'guide', icon: 'guide', noCache: true }
      }
    ]
  },

  {
    path: '/deviceChangeStick',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/deviceChangeStick',
        name: 'deviceChangeStick',
        component: () => import('@/views/ningGuan/deviceChange/Aggregation'),
        description: '宁波地区管理部流程'
      }
    ]
  },
  {
    path: '/newBranchBankAcceptance',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/newBranchBankAcceptance',
        name: 'newBranchBankAcceptance',
        component: () => import('@/views/ningGuan/newBranchBankAcceptance/Aggregation'),
        description: '宁波地区管理部流程'
      }
    ]
  },
  // 分行业务意向审批
  {
    path: '/newFhywyxFjh',
    component: Layout,
    hidden: true,
    children: [
      { path: '/newFhywyxFjh',
        name: 'newFhywyxFjh',
        component: () => import('@/views/branchIntention/Aggregation'),
        description: '分行业务意向审批'
      }
    ]
  },
  /**
   * 分行自研项目应用变更
   */
  {
    path: '/newFhyyxtbg',
    component: Layout,
    hidden: true,
    children: [
      { path: '/newFhyyxtbg',
        name: 'newFhyyxtbg',
        component: () => import('@/views/BanchProjectChange/Aggregation'),
        description: '分行自研项目应用变更'
      }
    ]
  },
  /**
   * 分行意向上线
   */
  {
    path: '/newfhxmsxFJH',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/newfhxmsxFJH',
        name: 'newfhxmsxFJH',
        component: () => import('@/views/online/newfhxmsxFJH/index'),
        description: '分行业务意向上线'
      }
    ]
  },
  // 分行意向上线验证
  {
    path: '/newfhsxyzFJH',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/newfhsxyzFJH',
        name: 'newfhsxyzFJH',
        component: () => import('@/views/online/newfhsxyzFJH/index'),
        description: '分行业务意向上线验证'
      }
    ]
  },
  // 数据中心需求
  {
    path: '/dataCenterDemandsLaunch',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/dataCenterDemandsLaunch',
        name: 'daCenterDemandManager',
        component: () => import('@/views/dataCenterDemandsManagement/index'),
        hidden: true,
        description: '数据中心需求发起'
      }
    ]
  },
  // 数据中心后督
  {
    path: '/daCenterPostGovernorManager',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/daCenterPostGovernorMa',
        name: 'daCenterPostGovernor',
        component: () => import('@/views/postSupervisionManagement/index'),
        hidden: true,
        description: '数据中心后督管理'
      }
    ]
  },
  // 数据中心门禁
  {
    path: '/newAppAccessApply',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/newAppAccessApply',
        name: 'newAppAccessApply',
        component: () => import('@/views/accessPermission/index'),
        hidden: true,
        description: '数据中心门禁权限申请'
      },
      {
        path: '/flowSetting',
        name: 'flowSetting',
        component: () => import('@/views/accessPermission/flowSetting'),
        hidden: true,
        description: '流程配置'
      }
    ]
  }
  // 数据中心门禁流程配置
  // {
  //   path: '/flowSetting',
  //   component: Layout,
  //   hidden: true,
  //   children: [
  //     {
  //       path: '/flowSetting',
  //       name: 'flowSetting',
  //       component: () => import('@/views/accessPermission/flowSetting'),
  //       hidden: true,
  //       description: '流程配置'
  //     }
  //   ]
  // },
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  sysManagementRouter,
  userManagerRouter,
  systemManageRouter,
  comprehensiveManagerRouter,
  researchManagerRouter,
  empLoyeeRouter,
  tiaoXiuRouter,
  gateManagerRouter,
  flowManagerRouter,
  bankAssessmentRouter,
  sourcePlatRouter,
  branchIntentionRouter,
  depAssessmenRouter,
  sourceAlterRouter,
  dataCenterManagementRouter,
  supplierManagerRouter,
  expenseSettlementRouter,
  ConfigurationManagementRouter,
  expensepaymentManagerRouter,
  accountManagerRouter,
  // proTransferRouter,
  testManagementRouter,
  safetyManagementRouter,
  informationTechnologyRouter,

  { path: '*', redirect: '/404', hidden: true, needAuth: false }
]

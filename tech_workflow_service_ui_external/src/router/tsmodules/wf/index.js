
import Layout from '@/layout'

export const workflowRouter = {
  // 我的代办任务
  '/wf/wfPendTask': () => import('@/views/wf/wfPendTask'),
  // 岗位代办任务  src\views\wf\
  '/wf/wfPendOrgTask': () => import('@/views/wf/wfPendOrgTask'),
  // 流程任务异常管理
  '/wf/errorProcess': () => import('@/views/wf/wfErrorProcess'),
  // 任务池代办
  '/wf/wfPendTaskPools': () => import('@/views/wf/wfPendTaskPools'),
  // 已办任务
  '/wf/wfCompleteTask': () => import('@/views/wf/wfCompleteTask'),
  // 租户管理
  '/wf/tenant/wfTenantList': () => import('@/views/wf/wfTenant'),
  // 流程分类管理
  '/wf/classify/wfClassifyList': () => import('@/views/wf/wfClassifyList'),
  // 流程创建管理
  '/wf/create': () => import('@/views/wf/wfCreateProcess'),
  // 自定义按钮配置
  '/wf/button': () => import('@/views/wf/wfButton'),
  // 离岗移交配置
  '/wf/wfLeaveTransfer': () => import('@/views/wf/wfLeaveTransfer'),
  // 表单管理
  '/wf/wfForm': () => import('@/views/wf/wfForm'),
  // 流程实例高级查询
  '/wf/procInstAdvaQuery': () => import('@/views/wf/procInstAdvaQuery'),
  // 工作项查询
  '/wf/workItemQuery': () => import('@/views/wf/workItemQuery'),
  // 交接管理
  '/wf/handover': () => import('@/views/wf/handover'),
  // 邮件节点内容模板
  '/wf/wfEmailTemplate': () => import('@/views/wf/wfEmailTemplate'),
  // 表详情维护
  'wf/wfFormDetail': () => import('@/views/wf/wfFormDetail'),
  // 代办已办拓展方案
  '/wf/wfTaskExpandPlan': () => import('@/views/wf/wfTaskExpandPlan'),
  // 流程变量配置
  '/wf/wfTaskExpandData': () => import('@/views/wf/wfTaskExpandData'),
  // 流程变量配置
  '/wf/wfProcessVariableConfig': () => import('@/views/wf/wfProcessVariableConfig'),
  // 流程跟踪管理
  '/wf/instance/wfRunningList': () => import('@/views/wf/wfRunningList'),
  '/wf/wfProEff': () => import('@/views/wf/wfProEff'),
  // 机构处理效率榜
  '/wf/wfProEff/wfProEffOrganiza': () => import('@/views/wf/wfProEffOrganizationRedAndBlue'),
  // 流程模拟测试
  '/wf/testPro': () => import('@/views/wf/wfTest'),
  // 流程跨步调试
  '/wf/skipTask': () => import('@/views/wf/skipTask'),
  '/wf/wfAgent': () => import('@/views/wf/wfAgent'),
  '/wf/eidtAgent': () => import('@/views/wf/wfAgent/agent'),
  // 代办
  '/wf/wfAgency': () => import('@/views/wf/wfAgency'),
  '/wf/eidtAgency': () => import('@/views/wf/wfAgency/edit'),
  // 流程统计分析
  // 工作流统计
  '/wf/workload': () => import('@/views/wf/wfStatisticalAnalysis/workloadStatistical'),
  // 流程执行时间统计
  '/wf/executionTime': () => import('@/views/wf/wfStatisticalAnalysis/executionTimeStatistical'),
  // 活动执行时间统计
  '/wf/activityTime': () => import('@/views/wf/wfStatisticalAnalysis/activityTimeStatistical'),
  // 流程实例规模统计
  '/wf/processScale': () => import('@/views/wf/wfStatisticalAnalysis/processScaleStatistical'),
  // 流程超时统计
  '/wf/overtimeStatis': () => import('@/views/wf/wfStatisticalAnalysis/overtimeStatistical'),
  // 流程退回统计
  '/wf/returnStatis': () => import('@/views/wf/wfStatisticalAnalysis/returnStatistical'),
  // 流程版本统计
  '/wf/processVersion': () => import('@/views/wf/wfStatisticalAnalysis/processVersionStatistical')

}

// 流程控制台常量路由 不由角色控制由第一个页面跳转
export const workflowConstRouter = {
  path: '/wf',
  component: Layout,
  redirect: '/tenant',
  children: [
    {
      path: 'processDesigner',
      name: 'processDesigner',
      component: () => import('@/views/wf/processDesigner'),
      meta: {
        page: '流程设计器',
        title: '流程设计器',
        icon: 'dashboard',
        affix: false
      }
    },
    {
      path: 'startProcess',
      name: 'startProcess',
      component: () => import('@/views/wf/startProcess'),
      meta: {
        page: '启动流程',
        title: '启动流程',
        icon: 'dashboard',
        affix: false
      }
    },
    {
      path: 'operationPendTask',
      name: 'operationPendTask',
      component: () => import('@/views/wf/operationPendTask'),
      meta: {
        page: '操作待办任务',
        title: '操作待办任务',
        icon: 'dashboard',
        affix: false
      }
    },
    {
      path: 'start',
      name: 'start',
      component: () => import('@/views/wf/wfTest/start'),
      meta: {
        page: '启动流程测试',
        title: '启动流程测试',
        icon: 'dashboard',
        affix: false
      }
    },
    {
      path: 'wfFormDetail',
      name: 'wfFormDetail',
      component: () => import('@/views/wf/wfFormDetail'),
      meta: {
        page: '表单详情',
        title: '表单详情',
        icon: 'dashboard',
        affix: false
      }
    },
    {
      path: 'processPreview',
      name: 'processPreview',
      component: () => import('@/views/wf/processPreview'),
      meta: {
        page: '流程轨迹图',
        title: '流程轨迹图',
        icon: 'dashboard',
        affix: false
      }
    },
    {
      path: 'wfTaskExpandData',
      name: 'wfTaskExpandData',
      component: () => import('@/views/wf/wfTaskExpandData'),
      meta: {
        page: '待办扩展列表',
        title: '待办扩展列表',
        icon: 'dashboard',
        affix: false
      }
    },
    {
      path: 'branchScenario',
      name: 'branchScenario',
      component: () => import('@/views/wf/branchScenario'),
      meta: {
        page: '分支场景预测',
        title: '分支场景预测',
        icon: '',
        affix: false
      }
    }
  ]
}

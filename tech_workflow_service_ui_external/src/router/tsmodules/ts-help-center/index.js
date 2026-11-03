/*
@Author: chenhuocan
@Date: 2020-03-11 19:53:42
@LastEditors:
@LastEditTime:
@Description: 帮助中心路由配置总入口
*/

import Layout from '@/layout'
const router = {
  path: '/help',
  component: Layout,
  redirect: '/help/userFeedback',
  name: 'helpCenter',
  meta: {
    title: '帮助中心',
    icon: 'documentation'
  },
  children: [{
    path: 'userFeedback',
    name: 'UserFeedback',
    component: () => import('@/views/ts-help-center/ts-user-feedback/index'),
    meta: {
      title: '用户反馈',
      icon: 'guide'
    }
  },
  {
    path: 'tooltipManage',
    name: 'TooltipManage',
    component: () => import('@/views/ts-help-center/ts-tooltip-manage/index'),
    meta: {
      title: '操作提示管理',
      icon: 'message'
    }
  },
  {
    path: 'feedbackDefine',
    name: 'FeedbackDefine',
    component: () => import('@/views/ts-help-center/ts-exception-define/index'),
    meta: {
      title: '异常消息定义',
      icon: 'bug'
    }
  },
  {
    path: 'exceptionInfo',
    name: 'ExceptionInfo',
    component: () => import('@/views/ts-help-center/ts-exception-info/index'),
    meta: {
      title: '异常提示帮助'
    },
    hidden: true
  }
  ]
}

export default router


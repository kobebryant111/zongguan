/**
 * 综合管理
 */
import Layout from '@/views/layout/Layout'
const comprehensiveManagerRouter = {
  path: '/crewManager',
  component: Layout,
  alwaysShow: true,
  redirect: '/crewManager',
  name: 'crewManager',
  needAuth: true,
  id: 'WB0001',
  meta: {
    title: '外包管理',
    icon: 'people'
  },
  children: [
    {
      path: '/systemMgr',
      name: 'systemMgr',
      component: () => import('@/views/crewManager/manager/system'),
      needAuth: true,
      id: 'B10007',
      meta: { title: '系统管理', noCache: true }
    },
    {
      path: '/projectMgr',
      name: 'projectMgr',
      component: () => import('@/views/crewManager/manager/project'),
      needAuth: true,
      id: 'B10008',
      meta: { title: '项目管理', noCache: true }
    },
    {
      path: '/wbrzManage',
      name: 'wbrzManage',
      component: () => import('@/views/crewManager/wbrz/wbrzManage'),
      needAuth: true,
      id: 'B10002',
      source: 'iconfont icon-yonghu',
      meta: { title: '外包花名册', keepAlive: false }
    },

    {
      path: '/requireList',
      name: 'requireList',
      component: () => import('@/views/crewManager/wbRequire/requireList'),
      alwaysShow: true,
      needAuth: true,
      id: 'B10003',
      source: 'iconfont icon-yanzhengma',
      meta: { title: '人力需求', keepAlive: false }
    },
    {
      path: '/wbmsManager',
      name: 'wbmsManager',
      component: () => import('@/views/flowsManager/wbms/interviewManger'),
      alwaysShow: true,
      needAuth: true,
      id: 'B10004',
      source: 'iconfont icon-dongtai',
      meta: {
        title: '面试管理', keepAlive: false
      }
    },
    {
      path: '/projectTransfer',
      name: 'projectTransfer',
      component: () => import('@/views/crewManager/projectTransfer'),
      alwaysShow: true,
      needAuth: true,
      id: 'B10009',
      meta: { title: '项目转人力', keepAlive: false }
    },
    {
      path: '/priceAdjustment',
      name: 'priceAdjustment',
      component: () => import('@/views/crewManager/priceAdjustment'),
      alwaysShow: true,
      needAuth: true,
      id: 'B10010',
      meta: { title: '单价调整', keepAlive: false }
    },
    {
      path: '/wbPersonAssessment',
      name: 'wbPersonAssessment',
      component: () => import('@/views/crewManager/wbPersonAssessment'),
      alwaysShow: true,
      needAuth: true,
      id: 'B10011',
      meta: { title: '外包人员考核', keepAlive: false }
    },
    {
      path: '/tqrcManager',
      name: 'tqrcManager',
      component: () => import('@/views/layout/components/AppMain'),
      alwaysShow: true,
      redirect: '/tqrcManager',
      needAuth: true,
      id: 'TQ0001',
      meta: {
        title: '提前入场信息'
      },
      children: [
        {
          path: '/tqrcApplyfor',
          name: 'tqrcApplyfor',
          component: () => import('@/views/crewManager/tqrcApplyfor/index'),
          needAuth: true,
          id: 'TQ000101',
          meta: { title: '提前入场申请', noCache: true }
        },
        {
          path: '/tqrcAbnormal',
          name: 'tqrcAbnormal',
          component: () => import('@/views/crewManager/tqrcApplyfor/tqrcAbnormal'),
          needAuth: true,
          id: 'TQ000102',
          meta: { title: '材料补全处理', noCache: true }
        }
      ]
    },
    {
      path: '/kqManager',
      component: () => import('@/views/layout/components/AppMain'),
      alwaysShow: true,
      redirect: '/kqManager',
      name: 'kqManager',
      needAuth: true,
      id: 'KQ0001',
      meta: {
        title: '考勤信息'
      },
      children: [
        {
          path: '/calendar',
          name: 'calendar',
          component: () => import('@/views/kqManager/calendar/index'),
          needAuth: true,
          id: 'KQ000101',
          meta: { title: '节假日信息', noCache: true }
        },
        {
          path: '/wbAttendance',
          name: 'wbAttendance',
          component: () => import('@/views/kqManager/wbAttendance/index'),
          needAuth: true,
          id: 'KQ000102',
          meta: { title: '外包人员考勤', noCache: true }
        },
        {
          path: '/kqFaceConfig',
          name: 'kqFaceConfig',
          component: () => import('@/views/kqManager/kqFaceConfig/index'),
          needAuth: true,
          id: 'KQ000103',
          meta: { title: '考勤人脸配置', noCache: true }
        },
        {
          path: '/kqMachineConfig',
          name: 'kqMachineConfig',
          component: () => import('@/views/kqManager/kqMachineConfig/index'),
          needAuth: true,
          id: 'KQ000104',
          meta: { title: '考勤机配置', noCache: true }
        },
        {
          path: '/abnormalKqHandling',
          name: 'abnormalKqHandling',
          component: () => import('@/views/kqManager/abnormalKqHandling/index'),
          needAuth: true,
          id: 'KQ000105',
          meta: { title: '异常考勤处理', noCache: true }
        }
      ]
    },
    {
      path: '/wbHmdManage',
      name: 'wbHmdManage',
      component: () => import('@/views/crewManager/wbHmd/wbHmdManage'),
      alwaysShow: true,
      needAuth: true,
      id: 'B10006',
      source: 'iconfont icon-cuowutishitubiao',
      meta: { title: '外包黑名单', keepAlive: false }
    },
    {
      path: '/supplierContract',
      name: 'supplierContract',
      component: () => import('@/views/crewManager/supplierContract/index'),
      needAuth: true,
      id: 'B10013',
      source: 'iconfont icon-yonghu',
      meta: { title: '合同录入', keepAlive: false }
    },
    {
      path: '/formalTalk',
      name: 'formalTalk',
      component: () => import('@/views/formalTalk/index'),
      needAuth: true,
      id: 'B10014',
      source: 'iconfont icon-yonghu',
      meta: { title: '人员约谈', keepAlive: false }
    }
  ]
}
export default comprehensiveManagerRouter

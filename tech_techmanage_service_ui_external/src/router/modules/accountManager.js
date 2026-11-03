/**
 * 台账管理
 */
import Layout from '@/views/layout/Layout'
const accountManagerRouter = {
  path: '/accountManagerRouter',
  component: Layout,
  alwaysShow: true,
  redirect: '/accountManagerRouter',
  name: 'accountManagerRouter',
  needAuth: true,
  id: 'TZ10001',
  meta: {
    title: '台账管理',
    icon: 'form'
  },
  children: [
    {
      path: '/accountManager',
      name: 'accountManager',
      component: () => import('@/views/accountManager/index'),
      needAuth: true,
      id: 'TZ1000101',
      meta: { title: '台账管理', noCache: true }
    },
    {
      path: '/accessCardLedger',
      name: 'accessCardLedger',
      component: () => import('@/views/accountManager/accessCard/index'),
      needAuth: true,
      id: 'TZ1000114',
      meta: { title: '监保部台账', noCache: true }
    },
    {
      path: '/personLedger',
      component: () => import('@/views/layout/components/AppMain'),
      alwaysShow: true,
      redirect: '/personLedger',
      name: 'personLedger',
      needAuth: true,
      id: 'TZ1000102',
      meta: {
        title: '非科技外包管理',
      },
      children: [
        {
          path: '/personindex',
          name: 'personindex',
          component: () => import('@/views/accountManager/personLedger/index'),
          needAuth: true,
          id: 'TZ100010201',
          meta: { title: '外包人员台账', noCache: true }
        },
        {
          path: '/kqFaceConfigOther',
          name: 'kqFaceConfigOther',
          component: () => import('@/views/accountManager/kqFaceConfig/index'),
          needAuth: true,
          id: 'TZ100010202',
          meta: { title: '外包人脸考勤配置', noCache: true }
        },
        {
          path: '/kqRecordOther',
          name: 'kqRecordOther',
          component: () => import('@/views/accountManager/kqRecord/index'),
          needAuth: true,
          id: 'TZ100010203',
          meta: { title: '外包人员考勤', noCache: true }
        },
      ]
    },
    {
      path: '/developGroup',
      name: 'developGroup',
      component: () => import('@/views/accountManager/developGroup/index'),
      needAuth: true,
      id: 'TZ1000120',
      meta: { title: '研发小组台账', noCache: true }
    }
  ]
}
export default accountManagerRouter;
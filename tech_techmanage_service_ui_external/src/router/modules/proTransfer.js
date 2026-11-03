/**
 * 台账管理
 */
 import Layout from '@/views/layout/Layout'
 const proTransferRouter = {
   path: '/proTransfer',
   component: Layout,
   alwaysShow: true,
   redirect: '/proTransfer',
   name: 'proTransfer',
   needAuth: true,
   id: 'ZY10001',
   meta: {
     title: '项目转人力',
     icon: 'form'
   },
   children: [
     {
       path: '/proTransfer',
       name: 'proTransfer',
       component: () => import('@/views/proTransfer/index'),
       needAuth: true,
       id: ' ',
       meta: { title: '项目转人力', noCache: true }
     },
   ]
 }
 export default proTransferRouter;
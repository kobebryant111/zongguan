/*
@Author: amberself
@Date: 2020-03-11 19:52:39
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2020-04-08 19:17:41
@Description: 系统管理的路由配置总入口
*/

const systemRouter = {
  '/menu/tree': () => import('@/views/ts-system-manage/ts-menu-manage'),
  '/org/tree': () => import('@/views/ts-system-manage/ts-org-manage'),
  '/role/tree': () => import('@/views/ts-system-manage/ts-role-manage'),
  '/user/usermg/personMgList': () => import('@/views/ts-system-manage/ts-user-manage'),
  '/user/usermg/personAddMsg': () => import('@/views/ts-system-manage/ts-user-manage/user-add'), // 新增用户
  '/department/tree': () => import('@/views/ts-system-manage/ts-department-manage'),
  '/param/list': () => import('@/views/ts-system-manage/ts-param-manage'),
  '/dict/list': () => import('@/views/ts-system-manage/ts-data-dictionary'),
  '/ecdgen/list': () => import('@/views/ts-system-manage/ts-ecdgen-manage'),
  '/cms/notice/list': () => import('@/views/ts-system-manage/ts-notice-manage'),
  '/sysArticle': () => import('@/views/ts-system-manage/ts-article-manage'),
  '/tenant/tenantmg/tenantMgList': () => import('@/views/ts-system-manage/ts-tenant-manage'),
  '/dataauth/query': () => import('@/views/ts-system-manage/ts-authority-manage'),
  '/user/holMgtList/index': () => import('@/views/ts-system-manage/ts-holiday-manage'),
  '/dataAuthortyModelManage': () => import('@/views/ts-system-manage/ts-data-authorty-model-manage'),
  '/dataAuthortyModelManage/add': () => import('@/views/ts-system-manage/ts-data-authorty-model-manage/add-modify')
}

export default systemRouter

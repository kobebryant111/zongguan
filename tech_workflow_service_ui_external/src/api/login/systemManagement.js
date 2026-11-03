/*
 * @Author: Evan-lian
 * @Date: 2020-03-13 09:08:45
 * @LastEditors: your name
 * @LastEditTime: 2020-04-01 10:45:29
 * @Description:
 */
import request from '@/utils/request'
import { config } from './../index'
const preLink = process.env.NODE_ENV === 'production' ? config.prod.url : config.dev.url

// 获取字典数据
export function fetchDict() {
  return request({
    url: `${preLink}/dict`,
    method: 'post',
    data: {}
  })
}

// 获取导航菜单
export function fetchUserRouteMenu() {
  return request({
    url: `${preLink}/menu.json`,
    method: 'post'
  })
}

// 获取菜单管理中添加的按钮路由
export function fetchUserAsyncRoute() {
  return request({
    url: `${preLink}/sys/indexMenu/permission`,
    method: 'post'
  })
}

// 获取用户机构部门
export function fetchUserOrgDept() {
  return request({
    url: `${preLink}/admin/userOrgDept`,
    method: 'post'
  })
}

// 用户机构部门切换
export function fetchUserChanger(data) {
  return request({
    url: `${preLink}/admin/changer`,
    method: 'post',
    data: data
  })
}


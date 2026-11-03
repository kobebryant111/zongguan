/*
 * @Author: amberself
 * @Date: 2020-03-31 16:16:58
 * @LastEditors: your name
 * @LastEditTime: 2020-04-03 10:35:59
 * @Description:
 */
import request from '@/utils/request'
import { config } from './../index'
import defaultSettings from '@/plugins/settings'

const sessionMode = defaultSettings.enableSession
const preLink = process.env.NODE_ENV === 'production' ? config.prod.url : config.dev.url
const gatewayLink = process.env.NODE_ENV === 'production' ? config.prod.gatewayUrl : config.dev.gatewayUrl

export function login(data) {
  return request({
    url: sessionMode ? `${preLink}/login` : `${gatewayLink}/auth/createToken`,
    method: 'post',
    data
  })
}

export function fetchInfo() {
  return request({
    url: sessionMode ? `${preLink}/getSession1` : `${preLink}/getSession`,
    method: 'post'
  })
}

export function logout() {
  return request({
    url: sessionMode ? `${gatewayLink}/logout` : `${gatewayLink}/user/logout`,
    method: 'post'
  })
}

export function getPublicKey() {
  return request({
    url: sessionMode ? `/rsa/getPublicKey` : `${preLink}/rsa/getPublicKey`,
    method: 'post'
  })
}

export function updatePwd(data) {
  return request({
    url: sessionMode ? `${gatewayLink}/usermg/changePassword` : `${preLink}/usermg/changePassword`,
    method: 'post',
    data
  })
}

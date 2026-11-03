import request from '@/utils/request'
import { formDataHeaders, headers ,jsonHeaders} from './api_header_conf'
import { stringify } from 'querystring';
// 登陆 login
export function login(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/login',
    method: 'post',
    data
  })
}
// 登陆 singlelogin
export function singlelogin(data) {
  const headers = jsonHeaders
  return request({
    headers,
    url: '/singleLogin',
    method: 'post',
    data
  })
}
// 获取用户角色等信息
export function fetchUserInfo(query) {
  return request({
    headers,
    url: '/user/info',
    method: 'get',
    params: query
  })
}

// 获取用户授权菜单列表
export function fetchAuthMenu(query) {
  console.log("query" + query + "query......"  + stringify(query))
  return request({
    headers,
    url: '/permiss/' + query.roleId,
    method: 'get'
  })
}

// 内管登出 logout
export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}

// origin
export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/login/login',
    method: 'post',
    data
  })
}
export function getUserInfo(token) {
  console.log('getUserInfo-token', token)
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}
/**
 * 登录日志分页
 * @param {*} token 
 */
export function loginLogPager(data) {
  return request({
    url: '/ppmNewloginLog/queryPager',
    method: 'post',
    data
  })
}

// 修改密码
export function updateUserPwd(data) {
  return request({
    url: '/commOperator/updateUserPwd',
    method: 'post',
    data
  })
};

// 重置密码
export function resetUserPwd(data) {
  return request({
    url: '/commOperator/resetUserPwd',
    method: 'post',
    data
  })
};
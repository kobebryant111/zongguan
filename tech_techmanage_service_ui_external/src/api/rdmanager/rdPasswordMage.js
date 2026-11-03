/**
 * 密码管理
 */
import request from '@/utils/request'

export function systemListSelect(data) {
  return request({
    url: '/ppmOperatorKey/queryPager',
    method: 'post',
    data
  })
}
export function systemListAllSelect(data) {
  return request({
    url: '/ppmOperatorKey/queryAllPager',
    method: 'post',
    data
  })
}
// 重置密码
export function resetUserPwd(data) {
  return request({
    url: '/ppmOperatorKey/resetUserScodPwd',
    method: 'post',
    data
  })
}

// 关闭验证码
export function clossverifyCode(data) {
  return request({
    url: '/ppmOperatorKey/clossverifyCode',
    method: 'post',
    data
  })
}
/**
 * 获取验证码
 * @param {*} data
 * @returns
 */
export function getVerficationCode(data) {
  return request({
    url: '/ppmOperatorKey/getCode',
    method: 'get',
    data,
    withCredentials: true
  })
}
/**
 *校验验证码
 * @param {*} data
 * @returns
 */
export function testAndVerify(params) {
  return request({
    url: '/ppmOperatorKey/verifyCode',
    method: 'post',
    params,
    withCredentials: true
  })
}
/**
 * 判断是否首次进入密码管理模块
 * @param {*} data
 * @returns
 */
export function judgeSecondOper(data) {
  return request({
    url: '/ppmOperatorKey/firstEnter',
    method: 'post',
    data,
    withCredentials: true
  })
}
/**
 * 首次进入密码管理模块设置密码
 * @param {*} params
 */
export function setAndVerify(params) {
  return request({
    url: '/ppmOperatorKey/setPassword',
    method: 'post',
    params
  })
}
/**
 * 修改密码
 * @param {*} params
 * @returns
 */
export function updatePwdAndVerify(params) {
  return request({
    url: '/ppmOperatorKey/changePassword',
    method: 'post',
    params
  })
}
/**
 * 第一次ecdhe
 * @param {*} data
 * @returns
 */
export function ecdhefirst(data) {
  return request({
    url: '/ppmOperatorKey/ecdhefirst',
    method: 'post',
    data
  })
}
/**
 * 第一次添加ecdhe
 * @param {*} data
 * @returns
 */
export function addecdhefirst(data) {
  return request({
    url: '/ppmOperatorKey/ecdheAddfirst',
    method: 'post',
    data
  })
}
// 删除系统密码表
export function deleteSysPsd(data) {
  return request({
    url: '/ppmOperatorKey/delete',
    method: 'post',
    data
  })
}
// 添加系统密码表
export function addSysPsdList(data) {
  return request({
    url: '/ppmOperatorKey/addSysPsd',
    method: 'post',
    data
  })
}
// 修改系统密码表
export function updataSysPsdList(data) {
  return request({
    url: '/ppmOperatorKey/updataSysPsd',
    method: 'post',
    data
  })
}

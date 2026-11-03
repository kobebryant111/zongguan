import request from '@/utils/request'
import { formDataHeaders } from './api_header_conf'

// userCenter
// 改密
export function modifyPassword(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/user/password/update',
    method: 'put',
    data
  })
};
/**
 * 获取员工档案信息
 * @param string data 字符串
 */
export function getUserFile(data) {
  return request({
    url : '/ppmStaffFile/queryUserFile/' + data,
    method : 'get',
  })
};
/**
 * 更新员工档案信息
 * @param {*} data 
 */
export function upateUserFile(data) {
  return request({
    url : '/ppmStaffFile/update',
    method : 'put',
    data:data
  })
};
/**
   * 获取图片地址
   * @param {*} data 
   */
  export function getImageUrl(data) { 
    return request({
      url : '/ppmDocument/getImageUrl',
      method : 'get',
      params : data
    })
  };
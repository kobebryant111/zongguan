import request from '@/utils/request'

export function getDsp(params) {
  return request({
    url: '/inforTechOut/getDsp',
    method: 'get',
    params
  })
}
export function getArvlDetail(data) {
  return request({
    url: '/inforTechOut/getArvlDetail',
    method: 'post',
    data
  })
}
/**
 * 删除申请
 * @param {*} data
 */
export function doDel(data) {
  return request({
    url: '/inforTechOut/delete',
    method: 'delete',
    params: data
  })
}

// 申请部门负责人审批
export function managerApprove(data) {
  return request({
    url: '/inforTechOut/managerApprove',
    method: 'post',
    data
  })
}
// 用工部门负责人审批
export function hiringDepApprove(data) {
  return request({
    url: '/inforTechOut/hiringDepApprove',
    method: 'post',
    data
  })
}
// 金融科技部经办人审核
export function financeApprove(data) {
  return request({
    url: '/inforTechOut/financeApprove',
    method: 'post',
    data
  })
}
// 风险管理部经办人审核
export function riskHandApprove(data) {
  return request({
    url: '/inforTechOut/riskHandApprove',
    method: 'post',
    data
  })
}
// 人力资源部负责人审批
export function resPerApprove(data) {
  return request({
    url: '/inforTechOut/resPerApprove',
    method: 'post',
    data
  })
}
//
export function getSignDetail(data) {
  return request({
    url: '/inforTechOut/getSignDetail',
    method: 'post',
    data
  })
}

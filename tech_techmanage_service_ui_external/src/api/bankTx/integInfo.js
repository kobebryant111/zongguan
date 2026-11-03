/**
 * 员工调休api
 */
import request from '@/utils/request'

export function pagerList(data) {
  return request({
    url: '/detailsLeave/queryPager',
    method: 'post',
    data
  })
}
/**
 * 提交使用调休信息
 * @param {*} data
 * @returns
 */
export function submitRest(data) {
  return request({
    url: '/detailsLeave/submitApproval',
    method: 'post',
    data
  })
}

/**
 * 删除使用调休信息
 * @param {*} data
 * @returns
 */
export function deleteRest(data) {
  return request({
    url: '/detailsLeave/deleteApproval',
    method: 'post',
    data
  })
}
/**
 * 可使用调休
 */
export function userRest(data) {
  return request({
    url: '/detailsLeave/userApproval',
    method: 'post',
    data
  })
}

/**
 * 积分审批列表获取
 */
export function pointsList(data) {
  return request({
    url: '/detailsLeave/pointsQueryPaper',
    method: 'post',
    data
  })
}
// 积分审批确认(所选)
export function confirmPoints(data) {
  return request({
    url: '/detailsLeave/confirmPointsChoice',
    method: 'post',
    data
  })
}
/**
 * 查询调休审批
 */
export function leaveList(data) {
  return request({
    url: '/detailsLeave/leaveQueryPager',
    method: 'post',
    data
  })
}
/**
 * 同意调休审批(所选)
 */
export function confirmLeave(data) {
  return request({
    url: '/detailsLeave/confirmLeaveChoice',
    method: 'post',
    data
  })
}

/**
 * 积分列表分页查询
 * @param {*} data
 */
export function queryPager(data) {
  return request({
    url: '/empTxUnrecogInteg/queryPager',
    method: 'post',
    data
  })
}

/**
 * 积分信息删除
 * @param {*} data
 */
export function del(data) {
  return request({
    url: '/empTxUnrecogInteg/delete',
    method: 'post',
    data
  })
}

/**
 * 提交登记与补登记日期
 * @param {*} data
 */
export function saveInfo(data) {
  return request({
    url: '/empTxUnrecogInteg/saveInfo',
    method: 'post',
    data
  })
}

/**
 * 积分信息删除
 * @param {*} data
 */
export function submit(data) {
  return request({
    url: '/empTxUnrecogInteg/submit',
    method: 'post',
    data
  })
}

/**
 * 提交积分信息
 * @param {*} data
 */
export function submitMsg(data) {
  return request({
    url: '/empTxUnrecogInteg/submitInfo',
    method: 'post',
    data
  })
}


/**
 * 员工调休api
 */
import request from '@/utils/request'

export function pagerList(data) {
  return request({
    url: '/ppmNetworkStick/queryPager',
    method: 'post'
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
 * 积分信息提交
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

/**
 * 判断是否是节假日
 * @param {*} data
 */
export function judgIsHoliday(data) {
  return request({
    url: '/empTxUnrecogInteg/judgIsHoliday',
    method: 'post',
    data
  })
}

/**
 * 待审核积分列表分页查询
 * @param {*} data
 */
export function queryPagerForApprover(data) {
  return request({
    url: '/empTxApproverDetail/queryPagerForApprover',
    method: 'post',
    data
  })
}

/**
 * 已确认积分列表分页查询
 * @param {*} data
 */
export function queryPagerForRecog(data) {
  return request({
    url: '/empTxRecogDetail/queryPagerForRecog',
    method: 'post',
    data
  })
}


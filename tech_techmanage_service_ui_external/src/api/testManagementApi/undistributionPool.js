import request from '@/utils/request'
const api = '/undistributionPool'
/**
 * init 发起流程
 * @param {*} data
 */
export function queryPager(data) {
  return request({
    url: api + '/queryPager',
    method: 'post',
    data
  })
}

/**
 * 导出
 * @param {*} data
 */
export function exportUndistriPoolExcel(data) {
  return request({
    url: api + '/exportUndistriPoolExcel',
    method: 'post',
    data
  })
}

/**
 * init 发起流程
 * @param {*} data
 */
export function getHeadOfCompanyList() {
  return request({
    url: api + '/getHeadOfCompanyList',
    method: 'get'
  })
}

// 测试人员出池
export function dialogAddSubmitUndis(data) {
  return request({
    url: api + '/dialogAddSubmitUndis',
    method: 'post',
    data
  })
}

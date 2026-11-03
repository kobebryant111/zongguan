import request from '@/utils/request'
const api = '/testApectaculars'
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
export function queryPagerInfo(data) {
  return request({
    url: api + '/queryPagerExcel',
    method: 'post',
    data
  })
}

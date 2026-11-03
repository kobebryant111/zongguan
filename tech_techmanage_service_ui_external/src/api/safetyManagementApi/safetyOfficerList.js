import request from '@/utils/request'
const api = '/safetyOfficerList'
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
 * 修改信息
 * @param {*} data
 */
export function updateMsg(data) {
  return request({
    url: api + '/updateMsg',
    method: 'post',
    data
  })
}

export function queryRole() {
  return request({
    url: api + '/queryRole',
    method: 'post'
  })
}

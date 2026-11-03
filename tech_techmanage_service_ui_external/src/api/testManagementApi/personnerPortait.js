// 人员画像
import request from '@/utils/request'

export function queryPager(params) {
  return request({
    url: 'testManagement/personnerPortaitQueryPager',
    method: 'post',
    params
  })
}
export function queryPagerInfo(params) {
  return request({
    url: 'testManagement/personnerPortaitQueryPagerInfo',
    method: 'post',
    params
  })
}


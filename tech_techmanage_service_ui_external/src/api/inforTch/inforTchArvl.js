import request from '@/utils/request'

export function queryList(data) {
  return request({
    url: '/inforTechOut/queryList',
    method: 'post',
    data
  })
}

export function queryListInfo(data) {
  return request({
    url: '/inforTechOut/queryListInfo',
    method: 'post',
    data
  })
}

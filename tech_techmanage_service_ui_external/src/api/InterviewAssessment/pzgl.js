/**
 * 配置管理
 */
import request from '@/utils/request'

export function insertInfo(data) {
  return request({
    url: '/interPoints/insertInfo',
    method: 'post',
    data
  })
}
export function keepIhfo(data) {
  return request({
    url: '/interPoints/keepIhfo',
    method: 'post',
    data
  })
}
export function deleteIhfo(data) {
  return request({
    url: '/interPoints/deleteIhfo',
    method: 'post',
    data
  })
}

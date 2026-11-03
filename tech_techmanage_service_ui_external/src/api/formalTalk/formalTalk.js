/**
 * 人员约谈
 */
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data
 */
export function queryPager(data) {
  return request({
    url: '/personnelInterview/page',
    method: 'post',
    data
  })
}

/**
 * 约谈上传
 * @param {*} data
 */
export function queryUpload(data) {
  return request({
    url: '/personnelInterview/save',
    method: 'post',
    data
  })
}

/**
 * 约谈文件下载
 * @param {*} data
 */
export function downLoad(params) {
  return request({
    responseType: 'blob',
    url: `/personnelInterview/download`,
    method: 'get',
    params
  })
}

/**
 * 删除
 * @param {*} data
 */
export function delContract(data) {
  return request({
    url: '/personnelInterview/delete',
    method: 'post',
    data
  })
}

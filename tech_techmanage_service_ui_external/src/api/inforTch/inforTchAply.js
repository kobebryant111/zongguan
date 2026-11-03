/**
 * 信息科技外包申请
 */
import request from '@/utils/request'

/**
 * 获取编号
 * @param {*} params
 * @returns
 */
export function getNextSeqNo(params) {
  return request({
    url: '/inforTechOut/getNextSeqNo',
    method: 'get',
    params
  })
}
/**
 * 查询文件
 * @param {*} params
 */
export function queryInforOutsourcFile(params) {
  return request({
    url: '/inforTechOut/queryInforOutsourcFile',
    method: 'get',
    params
  })
}

/**
 * 下载文件
 */
export function downloadRdProjectFile(params) {
  return request({
    url: '/inforTechOut/download',
    method: 'post',
    params
  })
}

/**
 * 删除
 * @param {*} params
 */
export function deleteInforFile(params) {
  return request({
    url: '/inforTechOut/delete',
    method: 'get',
    params
  })
}

export function getAplyDirector(params) {
  return request({
    url: '/inforTechOut/getAplyDirector',
    method: 'get',
    params
  })
}

export function getOutDirector(params) {
  return request({
    url: '/inforTechOut/getOutDirector',
    method: 'get',
    params
  })
}

export function saveInforTechApply(data) {
  return request({
    url: '/inforTechOut/saveInforTechApply',
    method: 'post',
    data
  })
}

export function submitInforTechApply(data) {
  return request({
    url: '/inforTechOut/outLeaveStart',
    method: 'post',
    data
  })
}

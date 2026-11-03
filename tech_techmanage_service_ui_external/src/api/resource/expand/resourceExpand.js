import request from '@/utils/request'
/**
 * 资源扩容新增
 * @param {*} data
 */
export function insertResourceExpand(data) {
  return request({
    url: '/resExpand/insertResourceExpand',
    method: 'post',
    data
  })
}
/**
 * 资源扩提交
 * @param {*} data
 */
export function submitResourceExpand(data) {
  return request({
    url: '/resExpand/submitResourceExpand',
    method: 'post',
    data
  })
}

/**
 * 校验已作废数据
 * @param {*} data
 * @returns
 */
export function validOutofDateData(data) {
  return request({
    url: '/resExpand/validOutofDateMain',
    method: 'post',
    data
  })
}

/**
 * 服务器配件查询
 * @param {*} data
 */
export function queryConfgiItemList(data) {
  return request({
    url: '/resExpand/queryConfgiItemList',
    method: 'post',
    data
  })
}

/**
 * 资源扩容编辑初始化
 * @param {*} data
 */
export function queryResourceExpand(data) {
  return request({
    url: '/resExpand/queryResourceExpand',
    method: 'post',
    data
  })
}
/**
 * 删除数据
 * @param {*} data
 */
export function deleteResourceExpand(data) {
  return request({
    url: '/resExpand/deleteResourceExpand',
    method: 'post',
    data
  })
}
/**
 * 根据流程实例ID，获取业务信息“ID，sourceType”
 * @param {*} data
 */
export function queryBusInfoByProcessInstId(data) {
  return request({
    url: '/resExpand/queryBusInfoByProcessInstId',
    method: 'post',
    data
  })
}

/**
 * 提交工作项
 * @param {*} data
 */
export function submitWorkItem(data) {
  return request({
    url: '/resExpand/submitWorkItem',
    method: 'post',
    data
  })
}
/**
 * 根据应用系统获取资源列表
 * @param {*} data
 */
export function queryCmdbResourceList(data) {
  return request({
    url: '/resExpand/queryCmdbResourceList',
    method: 'post',
    data
  })
}
/**
 * 根据虚拟机的CIID，获取对应物理机信息
 * @param {*} data
 */
export function queryCmdbRelateResourceList(data) {
  return request({
    url: '/resExpand/queryCmdbRelateResourceList',
    method: 'post',
    data
  })
}

/**
 * 根据SAN资源，获取总的价格信息
 * @param {*} data
 */
export function querySanInfo(data) {
  return request({
    url: '/resExpand/querySanInfo',
    method: 'post',
    data
  })
}

/**
 * 重新发起工作流提交
 * @param {*} data
 */
export function reSubmitWorkFlow(data) {
  return request({
    url: '/resExpand/reSubmitWorkFlow',
    method: 'post',
    data
  })
}
/**
 * 导出报表列表查询
 * @param {*} data
 */
export function exportResAuditReport(data) {
  return request({
    url: '/resExpand/exportResAuditReport',
    method: 'post',
    data,
    responseType: 'blob'
  })
}

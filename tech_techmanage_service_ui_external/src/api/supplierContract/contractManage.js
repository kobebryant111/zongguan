/*
*   合同管理
*/
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data
 */
export function queryPager(data) {
  return request({
    url: '/contractManage/queryPager',
    method: 'post',
    data
  })
}
/**
 * 查询详情
 * @param {*} data
 */
export function queryDetail(data) {
  return request({
    url: '/contractManage/queryDetail',
    method: 'post',
    data
  })
}
/**
 * 合同新增/修改
 * @param {*} data
 */
export function saveOrUpdateContract(data) {
  return request({
    url: '/contractManage/add',
    method: 'post',
    data
  })
}
/*
 * 删除合同信息
 * @param {*} data
 */
export function delContract(data) {
  return request({
    url: '/contractManage/delete',
    method: 'delete',
    data
  })
}

/**
 * 查询合同关联外包列表
 * @param {*} data
 */
export function queryOutsourceList(data) {
  return request({
    url: '/contractManage/queryOutsourceList',
    method: 'post',
    data
  })
}

/*
 * 新增外包关联
 * @param {*} data
 */
export function addOutsource(data) {
  return request({
    url: '/contractManage/addOutsource',
    method: 'post',
    data
  })
}

/*
 * 解除关联
 * @param {*} data
 */
export function deleteOutsource(data) {
  return request({
    url: '/contractManage/deleteOutsource',
    method: 'post',
    data
  })
}

/*
 * 删除关联
 * @param {*} data
 */
export function removeOutsource(data) {
  return request({
    url: '/contractManage/removeOutsource',
    method: 'post',
    data
  })
}

/**
 * 考勤excel导出
 * @param {*} data
 */
export function exportSupplier(data) {
  return request({
    url: '/contractManage/exportInfo',
    method: 'post',
    data,
    responseType: 'blob'
  })
}


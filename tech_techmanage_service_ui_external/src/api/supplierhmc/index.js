// 供应商花名册
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmOutsourcingInfo/queryPager',
        method : 'post',
        data
    })
}

/**
 * 查询系统名称
 * @param {*} params 
 */
export function queryListSyste(depId) {
    return request({
        url : `/ppmSystemInfo/queryList/?depId=${depId}`,
        method : 'get',
    })
}

/**
 * 查询项目名称
 * @param {*} params 
 */
export function queryListProject(depId) {
    return request({
        url : `/ppmProjectInfo/queryList/?depId=${depId}`,
        method : 'get',
    })
}

/**
 * 新增
 * @param {*} data 
 */
export function addRoster(data) {
    return request({
        url : '/supplierRoster/addRoster',
        method : 'post',
        data,
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

/**
 * 修改
 * @param {*} data 
 */
export function updateRoster(data) {
    return request({
        url : '/supplierRoster/updateRoster',
        method : 'post',
        data,
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

/**
 * 删除
 * @param {*} data 
 */
export function toDelete(data) {
    return request({
        url : '/supplierRoster/deleteRoster',
        method : 'delete',
        data,
    })
}

/**
 * 查询
 * @param {*} params 
 */
export function queryRoster(params) {
    return request({
        url : '/supplierRoster/queryRoster',
        method : 'GET',
        params,
    })
}

/**
 * 导入
 * @param {*} data 
 */
export function importRoster(data) {
    return request({
        url : '/supplierRoster/importRoster',
        method : 'post',
        data,
    })
}

/**
 * 导出
 * @param {*} data 
 */
export function exportRoster(data) {
    return request({
        responseType: 'blob',
        url : '/supplierRoster/exportRoster',
        method : 'POST',
        data,
    })
}

/**
 * 供应商考核评分 查询
 * @param {*} data 
 */
export function queryDetailQt(data) {
    return request({
        url : '/supplierAssessGrade/queryDetailQt',
        method : 'POST',
        data,
    })
}
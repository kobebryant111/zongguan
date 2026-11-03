/**
 * 部门考核-组织管理
 */
import request from '@/utils/request'

/**
 * 分页列表查询
 * @param {*} data 
 */
export function queryPagerList(data) {
    return request({
        url: '/ppmDepkpiDepRel/queryPagerList',
        method: 'post',
        data
    })
}
/**
 *部门考核关系导出
 * @param {*} data 
 */
export function exportDepKpiDepRels(data) {
    return request({
        url: '/ppmDepkpiDepRel/exportDepKpiDepRels',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 部门考核导入
 * @param {*} data 
 */
export function importExcel(data) {
    return request({
        url: '/ppmDepkpiDepRel/importExcel',
        method: 'post',
        data
    })
}
/**
 * 部门考核插入数据
 * @param {*} data 
 */
export function insertPpmDepKpiDepRels(data) {
    return request({
        url: '/ppmDepkpiDepRel/insertPpmDepKpiDepRels',
        method: 'post',
        data
    })
}
/**
 * 删除（多）
 * @param {*} params 
 */
export function deletePpmDepKpiDepRelList(params) {
    return request({
        url: '/ppmDepkpiDepRel/deletePpmDepKpiDepRelList',
        method: 'delete',
        params
    })
}
/**
 * 页面更新权限表
 * @param {*} data 
 */
export function updatePpmDepKpiDepRelDto(data) {
    return request({
        url: '/ppmDepkpiDepRel/updatePpmDepKpiDepRelDto',
        method: 'post',
        data
    })
}
/**
 * 页面更新权限表_多个同时更新
 * @param {*} data 
 */
export function updatePpmDepKpiDepRelDtos(data) {
    return request({
        url: '/ppmDepkpiDepRel/updatePpmDepKpiDepRelDtos',
        method: 'post',
        data
    })
}
/**
 * 添加
 * @param {*} data 
 */
export function add(data) {
    return request({
        url: '/ppmDepkpiDepRel/add',
        method: 'post',
        data
    })
}
/**
 * 根据多个中心获取部门
 * @param {*} params 
 */
export function queryDeptListBycenterIds(params) {
    return request({
        url: '/ppmDaCenter/queryDeptListBycenterIds',
        method: 'get',
        params
    })
}

/**
 *保存考核层级设置
 * @param {*} data 
 */
export function saveEosDepKpiGrade(data) {
    return request({
        url: '/ppmDepkpiDepRel/saveEosDepKpiGrade',
        method: 'post',
        data
    })
}


/**
 *获得考核层级业务字典并转化为对象
 */
export function getEosDepKpiGrade(data) {
    return request({
        url: '/ppmDepkpiDepRel/getEosDepKpiGrade',
        method: 'post',
        data
    })
}




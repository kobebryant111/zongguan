/**
 * 行员考核-组织管理
 */
import request from '@/utils/request'

/**
 * 分页列表查询
 * @param {*} data 
 */
export function queryPagerList(data) {
    return request({
        url: '/ppmKpiDepRole/queryPagerList',
        method: 'post',
        data
    })
}
/**
 * 考核部门角色导出
 * @param {*} data 
 */
export function exportKpiDepRoles(data) {
    return request({
        url: '/ppmKpiDepRole/exportKpiDepRoles',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 导入
 * @param {*} data 
 */
export function importExcel(data) {
    return request({
        url: '/ppmKpiDepRole/importExcel',
        method: 'post',
        data
    })
}
/**
 * 插入数据
 * @param {*} data 
 */
export function insertPpmKpiDepRoles(data) {
    return request({
        url: '/ppmKpiDepRole/insertPpmKpiDepRoles',
        method: 'post',
        data
    })
}
/**
 * 删除（多）
 * @param {*} params 
 */
export function deletePpmKpiDepRoleList(params) {
    return request({
        url: '/ppmKpiDepRole/deletePpmKpiDepRoleList',
        method: 'delete',
        params
    })
}
/**
 * 页面更新权限表
 * @param {*} data 
 */
export function updatePpmKpiDepRoleDto(data) {
    return request({
        url: '/ppmKpiDepRole/updatePpmKpiDepRoleDto',
        method: 'post',
        data
    })
}
/**
 * 页面更新权限表_多个同时更新
 * @param {*} data 
 */
export function updatePpmKpiRoleDtos(data) {
    return request({
        url: '/ppmKpiDepRole/updatePpmKpiRoleDtos',
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
        url: '/ppmKpiDepRole/add',
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



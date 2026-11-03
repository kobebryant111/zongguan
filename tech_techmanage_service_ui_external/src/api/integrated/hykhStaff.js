/**
 * 行员考核-员工管理 
 */
import request from '@/utils/request'

/**
 * 分页列表查询
 * @param {*} data 
 */
export function staffPagerList(data) {
    return request({
        url : '/ppmKpiRole/queryPagerList',
        method : 'post',
        data
    })
}
/**
 * 考核部门角色导出
 * @param {*} data 
 */
export function exportKpiRoles(data) {
    return request({
        url: '/ppmKpiRole/exportKpiRoles',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 导入
 * @param {*} data 
 */
export function staffImportExcel(data) {
    return request({
        url : '/ppmKpiRole/importExcel',
        method : 'post',
        data
    })
}
/**
 * 插入数据
 * @param {*} data 
 */
export function insertPpmKpiRoles(data) {
    return request({
        url : '/ppmKpiRole/insertPpmKpiRoles',
        method : 'post',
        data
    })
}
/**
 * 删除（多）
 * @param {*} params 
 */
export function deletePpmKpiRoleList(params) {
    return request({
        url : '/ppmKpiRole/deletePpmKpiRoleList',
        method : 'delete',
        params
    })
}
/**
 * 页面更新权限表
 * @param {*} data 
 */
export function updatePpmKpiRoleDto(data) {
    return request({
        url : '/ppmKpiRole/updatePpmKpiRoleDto',
        method : 'post',
        data
    })
}
/**
 * 页面更新权限表_多个同时更新
 * @param {*} data 
 */
export function updatePpmKpiRoleDtos(data) {
    return request({
        url : '/ppmKpiRole/updatePpmKpiRoleDtos',
        method : 'post',
        data
    })
}
/**
 * 添加
 * @param {*} data 
 */
export function add(data) {
    return request({
        url : '/ppmKpiRole/add',
        method : 'post',
        data
    })
}



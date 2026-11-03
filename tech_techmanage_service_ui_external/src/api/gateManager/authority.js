/**
 * 闸机权限管理 
 */
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmZjAuthority/queryPager',
        method : 'post',
        data
    })
}

/**
 * 查询部门名称
 * @param {*} params 
 */
export function queryResourceDept(params) {
    return request({
        url : '/commDepartment/queryResourceDept',
        method : 'get',
        params
    })
}

/**
 * 查询行员信息
 * @param {*} params 
 */
export function queryEmployee(params) {
    return request({
        url : `/commEmployee/queryDetail`,
        method : 'get',
        params
    })
}

/**
 * 查询行员列表
 * @param {*} params 
 */
export function queryEmployeeTree(params) {
    return request({
        url : `/commEmployee/queryEmployeeTree/${params}`,
        method : 'get',
    })
}

/**
 * 查询闸机列表
 * @param {*} data 
 */
export function queryZjMgr(data) {
    return request({
        url : '/ppmZjManager/queryList',
        method : 'post',
        data
    })
}

/**
 * 存草稿
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : '/ppmZjAuthorityApply/draft',
        method : 'post',
        data
    })
}

/**
 * 提交 申请
 * @param {*} data 
 */
export function doApply(data) {
    return request({
        url : '/ppmZjAuthorityApply/apply',
        method : 'post',
        data
    })
}

/**
 * 删除 申请 
 * @param {*} data 
 */
export function doDel(data) {
    return request({
        url : '/ppmZjAuthorityApply/delete',
        method : 'delete',
        params : data
    })
}

/**
 * 查询闸机权限申请详情
 * @param {*} params 
 */
export function queryDetail(params) {
    return request({
        url : '/ppmZjAuthorityApply/queryDetail',
        method : 'get',
        params 
    })
}
/**
 * 更新权限状态
 * @param {*} params 
 */
export function changeFlag(params) {
    return request({
        url : '/ppmZjAuthority/changeFlag',
        method : 'post',
        params 
    })
}
/**
 * 提交 申请
 * @param {*} data 
 */
export function twoManagerAudit(data) {
    return request({
        url : '/ppmZjAuthorityApply/twoManagerAudit',
        method : 'post',
        data
    })
}

/**
 * 提交 申请
 * @param {*} data 
 */
export function topManagerAudit(data) {
    return request({
        url : '/ppmZjAuthorityApply/topManagerAudit',
        method : 'post',
        data
    })
}
/**
 * excel导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        responseType: 'blob',
        url: '/ppmZjAuthority/exportExcel',
        method: 'post',
        data
    })
};

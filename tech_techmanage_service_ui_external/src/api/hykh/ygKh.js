/**
 * 员工考核
 */
import request from '@/utils/request'

/**
 * 考核查询页面汇总数据查询
 * @param {*} data 
 */
export function getPpmKpiPmInfoSummary(data) {
    return request({
        url: '/ppmKpiPm/getPpmKpiPmInfoSummary',
        method: 'post',
        data
    })
}
/**
 * 考核查询页面汇总数据查询
 * @param {*} data 
 */
export function getEmpKpiDataPager(data) {
    return request({
        url: '/ppmKpiPm1/getEmpKpiDataPager',
        method: 'post',
        data
    })
}
/**
 * 员工考核数据导出
 * @param {*} data 
 */
export function exportEmpKpiDatas(data) {
    return request({
        url: '/ppmKpiPm1/exportEmpKpiDatas',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 根据工号查询详情
 * @param {*} params 
 */
export function getPpmKpiRoleByBadge(params) {
    return request({
        url: '/ppmKpiRole/getPpmKpiRoleByBadge',
        method: 'get',
        params
    })
}
/**
 * 根据工号查询初审人和终审人
 * @param {*} params 
 */
export function getPpmKpiRoleInfo(params) {
    return request({
        url: '/ppmKpiRole/getPpmKpiRoleInfo',
        method: 'get',
        params
    })
}
/**
 * 删除
 * @param {*} params 
 */
export function del(params) {
    return request({
        url: '/ppmKpiPm/delete',
        method: 'delete',
        params
    })
}




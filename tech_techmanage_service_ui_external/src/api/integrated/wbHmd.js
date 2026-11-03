/**
 * 外包黑名单 
 */
import request from '@/utils/request'
/**
 * 检查是否黑名单
 * @param {*} params 
 */
export function checkedIsBlack(params) {
    return request({
        url : '/ppmOutsourcingBlacklist/checkedIsBlack',
        method : 'get',
        params
    })
}
/**
 * 删除
 * @param {*} params 
 */
export function hmdDelete(params) {
    return request({
        url : '/ppmOutsourcingBlacklist/delete',
        method : 'delete',
        params
    })
}
/**
 * 分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmOutsourcingBlacklist/queryPager',
        method : 'post',
        data
    })
}
/**
 * 分页查询
 * @param {*} data 
 */
export function update(data) {
    return request({
        url : '/ppmOutsourcingBlacklist/update',
        method : 'post',
        data
    })
}
/**
 * 导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        url: '/ppmOutsourcingBlacklist/exportExcel',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
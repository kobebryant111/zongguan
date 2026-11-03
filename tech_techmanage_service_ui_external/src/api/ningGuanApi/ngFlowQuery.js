/**
 * 宁管流程api
 */
import request from '@/utils/request'
/**
 * 分页
 * @param {*} params 
 */
export function flowPagerList(data) {
    return request({
        url: '/ppmNingGuanFlow/queryPager',
        method : 'post',
        data
    })
}
/**
 * 导出
 * @param {*} params 
 */
export function exportExcel(data) {
    return request({
        url: '/ppmNingGuanFlow/exportExcel',
        method : 'post',
        data,
        responseType: 'blob'
    })
}
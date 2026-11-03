/**
 * 行员考核--kpi指标配置项
 */
import request from '@/utils/request'

/**
 * 分页列表查询kpi指标配置项
 * @param {*} data 
 */
export function queryPagerList_kpi(data) {
    return request({
        url: '/ppmKpiItem/queryPagerList_kpi',
        method: 'post',
        data
    })
}
/**
 * kpi指标配置项导出
 * @param {*} data 
 */
export function exportKpiItems(data) {
    return request({
        url: '/ppmKpiItem/exportKpiItems',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 导入
 * @param {*} data 
 */
export function importExcel_kpi(data) {
    return request({
        url: '/ppmKpiItem/importExcel_kpi',
        method: 'post',
        data
    })
}
/**
 * 删除（多）
 * @param {*} params 
 */
export function deletePpmKpiItemList_kpi(params) {
    return request({
        url: '/ppmKpiItem/deletePpmKpiItemList_kpi',
        method: 'delete',
        params
    })
}
/**
 * 更新
 * @param {*} data 
 */
export function updatePpmKpiItemDto_kpi(data) {
    return request({
        url: '/ppmKpiItem/updatePpmKpiItemDto_kpi',
        method: 'post',
        data
    })
}
/**
 * 添加
 * @param {*} data 
 */
export function addPpmKpiItemDto_kpi(data) {
    return request({
        url: '/ppmKpiItem/addPpmKpiItemDto_kpi',
        method: 'post',
        data
    })
}



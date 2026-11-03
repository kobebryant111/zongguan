/**
 * 行员考核--平衡计分项配置
 */
import request from '@/utils/request'

/**
 * 分页列表查询平衡计分项配置
 * @param {*} data 
 */
export function queryPagerList_balance(data) {
    return request({
        url: '/ppmKpiItem/queryPagerList_balance',
        method: 'post',
        data
    })
}
/**
 * 平衡计分项配置导出
 * @param {*} data 
 */
export function exportKpiItems_balance(data) {
    return request({
        url: '/ppmKpiItem/exportKpiItems_balance',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 导入
 * @param {*} data 
 */
export function importExcel_balance(data) {
    return request({
        url: '/ppmKpiItem/importExcel_balance',
        method: 'post',
        data
    })
}
/**
 * 删除（多）
 * @param {*} params 
 */
export function deletePpmKpiItemList_balance(params) {
    return request({
        url: '/ppmKpiItem/deletePpmKpiItemList_balance',
        method: 'delete',
        params
    })
}
/**
 * 更新
 * @param {*} data 
 */
export function updateKpiItemData(data) {
    return request({
        url: '/ppmKpiItem/updateKpiItemData',
        method: 'post',
        data
    })
}
/**
 * 添加
 * @param {*} data 
 */
export function addKpiItemData(data) {
    return request({
        url: '/ppmKpiItem/addKpiItemData',
        method: 'post',
        data
    })
}
/**
 * 根据id查询指标库主子表数据详情
 * @param {*} params 
 */
export function getKpiItemData(params) {
    return request({
        url : '/ppmKpiItem/getKpiItemData',
        method : 'get',
        params 
    })
}
/**
 * 删除（修改表格）
 * @param {*} params 
 */
export function delDetail(params) {
    return request({
        url: '/ppmKpiItemDetail/delete',
        method: 'delete',
        params
    })
}



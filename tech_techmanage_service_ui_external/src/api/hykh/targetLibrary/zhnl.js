/**
 * 行员考核--综合能力评估项
 */
import request from '@/utils/request'

/**
 * 分页列表查询综合能力评估项
 * @param {*} data 
 */
export function queryPagerList_ComprehensiveAbility(data) {
    return request({
        url: '/ppmKpiItem/queryPagerList_ComprehensiveAbility',
        method: 'post',
        data
    })
}
/**
 * 综合能力评估项导出
 * @param {*} data 
 */
export function exportKpiItems_ComprehensiveAbility(data) {
    return request({
        url: '/ppmKpiItem/exportKpiItems_ComprehensiveAbility',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 导入
 * @param {*} data 
 */
export function importExcel_ComprehensiveAbility(data) {
    return request({
        url: '/ppmKpiItem/importExcel_ComprehensiveAbility',
        method: 'post',
        data
    })
}
/**
 * 删除（多）
 * @param {*} params 
 */
export function deletePpmKpiItemList_ComprehensiveAbility(params) {
    return request({
        url: '/ppmKpiItem/deletePpmKpiItemList_ComprehensiveAbility',
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



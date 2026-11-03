/**
 * 平衡计分
 */
import request from '@/utils/request'

/**
 * 获得平衡计分评价汇总数据
 * @param {*} data 
 */
export function getBalanceSummaryInfoPage(data) {
    return request({
        url: '/ppmKpiPm1/getBalanceSummaryInfoPage',
        method: 'post',
        data
    })
}
/**
 * 平衡计分汇总扣分数据导出
 * @param {*} data 
 */
export function exportBalanceSummary(data) {
    return request({
        url: '/ppmKpiPm1/exportBalanceSummary',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 查询考核项目表里面平衡计分项
 * @param {*} data 
 */
export function getBalanceItem(data) {
    return request({
        url: '/ppmKpiPmDetail/getBalanceItem',
        method: 'post',
        data
    })
}
/**
 * 查询考核项目表里面平衡计分项描述
 * @param {*} data 
 */
export function getBalancePlan(data) {
    return request({
        url: '/ppmKpiPmDetail/getBalancePlan',
        method: 'post',
        data
    })
}
/**
 * 添加数据
 * @param {*} data 
 */
export function addKfItem(data) {
    return request({
        url: '/ppmKpiBalanceDeduce/addPpmKpiParams',
        method: 'post',
        data
    })
}
/**
 * 删除
 * @param {*} params 
 */
export function del(params) {
    return request({
        url: '/ppmKpiBalanceDeduce/delete',
        method: 'delete',
        params
    })
}
/**
 * 平衡计分详细扣分数据导出
 * @param {*} data 
 */
export function exportBalanceDetail(data) {
    return request({
        url: '/ppmKpiBalanceDeduce/exportBalanceSummary',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 获取扣分详细项
 * @param {*} data 
 */
export function getBalanceDeduceItem(data) {
    return request({
        url: '/ppmKpiBalanceDeduce/getBalanceDeduceItem',
        method: 'post',
        data
    })
}
/**
 * 更新
 * @param {*} data 
 */
export function update(data) {
    return request({
        url: '/ppmKpiBalanceDeduce/update',
        method: 'post',
        data
    })
}

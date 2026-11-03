/**
 * 员工考核-状态修改
 */
import request from '@/utils/request'

/**
 * 员工考核_状态修改_获取考核考核项数据
 * @param {*} data 
 */
export function getDetailItemList(data) {
    return request({
        url: '/ppmKpiPmDetail1/getDetailItemList',
        method: 'post',
        data
    })
}
/**
 * 员工考核_状态修改_综合能力，履职评估，平衡计分统计数据
 * @param {*} data 
 */
export function queryPpmKpiPmDetailInfoGroup(data) {
    return request({
        url: '/ppmKpiPmDetail1/queryPpmKpiPmDetailInfoGroup',
        method: 'post',
        data
    })
}
/**
 * 员工修改状态查询分页
 * @param {*} data 
 */
export function queryKpiPmByConditionPager(data) {
    return request({
        url: '/ppmKpiPm1/queryKpiPmByConditionPager',
        method: 'post',
        data
    })
}
/**
 * 状态修改导出
 * @param {*} data 
 */
export function exportKpiPmByCondition(data) {
    return request({
        url: '/ppmKpiPm1/exportKpiPmByCondition',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 后台状态修改多条
 * @param {*} data 
 */
export function backStageUpdateListStatus(data) {
    return request({
        url: '/ppmKpiPmDetail1/backStageUpdateListStatus',
        method: 'post',
        data
    })
}
/**
 * 后台状态修改单条
 * @param {*} data 
 */
export function backStageUpdateStatus(data) {
    return request({
        url: '/ppmKpiPmDetail1/backStageUpdateStatus',
        method: 'post',
        data
    })
}






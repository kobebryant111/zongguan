/**
 * KPI自评
 */
import request from '@/utils/request'

/**
 * 提交考核指标项自评
 * @param {*} data 
 */
export function submitSelfEval(data) {
    return request({
        url: '/ppmKpiPm/submitSelfEval',
        method: 'post',
        data
    })
}
/**
 * 提交经理半年度考核自评
 * @param {*} data 
 */
export function submitSelfEvalHalfSelf(data) {
    return request({
        url: '/ppmKpiPm/submitSelfEvalHalfSelf',
        method: 'post',
        data
    })
}
/**
 * 自评之类从页面传参过来进行数据更新
 * @param {*} data 
 */
export function updatePpmKpiPmDetailDto(data) {
    return request({
        url: '/ppmKpiPmDetail/updatePpmKpiPmDetailDto',
        method: 'post',
        data
    })
}
/**
 * 根据ID查询详情数据
 * @param {*} params 
 */
export function getPpmKpiPmDetailInfo(params) {
    return request({
        url: '/ppmKpiPmDetail/getPpmKpiPmDetailInfo',
        method: 'get',
        params
    })
}

/**
 * 获得考核主项的统计项
 * @param {*} data 
 */
export function getPpmKpiPmInfoTotalDto(data) {
    return request({
        url: '/ppmKpiPm/getPpmKpiPmInfoTotalDto',
        method: 'post',
        data
    })
}





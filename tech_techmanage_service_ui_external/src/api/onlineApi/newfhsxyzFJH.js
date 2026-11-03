/**
 * 分行业务意向上线验证变更api
 */
import request from '@/utils/request'

/**
 * 详情
 * @param {*} data 
 */
export function queryDetailByInstanceid(params) {
    return request({
        url: '/ppmProductionReview/queryDetailByInstanceid',
        method : 'get',
        params
    })
}

/**
 * 分行业务意向上线验证业务经理审批
 * @param {*} data 
 */
export function approveReviewByBusjl(data) {
    return request({
        url: '/ppmProductionReview/approveReviewByBusjl',
        method : 'post',
        data
    })
}



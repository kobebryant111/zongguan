/**
 * 我的考核
 */
import request from '@/utils/request'

/**
 * 获取评价设置页面数据
 * @param {*} data 
 */
export function getMyKpi(data) {
    return request({
        url: '/ppmKpiPm/getMyKpi',
        method: 'post',
        data
    })
}
/**
 * 获取考核年份下拉框
 * @param {*} data 
 */
export function getPpmKpiPmTyears(data) {
    return request({
        url: '/ppmKpiPm/getPpmKpiPmTyears',
        method: 'post',
        data
    })
}
/**
 * 获取员工头像
 * @param {*} data 
 */
export function getProfilePicture(data) {
    return request({
        url: '/commOperator/getProfilePicture',
        method: 'post',
        data
    })
}






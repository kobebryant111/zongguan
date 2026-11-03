/**
 * 评级设置中心
 */
import request from '@/utils/request'

/**
 * 获取评级设置页面数据
 * @param {*} data 
 */
export function queryLists(data) {
    return request({
        url: '/ppmKpiParams/queryLists',
        method: 'post',
        data
    })
}
/**
 * 添加评级项设置
 * @param {*} data 
 */
export function addPpmKpiParams(data) {
    return request({
        url: '/ppmKpiParams/addPpmKpiParams',
        method: 'post',
        data
    })
}
/**
 * 更新评级项设置
 * @param {*} data 
 */
export function updateRateGrade(data) {
    return request({
        url: '/ppmKpiParams/update',
        method: 'post',
        data
    })
}
/**
 * 删除
 * @param {*} params 
 */
export function delRateGradeSet(params) {
    return request({
        url: '/ppmKpiParams/delete',
        method: 'delete',
        params
    })
}





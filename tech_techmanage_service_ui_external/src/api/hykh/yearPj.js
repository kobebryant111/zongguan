/**
 * 考核年度评级
 */
import request from '@/utils/request'

/**
 * 获得部门年度确认分页数据
 * @param {*} data 
 */
export function getDepYearDataPager(data) {
    return request({
        url: '/ppmKpiPm1/getDepYearDataPager',
        method: 'post',
        data
    })
}
/**
 * 加分
 * @param {*} data 
 */
export function addScore(data) {
    return request({
        url: '/ppmKpiPm1/addScore',
        method: 'post',
        data
    })
}
/**
 * 提交
 * @param {*} data 
 */
export function submitDepYearConfirm(data) {
    return request({
        url: '/ppmKpiPm1/submitDepYearConfirm',
        method: 'post',
        data
    })
}
/**
 * 获得中心年度确认分页数据
 * @param {*} data 
 */
export function getCenterYearDataPager(data) {
    return request({
        url: '/ppmKpiPm1/getCenterYearDataPager',
        method: 'post',
        data
    })
}
/**
 * 中心评级-提交
 * @param {*} data 
 */
export function submitCenterYearEval(data) {
    return request({
        url: '/ppmKpiPm1/submitCenterYearEval',
        method: 'post',
        data
    })
}
/**
 * 中心评级-保存
 * @param {*} data 
 */
export function updateCenterTlevel(data) {
    return request({
        url: '/ppmKpiPm1/updateCenterTlevel',
        method: 'post',
        data
    })
}
/**
 * 评级标签
 * @param {*} data 
 */
export function getLevelPersonTip(data) {
    return request({
        url: '/ppmKpiPm1/getLevelPersonTip',
        method: 'post',
        data
    })
}
/**
 * 中心评级标签
 * @param {*} data 
 */
export function getCenterLevelPersonTip(data) {
    return request({
        url: 'ppmKpiPm1/getCenterLevelPersonTip',
        method: 'post',
        data
    })
}
/**
 * 科技部分页查询
 * @param {*} data 
 */
export function getTecYearDataPager(data) {
    return request({
        url: 'ppmKpiPm1/getTecYearDataPager',
        method: 'post',
        data
    })
}
/**
 * 科技部提交
 * @param {*} data 
 */
export function submitTecYearEval(data) {
    return request({
        url: 'ppmKpiPm1/submitTecYearEval',
        method: 'post',
        data
    })
}
/**
 * 科技部更新
 * @param {*} data 
 */
export function updateTecTlevel(data) {
    return request({
        url: 'ppmKpiPm1/updateTecTlevel',
        method: 'post',
        data
    })
}
/**
 * 获取评级修改列表
 * @param {*} data 
 */
export function getDepStatusPager(data) {
    return request({
        url: '/ppmKpiPm1/getDepStatusPager',
        method: 'post',
        data
    })
}
/**
 * 评级状态更新
 * @param {*} data 
 */
export function updateDepStatus(data) {
    return request({
        url: '/ppmKpiPm1/updateDepStatus',
        method: 'post',
        data
    })
}
/**
 *  部门提示
 * @param {*} data 
 */
export function getNoSubmitDepTip(data) {
    return request({
        url: 'ppmKpiPm2/getNoSubmitDepTip',
        method: 'post',
        data
    })
}
/**
 *  中心提示
 * @param {*} data 
 */
export function getNoSubmitCenterTip (data) {
    return request({
        url: 'ppmKpiPm2/getNoSubmitCenterTip',
        method: 'post',
        data
    })
}
/**
 * 我的待办
 */
import request from '@/utils/request'

/**
 * 获取我的代办
 * @param {*} data 
 */
export function getMyDb(data) {
    return request({
        url: '/ppmKpiPm/getMyDb',
        method: 'post',
        data
    })
}
/**
 * 获取考核主表信息/获取考核审批流程信息
 * @param {*} data 
 */
export function getPpmKpiPmInfo(data) {
    return request({
        url: '/ppmKpiPm/getPpmKpiPmInfo',
        method: 'post',
        data
    })
}
/**
 * 获取考核考核项数据
 * @param {*} data 
 */
export function getPpmKpiPmDetails(data) {
    return request({
        url: '/ppmKpiPmDetail/getPpmKpiPmDetails',
        method: 'post',
        data
    })
}
/**
 * 添加KPI和能力提升（制定）
 * @param {*} data 
 */
export function addPpmKpiPmDetailItemFormulate(data) {
    return request({
        url: '/ppmKpiPmDetail/addPpmKpiPmDetailItemFormulate',
        method: 'post',
        data
    })
}
/**
 * 添加KPI和能力提升（审核）
 * @param {*} data 
 */
export function addPpmKpiPmDetailItemApprove(data) {
    return request({
        url: '/ppmKpiPmDetail/addPpmKpiPmDetailItemApprove',
        method: 'post',
        data
    })
}
/**
 * 更新(制定)
 * @param {*} data 
 */
export function updatePpmKpiPmDetailFormulate(data) {
    return request({
        url: '/ppmKpiPmDetail/updatePpmKpiPmDetailFormulate',
        method: 'post',
        data
    })
}
/**
 * 更新(审核)
 * @param {*} data 
 */
export function updatePpmKpiPmDetailApprove(data) {
    return request({
        url: '/ppmKpiPmDetail/updatePpmKpiPmDetailApprove',
        method: 'post',
        data
    })
}
/**
 * 查询开关详情
 * @param {*} params 
 */
export function querySwitch(params) {
    return request({
        url: '/ppmKpiItemSwitch/querySwitch',
        method: 'get',
        params
    })
}
/**
 * 提交考核指标项
 * @param {*} data 
 */
export function submitKpiDetail(data) {
    return request({
        url: '/ppmKpiPm/submitKpiDetail',
        method: 'post',
        data
    })
}
/**
 * 获取KPI指标项下拉框
 * @param {*} data 
 */
export function getKpiItems(data) {
    return request({
        url: '/ppmKpiItem/getKpiItems',
        method: 'post',
        data
    })
}
/**
 * 删除
 * @param {*} params 
 */
export function delKpi(params) {
    return request({
        url: '/ppmKpiPmDetail/delete',
        method: 'delete',
        params
    })
}
/**
 * 考核审批查询分页
 * @param {*} data 
 */
export function queryApprovePager(data) {
    return request({
        url: '/ppmKpiPm/queryApprovePager',
        method: 'post',
        data
    })
}
/**
 * 考核指标项审批
 * @param {*} data 
 */
export function approveKpiDetail(data) {
    return request({
        url: '/ppmKpiPm/approveKpiDetail',
        method: 'post',
        data
    })
}
/**
 * 考核指标项回退
 * @param {*} data 
 */
export function rollbackKpiDetail(data) {
    return request({
        url: '/ppmKpiPm/rollbackKpiDetail',
        method: 'post',
        data
    })
}





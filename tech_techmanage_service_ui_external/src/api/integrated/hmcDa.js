/**
 * 花名册档案 
 */
import request from '@/utils/request'
/**
 * 获得外包基本信息
 * @param {*} params 
 */
export function getOutsBasicInfo(params) {
    return request({
        url : '/ppmOutsourcingBasic/getOutsBasicInfo',
        method : 'get',
        params 
    })
}
/**
 * 获得外包拓展信息
 * @param {*} params 
 */
export function getExtDetailAll(params) {
    return request({
        url : '/ppmOutsourcingBasic/getExtDetailAll',
        method : 'get',
        params 
    })
}
/**
 * 保存更新基本信息
 * @param {*} data 
 */
export function update(data) {
    return request({
        url : '/ppmOutsourcingBasic/update',
        method : 'post',
        data
    })
}
/**
 * 保存更新拓展信息
 * @param {*} data 
 */
export function updatePpmOutsourcingExt(data) {
    return request({
        url : '/ppmOutsourcingBasic/updatePpmOutsourcingExt',
        method : 'post',
        data
    })
}
/**
 * 查询当前登陆人角色
 * @param {*} data 
 */
export function queryRole(data) {
    return request({
        url : '/ppmOutsourcingBasic/queryRole',
        method : 'post',
        data
    })
}

/**
 * 查询当前登陆人外包档案的权限
 * @param {*} data 
 */
export function queryPermission(data) {
    return request({
        url : '/ppmOutsourcingBasic/queryPermission',
        method : 'post',
        data
    })
}
/**
 * 获得相关流程数据
 * @param {*} params 
 */
export function getFlowData(params) {
    return request({
        url : '/ppmOutsourcingBasic/getFlowData',
        method : 'get',
        params 
    })
}
/**
 * 根据身份证查询面试信息
 * @param {*} params 
 */
export function getIntervieweeInfo(params) {
    return request({
        url : '/ppmIntervieweeOverinfo/getIntervieweeInfo/' + params,
        method : 'get',
    })
}

/**
 * 根据身份证查询面试信息
 * @param {*} params 
 */
export function getIntervieweeData(params) {
    return request({
        url : '/ppmIntervieweeOverinfo/getIntervieweeInfos/' + params,
        method : 'get',
    })
}
/**
 * 根据工号查询项目结算面试信息
 * @param {*} params 
 */
export function queryDetailExt(params) {
    return request({
        url : '/ppmOutsourcingAccessapply/queryDetailExt',
        method : 'get',
        params 
    })
}
/**
 * 查询外包考核月度统计数据
 * @param {*} params 
 */
export function getOutEvalMonthData(params) {
    return request({
        url : '/ppmKhOutsourceperson/getOutEvalMonthData',
        method : 'get',
        params 
    })
}
/**
 * 查询外包考核月度比例数据
 * @param {*} params 
 */
export function getOutEvalOrderData(params) {
    return request({
        url : '/ppmKhOutsourceperson/getOutEvalOrderData',
        method : 'get',
        params 
    })
}
/**
 * 查询外包加班月度统计数据
 * @param {*} params 
 */
export function getOutOvertimeMonthData(params) {
    return request({
        url : '/ppmKqTotal/getOutOvertimeMonthData',
        method : 'get',
        params 
    })
}
/**
 * 查询外包考核月度比例数据
 * @param {*} params 
 */
export function getOutOvertimeOrderData(params) {
    return request({
        url : '/ppmKqTotal/getOutEvalOrderData',
        method : 'get',
        params 
    })
}

/**
 * 查询外包培训数据
 * @param {*} params 
 */
export function getOutTrainList(params) {
    return request({
        url : '/ppmOutsourcingTrain/getOutTrainList',
        method : 'get',
        params 
    })
}
/**
 * 查询外包培训数据
 * @param {*} params 
 */
export function getOutRiskResults(params) {
    return request({
        url : '/ppmOutsourcingRiskResult/getOutRiskResults',
        method : 'get',
        params 
    })
}
/**
 * 获得相关流程数据
 * @param {*} params 
 */
export function getXgFlowData(params) {
    return request({
        url : '/ppmOutsourcingBasic/getFlowData',
        method : 'get',
        params 
    })
}

/**
 * 花名册分页
 * @param {*} data 
 */
export function queryCrewPage(data) {
    return request({
        url : '/ppmOutsourcingBasic/queryCrewPage',
        method : 'post',
        data
    })
}

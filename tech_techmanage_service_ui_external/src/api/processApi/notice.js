/**
 * 流程管理 - 通知
 */
import request from '@/utils/request'
/**
 * 获取模板分页集合
 * @param {*} data 
 */
export function getTemplateList(data) {
    return request({
        url : '/ppmMsgTemplate/queryPager',
        method : 'post',
        data
    })
}
/**
 * 获取模板详情
 * @param {*} params 
 */
export function getTemplateDetail(params) {
    return request({
        url : '/ppmMsgTemplate/queryDetail',
        method : 'get',
        params
    })
}
/**
 * 添加或者更新
 * @param {*} data 
 */
export function updateTmeplate(data) {
    return request({
        url : '/ppmMsgTemplate/update',
        method : 'post',
        data
    })
}
/**
 * 删除模板
 * @param {*} params 
 */
export function deleteTemplateForId(params) {
    return request({
        url : '/ppmMsgTemplate/delete',
        method : 'delete',
        params
    })
}

/**
 * 获取可用模板
 * @param {*} params 
 */
export function getAvailableTemplate(params) {
    return request({
        url : '/ppmMsgTemplate/getAvailableTemplate',
        method : 'get',
        params
    })
}
/**
 * ====================== 流程调度管理维护 =======
 */
/**
 * 流程类型树
 */
export function getFlowTree() {
    return request({
        url : '/ppmFlowType/loadFlow',
        method : 'get',
    }) 
}
/**
 * 删除流程调度管理维护
 * @param {*} params 
 */
export function deleteFlowTpye(params) {
    return request({
        url : '/ppmFlowType/delete',
        method : 'delete',
        params
    }) 
}
/**
 * 添加更新方法
 * @param {*} data 
 */
export function updateFlowType(data) {
    return request({
        url : '/ppmFlowType/update',
        method : 'post',
        data
    }) 
}

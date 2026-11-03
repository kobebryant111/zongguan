/**
 * 日志维护 api
 */
import request from '@/utils/request'

/**
 * 获取分页数据
 * @param {*} data 
 */
export function getPagerData(data) {
    return request({
        url : '/ppmFlowLog/queryContainHisPager',
        method : 'post',
        data
    })
};
/**
 * 添加或者 修改
 * @param {} data 
 */
export function doUpdate(data) {
    return request({
        url : '/ppmFlowLog/update',
        method : 'post',
        data
    })
};
/**
 * 获取详情
 * @param {} data 
 */
export function getDateil(params) {
    return request({
        url : '/ppmFlowLog/queryDetail',
        method : 'get',
        params
    })
};
/**
 * 删除
 * @param {*} data 
 */
export function doDelete(data) {
    
    return request({
        url : '/ppmFlowLog/delete',
        method : 'delete',
        params:data
    })
};

/**
 * 一键转历史
 * @param {*} data 
 */
export function addToHis(data) {
    return request({
        url : '/ppmNewflowHistorylog/add',
        method : 'post',
        data
    })
};
/**
 * 历史日志更新||添加
 * @param {*} data 
 */
export function doHisUpdate(data) {
    return request({
        url : '/ppmNewflowHistorylog/update',
        method : 'post',
        data
    })
}
/**
 * 删除历史日志
 * @param {*} params 
 */
export function doHisDelete(params) {
    return request({
        url : '/ppmNewflowHistorylog/delete',
        method : 'delete',
        params
    })
}
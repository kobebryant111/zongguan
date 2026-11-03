/**
 * 宁管设备变更api
 */
import request from '@/utils/request'
/**
 * 分页
 * @param {*} params 
 */
export function pagerList(data) {
    return request({
        url: '/ppmProjectNewonline/queryPager',
        method : 'post',
        data
    })
}
/**
 * 获取当前发起人信息
 */
export function getCurrentInfo(){
    return request({
        url: '/ppmProjectNewonline/getCurrentInfo',
        method : 'get',
    })
}

/**
 * 详情
 * @param {*} data 
 */
export function queryDetail(params) {
    return request({
        url: '/ppmProjectNewonline/queryDetail',
        method : 'get',
        params
    })
}


/**
 * 分行科技二级部经理审批
 * @param {*} data 
 */
export function approveFlowByEJBManagers(data) {
    return request({
        url: '/ppmProjectNewonline/approveFlowByEJBManagers',
        method : 'post',
        data
    })
}

/**
 * 分行业务总经理审批
 * @param {*} data 
 */
export function approveFlowByBusZJL(data) {
    return request({
        url: '/ppmProjectNewonline/approveFlowByBusZJL',
        method : 'post',
        data
    })
}

/**
 * 分行科技总经理审批
 * @param {*} data 
 */
export function approveFlowByTechZJL(data) {
    return request({
        url: '/ppmProjectNewonline/approveFlowByTechZJL',
        method : 'post',
        data
    })
}

/**
 * 获取上线详情
 * @param {*} params 
 */
export function getOnlineInfo(params) {
    return request({
        url: '/ppmProjectOnlinefjh/getOnlineInfo',
        method : 'get',
        params
    })
}

/**
 * 获取项目列表
 * @param {*} params 
 */
export function queryPpmProjectList(params) {
    return request({
        url: '/ppmProject/queryPpmProjectList',
        method : 'get',
        params
    })
}


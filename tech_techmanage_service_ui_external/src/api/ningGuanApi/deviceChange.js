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
        url: '/ppmNetworkStick/queryPager',
        method : 'post',
        data
    })
}
/**
 * 获取当前发起人信息
 */
export function getCurrentInfo(){
    return request({
        url: '/ppmNetworkStick/getCurrentInfo',
        method : 'get',
    })
}
/**
 * 获取设备名称集合
 */
export function getDeviceList() {
    return request({
        url: '/ppmChagedeviceMaster/queryList',
        method : 'get',
    })
}
/**
 * 发起流程
 * @param {*} data 
 */
export function initiateProcess(data) {
    return request({
        url: '/ppmNetworkStick/initiate',
        method : 'post',
        data
    })
}

/**
 * 保存草稿
 * @param {*} data 
 */
export function draftProcess(data) {
    return request({
        url: '/ppmNetworkStick/draft',
        method : 'post',
        data
    })
}

/**
 * 详情
 * @param {*} data 
 */
export function queryDetail(params) {
    return request({
        url: '/ppmNetworkStick/queryDetail',
        method : 'get',
        params
    })
}

/**
 * 二级部经理审批
 * @param {*} data 
 */
export function ministryManager(data) {
    return request({
        url: '/ppmNetworkStick/ministryManager',
        method : 'post',
        data
    })
}

/**
 * 处理人实施
 * @param {*} data 
 */
export function dealing(data) {
    return request({
        url: '/ppmNetworkStick/dealing',
        method : 'post',
        data
    })
}

/**
 * 监控变更确认
 * @param {*} data 
 */
export function monitor(data) {
    return request({
        url: '/ppmNetworkStick/monitorChange',
        method : 'post',
        data
    })
}

/**
 * 出入库录入变更确认
 * @param {*} data 
 */
export function skinprinterLib(data) {
    return request({
        url: '/ppmNetworkStick/skinprinterLib',
        method : 'post',
        data
    })
}

/**
 * CMDB录入确认
 * @param {*} data 
 */
export function cmdbEntry(data) {
    return request({
        url: '/ppmNetworkStick/cmdbEntry',
        method : 'post',
        data
    })
}

/**
 * 根据设备名称id查询选项名称
 * @param {*} data 
 */
export function queryListForMid(params) {
    return request({
        url: '/ppmChagedeviceChildren/queryList',
        method : 'get',
        params
    })
}

/**
 * 导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        url: '/ppmNetworkStick/exportExcel',
        method : 'post',
        data,
        responseType: 'blob',
    })
}
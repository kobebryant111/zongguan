/**
 * 定时器管理 js
 */
import request from '@/utils/request'
/**
 * 分页
 * @param {*} data 
 */
export function getPager(data) {
    return request({
        url : '/quartz/ppmQuartzManager/queryPager',
        method : 'post',
        data
    })
}
/**
 * 添加
 * @param {*} data 
 */
export function doAdd(data) {
    return request({
        url : '/quartz/ppmQuartzManager/add',
        method : 'post',
        data
    })
}

/**
 * 修改
 * @param {*} data 
 */
export function doUpdate(data) {
    return request({
        url : '/quartz/ppmQuartzManager/update',
        method : 'post',
        data
    })
}

/**
 * 详情
 * @param {*} params 
 */
export function getDetail(params) {
    return request({
        url : '/quartz/ppmQuartzManager/getDetail',
        method : 'get',
        params
    })
}

/**
 * 删除
 * @param {*} params 
 */
export function doDelete(params) {
    return request({
        url : '/quartz/ppmQuartzManager/delete',
        method : 'delete',
        params
    })
}

/**
 * 启用
 * @param {*} data 
 */
export function doEnable(data) {
    return request({
        url : '/quartz/ppmQuartzManager/doEnable',
        method : 'post',
        data
    })
}

/**
 * 停用
 * @param {*} data 
 */
export function doDisable(data) {
    return request({
        url : '/quartz/ppmQuartzManager/doDisable',
        method : 'post',
        data
    })
}

/**
 * 立即执行
 * @param {*} data 
 */
export function doRunNowJob(data) {
    return request({
        url : '/quartz/ppmQuartzManager/executeImmediately',
        method : 'post',
        data
    })
}
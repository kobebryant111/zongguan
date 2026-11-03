/**
 * 组织考核-一级部相关接口
 */
import request from '@/utils/request'

/**
 * 一级部指标列表查询
 * @param {*} data 
 */
 export function queryList(data) {
    return request({
        url: '/ppmDepkpiDepDetail/queryList',
        method: 'post',
        data
    })
}

/**
 * 一级部指标新增
 * @param {*} data 
 */
 export function addDepDetail(data) {
    return request({
        url: '/ppmDepkpiDepDetail/addDepDetail',
        method: 'post',
        data
    })
}

/**
 * 一级部指标修改
 * @param {*} data 
 */
 export function updateDepDetail(data) {
    return request({
        url: '/ppmDepkpiDepDetail/updateDepDetail',
        method: 'post',
        data
    })
}

/**
 * 一级部指标删除
 * @param {*} data 
 */
 export function deleteDepDetail(data) {
    return request({
        url: '/ppmDepkpiDepDetail/deleteDepDetail',
        method: 'delete',
        params: data
    })
}

/**
 * 一级部指标提交
 * @param {*} data 
 */
 export function commitDepDetail(data) {
    return request({
        url: '/ppmDepkpiDepDetail/commitDepDetail',
        method: 'post',
        data
    })
}

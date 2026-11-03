/**
 * 组织考核-模板管理
 */
import request from '@/utils/request'

/**
 * 分页列表查询
 * @param {*} data 
 */
export function queryModelPagerList(data) {
    return request({
        url: '/ppmDepkpiModel/queryModelPagerList',
        method: 'post',
        data
    })
}

/**
 * 列表查询
 * @param {*} data 
 */
export function queryModelList(data) {
    return request({
        url: '/ppmDepkpiModel/queryModelList',
        method: 'post',
        data
    })
}

/**
 * 新增
 * @param {*} data 
 */
export function addModel(data) {
    return request({
        url: '/ppmDepkpiModel/addModel',
        method: 'post',
        data
    })
}

/**
 * 修改
 * @param {*} data 
 */
export function updateModel(data) {
    return request({
        url: '/ppmDepkpiModel/updateModel',
        method: 'post',
        data
    })
}

/**
 * 删除
 * @param {*} data 
 */
export function deleteModel(data) {
    return request({
        url: '/ppmDepkpiModel/deleteModel',
        method: 'delete',
        params: data
    })
}
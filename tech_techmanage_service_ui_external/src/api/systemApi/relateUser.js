/**
 * 外包人员账号关联
 */
import request from '@/utils/request'
/**
 * 分页列表
 */
export function pageList(data) {
    return request({
        url : '/ppmRelateuser/queryPager',
        method : 'post',
        data
    })
}

/**
 * 添加|编辑
 * @param {*} data 
 */
export function edit(data) {
    return request({
        url : '/ppmRelateuser/update',
        method : 'post',
        data
    })
}
/**
 * 用户树
 * @param {*} params
 */
export function userTreeList(params) {
    return request({
        url : '/commEmployee/queryEmployees',
        method : 'get',
        params
    })
}
/**
 * 删除外包账号关联
 * @param {*} params 
 */
export function deleteItem(params) {
    return request({
        url : '/ppmRelateuser/delete',
        method : 'delete',
        params
    })
}
/**
 * 组织考核-标题管理
 */
import request from '@/utils/request'

/**
 * 考核标题列表查询
 * @param {*} data 
 */
export function queryTitleList(data) {
    return request({
        url: '/ppmDepkpiTitle/queryTitleList',
        method: 'post',
        data
    })
}

/**
 * 考核标题树查询
 * @param {*} data 
 */
 export function queryTitleTreeData(data) {
    return request({
        url: '/ppmDepkpiTitle/queryTitleTreeData',
        method: 'post',
        data
    })
}

/**
 * 公共考核制定-考核标题树查询
 * @param {*} data 
 */
 export function queryGgTitleTreeData(data) {
    return request({
        url: '/ppmDepkpiTitle/queryGgTitleTreeData',
        method: 'post',
        data
    })
}

/**
 * 新增标题
 * @param {*} data 
 */
export function addTitle(data) {
    return request({
        url: '/ppmDepkpiTitle/addTitle',
        method: 'post',
        data
    })
}

/**
 * 修改标题
 * @param {*} data 
 */
export function updateTitle(data) {
    return request({
        url: '/ppmDepkpiTitle/updateTitle',
        method: 'post',
        data
    })
}

/**
 * 删除标题
 * @param {*} data 
 */
export function deleteTitle(data) {
    return request({
        url: '/ppmDepkpiTitle/deleteTitle',
        method: 'delete',
        params: data
    })
}
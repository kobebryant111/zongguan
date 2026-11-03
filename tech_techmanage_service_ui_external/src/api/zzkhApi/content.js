/**
 * 组织考核-考核内容管理
 */
import request from '@/utils/request'

/**
 * 考核内容查询
 * @param {*} data 
 */
export function queryContentList(data) {
    return request({
        url: '/ppmDepkpiContent/queryContentList',
        method: 'post',
        data
    })
}

/**
 * 公共指标制定-考核内容查询
 * @param {*} data 
 */
 export function queryGgzdContentList(data) {
    return request({
        url: '/ppmDepkpiContent/queryGgzdContentList',
        method: 'post',
        data
    })
}

/**
 * 公共待办内容查询
 * @param {*} data 
 */
 export function queryGgDbContentList(data) {
    return request({
        url: '/ppmDepkpiContent/queryGgDbContentList',
        method: 'post',
        data
    })
}

/**
 * 评价考核内容查询
 * @param {*} data 
 */
 export function getEvalContentList(data) {
    return request({
        url: '/ppmDepkpiContent/getEvalContentList',
        method: 'post',
        data
    })
}

/**
 * 新增考核内容
 * @param {*} data 
 */
export function addContent(data) {
    return request({
        url: '/ppmDepkpiContent/addContent',
        method: 'post',
        data
    })
}

/**
 * 制定修改考核内容
 * @param {*} data 
 */
 export function updateZdContent(data) {
    return request({
        url: '/ppmDepkpiContent/updateZdContent',
        method: 'post',
        data
    })
}

/**
 * 修改考核内容
 * @param {*} data 
 */
export function updateContent(data) {
    return request({
        url: '/ppmDepkpiContent/updateContent',
        method: 'post',
        data
    })
}

/**
* 自评
* @param {*} data 
*/
export function selfeval(data) {
    return request({
        url: '/ppmDepkpiContent/selfeval',
        method: 'post',
        data
    })
}

/**
* 评价
* @param {*} data 
*/
export function evalContent(data) {
    return request({
        url: '/ppmDepkpiContent/eval',
        method: 'post',
        data
    })
}



/**
 * 删除考核内容
 * @param {*} data 
 */
export function deleteContent(data) {
    return request({
        url: '/ppmDepkpiContent/deleteContent',
        method: 'delete',
        params: data
    })
}

/**
 * 考核数据查询
 * @param {*} data 
 */
export function queryZdySumpoint(data) {
    return request({
        url: '/ppmDepkpiContent/queryZdySumpoint',
        method: 'post',
        data
    })
}

/**
 * 导出自定义内容
 * @param {*} data 
 */
 export function exportContent(data) {
    return request({
        url: '/ppmDepkpiContent/exportContent',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

/**
 * 导出公共内容
 * @param {*} data 
 */
export function exportGgContent(data) {
    return request({
        url: '/ppmDepkpiContent/exportGgContent',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 组织考核-指标管理
 */
import request from '@/utils/request'

/**
 * 分页列表查询
 * @param {*} data 
 */
export function queryPagerList(data) {
    return request({
        url: '/ppmDepkpiTarget/queryPagerList',
        method: 'post',
        data
    })
}

/**
 * 列表查询
 * @param {*} data 
 */
 export function queryTargetList(data) {
    return request({
        url: '/ppmDepkpiTarget/queryList',
        method: 'post',
        data
    })
}

/**
 * 新增指标
 * @param {*} data 
 */
export function add(data) {
    return request({
        url: '/ppmDepkpiTarget/addPpmDepkpiTarget',
        method: 'post',
        data
    })
}

/**
 * 修改指标
 * @param {*} data 
 */
export function update(data) {
    return request({
        url: '/ppmDepkpiTarget/updatePpmDepkpiTarget',
        method: 'post',
        data
    })
}

/**
 * 删除指标
 * @param {*} data 
 */
 export function deleteTarget(data) {
    return request({
        url: '/ppmDepkpiTarget/delete',
        method: 'delete',
        params: data
    })
}


/**
 * 指标导入
 * @param {*} data 
 */
 export function importExcel(data) {
    return request({
        url: '/ppmDepkpiTarget/importExcel',
        method: 'post',
        data
    })
}

/**
 * 指标导出
 * @param {*} data 
 */
 export function exportDepkpiTarget(data) {
    return request({
        url: '/ppmDepkpiTarget/exportDepkpiTarget',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
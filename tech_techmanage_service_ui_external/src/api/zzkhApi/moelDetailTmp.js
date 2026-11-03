/**
 * 组织考核-模板临时分配
 */
 import request from '@/utils/request'
 
 /**
 * 模板已分配指标查询
 * @param {*} data 
 */
 export function queryModelTargetList(data) {
    return request({
        url: '/ppmDepkpiModelDetailTmp/queryModelTargetList',
        method: 'post',
        data
    })
}

/**
 * 模板分配指标
 * @param {*} data 
 */
 export function modelAssignTarget(data) {
    return request({
        url: '/ppmDepkpiModelDetailTmp/modelAssignTarget',
        method: 'post',
        data
    })
}

/**
 * 修改模板分配指标
 * @param {*} data 
 */
 export function updateModelTarget(data) {
    return request({
        url: '/ppmDepkpiModelDetailTmp/updateModelTarget',
        method: 'post',
        data
    })
}

/**
 * 删除模板分配指标
 * @param {*} data 
 */
 export function deleteModelTarget(data) {
    return request({
        url: '/ppmDepkpiModelDetailTmp/deleteModelTarget',
        method: 'delete',
        params: data
    })
}

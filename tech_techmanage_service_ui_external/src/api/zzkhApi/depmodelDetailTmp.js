/**
 * 组织考核-部门指标临时
 */
 import request from '@/utils/request'

/**
 * 部门已分配指标查询
 * @param {*} data 
 */
 export function queryDepTargetList(data) {
    return request({
        url: '/ppmDepkpiDepmodelDetailTmp/queryDepTargetList',
        method: 'post',
        data
    })
}

/**
 * 部门分配指标
 * @param {*} data 
 */
 export function depAssignTarget(data) {
    return request({
        url: '/ppmDepkpiDepmodelDetailTmp/depAssignTarget',
        method: 'post',
        data
    })
}

/**
 * 部门修改已分配指标
 * @param {*} data 
 */
 export function updateDepTarget(data) {
    return request({
        url: '/ppmDepkpiDepmodelDetailTmp/updateDepTarget',
        method: 'post',
        data
    })
}

/**
 * 部门删除已分配指标
 * @param {*} data 
 */
 export function deleteDepTarget(data) {
    return request({
        url: '/ppmDepkpiDepmodelDetailTmp/deleteDepTarget',
        method: 'post',
        params: data
    })
}

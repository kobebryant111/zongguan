/**
 * 流程管理 - 节点维护
 */
import request from '@/utils/request'
/**
 * 节点维护树
 */
export function getNodeTree(data) {
    return request({
        url : '/ppmFlowInfo/queryList',
        method : 'post',
        data
    })
}
/**
 * 获取节点详情
 * @param {id} data
 */
export function getNodeDateil(params) {
    return request({
        url : '/ppmFlowInfo/queryDetail',
        method : 'get',
        params
    })
}


/**
 * 保存以及更新
 * @param {*} data
 */
export function updatedFlowNode(data) {
    return request({
        url : '/ppmFlowInfo/update',
        method : 'post',
        data
    })
}

/**
 * 删除
 * @param {*} data
 */
export function deletFlow(params) {
    return request({
        url : '/ppmFlowInfo/delete',
        method : 'delete',
        params
    })
}
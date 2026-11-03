/**
 * 关联关系
 */
import request from '@/utils/request'

/**
 * 查询
 * @param {*} params 
 */
export function queryRdProjectContractRelation(params) {
    return request({
        url : '/rdProjectContractRelation/query',
        method : 'get',
        params
    })
}
/**
 * 支付记录
 * @param {*} data 
 */
export function saveRdProjectContractRelation(data) {
    return request({
        url : '/rdProjectContractRelation/save',
        method : 'post',
        data
    })
}


/**
 * 删除
 * @param {*} params 
 */
export function deleteRdProjectContractRelation(params) {
    return request({
        url : '/rdProjectContractRelation/delete',
        method : 'get',
        params
    })
}


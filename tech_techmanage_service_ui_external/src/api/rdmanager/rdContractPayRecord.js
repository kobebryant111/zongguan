/**
 * 支付记录
 */
import request from '@/utils/request'

/**
 * 分页
 * @param {*} params 
 */
export function queryRdContractPayRecord(params) {
    return request({
        url : '/rdContractPayRecord/queryByRdProjectContractId',
        method : 'get',
        params
    })
}
/**
 * 支付记录
 * @param {*} data 
 */
export function saveRdContractPayRecord(data) {
    return request({
        url : '/rdContractPayRecord/save',
        method : 'post',
        data
    })
}


/**
 * 删除
 * @param {*} params 
 */
export function deleteRdContractPayRecord(params) {
    return request({
        url : '/rdContractPayRecord/delete',
        method : 'get',
        params
    })
}


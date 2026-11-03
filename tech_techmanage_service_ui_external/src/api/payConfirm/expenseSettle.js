/*
*   费用结算
*/
import request from '@/utils/request'
const api = "/expenseSettlement"
/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : api + '/queryPager',
        method : 'post',
        data
    })
}
/**
 *查询详情
 * 
 */
export function queryDetail(id) {
    return request({
        url : api + `/queryDetails/${id}`,
        method : 'get',
    })
}
/**
 *费用详情
 * 
 */
 export function queryDetailList(data) {
    return request({
        url : api + `/queryDetails`,
        method : 'post',
        data
    })
}
/**
 *新增支付确认
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : api + '/add',
        method : 'post',
        data
    })
}

/*
*   修改支付确认
*/
export function updatePayment(data) {
    return request({
        url: api + "/update",
        method: "put",
        data
    })
}
/*
*   删除支付确认
*/
export function delPayment(id) {
    return request({
        url: api + `/delete/${id}`,
        method: "delete",
    })
}

/**
 * 流转 -- 二级部审批
 * @param {*} data 
 */
export function twoManagerAudit(data) {
    return request({
        url : api + '/approvalCirculate',
        method : 'post',
        data
    });
}

/**
 * 流转 -- 一级部审批
 * @param {*} data 
 */
export function oneManagerAudit(data) {
    return request({
        url : api + '/confirm',
        method : 'post',
        data
    });
}
/**
 * 需求审批详细信息(根据流程Id查询数据)
 * @param {*} params 
 */
export function queryProcessDetail(data) {
    return request({
        url : '/expenseSettlement/queryListByMapBasic',
        method : 'post',
        data
    })
}
/*
*   撤回修改流程状态
*/
export function updateStatus(data) {
    return request({
        url: api + "/updateByInstanceId",
        method: "put",
        data
    })
}
/*
*   删除流程状态
*/
export function deleteFlow(data) {
    return request({
        url: api + "/deleteFlow",
        method: "delete",
        data
    })
}
/*
*   查询名称是否相似
*/
export function queryListByMapSmall(id) {
    return request({
        url: api + `/queryListByMapSmall`,
        method: "post",
        id
    })
}
/**
 * 上传
 * @param {*} data 
 */
export function uploadExcel(data) {
    return request({
        url : api + '/uploadExcel',
        method :'post',
        data
    })
}






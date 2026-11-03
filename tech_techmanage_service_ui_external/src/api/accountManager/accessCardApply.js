/*
*   门禁卡制卡/归还流程
*/
import request from '@/utils/request'
const api = "/accessCardApply"
/**
 * init 发起流程
 * @param {*} data 
 */
export function initFlow(data) {
    return request({
        url : api + '/initFlow',
        method : 'post',
        data
    })
}
/**
 * 总经理室审批
 * @param {*} data 
 */
export function gmApproval(data) {
    return request({
        url : api + `/gmApproval`,
        method : 'post',
        data
    })
}
/**
 * 监保部审批
 * @param {*} data 
 */
export function monitorApproval(data) {
    return request({
        url : api + '/monitorApproval',
        method : 'post',
        data
    })
}

/*
*  监保部确认 末节点
*/
export function finishFlow(data) {
    return request({
        url: api + "/finishFlow",
        method: "post",
        data
    })
}


/**
 * 查询当前用户登录信息
 */
export function queryUserDept(params) {
    return request({
        url : '/commDepartment/queryUserDept',
        method : 'get',
        params
    })
}

/**
 * 查询详情
 */
export function queryDetail(params) {
    return request({
        url : api + '/queryDetail',
        method : 'get',
        params
    })
}


/**
 * 删除流程
 */
export function deleteFlows(params) {
    return request({
        url : api + '/delete',
        method : 'delete',
        params
    })
}

export function exportInfo(data) {
    return request({
        responseType: 'blob',
        url : api + '/exportInfo',
        method : 'post',
        data,
    })
}







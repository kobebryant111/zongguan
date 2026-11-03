/**
 *  人员移交
 */
import request from '@/utils/request'

/*
*  查询可选人员
*/
export function initStaff(params) {
    return request({
        url: '/staffTransfer/initStaff?personInCharge=' + params,
        method: 'get',
    })
};

/*
*  查询可选系统名称
*/
export function initSystemInfo() {
    return request({
        url: '/staffTransfer/initSystemInfo',
        method: "get",
    })
}

/*
*  查询可选项目名称
*/
export function initProjectInfo() {
    return request({
        url: '/staffTransfer/initProjectInfo',
        method: "get",
    })
}

/*
*  草稿
*/
export function createFlow(data) {
    return request({
        url: '/staffTransfer/createFlow',
        method: 'post',
        data
    })
}

/*
* 发起流程 
*/
export function initFlow(data) {
    return request({
        url: "/staffTransfer/initFlow",
        method: "post",
        data,
        contentType: 'application/json'
    })
}

/*
* 发起流程 获取详情
*/
export function queryDetail(data) {
    return request({
        url: "/staffTransfer/queryDetail",
        method: "post",
        data,
    })
}

/*
* 发起流程 删除
*/
export function deleteFlows(data) {
    return request({
        url: "/staffTransfer/delete",
        method: "delete",
        data,
    })
}


/*
* 二级部经理审批
*/
export function dhApproval(data) {
    return request({
        url: "/staffTransfer/dhApproval",
        method: "post",
        data,
    })
}

/*
* 移交后二级部经理审批 
*/
export function dhApprovalTransfer(data) {
    return request({
        url: "/staffTransfer/dhApprovalTransfer",
        method: "post",
        data,
    })
}

/*
* 现项目经理审批 
*/
export function pmApprovalTransfer(data) {
    return request({
        url: "/staffTransfer/pmApprovalTransfer",
        method: "post",
        data,
    })
}

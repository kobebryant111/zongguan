/*
*   项目转人力
*/
import request from '@/utils/request'
const api = "/outsrcAttrChgApply"

/**
 *存草稿
 * @param {*} data 
 */
export function draft(data) {
    return request({
        url : api + '/draft',
        method : 'post',
        data
    })
}

/*
*   修改支付确认
*/
export function updateProTransfer(data) {
    return request({
        url: api + "/update",
        method: "put",
        data
    })
}
/*
*   删除支付确认
*/
export function delProTransfer(id) {
    return request({
        url: api + `/delete/${id}`,
        method: "delete",
    })
}
/*
*   通过名称查信息
*/
export function getOutsBasicInfo(data) {
    return request({
        url: '/outsrcAttrChgApply' + `/getOutsBasicInfo`,
        method: "post",
        data
    })
}
/**  查询季度信息*/
export function onePaymentConfirm(data) {
    return request({
        url: api + `/onePaymentConfirm`,
        method: "post",
        data
    })
}
/** 查询人员信息 */
export function queryPersons(data) {
    return request({
        url: api + `/queryPersons`,
        method: "post",
        data
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

/**
 * 需求审批详细信息(根据流程Id查询数据)
 * @param {*} params 
 */
 export function queryProcessDetail(data) {
    return request({
        url : api + '/queryDetailsByInsId',
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
*   删除流程
*/
export function deleteFlow(data) {
    return request({
        url: api + "/deleteFlow",
        method: "delete",
        data
    })
}
/** 发起申请 */
export function approvalCirculate(data) {
    return request({
        url : api + '/approvalCirculate',
        method : 'post',
        data
    });
}
/**
 * 流转 -- 二级部审批
 * @param {*} data 
 */
export function dhApproval(data) {
    return request({
        url : api + '/approvedByMgrL2',
        method : 'post',
        data
    });
}
/**
 * 流转 -- 一级部审批
 * @param {*} data 
 */
 export function gmApproval(data) {
    return request({
        url : api + '/approvedByMgrL1',
        method : 'post',
        data
    });
}

/**
 *  查询总办议案
 * @param {*} data 
 */
 export function queryZBYAValid(params) {
    return request({
        url : api + '/queryZBYAValid',
        method : 'get',
        params
    })
}

/**
 *  查询合同
 * @param {*} data 
 */
 export function queryContractValid(params) {
    return request({
        url : api + '/queryContractValid',
        method : 'get',
        params
    })
}

/**
 *  查询系统名称
 * @param {*} data 
 */
 export function querySystem(params) {
    return request({
        url : '/ppmSystemInfo/queryList',
        method : 'get',
        params
    })
}

/**
 *  查询项目名称
 * @param {*} data 
 */
 export function queryProject(params) {
    return request({
        url : '/ppmProjectInfo/queryList',
        method : 'get',
        params
    })
}

/**
 *  查询项目名称
 * @param {*} data 
 */
export function queryNewList(params) {
    return request({
        url : '/ppmProjectInfo/queryNewList',
        method : 'get',
        params
    })
}




/**
 * 查询taskId
 * @param {*} params 
 */
//  export function getDbDetails(data) {
//     return request({
//         url : '/workflow' + '/getDbDetails',
//         method : 'post',
//         data
//     })
// }




/*
*   支付确认
*/
import request from '@/utils/request'
const api = "/paymentConfirm"
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
/** 查询支付确认详情 */
export function queryDetailList(data) {
    return request({
        url : api + `/queryListByIds/${data}`,
        method : 'get',
    })
}
/**
 *新增支付确认
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : api + '/approvalCirculate',
        method : 'post',
        data
    })
}

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
export function queryProcessDetail(id) {
    return request({
        url : api + `/queryDetailsByInstanceId/${id}`,
        method : 'get',
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
*   撤回修改流程状态
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
export function twoManagerAudit(data) {
    return request({
        url : api + '/confirm',
        method : 'post',
        data
    });
}

/**
 * 合并信息
 * @param {*} params 
 */
export function queryForSettle(data) {
    return request({
        url : api + '/queryForSettle',
        method : 'post',
        data
    })
}

/**
 * 查询taskId
 * @param {*} params 
 */
 export function getDbDetails(data) {
    return request({
        url : '/workflow' + '/getDbDetails',
        method : 'post',
        data
    })
}



/**
 * 人力结算费用导出
 * @param {*} data 
 */
export function exportExcelPersons(data) {
    return request({
        responseType: 'blob',
        url: api + '/exportExcelPersons',
        method: 'post',
        data
    })
};


/**
 * 考勤结算费用导出
 * @param {*} data 
 */
 export function exportAttendanceDetails(data) {
    return request({
        responseType: 'blob',
        url: api + '/exportAttendanceDetails',
        method: 'post',
        data
    })
};
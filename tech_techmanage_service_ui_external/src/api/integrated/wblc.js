/**
 * 外包离场 
 */
import request from '@/utils/request'
/**
 * 根据流程instanceid查询数据
 * @param {*} data 
 */
export function getBaseInfo(data) {
    return request({
        url : '/ppmOutsourcingLzsq/getBaseInfo',
        method : 'post',
        data
    })
}

/**
 * 发起申请
 * @param {*} data 
 */
export function outLeaveStart(data) {
    return request({
        url : '/ppmOutsourcingLzsq/outLeaveStart',
        method : 'post',
        data
    })
}

/**
 * 保存流程数据
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : '/ppmOutsourcingLzsq/save',
        method : 'post',
        data
    })
}

/**
 * 更新
 * @param {*} data 
 */
export function update(data) {
    return request({
        url : '/ppmOutsourcingLzsq/update',
        method : 'post',
        data
    })
}

/**
 * 删除
 * @param {*} params 
 */
export function del(params) {
    return request({
        url : '/ppmOutsourcingLzsq/delete',
        method : 'delete',
        params
    });
}

/**
 * 行内负责人确认
 * @param {*} data 
 */
export function hnfzrConfirm(data) {
    return request({
        url : '/ppmOutsourcingLzsq/confirm',
        method : 'post',
        data
    })
}

/**
 * 账号注销
 * @param {*} data 
 */
export function accountLogOut(data) {
    return request({
        url : '/ppmOutsourcingLzsq/accountLogOut',
        method : 'post',
        data
    })
}

/**
 * 二级部经理审批
 * @param {*} data 
 */
export function managerApprove(data) {
    return request({
        url : '/ppmOutsourcingLzsq/managerApprove',
        method : 'post',
        data
    })
}

/**
 * 资源管理员审批
 * @param {*} data 
 */
export function resourceManagerApprove(data) {
    return request({
        url : '/ppmOutsourcingLzsq/resourceManagerApprove',
        method : 'post',
        data
    })
}
/**
 * 注销堡垒机
 * @param {*} data 
 */
export function logoutBej(data) {
    return request({
        url : '/ppmOutsourcingLzsq/logoutFortressMachice',
        method :'post',
        data
    })
}
/**
 * 判断流程是否存在
 * @param {*} data 
 */
export function isExist(data) {
    return request({
        url : '/ppmOutsourcingLzsq/isExist',
        method :'post',
        data
    })
}
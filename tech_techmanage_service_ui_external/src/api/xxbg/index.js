// 信息变更申请
import request from '@/utils/request'

// 信息变更申请 在岗人员
export function presenceInfo(params) {
    return request({
        url: "/ppmOutsourcUpdate/presenceInfo",
        method: 'get',
        params
    })
}

// 查询详情-发起流程
export function queryDetail(data) {
    return request({
        url: "/ppmOutsourcUpdate/queryDetail",
        method: 'post',
        data
    })
}

// 提交-发起流程
export function initFlow(data) {
    return request({
        url: "/ppmOutsourcUpdate/initFlow",
        method: 'post',
        data
    })
}

// 草稿-流程
export function createFlow(data) {
    return request({
        url: "/ppmOutsourcUpdate/createFlow",
        method: 'post',
        data
    })
}

// 二级部经理审批-流程
export function dhApproval(data) {
    return request({
        url: "/ppmOutsourcUpdate/dhApproval",
        method: 'post',
        data
    })
}

// 二级部经理审批-流程
export function gmApproval(data) {
    return request({
        url: "/ppmOutsourcUpdate/gmApproval",
        method: 'post',
        data
    })
}

// 流程删除-流程
export function deleteFlows(data) {
    return request({
        url: "/ppmOutsourcUpdate/delete",
        method: 'delete',
        data
    })
}
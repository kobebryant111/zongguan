/**
 *  提前入场申请
 */
import request from '@/utils/request'

/*
*  分页
*/
export function queryPager(data) {
    return request({
        url: '/ppmEarlyCourseApply/queryPager',
        method: 'post',
        data
    })
}

/*
*  申请人部门
*/
export function queryUserDept() {
    return request({
        url: '/ppmEarlyCourseApply/queryUserDept',
        method: 'get',
    })
}

/*
*  入场者信息
*/
export function interView(params) {
    return request({
        url: '/ppmEarlyCourseApply/interView',
        method: 'get',
        params
    })
}

/*
*  导出
*/
export function exportExcel(data) {
    return request({
        url: '/ppmEarlyCourseApply/exportExcel',
        method: 'post',
        data,
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        responseType: 'blob'
    })
}

/*
*  草稿
*/
export function createFlow(data) {
    return request({
        url: '/ppmEarlyCourseApply/createFlow',
        method: 'post',
        data
    })
}

/*
* 发起流程 
*/
export function initFlow(data) {
    return request({
        url: "/ppmEarlyCourseApply/initFlow",
        method: "post",
        data,
    })
}

/*
* 发起流程 获取详情
*/
export function queryDetail(data) {
    return request({
        url: "/ppmEarlyCourseApply/queryDetail",
        method: "post",
        data,
    })
}

/*
* 发起流程 删除
*/
export function toDelete(data) {
    return request({
        url: "/ppmEarlyCourseApply/delete",
        method: "delete",
        data,
    })
}


/*
* 二级部经理发起流程 
*/
export function dhApproval(data) {
    return request({
        url: "/ppmEarlyCourseApply/dhApproval",
        method: "post",
        data,
    })
}

/*
* 二级部经理发起流程 
*/
export function gmApproval(data) {
    return request({
        url: "/ppmEarlyCourseApply/gmApproval",
        method: "post",
        data,
    })
}

/*
* 是否补全入场材料
*/
export function queryPmPager(data) {
    return request({
        url: "/ppmEarlyCourseApply/queryPmPager",
        method: 'post',
        data,
    })
}

/*
* 是否补全入场材料 - 提交
*/
export function updateInfo(data) {
    return request({
        url: "/ppmEarlyCourseApply/updateInfo",
        method: 'post',
        data,
    })
}

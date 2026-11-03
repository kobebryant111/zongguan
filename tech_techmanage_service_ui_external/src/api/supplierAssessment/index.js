/*
*   外包考核
*/
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/supplierAssess/queryPager',
        method : 'post',
        data
    })
}

/**
 * 供应商打分查询
 * @param {*} params
 */
export function relationList(params) {
    return request({
        url: "/supplierRelation/relationList",
        method: 'get',
        params
    })
}

/*
*  导出
*/
export function download(params) {
    return request({
        url: '/supplierAssess/download',
        method: 'get',
        params,
        // headers: {
        //     'Content-Type': 'application/json;charset=UTF-8'
        // },
        responseType: 'blob'
    })
}

/*
*  草稿
*/
export function createFlow(data) {
    return request({
        url: '/supplierAssess/createFlow',
        method: 'post',
        data,
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

/*
* 发起流程 
*/
export function initFlow(data) {
    return request({
        url: "/supplierAssess/initFlow",
        method: "post",
        data,
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

/*
* 发起流程 获取详情
*/
export function queryDetail(data) {
    return request({
        url: "/supplierAssess/queryDetail",
        method: "post",
        data,
    })
}

/*
* 发起流程 删除
*/
export function toDelete(data) {
    return request({
        url: "/supplierAssess/delete",
        method: "delete",
        data,
    })
}


/*
* 二级部经理发起流程 
*/
export function dhApproval(data) {
    return request({
        url: "/supplierAssess/dhApproval",
        method: "post",
        data,
    })
}

/*
* 二级部经理发起流程 
*/
export function gmApproval(data) {
    return request({
        url: "/supplierAssess/gmApproval",
        method: "post",
        data,
    })
}

/*
* 是否补全入场材料
*/
export function queryPmPager(data) {
    return request({
        url: "/supplierAssess/queryPmPager",
        method: 'post',
        data,
    })
}

/*
* 是否补全入场材料 - 提交
*/
export function updateInfo(data) {
    return request({
        url: "/supplierAssess/updateInfo",
        method: 'post',
        data,
    })
}

/**
 * 获取所有外包商
 */
export function getListForNotCg() {
    return request({
        url : '/ppmOutsourcingInfo/getListForNotCg',
        method : 'get'
    })
}

/**
 * 获取所有外包商
 */
export function queryListForNotXmid(params) {
    return request({
        url : '/ppmProjectInfo/queryListForNotXmid',
        method : 'get',
        params
    })
}

/*
 * 草稿/详情下的供应商
 */
export function newqueryDetail(id) {
    return request({
        url : '/ppmOutsourcingInfo/queryDetail?orgcode=' + id,
        method : 'get',
    })
}
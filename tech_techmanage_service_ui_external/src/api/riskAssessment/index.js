/*
*   风险评估
*/
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/supplierRiskEval/queryPager',
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

/**
 * 查询一级部门
 * @param {*} params
 */
export function getDeptList(params) {
    return request({
        url: "/commDepartment/getDeptList",
        method: 'get',
        params
    })
}

/*
*  导出
*/
export function download(params) {
    return request({
        url: '/supplierRiskEval/download',
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
        url: '/supplierRiskEval/createFlow',
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
        url: "/supplierRiskEval/initFlow",
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
        url: "/supplierRiskEval/queryDetail",
        method: "post",
        data,
    })
}

/*
* 发起流程 删除
*/
export function toDelete(data) {
    return request({
        url: "/supplierRiskEval/delete",
        method: "delete",
        data,
    })
}


/*
* 二级部经理发起流程 
*/
export function dhApproval(data) {
    return request({
        url: "/supplierRiskEval/dhApproval",
        method: "post",
        data,
    })
}

/*
* 二级部经理发起流程 
*/
export function gmApproval(data) {
    return request({
        url: "/supplierRiskEval/gmApproval",
        method: "post",
        data,
    })
}

/*
* 是否补全入场材料
*/
export function queryPmPager(data) {
    return request({
        url: "/supplierRiskEval/queryPmPager",
        method: 'post',
        data,
    })
}

/*
* 是否补全入场材料 - 提交
*/
export function updateInfo(data) {
    return request({
        url: "/supplierRiskEval/updateInfo",
        method: 'post',
        data,
    })
}

/**
 * 获取合作满一年的供应商
 */
export function queryOneYears(params) {
    return request({
        url : '/supplierRoster/queryOneYears',
        method : 'get',
        params
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
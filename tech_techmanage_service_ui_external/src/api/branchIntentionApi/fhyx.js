/**
 * 分行意向需求
 */
import request from '@/utils/request';
/**
 * 分页
 * @param {*} data 
 */
export function pagerList(data) {
    return request({
        url : '/ppmDemandnewinfo/queryPager',
        method : 'post',
        data
    });
}
/**
 * 查看详情
 * @param {*} params 
 */
export function getDateil(params) {
    return request({
        url : '/ppmDemandnewinfo/queryDetail',
        method : 'get',
        params
    });
}
/**
 * 计算预算信息工作量
 * @param {*} data 
 */
export function budgetWorkload(params) {
    return request({
        url : '/ppmDemandnewinfo/budgetWorkload',
        method :'get',
        params
    })
}

/**
 * 预审材料上传
 * @param {*} data 
 */
export function preUpload(data) {
    return request({
        url : '/ppmDemandnewinfo/upload',
        method :'post',
        data
    })
}

/**
 * 预审材料删除
 * @param {*} data 
 */
export function revmoeDocument(data) {
    return request({
        url : '/ppmDocument/updatePojo',
        method :'post',
        data
    })
}
/**
 * 研发二级部经理流转
 * @param {*} data 
 */
export function preliminaryReview(data) {
    return request({
        url : '/ppmDemandnewinfo/preliminaryReview',
        method :'post',
        data
    })
}
/**
 * 分行科技部总经理
 * @param {*} params 
 */
export function getBranchGeneralManager(params) {
    return request({
        url : '/ppmDemandnewinfo/getBranchGeneralManager',
        method :'get',
        params
    })
}

/**
 * 业务总经理意向审批
 * @param {*} data 
 */
export function busGeneral(data) {
    return request({
        url : '/ppmDemandnewinfo/busGeneral',
        method :'post',
        data
    })
}
/**
 * 分行合规总经理审批
 * @param {*} data 
 */
export function complianceGeneral(data) {
    return request({
        url : '/ppmDemandnewinfo/complianceGeneral',
        method :'post',
        data
    })
}
/**
 * 分行人力资源部总经理审批
 * @param {*} data 
 */
export function humanResources(data) {
    return request({
        url : '/ppmDemandnewinfo/humanResources',
        method :'post',
        data
    })
}
/**
 * 科技总经理审批
 * @param {*} data 
 */
export function techGMApprove(data) {
    return request({
        url : '/ppmDemandnewinfo/techGMApprove',
        method :'post',
        data
    })
}
/**
 * 分行分管行长审批
 * @param {*} data 
 */
export function branchBank(data) {
    return request({
        url : '/ppmDemandnewinfo/branchBank',
        method :'post',
        data
    })
}
/**
 * 分行管理部审核
 * @param {*} data 
 */
export function branchMgt(data) {
    return request({
        url : '/ppmDemandnewinfo/branchMgt',
        method :'post',
        data
    })
}
/**
 * 架构部三新审核
 * @param {*} data 
 */
export function frameworkDept(data) {
    return request({
        url : '/ppmDemandnewinfo/frameworkDept',
        method :'post',
        data
    })
}
/**
 * 架构部分管副总审批
 * @param {*} data 
 */
export function frameworkbrandeputy(data) {
    return request({
        url : '/ppmDemandnewinfo/frameworkbrandeputy',
        method :'post',
        data
    })
}

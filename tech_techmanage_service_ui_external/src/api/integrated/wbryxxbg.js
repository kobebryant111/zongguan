/**
 * 外包人员信息变更 
 */
import request from '@/utils/request'
/**
 * 发起申请
 * @param {*} data 
 */
export function liuzhuan(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/liuzhuan',
        method : 'post',
        data
    })
}

/**
 * 存草稿
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/save',
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
        url : '/ppmOutsourcingChangeApply/delete',
        method : 'delete',
        params
    });
}

/**
 * 面试打分
 * @param {*} data 
 */
export function interviewscore(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/interviewscore',
        method : 'post',
        data
    })
}

/**
 * 综合部确认
 * @param {*} data 
 */
export function confirm(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/confirm',
        method : 'post',
        data
    })
}

/**
 * 二级部经理审批
 * @param {*} data 
 */
export function twoManager(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/twoManager',
        method : 'post',
        data
    })
}

/**
 * 录入结果
 * @param {*} data 
 */
export function resultinput(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/resultinput',
        method : 'post',
        data
    })
}

/**
 * 分管总经理
 * @param {*} data 
 */
export function deputyDirectorGeneral(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/deputyDirectorGeneral',
        method : 'post',
        data
    })
}

/**
 * 总经理审批
 * @param {*} data 
 */
export function manager(data) {
    return request({
        url : 'ppmOutsourcingChangeApply/manager',
        method : 'post',
        data
    })
}

/**
 * 查询中心部门树数据
 * @param {*} params 
 */
export function queryCenterDepTree(params) {
    return request({
        url : '/ppmOutsourcingChangeApply/queryCenterDepTree',
        method : 'get',
        params
    })
}

/**
 * 根据部门编号查询数据
 * @param {*} data 
 */
export function queryOutBaseInfos(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/queryOutBaseInfos',
        method : 'post',
        data
    })
}

/**
 * 表格数据删除
 * @param {*} params 
 */
export function delBgTableData(params) {
    return request({
        url : '/ppmOutsourcingChangeInfos/delete',
        method : 'delete',
        params
    });
}

/**
 * 获得当前登录人员的基本信息
 * @param {*} data 
 */
export function getEmpBaseInfo(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/getEmpBaseInfo',
        method : 'post',
        data
    });
}

/**
 * 根据流程instanceid查询数据
 * @param {*} params 
 */
export function getFLowInfo(params) {
    return request({
        url : '/ppmOutsourcingChangeApply/getFLowInfo',
        method : 'get',
        params
    })
}

/**
 * 根据流程instanceid查询数据
 * @param {*} data 
 */
export function getOutBaseInfo(data) {
    return request({
        url : '/ppmOutsourcingChangeApply/getOutBaseInfo',
        method : 'post',
        data
    })
}

/**
 * 查询合同下拉框(项目)
 * @param {*} params 
 */
export function queryContracts(params) {
    return request({
        url : '/ppmexampleKjlCght/queryContracts',
        method : 'get',
        params
    })
}

/**
 * 查询合同下拉框(人力)
 * @param {*} params 
 */
export function queryOrInsertPpmRlwbhtInfo(params) {
    return request({
        url : '/ppmRlwbhtInfo/queryOrInsertPpmRlwbhtInfo',
        method : 'get',
        params
    })
}

/**
 * 根据流程外包商编号查询单价
 * @param {*} params 
 */
export function queryList(params) {
    return request({
        url : '/ppmOutsourcingInfoPrice/queryList',
        method : 'get',
        params
    })
}
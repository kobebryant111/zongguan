/**数据中心门禁权限申请接口 */
import request from '@/utils/request'

// 管理员评估节点
export function adminAssessment(data) {
    return request({
        url: '/ppmAccessControlApply/adminAssessment',
        method: 'post',
        data
    })
}

// 管理员实施节点
export function adminImplementation(data) {
    return request({
        url: '/ppmAccessControlApply/adminImplementation',
        method: 'post',
        data
    })
}

// 数据中心总经理审核
export function daCenterManageRunFlowInfo(data) {
    return request({
        url: '/ppmAccessControlApply/daCenterManageRunFlowInfo',
        method: 'post',
        data
    })
}

// 删除
export function deleteData(params) {
    return request({
        url: '/ppmAccessControlApply/delete',
        method: 'delete',
        params
    });
}

// 分管总经理审核节点
export function fgManageRunFlowInfo(data) {
    return request({
        url: '/ppmAccessControlApply/fgManageRunFlowInfo',
        method: 'post',
        data
    })
}

// 提交流转流程
export function flowStartAndRun(data) {
    return request({
        url: '/ppmAccessControlApply/flowStartAndRun',
        method: 'post',
        data
    })
}

// 通过人员选择人员 id 获取申请人信息
export function getAccessControlInfoByUserId(data) {
    return request({
        url: '/ppmAccessControlApply/getAccessControlInfoByUserId',
        method: 'post',
        data
    })
}

// 数据中心门禁申请模板导入
export function importExceltAccessControl(data) {
    return request({
        url: '/ppmAccessControlApply/importExceltAccessControl',
        method: 'post',
        data
    })
}

// 二级部经理审批接口
export function managerRunFlowInfo(data) {
    return request({
        url: '/ppmAccessControlApply/managerRunFlowInfo',
        method: 'post',
        data
    })
}

// 查询详情
export function queryAllInfoDetail(params) {
    return request({
        url: '/ppmAccessControlApply/queryAllInfoDetail',
        method: 'get',
        params
    })
}

// 分页查询
export function queryPagerForSearch(params) {
    return request({
        url: '/ppmAccessControlApply/queryPager',
        method: 'post',
        params
    })
}

// 数据中心门禁申请保存草稿
export function saveDraft(data) {
    return request({
        url: '/ppmAccessControlApply/saveDraft',
        method: 'post',
        data
    })
}

//更新
export function update(data) {
    return request({
        url: '/ppmAccessControlApply/update',
        method: 'post',
        data
    })
}

//获取用户树
export function queryEmployees(params) {
    return request({
        url: '/commEmployee/queryEmployeeTreeByUserType',
        method: 'get',
        params
    })
}

//权限类别分页查询
export function queryPagerForPermissionsType(data) {
    return request({
        url: '/ppmAccessapplyconfig/queryPager',
        method: 'post',
        data
    })
}

// 数据中心门禁申请信息导出
export function exportAccessControlApplyInfo(data) {
    return request({
        url: '/ppmAccessControlApply/exportAccessControlApplyInfo',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

// 流程配置查询详情
export function queryDetailForFlowSetting(params) {
    return request({
        url: '/ppmAccessapplyconfig/queryDetail',
        method: 'get',
        params
    })
}

//流程配置分页查询
export function queryPagerForFlowSetting(data) {
    return request({
        url: '/ppmAccessapplyconfig/queryPager',
        method: 'post',
        data
    })
}

// 流程配置删除
export function deleteForFlowSetting(data) {
    return request({
        url: '/ppmAccessapplyconfig/delete',
        method: 'delete',
        data
    });
}

//流程配置更新
export function updateForFlowSetting(data) {
    return request({
        url: '/ppmAccessapplyconfig/update',
        method: 'post',
        data
    })
}

//流程配置新增
export function addForFlowSetting(data) {
    return request({
        url: '/ppmAccessapplyconfig/add',
        method: 'post',
        data
    })
}

/**
 * 分管总经理
 */

 
// 查询部门详情
export function queryDetailForDept(params) {
    return request({
        url: '/commDepartment/queryDetail',
        method: 'get',
        params
    })
}

 
// 查询总经理室
export function queryEmpManagerTree(params) {
    return request({
        url: '/commEmployee/queryEmpManagerTree',
        method: 'get',
        params
    })
}

//分页查询
export function queryPagerForEmployee(data) {
    return request({
        url: '/commEmployee/queryPager',
        method: 'post',
        data
    })
}

// 数据中心门禁申请信息修改
export function modifyAccessControlApply(data) {
    return request({
        url: '/ppmAccessControlApply/modifyAccessControlApply',
        method: 'post',
        data
    })
}






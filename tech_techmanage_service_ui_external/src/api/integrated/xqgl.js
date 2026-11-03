/**
 * 需求管理
 */
import request from '@/utils/request'
/**
 * 根据当前用户权限查询人力需求流程(需求管理列表)
 * @param {*} params 
 */
export function queryPagerList(params) {
    return request({
        url : '/ppmManpowerdemandApply/queryPagerList',
        method : 'get',
        params
    })
}

/**
 * 流转 -- 发起申请
 * @param {*} data 
 */
export function liuzhuan(data) {
    return request({
        url : '/ppmManpowerdemandApply/liuzhuan',
        method : 'post',
        data:data
    });
}
/**
 * 保存
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : '/ppmManpowerdemandApply/save',
        method : 'post',
        data
    })
}

/**
 * 删除业务
 * @param {*} data 
 */
export function delRequireStart(data) {
    return request({
        url : '/ppmManpowerdemandApply/deletePpmManpowerdemandApplyByInstanceid',
        method: 'delete',
        params: data
    })
}


/**
 * 需求审批详细信息(根据流程Id查询数据)
 * @param {*} params 
 */
export function queryDetail(params) {
    return request({
        url : '/ppmManpowerdemandApply/queryDetails',
        method : 'get',
        params
    })
}

/**
 * 根据主键删除列表数据
 * @param {*} params 
 */
export function deletePpmManpower(params) {
    return request({
        url : '/ppmManpowerdemandApply/deletePpmManpowerdemandApply',
        method : 'delete',
        params
    });
}

/**
 * 流转 -- 二级部审批
 * @param {*} data 
 */
export function twoManagerAudit(data) {
    return request({
        url : '/ppmManpowerdemandApply/twoManagerAudit',
        method : 'post',
        data:data
    });
}

/**
 * 流转 -- 综合部确认
 * @param {*} data 
 */
export function confirm(data) {
    return request({
        url : '/ppmManpowerdemandApply/confirm',
        method : 'post',
        data:data
    });
}

/**
 * 删除 申请 
 * @param {*} data 
 */
export function doDel(data) {
    return request({
        url : '/ppmManpowerdemandApply/delete',
        method : 'delete',
        params : data
    })
}

/**
 * 查询应聘者信息
 * @param {*} params 
 */
export function getApplicantsLisr(params) {
    return request({
        url : '/ppmIntervieweeInfo/getApplicantsLisr',
        method : 'get',
        params
    })
}

/**
 * 面试进度
 * @param {*} params 
 */
export function getInterviewProgress(params) {
    return request({
        url : `/ppmIntervieweeInfo/getInterviewProgress/${params}`,
        method : 'get',
        
    })
}

/**
 * 人力需求excel导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        responseType: 'blob',
        url: '/ppmManpowerdemandApply/export',
        method: 'post',
        data
    })
};



/**
 * 分行应用项目变更
 */
import request from '@/utils/request';

/**
 * 页面初始加载数据
 */
export function getProjectChangeInfo() {
    return request({
        url : '/ppmFhyyxtbgApply/getProjectChangeInfo',
        method : 'get',
    });
}
/**
 * 获取分行IT支持部或者软件研发部
 */
export function getFhITManager(params) {
    return request({
        url : '/commDepartment/getFhITManager',
        method : 'get',
        params
    });
}

/**
 * 获取分行总经理
 */
export function getFhYybManager(params) {
    return request({
        url : '/commDepartment/getFhYybManager',
        method : 'get',
        params
    });
}
/**
 * 获取设备分页
 */
export function querySheBeiPager(data) {
    return request({
        url : '/ppmPwdShebei/queryPager',
        method : 'post',
        data
    });
}
/**
 * 删除密管设备
 */
export function deleteShebei(params) {
    return request({
        url : '/ppmPwdShebei/deleteShebei',
        method : 'delete',
        params
    });
}
/**
 * 设备列表 - 主办人节点
 */
export function getPwdList(params) {
    return request({
        url : '/ppmPwdShebei/getPwdList',
        method : 'get',
        params
    });
}
/**
 * 生成运维工单
 */
export function scywsjgd(data) {
    return request({
        url : '/ppmFhyyxtbgApply/scywsjgd',
        method : 'post',
        data
    });
}

/**
 * 导出
 */
export function exportExcel(data) {
    return request({
        url : '/ppmPwdShebei/exportExcel',
        method : 'post',
        data,
        responseType: 'blob'
    });
}
/**
 * 获取系统列表
 */
export function querySysList(params) {
    return request({
        url : '/aapSystemProperty/queryList',
        method : 'get',
        params
    });
}

/**
 * 项目上线流程
 */
export function queryOnlinePager(data) {
    return request({
        url : '/ppmProjectNewonline/queryPager',
        method : 'post',
        data
    });
}
/**
 * 生产缺陷
 */
export function getProBus(params) {
    return request({
        url : '/ppmFhyyxtbgApply/getProBus',
        method : 'get',
        params
    });
}
/**
 * 获取通用密码密钥
 */
export function getGeneralPassworkKey(params) {
    return request({
        url : '/ppmFhyyxtbgApply/getGeneralPassworkKey',
        method : 'get',
        params
    });
}
/**
 * 获取系统负责人
 */
export function getSystemHead(params) {
    return request({
        url : '/aapSystemResponsibility/getSystemHead',
        method : 'get',
        params
    });
}
/**
 * 根据上线编号查询 系统编号
 */
export function querySysNoForOnlineno(params) {
    return request({
        url : '/ppmOnlineOrgarea/querySysNoForOnlineno',
        method : 'get',
        params
    });
}

/**
 * 文件上传
 * @param {*} data 
 */
export function preUpload(data) {
    return request({
        url : '/ppmFhyyxtbgApply/upload',
        method :'post',
        data
    })
}
/**
 * 是否白天
 * 1：白天，2：晚上
 */
export function isDayTime(params) {
    return request({
        url : '/ppmFhyyxtbgApply/getIsDayTime',
        method : 'get',
        params
    });
}
/**
 * 分行应用变更查看详情
 * @param {*} params 
 */
export function getFhChangeDateil(params) {
    return request({
        url : '/ppmFhyyxtbgApply/queryDetail',
        method : 'get',
        params
    });
}
/**
 * 删除文档
 * @param {*} params 
 */
export function delDocument(params) {
    return request({
        url : '/ppmDocument/deletePojo',
        method : 'delete',
        params
    });
}
/**
 * 获取密管密码
 * 判断是否可以直接获取密码
 * @param {*} params 
 */
export function queryPasswordIs(params) {
    return request({
        url : '/ppmFhyyxtbgApply/queryPasswordIs',
        method : 'get',
        params
    });
}
/**
 * 流程密码获取模式
 * （1：可直接获取密码，2：审批模式但未发起申请3：已发起申请但未开始，4：已发起申请且已开始）
 * @param {*} params 
 */
export function queryPwdModel(params) {
    return request({
        url : '/ppmFhyyxtbgApply/queryPwdModel',
        method : 'get',
        params
    });
}
/**
 * 获取密管密码
 * 没发起通用密码获取的前提下，获取密码并保存加密后的密码
 * @param {*} data 
 */
export function querySavePasswd(data) {
    return request({
        url : '/ppmFhyyxtbgApply/querySavePasswd',
        method : 'post',
        data
    });
}
/**
 * 是否隐藏按钮
 * @param {*} data 
 */
export function ifHiddenButton(params) {
    return request({
        url : '/ppmFhyyxtbgApply/ifHiddenButton',
        method : 'get',
        params
    });
}
/**
 * 保存草稿
 */
export function draftBtn(data) {
    return request({
        url : '/ppmFhyyxtbgApply/save',
        method : 'post',
        data
    });
}

/**
 * 流程发起
 */
export function liuzhuan(data) {
    return request({
        url : '/ppmFhyyxtbgApply/liuzhuan',
        method : 'post',
        data
    });
}

/**
 * 分行研发部/IT支持部经理审批
 */
export function banchDeve(data) {
    return request({
        url : '/ppmFhyyxtbgApply/banchDeve',
        method : 'post',
        data
    });
}

/**
 * 分行科技部/运营部总经理审批
 */
export function banchTech(data) {
    return request({
        url : '/ppmFhyyxtbgApply/banchTech',
        method : 'post',
        data
    });
}
/**
 * 主办人员办理
 */
export function hostHandle(data) {
    return request({
        url : '/ppmFhyyxtbgApply/hostHandle',
        method : 'post',
        data
    });
}
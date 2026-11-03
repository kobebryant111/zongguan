/**
 * 公共流程 js
 */
import request from '@/utils/request'
/**
 * 获取流程列表数据 - 
 * 我的待办，我的已办，我的归档
 * @param {*} params 
 */
export function getFlowData(url,params) {
    return request({
        url : url,
        method : 'get',
        params
    })
};

/**
 * 获取流程列表数据 - 
 * 我的待办
 * @param {*} params 
 */
export function getDb(params) {
    return request({
        url : '/workflow/getDb',
        method : 'get',
        params
    })
};

/**
 * 获取流程列表数据 - 
 * 我的已办
 * @param {*} params 
 */
export function getYb(params) {
    return request({
        url : '/workflow/getYb',
        method : 'get',
        params
    })
};

/**
 * 获取流程日志信息 - 
 * @param {*} data 
 */
export function getLogDetail(data) {
    return request({
        url : '/ppmFlowLog/queryPager',
        method : 'post',
        data
    })
};

/**
 * 获取流程列表数据 - 
 * 我的归档
 * @param {*} data 
 */
export function getFlowGDData(url,data) {
    return request({
        url : url,
        method : 'get',
        data
    })
};

/**
 * 获取流程列表数据 - 
 * 我的归档
 * @param {*} data 
 */
export function getFlowGDDataNew(data) {
    return request({
        url : '/workflow/getGdNew',
        method : 'post',
        data
    })
};

/**
 * 流程日志列表
 * @param {} params 
 */
export function getFlowLog(params) {
    return request({
        url : '/ppmFlowLog/queryList',
        method : 'get',
        params
    })
};

/**
 * 获取授权列表
 * @param {*} params 
 */
export function getAuthList(params) {
    return request({
        url :'/commonFlowApi/getAuthList',
        method : 'get',
        params
    })
}
/**
 * 执行授权
 * @param {*} data 
 */
export function doAuth(data) {
    return request({
        url : '/workflow/doAuth',
        method : 'post',
        data
    })
}
/**
 * 删除流程
 * @param {*} data 
 */
export function doDel(data) {
    return request({
        url : '/workflow/terminateProcess',
        method : 'post',
        data
    })
}
/**
 * 撤回流程
 * @param {*} data 
 */
export function drawback(data) {
    return request({
        url : '/workflow/reBackTask',
        method : 'post',
        data
    })
}

/**
 * 获取所有流程类型
 */
export function getFlowTypeAll() {
    return request({
        url : '/workflow/findClassifyList',
        method : 'get'
    })
}

/**
 * 根据用户名称、拼音、工号 查询员工信息
 */
export function getEmployUser(params) {
    return request({
        url : `/commEmployee/queryEmployUser/${params}`,
        method : 'get'
    })
}

/**
 * 获取所有外包商
 */
export function getListAll() {
    return request({
        url : '/ppmOutsourcingInfo/getListAll',
        method : 'get'
    })
}

/**
 * 预览
 */
export function preview(params) {
    return request({
        url : `/ppmDocument/preview?documentno=${params}`,
        method : 'get',
        params
    })
}

/**
 * 获取待办工作项详情-单条
 */
export function queryWorkItem(params) {
    return request({
        url : '/workflow/getTasksByInstanceId',
        method : 'get',
        params
    })
}

/**
 * 获取下载地址
 */
export function getDownloadUrl(params) {
    return request({
        url : '/ppmDocument/getDownloadUrl',
        method : 'get',
        params
    })
}

/**
 * 查询部门详情
 */
export function getDepartmentDetail(params) {
    return request({
        url : '/commDepartment/queryDetail',
        method : 'get',
        params
    })
}

/**
 * 查询项目详情
 */
export function getProjectDetail(params) {
    return request({
        url : '/ppmProjectBuildSoft/queryDetail',
        method : 'get',
        params
    })
}
/**
 * 查询归档详情
 * @param {*} params 
 */
export function queryGdDetail(params) {
    return request({
        url : '/commonFlowApi/queryGdDetail',
        method : 'get',
        params
    })
}

/**
 * 根据流程id 查询历史流程日志
 * @param {*} params 
 */
export function getGdLogs(params) {
    return request({
        url : '/ppmNewflowHistorylog/queryList',
        method : 'get',
        params
    })
}
/**
 * 查询流程状态
 * @param {*} params 
 */
export function getFlowStatus(params) {
    return request({
        url : '/commonFlowApi/getProcessInstState',
        method : 'get',
        params
    }) 
}
/**
 * 查询流程info
 * @param {*} params 
 */
export function getFlowInfo(params) {
    return request({
        url : '/commonFlowApi/getProcessInstInfo',
        method : 'get',
        params
    }) 
}
/**
* 根据奋进号id查询流程id
* @param {*} params 
*/
export function queryInstanceidByIssueId(params) {
   return request({
       url : '/ppmDemandnewinfo/queryByIssueId',
       method : 'get',
        params
    }) 
}

/**
 * 查询流程info
 * @param {*} params 
 */
export function queryInstanceidByOnlinenum(params) {
    return request({
        url : '/ppmProjectOnlinefjh/queryInstanceidByOnlinenum',
        method : 'get',
        params
    }) 
}

/**
 * 删除oa通知待办
 * @param {*} params 
 */
export function delOaNotice(data) {
    return request({
        url : '/commonFlowApi/delOaNotice',
        method : 'post',
        data
    }) 
}
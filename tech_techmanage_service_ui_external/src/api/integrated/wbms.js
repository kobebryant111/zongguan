/**
 * 外包面试 
 */
import request from '@/utils/request'
/**
 * 提交 申请
 * @param {*} data 
 */
export function doApply(data) {
    return request({
        url : '/ppmInterviewApply/apply',
        method : 'post',
        data
    })
}

/**
 * 删除 申请 
 * @param {*} data 
 */
export function doDel(data) {
    return request({
        url : '/ppmInterviewApply/delete',
        method : 'delete',
        params : data
    })
}

/**
 * 面试打分详情
 * @param {*} params 
 */
export function getInterviewScore(params) {
    return request({
        url : '/ppmInterviewApply/interviewScoreDateil',
        method : 'get',
        params
    })
}

/**
 * 查询面试信息
 * @param {*} params 
 */
export function queryInterDataList(params) {
    return request({
        url : '/ppmOutsourcingAccessapply/queryInterDataList',
        method : 'get',
        params
    })
}

/**
 * 查询面试信息
 * @param {*} params 
 */
export function delData(params) {
    return request({
        url : '/ppmOutsourcingAccessapply/delData',
        method : 'get',
        params
    })
}



/**
 * 面试流转
 * @param {*} data 
 */
export function doInterviewScore(data) {
    return request({
        url : '/ppmInterviewApply/interviewScore',
        method : 'post',
        data
    })
}
/**
 * 结果录入
 * @param {*} data 
 */
export function doResultEntry(data) {
    return request({
        url : '/ppmInterviewApply/resultEntry',
        method : 'post',
        data
    })
}
/**
 * 单价确认
 * @param {*} data 
 */
export function doPriceConfirm(data) {
    return request({
        url : '/ppmInterviewApply/priceConfirm',
        method : 'post',
        data
    })
}
/**
 * 综合部确认
 * @param {*} data 
 */
export function doZhbCofirm(data) {
    return request({
        url : '/ppmInterviewApply/zhbConfirm',
        method : 'post',
        data
    })
}
/**
 * 分管总经理
 * @param {*} data 
 */
export function doHousing(data) {
    return request({
        url : '/ppmInterviewApply/doHousing',
        method : 'post',
        data
    })
}
/**
 * 获取外包商单价列表
 * @param {*} params 
 */
export function getOutsourcPrice(params) {
    return request({
        url : '/ppmOutsourcingInfoPrice/getOutsourcPrices',
        method : 'get',
        params 
    })
}

/**
 * excel上传
 * @param {*} data 
 */
export function uploadExcel(data) {
    return request({
        url : '/ppmInterviewApply/uploadExcel',
        method : 'post',
        data
    })
}

/**
 * 面试人力需求查询
 * @param {*} params 
 */
export function queryManpowerdemands(params) {
    return request({
        url : '/ppmManpowerdemandApply/queryManpowerdemands',
        method : 'get',
        params 
    })
}


/**
 * 面试存草稿
 * @param {*} data 
 */
export function saveDraft(data) {
    return request({
        url : '/ppmInterviewApply/draft',
        method : 'post',
        data
    })
}


/**
 * 文档下载
 * @param {*} params 
 */
export function download(params) {
    return request({
        responseType: 'blob',
        url : `ppmInterviewApply/download/${params}`,
        method : 'get',
        params 
    })
}

/**
 * 面试列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmInterviewApply/queryPager',
        method : 'post',
        data
    })
}

/**
 * 面试excel导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        responseType: 'blob',
        url: '/ppmInterviewApply/exportExcel',
        method: 'post',
        data
    })
};

/**
 * 查询人力需求详情
 * @param {*} params 
 */
export function rlxqQueryDetail(params) {
    return request({
        url : '/ppmManpowerdemandApplyInfo/queryManpowerdemandsById',
        method : 'get',
        params 
    })
}

/**
 * 根据主键删除列表数据
 * @param {*} params 
 */
export function del(params) {
    return request({
        url : '/ppmIntervieweeInfo/del',
        method : 'delete',
        params
    });
}

/**
 * 单价表下载
 * @param {*} data 
 */
export function priceDownload(data) {
    return request({
        url : '/ppmOutsourcingInfoPrice/priceDownload',
        method : 'post',
        data,
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        responseType: 'blob'
    });
}


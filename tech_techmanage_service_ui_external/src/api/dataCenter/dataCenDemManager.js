/**数据中心需求管理接口 */
import request from '@/utils/request'

// 提交流转流程
export function flowStartAndRun(data) {
    return request({
        url: '/ppmDacenterDemand/flowStartAndRun',
        method: 'post',
        data
    })
}

// 保存草稿
export function saveDraft(data) {
    return request({
        url: '/ppmDacenterDemand/saveDraft',
        method: 'post',
        data
    })
}

// 数据中心需求管理编辑修改（运维部收口编辑保存）
export function modifyDacenterDemand(data) {
    return request({
        url: '/ppmDacenterDemand/modifyDacenterDemand',
        method: 'post',
        data
    })
}

// 文件上传
export function upload(data) {
    return request({
        url: '/ppmDacenterDemand/upload',
        method: 'post',
        data
    })
}

//获取投产日
export function getProductionDay(data) {
    return request({
        url: '/ppmProductionDay/queryPager',
        method: 'post',
        data
    })
}

//文件下载
export function download(params) {
    return request({
        url: `/ppmDocument/download/${params}`,
        method: 'get',
        responseType: 'blob'
    })
}

/**
 * 删除文档
 * @param {*} params 
 */
export function delDocument(params) {
    return request({
        url: '/ppmDocument/deletePojo',
        method: 'delete',
        params
    });
}

/**
 * 查询详情
 * @param {*} params 
 */
export function queryDetail(params) {
    return request({
        url: '/ppmDacenterDemand/queryDetail',
        method: 'post',
        params
    })
}

/**需求分页查询 */
export function queryDemands(params) {
    return request({
        url: '/ppmDacenterDemand/queryPager',
        method: 'post',
        params
    })
}

//流程审批接口共用
export function runFlowInfo(data) {
    return request({
        url: '/ppmDacenterDemand/runFlowInfo',
        method: 'post',
        data
    })
}

// 需求管理线上化数据导出
export function exportDacenterDemandDatas(data) {
    return request({
        url: '/ppmDacenterDemand/exportDacenterDemandDatas',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

// 消息提示(多节点)
export function messageNotifyMore(messageVal) {
    if (messageVal.length > 3) {
        messageVal[messageVal.length - 1] =
            messageVal[messageVal.length - 1].split(")")[0] + ")";
    }
    let messageSec = messageVal.slice(2).join("，");
    this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
            "<div><div>" +
            messageVal[1] +
            "</div><div>" +
            messageSec +
            "</div></div>",
        duration: 0
    });
}





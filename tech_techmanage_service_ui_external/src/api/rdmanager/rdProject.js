/**
 * 部门
 */
import request from '@/utils/request'

/**
 * 分页
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/rdProject/queryPager',
        method : 'post',
        data
    })
}
/**
 * 导出
 * @param {*} data 
 */
export function queryPagerInfo(data) {
    return request({
        url : '/rdProject/queryPagerExcel',
        method : 'post',
        data
    })
}
/**
 * 修改基本信息
 * @param {*} data 
 */
export function saveBaseInfo(data) {
    return request({
        url : '/rdProject/saveBaseInfo',
        method : 'post',
        data
    })
}

/**
 * 修改内部信息
 * @param {*} data 
 */
export function updateInnerInfo(data) {
    return request({
        url : '/rdProject/updateInnerInfo',
        method : 'post',
        data
    })
}

/**
 * 修改技术信息
 * @param {*} data 
 */
export function updateInnova(data) {
    return request({
        url : '/rdProject/updateInnova',
        method : 'post',
        data
    })
}

/**
 * 详情
 * @param {*} params 
 */
export function getDetails(params) {
    return request({
        url : '/rdProject/getRdProjectDetails',
        method : 'get',
        params
    })
}

/**
 * 删除
 * @param {*} params 
 */
export function doDelete(params) {
    return request({
        url : '/rdProject/delete',
        method : 'get',
        params
    })
}

/**
 * 上传文件
 * @param {*} data 
 */
export function uploadFile(data) {
    return request({
        url : '/rdProject/upload',
        method : 'post',
        data
    })
}
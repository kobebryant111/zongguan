/**
 * 项目文件
 */
import request from '@/utils/request'

/**
 * 查询文件
 * @param {*} params 
 */
export function queryRdProjectFile(params) {
    return request({
        url : '/rdProjectFile/queryByRdProjectId',
        method : 'get',
        params
    })
}
/**
 * 下载文件
 */
export function downloadRdProjectFile(params) {
    return request({
        url : '/rdProjectFile/download',
        method : 'post',
        params
    })
}

/**
 * 保存文件
 * @param {*} data 
 */
export function updateFileName(data) {
    return request({
        url : '/rdProjectFile/update',
        method : 'post',
        data
    })
}


/**
 * 删除
 * @param {*} params 
 */
export function deleteRdProjectFile(params) {
    return request({
        url : '/rdProjectFile/delete',
        method : 'get',
        params
    })
}

/**
 * 上传文件
 * @param {*} data 
 */
export function upload(data) {
    return request({
        url : '/rdProjectFile/upload',
        method : 'post',
        data
    })
}


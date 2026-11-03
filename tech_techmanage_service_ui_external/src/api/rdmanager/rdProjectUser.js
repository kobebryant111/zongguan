/**
 * 项目人员
 */
import request from '@/utils/request'

/**
 * 分页
 * @param {*} params 
 */
export function queryRdProjectUser(params) {
    return request({
        url : '/rdProjectUser/queryByRdProjectId',
        method : 'get',
        params
    })
}
/**
 * 项目内部资源查询
 */
export function queryRdProjectUserNew(params) {
    return request({
        url : '/rdProject/queryByRdProjectCode',
        method : 'get',
        params
    })
}
/**
 * 项目人员
 * @param {*} data 
 */
export function saveRdProjectUser(data) {
    return request({
        url : '/rdProjectUser/save',
        method : 'post',
        data
    })
}


/**
 * 删除
 * @param {*} params 
 */
export function deleteRdProjectUser(params) {
    return request({
        url : '/rdProjectUser/delete',
        method : 'get',
        params
    })
}


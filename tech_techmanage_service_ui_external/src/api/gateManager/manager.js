/**
 * 闸机管理 
 */
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmZjManager/queryPager',
        method : 'post',
        data
    })
}
      
/**
 * 查询闸机权限申请详情
 * @param {*} params 
 */
export function queryDetail(params) {
    return request({
        url : '/ppmZjManager/queryDetail',
        method : 'get',
        params 
    })
}

/**
 * 门禁点反控
 * @param {*} data 
 */
export function doControl(data) {
    return request({
        url : '/ppmZjManager/doControl',
        method : 'post',
        data 
    })
}
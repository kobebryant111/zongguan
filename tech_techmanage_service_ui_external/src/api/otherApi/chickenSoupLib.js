/**
 * 鸡汤库 api
 */
import request from '@/utils/request'
/**
 * 获取分页数据
 * @param {*} data 
 */
export function getPagerData(data) {
    return request({
        url : '/ppmSoulInfo/queryPager',
        method : 'post',
        data
    })
};
/**
 * 添加或者 修改
 * @param {} data 
 */
export function doUpdate(data) {
    return request({
        url : '/ppmSoulInfo/update',
        method : 'post',
        data
    })
};
/**
 * 获取详情
 * @param {} data 
 */
export function getDateil(params) {
    return request({
        url : '/ppmSoulInfo/queryDetail',
        method : 'get',
        params
    })
};
/**
 * 删除
 * @param {*} data 
 */
export function doDelete(data) {
    return request({
        url : '/ppmSoulInfo/delete',
        method : 'delete',
        params:data
    })
};
/**
 * 导出
 * @param {*} data 
 */
export function doExport(data) {
    return request({
        url : '/ppmSoulInfo/export',
        method : 'post',
        responseType : 'blob',
        data
    });
};
/**
 * 批量删除
 * @param {*} params 
 */
export function doDeletes(data) {
    return request({
        url :'/ppmSoulInfo/deletes',
        method :'post',
        data
    })
}
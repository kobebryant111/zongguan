/**
 * 题库
 */
import request from '@/utils/request'
/**
 * 获取分页数据
 * @param {*} data 
 */
export function getPager(data) {
    return request({
        url : '/ppmQuestionList/queryPager',
        method :'post',
        data
    })
};
/**
 * 更新 | 添加
 * @param {*} data 
 */
export function doUpdate(data) {
    return request({
        url : '/ppmQuestionList/update',
        method : 'post',
        data
    });
};
/**
 * 详情
 * @param {*} params 
 */
export function getDateil(params) {
    return request({
        url : '/ppmQuestionList/queryDetail',
        method : 'get',
        params: params
    })
};
/**
 * 删除主表数据
 * @param {*} params 
 */
export function doDelete(params) {
    return request({
        url : '/ppmQuestionList/delete',
        method : 'delete',
        params: params
    })
};
/**
 * 删除从表数据 - 题目选项表
 * @param {*} params 
 */
export function doOptionDelete(params) {
    return request({
        url : '/ppmQuestionOption/delete',
        method : 'delete',
        params: params
    })
};
/**
 * excel导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        responseType:'blob',
        url : '/ppmQuestionList/export',
        method : 'post',
        data
    })
};
/**
 * 批量删除
 */
export function batchDeletes(data) {
    return request({
        url : '/ppmQuestionList/deletes',
        method : 'post',
        data
    })
}

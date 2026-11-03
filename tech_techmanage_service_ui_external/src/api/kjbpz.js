/**
 * 分行科技部配置 api
 */
import request from '@/utils/request'
import Axios from 'axios';
/**
 * 获取分页数据
 * @param {*} data 
 */
export function getPagerData(data) {
    return request({
        url : '/ppmFhkjDeptRecord/queryPager',
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
        url : '/ppmFhkjDeptRecord/update',
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
        url : '/ppmFhkjDeptRecord/queryDetail',
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
        url : '/ppmFhkjDeptRecord/delete',
        method : 'delete',
        params:data
    })
};


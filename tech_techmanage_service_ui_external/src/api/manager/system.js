/**
 * 系统管理 
 */
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmSystemInfo/queryPager',
        method : 'post',
        data
    })
}

/**
 * 查询详情
 * @param {*} params 
 */
export function queryDetail(params) {
    return request({
        url : '/ppmSystemInfo/queryDetail',
        method : 'get',
        params 
    })
}

/**
 * 保存
 * @param {*} data 
 */
export function save(data) {
    return request({ 
        url: '/ppmSystemInfo',
        method: 'post',
        data
    })
};


/**
 * 根据主键删除列表数据
 * @param {*} params 
 */
export function del(params) {
    return request({
        url : '/ppmSystemInfo',
        method : 'delete',
        params
    });
}

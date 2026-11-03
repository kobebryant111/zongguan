/**
 * 项目管理 
 */
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmProjectInfo/queryPager',
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
        url : '/ppmProjectInfo/queryDetail',
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
        url: '/ppmProjectInfo',
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
        url : '/ppmProjectInfo',
        method : 'delete',
        params
    });
}

/*
* 采购系统 项目信息查询
*/
export function queryProjectList(params) {
    return request({
        url: '/suppliersSync/queryProjectList',
        method: 'get'
    })
}

/*
* 采购系统 项目信息查询 (仅修改)
*/
export function queryProjectListUpdate(params) {
    return request({
        url: '/suppliersSync/queryProjectListUpdate',
        method: 'get',
        params
    })
}
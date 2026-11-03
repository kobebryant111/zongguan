/**
 * 行员用户 js
 */
import request from '@/utils/request'

/**
 * 获取组织树
 * @param {*} params 
 */
export function getDeptTree(params) {
    return request({
        url : '/commDepartment/queryDeptTree',
        method : 'get',
        params
    });
};
/**
 * 行员花名册 - 分页
 * @param {*} data 
 */
export function getCrewPager(data) {
    return request({
        url : '/commEmployee/queryCrewPage',
        method : 'post',
        data
    })
}
/**
 * 行员列表- 组件
 * @param {*} params 
 */
export function getEmployeeComponent(params) {
    return request({
        url : '/commEmployee/getEmployList',
        method : 'get',
        params
    })
}

/**
 * 处理人- 组件
 * @param {*} params 
 */
export function getHandlerTreeComponent(params) {
    return request({
        url : '/commEmployee/queryEmployeeTree',
        method : 'get',
        params
    })
}
/**
 * 根据部门编号查询行员
 */
export function getdeptIdForUsers(id) {
    return request({
        url : '/commEmployee/queryEmployeeTree/' + id,
        method : 'get',
    })
}

/**
 * 行员分页
 */
export function queryPager(data) {
    return request({
        url : '/commEmployee/queryPager',
        method : 'post',
        data
    })
}


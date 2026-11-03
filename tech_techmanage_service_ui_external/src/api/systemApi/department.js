/**
 * 部门
 */
import request from '@/utils/request'

/**
 * 获取2级部门
 * @param {*} params 
 */
export function getDeptTwo(params) {
    return request({
        url : '/commDepartment/queryResourceDept',
        method : 'GET',
        params
    })
}
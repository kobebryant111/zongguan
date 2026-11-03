/**
 * 组织考核-部门指标
 */
import request from '@/utils/request'

/**
 * 部门提交
 * @param {*} data 
 */
export function commitDepTarget(data) {
    return request({
        url: '/ppmDepkpiDepmodelDetail/commitDepTarget',
        method: 'post',
        data
    })
}

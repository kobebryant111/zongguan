/**
 * 组织考核-模板分配
 */
import request from '@/utils/request'

/**
 * 提交
 * @param {*} data 
 */
 export function commitModelTarget(data) {
    return request({
        url: '/ppmDepkpiModelDetail/commitModelTarget',
        method: 'post',
        data
    })
}


/**
 *  考勤机配置
 */

import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryFaceDevicePager(data) {
    return request({
        url : '/kqFace/queryFaceDevicePager',
        method: 'post',
        data
    })
}

/**
 * 考勤机修改
 * @param {*} data
 *  */
export function updateFaceDevice(data) {
    return request({
        url: "/kqFace/updateFaceDevice",
        method: 'post',
        data
    })
}
/**
 *  考勤人脸配置
 */

import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/kqFace/queryPager',
        method : 'post',
        data
    })
}

/**
 * 文件上传
 * @param {*} data 
 */
export function uploadFile(data) {
    return request({
        url: '/ppmDocument/uploadExcel',
        method: 'post',
        data
    })
}

/**
 * 人脸考勤数据保存
 * @param {*} data 
 */
export function uploadFacePicture(data) {
    return request({
        url: '/kqFace/uploadFacePicture',
        method: 'post',
        data,
    })
}

/*
*   获取下发考勤机
* 
*/
export function queryFaceDevice(data) {
    return request({
        url: "/kqFace/queryFaceDevice",
        method: "post",
        data
    })
}


/*
*   确定下发考勤机
*/
export function handFaceDevice(data) {
    return request({
        url: "/kqFace/handFaceDevice",
        method: "post",
        data,
    })
}
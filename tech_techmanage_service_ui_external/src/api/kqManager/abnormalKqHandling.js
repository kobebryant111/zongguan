/* eslint-disable indent */
/**
 *  异常考勤处理
 */
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryRecordPager(data) {
    return request({
        url : '/ppmKqInfo/queryRecordPager',
        method : 'post',
        data
    })
}

/*
*   确认
*/ 
export function updateRecordList(data) {
    return request({
        url: "/ppmKqInfo/updateRecordList",
        method: "post",
        data
    })
}
/**
 * 回退
 */
export function backRecordList(data) {
    return request({
        url: '/ppmKqInfo/backRecordList',
        method: 'post',
        data
    })
}
export function cipherRecordList(data) {
    return request({
        url: '/ppmKqInfo/cipherRecordList',
        method: 'post',
        data
    })
}

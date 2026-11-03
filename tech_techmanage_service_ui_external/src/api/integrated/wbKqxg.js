// 外包考勤修改Api
import request from '@/utils/request'

/**
 * 流转
 * @param {*} data 
 */
export function liuzhuan(data) {
    return request({
        url : '/ppmAttendanceModify/liuzhuan',
        method : 'post',
        data
    })
}
/**
 * 保存
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : '/ppmAttendanceModify/save',
        method : 'post',
        data
    })
}
/**
 * 二级部主持经理审核
 * @param {*} data 
 */
export function hostManagerReview(data) {
    return request({
        url : '/ppmAttendanceModify/hostManagerReview',
        method : 'post',
        data
    })
}
/**
 * 分管总经理审批
 * @param {*} data 
 */
export function generalMnager(data) {
    return request({
        url : '/ppmAttendanceModify/generalMnager',
        method : 'post',
        data
    })
}
/**
 * 综合部确认
 * @param {*} data 
 */
export function generalDept(data) {
    return request({
        url : '/ppmAttendanceModify/generalDept',
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
        url : '/ppmAttendanceModify/queryDetail',
        method : 'get',
        params 
    })
}
/**
 * 导出
 */
export function exportExcel(data) {
    return request({
        url : '/ppmAttendanceModify/exportExcel',
        method : 'post',
        data ,
        responseType: 'blob'
    })
}
/**
 * 节假日管理 
 */
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmKqCalendar/queryPager',
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
        url : '/ppmKqCalendar/queryDetail',
        method : 'get',
        params 
    })
}

/**
 * 修改
 * @param {*} data 
 */
export function update(data) {
    return request({ 
        url: '/ppmKqCalendar/update',
        method: 'post',
        data
    })
};


/**
 * 查询权限
 * @param {*} params 
 */
export function queryRole(params) {
    return request({
        url : '/ppmKqCalendar/queryRole',
        method : 'post',
        params
    });
}

/**
 * 导入数据
 * @param {*} data 
 */
export function importExcel(data) {
    return request({
        url : '/ppmKqCalendar/importExcel',
        method : 'post',
        data
    })
}
/**
 * 审批
 * @param {*} data 
 */
export function approve(data) {
    return request({
        url : '/ppmKqCalendar/approve',
        method : 'post',
        data
    })
}

/**
 * 确定
 * @param {*} data 
 */
export function updateRecordHoliday(data) {
    return request({
        url : 'ppmKqInfo/updateRecordHoliday',
        method : 'post',
        data
    })
}

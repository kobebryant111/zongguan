/*
*   外包人员考勤
*/
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(params) {
    return request({
        url : '/kqRecordOther/queryPager',
        method : 'post',
        params
    })
}

/**
 * 考勤excel导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        responseType: 'blob',
        url: '/kqRecordOther/export',
        method: 'post',
        data
    })
};


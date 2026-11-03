/*
 * @Author: your name
 * @Date: 2022-11-23 09:03:43
 * @LastEditTime: 2022-11-23 09:04:44
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \tech_techmanage_service_ui\src\api\accountManager\accountManager.js
 */
/*
*   文件管理
*/
import request from '@/utils/request'
const api = "/outsourceLedger"
/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : api + '/queryPager',
        method : 'post',
        data
    })
}
/**
 *查询详情
 * 
 */
export function queryDetail(id) {
    return request({
        url : api + `/queryDetails/${id}`,
        method : 'get',
    })
}
/**
 *新增文件
 * @param {*} data 
 */
export function save(data) {
    return request({
        url : api + '/add',
        method : 'post',
        data
    })
}

/*
*   修改文件信息
*/
export function updateAccount(data) {
    return request({
        url: api + "/update",
        method: "put",
        data
    })
}
/*
*   删除文件信息
*/
export function delAccount(id) {
    return request({
        url: api + `/delete/${id}`,
        method: "delete",
    })
}
/*
*   查询名称是否相似
*/
export function count(data) {
    return request({
        url: api + `/count`,
        method: "post",
        data
    })
}
/**
 * 上传
 * @param {*} data 
 */
export function uploadExcel(data) {
    return request({
        url : api + '/uploadExcel',
        method :'post',
        data
    })
}

export function importExcel(data) {
    return request({
        url : api + '/importExcel',
        method : 'post',
        headers:{
            
            "Content-Type": 'application/json;charset=UTF-8',
        },
        responseType: "blob",
        data
    })
}

/**
 * 查询权限
 * @param {*} params 
 */
// export function queryRole(params) {
//     return request({
//         url : '/ppmKqCalendar/queryRole',
//         method : 'post',
//         params
//     });
// }

/**
 * 考勤excel导出
 * @param {*} data 
 */
// export function exportExcel(data) {
//     return request({
//         responseType: 'blob',
//         url: '/ppmKqInfo/exportInfo',
//         method: 'post',
//         data
//     })
// };




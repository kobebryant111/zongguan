/*
 * @Author: your name
 * @Date: 2023-07-21 09:03:43
 * @LastEditTime: 2022-11-23 09:04:44
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \tech_techmanage_service_ui\src\api\accountManager\accountManager.js
 */
/*
*   小组台账
*/
import request from '@/utils/request'
const api = "/developGroupLedger"
const groupApi = "/developGroupInfo"
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
 * @param {*} params 
 */
export function queryDetail(params) {
    return request({
        url : api + `/queryDetail`,
        method : 'GET',
        params
    })
}
/**
 *新增文件
 * @param {*} data 
 */
export function addLedger(data) {
    return request({
        url : api + '/add',
        method : 'post',
        data
    })
}

/*
*   修改文件信息
*/
export function updateLedger(data) {
    return request({
        url: api + "/update",
        method: "put",
        data
    })
}



/**
 * excel导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        responseType: 'blob',
        url: api + '/export',
        method: 'post',
        data
    })
};

/*
*   查询小组字典
*/
export function queryLedgerList(data) {
    return request({
        url : groupApi + '/queryList',
        method : 'post',
        data
    })
}

/**
 * 小组列表分页查询
 * @param {*} data 
 */
export function queryGroupInfoPager(data) {
    return request({
        url : groupApi + '/queryPager',
        method : 'post',
        data
    })
}

/**
 *新增小组信息
 * @param {*} data 
 */
export function addGroupInfo(data) {
    return request({
        url : groupApi + '/add',
        method : 'post',
        data
    })
}

/*
*   修改小组信息
*/
export function updateGroupInfo(data) {
    return request({
        url: groupApi + "/update",
        method: "put",
        data
    })
}

/*
*   修改小组信息
*/
export function deleteGroupInfo(data) {
    return request({
        url: groupApi + "/delete",
        method: "delete",
        data
    })
}

/**
 * excel导出
 * @param {*} data 
 */
export function exportGroupInfo(data) {
    return request({
        responseType: 'blob',
        url: groupApi + '/export',
        method: 'post',
        data
    })
};










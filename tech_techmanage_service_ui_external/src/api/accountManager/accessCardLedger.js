/*
 * @Author: your name
 * @Date: 2022-11-23 09:03:43
 * @LastEditTime: 2022-11-23 09:04:44
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \tech_techmanage_service_ui\src\api\accountManager\accountManager.js
 */
/*
*   非科技外包人员台账
*/
import request from '@/utils/request'
const api = "/accessCardLedger"
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

/*
*   修改文件信息
*/
export function updateLedgers(data) {
    return request({
        url: api + "/updates",
        method: "put",
        data
    })
}


/**
 * 查询当前用户登录信息
 */
export function queryUserDept(params) {
    return request({
        url : '/commDepartment/queryUserDept',
        method : 'get',
        params
    })
}

export function importInfo(data) {
    return request({
        url : api + '/importAccessCard',
        method : 'post',
        data,
    })
}







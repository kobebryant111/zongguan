/*
*   合同管理
*/
import request from '@/utils/request'
const api = "/vendorContract"
/**
 * 列表分页查询
 * @param {*} data 
 */
export function queryContractPager(data) {
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
export function queryListByMapSmall(data) {
    return request({
        url : api + `/queryListByMapSmall`,
        method : 'post',
        data
    })
}

/**
 *新增合同
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
*   修改合同信息
*/
export function updateContract(data) {
    return request({
        url: api + "/update",
        method: "post",
        data
    })
}
/*
*   删除合同信息
*/
export function delContract(id) {
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
// export function importErrorExcel(data) {
//     return request({
//         url : api + '/uploadExcel',
//         method : 'post',
//         headers:{
            
//             "Content-Type": 'application/json;charset=UTF-8',
//         },
//         responseType: "blob",
//         data
//     })
// }

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




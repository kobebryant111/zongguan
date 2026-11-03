/*
*   文件管理
*/
import request from '@/utils/request'
const api = "/attatchManagement"
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
export function updateFile(data) {
    return request({
        url: api + "/update",
        method: "put",
        data
    })
}
/*
*   删除文件信息
*/
export function delFile(id) {
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

export function queryLatestRecord() {
    return request({
        url : api + '/queryLatestRecord',
        method : 'get',
    });
}


/**
 * 流转 -- 二级部审批
 * @param {*} data 
 */
export function queryZBYA() {
    return request({
        url : api + '/queryZBYA',
        method : 'get',
    });
}

/**
 * 需求审批详细信息(根据流程Id查询数据)
 * @param {*} params 
 */
export function queryProcessDetail(data) {
    return request({
        url : '/attatchMUApply/queryListByMapBasic',
        method : 'post',
        data
    })
}
/**
 * 发起申请
 * @param {*} data 
 */
 export function approvalCirculate(data) {
    return request({
        url : '/attatchMUApply/approvalCirculate',
        method : 'post',
        data
    });
}
/**
 * 流转 -- 二级部审批
 * @param {*} data 
 */
export function twoManagerAudit(data) {
    return request({
        url : '/attatchMUApply/confirm',
        method : 'post',
        data
    });
}
/**
 * 撤回
 * @param {*} data 
 */
 export function reBackTask(data) {
    return request({
        url : '/attatchMUApply/reBackTask',
        method : 'post',
        data
    });
}
/*
*   撤回修改流程状态
*/
export function updateFileStatus(data) {
    return request({
        url: '/attatchMUApply' + "/update",
        method: "put",
        data
    })
}
/*
*   撤回修改流程状态
*/
export function deleteFlow(data) {
    return request({
        url: '/attatchMUApply' + "/deleteFlow",
        method: "delete",
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




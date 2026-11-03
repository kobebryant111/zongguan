/**数据中心后督管理接口 */
import request from '@/utils/request'

/**批量删除 */
export function batchDelete(data) {
    return request({
        url : '/ppmPostGovernor/batchDelete',
        method : 'post',
        data
    })
}

/**触发反馈流程*/
export function flowStartAndRun(data) {
    return request({
        url: '/ppmPostGovernor/flowStartAndRun',
        method: 'post',
        data
    })
}

/**后督管理人员审核审批接口 */
export function endRunFlowInfo(data) {
    return request({
        url: '/ppmPostGovernor/endRunFlowInfo',
        method: 'post',
        data
    })
}

/**后督管理流程模板导入 */
export function importExcelInfo(data) {
    return request({
        url: '/ppmPostGovernor/importExcelInfo',
        method: 'post',
        data
    })
}

/**二级部经理审批接口 */
export function managerRunFlowInfo(data) {
    return request({
        url: '/ppmPostGovernor/managerRunFlowInfo',
        method: 'post',
        data
    })
}

/** 查询详情*/
export function queryDetail(params) {
    return request({
        url: '/ppmPostGovernor/queryDetail',
        method: 'get',
        params
    })
}

/**后督反馈记录分页查询 */
export function queryPager(params) {
    return request({
        url: '/ppmPostGovernor/queryPager',
        method: 'post',
        params
    })
}

/** 后督录入记录分页查询 */
export function queryPagerInput(params) {
    return request({
        url: '/ppmPostGovernor/queryPagerInput',
        method: 'post',
        params
    })
}

/**关联人员审批接口 */
export function relationUserRunFlowInfo(data) {
    return request({
        url: '/ppmPostGovernor/relationUserRunFlowInfo',
        method: 'post',
        data
    })
}

/**新增 */
export function save(data) {
    return request({
        url: '/ppmPostGovernor/save',
        method: 'post',
        data
    })
}

/**更新 */
export function update(data) {
    return request({
        url: '/ppmPostGovernor/update',
        method: 'post',
        data
    })
}

/**后督信息录入导出 */
export function exportPostGovernorInputDatas(data) {
    return request({
        url: '/ppmPostGovernor/exportPostGovernorInputDatas',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

/**后督反馈记录导出 */
export function exportPostGovernorDatas(data) {
    return request({
        url: '/ppmPostGovernor/exportPostGovernorDatas',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

/** 查询流程编号*/
export function queryProject(params) {
    return request({
        url: '/ppmProjectFlow/queryDetail',
        method: 'get',
        params
    })
}

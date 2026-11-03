// 新员工部门分配
import request from '@/utils/request'
/**
 * 导入
 * @param {*} data 
 */
export function importExcel(data) {
    return request({
        url : '/ppmOnboardDistribution/importExcel',
        method : 'post',
        data
    })
}
/**
 * 导出
 * @param {*} data 
 */
export function exportExcel(data) {
    return request({
        url: '/ppmOnboardDistribution/exportExcel',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
/**
 * 分页查询
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/ppmOnboardDistribution/queryPager',
        method : 'post',
        data
    })
}
/**
 * 权限分页查询
 * @param {*} data 
 */
export function queryPagerNotAuth(data) {
    return request({
        url : '/ppmOnboardDistribution/queryPagerNotAuth',
        method : 'post',
        data
    })
}
/**
 * 更新
 * @param {*} data 
 */
export function update(data) {
    return request({
        url : '/ppmOnboardDistribution/update',
        method : 'post',
        data
    })
}
/**
 * 流程发起
 * @param {*} data 
 */
export function liuzhuan(data) {
    return request({
        url : '/ppmDistributionMaster/liuzhuan',
        method : 'post',
        data
    })
}
/**
 * 处理人审批
 * @param {*} data 
 */
export function processApproval(data) {
    return request({
        url : '/ppmDistributionMaster/processApproval',
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
        url : '/ppmDistributionMaster/queryDetail',
        method : 'get',
        params 
    })
}

/**
 * 查询查询中心集合
 * @param {*} params 
 */
export function queryList(params) {
    return request({
        url : '/ppmDaCenter/queryList',
        method : 'get',
        params 
    })
}

/**
 * 根据中心ID查询部门
 * @param {*} params 
 */
export function queryDeptList(params) {
    return request({
        url : '/ppmDaCenter/queryDeptList',
        method : 'get',
        params 
    })
}
/**
 * 根据部门ID查中心
 * @param {*} params 
 */
export function queryCenter(params) {
    return request({
        url : '/ppmDaCenter/queryCenter',
        method : 'get',
        params 
    })
}
/**
 * 获取操作权限列表
 * @param {*} params 
 */
export function getOperatAuth(params) {
    return request({
        url : '/ppmOnboardDistribution/getOperatAuth',
        method : 'get',
        params 
    })
}
/**
 * 获取权限详情
 * @param {*} params 
 */
export function getViewAuth(params) {
    return request({
        url : '/ppmOnboardDataauth/getViewAuth',
        method : 'get',
        params 
    })
}
/**
 * 流程发起
 * @param {*} data 
 */
export function authUpdate(data) {
    return request({
        url : '/ppmOnboardDataauth/update',
        method : 'post',
        data
    })
}
/**
 * 删除
 * @param {*} params 
 */
export function delRow(params) {
    return request({
        url : '/ppmOnboardDistribution/del',
        method : 'get',
        params 
    })
}
/**
 * 部门分配报表导入
 * @param {*} data 
 */
export function baobiaoImport(data) {
    return request({
        url : '/ppmDeptdistributionReport/importExcel',
        method : 'post',
        data
    })
}
/**
 * 部门分配报表导入
 * @param {*} data 
 */
export function baobiaoPager(data) {
    return request({
        url : '/ppmDeptdistributionReport/queryPager',
        method : 'post',
        data
    })
}
/**
 * 项目合同
 */
import request from '@/utils/request'

/**
 * 查询合同
 * @param {*} params 
 */
export function queryRdProjectContract(params) {
    return request({
        url : '/rdProjectContract/queryByRdProjectId',
        method : 'get',
        params
    })
}

/**
 * 查询合同
 * @param {*} params 
 */
export function queryByRdProjectContractId(params) {
    return request({
        url : '/rdProjectContract/queryByRdProjectContractId',
        method : 'get',
        params
    })
}
/**
 * 确认导入
 * @param {*} data
 * @returns
 */
export function contractPurchaseImport(data) {
  return request({
    url: '/purchase/imports',
    method: 'post',
    data
  })
}
/**
 * 保存合同
 * @param {*} data 
 */
export function saveRdProjectContract(data) {
    return request({
        url : '/rdProjectContract/save',
        method : 'post',
        data
    })
}


/**
 * 删除
 * @param {*} params 
 */
export function deleteRdProjectContract(params) {
    return request({
        url : '/rdProjectContract/delete',
        method : 'get',
        params
    })
}

/**
 * 分页
 * @param {*} data 
 */
export function queryPager(data) {
    return request({
        url : '/rdProjectContract/queryPager',
        method : 'post',
        data
    })
}
export function directConnection(data) {
  return request({
    url: '/purchase/queryPager',
    method: 'post',
    data
  })
}
/**
 * 导出
 * @param {*} data 
 */
export function queryPagerInfo(data) {
    return request({
        url : '/rdProjectContract/queryPagerInfo',
        method : 'post',
        data
    })
}

/**
 * 导入
 * @param {*} data 
 */
export function importContract(data) {
    return request({
        url : '/rdProjectContract/importContract',
        method : 'post',
        data,
    })
}

/**
 * 修改合同预算信息
 * @param {*} data 
 */
export function updateContractInfo(data) {
    return request({
        url : '/rdProjectContract/updateContractInfo',
        method : 'post',
        data
    })
}


/**
 * 研发项目关联
 * @param {*} data 
 */
export function updateContractResearchProject(data) {
    return request({
        url : '/rdProjectContract/updateContractResearchProject',
        method : 'post',
        data
    })
}


/**
 * 合同及支付信息
 * @param {*} data 
 */
export function updateContractPayInfo(data) {
    return request({
        url : '/rdProjectContract/updateContractPayInfo',
        method : 'post',
        data
    })
}

/**
 * 财务核算信息
 * @param {*} data 
 */
export function updateContractFinancialInfo(data) {
    return request({
        url : '/rdProjectContract/updateContractFinancialInfo',
        method : 'post',
        data
    })
}
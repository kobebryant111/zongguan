/**
 * 我的待办
 */
import request from '@/utils/request'

/**
 * 获取我的代办
 * @param {*} data 
 */
export function queryMyDbNum(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryMyDbNum',
        method: 'post',
        data
    })
}

/**
* 自定义指标制定分页查询
* @param {*} data 
*/
export function queryZdyzbzdPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryZdyzbzdPagerList',
        method: 'post',
        data
    })
}

/**
* 自定义指标自评分页查询
* @param {*} data 
*/
export function queryZdyzbzpPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryZdyzbzpPagerList',
        method: 'post',
        data
    })
}

/**
 * 公共指标制定分页查询
 * @param {*} data 
 */
export function queryGgzbzdPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryGgzbzdPagerList',
        method: 'post',
        data
    })
}

/**
 * 考核列表查询
 * @param {*} data 
 */
 export function getCheckTree(data) {
    return request({
        url: '/ppmDepkpiDepCheck/getCheckTree',
        method: 'post',
        data
    })
}

/**
 * 考核制定提交
 * @param {*} data 
 */
 export function commitzbzd(data) {
    return request({
        url: '/ppmDepkpiDepCheck/commitzbzd',
        method: 'post',
        data
    })
}

/**
 * 自评提交
 * @param {*} data 
 */
 export function zpCommit(data) {
    return request({
        url: '/ppmDepkpiDepCheck/zpCommit',
        method: 'post',
        data
    })
}

/**
 * 公共评价提交
 * @param {*} data 
 */
 export function ggPjCommit(data) {
    return request({
        url: '/ppmDepkpiDepCheck/ggPjCommit',
        method: 'post',
        data
    })
}

/**
 * 自定义评价提交
 * @param {*} data 
 */
 export function zdyPjCommit(data) {
    return request({
        url: '/ppmDepkpiDepCheck/zdyPjCommit',
        method: 'post',
        data
    })
}


/**
 * 评价分页查询
 * @param {*} data 
 */
 export function queryPjPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryPjPagerList',
        method: 'post',
        data
    })
}

/**
 * 考核分页查询
 * @param {*} data 
 */
 export function queryCheckPage(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryCheckPage',
        method: 'post',
        data
    })
}

/**

/**
 * 公共考核分页查询
 * @param {*} data 
 */
 export function queryGgCheckPage(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryGgCheckPage',
        method: 'post',
        data
    })
}

/**
 * 公共考核明细分页查询
 * @param {*} data 
 */
 export function queryGgCheckDetailPage(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryGgCheckDetailPage',
        method: 'post',
        data
    })
}

/**
 * 考核明细分页查询
 * @param {*} data 
 */
 export function getListPage(data) {
    return request({
        url: '/ppmDepkpiDepCheck/getListPage',
        method: 'post',
        data
    })
}

/**
 * 考核数据查询
 * @param {*} data 
 */
 export function querySumPoint(data) {
    return request({
        url: '/ppmDepkpiDepCheck/querySumPoint',
        method: 'post',
        data
    })
}

/**
 * 公共考核数据查询
 * @param {*} data 
 */
 export function queryGgSumPoint(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryGgSumPoint',
        method: 'post',
        data
    })
}


/**
 * 单个指标汇总查询
 * @param {*} data 
 */
 export function querySumPointById(data) {
    return request({
        url: '/ppmDepkpiDepCheck/querySumPointById',
        method: 'post',
        data
    })
}

/**
 * 修改状态
 * @param {*} data 
 */
 export function upCheckStatus(data) {
    return request({
        url: '/ppmDepkpiDepCheck/upCheckStatus',
        method: 'post',
        data
    })
}

/**
 * 自定义指标审批分页
 * @param {*} data 
 */
 export function querySpPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/querySpPagerList',
        method: 'post',
        data
    })
}

/**
 * 公共指标审批分页
 * @param {*} data 
 */
 export function queryGgSpPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryGgSpPagerList',
        method: 'post',
        data
    })
}

/**
 * 一级部审批分页
 * @param {*} data 
 */
 export function queryYjbSpPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/queryYjbSpPagerList',
        method: 'post',
        data
    })
}

/**
 * 指标审批提交
 * @param {*} data 
 */
 export function spCommit(data) {
    return request({
        url: '/ppmDepkpiDepCheck/spCommit',
        method: 'post',
        data
    })
}

/**
 * 指标审批退回
 * @param {*} data 
 */
 export function suggest(data) {
    return request({
        url: '/ppmDepkpiDepCheck/suggest',
        method: 'post',
        data
    })
}

/**
 * 评价导出
 * @param {*} data 
 */
 export function exportPjPagerList(data) {
    return request({
        url: '/ppmDepkpiDepCheck/exportPjPagerList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
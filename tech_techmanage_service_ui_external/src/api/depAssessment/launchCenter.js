/**
 * 部门考核-发起中心
 */
import request from '@/utils/request'


/**
 * 部门考核生成发起中心数据
 * @param {*} data 
 */
export function createPpmDepKpiLaunchCenter(data) {
    return request({
        url: '/ppmDepKpiLaunchCenter/createPpmDepKpiLaunchCenter',
        method: 'post',
        data
    })
}

/**
 * 更新数据
 * @param {*} data 
 */
export function updatePpmDepKpiLaunchCenter(data) {
    return request({
        url: '/ppmDepKpiLaunchCenter/update',
        method: 'post',
        data
    })
}
/**
 * 获得发起中心数据
 * @param {*} params 
 */
export function getPpmDepKpiDepRelList(params) {
    return request({
        url: '/ppmDepKpiLaunchCenter/getPpmDepKpiDepRelList',
        method: 'post',
        params
    })
}

/**
 * 发起方法
 * @param {*} data 
 */
export function launchUrl(data,url) {
    return request({
        url: url,
        method: 'post',
        data
    })
}







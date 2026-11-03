/**
 * 组织考核-部门模板管理
 */
import request from '@/utils/request'

/**
 * 分页列表查询
 * @param {*} data 
 */
export function queryDepModelPagerList(data) {
    return request({
        url: '/ppmDepkpiDepModel/queryDepModelPagerList',
        method: 'post',
        data
    })
}

/**
 * 部门更换模板
 * @param {*} data 
 */
 export function changeDepModel(data) {
    return request({
        url: '/ppmDepkpiDepModel/changeDepModel',
        method: 'post',
        data
    })
}

/**
 * 一级部指标
 * @param {*} data 
 */
 export function queryFirDep(data) {
    return request({
        url: '/ppmDepkpiDepModel/queryFirDep',
        method: 'post',
        data
    })
}
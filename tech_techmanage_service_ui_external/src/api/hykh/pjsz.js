/**
 * 评价设置中心
 */
import request from '@/utils/request'

/**
 * 获取评价设置页面数据
 * @param {*} data 
 */
export function getEosDictEntrys(data) {
    return request({
        url: '/ppmKpiItemSwitch/getEosDictEntrys',
        method: 'post',
        data
    })
}
/**
 * 添加评价项设置
 * @param {*} data 
 */
export function addEvalSet(data) {
    return request({
        url: '/ppmKpiItemSwitch/addEvalSet',
        method: 'post',
        data
    })
}
/**
 * 更新评价项设置
 * @param {*} data 
 */
export function updateEvalSet(data) {
    return request({
        url: '/ppmKpiItemSwitch/updateEvalSet',
        method: 'post',
        data
    })
}
/**
 * 删除
 * @param {*} params 
 */
export function delEvalSet(params) {
    return request({
        url: '/ppmKpiItemSwitch/delEvalSet',
        method: 'delete',
        params
    })
}
/**
 * 添加级别设置
 * @param {*} data 
 */
export function addKpiLevelPerson(data) {
    return request({
        url: '/ppmKpiLevelPerson/addKpiLevelPerson',
        method: 'post',
        data
    })
}
/**
 * 删除级别设置
 * @param {*} data 
 */
export function deleteKpiLevelPerson(data) {
    return request({
        url: '/ppmKpiLevelPerson/deleteKpiLevelPerson',
        method: 'post',
        data
    })
}
/**
 * 同步级别设置
 * @param {*} data 
 */
export function syncLevelPersonData(data) {
    return request({
        url: '/ppmKpiLevelPerson/syncLevelPersonData',
        method: 'post',
        data
    })
}
/**
 * 一级部查询
 * @param {*} data 
 */
export function queryShowPager(data) {
    return request({
        url: '/ppmKpiLevelPerson/queryShowPager',
        method: 'post',
        data
    })
}
/**
 * 一级部导出
 * @param {*} data 
 */
export function exportData(data) {
    return request({
        url: '/ppmKpiLevelPerson/exportData',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

/**
 * 一级部导入
 * @param {*} data 
 */
export function importExcel(data) {
    return request({
        url: '/ppmKpiLevelPerson/importExcel',
        method: 'post',
        data
    })
}
/**
 * 一级部更新
 * @param {*} data 
 */
export function updateOrAdd(data) {
    return request({
        url: '/ppmKpiLevelPerson/updateOrAdd',
        method: 'post',
        data
    })
}





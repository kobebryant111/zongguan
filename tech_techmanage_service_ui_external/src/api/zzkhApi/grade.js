/**
 * 组织考核-评级配置
 */
 import request from '@/utils/request'

/**
 * 级别分页列表查询
 * @param {*} data 
 */
 export function queryGradePagerList(data) {
    return request({
        url: '/ppmDepkpiGrade/queryGradePagerList',
        method: 'post',
        data
    })
}

/**
 * 级别新增
 * @param {*} data 
 */
 export function addGrade(data) {
    return request({
        url: '/ppmDepkpiGrade/addGrade',
        method: 'post',
        data
    })
}

/**
 * 级别修改
 * @param {*} data 
 */
 export function updateGrade(data) {
    return request({
        url: '/ppmDepkpiGrade/updateGrade',
        method: 'post',
        data
    })
}

/**
 * 级别删除
 * @param {*} data 
 */
 export function deleteGrade(data) {
    return request({
        url: '/ppmDepkpiGrade/deleteGrade',
        method: 'delete',
        params: data
    })
}

/**
 * 级别总数查询
 * @param {*} data 
 */
 export function queryDepNum(data) {
    return request({
        url: '/ppmDepkpiGradeTotal/queryDepNum',
        method: 'post',
        data
    })
}

/**
 * 编辑部门总数
 * @param {*} data 
 */
 export function updateDepNum(data) {
    return request({
        url: '/ppmDepkpiGradeTotal/updateDepNum',
        method: 'post',
        data
    })
}

/**
 * 级别列表查询
 * @param {*} data 
 */
 export function queryGradeList(data) {
    return request({
        url: '/ppmDepkpiGradeTotal/queryGradeList',
        method: 'post',
        data
    })
}

/**
 * 评级导出
 * @param {*} data 
 */
 export function exportPjGradePagerList(data) {
    return request({
        url: '/ppmDepkpiDepGrade/exportPjGradePagerList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

/**
 * 级别导入
 * @param {*} data 
 */
 export function importExcel(data) {
    return request({
        url: '/ppmDepkpiGradeTotal/importExcel',
        method: 'post',
        data
    })
}

/**
 * 级别导出
 * @param {*} data 
 */
 export function exportGradeTotal(data) {
    return request({
        url: '/ppmDepkpiGradeTotal/exportGradeTotal',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


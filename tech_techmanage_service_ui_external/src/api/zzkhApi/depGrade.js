/**
 * 组织考核-部门评级
 */
 import request from '@/utils/request'

 /**
 * 部门评级列表查询
 * @param {*} data 
 */
export function queryDepGradePagerList(data) {
    return request({
        url: '/ppmDepkpiDepGrade/queryDepGradePagerList',
        method: 'post',
        data
    })
}

 /**
 * 修改部门级别
 * @param {*} data 
 */
export function updateDepGrade(data) {
    return request({
        url: '/ppmDepkpiDepGrade/updateDepGrade',
        method: 'post',
        data
    })
}

 /**
 * 查询二级部级别比例
 * @param {*} data 
 */
  export function queryDepGradeScale(data) {
    return request({
        url: '/ppmDepkpiDepGrade/queryDepGradeScale',
        method: 'post',
        data
    })
}

 /**
 * 查询中心级别比例
 * @param {*} data 
 */
  export function queryCenterGradeScale(data) {
    return request({
        url: '/ppmDepkpiDepGrade/queryCenterGradeScale',
        method: 'post',
        data
    })
}

 /**
 * 评级提交
 * @param {*} data 
 */
  export function gradeCommit(data) {
    return request({
        url: '/ppmDepkpiDepGrade/gradeCommit',
        method: 'post',
        data
    })
}

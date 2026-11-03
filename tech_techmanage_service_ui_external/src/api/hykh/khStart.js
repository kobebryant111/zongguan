/**
 * 考核发起中心
 */
import request from '@/utils/request'

/**
 * 获取开关配置页面数据
 * @param {*} data 
 */
export function getPpmKpiItemSwitchList(data) {
    return request({
        url: '/ppmKpiItemSwitch/getPpmKpiItemSwitchList',
        method: 'post',
        data
    })
}
/**
 * 考核配置发起中心数据发起
 * @param {*} data 
 */
export function launch(data) {
    return request({
        url: '/ppmKpiItemSwitch/launch',
        method: 'post',
        data
    })
}
/**
 * 更新考核开关配置项
 * @param {*} data 
 */
export function updatePpmKpiItemSwitch(data) {
    return request({
        url: '/ppmKpiItemSwitch/updatePpmKpiItemSwitch',
        method: 'post',
        data
    })
}

/**
 * 更新考核开关配置项(发起)
 * @param {*} data 
 */
export function updateLaunchPpmKpiItemSwitch(data) {
    return request({
        url: '/ppmKpiItemSwitch/updateLaunchPpmKpiItemSwitch',
        method: 'post',
        data
    })
}

/**
 * 发起员工考核数据
 * @param {*} data 
 */
export function launchEmployee(data) {
    return request({
        url: '/ppmKpiPm/launchEmployee',
        method: 'post',
        data
    })
}

/**
 * 发起经理层考核数据
 * @param {*} data 
 */
export function launchManagers(data) {
    return request({
        url: '/ppmKpiPm/launchManagers',
        method: 'post',
        data
    })
}

/**
 * 发起主持经理考核数据
 * @param {*} data 
 */
export function launchWorkManager(data) {
    return request({
        url: '/ppmKpiPm/launchWorkManager',
        method: 'post',
        data
    })
}
/**
 * 发起经理半年度自评KPI考核
 * @param {*} data 
 */
export function launchSelfEval(data) {
    return request({
        url: '/ppmKpiPm/launchSelfEval',
        method: 'post',
        data
    })
}
/**
 * 发起履职评估
 * @param {*} data 
 */
export function launchPostEvaluationEval(data) {
    return request({
        url: '/ppmKpiPm1/launchPostEvaluationEval',
        method: 'post',
        data
    })
}
/**
 * 同步给考核权限数据
 * @param {*} data 
 */
export function updatePpmKpiPm(data) {
    return request({
        url: '/ppmKpiRole/updatePpmKpiPm',
        method: 'post',
        data
    })
}
/**
 * 发起年度评级
 * @param {*} data 
 */
export function launchYearData(data) {
    return request({
        url: '/ppmKpiPm1/launchYearData',
        method: 'post',
        data
    })
}
/**
 * 发起综合能力评价
 * @param {*} data 
 */
export function launchComprehensive(data) {
    return request({
        url: '/ppmKpiPm2/launchComprehensive',
        method: 'post',
        data
    })
}
/**
 * 发起总体评语
 * @param {*} data 
 */
export function launchComment(data) {
    return request({
        url: '/ppmKpiPm2/launchComment',
        method: 'post',
        data
    })
}
/**
 * 发起经理Kpi自评
 * @param {*} data 
 */
export function launchDepManagersSelfEval(data) {
    return request({
        url: '/ppmKpiPm2/launchDepManagersSelfEval',
        method: 'post',
        data
    })
}



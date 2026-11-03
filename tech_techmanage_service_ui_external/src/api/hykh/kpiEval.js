/**
 * KPI评价
 */
import request from '@/utils/request'

/**
 * 查询考核评价分页信息
 * @param {*} data 
 */
export function getPpmKpiPmEvalInfoPage(data) {
    return request({
        url: '/ppmKpiPm/getPpmKpiPmEvalInfoPage',
        method: 'post',
        data
    })
}
/**
 * 通过当前人和审批人判断考核所处阶段
 * @param {*} data 
 */
export function judgeStatus(data) {
    return request({
        url: '/ppmKpiRole/judgeStatus',
        method: 'post',
        data
    })
}
/**
 * 评价修改考核信息
 * @param {*} data 
 */
export function updateDetail(data) {
    return request({
        url: '/ppmKpiPmDetail/updateDetail',
        method: 'post',
        data
    })
}
/**
 * 提交评价
 * @param {*} data 
 */
export function submitEval(data) {
    return request({
        url: '/ppmKpiPmDetail/submitEval',
        method: 'post',
        data
    })
}
/**
 * 提交评价(全部)
 * @param {*} data 
 */
export function submitKpiEval(data) {
    return request({
        url: '/ppmKpiPm1/submitKpiEval',
        method: 'post',
        data
    })
}
/**
 * 回退自评
 * @param {*} data 
 */
export function backEvalSelf(data) {
    return request({
        url: '/ppmKpiPmDetail/backEvalSelf',
        method: 'post',
        data
    })
}
/**
 * 评价回退初评
 * @param {*} data 
 */
export function backFirstEval(data) {
    return request({
        url: '/ppmKpiPmDetail/backFirstEval',
        method: 'post',
        data
    })
}
/**
 * 获取综合能力评价汇总数据
 * @param {*} data 
 */
export function getPpmKpiPmComprehensiveAbilityEvalInfo(data) {
    return request({
        url: '/ppmKpiPmDetail/getPpmKpiPmComprehensiveAbilityEvalInfo',
        method: 'post',
        data
    })
}
/**
 * 查询综合能力评价数据
 * @param {*} params 
 */
export function queryComprehensiveAbilityEvalInfo(params) {
    return request({
        url: '/ppmKpiPmDetail/queryComprehensiveAbilityEvalInfo',
        method: 'get',
        params
    })
}
/**
 * 获得考核评价TAB页的统计项
 * @param {*} data 
 */
export function getCheckEvalItemsSummary(data) {
    return request({
        url: '/ppmKpiPm1/getCheckEvalItemsSummary',
        method: 'post',
        data
    })
}
/**
 * 提交综合能力评价
 * @param {*} data 
 */
export function submitComprehensiveAbilityEval(data) {
    return request({
        url: '/ppmKpiPmDetail/submitComprehensiveAbilityEval',
        method: 'post',
        data
    })
}
/**
 * 提交整体评语
 * @param {*} data 
 */
export function submitWholeComment(data) {
    return request({
        url: '/ppmKpiPmDetail/submitWholeComment',
        method: 'post',
        data
    })
}
/**
 * 保存评语
 * @param {*} data 
 */
export function saveWholeComment(data) {
    return request({
        url: '/ppmKpiPmDetail1/saveWholeComment',
        method: 'post',
        data
    })
}
/**
 * 综合能力，履职评估，平衡计分统计评价数据
 * @param {*} data 
 */
export function queryPpmKpiPmDetailEvalGroup(data) {
    return request({
        url: '/ppmKpiPmDetail1/queryPpmKpiPmDetailEvalGroup',
        method: 'post',
        data
    })
}
/**
 * 保存综合能力评价
 * @param {*} data 
 */
export function saveComprehensiveAbilityEval(data) {
    return request({
        url: '/ppmKpiPmDetail/saveComprehensiveAbilityEval',
        method: 'post',
        data
    })
}
/**
 * 综合能力整体提交
 * @param {*} data 
 */
export function submitTotalComprehensiveAbilityEval(data) {
    return request({
        url: '/ppmKpiPmDetail1/submitTotalComprehensiveAbilityEval',
        method: 'post',
        data
    })
}
/**
 * 获取履职评估数据
 * @param {*} data 
 */
export function getPpmKpiPmPostEvaluationEvalInfo(data) {
    return request({
        url: '/ppmKpiPmDetail1/getPpmKpiPmPostEvaluationEvalInfo',
        method: 'post',
        data
    })
}
/**
 * 查询履职评估评价数据
 * @param {*} params 
 */
export function queryPostEvaluationEvalEvalInfo(params) {
    return request({
        url: '/ppmKpiPmDetail1/queryPostEvaluationEvalEvalInfo',
        method: 'get',
        params
    })
}
/**
 * 提交履职评估评价
 * @param {*} data 
 */
export function submitTotalPostEvaluationEval(data) {
    return request({
        url: '/ppmKpiPmDetail1/submitTotalPostEvaluationEval',
        method: 'post',
        data
    })
}
/**
 * 保存履职评估评价
 * @param {*} data 
 */
export function savePostEvaluationEval(data) {
    return request({
        url: '/ppmKpiPmDetail1/savePostEvaluationEval',
        method: 'post',
        data
    })
}
/**
 * 查询下级评上级履职评估列表
 * @param {*} data 
 */
export function getPostEvalInfoSummaryPager(data) {
    return request({
        url: '/ppmKpiPm1/getPostEvalInfoSummaryPager',
        method: 'post',
        data
    })
}
/**
 * 查询下级评上级履职评估
 * @param {*} data 
 */
export function queryPpmKpiPostevaluationInfos(data) {
    return request({
        url: '/ppmKpiPostevaluation/queryPpmKpiPostevaluationInfos',
        method: 'post',
        data
    })
}
/**
 * 下级评上级履职评估-更新
 * @param {*} data 
 */
export function updateSinglePpmKpiPostevaluation(data) {
    return request({
        url: '/ppmKpiPostevaluation/updateSinglePpmKpiPostevaluation',
        method: 'post',
        data
    })
}
/**
 * 下级评上级履职评估-提交
 * @param {*} data 
 */
export function submitPpmKpiPostevaluation(data) {
    return request({
        url: '/ppmKpiPostevaluation/submitPpmKpiPostevaluation',
        method: 'post',
        data
    })
}
/**
 * 后台查询页面
 * @param {*} data 
 */
export function getPostEvaluationGroupPager(data) {
    return request({
        url: '/ppmKpiPmDetail1/getPostEvaluationGroupPager',
        method: 'post',
        data
    })
}
/**
 * 下级评上级履职评估-更新
 * @param {*} data 
 */
export function updateKjbLzpgDetail(data) {
    return request({
        url: '/ppmKpiPmDetail/updateDetail',
        method: 'post',
        data
    })
}
/**
 * 下级评上级履职评估-提交
 * @param {*} data 
 */
export function submitTechPostEvaluation(data) {
    return request({
        url: '/ppmKpiPmDetail1/submitTechPostEvaluation',
        method: 'post',
        data
    })
}
/**
 * 查询科技部履职评估
 * @param {*} data 
 */
export function getPostEvaluationList(data) {
    return request({
        url: '/ppmKpiPmDetail1/getPostEvaluationList',
        method: 'post',
        data
    })
}

/**
 * 评价全部提交
 * @param {*} data 
 */
export function totalSubmit(data) {
    return request({
        url: '/ppmKpiPm/totalSubmit',
        method: 'post',
        data
    })
}







/**
 * 履职评估
 */
import request from '@/utils/request'

/**
 * 员工考核+我的考核履职评估详情
 * @param {*} data 
 */
export function getPpmKpiPmPostEvaluationEvalInfo(data) {
    return request({
        url: '/ppmKpiPmDetail1/getPpmKpiPmPostEvaluationEvalInfo',
        method: 'post',
        data
    })
}







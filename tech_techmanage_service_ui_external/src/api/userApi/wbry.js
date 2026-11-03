/**
 * 人员准入 js
 */
import request from '@/utils/request'

/**
 * 外包人员准入基本信息
 * @param {*} data 
 */
export function getBasicInformation(data) {
    return request({
        url : '/commEmployee/queryCrewPage',
        method : 'post',
        data
    })
}
/**
 * 外包人员公司 - 外包商 api
 */
import request from '@/utils/request'
/**
 * 分页
 * @param {*} data 
 */
export function getPagerList(data) {
    return request({
        url : '/ppmOutsourcingInfo/queryPager',
        method : 'post',
        data
    });
}
/**
 * 获取所有外包商
 */
export function getListAll() {
    return request({
        url : '/ppmOutsourcingInfo/getListAll',
        method : 'get',
    })
}
import request from '@/utils/request'
/**
 * 查询业务字典
 * @param {dicttypeid} params 
 */
export function getDicEntry(params) {
    return request({
        url : '/eosDictEntry/queryList',
        method : 'get',
        params : params
    })
};
/**
 * 查询业务字典
 * @param {dicttypeid} params 
 */
export function getDictListForStatus(params) {
    return request({
        url : '/eosDictEntry/queryListForStatus',
        method : 'get',
        params : params
    })
};
/**
 * 详情
 * @param {*} params 
 */
export function getDicEntryOne(params) {
    return request({
        url : '/eosDictEntry/queryDetail',
        method : 'get',
        params : params
    })
};

/**
 * 获取系统来源 oa vd
 * @param {*} params 
 */
export function getDomainName(params) {
    return request({
        url : '/eosDictEntry/getDomainName',
        method : 'get',
        params : params
    })
};


/**
 * 查询所有字典
 * @param {dicttypeid} params 
 */
export function queryAllDict(params) {
    return request({
        url : '/eosDictEntry/queryAll',
        method : 'get',
        params : params
    })
};
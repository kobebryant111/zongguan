/**
 * 分行外包采购审批配置
 */
import request from '@/utils/request';
/**
 * 页面初始加载数据
 */
export function getList() {
    return request({
        url : '/ppmFhspbmpz/getList',
        method : 'post',
    });
}

/**
 * 只查询分行的机构树
 */
export function queryBranchThree() {
    return request({
        url : '/commOrganization/queryBranchThree',
        method : 'get',
    });
}

/**
 * 员工机构树
 */
export function getEmpTree(id) {
    return request({
        url : `/commEmployee/queryEmployeeByOrgidTree/${id}`,
        method : 'get',
    });
}
/**
 * 添加 || 更新
 */
export function updateOrAdd(data) {
    return request({
        url : `/ppmFhspbmpz/update`,
        method : 'post',
        data
    });
}
/**
 * 删除
 */
export function deleteById(id) {
    return request({
        url : `/ppmFhspbmpz/delete/${id}`,
        method : 'delete',
    });
}
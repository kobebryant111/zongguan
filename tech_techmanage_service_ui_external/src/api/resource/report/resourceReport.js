import request from '@/utils/request'
/**
 * 加载关联系统树
 * @returns
 */
export function queryRepDeptTree() {
  return request({
    url: '/resReport/queryRepDeptTree',
    method: 'get'
  })
}

/**
 * 资源新增列表查询
 * @param {*} data
 */
export function queryResAddedListAndStatisticReportInfo(data) {
  return request({
    url: '/resReport/queryPager',
    method: 'post',
    data
  })
}

/**
 * 导出报表列表查询
 * @param {*} data
 */
export function exportResourceReportList(data) {
  return request({
    url: '/resReport/exportResReport',
    method: 'post',
    data,
    responseType: 'blob'
  })
}

/**
 * 当前登录者身份：是否为总行金融科技部-系统管理部人员
 */
export function getDeptIdLogin() {
  return request({
    url: '/resReport/getDeptIdLogin',
    method: 'post'
  })
}

import request from '@/utils/request'
/**
 * iaas资源页面分页查询
 * @param {*} data
 */
export function queryIaasPager(data) {
  return request({
    url: '/resAddedExtApi/queryIaasResourcePager',
    method: 'post',
    data
  })
}

/**
 * iaas资源页面分页查询
 * @param {*} data
 */
export function queryIaasResourcePagerOhter(data) {
  return request({
    url: '/resAddedExtApi/queryIaasResourcePagerOhter',
    method: 'post',
    data
  })
}

/**
 * itemExt列表查询
 * @param {*} data
 */
export function queryItemExtList(data) {
  return request({
    url: '/resAddedExtApi/queryList',
    method: 'post',
    data
  })
}

/**
 * 保存
 * @param {*} data
 */
export function saveItemExt(data) {
  return request({
    url: '/resAddedExtApi/save',
    method: 'post',
    data
  })
}

/**
 * 检查登录用户所属部门
 */
export function checkLoginUserDept() {
  return request({
    url: '/resAddedExtApi/checkLoginUserDept',
    method: 'post'
  })
}

/**
 * 根据ID查询资源配置的详细信息
 * @param {*} data
 * @returns
 */
export function queryResAddedConfig(data) {
  return request({
    url: '/resAddedExtApi/queryResAddedConfig',
    method: 'post',
    data
  })
}

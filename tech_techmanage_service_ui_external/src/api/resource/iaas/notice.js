import request from '@/utils/request'
/**
 * 分页查询通知配置
 * @param {*} data
 */
export function queryPageConfig (data) {
  return request({
    url: '/resNoticeConfig/queryPager',
    method: 'post',
    data
  })
}
/**
 * 列表查询
 * @param {*} data
 */
export function queryList (data) {
  return request({
    url: '/resNoticeConfig/queryList',
    method: 'post',
    data
  })
}
/**
 * 保存
 * @param {*} data
 */
export function saveConfig (data) {
  return request({
    url: '/resNoticeConfig/save',
    method: 'post',
    data
  })
}
/**
 * 删除
 * @param {*} data
 */
export function deleteConfig (data) {
  return request({
    url: '/resNoticeConfig/delete',
    method: 'post',
    data
  })
}

/**
 * 当前登录者身份：通知设置通过字典配置的独立身份，与角色等无关
 */
export function loginUserIdentity () {
  return request({
    url: '/resNoticeConfig/loginUserIdentity',
    method: 'post',
  })
}
import request from '@/utils/request'

/**
 * 获取应用系统列表数据
 * @param {*} params
 */
export function queryUseSysPageList(params) {
  return request({
    url: '/resUseSys/queryUseSysPageList',
    method: 'post',
    params
  })
}

/**
 * 获取应用系统列表数据
 * @param {*} data
 */
export function queryResUseSysList(data) {
  return request({
    url: '/resUseSys/queryPager',
    method: 'post',
    data
  })
}

/**
 * 获取应用系统列表数据
 * @param {*} params
 */
export function queryUseSysList(params) {
  return request({
    url: '/resUseSys/queryUseSysList',
    method: 'post',
    params
  })
}

/**
 * 获取系统管理员A岗人员
 * @param {*} params
 */
export function querySystemUser(params) {
  return request({
    url: '/resUseSys/querySystemUser',
    method: 'post',
    params
  })
}

/**
 * 获取流程中应用系统数据
 * @param {*} params
 */
export function queryResUseSysAlterData(params) {
  return request({
    url: '/resUseSys/queryResUseSysAlterData',
    method: 'post',
    params
  })
}

/**
 * 保存应用系统变更数据
 * @param {*} data
 */
export function saveUseSysData(data) {
  return request({
    url: '/resUseSys/saveUseSysData',
    method: 'post',
    data
  })
}

/**
 * 发起应用系统变更流程
 * @param {*} data
 */
export function submitResUseSysData(data) {
  return request({
    url: '/resUseSys/submitResUseSysData',
    method: 'post',
    data
  })
}

/**
 * 重新发起应用系统变更流程
 * @param {*} data
 */
export function reSubmitWorkFlow(data) {
  return request({
    url: '/resUseSys/reSubmitWorkFlow',
    method: 'post',
    data
  })
}

/**
 * 删除配置人员信息
 * @param {*} data
 */
export function delUseSysAlterData(data) {
  return request({
    url: '/resUseSys/delUseSysAlterData',
    method: 'post',
    data
  })
}

/**
 * 提交工作项
 * @param {*} data
 */
export function submitWorkItem(data) {
  return request({
    url: '/resUseSys/submitWorkItem',
    method: 'post',
    data
  })
}

/**
 * 流程撤回时回滚数据
 * @param {*} data
 */
export function updateUseSysFlowData(data) {
  return request({
    url: '/resUseSys/updateUseSysFlowData',
    method: 'post',
    data
  })
}

/**
 * 生成流水号
 * @param {*} params
 */
export function getFlowId(params) {
  return request({
    url: '/resUseSys/getFlowId',
    method: 'post',
    params
  })
}

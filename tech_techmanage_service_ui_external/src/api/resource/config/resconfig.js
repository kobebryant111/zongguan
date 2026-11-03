import request from '@/utils/request'

/**
 * 保存物理机套餐配置信息
 * @param {*} data
 */
export function addConfigCombo(data) {
  return request({
    url: '/resConfig/addConfigComboData',
    method: 'post',
    data
  })
}

/**
 * 获取物理机列表数据
 * @param {*} params
 */
export function queryConfigComboList(params) {
  return request({
    url: '/resConfig/queryConfigComboList',
    method: 'post',
    params
  })
}

/**
 * 修改物理机列表数据
 * @param {*} data
 */
export function upConfigComboData(data) {
  return request({
    url: '/resConfig/upConfigComboData',
    method: 'post',
    data: data
  })
}

/**
 * 保存虚拟机配件信息
 * @param {*} data
 */
export function addVMPartsData(data) {
  return request({
    url: '/resConfig/addVMPartsData',
    method: 'post',
    data
  })
}

/**
 * 修改虚拟机配件数据
 * @param {*} data
 */
export function upVMPartsData(data) {
  return request({
    url: '/resConfig/upVMPartsData',
    method: 'post',
    data: data
  })
}

/**
 * 获取软件列表数据
 * @param {*} params
 */
export function querySoftList(params) {
  return request({
    url: '/resConfig/querySoftList',
    method: 'post',
    params
  })
}

/**
 * 保存软件套餐配置信息
 * @param {*} data
 */
export function addSoftCombo(data) {
  return request({
    url: '/resConfig/addSoftData',
    method: 'post',
    data
  })
}

/**
 * 修改虚拟机配件数据
 * @param {*} data
 */
export function upSoftData(data) {
  return request({
    url: '/resConfig/upSoftData',
    method: 'post',
    data: data
  })
}

/**
 * 获取虚拟机列表数据
 * @param {*} params
 */
export function queryVMPartsList(params) {
  return request({
    url: '/resConfig/queryVMPartsList',
    method: 'post',
    params
  })
}

/**
 * 更新配置信息的状态
 * @param {*} data
 */
export function upConfigStatus(data) {
  return request({
    url: '/resConfig/upConfigStatus',
    method: 'post',
    data: data
  })
}

/**
 * 获取物理机配件
 * @param {*} params
 */
export function queryPhysicalParts(params) {
  return request({
    url: '/resConfig/queryPhysicalParts',
    method: 'post',
    params
  })
}

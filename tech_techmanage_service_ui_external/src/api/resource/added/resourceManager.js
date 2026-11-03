import request from '@/utils/request'
/**
 * 资源新增列表查询
 * @param {*} data
 */
export function queryResAddedList(data) {
  return request({
    url: '/resAdded/queryPager',
    method: 'post',
    data
  })
}

/**
 * 删除资源新增配置
 * @param {*} data
 * @returns
 */
export function deleteResourceAdded(data) {
  return request({
    url: '/resAdded/deleteResourceAdded',
    method: 'post',
    data
  })
}

/**
 * 加载关联系统树
 * @returns
 */
export function queryRelaSysTree() {
  return request({
    url: '/resAdded/queryRelaSysTree',
    method: 'get'
  })
}

/**
 * 加载关联系统信息
 * @returns
 */
export function getRelaSysData(data) {
  return request({
    url: '/resAdded/queryRelaSysData',
    method: 'post',
    data
  })
}

/**
 * 查询IP申请网络位置
 * @returns
 */
export function queryIpApplyNetAddress() {
  return request({
    url: '/resAdded/queryIpApplyNetAddress',
    method: 'get'
  })
}

/**
 * 查询IP申请网络位置
 * @returns
 */
export function queryRelaProgramTree() {
  return request({
    url: '/resAdded/queryRelaProgramTree',
    method: 'get'
  })
}

/**
 * 查询预先设置的情况
 * @returns
 */
export function querySelectionConfig() {
  return request({
    url: '/resAdded/querySelectionConfig',
    method: 'get'
  })
}

/**
 * 查询配件关系表
 * @returns
 */
export function queryHardWareRela() {
  return request({
    url: '/resAdded/queryHardWareRela',
    method: 'get'
  })
}

/**
 * 查询软件版本关系表
 * @returns
 */
export function querySoftWareRela() {
  return request({
    url: '/resAdded/querySoftWareRela',
    method: 'get'
  })
}

/**
 * 根据条件查询套餐列表
 * @param {*} data
 * @returns
 */
export function queryRelaComboList(data) {
  return request({
    url: '/resAdded/queryRelaComboList',
    method: 'post',
    data
  })
}

/**
 * 保存资源新增主表的值
 * @param {*} data
 * @returns
 */
export function addResource(data) {
  return request({
    url: '/resAdded/addResource',
    method: 'post',
    data
  })
}

/**
 * 保存配置数据并发起流程
 * @param {*} data
 * @returns
 */
export function submitResourceAdded(data) {
  return request({
    url: '/resAdded/submitResourceAdded',
    method: 'post',
    data
  })
}

/**
 * 查询存储套餐数据
 * @returns
 */
export function queryStorageComboData() {
  return request({
    url: '/resAdded/queryStorageComboData',
    method: 'get'
  })
}

/**
 * 根据ID查询资源配置的详细信息
 * @param {*} data
 * @returns
 */
export function queryResAddedConfig(data) {
  return request({
    url: '/resAdded/queryResAddedConfig',
    method: 'post',
    data
  })
}

/**
 * 退回发起重新提交
 * @param {*} data
 * @returns
 */
export function reSubmitWorkFlow(data) {
  return request({
    url: '/resAdded/reSubmitWorkFlow',
    method: 'post',
    data
  })
}

/**
 * 获取补录数据
 * @param {*} data
 * @returns
 */
export function queryAdditionalList(data) {
  return request({
    url: '/resAdded/queryAdditionalList',
    method: 'post',
    data
  })
}

/**
 * 终止拒绝的流程并删除业务数据
 * @param {*} data
 * @returns
 */
export function terminateProcess(data) {
  return request({
    url: '/resAdded/deleteProcessInstance',
    method: 'post',
    data
  })
}

/**
 * 终止拒绝的流程并删除业务数据
 * @param {*} data
 * @returns
 */
export function exportExcel(data) {
  return request({
    url: '/resAdded/exportZb',
    method: 'post',
    data,
    responseType: 'blob'
  })
}

/**
 * 加入购物车
 * @param {*} data
 * @returns
 */
export function putIntoShop(data) {
  return request({
    url: '/resAdded/putIntoShop',
    method: 'post',
    data
  })
}

/**
 * 点击资源子项菜单显示详情
 * @param {*} data
 * @returns
 */
export function showMenuItem(data) {
  return request({
    url: '/resAdded/showItemDetailById',
    method: 'post',
    data
  })
}

/**
 * 根据ID删除资源配置以及软硬件信息
 * @param {*} data
 * @returns
 */
export function deleteItemByItemId(data) {
  return request({
    url: '/resAdded/deleteItemByItemId',
    method: 'post',
    data
  })
}

/**
 * 根据ID删除对应资源配置以及软硬件信息
 * @param {*} data
 * @returns
 */
export function refreshMenu(data) {
  return request({
    url: '/resAdded/getDynamicForms',
    method: 'post',
    data
  })
}

/**
 * 根据主表Id删除所有的Item表 parts表 version表数据
 * @param {*} data
 * @returns
 */
export function deleteDynamicForms(data) {
  return request({
    url: '/resAdded/deleteConfigItem',
    method: 'post',
    data
  })
}

/**
 * 保存主表数据
 * @param {*} data
 * @returns
 */
export function saveForm(data) {
  return request({
    url: '/resAdded/saveResMain',
    method: 'post',
    data
  })
}

/**
 * 校验已作废数据
 * @param {*} data
 * @returns
 */
export function validOutofDateData(data) {
  return request({
    url: '/resAdded/validOutofDateMain',
    method: 'post',
    data
  })
}

/**
 * 【重新提交用】校验已作废数据
 * @param {*} data
 * @returns
 */
export function validOutofDateResubmit(data) {
  return request({
    url: '/resAdded/validOutofDateMainResubmit',
    method: 'post',
    data
  })
}

import request from '@/utils/request'
const api = '/testEmployeePool'
/**
 * init 发起流程
 * @param {*} data
 */
export function queryPager(data) {
  return request({
    url: api + '/queryPager',
    method: 'post',
    data
  })
}

/**
 * 导出
 * @param {*} data
 */
export function exportTestEmpPoolExcel(data) {
  return request({
    url: api + '/exportTestEmpPoolExcel',
    method: 'post',
    data
  })
}

/**
 * init 发起流程
 * @param {*} data
 */
export function getHeadOfCompanyList() {
  return request({
    url: api + '/getHeadOfCompanyList',
    method: 'get'
  })
}

/**
 * init 发起流程 获取所属公司
 * @param {*} data
 */
export function getAffilCompanyList() {
  return request({
    url: api + '/getAffilCompanyList',
    method: 'get'
  })
}

/**
 * init 发起流程 获取所属测试团队
 * @param {*} data
 */
export function getOwnedTestTeamList() {
  return request({
    url: api + '/getOwnedTestTeamList',
    method: 'get'
  })
}

/**
 * init 发起流程 获取所属测试团队
 * @param {*} data
 */
export function getOwnedTestGroupList() {
  return request({
    url: api + '/getOwnedTestGroupList',
    method: 'get'
  })
}

/**
 * init 发起流程 获取测试小组人员
 * @param {*} data
 */
export function getTestGroupEmpList() {
  return request({
    url: api + '/getTestGroupEmpList',
    method: 'get'
  })
}

/**
 * 测试人员信息修改
 * @param {*} data
 * @returns
 */
export function dialogAddSubmit(data) {
  return request({
    url: api + '/dialogAddSubmit',
    method: 'post',
    data
  })
}

/**
 * 查询测试团队表所有的小组信息
 */
export function selectTestGroupInfoMsg(data) {
  return request({
    url: api + '/selectTestGroupInfoMsg',
    method: 'post',
    data
  })
}

import request from '@/utils/request'
const api = '/testTeamManagement'
/**
 * init 发起流程
 * @param {*} data
 */
export function creatTestGroup(data) {
  return request({
    url: api + '/creatTestGroup',
    method: 'post',
    data
  })
}

/**
 * 查询测试小组人员
 */
export function selectTestGroupEmployee(data) {
  return request({
    url: api + '/selectTestGroupEmployee',
    method: 'post',
    data
  })
}

/**
 * 查询输入的测试小组名称
 */
export function selectGroupName(data) {
  return request({
    url: api + '/selectGroupName',
    method: 'post',
    data
  })
}

/**
 * 查询项目经理列表
 * @param {*} data
 * @returns
 */
export function selectTeamMansgerList(data) {
  return request({
    url: '/testManagement/selectTeamMansgerList',
    method: 'post',
    data
  })
}
// 查询团队成员列表
export function selectTeamMemberList(data) {
  return request({
    url: '/testManagement/selectTeamMemberList',
    method: 'post',
    data
  })
}
// 创建测试团队
export function creatOfTeam(data) {
  return request({
    url: '/testManagement/creatOfTeam',
    method: 'post',
    data

  })
}
// 查询测试团队
export function selectTeamList(data) {
  return request({
    url: '/testManagement/selectTeam',
    method: 'post',
    data
  })
}
// 查询测试团队
export function queryPager(data) {
  return request({
    url: '/testManagement/selectTeam',
    method: 'post',
    data
  })
}
// 查询团队成员
export function selectMemberList(data) {
  return request({
    url: '/testManagement/selectTeamMemberInfo',
    method: 'post',
    data
  })
}
export function updateTestTeam(data) {
  return request({
    url: '/testManagement/edit',
    method: 'post',
    data
  })
}
export function temMebById(data) {
  return request({
    url: '/testManagement/editTemMeb',
    method: 'post',
    data
  })
}

export function temMebAllNO(data) {
  return request({
    url: '/testManagement/queryTemMeb',
    method: 'post',
    data
  })
}

export function eidtTemMen(data) {
  return request({
    url: '/testManagement/updateTemMeb',
    method: 'post',
    data
  })
}

export function delTemById(data) {
  return request({
    url: '/testManagement/deleteTeam',
    method: 'post',
    data
  })
}

export function selectTemDetail(data) {
  return request({
    url: '/testManagement/temDetail',
    method: 'post',
    data
  })
}
export function queryPersonListInfo(data) {
  return request({
    url: '/testManagement/queryPagerInfo',
    method: 'post',
    data
  })
}

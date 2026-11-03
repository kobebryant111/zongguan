import request from '@/utils/request'

// 测试小组列表
export function queryPager(data) {
  return request({
    url: '/testManagement/queryPagerGrop',
    method: 'post',
    data
  })
}
// 测试小组下拉框
export function secltGroup(params) {
  return request({
    url: '/testManagement/secltGroup',
    method: 'post',
    params
  })
}
// 下载测试小组列表
export function queryPersonListInfo(data) {
  return request({
    url: '/testManagement/secltGroupExcel',
    method: 'post',
    data
  })
}
// 删除
export function deleteGroupById(data) {
  return request({
    url: '/testManagement/deleteGroupInfo',
    method: 'post',
    data
  })
}
// 编辑
export function getGroupDate(data) {
  return request({
    url: '/testManagement/groupDateInfo',
    method: 'post',
    data
  })
}
// dialog确认
export function updateTestGroup(data) {
  return request({
    url: '/testManagement/updateTestGroup',
    method: 'post',
    data
  })
}

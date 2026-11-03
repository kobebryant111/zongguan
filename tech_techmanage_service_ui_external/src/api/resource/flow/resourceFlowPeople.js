import request from '@/utils/request'

/**
 * 获取配置人员列表数据
 * @param {*} params
 */
export function queryFlowPeopleList(params) {
  return request({
    url: '/resFlowPeople/queryFlowPeopleList',
    method: 'post',
    params
  })
}

/**
 * 新增配置人员信息
 * @param {*} params
 */
export function insertFlowPeople(params) {
  return request({
    url: '/resFlowPeople/insertFlowPeople',
    method: 'post',
    params
  })
}

/**
 * 删除配置人员信息
 * @param {*} data
 */
export function delFlowPeople(data) {
  return request({
    url: '/resFlowPeople/delFlowPeople',
    method: 'post',
    data
  })
}

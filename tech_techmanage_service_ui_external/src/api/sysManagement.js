import request from '@/utils/request'
import { formDataHeaders, headers } from './api_header_conf'
// roleManagement
// 获取当前用户角色树
export function fetchUserRoleTree(query) {
  return request({
    headers,
    url: '/role/roleTree',
    method: 'get',
    params: query
  })
}

// 新增角色
export function newRole(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/role/createRole',
    method: 'post',
    data
  })
}

// 修改角色
export function alterRole(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/role/updateRole',
    method: 'put',
    data
  })
}

// 删除角色
export function deleteRole(query) {
  return request({
    headers,
    url: '/role/deleteRole',
    method: 'delete',
    params: query
  })
}


export function resetPassword(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/user/password/reset',
    method: 'put',
    data
  })
}


// 查询所有角色树
export function fetchAllRoles() {
  return request({
    headers,
    url: '/role/allRoles',
    method: 'get'
  })
}

// userManagement
// 分页查询用户
export function fetchUsers(query) {
  return request({
    headers,
    url: '/users',
    method: 'get',
    params: query
  })
}

// 查询机构
export function fetchBranch() {
  return request({
    headers,
    url: '/dept/treeList',
    method: 'get'
  })
}

// 新建用户
export function createUser(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/user/add',
    method: 'post',
    data
  })
}

// 修改用户
export function modifyUser(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/user',
    method: 'put',
    data
  })
}

// 删除用户
export function deleteUser(data) {
  const headers = formDataHeaders
  return request({
    headers,
    url: '/user/delete',
    method: 'post',
    data
  })
}

// 核查用户ID是否有效
export function checkUserId(query) {
  const headers = formDataHeaders
  return request({
    headers,
    url: 'user/validationUserId',
    method: 'get',
    params: query
  })
}

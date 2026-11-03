/**
 * 研发人员工作管理api
 */
import request from '@/utils/request'

export function queryResourceByPerson(data) {
  return request({
    url: '/researchProAllocation/userProjectMess',
    method: 'post',
    data
  })
}
export function documentsChanged(data) {
  return request({
    url: '/researchProAllocation/documentsChanged',
    method: 'post',
    data
  })
}
export function documentsDelete(data) {
  return request({
    url: '/researchProAllocation/documentsDelete',
    method: 'post',
    data
  })
}
export function downloadFile(params) {
  return request({
    url: '/researchProAllocation/downloadFile',
    method: 'post',
    params
  })
}
export function queryOrgPersonNew(data) {
  return request({
    url: '/researchProAllocation/rdQueryDeptTree',
    method: 'post',
    data
  })
}
export function downloadFiles(params) {
  return request({
    url: '/researchProAllocation/downloadFiles',
    method: 'post',
    params
  })
}

export function exportFileMsg(params) {
  return request({
    url: '/researchProAllocation/exportFileMsg',
    method: 'post',
    params
  })
}

export function renewChildren(params) {
  return request({
    url: '/researchProAllocation/renewChildren',
    method: 'post',
    params
  })
}

export function quertOrgDepartment() {
  const data = {}
  return request({
    url: 'researchProAllocation/queryDepartment',
    method: 'post',
    data
  })
}

export function quertOrgProject() {
  const data = {}
  return request({
    url: 'researchProAllocation/queryProject',
    method: 'post',
    data
  })
}

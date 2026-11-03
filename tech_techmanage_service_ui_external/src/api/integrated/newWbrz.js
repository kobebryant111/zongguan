// 新外包入职
import request from '@/utils/request'

/**
 * 创建joid-员工工号
 * @param {*} data
 */
export function getJobid(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/getJobid',
    method: 'get',
    data
  })
}

/**
 * 保存
 * @param {*} data
 */
export function saveDraft(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/add',
    method: 'post',
    data
  })
}

/**
 * 获取评分
 * @param {*} params
 */
export function getScore(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/getScore',
    method: 'get',
    params
  })
}

/**
 * 详情
 * @param {*} params
 */
export function getWbrzDetail(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/queryDetail',
    method: 'get',
    params
  })
}

/**
 * 发起申请
 * @param {*} data
 */
export function liuzhuan(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/liuzhuan',
    method: 'post',
    data
  })
}
/**
 * 删除申请
 * @param {*} data
 */
export function doDel(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/delete',
    method: 'delete',
    params: data
  })
}

/**
 * 资料上传
 * @param {*} data
 */
export function upload(data) {
  return request({
    url: '/ppmDocument/uploadExcel',
    method: 'post',
    data
  })
}

/**
 * 二级部经理审批
 * @param {*} data
 */
export function twoManagerAudit(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/twoManagerAudit',
    method: 'post',
    data
  })
}

/**
 * 综合部人事审批(资料审核)
 * @param {*} data
 */
export function zhbrsAudit(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/zhbrsAudit',
    method: 'post',
    data
  })
}

/**
 * 综合部风控征信审批
 * @param {*} data
 */
export function zhbfkzxAudit(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/zhbfkzxAudit',
    method: 'post',
    data
  })
}

/**
 * 分管总经理审批
 * @param {*} data
 */
export function fgzjlAudit(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/fgzjlAudit',
    method: 'post',
    data
  })
}

/**
 * 结算为人力总经理审批
 * @param {*} data
 */
export function rlzjlAudit(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/rlzjlAudit',
    method: 'post',
    data
  })
}

/**
 * 查询部门
 * @param {*} params
 */
export function searchDept(params) {
  return request({
    url: '/commDepartment/queryDetail',
    method: 'get',
    params
  })
}

/**
 * 获取图片地址
 * @param {*} params
 */
export function getImageUrl(params) {
  return request({
    url: '/ppmDocument/getImageUrl',
    method: 'get',
    params
  })
}

/**
 * 征信风控查询
 * @param {*} params
 */
export function getWindControl(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/getWindControl',
    method: 'get',
    params
  })
}

/**
 * 花名册导出(非管理员)
 * @param {*} data
 */
export function exporthmcExcel(data) {
  return request({
    url: '/commEmployee/exportOutsourcing',
    method: 'post',
    data,
    responseType: 'blob'
  })
}
/**
 * 导入成绩
 * @param {*} data
 */
export function importAchievement(data) {
  return request({
    url: '/ppmOutsourcingAccessapply/importAchievement',
    method: 'post',
    data
  })
}

/**
 * 导入成绩
 * @param {*} data
 */
export function importAccessCard(data) {
  return request({
    url: '/ppmOutsourcingBasic/importAccessCard',
    method: 'post',
    data
  })
}

/**
 * 花名册导出(管理员)
 * @param {*} data
 */
export function exportAdminOutsourcing(data) {
  return request({
    url: '/ppmOutsourcingBasic/exportAdminOutsourcing',
    method: 'post',
    data,
    responseType: 'blob'
  })
}

/**
 * 检查人力导入excel是否存在
 */
export function checkedExistEntry(params) {
  return request({
    url: `/ppmIntervieweeOverinfo/checkedPassEntry/${params}`,
    method: 'get'
  })
}

/**
 * 虚拟桌面详情查询
 * @param {*} params
 */
export function getXnzmDateil(params) {
  return request({
    url: '/virtualDesktop/getDateil',
    method: 'get',
    params
  })
}

/**
 * 虚拟桌面审批
 * @param {*} data
 */
export function nbAreaManagerApprove(data) {
  return request({
    url: '/virtualDesktop/nbAreaManagerApprove',
    method: 'post',
    data
  })
}

/**
 * SVN详情查询
 * @param {*} params
 */
export function getSvnDateil(params) {
  return request({
    url: '/virtualDesktop/getSvnDateil',
    method: 'get',
    params
  })
}

/**
 * SVN申请
 * @param {*} data
 */
export function svnApprove(data) {
  return request({
    url: '/virtualDesktop/svnApprove',
    method: 'post',
    data
  })
}

/**
 * 是否重复入职
 * @param {*} params
 */
export function getCheckedIdCard(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/getCheckedIdCard',
    method: 'get',
    params
  })
}

/**
 * 公安信息
 * @param {*} params
 */
export function getCheckedSecurity(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/getCheckedSecurity',
    method: 'get',
    params
  })
}

/**
 * 检查虚拟桌面号是否重复
 * @param {*} params
 */
export function checkedVirtualNo(params) {
  return request({
    url: '/virtualDesktop/checkedVirtualNo',
    method: 'get',
    params
  })
}

/**
 * 外包花名册导出
 * @param {*} data
 */
export function exportOutsourcing(data) {
  return request({
    url: '/ppmOutsourcingBasic/exportOutsourcing',
    method: 'post',
    data,
    responseType: 'blob'
  })
}

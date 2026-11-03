/**
 * 外包人员准入流程
 */
import request from '@/utils/request'
/**
 * 流转 -- 发起申请
 * @param {*} data
 */
export function liuzhuan(data) {
  return request({
    url: '/ppmOutsourcingApply/liuzhuan',
    method: 'post',
    data: data
  })
}
/**
 * 保存
 * @param {*} data
 */
export function save(data) {
  return request({
    url: '/ppmOutsourcingApply/add',
    method: 'post',
    data
  })
}

/**
 * 查询项目名称
 * @param {*} data
 */
export function getTree(data) {
  return request({
    url: '/ppmProjectBuildSoft/queryProjectTree',
    method: 'get',
    data
  })
}
/**
 * 查询系统名称
 * @param {*} params
 */
export function querySystemList(params) {
  return request({
    url: '/ppmSystemInfo/queryList',
    method: 'get',
    params
  })
}

/**
 * 查询系统负责人
 * @param {*} params
 */
export function querySystemEmp(params) {
  return request({
    url: '/ppmSystemInfo/queryEmp',
    method: 'get',
    params
  })
}

/**
 * 查询项目名称
 * @param {*} params
 */
export function queryProjectList(params) {
  return request({
    url: '/ppmProjectInfo/queryList',
    method: 'get',
    params
  })
}

/**
 * 查询项目名称
 * @param {*} params
 */
export function queryApplyList(params) {
  return request({
    url: '/ppmProjectInfo/queryApplyList',
    method: 'get',
    params
  })
}

/**
 * 查询项目经理
 * @param {*} params
 */
export function queryProjectEmp(params) {
  return request({
    url: '/ppmProjectInfo/queryEmp',
    method: 'get',
    params
  })
}

/**
 * 查询部门名称
 * @param {*} params
 */
export function queryResourceDept(params) {
  return request({
    url: '/commDepartment/queryResourceDept',
    method: 'get',
    params
  })
}

/**
 * 查询小组
 * @param {*} params
 */
export function queryGroup(params) {
  return request({
    url: '/ppmDaTeamGroup/queryGroup',
    method: 'get',
    params
  })
}

/**
 * 查询行内负责人
 * @param {*} params
 */
export function queryEmployeeTree(params) {
  return request({
    url: `/commEmployee/queryEmployeeTree/${params}`,
    method: 'get'
  })
}

/**
 * 查询行内负责人
 * @param {*} params
 */
export function queryEmployeeList(params) {
  return request({
    url: `/commEmployee/queryEmployeeList/${params}`,
    method: 'get'
  })
}

/**
 * 根据角色进行查询面试官信息
 */
export function queryMsgList() {
  return request({
    url: `/commEmployee/queryMsgList`,
    method: 'get'
  })
}

/**
 * 根据面试官进行查询部门信息
 * @param {*} params
 * @returns
 */
export function queryDep(params) {
  return request({
    url: `/commDepartment/queryDep`,
    method: 'get',
    params
  })
}

/**
 * 查询行内负责人
 * @param {*} params
 */
export function queryEmployeeByOrgidTree(params) {
  return request({
    url: `/commEmployee/queryEmployeeByOrgidTree/${params}`,
    method: 'get'
  })
}

/**
 * 根据机构查询部门编号
 * @param {*} params
 */
export function queryDeptIdForCompId(params) {
  return request({
    url: `/commDepartment/queryDeptIdForCompId`,
    method: 'get',
    params
  })
}

/**
 * 查询系统名称
 * @param {*} params
 */
export function querySystemTree(params) {
  return request({
    url: `/aapSystem/querySystemTree/${params}`,
    method: 'get'

  })
}

/**
 * 查询考勤类型
 * @param {*} data
 */
export function queryKqTypeList(data) {
  return request({
    url: '/ppmKqDutyPlan/queryKqTypeList',
    method: 'get',
    data
  })
}

/**
 * 根据考勤类型查考勤时间
 * @param {*} params
 */
export function queryKqTime(params) {
  return request({
    url: '/ppmKqDutyPlan/queryKqTime',
    method: 'get',
    params
  })
}

/**
 * 获取代办工作项详情-单条
 * @param {*} params
 */
export function queryWorkItem(params) {
  return request({
    url: '/workflow/getTasksByInstanceId',
    method: 'get',
    params
  })
}

/**
 * 资料上传
 * @param {*} data
 */
export function upload(data) {
  return request({
    url: '/ppmOutsourcingApply/upload',
    method: 'post',
    data
  })
}

/**
 * 获取详情
 * @param {*} params
 */
export function queryDetail(params) {
  return request({
    url: '/ppmOutsourcingApply/queryDetail',
    method: 'get',
    params
  })
}

/**
 * 获取外包商
 * @param {*} data
 */
export function getWbCompany(data) {
  return request({
    url: '/ppmOutsourcingInfo/queryPager',
    method: 'post',
    data
  })
}

/**
 * 获取工号
 */
export function getJobid() {
  return request({
    url: '/ppmOutsourcingApply/getJobid',
    method: 'get'
  })
}

/**
 * 获取门禁编码
 */
export function getJobCardId(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/getEntranceCard',
    method: 'get',
    params
  })
}

/**
 * 卡牌预览
 * @param {*} params
 */
export function cardPreview(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/card/preview',
    method: 'get',
    params
  })
}

/**
 * 卡牌导出
 * @param {*} params
 */
export function cardDownload(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/card/download',
    method: 'get',
    responseType: 'blob',
    params
  })
}

/**
 * 查询系统负责人
 *  @param {*} params
 */
export function getSystemPeo(params) {
  return request({
    url: '/ppmProjectBuildSoft/queryMAS',
    method: 'get',
    params
  })
}

/**
 * 获取审批页面详情
 * @param {*} params
 */
export function approveDetail(params) {
  return request({
    url: '/ppmOutsourcingApply/queryDetail',
    method: 'get',
    params
  })
}

/**
 * 二级部经理审批
 * @param {*} data
 */
export function approve2(data) {
  return request({
    url: '/ppmOutsourcingApply/twoManagerAudit',
    method: 'post',
    data
  })
}
/**
 * 综合管理部人事信息审核
 * @param {*} data
 */
export function approve5(data) {
  return request({
    url: '/ppmOutsourcingApply/zhbrsAudit',
    method: 'post',
    data
  })
}

/**
 * 综合管理部风控征信审核
 * @param {*} data
 */
export function approve1(data) {
  return request({
    url: '/ppmOutsourcingApply/zhbfkzxAudit',
    method: 'post',
    data
  })
}

/**
 * 分管总经理审批
 * @param {*} data
 */
export function approve3(data) {
  return request({
    url: '/ppmOutsourcingApply/fgzjlAudit',
    method: 'post',
    data
  })
}

/**
 * 结算为人力总经理审批
 * @param {*} data
 */
export function approve6(data) {
  return request({
    url: '/ppmOutsourcingApply/rlzjlAudit',
    method: 'post',
    data
  })
}
/**
 * 历史单价
 * @param {*} data
 */
export function histPricePager(data) {
  return request({
    url: '/ppmOutsourcingPrice/queryPager',
    method: 'post',
    data
  })
}

/**
 * 采购项目
 * @param {*} data
 */
export function getProcurements() {
  // return request({
  //     url : '/xmZbxm/queryList',
  //     method : 'get',
  // })
  return null
}

/*
* 总办议案
*/
export function validMotion(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/validMotion',
    method: 'get',
    params
  })
}

/*
* 合同
*/
export function validContract(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/validContract',
    method: 'get',
    params
  })
}

/*
* 2023年3月7日
* 通过jobId查询流程id
*/
export function queryInstanceId(params) {
  return request({
    url: '/ppmOutsourcingAccessapply/queryInstanceId',
    method: 'get',
    params
  })
}

import request from '@/utils/request'
import { headers } from '@/api/api_header_conf'
import store from '@/store'

if (process.env.NODE_ENV === 'development') {
  headers.user = JSON.stringify({ "userId": "00000001", "brachId": "01000", "roles": [{ "roleId": "00000001", "roleName": "?????" }], "currentRoleId": "00000001" });
}

const URL_CUSTOMER_GROUP_PRE = '/flow';
const URL_PROMOTION_PRE = '/imapi/promotion/v1';
let BRANCH_ID = '';
let BRANCH_NAME = '';

function getBranchId() {
  // 直接返回缓存
  if (BRANCH_ID) return Promise.resolve(BRANCH_ID)
  // 从store获取相关数据
  const { branchId, branchLevel } = store.state.user;

  if (parseInt(branchLevel) <= 1) {
    BRANCH_ID = branchId;
    BRANCH_NAME = 'XX银行';
    return Promise.resolve(BRANCH_ID)
  }

  // const branchId = '07762';

  return request({
    headers,
    url: `${URL_CUSTOMER_GROUP_PRE}/second-org-trees?orgId=${branchId}`,
  }).then(res => res.data.data[0]).then(data => {
    BRANCH_NAME = data.name;
    BRANCH_ID = data.code;
    return BRANCH_ID;
  })
}

// api以一个对象的形式导出，主要是为了体现"命名空间"的思想，以简化命名，以求无注释。
export const budgetManageList = {
  list: function (params, isSubBranch) {
    return getBranchId().then(branchId => {
      if (!isSubBranch) {
        return request({
          headers,
          url: `${URL_PROMOTION_PRE}/budget/head-budgets`,
          params: params
        })
      } else {
        return request({
          headers,
          url: `${URL_PROMOTION_PRE}/budget/branch-budgets`,
          params: {
            size: params.size,
            page: params.page,
            orgId: branchId,
          }
        })
      }
    }).then(res => {
      const list = res.data.data;

      list.content = (list.content || []).map(n => new Budget(n))
      return list;
    })
  },
  //获取数据列表
  allBranchList: function (userId) {
    return request({
        headers,
        url: `${URL_CUSTOMER_GROUP_PRE}/list/` + userId,
      })
  },
  //申请流程表单提交
  apply: function (applyInst, data, optType = 1) { 
    const p = {
        processInstID : "",
        processInfo : "",
        processDefName : data.processDefName,
        processInstName : "测试报销流程001",
        processInstDesc : "报销流程",
         relaData : {
             users : data.relaData
       },
       expenseinfo : {
        expname : data.expname,
        expmoney : data.expmoney,
        reason : data.reason
      }
    }
      return request({
        headers,
        url: `${URL_CUSTOMER_GROUP_PRE}/start`,
        method: 'post',
        data: p,
        type : "json"
      })
  },
  
  //获取领导审批数据列表
  alldeptDataList: function (auditId) {
    return request({
        headers,
        url: `${URL_CUSTOMER_GROUP_PRE}/auditList/` + auditId,
      })
  },
  //获取审批数据详情
  auditDataDetails: function (processInstID) {
    return request({
        headers,
        url: `${URL_CUSTOMER_GROUP_PRE}/expense/` + processInstID,
      })
  },
  //通过/不通过表单提交
  applyAuditSubmit: function (applyInst, data, optType = 1) {
    
    let flowstatus = "1";
    const relaData = {
      users : data.relaData
    };
    if(data.opinion == '1'){
      const result = {
        result : "02"
      }
      relaData.result = "02";
      flowstatus = "2";
    }
    const p = {
        processInfo : "",
        processDefName : data.processDefName,
        processInstName : "测试报销流程001",
        processInstDesc : "报销流程",
        processInstID : data.processInstID,
        workItemID : data.workitemid,
        userID : "800017",
        flowstatus : flowstatus,
        relaData : relaData,
        opinion : opinion
    }
      return request({
        headers,
        url: `${URL_CUSTOMER_GROUP_PRE}/finishWork`,
        method: 'post',
        data: p,
        type : "json"
      })
  },

  exportFileUrl: function () {
    return `${process.env.BASE_API}${URL_PROMOTION_PRE}/budget/export`;
  },
  delBudget: function (budgetInst) {
    return request({
      headers,
      url: `${URL_PROMOTION_PRE}/budget/${budgetInst.budgetId}`,
      method: 'delete',
    })
  },
  applyList: function (params, isSubBranch) {
    return getBranchId().then(branchId => {
      return request({
        headers,
        url: `${URL_PROMOTION_PRE}/budget-apply`,
        params: {
          orgId: isSubBranch ? branchId : undefined
        }
      })
    }).then(res => {
      const list = res.data.data;

      list.content = (list.content || []).map(n => new Apply(n))
      return list;
    })
  },
  applyDetail: function (params) {
    return request({
      headers,
      url: `${URL_PROMOTION_PRE}/budget-apply/${params.applyId}?id=${params.applyId}`,
    }).then(res => new Apply(res.data.data))
  },
  
  editBudget: function (budgetInst, data, optType = 1) {
    return request({
      headers,
      url: `${URL_PROMOTION_PRE}/budget/${budgetInst.budgetId}`,
      method: 'put',
      data: budgetInst.genData(data, optType)
    }).then(res => res.data.data)
  },
  getBranchId() {
    return getBranchId();
  },
  getBranchName() {
    return getBranchId().then(() => BRANCH_NAME);
  },
  getLeftBudget: function () {
    return getBranchId().then(branchId => request({
      headers,
      url: `${URL_PROMOTION_PRE}/budget/${branchId}/budget`,
    })).then(res => res.data.data.budgetSurplus)
  },
}

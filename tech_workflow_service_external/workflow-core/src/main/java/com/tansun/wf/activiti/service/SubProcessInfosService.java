package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.model.AssignmentModel;
import com.tansun.wf.activiti.model.CarbonCopyConfig;
import com.tansun.wf.decision.ExecutionDB;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 子流程流程启动参数处理服务类
 */
@Service("subProcessInfos")
public class SubProcessInfosService {



    public int size() {
        List<ProcessInfo> list = (List<ProcessInfo>) WorkflowThreadLocalUtils.get(WorkflowConstants.SUB_PROCESS_INFOS);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.size();
        } else {
            return 0;
        }
    }

    public Object sendData(Execution execution) {
        if (!(execution instanceof ExecutionEntity)) {
            return "null";
        }

        List<ProcessInfo> list = (List<ProcessInfo>) WorkflowThreadLocalUtils.get(WorkflowConstants.SUB_PROCESS_INFOS);
        if (CollectionUtils.isEmpty(list)) {
            return "null";
        }

        ExecutionEntity entity = (ExecutionEntity) execution;
        Object loopCounterObj = entity.getVariable("loopCounter");
        ProcessInfo processInfo = null;
        if (loopCounterObj == null) {
            processInfo = list.get(0);
        } else {
            Integer loopCounter = (Integer) loopCounterObj;

            if ( loopCounter > list.size() - 1) {
                return "null";
            }else {
                processInfo = list.get(loopCounter);
            }
        }

        ExecutionEntity subProcessInstance = entity.getSubProcessInstance();
        String businessKey = processInfo.getBusinessKey();
        subProcessInstance.setBusinessKey(businessKey);

        // camunda-TODO
//        HistoricProcessInstanceEntity inCache = Context.getCommandContext().getDbSqlSession().getSqlSession().clearCache().findInCache(HistoricProcessInstanceEntity.class, subProcessInstance.getId());
//        if (inCache != null) {
//            inCache.setBusinessKey(businessKey);
//        }

        //覆盖子流程的待办扩展
        String taskExpandDataStr = (String) subProcessInstance.getVariable(WorkflowTaskExpandDataService._TASK_EXPAND_DATA);
        Map map = JSON.parseObject(taskExpandDataStr, Map.class);
        Map<String, String> taskExpandData = processInfo.getTaskExpandData() == null ? new HashMap<String, String>() : processInfo.getTaskExpandData();
        map.putAll(taskExpandData);
        subProcessInstance.setVariable(WorkflowTaskExpandDataService._TASK_EXPAND_DATA, JSON.toJSONString(map));

        //设置抄送信息
        CarbonCopyConfig config = new CarbonCopyConfig();
        config.setRoleCodes(processInfo.getCcRoles());
        config.setUserLoginNames(processInfo.getCcUsers());
        subProcessInstance.setVariable("_CARBON_COPY", JSON.toJSONString(config));

        //保存表单数据，给规则或数据源调用
        ExecutionDB db = new ExecutionDB(JSON.toJSONString(processInfo.getmPara()));
        db.setBlockDataIgnoreKeyRepeat(ExecutionDB._APPLY_DATA_BLOCK, processInfo.getFormData());
//        subProcessInstance.setVariable(ExecutionDB._RULE_DOMAIN_KEY, db.getJSONDB());


        //设置发起人部门deptCode
        subProcessInstance.setVariable(AssignmentModel.START_USER_DEPT_ID_KEY, processInfo.getStartDeptId());

        //设置发起人上级机构orgCode
        subProcessInstance.setVariable(AssignmentModel.START_USER_ORG_ID_KEY, processInfo.getStartOrgId());

        //设置发起人上级角色orgId
        String startRoleId = processInfo.getStartRoleId();
        subProcessInstance.setVariable(AssignmentModel.START_USER_ROLE_ID_KEY, startRoleId);

        //设置发起人上级机构parentOrgId
        subProcessInstance.setVariable(AssignmentModel.START_USER_PARENT_ORG_ID_KEY, processInfo.getStartParentOrgId());

        //设置发起人岗位
        List<String> startRoleIdList = processInfo.getStartRoleIdList();
        if (StringUtils.isNotBlank(startRoleId)) {
            startRoleIdList = startRoleIdList == null ? new ArrayList<String>() : startRoleIdList;
            if (!startRoleIdList.contains(startRoleId)) {
                startRoleIdList.add(startRoleId);
            }
        }

        subProcessInstance.setVariable(WorkflowConstants.START_ROLE_ID_LIST, startRoleIdList);

        //子流程发起人
        subProcessInstance.setVariable(WorkflowConstants.START_USER_ID, processInfo.getUserId());

        return JSON.toJSONString(processInfo);

    }
}


// if(processInfo.getmPara() == null){
//         processInfo.setmPara(new HashMap());
//         }
//         //2017-9-4 15:03:43 添加流程标题(动态值),用于待办列表展示
//         processInfo.setmPara(setInVariable("processTitle",processInfo.getProcessTitle(),processInfo.getmPara()));
//
//         //2017-11-8 12:14:39 设置待办扩展数据
//         Map<String, String> taskExpandData = processInfo.getTaskExpandData();
//        if(taskExpandData == null){
//        taskExpandData = new HashMap<String,String>();
//        }
//        String taskExpandDataJson = JSON.toJSONString(taskExpandData);
//        processInfo.setmPara(setInVariable(WorkflowTaskExpandDataService._TASK_EXPAND_DATA,taskExpandDataJson,processInfo.getmPara()));
//        //设置抄送信息
//        CarbonCopyConfig config = new CarbonCopyConfig();
//        config.setRoleCodes(processInfo.getCcRoles());
//        config.setUserLoginNames(processInfo.getCcUsers());
//        processInfo.setmPara(setInVariable("_CARBON_COPY",JSON.toJSONString(config),processInfo.getmPara()));
//
//        //保存表单数据，给规则或数据源调用
//        ExecutionDB db = new ExecutionDB(JSON.toJSONString(processInfo.getmPara()));
//        db.setBlockDataIgnoreKeyRepeat(ExecutionDB._APPLY_DATA_BLOCK,processInfo.getFormData());
//        processInfo.addToMPara(ExecutionDB._RULE_DOMAIN_KEY,db.getJSONDB());
//
//        //设置发起人部门deptCode
//        processInfo.getmPara().put(AssignmentModel.START_USER_DEPT_ID_KEY,processInfo.getStartDeptId());
//        //设置发起人上级机构orgCode
//        processInfo.getmPara().put(AssignmentModel.START_USER_ORG_ID_KEY,processInfo.getStartOrgId());
//        //设置发起人上级角色orgId
//        String startRoleId = processInfo.getStartRoleId();
//        processInfo.getmPara().put(AssignmentModel.START_USER_ROLE_ID_KEY, startRoleId);
//        //设置发起人上级机构parentOrgId
//        processInfo.getmPara().put(AssignmentModel.START_USER_PARENT_ORG_ID_KEY,processInfo.getStartParentOrgId());
//
//        //设置发起人岗位
//        List<String> startRoleIdList = processInfo.getStartRoleIdList();
//        if (StringUtils.isNotBlank(startRoleId)) {
//        startRoleIdList = startRoleIdList == null ? new ArrayList<String>() : startRoleIdList;
//        if (!startRoleIdList.contains(startRoleId)) {
//        startRoleIdList.add(startRoleId);
//        }
//        }
//
//        processInfo.getmPara().put(WorkflowConstants.START_ROLE_ID_LIST, startRoleIdList);
//
//        return this.workflowEngineService.startProcessInstanceByKey(processInfo);
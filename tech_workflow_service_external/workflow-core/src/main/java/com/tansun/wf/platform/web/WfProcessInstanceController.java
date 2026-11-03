package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.activiti.service.WorkflowTaskService;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import com.tansun.wf.platform.service.PlatformEditorService;
import com.tansun.wf.platform.service.WfActHiCommentService;
import com.tansun.workflow.model.*;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/27
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/instance")
public class WfProcessInstanceController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WorkFlowClientService workFlowClientService;

    @Autowired
    EditorServiceProxyService editorServiceProxyService;

    @Autowired
    WfActHiCommentService actHiCommentService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    PlatformEditorService platformEditorService;

    @Autowired
    WorkflowTaskService workflowTaskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;

    /**
     * 运行中的流程列表
     */
    @RequestMapping("/list")
    public WfResponseVO query(ProcessInfo processInfo, PageInfo<Map<String, Object>> page) {
        Map<String, Object> result = new HashMap<>();
        try {
            processInfo.setCurrentPage(String.valueOf(page.getPageNum()));
            processInfo.setPageSize(String.valueOf(page.getPageSize()));
            ProcessBean processBean = workFlowClientService.getCompletedProcess(processInfo);
            page.setTotal(Long.parseLong(processBean.getTotal()));
            page.setList(processBean.getResultList());
            result.put("grid", page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return WfResponseVO.successResponse(result);
    }


    /**
     * 获取任务干预弹出框的节点下拉框数据
     */
    @RequestMapping("/getWfPointData")
    public WfResponseVO getWfPointData(@RequestParam Map<String,Object> reqMap) {
        String processDefineId = MapUtils.getString(reqMap,"processDefineId");
        if(StringUtils.isBlank(processDefineId)){
            return WfResponseVO.errorResponse("流程定义id为空");
        }
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map> mapList= new ArrayList<>();
            TaskBean taskBean =workFlowClientService.getTaskDefinitions(processDefineId);
            if("0".equals(taskBean.getRetCode())){
                if(null!=taskBean.getTaskDefinitionList()){
                    mapList = taskBean.getTaskDefinitionList();
                }
            }
            result.put("WfPoint", mapList);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return WfResponseVO.successResponse(result);
    }

    //终止流程
    @RequestMapping("/wfTerminateProcess")
    public WfResponseVO wfTerminateProcess(ProcessInfo info) throws Exception {
        ProcessBean processBean = workFlowClientService.terminateProcess(info);
        if (!"0".equals(processBean.getRetCode())){
            return WfResponseVO.errorResponse(processBean.getRetMsg());
        }else {
            return WfResponseVO.successResponse("终止流程成功");
        }
    }
    /**
     * 根据流程实例ID批量终止流程
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/wfBatchTerminateProcess",method = RequestMethod.POST)
    public WfResponseVO batchterminateProcess(@RequestBody List<ProcessInfo> info) throws Exception {
        ProcessBean processBean = workFlowClientService.batchTerminateProcess(info);
        if (!"0".equals(processBean.getRetCode())){
            return WfResponseVO.errorResponse(processBean.getRetMsg());
        }else {
            return WfResponseVO.successResponse(processBean.getRetMsg());
        }
    }
    /**
     * 完成的流程
     * @return
     */
    @RequestMapping("/wfCompleteProcess")
    public WfResponseVO completeProcess(ProcessInfo processInfo,PageInfo<Map<String, Object>> page) {
        Map<String, Object> result = new HashMap<>(1);
        try {
            processInfo.setCurrentPage(String.valueOf(page.getPageNum()));
            processInfo.setPageSize(String.valueOf(page.getPageSize()));
            ProcessBean processBean = workFlowClientService.getCompletedProcess(processInfo);
            page.setTotal(Long.parseLong(processBean.getTotal()));
            page.setList(processBean.getResultList());
            result.put("grid", page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return WfResponseVO.successResponse(result);
    }


    /**
     * 流程挂起
     */
    @RequestMapping("/wfSuspend")
    public WfResponseVO wfSuspend(@RequestParam Map<String,Object> reqMap) {
        String processInstanceId = MapUtils.getString(reqMap,"processInstanceId");
        if(StringUtils.isBlank(processInstanceId)){
            return WfResponseVO.errorResponse("流程实例id为空");
        }
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if ("SUSPENDED".equals(historicProcessInstance.getState())){
            return WfResponseVO.errorResponse("当前流程已挂起");
        }
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcessInstanceId(processInstanceId);
        try {
            ProcessBean processBean =workFlowClientService.suspendProcess(processInfo);
            if(!"0".equals(processBean.getRetCode())){
                return WfResponseVO.errorResponse(processBean.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return WfResponseVO.successResponse("挂起流程成功");
    }
    /**
     * 流程恢复
     */
    @RequestMapping("/wfActivate")
    public WfResponseVO wfActivate(@RequestParam Map<String,Object> reqMap) {
        String processInstanceId = MapUtils.getString(reqMap,"processInstanceId");
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if ("ACTIVE".equals(historicProcessInstance.getState())){
            return WfResponseVO.errorResponse("当前流程已激活");
        }
        if(StringUtils.isBlank(processInstanceId)){
            return WfResponseVO.errorResponse("流程实例id为空");
        }
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcessInstanceId(processInstanceId);
        try {
            ProcessBean processBean =workFlowClientService.activateProcess(processInfo);
            if(!"0".equals(processBean.getRetCode())){
                return WfResponseVO.errorResponse(processBean.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return WfResponseVO.successResponse("流程激活成功");
    }

    /**
     * 保存审批意见
     */
    @RequestMapping("/saveApproveSuggestion")
    public WfResponseVO saveApproveSuggestion(String taskId,String processInstanceId,String comment,String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(null!=taskId&&!"".equals(taskId)){
            try {
                actHiCommentService.saveApproveSuggestionInfo(taskId, processInstanceId, comment);
                result.put("retCode", "0");
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                return WfResponseVO.errorResponse(e.getMessage());
            }
        }
        return WfResponseVO.successResponse(result);
    }

    /**
     * 任务审批维护下修改任务的候选用户，修改意见和处理人
     */
    @RequestMapping("/updateCandidateUser")
    @Transactional
    public WfResponseVO updateCandidateUser(String taskId,String processInstanceId,String[] userList,String comment,String assignee) {
        if(userList != null && userList.length != 0){
            actHiCommentService.updateCandidateUser(taskId, processInstanceId,userList);
        }
        if(StringUtils.isNotBlank(comment)){
            actHiCommentService.saveApproveSuggestionInfo(taskId, processInstanceId, comment);
        }
        if(StringUtils.isNotBlank(assignee)){
            // 修改历史任务 和 运行任务
            workflowTaskService.updateHiTaskAssignee(taskId,assignee);
            workflowTaskService.updateRuTaskAssignee(taskId,assignee);
        }
        return WfResponseVO.successResponse("修改成功");
    }

    /**
     * 获取任务处理人和候选用户
     */
    @RequestMapping("/candidateUserList")
    public WfResponseVO candidateUserList(@RequestParam Map<String,Object> reqMap) {
        ActRuIdentityLinkQuery actRuIdentityLinkQuery = new ActRuIdentityLinkQuery();
        String taskId = MapUtils.getString(reqMap, "taskId");
        actRuIdentityLinkQuery.setTaskId(taskId);
        String assignee = null;
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            assignee = task.getAssignee();
        }
        List<ActRuIdentitylinkEntity> list = actHiCommentService.findCandidateUserList(actRuIdentityLinkQuery);
        PageInfo<ActRuIdentitylinkEntity> page = new PageInfo<>() ;
        if(list != null && list.size() > 0){
            page = new PageInfo<>(list);
            page.setTotal(list.size());
        }
        Map<String,Object> result = new HashMap(2);
        result.put("assignee", assignee);
        result.put("candidateUserList", page);
        return WfResponseVO.successResponse(result);
    }


    /**
     * 删除（终止）
     */
    @RequestMapping("/wfDeleteInfo")
    public WfResponseVO wfDeleteInfo(@RequestParam Map<String,Object> reqMap) {
        String reason = "";
        String processInstanceId;
        processInstanceId = MapUtils.getString(reqMap,"processInstanceId");
        reason = MapUtils.getString(reqMap,"reason");
        if(StringUtils.isBlank(processInstanceId)){
            return WfResponseVO.errorResponse("流程实例id为空");
        }
        try {
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setProcessInstanceId(processInstanceId);
            workFlowClientService.deleteProcessInstance(processInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return  WfResponseVO .successResponse("删除成功");
    }

    /**
     * 删除（终止）
     */
    @RequestMapping("/wfBatchDeleteInfo")
    public WfResponseVO wfBatchDeleteInfo(@RequestParam Map<String,Object> reqMap) {
        String reason = "";
        String processInstanceId;
        processInstanceId = MapUtils.getString(reqMap,"processInstanceId");
        reason = MapUtils.getString(reqMap,"reason");
        if(StringUtils.isBlank(processInstanceId)){
            return WfResponseVO.errorResponse("流程实例id为空");
        }
        try {
            String[] processInstanceIds = processInstanceId.split(",");
            for (String processInstanceId1:processInstanceIds){
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setProcessInstanceId(processInstanceId1);
                workFlowClientService.deleteProcessInstance(processInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return  WfResponseVO .successResponse("删除成功");
    }


    /**
     *  获取用户列表
     * @param reqMap
     * @param pageInfo
     * @return
     */
    @RequestMapping(value = "/userList")
    @ResponseBody
    public WfResponseVO getUsers(@RequestParam Map<String,Object> reqMap,PageInfo pageInfo){
        EditorInfo editorInfo = new EditorInfo();
        editorInfo.setCurrentPage(pageInfo.getPageNum());
        editorInfo.setPageSize(pageInfo.getPageSize());
        String loginName = MapUtils.getString(reqMap,"loginName");
        if(StringUtils.isNotBlank(loginName)){
            editorInfo.setUserLoginName(loginName);
        }
        EditorBean editorBean = editorServiceProxyService.findUsersList(editorInfo);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> pageInfoObject = new HashMap<>();
        pageInfoObject.put("list",editorBean.getUserList());
        pageInfoObject.put("hasNextPage",editorBean.isHasNextPage());
        pageInfoObject.put("total",editorBean.getTotal());
        pageInfoObject.put("pageNum",pageInfo.getPageNum());
        pageInfoObject.put("pageSize",pageInfo.getPageSize());
        map.put("grid",pageInfoObject);
        return WfResponseVO.successResponse(map);
    }

    /**
     * 获取审批维护页面的表格数据
     */
    @RequestMapping(value = "/getRunningApproveEditTable")
    public WfResponseVO getRunningApproveEditTable(String processDefineId, String processInstanceId,PageInfo<Map> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        ProcessInfo processInfo = new ProcessInfo();
        if(null!=processDefineId&&!"".equals(processDefineId)){
            processInfo.setProcessDefineId(processDefineId);
        }
        if(null!=processInstanceId&&!"".equals(processInstanceId)){
            processInfo.setProcessInstanceId(processInstanceId);
        }
        processInfo.setCurrentPage(Integer.toString(page.getPageNum()));
        processInfo.setPageSize(Integer.toString(page.getPageSize()));
        processInfo.setIsShowSubProcess("Y");
        //查询流程轨迹
        List<Map> mapList= new ArrayList<Map>();
        try {
            TaskBean taskBean = workFlowClientService.queryProcessTrack(processInfo);
            if("0".equals(taskBean.getRetCode())){
                if(null!=taskBean.getTaskList()){
                    mapList = taskBean.getTaskList();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        // 对list加一个任务完成字段，根据结束时间是否为空来判断,原本是要在前端做的
        for(Map<String,Object> map : mapList){
            if(map.get("endTime") == null){
                map.put("finish","未完成");
                map.put("taskType","待办");
            } else {
                if(StringUtils.equals("beReBack",(String)map.get("deleteReason"))){
                    map.put("finish","被撤回");
                }else{
                    map.put("finish","已完成");
                }
                map.put("taskType","已办");
            }
        }
        page.setList(mapList);
        result.put("grid", page);
        return WfResponseVO.successResponse(result);
    }
    /**
     * 保存任务干预节点、处理人信息
     */
    @ResponseBody
    @RequestMapping(value = "/saveWfPointInfo")
    public WfResponseVO savewfPointInfo(String processInstanceId,String nextTaskId,String userId) {
        Map<String, Object> result = new HashMap<>(1);
        if(nextTaskId == null || "".equals(nextTaskId)){
            return WfResponseVO.errorResponse("请选择节点信息");
        }
        if(null != processInstanceId && !"".equals(processInstanceId)){
            try {
                //获取taskList（包含多个taskId）
                TaskBean taskBean = new TaskBean();
                taskBean.setProcessInstanceId(processInstanceId);
                taskBean.setNextTaskId(nextTaskId);
                taskBean.setUserId(userId);
                //自由流
                TaskBean tb = workFlowClientService.jumpFlowTask(taskBean);
                if("0".equals(tb.getRetCode())){
                    result.put("retCode", tb.getRetCode());
                }else{
                    result.put("retMsg", tb.getRetMsg());
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                return WfResponseVO.errorResponse(e.getMessage());
            }
        }
        return WfResponseVO.successResponse(result);
    }

    @ResponseBody
    @RequestMapping(value = "/findProcessVariable")
    public WfResponseVO findProcessVariable(String processInstanceId) {
        Map<String, Object> result = new HashMap<>();
        if(null != processInstanceId && !"".equals(processInstanceId)){
            try {
                //获取taskList（包含多个taskId）
                Map<String, Object> variables = workflowTaskService.findVariableByInstanceId(processInstanceId);
                for(Map.Entry<String,Object> entry : variables.entrySet()){
                    if(entry.getValue() != null){
                        result.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
                    }else {
                        result.put(entry.getKey(),"none");
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                return WfResponseVO.errorResponse(e.getMessage());
            }
        }
        return WfResponseVO.successResponse(result,"查询成功");
    }
    @ResponseBody
    @RequestMapping(value = "/saveProcessVariable")
    public WfResponseVO saveProcessVariable( @RequestBody Map<String,Object> reqMap) {
        String processInstanceId = MapUtils.getString(reqMap,"processInstanceId");
        reqMap.remove("processInstanceId");

        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(historicProcessInstance ==null){
            return WfResponseVO.errorResponse("对不起，该流程不存在");
        }
        if(historicProcessInstance.getEndTime()!=null){
            return WfResponseVO.errorResponse("对不起，该流程已结束");
        }
        Iterator<String> iterator = reqMap.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            Object variable = runtimeService.getVariable(processInstanceId, key);
            if(variable!=null){
                Class<?> aClass = variable.getClass();
                Object result = JSON.parseObject(reqMap.get(key).toString(), aClass);
                reqMap.put(key, result);
            }
        }
        runtimeService.setVariables(processInstanceId,reqMap);
        return WfResponseVO.successResponse("保存成功");
    }

}
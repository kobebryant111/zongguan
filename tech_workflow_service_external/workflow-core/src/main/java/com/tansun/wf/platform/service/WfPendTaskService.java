package com.tansun.wf.platform.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.model.TaskExpandDataEntity;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.activiti.service.WorkflowTaskExpandDataService;
import com.tansun.wf.activiti.service.WorkflowTaskExpandPlanService;
import com.tansun.wf.activiti.util.ResultUtil;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.mapper.WfClassifyMapper;
import com.tansun.wf.platform.mapper.WfTaskExpandDataMapper;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.model.WfClassifyEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import com.tansun.workflow.api.IWorkflowService;
import com.tansun.workflow.model.*;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/21
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfPendTaskService {

    public static final String FAILED = "1";

    @Autowired
    WfClassifyMapper wfClassifyMapper;

    @Autowired
    private WorkFlowClientService workFlowClientService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private WfTaskSelectUserService wfTaskSelectUserService;

    @Autowired
    private WfTaskExpandDataMapper wfTaskExpandDataMapper;

    @Autowired
    private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;

    @Autowired
    private WorkflowTaskExpandDataService workflowTaskExpandDataService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IWorkflowService workflowService;

    @Autowired
    private WfActHiCommentService wfActHiCommentService;

    @Autowired
    private EditorServiceProxyService editorServiceProxyService;


    public PageInfo<Map> findWfPendOrgTaskPage(TaskInfo taskInfo,PageInfo pageInfo) throws Exception {
        //判断分页信息是否为空，为0则默认查询所有列表
        int pageNum = pageInfo.getPageNum() == 0  ? 1:pageInfo.getPageNum();
        int pageSize = pageInfo.getPageSize() == 0  ? 999: pageInfo.getPageSize();
        taskInfo.setCurrentPage(String.valueOf(pageNum));
        taskInfo.setPageSize(String.valueOf(pageSize));
        taskInfo.setState("1");
        WfClassifyEntity wfClassify = new WfClassifyEntity();
        if(taskInfo.getWfClassifyCode() != null){
             wfClassify = wfClassifyMapper.findClassifyByCode(taskInfo.getWfClassifyCode());
            // 设置机构代码
             if(wfClassify != null && StringUtils.isNotBlank(wfClassify.getCode())){
                 taskInfo.setWfClassifyCode(wfClassify.getCode());
             }
        }
        if(StringUtils.isBlank(taskInfo.getTaskPoolStatus())){
            // 默认无任务池概念
            taskInfo.setTaskPoolStatus("0");
        }
        TaskBean taskBean = workFlowClientService.findPendOrgTaskList(taskInfo);

        List<Map> taskList = taskBean.getTaskList();
        if(taskList != null) {
            for(Map task:taskList){
                if (StringUtils.isNotBlank(wfClassify.getCode())) {
                    task.put("isShow", "Y");
                    task.put("plans", workflowTaskExpandPlanService.getExpandPlanByClassifyCode(wfClassify.getCode(),taskInfo.getTenantId()));
                }else {
                    task.put("isShow", "N");
                }
                task.put("processList", findProcessList());
            }
        }
        PageInfo<Map> resultData = new PageInfo<>(taskList);
        resultData.setPageSize(pageSize);
        resultData.setTotal(StringUtils.isNumeric(taskBean.getTotal())?Long.parseLong(taskBean.getTotal()):0);
        return resultData;
    }

    public PageInfo<Map> findWfPendOrgHiTaskPage(TaskInfo taskInfo,PageInfo pageInfo) throws Exception {
        //判断分页信息是否为空，为0则默认查询所有列表
        int pageSize = pageInfo.getPageSize() == 0  ? 999: pageInfo.getPageSize();
        int pageNum = pageInfo.getPageNum() == 0  ? 1:pageInfo.getPageNum();
        taskInfo.setCurrentPage(String.valueOf(pageNum));
        taskInfo.setPageSize(String.valueOf(pageSize));
        WfClassifyEntity wfClassify = new WfClassifyEntity();
        if(taskInfo.getWfClassifyCode() != null){
            wfClassify = wfClassifyMapper.findClassifyByCode(taskInfo.getWfClassifyCode());
            // 设置机构代码
            if(wfClassify != null && StringUtils.isNotBlank(wfClassify.getCode())){
                taskInfo.setWfClassifyCode(wfClassify.getCode());
            }
        }
        if(StringUtils.isBlank(taskInfo.getTaskPoolStatus())){
            // 默认无任务池概念
            taskInfo.setTaskPoolStatus("0");
        }
        TaskBean taskBean = workflowService.findOrgTaskList(taskInfo);

        List<Map> taskList = taskBean.getTaskList();
        if(CollectionUtils.isNotEmpty(taskList)) {
            if(!taskInfo.isQueryAllTask()) {
                List<ActRuIdentitylinkEntity> postList = new ArrayList<>();
                ActRuIdentityLinkQuery actRuIdentityLinkQuery = new ActRuIdentityLinkQuery();
                actRuIdentityLinkQuery.setTenantId(taskInfo.getTenantId());
                List<String> taskIds = new ArrayList<>();
                for (Map map : taskList) {
                    String taskId = MapUtils.getString(map, "taskId");
                    taskIds.add(taskId);
                }
                actRuIdentityLinkQuery.setTaskIds(taskIds);
                postList = wfActHiCommentService.findCandidateUserList(actRuIdentityLinkQuery);
                for (Map map : taskList) {
                    String taskId = MapUtils.getString(map, "taskId");
                    map.put("currentTaskPost", new ArrayList<>());
                    for (ActRuIdentitylinkEntity actRuIdentitylinkEntity : postList) {
                        if (StringUtils.equals(taskId, actRuIdentitylinkEntity.getTaskId())) {
                            List currentTaskPost = (List) map.get("currentTaskPost");
                            currentTaskPost.add(actRuIdentitylinkEntity);
                        }
                    }
                }
            }
            for (Map map : taskList) {

                map.remove("startTime");
                if (StringUtils.isNotBlank(wfClassify.getCode())) {
                    map.put("isShow", "Y");
                    map.put("plans", workflowTaskExpandPlanService.getExpandPlanByClassifyCode(wfClassify.getCode(),taskInfo.getTenantId()));
                }else {
                    map.put("isShow", "N");
                }

                // 处理人
                String assignee = MapUtils.getString(map, "assignee");
                List<ActRuIdentitylinkEntity> currentTaskPost = (List)map.get("currentTaskPost");
                if(StringUtils.isBlank(assignee)){
                    Set<String> userIds = new HashSet<>();
                    for (ActRuIdentitylinkEntity identityLinkEntity : currentTaskPost) {
                        EditorInfo editorInfo = new EditorInfo();
                        editorInfo.setTenantId(identityLinkEntity.getTenantId());
                        editorInfo.setGroupId(identityLinkEntity.getGroupId());
                        editorInfo.setType(identityLinkEntity.getType());

                        if("user".equals(identityLinkEntity.getType())){
                            userIds.add(identityLinkEntity.getUserId());
                        }

                        if("role".equals(identityLinkEntity.getType()) && StringUtils.isNotBlank(identityLinkEntity.getRoleCode())){
                            List<String> roleCodes = new ArrayList<>();
                            roleCodes.add(identityLinkEntity.getRoleCode());
                            editorInfo.setRoleCodes(roleCodes);
                        }

                        if("org".equals(identityLinkEntity.getType()) && StringUtils.isNotBlank(identityLinkEntity.getOrgCode())){
                            List<String> orgCodes = new ArrayList<>();
                            orgCodes.add(identityLinkEntity.getOrgCode());
                            editorInfo.setOrgCodes(orgCodes);
                        }

                        if("dept".equals(identityLinkEntity.getType()) && StringUtils.isNotBlank(identityLinkEntity.getDeptCode())){
                            List<String> deptCodes = new ArrayList<>();
                            deptCodes.add(identityLinkEntity.getDeptCode());
                            editorInfo.setDeptCodes(deptCodes);
                        }

                        if("post".equals(identityLinkEntity.getType())){
                            List<String> roleCodes = new ArrayList<>();
                            roleCodes.add(identityLinkEntity.getRoleCode());
                            editorInfo.setRoleCodes(roleCodes);

                            if(StringUtils.isNotBlank(identityLinkEntity.getOrgCode())){
                                List<String> orgCodes = new ArrayList<>();
                                orgCodes.add(identityLinkEntity.getOrgCode());
                                editorInfo.setOrgCodes(orgCodes);
                            }

                            if(StringUtils.isNotBlank(identityLinkEntity.getDeptCode())){
                                List<String> deptCodes = new ArrayList<>();
                                deptCodes.add(identityLinkEntity.getDeptCode());
                                editorInfo.setDeptCodes(deptCodes);
                            }
                        }

                        if (StringUtils.isNotBlank(identityLinkEntity.getType()) && !"user".equals(identityLinkEntity.getType())) {
                            EditorBean editorBean = editorServiceProxyService.findUsersList(editorInfo);
                            if(editorBean!=null && CollectionUtils.isNotEmpty(editorBean.getUserList())){
                                List<Map<String, Object>> userList = editorBean.getUserList();
                                for (Map<String, Object> userMap : userList) {
                                    String userLoginName = MapUtils.getString(userMap, "userLoginName");
                                    userIds.add(userLoginName);
                                }
                            }
                        }
                        map.put("assignee", StringUtils.join(userIds,","));
                    }
                }
            }

        }
        PageInfo<Map> resultData = new PageInfo<>(taskList);
        resultData.setPageSize(pageSize);
        resultData.setTotal(StringUtils.isNumeric(taskBean.getTotal())?Long.parseLong(taskBean.getTotal()):0);
        return resultData;
    }
    
    public PageInfo<Map> findWfPendTaskPage(TaskInfo taskInfo,PageInfo pageInfo) throws Exception {
        //判断分页信息是否为空，为0则默认查询所有列表
        int pageNum = pageInfo.getPageNum() == 0  ? 1:pageInfo.getPageNum();
        int pageSize = pageInfo.getPageSize() == 0  ? 999: pageInfo.getPageSize();
        taskInfo.setCurrentPage(String.valueOf(pageNum));
        taskInfo.setPageSize(String.valueOf(pageSize));
        taskInfo.setState("1");
        WfClassifyEntity wfClassify = new WfClassifyEntity();
        if(taskInfo.getWfClassifyCode() != null){
             wfClassify = wfClassifyMapper.findClassifyByCode(taskInfo.getWfClassifyCode());
            // 设置机构代码
             if(wfClassify != null && StringUtils.isNotBlank(wfClassify.getCode())){
                 taskInfo.setWfClassifyCode(wfClassify.getCode());
             }
        }
        if(StringUtils.isBlank(taskInfo.getTaskPoolStatus())){
            // 默认无任务池概念
            taskInfo.setTaskPoolStatus("0");
        }
//        taskInfo.setQueryAllTask(true);
        TaskBean taskBean = workFlowClientService.findPendOrgTaskList(taskInfo);

        List<Map> taskList = taskBean.getTaskList();
        if(taskList != null) {
            for(Map task:taskList){
                if (StringUtils.isNotBlank(wfClassify.getCode())) {
                    task.put("isShow", "Y");
                    task.put("plans", workflowTaskExpandPlanService.getExpandPlanByClassifyCode(wfClassify.getCode(),taskInfo.getTenantId()));
                }else {
                    task.put("isShow", "N");
                }
                task.put("processList", findProcessList());
            }
        }
        PageInfo<Map> resultData = new PageInfo<>(taskList);
        resultData.setPageSize(pageSize);
        resultData.setTotal(StringUtils.isNumeric(taskBean.getTotal())?Long.parseLong(taskBean.getTotal()):0);
        return resultData;
    }

    public TaskBean executeOperation(Map<String, Object> params) throws Exception{
        String button = MapUtils.getString(params, "button");

        TaskBean retTaskBean = new TaskBean();

        if ("nextTask".equals(button)) {
            TaskBean taskBean = toWorkflowParam(params, TaskBean.class);
            taskBean.setTaskId(MapUtils.getString(params, "taskId"));
//            taskBean.setActivityId(null);
            taskBean.setTaskExpandData(taskBean.getmPara());
            taskBean.setFormData(taskBean.getmPara());
            Map map = taskBean.getmPara();
            if(map.get("users")!=null&& map.get("users") instanceof JSONArray){
                JSONArray users = (JSONArray) map.get("users");
                List list = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(users)){
                    Iterator<Object> iterator = users.iterator();
                    while(iterator.hasNext()){
                        list.add(iterator.next());
                    }
                }
                map.put("users", list);
            }
            taskBean.setProcessVariableMap(map);
            retTaskBean = workFlowClientService.submitTask(taskBean);
            return retTaskBean;
        }
        if ("relay".equals(button)) {
            TaskInfo taskInfo = toWorkflowParam(params, TaskInfo.class);
            String tUserId = (String) JSONObject.parseObject(params.get("paramStr").toString()).get("tUserId");
            taskInfo.settUserId(tUserId);
            retTaskBean = workFlowClientService.transferTask(taskInfo);
            return retTaskBean;
        }

        if ("terminateProcess".equals(button)) {
            ProcessInfo processInfo = toWorkflowParam(params, ProcessInfo.class);
            ProcessBean terminateProcess = workFlowClientService.terminateProcess(processInfo);
            retTaskBean.setRetCode(terminateProcess.getRetCode());
            retTaskBean.setRetMsg(terminateProcess.getRetMsg());
            return retTaskBean;
        }
        if ("suspendProcess".equals(button)) {
            ProcessInfo processInfo = toWorkflowParam(params, ProcessInfo.class);
            ProcessBean suspendProcess = workFlowClientService.suspendProcess(processInfo);
            retTaskBean.setRetCode(suspendProcess.getRetCode());
            retTaskBean.setRetMsg(suspendProcess.getRetMsg());
            return retTaskBean;
        }
        if ("activateProcess".equals(button)) {
            ProcessInfo processInfo = toWorkflowParam(params, ProcessInfo.class);
            ProcessBean suspendProcess = workFlowClientService.activateProcess(processInfo);
            retTaskBean.setRetCode(suspendProcess.getRetCode());
            retTaskBean.setRetMsg(suspendProcess.getRetMsg());
            return retTaskBean;
        }
        if ("releaseTask".equals(button)) {
            TaskInfo taskInfo = toWorkflowParam(params, TaskInfo.class);
            retTaskBean = workFlowClientService.releaseTask(taskInfo);
            return retTaskBean;
        }
        if ("firstTask".equals(button)) {
            TaskBean taskBean = toWorkflowParam(params, TaskBean.class);
            retTaskBean = workFlowClientService.backToFirstUserTask(taskBean);
            return retTaskBean;
        }
        if ("lastTask".equals(button)) {
            TaskBean taskBean = toWorkflowParam(params, TaskBean.class);
            retTaskBean = workFlowClientService.backPreTask(taskBean);
            return retTaskBean;
        }
        //回退指定节点
        if ("appointTask".equals(button)) {
            TaskBean taskBean = toWorkflowParam(params, TaskBean.class);
            return workFlowClientService.backTask(taskBean);
        }
        //取回任务
        if ("getBack".equals(button)) {
            TaskBean taskBean = toWorkflowParam(params, TaskBean.class);
            return workFlowClientService.rebackTask(taskBean);
        }
        ResultUtil.setReturnCode(retTaskBean, FAILED, "执行失败，无法执行"+button+"操作");
        return retTaskBean;
    }
    private  <T> T toWorkflowParam(Map<String, Object> params, Class<T> clazz) {
        String paramStr = MapUtils.getString(params, "paramStr");
        String userId = MapUtils.getString(params, "userId");

        T t = JSON.parseObject(paramStr, clazz);
        if(t instanceof TaskBean){
            TaskBean taskBean = (TaskBean) t;
            taskBean.setUserId(userId);

        }else if(t instanceof TaskInfo){
            TaskInfo taskInfo = (TaskInfo) t;
            taskInfo.setUserId(userId);
        }else if(t instanceof ProcessInfo){
            ProcessInfo processInfo = (ProcessInfo) t;
            processInfo.setUserId(userId);
        }
        return t;
    }
    public List<Map<Object,Object>> findProcessList(){
        ProcessBean bean = workFlowClientService.processDefinitionList(new ProcessInfo());
        List<Map<Object,Object>> list = new ArrayList<>();
        if(bean != null && bean.getResultList() != null && bean.getResultList().size() > 0){
            for(Map<String, Object> rmap : bean.getResultList()){
                Map<Object,Object> map = new HashMap<Object,Object>();
                if(rmap.get("processDefineName")!=null){
                    map.put("processDefineId", rmap.get("processDefineId"));
                    map.put("processDefineName", rmap.get("processDefineName"));
                    list.add(map);
                }
            }
        }
        return list;
    }


    public TaskBean claimWorkInfo(TaskInfo taskInfo) {
        TaskBean taskBean=new TaskBean();
        try {
            //判断该任务是否已被领取
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskInfo.getTaskId()).singleResult();
            if (historicTaskInstance.getAssignee()!=null){
                taskBean.setRetCode("1");
                taskBean.setRetMsg("任务已被领取");
                return taskBean;
            }
            String[] split = taskInfo.getUserId().split(",");
            if (split.length==1){
                //有多个参与者领取
                taskService.claim(taskInfo.getTaskId(),taskInfo.getUserId());
                //删除旧的数据
                wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
                wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),split);
                taskBean.setRetCode("0");
                taskBean.setRetMsg("任务领取成功");
                return taskBean;
            }else {
                //先删除旧的
                wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
                //保存新的
                wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),split);
                taskBean.setRetCode("0");
                taskBean.setRetMsg("任务领取成功");
                return taskBean;
            }
        }catch (Exception e){
            e.printStackTrace();
            taskBean.setRetCode("0");
            taskBean.setRetMsg("任务领取失败");
            return taskBean;
        }
    }


    public Boolean reAssignmentTask(TaskInfo taskInfo) {
        try {
            String[] split = taskInfo.getUserId().split(",");
            if (split.length==1){
                //有多个参与者领取
                wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
                taskService.setAssignee(taskInfo.getTaskId(),taskInfo.getUserId());
                wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),split);
                return true;
            }else {
                //先删除旧的
                wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
                taskService.setAssignee(taskInfo.getTaskId(),null);
                //保存新的
                wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),split);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public TaskBean editInfoTask(TaskInfo taskInfo) {
        TaskBean taskBean=new TaskBean();
        try {
            TaskExpandDataEntity taskExpandDataEntity=new TaskExpandDataEntity();
            taskExpandDataEntity.setFormScene(taskInfo.getFormScence());
            taskExpandDataEntity.setTaskName(taskInfo.getTaskName());
            taskExpandDataEntity.setTaskId(taskInfo.getTaskId());
            //更新扩展字段
            workflowTaskExpandDataService.updateDate(taskExpandDataEntity);
            //查询当前任务
            Task task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
            task.setName(taskInfo.getTaskName());
            taskService.saveTask(task);
            taskBean.setRetCode("0");
            taskBean.setRetMsg("编辑工作项成功");
            return taskBean;
        }catch (Exception e){
            e.printStackTrace();
            taskBean.setRetCode("1");
            taskBean.setRetMsg("编辑工作项失败");
            return taskBean;
        }

    }
}

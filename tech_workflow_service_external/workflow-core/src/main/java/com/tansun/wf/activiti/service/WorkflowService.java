package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tansun.wf.activiti.annotation.WorkflowIdempotent;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.model.AssignmentModel;
import com.tansun.wf.activiti.util.DateUtil;
import com.tansun.wf.activiti.util.ResultUtil;
import com.tansun.wf.platform.mapper.WfJobMapper;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.model.WfJobEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import com.tansun.wf.platform.service.CacheQueueService;
import com.tansun.wf.platform.service.WfActHiCommentService;
import com.tansun.wf.platform.service.WfTaskSelectUserService;
import com.tansun.workflow.api.IWorkflowService;
import com.tansun.workflow.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.IdentityLinkEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工作流服务提供类
 */
@Service
public class WorkflowService implements IWorkflowService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SUCCEED = WorkflowConstants.SUCCEED;

    private static final String FAILED = WorkflowConstants.FAILED;
    //静态全局共享
    private static ConcurrentHashMap<String,String> chm = new ConcurrentHashMap<String,String>();

    @Autowired
    private   IWorkflowEngineService  workflowEngineService;


    @Autowired
    private WfJobMapper wfJobMapper;
    @Autowired
    private WorkflowModelService workflowModelService;

    @Autowired
    private WfTaskSelectUserService wfTaskSelectUserService;

    @Autowired
    private WorkflowProcessDefineService workflowProcessDefineService;

    @Autowired
    private WorkflowProcessInstanceService workflowProcessInstanceService;

    @Autowired
    private WorkflowManagementService workflowManagementService;

    @Autowired
    private WorkflowTaskService workflowTaskService;

    @Autowired
    private WorkflowTaskExpandDataService workflowTaskExpandDataService;

    @Autowired
    private TaskService	taskService;
    @Autowired
    private WorkflowUserService	workflowUserService;

    @Autowired
	private WorkflowCarbonCopyService workflowCarbonCopyService;
    
    @Autowired
    private WorkflowHiTaskPoolExecuteDataService workflowHiTaskPoolExecuteDataService;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private WorkflowClassifyService workflowClassifyService;

    @Autowired
    private WorkflowLeaveTransferService workflowLeaveTransferService;

    @Autowired
    private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private WfActHiCommentService wfActHiCommentService;

    @Autowired
    private EditorServiceProxyService editorServiceProxyService;

    @Autowired
    private CacheQueueService cacheQueueService;

    @WorkflowIdempotent
    public ProcessBean startProcess(ProcessInfo processInfo)  {
        ProcessBean processBean = new ProcessBean();
        String startUserId = processInfo.getStartUserId();

        try {
            if(processInfo.getmPara() == null){
             	processInfo.setmPara(new HashMap());
            }

            // 发起人id
            if(StringUtils.isNotBlank(startUserId)){
                identityService.setAuthenticatedUserId(startUserId);
            }

            //2017-9-4 15:03:43 添加流程标题(动态值),用于待办列表展示
            processInfo.setmPara(setInVariable("processTitle",processInfo.getProcessTitle(),processInfo.getmPara()));

            //2017-11-8 12:14:39 设置待办扩展数据
            Map<String, String> taskExpandData = processInfo.getTaskExpandData();
            if(taskExpandData == null){
            	taskExpandData = new HashMap<String,String>();
    		}
            String taskExpandDataJson = JSON.toJSONString(taskExpandData);
            processInfo.setmPara(setInVariable(WorkflowTaskExpandDataService._TASK_EXPAND_DATA,taskExpandDataJson,processInfo.getmPara()));

            if (processInfo.getNextPost() != null) {
            	processInfo.getmPara().put("nextPost",processInfo.getNextPost());
            }else {
            	processInfo.getmPara().put("nextPost",null);
            }

            ProcessBean result = this.workflowEngineService.startProcessInstanceByKey(processInfo);

            return result;

        } finally {
            identityService.setAuthenticatedUserId(null);
        }

    }

    private Map<String, Object> setInVariable(String key, String value, Map<String,Object> variables){
        if(StringUtils.isBlank(value)){
            return variables;
        }
        if (variables == null){
            variables = new HashedMap();
        }
        variables.put(key,value);
        return variables;
    }

    @WorkflowIdempotent
    public ProcessBean startAndSubmitProcess(ProcessInfo processInfo){
        ProcessBean  processBean  = new  ProcessBean();

        try {
            if(processInfo.getmPara() == null){
            	processInfo.setmPara(new HashMap());
            }
            if (StringUtils.isNotBlank(processInfo.getStartUserId())) {
            	identityService.setAuthenticatedUserId(processInfo.getStartUserId());
            }

            //2017-9-4 15:03:43 添加流程标题(动态值),用于待办列表展示
            processInfo.setmPara(setInVariable("processTitle",processInfo.getProcessTitle(),processInfo.getmPara()));

            //2017-11-8 12:14:39 设置待办扩展数据
            Map<String, String> taskExpandData = processInfo.getTaskExpandData();
            if(taskExpandData == null){
            	taskExpandData = new HashMap<String,String>();
    		}
            String taskExpandDataJson = JSON.toJSONString(taskExpandData);
            processInfo.setmPara(setInVariable(WorkflowTaskExpandDataService._TASK_EXPAND_DATA,taskExpandDataJson,processInfo.getmPara()));

            processBean = this.workflowEngineService.startProcessInstanceByKey(processInfo);

            if(FAILED.equals(processBean.getRetCode())){
                return processBean;
            }

            String currentTaskId = processBean.getCurrentTaskId();
            
            if (processInfo.getNextPost() !=null) {
            	processInfo.getmPara().put("nextPost",processInfo.getNextPost());
            }else {
            	processInfo.getmPara().put("nextPost",null);
            }

            WorkflowThreadLocalUtils.put(WorkflowConstants.SUB_PROCESS_INFOS,processInfo.getSubProcessInfos());

            taskService.claim(currentTaskId,processInfo.getUserId());
            this.workflowEngineService.passProcess(currentTaskId, processInfo.getmPara());

            wfActHiCommentService.deleteRuIdentityLink(currentTaskId);

            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processBean.getProcessInstanceId()).list();
            if(CollectionUtils.isNotEmpty(tasks)){
                Task task = tasks.get(0);
                if(task!=null){
                    processBean.setCurrentTaskId(task.getId());
                    processBean.setCurrentActivityId(task.getTaskDefinitionKey());
                }
            }
            ResultUtil.setReturnCode(processBean, SUCCEED, "流程启动成功");
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processBean;
    }

    @WorkflowIdempotent
    public ProcessBean terminateProcess(ProcessInfo processInfo){
        ProcessBean  processBean  = new  ProcessBean();
        final String processInstanceId = processInfo.getProcessInstanceId();
        if (StringUtils.isBlank(processInstanceId)) {
            return new ProcessBean(FAILED, "终止失败，流程实例id为空");
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            return new ProcessBean(FAILED, "终止失败，流程实例不存在或者流程已结束");
        }
        processInfo.setVariables(processInfo.getVariables() == null ? new HashedMap() : processInfo.getVariables());
        if (processInfo.getLocalVariables() != null) {
            processInfo.getVariables().putAll(processInfo.getLocalVariables());
        }
        runtimeService.setVariables(processInstanceId,processInfo.getVariables());
        this.workflowEngineService.terminateProcess(processInfo.getProcessInstanceId()
                    , processInfo.getVariables(), processInfo.getComment(), processInfo.getTaskId(),processInfo.getUserId());
        ResultUtil.setReturnCode(processBean, SUCCEED, "流程终止成功");
        return processBean;
    }

    @WorkflowIdempotent
    public ProcessBean suspendProcess(ProcessInfo processInfo)  {
        ProcessBean  processBean  = new  ProcessBean();
        this.workflowEngineService.suspendProcessInstance(processInfo.getProcessInstanceId());
        ResultUtil.setReturnCode(processBean, SUCCEED, "流程挂起成功");
        return processBean;
    }

    @WorkflowIdempotent
    public ProcessBean activateProcess(ProcessInfo processInfo)  {
        ProcessBean  processBean  = new  ProcessBean();
        this.workflowEngineService.activateProcessInstanceById(processInfo.getProcessInstanceId());
        ResultUtil.setReturnCode(processBean, SUCCEED, "流程激活成功");
        return processBean;
    }

    public TaskBean queryProcessTrack(ProcessInfo processInfo){
        return workflowProcessInstanceService.queryProcessTrack(processInfo);
    }
    
    public TaskBean signTaskStatus(TaskInfo taskInfo){
        return workflowProcessInstanceService.signTaskStatus(taskInfo);
    }
    public TaskBean removeTaskStatus(TaskInfo taskInfo){
        return workflowProcessInstanceService.removeTaskStatus(taskInfo);
    }
    public TaskBean findTaskList(TaskInfo taskInfo) {
        if (StringUtils.isBlank(taskInfo.getStartTimeSort()) && StringUtils.isBlank(taskInfo.getClaimTimeSort()) && StringUtils.isBlank(taskInfo.getEndTimeSort())) {
            taskInfo.setEndTimeSort("desc");
        }
        TaskBean taskBean = workflowEngineService.findCompletedTaskList(taskInfo);
        ResultUtil.setReturnCode(taskBean, SUCCEED, "任务查询成功");
        return taskBean;
    }
    /**
	 * 根据taskId查询任务详情(任务信息按钮信息等)
	 * @param taskInfo
	 * @return
	 *
	 */
    public TaskBean findTaskInfo(TaskInfo taskInfo) {
        TaskBean taskBean = new TaskBean();
        String taskId = taskInfo.getTaskId();
        if (StringUtils.isBlank(taskId)) {
            ResultUtil.setReturnCode(taskBean, FAILED, "任务查询失败,taskId为空");
            return taskBean;
        }
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskId(taskId).list();
        if (CollectionUtils.isEmpty(list)) {
            ResultUtil.setReturnCode(taskBean, FAILED, "任务查询失败,该任务id无数据");
            return taskBean;
        }
        HistoricTaskInstance historicTaskInstance = list.get(0);
        if (historicTaskInstance.getEndTime() != null) {
            taskInfo.setState("3");
        }
        if (WorkflowConstants.FINISHED_TASK.equals(taskInfo.getState())) {// 已办
            taskBean = workflowEngineService.findCompletedTaskList(taskInfo);
        } else {// 代办
            taskBean = workflowEngineService.findPendTaskList(taskInfo);
        }
        List<Map> listMap = taskBean.getTaskList();
        taskBean.setTaskList(listMap);
        ResultUtil.setReturnCode(taskBean, SUCCEED, "任务查询成功");

        return taskBean;
    }

	private String date2Str(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date == null) return null;
		return sdf.format(date);
	}
    public List<TaskBean> getAllClassifyPendTasks(TaskInfo taskInfo){
		return workflowEngineService.getAllClassifyPendTasks(taskInfo);
	}
	
	public List<TaskBean> getAllClassifyCompletedTasks(TaskInfo taskInfo){
		return workflowEngineService.getAllClassifyCompletedTasks(taskInfo);
	}
    
	public TaskBean findButtonPower(TaskInfo taskInfo){
		return workflowEngineService.findButtonPower(taskInfo);
	}
	
	/**
	 * 获取流程回调类
	 * @param processBean
	 * @return
	 */
	public ProcessBean findProcessHandleClass(ProcessBean processBean){
		return workflowEngineService.findProcessHandleClass(processBean);
	}
	
	public TaskBean findClassName(TaskInfo taskInfo) {
		return workflowEngineService.findClassName(taskInfo);
	}
	public TaskBean findProcessVariable(ProcessInfo processInfo){
		return workflowEngineService.findProcessVariable(processInfo);
	}

	public TaskBean findHiTaskByProcessInstanceId(ProcessInfo processInfo){
		return workflowEngineService.findHiTaskByProcessInstanceId(processInfo);
	}

	/**
     * 回退至第一个节点
     *
     * @param taskBean
     */
    @Override
    @WorkflowIdempotent
    public TaskBean backToFirstUserTask(TaskBean taskBean) throws Exception {
    	TaskBean check = workflowTaskService.checkAndClaimTaskIgnoreSuperAdmin(taskBean.getUserId(), taskBean.getTaskId());
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }

        Task task = taskService.createTaskQuery().taskId(taskBean.getTaskId()).singleResult();
        if(task==null){
            return new TaskBean(FAILED,"该任务不存在");
        }
        taskBean.setActivityId(task.getTaskDefinitionKey());
        taskBean.setProcessInstanceId(task.getProcessInstanceId());

        //查询第一个审批节点
        List<HistoricTaskInstance> firstTask = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).finished().orderByHistoricActivityInstanceStartTime().asc().listPage(0, 1);
        if(CollectionUtils.isEmpty(firstTask)){
            return new TaskBean(FAILED,"无法回退到首个用户任务");
        }
        List<String> nextActivityIds = new ArrayList<>();
        nextActivityIds.add(firstTask.get(0).getTaskDefinitionKey());
        taskBean.setNextActivityIds(nextActivityIds);
        workflowTaskExpandDataService.saveToProcessInstance(taskBean.getTaskId(),taskBean.getTaskExpandData());
        workflowEngineService.backTask(taskBean);

        WorkflowThreadLocalUtils.put(WorkflowConstants.SUB_PROCESS_INFOS,taskBean.getSubProcessInfos());
        ResultUtil.setReturnCode(taskBean, SUCCEED, "回退首节点成功");
        return taskBean;

    }

    @WorkflowIdempotent
    public TaskBean submitTask(TaskBean taskBean) {
        String taskId = taskBean.getTaskId();
        TaskBean check = workflowTaskService.checkAndClaimTask(taskBean.getUserId(), taskId);
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }
        if (taskBean.getmPara() == null) {
            taskBean.setmPara(new HashMap());
        }
        setmPara(taskBean);
        if (taskBean.getNextParticipates() != null && taskBean.getNextParticipates().size() > 0) {
            String user = StringUtils.join(taskBean.getNextParticipates().toArray(), ",");
            taskBean.getmPara().put("nextUser", user);
            taskBean.getmPara().put("assigneeList", taskBean.getNextParticipates() );
        }else {
            //抹去痕迹,防止变量污染下一次非选人模式
            taskBean.getmPara().put("nextUser","");
        }

        if(taskBean.getNextPost() !=null ) {
            taskBean.getmPara().put("nextPost", taskBean.getNextPost());
        }else {
            taskBean.getmPara().put("nextPost", null);
        }

        //并行节点设置
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        //保存待办扩展数据到流程实例中
        workflowTaskExpandDataService.saveToProcessInstance(taskBean.getTaskId(),taskBean.getTaskExpandData());
        //保存抄送信息
        workflowCarbonCopyService.saveCarbonCopyInfo(taskBean);

        //保存规则参数
        workflowTaskService.saveRuleParams(taskBean.getTaskId(),taskBean.getRuleParams());

        if (StringUtils.isNotBlank(taskBean.getVotingOpinion())){
            Map localVariables = taskBean.getLocalVariables() == null ? new HashMap() : taskBean.getLocalVariables();
            localVariables.put("votingOpinion",taskBean.getVotingOpinion());
            taskBean.setLocalVariables(localVariables);
        }
        if (taskBean.getLocalVariables() != null) {
            taskService.setVariablesLocal(taskId,taskBean.getLocalVariables());
        }
        WorkflowThreadLocalUtils.put(WorkflowConstants.SUB_PROCESS_INFOS,taskBean.getSubProcessInfos());
        taskService.claim(task.getId(), taskBean.getUserId());
        this.workflowEngineService.submitTask(task,taskBean);
        // 提交后返回流程是否结束的状态
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if(historicProcessInstance.getEndTime()==null){
            taskBean.setIsEnd("N");
        }else{
            taskBean.setIsEnd("Y");
        }
        ResultUtil.setReturnCode(taskBean, SUCCEED, "任务提交成功");
        return taskBean;

    }
    /**
     * 批量任务提交
     * @param taskBeans
     * @return
     */
    public List<TaskBean> batchSubmitTask(List<TaskBean> taskBeans) {
        List<TaskBean> taskBeanList=new ArrayList();
        if(taskBeans!=null&&!taskBeans.isEmpty()){
            for(TaskBean taskBean:taskBeans){
                taskBeanList.add(this.submitTask(taskBean));
            }
        }
        return taskBeanList;
    }

    @Override
    @Transactional
    public List<ProcessBean> batchStartProcess(List<ProcessInfo> processInfoList) throws Exception{
        List<ProcessBean> processBeanList = new ArrayList();
        for (ProcessInfo processInfo : processInfoList) {
            ProcessBean processBean = startProcess(processInfo);
            if(FAILED.equals(processBean.getRetCode())){
                throw new RuntimeException(processBean.getRetMsg());
            }
            processBeanList.add(processBean);
        }
        return processBeanList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BatchExecuteVo batchExecuteTask(Object o,BatchExecuteParam batchExecuteParam) throws Exception {
        String methodName = batchExecuteParam.getMethodName();
        List list = batchExecuteParam.getArgList();
        BatchExecuteVo<BaseBean> batchExecuteVo = new BatchExecuteVo();
        Method[] methods = o.getClass().getMethods();
        Method method = null;
        for (Method value : methods) {
            if (value.getName().equals(methodName)) {
                method = value;
                break;
            }
        }
        if(method == null){
            batchExecuteVo.setRetMsg("方法名错误");
            batchExecuteVo.setRetCode("1");
            return batchExecuteVo;
        }
        Class paramType = method.getParameterTypes()[0];
        List<BaseBean> beanList = new ArrayList();
        if(list != null && !list.isEmpty()){
            for(Object bean : list){
                JSONObject json = (JSONObject) JSON.toJSON(bean);
                BaseBean processBean = (BaseBean)method.invoke(o,JSONObject.toJavaObject(json,paramType));
                if(FAILED.equals(processBean.getRetCode())){
                    throw new RuntimeException(processBean.getRetMsg());
                }
                beanList.add(processBean);

            }
        }
        batchExecuteVo.setRetMsg("批量执行成功");
        batchExecuteVo.setRetCode("0");
        batchExecuteVo.setResultList(beanList);
        return batchExecuteVo;
    }



    /**
     * 回退
     * @param taskBean
     */
    @WorkflowIdempotent
    public TaskBean backTask(TaskBean taskBean)  {
        //获取回退的指定节点
        if (CollectionUtils.isEmpty(taskBean.getNextActivityIds())){
            throw new RuntimeException("退回节点不能为空");
        }

        //验证任务是否被领取
        TaskBean check = workflowTaskService.checkAndClaimTaskIgnoreSuperAdmin(taskBean.getUserId(), taskBean.getTaskId());
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }
        WorkflowThreadLocalUtils.put(WorkflowConstants.SUB_PROCESS_INFOS,taskBean.getSubProcessInfos());
        //回退至上一步

        if(taskBean.getIsCopyHiTaskExpandData()!=null&&!taskBean.getIsCopyHiTaskExpandData().equals("")){
            WorkflowThreadLocalUtils.put("isCopyHiTaskExpandData",taskBean.getIsCopyHiTaskExpandData());
        }

        Task task = taskService.createTaskQuery().taskId(taskBean.getTaskId()).singleResult();
        if(task==null){
            ResultUtil.setReturnCode(taskBean, FAILED, "当前任务不存在");
            return taskBean;
        }

        taskBean.setProcessInstanceId(task.getProcessInstanceId());
        taskBean.setActivityId(task.getTaskDefinitionKey());

        //保存待办扩展数据到流程实例中
        workflowTaskExpandDataService.saveToProcessInstance(taskBean.getTaskId(),taskBean.getTaskExpandData());

        this.workflowEngineService.backTask(taskBean);

        ResultUtil.setReturnCode(taskBean, SUCCEED, "任务回退成功");
        return taskBean;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @WorkflowIdempotent
    public TaskBean jumpFlowTask(TaskBean taskBean)  {
        try {
            WorkflowThreadLocalUtils.put("request-method","jumpFlowTask");
            this.workflowEngineService.jumpFlowTask(taskBean);
            ResultUtil.setReturnCode(taskBean, SUCCEED, "自由流转成功");
            return taskBean;
        }finally {
            WorkflowThreadLocalUtils.removeAll();
        }
    }

    @WorkflowIdempotent
    public TaskBean releaseTask(TaskInfo taskInfo) throws Exception {
        TaskBean taskBean=new TaskBean();
        if (StringUtils.isBlank(taskInfo.getTaskId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "任务id不能为空");
            return taskBean;
        }
        if (StringUtils.isBlank(taskInfo.getUserId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "用户id不能为空");
            return taskBean;
        }
        // 任务要有权限，才能领取
        TaskBean check = workflowTaskService.checkAndClaimTaskIgnoreSuperAdmin(taskInfo.getUserId(), taskInfo.getTaskId());
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }

        Task task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
        if (task != null) {
            String assignee = task.getAssignee();
            if (assignee==null){
                ResultUtil.setReturnCode(taskBean, FAILED, "该任务还未被领取");
                return taskBean;
            }

            BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
            UserTask userTask = bpmnModelInstance.getModelElementById(task.getTaskDefinitionKey());
            if(userTask.getLoopCharacteristics()==null){
                // 普通节点
                // 先删除普通
                wfTaskSelectUserService.deleteUserTaskAssignment(task.getId());
                workflowTaskService.insertPostInfo((TaskEntity)task,userTask);
            }else{
                // 多工作项
                wfTaskSelectUserService.deleteUserTaskAssignment(task.getId());
                String camundaElementVariable = ((MultiInstanceLoopCharacteristics) userTask.getLoopCharacteristics()).getCamundaElementVariable();
                Object variable = taskService.getVariable(task.getId(), camundaElementVariable);
                PostInfo postInfo = null;
                if(variable instanceof PostInfo){
                    postInfo = (PostInfo)taskService.getVariable(task.getId(), camundaElementVariable);
                }else{
                    String dataString = JSON.toJSONString(variable);
                    postInfo = JSON.parseObject(dataString, PostInfo.class);
                }
                wfTaskSelectUserService.saveUserTaskAssignment(task.getId(),task.getTenantId(), postInfo);
            }

            // 保存完后看是否是自动抢单走了，是就不改assignee
            ActRuIdentityLinkQuery param = new ActRuIdentityLinkQuery();
            param.setTaskId(taskInfo.getTaskId());
            List<ActRuIdentitylinkEntity> candidateUserList = wfActHiCommentService.findCandidateUserList(param);

            if(CollectionUtils.isNotEmpty(candidateUserList) && candidateUserList.size()==1 && "user".equals(candidateUserList.get(0).getType())){
                // 释放后，如果还是一个，继续自动抢单
                this.workflowEngineService.unClaimTask(taskInfo.getTaskId());
                this.workflowEngineService.claimTask(taskInfo.getTaskId(),candidateUserList.get(0).getUserId());
                // 如果释放后还是一样，处理人没有更改，则不需要加入队列
                if(!StringUtils.equals(assignee,candidateUserList.get(0).getUserId())){
                    cacheQueueService.cacheToUserTaskQueue(task.getId());
                }
            }else{
                this.workflowEngineService.unClaimTask(taskInfo.getTaskId());
                // 缓存队列
                cacheQueueService.cacheToUserTaskQueue(task.getId());
            }

            ResultUtil.setReturnCode(taskBean, SUCCEED, "任务释放成功");
        }else {
            ResultUtil.setReturnCode(taskBean, FAILED, "任务释放失败，该任务不存在");
        }
        return taskBean;
    }

    @WorkflowIdempotent
    public  TaskBean transferTask(TaskInfo taskInfo) throws Exception {
        TaskBean taskBean = new TaskBean();
        //验证任务权限
        TaskBean check = workflowTaskService.checkAndClaimTaskIgnoreSuperAdmin(taskInfo.getUserId(), taskInfo.getTaskId());
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }
        if (taskInfo.getTransferUser()==null){
            ResultUtil.setReturnCode(taskBean, FAILED, "代办人信息不能为空");
            return taskBean;
        }
        Task task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
        if (task != null) {
            trackTransfer(taskInfo,task);
            taskInfo.setTenantId(task.getTenantId());
            taskInfo.setActivityId(task.getTaskDefinitionKey());
            this.workflowEngineService.transferAssignee(taskInfo);
            // 缓存队列
            cacheQueueService.cacheToUserTaskQueue(task.getId());
            ResultUtil.setReturnCode(taskBean, SUCCEED, "转办任务成功");
        } else {
            ResultUtil.setReturnCode(taskBean, FAILED, "转办任务失败,该任务不存在");
        }
        return taskBean;

    }

    private void trackTransfer(TaskInfo taskInfo,Task task) {
        Object transfer_track = taskService.getVariableLocal(task.getId(), WorkflowConstants._TRANSFER_TRACK);
        Map<String,Object> track = new HashMap<>();
        track.put("endTime", DateUtil.formatDate(new Date()));
        track.put("comment",taskInfo.getComment());
        track.put("assignee",taskInfo.getUserId());
        track.put("localVariables",taskInfo.getLocalVariables());
        if(transfer_track!=null){
            Map<String,Object> next = (Map)transfer_track;
            Object last = null;
            while((last = next.get("next"))!=null){
                next = (Map)last;
            }
            next.put("next",track);

        }else{
            transfer_track = track;
        }
        taskService.setVariableLocal(task.getId(),WorkflowConstants._TRANSFER_TRACK,transfer_track);
    }

    //@Override
    public TaskBean queryNextTaskList(TaskInfo taskInfo) throws Exception {
        return this.workflowEngineService.queryNextTaskList(taskInfo);
    }

    // ======================= 模型操作 ========================

    /**
     * 获取模型列表
     * @param processInfo
     * @return
     */
    @Override
    public ProcessBean modelList(ProcessInfo processInfo) {
        return workflowModelService.modelList(processInfo);
    }

    /**
     * json byte -> json -> xml -> xml bytes
     * 根据模型部署流程
     * @param processDefinitionId
     * @return
     */
    @Override
    public ProcessBean deployByDefinitionId (String processDefinitionId) throws Exception  {
        return workflowModelService.deployByDefinitionId(processDefinitionId);
    }


    /**
     * 创建模型
     * @param tenantId
     * @return
     */
    @Override
    public ProcessBean createModel(String key, String name, String description, String tenantId) {
        return workflowModelService.createModel(key, name, description, tenantId);
    }

    /**
     * 删除模型
     * @return
     */
    @Override
    public ProcessBean deleteModel(String key,String tenantId) {
        return workflowModelService.deleteModel(key,tenantId);
    }
    /**
     * model文件导出,导出类型有json,bpmn格式
     * processBean={fileName,bytes}
     */
    @Override
    public ProcessBean exportModel(String modelId,String type){
        return workflowModelService.exportModel(modelId, type);
    }



    // ======================= 流程定义操作 ========================
    /**
     * 获取流程定义列表
     *
     * @param processInfo
     * @return
     */
    @Override
    public ProcessBean processDefinitionList(ProcessInfo processInfo) {
        return workflowProcessDefineService.processDefinitionList(processInfo);
    }
    
    /**
     * 获取分类及其子分类的所有已发布流程创建信息
     * @param processInfo
     * @return
     */
    public ProcessBean findProcessByCode(ProcessInfo processInfo){
    	return workflowProcessDefineService.findProcessByCode(processInfo);
    }
    
    /**
     * 部署流程
     * @param deployInfo
     * @return
     */
    @Override
    public ProcessBean deploy(DeployInfo deployInfo) {
        return workflowProcessDefineService.deploy(deployInfo);
    }


    /**
     * 获取流程运行的实时图片
     * @param processDefineId
     * @return
     */
    @Override
    public InputStream getProcessImage(String processDefineId) {
        return workflowProcessDefineService.getProcessImage(processDefineId);
    }

    /**
     * 获取流程定义文件
     * @param processDefineId
     * @return
     */
    @Override
    public InputStream getProcessXml(String processDefineId) {
        return workflowProcessDefineService.getProcessXml(processDefineId);
    }

    /**
     * 根据流程定义创建模型
     * @param ids
     * @return
     */
    @Override
    public ProcessBean createModelByProcessDefinition(String[] ids) {
        return workflowProcessDefineService.createModelByProcessDefinition(ids);
    }

    /**
     * 复制流程定义
     * @param targetDefinitionId 目标流程定义id
     * @param newKey 新的键值
     * @return newName 新的名字
     */
    @Override
    public ProcessBean copyProcessDefinition(String targetDefinitionId,String newKey,String newName) {
        return workflowProcessDefineService.copyProcessDefinition(targetDefinitionId, newKey, newName);
    }

    /**
     * 更改处理人
     * @param processDefinitionId
     * @param activityId
     * @return assignee 处理人
     */
    @Override
    public ProcessBean changeAssigee(String processDefinitionId,String activityId,String assignee) {
        return workflowProcessDefineService.changeAssigee(processDefinitionId, activityId, assignee);
    }

    //============================== 流程实例服务=================
    /**
     * 运行中的流程查询
     * @param processInfo
     * @return
     */
    @Override
    public ProcessBean processInstanceList(ProcessInfo processInfo) {
        return workflowProcessInstanceService.processInstanceList(processInfo);
    }

    /**
     * 获取已经结束的流程列表
     * @param processInfo
     * @return
     */
    @Override
    public ProcessBean getCompletedProcess(ProcessInfo processInfo) {
        if (StringUtils.isBlank(processInfo.getStartTimeSort())&&StringUtils.isBlank(processInfo.getEndTimeSort())){
            processInfo.setEndTimeSort("desc");
        }
        return workflowProcessInstanceService.getCompletedProcess(processInfo);
    }

    /**
     * 修改业务主键
     * @param processInfo
     * @return
     */
    @Override
    public ProcessBean updateBusinessKey(ProcessInfo processInfo) {
        return workflowProcessInstanceService.updateBusinessKey(processInfo);
    }

    //============================== service task 服务===========
    @Override
    public TaskBean serviceTaskRetry(TaskBean taskBean) {
        return workflowManagementService.serviceTaskRetry(taskBean);
    }

    //============================== task 服务 ==================
//    @Override
//    public TaskBean findCandidateGroupTasks(TaskInfo info) {
//        return workflowTaskService.findCandidateGroupTasks(info);
//    }


    @Override
    @WorkflowIdempotent
    public TaskBean rebackTask(TaskBean taskBean) throws Exception {
        return this.workflowEngineService.rebackTask(taskBean);
    }

    @Override
    public TaskBean getMultiStatus(TaskInfo taskInfo)  {
        return this.workflowEngineService.getMultiStatus(taskInfo);
    }

    @Override
    public TaskBean getTaskDefinitions(String processDefinitionId) {
        return workflowProcessDefineService.getTaskDefinitions(processDefinitionId);
    }

    @Override
    public TaskBean getTasksByProcessInstanceId(String processInstanceId) throws Exception {
        return workflowTaskService.getTasksByProcessInstanceId(processInstanceId);
    }

    /**
     * 根据流程实例id获取所有的下一步审批任务
     *
     * @param taskInfo
     * @return
     * @throws
     */
    @Override
    public TaskBean getAllNextTaskInfo(TaskInfo taskInfo) {
        return workflowTaskService.getAllNextTaskInfo(taskInfo);

    }
    
    public TaskBean findHiTaskPoolExecuteDataList(TaskInfo taskInfo){
    	return workflowTaskService.findHiTaskPoolExecuteDataList(taskInfo);
    }
    
    public TaskBean temporarySaveTaskExpandData(TaskInfo taskInfo){
    	TaskBean taskBean = new TaskBean();
    	String taskId = taskInfo.getTaskId();
    	Map<String, String> expandData = taskInfo.getTaskExpandData();
    	if (StringUtils.isBlank(taskId)) {
    		taskBean.setRetCode(FAILED);
        	taskBean.setRetMsg("任务ID不能为空");
			return taskBean;
		}
    	workflowTaskExpandDataService.temporarySaveData(taskId, expandData);
    	taskBean.setRetCode(SUCCEED);
    	taskBean.setRetMsg("存储扩展数据成功");
    	return taskBean;
    }

	/**
	 * 获取当前节点用户信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findTaskUsers(TaskInfo taskInfo){
		final String id = taskInfo.getTaskId();
		final List<String> users = Lists.newArrayList();
		// camunda-TODO
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				List<IdentityLinkEntity> taskIdentityLinkEntities = commandContext.getIdentityLinkManager()
		    	        .findIdentityLinksByTaskId(id);
				if (CollectionUtils.isNotEmpty(taskIdentityLinkEntities)) {
					for(IdentityLinkEntity identityLinkEntity:taskIdentityLinkEntities){
						String userId = identityLinkEntity.getUserId();
						users.add(userId);
					}
				}
				return null;
			}
		});
		
    	TaskBean taskBean = new TaskBean();
    	taskBean.setUsers(users);
    	return taskBean;
	}

    /**
     * 删除整个流程实例，包括待办已办审批历史等所有数据
     * @param info
     * @return
     */
    @Override
    public ProcessBean deleteProcessInstance(ProcessInfo info) {
        return workflowProcessInstanceService.deleteProcessInstance(info);
    }

    @Override
    public TaskBean getTaskDefinitionsByKey(TaskInfo taskInfo) {
        return workflowProcessDefineService.getTaskDefinitionsByKey(taskInfo);
    }

    @Override
    public TaskBean getFirstTaskDefine(TaskInfo taskInfo) {
        return workflowEngineService.getFirstTaskDefine(taskInfo);
    }



    @Override
    @Transactional
    public List<ProcessBean> batchStartAndSubmitProcess(List<ProcessInfo> processInfoList) throws Exception{
        List<ProcessBean> processBeanList=new ArrayList();
        for (ProcessInfo processInfo : processInfoList) {
            processBeanList.add(startAndSubmitProcess(processInfo));
//            if(FAILED.equals(processBean.getRetCode())){
//                throw new RuntimeException(processBean.getRetMsg());
//            }
        }
        return processBeanList;
    }

    @Override
    public List<TaskBean> batchBackTask(List<TaskBean> taskBeanList) throws Exception {
        List<TaskBean> taskBeans=new ArrayList();
        for (TaskBean taskBean : taskBeanList) {
            taskBeans.add(backTask(taskBean));
        }
        return taskBeans;
    }

    private void setmPara(TaskBean taskBean){
        //设置上一节点beforeNodeOrgId
        if(StringUtils.isNotBlank(taskBean.getBeforeNodeOrgId())) {
            taskBean.getmPara().put(AssignmentModel.LAST_TASK_ORG_ID_KEY, taskBean.getBeforeNodeOrgId());
        }
        //设置上一节点上级机构beforeNodeParentOrgId
        if(StringUtils.isNotBlank(taskBean.getBeforeNodeParentOrgId())) {
            taskBean.getmPara().put(AssignmentModel.LAST_TASK_PARENT_ORG_ID_KEY, taskBean.getBeforeNodeParentOrgId());
        }
        //设置与上一个节点审批人下级机构同机构
        if(taskBean.getBeforeNodeChildOrgId() != null && taskBean.getBeforeNodeChildOrgId().size() > 0) {
            taskBean.getmPara().put(AssignmentModel.LAST_TASK_CHILD_ORG_ID_KEY, taskBean.getBeforeNodeChildOrgId());
        }
        //设置与上一个节点审批人同部门
        if(StringUtils.isNotBlank(taskBean.getBeforeNodeDeptId())) {
            taskBean.getmPara().put(AssignmentModel.LAST_TASK_DEPT_ID_KEY, taskBean.getBeforeNodeDeptId());
        }
        //设置与上一个节点审批人上级部门同部门
        if(StringUtils.isNotBlank(taskBean.getBeforeNodeParentDeptId())) {
            taskBean.getmPara().put(AssignmentModel.LAST_TASK_PARENT_DEPT_ID_KEY, taskBean.getBeforeNodeParentDeptId());
        }
        //设置与上一个节点审批人下级部门同部门
        if(taskBean.getBeforeNodeChildDeptId()!=null&&taskBean.getBeforeNodeChildDeptId().size()>0) {
            taskBean.getmPara().put(AssignmentModel.LAST_TASK_CHILD_DEPT_ID_KEY, taskBean.getBeforeNodeChildDeptId());
        }
        if(taskBean.getEditorVariables()!=null&&taskBean.getEditorVariables().size()>0) {
            taskBean.getmPara().put("editorVariables", taskBean.getEditorVariables());
        }
    }


    /**
     * 流程结束后获取流程变量信息
     * @param processInfo
     * @return
     */
    public TaskBean findFinishedVariable(ProcessInfo processInfo){
        return workflowEngineService.findFinishedVariable(processInfo);
    }

    /**
     * 查询流程分类列表
     */
    public List<ClassifyInfo> findClassifyList(ClassifyInfo classifyInfo){
        return workflowClassifyService.findClassifyList(classifyInfo);
    }

    /**
     * 离岗移交
     * @param wfLeaveTransfer
     * @return
     */
    public TaskBean saveLeaveTransfer(WfLeaveTransfer wfLeaveTransfer){
        return workflowLeaveTransferService.saveLeaveTransfer(wfLeaveTransfer);
    }

    /**
     * 离岗移交终止
     * @param wfLeaveTransfer
     * @return
     */
    public TaskBean stopLeaveTransfer(WfLeaveTransfer wfLeaveTransfer){
        return workflowTaskService.stopLeaveTransfer(wfLeaveTransfer);
    }

    @WorkflowIdempotent
    public TaskBean backPreTask(TaskBean taskBean)  {
        if(StringUtils.isBlank(taskBean.getTaskId())){
            return new TaskBean(FAILED, "任务id不能为空");
        }
        Task task = taskService.createTaskQuery().taskId(taskBean.getTaskId()).singleResult();
        if(task==null){
            return new TaskBean(FAILED, "该任务不存在");
        }
        //验证任务是否被领取
        TaskBean check = workflowTaskService.checkAndClaimTaskIgnoreSuperAdmin(taskBean.getUserId(), taskBean.getTaskId());
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }
        String processInstanceId = task.getProcessInstanceId();
//        // 查询已办数据
        List<HistoricActivityInstance> activityInstances = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .desc()
                .list();

        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
        UserTask userTask = bpmnModelInstance.getModelElementById(task.getTaskDefinitionKey());
        List<UserTask> previousUserTasks = getPreviousUserTask(userTask);
        workflowTaskExpandDataService.saveToProcessInstance(taskBean.getTaskId(),taskBean.getTaskExpandData());
        // 回退到的节点id
        List<String> nextActivityIds = new ArrayList<>();
        List<String> cancelActivityIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(previousUserTasks)){
            List<HistoricTaskInstance> taskInstances = historyService
                    .createHistoricTaskInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .finished()
                    .taskDeleteReason("completed")
                    .orderByHistoricTaskInstanceEndTime()
                    .desc()
                    .list();
            for (HistoricTaskInstance taskInstance : taskInstances) {
                for (UserTask previousUserTask : previousUserTasks) {
                    if(StringUtils.equals(previousUserTask.getId(),taskInstance.getTaskDefinitionKey())){
                        nextActivityIds.add(taskInstance.getTaskDefinitionKey());
                    }
                }
                if(CollectionUtils.isNotEmpty(nextActivityIds)){
                    UserTask nextUserTask = (UserTask) bpmnModelInstance.getModelElementById(nextActivityIds.get(0));
                    if(CollectionUtils.isNotEmpty(nextUserTask.getOutgoing())&& nextUserTask.getOutgoing().size()==1){
                        FlowNode target = nextUserTask.getOutgoing().iterator().next().getTarget();
                        // 回退到并行网关内，需要同时回退到其他并行箭头的任务上
                        if(target instanceof ParallelGateway){
                            nextActivityIds.clear();
                            List<UserTask> previousUserTask = getPreviousUserTask(target);
                            for (UserTask item : previousUserTask) {
                                nextActivityIds.add(item.getId());
                            }
                        }
                    }
                    if(userTask.getPreviousNodes().list()!=null && userTask.getPreviousNodes().list().size()==1 && userTask.getPreviousNodes().list().get(0) instanceof ParallelGateway){
                        // 退回出并行节点外，需要同时取消并行节点中的其他任务
                        List<Task> currentTasks = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
                        for (Task currentTask : currentTasks) {
                            cancelActivityIds.add(currentTask.getTaskDefinitionKey());
                        }
                    }else{
                        // 非并行
                        cancelActivityIds.add(userTask.getId());
                    }
                    break;
                }
            }
        }
        if(CollectionUtils.isEmpty(nextActivityIds)){
            ResultUtil.setReturnCode(taskBean, FAILED, "无法退回上一步");
            return taskBean;
        }
        if(CollectionUtils.isNotEmpty(cancelActivityIds)){
            taskBean.setCancelActivityIds(cancelActivityIds);
        }
        taskBean.setNextActivityIds(nextActivityIds);
        taskBean.setActivityId(task.getTaskDefinitionKey());
        taskBean.setProcessInstanceId(task.getProcessInstanceId());
        workflowEngineService.backTask(taskBean);
        ResultUtil.setReturnCode(taskBean, SUCCEED, "任务退回上一节点成功");
        return taskBean;
    }

    /*
    递归获取所有上一个用户节点
     */
    private List<UserTask> getPreviousUserTask(FlowNode flowNode) {
        List<UserTask> userTasks = new ArrayList<>();
        List<FlowNode> list = flowNode.getPreviousNodes().list();
        for (FlowNode node : list) {
            if(node instanceof UserTask){
                userTasks.add((UserTask)node);
            }else{
                userTasks.addAll(getPreviousUserTask(node));
            }
        }
        return userTasks;
    }

    @Override
    public ProcessBean copyModel(ModelInfo modelInfo) throws Exception {
        return workflowModelService.copyModel(modelInfo);
    }

    @Override
    public ProcessBean getExpandPlanByProcessInfo(ProcessInfo info) {
        return workflowTaskExpandPlanService.getExpandPlanByProcessInfo(info);
    }


    public TaskBean findTaskCounts(TaskInfo taskInfo){
        TaskBean bean =  workflowTaskService.findTaskCounts(taskInfo);
       return bean;
    }

    @Override
    public TaskBean getSignTaskStatus(TaskInfo taskInfo) {
        return workflowProcessInstanceService.getSignTaskStatus(taskInfo);
    }
    
    @Override
    public TaskBean findOrgTaskList(TaskInfo taskInfo) {
        if (StringUtils.isBlank(taskInfo.getCreateTimeSort())){
            taskInfo.setCreateTimeSort("desc");
        }
        TaskBean taskBean = workflowEngineService.findPendOrgTaskList(taskInfo);
        if (FAILED.equals(taskBean.getRetCode())) {
            return taskBean;
        }
        ResultUtil.setReturnCode(taskBean, SUCCEED, "任务查询成功");
        return taskBean;
    }

    @Override
    public TaskBean findOrgHiTaskList(TaskInfo taskInfo) {
        if (StringUtils.isBlank(taskInfo.getCreateTimeSort())){
            taskInfo.setCreateTimeSort("desc");
        }
        TaskBean taskBean = workflowTaskService.findPendOrgHiTaskList(taskInfo);
        if (FAILED.equals(taskBean.getRetCode())) {
            return taskBean;
        }
        ResultUtil.setReturnCode(taskBean, SUCCEED, "任务查询成功");
        return taskBean;
    }

    @Override
    public ProcessBean messageConfirmProcessInstanceId(ProcessInfo processInfo) {
        workflowProcessInstanceService.messageConfirmProcessInstanceId(processInfo.getProcessInstanceId(),processInfo.getMessageName());
        return new ProcessBean(SUCCEED,"发送成功");
    }



	@Override
	public QueryBean  findOrgTaskCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		QueryBean queryBean = new QueryBean();
		List< Map<String,Object>> list = this.workflowTaskService.findOrgTaskCount(params);
		queryBean.setResList(list);
		 ResultUtil.setReturnCode(queryBean, SUCCEED, "任务查询成功");
		 return queryBean;
	}

    @Override
    public Map<String, Object> findErrorServiceTask(Map<String,Object> map) {
        Map<String,Object> map1=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()),Integer.valueOf(map.get("pageSize").toString()));
        WfJobEntity wfJobEntity=new WfJobEntity();
        wfJobEntity.setHandlerType("async-continuation");
        wfJobEntity.setTenantId(map.get("tenantId").toString());
        String processInstanceId = MapUtils.getString(map, "processInstanceId");
        String processDefineKey = MapUtils.getString(map, "processDefineKey");
        String executionId = MapUtils.getString(map, "executionId");
        String activityId = MapUtils.getString(map, "activityId");
        //查询processInstanceId
        if (StringUtils.isNoneBlank(processInstanceId)){
            wfJobEntity.setProcessInstanceId(processInstanceId);
        }
        if (StringUtils.isNoneBlank(processDefineKey)){
            wfJobEntity.setProcessDefKey(processDefineKey);
        }
        if (StringUtils.isNoneBlank(executionId)){
            wfJobEntity.setExecutionId(executionId);
        }
        if (StringUtils.isNoneBlank(activityId)){
            wfJobEntity.setFailedActId(activityId);
        }
        List<WfJobEntity> wfJobEntityList=wfJobMapper.findErrorServiceTask(wfJobEntity);
        PageInfo<WfJobEntity> wfJobEntityPage=new PageInfo<>(wfJobEntityList);
        PageInfo<WfJobEntity> page=new PageInfo<>();
        page.setTotal(wfJobEntityPage.getTotal());
        page.setList(wfJobEntityPage.getList());
        map1.put("grid",page);
        return map1;
    }

    @Override
    public TaskBean retryServiceTask(TaskInfo taskInfo) {
        TaskBean taskBean=new TaskBean();
        taskBean.setProcessInstanceId(taskInfo.getProcessInstanceId());
        taskBean.setExecutionId(taskInfo.getExecutionId());
        taskBean.setActivityId(taskInfo.getActivityId());
        return serviceTaskRetry(taskBean);
    }

    @Override
    public TaskBean reAssignmentTask(TaskInfo taskInfo) {
        TaskBean taskBean=new TaskBean();
        if (StringUtils.isBlank(taskInfo.getTaskId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "任务id不能为空");
            return taskBean;
        }
        if (StringUtils.isBlank(taskInfo.getUserId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "任务处理人不能为空");
            return taskBean;
        }
        if (StringUtils.isBlank(taskInfo.getTenantId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "租户id不能为空");
            return taskBean;
        }
        if (CollectionUtils.isEmpty(taskInfo.getUsers())){
            ResultUtil.setReturnCode(taskBean, FAILED, "改派人不能为空");
            return taskBean;
        }
        // 任务要有权限，才能改派
        TaskBean check = workflowTaskService.checkAndClaimTaskIgnoreSuperAdmin(taskInfo.getUserId(), taskInfo.getTaskId());
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }
        //判断该任务是否被领取
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskInfo.getTaskId()).singleResult();
        if (historicTaskInstance.getAssignee()==null){
            ResultUtil.setReturnCode(taskBean, FAILED, "该任务还未被领取");
            return taskBean;
        }

        String[] users = taskInfo.getUsers().toArray(new String[taskInfo.getUsers().size()]);
        if (users.length==1){
            //有多个参与者领取
            wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
            taskService.setAssignee(taskInfo.getTaskId(),users[0]);
            wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),users);
            ResultUtil.setReturnCode(taskBean, SUCCEED, "改派成功");
        }else {
            //先删除旧的
            wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
            taskService.setAssignee(taskInfo.getTaskId(),null);
            //保存新的
            wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),users);
            ResultUtil.setReturnCode(taskBean, SUCCEED, "改派成功");
        }
        // 缓存队列
        cacheQueueService.cacheToUserTaskQueue(taskInfo.getTaskId());
        return taskBean;

    }

    @Override
    public TaskBean claimWorkInfo(TaskInfo taskInfo) {
        TaskBean taskBean=new TaskBean();
        if (StringUtils.isBlank(taskInfo.getTaskId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "任务id不能为空");
            return taskBean;
        }
        if (StringUtils.isBlank(taskInfo.getUserId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "任务处理人不能为空");
            return taskBean;
        }
        if (StringUtils.isBlank(taskInfo.getTenantId())){
            ResultUtil.setReturnCode(taskBean, FAILED, "租户id不能为空");
            return taskBean;
        }
        if (CollectionUtils.isEmpty(taskInfo.getUsers())){
            ResultUtil.setReturnCode(taskBean, FAILED, "领取人不能为空");
            return taskBean;
        }
        // 任务要有权限，才能领取
        TaskBean check = workflowTaskService.checkAndClaimTaskIgnoreSuperAdmin(taskInfo.getUserId(), taskInfo.getTaskId());
        if(FAILED.equals(check.getRetCode())){
            return  check;
        }
        //判断该任务是否已被领取
        Task task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
        if (task==null){
            ResultUtil.setReturnCode(taskBean, FAILED, "找不到该任务");
            return taskBean;
        }
        if (task.getAssignee()!=null){
            ResultUtil.setReturnCode(taskBean, FAILED, "该任务已被领取");
            return taskBean;
        }

        String[] users = taskInfo.getUsers().toArray(new String[taskInfo.getUsers().size()]);
        // 有权限的人才能领取，superadmin不做权限校验
        if(!StringUtils.equals("superadmin",taskInfo.getUserId())){
            for (String assignee : users) {
                //验证任务是否被领取
                TaskBean userCheck = workflowTaskService.checkAndClaimTask(assignee, taskInfo.getTaskId());
                if(FAILED.equals(userCheck.getRetCode())){
                    return new TaskBean(WorkflowConstants.FAILED,"对不起，领取人没有权限");
                }
            }
        }

        if (users.length==1){
            //有多个参与者领取
            taskService.claim(taskInfo.getTaskId(),users[0]);
            //删除旧的数据
            wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
            wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),users);
            ResultUtil.setReturnCode(taskBean, SUCCEED, "领取任务成功");
        }else {
            //先删除旧的
            wfTaskSelectUserService.deleteUserTaskAssignment(taskInfo.getTaskId());
            //保存新的
            wfTaskSelectUserService.saveUserTaskAssignment(taskInfo.getTaskId(),taskInfo.getTenantId(),users);
            ResultUtil.setReturnCode(taskBean, SUCCEED, "领取任务成功");
        }
        // 缓存队列
        cacheQueueService.cacheToUserTaskQueue(taskInfo.getTaskId());
        return taskBean;

    }

    @Override
    public TaskBean findTodoList(TaskInfo taskInfo) {
        String userId = taskInfo.getUserId();
        if(StringUtils.isBlank(userId)){
            return new TaskBean(FAILED,"userId不能为空");
        }

        // 根据userId查询该用户所拥有的角色、机构、岗位等信息
        EditorInfo editorInfo = new EditorInfo();
        editorInfo.setUserId(userId);
        editorInfo.setTenantId(taskInfo.getTenantId());
        EditorBean editorBean = editorServiceProxyService.findAllPostInfo(editorInfo);
        List<PostInfo> postInfos = editorBean.getPostInfos();
        taskInfo.setUserId(null);
        taskInfo.setPostInfos(postInfos);
        return findOrgTaskList(taskInfo);
    }

    @Override
    public ProcessBean batchTerminateProcess(List<ProcessInfo> info) {
        ProcessBean processBean=new ProcessBean();
        for (ProcessInfo processInfo:info){
            this.terminateProcess(processInfo);
        }
        ResultUtil.setReturnCode(processBean, SUCCEED, "流程批量终止成功");
        return processBean;
    }

    public TaskBean deleteTask(TaskBean taskBean) {
        ProcessInstanceModificationBuilder processInstanceModification = runtimeService.createProcessInstanceModification(taskBean.getProcessInstanceId());
        // 取消自身的活动
        processInstanceModification.cancelAllForActivity(taskBean.getActivityId());
        processInstanceModification.execute();
        ResultUtil.setReturnCode(taskBean, SUCCEED, "删除工作项成功");
        return taskBean;
    }

    public TaskBean insertTask(TaskBean taskBean) {
        ProcessInstanceModificationBuilder processInstanceModification = runtimeService.createProcessInstanceModification(taskBean.getProcessInstanceId());
        processInstanceModification.startBeforeActivity(taskBean.getActivityId());
        processInstanceModification.execute();
        ResultUtil.setReturnCode(taskBean, SUCCEED, "新增工作项成功");
        return taskBean;
    }

    @Override
	public ProcessBean deleteModelList(List<String> deploymentIds) {
		for (String deploymentId: deploymentIds) {
			repositoryService.deleteDeployment(deploymentId, true);	
		}		
		return new ProcessBean(SUCCEED,"删除成功");
	}

    @Override
    public ProcessBean setProcessVariable(ProcessInfo processInfo) {
        if(StringUtils.isBlank(processInfo.getProcessInstanceId())){
            return new ProcessBean(FAILED,"流程实例id不能为空");
        }
        if(processInfo.getVariables()==null || processInfo.getVariables().size()==0){
            return new ProcessBean(FAILED,"流程变量不能为空");
        }
        runtimeService.setVariables(processInfo.getProcessInstanceId(),processInfo.getVariables());
        return new ProcessBean(SUCCEED,"设置成功");
    }
}
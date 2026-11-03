package com.tansun.wf.activiti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.manager.WfHiTaskExpandDataManager;
import com.tansun.wf.activiti.manager.WfTaskExpandDataManager;
import com.tansun.wf.activiti.model.AssignmentModel;
import com.tansun.wf.activiti.model.HiTaskExpandDataEntity;
import com.tansun.wf.activiti.model.SequentialAddedTaskState;
import com.tansun.wf.activiti.model.TaskExpandDataEntity;
import com.tansun.wf.camunda.bpmn.impl.instance.FormImpl;
import com.tansun.wf.camunda.bpmn.instance.Form;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;

/**
 * 流程待办扩展数据服务
 *
 */
@Service("workflowTaskExpandDataService")
public class WorkflowTaskExpandDataService {

	public static final String _TASK_EXPAND_DATA = "_TASK_EXPAND_DATA";

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;

	@Autowired
	private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private WorkflowProcessDefineService workflowProcessDefineService;

	@Autowired
	private WorkflowClassifyService workflowClassifyService;

	@Autowired
	private ProcessEngine processEngine;
	
  @Autowired
   private RepositoryService repositoryService;
	  



	/**
	 * 将待办扩展数据保存至流程流程变量中
	 * @param taskId
	 * @param expandData
	 */
	public void saveToProcessInstance(String taskId, Map<String,String> expandData){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		HistoricTaskInstance taskinst = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		if(task == null && taskinst == null){
			logger.warn("task {0} not exist",taskId);
			return ;
		}

		if(expandData == null){
			expandData = new HashMap<String,String>();
//			logger.info("expandData is null !");
//			return ;
		}
		List<Execution> executions = new ArrayList<Execution>();
		if (task != null) {
			executions = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).list();
		}else {
			executions = runtimeService.createExecutionQuery().processInstanceId(taskinst.getProcessInstanceId()).list();
		}

		if(executions == null || executions.size() == 0){
			logger.info("executions is null !");
			return ;
		}
		for (Execution execution : executions ) {
            String oldJsonStr = (String) runtimeService.getVariable(execution.getId(), _TASK_EXPAND_DATA);
            if(StringUtils.isBlank(oldJsonStr)){
               oldJsonStr="{}";
            }
            //覆盖合并
            Map oldData = JSON.parseObject(oldJsonStr,Map.class);
            oldData.putAll(expandData);
            runtimeService.setVariable(execution.getId(),_TASK_EXPAND_DATA,JSON.toJSONString(oldData));
            String newStr = (String) runtimeService.getVariable(execution.getId(), _TASK_EXPAND_DATA);
            logger.info("============================_TASK_EXPAND_DATA======================"+newStr);
		}
	}

	/**
	 * 保存待办扩展数据
	 * @param entity
	 */

	public void saveTaskExpandData(TaskEntity entity,String orgCodes){
		//从流程变量中获取待办暂存数据
		String taskExpandDataJson = (String) entity.getVariable(_TASK_EXPAND_DATA);
		if(StringUtils.isBlank(taskExpandDataJson)){
			//true 存入基本任务节点数据至扩展数据表（不包括扩展数据）
			Map basicExpandData = new HashMap();
			List<WfTaskExpandPlanEntity> basicPlans = new ArrayList<>();
			TaskExpandDataEntity expandDataEntity = generateExpandData(basicExpandData,basicPlans,entity);
			this.saveData(expandDataEntity);
			return;
		}
		Map expandData = JSON.parseObject(taskExpandDataJson, Map.class);

		//获取待办扩展方案
		String processDefinitionId = entity.getProcessDefinitionId();
		List<WfTaskExpandPlanEntity> plans = workflowTaskExpandPlanService.getExpandPlanByProcessDefinitionId(processDefinitionId);
		if(plans == null || plans.size() == 0){
			//true 存入基本任务节点数据至扩展数据表（不包括扩展数据）
			Map basicExpandData = new HashMap();
			List<WfTaskExpandPlanEntity> basicPlans = new ArrayList<>();
			TaskExpandDataEntity expandDataEntity = generateExpandData(basicExpandData,basicPlans,entity);
			if(StringUtils.isNotBlank(orgCodes)){
				expandDataEntity.setAssigneeOrgCode(orgCodes);
			}
			this.saveData(expandDataEntity);
//			logger.warn("expandPlan is not found !, processDefinitionId is {0}",processDefinitionId);
			return ;
		}

		//生成待办扩展数据存储对象
		TaskExpandDataEntity expandDataEntity = generateExpandData(expandData,plans,entity);
		String backNodeId = WorkflowBackTaskAspectService.getBackNodeId();
		String reBackNodeId = WorkflowReBackTaskAspectService.getReBackNodeId();
		//先判断该节点是否为回退或者取回任务
		if (backNodeId != null || reBackNodeId != null) {
			String actId = entity.getTaskDefinitionKey();
			if (WorkflowThreadLocalUtils.get("isCopyHiTaskExpandData") != null){
				String isCopyHiTaskExpandData = (String) WorkflowThreadLocalUtils.get("isCopyHiTaskExpandData");
			//拿到该节点的上个任务的历史数据
				List<HistoricTaskInstance> historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
					.processInstanceId(entity.getProcessInstanceId()).taskDefinitionKey(actId).orderByHistoricTaskInstanceEndTime().finished()
					.desc().listPage(0, 1);
				if (historicTaskInstance != null && historicTaskInstance.size() != 0 && isCopyHiTaskExpandData.equals("Y")) {
					String hiTaskId= historicTaskInstance.get(0).getId();
					HiTaskExpandDataEntity hiDataEntity = this.selectByHiTaskId(hiTaskId);
					TaskExpandDataEntity dataEntity=new TaskExpandDataEntity();
					dataEntity.setId(hiDataEntity.getId());
					dataEntity.setProcessDefineId(hiDataEntity.getProcessDefineId());
					dataEntity.setTaskId(entity.getId());
					dataEntity.setTaskExpandPlanId(hiDataEntity.getTaskExpandPlanId());
					dataEntity.setProcessInstanceId(hiDataEntity.getProcessInstanceId());
					dataEntity.setTaskNodeId(hiDataEntity.getTaskNodeId());
					dataEntity.setField01(hiDataEntity.getField01());
					dataEntity.setField02(hiDataEntity.getField02());
					dataEntity.setField03(hiDataEntity.getField03());
					dataEntity.setField04(hiDataEntity.getField04());
					dataEntity.setField05(hiDataEntity.getField05());
					dataEntity.setField06(hiDataEntity.getField06());
					dataEntity.setField07(hiDataEntity.getField07());
					dataEntity.setField08(hiDataEntity.getField08());
					dataEntity.setField09(hiDataEntity.getField09());
					dataEntity.setField11(hiDataEntity.getField11());
					dataEntity.setField12(hiDataEntity.getField12());
					dataEntity.setField13(hiDataEntity.getField13());
					dataEntity.setField14(hiDataEntity.getField14());
					dataEntity.setField15(hiDataEntity.getField15());
					dataEntity.setField16(hiDataEntity.getField16());
					dataEntity.setField17(hiDataEntity.getField17());
					dataEntity.setField18(hiDataEntity.getField18());
					dataEntity.setField19(hiDataEntity.getField19());
					dataEntity.setField20(hiDataEntity.getField20());
					this.saveData(dataEntity);
					WorkflowThreadLocalUtils.put("isCopyHiTaskExpandData","N");
					return;
				}
			}
		}
		if(StringUtils.isNotBlank(orgCodes)){
			expandDataEntity.setAssigneeOrgCode(orgCodes);
		}

		this.saveData(expandDataEntity);
		//删除暂存变量
		//runtimeService.removeVariable(entity.getExecutionId(),_TASK_EXPAND_DATA);

	}

	/**
	 * 生成待办扩展数据
	 * @param expandData
	 * @param plans
	 * @param task
	 */
	private TaskExpandDataEntity generateExpandData(Map<String, String> expandData, List<WfTaskExpandPlanEntity> plans, TaskEntity task) {
		//Map<String, String> expandDatas =this.putExpandData(expandData,task);
//		String taskExpandPlanId = plans.get(0).getId();
		String taskExpandPlanId = "";
		if (plans.size() != 0) {
			taskExpandPlanId = plans.get(0).getId();
		}else {
			taskExpandPlanId = task.getId();
		}
		String processInstanceId = task.getProcessInstanceId();
		String processDefineId = task.getProcessDefinitionId();
		String taskId = task.getId();
		String nodeId = task.getTaskDefinitionKey();
		String tenantId = task.getTenantId();

		TaskExpandDataEntity model = new TaskExpandDataEntity();
		redundantData(model,task);
		model.setId(idGenerator.getNextId());
		model.setTaskExpandPlanId(taskExpandPlanId);
		model.setProcessInstanceId(processInstanceId);
		model.setProcessDefineId(processDefineId);
		model.setTaskId(taskId);
		model.setTaskNodeId(nodeId);
		model.setTenantId(tenantId);
		for (WfTaskExpandPlanEntity plan : plans) {
			String fieldKey = plan.getFieldKey();
			String filedColumn = plan.getFieldValue();
			//获取需要存储的值
			String value = expandData.get(fieldKey);
			try {
				PropertyUtils.setProperty(model,filedColumn,value);
			} catch (Exception e) {
				logger.warn("{0} set value failed !!!",filedColumn);
				e.printStackTrace();
			}
		}
		return model;
	}

	/**
	 * 冗余必要的流程数据
	 * @param model
	 * @param task
	 */
	private void redundantData(TaskExpandDataEntity model, Task task) {
		TaskEntity taskEntity = (TaskEntity) task;
		ExecutionEntity processInstance = taskEntity.getProcessInstance();
		Map<String, Object> variables = taskEntity.getVariables();
		String taskDefinitionKey = taskEntity.getTaskDefinitionKey();
		String processDefinitionId = taskEntity.getProcessDefinitionId();

		// camunda-TODO camunda没有cache？
		HistoricProcessInstanceEventEntity hiProcessInstanceCache = Context.getCommandContext().getDbEntityManager().getCachedEntity(HistoricProcessInstanceEventEntity.class, taskEntity.getProcessInstanceId());
		if (hiProcessInstanceCache != null) {
			//启动时缓存有hiProcessInstanceCache
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) this.repositoryService.getProcessDefinition(processDefinitionId);

			model.setProcessDefKey(processDefinition.getKey()); //流程定义键值
			model.setBusinessKey(processInstance.getProcessBusinessKey()); //业务主键
			model.setProcessName(processDefinition.getName());//流程名称

			String category = processDefinition.getCategory();
			model.setProcessClassifyId(category);//流程分类Id

			Map<String, Object> classify = workflowClassifyService.getCodeById(category);
			model.setProcessClassifyCode(MapUtils.getString(classify,"code"));//流程分类编码
			model.setSuperProcessInstanceId(hiProcessInstanceCache.getSuperProcessInstanceId());//父流程实例Id
			model.setProcessStartTime(hiProcessInstanceCache.getStartTime());//流程发起时间
			model.setStartUserId(hiProcessInstanceCache.getStartUserId());//发起人用户
			Object startUserName = ((TaskEntity) task).getVariable("startUserName");
			if(startUserName!=null){
				model.setStartUserName(startUserName.toString());//发起人用户
			}
			model.setIsStartNode("Y");//是否为发起节点
			model.setStartUserDeptCode(MapUtils.getString(variables,AssignmentModel.START_USER_DEPT_ID_KEY));//发起人部门编码
			model.setStartUserRoleCode(MapUtils.getString(variables,AssignmentModel.START_USER_ROLE_ID_KEY));//发起人角色编码
			model.setStartUserParentOrgCode(MapUtils.getString(variables,AssignmentModel.START_USER_PARENT_ORG_ID_KEY));//发起人父级机构编码
			model.setStartUserOrgCode(MapUtils.getString(variables,AssignmentModel.START_USER_ORG_ID_KEY));//发起人机构编码
			model.setTenantId(hiProcessInstanceCache.getTenantId());

		} else {
			//产生新任务有缓存上一步的信息
			List<TaskExpandDataEntity> inCache = Context.getCommandContext().getDbEntityManager().getCachedEntitiesByType(TaskExpandDataEntity.class);
			if (CollectionUtils.isNotEmpty(inCache)) {
				TaskExpandDataEntity cacheEntity = inCache.get(0);
				model.setProcessDefKey(cacheEntity.getProcessDefKey()); //流程定义键值
				model.setBusinessKey(cacheEntity.getBusinessKey());//业务主键
				model.setProcessName(cacheEntity.getProcessName());//流程名称
				model.setProcessClassifyId(cacheEntity.getProcessClassifyId());//流程分类Id
				model.setProcessClassifyCode(cacheEntity.getProcessClassifyCode());//流程分类编码
				model.setSuperProcessInstanceId(cacheEntity.getSuperProcessInstanceId());//父流程实例Id
				model.setProcessStartTime(cacheEntity.getProcessStartTime());//流程发起时间
				model.setStartUserId(cacheEntity.getStartUserId());//发起人用户
				model.setStartUserName(cacheEntity.getStartUserName());//发起人用户
				model.setStartUserDeptCode(cacheEntity.getStartUserDeptCode());//发起人部门编码
				model.setStartUserRoleCode(cacheEntity.getStartUserRoleCode());//发起人角色编码
				model.setStartUserParentOrgCode(cacheEntity.getStartUserParentOrgCode());//发起人父级机构编码
				model.setStartUserOrgCode(cacheEntity.getStartUserOrgCode());//发起人机构编码
				model.setTenantId(cacheEntity.getTenantId());
			}
			List<HistoricTaskInstance> hiTasks = Context.getProcessEngineConfiguration().getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).orderByTaskDueDate().asc().listPage(0, 1);
			if (CollectionUtils.isNotEmpty(hiTasks)) {
				if (hiTasks.get(0).getTaskDefinitionKey().equals(taskEntity.getTaskDefinitionKey())) {
					model.setIsStartNode("Y");//是否为发起节点
				}else {
					model.setIsStartNode("N");//是否为发起节点
				}
			}

		}

		model.setTaskName(taskEntity.getName());//任务名称
		String taskType = MapUtils.getString(variables, SequentialAddedTaskState.TASK_TYPE_KEY);
		model.setTaskType(taskType == null ? SequentialAddedTaskState.NORMAL_TASK:taskType);//任务类型

		model.setTaskCreateTime(taskEntity.getCreateTime());//任务创建时间

		Form form = workflowProcessDefineService.getNodeExtensionProperty(processDefinitionId, taskDefinitionKey, FormImpl.class, FormImpl.class);
		if (form != null) {
			model.setFormScene(form.getFormUrl());
		}		

		TaskInfo buttonInfo = new TaskInfo();
		buttonInfo.setActivityId(taskDefinitionKey);
		buttonInfo.setProcessDefineId(processDefinitionId);
		TaskBean buttonBean = workflowProcessDefineService.findButtonPower(buttonInfo);
		model.setActButton(buttonBean.getActButton());//操作权限,字段长度确认

		String isMulti = workflowProcessDefineService.getIsMulti(processDefinitionId, taskDefinitionKey);
		model.setIsMulti(isMulti);//是否会签

	}

	public void temporarySaveData(String taskId, Map<String,String> expandData){
		TaskExpandDataEntity taskExpandData = this.selectByTaskId(taskId);
		this.saveToProcessInstance(taskId, expandData);
		TaskEntity task = (TaskEntity)taskService.createTaskQuery().taskId(taskId).singleResult();

		if (task == null) return;
		String jsonStr = (String) runtimeService.getVariable(task.getExecutionId(), _TASK_EXPAND_DATA);
		Map expandDataMap = JSON.parseObject(jsonStr, Map.class);
		String processDefinitionId = task.getProcessDefinitionId();
		List<WfTaskExpandPlanEntity> plans = workflowTaskExpandPlanService.getExpandPlanByProcessDefinitionId(processDefinitionId);
		if(plans == null || plans.size() == 0){
			logger.warn("expandPlan is not found !, processDefinitionId is {0}",processDefinitionId);
			return ;
		}
		//生成待办扩展数据存储对象
		TaskExpandDataEntity expandDataEntity = generateExpandData(expandDataMap,plans,task);
		//存储
		if (taskExpandData == null) {
			this.saveData(expandDataEntity);
		}else {
			this.updateDate(expandDataEntity);
		}
	}

	private void saveData(final TaskExpandDataEntity expandDataEntity){
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
//		managementService.executeCommand(new Command<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			public Void execute(CommandContext commandContext) {
				WfTaskExpandDataManager wfTaskExpandDataManager = commandContext.getSession(WfTaskExpandDataManager.class);
				wfTaskExpandDataManager.insert(expandDataEntity);
				return null;

			}
		});
	}
	
	public void updateDate(final TaskExpandDataEntity expandDataEntity){
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
//		managementService.executeCommand(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				WfTaskExpandDataManager wfTaskExpandDataManager = commandContext.getSession(WfTaskExpandDataManager.class);
				wfTaskExpandDataManager.updateTaskExpandData(expandDataEntity);
				return null;

			}
		});
	}

	/**
	 * 根据任务id查询待办扩展数据
	 * @param taskId
	 * @return
	 */
	public TaskExpandDataEntity selectByTaskId(final String taskId) {
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		return executor.execute(new Command<TaskExpandDataEntity>() {
//		return managementService.executeCommand(new Command<TaskExpandDataEntity>() {
			@Override
			public TaskExpandDataEntity execute(CommandContext commandContext) {
				WfTaskExpandDataManager wfTaskExpandDataManager = commandContext.getSession(WfTaskExpandDataManager.class);
				return wfTaskExpandDataManager.selectByTaskId(taskId);
			}
		});

	}

	public void delete(final TaskExpandDataEntity taskExpandDataEntity) {
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
//		managementService.executeCommand(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				WfTaskExpandDataManager wfTaskExpandDataManager = commandContext.getSession(WfTaskExpandDataManager.class);
				wfTaskExpandDataManager.delete(taskExpandDataEntity);
				return null;
			}
		});
	}
	/**
	 * 根据任务id查询历史待办扩展数据
	 * @param taskId
	 * @return
	 */
	public HiTaskExpandDataEntity selectByHiTaskId(final String taskId) {
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		return executor.execute(new Command<HiTaskExpandDataEntity>() {
//		return managementService.executeCommand(new Command<HiTaskExpandDataEntity>() {
			@Override
			public HiTaskExpandDataEntity execute(CommandContext commandContext) {
				WfHiTaskExpandDataManager hiWfTaskExpandDataManager = commandContext.getSession(WfHiTaskExpandDataManager.class);
				return hiWfTaskExpandDataManager.selectByHiTaskId(taskId);
			}
		});

	}

}

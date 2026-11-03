package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.cmd.AddTaskCmd;
import com.tansun.wf.activiti.cmd.DeleteRunningTaskCmd;
import com.tansun.wf.activiti.cmd.StartActivityCmd;
import com.tansun.wf.activiti.cmd.UpdateHiTaskReasonCmd;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.exception.WorkflowException;
import com.tansun.wf.activiti.model.*;
import com.tansun.wf.activiti.util.Page;
import com.tansun.wf.activiti.util.ResultUtil;
import com.tansun.wf.camunda.bpmn.impl.instance.MultiWork;
import com.tansun.wf.camunda.bpmn.instance.Participants;
import com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant;
import com.tansun.wf.decision.service.RuleService;
import com.tansun.wf.platform.service.WfTaskSelectUserService;
import com.tansun.workflow.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.form.FormProperty;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.ProcessInstanceModificationBuilderImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.bpm.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.form.TaskFormDataImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.javax.el.ExpressionFactory;
import org.camunda.bpm.engine.impl.juel.ExpressionFactoryImpl;
import org.camunda.bpm.engine.impl.juel.SimpleContext;
import org.camunda.bpm.engine.impl.persistence.entity.*;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.TransitionInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.ConditionExpressionImpl;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
@Service("workflowEngineService")
@Transactional
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class WorkflowEngineServiceImpl implements IWorkflowEngineService {

	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected ProcessEngine _processEngine;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected FormService formService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	private   IdentityService  identityService;


	@Autowired
	private   WorkflowUserService workflowUserService;

	@Autowired
	private WorkflowTaskService workflowTaskService;

	@Autowired
    private ManagementService managementService;

	@Autowired
	private WorkflowProcessDefineService workflowProcessDefineService;

	@Autowired
    private WorkflowTaskExpandDataService workflowTaskExpandDataService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	private WorkflowClassifyService workflowClassifyService;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	WfTaskSelectUserService taskSelectUserService;

	@Autowired
	private WorkflowHiTaskExpandDataService workflowHiTaskExpandDataService;

	@Autowired
	private WfTaskSelectUserService wfTaskSelectUserService;

	private static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
	private static final String SUCCEED = WorkflowConstants.SUCCEED;
	private static final String FAILED = WorkflowConstants.FAILED;



//	/**
//	 * 根据任务ID查找下一活动配置信息
//	 * @param  taskId  任务ID
//	 * @throws Exception
//	 */
//	public  TaskDefinition   nextTaskDefinition(String  taskId,ValueType  valueType) throws WorkflowException{
//		Task  task  = taskService.createTaskQuery().taskId(taskId).singleResult();
//		ProcessDefinitionEntity def = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
//
//		ActivityImpl destAct = null;
//
//		List<PvmTransition>   pvmList  = null;
//
//		String   destActId  = null;
//
//		PvmActivity  pvmAct  = null;
//
//		SelectUserMode  selectUserMode  = new SelectUserMode();
//
//		selectUserMode.setUserList(new ArrayList<String>());
//
//		ActivityImpl actImpl = this.findActivitiImpl(taskId, task.getTaskDefinitionKey());
//
//		if (actImpl !=null){
//
//			pvmList=actImpl.getOutgoingTransitions();
//			if ( pvmList != null && pvmList.size() >1){
//				for (PvmTransition pvmTr:pvmList){
//					pvmAct =pvmTr.getDestination();
//					if (pvmAct.getProperty("type").equals("userTask")){//userTask
//						destActId=pvmTr.getDestination().getId();
//						destAct=	def.findActivity(destActId);
//						if (destAct.getActivityBehavior() instanceof UserTaskActivityBehavior ){
//							UserTaskActivityBehavior userTaskActivityBehavior =(UserTaskActivityBehavior) destAct.getActivityBehavior();
//							return  userTaskActivityBehavior.getTaskDefinition();
//						}
//					}else {
//						if (pvmAct instanceof ActivityImpl)
//							return  nextUserTaskDefinition((ActivityImpl)pvmAct,valueType);
//					}
//				}
//			}
//
//		}
//
//		return  null;
//
//	}

//
//
//	private   TaskDefinition   nextUserTaskDefinition(ActivityImpl activityImpl,ValueType valueType){
//
//		if (activityImpl== null) return null;
//
//		List<PvmTransition> pvmTrList =activityImpl.getOutgoingTransitions();
//		if (pvmTrList != null){
//			PvmActivity  pvmAct  = null;
//			List<PvmTransition> outTransitionsTemp= null;
//			for (PvmTransition pvmTr :pvmTrList){
//				pvmAct = pvmTr.getDestination();
//				if("exclusiveGateway".equals(pvmAct.getProperty("type"))){ //排他网关
//					outTransitionsTemp = pvmAct.getOutgoingTransitions();
//					if(outTransitionsTemp.size() == 1){
//						return nextUserTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(),valueType);
//					}else if(outTransitionsTemp.size() > 1){
//						for(PvmTransition tr1 : outTransitionsTemp){
//							Object s = tr1.getProperty("conditionText");
//							if (valueType != null&&s !=null){
//
//								Boolean  b= this.getValueExpression(valueType.getKey(), (String) s, valueType.getValue(), valueType.getClazz());
//								if (b){
//									return nextUserTaskDefinition((ActivityImpl)tr1.getDestination(), valueType);
//								}
//							}
//
//						}
//					}
//				}else if ("userTask".equals(pvmAct.getProperty("type"))){
//					return ((UserTaskActivityBehavior)((ActivityImpl)pvmAct).getActivityBehavior()).getTaskDefinition();
//				}
//			}
//		}
//		return  null;
//
//
//
//	}

	/**
	 * 根据当前任务ID查找下一任务的执行者
	 * @param
	 */
	public List<SelectUserMode> findCandidateGroups(TaskInfo taskInfo){

		// camunda-TODO 全面了解完Bpmn相关API后再改造改流程模型相关内容
		String processDefinitionId = taskInfo.getProcessDefinitionId();
		BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);

		ModelElementInstance instance = bpmnModelInstance.getModelElementById(taskInfo.getActivityId());
		FlowNode userTask = null;
		if(instance!=null && instance instanceof FlowNode){
			userTask = (FlowNode)instance;
		}

		SelectUserMode  selectUserMode  = new SelectUserMode();

		List<SelectUserMode> list = new ArrayList<>();

		if(userTask!=null && userTask.getOutgoing().size() == 1){
			SequenceFlow sequenceFlow = userTask.getOutgoing().iterator().next();
			FlowElement flowElement = sequenceFlow.getTarget();
			if (flowElement instanceof UserTask){
				//用户任务，直接输出
				selectUserMode = makeSelectUserMode(flowElement, taskInfo.getTaskId(),  processDefinitionId);
				list.add(selectUserMode);
			}else{
				nextUserTaskSelectUser(processDefinitionId,flowElement,taskInfo,list,false);
			}
		}

		return list;

	}

	/**
	 * 考虑场景：
	 * 1.下一节点是结束节点
	 * 2.下一节点有多个排他网关判断
	 *   2.1排他网关有默认输出
	 * 	 2.2排他网关条件有多个参数组合
	 * 3.下一个节点有规则节点并且有多个
	 * 4.下一个节点有并行网关
	 *
	 * @param processDefinitionId
	 * @param flowElement
	 * @param taskInfo
	 * @param selectUserModes
	 * @return
	 */
	public   void   nextUserTaskSelectUser(String processDefinitionId, FlowElement flowElement, TaskInfo taskInfo, List<SelectUserMode> selectUserModes, boolean isInSubProcess ) {
		// camunda-TODO 全面了解完Bpmn相关API后再改造改流程模型相关内容
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
		//是否在子流程中

		if (flowElement instanceof UserTask) {
			//用户活动
			SelectUserMode model = makeSelectUserMode(flowElement, taskInfo.getTaskId(),  processDefinitionId);
			// camunda-TODO
			String isInSubProcessStr = "N";
//			if(null == bpmnModel.getMainProcess().getFlowElement(flowElement.getId())) {
//				isInSubProcessStr = "Y";
//
//			}else{
//				if(StringUtils.isNotEmpty(taskInfo.getTaskId())){
//					Task task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
//					Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
//					if(execution.getSuperExecutionId() != null){
//						isInSubProcessStr = "Y";
//					}
//				}
//			}
			model.setIsInSubProcess(isInSubProcessStr);
			selectUserModes.add(model);
			return;
		} else if (flowElement instanceof ExclusiveGateway) {
			//排他网关
			ExclusiveGateway exclusiveGateway = (ExclusiveGateway) flowElement;
			Collection<SequenceFlow> outgoingFlows = exclusiveGateway.getOutgoing();
			if (outgoingFlows.size() == 1) {
				//一个分支
				FlowElement target = outgoingFlows.iterator().next().getTarget();
				nextUserTaskSelectUser(processDefinitionId, target, taskInfo, selectUserModes,false);
				return;

			} else {
				//多个分支，进行条件验证
				//同时满足条件的多个分支
				List<SequenceFlow> selectedFlows = new ArrayList<>();
				SequenceFlow defaultFlow = null;

				for (SequenceFlow flow : outgoingFlows) {
					boolean expressResult = calculateExpress(flow.getConditionExpression(), taskInfo.getmPara());
					if (expressResult) {
						selectedFlows.add(flow);
					}

					if (flow==exclusiveGateway.getDefault()) {
						defaultFlow = flow;
					}
				}

				if (CollectionUtils.isNotEmpty(selectedFlows)) {
					for (SequenceFlow selectedFlow : selectedFlows) {
						nextUserTaskSelectUser(processDefinitionId, selectedFlow.getTarget(), taskInfo, selectUserModes,false);
					}
					return;
				} else if (defaultFlow != null) {
					nextUserTaskSelectUser(processDefinitionId, defaultFlow.getTarget(), taskInfo, selectUserModes,false);
					return;

				} else {
					//无法继续找到下一节点
					throw new RuntimeException("互斥网关找不到满足条件的输出！");
				}
			}

		}else if (flowElement instanceof InclusiveGateway) {
			//包容网关
			InclusiveGateway inclusiveGateway = (InclusiveGateway) flowElement;
			Collection<SequenceFlow> outgoingFlows = inclusiveGateway.getOutgoing();
			if (outgoingFlows.size() == 1) {
				//一个分支
				FlowElement target = outgoingFlows.iterator().next().getTarget();
				nextUserTaskSelectUser(processDefinitionId, target, taskInfo, selectUserModes,false);
				return;

			} else {
				//多个分支，进行条件验证
				List<SequenceFlow> selectedFlows = new ArrayList<>();
				SequenceFlow defaultFlow = null;

				for (SequenceFlow flow : outgoingFlows) {
					boolean expressResult = calculateExpress(flow.getConditionExpression(), taskInfo.getmPara());
					if (expressResult) {
						selectedFlows.add(flow);
					}

					if (flow==inclusiveGateway.getDefault()) {
						defaultFlow = flow;
					}
				}

				if (selectedFlows.size() > 0 ) {
					for (SequenceFlow selectedFlow : selectedFlows) {
						nextUserTaskSelectUser(processDefinitionId, selectedFlow.getTarget(), taskInfo, selectUserModes,false);
					}
					return;

				} else if (defaultFlow != null) {
					nextUserTaskSelectUser(processDefinitionId, defaultFlow.getTarget(), taskInfo, selectUserModes,false);
					return;

				} else {
					//无法继续找到下一节点
					throw new RuntimeException("互斥网关找不到满足条件的输出！");
				}
			}

		} else if (flowElement instanceof ParallelGateway) {
			//并行网关
			ParallelGateway parallelGateway = (ParallelGateway) flowElement;
			Collection<SequenceFlow> outgoingFlows = parallelGateway.getOutgoing();

			for (SequenceFlow flow : outgoingFlows) {
				nextUserTaskSelectUser(processDefinitionId, flow.getTarget(), taskInfo, selectUserModes,false);
			}

		} else if (flowElement instanceof ServiceTask) {
			//服务任务
			// camunda-TODO
			ServiceTask serviceTask = (ServiceTask) flowElement;
//			Map<String, List<ExtensionElements>> extensionElements = serviceTask.getExtensionElements();
//			if (extensionElements != null || CollectionUtils.isNotEmpty(extensionElements.get("ruleconfig"))) {
//				//规则节点
//				invokeRule(extensionElements.get("ruleconfig").get(0), taskInfo);
//			}
//
			for (SequenceFlow flow : serviceTask.getOutgoing()) {
				nextUserTaskSelectUser(processDefinitionId,flow.getTarget(), taskInfo, selectUserModes,false);
			}

		} else if (flowElement instanceof ScriptTask) {
			// camunda-TODO
			//脚本任务
//			ScriptTask scriptTask = (ScriptTask) flowElement;
//
//			for (SequenceFlow flow : scriptTask.getOutgoingFlows()) {
//				nextUserTaskSelectUser(processDefinitionId, bpmnModel.getFlowElement(flow.getTargetRef()), taskInfo, selectUserModes,false);
//			}

		} else if(flowElement instanceof CallActivity){
			// camunda-TODO
//			CallActivity callActivity = (CallActivity) flowElement;
//			ProcessDefinition mainProcessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
//			String tenantId = mainProcessDefinition.getTenantId();
//
//			boolean b = flowElement instanceof SubProcess;
//
//			//如果是会签的调用活动（会签子流程）
//			MultiInstanceLoopCharacteristics loopCharacteristics = callActivity.getLoopCharacteristics();
//			if (loopCharacteristics != null) {
//				//会签
//				SelectUserMode model = makeSelectUserMode(flowElement, taskInfo.getTaskId(),  processDefinitionId);
//				model.setSeleMulUserInd(true);
//				model.setType("callActivity");
//				selectUserModes.add(model);
//				return;
//			}
//
//			String calledElement = callActivity.getCalledElement();
//			ProcessDefinition subProcessDefinition = null;
//			if (StringUtils.isBlank(tenantId)) {
//				subProcessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(calledElement).processDefinitionWithoutTenantId().latestVersion().singleResult();
//			} else {
//				subProcessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(calledElement).processDefinitionTenantId(tenantId).latestVersion().singleResult();
//			}
//			if (subProcessDefinition == null) {
//				throw new RuntimeException("查询失败，获取子流程失败，流程键值：" + calledElement + "不存在");
//			}
//			BpmnModel subBpmnModel = repositoryService.getBpmnModel(subProcessDefinition.getId());
//			List<StartEvent> flowElementsOfType = subBpmnModel.getMainProcess().findFlowElementsOfType(StartEvent.class);
//			if(CollectionUtils.isEmpty(flowElementsOfType) || flowElementsOfType.size() >1){
//				throw new RuntimeException("查询失败，子流程" + calledElement + "不存在启动节点或者存在多个启动节点");
//			}
//			List<SequenceFlow> outgoingFlows = flowElementsOfType.get(0).getOutgoingFlows();
//			for (SequenceFlow flow : outgoingFlows) {
//				nextUserTaskSelectUser(subProcessDefinition.getId(), subBpmnModel.getFlowElement(flow.getTargetRef()), taskInfo, selectUserModes,true);
//			}

		} else if(flowElement instanceof SubProcess){
			// camunda-TODO
			//嵌入子流程
//			SubProcess subProcess = (SubProcess) flowElement;
//			Collection<FlowElement> flowElements = subProcess.getFlowElements();
//			if(CollectionUtils.isEmpty(flowElements)){
//				return;
//			}
//			for (FlowElement element : flowElements) {
//				if(element instanceof StartEvent){
//					List<SequenceFlow> outgoingFlows = ((StartEvent) element).getOutgoingFlows();
//					for (SequenceFlow flow : outgoingFlows) {
//						nextUserTaskSelectUser(processDefinitionId, bpmnModel.getFlowElement(flow.getTargetRef()), taskInfo, selectUserModes,false);
//					}
//					break;
//				}
//			}
		}else if(flowElement instanceof EndEvent){
			// 结束节点，若是子流程里的结束节点，需要查询下一个主流程节点
			// camunda-TODO
//			Task task = null;
//			if(StringUtils.isNotBlank(taskInfo.getTaskId())) {
//				task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
//				if (task != null) {
//					Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
//					// 子流程里的结束节点
//					if (execution != null && StringUtils.isNotBlank(execution.getSuperExecutionId())) {
//						Execution superExecution = runtimeService.createExecutionQuery().executionId(execution.getSuperExecutionId()).singleResult();
//						if (superExecution instanceof ExecutionEntity) {
//							ExecutionEntity entity = (ExecutionEntity) superExecution;
//							TaskInfo info = new TaskInfo();
//							info.setmPara(taskInfo.getmPara());
//							info.setProcessDefinitionId(entity.getProcessDefinitionId());
//							info.setActivityId(superExecution.getActivityId());
//							info.setFormData(taskInfo.getFormData());
//							info.setTenantId(taskInfo.getTenantId());
//							info.setOrgId(taskInfo.getOrgId());
//							selectUserModes.addAll(findCandidateGroups(info));
//							return;
//						}
//					}
//				}
//			}
			SelectUserMode model = makeSelectUserMode(flowElement, taskInfo.getTaskId(), processDefinitionId);
			model.setIsInSubProcess("N");
			selectUserModes.add(model);
			return;
		}else if(flowElement instanceof ReceiveTask){

		}else {
			//无法继续找到下一节点
			throw new RuntimeException("无法继续找到下一节点！");
		}

	}

	/**
     * 获取节点信息
     * @return
     */
	private  SelectUserMode makeSelectUserMode(FlowElement flowElement, String taskId, String processDefinitionId){
		// camunda-TODO 全面了解完Bpmn相关API后再改造改流程模型相关内容
		SelectUserMode  selectUserMode  = new SelectUserMode();
		selectUserMode.setUserList(new ArrayList<String>());

		if(flowElement instanceof UserTask){
			UserTask userTask = (UserTask) flowElement;
			//用户活动
			LoopCharacteristics loopCharacteristics = userTask.getLoopCharacteristics();
			if (loopCharacteristics != null) {
				//会签
				selectUserMode.setSeleMulUserInd(true);
				MultiWork  mulWork =	workflowProcessDefineService.getNodeExtensionProperty(processDefinitionId,flowElement.getId(),MultiWork.class,MultiWork.class);
				String  iterateElement = mulWork.getIterateElement();
				selectUserMode.setIterateElement(iterateElement);
			}else {
				//非会签
				selectUserMode.setSeleMulUserInd(false);
			}
			selectUserMode.setType("userTask");
			selectUserMode.setAssignee(userTask.getCamundaAssignee());

			// camunda-TODO
			UserTaskParticipant userTaskParticipant = workflowProcessDefineService.getNodeExtensionProperty(processDefinitionId,flowElement.getId(),UserTaskParticipant.class,UserTaskParticipant.class);
			if (userTaskParticipant != null) {
					// 运行时数据
					if(StringUtils.isNotEmpty(taskId)){
						Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
						this.workflowTaskService.getPostInfos(userTaskParticipant, task, selectUserMode);
						if(task!=null){
							String executionId = task.getExecutionId();
							String startOrgId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_ORG_ID_KEY);
							selectUserMode.setStartOrgId(startOrgId);
							String startRoleId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_ROLE_ID_KEY);
							selectUserMode.setStartRoleId(startRoleId);
							String startDeptId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_DEPT_ID_KEY);
							selectUserMode.setStartDeptId(startDeptId);
							String startParentOrgId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_PARENT_ORG_ID_KEY);
							selectUserMode.setStartParentOrgId(startParentOrgId);
						}
					}else {
						List<PostInfo>  postInfos = workflowProcessDefineService.getPostInfos(userTaskParticipant);
	                    selectUserMode.setPostInfos(postInfos);
					}
			}
		} else if (flowElement instanceof EndEvent){
			//结束节点
			selectUserMode.setType("end");
		}

		selectUserMode.setTaskId(taskId);
		selectUserMode.setNextActId(flowElement.getId());
		selectUserMode.setNextActName(flowElement.getName());

		return  selectUserMode;

	}

    /**
     * 计算条件表达式值
     * @param expression
     * @param valueTypes
     * @return
     */
	private   boolean calculateExpress(Object expression, List<ValueType> valueTypes){
	    if(expression == null || valueTypes == null){
	        return false;
        }

        ExpressionFactory factory  = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();

        for (ValueType valueType : valueTypes) {
            context.setVariable(valueType.getKey(), factory.createValueExpression(valueType.getValue(), valueType.getClazz()));
        }
//		ValueExpression valueExpression = factory.createValueExpression(context, (String) expression, Boolean.class);
//		valueExpression.setValue();

		return (Boolean) factory.createValueExpression(context, ((ConditionExpressionImpl) expression).getTextContent(), Boolean.class).getValue(context);
	}

    /**
	 * camunda-TODO 全面了解完Bpmn相关API后再改造改流程模型相关内容
     * 获取下一个任务节点，选人信息
     * @param taskInfo 参数
     * @param pvmTr 连接下一个任务节点的箭头
     * @param def 流程定义
     * @return
     */
	private  SelectUserMode makeSelectUserMode(TaskInfo taskInfo,PvmTransition pvmTr,ProcessDefinitionEntity def){
//		ActivityImpl destAct = null;
////		Set<Expression> groupSet = null;
////		Set<Expression> userSet  = null;
//
//		String   destActId  = null;
//
//		Expression  assignExp  = null;
//
		SelectUserMode  selectUserMode  = new SelectUserMode();
//
//		selectUserMode.setType("-1");
//
//		selectUserMode.setUserList(new ArrayList<String>());
//
//		destActId=pvmTr.getDestination().getId();
//
//		destAct=	def.findActivity(destActId);
//
//		AssignmentModel assignmentModel = workflowProcessDefineService.getNodeExtensionProperty(destAct.getProcessDefinition().getId(),destActId,"assignment",AssignmentModel.class);
//
//		if (destAct.getActivityBehavior() instanceof UserTaskActivityBehavior ){
//			UserTaskActivityBehavior userTaskActivityBehavior =(UserTaskActivityBehavior) destAct.getActivityBehavior();
//			// 非表达式处理方式
//			assignExp= userTaskActivityBehavior.getTaskDefinition().getAssigneeExpression();
//			selectUserMode.setNextActId(destAct.getId());
//			selectUserMode.setNextActName((String)destAct.getProperty("name"));
//			if (assignExp != null){ //指定具体人
//                selectUserMode.setAssignee(assignExp.getExpressionText());
//			}
//
//
//			selectUserMode.setSeleMulUserInd(false);
//
//		}else if (destAct.getActivityBehavior() instanceof ParallelMultiInstanceBehavior){//会签
//			// camunda-TODO 不知camunda是否支持自定义行为类
//			ParallelMultiInstanceBehavior  multiInstanceBehavior  = (ParallelMultiInstanceBehavior) destAct.getActivityBehavior();
//			if (multiInstanceBehavior.getInnerActivityBehavior() instanceof  UserTaskActivityBehavior){
//				UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) multiInstanceBehavior.getInnerActivityBehavior() ;
//				assignExp= userTaskActivityBehavior.getTaskDefinition().getAssigneeExpression();
//				selectUserMode.setNextActId(destAct.getId());
//				selectUserMode.setNextActName((String)destAct.getProperty("name"));
//                if (assignExp != null){ //指定具体人
//                    selectUserMode.setAssignee(assignExp.getExpressionText());
//                }
//				selectUserMode.setSeleMulUserInd(true);
//
//			}
//
//		}
//        if (assignmentModel != null) {
//            selectUserMode.setCandidateUsers(assignmentModel.getCandidateUsers());
//            selectUserMode.setCandidateRoles(assignmentModel.getCandidateRoles());
//			selectUserMode.setCandidateRoleNames(assignmentModel.getCandidateRoleNames());
//            selectUserMode.setCandidateDepts(assignmentModel.getCandidateDepts());
//            selectUserMode.setCandidateOrgs(assignmentModel.getCandidateOrgs());
//            selectUserMode.setSelectUserModel(assignmentModel.getSelectUserModel());
//        }
		return  selectUserMode;

	}

	public List<WorkItem> findToClaimListByUserId(String userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		// camunda-TODO camunda的该Query没有两者混合的查询，有taskCandidateUser和taskAssignee两个，后续再深入了解
//		List<Task>  tasks =taskService.createTaskQuery().taskCandidateOrAssigned(userId).active().list();
		List<Task>  tasks =taskService.createTaskQuery().taskCandidateUser(userId).active().list();

		List<WorkItem>  results  = new  ArrayList<WorkItem>();
		WorkItem  workItem  = null;
		String    formKey =null;
		for (Task task:tasks){
			workItem  =  new  WorkItem();
			workItem.setCreateTime(sdf.format(task.getCreateTime()));

			ProcessDefinition processDef =this.getProcessDefinition(task.getProcessDefinitionId());
			formKey =this.formService.getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
			workItem.setProcessDefName(processDef.getName());
			workItem.setTaskName(task.getName());
			workItem.setProcessInsId(task.getProcessInstanceId());

			workItem.setTaskId(task.getId());
			workItem.setTaskStatus("claim");
			workItem.setActionFormKey(formKey);
			workItem.setParticipant(task.getAssignee());
			results.add(workItem);
		}
		return results;
	}

	public   List<TaskTraceInfo>     findTaskListByProcessInstanceId(String   processInstanceId){

		TaskTraceInfo  taskTraceInfo  = null;
		List<TaskTraceInfo>  taskTraceList  = new  ArrayList<TaskTraceInfo>();

		List<HistoricTaskInstance>  hisList = this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskId().asc().list();

		HistoricTaskInstance  preTaskInstance = null;
		for (HistoricTaskInstance hisTask :hisList){
			taskTraceInfo  = new  TaskTraceInfo();
			taskTraceInfo.setTaskId(hisTask.getId());
			taskTraceInfo.setTaskName(hisTask.getName());
			taskTraceInfo.setActivityId(hisTask.getTaskDefinitionKey());
			taskTraceInfo.setStartDate(hisTask.getStartTime());
			taskTraceInfo.setCompleteDate(hisTask.getEndTime());
			// camunda-TODO 没有createTime字段，使用startTime代替，可能有问题
//				taskTraceInfo.setCreateDate(hisTask.getCreateTime());
			taskTraceInfo.setCreateDate(hisTask.getStartTime());
			taskTraceInfo.setExecuteUserId(hisTask.getAssignee());
			taskTraceInfo.setDescription(hisTask.getDescription());
			//2017-9-4 12:32:14 添加批注
			List<Comment> comments = taskService.getTaskComments(hisTask.getId());
			taskTraceInfo.setComment(comments != null && comments.size()>0?comments.get(0).getFullMessage():"");
			if (preTaskInstance != null){
				taskTraceInfo.setPreTaskName(preTaskInstance.getName());
				taskTraceInfo.setPreTaskUserId(preTaskInstance.getAssignee());
			}
			taskTraceList.add(taskTraceInfo);
			if (preTaskInstance == null ){
				preTaskInstance = hisTask;
			}else  if (!isJointTask(hisTask.getId())){
				preTaskInstance = hisTask;
			}
		}

		return   taskTraceList;


	}

	public  Page<WorkItem> findAssignOrCandidate(String userId,int pageNumber,int pageSize){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Page<WorkItem>  page  = new  Page<WorkItem>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if (pageNumber<1){
			pageNumber=1;
		}
		int firstResult = pageSize * (pageNumber - 1);
		int maxResults = pageSize* pageNumber;
		// camunda-TODO
//		TaskQuery   query  = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
		TaskQuery   query  = taskService.createTaskQuery().taskCandidateUser(userId);
		List<Task>  tasks =query.listPage(firstResult, maxResults);


		List<WorkItem>  results  = new  ArrayList<WorkItem>();
		WorkItem  workItem  = null;

		for (Task task:tasks){
			workItem  =  new  WorkItem();
			workItem.setCreateTime(sdf.format(task.getCreateTime()));

			ProcessDefinition processDef =this.getProcessDefinition(task.getProcessDefinitionId());
			//formKey =this.formService.getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
			workItem.setProcessDefName(processDef.getName());

			workItem.setTaskName(task.getName());
			workItem.setProcessInsId(task.getProcessInstanceId());
			workItem.setProcessDefineId(task.getProcessDefinitionId());
			workItem.setTaskId(task.getId());
			if (task.getAssignee() == null){
				workItem.setTaskStatus(TaskStatusEnum.CLAIM.getIndex());
			}else
				workItem.setTaskStatus(TaskStatusEnum.HANDLE.getIndex());
			workItem.setDueDate(task.getDueDate());
			workItem.setActionFormKey(task.getFormKey());
			workItem.setParticipant(task.getAssignee());
			results.add(workItem);
		}
		page.setResult(results);
		page.setTotalCount(query.count());
		return  page;

	}

	private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
		if (processDefinition == null) {
			processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
			PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
		}
		return processDefinition;
	}

	public   void  suspendProcessInstance(String processInstanceId ){
		this.runtimeService.suspendProcessInstanceById(processInstanceId);
	}

	public   void  activateProcessInstanceById(String  processInstanceId){
		this.runtimeService.activateProcessInstanceById(processInstanceId);
	}


	public  ProcessInst   submitStartFormData(String processDefinitionKey,Map<String,String>  formProperties  ) throws  WorkflowException{

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();

		if (processDefinition == null){
			throw  new  WorkflowException("Workflow-E-000006");
		}
		ProcessInstance procIns= this.formService.submitStartFormData(processDefinition.getId(), formProperties);
		ProcessInst  processInst  = new  ProcessInst();
		processInst.setProcessDefineId(procIns.getProcessDefinitionId());
		processInst.setProcessInstanceId(procIns.getId());
		// camunda-TODO camunda的流程name还没找到
//		processInst.setProcessName(procIns.getName());

		return  processInst;
	}

	public  String    findProcessDefinitionIdByKey(String  processDefinitionKey) throws  WorkflowException{
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();

		if (processDefinition == null){
			throw  new  WorkflowException("Workflow-E-000006");
		}
		return  processDefinition.getId();
	}

	@Transactional
	public  void  completeTask(String  taskId, Map<String, Object> variables)
			throws WorkflowException{
		this.taskService.complete(taskId, variables);
	}
	public   TaskFormDataVO   getTaskFormData(String  taskId){
		TaskFormDataImpl  taskFormData =  (TaskFormDataImpl) this.formService.getTaskFormData(taskId);
		TaskFormDataVO   taskFormDataVO   = new  TaskFormDataVO();
		taskFormDataVO.setFormPropertyList(new ArrayList<FormPropertyVO>());
		if (taskFormData != null){
			taskFormDataVO.setFormKey(taskFormData.getFormKey());
			FormPropertyVO  formPropertyVO = null;
			FormType  formType = null;
			for (FormProperty formProperty :taskFormData.getFormProperties()){
				formPropertyVO  = new  FormPropertyVO();
				formPropertyVO.setId(formProperty.getId());
				formPropertyVO.setName(formProperty.getName());
				formPropertyVO.setReadable(formProperty.isReadable());
				formPropertyVO.setRequired(formProperty.isRequired());
				formType = new  FormType();
				formType.setName(formProperty.getType().getName());

				if (formType.getName().equals("enum")){
					formType.setInformation(formProperty.getType().getInformation("values"));
				}else if (formType.getName().equals("list")){
					formType.setInformation(formProperty.getType().getInformation("values"));
				}
				formPropertyVO.setType(formType);
				formPropertyVO.setValue(formProperty.getValue());
				formPropertyVO.setWritable(formProperty.isWritable());
				taskFormDataVO.getFormPropertyList().add(formPropertyVO);
			}
		}
		return   taskFormDataVO;
	}

	/**
	 * 根据任务ID判断会签
	 * @param taskId
	 * @return
	 * @throws WorkflowException
	 */
	private  boolean  isJointTask(String taskId){
		HistoricTaskInstanceEntity  hisTaskInstance = (HistoricTaskInstanceEntity) this.historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();

		String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(hisTaskInstance.getProcessInstanceId()).singleResult().getProcessDefinitionId();

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);

		List<ActivityImpl> list = def.getActivities();
		ActivityImpl  actImp = null;
		for (ActivityImpl actTmp :list){
			if (actTmp.getId().equals(hisTaskInstance.getTaskDefinitionKey())){
				actImp = actTmp;
				break;
			}
		}
		if (actImp == null) return  false;

		// camunda-TODO 判断是否会签的方法
//		if (actImp.getActivityBehavior() instanceof ParallelMultiInstanceBehavior){
//			return  true;
//		}

		return   false;
	}
	/**
	 * 根据当前任务ID，查询可以驳回的任务节点
	 *
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findBackAvtivity(String taskId) throws WorkflowException {
		List<ActivityImpl> rtnList = null;
//		if (this.isJointTask(taskId)) {// 会签任务节点，不允许驳回
//			rtnList = new ArrayList<ActivityImpl>();
//		} else {
			rtnList = iteratorBackActivity(taskId, findActivitiImpl(taskId,
					null), new ArrayList<ActivityImpl>(),
					new ArrayList<ActivityImpl>());
//		}
		return reverList(rtnList);
	}


	/**
	 * 审批通过
	 *
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程存储参数
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void passProcess(String taskId, Map<String, Object> variables){
		List<Task> tasks = taskService.createTaskQuery().taskId(taskId)
				.taskDescription("jointProcess").list();
		for (Task task : tasks) {// 级联结束本节点发起的会签任务
			commitProcess(task.getId(), null, null);
		}
		commitProcess(taskId, variables, null);
	}

	/**
	 * 驳回流程
	 *
	 * @throws Exception
	 */
	public void backTask(TaskBean taskBean) {
	    String activityId = taskBean.getActivityId();
	    String taskId = taskBean.getTaskId();
	    String comment = taskBean.getComment();
		if (StringUtils.isEmpty(activityId)) {
			throw new RuntimeException("回退失败,目标节点ID为空!");
		}
		String processInstanceId = taskBean.getProcessInstanceId();
		// 退回意见
		if(StringUtils.isNotBlank(comment)){
			taskService.createComment(taskId, processInstanceId, comment);
		}
		// 退回人
		String userId = taskBean.getUserId();
		if(StringUtils.isNotBlank(userId)){
			taskService.setAssignee(taskId, userId);
		}
		// 流程变量
		if(taskBean.getmPara()!=null){
            taskService.setVariables(taskBean.getTaskId(),taskBean.getmPara());
        }
		// 任务本地变量
		if (taskBean.getLocalVariables() != null) {
			taskService.setVariablesLocal(taskId, taskBean.getLocalVariables());
		}
//
//		HistoricTaskInstance  historicTaskInstance =historyService.createHistoricTaskInstanceQuery().taskId(taskBean.getTaskId()).singleResult();
//		if (historicTaskInstance instanceof HistoricTaskInstanceEntity) {
//			HistoricTaskInstanceEntity hiTask = (HistoricTaskInstanceEntity) historicTaskInstance;
//			workflowHiTaskExpandDataService.taskToHistory(hiTask);
//		}

		ProcessInstanceModificationBuilderImpl processInstanceModification = (ProcessInstanceModificationBuilderImpl)runtimeService.createProcessInstanceModification(processInstanceId);
		// 取消自身的活动
		if(CollectionUtils.isNotEmpty(taskBean.getCancelActivityIds())){
			// 并行节点要取消并行其他分支的兄弟节点
			for (String cancelActivityId : taskBean.getCancelActivityIds()) {
				processInstanceModification.cancelAllForActivity(cancelActivityId);
			}
		}else{
			processInstanceModification.cancelAllForActivity(activityId);
		}
		List<String> nextActivityIds = taskBean.getNextActivityIds();

		Task task = taskService.createTaskQuery().taskId(taskBean.getTaskId()).singleResult();
		BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
		for (String nextActivityId : nextActivityIds) {
			// 从哪个节点开始，并行节点多个
			ModelElementInstance modelElementInstance = bpmnModelInstance.getModelElementById(nextActivityId);
			if(modelElementInstance instanceof UserTask){
				UserTask userTask = (UserTask) modelElementInstance;
				if(userTask.getLoopCharacteristics()!=null){
					// 多工作项标识
					nextActivityId = nextActivityId + "#multiInstanceBody";
				}
			}
			processInstanceModification.startBeforeActivity(nextActivityId);
		}
		processInstanceModification.execute();
	}

	public void moveTo(TaskEntity currentTaskEntity, String targetTaskDefinitionKey) throws WorkflowException
	{
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(currentTaskEntity.getId());
		ActivityImpl activity = ((ProcessDefinitionImpl) processDefinition)
				.findActivity(targetTaskDefinitionKey);
		moveTo(currentTaskEntity, activity);
	}

	private void moveTo(TaskEntity currentTaskEntity, ActivityImpl activity)
	{
		executeCommand(new StartActivityCmd(currentTaskEntity.getExecutionId(), activity));
		executeCommand(new DeleteRunningTaskCmd(currentTaskEntity));
	}
	private void executeCommand(Command<java.lang.Void> command)
	{
		((RuntimeServiceImpl) _processEngine.getRuntimeService()).getCommandExecutor().execute(command);
	}
	/**
	 * 自由流转(根据taskList、nextTaskId、userId)
	 * @param
	 * @param
	 * @throws Exception
	 * @return
	 */
	public TaskBean jumpFlowTask(TaskBean taskBean){
        String processInstanceId = taskBean.getProcessInstanceId();
        if (StringUtils.isBlank(processInstanceId)) {
            return new TaskBean(FAILED, "流程实例id为空");
        }
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (CollectionUtils.isEmpty(list)) {
            return new TaskBean(FAILED, "流程不存在任务，无法自由跳转");
        }
        Task task = list.get(0);
        String id = task.getId();
        final String nextTaskId = taskBean.getNextTaskId();
        Map variables = new HashMap();

//        UserTaskParticipant userTaskParticipant = workflowProcessDefineService.getNodeExtensionProperty(task.getProcessDefinitionId(), nextTaskId, UserTaskParticipant.class, UserTaskParticipant.class);
//        List<PostInfo> postInfos = this.workflowTaskService.getPostInfos(userTaskParticipant, id);
//        variables.put("postInfos", postInfos);
        turnTransition(task.getId(), nextTaskId, variables);
        if(StringUtils.isNotBlank(taskBean.getUserId())){
			//有多个参与者领取
			List<Task> newTasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			Task newTask = newTasks.get(0);
			taskService.claim(newTask.getId(),taskBean.getUserId());
			//删除旧的数据
			wfTaskSelectUserService.deleteUserTaskAssignment(newTask.getId());
			wfTaskSelectUserService.saveUserTaskAssignment(newTask.getId(),task.getTenantId(),taskBean.getUserId());
		}

        return new TaskBean(SUCCEED, "自由跳转成功!");
    }

	/**
	 * 取回流程
	 *
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            取回节点ID
	 * @throws Exception
	 */
	public void callBackTask(String taskId, String activityId)
			throws WorkflowException {
		if (StringUtils.isEmpty(activityId)) {
			throw new WorkflowException("Workflow-E-000005");
		}
		// 查找所有并行任务节点，同时取回
		List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(
				taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
		for (Task task : taskList) {
			commitProcess(task.getId(), null, activityId);
		}
	}

    /**
	 * 中止流程
	 *
	 * @param taskId  任务ID
	 */
	public void endProcess(String taskId) throws  WorkflowException {
		ActivityImpl endActivity = findActivitiImpl(taskId, "end");
		commitProcess(taskId, null, endActivity.getId());
	}


	/**
	 * 转办流程
	 */
	public void transferAssignee(TaskInfo taskInfo) {
        String taskId = taskInfo.getTaskId();
        String comment = taskInfo.getComment();
        Map localVariables = taskInfo.getLocalVariables() == null ? new HashMap<>() : taskInfo.getLocalVariables();
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
		List<String> beforeTransfers = taskService.getVariableLocal(taskId, AssignmentModel.TASK_BEFORE_TRANSFER_ASSIGNEE)
        		== null? (new ArrayList<String>()):(List<String>)taskService.getVariableLocal(taskId, AssignmentModel.TASK_BEFORE_TRANSFER_ASSIGNEE);
        if (beforeTransfers.size() != 0) {
        	taskService.removeVariableLocal(taskId, AssignmentModel.TASK_BEFORE_TRANSFER_ASSIGNEE);
        }
        beforeTransfers = new ArrayList<String>();
		PostInfo transferUser = taskInfo.getTransferUser();
		if ("user".equals(transferUser.getType())){
			taskService.setAssignee(taskId,taskInfo.getTransferUser().getUserId());
		}else {
			taskService.setAssignee(taskId,null);
		}
		taskSelectUserService.deleteUserTaskAssignment(taskId);
		taskSelectUserService.saveUserTaskAssignment(taskId, taskInfo.getTenantId(), transferUser);

		TaskExpandDataEntity taskExpandDataEntity = workflowTaskExpandDataService.selectByTaskId(taskId);
		taskExpandDataEntity.setAssigneeOrgCode(transferUser.getOrgCode());
		workflowTaskExpandDataService.updateDate(taskExpandDataEntity);
//        beforeTransfers.add(userCode);
        taskService.setVariableLocal(taskId, AssignmentModel.TASK_BEFORE_TRANSFER_ASSIGNEE, beforeTransfers);
	}



	/**
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程变量
	 * @param activityId
	 *            流程转向执行任务节点ID<br>
	 *            此参数为空，默认为提交操作
	 * @throws Exception
	 */
	private void commitProcess(String taskId, Map<String, Object> variables,
							   String activityId) {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 跳转节点为空，默认提交操作
		if (StringUtils.isEmpty(activityId)) {
			taskService.complete(taskId, variables);
		} else {// 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}

	/**
	 * 流程转向操作
	 *
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点任务ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
//	private void turnTransition(String taskId, String activityId,
//								Map<String, Object> variables) throws WorkflowException {
//		GenericJumpCmd genericJumpCmd = new GenericJumpCmd(taskId, activityId, variables);
//		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
//		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
//		executor.execute(genericJumpCmd);
//	}

	/**
	 * 流程转向操作
	 *
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点任务ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId,
								Map<String, Object> variables){

//		GenericJumpCmd genericJumpCmd = new GenericJumpCmd(taskId, activityId, variables);
//		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
//		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
//		executor.execute(genericJumpCmd);

		TaskService taskService = processEngine.getTaskService();
		//根据流程实例id获取当前任务信息
		org.camunda.bpm.engine.task.Task task1 = taskService.createTaskQuery().taskId(taskId).singleResult();
		//查询当前任务节点的节点信息 task.getTaskDefinitionKey()为当前节点的定义key
		ActivityImpl currentActivity = qureyCurrentTask(task1.getProcessInstanceId(),task1.getTaskDefinitionKey());
		//查询需要跳转任务节点的信息 taskQuery1.getTargetTaskDefineKey()为需要跳转节点的定义key
		ActivityImpl targetActivity = queryTargetActivity(task1.getProcessDefinitionId(), activityId);
		//通过活动可以获得流程 将要出去的路线，只要更改出去的目的Activity ，就可以实现自由的跳转
		List<PvmTransition> outgoingTransitions = currentActivity.getOutgoingTransitions();

        List<PvmTransition> originPvmTransitionList = new ArrayList<PvmTransition>();
        // 考虑排他网关箭头不显示问题
        boolean isExclusiveGatewayActivityId = false;
        if(outgoingTransitions.size()==1 && outgoingTransitions.get(0).getDestination().getActivityBehavior() instanceof ExclusiveGatewayActivityBehavior){
            PvmActivity exclusiveActivity = outgoingTransitions.get(0).getDestination();
            List<PvmTransition> exclusiveGatewayOutgoingTransitions = exclusiveActivity.getOutgoingTransitions();

            // 判断要提交的节点是在排他网关后面
            for (PvmTransition exclusiveGatewayOutgoingTransition : exclusiveGatewayOutgoingTransitions) {
                String id = exclusiveGatewayOutgoingTransition.getDestination().getId();
                if(StringUtils.equals(activityId,id)){
                    isExclusiveGatewayActivityId = true;
                    break;
                }
            }
        }

        if(isExclusiveGatewayActivityId){
            PvmActivity exclusiveActivity = outgoingTransitions.get(0).getDestination();
            List<PvmTransition> exclusiveGatewayOutgoingTransitions = exclusiveActivity.getOutgoingTransitions();
            for (PvmTransition exclusiveGatewayOutgoingTransition : exclusiveGatewayOutgoingTransitions) {
            	if(!StringUtils.equals(exclusiveGatewayOutgoingTransition.getDestination().getId(),activityId)){
					originPvmTransitionList.add(exclusiveGatewayOutgoingTransition);
				}
            }
            // 去除目标节点之外的其他节点
            exclusiveGatewayOutgoingTransitions.removeAll(originPvmTransitionList);
            String defaultId = (String) exclusiveActivity.getProperty("default");
			((ActivityImpl) exclusiveActivity).setProperty("default",exclusiveGatewayOutgoingTransitions.get(0).getId());
            taskService.complete(task1.getId(),variables);

            // 提交后恢复
			exclusiveGatewayOutgoingTransitions.addAll(originPvmTransitionList);
			((ActivityImpl) exclusiveActivity).setProperty("default",defaultId);

		}else{
            for (PvmTransition pvmTransition : outgoingTransitions) {
                originPvmTransitionList.add(pvmTransition);
            }
            outgoingTransitions.clear();
            TransitionImpl transitionImpl = currentActivity.createOutgoingTransition(activityId);
            transitionImpl.setDestination(targetActivity);
            taskService.complete(task1.getId(),variables);
            currentActivity.getOutgoingTransitions().remove(transitionImpl);
            for (PvmTransition pvmTransition : originPvmTransitionList) {
                outgoingTransitions.add(pvmTransition);
            }
        }
        // 重新刷新流程定义缓存
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor commandExecutor = processEngineConfiguration.getCommandExecutorTxRequired();

		commandExecutor.execute(commandContext -> {

			commandContext.getProcessEngineConfiguration().getDeploymentCache().removeProcessDefinition(currentActivity.getProcessDefinition().getId());
			return null;
		});


	}


	/**
	 * 查询当前的活动节点
	 */
	public ActivityImpl qureyCurrentTask(String processInstanceId,String processDefineKey) {
		//通过流程实例id查询任务
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).listPage(0, 1);
		ActivityImpl currentActivity = queryTargetActivity(tasks.get(0).getProcessDefinitionId(),processDefineKey);
		return currentActivity;
	}
	/**
	 *  根据ActivityId 查询出来想要活动Activity
	 * @param processDefineId
	 * @return
	 */
	public ActivityImpl  queryTargetActivity(String processDefineId,String processDefineKey) {
//		Activity(UserTask_H7ll7rxO#multiInstanceBody)
		ReadOnlyProcessDefinition deployedProcessDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefineId);
		List<ActivityImpl> activities = (List<ActivityImpl>) deployedProcessDefinition.getActivities();
		for (ActivityImpl activityImpl : activities) {
			if (activityImpl.getId().equals(processDefineKey) || activityImpl.getId().contains(processDefineKey)) {
				return activityImpl;
			}
		}
		return null;
	}




	/**
	 * 迭代循环流程树结构，查询当前节点可驳回的任务节点
	 *
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储回退节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 回退节点集合
	 */
	private List<ActivityImpl> iteratorBackActivity(String taskId,
													ActivityImpl currActivity, List<ActivityImpl> rtnList,
													List<ActivityImpl> tempList) throws WorkflowException {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);

		// 当前节点的流入来源
		List<PvmTransition> incomingTransitions = currActivity
				.getIncomingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			ActivityImpl activityImpl = transitionImpl.getSource();
			String type = (String) activityImpl.getProperty("type");
			/**
			 * 并行节点配置要求：<br>
			 * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
			 */
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId
						.lastIndexOf("_") + 1);
				if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
					return rtnList;
				} else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
					parallelGateways.add(activityImpl);
				}
			} else if ("startEvent".equals(type)) {// 开始节点，停止递归
				return rtnList;
			} else if ("userTask".equals(type)) {// 用户任务
				if (isExist(rtnList,activityImpl)){
					return rtnList;
				}else{
					tempList.add(activityImpl);
				}
			} else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
				currActivity = transitionImpl.getSource();
				exclusiveGateways.add(currActivity);
			} else if ("serviceTask".equals(type)){
				currActivity = transitionImpl.getSource();
				iteratorBackActivity(taskId, currActivity, rtnList, tempList);
			}
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : parallelGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 根据同级userTask集合，过滤最近发生的节点
		 */
		currActivity = filterNewestActivity(processInstance, tempList);
		if (currActivity != null) {
			// 查询当前节点的流向是否为并行终点，并获取并行起点ID
			String id = findParallelGatewayId(currActivity);
			if (StringUtils.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
				rtnList.add(currActivity);
			} else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
				currActivity = findActivitiImpl(taskId, id);
			}

			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorBackActivity(taskId, currActivity, rtnList, tempList);
		}
		return rtnList;
	}
	private boolean isExist(List<ActivityImpl> rtnList, ActivityImpl activityImpl) {
		boolean isExist = false;
		for (ActivityImpl activity : rtnList) {
			if (activity.getId().equals(activityImpl.getId())) {
				isExist = true;
			}
		}
		return isExist;
	}
	/**
	 * 反向排序list集合，便于驳回节点按顺序显示
	 *
	 * @param list
	 * @return
	 */
	private List<ActivityImpl> reverList(List<ActivityImpl> list) {
		List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();
		// 由于迭代出现重复数据，排除重复
		for (int i = list.size(); i > 0; i--) {
			if (!rtnList.contains(list.get(i - 1)))
				rtnList.add(list.get(i - 1));
		}
		return rtnList;
	}

	/**
	 * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
	 *
	 * @param activityImpl
	 *            当前节点
	 * @return
	 */
	private String findParallelGatewayId(PvmActivity activityImpl) {
		List<PvmTransition> incomingTransitions = activityImpl
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
//			activityImpl = transitionImpl.getDestination();
			activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId
						.lastIndexOf("_") + 1);
				if ("END".equals(gatewayType.toUpperCase())) {
					return gatewayId.substring(0, gatewayId.lastIndexOf("_"))
							+ "_start";
				}
			}
		}
		return null;
	}

	/**
	 * 根据流入任务集合，查询最近一次的流入任务节点
	 *
	 * @param processInstance
	 *            流程实例
	 * @param tempList
	 *            流入任务集合
	 * @return
	 */
	private ActivityImpl filterNewestActivity(ProcessInstance processInstance,
											  List<ActivityImpl> tempList) {
		while (tempList.size() > 0) {
			ActivityImpl activity_1 = tempList.get(0);
			HistoricActivityInstance activityInstance_1 = findHistoricUserTask(
					processInstance, activity_1.getId());
			if (activityInstance_1 == null) {
				tempList.remove(activity_1);
				continue;
			}

			if (tempList.size() > 1) {
				ActivityImpl activity_2 = tempList.get(1);
				HistoricActivityInstance activityInstance_2 = findHistoricUserTask(
						processInstance, activity_2.getId());
				if (activityInstance_2 == null) {
					tempList.remove(activity_2);
					continue;
				}

				if (activityInstance_1.getEndTime().before(
						activityInstance_2.getEndTime())) {
					tempList.remove(activity_1);
				} else {
					tempList.remove(activity_2);
				}
			} else {
				break;
			}
		}
		if (tempList.size() > 0) {
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 查询指定任务节点的最新记录
	 *
	 * @param processInstance
	 *            流程实例
	 * @param activityId
	 * @return
	 */
	private HistoricActivityInstance findHistoricUserTask(
			ProcessInstance processInstance, String activityId) {
		HistoricActivityInstance rtnVal = null;
		// 查询当前流程实例审批结束的历史节点
		List<HistoricActivityInstance> historicActivityInstances = historyService
				.createHistoricActivityInstanceQuery().activityType("userTask")
				.processInstanceId(processInstance.getId()).activityId(
						activityId).finished()
				.orderByHistoricActivityInstanceEndTime().desc().list();
		if (historicActivityInstances.size() > 0) {
			rtnVal = historicActivityInstances.get(0);
		}

		return rtnVal;
	}



	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}



	/**
	 * 根据任务ID获得任务实例
	 *
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private TaskEntity findTaskById(String taskId) throws WorkflowException {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(
				taskId).singleResult();
		if (task == null) {
			throw new WorkflowException("Workflow-E-000002");
		}
		return task;
	}

	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 *
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	private List<Task> findTaskListByKey(String processInstanceId, String key) {
		return taskService.createTaskQuery().processInstanceId(
				processInstanceId).taskDefinitionKey(key).list();
	}

	/**
	 * 根据任务ID获取对应的流程实例
	 *
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessInstance findProcessInstanceByTaskId(String taskId)
			throws WorkflowException {
		// 找到流程实例
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery().processInstanceId(
						findTaskById(taskId).getProcessInstanceId())
				.singleResult();
		if (processInstance == null) {
			throw new WorkflowException("Workflow-E-000004");
		}
		return processInstance;
	}

	/**
	 * 根据任务ID获取流程定义
	 *
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
			String taskId) throws WorkflowException {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(findTaskById(taskId)
						.getProcessDefinitionId());

		if (processDefinition == null) {
			throw new WorkflowException("流程定义未找到!");
		}

		return processDefinition;
	}

	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 *
	 * @param taskId         任务ID
	 * @param activityId    活动节点ID <br>
	 *                      如果为null或""，则默认查询当前活动节点 <br>
	 *                      如果为"end"，则查询结束节点 <br>
	 *
	 * @return
	 * @throws Exception
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId)
			throws WorkflowException {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (StringUtils.isEmpty(activityId)) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl
						.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
				.findActivity(activityId);

		return activityImpl;
	}

	@Transactional
	public void claimTask(String taskId, String userId) {
		this.taskService.claim(taskId, userId);

	}

	public   void   submitTaskFormData(String  taskId, Map<String,String> variables){
		this.formService.submitTaskFormData(taskId, variables);
	}

	public List<ActivityInfo> findBackAvtivityInfo(String taskId) throws WorkflowException {
		List<ActivityImpl>   lists =   this.findBackAvtivity(taskId);
		List<ActivityInfo>  results  = new  ArrayList<ActivityInfo>();
		if (lists != null){
			ActivityInfo  actInfo  = null;
			for (ActivityImpl actImpl :lists){
				actInfo = new  ActivityInfo();
				actInfo.setId(actImpl.getId());
				if (actImpl.getProperty("name") !=null)
					actInfo.setName(actImpl.getProperty("name").toString());
				results.add(actInfo);
			}
		}
		return  results;
	}


    /**
	 * 改方法废弃，建议使用方法
	 */
	@Deprecated
	public ProcessInst startProcessInstanceByKey(String processDefinitionKey,
												 String userId, String businessKey)
			throws WorkflowException {

		List<ProcessDefinition> list= repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).orderByProcessDefinitionVersion().desc().list();
		if (list == null|| list.size()==0){
			throw  new  WorkflowException("Workflow-E-000006");
		}
		ProcessDefinition processDefinition = list.get(0);
		this.identityService.setAuthenticatedUserId(userId);
		ProcessInstance  procIns =  this.runtimeService.startProcessInstanceById(processDefinition.getId(), businessKey);

		return  this.toProcessInstance(procIns);

	}

	@Transactional
	public ProcessBean startProcessInstanceByKey(ProcessInfo processInfo){
		String tenantId = processInfo.getTenantId();
		String userId = processInfo.getUserId();

		// 预定义变量
		if(StringUtils.isNotBlank(processInfo.getStartUserId())){
			processInfo.getmPara().put("startUserId", processInfo.getStartUserId());
		}
		if(StringUtils.isNotBlank(processInfo.getStartUserName())){
			processInfo.getmPara().put("startUserName", processInfo.getStartUserName());
		}
		if(StringUtils.isNotBlank(processInfo.getStartOrgId())){
			processInfo.getmPara().put(AssignmentModel.START_USER_ORG_ID_KEY, processInfo.getStartOrgId());
		}
		if(StringUtils.isNotBlank(processInfo.getStartRoleId())){
			processInfo.getmPara().put(AssignmentModel.START_USER_ROLE_ID_KEY, processInfo.getStartRoleId());
		}
		if(StringUtils.isNotBlank(processInfo.getStartDeptId())){
			processInfo.getmPara().put(AssignmentModel.START_USER_DEPT_ID_KEY, processInfo.getStartDeptId());
		}

		String processDefinitionKey = processInfo.getProcessDefineKey();
		String processDefineId = processInfo.getProcessDefineId();

		String businessKey = processInfo.getBusinessKey();
		Integer processDefineVersion = processInfo.getProcessDefineVersion();
		Map<String, Object> variables = processInfo.getmPara();
		if(variables == null){
            variables = new HashedMap();
        }

		//2017-9-4 15:03:43 添加流程标题(动态值),用于待办列表展示

		//如果部署事添加tenantId,可能会出现相同的版本号
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		if(StringUtils.isNotBlank(tenantId)){
			processDefinitionQuery.tenantIdIn(tenantId);
		}
		if(StringUtils.isNotBlank(processDefineId)){
			processDefinitionQuery.processDefinitionId(processDefineId);
		}else{
			if(StringUtils.isBlank(processDefinitionKey)){
				return new ProcessBean(FAILED, "流程定义键值或id空");
			}
			processDefinitionQuery.processDefinitionKey(processDefinitionKey);
			if(processDefineVersion!=null){
				processDefinitionQuery.processDefinitionVersion(processDefineVersion);
			}else{
				processDefinitionQuery.latestVersion();
				processDefinitionQuery.orderByProcessDefinitionVersion();
				processDefinitionQuery.desc();
			}
		}

		List<ProcessDefinition> processDefinitions = processDefinitionQuery.listPage(0, 1);

		// 未找到定义流程
		if (processDefinitions == null || processDefinitions.isEmpty()){
			return new ProcessBean(FAILED, "未找到定义流程");
		}
		//根据部署时间区分最新部署的
		ProcessDefinition processDefinition = processDefinitions.get(0);
		Deployment lastDeploy = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
		for (int i = 1; i < processDefinitions.size(); i++) {
			Deployment deploy = repositoryService.createDeploymentQuery().deploymentId(processDefinitions.get(i).getDeploymentId()).singleResult();
			if(deploy.getDeploymentTime().after(lastDeploy.getDeploymentTime())){
				processDefinition = processDefinitions.get(i);
				lastDeploy = deploy;
			}
		}

		//校验发起人权限
		if (StringUtils.equals("Y",processInfo.getIsCheckStartUserPower())){
			ProcessBean validity = this.checkPost((List<String>)variables.get(WorkflowConstants.START_ROLE_ID_LIST),processDefinition,variables);
			if(WorkflowConstants.FAILED.equals(validity.getRetCode())){
				return validity;
			}
		}

		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), businessKey, variables);

		workflowProcessInstanceService.updateSuperProcessInstanceId(processInstance.getProcessInstanceId(),processInfo.getSuperProcessInstanceId());

		String category = processDefinition.getCategory();
		Map<String, Object> classfy= workflowClassifyService.getCodeById(category);
        String code=classfy == null? null : (String) classfy.get("code");

		ProcessBean processBean = new ProcessBean(SUCCEED, "流程启动成功");
		processBean.setProcessDefineId(processInstance.getProcessDefinitionId());
		processBean.setProcessName(processDefinition.getName());
		processBean.setProcessInstanceId(processInstance.getProcessInstanceId());

		// 获取当前待办任务
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
		if(CollectionUtils.isNotEmpty(tasks)){
			Task task = tasks.get(0);
			if(task!=null){
				processBean.setCurrentTaskId(task.getId());
				processBean.setCurrentActivityId(task.getTaskDefinitionKey());
			}
		}
		processBean.setClassfyCode(code);
		return processBean;
	}

	private ProcessBean checkPost(List<String> roleIdList, ProcessDefinition processDefinition, Map<String, Object> variables) {
		ProcessBean result = new ProcessBean(SUCCEED, "");
		result.setExtendedData(new HashMap<String,String>());
		if(roleIdList == null || roleIdList.size() == 0){
			return result;
		}
		StartEvent startEvent = this.findStartEvent(processDefinition);
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setmPara(parseValueType(variables));
		taskInfo.setActivityId(startEvent.getId());
		taskInfo.setProcessDefinitionId(processDefinition.getId());
		Map<String,String> roleMap = this.findCandidateRoles(taskInfo);

		if(roleMap == null || roleMap.isEmpty()){
			return result;
		}
		result.setRetCode(FAILED);
		result.setRetMsg("发起人权限不足");
		for(String role:roleIdList){
			if(roleMap.containsKey(role)){
				result.setRetCode(SUCCEED);
				result.setRetMsg("");
				result.getExtendedData().put("roleName",roleMap.get(role));
				result.getExtendedData().put("roleCode",role);
				return result;
			}
		}
		return result;
	}

	private List<ValueType> parseValueType(Map<String,Object> variables) {
		Set<Map.Entry<String, Object>> entries = variables.entrySet();
		List<ValueType> list = new ArrayList<>();
		for(Map.Entry<String, Object> entry:entries){
			if(entry.getValue() !=null && entry.getValue() instanceof String){
				ValueType v = new ValueType();
				v.setKey(entry.getKey());
				v.setValue(entry.getValue());
				list.add(v);
			}
		}
		return list;
	}

	private Map<String,String> findCandidateRoles(TaskInfo taskInfo) {
		List<SelectUserMode> candidateGroups = this.findCandidateGroups(taskInfo);
		if(candidateGroups==null||candidateGroups.isEmpty()){
			return null;
		}
		Map<String,String> roles = new HashMap<>();
		for (SelectUserMode su:candidateGroups){
			if(su.getPostInfos() != null){
				for(int i=0;i<su.getPostInfos().size();i++){
					PostInfo postInfo = su.getPostInfos().get(i);
					if (Participants.TYPE_ROLE.equals(postInfo.getType())) {
						roles.put(postInfo.getRoleCode(), postInfo.getRoleCode());
					}else if (Participants.TYPE_POST.equals(postInfo.getType())) {
						roles.put(postInfo.getRoleCode(), postInfo.getRoleCode());
					}
					//roles.put(su.getCandidateRoles().get(i),su.getCandidateRoleNames().get(i));
				}
			}
		}
		return roles;
	}

	private StartEvent findStartEvent(ProcessDefinition processDefinition) {
		// camunda-TODO
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinition.getId());
//		Process process = bpmnModel.getProcessById(processDefinition.getKey());
//		Collection<FlowElement> flowElements = process.getFlowElements();
//		for(FlowElement e:flowElements){
//			if(e instanceof StartEvent)
//				return (StartEvent) e;
//		}
		Collection<StartEvent> elements = bpmnModel.getModelElementsByType(StartEvent.class);
		if(CollectionUtils.isNotEmpty(elements)){
			return elements.iterator().next();
		}
		throw new IllegalStateException("无效的流程：未定义开始事件");
	}

	public ProcessInst findProcessInstByProcessInstanceId(String processInstanceId) {
		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
		return  this.toProcessInstance(processInstance);
	}

	private   ProcessInst  toProcessInstance(ProcessInstance processInstance){

		ProcessInst  processInst  = new  ProcessInst();
		processInst.setProcessDefineId(processInstance.getProcessDefinitionId());
		processInst.setProcessInstanceId(processInstance.getId());
		// camunda-TODO camunda没有Name
//		processInst.setProcessName(processInstance.getName());
		processInst.setBusinessKey(processInstance.getBusinessKey());
		return  processInst;
	}

	public ProcessInst findProcessInstByTaskId(String taskId) throws WorkflowException{
		ProcessInstance  processInstance= this.findProcessInstanceByTaskId(taskId);
		return  this.toProcessInstance(processInstance);
	}

	@Override
	public void submitTask(Task task, TaskBean taskBean) {
		Map variables = taskBean.getmPara();
		String comment = taskBean.getComment();
		// 审批意见
		if(StringUtils.isNotBlank(comment)){
			taskService.createComment(task.getId(),task.getProcessInstanceId(),comment);
		}
		String activityId = null;
		if (StringUtils.isNotBlank(taskBean.getActivityId()))  {
			activityId = taskBean.getActivityId();
		}
		
		commitProcess(task.getId(), variables, activityId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public TaskBean findCompletedTaskList(TaskInfo taskInfo){
		return workflowTaskService.findCompletedTaskList(taskInfo);
	}

	@Override
	public TaskBean findPendTaskList(TaskInfo taskInfo){
		return workflowTaskService.findPendTaskList(taskInfo);
	}
	
	@Override
	public  TaskBean findPendOrgTaskList(TaskInfo taskInfo) {
		return  workflowTaskService.findPendOrgTaskList(taskInfo);
		
	}

	public List<TaskBean> getAllClassifyPendTasks(TaskInfo taskInfo){
		return workflowTaskService.getAllClassifyPendTasks(taskInfo);
	}

	/**
	 * 先查分类树,并获取每个分类下的已办表头
	 * @param taskInfo
	 * @return
	 */
	public List<TaskBean> getAllClassifyCompletedTasks(TaskInfo taskInfo){
		return workflowTaskService.getAllClassifyCompletedTasks(taskInfo);
	}

	public TaskBean findButtonPower(TaskInfo taskInfo){
		return workflowProcessDefineService.findButtonPower(taskInfo);
	}

	/**
	 * 获取流程回调类
	 * @param processBean
	 * @return
	 */
	public ProcessBean findProcessHandleClass(ProcessBean processBean){
		return workflowTaskService.findProcessHandleClass(processBean);
	}

	public TaskBean findClassName(TaskInfo taskInfo){
		return workflowTaskService.findClassName(taskInfo);
	}

	public TaskBean findHiTaskByProcessInstanceId(ProcessInfo processInfo){
		return workflowTaskService.findHiTaskByProcessInstanceId(processInfo);
	}

	public TaskBean findProcessVariable(ProcessInfo processInfo){
		return workflowTaskService.findProcessVariable(processInfo);
	}

	public  ProcessBean   findProcessListByInfo( ProcessInfo  processInfo)  {
		ProcessBean   processBean  = new  ProcessBean();
		HistoricProcessInstanceQuery   hisProcessInstQuery =   this.historyService.createHistoricProcessInstanceQuery();
		if (processInfo.getProcessDefineId() != null){
			hisProcessInstQuery.processDefinitionId(processInfo.getProcessDefineId());
		}
		if (processInfo.getProcessInstanceId() !=null){
			hisProcessInstQuery.processInstanceId(processInfo.getProcessInstanceId());
		}
		if (processInfo.getProcessName() !=null){
			hisProcessInstQuery.processDefinitionNameLike(processInfo.getProcessName());
		}
		if (processInfo.getStatus() !=null ){
			if ( "0".equals(processInfo.getStatus())){
				hisProcessInstQuery.unfinished();
			}else if ("1".equals(processInfo.getStatus())){
				hisProcessInstQuery.finished();
			}
		}

		String   currPage  =   processInfo.getCurrentPage();
		if (currPage == null) currPage ="1";
		String   strPageSize = processInfo.getPageSize();
		if (strPageSize== null) strPageSize ="10";
		int firstResult = Integer.parseInt(strPageSize) * (Integer.parseInt(currPage) - 1);
		if (firstResult<0)  firstResult =1;
		int maxResults = Integer.parseInt(currPage) * Integer.parseInt(strPageSize);
		processBean.setCurrentPageIndex(currPage);
		long  count  =hisProcessInstQuery.count();
		processBean.setTotal(String.valueOf(count));
		List<HistoricProcessInstance>  list = hisProcessInstQuery.orderByProcessInstanceStartTime().desc().listPage(firstResult, maxResults);
		List<Map<String,Object>> resultList   = new  ArrayList<Map<String,Object>>();
		processBean.setResultList(resultList);
		Map<String,Object> map = null;


		for (HistoricProcessInstance hisProcessInstance:list){
			map  = new  HashMap<String,Object>();
			// camunda-TODO camunda没有Name
//			map.put("processDefineName", hisProcessInstance.getProcessDefinitionName());
			map.put("businessKey", hisProcessInstance.getBusinessKey());
			map.put("processDefinitionId", hisProcessInstance.getProcessDefinitionId());
			// camunda-TODO camunda没有version?
//			map.put("version", hisProcessInstance.getProcessDefinitionVersion());
			map.put("processInstanceId", hisProcessInstance.getId());
			map.put("beginTime", date2Str( hisProcessInstance.getStartTime()));

			map.put("endTime", date2Str(hisProcessInstance.getEndTime()));
			map.put("applyUser", hisProcessInstance.getStartUserId());
			resultList.add(map);
		}
		return  processBean;


	}

	private   String   date2Str(Date  date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		if (date == null) return  null;
		return sdf.format(date);
	}




	public   void    setProcessActivityCandidateGroups(String processDefKey,String  actDefId,List<String> candidateGroups){
		ProcessDefinition  processDefinition= this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefKey).latestVersion().singleResult();
		processDefinition  = this.getProcessDefinition(processDefinition.getId());
		BpmnModelInstance bpmnModel =this.repositoryService.getBpmnModelInstance(processDefinition.getId());

		// camunda-TODO 理解此段代码的作用，是否有被调用?
//		Process process =bpmnModel.getMainProcess();
//
//		Collection<FlowElement> flowEles = process.getFlowElements();
//
//		UserTask userTask  = null;
//
//		StartEvent  startEvent = null;
//		for (FlowElement  flowEle :flowEles){
//
//			if (flowEle instanceof UserTask){
//				userTask  =  (UserTask)flowEle;
//				if (userTask.getId().equals(actDefId))
//					userTask.setCandidateGroups(candidateGroups);
//			}else  if (flowEle  instanceof  StartEvent){
//				startEvent = (StartEvent)flowEle;
//
//			}
//
//		}



	}

	public void terminateProcess(String processInstanceId,Map<String, Object> variablesMap, String comment, String taskId, String userId){
		if (StringUtils.isBlank(comment)) {
			comment = "强制终止";
		}
		if (StringUtils.isBlank(taskId)){
			List<String> taskIds = new ArrayList<>();
			List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			if (list != null) {
				for (Task task : list) {
					workflowTaskService.setOnlyOneTaskComment(task.getId(), comment, processInstanceId);
					taskService.setVariables(task.getId(), variablesMap);
					if(StringUtils.isNotBlank(userId)){
						taskService.setAssignee(task.getId(),userId);
					}
				}
			}
		}else{
			workflowTaskService.setOnlyOneTaskComment(taskId, comment, processInstanceId);
			taskService.setVariables(taskId, variablesMap);
			if(StringUtils.isNotBlank(userId)){
				taskService.setAssignee(taskId,userId);
			}
		}
		runtimeService.deleteProcessInstance(processInstanceId,"强制终止");
	}

	@Override
	public void setApproveUser(String taskId, String user) {
		Map<String,Object> map  = new  HashMap<String,Object>();
		map.put("approveUser", user);
		this.taskService.setVariables(taskId,map );

	}

    @Override
    public TaskBean queryNextTaskList(TaskInfo taskInfo){
        TaskBean taskBean = new TaskBean();
        if(StringUtils.isBlank(taskInfo.getTaskId())){
            return new TaskBean(FAILED,"任务ID不能为空!");
        }

        Task task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
        if (task == null) {
            return new TaskBean(FAILED,"该任务不存在!");
        }

        String processInstanceId = task.getProcessInstanceId();
        taskInfo.setProcessDefinitionId(task.getProcessDefinitionId());
        taskInfo.setActivityId(task.getTaskDefinitionKey());

		List<Map> taskList = getTaskList(taskInfo, processInstanceId);

        taskBean.setProcessInstanceId(processInstanceId);
        taskBean.setTaskList(taskList);

        ResultUtil.setReturnCode(taskBean, SUCCEED, "获取下一个节点列表成功");
        return taskBean;
    }

	public void unClaimTask(final String taskId) {
        this.taskService.claim(taskId, null);
	}


	public   void   setVariables(String executionId,Map<String,Object> variables){
		this.runtimeService.setVariables(executionId, variables);
	}

	@Override
	public TaskBean rebackTask(TaskBean taskBean) throws WorkflowException {
		final String taskId = taskBean.getTaskId();
		if (StringUtils.isEmpty(taskId)) {
			return new TaskBean(WorkflowConstants.FAILED, "撤回失败，taskId不能为空");
		}

		// 撤回人校验
		String userId = taskBean.getUserId();
		if(StringUtils.isBlank(userId)){
			return new TaskBean(WorkflowConstants.FAILED, "撤回失败，userId不能为空");
		}

		// 流程变量
		if (taskBean.getmPara() == null) {
			taskBean.setmPara(new HashMap());
		}

		// 获取取回任务实例
		HistoricTaskInstance reBakTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		if (reBakTask == null) {
			return new TaskBean(WorkflowConstants.FAILED, "撤回失败，任务id不存在");
		}

		// 已办任务才能撤回
		if (reBakTask.getEndTime() == null){
			return new TaskBean(WorkflowConstants.FAILED, "撤回失败，该任务还未提交");
		}

		// 权限校验
		String assignee = reBakTask.getAssignee();
		if(!StringUtils.equals(userId,assignee)){
			return new TaskBean(WorkflowConstants.FAILED, "对不起，您没有权限");
		}

		// 取得流程实例，如果是最后一个任务，提交后流程结束无法取回
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(reBakTask.getProcessInstanceId()).singleResult();
		if (instance == null) {
			return new TaskBean(WorkflowConstants.FAILED, "撤回失败，流程已经结束");
		}

		// 是否复制历史的扩展字段信息
		if(taskBean.getIsCopyHiTaskExpandData()!=null&&!taskBean.getIsCopyHiTaskExpandData().equals("")){
			WorkflowThreadLocalUtils.put("isCopyHiTaskExpandData",taskBean.getIsCopyHiTaskExpandData());
		}

		// 获取流程定义
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(reBakTask.getProcessDefinitionId());

		BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(reBakTask.getProcessDefinitionId());

		// 获取撤回任务的bpmn信息
		ModelElementInstance modelElementInstance = bpmnModelInstance.getModelElementById(reBakTask.getTaskDefinitionKey());
		UserTask userTask = null;
		if(modelElementInstance instanceof UserTask){
			userTask = (UserTask)modelElementInstance;
			if (userTask.getLoopCharacteristics() != null ) {
				//在会签任务中
				return new TaskBean(WorkflowConstants.FAILED, "撤回失败，多工作项任务不能撤回");
			}
		}else{
			return new TaskBean(WorkflowConstants.FAILED, "撤回失败，非人工节点不能撤回");
		}

		// 是否是串行会签
		boolean isSequential = false;
		LoopCharacteristics loopCharacteristics = userTask.getLoopCharacteristics();
		if(loopCharacteristics != null && loopCharacteristics instanceof MultiInstanceLoopCharacteristics){
			isSequential = ((MultiInstanceLoopCharacteristics) loopCharacteristics).isSequential();
		}
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(reBakTask.getProcessInstanceId()).startedAfter(reBakTask.getStartTime()).list();

		// 获取活动的实例（人工节点，并行多工作项，串行多工作项，互斥网关后，并行网关后，服务节点，脚本节点，接收节点，引用子流程，包容网关后）
		ActivityInstance activityInstance = runtimeService.getActivityInstance(reBakTask.getProcessInstanceId());

		// 人工节点，并行多工作项，串行多工作项，互斥网关后，并行网关后
		ActivityInstance[] childActivityInstances = activityInstance.getChildActivityInstances();

		// serviceTask（异步服务节点）
		TransitionInstance[] childTransitionInstances = activityInstance.getChildTransitionInstances();
		// userTask（人工节点）、multiInstanceBody（会签节点）、receiveTask（接收节点）callActivity（调用子流程）

		ProcessInstanceModificationBuilderImpl processInstanceModification = (ProcessInstanceModificationBuilderImpl)runtimeService.createProcessInstanceModification(reBakTask.getProcessInstanceId());
		processInstanceModification.setModificationReason("beReBack");// 被撤回的节点
		List<String> beReBackTaskIds = new ArrayList<>();
		for (HistoricActivityInstance historicActivityInstance : list) {
			// 去掉撤回节点本身
			if(StringUtils.equals(historicActivityInstance.getTaskId(),reBakTask.getId())){
				continue;
			}
			String activityType = historicActivityInstance.getActivityType();
			// 去掉开始节点、会签节点、排他网关、并行网关、结束节点
			if("startEvent".equals(activityType) || "multiInstanceBody".equals(activityType)
					|| "exclusiveGateway".equals(activityType) || "parallelGateway".equals(activityType)
					|| "noneEndEvent".equals(activityType)){
				continue;
			}

			if("callActivity".equals(activityType)) {
				processInstanceModification.cancelActivityInstance(historicActivityInstance.getId());
				// 获取子流程的实例
				List<HistoricActivityInstance> callActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(historicActivityInstance.getCalledProcessInstanceId()).list();
				for (HistoricActivityInstance callActivityInstance : callActivityInstances) {
					beReBackTaskIds.add(callActivityInstance.getTaskId());
					if("userTask".equals(callActivityInstance.getActivityType()) && callActivityInstance.getEndTime()!=null){
						return new TaskBean(WorkflowConstants.FAILED, "撤回失败,任务已经被处理");
					}
				}
			}else {
				if(historicActivityInstance.getEndTime()!=null){
					return new TaskBean(WorkflowConstants.FAILED, "撤回失败,任务已经被处理");
				}
				beReBackTaskIds.add(historicActivityInstance.getTaskId());
				processInstanceModification.cancelActivityInstance(historicActivityInstance.getId());
			}

			if("userTask".equals(historicActivityInstance.getActivityType()) && historicActivityInstance.getEndTime()!=null){
				return new TaskBean(WorkflowConstants.FAILED, "撤回失败,任务已经被处理");
			}
		}
		// 需要在监听器中delete的任务
		WorkflowThreadLocalUtils.put("beReBack",beReBackTaskIds);
		processInstanceModification.startBeforeActivity(reBakTask.getTaskDefinitionKey());
		processInstanceModification.execute();

		// 调用撤回接口的节点
		UpdateHiTaskReasonCmd reasonCmd = new UpdateHiTaskReasonCmd(reBakTask.getId(), "reBack");
		ProcessEngineConfigurationImpl configuration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor commandExecutor = configuration.getCommandExecutorTxRequired();

		commandExecutor.execute(reasonCmd);

		return  new TaskBean(WorkflowConstants.SUCCEED, "撤回成功!");

	}

	/**
	 * 取回前将该任务涉及到的选人变量赋值为 "rebackSetNull"
	 *
	 */
	private Map setAssigneeExpress(ActivityImpl source){
		HashMap vars = new HashMap();
		if (!(source.getActivityBehavior() instanceof UserTaskActivityBehavior)) {
			return new HashMap();
		}
		TaskDefinition taskDefinition = ((UserTaskActivityBehavior) source.getActivityBehavior()).getTaskDefinition();
		Expression assigneeExpression = taskDefinition.getAssigneeExpression();
		if (assigneeExpression == null) {
			return vars;
		}
		String expressionText = assigneeExpression.getExpressionText();
		if(expressionText.indexOf("${") == 0) {
			String variableName = expressionText.substring(2, expressionText.length() - 1);
			if ("nextUser".equals(variableName)) {
				vars.put(variableName, "rebackSetNull");
			}
		}
		return vars;
	}
	/**
	 * 获取下一步节点列表
	 * @param
	 */
	private List<PvmTransition> getNextUserTask(PvmTransition pvmTr) {
		List<PvmTransition> retTransitionList=new ArrayList();
		PvmActivity pvmAct =pvmTr.getDestination();
		if (pvmAct.getProperty("type").equals("userTask")){//userTask
			retTransitionList.add(pvmTr);
		}	else{
			List<PvmTransition> list= pvmAct.getOutgoingTransitions();
			for(PvmTransition nextpvmTr:list){
				retTransitionList.addAll(this.getNextUserTask(nextpvmTr));
			}
		}
		return retTransitionList;

	}

    /**
     * 加签
     * @param taskBean
     * @return
     * @throws WorkflowException
     */
	@Override
    public TaskBean addGroupUser(TaskBean taskBean) throws WorkflowException {

		HistoricTaskInstance hiTask = historyService.createHistoricTaskInstanceQuery().taskId(taskBean.getTaskId()).singleResult();
		if (hiTask == null) {
			return new TaskBean(FAILED,"加签失败，任务不存在！");
		}

		//根据taskId 找到流程中正在运行的任务
		String taskId = null;
		if ( hiTask.getEndTime() == null) {
			taskId = hiTask.getId();
		}else {
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(hiTask.getExecutionId()).list();
			if (CollectionUtils.isNotEmpty(tasks)) {
				taskId = tasks.get(0).getId();
			}else{
				//查找子流程的任务id TODO 找出全部的子任务
				List<HistoricProcessInstance> childrenProcessInstances = historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(hiTask.getProcessInstanceId()).list();
				if (CollectionUtils.isNotEmpty(childrenProcessInstances)) {
					HistoricProcessInstance childnProcessInstance = childrenProcessInstances.get(0);//同时存在多个子流程不能支持
					List<Task> childTasks = taskService.createTaskQuery().processInstanceId(childnProcessInstance.getId()).list();
					if (CollectionUtils.isNotEmpty(childTasks)) {
						taskId = childTasks.get(0).getId();
					}
				}


			}
		}

		//TODO 空会签能执行加签任务
		if (taskId == null) {
			return new TaskBean(FAILED,"加签失败，找不到加签的任务节点！");
		}


		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (CollectionUtils.isEmpty(taskBean.getAddDeptCodes()) && CollectionUtils.isEmpty(taskBean.getAddTaskUsers()) && CollectionUtils.isEmpty(taskBean.getAddRoleCodes())) {

			if(CollectionUtils.isEmpty(taskBean.getAddTaskUsers())){
				return new TaskBean(FAILED,"加签失败，加签人员不能为空！");
			}


			if(CollectionUtils.isEmpty(taskBean.getAddDeptCodes())){
				return new TaskBean(FAILED,"加签失败，加签部门不能为空！");
			}


			if(CollectionUtils.isEmpty(taskBean.getAddRoleCodes())){
				return new TaskBean(FAILED,"加签失败，加签角色不能为空！");
			}

		}


        //会签节点才允许加签动作
		// camunda-TODO
		UserTask bpmnUserTask = workflowProcessDefineService.getBpmnUserTask(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)bpmnUserTask.getLoopCharacteristics();
        if (loopCharacteristics == null) {
            return new TaskBean(FAILED,"加签失败，非会签节点不能执行加签操作！");
        }

        //2017-12-4 10:30:46 提价批注
        if(StringUtils.isNotBlank(taskBean.getComment())){
			workflowTaskService.setOnlyOneTaskComment(taskId, taskBean.getComment(), task.getProcessInstanceId());
		}

		taskBean.setExecutionId(task.getExecutionId());
		AddTaskCmd command = new AddTaskCmd(taskBean,loopCharacteristics);
		// camunda-TODO
//				managementService.executeCommand(command);
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(command);

        return new TaskBean(SUCCEED, "加签成功");
    }

	/**
	 * 返回会签任务的审批状态
	 * @param taskInfo
	 */
	@Override
	public TaskBean getMultiStatus(TaskInfo taskInfo){
		HistoricTaskInstance hiTask = historyService.createHistoricTaskInstanceQuery().taskId(taskInfo.getTaskId()).singleResult();
		if (hiTask == null) {
			return new TaskBean(FAILED,"查询失败，任务不存在！");
		}

		//根据taskId 找到流程中正在运行的任务 TODO 精确的获取下一个的会签任务
		String taskId = null;
		if ( hiTask.getEndTime() == null) {
			taskId = hiTask.getId();
		}else {
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(hiTask.getExecutionId()).list();
			if (CollectionUtils.isNotEmpty(tasks)) {
				taskId = tasks.get(0).getId();
			}else{
				//查找子流程的任务id TODO 找出全部的子任务
				List<HistoricProcessInstance> childrenProcessInstances = historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(hiTask.getProcessInstanceId()).list();
				if (CollectionUtils.isNotEmpty(childrenProcessInstances)) {
					HistoricProcessInstance childnProcessInstance = childrenProcessInstances.get(0);//同时存在多个子流程不能支持
					List<Task> childTasks = taskService.createTaskQuery().processInstanceId(childnProcessInstance.getId()).list();
					if (CollectionUtils.isNotEmpty(childTasks)) {
						taskId = childTasks.get(0).getId();
					}
				}


			}
		}
		if (taskId == null) {
			return new TaskBean(FAILED,"查询失败，找不到会签的任务节点！");
		}

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();


		//会签节点
		UserTask bpmnUserTask = workflowProcessDefineService.getBpmnUserTask(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		// camunda-TODO
		MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)bpmnUserTask.getLoopCharacteristics();
		if (loopCharacteristics == null) {
			return new TaskBean(FAILED,"查询失败，找不到会签的任务节点！");
		}
		String multiElementKey = WorkflowProcessDefineService.getMultiElementKey(repositoryService, task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		List<HistoricTaskInstance> multiHiTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
		List<Map> results = new ArrayList<>();
		for (HistoricTaskInstance multiHiTask : multiHiTasks) {
			Map item = new HashMap<>();
			item.put("taskId", multiHiTask.getId());
			item.put("activityId", multiHiTask.getTaskDefinitionKey());
			if (multiHiTask.getEndTime() == null) {
				item.put("isFinished", "N");
				// camunda-TODO 会签的变量是否是这样获取?
//				String elementValue = runtimeService.getVariableLocal(multiHiTask.getExecutionId(), multiElementKey);
				Object elementValue = runtimeService.getVariableLocal(multiHiTask.getExecutionId(), multiElementKey);
				item.put("elementValue", elementValue);
			}else{
				item.put("isFinished", "Y");
				HistoricVariableInstance variableInstance = historyService.createHistoricVariableInstanceQuery().variableName(multiElementKey).executionIdIn(multiHiTask.getExecutionId()).singleResult();
				item.put("elementValue", variableInstance == null ? "" :  variableInstance.getValue());

				HistoricVariableInstance votingOpinionInstance = historyService.createHistoricVariableInstanceQuery().variableName("votingOpinion").executionIdIn(multiHiTask.getExecutionId()).singleResult();
				item.put("votingOpinion", votingOpinionInstance == null ? "" : (String) votingOpinionInstance.getValue());

			}
            results.add(item);
		}

		TaskBean taskBean = new TaskBean(SUCCEED, "查询成功!");
		taskBean.setTaskList(results);
		return taskBean;

	}
	/**
	 * 减签
	 * 1.判断任务是否结束；
	 * 2.判断该人是否已经提交任务结束;
	 */
	@Override
	public TaskBean delGroupUser(TaskBean taskBean) throws WorkflowException {

		final List<String> deleteTaskIds = taskBean.getDeleteTaskIds();
		if (CollectionUtils.isEmpty(deleteTaskIds)) {
			return new TaskBean(FAILED, "减签失败,减签的任务不能为空！");
		}
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				RuntimeService runtimeService = commandContext.getProcessEngineConfiguration().getRuntimeService();
				// camunda-TODO
				TaskManager taskEntityManager = Context.getCommandContext().getTaskManager();
				HistoricTaskInstanceManager historicTaskInstanceEntityManager = Context.getCommandContext().getHistoricTaskInstanceManager();
				VariableInstanceManager variableInstanceEntityManager = commandContext.getVariableInstanceManager();
				String deleteReason = "TansunBackDelete";
				boolean cascade = true;
				String processDefinitionId = null;
				String taskDefinitionKey = null;
				String processInstanceId = null;
				ExecutionEntity processInstance = null;

				List<String> multiInputDataItems = new ArrayList<>();

				for (String deleteTaskId : deleteTaskIds) {
					HistoricTaskInstanceEntity hiTask = historicTaskInstanceEntityManager.findHistoricTaskInstanceById(deleteTaskId);
					TaskEntity task = taskEntityManager.findTaskById(deleteTaskId);

					if (hiTask == null) {
						throw new RuntimeException( "减签失败,减签的任务id"+deleteTaskId+"不存在！");
					}
					if (hiTask.getEndTime() != null) {
						throw new RuntimeException( "减签失败,减签的任务id"+deleteTaskId+"已经审批完成！");
					}

					processDefinitionId = task.getProcessDefinitionId();
					taskDefinitionKey = task.getTaskDefinitionKey();
					processInstanceId = task.getProcessInstanceId();


					String multiElementKey = WorkflowProcessDefineService.getMultiElementKey(repositoryService, task.getProcessDefinitionId(), task.getTaskDefinitionKey());
					// camunda-TODO
					multiInputDataItems.add((String)taskService.getVariableLocal(hiTask.getId(), multiElementKey));


					Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
					// camunda-TODO
					ExecutionEntity executionEntity=(ExecutionEntity)execution;
					String parentId=executionEntity.getParentId();
//					String parentId = execution.getParentId();
//					Integer nrOfInstances = runtimeService.getVariableLocal(parentId, "nrOfInstances", Integer.class);
//					Integer nrOfActiveInstances = runtimeService.getVariableLocal(parentId, "nrOfActiveInstances", Integer.class);
//					String parentId = execution.getId();
					Integer nrOfInstances = (Integer) runtimeService.getVariableLocal(parentId, "nrOfInstances");
					Integer nrOfActiveInstances = (Integer) runtimeService.getVariableLocal(parentId, "nrOfActiveInstances");

					runtimeService.setVariableLocal(parentId,"nrOfInstances",nrOfInstances -1);
					runtimeService.setVariableLocal(parentId,"nrOfActiveInstances",nrOfActiveInstances -1);


					String reason = (deleteReason == null || deleteReason.length() == 0) ? TaskEntity.DELETE_REASON_DELETED : deleteReason;
					taskEntityManager.deleteTask(task, reason, cascade,false);

					List<VariableInstanceEntity> variableInstances = variableInstanceEntityManager.findVariableInstancesByExecutionId(execution.getId());
					if (variableInstances != null) {
						for (VariableInstanceEntity variableInstance : variableInstances) {
							variableInstanceEntityManager.delete(variableInstance);
						}
					}
					historicTaskInstanceEntityManager.deleteHistoricTaskInstanceById(hiTask.getId());

					// camunda-TODO
					HistoricActivityInstanceManager historicActivityInstanceEntityManager = commandContext.getHistoricActivityInstanceManager();
					//TODO 删除历史节点

					commandContext.getDbSqlSession().getSqlSession().delete("deleteExecution",(ExecutionEntity) execution);

				}

				String multiInputDataItem = WorkflowProcessDefineService.getMultiInputDataItem(repositoryService, processDefinitionId, taskDefinitionKey);
				// camunda-TODO 如何获取会签的变量?
//				Collection multiInputDataItemValue = runtimeService.getVariables().getVariable(processInstanceId, multiInputDataItem);
				Map<String, Object> variables = runtimeService.getVariables(processInstanceId, new ArrayList<>());
//				Collection multiInputDataItemValue = ariables;
//				if (CollectionUtils.isNotEmpty(multiInputDataItemValue)) {
//					Collection subtract = CollectionUtils.subtract(multiInputDataItemValue, multiInputDataItems);
//					runtimeService.setVariable(processInstanceId,multiInputDataItem,subtract);
//				}

				runtimeService.setVariables(processInstanceId,variables);

				return null;
			}
		});

		ResultUtil.setReturnCode(taskBean, SUCCEED, "减签成功");
		return taskBean;
	}

	@Override
	public List<Map> getTaskList(TaskInfo taskInfo,String processInstanceId) {
        List<Map> taskList = new ArrayList();
        List<SelectUserMode> resultSelectUserMode = this.findCandidateGroups(taskInfo);

		HistoricProcessInstance hiProc = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		List<HistoricTaskInstance> hiTasks = null;
		for (SelectUserMode sum : resultSelectUserMode) {
            Map<String, Object> result = new LinkedHashMap<String, Object>();
            result.put("processInstance", processInstanceId);
            result.put("nextActivityId", sum.getNextActId());
            result.put("nextActivityIName", sum.getNextActName());
            Set<String> candidateUsers = new HashSet<String>();
            if (sum.getAssignee() != null) {
                candidateUsers.add(sum.getAssignee());
            }
            result.put("postInfos", sum.getPostInfos());
            result.put("nextNodeType", sum.getType());
            result.put("nextIsMulti", sum.isSeleMulUserInd());
            result.put("nextIsInSubProcess", sum.getIsInSubProcess());
            result.put("parRadioConfig", sum.getParRadioConfig());
            result.put("allowPreAct", sum.isAllowPreAct());
            result.put("aboutData", sum.getAboutData());
            result.put("aboutDataKey", sum.getAboutDataKey());
            //发起人id
			if(hiProc!=null){
				result.put("startUserId",hiProc.getStartUserId());
			}
            taskList.add(result);
        }
        return taskList;
    }

	/**
	 * 根据流程定义id和第一个节点的id获取属性信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean getFirstTaskDefine(TaskInfo taskInfo){
		TaskBean taskBean = new TaskBean();
		BpmnModelInstance bpmnModel = null;
		if(StringUtils.isNotBlank(taskInfo.getProcessDefinitionId())){
			bpmnModel = repositoryService.getBpmnModelInstance(taskInfo.getProcessDefinitionId());
		}else{
			// 如果没有传 processDefinitionId，默认查询最新的版本
			List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(taskInfo.getProcessDefineKey()).latestVersion().list();
			if(CollectionUtils.isEmpty(processDefinitions)){
				return new TaskBean(FAILED,"未找到定义流程！");
			}
			bpmnModel = repositoryService.getBpmnModelInstance(processDefinitions.get(0).getId());
			taskInfo.setProcessDefinitionId(processDefinitions.get(0).getId());
		}
		// 如果没有传节点id，默认查找第一个节点
		// camunda-TODO
		if(StringUtils.isBlank(taskInfo.getActivityId())){
			Collection<StartEvent> elements = bpmnModel.getModelElementsByType(StartEvent.class);
			// camunda-TODO
//				Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
//				Iterator<FlowElement> iterator = flowElements.iterator();
//				while (iterator.hasNext()){
//					FlowElement next = iterator.next();
//					if(next instanceof StartEvent){
//						taskInfo.setActivityId(next.getId());
//						break;
//					}
//				}
			taskInfo.setActivityId(elements.iterator().next().getId());
		}
		taskBean.setTaskList(getTaskList(taskInfo, ""));

		Definitions definitions = bpmnModel.getDefinitions();
		Collection<Extension> extensions = definitions.getExtensions();
		Iterator<Extension> iterator = extensions.iterator();
		while (iterator.hasNext()){
			// 扩展
			Extension extension = iterator.next();
			if(StringUtils.equals("process_started_class",extension.getDefinition())){
				taskBean.setProcessStartedClassName(WorkflowTaskService.trimQuotes(extension.getTextContent()));
			}
		}
		ResultUtil.setReturnCode(taskBean, SUCCEED, "获取节点信息成功");
		return taskBean;
	}

	/**
	 * 流程结束后获取流程变量信息
	 * @param processInfo
	 * @return
	 */
	public TaskBean findFinishedVariable(ProcessInfo processInfo){
		return workflowTaskService.findFinishedVariable(processInfo);
	}
	
	
	
}

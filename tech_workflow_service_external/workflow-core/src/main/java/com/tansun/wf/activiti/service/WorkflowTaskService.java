package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.handler.QueryHandler;
import com.tansun.wf.activiti.handler.QueryResultHandler;
import com.tansun.wf.activiti.model.*;
import com.tansun.wf.activiti.query.*;
import com.tansun.wf.activiti.util.*;
import com.tansun.wf.camunda.bpmn.impl.instance.Backtype;
import com.tansun.wf.camunda.bpmn.impl.instance.Designform;
import com.tansun.wf.camunda.bpmn.impl.instance.Votingstrategy;
import com.tansun.wf.camunda.bpmn.instance.Participants;
import com.tansun.wf.camunda.bpmn.instance.ProcessAttribute;
import com.tansun.wf.camunda.bpmn.instance.RuleLogicParam;
import com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant;
import com.tansun.wf.decision.ExecutionDB;
import com.tansun.wf.decision.service.RuleService;
import com.tansun.wf.platform.mapper.ActHiTaskinstMapper;
import com.tansun.wf.platform.mapper.ActRuTaskMapper;
import com.tansun.wf.platform.mapper.WfHiTaskExpandDataMapper;
import com.tansun.wf.platform.mapper.WfUserOrgRoleMapper;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import com.tansun.wf.platform.service.WfActHiCommentService;
import com.tansun.wf.platform.service.WfTaskSelectUserService;
import com.tansun.workflow.api.IWorkflowController;
import com.tansun.workflow.api.IWorkflowCustomUserService;
import com.tansun.workflow.model.*;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.ManagementServiceImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.javax.el.ExpressionFactory;
import org.camunda.bpm.engine.impl.javax.el.ValueExpression;
import org.camunda.bpm.engine.impl.juel.*;
import org.camunda.bpm.engine.impl.persistence.entity.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.impl.value.PrimitiveTypeValueImpl.StringValueImpl;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant.*;



/**
 * 流程定义操作服务类
 *
 */
@Service("workflowTaskService")
public class WorkflowTaskService {

	public static void main(String[] args) {
		WorkFlowHttpClientService workFlowHttpClientService =  new WorkFlowHttpClientService("http://localhost:8081/workflow-micro-service","","");
		for (int i = 0; i < 1000; i++) {
			Map map = new HashMap();
			map.put("buttonType","saveBizInf");
			map.put("loginName","zhangsan"+i);
			map.put("password","");
			map.put("realname","张三"+i);
			map.put("repassword","");
			map.put("select",0);
			map.put("sexCd","1");
			map.put("telephone","");
			workFlowHttpClientService.post("http://localhost:8081/workflow-micro-service/usermg/save",JSON.toJSONString(map));
		}

	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;

	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	public static final String IS_PROCESS = "0";//流程基本信息

	public static final String IS_ACT = "1";//节点基本信息

	public static final String IS_EFFECTIVE = "0";//分类是否有效

	/**
	 * 为了兼容优化前的任务查询逻辑，保留旧代码，升级
	 * 至5.3.0 之后，默认使用优化后的任务查询逻辑, 为true则使用3.6.5版本的查询逻辑
	 */
	@Value("${use.365.tasklist.query:false}")
	private boolean use365TaskListQuery;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private QueryHandler queryHandler;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;

	@Autowired
	private WorkflowClassifyCompletedService workflowClassifyCompletedService;

	@Autowired
	private WorkflowClassifyService workflowClassifyService;

	@Autowired
	private WorkflowClassifyPendService workflowClassifyPendService;

	@Autowired
	private WorkflowLeaveTransferService workflowLeaveTransferService;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private WorkflowProcessDefineService workflowProcessDefineService;

	@Autowired
	private WorkflowUserService workflowUserService;

	@Autowired
	private RuleService ruleService;


	@Autowired
	private WorkflowHiTaskPoolExecuteDataService workflowHiTaskPoolExecuteDataService;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ProcessEngine _processEngine;

	@Autowired
	private EditorServiceProxyService editorServiceProxyService;

	@Autowired
	private ActHiTaskinstMapper actHiTaskinstMapper;

	@Autowired
	private ActRuTaskMapper actRuTaskMapper;

	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private WfActHiCommentService wfActHiCommentService;

	@Autowired
	private WfUserOrgRoleMapper  wfUserOrgRoleMapper;

	@Autowired
	private WfTaskSelectUserService taskSelectUserService;

	@Autowired
	private WfHiTaskExpandDataMapper wfHiTaskExpandDataMapper;

	public List<Map<String,Object>> findOrgTaskCount(Map<String,Object> params){
	    List<Map<String,Object>>  list =	this.wfUserOrgRoleMapper.findOrgTaskCount(params);
	    if (list != null && list.size()>0) {
	    	return list;
	    }
	    return null;
	}

	/**
	 * 根据流程实例id查询任务
	 * @param processInstanceId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public TaskBean getTasksByProcessInstanceId(String processInstanceId){
		TaskBean bean = new TaskBean();
		bean.setRetCode(SUCCEED);
		bean.setRetMsg("查询成功!");
		if(StringUtils.isBlank(processInstanceId)){
			bean.setRetCode(FAILED);
			bean.setRetMsg("查询失败,流程实例id为空!");
			return bean;
		}
		bean.setTaskList(new ArrayList<Map>());
		bean.setProcessInstanceId(processInstanceId);
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(instance == null){
			HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if (processInstance == null) {
				return new TaskBean(FAILED, "查询失败,不存在该流程实例!");
			}else{
				bean.setIsFinished("Y");
			}
			return bean;
		}else {
			bean.setIsFinished("N");
		}
		//1. 普通主流程节点
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		if(tasks == null || tasks.size()==0){
			//2. 若当前任务到了子流程里，查询子流程，若会签子流程，查询多个
			List<HistoricProcessInstance> childrenProcessInstances = historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(processInstanceId).list();
			for (HistoricProcessInstance childrenProcessInstance : childrenProcessInstances) {
				List<Task> subTasks = taskService.createTaskQuery().processInstanceId(childrenProcessInstance.getId()).list();
				tasks.addAll(subTasks);
			}
		}

		// 转换
		if(tasks != null && tasks.size()>0){
			for (Task task : tasks) {
				Map<String,Object> taskMap = new HashMap<String,Object>();
				taskMap.put("taskId", task.getId());
				taskMap.put("taskName", task.getName());
				taskMap.put("taskKey", task.getTaskDefinitionKey());
				taskMap.put("assignee", task.getAssignee());
				//候选组id
				List<String> candidateGroupIds = new ArrayList<String>();
				//获选人id
				List<String> candidateUserIds = new ArrayList<String>();
				List<IdentityLink> links = taskService.getIdentityLinksForTask(task.getId());
				for (IdentityLink identityLink : links) {
					if (identityLink.getType().equals(IdentityLinkType.CANDIDATE)) {
						if(null != identityLink.getGroupId()){
							candidateGroupIds.add(identityLink.getGroupId());
						}else if(null != identityLink.getUserId()){
							candidateUserIds.add(identityLink.getUserId());
						}
					}
				}
				taskMap.put("candidateGroupIds", candidateGroupIds);
				taskMap.put("candidateUserIds", candidateUserIds);
				bean.getTaskList().add(taskMap);
			}
		}


		bean.setRetCode(SUCCEED);
		bean.setRetMsg("查询成功!");
		return bean;
	}

	/**
	 * 根据流程实例id获取获取所有下一任务节点信息
	 *
	 * @param
	 * @return
	 */
	public TaskBean getAllNextTaskInfo(TaskInfo taskInfo)  {
		String processDefineId = taskInfo.getProcessDefineId();
		if (processDefineId == null) {
			processDefineId = taskInfo.getProcessDefinitionId();
		}		
		String activityId = taskInfo.getActivityId();
		if(StringUtils.isBlank(processDefineId) || StringUtils.isBlank(activityId)){
			return new TaskBean(FAILED, "查询失败，流程定义id为空或任务节点id为空！");
		}
		// camunda-TODO
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefineId);
		if (bpmnModel == null) {
			return new TaskBean(FAILED, "查询失败，流程定义id不存在！");
		}
		List<UserTaskInfo> userTaskInfos = new ArrayList<UserTaskInfo>();
		FlowElement flowElement = bpmnModel.getModelElementById(activityId);
		UserTask userTask = null;
		if (flowElement == null || !(flowElement instanceof UserTask)) {
			return new TaskBean(FAILED, "查询失败，任务节点id不存在！");
		}
		userTask = (UserTask) flowElement;

		Task task = null;
		if(StringUtils.isNotEmpty(taskInfo.getTaskId())){
			task = taskService.createTaskQuery().taskId(taskInfo.getTaskId()).singleResult();
		}
		boolean isInSubProcess = false;
		if(task!=null){
			HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			if(StringUtils.isNotEmpty(instance.getSuperProcessInstanceId())){
				isInSubProcess = true;
			}
		}
		Collection<SequenceFlow> outgoingFlows = userTask.getOutgoing();
		if (CollectionUtils.isNotEmpty(outgoingFlows)){
			for (SequenceFlow sequenceFlow : outgoingFlows) {
				ArrayList<String> expressions = new ArrayList<>();
				HashMap<String, Object> conditions = new HashMap<>();
				getNextUserTask(sequenceFlow.getId(), processDefineId, userTaskInfos, expressions, conditions, isInSubProcess,taskInfo.getTaskId());
			}
		}
		List<Map> list = BeanMapUtil.objectsToMaps(userTaskInfos);

		for (Map map : list) {
			Map<String, Object> processContext = getProcessContext(task,(String)map.get("taskKey"),(String)map.get("processDefinitionId"));
			map.putAll(processContext);
		}
		TaskBean taskBean = new TaskBean(SUCCEED, "查询成功！");
		taskBean.setTaskList(list);
		return taskBean;
	}

	/**
	 * 递归获取下一节点任务信息
	 * @param flowElementId
	 * @param processDefineId
	 * @param userTaskInfos
	 * @param expressions
	 * @param conditions
	 * @param isInSubProcess
	 */
	private void getNextUserTask(String flowElementId, String processDefineId, List<UserTaskInfo> userTaskInfos, List<String> expressions, Map<String, Object> conditions, boolean isInSubProcess,String taskId){
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefineId);
		//是否在子流程中
		String isInSubProcessStr = "N";
		// camunda-TODO camunda如何判断是否在子流程中？
		Collection<CallActivity> subFlows = bpmnModel.getDocumentElement().getChildElementsByType(CallActivity.class);
		if (subFlows != null && subFlows.size()>0) {
			isInSubProcessStr = "Y";
		}
//		if(isInSubProcess || null == bpmnModel.getMainProcess().getFlowElement(flowElementId)) {
//			isInSubProcessStr = "Y";
//		}

		FlowElement element = bpmnModel.getModelElementById(flowElementId);

		if(element instanceof UserTask){
			//如果是用户任务，获取信息
			UserTask userTask = (UserTask) element;
			//判断是否为会签
			MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
			final UserTaskInfo userTaskInfo = new UserTaskInfo();
			//子流程的用户节点需要用子流程的流程定义id获取
			userTaskInfo.setProcessDefinitionId(processDefineId);
			String collectionVariable = null;
			if (loopCharacteristics != null) {
				// camunda-TODO
//				collectionVariable = loopCharacteristics.getInputDataItem();
				collectionVariable = loopCharacteristics.getCamundaElementVariable();
				userTaskInfo.setIsMulti("true");
			} else {
				userTaskInfo.setIsMulti("false");
			}
			userTaskInfo.setTaskKey(userTask.getId());
			userTaskInfo.setTaskName(userTask.getName());
			userTaskInfo.setCollectionVariable(collectionVariable);
			userTaskInfo.setConditions(conditions);
			userTaskInfo.setExpressions(expressions);
			// camunda-TODO
			userTaskInfo.setAssignee(userTask.getCamundaAssignee());

			userTaskInfo.setIsInSubProcess(isInSubProcessStr);
			// camunda-TODO
//			userTaskInfo.setCandidateUsers(userTask.getCandidateUsers());
//			userTaskInfo.setCandidateGroups(userTask.getCandidateGroups());
			userTaskInfo.setCandidateUsers(userTask.getCamundaCandidateUsersList());
			userTaskInfo.setCandidateGroups(userTask.getCamundaCandidateGroupsList());

			//组装参与者
	        UserTaskParticipant  taskParticipant = workflowProcessDefineService.getNodeExtensionProperty(processDefineId,userTask.getId(),UserTaskParticipant.class,UserTaskParticipant.class);
			if (taskParticipant != null) {
			    List<PostInfo> postInfos = this.getPostInfos(taskParticipant,taskId);
				userTaskInfo.setPostInfos(postInfos);	
//				userTaskInfo.setCandidateUsers(assignmentModel.getCandidateUsersList());
//				userTaskInfo.setCandidateRoles(assignmentModel.getCandidateRolesList());
//				userTaskInfo.setCandidateDepts(assignmentModel.getCandidateDepartmentsList());
//				userTaskInfo.setCandidateOrgs(assignmentModel.getCandidateOrganizationsList());
//				userTaskInfo.setSelectUserModel(assignmentModel.getSelectUserModel());
			}
			userTaskInfos.add(userTaskInfo);
			return ;
		}else if(element instanceof EndEvent){//
			EndEvent endEvent = (EndEvent) element;
			UserTaskInfo userTaskInfo = new UserTaskInfo();
			userTaskInfo.setTaskKey(element.getId());
			userTaskInfo.setTaskName(StringUtils.isBlank(endEvent.getName())?"结束":endEvent.getName());
			userTaskInfo.setIsEndNode("Y");
			userTaskInfo.setIsInSubProcess(isInSubProcessStr);
			userTaskInfo.setConditions(conditions);
			userTaskInfo.setExpressions(expressions);
			userTaskInfos.add(userTaskInfo);
			return ;
		}else if(element instanceof SequenceFlow){
			//如果是箭头,获取箭头目标元素,继续递归
			SequenceFlow sequenceFlow = (SequenceFlow) element;
			if(sequenceFlow.getConditionExpression() != null){
				// camunda-TODO
//				expressions.add(sequenceFlow.getConditionExpression());
//				parserExpression(sequenceFlow.getConditionExpression(), conditions);
				expressions.add(sequenceFlow.getConditionExpression().getTextContent());
				parserExpression(sequenceFlow.getConditionExpression().getTextContent(), conditions);
			}
			// camunda-TODO
			String targetRef = sequenceFlow.getTarget().getId();
			getNextUserTask(targetRef, processDefineId, userTaskInfos,expressions, conditions, isInSubProcess,taskId);
		}else if(element instanceof Gateway){
			//如果是网关，获取输出元素继续递归
			Gateway gateway = (Gateway) element;
			// camunda-TODO
			Collection<SequenceFlow> outgoingFlows = gateway.getOutgoing();
			for (SequenceFlow sequenceFlow : outgoingFlows) {
				ArrayList<String> newVariableArray = new ArrayList<String>();
				Map newVariableMap = new HashMap<String,Object>();
				if(expressions == null){
					expressions = new ArrayList<String>();
				}else{
					newVariableArray.addAll(expressions);
				}

				if(conditions == null){
					conditions = new HashMap<String, Object>();
				}else{
					newVariableMap.putAll(conditions);
				}
				getNextUserTask(sequenceFlow.getId(), processDefineId, userTaskInfos,newVariableArray, newVariableMap, isInSubProcess,taskId);
			}
		}else if(element instanceof CallActivity){
			CallActivity callActivity = (CallActivity) element;
			ProcessDefinition mainProcessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefineId).singleResult();
			String tenantId = mainProcessDefinition.getTenantId();

			String calledElement = callActivity.getCalledElement();
			ProcessDefinition subProcessDefinition = null;
			if (StringUtils.isBlank(tenantId)) {
				// camunda-TODO
				subProcessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(calledElement).latestVersion().singleResult();
			} else {
				// camunda-TODO
				subProcessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(calledElement).tenantIdIn(tenantId).latestVersion().singleResult();
			}
			if (subProcessDefinition == null) {
				throw new RuntimeException("查询失败，获取子流程失败，流程键值：" + calledElement + "不存在");
			}
			BpmnModelInstance subBpmnModel = repositoryService.getBpmnModelInstance(subProcessDefinition.getId());
			// camunda-TODO 如何判断子流程
//			List<StartEvent> flowElementsOfType = subBpmnModel.getMainProcess().findFlowElementsOfType(StartEvent.class);
//			if(CollectionUtils.isEmpty(flowElementsOfType) || flowElementsOfType.size() >1){
//				throw new RuntimeException("查询失败，子流程" + calledElement + "不存在启动节点或者存在多个启动节点");
//			}
//			List<SequenceFlow> outgoingFlows = flowElementsOfType.get(0).getOutgoingFlows();
//			for (SequenceFlow outgoingFlow : outgoingFlows) {
//				getNextUserTask(outgoingFlow.getId(), subProcessDefinition.getId(), userTaskInfos,expressions, conditions ,true);
//			}

		} else if(element instanceof SubProcess){
			//嵌入子流程
			SubProcess subProcess = (SubProcess) element;
			Collection<FlowElement> flowElements = subProcess.getFlowElements();
			if(CollectionUtils.isEmpty(flowElements)){
				return;
			}
			for (FlowElement flowElement : flowElements) {
				if(flowElement instanceof StartEvent){
					// camunda-TODO
					Collection<SequenceFlow> outgoingFlows = ((StartEvent) flowElement).getOutgoing();
					for (SequenceFlow outgoingFlow : outgoingFlows) {
						getNextUserTask(outgoingFlow.getId(), processDefineId, userTaskInfos,expressions, conditions ,true,taskId);
					}
					break;
				}
			}
		}
	}



	/**
	 * 解析表达式
	 * 形如：${"abc" == "N"}
	 * @param expression
	 * @param conditons
	 */
	private void parserExpression(String expression, Map<String, Object> conditons){

		ExpressionFactory factory = new ExpressionFactoryImpl();
		SimpleContext context = new SimpleContext();

		ValueExpression e = factory.createValueExpression(context, expression, Boolean.class);

		if (e instanceof TreeValueExpression){
			Map<String,Object> map = new HashMap<>();
			TreeValueExpression treeValueExpression = (TreeValueExpression) e;
			ExpressionNode expressionNode = expressionNode(treeValueExpression);
			if (expressionNode instanceof AstEval) {
				AstEval astEval = (AstEval) expressionNode;
				AstNode child = astEval.getChild(0);
				getVariableKey(map, child);

			}
			conditons.putAll(map);
		}
	}

	private  void getVariableKey(Map<String,Object> variable,AstNode astNode){
		if (!(astNode instanceof AstBinary)){
			return;
		}
		AstBinary astBinary = (AstBinary) astNode;
		AstNode left = astBinary.getChild(0);
		AstNode right = astBinary.getChild(1);
		if (left instanceof  AstIdentifier){
			String name = ((AstIdentifier) left).getName();
			variable.put(name, right.eval(null,null));
		}else if(right instanceof AstIdentifier){
			String name = ((AstIdentifier) right).getName();
			variable.put(name, left.eval(null,null));
		}else if(left instanceof AstDot){
			String name = "";
			name = getFullKey((AstDot) left, name);
			variable.put(name,right.eval(null,null));
		}else if (right instanceof AstDot){
			String name = "";
			name = getFullKey((AstDot) left, name);
			variable.put(name,left.eval(null,null));
		}else {

			getVariableKey(variable,left);
			getVariableKey(variable,right);
		}
	}

	private  String getFullKey(AstDot astDot,String key)  {

		key =    key + astDot.toString().replace(" ","") ;
		Object prefix = null;
		try {
			Method getPrefix = AstProperty.class.getDeclaredMethod("getPrefix");
			getPrefix.setAccessible(true);
			prefix = getPrefix.invoke(astDot);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (prefix instanceof AstDot){
			return getFullKey((AstDot) prefix, key);
		}else {
			AstIdentifier astIdentifier = (AstIdentifier) prefix;
			key =  astIdentifier.getName()  + key ;
		}


		return key;
	}

	private  ExpressionNode expressionNode(TreeValueExpression treeValueExpression){
		ExpressionNode o = null;
		try {
			Field node = treeValueExpression.getClass().getDeclaredField("node");
			node.setAccessible(true);
			return (ExpressionNode) node.get(treeValueExpression);
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 	查询逻辑
	 *  1.拼装需要返回的待办扩展字段
	 *  2.拼装待办扩展字段的查询条件
	 *  3.设置分页
	 *  4.设置正常的查询条件
	 *  5.返回结果
	 *     createTime
	 *     processDefineName
	 *     taskId
	 *     processInstanceId
	 *     processDefinitionId
	 *     businessKey
	 *     activityId
	 *     activityName
	 *     dueDate
	 *     formKey
	 *     description
	 *     扩展结果
	 *   6.去除 participant,processTitle 两个返回结果
	 *
	 * 获取待办列表
	 * @param taskInfo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TaskBean findPendTaskList(TaskInfo taskInfo) {
		if(StringUtils.isBlank(taskInfo.getTaskPoolStatus()) && StringUtils.isBlank(taskInfo.getIsAllot())){
			//设置默认值表示无任务池概念，普通查代办
			taskInfo.setTaskPoolStatus("0");
		}
		TaskBean taskBean = new TaskBean();
		if (!"1".equals(taskInfo.getIsAllot())
				&& !("0".equals(taskInfo.getTaskPoolStatus()) || "1".equals(taskInfo.getTaskPoolStatus())
				|| "2".equals(taskInfo.getTaskPoolStatus())
				|| "3".equals(taskInfo.getTaskPoolStatus()))) {
			ResultUtil.setReturnCode(taskBean, FAILED, "获取数据失败，isAllot或taskPoolStatus参数有误");
			return taskBean;
		}

		if ("1".equals(taskInfo.getIsAllot())
				&& ("0".equals(taskInfo.getTaskPoolStatus()) || "1".equals(taskInfo.getTaskPoolStatus())
				|| "2".equals(taskInfo.getTaskPoolStatus())
				|| "3".equals(taskInfo.getTaskPoolStatus()))) {
			ResultUtil.setReturnCode(taskBean, FAILED, "获取数据失败，isAllot和taskPoolStatus不能同时传参");
			return taskBean;
		}
//
		List<WfTaskExpandPlanEntity> plans = this.getExpandPlan(taskInfo);
		Map<String, String> classifyCodeMapName = workflowClassifyService.getClassifyCodeMapName();
		String wfClassifyName = classifyCodeMapName.get(taskInfo.getWfClassifyCode());


		List<String> fieldMapping = WfExpandPlanUtil.generateFieldMapping(plans);
		List<String> fieldCondition = generateFieldCondition(taskInfo,plans);
		List<Map> columnsList = generateColumnsMapping(plans);
		columnsList.addAll(getRegularPendTaskColumns());

		//设置查询条件
		// camunda-TODO
		WfTaskQuery query = new WfTaskQuery();

	
		query.userId(taskInfo.getUserId());
		query.processCreateTimeBefore(taskInfo.getProcessCreateTimeBefore());
		query.processCreateTimeAfter(taskInfo.getProcessCreateTimeAfter());
		query.processDefinitionId(taskInfo.getProcessDefineId());
		query.processInstanceId(taskInfo.getProcessInstanceId());
		query.taskDefinitionKey(taskInfo.getActivityId());
		query.taskNameLike(taskInfo.getTaskName());
	
//		query.fieldMapping(fieldMapping);
//		query.fieldCondition(fieldCondition);
		query.taskId(taskInfo.getTaskId());
		query.classifyCode(taskInfo.getWfClassifyCode());
		if (StringUtils.isNotBlank(taskInfo.getTenantId())) {
			query.tenantId(taskInfo.getTenantId());
		}
		if (taskInfo.getTenantIds() != null) {
			query.tenantIds(taskInfo.getTenantIds());
		}
		
		query.roleList(taskInfo.getRoleList());
		query.businessKey(taskInfo.getBusinessKey());
		query.businessKeyLike(taskInfo.getBusinessKeyLike());
		query.claimTimeBefore(taskInfo.getClaimTimeBefore());
		query.claimTimeAfter(taskInfo.getClaimTimeAfter());
		query.processDefineName(taskInfo.getProcessDefineName());
		query.processDefineNameLike(taskInfo.getProcessDefineNameLike());
		query.processDefineName(taskInfo.getProcessDefineName());
		query.processDefineNameLike(taskInfo.getProcessDefineNameLike());
		query.processDefineKey(taskInfo.getProcessDefineKey());
		query.processDefineKeys(taskInfo.getProcessDefineKeys());
		query.processDefineKeyLike(taskInfo.getProcessDefineKeyLike());
		query.createTimeBefore(taskInfo.getCreateTimeBefore());
		query.createTimeAfter(taskInfo.getCreateTimeAfter());
		query.createTimeSort(taskInfo.getCreateTimeSort());
		//		query.is365Query(use365TaskListQuery);
		query.unequalActivityId(taskInfo.getUnequalActivityId());
		query.unequalActivityName(taskInfo.getUnequalActivityName());
		if(StringUtils.isNotBlank(taskInfo.getOrgId())){
			query.orgList(Arrays.asList(taskInfo.getOrgId()));
		}
		if ("1".equals(taskInfo.getIsAllot())) {
			query.isAllot(taskInfo.getIsAllot());
		}else {
			query.taskPoolStatus(taskInfo.getTaskPoolStatus());
		}
		//获取分页
		int currentPage = StringUtils.isNumericSpace(taskInfo.getCurrentPage()) ? Integer.parseInt(taskInfo.getCurrentPage()):1;
		int pageSize = StringUtils.isNumericSpace(taskInfo.getPageSize()) ? Integer.parseInt(taskInfo.getPageSize()):10;
		int firstResult = pageSize * (currentPage - 1);

		query.setCommandExecutor(((ManagementServiceImpl)this.managementService).getCommandExecutor());
		//查询内容
		List list = query.listPage(firstResult, pageSize);
		long count = query.count();

		//转换内容
		for (Object obj : list) {
			Map<String,Object> resultSet = (Map<String, Object>) obj;
			resultSet.put("createTime", DateUtil.formatDate(DateUtil.toDate(resultSet.get("createTime")),"yyyy-MM-dd HH:mm:ss.SSS"));
			resultSet.put("dueDate", DateUtil.formatDate(DateUtil.toDate(resultSet.get("dueDate"))));
			resultSet.put("claimTime", DateUtil.formatDate(DateUtil.toDate(resultSet.get("claimTime"))));
			Object processStartTime = resultSet.get("processStartTime");
			resultSet.put("processStartTime", DateUtil.formatDate(DateUtil.toDate(processStartTime)));
			resultSet.put("totalSpendHour", calculateTime(DateUtil.toDate(processStartTime),new Date()));
			resultSet.put("taskStatus","1");
			readableTaskType(resultSet);
			if("Y".equals(taskInfo.getIsShowPreTask())){
				resultSet.putAll(getPreTaskInfo(resultSet));
			}
			resultSet.put("powerUsers",getPowerUsers(resultSet));

			String actButton = MapUtils.getString(resultSet, "actButton");
			if(StringUtils.isNotBlank(actButton) ) {
				resultSet.put("actButton", JSONArray.parse(actButton));
			}else {
				resultSet.put("actButton","");
			}

			String candidateRoles = MapUtils.getString(resultSet, "candidateRoles");
			if(StringUtils.isNotBlank(candidateRoles) ) {
				resultSet.put("candidateRoles", JSONArray.parse(candidateRoles));
			}

			String candidateDepts = MapUtils.getString(resultSet, "candidateDepts");
			if(StringUtils.isNotBlank(candidateDepts) ) {
				resultSet.put("candidateDepts", JSONArray.parse(candidateDepts));
			}

			String candidateOrgs = MapUtils.getString(resultSet, "candidateOrgs");
			if(StringUtils.isNotBlank(candidateOrgs) ) {
				resultSet.put("candidateOrgs", JSONArray.parse(candidateOrgs));
			}

		}
		addUserName(list);
		taskBean.setTaskList(list);
		taskBean.setColumns(columnsList);
		taskBean.setWfClassifyCode(taskInfo.getWfClassifyCode());
		taskBean.setWfClassifyName(wfClassifyName);
		taskBean.setTotal(count+"");
		taskBean.setCurrentPageIndex(currentPage+"");
		return taskBean;

	}

	/**
	 * powerUsers 返回有效处理人,当有处理人时返回处理人,没有处理人就返回所有候选人
	 * @param resultSet
	 * @return
	 */
	private List<String> getPowerUsers(Map<String, Object> resultSet) {
		final List<String> powerUsers = new ArrayList<>();
		if(resultSet.containsKey("assignee") && resultSet.get("assignee")!=null){
			String assginee= MapUtils.getString(resultSet,"assignee");
			powerUsers.add(assginee);
		}else {
			final String taskId = MapUtils.getString(resultSet, "taskId");
			ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
			CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
			executor.execute(new Command<Void>() {
				//			managementService.executeCommand(new Command<Void>() {
				@Override
				public Void execute(CommandContext commandContext) {
					IdentityLinkManager identityLinkManager = commandContext.getIdentityLinkManager();
					List<IdentityLinkEntity> identityLinks = identityLinkManager.findIdentityLinkByTaskUserGroupAndType(taskId, null, null, IdentityLinkType.CANDIDATE);
					for (IdentityLinkEntity identityLink : identityLinks) {
						if (StringUtils.isNotBlank(identityLink.getUserId())) {
							powerUsers.add(identityLink.getUserId());
						}
					}
					return null;
				}
			});

		}
		return powerUsers;
	}

	private Map getPreTaskInfo(Map<String, Object> resultSet) {
		final String processInstanceId = MapUtils.getString(resultSet,"processInstanceId");
		final String createTime = MapUtils.getString(resultSet,"createTime");
		final String beginTime = MapUtils.getString(resultSet,"beginTime");
		final String actId = MapUtils.getString(resultSet,"activityId");
		final String databaseType = this._processEngine.getProcessEngineConfiguration().getDatabaseType();

		// camunda-TODO
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("maxResults", 1);
		params.put("firstResult", 0);
		params.put("processInstanceId", processInstanceId);
		params.put("createTime", createTime == null? beginTime: createTime);
		params.put("actId", actId);
		params.put("databaseType", databaseType);
		List<Map<String,Object>> preTask = actHiTaskinstMapper.selectPreHiTask(params);

//		List<Map<String,Object>> preTask = managementService.executeCustomSql(new AbstractCustomSqlExecution<ActHiTaskinstMapper,  List<Map<String,Object>>>(ActHiTaskinstMapper.class) {
//			@Override
//			public List<Map<String, Object>> execute(ActHiTaskinstMapper actHiTaskinstMapper) {
//
//				return actHiTaskinstMapper.selectPreHiTask(params);
//			}
//		});
		Map<String, Object> resultMap = preTask == null || preTask.size()==0 ?  new HashMap() : preTask.get(0);
		String preEndDate = DateUtil.formatDate(DateUtil.toDate(resultMap.get("preEndDate")));
		resultMap.put("preEndDate", preEndDate);
		return resultMap;
	}

	private void readableTaskType(Map<String, Object> resultSet) {
		String taskType = (String)resultSet.get("taskType");
		if (StringUtils.isNotBlank(taskType)) {
			if (SequentialAddedTaskState.ADD_TASK.equals(taskType)) {
				resultSet.put("taskType", "addTask");
			} else if (SequentialAddedTaskState.IGNORE_TASK.equals(taskType)) {
				resultSet.put("taskType", "ignoreTask");
			} else {
				resultSet.put("taskType", "normalTask");
			}
		} else {
			resultSet.put("taskType", "normalTask");
		}
	}

	private List<Map<String,Object>> getRegularPendTaskColumns() {
		List<Map<String,Object>> columnsList = new ArrayList<>();
		Map<String,Object> columnsMap = new HashMap<String,Object>();
		columnsMap.put("processDefinitionId", "业务流程定义ID");
		columnsMap.put("processDefineName", "业务流程定义名称");
		columnsMap.put("processInstanceId", "业务流程实例ID");
		columnsMap.put("activityId", "节点ID");
		columnsMap.put("activityName", "节点名称");
		columnsMap.put("businessKey", "业务主键");
		columnsMap.put("taskId", "任务实例ID");
		columnsMap.put("createTime", "创建时间");
		columnsMap.put("formKey", "表单键值");
		columnsMap.put("formScene", "表单应用场景");
		columnsMap.put("participant", "参与者");
		columnsMap.put("dueDate", "预期时间");
		columnsMap.put("processStartTime", "流程启动时间");
		columnsMap.put("superProcessInstanceId", "父流程实例id");
		columnsMap.put("totalSpendHour", "流程总耗时");
		columnsMap.put("claimTime", "任务领取时间");
		columnsMap.put("comment", "批注");
		columnsMap.put("processTitle", "流程主题");
		columnsMap.put("taskType", "任务类型");
		columnsMap.put("candidateRoles", "候选角色");
		columnsMap.put("candidateDepts", "候选部门");
		columnsMap.put("candidateOrgs", "候选机构");
		columnsMap.put("selectUserModel", "处理人模式");
		columnsMap.put("preActId", "上一节点Id");
		columnsMap.put("preAct", "上一节点名称");
		columnsMap.put("preEndDate", "上一节点结束时间");
		columnsMap.put("preUser", "上一节节点处理人");
		columnsMap.put("preUserName", "上一节节点处理人");

		Set<String> keys = columnsMap.keySet();
		for(String key :keys){
			Map<String,Object> columns = new HashMap<>();
			columns.put("field", key);
			columns.put("name", columnsMap.get(key));
			columnsList.add(columns);
		}
		return columnsList;
	}

	private String getConfigViewId(String processDefineId, String activityId) {
		JSONObject jsonObject = workflowProcessDefineService.getNodeExtensionProperty(processDefineId, activityId, Designform.class,JSONObject.class);
		if(null == jsonObject){
			return null;
		}
		return jsonObject.getString("id");
	}

	/**
	 * 是否为开始节点
	 * @param procDefId
	 * @param taskDefKey
	 * 实现逻辑,只要判断待办
	 * @return
	 */
	public String isStartNode(String procDefId,String taskDefKey){
		// camunda-TODO
//        Collection<FlowElement> flowElements = repositoryService.getBpmnModel(procDefId).getMainProcess().getFlowElements();
		List<String> firstNodes = new ArrayList<String>();
//        for (FlowElement e: flowElements) {
//            if(e instanceof StartEvent){
//                List<SequenceFlow> outgoings = ((StartEvent) e).getOutgoingFlows();
//                if(outgoings == null || outgoings.size() == 0){
//                    break;
//                }
//                for (SequenceFlow seq: outgoings) {
//                    firstNodes.add(seq.getTargetRef());
//                }
//                break;
//            }
//        }
		return firstNodes.contains(taskDefKey)?"Y":"N";
	}

	/**
	 * 获取流程回调类
	 * @param processBean
	 * @return
	 */
	public ProcessBean findProcessHandleClass(ProcessBean processBean){
		if (StringUtils.isEmpty(processBean.getProcessDefineId())) {
			processBean.setRetCode(FAILED);
			return processBean;
		}
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processBean.getProcessDefineId());

		Process process = bpmnModel.getModelElementsByType(Process.class).iterator().next();

		ExtensionElements  extensionElements =  process.getExtensionElements();
		if (extensionElements != null) {
			Collection<ProcessAttribute>  processAttributes = extensionElements.getChildElementsByType(ProcessAttribute.class);
			if (processAttributes != null && processAttributes.size()>0) {
				Map<String, String> map = new HashMap<String, String>();
				ProcessAttribute  processAttribute  = processAttributes.iterator().next();
				map.put("process_started_class", trimQuotes(processAttribute.getProcessStartClass()));
				map.put("process_completed_class",trimQuotes(processAttribute.getProcessCompleteClass()));
				processBean.setExtendedData(map);
			}
		}
		processBean.setRetCode(SUCCEED);
		return processBean;
	}

	/**
	 * 获取业务处理类类名
	 * @param taskInfo
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public TaskBean findClassName(TaskInfo taskInfo) {
//		TaskBean taskBean = new TaskBean();
//		String processDefinitionId = taskInfo.getProcessDefineId();
//		String taskId = taskInfo.getTaskId();
//		String processStatus = taskInfo.getProcessStatus();
//		String className = "";
//		if (StringUtils.isNotEmpty(processStatus) && StringUtils.isNotEmpty(processDefinitionId)) {
//			if (IS_ACT.equals(taskInfo.getProcessStatus())) {
//				if (StringUtils.isNotEmpty(taskId)) {
//					ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
//					if (processDefinition != null) {
//						String activitiId = taskService.createTaskQuery().taskId(taskId).singleResult().getTaskDefinitionKey();
//						if (StringUtils.isNotEmpty(activitiId)) {
//							ProcessConfig processConfig = workflowProcessConfigService
//									.getProcessConfigByProcDefIdAndActId(processDefinitionId, activitiId, processStatus);
//							if (processConfig != null) {
//								className = processConfig.getBasReceiveClass();
//								if (StringUtils.isEmpty(className)) {
//									taskBean.setRetCode(SUCCEED);
//									taskBean.setRetMsg("no message!");
//								}else {
//									taskBean.setRetCode(SUCCEED);
//									taskBean.setRetMsg("success!");
//								}
//							}else {
//								taskBean.setRetCode(SUCCEED);
//								taskBean.setRetMsg("not found!");
//							}
//						}else {
//							taskBean.setRetCode(FAILED);
//							taskBean.setRetMsg("fail!");
//						}
//						taskBean.setActClassName(className);
//					}
//				}
//			}else if (IS_PROCESS.equals(processStatus)) {
//				ProcessConfig processConfig = workflowProcessConfigService
//						.getProcessClassByProcDefId(processDefinitionId, processStatus);
//				if (processConfig != null) {
//					String processStartedClassName = processConfig.getProcessStartedClass();
//					if (StringUtils.isNotEmpty(processStartedClassName)) {
//						taskBean.setProcessStartedClassName(processStartedClassName);
//					}
//					String processCompletedClassName = processConfig.getProcessCompletedClass();
//					if (StringUtils.isNotEmpty(processCompletedClassName)) {
//						taskBean.setProcessCompletedClassName(processCompletedClassName);
//					}
//					taskBean.setRetCode(SUCCEED);
//					taskBean.setRetMsg("success!");
//				}else {
//					taskBean.setRetCode(SUCCEED);
//					taskBean.setRetMsg("not found!");
//				}
//			}
//		}else {
//			taskBean.setRetCode(FAILED);
//			taskBean.setRetMsg("fail!");
//		}
		TaskBean taskBean = new TaskBean();
		String className = "";
		if (StringUtils.isEmpty(taskInfo.getProcessDefinitionId()) || StringUtils.isEmpty(taskInfo.getTaskDefineKey())) {
			taskBean.setRetCode(FAILED);
			return taskBean;
		}
		// camunda-TODO
		//流程定义id
		BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(taskInfo.getProcessDefinitionId());
		//任务id
		ModelElementInstance modelElementInstance = bpmnModelInstance.getModelElementById(taskInfo.getTaskDefineKey());
		if(modelElementInstance==null){
		   //没有找到
            taskBean.setRetCode(FAILED);
            taskBean.setRetMsg("找不到该任务定义key！");
            return taskBean;
        }else {
            FlowNode flowNode = (FlowNode) modelElementInstance;
            ExtensionElements extensionElements = flowNode.getExtensionElements();
            //获取userTask节点的子元素
//            Collection<UserTaskAssignment> list = extensionElements.getChildElementsByType(UserTaskAssignment.class);
//            if (list != null && list.size() >0) {
//            	className= list.iterator().next().getClassName();
//            }
//		BpmnModel bpmnModel = repositoryService.getBpmnModel(taskInfo.getProcessDefineId());
//		Map<String, List<ExtensionElement>> extensionElementMap = bpmnModel.getFlowElement(taskInfo.getActivityId()).getExtensionElements();
//		logger.info("extensionElementMap:========================"+extensionElementMap);
//		List<ExtensionElement> extensionElementsList = extensionElementMap.get("receive_class");
//		logger.info("extensionElementsList:========================"+extensionElementsList);
//		if(CollectionUtils.isNotEmpty(extensionElementsList)&&extensionElementsList.size()>0){
//			for (int j = 0; j < extensionElementsList.size(); j++) {
//				if ("receive_class".equals(extensionElementsList.get(j).getName())) {
//					String elementTextStr = extensionElementsList.get(j).getElementText();
//					className = trimQuotes(elementTextStr);
//				}
//			}
//		}
			taskBean.setRetCode(SUCCEED);
			taskBean.setActClassName(className);
			return taskBean;
		}
	}

	/**
	 * 根据流程实例id获取该流程实例已办任务列表
	 * @param processInfo
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TaskBean findHiTaskByProcessInstanceId(ProcessInfo processInfo) {
		TaskBean taskBean = new TaskBean();
		if (StringUtils.isEmpty(processInfo.getProcessInstanceId())  ||
				StringUtils.isEmpty(processInfo.getTaskId())) {
			taskBean.setRetCode(FAILED);
			return taskBean;
		}
		Task task = taskService.createTaskQuery().taskId(processInfo.getTaskId()).singleResult();
		if (task == null) {
			return new TaskBean(FAILED, "该任务已不存在");
		}
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInfo.getProcessInstanceId()).singleResult();
		if (historicProcessInstance == null || historicProcessInstance.getEndTime() !=null) {
			return new TaskBean(SUCCEED, "查询成功!");
		}
//		List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInfo.getProcessInstanceId())
//				.orderByHistoricActivityInstanceStartTime().asc().finished().list();
		List<HistoricActivityInstance> activityInstances = this.findHiTaskBeforeCurrentNodes(task);
		String activityId = task.getTaskDefinitionKey();
		Set set = new HashSet();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// camunda-TODO
		UserTask userTask = (UserTask)repositoryService.getBpmnModelInstance(historicProcessInstance.getProcessDefinitionId()).getModelElementById(task.getTaskDefinitionKey());
		List<HistoricActivityInstance> activityInstancesExcludeParallelTask = this.activityInstancesExcludeParallelTask(activityInstances, task, userTask);
		for (HistoricActivityInstance activityInstance : activityInstancesExcludeParallelTask) {
			if (activityInstance.getActivityType().equals("userTask") || activityInstance.getActivityType().equals("parallelGateway")) {
				if (activityInstance.getActivityType().equals("parallelGateway")) {
					// camunda-TODO
					ParallelGateway parallelGateway = (ParallelGateway)repositoryService.getBpmnModelInstance(activityInstance.getProcessDefinitionId()).getModelElementById(activityInstance.getActivityId());
					if (parallelGateway.getIncoming().size() > parallelGateway.getOutgoing().size()){ // 结束并行
						continue;
					}
					Collection<SequenceFlow> incomingFlows =userTask.getIncoming(); //任务输入箭头

					if (parallelGateway.getId().equals(incomingFlows.iterator().next().getSource().getId()) && parallelGateway.getIncoming().size() < parallelGateway.getOutgoing().size()) {
						//并行外的任务
						continue;
					}
				}
				if (set.add(activityInstance.getActivityId())) {
					Map<String, Object> map = new HashMap<String,Object>();
					map.put("activitiId", activityInstance.getActivityId());
					map.put("name", activityInstance.getActivityName());
					map.put("type", activityInstance.getActivityType());
					map.put("lastAssignee", activityInstance.getAssignee());
					list.add(map);
				}
			}

		}

		//去重
		List<Map<String, Object>> noRepeatList = new ArrayList<Map<String, Object>>();
		Set<String> activitiIds = new HashSet<>();

		for (Map<String, Object> objectMap : list) {
			String activitiId = MapUtils.getString(objectMap, "activitiId");
			if(!activitiIds.contains(activitiId)){
				activitiIds.add(activitiId);
				noRepeatList.add(objectMap);
			}
		}
		taskBean.setRetCode(SUCCEED);
		taskBean.setCreditHiTaskList(noRepeatList);
		return taskBean;
	}

	public List<HistoricActivityInstance> findHiTaskBeforeCurrentNodes(Task task){
		List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.orderByHistoricActivityInstanceStartTime().asc().finished().list();
		return activityInstances;
	}

	public List<HistoricActivityInstance> activityInstancesExcludeParallelTask(List<HistoricActivityInstance> activityInstances, Task task, UserTask userTask){
		List<HistoricActivityInstance> historicActivityInstances = Lists.newArrayList();

		// camunda-TODO
		MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
		if (loopCharacteristics != null  || (task.getProcessInstanceId().equals(task.getExecutionId()))) {
			return activityInstances;
		}

//		if (task.getProcessInstanceId().equals(task.getExecutionId())) {
//			return activityInstances;
//			return this.findHiTaskBeforeCurrentNodes(task);
//		}
		// camunda-TODO
		FlowElement flowElement = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId()).getModelElementById(task.getTaskDefinitionKey());
		FlowElement parallelGatewayBeforeTask = this.findParallelGatewayBeforeTask(flowElement, task.getProcessDefinitionId());
		List<String> findAllTaskBetweenParallelGateway = this.findAllTaskBetweenParallelGateway(parallelGatewayBeforeTask, task.getProcessDefinitionId(), userTask);
		for(HistoricActivityInstance activityInstance:activityInstances){
			String activityId = activityInstance.getActivityId();
			if (findAllTaskBetweenParallelGateway.contains(activityId)) {
				continue;
			}
			historicActivityInstances.add(activityInstance);
		}
		return historicActivityInstances;
	}

	//反向
	public FlowElement findParallelGatewayBeforeTask(FlowElement flowElement, String processDefinitionId){
		// camunda-TODO
		List<SequenceFlow> incomingFlows = (ArrayList)((FlowNode) flowElement).getIncoming();
		if (CollectionUtils.isEmpty(incomingFlows)){
			return flowElement;
		}
		// camunda-TODO
		String sourceId = incomingFlows.get(0).getSource().getId();
		FlowElement sourceRefFlowElement = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(sourceId);
		if (!(sourceRefFlowElement instanceof ParallelGateway)) {
			return this.findParallelGatewayBeforeTask(sourceRefFlowElement, processDefinitionId);
		}else {
			if (this.isStartParallelGateway(sourceRefFlowElement)) {
				return sourceRefFlowElement;
			}
			return this.findParallelGatewayBeforeTask(sourceRefFlowElement, processDefinitionId);
		}
	}

	//正向(不包含当前节点所在分支)
	public List<String> findAllTaskBetweenParallelGateway(FlowElement flowElement, String processDefinitionId,
														  UserTask userTask){
		List<String> lists = Lists.newArrayList();
		// camunda-TODO
		List<SequenceFlow> outgoingFlows = (ArrayList)((FlowNode) flowElement).getOutgoing();
		for(SequenceFlow outgoingFlow:outgoingFlows){
			List<String> singleLineLists = Lists.newArrayList();
			// camunda-TODO
			String targetId = outgoingFlow.getTarget().getId();
			FlowElement targetRefFlowElement = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(targetId);
			singleLineLists = this.findSingleLineTaskBetweenParallelGateway(singleLineLists,
					targetRefFlowElement, processDefinitionId, userTask);
			lists.addAll(singleLineLists);
		}
		return lists;
	}

	public List<String> findSingleLineTaskBetweenParallelGateway(List<String> singleLineLists, FlowElement flowElement,
																 String processDefinitionId, UserTask userTask){
		if (!(flowElement instanceof ParallelGateway)) {
			//单线上存在当前任务id则单线节点list不保留
			if (userTask.getId().equals(flowElement.getId())) {//验证  act id
				singleLineLists = Lists.newArrayList();
				return singleLineLists;
			}
			singleLineLists.add(flowElement.getId());//验证  act id
			// camunda-TODO
			List<SequenceFlow> outgoingFlows = (ArrayList)((FlowNode) flowElement).getOutgoing();
//			if (outgoingFlows.size() > 1) {
//				this.findSingleLineTaskBetweenParallelGateway(singleLineLists, targetRefFlowElement, processDefinitionId, userTask)
//			}SequenceFlow outgoingFlow:outgoingFlows
			for (int i = 0; i < outgoingFlows.size(); i++) {
				String targetId = outgoingFlows.get(i).getTarget().getId();
				FlowElement targetRefFlowElement = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(targetId);
				if (outgoingFlows.size() > 1 && i < outgoingFlows.size()) {
					singleLineLists = this.findSingleLineTaskBetweenParallelGateway(singleLineLists, targetRefFlowElement, processDefinitionId, userTask);
					if (singleLineLists.size() == 0) {
						return singleLineLists;
					}
				}else {
					return this.findSingleLineTaskBetweenParallelGateway(singleLineLists, targetRefFlowElement, processDefinitionId, userTask);
				}
			}
		}
		return singleLineLists;
	}



	public boolean isStartParallelGateway(FlowElement flowElement){
		ParallelGateway parallelGateway = (ParallelGateway)flowElement;
		if (parallelGateway.getIncoming().size() < parallelGateway.getOutgoing().size()){
			return true;
		}
		return false;
	}

	/**
	 * 获取流程变量
	 * @param processInfo
	 * @return
	 */
	public TaskBean findProcessVariable(ProcessInfo processInfo){
		TaskBean taskBean = new TaskBean();
		Map<String, Object> variables = new HashMap<>();
		String processInstanceId = processInfo.getProcessInstanceId();
		if (StringUtils.isNotEmpty(processInstanceId)) {
			variables = findVariableByInstanceId(processInstanceId);
			taskBean.setRetCode(SUCCEED);
		}else {
			taskBean.setRetCode(FAILED);
		}
		taskBean.setProcessVariableMap(variables);
		return taskBean;
	}

	public Map<String, Object> findVariableByInstanceId(String instanceId){
		Map<String, Object> variables = new HashMap<>();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
		// 流程结束
		if(historicProcessInstance!=null){
			if(historicProcessInstance.getEndTime()!=null){
				// 流程结束后，获取历史流程变量
				List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(instanceId).list();
				if(CollectionUtils.isNotEmpty(list)){
					for (HistoricVariableInstance historicVariableInstance : list) {
						variables.put(historicVariableInstance.getName(),historicVariableInstance.getValue());
					}
				}
			}else{
				// 获取运行中的流程变量
				variables = runtimeService.getVariables(instanceId);
			}
		}
		return variables;
	}

	/**
	 * 先查分类树,并获取每个分类下的待办扩展表头
	 * @param taskInfo
	 * @return
	 */
	public List<TaskBean> getAllClassifyPendTasks(TaskInfo taskInfo){
		List<TaskBean> allTasks = new ArrayList<TaskBean>();
		if (StringUtils.isEmpty(taskInfo.getUserId())) {
			return allTasks;
		}

		List<ClassifyEntity> classifies = workflowClassifyPendService.getClassifyPendByUserId(taskInfo.getUserId(),
				taskInfo.getWfClassifyCode(), taskInfo.getTaskPoolStatus(),taskInfo.getCreateTimeAfter(),taskInfo.getCreateTimeBefore());
		List<WfTaskExpandPlanEntity> plans = workflowTaskExpandPlanService.getExpandPlanByClassifyCode("",taskInfo.getTenantId());
		for (int i = 0; i < classifies.size(); i++) {
			ClassifyEntity classifyEntity = classifies.get(i);
			String delFlag = classifyEntity.getDel_flag();
			if (IS_EFFECTIVE.equals(delFlag)) {
				List<Map> columnsList = new ArrayList();
				for (WfTaskExpandPlanEntity plan: plans) {
					if(StringUtils.equals(classifyEntity.getId(),plan.getWfClassId())){
						Map map = new HashMap();
						String fieldName = plan.getFieldName();
						String fieldKey = plan.getFieldKey();
						String fieldType = plan.getFieldType();
						String fieldUrl = plan.getFieldUrl();
						String fieldStatus = plan.getFieldStatus();
						String isQueryCondition = plan.getIsQueryCondition();
						map.put("field", fieldKey);
						map.put("name", fieldName);
						map.put("fieldType", fieldType);
						map.put("fieldUrl", fieldUrl);
						map.put("fieldStatus", fieldStatus);
						map.put("isQueryCondition", isQueryCondition);
						columnsList.add(map);
					}
				}
				columnsList.addAll(getRegularPendTaskColumns());
				TaskBean taskBean = new TaskBean();
				taskBean.setWfClassifyCode(classifyEntity.getCode());
				taskBean.setWfClassifyName(classifyEntity.getName());
				taskBean.setColumns(columnsList);
				taskBean.setTotal(classifyEntity.getTotal());
				allTasks.add(taskBean);
			}
		}
		return allTasks;
	}

	/**
	 * 先查分类树,并获取每个分类下的已办扩展表头
	 * @return
	 */
	public List<TaskBean> getAllClassifyCompletedTasks(TaskInfo taskInfo){
		List<TaskBean> allTasks = new ArrayList<TaskBean>();
		if (StringUtils.isEmpty(taskInfo.getUserId())) {
			return allTasks;
		}

		List<ClassifyEntity> classifies = workflowClassifyCompletedService
				.getClassifyCompletedByUserId(taskInfo.getUserId(), taskInfo.getIsTerminateProcess(),
						taskInfo.getWfClassifyCode(), taskInfo.getIsEnd(), taskInfo.getSingleHiTaskStatus(),taskInfo.getCreateTimeAfter(),taskInfo.getCreateTimeBefore());

		List<WfTaskExpandPlanEntity> plans = workflowTaskExpandPlanService.getExpandPlanByClassifyCode("",taskInfo.getTenantId());
		for (int i = 0; i < classifies.size(); i++) {
			ClassifyEntity classifyEntity = classifies.get(i);
			String delFlag = classifyEntity.getDel_flag();
			if (IS_EFFECTIVE.equals(delFlag)) {
				List<Map> columnsList = new ArrayList();
				for (WfTaskExpandPlanEntity plan: plans) {
					if(StringUtils.equals(classifyEntity.getId(),plan.getWfClassId())){
						Map map = new HashMap();
						String fieldName = plan.getFieldName();
						String fieldKey = plan.getFieldKey();
						String fieldType = plan.getFieldType();
						String fieldUrl = plan.getFieldUrl();
						String fieldStatus = plan.getFieldStatus();
						String isQueryCondition = plan.getIsQueryCondition();
						map.put("field", fieldKey);
						map.put("name", fieldName);
						map.put("fieldType", fieldType);
						map.put("fieldUrl", fieldUrl);
						map.put("fieldStatus", fieldStatus);
						map.put("isQueryCondition", isQueryCondition);
						columnsList.add(map);
					}
				}
				columnsList.addAll(getRegularCompletedColumns());
				TaskBean taskBean = new TaskBean();
				taskBean.setWfClassifyCode(classifyEntity.getCode());
				taskBean.setWfClassifyName(classifyEntity.getName());
				taskBean.setColumns(columnsList);
				taskBean.setTotal(classifies.get(i).getTotal());
				allTasks.add(taskBean);
			}
		}
		return allTasks;
	}


	/**
	 * 已办任务查询
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findCompletedTaskList(TaskInfo taskInfo) {
		TaskBean taskBean = new TaskBean();
		//获取分页
		int currentPage = StringUtils.isNumericSpace(taskInfo.getCurrentPage()) ? Integer.parseInt(taskInfo.getCurrentPage()):1;
		int pageSize = StringUtils.isNumericSpace(taskInfo.getPageSize()) ? Integer.parseInt(taskInfo.getPageSize()):10;
		int firstResult = pageSize * (currentPage - 1);

		//查询待办扩展方案
		List<WfTaskExpandPlanEntity> plans = this.getExpandPlan(taskInfo);

		Map<String, String> classifyCodeMapName = workflowClassifyService.getClassifyCodeMapName();
		String wfClassifyName = classifyCodeMapName.get(taskInfo.getWfClassifyCode());

		List<String> fieldMapping = WfExpandPlanUtil.generateFieldMapping(plans);
		List<String> fieldCondition = generateFieldCondition(taskInfo,plans);
		List<Map> columnsList = generateColumnsMapping(plans);
		columnsList.addAll(getRegularCompletedColumns());

		// 如果查询条件不需要连表查询，采用先单表查询已办表，再单表查询已办扩展表
		if(CollectionUtils.isEmpty(fieldCondition)
				&& StringUtils.isBlank(taskInfo.getProcessCreateTimeAfter())
				&& StringUtils.isBlank(taskInfo.getProcessCreateTimeBefore())
				&& StringUtils.isBlank(taskInfo.getStartUserId())
				&& StringUtils.isBlank(taskInfo.getStartUserOrgCode())
				&& StringUtils.isBlank(taskInfo.getSingleHiTaskStatus())
				&& StringUtils.isBlank(taskInfo.getWfClassifyCode())
				&& taskInfo.getClaimTimeBefore()==null
				&& taskInfo.getClaimTimeAfter()==null
				&& StringUtils.isBlank(taskInfo.getIsShowPreTask())
				&& StringUtils.isBlank(taskInfo.getIsShowCurrentTask())
				&& CollectionUtils.isEmpty(taskInfo.getIgnoreActivityId())
				&& CollectionUtils.isEmpty(taskInfo.getIgnoreActivityName())
		){
			HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(taskInfo.getUserId()).finished();
			if("asc".equalsIgnoreCase(taskInfo.getStartTimeSort())){
				historicTaskInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();
			}
			if("desc".equalsIgnoreCase(taskInfo.getStartTimeSort())){
				historicTaskInstanceQuery.orderByHistoricActivityInstanceStartTime().desc();
			}
			if("asc".equalsIgnoreCase(taskInfo.getEndTimeSort())){
				historicTaskInstanceQuery.orderByHistoricTaskInstanceEndTime().asc();
			}
			if("desc".equalsIgnoreCase(taskInfo.getEndTimeSort())){
				historicTaskInstanceQuery.orderByHistoricTaskInstanceEndTime().desc();
			}
			if(StringUtils.isNotBlank(taskInfo.getProcessInstanceId())){
				historicTaskInstanceQuery.processInstanceId(taskInfo.getProcessInstanceId());
			}
			if(StringUtils.isNotBlank(taskInfo.getProcessDefinitionId())){
				historicTaskInstanceQuery.processDefinitionId(taskInfo.getProcessDefinitionId());
			}
			if(StringUtils.isNotBlank(taskInfo.getActivityId())){
				historicTaskInstanceQuery.taskDefinitionKey(taskInfo.getActivityId());
			}
			if(StringUtils.isNotBlank(taskInfo.getProcessDefineKey())){
				historicTaskInstanceQuery.processDefinitionKey(taskInfo.getProcessDefineKey());
			}
			if(StringUtils.isNotBlank(taskInfo.getTaskName())){
				historicTaskInstanceQuery.taskNameLike(taskInfo.getTaskName());
			}
			if(StringUtils.isNotBlank(taskInfo.getTaskId())){
				historicTaskInstanceQuery.taskId(taskInfo.getTaskId());
			}
			if(StringUtils.isNotBlank(taskInfo.getBusinessKey())){
				historicTaskInstanceQuery.processInstanceBusinessKey(taskInfo.getBusinessKey());
			}
			if(StringUtils.isNotBlank(taskInfo.getBusinessKeyLike())){
				historicTaskInstanceQuery.processInstanceBusinessKeyLike(taskInfo.getBusinessKeyLike());
			}
			if(StringUtils.isNotBlank(taskInfo.getTenantId())){
				historicTaskInstanceQuery.tenantIdIn(taskInfo.getTenantId());
			}
			if(CollectionUtils.isNotEmpty(taskInfo.getTenantIds())){
				historicTaskInstanceQuery.tenantIdIn(taskInfo.getTenantIds().toArray(new String[taskInfo.getTenantIds().size()]));
			}
			if("0".equals(taskInfo.getIsEnd())){
				historicTaskInstanceQuery.processFinished();
			}
			if("1".equals(taskInfo.getIsEnd())){
				historicTaskInstanceQuery.processUnfinished();
			}
			if(StringUtils.isNotEmpty(taskInfo.getCreateTimeAfter())){
				historicTaskInstanceQuery.startedAfter(DateUtil.stringToDate(taskInfo.getCreateTimeAfter()));
			}
			if(StringUtils.isNotEmpty(taskInfo.getCreateTimeBefore())){
				historicTaskInstanceQuery.startedBefore(DateUtil.stringToDate(taskInfo.getCreateTimeBefore()));
			}
			if(StringUtils.isNotEmpty(taskInfo.getEndTimeAfter())){
				historicTaskInstanceQuery.finishedAfter(DateUtil.stringToDate(taskInfo.getEndTimeAfter()));
			}
			if(StringUtils.isNotEmpty(taskInfo.getEndTimeBefore())){
				historicTaskInstanceQuery.finishedBefore(DateUtil.stringToDate(taskInfo.getEndTimeBefore()));
			}
			List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.listPage(firstResult, pageSize);
			if(CollectionUtils.isNotEmpty(historicTaskInstances)){
				List<Map> list = new ArrayList<>();
				List<String> taskIds = new ArrayList<>();
				for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
					Map<String,Object> data = new HashMap<>();
					data.put("processInstanceId",historicTaskInstance.getProcessInstanceId());
					data.put("taskId",historicTaskInstance.getId());
					data.put("processDefinitionId",historicTaskInstance.getProcessDefinitionId());
					data.put("processDefineKey",historicTaskInstance.getProcessDefinitionKey());
					data.put("dueDate",historicTaskInstance.getDueDate());
					data.put("activityName",historicTaskInstance.getName());
					data.put("executionId",historicTaskInstance.getExecutionId());
					data.put("activityId",historicTaskInstance.getTaskDefinitionKey());
					data.put("taskType","normalTask");
					data.put("beginTime",DateUtil.formatDate(historicTaskInstance.getStartTime()));
					data.put("endTime",DateUtil.formatDate(historicTaskInstance.getEndTime()));
					data.put("tenantId",historicTaskInstance.getTenantId());
					taskIds.add(historicTaskInstance.getId());
					list.add(data);
				}
				// 根据taskId查询扩展表
				List<HiTaskExpandDataEntity> hiTaskExpandDataEntities = wfHiTaskExpandDataMapper.selectByHiTaskId(new WfHiTaskQuery().taskIds(taskIds));
				// 两层循环添加扩展数据，historicTaskInstances和扩展表
				for (HiTaskExpandDataEntity hiTaskExpandDataEntity : hiTaskExpandDataEntities) {
					for (Map data : list) {
						if(StringUtils.equals(MapUtils.getString(data,"taskId"),hiTaskExpandDataEntity.getTaskId())){
							data.put("isStartNode",hiTaskExpandDataEntity.getIsStartNode());
							Date processStartTime = DateUtil.toDate(hiTaskExpandDataEntity.getProcessStartTime());
							Date processEndTime = DateUtil.toDate(hiTaskExpandDataEntity.getProcessEndTime());
							data.put("totalSpendHour",calculateTime(processStartTime, processEndTime == null ? new Date() : processEndTime));
							data.put("processDefineName",hiTaskExpandDataEntity.getProcessName());
							data.put("processStartTime",DateUtil.formatDate(hiTaskExpandDataEntity.getProcessStartTime()));
							data.put("processEndTime",DateUtil.formatDate(hiTaskExpandDataEntity.getProcessEndTime()));
							data.put("classifyCode",hiTaskExpandDataEntity.getProcessClassifyCode());
							data.put("classifyName",classifyCodeMapName.get(hiTaskExpandDataEntity.getProcessClassifyCode()));
							data.put("isMulti",hiTaskExpandDataEntity.getIsMulti());
							data.put("processState",hiTaskExpandDataEntity.getProcessEndTime()==null?"正在进行":"已结束");
						}
					}
				}
				taskBean.setTaskList(list);

				long count = historicTaskInstanceQuery.count();
				taskBean.setTotal(count+"");
				taskBean.setCurrentPageIndex(currentPage+"");
				taskBean.setColumns(columnsList);
			}
			return taskBean;
		}

		//设置查询条件
		WfHiTaskQuery query = new WfHiTaskQuery();
		if (StringUtils.isNotEmpty(taskInfo.getIsTerminateProcess())) {
			query.isTerminateProcess("强制终止");
		}
		if (this.managementService instanceof  ManagementServiceImpl) {
			query.setCommandExecutor(((ManagementServiceImpl) this.managementService).getCommandExecutor());
		}
		query.activityType("userTask");
		query.processDefinitionId(taskInfo.getProcessDefineId());
		query.processCreateTimeBefore(taskInfo.getProcessCreateTimeBefore());
		query.processCreateTimeAfter(taskInfo.getProcessCreateTimeAfter());
		query.processInstanceId(taskInfo.getProcessInstanceId());
		query.taskNameLike(taskInfo.getTaskName());
		query.userId(taskInfo.getUserId());
		query.taskDefinitionKey(taskInfo.getActivityId());
		query.fieldMapping(fieldMapping);
		query.fieldCondition(fieldCondition);
		query.taskId(taskInfo.getTaskId());
		query.classifyCode(taskInfo.getWfClassifyCode());
		query.singleHiTaskStatus(taskInfo.getSingleHiTaskStatus());
		query.startUserId(taskInfo.getStartUserId());
		query.startUserName(taskInfo.getStartUserName());
		query.startUserOrgCode(taskInfo.getStartUserOrgCode());
		if (StringUtils.isNotBlank(taskInfo.getTenantId())) {
			query.tenantId(taskInfo.getTenantId());
		}
		if (taskInfo.getTenantIds() != null) {
			query.tenantIds(taskInfo.getTenantIds());
		}
		query.isEnd(taskInfo.getIsEnd());
		query.isEndIncludeRuTask(taskInfo.getIsEndIncludeRuTask());
		query.singleHiTaskStatus(taskInfo.getSingleHiTaskStatus());
		query.businessKey(taskInfo.getBusinessKey());
		query.businessKeyLike(taskInfo.getBusinessKeyLike());
		query.processDefineName(taskInfo.getProcessDefineName());
		query.processDefineNameLike(taskInfo.getProcessDefineNameLike());
		query.processDefineKey(taskInfo.getProcessDefineKey());
		query.processDefineKeys(taskInfo.getProcessDefineKeys());
		query.processDefineKeyLike(taskInfo.getProcessDefineKeyLike());
		query.createTimeBefore(taskInfo.getCreateTimeBefore());
		query.createTimeAfter(taskInfo.getCreateTimeAfter());
		query.endTimeBefore(taskInfo.getEndTimeBefore());
		query.endTimeAfter(taskInfo.getEndTimeAfter());
		query.startTimeSort(taskInfo.getStartTimeSort());
		query.claimTimeSort(taskInfo.getClaimTimeSort());
		query.endTimeSort(taskInfo.getEndTimeSort());
		query.ignoreActivityId(taskInfo.getIgnoreActivityId());
		query.ignoreActivityName(taskInfo.getIgnoreActivityName());
		//query.is365Query(use365TaskListQuery);


		//查询内容
		List list = query.listPage(firstResult, pageSize);
		long count = query.count();

		//转换内容
		for (Object obj : list) {
			Map<String,Object> resultSet = (Map<String, Object>) obj;
			resultSet.put("beginTime", DateUtil.formatDate(DateUtil.toDate(resultSet.get("beginTime")),"yyyy-MM-dd HH:mm:ss.SSS"));
			resultSet.put("endTime", DateUtil.formatDate(DateUtil.toDate(resultSet.get("endTime"))));
			resultSet.put("dueDate", DateUtil.formatDate(DateUtil.toDate(resultSet.get("dueDate"))));
			Date processStartTime = DateUtil.toDate(resultSet.get("processStartTime"));
			Date processEndTime = DateUtil.toDate(resultSet.get("processEndTime"));
			resultSet.put("processEndTime", DateUtil.formatDate(processEndTime));
			resultSet.put("processStartTime", DateUtil.formatDate(processStartTime));
			resultSet.put("totalSpendHour", calculateTime(processStartTime, processEndTime == null ? new Date() : processEndTime));
			if("Y".equals(taskInfo.getIsShowPreTask())){
				resultSet.putAll(getPreTaskInfo(resultSet));
			}

			//5.3.0版本之后查询逻辑
			String actButton = MapUtils.getString(resultSet, "actButton");
			if(StringUtils.isNotBlank(actButton) ) {
				resultSet.put("actButton", JSONArray.parse(actButton));
			}else {
				resultSet.put("actButton","");
			}

			String candidateRoles = MapUtils.getString(resultSet, "candidateRoles");
			if(StringUtils.isNotBlank(candidateRoles) ) {
				resultSet.put("candidateRoles", JSONArray.parse(candidateRoles));
			}

			String candidateDepts = MapUtils.getString(resultSet, "candidateDepts");
			if(StringUtils.isNotBlank(candidateDepts) ) {
				resultSet.put("candidateDepts", JSONArray.parse(candidateDepts));
			}

			String candidateOrgs = MapUtils.getString(resultSet, "candidateOrgs");
			if(StringUtils.isNotBlank(candidateOrgs) ) {
				resultSet.put("candidateOrgs", JSONArray.parse(candidateOrgs));
			}
			String classifyCode = MapUtils.getString(resultSet, "classifyCode");
			resultSet.put("classifyName",classifyCodeMapName.get(classifyCode));
			if ("正在进行".equals(MapUtils.getString(resultSet,"processState"))){
				String processInstanceId = MapUtils.getString(resultSet, "processInstanceId");
				if("Y".equals(taskInfo.getIsShowCurrentTask())){
					findCurrentActivity(resultSet,processInstanceId);
				}
			}
			readableTaskType(resultSet);
		}
		//增加中文名，①currentUserIds -> currentUserNames ②preUser -> preUserName
		addUserName(list);
		taskBean.setTaskList(list);
		taskBean.setColumns(columnsList);
		taskBean.setWfClassifyCode(taskInfo.getWfClassifyCode());
		taskBean.setWfClassifyName(wfClassifyName);
		taskBean.setTotal(count+"");
		taskBean.setCurrentPageIndex(currentPage+"");
		return taskBean;

	}

	private void findCurrentActivity(Map<String,Object> resultSet, String processInstanceId) {
		ProcessInfo processInfo = new ProcessInfo();
		processInfo.setProcessInstanceId(processInstanceId);
		try {
			TaskBean taskBean = workflowProcessInstanceService.queryProcessTrack(processInfo);
			if("1".equals(taskBean.getRetCode())){
				this.logger.error(taskBean.getRetMsg());
				return;
			}
			List<String> userIds = new ArrayList<>();
			List<String> userNames = new ArrayList<>();
			List<String> names = new ArrayList<>();
			for(Map task:taskBean.getTaskList()){
				if(task.get("endTime")!=null&&!"".equals(task.get("endTime").toString())){
					continue;
				}
				if(task.get("taskName")!=null&&!"".equals(task.get("taskName"))){
					names.add(task.get("taskName").toString());
				}else{
					names.add("");
				}
				if(task.get("assignee")!=null&&!"".equals(task.get("assignee").toString())){
					userIds.add(task.get("assignee").toString());
				}else{
					userIds.add("");
				}
				if(task.get("assigneeName")!=null&&!"".equals(task.get("assigneeName").toString())){
					userNames.add(task.get("assigneeName").toString());
				}else{
					userNames.add("");
				}
			}
			resultSet.put("currentActivityNames",names);
			resultSet.put("currentUserIds",userIds);
			resultSet.put("currentUserNames",userNames);

		} catch (Exception e) {
			this.logger.error("查询当前流程最新节点信息失败",e);
			throw new RuntimeException(e);
		}

	}


	public String calculateTime(Object range1, Object rang2){
		if (range1 instanceof Date && rang2 instanceof Date){
			long spend = ((Date) rang2).getTime() - ((Date) range1).getTime();
			long l = spend / (1000 * 60 * 60);
			return (l <=0 ? "小于1": l) + "小时";
		}
		return null;
	}

	/**
	 * 获取已办流程分类扩展字段数据
	 * @param taskInfo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TaskBean findCompletedTaskExtendList(TaskInfo taskInfo) {
		List<ClassifyEntity> classifies = workflowClassifyService.getClassifyByClassifyCode(taskInfo.getWfClassifyCode());
		String wfClassifyName = null;
		if(CollectionUtils.isNotEmpty(classifies)){
			wfClassifyName = classifies.get(0).getName();
		}
		List<WfTaskExpandPlanEntity> plans = workflowTaskExpandPlanService.getExpandPlanByClassifyCode(taskInfo.getWfClassifyCode(),taskInfo.getTenantId());
		List<Map> columnsList = generateColumnsMapping(plans);
		columnsList.addAll(getRegularCompletedColumns());
		TaskBean taskBean = new TaskBean();
		taskBean.setWfClassifyCode(taskInfo.getWfClassifyCode());
		taskBean.setWfClassifyName(wfClassifyName);
		taskBean.setColumns(columnsList);
		return taskBean;
	}

	/**
	 * 获取已办固定列头
	 */
	private List<Map> getRegularCompletedColumns() {
		List<Map> columnsList = new ArrayList<>();
		Map<String,Object> columnsMap = new HashMap<>();
		columnsMap.put("processDefinitionId", "业务流程定义ID");
		columnsMap.put("processDefineName", "业务流程定义名称");
		columnsMap.put("processInstanceId", "业务流程实例ID");
		columnsMap.put("activityId", "节点ID");
		columnsMap.put("activityName", "节点名称");
		columnsMap.put("businessKey", "业务主键");
		columnsMap.put("taskId", "任务实例ID");
//		columnsMap.put("createTime", "创建时间");
		columnsMap.put("formKey", "表单键值");
		columnsMap.put("participant", "参与者");
		columnsMap.put("dueDate", "预期时间");
		columnsMap.put("comment", "批注");
		columnsMap.put("processTitle", "流程主题");
		columnsMap.put("endTime", "结束时间");
		columnsMap.put("processStartTime", "流程启动时间");
		columnsMap.put("superProcessInstanceId", "父流程实例id");
		columnsMap.put("totalSpendHour", "流程总耗时");
		columnsMap.put("formScene", "表单应用场景");
		columnsMap.put("taskType", "任务类型");
		columnsMap.put("candidateRoles", "候选角色");
		columnsMap.put("candidateDepts", "候选部门");
		columnsMap.put("candidateOrgs", "候选机构");
		columnsMap.put("selectUserModel", "处理人模式");
		columnsMap.put("preActId", "上一节点Id");
		columnsMap.put("preAct", "上一节点名称");
		columnsMap.put("preEndDate", "上一节点结束时间");
		columnsMap.put("preUser", "上一节节点处理人");
		columnsMap.put("preUserName", "上一节节点处理人");
		Set<String> keys = columnsMap.keySet();
		for(String key :keys){
			Map columns = new HashMap();
			columns.put("field", key);
			columns.put("name", columnsMap.get(key));
			columnsList.add(columns);
		}
		return columnsList;
	}

	/**
	 * 获取流程第一个用户任务
	 * @param taskId
	 * @return
	 */
	public TaskBean getFirstUserTask(String taskId){
		//获取逻辑，从历史任务表中获取流程第一个完成的用户节点
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if(task == null){
			return new TaskBean(FAILED,"任务"+taskId+"不存在!");
		}
		// camunda-TODO
		List<HistoricTaskInstance> firstTask = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).finished().orderByHistoricActivityInstanceStartTime().asc().listPage(0, 1);

		if(firstTask == null){
			return new TaskBean(FAILED,"无法回退到首个用户任务");
		}

		return new TaskBean(SUCCEED,firstTask.get(0).getTaskDefinitionKey());
	}

	/**
	 * 生成扩展字段查询条件
	 * @param taskInfo
	 * @param plans
	 * @return
	 */
	private List<String> generateFieldCondition(TaskInfo taskInfo, List<WfTaskExpandPlanEntity> plans) {
		if(plans == null || plans.size() ==0){
			return new ArrayList<String>();
		}

		Map<String, String> expandCondition = taskInfo.getTaskExpandCondition();
		if(expandCondition == null || expandCondition.size() == 0){
			return new ArrayList<String>();
		}

		//生成扩展字段查询条件
		List<String> fieldCondition = new ArrayList<String>();
		for (WfTaskExpandPlanEntity plan: plans) {
			String fieldColumn = plan.getFieldValue();//第几列
			String fieldKey = plan.getFieldKey();//key
			String conditionValue = expandCondition.get(fieldKey);//值date
			String dateValueBefore = fieldKey + "Before";
			String dateValueAfter = fieldKey + "After";
			String conditionValueBefore = expandCondition.get(dateValueBefore);
			String conditionValueAfter = expandCondition.get(dateValueAfter);

			if ("1".equals(plan.getFieldType())) {
				//string类型
				fieldCondition = this.getFieldConditionSqlWhenTypeIsString(
						conditionValue, fieldColumn, fieldCondition);
			}else if ("2".equals(plan.getFieldType())) {
				//date类型
				fieldCondition = this.getFieldConditionSqlWhenTypeIsDate(fieldColumn,
						fieldCondition, conditionValueBefore, conditionValueAfter);
			}else {
				//其他类型

			}

		}
		List<String> fieldConditions = new ArrayList<String>();
		for(String singleSql:fieldCondition){
			String newsingleSql = null;
			if (StringUtils.isNotBlank(taskInfo.getWfClassifyCode()) || CollectionUtils.isNotEmpty(taskInfo.getWfClassifyCodes())) {
				newsingleSql = singleSql.replaceAll("\\$\\{OR\\}", "AND");
			}else{
				newsingleSql = singleSql.replaceAll("\\$\\{OR\\}", "OR");
			}
			fieldConditions.add(newsingleSql);
		}
		return fieldConditions;
	}

	/**
	 * 获取扩展sql,当Type类型为Date时
	 * @param
	 * @param fieldColumn
	 * @param fieldCondition
	 * @param conditionValueBefore
	 * @param conditionValueAfter
	 * @return
	 */
	public List<String> getFieldConditionSqlWhenTypeIsDate(String fieldColumn,
														   List<String> fieldCondition, String conditionValueBefore, String conditionValueAfter){
		if (fieldCondition.size() == 0) {
			if (StringUtils.isNotBlank(conditionValueBefore) || StringUtils.isNotBlank(conditionValueAfter)) {
				if (StringUtils.isBlank(conditionValueBefore)) {
					fieldCondition.add(" W." + fieldColumn + " < '"+ conditionValueAfter +"' ");
				}else if (StringUtils.isBlank(conditionValueAfter)) {
					fieldCondition.add(" W." + fieldColumn + " > '"+ conditionValueBefore +"' ");
				}else {
					fieldCondition.add(" W." + fieldColumn + " BETWEEN '"+conditionValueBefore+"' AND '"+conditionValueAfter+"' ");
				}
			}
		}else {
			String sql = fieldCondition.get(0);
			if ("(".equals(sql.substring(0, 1))) {
				sql = sql.substring(1, sql.length()-1);
			}

			if (StringUtils.isNotBlank(conditionValueBefore) || StringUtils.isNotBlank(conditionValueAfter)) {
				fieldCondition = new ArrayList<String>();
				if (StringUtils.isBlank(conditionValueBefore)) {
					fieldCondition.add("( " + sql + " ${OR} W." + fieldColumn + " < '"+ conditionValueAfter +"' " + " )");
				}else if (StringUtils.isBlank(conditionValueAfter)) {
					fieldCondition.add("( " + sql + " ${OR} W." + fieldColumn + " > '"+ conditionValueBefore +"' " + " )");
				}else {
					fieldCondition.add("( " + sql + " ${OR} W." + fieldColumn + " BETWEEN '"+conditionValueBefore+"' AND '"+conditionValueAfter+"' " + " )");
				}
			}
		}
		return fieldCondition;
	}

	/**
	 * 获取扩展sql,当Type类型为String时
	 * @param conditionValue
	 * @param fieldColumn
	 * @param fieldCondition
	 * @param
	 * @return
	 */
	public List<String> getFieldConditionSqlWhenTypeIsString(String conditionValue,
															 String fieldColumn, List<String> fieldCondition){
		if(StringUtils.isBlank(conditionValue)){
			return fieldCondition;
		}
		if (fieldCondition.size() == 0) {
			if (conditionValue.contains(",")) {//IN ('12','10')
				String cvs = this.getFieldConditionWhenIsConditionValues(conditionValue, fieldColumn);
				fieldCondition.add("W." + fieldColumn + " IN (" + cvs + ")");
			}else {
				fieldCondition.add("W." + fieldColumn + " LIKE '%" + conditionValue + "%' ");
			}
		}else {
			String sql = fieldCondition.get(0);
			if ("(".equals(sql.substring(0, 1))) {
				sql = sql.substring(1, sql.length()-1);
			}
			fieldCondition = new ArrayList<String>();
			if (conditionValue.contains(",")) {//IN ('12','10')
				String cvs = this.getFieldConditionWhenIsConditionValues(conditionValue, fieldColumn);
				fieldCondition.add("( " + sql + " ${OR} W." + fieldColumn + " IN (" + cvs + ")"+")");
			}else {
				fieldCondition.add("( " + sql + " ${OR} W." + fieldColumn + " LIKE '%" + conditionValue + "%' " + ")");
			}
		}
		return fieldCondition;
	}

	/**
	 * 当conditionValue多个时,即多参数查询时
	 * @param conditionValue
	 * @param fieldColumn
	 * @param
	 * @return
	 */
	public String getFieldConditionWhenIsConditionValues(String conditionValue,
														 String fieldColumn){
		String[] conditionValues = conditionValue.split(",");
		String cvs = "";
		for (int i = 0; i < conditionValues.length; i++) {
			if (i != conditionValues.length -1) {
				cvs = cvs + "'" + conditionValues[i] + "',";
			}else {
				cvs = cvs + "'" + conditionValues[i] + "'";
			}
		}
		return cvs;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map> generateColumnsMapping(List<WfTaskExpandPlanEntity> columnsData) {
		List<Map> columnsList = new ArrayList();
		for (WfTaskExpandPlanEntity plan: columnsData) {
			Map map = new HashMap();
			String fieldName = plan.getFieldName();
			String fieldKey = plan.getFieldKey();
			String fieldType = plan.getFieldType();
			String fieldUrl = plan.getFieldUrl();
			String fieldStatus = plan.getFieldStatus();
			String isQueryCondition = plan.getIsQueryCondition();
			map.put("field", fieldKey);
			map.put("name", fieldName);
			map.put("fieldType", fieldType);
			map.put("fieldUrl", fieldUrl);
			map.put("fieldStatus", fieldStatus);
			map.put("isQueryCondition", isQueryCondition);
			columnsList.add(map);
		}
		return columnsList;
	}





	/**
	 * 回退任务时添加候选用户
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
	public TaskInfo addCandidateUser(DelegateTask delegateTask) {
		String backNodeId = WorkflowBackTaskAspectService.getBackNodeId();
		String backTaskId = WorkflowBackTaskAspectService.getBackTaskId();
		String reBackNodeId = WorkflowReBackTaskAspectService.getReBackNodeId();
        TaskEntity entity =  null;
        if (delegateTask instanceof  TaskEntity) {
            entity = (TaskEntity) delegateTask;
        }
		//串行加签人员处理
		dealSequentialAddTask(entity);

		if (backNodeId == null && reBackNodeId == null) {
			//正向提交
			logger.info("=============正向提交："
					+ " 当前节点id：" + entity.getId()
					+ " 当前处理人：" + entity.getAssignee()
					+ " 当前实例id：" + entity.getProcessInstanceId()
					+ " 回退目标节点id：" + backNodeId
					+ " 回退任务id：" + backTaskId
					+ " 取回任务id：" + reBackNodeId
			);
			return insertPostInfo(delegateTask);
		} else {
			//反向提交
			logger.info("=============反向提交："
					+ " 当前节点id：" + entity.getId()
					+ " 当前处理人：" + entity.getAssignee()
					+ " 当前实例id：" + entity.getProcessInstanceId()
					+ " 回退目标节点id：" + backNodeId
					+ " 回退任务id：" + backTaskId
					+ " 取回任务id：" + reBackNodeId
			);
			//
			List<Backtype> backtypes = delegateTask.getBpmnModelElementInstance().getExtensionElements().getElementsQuery().filterByType(Backtype.class).list();
			if(CollectionUtils.isNotEmpty(backtypes)){
				Backtype backtype = backtypes.get(0);
				// 退回且退回到组
				if(backNodeId!=null && Backtype.BACK_TO_GROUP.equals(backtype.getTextContent())){
					return insertPostInfo(delegateTask);
				}
			}
            return insertHiPostInfo(delegateTask);
		}


	}

	/**
	 * 处理串行加签,将加签人分配至任务
	 * @param entity
	 */
	private void dealSequentialAddTask(TaskEntity entity) {
		// camunda-TODO
		UserTask userTask = (UserTask) repositoryService.getBpmnModelInstance(entity.getProcessDefinitionId()).getModelElementById(entity.getTaskDefinitionKey());
		MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
		if (loopCharacteristics !=null && loopCharacteristics.isSequential()) {
			String states = (String) entity.getVariable(SequentialAddedTaskState.STATE_VARIABLE_KEY);
			if (StringUtils.isBlank(states)) {
				//未加签过,不处理
				return;
			}
			List<SequentialAddedTaskState> sequentialAddedTaskStates = JSON.parseArray(states, SequentialAddedTaskState.class);
			Integer loopCounter = (Integer) entity.getVariable("loopCounter");
			SequentialAddedTaskState state = sequentialAddedTaskStates.get(loopCounter);
			if(SequentialAddedTaskState.ADD_TASK.equals(state.getTaskType()) || SequentialAddedTaskState.MOVE_TASK.equals(state.getTaskType())){
				//加签任务,/前加签后移的任务
				entity.setAssignee(state.getUser());
				entity.setVariableLocal(SequentialAddedTaskState.TASK_TYPE_KEY, state.getTaskType());
				logger.info(" update assignee sequential added task state: {}, old assignee is :{}",JSON.toJSONString(state),entity.getAssignee());
			}
		}
	}

	/**
	 * 获取原始数据
	 * @param hiTask
	 * @param entity
	 * @param
	 * @return
	 */
	public List<String> getBeforeTransferList(HistoricTaskInstance hiTask, TaskEntity entity){
		List<String> beforeTransferList = new ArrayList<String>();
		if(hiTask != null){
			//获取该已办任务节点原始数据
			HistoricVariableInstance hiBeforeTransfer = historyService.createHistoricVariableInstanceQuery().
					variableName(AssignmentModel.TASK_BEFORE_TRANSFER_ASSIGNEE).
					taskIdIn(hiTask.getId()).processInstanceId(entity.getProcessInstanceId()).singleResult();

			if (hiBeforeTransfer != null) {
				entity.setVariableLocal(AssignmentModel.LAST_HI_TASK_ID, hiBeforeTransfer.getTaskId());
				beforeTransferList = hiBeforeTransfer.getValue() == null?(new ArrayList<String>()): (List<String>)hiBeforeTransfer.getValue();
				logger.info("=============addCandidateWhenCreateTask方法，存在执行回退："
						+ " 历史节点id：" + hiBeforeTransfer.getTaskId()
						+ " 历史节点变量：" + beforeTransferList
						+ " 当前实例id：" + entity.getProcessInstanceId()
				);
			}
		}
		return beforeTransferList;
	}

	/**
	 * 回退时或岗位处理人在离岗移交时设置非申请人用户信息
	 * @param entity
	 * @param
	 * @return
	 */
	public TaskEntity setTransferUserInfo(TaskEntity entity, LeaveTransferEntity leaveTransferEntity){
		Object roleCode = entity.getVariableLocal("roleCode");
		Object roleName = entity.getVariableLocal("roleName");
		Object deptCode = entity.getVariableLocal("deptCode");
		Object deptName = entity.getVariableLocal("deptName");
		Object orgCode = entity.getVariableLocal("orgCode");
		Object orgName = entity.getVariableLocal("orgName");
		Object userName = entity.getVariableLocal("userName");
		if (roleCode != null) {
			entity.removeVariableLocal("roleCode");
		}
		if (roleName != null) {
			entity.removeVariableLocal("roleName");
		}
		if (deptCode != null) {
			entity.removeVariableLocal("deptCode");
		}
		if (deptName != null) {
			entity.removeVariableLocal("deptName");
		}
		if (orgCode != null) {
			entity.removeVariableLocal("orgCode");
		}
		if (orgName != null) {
			entity.removeVariableLocal("orgName");
		}
		if (userName != null) {
			entity.removeVariableLocal("userName");
		}
		String TransferRoleCode = leaveTransferEntity.getTransferRoleCode();
		String TransferRoleName = leaveTransferEntity.getTransferRoleName();
		String TransferDeptCode = leaveTransferEntity.getTransferDeptCode();
		String TransferDeptName = leaveTransferEntity.getTransferDeptName();
		String TransferOrgCode = leaveTransferEntity.getTransferOrgCode();
		String TransferOrgName = leaveTransferEntity.getTransferOrgName();
		String TransferUserName = leaveTransferEntity.getTransferUserName();
		entity.setVariableLocal("roleCode", TransferRoleCode);
		entity.setVariableLocal("roleName", TransferRoleName);
		entity.setVariableLocal("deptCode", TransferDeptCode);
		entity.setVariableLocal("deptName", TransferDeptName);
		entity.setVariableLocal("orgCode", TransferOrgCode);
		entity.setVariableLocal("orgName", TransferOrgName);
		entity.setVariableLocal("userName", TransferUserName);
		return entity;
	}

	/**
	 * 回退时或岗位处理人在离岗移交时设置申请人用户信息
	 * @param entity
	 * @param
	 * @return
	 */
	public TaskEntity setUserInfo(TaskEntity entity, LeaveTransferEntity leaveTransferEntity){
		Object OldRoleCode = entity.getVariableLocal("roleCode");
		Object OldRoleName = entity.getVariableLocal("roleName");
		Object OldDeptCode = entity.getVariableLocal("deptCode");
		Object OldDeptName = entity.getVariableLocal("deptName");
		Object OldOrgCode = entity.getVariableLocal("orgCode");
		Object OldOrgName = entity.getVariableLocal("orgName");
		Object OldUserName = entity.getVariableLocal("userName");
		if (OldRoleCode != null) {
			entity.removeVariableLocal("roleCode");
		}
		if (OldRoleName != null) {
			entity.removeVariableLocal("roleName");
		}
		if (OldDeptCode != null) {
			entity.removeVariableLocal("deptCode");
		}
		if (OldDeptName != null) {
			entity.removeVariableLocal("deptName");
		}
		if (OldOrgCode != null) {
			entity.removeVariableLocal("orgCode");
		}
		if (OldOrgName != null) {
			entity.removeVariableLocal("orgName");
		}
		if (OldUserName != null) {
			entity.removeVariableLocal("userName");
		}
		String roleCode = leaveTransferEntity.getRoleCode();
		String roleName = leaveTransferEntity.getRoleName();
		String deptCode = leaveTransferEntity.getDeptCode();
		String deptName = leaveTransferEntity.getDeptName();
		String orgCode = leaveTransferEntity.getOrgCode();
		String orgName = leaveTransferEntity.getOrgName();
		String userName = leaveTransferEntity.getUserName();
		entity.setVariableLocal("roleCode", roleCode);
		entity.setVariableLocal("roleName", roleName);
		entity.setVariableLocal("deptCode", deptCode);
		entity.setVariableLocal("deptName", deptName);
		entity.setVariableLocal("orgCode", orgCode);
		entity.setVariableLocal("orgName", orgName);
		entity.setVariableLocal("userName", userName);
		return entity;
	}
	/**
	 * 判断该节点是否有过回退操作
	 * @param beforeTransferList
	 * @param assignee
	 * @param userId
	 * @return
	 */
	public boolean isBackPreTask(List<String> beforeTransferList, String assignee, String userId){
		if (StringUtils.isNotBlank(assignee) && beforeTransferList.size() != 0) {
			if (StringUtils.isEmpty(userId) && beforeTransferList.contains(assignee)) {
				return true;
			}
		}
		return false;
	}




	/**
	 * 部门或角色会签时设置循环的值
	 * @param editorInfo
	 * @param task
	 */
	private void setMuiltDeptCodeOrRoleCode( TaskEntity task, EditorInfo editorInfo, Set<String> users) {
		setMuiltDeptCodeOrRoleCode(task.getProcessDefinitionId(), task.getTaskDefinitionKey(), task, editorInfo, users);
	}

	/**
	 * 部门或角色会签时设置循环的值
	 * @param editorInfo
	 * @param task
	 */
	private void setMuiltDeptCodeOrRoleCode(String processDefinitionId,
											String taskDefinitionKey,
											VariableScope task,
											EditorInfo editorInfo,
											Set<String> users) {
		//1. 获取会签配置
		// camunda-TODO
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
		UserTask userTask = (UserTask) bpmnModel.getModelElementById(taskDefinitionKey);
		MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
		if (loopCharacteristics == null || loopCharacteristics.isSequential()){
			return;
		}

		// camunda-TODO
		String elementVariable = loopCharacteristics.getCamundaElementVariable();
		String loopValue = (String) task.getVariable(elementVariable);
		String inputDataItem = loopCharacteristics.getTextContent();
		if("roles".equals(inputDataItem)){
			// 指定角色会签
			editorInfo.getRoleCodes().add(loopValue);

		} else if ("depts".equals(inputDataItem)){
			// 指定部门会签
			editorInfo.getDeptCodes().add(loopValue);

		} else if ("users".equals(inputDataItem)){
			// 指定人员会签
			users.add(loopValue);
		}

	}

	/**
	 * 判断是否有用户过滤条件
	 * @param editorInfo
	 * @return
	 */
	private boolean hasUserFilterCondition(EditorInfo editorInfo) {
		boolean hasUserFilterCondition = false;
		if (CollectionUtils.isNotEmpty(editorInfo.getOrgCodes())) {
			hasUserFilterCondition = true;
		}
		if (CollectionUtils.isNotEmpty(editorInfo.getDeptCodes())){
			hasUserFilterCondition = true;
		}
		if (CollectionUtils.isNotEmpty(editorInfo.getRoleCodes())){
			hasUserFilterCondition = true;
		}
		if (StringUtils.isNotBlank(editorInfo.getCustomRule())) {
			hasUserFilterCondition = true;
		}
		return hasUserFilterCondition;
	}


	/**
	 * 回退任务时,设置获选人
	 *
	 * @aram entity
	 * @param backNodeId
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private EditorInfo addCandidateWhenBackOperate(TaskEntity entity, String backNodeId, String backTaskId, String reBackNodeId) {
		EditorInfo  editorInfo  = null;
		String procDefId = entity.getProcessDefinitionId();
		String taskDefKey = entity.getTaskDefinitionKey();
//        Backtype backtype = delegateTask.getBpmnModelElementInstance().getExtensionElements().getElementsQuery().filterByType(Backtype.class).singleResult();

        // camunda-TODO
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(procDefId);
		FlowElement flowElement = bpmnModel.getModelElementById(taskDefKey);
		if(flowElement instanceof UserTask){
			UserTask userTask = (UserTask) flowElement;
			MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
			// 判断是否是会签，是的话不通过历史节点设置处理人
			if(loopCharacteristics!=null){
				return null;
			}
		}

		//查找上一审批节点
		HistoricTaskInstance hiTask = getLastHiTask(entity, backNodeId, reBackNodeId);
		if (hiTask == null) {
			return null;
		}

		return  editorInfo;
	}

    /**
     * 退回到人或者收回
     * @param delegateTask
     */
	public TaskInfo insertHiPostInfo(DelegateTask delegateTask){
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().finished()
				.processInstanceId(delegateTask.getProcessInstanceId())
				.taskDefinitionKey(delegateTask.getTaskDefinitionKey())
				.orderByHistoricTaskInstanceEndTime().desc().listPage(0, 1);
		if(CollectionUtils.isEmpty(historicTaskInstances)){
			throw new RuntimeException("退回失败，历史节点为空");
		}else{
			HistoricTaskInstance historicTaskInstance = historicTaskInstances.get(0);
			String assignee = historicTaskInstance.getAssignee();
			taskSelectUserService.saveUserTaskAssignment(delegateTask,assignee);
		}
		return new TaskInfo();
	}
	
	public  List<PostInfo>  getPostInfos(UserTaskParticipant userTaskParticipant,TaskEntity  task) {
		  String parRadioConfig = userTaskParticipant.getParRadioConfig();
		  List<PostInfo>  seleInfos = new  ArrayList<>();
		  PostInfo  postInfo = null;
	
	      if(CONFIG_TYPE_1.equals(parRadioConfig)){
	            if(userTaskParticipant.getAllowPreAct() && task.getVariables().get("nextPost")!=null){
	                List<PostInfo> nextPost = (List<PostInfo>)task.getVariables().get("nextPost");         
	                seleInfos.addAll(nextPost);
	            }else{
	                // 从参与者列表获得
	                Collection<Participants> participants = userTaskParticipant.getParticipants();
	                if(CollectionUtils.isEmpty(participants)){
	                    throw new RuntimeException("没有找到参与者列表");
	                }
	                seleInfos =this.workflowProcessDefineService.getPostInfos(userTaskParticipant);
	            }
	        }else if(CONFIG_TYPE_2.equals(parRadioConfig)){
	            // 流程启动者
	            String startUserId = (String)task.getVariables().get("startUserId");
	            if(StringUtils.isBlank(startUserId)){
	                throw new RuntimeException("发起人为空");
	            }
	            postInfo = new PostInfo();
	            postInfo.setUserId(startUserId);
	            postInfo.setType("user");
	            seleInfos.add(postInfo);
	           
	        }else if(CONFIG_TYPE_3.equals(parRadioConfig)){
	            // 流程执行者（其他节点的）
	            String activityId = userTaskParticipant.getExecutor();
	            // 历史节点
	            HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
	            List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.processInstanceId(task.getProcessInstanceId()).taskDefinitionKey(activityId).orderByHistoricTaskInstanceEndTime().desc().list();
	            if(CollectionUtils.isEmpty(historicTaskInstances)){
	                throw new RuntimeException("流程执行者"+activityId+"为空");
	            }
	            HistoricTaskInstance historicTaskInstance = historicTaskInstances.get(0);
	            String userId = historicTaskInstance.getAssignee();
	            postInfo = new PostInfo();
	            postInfo.setUserId(userId);
	            postInfo.setType("user");
	            seleInfos.add(postInfo);
	     
	        }else if(CONFIG_TYPE_4.equals(parRadioConfig)){
	            // 参与者规则 可配置的 TODO
	        }else if(CONFIG_TYPE_5.equals(parRadioConfig)){
	            // 相关数据 流程变量${} 人或者岗位
	            String aboutData = userTaskParticipant.getAboutData();
	            String aboutDataKey = aboutData.substring(aboutData.indexOf("${") + 2, aboutData.indexOf("}", aboutData.indexOf("}")));
	            Object aboutDataValue = task.getVariables().get(aboutDataKey);
	            if(aboutDataValue==null){
	                throw new RuntimeException("变量"+aboutDataKey+"不能为空");
	            }
	            if(aboutDataValue instanceof String){
	               postInfo = new PostInfo();
	               postInfo.setUserId((String) aboutDataValue);
	               postInfo.setType("user");
	               seleInfos.add(postInfo);
	            }else if(aboutDataValue instanceof PostInfo){
	                 postInfo = (PostInfo) aboutDataValue;
	                 seleInfos.add(postInfo);

	            }else if(aboutDataValue instanceof List){
	            	seleInfos = (List<PostInfo>) aboutDataValue;

	            }

	        }else if(CONFIG_TYPE_6.equals(parRadioConfig)){
	            // 规则逻辑
	            String serviceType = userTaskParticipant.getServiceType();
	            String serviceValue = userTaskParticipant.getServiceValue();
	            Collection<RuleLogicParam> ruleLogicParams = userTaskParticipant.getRuleLogicParams();
	            // 参数
	            Map<String,Object> param = new HashMap<>();
	            for (RuleLogicParam ruleLogicParam : ruleLogicParams) {
	                String name = ruleLogicParam.getName();
	                String value = ruleLogicParam.getValue();
	                String valueType = ruleLogicParam.getValueType();
	                if(RuleLogicParam.VALUE_TYPE_CONSTANT.equals(valueType)){
	                    param.put(name,value);
	                }
	                if(RuleLogicParam.VALUE_TYPE_VARIABLE.equals(valueType)){
	                    param.put(name,task.getVariable(value));
	                }
	            }
	            // 执行         
	            if(SERVICE_TYPE_LOCAL.equals(serviceType)){
	                // 本地服务
	                IWorkflowCustomUserService bean = applicationContext.getBean(serviceValue, IWorkflowCustomUserService.class);
	                seleInfos = bean.execute(param);
	            }else if(SERVICE_TYPE_ONLINE.equals(serviceType)){
	                // 远程服务
	                HttpCustomUserService bean = applicationContext.getBean(HttpCustomUserService.class);
	                seleInfos = bean.execute(serviceValue,param);
	            }
	                 
	        }
	        return  seleInfos;

	}
	/**
	 * 根据任务配置信息和taskId 获取节点参与人信息
	 * taskId 为空 ,查找节点参与人列表
	 * @param userTaskParticipant
	 * @param taskId
	 * @return
	 */
	public  List<PostInfo>  getPostInfos(UserTaskParticipant userTaskParticipant,String  taskId) {
	
	  if (taskId == null) {
		   return  this.workflowProcessDefineService.getPostInfos(userTaskParticipant);
	  }
	  TaskEntity  task = (TaskEntity) this.taskService.createTaskQuery().taskId(taskId).singleResult();
	  if (task == null) {
		  return  this.workflowProcessDefineService.getPostInfos(userTaskParticipant);
	  }
	  return  this.getPostInfos(userTaskParticipant, task);
	
	}

	/**
	 * 查找流程节点参与者
	 * @param userTaskParticipant
	 * @param vtask
	 * @param selectUserMode
	 */
	public void getPostInfos(UserTaskParticipant userTaskParticipant,Task vtask ,SelectUserMode selectUserMode){
        // 新的参与者模式
		List<PostInfo>   seleInfos = null;	
		TaskEntity  task = null;
		if (vtask instanceof TaskEntity) {
			task = (TaskEntity) vtask;
		}else {
			seleInfos =this.workflowProcessDefineService.getPostInfos(userTaskParticipant);
			selectUserMode.setPostInfos(seleInfos);
			return ;
		}			
        String parRadioConfig = userTaskParticipant.getParRadioConfig();
        boolean allowPreAct = userTaskParticipant.getAllowPreAct();
		selectUserMode.setParRadioConfig(parRadioConfig);
		selectUserMode.setAllowPreAct(allowPreAct);
        PostInfo  postInfo = null;
		Map<String, Object> variables = taskService.getVariables(task.getId());
		if(CONFIG_TYPE_1.equals(parRadioConfig)){
            if(allowPreAct && variables.get("nextPost")!=null){
                List<PostInfo> nextPost = (List<PostInfo>)variables.get("nextPost");
                selectUserMode.setPostInfos(nextPost);              
            }else{
                // 从参与者列表获得
                Collection<Participants> participants = userTaskParticipant.getParticipants();
                if(CollectionUtils.isEmpty(participants)){
                    throw new RuntimeException("没有找到参与者列表");
                }
                seleInfos =this.workflowProcessDefineService.getPostInfos(userTaskParticipant);
                selectUserMode.setPostInfos(seleInfos);
            }
        }else if(CONFIG_TYPE_2.equals(parRadioConfig)){
            // 流程启动者
            String startUserId = (String)variables.get("startUserId");
            selectUserMode.setStartUserId(startUserId);
            selectUserMode.setAssignee(startUserId);
           
        }else if(CONFIG_TYPE_3.equals(parRadioConfig)){
            // 流程执行者（其他节点的）
            String activityId = userTaskParticipant.getExecutor();
            // 历史节点
            HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
            List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.processInstanceId(task.getProcessInstanceId()).taskDefinitionKey(activityId).orderByHistoricTaskInstanceEndTime().desc().list();
            HistoricTaskInstance historicTaskInstance = historicTaskInstances.get(0);
            String userId = historicTaskInstance.getAssignee();
            selectUserMode.setAssignee(userId);
     
        }else if(CONFIG_TYPE_4.equals(parRadioConfig)){
            // 参与者规则 可配置的 TODO
        }else if(CONFIG_TYPE_5.equals(parRadioConfig)){
            // 相关数据 流程变量${} 人或者岗位
            String aboutData = userTaskParticipant.getAboutData();
            String aboutDataKey = aboutData.substring(aboutData.indexOf("${") + 2, aboutData.indexOf("}", aboutData.indexOf("}")));
            Object aboutDataValue = variables.get(aboutDataKey);
            if(aboutDataValue!=null){
				if(aboutDataValue instanceof String){
					selectUserMode.setAssignee((String) aboutDataValue);
				}else if(aboutDataValue instanceof PostInfo){
					postInfo = (PostInfo) aboutDataValue;
					selectUserMode.getPostInfos().add(postInfo);
				}else if(aboutDataValue instanceof List){
					List<PostInfo> nextPost = (List<PostInfo>) aboutDataValue;
					selectUserMode.setPostInfos(nextPost);
				}
			}
			selectUserMode.setAboutData(aboutData);
			selectUserMode.setAboutDataKey(aboutDataKey);
        }else if(CONFIG_TYPE_6.equals(parRadioConfig)){

        }
	}

	
    /**
     * 正常提交或者退回到组
     * 处理人
     * @param delegateTask
     */
	public TaskInfo insertPostInfo(DelegateTask delegateTask){
        // 新的参与者模式
        UserTaskParticipant userTaskParticipant = delegateTask.getBpmnModelElementInstance().getExtensionElements().getElementsQuery().filterByType(UserTaskParticipant.class).singleResult();
        String parRadioConfig = userTaskParticipant.getParRadioConfig();

        String orgCode = "";
        if(CONFIG_TYPE_1.equals(parRadioConfig)){
            if(userTaskParticipant.getAllowPreAct() && delegateTask.getVariables().get("nextPost")!=null){
                List<PostInfo> nextPost = (List<PostInfo>)delegateTask.getVariables().get("nextPost");
                taskSelectUserService.saveUserTaskAssignment(delegateTask, nextPost);
                orgCode = nextPost.iterator().next().getOrgCode();
            }else{
                // 从参与者列表获得
                Collection<Participants> participants = userTaskParticipant.getParticipants();
                if(CollectionUtils.isEmpty(participants)){
                    throw new RuntimeException("没有找到参与者列表");
                }
                // 允许前驱活动根据如上列表指派活动参与者
                // 如果前驱没有传，就用参与者列表中的数据
                List<ActRuIdentitylinkEntity> actRuIdentitylinkEntities = taskSelectUserService.saveUserTaskAssignment(delegateTask, participants,delegateTask.getVariables());
                orgCode = actRuIdentitylinkEntities.iterator().next().getOrgCode();
            }
        }else if(CONFIG_TYPE_2.equals(parRadioConfig)){
            // 流程启动者
            String startUserId = (String)delegateTask.getVariables().get("startUserId");
            if(StringUtils.isBlank(startUserId)){
                throw new RuntimeException("发起人为空");
            }
            taskSelectUserService.saveUserTaskAssignment(delegateTask,startUserId);
            orgCode = (String)delegateTask.getVariable("startOrgId");
        }else if(CONFIG_TYPE_3.equals(parRadioConfig)){
            // 流程执行者（其他节点的）
            String activityId = userTaskParticipant.getExecutor();
            // 历史节点
            HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
            List<HistoricActivityInstance> historicActivityInstances = historicActivityInstanceQuery.processInstanceId(delegateTask.getProcessInstanceId()).activityId(activityId).orderByHistoricActivityInstanceEndTime().desc().list();
            // TODO 如果选择的历史节点是会签节点，那么需要考虑回退的问题，只能去最新一次的历史人员
            if(CollectionUtils.isEmpty(historicActivityInstances)){
                throw new RuntimeException("流程执行者"+activityId+"为空");
            }
            // 时间最近的一个任务
            HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(0);
            // 历史节点是一个多工作项，去多个任务的历史处理人
			if(((UserTask)delegateTask.getBpmnModelInstance().getModelElementById(historicActivityInstance.getActivityId())).getLoopCharacteristics()!=null){
                String parentActivityInstanceId = historicActivityInstance.getParentActivityInstanceId();
                Set<String> userIds = new HashSet<>();
                for (HistoricActivityInstance activityInstance : historicActivityInstances) {
                    if(StringUtils.equals(parentActivityInstanceId,activityInstance.getParentActivityInstanceId())) {
                        userIds.add(activityInstance.getAssignee());
                    }
                }
				taskSelectUserService.saveUserTaskAssignment(delegateTask,userIds.toArray(new String[userIds.size()]));
			}else{
				// 不是多工作项，就取一个最新任务的历史处理人
				String userId = historicActivityInstance.getAssignee();
				taskSelectUserService.saveUserTaskAssignment(delegateTask,userId);
			}

        }else if(CONFIG_TYPE_4.equals(parRadioConfig)){
            // 参与者规则 可配置的 TODO
        }else if(CONFIG_TYPE_5.equals(parRadioConfig)){
            // 相关数据 流程变量${} 人或者岗位
            String aboutData = userTaskParticipant.getAboutData();
            String aboutDataKey = aboutData.substring(aboutData.indexOf("${") + 2, aboutData.indexOf("}", aboutData.indexOf("}")));
            Object aboutDataValue = delegateTask.getVariables().get(aboutDataKey);
            if(aboutDataValue==null){
                throw new RuntimeException("变量"+aboutDataKey+"不能为空");
            }
            if(aboutDataValue instanceof String){
                taskSelectUserService.saveUserTaskAssignment(delegateTask,(String)aboutDataValue);
            }else if(aboutDataValue instanceof Map) {
				String dataString = JSON.toJSONString(aboutDataValue);
				PostInfo postInfo = JSON.parseObject(dataString, PostInfo.class);
				taskSelectUserService.saveUserTaskAssignment(delegateTask, postInfo);
				orgCode = postInfo.getOrgCode();
			}else if(aboutDataValue instanceof PostInfo){
				PostInfo postInfo = (PostInfo)aboutDataValue;
				taskSelectUserService.saveUserTaskAssignment(delegateTask, postInfo);
				orgCode = postInfo.getOrgCode();
            }else if(aboutDataValue instanceof List){
				String dataString = JSON.toJSONString(aboutDataValue);
				List<PostInfo> nextPost = JSONArray.parseArray(dataString, PostInfo.class);
                taskSelectUserService.saveUserTaskAssignment(delegateTask,nextPost);
                orgCode = nextPost.get(0).getOrgCode();
            }else{
				throw new RuntimeException(aboutDataKey+"变量类型错误："+aboutDataValue.getClass());
			}

        }else if(CONFIG_TYPE_6.equals(parRadioConfig)){
            // 规则逻辑
            String serviceType = userTaskParticipant.getServiceType();
            String serviceValue = userTaskParticipant.getServiceValue();
            Collection<RuleLogicParam> ruleLogicParams = userTaskParticipant.getRuleLogicParams();
            // 参数
            Map<String,Object> param = new HashMap<>();
            for (RuleLogicParam ruleLogicParam : ruleLogicParams) {
                String name = ruleLogicParam.getName();
                String value = ruleLogicParam.getValue();
                String valueType = ruleLogicParam.getValueType();
                if(RuleLogicParam.VALUE_TYPE_CONSTANT.equals(valueType)){
                    param.put(name,value);
                }
                if(RuleLogicParam.VALUE_TYPE_VARIABLE.equals(valueType)){
                    param.put(name,delegateTask.getVariable(value));
                }
            }

            // 执行
            List<PostInfo> postInfos = new ArrayList<>();
            if(SERVICE_TYPE_LOCAL.equals(serviceType)){
                // 本地服务
                IWorkflowCustomUserService bean = applicationContext.getBean(serviceValue, IWorkflowCustomUserService.class);
                postInfos = bean.execute(param);
            }else if(SERVICE_TYPE_ONLINE.equals(serviceType)){
                // 远程服务
                HttpCustomUserService bean = applicationContext.getBean(HttpCustomUserService.class);
                postInfos = bean.execute(serviceValue,param);
            }
            if(CollectionUtils.isEmpty(postInfos)){
                throw new RuntimeException("找不到参与者");
            }else{
                taskSelectUserService.saveUserTaskAssignment(delegateTask,postInfos);
                orgCode = postInfos.iterator().next().getOrgCode();
            }
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setOrgCode(orgCode);
        return taskInfo;
    }

	public TaskInfo insertPostInfo(TaskEntity taskEntity,UserTask userTask){
		// 新的参与者模式
		UserTaskParticipant userTaskParticipant = userTask.getExtensionElements().getElementsQuery().filterByType(UserTaskParticipant.class).singleResult();
		String parRadioConfig = userTaskParticipant.getParRadioConfig();
		Map<String, Object> variables = taskService.getVariables(taskEntity.getId());
		String orgCode = "";
		List<PostInfo> postInfos = new ArrayList<>();
		if(CONFIG_TYPE_1.equals(parRadioConfig)){
			if(userTaskParticipant.getAllowPreAct() && variables.get("nextPost")!=null){
				List<PostInfo> nextPost = (List<PostInfo>)variables.get("nextPost");
				taskSelectUserService.saveUserTaskAssignment(taskEntity, nextPost);
				orgCode = nextPost.iterator().next().getOrgCode();
			}else{
				// 从参与者列表获得
				Collection<Participants> participants = userTaskParticipant.getParticipants();
				if(CollectionUtils.isEmpty(participants)){
					throw new RuntimeException("没有找到参与者列表");
				}
				// 允许前驱活动根据如上列表指派活动参与者
				// 如果前驱没有传，就用参与者列表中的数据
				List<ActRuIdentitylinkEntity> actRuIdentitylinkEntities = taskSelectUserService.saveUserTaskAssignment(taskEntity, participants,variables);
				orgCode = actRuIdentitylinkEntities.iterator().next().getOrgCode();
			}
		}else if(CONFIG_TYPE_2.equals(parRadioConfig)){
			// 流程启动者
			String startUserId = (String)taskEntity.getVariables().get("startUserId");
			if(StringUtils.isBlank(startUserId)){
				throw new RuntimeException("发起人为空");
			}
			taskSelectUserService.saveUserTaskAssignment(taskEntity,startUserId);
			orgCode = (String)taskEntity.getVariable("startOrgId");
		}else if(CONFIG_TYPE_3.equals(parRadioConfig)){
			// 流程执行者（其他节点的）
			String activityId = userTaskParticipant.getExecutor();
			// 历史节点
			HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
			List<HistoricActivityInstance> historicActivityInstances = historicActivityInstanceQuery.processInstanceId(taskEntity.getProcessInstanceId()).activityId(activityId).orderByHistoricActivityInstanceEndTime().desc().list();
			if(CollectionUtils.isEmpty(historicActivityInstances)){
				throw new RuntimeException("流程执行者"+activityId+"为空");
			}
			// 时间最近的一个任务
			HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(0);
			// 历史节点是一个多工作项，去多个任务的历史处理人
			if(((UserTask)taskEntity.getBpmnModelInstance().getModelElementById(historicActivityInstance.getActivityId())).getLoopCharacteristics()!=null){
				String parentActivityInstanceId = historicActivityInstance.getParentActivityInstanceId();
				Set<String> userIds = new HashSet<>();
				for (HistoricActivityInstance activityInstance : historicActivityInstances) {
					if(StringUtils.equals(parentActivityInstanceId,activityInstance.getParentActivityInstanceId())) {
						userIds.add(activityInstance.getAssignee());
					}
				}
				taskSelectUserService.saveUserTaskAssignment(taskEntity,userIds.toArray(new String[userIds.size()]));
			}else{
				// 不是多工作项，就取一个最新任务的历史处理人
				String userId = historicActivityInstance.getAssignee();
				taskSelectUserService.saveUserTaskAssignment(taskEntity,userId);
			}

		}else if(CONFIG_TYPE_4.equals(parRadioConfig)){
			// 参与者规则 可配置的 TODO
		}else if(CONFIG_TYPE_5.equals(parRadioConfig)){
			// 相关数据 流程变量${} 人或者岗位
			String aboutData = userTaskParticipant.getAboutData();
			String aboutDataKey = aboutData.substring(aboutData.indexOf("${") + 2, aboutData.indexOf("}", aboutData.indexOf("}")));
			Object aboutDataValue = taskEntity.getVariables().get(aboutDataKey);
			if(aboutDataValue==null){
				throw new RuntimeException("变量"+aboutDataKey+"不能为空");
			}
			if(aboutDataValue instanceof String){
				taskSelectUserService.saveUserTaskAssignment(taskEntity,(String)aboutDataValue);
			}else if(aboutDataValue instanceof Map) {
				String dataString = JSON.toJSONString(aboutDataValue);
				PostInfo postInfo = JSON.parseObject(dataString, PostInfo.class);
				taskSelectUserService.saveUserTaskAssignment(taskEntity, postInfo);
				orgCode = postInfo.getOrgCode();
			}else if(aboutDataValue instanceof PostInfo){
				PostInfo postInfo = (PostInfo)aboutDataValue;
				taskSelectUserService.saveUserTaskAssignment(taskEntity, postInfo);
				orgCode = postInfo.getOrgCode();
			}else if(aboutDataValue instanceof List){
				String dataString = JSON.toJSONString(aboutDataValue);
				List<PostInfo> nextPost = JSONArray.parseArray(dataString, PostInfo.class);
				taskSelectUserService.saveUserTaskAssignment(taskEntity,nextPost);
				orgCode = nextPost.get(0).getOrgCode();
			}else{
				throw new RuntimeException(aboutDataKey+"变量类型错误："+aboutDataValue.getClass());
			}

		}else if(CONFIG_TYPE_6.equals(parRadioConfig)){
			// 规则逻辑
			String serviceType = userTaskParticipant.getServiceType();
			String serviceValue = userTaskParticipant.getServiceValue();
			Collection<RuleLogicParam> ruleLogicParams = userTaskParticipant.getRuleLogicParams();
			// 参数
			Map<String,Object> param = new HashMap<>();
			for (RuleLogicParam ruleLogicParam : ruleLogicParams) {
				String name = ruleLogicParam.getName();
				String value = ruleLogicParam.getValue();
				String valueType = ruleLogicParam.getValueType();
				if(RuleLogicParam.VALUE_TYPE_CONSTANT.equals(valueType)){
					param.put(name,value);
				}
				if(RuleLogicParam.VALUE_TYPE_VARIABLE.equals(valueType)){
					param.put(name,taskEntity.getVariable(value));
				}
			}

			// 执行
			if(SERVICE_TYPE_LOCAL.equals(serviceType)){
				// 本地服务
				IWorkflowCustomUserService bean = applicationContext.getBean(serviceValue, IWorkflowCustomUserService.class);
				postInfos = bean.execute(param);
			}else if(SERVICE_TYPE_ONLINE.equals(serviceType)){
				// 远程服务
				HttpCustomUserService bean = applicationContext.getBean(HttpCustomUserService.class);
				postInfos = bean.execute(serviceValue,param);
			}
			if(CollectionUtils.isEmpty(postInfos)){
				throw new RuntimeException("找不到参与者");
			}else{
				taskSelectUserService.saveUserTaskAssignment(taskEntity,postInfos);
				orgCode = postInfos.iterator().next().getOrgCode();
			}
		}
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setOrgCode(orgCode);
		return taskInfo;
	}

	/**
	 * //查找上一审批节点
	 * @param entity
	 * @param backNodeId
	 * @param reBackNodeId
	 * @return
	 */
	private HistoricTaskInstance getLastHiTask(TaskEntity entity, String backNodeId, String reBackNodeId) {
		//取回

		if (StringUtils.isNotBlank(reBackNodeId)) {
			List<HistoricTaskInstance> hiTasks = historyService.createHistoricTaskInstanceQuery().finished().
					processInstanceId(entity.getProcessInstanceId()).orderByHistoricTaskInstanceEndTime().
					taskId(reBackNodeId).desc().listPage(0, 1);
			return CollectionUtils.isNotEmpty(hiTasks) ? hiTasks.get(0) : null;
		}

		//退回
		HistoricTaskInstance hiTask = null;
		if (StringUtils.isNotBlank(backNodeId)) {
			List<HistoricTaskInstance> hiTasks = historyService.createHistoricTaskInstanceQuery().finished().
					processInstanceId(entity.getProcessInstanceId()).orderByHistoricTaskInstanceEndTime().
					taskDefinitionKey(entity.getTaskDefinitionKey()).desc().listPage(0, 1);
			if (CollectionUtils.isEmpty(hiTasks)) {
				return hiTask;
			}
			hiTask = hiTasks.get(0);
			//判断是否加签
			List<HistoricVariableInstance> varList = historyService.createHistoricVariableInstanceQuery().taskIdIn(hiTask.getId()).list();
			String taskType = null;
			if (CollectionUtils.isNotEmpty(varList)){
				for (HistoricVariableInstance variableInstance : varList) {
					if (variableInstance.getVariableName().equals(SequentialAddedTaskState.TASK_TYPE_KEY)) {
						taskType = (String) variableInstance.getValue();
						break;
					}
				}
			}
			//加签任务，找到原始任务
			if (SequentialAddedTaskState.ADD_TASK.equals(taskType)){
				// camunda-TODO
				List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().executionId(hiTask.getExecutionId()).taskDefinitionKey(hiTask.getTaskDefinitionKey()).finished().orderByHistoricActivityInstanceStartTime().desc().finished().list();
				if(CollectionUtils.isNotEmpty(list)){
					hiTask = list.get(list.size()-1);
				}
			}
		}

		return hiTask;
	}

	public TaskBean checkAndClaimTaskIgnoreSuperAdmin(String userId,String taskId){
		if(StringUtils.equals("superadmin",userId)){
			return new TaskBean(WorkflowConstants.SUCCEED,"权限校验成功");
		}
		return checkAndClaimTask(userId,taskId);
	}

	/**
	 * 校验任务是否存在，并领取任务 (用于回退、提交验证)
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public TaskBean checkAndClaimTask(String userId,String taskId){
		if(StringUtils.isBlank(taskId)){
			return new TaskBean(FAILED,"任务ID为空!");
		}

		if(StringUtils.isBlank(userId)){
			return new TaskBean(FAILED,"用户ID为空");
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//验证是否是已处理任务
		if(task != null){
			String assignee = task.getAssignee();
			// 已经被领取了，且userId一致，有权限审批
			if(StringUtils.isNotBlank(assignee)){
				if(StringUtils.equals(userId,assignee)){
					return new TaskBean(WorkflowConstants.SUCCEED,"权限校验成功");
				}else{
					return new TaskBean(WorkflowConstants.FAILED,"对不起，您没有权限");
				}
			}
		}else{
            return new TaskBean(WorkflowConstants.FAILED,"任务已被处理");
        }
		return taskSelectUserService.userCheck(taskId, userId, task.getTenantId());
	}

	/**
	 * 保存规则参数到流程变量中
	 * @param ruleParams
	 */
	public void saveRuleParams(String taskId, Map<String, Object> ruleParams) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String DBJson  = (String)runtimeService.getVariable(task.getExecutionId(), ExecutionDB._RULE_DOMAIN_KEY);
		ExecutionDB executionDB = new ExecutionDB(DBJson);
		executionDB.setBlockDataIgnoreKeyRepeat(ExecutionDB._COLLECT_DATA_BLOCK,ruleParams);
		runtimeService.setVariable(task.getExecutionId(), ExecutionDB._RULE_DOMAIN_KEY,JSON.toJSONString(executionDB.getDB()));
	}

	/**
	 * 去除字符串前后双引号
	 * @return
	 */
	public static String trimQuotes(String str){
		if (str == null) {
			return  "";
		}
		if(!StringUtils.isEmpty(str) && str.length() >1){
			if("\"".equals(str.charAt(0)+"") && "\"".equals(str.charAt(str.length()-1)+"")){
				return str.substring(1,str.length()-1);
			}
		}
		return str;
	}

	/**
	 * 定时终止离岗移交时获取taskEntity
	 * @param taskEntity
	 * @param toUsers
	 * @param fromUsers
	 * @param classifyId
	 * @param procInstId
	 * @param beforeTransfers
	 * @param leaveTransferEntity
	 * @return
	 */
	public TaskEntity setTaskEntityWhenAbandonTransferOrganizationTask(TaskEntity taskEntity, Set<String> toUsers,
																	   Set<String> fromUsers, String classifyId, String procInstId, List<String> beforeTransfers,
																	   LeaveTransferEntity leaveTransferEntity){
		// camunda-TODO
//    	String category = taskEntity.getCategory();
		String processInstanceId = taskEntity.getProcessInstanceId();
//    	if (!(StringUtils.isNotEmpty(classifyId) && classifyId.equals(category))) {
//			return taskEntity;
//		}
		if (!(StringUtils.isNotEmpty(processInstanceId) && procInstId.equals(processInstanceId))) {
			return taskEntity;
		}
		String assignee = taskEntity.getAssignee();
		List<String> fromUsersList = Lists.newArrayList(fromUsers);
		List<String> toUsersList = Lists.newArrayList(toUsers);
		//在任务变量beforeTransfers里面找不到申请人,说明该条任务没被移交过
		//或 在任务变量beforeTransfers里面找到申请人，当申请人和非申请人均在任务变量里面
		if ((!beforeTransfers.contains(fromUsersList.get(0))) ||
				(beforeTransfers.contains(fromUsersList.get(0))
						&& beforeTransfers.contains(toUsersList.get(0)))) {
			return taskEntity;
		}

		//当处理人存在时
		if (StringUtils.isNotEmpty(assignee)) {
			taskEntity.setAssignee(fromUsersList.get(0));
			taskEntity = this.setUserInfo(taskEntity, leaveTransferEntity);
			return taskEntity;
		}

		//当为候选用户时
		taskEntity.deleteCandidateUser(toUsersList.get(0));
		taskEntity.addCandidateUser(fromUsersList.get(0));
		return taskEntity;
	}

	/**
	 * 每个任务id只能关联一个审批意见
	 * @param taskId
	 * @param comment
	 * @param processInstanceId
	 */
	public void setOnlyOneTaskComment(String taskId, String comment, String processInstanceId) {
		taskService.createComment(taskId, processInstanceId, comment);
	}

	private  CommandExecutor  getCommandExecutor() {
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		return  executor;
	}

	/**
	 * 处理会签审批结果
	 * @param entity
	 */
	public void dealVotingResult(TaskEntity entity) {
		//会签审批策略
		String votingStrategy = workflowProcessDefineService.getNodeExtensionProperty(entity.getProcessDefinitionId(),
				entity.getTaskDefinitionKey(), Votingstrategy.class, String.class);
//			if(StringUtils.isBlank(votingStrategy) || "None".equals(votingStrategy)){
//				return;
//			}

		//未完成会签任务数
		Map<String, Object> variables = entity.getVariables();
		Integer nrOfActiveInstances = (Integer) variables.get("nrOfActiveInstances");
		Integer count = (Integer) variables.get("nrOfInstances");
		Integer nrOfCompletedInstances = (Integer) variables.get("nrOfCompletedInstances");
		if (nrOfActiveInstances == null) {
			return;
		}

		//1.获取所有会签审批意见
		Collection<String> votingOpinionResults = getAllVotingOpinionResults(entity,nrOfCompletedInstances);
		if (votingOpinionResults == null || votingOpinionResults.isEmpty()) {
			return;
		}

		//2.计算审批结果
		String votingResult = calculateVotingResult(votingStrategy,votingOpinionResults,count);

		Map<String,Object> varMap =this.resetCompleteCond(entity.getExecution(), votingOpinionResults);

		logger.info(" voting count is :{} , votingStrategy is : {}, votingOpinionResults is {}, executionId :{} ,processInstanceId : {}",
				count,votingStrategy,votingOpinionResults,entity.getExecutionId(),entity.getProcessInstanceId());

		if (nrOfActiveInstances == 1) {
			//会签最后一个审批任务,更新会签结果
			updateVotingResult(entity,votingResult);
			//赋值流程变量，agreeNum、againstNum、noOpinionNum
			Map<String, Object> numMap = updateOpinionNum(entity.getExecution(), votingOpinionResults,varMap);
			//会签结束调用模板方法
			Map map = new HashMap();
			map.putAll(numMap);
			map.put("votingResult",votingResult);
			map.put("votingOpinionResults",votingOpinionResults);
			map.put("taskId",entity.getId());
			map.put("assignee",entity.getAssignee());
			map.put("executionId",entity.getExecutionId());
			map.put("processInstanceId",entity.getProcessInstanceId());
			map.put("processDefinitionId",entity.getProcessDefinitionId());
			map.put("taskDefinitionKey",entity.getTaskDefinitionKey());
			map.put("tenantId",entity.getTenantId());
			map.put("votingStrategy",votingStrategy);
			map.put("businessKey", entity.getProcessInstance().getBusinessKey());
			// TODO 先注释规则调用
//			try {
//				IWorkflowController workflowController = this.getIWorkflowController(entity.getProcessDefinitionId());
//				workflowController.afterVotingCompleted(map);
//			} catch (BeansException e) {
//				logger.warn(e.getMessage());
//			}
//
//			HttpWorkController httpWorkController = applicationContext.getBean(HttpWorkController.class);
//			if(httpWorkController.getMethods().contains(HttpWorkController.AFTER_VOTING_COMPLETED)){
//				httpWorkController.afterVotingCompleted(map);
//			}
		} else {
			//会签进行中

			//根据会签策略判断是否要提前会签
			if(isStopVotingNow(votingStrategy,votingOpinionResults,count)){
				//TODO 立即终止流程

				//更新会签结果
				updateVotingResult(entity,votingResult);
			}

		}


	}

	private boolean isStopVotingNow(String votingStrategy, Collection<String> votingOpinionResults, Integer count) {
		boolean isStopNow = false;
		int againstNum = 0;
		for (String votingOpinionResult : votingOpinionResults) {
			if("against".equals(votingOpinionResult)){
				againstNum ++;
			}
		}

		if ("rejectByOne".equals(votingStrategy) && againstNum >0) {
			//一票否决
			isStopNow =  true;

		} else if ("halfThrough".equals(votingStrategy)) {
			//半数通过
			if (againstNum / (count * 1.0) > 0.5) {
				isStopNow = true;
			}
		}

		return isStopNow;
	}

	/**
	 * 更新会签审批结果
	 * @param entity
	 * @param votingResult
	 */
	private void updateVotingResult(final TaskEntity entity, final String votingResult) {
		//更新至流程变量，为分支判断使用
		runtimeService.setVariable(entity.getExecutionId(),"votingResult",votingResult);

		//更新值历史任务本地变量中,为分析使用
		// camunda-TODO taskDueDate和taskCreatedOn一个意思？
		/**
		 * taskDueDate 仅选择在给定日期创建的任务。
		 * taskDueBefore 仅选择在给定日期之前创建的任务。
		 * taskDueAfter 仅选择在给定日期之后创建的任务。
		 */
		final List<HistoricTaskInstance> hiTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(entity.getProcessInstanceId())
				.taskDefinitionKey(entity.getTaskDefinitionKey()).taskDueDate(entity.getCreateTime()).finished().list();

		//entity.setVariableLocal("votingResult",votingResult);

		//如果会签提前结束，更新结果至为审批的任务
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(entity.getProcessInstanceId()).taskCreatedOn(entity.getCreateTime()).list();
		if (tasks != null) {
			for (Task task : tasks) {
				TaskEntity taskEntity = (TaskEntity) task;
				taskEntity.setVariableLocal("votingResult",votingResult);
			}
		}

		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			//		managementService.executeCommand(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				for (HistoricTaskInstance hiTask : hiTasks) {

					// camunda-TODO
//					VariableTypes variableTypes = Context
//							.getProcessEngineConfiguration()
//							.getFormTypes().getVariableTypes();
//
//					VariableType type = variableTypes.findVariableType("String");
					TypedValue   typeValue  = new StringValueImpl(votingResult);
					VariableInstanceEntity variable = VariableInstanceEntity.create("votingResult",typeValue,true);
					variable.setProcessInstanceId(entity.getProcessInstanceId());
					variable.setExecutionId(hiTask.getExecutionId());
					variable.setTaskId(hiTask.getId());
					VariableInstanceEntity.insert(variable);
					//HistoricVariableInstanceEntity.copyAndInsert(variable);
				}
				return null;
			}

		});

		logger.info(" update voting result :{} ,executionId :{} ,processInstanceId : {}",votingResult,entity.getExecutionId(),entity.getProcessInstanceId());
	}

	/**
	 * 计算会签结果
	 * @param votingStrategy
	 * @param votingOpinionResults
	 * @param count
	 * @return
	 */
	private String calculateVotingResult(String votingStrategy, Collection<String> votingOpinionResults, Integer count) {

		int agreeCount = 0;
		int againstCount = 0;
		for (String votingOpinionResult : votingOpinionResults) {
			if ("against".equals(votingOpinionResult)) {
				againstCount++;
			} else if ("agree".equals(votingOpinionResult)) {
				agreeCount++;
			}
		}

		String votingResult = "pass";
		if ("rejectByOne".equals(votingStrategy)) {
			//一票否决
			if (againstCount > 0) {
				votingResult = "reject";
			}

		} else if ("passByOne".equals(votingStrategy)) {
			//一票通过
			if (agreeCount > 0) {
				votingResult = "pass";
			}else{
				votingResult = "reject";
			}
		} else if ("halfThrough".equals(votingStrategy)) {
			//半数通过
			if (agreeCount / (count * 1.0) >= 0.5) {
				votingResult = "pass";
			} else {
				votingResult = "reject";
			}
		} else if ("twoThirds".equals(votingStrategy)) {
			//三分之二通过
			if (agreeCount / (count * 1.0) >= 2.0/3) {
				votingResult = "pass";
			} else {
				votingResult = "reject";
			}
		}

		return votingResult;
	}

	/**
	 * 获取会签审批结果
	 * @param entity
	 * @param count
	 * @return
	 */
	private Collection<String> getAllVotingOpinionResults(TaskEntity entity, Integer count) {

		//获取已经会审的任务
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(entity.getProcessInstanceId())
				.taskDefinitionKey(entity.getTaskDefinitionKey()).finished().orderByHistoricActivityInstanceStartTime().desc().listPage(0, count);
		if (list == null) {
			return null;
		}

		//获取流程实例的审批结果
		List<HistoricVariableInstance> votingOpinions = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(entity.getProcessInstanceId()).variableName("votingOpinion").list();
		if (votingOpinions == null) {
			return null;
		}

		//筛选当前会签的结果
		Map<String,String> votingOpinionResults = new HashMap<>();
		for (HistoricVariableInstance votingOpinion : votingOpinions) {
			HistoricVariableInstanceEntity votingOpinionEntity = (HistoricVariableInstanceEntity) votingOpinion;

			if (votingOpinionEntity.getExecutionId().equals(entity.getExecutionId())) {
				collectVoting(votingOpinionResults,votingOpinionEntity.getExecutionId(),votingOpinionEntity.getTextValue());
			} else {
				for (HistoricTaskInstance historicTaskInstance : list) {
					if (historicTaskInstance.getExecutionId().equals(votingOpinionEntity.getExecutionId())) {
						collectVoting(votingOpinionResults, historicTaskInstance.getExecutionId(),votingOpinionEntity.getTextValue());
					}
				}
			}
		};
		return votingOpinionResults.values();
	}

	private void collectVoting(Map<String, String> votingOpinionResults, String executionId, String textValue) {
		if(votingOpinionResults.get(executionId)==null){
			votingOpinionResults.put(executionId,textValue);
		}else if("against".equals(textValue)){
			votingOpinionResults.put(executionId,"against");
		}else if("agree".equals(textValue)&&!"against".equals(votingOpinionResults.get(executionId))){
			votingOpinionResults.put(executionId,"agree");
		}

	}



	public Map<String,Object> getProcessContext(Task task, String  nextActId, String processDefinitionId) {
		HashMap<String, Object> result = new HashMap<>();
		try{
			// 运行时数据
			if(task!=null){
				String executionId = task.getExecutionId();
				String startOrgId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_ORG_ID_KEY);
				result.put("startOrgId", startOrgId);
				String startRoleId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_ROLE_ID_KEY);
				result.put("startRoleId", startRoleId);
				String startDeptId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_DEPT_ID_KEY);
				result.put("startDeptId", startDeptId);
				String startParentOrgId = (String) runtimeService.getVariable(executionId, AssignmentModel.START_USER_PARENT_ORG_ID_KEY);
				result.put("startParentOrgId", startParentOrgId);
			}
		}catch (Exception e){

		}
		return result;

	}

	private    Map<String,Object>  resetCompleteCond(Execution execution, Collection<String> votingOpinionResults) {
		int agreeNum = 0;
		int againstNum = 0;
		int noOpinionNum = 0;
		for (String result : votingOpinionResults) {
			if("agree".equals(result)){
				agreeNum++;
			}
			if("against".equals(result)){
				againstNum++;
			}
			if("noOpinion".equals(result)){
				noOpinionNum++;
			}
		}
		String executionId = execution.getId();
		//同意数量
		runtimeService.setVariable(executionId,"agreeNum",agreeNum);
		//反对数量
		runtimeService.setVariable(executionId,"againstNum",againstNum);
		//弃权数量
		runtimeService.setVariable(executionId,"noOpinionNum",noOpinionNum);
		//总的投票数量
		runtimeService.setVariable(executionId,"votingNum",votingOpinionResults.size());
		Map<String,Object> map = new  HashMap<>();
		map.put("agreeNum", agreeNum);
		map.put("againstNum", againstNum);
		map.put("noOpinionNum", noOpinionNum);
		map.put("votingNum", votingOpinionResults.size());
		return  map;
	}

	private Map<String,Object> updateOpinionNum(Execution execution, Collection<String> votingOpinionResults,Map<String,Object> varMap){
		Object agreeNum = varMap.get("agreeNum");
		Object againstNum = varMap.get("againstNum");
		Object noOpinionNum = varMap.get("noOpinionNum");

		String executionId = execution.getId();
		//总的投票数量
		runtimeService.setVariable(executionId,"votingNum",votingOpinionResults.size());

		ExecutionDB executionDB = new ExecutionDB((DelegateExecution) execution);
		Map<String,Object> map = new HashMap<>();
		map.put("agreeNum",agreeNum);
		map.put("againstNum",againstNum);
		map.put("noOpinionNum",noOpinionNum);
		map.put("votingNum",votingOpinionResults.size());
		try {
			executionDB.setBlockDataWithKeyNoRepeat(ExecutionDB._COLLECT_DATA_BLOCK, map);
		} catch (Exception e) {
			logger.info("updateOpinionNum set params error");
			throw new RuntimeException(e);
		}
		return map;
	}

	/**
	 * 根据processDefinitionId获取流程完成处理类
	 * @param processDefinitionId
	 * @return
	 */
	private IWorkflowController getIWorkflowController(String processDefinitionId){
		ProcessBean processBean = new ProcessBean();
		processBean.setProcessDefineId(processDefinitionId);
		processBean = findProcessHandleClass(processBean);
		String process_completed_class=processBean.getExtendedData().get("process_completed_class").replaceAll("\"", "");
		return (IWorkflowController)applicationContext.getBean(process_completed_class);
	}

	/**
	 * 流程结束后获取流程变量信息
	 * @param processInfo
	 * @return
	 */
	public TaskBean findFinishedVariable(ProcessInfo processInfo){
		TaskBean taskBean = findProcessVariable(processInfo);
		if (SUCCEED.equals(taskBean.getRetCode())){
			Map<String, Object> variables = taskBean.getProcessVariableMap();
			Object jet = variables.get("_TASK_EXPAND_DATA");
			if (jet != null) {
				Map<String, String> taskmap = (Map<String, String>) JSONObject.parse(jet.toString());
				taskBean.setTaskExpandData(taskmap);
			}
			String domainStr = MapUtils.getString(variables,ExecutionDB._RULE_DOMAIN_KEY);
			if(StringUtils.isNotBlank(domainStr)){
				ExecutionDB executionDB = new ExecutionDB(domainStr);
				Map<String, Object> blockData = executionDB.getBlockData(ExecutionDB._APPLY_DATA_BLOCK);
				if (blockData != null){
					// 启动的formData
					taskBean.setFormData(blockData);
				}
				Map<String, Object> collectData = executionDB.getBlockData(ExecutionDB._COLLECT_DATA_BLOCK);
				if (collectData != null){
					// 数据源采集的所有数据
					variables.put("allCollectData", collectData);
				}
				Map<String, Object> ruleData = executionDB.getBlockData(ExecutionDB._RULE_DATA_BLOCK);
				if (ruleData != null){
					// 调用规则的所有结果
					taskBean.setRuleParams(ruleData);
				}
			}
		}
		return taskBean;
	}

	/**
	 * 离岗移交终止
	 * @param wfLeaveTransfer
	 * @return
	 */
	public TaskBean stopLeaveTransfer(WfLeaveTransfer wfLeaveTransfer){
		String ids = wfLeaveTransfer.getIds();
		TaskBean taskBean = new TaskBean();
		String[] allId = ids.split(",");
		List<LeaveTransferEntity> leaveTransfers = workflowLeaveTransferService.getLeaveTransfersByIds(allId);
		for (LeaveTransferEntity leaveTransfer : leaveTransfers) {
			TaskBean result = workflowLeaveTransferService.stopTransfer(leaveTransfer);
			if (FAILED.equals(result.getRetCode())) {
				return result;
			}
		}
		taskBean.setRetCode(SUCCEED);
		taskBean.setRetMsg("强制终止成功");
		return taskBean;
	}

	/**
	 * 待办已办获取扩展字段
	 * 1.只传一个分类wfClassifyCode，查询其所有扩展字段
	 * 2.传多个分类wfClassifyCodes或者不传分类，查询公共的扩展字段
	 * @param taskInfo
	 * @return
	 */
	private List<WfTaskExpandPlanEntity> getExpandPlan(TaskInfo taskInfo){
		List<WfTaskExpandPlanEntity> plans = Collections.emptyList();
		if (StringUtils.isNotBlank(taskInfo.getWfClassifyCode())) {
			plans = workflowTaskExpandPlanService.getExpandPlanByClassifyCode(taskInfo.getWfClassifyCode(), taskInfo.getTenantId());
		}
		return plans;
	}

	/**
	 * 待办已办获取当前处理人中文名 把currentUserIds翻译成currentUserNames，把preUser翻译成preUserName
	 * @param hiTasks
	 */
	public void addUserName(List<Map> hiTasks){
		Set<String> userIds = TransformUtil.getValues(hiTasks, new String[]{"preUser","powerUsers"});
		if(CollectionUtils.isNotEmpty(userIds)){
			Map<String,Object> cnNames = workflowUserService.transferUserName(userIds);
			TransformUtil.transfromString(hiTasks,cnNames,"preUser","preUserName");
			TransformUtil.transfromList(hiTasks,cnNames,"powerUsers","powerUserNames");
		}
	}

	private List<WfTaskExpandPlanEntity> getCommonExpandPlanEntity(List<WfTaskExpandPlanEntity> plans) {
		Map<String,WfTaskExpandPlanEntity> map = new HashMap<>();
		Set<String> diffValues = new HashSet<>();
		for (WfTaskExpandPlanEntity plan : plans) {
			//第一次，添加到map
			if(!map.containsKey(plan.getFieldValue())){
				map.put(plan.getFieldValue(),plan);
			}else{
				//如果map中有，判断key是否相同，不相同需要剔除这个field
				if(!map.get(plan.getFieldValue()).getFieldKey().equals(plan.getFieldKey())){
					diffValues.add(plan.getFieldValue());
				}
			}
		}
		//删除非公共的field
		for (String diffValue : diffValues) {
			map.remove(diffValue);
		}
		return new ArrayList<>(map.values());
	}

	/**
	 * 修改历史任务处理人
	 */
	public void updateHiTaskAssignee(final String taskId,final String assignee){
		HiTaskEntity hiTaskEntity=new  HiTaskEntity();
		hiTaskEntity.setTaskId(taskId);
		hiTaskEntity.setAssignee(assignee);
		actHiTaskinstMapper.updateHiTaskAssignee(hiTaskEntity);
	}

	/**
	 * 修改运行任务处理人
	 */
	public void updateRuTaskAssignee(final String taskId,final String assignee){
		RuTaskEntity ruTaskEntity = new RuTaskEntity();
		ruTaskEntity.setTaskId(taskId);
		ruTaskEntity.setAssignee(assignee);
		actRuTaskMapper.updateRuTaskAssignee(ruTaskEntity);
	}

	/**
	 * 获取个人代办任务数量
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findTaskCounts(TaskInfo taskInfo) {
		TaskBean taskBean = new TaskBean();
		WfTaskCountsQuery query = new WfTaskCountsQuery();
		CommandExecutor commandExe =this.getCommandExecutor();
		query.setCommandExecutor(commandExe);
		WfHiTaskCountsQuery hiQuery = new WfHiTaskCountsQuery();
		hiQuery.setCommandExecutor(commandExe);
		if (taskInfo.getAssignees()==null||taskInfo.getAssignees().size()==0){
			ResultUtil.setReturnCode(taskBean, FAILED, "未获取到处理人参数");
			return taskBean;
		}
		List<String> assignees = taskInfo.getAssignees();
		if (taskInfo.getIsNotLimit()!=null&&!"Y".equals(taskInfo.getIsNotLimit())){
			if(taskInfo.getAssignees().size()>100){
				assignees = taskInfo.getAssignees().subList(0,100);
			}
		}
		if (!StringUtils.isEmpty(taskInfo.getTenantId())) {
			query.tenantId(taskInfo.getTenantId());
			hiQuery.tenantId(taskInfo.getTenantId());
		}
		query.assignees(assignees);
		List<Map> taskCount = query.list();
		hiQuery.assignees(assignees);
		if(taskInfo.getSingleHiTaskStatus()!=null){
			hiQuery.singleHiTaskStatus(taskInfo.getSingleHiTaskStatus());
		}
		hiQuery.isEndIncludeRuTask("1");
		List<Map> hiTaskCount = hiQuery.list();
		hiQuery.isEndIncludeRuTask("0");
		List<Map> hiEndTaskCount = hiQuery.list();
		if(taskCount!=null&&taskCount.size()!=0){
			for (Map pendTask: taskCount) {
				if (pendTask.containsKey("assignee")){
					String assignee = (String) pendTask.get("assignee");
					for (Map hiTask: hiTaskCount) {
						if (hiTask.containsKey("assignee")&&assignee.equals(hiTask.get("assignee"))){
							if (hiTask.containsKey("total")&&hiTask.get("total")!=null){
								pendTask.put("hiTaskCount",hiTask.get("total"));
							}
						}
					}
					if (!pendTask.containsKey("hiTaskCount")||pendTask.get("hiTaskCount")==null){
						pendTask.put("hiTaskCount",0);
					}
					for (Map hiEndTask: hiEndTaskCount) {
						if (hiEndTask.containsKey("assignee")&&assignee.equals(hiEndTask.get("assignee"))){
							if (hiEndTask.containsKey("total")&&hiEndTask.get("total")!=null){
								pendTask.put("hiEndTaskCount",hiEndTask.get("total"));
							}
						}
					}
					if (!pendTask.containsKey("hiEndTaskCount")||pendTask.get("hiEndTaskCount")==null){
						pendTask.put("hiEndTaskCount",0);
					}
					if(pendTask.containsKey("total")&&pendTask.get("total")!=null){
						pendTask.put("taskCount",pendTask.get("total"));
						pendTask.remove("total");
					}else {
						pendTask.put("taskCount",0);
					}
				}

			}
			taskBean.setRetCode("0");
			taskBean.setTaskList(taskCount);
		}else{
			ResultUtil.setReturnCode(taskBean,"1","未获取到任务数据");
		}
		return taskBean;
	}

	public TaskBean findHiTaskPoolExecuteDataList(TaskInfo taskInfo){
		TaskBean taskBean = new TaskBean();

		List<WfTaskExpandPlanEntity> plans = getExpandPlan(taskInfo);

		List<String> fieldMapping = WfExpandPlanUtil.generateFieldMapping(plans);
		List<String> fieldCondition = generateFieldCondition(taskInfo,plans);
		List<Map> columnsList = generateColumnsMapping(plans);
		columnsList.addAll(getRegularPendTaskColumns());

		WfHiTaskPoolExecuteDataQuery query = new WfHiTaskPoolExecuteDataQuery();
		query.taskId(taskInfo.getTaskId());
		query.sysId(taskInfo.getSysId());
		query.userId(taskInfo.gettUserId());
		query.isAllot(taskInfo.getIsAllot());
		query.tenantId(taskInfo.getTenantId());
		query.taskId(taskInfo.getTaskId());
		query.fieldMapping(fieldMapping);
		query.fieldCondition(fieldCondition);
		int currentPage = StringUtils.isNumericSpace(taskInfo.getCurrentPage()) ? Integer.parseInt(taskInfo.getCurrentPage()):1;
		int pageSize = StringUtils.isNumericSpace(taskInfo.getPageSize()) ? Integer.parseInt(taskInfo.getPageSize()):10;
		int firstResult = pageSize * (currentPage - 1);

		//查询内容
		List list = query.listPage(firstResult, pageSize);
		long count = query.count();
		taskBean.setTaskList(list);
		taskBean.setTotal(count+"");
		return taskBean;
	}

	public TaskBean findPendOrgTaskList(TaskInfo taskInfo) {
		WfOrgTaskQuery  orgTaskQuery =  new  WfOrgTaskQuery();
		if (this.managementService instanceof  ManagementServiceImpl) {
			orgTaskQuery.setCommandExecutor(((ManagementServiceImpl)this.managementService).getCommandExecutor());
		}
		List<WfTaskExpandPlanEntity> plans = this.getExpandPlan(taskInfo);
		List<String> fieldMapping = WfExpandPlanUtil.generateFieldMapping(plans);
		List<String> fieldCondition = generateFieldCondition(taskInfo,plans);

		orgTaskQuery.fieldMapping(fieldMapping);
		orgTaskQuery.fieldCondition(fieldCondition);

		if(StringUtils.isNotBlank(taskInfo.getProcessInstanceId())){
			orgTaskQuery.processInstanceId(taskInfo.getProcessInstanceId());
		}
		orgTaskQuery.classifyCode(taskInfo.getWfClassifyCode());
		orgTaskQuery.taskDefinitionKey(taskInfo.getActivityId());
		orgTaskQuery.processDefinitionId(taskInfo.getProcessDefinitionId());
		orgTaskQuery.startUserId(taskInfo.getStartUserId());
		orgTaskQuery.startUserName(taskInfo.getStartUserName());
		orgTaskQuery.assignee(taskInfo.getUserName());
		orgTaskQuery.startUserOrgCode(taskInfo.getStartUserOrgCode());

		orgTaskQuery.taskNameLike(taskInfo.getTaskName());
		orgTaskQuery.taskId(taskInfo.getTaskId());
		orgTaskQuery.classifyCode(taskInfo.getWfClassifyCode());
		if (StringUtils.isNotBlank(taskInfo.getTenantId())) {
			orgTaskQuery.tenantId(taskInfo.getTenantId());
		}
		if (taskInfo.getTenantIds() != null) {
			orgTaskQuery.tenantIds(taskInfo.getTenantIds());
		}
		orgTaskQuery.businessKey(taskInfo.getBusinessKey());
		orgTaskQuery.businessKeyLike(taskInfo.getBusinessKeyLike());
		orgTaskQuery.processDefineName(taskInfo.getProcessDefineName());
		orgTaskQuery.processDefineNameLike(taskInfo.getProcessDefineNameLike());
		orgTaskQuery.processDefineKey(taskInfo.getProcessDefineKey());
		orgTaskQuery.processDefineKeyLike(taskInfo.getProcessDefineKeyLike());
		orgTaskQuery.processDefineKeys(taskInfo.getProcessDefineKeys());
		orgTaskQuery.unequalActivityId(taskInfo.getUnequalActivityId());
		orgTaskQuery.unequalActivityName(taskInfo.getUnequalActivityName());
		orgTaskQuery.postInfos(getPostInfoQueryParam(taskInfo));
		orgTaskQuery.createTimeAfter(taskInfo.getCreateTimeAfter());
		orgTaskQuery.createTimeBefore(taskInfo.getCreateTimeBefore());
		orgTaskQuery.processCreateTimeBefore(taskInfo.getProcessCreateTimeBefore());
		orgTaskQuery.processCreateTimeAfter(taskInfo.getProcessCreateTimeAfter());
		orgTaskQuery.claimTimeAfter(taskInfo.getClaimTimeAfter());
		orgTaskQuery.claimTimeBefore(taskInfo.getClaimTimeBefore());
		orgTaskQuery.createTimeSort(taskInfo.getCreateTimeSort());
		orgTaskQuery.queryAllTask(taskInfo.isQueryAllTask());
		orgTaskQuery.claimStatus(taskInfo.getClaimStatus());
		TaskBean taskBean =this.queryHandler.commonPageQuery(taskInfo, orgTaskQuery, new QueryResultHandler() {
			@Override
			public Map<String, Object> handlerRecord(Object record) {
				Map<String,Object> data =  (Map<String, Object>) record;
				data.put("createTime", DateUtil.formatDate(DateUtil.toDate(data.get("createTime")),"yyyy-MM-dd HH:mm:ss.SSS"));
				data.put("dueDate", DateUtil.formatDate(DateUtil.toDate(data.get("dueDate"))));
				data.put("claimTime", DateUtil.formatDate(DateUtil.toDate(data.get("claimTime"))));
				Object processStartTime = data.get("processStartTime");
				data.put("processStartTime", DateUtil.formatDate(DateUtil.toDate(processStartTime)));

				return data;
			}
		});
		// 查询所有任务
		if(taskInfo.isQueryAllTask()){
			List<Map> taskList = taskBean.getTaskList();
			List<ActRuIdentitylinkEntity> postList = new ArrayList<>();
			ActRuIdentityLinkQuery actRuIdentityLinkQuery= new ActRuIdentityLinkQuery();
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
				map.put("currentTaskPost",new ArrayList<>());
				for (ActRuIdentitylinkEntity actRuIdentitylinkEntity : postList) {
					if(StringUtils.equals(taskId,actRuIdentitylinkEntity.getTaskId())){
						List currentTaskPost = (List)map.get("currentTaskPost");
						currentTaskPost.add(actRuIdentitylinkEntity);
					}
				}
			}
		}

		return taskBean;
	}

	public TaskBean findPendOrgHiTaskList(TaskInfo taskInfo) {
		WfOrgHiTaskQuery orgTaskQuery =  new  WfOrgHiTaskQuery();
		if (this.managementService instanceof  ManagementServiceImpl) {
			orgTaskQuery.setCommandExecutor(((ManagementServiceImpl)this.managementService).getCommandExecutor());
		}
		List<WfTaskExpandPlanEntity> plans = this.getExpandPlan(taskInfo);
		List<String> fieldMapping = WfExpandPlanUtil.generateFieldMapping(plans);
		List<String> fieldCondition = generateFieldCondition(taskInfo,plans);

		orgTaskQuery.fieldMapping(fieldMapping);
		orgTaskQuery.fieldCondition(fieldCondition);

		if(StringUtils.isNotBlank(taskInfo.getProcessInstanceId())){
			orgTaskQuery.processInstanceId(taskInfo.getProcessInstanceId());
		}
		if (StringUtils.isNotBlank(taskInfo.getTenantId())) {
			orgTaskQuery.tenantId(taskInfo.getTenantId());
		}
		if (taskInfo.getTenantIds() != null) {
			orgTaskQuery.tenantIds(taskInfo.getTenantIds());
		}
		orgTaskQuery.classifyCode(taskInfo.getWfClassifyCode())
				.taskDefinitionKey(taskInfo.getActivityId())
				.processDefinitionId(taskInfo.getProcessDefinitionId())
				.startUserId(taskInfo.getStartUserId())
				.startUserName(taskInfo.getStartUserName())
				.startUserOrgCode(taskInfo.getStartUserOrgCode())
				.taskNameLike(taskInfo.getTaskName())
				.taskId(taskInfo.getTaskId())
				.classifyCode(taskInfo.getWfClassifyCode())
				.businessKey(taskInfo.getBusinessKey())
				.businessKeyLike(taskInfo.getBusinessKeyLike())
				.processDefineName(taskInfo.getProcessDefineName())
				.processDefineNameLike(taskInfo.getProcessDefineNameLike())
				.processDefineKey(taskInfo.getProcessDefineKey())
				.processDefineKeyLike(taskInfo.getProcessDefineKeyLike())
				.processDefineKeys(taskInfo.getProcessDefineKeys())
				.unequalActivityId(taskInfo.getUnequalActivityId())
				.unequalActivityName(taskInfo.getUnequalActivityName())
				.postInfos(getPostInfoQueryParam(taskInfo))
				.createTimeAfter(taskInfo.getCreateTimeAfter())
				.createTimeBefore(taskInfo.getCreateTimeBefore())
				.endTimeAfter(taskInfo.getEndTimeAfter())
				.endTimeBefore(taskInfo.getEndTimeBefore())
				.processCreateTimeBefore(taskInfo.getProcessCreateTimeBefore())
				.processCreateTimeAfter(taskInfo.getProcessCreateTimeAfter())
				.claimTimeAfter(taskInfo.getClaimTimeAfter())
				.claimTimeBefore(taskInfo.getClaimTimeBefore())
				.createTimeSort(taskInfo.getCreateTimeSort())
				.queryAllTask(taskInfo.isQueryAllTask());
		TaskBean taskBean =this.queryHandler.commonPageQuery(taskInfo, orgTaskQuery, new QueryResultHandler() {
			@Override
			public Map<String, Object> handlerRecord(Object record) {
				Map<String,Object> data =  (Map<String, Object>) record;
				data.put("createTime", DateUtil.formatDate(DateUtil.toDate(data.get("createTime")),"yyyy-MM-dd HH:mm:ss.SSS"));
				data.put("dueDate", DateUtil.formatDate(DateUtil.toDate(data.get("dueDate"))));
				data.put("claimTime", DateUtil.formatDate(DateUtil.toDate(data.get("claimTime"))));
				Object processStartTime = data.get("processStartTime");
				data.put("processStartTime", DateUtil.formatDate(DateUtil.toDate(processStartTime)));

				return data;
			}
		});
		// 查询所有任务
		if(taskInfo.isQueryAllTask()){
			List<Map> taskList = taskBean.getTaskList();
			List<ActRuIdentitylinkEntity> postList = new ArrayList<>();
			ActRuIdentityLinkQuery actRuIdentityLinkQuery= new ActRuIdentityLinkQuery();
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
				map.put("currentTaskPost",new ArrayList<>());
				for (ActRuIdentitylinkEntity actRuIdentitylinkEntity : postList) {
					if(StringUtils.equals(taskId,actRuIdentitylinkEntity.getTaskId())){
						List currentTaskPost = (List)map.get("currentTaskPost");
						currentTaskPost.add(actRuIdentitylinkEntity);
					}
				}
			}
		}
		return taskBean;
	}

	/**
	 * 根据客户传过来的机构角色信息添加参数
	 * @param taskInfo
	 * @return
	 */
	public List<PostInfo> getPostInfoQueryParam(TaskInfo taskInfo){
		List<PostInfo> postInfos = new ArrayList<>();
		// 用户
		if(StringUtils.isNotBlank(taskInfo.getUserId())){
			PostInfo postInfo = new PostInfo();
			postInfo.setUserId(taskInfo.getUserId());
			postInfo.setType(Participants.TYPE_USER);
			postInfos.add(postInfo);
		}
		// 机构
		if(StringUtils.isNotBlank(taskInfo.getOrgCode())){
			PostInfo postInfo = new PostInfo();
			postInfo.setOrgCode(taskInfo.getOrgCode());
			postInfo.setType(Participants.TYPE_ORG);
			postInfos.add(postInfo);
		}
		// 部门
		if(StringUtils.isNotBlank(taskInfo.getDeptCode())){
			PostInfo postInfo = new PostInfo();
			postInfo.setDeptCode(taskInfo.getDeptCode());
			postInfo.setType(Participants.TYPE_DEPT);
			postInfos.add(postInfo);
		}
		// 角色，可能有多个
		if(CollectionUtils.isNotEmpty(taskInfo.getRoleCodes())){
			List<String> roleCodes = taskInfo.getRoleCodes();
			for (String roleCode : roleCodes) {
				PostInfo postInfo = new PostInfo();
				postInfo.setRoleCode(roleCode);
				postInfo.setType(Participants.TYPE_ROLE);
				postInfos.add(postInfo);
			}
		}
		// 角色+机构
		if(CollectionUtils.isNotEmpty(taskInfo.getRoleCodes()) && StringUtils.isNotBlank(taskInfo.getOrgCode())){
			List<String> roleCodes = taskInfo.getRoleCodes();
            for (String roleCode : roleCodes) {
                PostInfo postInfo = new PostInfo();
                postInfo.setOrgCode(taskInfo.getOrgCode());
                postInfo.setRoleCode(roleCode);
                postInfo.setDeptCode("");
                postInfo.setType(Participants.TYPE_POST);
                postInfos.add(postInfo);
            }
		}
		// 角色+部门
		if(CollectionUtils.isNotEmpty(taskInfo.getRoleCodes()) && StringUtils.isNotBlank(taskInfo.getDeptCode())){
			List<String> roleCodes = taskInfo.getRoleCodes();
			for (String roleCode : roleCodes) {
				PostInfo postInfo = new PostInfo();
				postInfo.setDeptCode(taskInfo.getDeptCode());
				postInfo.setRoleCode(roleCode);
				postInfo.setOrgCode("");
				postInfo.setType(Participants.TYPE_POST);
				postInfos.add(postInfo);
			}
		}
		// 角色+机构+部门
		if(CollectionUtils.isNotEmpty(taskInfo.getRoleCodes()) && StringUtils.isNotBlank(taskInfo.getDeptCode()) && StringUtils.isNotEmpty(taskInfo.getOrgCode())){
			List<String> roleCodes = taskInfo.getRoleCodes();
			for (String roleCode : roleCodes) {
				PostInfo postInfo = new PostInfo();
				postInfo.setOrgCode(taskInfo.getOrgCode());
				postInfo.setDeptCode(taskInfo.getDeptCode());
				postInfo.setRoleCode(roleCode);
				postInfo.setType(Participants.TYPE_POST);
				postInfos.add(postInfo);
			}
		}
		// 加入其他客户自定义的postInfos
		if(CollectionUtils.isNotEmpty(taskInfo.getPostInfos())){
			postInfos.addAll(taskInfo.getPostInfos());
		}
		return postInfos;
	}


}
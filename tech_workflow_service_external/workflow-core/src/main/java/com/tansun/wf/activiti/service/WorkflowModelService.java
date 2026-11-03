package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.handler.QueryHandler;
import com.tansun.wf.activiti.handler.QueryResultHandler;
import com.tansun.wf.activiti.util.DateUtil;
import com.tansun.wf.activiti.util.ResultUtil;
import com.tansun.wf.camunda.bpmn.impl.instance.*;
import com.tansun.wf.camunda.bpmn.instance.*;
import com.tansun.wf.platform.mapper.ActProcessMapper;
import com.tansun.wf.platform.mapper.WfClassifyMapper;
import com.tansun.wf.platform.model.WfClassifyEntity;
import com.tansun.wf.platform.service.WfProcessCreateService;
import com.tansun.workflow.model.ModelInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.ByteArrayEntity;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ResourceEntity;
import org.camunda.bpm.engine.repository.Resource;
import org.camunda.bpm.engine.repository.*;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnDiagram;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnEdge;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnPlane;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnShape;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaIn;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaOut;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.camunda.bpm.model.bpmn.instance.dc.Bounds;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;
import org.camunda.bpm.model.xml.ModelInstance;
import org.camunda.bpm.model.xml.impl.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模型操作服务类
 *
 */
@Service("workflowModelService")
public class WorkflowModelService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;

	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	private static final String CALLED_ELEMENT_BINDING_VERSION = "version";
	private static final String START_NODE_EVENT = "StartNoneEvent";
	private static final String USER_TASK = "UserTask";
	private static final String END_NODE_EVENT = "EndNoneEvent";
	private static final String EXCLUSIVE_GATEWAY = "ExclusiveGateway";
	private static final String INCLUSIVE_GATEWAY="InclusiveGateway";
	private static final String PARALLEL_GATEWAY="ParallelGateway";
	private static final String SEQUENCE_FLOW = "SequenceFlow";
	private static final String SCRIPT_TASK="ScriptTask";
	private static final String SERVICE_TASK = "ServiceTask";
	private static final String RECEIVE_TASK = "ReceiveTask";
	private static final String SEND_TASK = "MailTask";
	private static final String CALL_ACTIVITY = "CallActivity";
	private static final String SEND_TASK_EMAIL_DELEGATE = "com.tansun.wf.decision.service.SendTaskEmailDelegate";
	private static final String BY_ASSIGNMENT = "byAssignment"; // 岗位模式
	private static final String COUNTERSIGN_LISTENER_CLASS = "com.tansun.wf.activiti.listener.HqFlowListener"; // 连线会签监听
	private static final String COUNTERSIGN_LISTENER_TYPE = "take"; // 连线会签监听

	/** 定时边界事件 */
	private static final String TIMER_BOUNDARY_EVENT = "TimerBoundaryEvent";
	private static final String TIMER_CATCH_EVENT = "TimerCatchEvent";
	private static final String TIMER_TYPE_DATE = "date";
	private static final String TIMER_TYPE_DURATION = "duration";
	private static final String TIMER_TYPE_CYCLE = "cycle";
	private  static final String TASK_ENVET_TYPE_TIME_OUT="timeout";

	@Autowired
	ProcessEngine processEngine;

	@Autowired
	private QueryHandler queryHandler;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private WorkflowClassifyService workflowClassifyService;

	@Autowired
	private WfClassifyMapper classifyMapper;
	@Autowired
	private ActProcessMapper actProcessMapper;

	@Autowired
	private ManagementService managementService;


	/**
	 * 获取模型列表
	 * @param processInfo
	 * @return
	 */
	public ProcessBean modelList(ProcessInfo processInfo) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
		if(StringUtils.isNotBlank(processInfo.getTenantId())){
			deploymentQuery.tenantIdIn(processInfo.getTenantId());
		}
		List<Deployment> deploymentList = deploymentQuery.list();
		Map<String,Date> deploymentMap = new HashMap<>();
		for (Deployment deployment : deploymentList) {
			deploymentMap.put(deployment.getId(),deployment.getDeploymentTime());
		}
		if(StringUtils.isNotBlank(processInfo.getTenantId())){
			processDefinitionQuery.tenantIdIn(processInfo.getTenantId());
		}
		if(StringUtils.isNotBlank(processInfo.getProcessDefineId())){
			processDefinitionQuery.processDefinitionId(processInfo.getProcessDefineId());
		}
		if(StringUtils.isNotBlank(processInfo.getModelKey())){
			processDefinitionQuery.processDefinitionKeyLike(processInfo.getModelKey());
		}
		if(StringUtils.isNotBlank(processInfo.getModelKeyLike())){
			processDefinitionQuery.processDefinitionKeyLike('%'+processInfo.getModelKeyLike()+'%');
		}
		if(StringUtils.isNotBlank(processInfo.getModelName())){
			processDefinitionQuery.processDefinitionNameLike("%" + processInfo.getModelName()+"%");
		}
		if(StringUtils.isNotBlank(processInfo.getClassifyId())){
		    // 使用categoryLike作为多分类查询的字段
			processDefinitionQuery.processDefinitionCategoryLike(processInfo.getClassifyId());
		}
		// 根据部署时间倒序
		if(StringUtils.isBlank(processInfo.getModelId())){
			processDefinitionQuery.latestVersion().orderByDeploymentTime().desc();
		}

		Map<String, Object> params = new HashMap<>();
		params.put("pageNum", "1");
		params.put("pageSize", Integer.MAX_VALUE +"");
		ProcessBean processBean = this.queryHandler.commonPageQuery(processInfo, processDefinitionQuery, new QueryResultHandler() {
			@Override
			public Map<String, Object> handlerRecord(Object record) {
				Map<String, Object> item = new LinkedHashMap<String, Object>();
				ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)record;
				item.put("id", processDefinition.getId());
				item.put("deploymentId", processDefinition.getDeploymentId());
				item.put("modelKey", processDefinition.getKey());
				item.put("modelName", processDefinition.getName());
				item.put("modelVersion", processDefinition.getVersion());
				item.put("createTime", DateUtil.formatDate(deploymentMap.get(processDefinition.getDeploymentId())));
				WfClassifyEntity classify = classifyMapper.findClassifyById(processDefinition.getCategory());
				if (classify!=null){
					item.put("categoryName",classify.getName());
				}else {
					item.put("categoryName",null);
				}

				item.put("category", processDefinition.getCategory());
				Map<String, Object> map = processDefinition.getProperties().toMap();
				String description = (String)map.get("documentation");
				item.put("description",description);
				item.put("tenantId",processDefinition.getTenantId());
				ProcessInfo processInfo=new ProcessInfo();
				processInfo.setProcessDefineId(processDefinition.getId());
				Integer isTemplate = actProcessMapper.findIsTemplate(processInfo);
				item.put("isTemplate",isTemplate);
				Boolean suspended = processDefinition.isSuspended();
				item.put("resourceName", processDefinition.getResourceName());
				item.put("modelVersion", processDefinition.getVersion());
				item.put("deploymentId", processDefinition.getDeploymentId());
				if (suspended == false) {
					item.put("suspended", "2");
				}else {
					item.put("suspended", "3");
				}

				return item;
			}
		});
		if(processBean != null && SUCCEED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询模型流程列表成功");
		}else if(processBean != null && FAILED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询模型流程列表失败");
		}
		return processBean;
	}

	/**
	 * json byte -> json -> xml -> xml bytes
	 * 根据模型部署流程
	 * @param processDefinitionId
	 * @return
	 */
	@Transactional
	public ProcessBean deployByDefinitionId(String processDefinitionId) throws Exception {
		ProcessBean processBean = new ProcessBean();
		if(StringUtils.isBlank(processDefinitionId)){
			ResultUtil.setReturnCode(processBean, FAILED, "模型id为空");
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String definitionId = processDefinition.getId();
		final String deleteId = definitionId;
		String key = processDefinition.getKey();
		String processName = processDefinition.getName();
		List<Resource> list = repositoryService.getDeploymentResources(definitionId);
		byte[] bytes = null;
		for(Resource r : list){
			if(r.getName().contains("source")){
				bytes = r.getBytes();
				String[] s = r.getName().split("_");
				// 修复流程创建页面发布按钮，名称变成了source
				if(s.length>1){
					processName=s[0];
				}
				break;
			}
		}
		if(bytes == null){
			ResultUtil.setReturnCode(processBean, FAILED, "部署失败，没有找到已保存的流程绘制图");
			return processBean;
		}

		String xmlJson = new String(bytes, StandardCharsets.UTF_8);
		JSONArray childShapes = JSONObject.parseObject(xmlJson).getJSONArray("childShapes");
		//判断是否存在排他网关
        for (Object childShape : childShapes) {
            JSONObject shape = (JSONObject) childShape;
            String type = shape.getJSONObject("stencil").getString("id");
            String id = shape.getString("id");
            int defaultCount = 0;
            if (id.contains("ExclusiveGateway")){
				Boolean hasDefault=false;
                //如果该节点是排他网关
                for (Object childShape1 : childShapes) {
                    JSONObject shape1 = (JSONObject) childShape1;
                    String gatewayType = shape1.getString("source");
                    if (id.equals(gatewayType)){
                        //属于该排他网关的连接线
                        String defaultStatus=shape1.getJSONObject("properties").getString("defaultflow");
                        if ("true".equals(defaultStatus)){
                            hasDefault=true;
                            defaultCount++;
                        }
                    }
                }
                //当前的结果
				//判断该排他网关是否有默认
				if (!hasDefault){
					ResultUtil.setReturnCode(processBean, FAILED, "排他网关必须要有默认流转线!");
					return processBean;
				}
				if(defaultCount>1){
                    ResultUtil.setReturnCode(processBean, FAILED, "排他网关不能有多个默认流转线!");
                    return processBean;
                }
            }

//            if (id=="ExclusiveGateway") {
//                //属于排他网关
//                //查询连接线的终点为排他网关的线
//                String gatewayType = shape.getString("source");
//                //判断是哪个网关
//                if (gatewayType.contains("ExclusiveGateway")){
//
//                }
//            }
        }
		String  tenantId =processDefinition.getTenantId();

		// 创建一个空的bpmn
		ProcessBuilder baseBuilder = Bpmn.createExecutableProcess(key).name(processName).documentation(processDefinition.getDescription());
		// 画开始节点
		BpmnModelInstance modelInstance = baseBuilder.done();

		this.createBpmnModel(modelInstance, xmlJson, childShapes, processDefinition);
		// 部署流程模型
		Deployment deploy = repositoryService.createDeployment().tenantId(tenantId).addString(key + ".bpmn", Bpmn.convertToString(modelInstance)).deploy();
      	// 流程模型source版本号调整
		final ByteArrayEntity byteArrayEntity = new ByteArrayEntity("source", bytes);
		byteArrayEntity.setDeploymentId(((DeploymentEntity) deploy).getDeployedProcessDefinitions().get(0).getId());
		byteArrayEntity.setTenantId(tenantId);
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor commandExecutor = processEngineConfiguration.getCommandExecutorTxRequired();
		commandExecutor.execute(commandContext -> {
			commandContext.getByteArrayManager().deleteByteArrayById(deleteId);
			commandContext.getByteArrayManager().insertByteArray(byteArrayEntity);
			return null;
		});
		ResultUtil.setReturnCode(processBean, SUCCEED, "部署成功，部署ID=" + deploy.getId());
		return processBean;
	}
	
	
	
	
	
	/**
	 * json byte -> json -> xml -> xml bytes
	 * 根据模型部署流程
	 * @param modelId
	 * @return
	 */
	@Transactional
	public ProcessBean deployeBymodel(String modelId,String  deployId) throws Exception {
		ProcessBean processBean = new ProcessBean();
		if(StringUtils.isBlank(modelId)){
			ResultUtil.setReturnCode(processBean, FAILED, "模型id为空");
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(modelId).singleResult();
		String definitionId = processDefinition.getId();
		final String deleteId = definitionId;
		String key = processDefinition.getKey();
		String processName = processDefinition.getName();
		List<Resource> list = repositoryService.getDeploymentResources(definitionId);
		byte[] bytes = null;
		for(Resource r : list){
			if(r.getName().contains("source")){
				bytes = r.getBytes();
				String[] s = r.getName().split("_");
				// 修复流程创建页面发布按钮，名称变成了source
				if(s.length>1){
					processName=s[0];
				}
				break;
			}
		}
		if(bytes == null){
			ResultUtil.setReturnCode(processBean, FAILED, "部署失败，没有找到已保存的流程绘制图");
			return processBean;
		}
		String xmlJson = new String(bytes, StandardCharsets.UTF_8);
		JSONArray childShapes = JSONObject.parseObject(xmlJson).getJSONArray("childShapes");
		String  tenantId =processDefinition.getTenantId();

		// 创建一个空的bpmn
		ProcessBuilder baseBuilder = Bpmn.createExecutableProcess(key).name(processName);
		// 画开始节点
		BpmnModelInstance modelInstance = baseBuilder.done();
		this.createBpmnModel(modelInstance, xmlJson, childShapes, processDefinition);
		// 部署流程模型
		DeploymentEntity deploy = (DeploymentEntity) repositoryService.createDeploymentQuery().deploymentId(deployId).singleResult();
      	// 流程模型source版本号调整
		final ByteArrayEntity byteArrayEntity = new ByteArrayEntity("source", bytes);
		byteArrayEntity.setDeploymentId(deployId);
		byteArrayEntity.setTenantId(tenantId);
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor commandExecutor = processEngineConfiguration.getCommandExecutorTxRequired();

		final ByteArrayEntity byteArrayBpm = new ByteArrayEntity(key+".bpmn", Bpmn.convertToString(modelInstance).getBytes("utf-8"));
		byteArrayBpm.setDeploymentId(deployId);
		byteArrayBpm.setTenantId(tenantId);
		commandExecutor.execute(commandContext -> {
			List<ResourceEntity> rList =commandContext.getResourceManager().findResourcesByDeploymentId(deployId);
			for (ResourceEntity resEntity:rList) {
				commandContext.getByteArrayManager().delete(resEntity);
			}			
			commandContext.getByteArrayManager().insertByteArray(byteArrayBpm);
			return null;
		});		
		commandExecutor.execute(commandContext -> {
			commandContext.getByteArrayManager().deleteByteArrayById(deleteId);
			commandContext.getByteArrayManager().insertByteArray(byteArrayEntity);
			commandContext.getProcessEngineConfiguration().getDeploymentCache().getBpmnModelInstanceCache().put(modelId, modelInstance);
			return null;
		});
		ResultUtil.setReturnCode(processBean, SUCCEED, "更新流程成功，部署ID=" + deploy.getId());
		return processBean;
	}
	
	private  void  createBpmnModel(BpmnModelInstance modelInstance,String  xmlJson,JSONArray childShapes,ProcessDefinition processDefinition) {
		Process process = modelInstance.getModelElementsByType(Process.class).iterator().next();
		Map<String, FlowNode> flowNodes = new HashMap<>();
		Map<String, SequenceFlow> sequenceFlows = new HashMap<>();
		// 循环创建各种节点
		for (Object childShape : childShapes) {
			JSONObject shape = (JSONObject) childShape;
			String type = shape.getJSONObject("stencil").getString("id");
			String id = shape.getString("id");
			String nodeName = shape.getJSONObject("properties").getString("name");
			switch (type){
				case START_NODE_EVENT:
					buildStartNodeEvent(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case USER_TASK:
					buildUserTask(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case PARALLEL_GATEWAY:
					buildParallelGateway(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case EXCLUSIVE_GATEWAY:
					buildExclusiveGateway(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case END_NODE_EVENT:
					buildEndNodeEvent(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case SCRIPT_TASK:
					buildScriptTask(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case SERVICE_TASK:
					buildServiceTask(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case RECEIVE_TASK:
					buildReceiveTask(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case SEND_TASK:
					buildSendTask(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case SEQUENCE_FLOW:
					buildSequenceFlow(modelInstance,id,nodeName,shape,process,sequenceFlows);
					break;
				case INCLUSIVE_GATEWAY:
					buildInclusiveGateway(modelInstance,id,nodeName,shape,process,flowNodes);
					break;
				case CALL_ACTIVITY:
					buildCallActivity(modelInstance,id,nodeName,shape,process, flowNodes);
					break;
				case TIMER_BOUNDARY_EVENT:// 边界事件
					buildTimerBoundaryEvent(modelInstance,id,nodeName,shape,process,flowNodes,sequenceFlows);
					break;
				case TIMER_CATCH_EVENT:// 边界事件
					buildTimerCatchEvent(modelInstance,id,nodeName,shape,process,flowNodes,sequenceFlows);
					break;
				default:
			}
			if(!SEQUENCE_FLOW.equals(type)){
				this.plainShape(modelInstance, shape, id);
			}
		}
		// 循环连接各个线
		for (Object childShape : childShapes) {
			JSONObject shape = (JSONObject) childShape;
			String type = shape.getJSONObject("stencil").getString("id");
			String id = shape.getString("id");
			JSONArray outgoings = shape.getJSONArray("outgoing");
			switch (type){
				case SEQUENCE_FLOW:
					JSONObject outgoing = (JSONObject)outgoings.iterator().next();
					String resourceId = outgoing.getString("resourceId");
					String source = shape.getString("source");
					SequenceFlow sequenceFlow = sequenceFlows.get(id);
					try{
						// 分支条件
						String expression = shape.getJSONObject("properties").getString("conditionsequenceflow");
						if(StringUtils.isNotBlank(expression)){
							ConditionExpression conditionExpression = modelInstance.newInstance(ConditionExpression.class);
							conditionExpression.setTextContent(expression);
							sequenceFlow.setConditionExpression(conditionExpression);
						}
					}catch (Exception e){
						logger.error("分支条件处理错误", e);
					}
					FlowNode sourceNode = flowNodes.get(source);
					FlowNode targetNode = flowNodes.get(resourceId);
					sequenceFlow.setSource(sourceNode);
					sequenceFlow.setTarget(targetNode);					
					process.addChildElement(sequenceFlow);
					targetNode.getIncoming().add(sequenceFlow);
					flowNodes.get(source).getOutgoing().add(sequenceFlow);
					if ("true".equals(shape.getJSONObject("properties").getString("defaultflow"))){
						String gatewayType = shape.getString("source");
						//判断是哪个网关
						if (gatewayType.contains(EXCLUSIVE_GATEWAY)){
							//排他网关
							ExclusiveGateway flowNode1 = (ExclusiveGateway)flowNodes.get(gatewayType);
							flowNode1.setDefault(sequenceFlow);
						}else if (gatewayType.contains(INCLUSIVE_GATEWAY)){
							//包含网关
							InclusiveGateway flowNode1 = (InclusiveGateway) flowNodes.get(gatewayType);
							flowNode1.setDefault(sequenceFlow);
						}
					}

					break;
				default:
			}
			if(SEQUENCE_FLOW.equals(type)){
				this.plainShape(modelInstance, shape, id);
			}
		}

		// 判断流程图箭头是否错误
		Iterator<Process> iterator = modelInstance.getModelElementsByType(Process.class).iterator();
		while (iterator.hasNext()){
			Iterator<FlowElement> flowElements = iterator.next().getFlowElements().iterator();
			while(flowElements.hasNext()){
				FlowElement flow = flowElements.next();
				if(flow instanceof StartEvent){
					// 开始节点不能有输入箭头，不能没有输出箭头
					StartEvent startEvent = (StartEvent) flow;
					if(CollectionUtils.isNotEmpty(startEvent.getIncoming())){
						throw new RuntimeException("开始节点箭头错误，发布失败");
					}
					if(CollectionUtils.isEmpty(startEvent.getOutgoing())){
						throw new RuntimeException("开始节点箭头错误，发布失败");
					}
				}else if(flow instanceof EndEvent){
					// 结束节点不能有输出箭头，不能没有输入箭头
					EndEvent endEvent = (EndEvent) flow;
					if(CollectionUtils.isNotEmpty(endEvent.getOutgoing())){
						throw new RuntimeException("结束节点箭头错误，发布失败");
					}
					if(CollectionUtils.isEmpty(endEvent.getIncoming())){
						throw new RuntimeException("结束节点箭头错误，发布失败");
					}
				}else if(flow instanceof Activity){
					// 活动节点需要输入和输出箭头
					Activity activity = (Activity) flow;
					if(CollectionUtils.isEmpty(activity.getOutgoing())){
						throw new RuntimeException("存在节点箭头错误，发布失败");
					}
					if(CollectionUtils.isEmpty(activity.getIncoming())){
						throw new RuntimeException("存在节点箭头错误，发布失败");
					}
				}else if(flow instanceof Gateway){
					// 网关需要输入和输出箭头
					Gateway gateway = (Gateway) flow;
					if(CollectionUtils.isEmpty(gateway.getOutgoing())){
						throw new RuntimeException("网关节点箭头错误，发布失败");
					}
					if(CollectionUtils.isEmpty(gateway.getIncoming())){
						throw new RuntimeException("网关节点箭头错误，发布失败");
					}
				}
			}
		}
		Definitions definitions = modelInstance.getDefinitions();
		definitions.setTargetNamespace(processDefinition.getCategory());
	}


	/**
	 * 定时器中间捕捉事件
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 * @param sequenceFlows
	 */
	private void buildTimerCatchEvent(BpmnModelInstance modelInstance, String id, String nodeName, JSONObject shape, Process process, Map<String, FlowNode> flowNodes, Map<String, SequenceFlow> sequenceFlows) {
		IntermediateCatchEvent intermediateCatchEvent = modelInstance.newInstance(IntermediateCatchEvent.class);
		TimerEventDefinition timerEventDefinition = modelInstance.newInstance(TimerEventDefinition.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,intermediateCatchEvent,id,nodeName,documentation);
		try {
			String timerType = properties.getString("timerType");
			String timerValue = properties.getString("timerValue");
			switch (timerType){
				case TIMER_TYPE_DATE:
					// 时间
					TimeDate timeDate = modelInstance.newInstance(TimeDate.class);
					timeDate.setTextContent(timerValue);
					timerEventDefinition.setTimeDate(timeDate);
					break;
				case TIMER_TYPE_DURATION:
					// 时长
					TimeDuration timeDuration = modelInstance.newInstance(TimeDuration.class);
					timeDuration.setTextContent(timerValue);
					timerEventDefinition.setTimeDuration(timeDuration);
					break;
				case TIMER_TYPE_CYCLE:
					// 时间周期
					TimeCycle timeCycle = modelInstance.newInstance(TimeCycle.class);
					timeCycle.setTextContent(timerValue);
					timerEventDefinition.setTimeCycle(timeCycle);
					break;
				default:
			}
		}catch (Exception e){}
		intermediateCatchEvent.addChildElement(timerEventDefinition);
		process.addChildElement(intermediateCatchEvent);
		flowNodes.put(id,intermediateCatchEvent);
	}

	/**
	 * 超时边界时间
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 * @param sequenceFlows
	 */
	private void buildTimerBoundaryEvent(BpmnModelInstance modelInstance, String id, String nodeName, JSONObject shape, Process process, Map<String, FlowNode> flowNodes,Map<String, SequenceFlow> sequenceFlows) {
		BoundaryEvent boundaryEvent = modelInstance.newInstance(BoundaryEvent.class);
		TimerEventDefinition timerEventDefinition = modelInstance.newInstance(TimerEventDefinition.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,boundaryEvent,id,nodeName,documentation);
		JSONArray outgoings = shape.getJSONArray("outgoing");
		try {
			String timerType = properties.getString("timerType");
			String timerValue = properties.getString("timerValue");
			switch (timerType){
				case TIMER_TYPE_DATE:
					// 时间
					TimeDate timeDate = modelInstance.newInstance(TimeDate.class);
					timeDate.setTextContent(timerValue);
					timerEventDefinition.setTimeDate(timeDate);
					break;
				case TIMER_TYPE_DURATION:
					// 时长
					TimeDuration timeDuration = modelInstance.newInstance(TimeDuration.class);
					timeDuration.setTextContent(timerValue);
					timerEventDefinition.setTimeDuration(timeDuration);
					break;
				case TIMER_TYPE_CYCLE:
					// 时间周期
					TimeCycle timeCycle = modelInstance.newInstance(TimeCycle.class);
					timeCycle.setTextContent(timerValue);
					timerEventDefinition.setTimeCycle(timeCycle);
					break;
				default:
			}
		}catch (Exception e){}
		boundaryEvent.addChildElement(timerEventDefinition);
		boundaryEvent.setCancelActivity(false);

		JSONObject outgoing = (JSONObject)outgoings.iterator().next();
		String resourceId = outgoing.getString("resourceId");
		// 边界输出箭头
//		boundaryEvent.getOutgoing().add(sequenceFlows.get(resourceId));
		flowNodes.put(id, boundaryEvent);
		process.addChildElement(boundaryEvent);
//		 附加到任务上
//		boundaryEvent.setAttachedTo((Activity) flowNodes.get(source.getId()));
	}


	/**
	 * 创建模型
	 * @return
	 */
	public ProcessBean createModel(String key, String name, String description,String tenantId) {
		ProcessBean processBean = new ProcessBean();
		if(StringUtils.isBlank(name) || StringUtils.isBlank(key)){
			ResultUtil.setReturnCode(processBean, FAILED, "键或名称不能为空");
			return processBean;
		}
		try {
			// camunda transform begin
			BpmnModelInstance emptyModel = Bpmn.createExecutableProcess(key).name(name).done();
			Definitions definitions = emptyModel.getDefinitions();
			// 分类
			Deployment deploy = repositoryService.createDeployment()
                .tenantId(tenantId)
					.addModelInstance(key+".bpmn", emptyModel).deploy();
			// camunda transform end
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ResultUtil.setReturnCode(processBean, FAILED, "创建模型失败");
		}
		return processBean;
	}

	/**
	 * 删除模型
	 * @return
	 */
	public ProcessBean deleteModel(String key,String tenantId) {
		ProcessBean processBean = new ProcessBean();
		try {
			List<ProcessDefinition> prcoessDefList = repositoryService.createProcessDefinitionQuery().tenantIdIn(tenantId).processDefinitionKey(key).list();
			for (int i = 0; i < prcoessDefList.size(); i++) {
				String deploymentId = prcoessDefList.get(i).getDeploymentId();
				repositoryService.deleteDeployment(deploymentId, true);
			}
			// camunda-TODO
//			repositoryService.deleteModel(modelId);
			ResultUtil.setReturnCode(processBean, SUCCEED, "删除模型成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ResultUtil.setReturnCode(processBean, FAILED, "删除模型失败");
		}
		return processBean;
	}
	/**
	 * model文件导出,导出类型有json,bpmn格式
	 * processBean={fileName,bytes}
	 */
	public ProcessBean exportModel(String modelId,String type){
		ProcessBean processBean = new ProcessBean();
		if(StringUtils.isBlank(modelId)){
			ResultUtil.setReturnCode(processBean, FAILED, "模型id为空，模型导出失败");
			return processBean;
		}
		if(!"bpmn".equals(type)){
			ResultUtil.setReturnCode(processBean, FAILED, "未识别的导出类型，模型导出失败");
			return processBean;
		}
		String content = IoUtil.convertXmlDocumentToString(repositoryService.getBpmnModelInstance(modelId).getDocument());
		// 处理异常
		if (StringUtils.isBlank(content)) {
			ResultUtil.setReturnCode(processBean, FAILED, "模型不存在");
			return processBean;
		}
		String fileName = repositoryService.getProcessDefinition(modelId).getKey();
		byte[] exportBytes = null;
		if (type.equals("bpmn")) {
			exportBytes = content.getBytes();
			fileName += ".bpmn20.xml";
		}
		ResultUtil.setReturnCode(processBean, SUCCEED, "导出成功");
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("fileName", fileName);
		map.put("bytes", exportBytes);
		processBean.setResultObject(map);
		return processBean;
	}

	// model 指的是 流程定义
	// camunda改造: modelId -> deploymentId, 根据deploymentId查找旧流程定义,使用新classify,key,name,description,tanantId创建新的流程定义
	// camunda没有找到记录描述description的字段
	public ProcessBean copyModel(ModelInfo modelInfo) throws Exception {
		String classify = modelInfo.getClassify();
		String modelId = modelInfo.getModelId();
		String key = modelInfo.getKey();
		String name = modelInfo.getName();
		String description = modelInfo.getDescription();
		String tenantId = modelInfo.getTenantId();

		ProcessBean processBean = new ProcessBean();
		if (StringUtils.isBlank(classify)) {
			ResultUtil.setReturnCode(processBean, FAILED, "流程分类不能为空");
			return processBean;
		}
		if (StringUtils.isBlank(name) || StringUtils.isBlank(key)) {
			ResultUtil.setReturnCode(processBean, FAILED, "键或名称不能为空");
			return processBean;
		}
		// camunda transform begin
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().tenantIdIn(tenantId).processDefinitionKey(key).list();
		if (list.size() != 0) {
			ResultUtil.setReturnCode(processBean, FAILED, "流程键值已存在");
			return processBean;
		}
		String content = IoUtil.convertXmlDocumentToString(repositoryService.getBpmnModelInstance(modelId).getDocument());
		try {
			Pattern pattern = Pattern.compile("targetNamespace=\"([^\"]*)\"");
			Matcher matcher = pattern.matcher(content);
			if(!matcher.find()){
				ResultUtil.setReturnCode(processBean, FAILED, "原流程定义格式错误,<definitions>必须有targetNamespace属性");
				return processBean;
			}
			String temp = matcher.group(1);
			content = content.replaceAll(temp, classify);
			pattern = Pattern.compile("<process[^>]*\\s+id=\"([^\"]*)\"");
			matcher = pattern.matcher(content);
			if(!matcher.find()){
				ResultUtil.setReturnCode(processBean, FAILED, "原流程定义格式错误,<process>必须有id属性");
				return processBean;
			}
			temp = matcher.group(1);
			content = content.replaceAll(temp, key);
		}catch (IndexOutOfBoundsException e){
			ResultUtil.setReturnCode(processBean, FAILED, "原流程定义格式错误,<definitions>必须有targetNamespace属性，<process>必须有id属性");
			return processBean;
		}
		if (StringUtils.isBlank(content)) {
			ResultUtil.setReturnCode(processBean, FAILED, "模型不存在");
			return processBean;
		}
		repositoryService.createDeployment().tenantId(tenantId).name(name).source("copyModel")
				.addInputStream(key + ".bpmn", org.camunda.commons.utils.IoUtil.stringAsInputStream(content)).deploy();
		ResultUtil.setReturnCode(processBean, SUCCEED, "复制成功");
		return processBean;
	}

	/**
	 * 构建开始节点
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildStartNodeEvent(ModelInstance modelInstance, String id, String nodeName, JSONObject shape, Process process, Map<String,FlowNode> flowNodes){
		StartEvent startEvent = modelInstance.newInstance(StartEvent.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,startEvent,id,nodeName,documentation);
		process.addChildElement(startEvent);
		flowNodes.put(id,startEvent);
	}

    /**
               * 构建用户节点
     * @param modelInstance
     * @param id
     * @param nodeName
     * @param shape
     * @param process
     * @param flowNodes
     */
    public void buildUserTask(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
        UserTask userTask = modelInstance.newInstance(UserTask.class);
        JSONObject properties = shape.getJSONObject("properties");
        String documentation = properties.getString("documentation");
        // 1.基本信息
        // id、名称、描述
        buildBaseProperties(modelInstance,userTask,id,nodeName,documentation);

		// 扩展元素
		ExtensionElements extensionElements = modelInstance.newInstance(ExtensionElements.class);

		// 2.业务手段 表单
		Form form = modelInstance.newInstance(Form.class);
		form.setBusimeans(properties.getString("busimeans"));
		if (properties.getJSONObject("formselect") !=null) {
			form.setFormUrl(properties.getJSONObject("formselect").getString("formUrl"));
		}
		
		extensionElements.addChildElement(form);

		// 3.参与者
		UserTaskParticipant userTaskParticipant = modelInstance.newInstance(UserTaskParticipant.class);
		userTaskParticipant.setParRadioConfig(properties.getString("parRadioConfig"));
		userTaskParticipant.setAllowPreAct(properties.getBooleanValue("allowPreAct"));
		userTaskParticipant.setExecutor(properties.getString("executor"));
		userTaskParticipant.setParticRule(properties.getString("particRule"));
		userTaskParticipant.setAboutData(properties.getString("aboutData"));

		// 参与者列表
		String parRadioConfig = userTaskParticipant.getParRadioConfig();
		if(StringUtils.isBlank(parRadioConfig)){
			if(StringUtils.isNotBlank(nodeName)){
				throw new RuntimeException("请配置人工节点的参与者，节点名称：" + nodeName);
			}else{
				throw new RuntimeException("请配置人工节点的参与者，节点id：" + id);
			}
		}
		if(UserTaskParticipant.CONFIG_TYPE_1.equals(userTaskParticipant.getParRadioConfig())){
			JSONArray participants = properties.getJSONArray("participants");
			if(CollectionUtils.isNotEmpty(participants)){
				Iterator<Object> iterator = participants.iterator();
				while(iterator.hasNext()){
					JSONObject next = (JSONObject)iterator.next();
					Participants participant = modelInstance.newInstance(Participants.class);
					participant.setId(next.getString("id"));
					participant.setName(next.getString("name"));
					participant.setType(next.getString("type"));
					participant.setModel(next.getString("model"));
					userTaskParticipant.addChildElement(participant);
				}
			}else{
				if(StringUtils.isNotBlank(nodeName)){
					throw new RuntimeException("请配置人工节点的参与者，节点名称：" + nodeName);
				}else{
					throw new RuntimeException("请配置人工节点的参与者，节点id：" + id);
				}
			}
		}

		// 逻辑规则
		if(UserTaskParticipant.CONFIG_TYPE_6.equals(userTaskParticipant.getParRadioConfig())){
			JSONObject ruleLogic = properties.getJSONObject("ruleLogic");
			userTaskParticipant.setServiceType(ruleLogic.getString("serviceType"));
			userTaskParticipant.setServiceValue(ruleLogic.getString("serviceValue"));
			JSONArray configList = ruleLogic.getJSONArray("configList");
			Iterator<Object> iterator = configList.iterator();
			while(iterator.hasNext()){
				JSONObject next = (JSONObject)iterator.next();
				RuleLogicParam ruleLogicParam = modelInstance.newInstance(RuleLogicParam.class);
				ruleLogicParam.setName(next.getString("name"));
				ruleLogicParam.setDataType(next.getString("dataType"));
				ruleLogicParam.setValue(next.getString("value"));
				ruleLogicParam.setValueType(next.getString("valueType"));
				userTaskParticipant.addChildElement(ruleLogicParam);
			}
		}
		extensionElements.addChildElement(userTaskParticipant);

		// 超时设置
		JSONArray   jsonArray =  properties.getJSONArray("overTimes");
		if (jsonArray != null) {
			for (int i =0;i<jsonArray.size();i++) {
				JSONObject  jsonObject = (JSONObject) jsonArray.get(i);
				CamundaTaskListener taskLister =modelInstance.newInstance(CamundaTaskListener.class);
				String  express =jsonObject.getString("express");
				String  lisId  = jsonObject.getString("id");
				taskLister.setCamundaEvent(TASK_ENVET_TYPE_TIME_OUT);
				taskLister.setCamundaDelegateExpression(express);
				taskLister.setAttributeValue("id", lisId);
				extensionElements.addChildElement(taskLister);
				TimerEventDefinition timerEventDefinition = modelInstance.newInstance(TimerEventDefinition.class);
				taskLister.addChildElement(timerEventDefinition);
				String timerType = jsonObject.getString("timerType");
				String timerValue = jsonObject.getString("timerValue");
				switch (timerType){
					case TIMER_TYPE_DATE:
						// 时间
						TimeDate timeDate = modelInstance.newInstance(TimeDate.class);
						timeDate.setTextContent(timerValue);
						timerEventDefinition.setTimeDate(timeDate);
						break;
					case TIMER_TYPE_DURATION:
						// 时长
						TimeDuration timeDuration = modelInstance.newInstance(TimeDuration.class);
						timeDuration.setTextContent(timerValue);
						timerEventDefinition.setTimeDuration(timeDuration);
						break;
					case TIMER_TYPE_CYCLE:
						// 时间周期
						TimeCycle timeCycle = modelInstance.newInstance(TimeCycle.class);
						timeCycle.setTextContent(timerValue);
						timerEventDefinition.setTimeCycle(timeCycle);
						break;
					default:
				}
				
			}
		}
		
		//多工作项
		JSONObject mulWork =properties.getJSONObject("multiWork");
		if (mulWork != null) {
			
		    boolean  isMul =	mulWork.getBooleanValue("enableMultiWork");
			if (isMul) {
				MultiWork work =modelInstance.newInstance(MultiWork.class);
				work.setAutoStop(mulWork.getString("autoStop"));
				work.setCompleteNum(mulWork.getString("completeNum"));
				work.setCompletePercent(mulWork.getString("completePercent"));
				work.setDisStrategy(mulWork.getString("disStrategy"));
				work.setEnableMultiWork(mulWork.getBooleanValue("enableMultiWork"));
				work.setFinishRule(mulWork.getString("finishRule"));
				work.setIsInOrder(mulWork.getString("isInOrder"));
				work.setIterateElement(mulWork.getString("iterateElement"));
				work.setIterateVariable(mulWork.getString("iterateVariable"));
				extensionElements.addChildElement(work);
				MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = modelInstance.newInstance(MultiInstanceLoopCharacteristics.class);

				if (work.getIsInOrder() !=null && work.getIsInOrder().equals("0")) {
					multiInstanceLoopCharacteristics.setSequential(false);
				}else {
					multiInstanceLoopCharacteristics.setSequential(true);
				}

				if (work.getDisStrategy() != null) {
					if (work.getDisStrategy().equals("1") || work.getDisStrategy().equals("2")) {
						multiInstanceLoopCharacteristics.setCamundaCollection("postInfos");
						multiInstanceLoopCharacteristics.setCamundaElementVariable("postInfo");

					}else {
						multiInstanceLoopCharacteristics.setCamundaCollection(mulWork.getString("iterateElement"));
						multiInstanceLoopCharacteristics.setCamundaElementVariable(mulWork.getString("iterateVariable"));
					}
				}
				if (work.getFinishRule() != null) {
					String  compStr = null;
					if (work.getFinishRule().equals("2")) {
						compStr="${nrOfCompletedInstances =="+work.getCompleteNum()+"}";
					}else if ("3".equals(work.getFinishRule())) {
						String compPercent =work.getCompletePercent();
						if (compPercent !=null) {
							double  d = Double.valueOf(compPercent)/100;
							compStr="${nrOfCompletedInstances/nrOfInstances >="+d+"}";
						}
					}else{
						compStr = "${nrOfCompletedInstances==nrOfInstances}";
					}
					if (StringUtils.isNotBlank(compStr)) {
						CompletionCondition  completionCondition =  modelInstance.newInstance(CompletionCondition.class);
						completionCondition.setTextContent(compStr);
						multiInstanceLoopCharacteristics.addChildElement(completionCondition);
					}
				}
				userTask.addChildElement(multiInstanceLoopCharacteristics);

			}
			
		}
        //事件 
		JSONArray  eventArray = properties.getJSONArray("triggerEvents");
		if (eventArray != null) {
			for (int j=0;j< eventArray.size();j++) {
				JSONObject  eventObject = (JSONObject) eventArray.get(j);
				String  serviceValue = eventObject.getString("serviceValue");
				String  eventAction =  eventObject.getString("eventAction");
				String  triggerTime =  eventObject.getString("triggerTime");
				ExtendEvent  extendEvent =	modelInstance.newInstance(ExtendEvent.class);
				extendEvent.setServiceName(serviceValue);
				extendEvent.setEvent(DefineEvent.getEventType(triggerTime));
				extendEvent.setCallType(eventAction);
				extensionElements.addChildElement(extendEvent);
			}
		}
		// 回退类型
		String  backType = properties.getString("backtype");
		if (StringUtils.isNotBlank(backType)) {
			Backtype backInstance =modelInstance.newInstance(Backtype.class);
			backInstance.setTextContent(backType);
			extensionElements.addChildElement(backInstance);
		}


		// 按钮权限
        JSONObject usertaskfunction=  properties.getJSONObject("usertaskfunction");
        if (usertaskfunction != null) {
            Usertaskfunction functionInstance =modelInstance.newInstance(Usertaskfunction.class);
            functionInstance.setTextContent(usertaskfunction.toJSONString());
            extensionElements.addChildElement(functionInstance);
        }

        //
        userTask.setExtensionElements(extensionElements);

        process.addChildElement(userTask);
        flowNodes.put(id,userTask);
    }

	/**
	 * 构建排他网关
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildExclusiveGateway(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes
	){
		ExclusiveGateway exclusiveGateway= modelInstance.newInstance(ExclusiveGateway.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		//添加进子元素
		buildBaseProperties(modelInstance,exclusiveGateway,id,nodeName,documentation);
		process.addChildElement(exclusiveGateway);
		flowNodes.put(id,exclusiveGateway);

	}

	/**
	 * id,name,描述
	 * @param modelInstance
	 * @param description
	 * @return
	 */
	private void buildBaseProperties(ModelInstance modelInstance,FlowNode flowNode,String id,String name,String description) {
		flowNode.setId(id);
		flowNode.setName(name);
		if(StringUtils.isNotBlank(description)){
			Documentation documentation = modelInstance.newInstance(Documentation.class);
			documentation.setTextContent(description);
			flowNode.addChildElement(documentation);
		}
	}


	public void buildInclusiveGateway(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		InclusiveGateway inclusiveGateway = modelInstance.newInstance(InclusiveGateway.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,inclusiveGateway,id,nodeName,documentation);
		process.addChildElement(inclusiveGateway);
		flowNodes.put(id,inclusiveGateway);
	}
	/**
	 * 构建并行网关
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildParallelGateway(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		ParallelGateway parallelGateway = modelInstance.newInstance(ParallelGateway.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,parallelGateway,id,nodeName,documentation);
		process.addChildElement(parallelGateway);
		flowNodes.put(id,parallelGateway);
	}


	/**
	 * 构建脚本网关
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildScriptTask(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		ScriptTask scriptTask = modelInstance.newInstance(ScriptTask.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		//获取脚本
		String scriptformat = properties.getString("scriptformat");
		scriptTask.setScriptFormat(scriptformat);
		String scripttext = properties.getString("scripttext");
		if(StringUtils.isNotBlank(scripttext)){
			Script script = modelInstance.newInstance(Script.class);
			script.setTextContent(scripttext);
			scriptTask.setScript(script);
		}
		String scriptresource = properties.getString("scriptresource");
		if(StringUtils.isNotBlank(scriptresource)){
			scriptTask.setCamundaResource(scriptresource);
		}
		buildBaseProperties(modelInstance,scriptTask,id,nodeName,documentation);
		process.addChildElement(scriptTask);
		flowNodes.put(id,scriptTask);
	}


	/**
	 * 构建结束节点
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildEndNodeEvent(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		EndEvent endEvent = modelInstance.newInstance(EndEvent.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,endEvent,id,nodeName,documentation);
		process.addChildElement(endEvent);
		flowNodes.put(id,endEvent);
	}

	/**
	 * 构建服务节点
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildServiceTask(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		ServiceTask serviceTask = modelInstance.newInstance(ServiceTask.class);
		JSONObject properties = shape.getJSONObject("properties");
		String serviceTaskDelegateExpression = properties.getString("servicetaskdelegateexpression");
		String serviceTaskExpression = properties.getString("servicetaskexpression");
		Boolean asynchronousdefinition = properties.getBoolean("asynchronousdefinition");
		// 远程接口
		ExtensionElements extensionElements = modelInstance.newInstance(ExtensionElements.class);
		String longinterface = properties.getString("longinterface");
		LongInterface longInterface = modelInstance.newInstance(LongInterface.class);
		longInterface.setTextContent(longinterface);
		extensionElements.addChildElement(longInterface);

		serviceTask.addChildElement(extensionElements);
		//表达式
		if (StringUtils.isNotBlank(serviceTaskExpression)) {
			serviceTask.setCamundaExpression(serviceTaskExpression);
		}
		//是否异步
		serviceTask.setCamundaAsyncBefore(asynchronousdefinition);
		//委托表达式
		if (StringUtils.isNotBlank(serviceTaskDelegateExpression)) {
			serviceTask.setCamundaDelegateExpression(serviceTaskDelegateExpression);
		}else{
			if(StringUtils.isNotBlank(nodeName)){
				nodeName=" '"+nodeName+"' ";
			}
			throw new RuntimeException("服务节点"+nodeName+"委托表达式不能为空");
		}
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,serviceTask,id,nodeName,documentation);
		process.addChildElement(serviceTask);
		flowNodes.put(id,serviceTask);
	}

	/**
	 * 构建接收任务
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildReceiveTask(BpmnModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		ReceiveTask receiveTask = modelInstance.newInstance(ReceiveTask.class);
		String msg = shape.getString("waitState");
		if(StringUtils.isNotBlank(msg)){
			receiveTask.setMessage(this.findMessageForName(msg, modelInstance));
		}
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,receiveTask,id,nodeName,documentation);
		process.addChildElement(receiveTask);
		flowNodes.put(id,receiveTask);
	}

	/**
	 * 构建发送任务
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param flowNodes
	 */
	public void buildSendTask(BpmnModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		SendTask sendTask = modelInstance.newInstance(SendTask.class);
		JSONObject properties = shape.getJSONObject("properties");
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,sendTask,id,nodeName,documentation);
		String emailAddress = properties.getString("emailAddress");
		String emailContent = properties.getString("emailContent");
		if(StringUtils.isBlank(emailAddress) || StringUtils.isBlank(emailContent)){
			throw new RuntimeException("发送节点"+nodeName+"邮件发送地址和邮件内容不可为空");
		}
		SendTaskEmailAddress sendTaskEmailAddress = modelInstance.newInstance(SendTaskEmailAddress.class);
		sendTaskEmailAddress.setTextContent(emailAddress);
		SendTaskEmailContext sendTaskEmailContext = modelInstance.newInstance(SendTaskEmailContext.class);
		sendTaskEmailContext.setTextContent(emailContent);
		ExtensionElements extensionElements = modelInstance.newInstance(ExtensionElements.class);
		extensionElements.addChildElement(sendTaskEmailAddress);
		extensionElements.addChildElement(sendTaskEmailContext);
		sendTask.setExtensionElements(extensionElements);
		sendTask.setCamundaClass(SEND_TASK_EMAIL_DELEGATE);
		process.addChildElement(sendTask);
		flowNodes.put(id,sendTask);
	}

	private Message findMessageForName(String messageName,BpmnModelInstance modelInstance) {
		Collection<Message> messages = modelInstance.getModelElementsByType(Message.class);
		for (Message message : messages) {
			if (messageName.equals(message.getName())) {
				return message;
			}
		}
		BpmnModelInstance  pmnModel =  modelInstance;
		Definitions definitions = pmnModel.getDefinitions();
		Message message = modelInstance.newInstance(Message.class);
		message.setName(messageName);
		definitions.addChildElement(message);
		return message;
	}

	/**
	 * 构建箭头
	 * @param modelInstance
	 * @param id
	 * @param nodeName
	 * @param shape
	 * @param process
	 * @param sequenceFlows
	 */
	public void buildSequenceFlow(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,SequenceFlow> sequenceFlows){
		SequenceFlow sequenceFlow = modelInstance.newInstance(SequenceFlow.class);
		sequenceFlow.setId(id);
		sequenceFlow.setName(nodeName);
		// 分支条件
		try {
			String conditionSequenceFlow = shape.getJSONObject("properties").getString("conditionsequenceflow");
			if(StringUtils.isNotBlank(conditionSequenceFlow)){
				ConditionExpression conditionExpression = modelInstance.newInstance(ConditionExpression.class);
				conditionExpression.setTextContent(conditionSequenceFlow);
				sequenceFlow.setConditionExpression(conditionExpression);
			}

			ExtensionElements extensionElements = modelInstance.newInstance(ExtensionElements.class);
			// 箭头优先级
			Priority priority = modelInstance.newInstance(Priority.class);
			Integer priorityNum = shape.getJSONObject("properties").getInteger("priority");
			if(priorityNum!=null){
				priority.setOrder(priorityNum);
			}else{
				priority.setOrder(0);
			}
			extensionElements.addChildElement(priority);
			sequenceFlow.addChildElement(extensionElements);
		}catch (Exception e){}
		sequenceFlows.put(id,sequenceFlow);
	}

	private void buildCallActivity(ModelInstance modelInstance,String id,String nodeName,JSONObject shape,Process process,Map<String,FlowNode> flowNodes){
		CallActivity callActivity = modelInstance.newInstance(CallActivity.class);
		JSONObject properties = shape.getJSONObject("properties");
		String calledElement = properties.getString("callactivitycalledelement");
		callActivity.setCalledElement(calledElement);
		String calledElementBinding = properties.getString("calledElementBinding");
		if(!StringUtils.isBlank(calledElementBinding) && calledElementBinding.equals(CALLED_ELEMENT_BINDING_VERSION)){
			String calledElementVersion = properties.getString("calledElementVersion");
			if (calledElementVersion != null) {
				callActivity.setCamundaCalledElementBinding(CALLED_ELEMENT_BINDING_VERSION);
				callActivity.setCamundaCalledElementVersion(calledElementVersion);
			}
		}
		ExtensionElements extensionElements = modelInstance.newInstance(ExtensionElements.class);
		if(properties.getJSONObject("callactivityoutparameters")!=null){
			JSONArray outParam = properties.getJSONObject("callactivityoutparameters").getJSONArray("outParameters");
			outParam.forEach(out -> {
				CamundaOut camundaOut = modelInstance.newInstance(CamundaOut.class);
				JSONObject outJson = (JSONObject) out;
				camundaOut.setCamundaSource(outJson.getString("source"));
				camundaOut.setCamundaTarget(outJson.getString("target"));
				camundaOut.setCamundaSourceExpression(outJson.getString("sourceExpression"));
				extensionElements.addChildElement(camundaOut);
			});
		}

		if(properties.getJSONObject("callactivityinparameters")!=null){
			JSONArray inParam = properties.getJSONObject("callactivityinparameters").getJSONArray("inParameters");

			inParam.forEach(in -> {
				CamundaIn camundaIn = modelInstance.newInstance(CamundaIn.class);
				JSONObject inJson = (JSONObject) in;
				camundaIn.setCamundaSource(inJson.getString("source"));
				camundaIn.setCamundaTarget(inJson.getString("target"));
				camundaIn.setCamundaSourceExpression(inJson.getString("sourceExpression"));
				extensionElements.addChildElement(camundaIn);
			});
		}

		callActivity.setExtensionElements(extensionElements);
		String documentation = properties.getString("documentation");
		buildBaseProperties(modelInstance,callActivity,id,nodeName,documentation);
		process.addChildElement(callActivity);
		flowNodes.put(id, callActivity);
	}

	private  void  plainShape(BpmnModelInstance modelInstance,JSONObject shape,String id) {
		Collection<BpmnDiagram> bmpList = modelInstance.getDefinitions().getBpmDiagrams();
		if (bmpList != null &&  bmpList.size() >0) {
			BpmnDiagram  bpmnDiagram =  bmpList.iterator().next();
			BpmnPlane bpmnPlane =bpmnDiagram.getBpmnPlane();
			String type = shape.getJSONObject("stencil").getString("id");
			BaseElement bpmElement = modelInstance.getModelElementById(id);
			if (type.equals(SEQUENCE_FLOW)) {
				BpmnEdge bpmnEdge = modelInstance.newInstance(BpmnEdge.class);
				bpmnPlane.addChildElement(bpmnEdge);
				Integer sourceAnchor = null;
				Integer targetAnchor = null;
				sourceAnchor = shape.getInteger("sourceAnchor");
				targetAnchor = shape.getInteger("targetAnchor");
				bpmnEdge.setBpmnElement(bpmElement);
				Waypoint waypoint1 = modelInstance.newInstance(Waypoint.class);
				if(sourceAnchor!=null){
					waypoint1.setX(sourceAnchor);
				}else{
					waypoint1.setX(-1);
				}
				if (targetAnchor!=null){
					waypoint1.setY(targetAnchor);
				}else{
					waypoint1.setY(-1);
				}
				bpmnEdge.addChildElement(waypoint1);
				Waypoint waypoint2 = modelInstance.newInstance(Waypoint.class);
				if(sourceAnchor!=null){
					waypoint2.setX(sourceAnchor);
				}else{
					waypoint2.setX(-1);
				}
				if (targetAnchor!=null){
					waypoint2.setY(targetAnchor);
				}else{
					waypoint2.setY(-1);
				}
				bpmnEdge.addChildElement(waypoint1);
				bpmnEdge.addChildElement(waypoint2);
			}else {
				BpmnShape bpmnShape = modelInstance.newInstance(BpmnShape.class);
				bpmnPlane.addChildElement(bpmnShape);
				JSONObject  shapeObject =shape.getJSONObject("bounds");
				double  leftX = 0;
				double  leftY =0;
				double  rightX =0;
				double  rightY =0;
				if (shapeObject != null) {
					leftX= shapeObject.getJSONObject("upperLeft").getDoubleValue("x");
					leftY =   shapeObject.getJSONObject("upperLeft").getDoubleValue("y");
					rightX=  shapeObject.getJSONObject("lowerRight").getDoubleValue("x");
					rightY= shapeObject.getJSONObject("lowerRight").getDoubleValue("y");
					double  x =(rightX-leftX)/2+leftX;
					double  y = (rightY-leftY)/2+leftY;
					bpmnShape.setBpmnElement(bpmElement);
					Bounds bounds = modelInstance.newInstance(Bounds.class);
					bounds.setHeight(66.0);
					bounds.setWidth(66.0);
					bounds.setX(x);
					bounds.setY(y);
					bpmnShape.setBounds(bounds);
				}
			}
		}
	}

}

package com.tansun.wf.activiti.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.CallActivity;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.commons.utils.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.handler.QueryHandler;
import com.tansun.wf.activiti.handler.QueryResultHandler;
import com.tansun.wf.activiti.model.ClassifyEntity;
import com.tansun.wf.activiti.util.ResultUtil;
import com.tansun.wf.camunda.bpmn.impl.instance.MultiWork;
import com.tansun.wf.camunda.bpmn.instance.Participants;
import com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant;
import com.tansun.wf.camunda.bpmn.instance.Usertaskfunction;
import com.tansun.workflow.model.ButtonModel;
import com.tansun.workflow.model.DeployInfo;
import com.tansun.workflow.model.PostInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;

/**
 * 流程定义操作服务类
 *
 */
@Service("workflowProcessDefineService")
@SuppressWarnings({"rawtypes","unchecked"})
public class WorkflowProcessDefineService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;
	
	public static final int STR_CHARACTER_START = 0;//字符串第一个字符开始位置
	
	public static final int STR_CHARACTER_END = 1;//字符串第一个字符结束位置
	
	@Autowired
	private QueryHandler queryHandler;
	
	@Autowired
    private WorkflowClassifyService workflowClassifyService;
	
	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 获取流程定义列表
	 * 
	 * @param processInfo
	 * @return
	 */
	public ProcessBean processDefinitionList(ProcessInfo processInfo) {
		ProcessDefinitionQuery processDefinitionQuery = this.repositoryService.createProcessDefinitionQuery();
		if(StringUtils.isNotBlank(processInfo.getTenantId())){
			processDefinitionQuery.tenantIdIn(processInfo.getTenantId());
		}
		if(StringUtils.isNotBlank(processInfo.getProcessDefineId())){
			processDefinitionQuery.processDefinitionId(processInfo.getProcessDefineId());
		}
		if(StringUtils.isNotBlank(processInfo.getProcessDefineKey())){
			processDefinitionQuery.processDefinitionKeyLike(processInfo.getProcessDefineKey());
		}
		if(StringUtils.isNotBlank(processInfo.getProcessName())){
			processDefinitionQuery.processDefinitionNameLike("%" +processInfo.getProcessName()+ "%" );
		}
//		processDefinitionQuery.orderByProcessDefinitionKey().orderByDeploymentId().desc();
		processDefinitionQuery.orderByProcessDefinitionVersion().desc();
		ProcessBean processBean = queryHandler.commonPageQuery(processInfo, processDefinitionQuery, new QueryResultHandler() {
			@Override
			public Map<String, Object> handlerRecord(Object record) {
		    	Map<String, Object> item = new LinkedHashMap<String, Object>();
		    	ProcessDefinition processDefinition =(ProcessDefinition) record;
				item.put("deploymentId", processDefinition.getDeploymentId());
				item.put("processDefineId", processDefinition.getId());
				item.put("processDefineKey", processDefinition.getKey());
				item.put("processDefineVersion", processDefinition.getVersion());
				item.put("processDefineName", processDefinition.getName());
				item.put("description", processDefinition.getDescription());
				item.put("resourceName", processDefinition.getResourceName());
				item.put("diagramResourceName", processDefinition.getDiagramResourceName());
				item.put("tenantId", processDefinition.getTenantId());
				return item;
			}
		});
		if(SUCCEED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询流程定义列表成功");
		}else if(processBean != null && FAILED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询流程定义列表失败");
		}
		return processBean;
	}
	
	/**
	 * 部署文件
	 * @param deployInfo
	 * @return
	 */
	public ProcessBean deploy(DeployInfo deployInfo) {
		InputStream fileInputStream = deployInfo.getInputStream();
		String fileName = deployInfo.getFileName();
		ProcessBean processBean = new ProcessBean();

		if (null == fileInputStream) {
			ResultUtil.setReturnCode(processBean, FAILED, "文件输入流为空!");
			return processBean;
		}

		if (StringUtils.isBlank(fileName)) {
			ResultUtil.setReturnCode(processBean, FAILED, "文件名为空!");
			return processBean;
		}
		try {
			DeploymentBuilder  deploymentBuilder= repositoryService.createDeployment();
			String extension = FilenameUtils.getExtension(fileName);
			if ("zip".equals(extension) || "bar".equals(extension)) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deploymentBuilder.addZipInputStream(zip);
			} else if ("bpmn".equals(extension) || fileName.endsWith("bpmn20.xml")) {
				// 和项目经理确认category就是classifyId
				if(StringUtils.isNotBlank(deployInfo.getCategory())){
					String content = IoUtil.inputStreamAsString(fileInputStream);
					Pattern pattern = Pattern.compile("targetNamespace=\"([^\"]*)\"");
					Matcher matcher = pattern.matcher(content);
					if(!matcher.find()){
						ResultUtil.setReturnCode(processBean, FAILED, "<definitions>必须有targetNamespace属性");
						return processBean;
					}
					String temp = matcher.group(1);
					content = content.replaceAll(temp, deployInfo.getCategory());
					fileInputStream =  IoUtil.stringAsInputStream(content);
				}
				deploymentBuilder.addInputStream(fileName, fileInputStream);
			}else {
				ResultUtil.setReturnCode(processBean, FAILED, "无法识别的文件类型");
				return processBean;
			}
			
			if(StringUtils.isNotBlank(deployInfo.getName())){
				deploymentBuilder.name(deployInfo.getName()); 
			}
			if(StringUtils.isNotBlank(deployInfo.getTenantId())){
				deploymentBuilder.tenantId(deployInfo.getTenantId()); 
			}
			if (StringUtils.isNotBlank(deployInfo.getName()) && WorkflowConstants.DUPLICATE_DEPLOY_FLAG.equals(deployInfo.getDuplicateFilter())) {
				deploymentBuilder.enableDuplicateFiltering();
			}
			Deployment deployment = deploymentBuilder.deploy();
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			Map<String, Object> item = new LinkedHashMap<String, Object>();
			resultList.add(item);
			item.put("id", deployment.getId());
			item.put("name", deployment.getName());
			item.put("deploymentTime", deployment.getDeploymentTime());
			item.put("category", deployInfo.getCategory());
			processBean.setResultList(resultList);
			ResultUtil.setReturnCode(processBean, SUCCEED, "流程部署成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ResultUtil.setReturnCode(processBean, FAILED, "流程部署失败");
		}
		return processBean;
	}


	/**
	 * 获取流程运行的实时图片
	 * @param processDefineId
	 * @return 
	 */
	public InputStream getProcessImage(String processDefineId) {
		return getProcessResource(processDefineId, "image");
	}

	/**
	 * 获取流程定义文件
	 * @param processDefineId
	 * @return 
	 */
	public InputStream getProcessXml(String processDefineId) {
		return getProcessResource(processDefineId, "xml");
	}
	
	/**
	 * 获取流程资源
	 * @param resourceType
	 * @param processDefineId 
	 * @return
	 */
	private InputStream getProcessResource(String processDefineId,String resourceType) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefineId).singleResult();
		String resourceName = "";
		if (resourceType.equals("image")) {
		    resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
		    resourceName = processDefinition.getResourceName();
		}
		return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
	}
	
	/**
	 * 根据流程定义创建模型  xml -json
	 * @param ids
	 * @return
	 */
	public ProcessBean createModelByProcessDefinition(String[] ids) {
		ProcessBean processBean = new ProcessBean();
		if (ArrayUtils.isEmpty(ids)) {
			ResultUtil.setReturnCode(processBean, FAILED, "流程定义id为空");
			return processBean;
		}
		try {
			for (String id : ids) {
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(id).singleResult();
				InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
						processDefinition.getResourceName());
				// camunda-TODO
//				InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
//				XMLStreamReader xtr = xmlInputFactory.createXMLStreamReader(in);
//				BpmnModelInstance bpmnModel =  this.bpmnXMLConverter.convertToBpmnModel(xtr);
				BpmnModelInstance bpmnModel =  Bpmn.readModelFromStream(bpmnStream);

				// camunda-TODO 探索创建模型的方法
//				ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
//				Model modelData = repositoryService.newModel();
//				modelData.setKey(processDefinition.getKey());
//				modelData.setName(processDefinition.getName());
//				modelData.setCategory(processDefinition.getDeploymentId());
//				modelData.setTenantId(processDefinition.getTenantId());
//				ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
//				modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
//				modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
//				modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
//				modelData.setMetaInfo(modelObjectNode.toString());
//
//				repositoryService.saveModel(modelData);
//				repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
				ResultUtil.setReturnCode(processBean, SUCCEED, "模型创建成功");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ResultUtil.setReturnCode(processBean, FAILED, "创建模型失败");
		}
		return processBean;
	}

	/**
	 * 复制流程定义
	 * @param targetDefinitionId 目标流程定义id
	 * @param newKey 新的键值
	 * @return newName 新的名字
	 * 
	 * 实现逻辑：
	 *   1.获取被复制的流程定义
	 *   2.转换为bpmnMoel
	 *   3.设置newKey,newName
	 *   4.部署成定义文件
	 */
	public ProcessBean copyProcessDefinition(String targetDefinitionId,String newKey,String newName) {
		if(StringUtils.isBlank(targetDefinitionId)){
			return new ProcessBean(FAILED,"复制流程定义失败，流程定义id为空！");
		}else{
			try{
				// camunda-TODO 熟悉转换方法
				BpmnModelInstance bpmnModelInstance = this.getBpmnModelByProcessDefinitionId(targetDefinitionId);
//				Definitions definitions = bpmnModel.getDefinitions();
//				definitions.
//				Process mainProcess = bpmnModel.getMainProcess();
//				mainProcess.setId(newKey);
//				mainProcess.setName(newName);
				//bompn转换为btye[]

//				byte[] bpmnBytes = bpmnXMLConverter.convertToXML(bpmnModel);
				//部署流程
//				repositoryService.createDeployment().name(newName).addString(newName+".bpmn", new String(bpmnBytes,"UTF-8")).deploy();
				repositoryService.createDeployment().name(newName).addString(newName+".bpmn", Bpmn.convertToString(bpmnModelInstance)).deploy();
				return new ProcessBean(SUCCEED,"复制流程定义成功！");
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				return new ProcessBean(FAILED, "复制流程定义失败");
			}
		}
	}

	/**
	 * 修改处理人并部署
	 * @param processDefinitionId
	 * @param activityId
	 * @param assignee
	 * @return
	 */
	public ProcessBean changeAssigee(String processDefinitionId, String activityId, String assignee) {
		if(StringUtils.isBlank(processDefinitionId)){
			return new ProcessBean(FAILED, "更改处理人失败！流程定义Id为空！");
		}else if(StringUtils.isBlank(assignee)){
			return new ProcessBean(FAILED, "更改处理人失败！处理人不为空！");
		}else if(StringUtils.isBlank(activityId)){
			return new ProcessBean(FAILED, "更改处理人失败！任务定义id为空！");
		}else {
			
			try{
				// camunda-TODO
				BpmnModelInstance bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
				ModelElementInstance element = bpmnModel.getModelElementById(activityId);
				if(!(element instanceof UserTask)){
					return new ProcessBean(FAILED, "更改处理人失败！节点id不是 user task 类型！");
				}else{
					UserTask userTask = (UserTask) element;
					userTask.setCamundaAssignee(assignee);
				}
				//部署
//				byte[] bpmnBytes = bpmnXMLConverter.convertToXML(bpmnModel);
//				String name = bpmnModel.getMainProcess().getName();
				String name = bpmnModel.getModel().getModelName();
				repositoryService.createDeployment().name(name).addString(name+".bpmn", Bpmn.convertToString(bpmnModel)).deploy();
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				return new ProcessBean(FAILED, "更改处理人失败！"); 
			}
			return new ProcessBean(SUCCEED, "更改处理人成功！"); 
		}
	}
	
	/**
	 * 根据流程定义id获取bpmnModel对象
	 * @param processDefinitionId
	 * @return
	 * @throws XMLStreamException 
	 */
	private BpmnModelInstance getBpmnModelByProcessDefinitionId(String processDefinitionId) throws XMLStreamException{
		// camunda-TODO
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),processDefinition.getResourceName());
//		XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(bpmnStream);
		return Bpmn.readModelFromStream(bpmnStream);
	}
	
	public  List<PostInfo>  getPostInfos(UserTaskParticipant taskParticipant){
		
		final  List<PostInfo> res = new  ArrayList<>(); 
		
		String parRadioConfig= taskParticipant.getParRadioConfig();
		if (UserTaskParticipant.CONFIG_TYPE_1.equals(parRadioConfig)) {
			Collection<Participants> parList = taskParticipant.getParticipants();
			if (parList != null) {					
				parList.forEach(par ->{
					PostInfo  postInfo  = new  PostInfo();
					postInfo.setType(par.getType());
					if (Participants.TYPE_ORG.equals(par.getType())) {
						postInfo.setOrgCode(par.getId());
					}else if (Participants.TYPE_DEPT.equals(par.getType())) {
						postInfo.setDeptCode(par.getId());
					}else if (Participants.TYPE_ROLE.equals(par.getType())) {
						postInfo.setRoleCode(par.getId());
					}else if (Participants.TYPE_USER.equals(par.getType())) {
						postInfo.setUserId(par.getId());
					}else if (Participants.TYPE_POST.equals(par.getType())) {
						String expression = par.getId();
						String roleCode = expression.substring(expression.indexOf("@{")+2,expression.indexOf("}{"));
						String orgCode = expression.substring(expression.indexOf("}{")+2,expression.length()-1);
						postInfo.setOrgCode(orgCode);
						postInfo.setRoleCode(roleCode);
					}
					res.add(postInfo);
										
				});
			}
		}
		return  res;
	}
	
	/**
	 * 根据流程定义id获取任务节点信息
	 * @param processDefinitionId
	 * @return
	 */
	public TaskBean getTaskDefinitions(String processDefinitionId){
		TaskBean bean = new TaskBean();
		if(StringUtils.isBlank(processDefinitionId)){
			bean.setRetCode(WorkflowConstants.SUCCEED);
			bean.setRetMsg("查询失败,流程定义id为空！");
			return bean;
		}else{
			bean.setRetCode(WorkflowConstants.SUCCEED);
			bean.setRetMsg("查询成功");
		}
		
        ProcessDefinitionEntity entity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(processDefinitionId);  
		List<ActivityImpl> acivities = entity.getActivities();
		List<Map> taskDefinitions = new ArrayList<Map>();

		for (ActivityImpl activityImpl : acivities) {
	
			if("userTask".equals(activityImpl.getProperty("type"))){
				Map<String,Object> taskDefinition = new HashMap<String,Object>();
				taskDefinition.put("taskName", activityImpl.getProperty("name"));
				taskDefinition.put("isMulti","N");
				//taskDefinition.put("taskDefinition",multiInstance == null?false:true);
				if (activityImpl.getActivityBehavior() instanceof MultiInstanceActivityBehavior) {
					taskDefinition.put("isMulti","Y");
				}				
				TaskDefinition taskDefinitionProperties =(TaskDefinition) activityImpl.getProperty("taskDefinition");
				UserTaskParticipant  userTaskParticipant = null;
				if(null != taskDefinitionProperties){
					taskDefinition.put("taskKey", taskDefinitionProperties.getKey());
					userTaskParticipant = this.getNodeExtensionProperty(processDefinitionId,taskDefinitionProperties.getKey(),UserTaskParticipant.class,UserTaskParticipant.class);
				}else {
					taskDefinition.put("taskKey",activityImpl.getId());
					userTaskParticipant = this.getNodeExtensionProperty(processDefinitionId,activityImpl.getId(),UserTaskParticipant.class,UserTaskParticipant.class);
					
				}
				if ("Y".equals(taskDefinition.get("isMulti"))) {
					MultiWork mulWork =this.getNodeExtensionProperty(processDefinitionId,activityImpl.getId(),MultiWork.class,MultiWork.class);
					String  colletion = mulWork.getIterateElement();
					if (!StringUtils.isEmpty(colletion)) {
						taskDefinition.put("iterateElement", colletion);
					}
				}
				//获取自定义属性
				if(null!=userTaskParticipant){
					List<PostInfo> postInfos =this.getPostInfos(userTaskParticipant);
					if (null != postInfos) {
						taskDefinition.put("postInfos", postInfos);
					}			
				}
				taskDefinitions.add(taskDefinition);
			}else if("callActivity".equals(activityImpl.getProperty("type"))){
				// 会签节点
				String id = activityImpl.getProcessDefinition().getId();
				// camunda-TODO
//				FlowElement flowElement = repositoryService.getBpmnModel(id).getFlowElement(activityImpl.getId());
				FlowElement flowElement = repositoryService.getBpmnModelInstance(id).getModelElementById(activityImpl.getId());

				CallActivity callActivity = (CallActivity) flowElement;
				String processDefinitonKey = callActivity.getCalledElement();
				TaskInfo taskInfo = new TaskInfo();
				taskInfo.setProcessDefineKey(processDefinitonKey);
				TaskBean taskBean = getTaskDefinitionsByKey(taskInfo);
				Map<String,Object> taskDefinition = new HashMap<String,Object>();
				taskDefinition.put("taskName", activityImpl.getProperty("name"));
				taskDefinition.put("isMulti","N");
				//taskDefinition.put("taskDefinition",multiInstance == null?false:true);
				taskDefinition.put("taskKey", activityImpl.getId());
				taskDefinition.put("isSubProcess", "true");
				List<Map> taskDefinitionList = taskBean.getTaskDefinitionList();
				if (taskDefinitionList != null){
					for (Map map : taskDefinitionList) {
						map.put("superTaskKey",activityImpl.getId());
					}
					taskDefinitions.addAll(taskDefinitionList);
				}

				taskDefinitions.add(taskDefinition);

			}
		}
		bean.setTaskDefinitionList(taskDefinitions);
		return bean;
	}

	/**
	 * 获取节点的自定义属性
	 * @param processDefId
	 * @param taskDefId
	 * @param propertyName
	 * @return
	 */
	@Deprecated
	public <T> T getNodeExtensionProperty(String processDefId, String taskDefId, String propertyName, Class<T> clazz) {
		// camunda-TODO
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefId);
		return getNodeExtensionProperty(bpmnModel, taskDefId, propertyName, clazz);
	}

	/**
	 *
	 * 获取节点的自定义属性
	 * @param taskDefId
	 * @param propertyName
	 * @return
	 */	
	public <T> T getNodeExtensionProperty(BpmnModelInstance bpmnModel, String taskDefId, String propertyName, Class<T> clazz) {
		FlowElement flowElement = bpmnModel.getModelElementById(taskDefId);
		ExtensionElements elements = flowElement.getExtensionElements();

		if(elements != null){
				String text =  elements.getTextContent();
				if(StringUtils.isNotBlank(text) && !"\"\"".equals(text)){
					if ("\"".equals(text.substring(STR_CHARACTER_START, STR_CHARACTER_END))) {
						text = trimQuotes(text);
					}
					text = text.replaceAll("\\\\", "");
					if(StringUtils.isNotBlank(text)){
						if(clazz == String.class){
							return (T) text;
						}else {
							return JSON.parseObject(text,clazz);
						}
					}
				}

//			}
		}

		return null;
	}
	
	public <T> T getNodeExtensionProperty(String processDefId, String taskDefId, Class claz, Class<T> retClazz) {

		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefId);
		return getNodeExtensionProperty(bpmnModel, taskDefId, claz, retClazz);
	}

	/**
	 *
	 * 获取节点的自定义属性
	 * @param taskDefId
	 * @return
	 */
	public <T> T getNodeExtensionProperty(BpmnModelInstance bpmnModel, String taskDefId, Class claz, Class<T> clazz) {

		FlowElement flowElement = bpmnModel.getModelElementById(taskDefId);
		if (flowElement == null)
			return null;
		ExtensionElements elements = flowElement.getExtensionElements();
		// 第一个节点，可能没有配置，没有自定义标签
		if(elements==null){
			return null;
		}
		List<ModelElementInstance> modelList = elements.getElementsQuery().filterByType(claz).list();
		if (modelList != null && modelList.size()>0 ) {
			ModelElementInstance modelElem = modelList.get(0);
			String text = modelElem.getTextContent();
			if(StringUtils.isNotBlank(text) && !"\"\"".equals(text)){
				if ("\"".equals(text.substring(STR_CHARACTER_START, STR_CHARACTER_END))) {
					text = trimQuotes(text);
				}
				text = text.replaceAll("\\\\", "");
				if(StringUtils.isNotBlank(text)){
					if(clazz == String.class){
						return (T) text;
					}else {
						return JSON.parseObject(text,clazz);
					}
				}
			}else {
				return (T)modelElem;
			}
		}
		

		return null;
	}

    /**
     * 获取bpmn 用户节点信息
     * @param processDefId
     * @param taskDefId
     */
	public UserTask getBpmnUserTask(String processDefId, String taskDefId){
		// camunda-TODO
        BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefId);
        return (UserTask) bpmnModel.getModelElementById(taskDefId);
    }
	
	/**
     * 获取分类及其子分类的所有已发布流程创建信息
     * @param processInfo
     * @return
     */
    public ProcessBean findProcessByCode(ProcessInfo processInfo){
    	ProcessBean processBean = new ProcessBean();
    	if (StringUtils.isBlank(processInfo.getClassifyCode())) {
			processBean.setRetCode(FAILED);
			processBean.setRetMsg("流程编号不能为空");
			return processBean;
		}
    	List<ClassifyEntity> classifyList = workflowClassifyService.getClassifyByClassifyCode(processInfo.getClassifyCode());//这里list.size <= 1
    	List<Map<String, Object>> processDefinitionsMap = new ArrayList<Map<String, Object>>();
    	for(ClassifyEntity classify:classifyList){
    		String parentIds = classify.getId();
    		List<ClassifyEntity> list = workflowClassifyService.getClassifyByClassifyId(parentIds);
    		for(ClassifyEntity l:list){
    			List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
    					.processDefinitionCategory(l.getId()).latestVersion().list();
    			for(ProcessDefinition processDefinition:processDefinitions){
    				Map<String, Object> map = new HashMap<String, Object>();
    				map.put("proceeDefinitionName",processDefinition.getName());
    				map.put("proceeDefinitionKey", processDefinition.getKey());
    				map.put("proceeDefinitionId", processDefinition.getId());
    				processDefinitionsMap.add(map);
    			}
    		}
    		processBean.setRetCode(SUCCEED);
    	}
    	processBean.setResultList(processDefinitionsMap);
    	return processBean;
    }
	
	/**
     * 去除字符串前后双引号
     * @return
     */
    private String trimQuotes(String str){
        if(!StringUtils.isEmpty(str) && str.length() >1){
            if("\"".equals(str.charAt(0)+"") && "\"".equals(str.charAt(str.length()-1)+"")){
                return str.substring(1,str.length()-1);
            }
        }
        return str;
    }

	public TaskBean getTaskDefinitionsByKey(TaskInfo taskInfo) {
		String processDefineKey = taskInfo.getProcessDefineKey();
		if (StringUtils.isBlank(processDefineKey)){
			return new TaskBean(FAILED, "查询失败，流程定义Key为空");
		}
		String tenantId = taskInfo.getTenantId();
		String processDefinitionId = null;
		if (StringUtils.isNotBlank(tenantId)){
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefineKey).tenantIdIn(tenantId).latestVersion().singleResult();
			processDefinitionId = processDefinition.getId();
		}else {
			List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefineKey).latestVersion().list();
			if (CollectionUtils.isEmpty(processDefinitions)){
				return new TaskBean(FAILED, "查询失败，找不到流程定义");
			}else if(processDefinitions.size() >1){
				return new TaskBean(FAILED, "查询失败，该键值下有多个流程定义，请添加租户id作为过滤条件");
			}
			processDefinitionId = processDefinitions.get(0).getId();
		}

		if (null == processDefinitionId){
			return new TaskBean(FAILED, "查询失败，找不到流程定义");
		}

		return getTaskDefinitions(processDefinitionId);
	}

	/**
	 * 获取会签元素变量key
	 * @param processDefinitionId
	 * @param activitId
	 * @return
	 */
	public static String getMultiElementKey(RepositoryService repositoryService, String processDefinitionId, String activitId){
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
		UserTask userTask = (UserTask) bpmnModel.getModelElementById(activitId);
		MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
		if (loopCharacteristics == null || loopCharacteristics.isSequential()){
			return null;
		}

		// camunda-TODO
//		return loopCharacteristics.getElementVariable();
		return loopCharacteristics.getCamundaElementVariable();
	}

	/**
	 * 获取会签集合变量key
	 * @param processDefinitionId
	 * @param activitId
	 * @return
	 */
	public static String getMultiInputDataItem(RepositoryService repositoryService,String processDefinitionId,String activitId){
		// camunda-TODO
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
		UserTask userTask = (UserTask) bpmnModel.getModelElementById(activitId);
		MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
		if (loopCharacteristics == null || loopCharacteristics.isSequential()){
			return null;
		}

		// camunda-TODO
//		return loopCharacteristics.getInputDataItem();
		return loopCharacteristics.getCamundaElementVariable();
	}

	/**
	 * 获取按钮信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findButtonPower(TaskInfo taskInfo){
		String actButton = "";
		TaskBean taskBean = new TaskBean();
		//存数据库老方法
		if (StringUtils.isEmpty(taskInfo.getProcessDefineId()) || StringUtils.isEmpty(taskInfo.getActivityId())) {
			taskBean.setActButton(actButton);
			taskBean.setRetCode(WorkflowTaskService.FAILED);
			taskBean.setRetMsg("查询失败！参数流程定义id和任务id都不能为空！");
			return taskBean;
		}
		JSONObject jsonObject = getNodeExtensionProperty(taskInfo.getProcessDefineId(), taskInfo.getActivityId(), Usertaskfunction.class,JSONObject.class);

		List<ButtonModel> butonModes = new ArrayList<ButtonModel>();

		if (jsonObject != null) {

			JSONArray jSONArray = jsonObject.getJSONArray("taskbutton");
			butonModes = JSONArray.parseArray(jSONArray.toJSONString(), ButtonModel.class);
			if (jSONArray != null) {
				actButton = JSON.toJSONString(jSONArray);
			}
		}

		taskBean.setActButton(actButton);
		taskBean.setActButtonModel(butonModes);
		taskBean.setRetCode(WorkflowTaskService.SUCCEED);
		return taskBean;
	}

	/**
	 * 获取该节点是否是会签
	 * @param processDefinitionId
	 * @param activityId
	 * @return
	 */
	public String getIsMulti(String processDefinitionId, String activityId) {
		// camunda-TODO
		BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
		FlowElement flowElement = bpmnModel.getModelElementById(activityId);
		if(flowElement instanceof UserTask){
			UserTask userTask = (UserTask) flowElement;
			MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
			if (loopCharacteristics != null ) {
				return "true";
			}else{
				return "false";
			}
		}
		return "false";
	}

}

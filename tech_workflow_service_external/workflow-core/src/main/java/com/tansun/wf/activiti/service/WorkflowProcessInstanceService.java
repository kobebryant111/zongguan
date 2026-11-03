package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSONObject;
import com.tansun.wf.activiti.comparator.HistoricTaskInstanceComparator;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.handler.QueryHandler;
import com.tansun.wf.activiti.handler.QueryResultHandler;
import com.tansun.wf.activiti.model.*;
import com.tansun.wf.activiti.util.DateUtil;
import com.tansun.wf.activiti.util.TransformUtil;
import com.tansun.wf.activiti.util.WfExpandPlanUtil;
import com.tansun.wf.platform.mapper.ActHiTaskinstMapper;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import com.tansun.wf.platform.service.WfActHiCommentService;
import com.tansun.workflow.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流程实例操作服务类
 *
 */
@Service("workflowProcessInstanceService")
@SuppressWarnings({"rawtypes","unchecked"})
public class WorkflowProcessInstanceService {
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;
	
	@Autowired
	private QueryHandler queryHandler;
	
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;

	@Autowired
	private WorkflowHiTaskExpandDataService workflowHiTaskExpandDataService;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private EditorServiceProxyService editorServiceProxyService;

	@Autowired
	private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;

	@Autowired
	private WorkflowProcessDefineService workflowProcessDefineService;

	@Autowired
	private WorkflowUserService workflowUserService;

	@Autowired
	private ActHiTaskinstMapper actHiTaskinstMapper;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private WfActHiCommentService wfActHiCommentService;

	/**
	 * 运行中的流程查询
	 * @param processInfo
	 * @return
	 */
	public ProcessBean processInstanceList(ProcessInfo processInfo) {
		ProcessBean processBean = null;
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		if(StringUtils.isNotBlank(processInfo.getProcessInstanceId())){
			processInstanceQuery.processInstanceId(processInfo.getProcessInstanceId());
		}
		
		if(StringUtils.isNotBlank(processInfo.getProcessDefineId())){
			processInstanceQuery.processDefinitionId(processInfo.getProcessDefineId());
		}
		
		if(StringUtils.isNotBlank(processInfo.getProcessDefineKey())){
			processInstanceQuery.processDefinitionKey(processInfo.getProcessDefineKey());
		}
		
        if (StringUtils.isNotBlank(processInfo.getProcessName())) {
        	// camunda的流程实例没有name
//            processInstanceQuery.processInstanceNameLike(processInfo.getProcessName()+"%");
        }
		
		if(StringUtils.isNotBlank(processInfo.getTenantId())){
			processInstanceQuery.tenantIdIn(processInfo.getTenantId());
        }
        if (StringUtils.isNotBlank(processInfo.getBusinessKey())) {
            processInstanceQuery.processInstanceBusinessKey(processInfo.getBusinessKey());
		}
		if(StringUtils.isNotBlank(processInfo.getTaskId())){
			 Task task = taskService.createTaskQuery().taskId(processInfo.getTaskId()).singleResult();
			 if(task == null){
				 processBean = new ProcessBean();
				 processBean.setRetCode(FAILED);
				 processBean.setRetMsg("查询运行中的流程列表失败,任务Id没有找的");
				 return processBean;
			 }else{
				 processInstanceQuery.processInstanceId(task.getProcessInstanceId());
			 }
		}
		processInstanceQuery.orderByProcessDefinitionId().desc().orderByProcessInstanceId().desc();
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		processBean = queryHandler.commonPageQuery(processInfo, processInstanceQuery, new QueryResultHandler() {
			@Override
			public Map<String, Object> handlerRecord(Object record) {
		    	Map<String, Object> item = new LinkedHashMap<String, Object>();
		    	ProcessInstance processInstance = (ProcessInstance)record;
				item.put("executionId", processInstance.getId());
				item.put("processInstanceId", processInstance.getProcessInstanceId());
				item.put("processVariables", runtimeService.getVariablesLocal(processInstance.getId()));
				HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().
						processInstanceId(processInstance.getId()).
						singleResult();
				item.put("processStartTime",formatter.format(historicProcessInstance.getStartTime()));
				item.put("processDefineId", processInstance.getProcessDefinitionId());
				// camunda-流程定义信息
				String definitionId = processInstance.getProcessDefinitionId();
				ProcessDefinition definition = repositoryService.getProcessDefinition(definitionId);
				item.put("processDefineKey", definition.getKey());
				item.put("processDefineName", definition.getName());
				item.put("processDefineVersion", definition.getVersion());
				// 是否挂起
				item.put("isSuspended", processInstance.isSuspended());
                // camunda-没有activityId、activityName
//				item.put("activityId", processInstance.getActivityId());
//				item.put("activityName", getActivityName(processInstance.getProcessDefinitionId(), processInstance.getActivityId()));
				item.put("businessKey", processInstance.getBusinessKey());
				item.put("tenantId",processInstance.getTenantId());
				return item;
			}
		});
		if(SUCCEED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询运行中的流程列表成功");
		}else if(processBean != null && FAILED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询运行中的流程列表失败");
		}
		return processBean;
	}
	
	private String getActivityName(String processDefineId,String activityId){
		ActivityImpl activityImpl = getActivity(processDefineId, activityId);
		if(activityImpl == null){
			return null;
		}else{
			Object name = activityImpl.getProperty("name");
			return name == null?"":name.toString();
		}
	}
	
	private ActivityImpl getActivity(String processDefineId,String activityId){
		ProcessDefinitionEntity def = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefineId);
		List<ActivityImpl> ActivityImpls = def.getActivities();
		for (ActivityImpl activityImpl : ActivityImpls) {
			if(activityImpl.getId().equals(activityId)){
				return activityImpl;
			}
		}
		return null;
	}
	
	/**
	 * 获取已经结束的流程列表
	 * @param processInfo
	 * @return
	 */
	public ProcessBean getCompletedProcess(ProcessInfo processInfo) {
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		if ("unfinish".equals(processInfo.getIgnoreFinish())){
			historicProcessInstanceQuery.unfinished();
        }
        if ("finish".equals(processInfo.getIgnoreFinish())){
            historicProcessInstanceQuery.finished();
        }
		if(StringUtils.isNotBlank(processInfo.getTenantId())){
			historicProcessInstanceQuery.tenantIdIn(processInfo.getTenantId());
		}
		if(StringUtils.isNotBlank(processInfo.getProcessDefineId())){
			historicProcessInstanceQuery.processDefinitionId(processInfo.getProcessDefineId());
		}
		if(StringUtils.isNotBlank(processInfo.getProcessDefineKey())){
			historicProcessInstanceQuery.processDefinitionKey(processInfo.getProcessDefineKey());
		}
		if(StringUtils.isNotBlank(processInfo.getProcessInstanceId())){
			historicProcessInstanceQuery.processInstanceId(processInfo.getProcessInstanceId());
		}
        if (StringUtils.isNotBlank(processInfo.getProcessName())) {
            historicProcessInstanceQuery.processDefinitionNameLike(processInfo.getProcessName() + "%");
        }
        if (StringUtils.isNotBlank(processInfo.getBusinessKey())) {
            historicProcessInstanceQuery.processInstanceBusinessKey(processInfo.getBusinessKey());
		}
		if (StringUtils.isNotBlank(processInfo.getStartUserId())) {
			historicProcessInstanceQuery.startedBy(processInfo.getStartUserId());
		}
		if ("desc".equals(processInfo.getStartTimeSort())){
            historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        }
        if ("asc".equals(processInfo.getStartTimeSort())){
            historicProcessInstanceQuery.orderByProcessInstanceStartTime().asc();
        }
        if ("desc".equals(processInfo.getEndTimeSort())){
            historicProcessInstanceQuery.orderByProcessInstanceEndTime().desc();
        }
        if ("asc".equals(processInfo.getEndTimeSort())){
            historicProcessInstanceQuery.orderByProcessInstanceEndTime().asc();
        }

        // 流程变量作为查询条件
		Map<String, Object> mPara = processInfo.getmPara();
		if(MapUtils.isNotEmpty(mPara)){
			Set<String> keySet = mPara.keySet();
			for (String key : keySet) {
				Object object = MapUtils.getObject(mPara, key);
				if(object instanceof String){
					// String类型可以模糊查询
					historicProcessInstanceQuery.variableValueLike(key,MapUtils.getString(mPara, key));
				}else{
					historicProcessInstanceQuery.variableValueEquals(key,object);
				}
			}
		}
		// 一次性查询多条流程的流程变量
		List<String> processInstanceIdList = new ArrayList<>();

		ProcessBean processBean = queryHandler.commonPageQuery(processInfo, historicProcessInstanceQuery, record -> {
			Map<String, Object> item = new LinkedHashMap<>();
			HistoricProcessInstanceEntity historicProcessInstance = (HistoricProcessInstanceEntity)record;
			item.put("id", historicProcessInstance.getId());
			item.put("processInstanceId", historicProcessInstance.getId());
			processInstanceIdList.add(historicProcessInstance.getId());
			item.put("processDefineId", historicProcessInstance.getProcessDefinitionId());
			item.put("processDefineKey", historicProcessInstance.getProcessDefinitionKey());
			item.put("processDefineName", historicProcessInstance.getProcessDefinitionName());
			// camunda-没有activityId、activityName
//			item.put("activityId", historicProcessInstance.getNowActivityId());
//			item.put("activityName", getActivityName(historicProcessInstance.getProcessDefinitionId(), historicProcessInstance.getNowActivityId()));
			// 历史流程实例表没有记录是否挂起的信息
			// 流程挂起激活状态：SUSPENDED 挂起，ACTIVE 激活
			boolean state = false;
			if("SUSPENDED".equals(historicProcessInstance.getState())){
				state = true;
			}
			item.put("isSuspended", state);
			item.put("processDefineVersion", historicProcessInstance.getProcessDefinitionVersion());
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime="";
			if (historicProcessInstance.getStartTime()!=null){
                startTime = time.format(historicProcessInstance.getStartTime());
			}
			item.put("startTime", startTime);
			item.put("endTime", historicProcessInstance.getEndTime());
			item.put("deleteReason", historicProcessInstance.getDeleteReason());
			item.put("businessKey", historicProcessInstance.getBusinessKey());
			item.put("userId", historicProcessInstance.getStartUserId());
			item.put("tenantId",historicProcessInstance.getTenantId());
			return item;
		});

		// 一次性查询多个流程的流程变量数据，插入返回的列表数据中
		if(CollectionUtils.isNotEmpty(processInstanceIdList)){
			List<Map<String, Object>> resultList = processBean.getResultList();
			String[] processInstanceIds = processInstanceIdList.toArray(new String[resultList.size()]);
			List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceIdIn(processInstanceIds).list();
			if(CollectionUtils.isNotEmpty(list)){
				for (HistoricVariableInstance historicVariableInstance : list) {
					for (Map<String, Object> map : resultList) {
						if(StringUtils.equals(MapUtils.getString(map,"processInstanceId"),historicVariableInstance.getProcessInstanceId())){
							map.put(historicVariableInstance.getName(),historicVariableInstance.getValue());
						}
					}
				}
			}
		}

		if(SUCCEED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询流程列表成功");
		}else if(processBean != null && FAILED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询流程列表失败");
		}
		return processBean;
	}

	public ProcessBean queryWfInstList(WfInstListQo param) {
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		if ("unfinish".equals(param.getIgnoreFinish())){
			historicProcessInstanceQuery.unfinished();
		}
		if ("finish".equals(param.getIgnoreFinish())){
			historicProcessInstanceQuery.finished();
		}
		if(StringUtils.isNotBlank(param.getTenantId())){
			historicProcessInstanceQuery.tenantIdIn(param.getTenantId());
		}
		if(StringUtils.isNotBlank(param.getProcessDefineId())){
			historicProcessInstanceQuery.processDefinitionId(param.getProcessDefineId());
		}
		if(StringUtils.isNotBlank(param.getProcessDefineKey())){
			historicProcessInstanceQuery.processDefinitionKey(param.getProcessDefineKey());
		}
		if(StringUtils.isNotBlank(param.getProcessInstanceId())){
			historicProcessInstanceQuery.processInstanceId(param.getProcessInstanceId());
		}
		if (StringUtils.isNotBlank(param.getProcessName())) {
			historicProcessInstanceQuery.processDefinitionNameLike("%" + param.getProcessName() + "%");
		}
		if (StringUtils.isNotBlank(param.getBusinessKey())) {
			historicProcessInstanceQuery.processInstanceBusinessKey(param.getBusinessKey());
		}
		if (param.getStartedBefore() != null) {
			historicProcessInstanceQuery.startedBefore(param.getStartedBefore());
		}
		if (param.getStartedAfter() != null) {
			historicProcessInstanceQuery.startedAfter(param.getStartedAfter());
		}
        if (param.getFinishedBefore() != null) {
            historicProcessInstanceQuery.finishedBefore(param.getFinishedBefore());
        }
        if (param.getFinishedAfter() != null) {
            historicProcessInstanceQuery.finishedAfter(param.getFinishedAfter());
        }
		if ("desc".equals(param.getStartTimeSort())){
			historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
		}
		if ("asc".equals(param.getStartTimeSort())){
			historicProcessInstanceQuery.orderByProcessInstanceStartTime().asc();
		}
		if ("desc".equals(param.getEndTimeSort())){
			historicProcessInstanceQuery.orderByProcessInstanceEndTime().desc();
		}
		if ("asc".equals(param.getEndTimeSort())){
			historicProcessInstanceQuery.orderByProcessInstanceEndTime().asc();
		}

		ProcessInfo processInfo = new ProcessInfo();
		processInfo.setPageSize(param.getPageSize());
		processInfo.setCurrentPage(param.getPageNum());
		ProcessBean processBean = queryHandler.commonPageQuery(processInfo, historicProcessInstanceQuery, record -> {
			Map<String, Object> item = new LinkedHashMap<>();
			HistoricProcessInstanceEntity historicProcessInstance = (HistoricProcessInstanceEntity)record;
			item.put("id", historicProcessInstance.getId());
			item.put("processInstanceId", historicProcessInstance.getId());
			item.put("processDefineId", historicProcessInstance.getProcessDefinitionId());
            item.put("processDefineName", historicProcessInstance.getProcessDefinitionName());
            item.put("processDefineKey", historicProcessInstance.getProcessDefinitionKey());
			item.put("processDefineVersion", historicProcessInstance.getProcessDefinitionVersion());
            item.put("endTime", historicProcessInstance.getEndTime());
            item.put("startTime", historicProcessInstance.getStartTime());
			item.put("deleteReason", historicProcessInstance.getDeleteReason());
            item.put("userId", historicProcessInstance.getStartUserId());
            item.put("businessKey", historicProcessInstance.getBusinessKey());
			item.put("tenantId",historicProcessInstance.getTenantId());
			item.put("state", historicProcessInstance.getState());
			return item;
		});

		if(SUCCEED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询完成流程列表成功");
		}else if(processBean != null && FAILED.equals(processBean.getRetCode())){
			processBean.setRetMsg("查询完成流程列表失败");
		}
		return processBean;
	}
	
    public TaskBean queryProcessTrack(ProcessInfo processInfo) {
		String processInstanceId = processInfo.getProcessInstanceId();
		List<Map> hiTasks = new ArrayList();
		if (StringUtils.isBlank(processInstanceId)) {
			return new TaskBean(FAILED, "流程实例ID为空!");
		}

		HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
		if (StringUtils.isNotBlank(processInfo.getTaskId())) {
			historicTaskInstanceQuery.taskId(processInfo.getTaskId());
		}
		List<HistoricTaskInstance> list = historicTaskInstanceQuery.processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
		List<HistoricActivityInstance> serviceTaskList = historyService.createHistoricActivityInstanceQuery().activityType("serviceTask").processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
		if (CollectionUtils.isEmpty(list) && CollectionUtils.isEmpty(serviceTaskList)) {
			return new TaskBean(FAILED, "流程实例不存在!");
		}
		List<HistoricVariableInstance> hiVariables = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
		//查询子流程，显示主流程和子流程的流程轨迹
		if("Y".equals(processInfo.getIsShowSuperProcess())){
			HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if(StringUtils.isNotEmpty(instance.getSuperProcessInstanceId())){
				final ProcessInfo info = new ProcessInfo();
				info.setProcessInstanceId(instance.getSuperProcessInstanceId());
				info.setIsShowSubProcess("Y");
				return queryProcessTrack(info);
			}
		}
		if(CollectionUtils.isNotEmpty(list)){

			List<WfTaskExpandPlanEntity> plans = workflowTaskExpandPlanService.getExpandPlanByProcessDefinitionId(list.get(0).getProcessDefinitionId());
			final List<String> fieldMapping = WfExpandPlanUtil.generateFieldMapping(plans);
//			// 已办轨迹
			List<Map<String, Object>> hiProcinstWithHiTaskExpands = actHiTaskinstMapper.selectHiProcinstWithHiTaskExpand(processInfo,fieldMapping);
			// 待办轨迹
			List<Map<String, Object>> hiProcinstWithTaskExpands = actHiTaskinstMapper.selectHiProcinstWithTaskExpand(processInfo,fieldMapping);
//			List<Task> task = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
//			List<String> taskIds = new ArrayList<>();
//			if (task != null && task.size() != 0) {
//				for(Task t:task){
//					taskIds.add(t.getId());
//				}
//			}

			for (HistoricTaskInstance hiTask : list) {
				String strDeleteReason = hiTask.getDeleteReason();
				if("reback".equalsIgnoreCase(strDeleteReason)||"TansunBackDelete".equalsIgnoreCase(strDeleteReason)){
					continue;
				}
				Map map = new HashMap();
				map.put("taskId", hiTask.getId());
				map.put("taskName", hiTask.getName());
				map.put("activityId", hiTask.getTaskDefinitionKey());
				map.put("assignee", hiTask.getAssignee());
				map.put("beginTime", DateUtil.formatDate(hiTask.getStartTime()));
				map.put("createTime", DateUtil.formatDate(hiTask.getStartTime()));
				map.put("endTime", DateUtil.formatDate(hiTask.getEndTime()));
				map.put("isMulti", workflowProcessDefineService.getIsMulti(hiTask.getProcessDefinitionId(), hiTask.getTaskDefinitionKey()));
				map.put("deleteReason", hiTask.getDeleteReason());
				if (hiTask.getEndTime() == null) {
					// 如果一条流程几十个任务的话，会很慢，优化
					//去查询是否已阅
                    map.put("status", "未阅");
                    for (HistoricVariableInstance variable : hiVariables) {
						if (hiTask.getId().equals(variable.getTaskId()) && "taskReadStatus".equals(variable.getName())) {
							Object readStatus = variable.getValue();
							if (readStatus != null && !"".equals(readStatus.toString())) {
								map.put("status", "已阅");
								break;
							}
						}
					}
				}else {
					map.put("status", "已阅");
				}
				List<Comment> comments = taskService.getTaskComments(hiTask.getId());
				map.put("comment", CollectionUtils.isEmpty(comments) ? "" : comments.get(0).getFullMessage());
				// 赋值所有流程变量
				map.putAll(getFromVariables(hiVariables,hiTask));
				getCandidate(hiTask, map);
				addTransferTrack(hiVariables,hiTasks,hiTask);
				hiTasks.add(map);
			}
//			addUserName(hiTasks);
			addExpandsPlanField(hiTasks,hiProcinstWithHiTaskExpands);
			addExpandsPlanField(hiTasks,hiProcinstWithTaskExpands);
		}
		// 查询所有子流程的任务
		if("Y".equals(processInfo.getIsShowSubProcess())){
			List<HistoricProcessInstance> childrenProcessInstances = historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(processInstanceId).list();
			for (HistoricProcessInstance instance : childrenProcessInstances) {
				ProcessInfo subProcessInfo = new ProcessInfo();
				subProcessInfo.setProcessInstanceId(instance.getId());
				TaskBean taskBean = queryProcessTrack(subProcessInfo);
				if(taskBean != null){
					hiTasks.addAll(taskBean.getTaskList());
				}
			}
			// 对轨迹重新排序
			if(CollectionUtils.isNotEmpty(childrenProcessInstances)){
				Collections.sort(hiTasks,new HistoricTaskInstanceComparator());
			}
		}

		TaskBean result = new TaskBean(SUCCEED, "查询成功!");
		result.setTaskList(hiTasks);
		return result;
    }

	/**
	 * 把转交的记录加到历史流程轨迹中
	 * @param hiTasks
	 * @param hiTask
	 */
	private void addTransferTrack(List<HistoricVariableInstance> hiVariables, List hiTasks, HistoricTaskInstance hiTask) {
		Object track = null;

		for (HistoricVariableInstance variable : hiVariables) {
			if (hiTask.getId().equals(variable.getTaskId()) && WorkflowConstants._TRANSFER_TRACK.equals(variable.getName())) {
				track = variable.getValue();
			}
		}

		if(track == null){
			return;
		}
		Map transfer = (Map)track;
		while ((transfer != null)){
			Map map = new HashMap();
			map.put("taskId", hiTask.getId());
			map.put("taskName", hiTask.getName());
			map.put("activityId", hiTask.getTaskDefinitionKey());
			map.put("assignee", transfer.get("assignee"));
			map.put("beginTime", DateUtil.formatDate(hiTask.getStartTime()));
			map.put("createTime", DateUtil.formatDate(hiTask.getStartTime()));
			map.put("endTime", transfer.get("endTime"));
			map.put("comment",transfer.get("comment"));
            map.put("status", "已阅");
            if(null != transfer.get("localVariables")){
				JSONObject js = (JSONObject) transfer.get("localVariables");
				for(String key:js.keySet()){
					map.put(key,js.get(key));
				}
			}
			hiTasks.add(map);
			transfer = (Map)transfer.get("next");
		}


	}

	public void getCandidate(HistoricTaskInstance hiTask, Map map){
		if (hiTask.getEndTime() == null) {
			final TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(hiTask.getId()).singleResult();

			if (task != null) {
				Set<String> userIds = new HashSet<>();
				ActRuIdentityLinkQuery actRuIdentityLinkQuery = new ActRuIdentityLinkQuery();
				actRuIdentityLinkQuery.setTaskId(task.getId());
				List<ActRuIdentitylinkEntity> candidateUserList = wfActHiCommentService.findCandidateUserList(actRuIdentityLinkQuery);
				if(CollectionUtils.isNotEmpty(candidateUserList)){
					for (ActRuIdentitylinkEntity identityLinkEntity : candidateUserList) {
						EditorInfo editorInfo = new EditorInfo();
						editorInfo.setTenantId(task.getTenantId());
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

					}
				}
				map.put("assignee", StringUtils.join(userIds,","));
			}
		}
	}


	/**
	 * 标记任务，已阅、未阅
	 * @param taskInfo
	 * @return
	 */
	public TaskBean signTaskStatus(TaskInfo taskInfo){
		if (StringUtils.isBlank(taskInfo.getTaskId())) {
			return new TaskBean(FAILED, "taskId不能为空");
		}


		String taskReadStatus = "taskReadStatus";

		String statusValue = (String)taskService.getVariableLocal(taskInfo.getTaskId(), taskReadStatus);
		if (StringUtils.equals("已阅",statusValue)) {
			return new TaskBean(SUCCEED,"该任务已标为已阅");
		}

		taskService.setVariableLocal(taskInfo.getTaskId(), taskReadStatus, "已阅");

		return new TaskBean(SUCCEED,"已阅标记成功");
	}
	/**
	 * 移除任务已阅状态
	 * @param taskInfo
	 * @return
	 */
	public TaskBean removeTaskStatus(TaskInfo taskInfo){
		if (StringUtils.isBlank(taskInfo.getTaskId())) {
			return new TaskBean(FAILED, "taskId不能为空");
		}


		String taskReadStatus = "taskReadStatus";

		String statusValue = (String)taskService.getVariableLocal(taskInfo.getTaskId(), taskReadStatus);
		if (StringUtils.isBlank(statusValue)) {
			return new TaskBean(SUCCEED,"该任务不是已阅任务，无须移除已阅状态");
		}

		taskService.removeVariable(taskInfo.getTaskId(),taskReadStatus);

		return new TaskBean(SUCCEED,"移除已阅标记成功");
	}
	/**
	 * 获取任务已阅状态
	 * @param taskInfo
	 * @return
	 */
	public TaskBean getSignTaskStatus(TaskInfo taskInfo){
		if (StringUtils.isBlank(taskInfo.getTaskId()) && StringUtils.isBlank(taskInfo.getProcessInstanceId())) {
			return new TaskBean(FAILED, "taskId不能为空 或 processInstanceId不能为空");
		}

		TaskQuery taskQuery = taskService.createTaskQuery();
		if (StringUtils.isNotBlank(taskInfo.getTaskId())){
			taskQuery.taskId(taskInfo.getTaskId());
		}
		if (StringUtils.isNotBlank(taskInfo.getProcessInstanceId())){
			taskQuery.processInstanceId(taskInfo.getProcessInstanceId());
		}

		List<Task> list = taskQuery.list();
		String taskReadStatus = "taskReadStatus";
		List taskStatus = new ArrayList();

		if (CollectionUtils.isNotEmpty(list)) {
			for (Task task : list) {
				String statusValue = (String)taskService.getVariableLocal(task.getId(), taskReadStatus);
				Map<String, String> details = new HashMap<>();
				details.put("taskId", task.getId());
				details.put("taskName", task.getName());
				details.put("taskReadStatus", StringUtils.isNotBlank(statusValue) ? statusValue : "未阅");
				taskStatus.add(details);
			}
		}

		TaskBean result = new TaskBean(SUCCEED, "查询成功");
		result.setTaskList(taskStatus);
		return result;
	}

    /**
     * 意见结论 opinion
     * 操作类型 option
     * 角色编号  roleCode
     * 角色名称  roleName
     * 部门编号  deptCode
     * 部门名称  deptName
     * 机构编号  orgCode
     * 机构名称  orgName
     *
     * @param taskLocalVariables
     * @param task
     * @return
     */
    private Map getFromVariables(List<HistoricVariableInstance> taskLocalVariables, HistoricTaskInstance task) {
		HashedMap info = new HashedMap();
		if (taskLocalVariables == null) {
			return info;
		}

		for (HistoricVariableInstance variable : taskLocalVariables) {
			if (task.getId().equals(variable.getTaskId())) {
				info.put(variable.getName(),variable.getValue());
				if (variable.getName().equals(SequentialAddedTaskState.TASK_TYPE_KEY)) {
					if(SequentialAddedTaskState.ADD_TASK.equals(variable.getValue())){
						info.put("taskType", "addTask");
					}else if(SequentialAddedTaskState.IGNORE_TASK.equals(variable.getValue())){
						info.put("taskType", "ignoreTask");
					} else {
						info.put("taskType", "normalTask");
					}
				}
			}

        }
        if (info.get("taskType") == null){
			info.put("taskType", "normalTask");
		}
        return info;
    }
    
    public ProcessBean updateBusinessKey(ProcessInfo processInfo){
    	ProcessBean processBean = new ProcessBean();
    	if (StringUtils.isBlank(processInfo.getBusinessKey()) 
    			|| StringUtils.isBlank(processInfo.getProcessInstanceId())) {
    		processBean.setRetCode(FAILED);
    		processBean.setRetMsg("实例id或业务主键不能为空");
    		return processBean;
		}
    	// camunda-TODO camunda没有该方法
//    	runtimeService.updateBusinessKey(processInfo.getProcessInstanceId(), processInfo.getBusinessKey());
    	processBean.setRetCode(SUCCEED);
		processBean.setRetMsg("修改成功");
		return processBean;
    }

    /**
     * 删除完成的流程
     * @param processInstanceIds
     * @return
     */
    @Transactional
    public String completeProcessDelete(String[] processInstanceIds) {
        String str="";
        try {
            for(String processInstanceId:processInstanceIds){
                HiTaskExpandDataEntity hiTaskExpandDataEntity = new HiTaskExpandDataEntity();
                hiTaskExpandDataEntity.setProcessInstanceId(processInstanceId);
                workflowHiTaskExpandDataService.delete(hiTaskExpandDataEntity);
                historyService.deleteHistoricProcessInstance(processInstanceId);
            }
            str="0";
        } catch (Exception e) {
            str=e.getMessage();
        }
        return str;
    }

	/**
	 * 删除整个流程实例，包括待办已办审批历史等所有数据
	 * @param info
	 * @return
	 */
	public ProcessBean deleteProcessInstance(ProcessInfo info) {
		final String processInstanceId = info.getProcessInstanceId();
		if (StringUtils.isBlank(processInstanceId)) {
			return new ProcessBean(FAILED, "删除失败，流程实例id为空");
		}
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if (historicProcessInstance == null) {
			return new ProcessBean(FAILED, "删除失败，流程实例不存在或者流程已结束");
		}
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				List<String> processInstanceIds = new ArrayList<>();
				processInstanceIds.add(processInstanceId);
				commandContext.getHistoricProcessInstanceManager().deleteHistoricProcessInstanceByIds(processInstanceIds);
				List<TaskEntity> tasksByProcessInstanceId = commandContext.getTaskManager().findTasksByProcessInstanceId(processInstanceId);
				for (TaskEntity taskEntity:tasksByProcessInstanceId){
					commandContext.getTaskManager().deleteTasksByProcessInstanceId(processInstanceId,info.getTerminateReason(),true,true);
					//删除扩展字段的数据
					HiTaskExpandDataEntity hiTaskExpandDataEntity=new HiTaskExpandDataEntity();
					hiTaskExpandDataEntity.setProcessInstanceId(processInstanceId);
					hiTaskExpandDataEntity.setTaskId(taskEntity.getId());
					workflowHiTaskExpandDataService.delete(hiTaskExpandDataEntity);
				}
				return null;
			}
		});

		return new ProcessBean(SUCCEED,"删除成功!");
	}

    /**
     * 把assignee翻译成assigneeName，把preUser翻译成preUserName
     * @param hiTasks
     */
	public void addUserName(List<Map> hiTasks){
		Set<String> userIds = TransformUtil.getValues(hiTasks, new String[]{"assignee", "preUser"});
		Map<String,Object> cnNames = workflowUserService.transferUserName(userIds);
		TransformUtil.transfromString(hiTasks,cnNames,"assignee","assigneeName");
		TransformUtil.transfromString(hiTasks,cnNames,"preUser","preUserName");
	}

	/**
	 * 把流程中的扩展数据加到流程轨迹中
	 * @param hiTasks 历史轨迹
	 * @param taskExpands 待办扩展和已办扩展
	 */
	private void addExpandsPlanField(List<Map> hiTasks, List<Map<String, Object>> taskExpands) {
		for (Map hiTask : hiTasks) {
			for (Map<String, Object> taskExpand : taskExpands) {
				taskExpand.remove("startTime");
				if(hiTask.get("taskId") !=null && hiTask.get("taskId").equals(taskExpand.get("taskId"))){
					hiTask.putAll(taskExpand);
					break;
				}
			}
		}
	}

	public void updateSuperProcessInstanceId(final String procInstId, final String superProcessInstanceId) {
		if (StringUtils.isNotBlank(superProcessInstanceId) && StringUtils.isNotBlank(procInstId)) {
			ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
			CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
			executor.execute(new Command<Void>() {
				@Override
				public Void execute(CommandContext commandContext) {
					Map map = new HashMap();
					map.put("superProcessInstanceId", superProcessInstanceId);
					map.put("procInstId", procInstId);
					commandContext.getDbSqlSession().getSqlSession().update("updateSuperProcessInstanceId", map);
					return null;
				}
			});

		}
	}
	
	
	public  void  messageConfirmProcessInstanceId(String procInstId,String messageName) {
		MessageCorrelationBuilder  messageCorrelation =	this.runtimeService.createMessageCorrelation(messageName);
		messageCorrelation.processInstanceId(procInstId);
		messageCorrelation.correlate();		
	}

	@Transactional
	public int handoverLoadItem(String transferor, String receiver){
		List<Task> list = taskService.createTaskQuery()
				.taskAssignee(transferor).list();
		list.forEach( t -> {
			taskService.setAssignee(t.getId(), receiver);
		});
		return list.size();
	}

	@Transactional
	public int handoverLoadReceiveItem(String transferor, String receiver) {
		List<Task> list = taskService.createTaskQuery()
				.taskCandidateUser(transferor).list();
		list.forEach( t -> {
			taskService.deleteCandidateUser(t.getId(), transferor);
			taskService.addCandidateUser(t.getId(), receiver);
		});
		return list.size();
	}

}

package com.tansun.wf.decision.handler;


import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.decision.model.BaseVo;
import com.tansun.wf.decision.service.ExternalService;
import com.tansun.workflow.model.ActExpandTracelog;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("taskCreateServiceHandler")
@Transactional(rollbackFor=Exception.class)
public class TaskCreateServiceHandler{
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	@Autowired
	private ExternalService externalService;
	public BaseVo createTask(DelegateTask task, String createTask, ActExpandTracelog actExpandTracelog) throws Exception {
		DelegateExecution execution = task.getExecution();
		String processInstanceId = execution.getProcessInstanceId();
		// camunda-TODO
		TaskService taskService = execution.getProcessEngineServices().getTaskService();
		Map<String,Object> taskMap = new HashMap<String,Object>(); 
		taskMap.put("processInstanceId", processInstanceId);
		taskMap.put("bussessId", task.getExecution().getProcessBusinessKey());
		taskMap.put("taskId", task.getId());
		taskMap.put("taskName", task.getName());
		taskMap.put("taskKey", task.getTaskDefinitionKey());
		taskMap.put("assignee", task.getAssignee());
		//候选组id
		String candidateGroupIds = "";
		//获选人id
		String candidateUserIds = "";
		List<IdentityLink> links = taskService.getIdentityLinksForTask(task.getId());
		for (IdentityLink identityLink : links) {
			if (identityLink.getType().equals(IdentityLinkType.CANDIDATE)) {
				if(null != identityLink.getGroupId()){
					candidateGroupIds = candidateGroupIds + identityLink.getGroupId() + ",";
				}else if(null != identityLink.getUserId()){
					candidateUserIds = candidateUserIds + identityLink.getUserId() + ",";
				}
			}
		}
		candidateGroupIds = candidateGroupIds.length()>0?candidateGroupIds.substring(0, candidateGroupIds.length()-1):"";
		candidateUserIds = candidateUserIds.length()>0?candidateUserIds.substring(0, candidateUserIds.length()-1):"";
		taskMap.put("roleId", candidateGroupIds);
		taskMap.put("candidateUserIds", candidateUserIds);
		return externalService.createTask(taskMap);
	}

}

package com.tansun.wf.platform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansun.wf.activiti.service.WorkflowService;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.enums.TrustType;
import com.tansun.wf.platform.mapper.WfAgentMapper;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.model.AgentTrust;
import com.tansun.wf.platform.model.AgentUser;
import com.tansun.wf.platform.model.WfAgentEntity;
import com.tansun.wf.platform.model.WfTreeNode;
import com.tansun.workflow.model.PostInfo;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;

@Service
public class WfAgentService extends WfBaseManager<WfAgentMapper, WfAgentEntity> {
	
	@Autowired
	private  WorkflowService  workflowService;
	
	@Autowired
	private  RepositoryService  repositoryService;
	

    @Autowired
    private WfActHiCommentService wfActHiCommentService;
	

	
	
	public  List<WfTreeNode>  findProcessTaskInfo(ProcessInfo processInfo) {
		List<ProcessDefinition>  processList = repositoryService.createProcessDefinitionQuery().tenantIdIn(processInfo.getTenantId()).latestVersion().list();
		List<WfTreeNode>  tree  = new  ArrayList<>();
		WfTreeNode  treeNode  = null;
		for (ProcessDefinition processDefinition :processList) {
			treeNode  = new  WfTreeNode(); 
			treeNode.setId(processDefinition.getId());
			treeNode.setPid("0");
			treeNode.setName(processDefinition.getName()+"-"+processDefinition.getVersion());
			tree.add(treeNode);
			TaskBean taskBean =this.workflowService.getTaskDefinitions(processDefinition.getId());
			List<Map>  list =  taskBean.getTaskDefinitionList();
			for (Map<String,Object> map :list) {
				treeNode  = new  WfTreeNode(); 
				treeNode.setPid(processDefinition.getId());
				treeNode.setId((String) map.get("taskKey"));
				treeNode.setName((String) map.get("taskName"));
				tree.add(treeNode);
			}
		}
		return  tree;
	}
	
	public  void  save(WfAgentEntity  agentEntity) {
		 this.insert(agentEntity);
	}
	
	public AgentUser   findAgentUser(TaskInfo  taskInfo){
		 AgentUser agentUser  = new  AgentUser(); 
		 Map<String,Object>  paramMap  =  new  HashMap<>();
		 paramMap.put("userId", taskInfo.getUserId());
		 paramMap.put("roleList", taskInfo.getRoleList());
		 paramMap.put("orgList", taskInfo.getOrgList());
		 paramMap.put("tenantId",taskInfo.getTenantId());
		 List<Map<String,Object>> list = super.mapper.findTrustProcess(paramMap);
		 Map<String,String>  userMap  = new HashMap<>();
		 Map<String,String>  roleMap  = new HashMap<>();
		 Map<String,String>  orgMap  = new  HashMap<>();
         this.makeUser(list, userMap, roleMap, orgMap);
         List<Map<String,Object>> taskList  =  super.mapper.findTrustProcessTask(paramMap);
         this.makeUser(taskList, userMap, roleMap, orgMap);
         List<Map<String,Object>>  aList = super.mapper.findTrust(paramMap);
         this.makeUser(aList, userMap, roleMap, orgMap);
         agentUser.setUserList(new ArrayList<>(userMap.values()));
         agentUser.setOrgList(new ArrayList<>(orgMap.values()));
         agentUser.setRoleList(new ArrayList<>(roleMap.values()));
         agentUser.setUserId(taskInfo.getUserId());
         return  agentUser;
	}
	
	private  void  makeUser (List<Map<String,Object>> list,Map<String,String>  userMap,Map<String,String>  roleMap,Map<String,String>  orgMap) {
		 for (Map<String,Object> map : list) {
			String type =(String) map.get("type");
			String  trustUser = (String)map.get("TRUST_USER");
			if ("user".equals(type)) {
				
				userMap.put(trustUser, trustUser);
			}else if ("role".equals(type)) {
	
				roleMap.put(trustUser, trustUser);
			}else if ("org".equals(type)) {
				orgMap.put(trustUser, trustUser);
			}
			
		 }
	}
	/**
	 * 根据租户和代理人查找有效的代理列表
	 * @param taskInfo
	 * @return
	 */
	public  List<WfAgentEntity>   findEffectAgent(TaskInfo taskInfo){
		  Map<String,Object> paramMap  = new  HashMap<>();
		  paramMap.put("tenantId", taskInfo.getTenantId());
		  paramMap.put("trustType", TrustType.AGENT.val);
		  List<String>  users = new  ArrayList<>();
		  if (taskInfo.getUserId() !=null) {
		     users.add(taskInfo.getUserId());
		  }
		  if (taskInfo.getOrgList() !=null) {
			  users.addAll(taskInfo.getOrgList());  
		  }
		  if (taskInfo.getRoleList() !=null) {
			  users.addAll(taskInfo.getRoleList()); 
		  }		 
		  paramMap.put("users", users);
		  return  super.mapper.findEffectAgent(paramMap);
	}
	
	public  List<WfAgentEntity>   findEffectAgent(Map<String,Object> paramMap){
		return  super.mapper.findEffectAgent(paramMap);
	}
	
	/**
	 * 根据代理信息查找委托信息
	 * @param taskInfo
	 * @return
	 */
	public  AgentTrust  findEffectTrustByAgent(TaskInfo taskInfo){
		List<WfAgentEntity>  list =  this.findEffectAgent(taskInfo);
		List<WfAgentEntity>  users  = new ArrayList<>();
		List<WfAgentEntity>  roleList  = new ArrayList<>();
		List<WfAgentEntity>  orgList  = new ArrayList<>();
		for (WfAgentEntity  wfAgent :list) {
			if ("user".equals(wfAgent.getType())) {
				users.add(wfAgent);
			}else if ("role".equals(wfAgent.getType())) {
				roleList.add(wfAgent);
			}else if ("org".equals(wfAgent.getType())) {
				orgList.add(wfAgent);
			}
		}
		AgentTrust  res  = new  AgentTrust();
		res.setUsers(users);
		res.setOrgList(orgList);
		res.setRoleList(roleList);

		return  res;
	}
	
	
	public  void   saveAgentExe(DelegateTask  task , PostInfo postInfo) {
		  Map<String,Object>  paraMap  = new  HashMap<>();
		  if (postInfo != null) {
			  if ("user".equals(postInfo.getType())) {
				  paraMap.put("trustUser", postInfo.getUserId());
			  }else if ("role".equals(postInfo.getType())) {
				  paraMap.put("trustUser", postInfo.getRoleCode()); 
			  }else if ("org".equals(postInfo.getType())) {
				  paraMap.put("trustUser", postInfo.getOrgCode());  
			  }
		  }
		  paraMap.put("processDefId", task.getProcessDefinitionId());
		  paraMap.put("taskDefKey", task.getTaskDefinitionKey());
		  paraMap.put("tenantId", task.getTenantId());
		  
		  List<WfAgentEntity>  list = super.mapper.findAgent(paraMap);		  
		  ActRuIdentitylinkEntity  linkEntity = null;
		  List<ActRuIdentitylinkEntity> linkList = new  ArrayList<>();
		  for (WfAgentEntity agent :list) {
			  linkEntity = new ActRuIdentitylinkEntity();
			  if ("user".equals(postInfo.getType())) {
				  linkEntity.setUserId(agent.getAgentUser());  
			  }else if ("role".equals(postInfo.getType())) {
				  linkEntity.setRoleCode(agent.getAgentUser());
			  }else if ("org".equals(postInfo.getType())) {
				  linkEntity.setOrgCode(agent.getAgentUser()); 
			  }  
			  linkEntity.setTaskDefKey(task.getTaskDefinitionKey());
			  linkEntity.setProcDefId(task.getProcessDefinitionId());
			  linkEntity.setProcInstId(task.getProcessInstanceId());
			  linkEntity.setTaskId(task.getId());
			  linkEntity.setTenantId(task.getTenantId());
			  linkEntity.setType(postInfo.getType());
			  linkEntity.setDueDate(agent.getEndDate());
			  linkList.add(linkEntity);
		  }
		  this.wfActHiCommentService.insertRuIdentityLink(linkList);
	}
	
	public  void   saveAgentExe(List<ActRuIdentitylinkEntity> linkList) {
		List<ActRuIdentitylinkEntity>  upList  = new  ArrayList<>();
		List<WfAgentEntity>   agentList  = new ArrayList<>();
		if (linkList != null) {
			for (ActRuIdentitylinkEntity linkEntity : linkList) {
				  Map<String,Object>  paraMap  = new  HashMap<>();
				  if (linkEntity != null) {
					  if ("user".equals(linkEntity.getType())) {
						  paraMap.put("trustUser", linkEntity.getUserId());
					  }else if ("role".equals(linkEntity.getType())) {
						  paraMap.put("trustUser", linkEntity.getRoleCode()); 
					  }else if ("org".equals(linkEntity.getType())) {
						  paraMap.put("trustUser", linkEntity.getOrgCode());  
					  }else { // post 暂时不做代理
						  continue;
					  }
				  }
				  paraMap.put("tenantId", linkEntity.getTenantId());
				  
				  List<WfAgentEntity>  list = super.mapper.findAgent(paraMap);
				  WfAgentEntity  resAgent = null;
				  for  (WfAgentEntity  agentEntity : list) {					  
					  if (!StringUtils.isEmpty(agentEntity.getProcessDefId())
						 && !agentEntity.getProcessDefId().equals(linkEntity.getProcDefId())	  ) {
                         continue;
					  }
					  if (!StringUtils.isEmpty(agentEntity.getTaskDefKey()) && !agentEntity.getTaskDefKey().equals(linkEntity.getTaskDefKey())) {
						 continue; 
					  }
					  resAgent = agentEntity;					 
				  }	  
				  if (resAgent == null) {
					  continue;
				  }
				  agentList.add(resAgent);
				  if ("user".equals(resAgent.getType())) {
					   linkEntity.setUserId(resAgent.getAgentUser());
				  }else if ("role".equals(linkEntity.getType())) {
					   linkEntity.setRoleCode(resAgent.getAgentUser());
				  }else if ("org".equals(linkEntity.getType())) {
					   linkEntity.setOrgCode(resAgent.getAgentUser());
				  }
				  linkEntity.setDueDate(resAgent.getEndDate());
				  upList.add(linkEntity);				  
			}
		}
		if (upList.size()>0) {
			this.wfActHiCommentService.insertRuIdentityLink(upList);
		}
		if (agentList.size()>0) {
			for (WfAgentEntity agent :agentList) {
				if (agent.getEffectInd() ==null) {
					agent.setEffectInd("1");
					mapper.update(agent);
				}
			}
		}
		
	}

}

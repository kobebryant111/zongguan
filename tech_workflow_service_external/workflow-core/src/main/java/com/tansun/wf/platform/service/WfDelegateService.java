package com.tansun.wf.platform.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.enums.TrustType;
import com.tansun.wf.platform.mapper.WfActHiCommentMapper;
import com.tansun.wf.platform.mapper.WfDelegateMapper;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.model.WfAgentEntity;
import com.tansun.wf.platform.model.WfDelegateEntity;
import com.tansun.wf.platform.query.WfDelegateQuery;


@Service
public class WfDelegateService extends WfBaseManager<WfDelegateMapper, WfDelegateEntity> {

	  @Autowired
	  private  WfAgentService   wfAgentService;
	  
	  @Autowired
	  private  TaskService  taskService;
	  
	  @Autowired
	  private  WfActHiCommentMapper  wfActHiCommentMapper;	  
	  
	  /**
	     *  将任务代办执行,返回代办列表
	   * @param delegateTask
	   * @param trustUser
	   */
	  public  List<String>   delegateExecute(DelegateTask delegateTask,String  trustUser ) {
		    Map<String,Object>  paramMap  = new  HashMap<>();
		    paramMap.put("tenantId", delegateTask.getTenantId());
		    paramMap.put("trustUser",trustUser);
		    paramMap.put("trustType", TrustType.REPLACE.val);
		    List<WfAgentEntity> list = wfAgentService.findEffectAgent(paramMap);
		    List<String>  resList  = new  ArrayList<>();
		    WfDelegateEntity   delegateEntity  = null;
		    for (WfAgentEntity  agentEntity :list) {
		    	delegateEntity = new  WfDelegateEntity();
		    	delegateEntity.setTrustUser(trustUser);
		    	delegateEntity.setAgentUser(agentEntity.getAgentUser());
		    	delegateEntity.setDelegateTime(new Date());
		    	delegateEntity.setProcessDefId(delegateTask.getProcessDefinitionId());
		    	delegateEntity.setTaskDefKey(delegateTask.getTaskDefinitionKey());
		    	delegateEntity.setTaskId(delegateTask.getId());
		    	this.insert(delegateEntity);
		    	resList.add(agentEntity.getAgentUser());
		    }
		    return  resList;		  
	  }
	  
	  public  void   saveDelegate(WfAgentEntity agentEntity ) {
		  ActRuIdentityLinkQuery actRuIdentityLinkQuery = new ActRuIdentityLinkQuery();
		  actRuIdentityLinkQuery.setType("user");
		  actRuIdentityLinkQuery.setUserId(agentEntity.getTrustUser());
		  actRuIdentityLinkQuery.setTenantId(agentEntity.getTenantId());
		  String tenantId = agentEntity.getTenantId();
		  List<ActRuIdentitylinkEntity> linkList = this.wfActHiCommentMapper.findList(actRuIdentityLinkQuery);
		  WfDelegateEntity   delegateEntity  = null;
		  for (ActRuIdentitylinkEntity idLink:linkList) {
			    if (idLink.getDueDate() != null) {
				  if (idLink.getDueDate().compareTo(new Date())<0) {
					  continue;
				  }
			    }
			    idLink.setUserId(agentEntity.getAgentUser());
		    	delegateEntity = new  WfDelegateEntity();
		    	delegateEntity.setTrustUser(agentEntity.getTrustUser());
		    	delegateEntity.setAgentUser(agentEntity.getAgentUser());
		    	delegateEntity.setDelegateTime(new Date());
		    	delegateEntity.setProcessDefId(idLink.getProcDefId());
		    	delegateEntity.setTaskDefKey(idLink.getTaskDefKey());
		    	delegateEntity.setTaskId(idLink.getTaskId());
		    	delegateEntity.setTenantId(agentEntity.getTenantId());
		    	this.insert(delegateEntity);
		    	this.wfActHiCommentMapper.update(idLink);
		  }
		  List<Task> taskList = this.taskService.createTaskQuery().tenantIdIn(tenantId).taskAssignee(agentEntity.getTrustUser()).list();
		  for  (Task  task :taskList) {
			    this.taskService.claim(task.getId(), null);
			    this.taskService.claim(task.getId(), agentEntity.getAgentUser());
		        delegateEntity = new  WfDelegateEntity();
		    	delegateEntity.setTrustUser(agentEntity.getTrustUser());
		    	delegateEntity.setAgentUser(agentEntity.getAgentUser());
		    	delegateEntity.setDelegateTime(new Date());
		    	delegateEntity.setProcessDefId(task.getProcessDefinitionId());
		    	delegateEntity.setTaskDefKey(task.getTaskDefinitionKey());
		    	delegateEntity.setTenantId(tenantId);
		    	delegateEntity.setTaskId(task.getId());
		    	this.insert(delegateEntity);
		  }
		  this.wfAgentService.save(agentEntity);
	  }
	  
	  public  PageInfo<List<Map<String,Object>>>  findDelegateInfo( WfDelegateQuery query,PageInfo pageInfo){
	        pageInfo = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true).doSelectPageInfo(new ISelect() {
	            @Override
	            public void doSelect() {
	            	  mapper.findDelegateList(query);
	            }
	        });
	        return pageInfo;		  
	  }
	  
	  public  void  taskBack(List<String>  ids) {
		 if (ids != null) {
			 WfDelegateEntity  delegateEntity = null;
			 ActRuIdentityLinkQuery linkEntity = null;
			 List<ActRuIdentitylinkEntity> linkList = null;
			 for (String  id  :  ids) {
				  delegateEntity= mapper.selectById(id);
				  linkEntity = new ActRuIdentityLinkQuery();
				  linkEntity.setType("user");
				  linkEntity.setUserId(delegateEntity.getAgentUser());
				  linkEntity.setTenantId(delegateEntity.getTenantId());
		          linkEntity.setTaskId(delegateEntity.getTaskId());
				  linkList = this.wfActHiCommentMapper.findList(linkEntity);
				  for (ActRuIdentitylinkEntity idLink : linkList) {
					  idLink.setUserId(delegateEntity.getTrustUser());
					  this.wfActHiCommentMapper.update(idLink);
				  }
				  delegateEntity.setStatus("2");
				  delegateEntity.setDelegateTime(new Date());
				  mapper.update(delegateEntity);
				  final  String  trustUser = delegateEntity.getTrustUser();
				  List<Task> taskList =this.taskService.createTaskQuery().tenantIdIn(delegateEntity.getTenantId())
				  .taskId(delegateEntity.getTaskId()).taskAssignee(delegateEntity.getAgentUser()).list();
				  taskList.forEach(task->{
					  this.taskService.claim(task.getId(), null);
					  this.taskService.claim(task.getId(),trustUser );
				  });
			 }
		 }
	  }
}

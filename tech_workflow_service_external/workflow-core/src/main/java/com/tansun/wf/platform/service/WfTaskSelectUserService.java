package com.tansun.wf.platform.service;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.activiti.service.WorkflowTaskService;
import com.tansun.wf.activiti.util.ParticipantUtil;
import com.tansun.wf.camunda.bpmn.instance.Participants;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.workflow.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * 岗位模式选择人服务
 *
 */
@Service
public class WfTaskSelectUserService {

	@Value("${user.check:false}")
	private boolean userCheck;

	@Autowired
	private  WfActHiCommentService  actCommentService;

	@Autowired
	private  TaskService taskService;
	


	@Autowired
	private WorkflowTaskService workflowTaskService;

	@Autowired
	private EditorServiceProxyService editorServiceProxyService;
	
	@Autowired
	private  WfAgentService  wfAgentService;


	/**
	 * 校验用户是否拥有该任务的权限
	 * @return
	 */
	public TaskBean userCheck(String taskId, String userId, String tenantId){
		if(userCheck){
			EditorInfo editorInfo = new EditorInfo();
			editorInfo.setUserId(userId);
			editorInfo.setTenantId(tenantId);
			EditorBean editorBean = editorServiceProxyService.findAllPostInfo(editorInfo);
			List<PostInfo> postInfos = editorBean.getPostInfos();
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setTenantId(tenantId);
			taskInfo.setTaskId(taskId);
			taskInfo.setPostInfos(postInfos);
			TaskBean taskBean = workflowTaskService.findPendOrgTaskList(taskInfo);
			if(CollectionUtils.isEmpty(taskBean.getTaskList())){
				return new TaskBean(WorkflowConstants.FAILED,"对不起，您没有权限");
			}
		}
		return new TaskBean(WorkflowConstants.SUCCEED,"权限校验成功");
	}

	/**
	 * 根据nextPost保存参与人信息
	 */
	public void saveUserTaskAssignment(DelegateTask delegateTask,PostInfo postInfo) {
		List<PostInfo> nextPost = new ArrayList<>();
		nextPost.add(postInfo);
		saveUserTaskAssignment(delegateTask,nextPost);
	}

	/**
	 * 根据nextPost保存参与人信息
	 */
	public void saveUserTaskAssignment(String taskId, String tenantId,PostInfo postInfo) {
		List<PostInfo> nextPost = new ArrayList<>();
		nextPost.add(postInfo);
		saveUserTaskAssignment(taskId,tenantId,nextPost);
	}

	public void saveUserTaskAssignment(DelegateTask delegateTask, List<PostInfo> nextPost) {
		Set<String> userIds = new HashSet<>();
		for (PostInfo postInfo : nextPost) {
			if("user".equals(postInfo.getType())){
				userIds.add(postInfo.getUserId());
				continue;
			}
			EditorInfo editorInfo = new EditorInfo();
			editorInfo.setType(postInfo.getType());
			editorInfo.setTenantId(delegateTask.getTenantId());
			if("role".equals(postInfo.getType()) && StringUtils.isNotBlank(postInfo.getRoleCode())){
				List<String> roleCodes = new ArrayList<>();
				roleCodes.add(postInfo.getRoleCode());
				editorInfo.setRoleCodes(roleCodes);
			}

			if("org".equals(postInfo.getType()) && StringUtils.isNotBlank(postInfo.getOrgCode())){
				List<String> orgCodes = new ArrayList<>();
				orgCodes.add(postInfo.getOrgCode());
				editorInfo.setOrgCodes(orgCodes);
			}

			if("dept".equals(postInfo.getType()) && StringUtils.isNotBlank(postInfo.getDeptCode())){
				List<String> deptCodes = new ArrayList<>();
				deptCodes.add(postInfo.getDeptCode());
				editorInfo.setDeptCodes(deptCodes);
			}

			if("post".equals(postInfo.getType())){
				List<String> roleCodes = new ArrayList<>();
				roleCodes.add(postInfo.getRoleCode());
				editorInfo.setRoleCodes(roleCodes);

				if(StringUtils.isNotBlank(postInfo.getOrgCode())){
					List<String> orgCodes = new ArrayList<>();
					orgCodes.add(postInfo.getOrgCode());
					editorInfo.setOrgCodes(orgCodes);
				}

				if(StringUtils.isNotBlank(postInfo.getDeptCode())){
					List<String> deptCodes = new ArrayList<>();
					deptCodes.add(postInfo.getDeptCode());
					editorInfo.setDeptCodes(deptCodes);
				}
			}
			if (StringUtils.isNotBlank(postInfo.getType()) && !"user".equals(postInfo.getType())) {
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
		// 自动抢单
		if(userIds.size()==1){
			saveUserTaskAssignment(delegateTask,userIds.iterator().next());
			return;
		}

		List<ActRuIdentitylinkEntity> linkList = new ArrayList<>();
		for (PostInfo postInfo : nextPost) {
			ActRuIdentitylinkEntity identityEntity = new ActRuIdentitylinkEntity();
		    identityEntity.setUserId(postInfo.getUserId());	
			identityEntity.setUserId(postInfo.getUserId());
			identityEntity.setOrgCode(postInfo.getOrgCode());
			identityEntity.setRoleCode(postInfo.getRoleCode());
			identityEntity.setDeptCode(postInfo.getDeptCode());
			identityEntity.setGroupId(postInfo.getGroupId());
			identityEntity.setTenantId(delegateTask.getTenantId());
			identityEntity.setTaskId(delegateTask.getId());
			identityEntity.setProcDefId(delegateTask.getProcessDefinitionId());
			identityEntity.setTaskDefKey(delegateTask.getTaskDefinitionKey());
			identityEntity.setType(postInfo.getType());
			linkList.add(identityEntity);
		}
		
		actCommentService.insertRuIdentityLink(linkList);
		wfAgentService.saveAgentExe(linkList);
	}
	/**
	 * 根据nextPost保存参与人信息
	 */
	public void saveUserTaskAssignment(String taskId, String tenantId, List<PostInfo> nextPost) {
		List<ActRuIdentitylinkEntity> linkList = new ArrayList<>();
		Task task  = null;
		if (taskId != null) {
			   task =	taskService.createTaskQuery().taskId(taskId).singleResult();
		}
		for (PostInfo postInfo : nextPost) {
			ActRuIdentitylinkEntity identityEntity = new ActRuIdentitylinkEntity();
			identityEntity.setUserId(postInfo.getUserId());
			identityEntity.setOrgCode(postInfo.getOrgCode());
			identityEntity.setRoleCode(postInfo.getRoleCode());
			identityEntity.setDeptCode(postInfo.getDeptCode());
			identityEntity.setGroupId(postInfo.getGroupId());
			if (task != null) {
				identityEntity.setProcDefId(task.getProcessDefinitionId());
				identityEntity.setTaskDefKey(task.getTaskDefinitionKey());
			}
			identityEntity.setTenantId(tenantId);
			identityEntity.setTaskId(taskId);
			identityEntity.setType(postInfo.getType());
			linkList.add(identityEntity);
		}
		actCommentService.insertRuIdentityLink(linkList);
	}
	

	

	/**
	 * 保存参与人
	 * @param delegateTask
	 * @param userId
	 */
	public void saveUserTaskAssignment(DelegateTask delegateTask,String... userId) {
		List<ActRuIdentitylinkEntity> linkList = new ArrayList<>();
		for (String id : userId) {
			ActRuIdentitylinkEntity identityEntity = new ActRuIdentitylinkEntity();
			identityEntity.setUserId(id);
			identityEntity.setTenantId(delegateTask.getTenantId());
			identityEntity.setTaskId(delegateTask.getId());
			identityEntity.setTaskDefKey(delegateTask.getTaskDefinitionKey());
			identityEntity.setProcDefId(delegateTask.getProcessDefinitionId());
			identityEntity.setType("user");
			linkList.add(identityEntity);
		}
		// 自动抢单
		if (userId.length==1){
			delegateTask.setAssignee(userId[0]);
		}
		actCommentService.insertRuIdentityLink(linkList);
		wfAgentService.saveAgentExe(linkList);
	}
	/**
	 * 保存参与人
	 * @param
	 * @param userId
	 */
	public void saveUserTaskAssignment(String taskId,String tenantId,String... userId) {
		List<ActRuIdentitylinkEntity> linkList = new ArrayList<>();
		Task task  = null;
		if (taskId != null) {
			   task =	taskService.createTaskQuery().taskId(taskId).singleResult();
		}
		for (String id : userId) {
			ActRuIdentitylinkEntity identityEntity = new ActRuIdentitylinkEntity();
			identityEntity.setUserId(id);
			identityEntity.setTenantId(tenantId);
			identityEntity.setTaskId(taskId);
			if (task != null) {
				identityEntity.setProcDefId(task.getProcessDefinitionId());
				identityEntity.setTaskDefKey(task.getTaskDefinitionKey());
			}
			identityEntity.setType("user");
			linkList.add(identityEntity);
		}
		actCommentService.insertRuIdentityLink(linkList);
	}

	/**
	 * 删除参与人
	 */
	public void deleteUserTaskAssignment(String taskId){
		actCommentService.deleteRuIdentityLink(taskId);
	}
	/**
	 * 根据参与者列表保存参与人
	 * @param delegateTask
	 * @param participants
	 */
	public List<ActRuIdentitylinkEntity> saveUserTaskAssignment(DelegateTask delegateTask, Collection<Participants> participants, Map<String, Object> variables) {
    	List<ActRuIdentitylinkEntity>  linkList  = new ArrayList<>();

    	ActRuIdentitylinkEntity identityEntity= null;
		for (Participants participant : participants) {
			identityEntity = new  ActRuIdentitylinkEntity();
			identityEntity.setType(participant.getType());
			identityEntity.setTaskDefKey(delegateTask.getTaskDefinitionKey());
			identityEntity.setProcDefId(delegateTask.getProcessDefinitionId());
			String type = participant.getType();
			switch (type){
				case Participants.TYPE_ORG:{
					// 机构
					// 判断是不是表达式
					identityEntity.setOrgCode(ParticipantUtil.getVariableValue(variables,participant.getId()));
					break;
				}
				case Participants.TYPE_DEPT:{
					// 部门
					// 判断是不是表达式
					identityEntity.setDeptCode(ParticipantUtil.getVariableValue(variables,participant.getId()));
					break;
				}
				case Participants.TYPE_ROLE:{
					// 角色
					identityEntity.setRoleCode(ParticipantUtil.getVariableValue(variables,participant.getId()));
					break;
				}
				case Participants.TYPE_USER:{
					// 个人
					identityEntity.setUserId(ParticipantUtil.getVariableValue(variables,participant.getId()));
					break;
				}
				case Participants.TYPE_POST:{
					// 岗位（角色+机构+部门）
					String expression = participant.getId();
					String roleCode = expression.substring(expression.indexOf("@{")+2,expression.indexOf("}{"));
					String orgCode = expression.substring(expression.indexOf("}{")+2,expression.lastIndexOf("}{"));
					String deptCode = expression.substring(expression.lastIndexOf("}{")+2,expression.length()-1);
					identityEntity.setRoleCode(ParticipantUtil.getVariableValue(variables, roleCode));
					identityEntity.setOrgCode(ParticipantUtil.getVariableValue(variables, orgCode));
					identityEntity.setDeptCode(ParticipantUtil.getVariableValue(variables, deptCode));
					break;
				}
				default:{
					// 其他组
					identityEntity.setGroupId(ParticipantUtil.getVariableValue(variables,participant.getId()));
					break;
				}
			}
			identityEntity.setTenantId(delegateTask.getTenantId());
			identityEntity.setTaskId(delegateTask.getId());
			linkList.add(identityEntity);
		}

		// 抢单
		Set<String> userIds = new HashSet<>();
		if(CollectionUtils.isNotEmpty(linkList)) {
			for (ActRuIdentitylinkEntity identityLinkEntity : linkList) {
				EditorInfo editorInfo = new EditorInfo();
				editorInfo.setGroupId(identityLinkEntity.getGroupId());
				editorInfo.setType(identityLinkEntity.getType());

				if ("user".equals(identityLinkEntity.getType())) {
					userIds.add(identityLinkEntity.getUserId());
				}

				if ("role".equals(identityLinkEntity.getType()) && StringUtils.isNotBlank(identityLinkEntity.getRoleCode())) {
					List<String> roleCodes = new ArrayList<>();
					roleCodes.add(identityLinkEntity.getRoleCode());
					editorInfo.setRoleCodes(roleCodes);
				}

				if ("org".equals(identityLinkEntity.getType()) && StringUtils.isNotBlank(identityLinkEntity.getOrgCode())) {
					List<String> orgCodes = new ArrayList<>();
					orgCodes.add(identityLinkEntity.getOrgCode());
					editorInfo.setOrgCodes(orgCodes);
				}

				if ("dept".equals(identityLinkEntity.getType()) && StringUtils.isNotBlank(identityLinkEntity.getDeptCode())) {
					List<String> deptCodes = new ArrayList<>();
					deptCodes.add(identityLinkEntity.getDeptCode());
					editorInfo.setDeptCodes(deptCodes);
				}

				if ("post".equals(identityLinkEntity.getType())) {
					List<String> roleCodes = new ArrayList<>();
					roleCodes.add(identityLinkEntity.getRoleCode());
					editorInfo.setRoleCodes(roleCodes);

					if (StringUtils.isNotBlank(identityLinkEntity.getOrgCode())) {
						List<String> orgCodes = new ArrayList<>();
						orgCodes.add(identityLinkEntity.getOrgCode());
						editorInfo.setOrgCodes(orgCodes);
					}

					if (StringUtils.isNotBlank(identityLinkEntity.getDeptCode())) {
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

		if (userIds.size()==1){
			String userId = userIds.iterator().next();
			saveUserTaskAssignment(delegateTask,userId);
		}else{
    		actCommentService.insertRuIdentityLink(linkList);
    		wfAgentService.saveAgentExe(linkList);
    	}
    	return linkList;

	 }




    public  void  reSetOrgCd(List<OrgRole> list ,String orgCd) {
		for (OrgRole orgRole: list) {
			orgRole.setOrgCd(orgCd);
		}
	}
		
	public  void  reSetRoleCd(List<OrgRole> list,String roleCd) {
		for (OrgRole orgRole: list) {
		  orgRole.setRoleCd(roleCd);
		}
	}
		
	public  void  reSetDeptCd(List<OrgRole> list,String deptCd) {
		for (OrgRole orgRole: list) {
			orgRole.setDeptCd(deptCd);
	    }
	}
	    
	    
	static  class   OrgRole{
    	private  String  orgCd;
    	  
    	private  String  roleCd;
    	  
    	private  String  deptCd;

		public String getOrgCd() {
			return orgCd;
		}

		public void setOrgCd(String orgCd) {
			this.orgCd = orgCd;
		}

		public String getRoleCd() {
			return roleCd;
		}

		public void setRoleCd(String roleCd) {
			this.roleCd = roleCd;
		}

		public String getDeptCd() {
			return deptCd;
		}

		public void setDeptCd(String deptCd) {
			this.deptCd = deptCd;
		}
	    	  
  }

}

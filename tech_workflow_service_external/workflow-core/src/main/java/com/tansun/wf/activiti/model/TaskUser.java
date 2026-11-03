package com.tansun.wf.activiti.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tansun.workflow.model.TaskBean;

/**
 * 任务的用户信息
 */
public class TaskUser {

	private String userId;

	private String orgId;

	private String roleId;

	public String getUserId() {
		return userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public TaskUser(){
		
	}
	
	public TaskUser(String userId) {
		super();
		this.userId = userId;
	}

	public TaskUser(String userId, String orgId, String roleId) {
		super();
		this.userId = userId;
		this.orgId = orgId;
		this.roleId = roleId;
	}

	public List<TaskUser> convertToTaskUsers(TaskBean taskBean) {
		return new ArrayList<TaskUser>();
	}
	
	public List<TaskUser> convertToTaskUsers(String userId,String orgId) {
		List<TaskUser> nextTaskUsers = new ArrayList<TaskUser>();
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orgId)) {
			TaskUser nextTaskUser = new TaskUser();
			nextTaskUser.setUserId(userId);
			nextTaskUser.setOrgId(orgId);
			nextTaskUsers.add(nextTaskUser);
		}
		return nextTaskUsers;
	}

	/**
	 * 将对象转换为map
	 */
	public Map<String, String> toMap() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("userId", this.userId);
		map.put("orgId", this.orgId);
		map.put("roleId", this.roleId);
		return map;
	}
}

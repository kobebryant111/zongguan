package com.tansun.workflow.model;

public class ExternalUserRole extends BaseBean {
	private String userId;       // userId
	private String roleId;		 // roleId
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
}

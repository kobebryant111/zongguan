package com.tansun.workflow.model;

import java.util.List;

public class ExternalTable extends BaseBean {
	private String retCode;
	private List<ExternalRole> roleList;
	private List<ExternalUser> userList;
	private List<ExternalUserRole> userRoleList;
	private List<ExternalRoleMenu> roleMenuList;
	public List<ExternalRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<ExternalRole> roleList) {
		this.roleList = roleList;
	}
	public List<ExternalUser> getUserList() {
		return userList;
	}
	public void setUserList(List<ExternalUser> userList) {
		this.userList = userList;
	}
	public List<ExternalUserRole> getUserRoleList() {
		return userRoleList;
	}
	public void setUserRoleList(List<ExternalUserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}
	public List<ExternalRoleMenu> getRoleMenuList() {
		return roleMenuList;
	}
	public void setRoleMenuList(List<ExternalRoleMenu> roleMenuList) {
		this.roleMenuList = roleMenuList;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
}

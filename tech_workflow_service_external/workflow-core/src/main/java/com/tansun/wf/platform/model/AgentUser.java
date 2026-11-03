package com.tansun.wf.platform.model;

import java.util.List;

public class AgentUser {
	
    private  String  userId;
	  /**
	   * 委托用户
	   */
    private  List<String> userList;
	  /**
	   * 委托机构
	   */
    private  List<String>  orgList;
	   /**
	    *委托角色
	    */
    private  List<String>  roleList;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getUserList() {
		return userList;
	}
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
	public List<String> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	  
	  

}

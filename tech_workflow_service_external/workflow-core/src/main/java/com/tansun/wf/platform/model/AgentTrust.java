package com.tansun.wf.platform.model;

import java.util.List;

public class AgentTrust {
	
	 private  List<WfAgentEntity> users;
	 
	 private  List<WfAgentEntity> roleList;
	 
	 private  List<WfAgentEntity>  orgList;

	public List<WfAgentEntity> getUsers() {
		return users;
	}

	public void setUsers(List<WfAgentEntity> users) {
		this.users = users;
	}

	public List<WfAgentEntity> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<WfAgentEntity> roleList) {
		this.roleList = roleList;
	}

	public List<WfAgentEntity> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<WfAgentEntity> orgList) {
		this.orgList = orgList;
	}
	 
	 

}

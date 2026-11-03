package com.tansun.wf.platform.query;

import com.tansun.wf.extend.db.WfBaseQuery;

public class WfDelegateQuery extends WfBaseQuery {
	
	private  String  taskId;
	private  String  trustUser;
	private  String  agentUser;
	private  String  tenantId;
	private  String  processDefId;
	private  String  taskDefKey;
	private  String  status;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTrustUser() {
		return trustUser;
	}
	public void setTrustUser(String trustUser) {
		this.trustUser = trustUser;
	}
	public String getAgentUser() {
		return agentUser;
	}
	public void setAgentUser(String agentUser) {
		this.agentUser = agentUser;
	}

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	

}

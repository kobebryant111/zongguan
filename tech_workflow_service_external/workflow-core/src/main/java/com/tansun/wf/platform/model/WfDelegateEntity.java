package com.tansun.wf.platform.model;

import java.util.Date;

import com.tansun.wf.extend.db.WfBaseEntity;

public class WfDelegateEntity extends WfBaseEntity {
	
	private  String  trustUser;
	
	private  String  agentUser;
	
	private  String  taskId;
	
	private  String  processDefId;
	
	private  String  taskDefKey;
	
	private  String   status;
	
	private  Date   delegateTime;
	
	private  String  tenantId;
	
	private String  taskName;
	
	private String  processName;

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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}



	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDelegateTime() {
		return delegateTime;
	}

	public void setDelegateTime(Date delegateTime) {
		this.delegateTime = delegateTime;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	

}

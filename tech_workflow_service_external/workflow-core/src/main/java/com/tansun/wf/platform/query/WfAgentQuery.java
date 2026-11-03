package com.tansun.wf.platform.query;

import java.util.Date;
import java.util.List;

import com.tansun.wf.extend.db.WfBaseQuery;

public class WfAgentQuery extends WfBaseQuery {
	private  String  processDefName;
	private  String  processDefId;
	private  String  taskDefKey;
	private  Date  startDate;
	private  Date   endDate;
	private  String  agentUser;
	private  String  trustUser;
	private  String  type;
	private  String  remarks;
	private  String  effectInd;
	private  String  tenantId;
	private  String  createBy;
	private  String trustType;
	
	private  List<String>  trustTypes;
	
	public String getProcessDefName() {
		return processDefName;
	}
	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAgentUser() {
		return agentUser;
	}
	public void setAgentUser(String agentUser) {
		this.agentUser = agentUser;
	}
	public String getTrustUser() {
		return trustUser;
	}
	public void setTrustUser(String trustUser) {
		this.trustUser = trustUser;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getEffectInd() {
		return effectInd;
	}
	public void setEffectInd(String effectInd) {
		this.effectInd = effectInd;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public List<String> getTrustTypes() {
		return trustTypes;
	}
	public void setTrustTypes(List<String> trustTypes) {
		this.trustTypes = trustTypes;
	}
	
	
	
	
	
	
}

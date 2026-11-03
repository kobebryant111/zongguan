package com.tansun.wf.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.camunda.bpm.engine.impl.db.DbEntity;

/**
 * 任务池操作历史记录
 */
@SuppressWarnings({"serial"})
public class HiTaskPoolExecuteDataEntity implements Serializable ,DbEntity {

	private String id;
	private String taskId;		
	private String sysId;		
	private String userId;		
	private String isAllot;
	private String tenantId;		
	private Date createDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsAllot() {
		return isAllot;
	}
	public void setIsAllot(String isAllot) {
		this.isAllot = isAllot;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public Object getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}		
	
}

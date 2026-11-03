package com.tansun.workflow.model;

import java.util.Date;

/**
 * 任务池操作历史表
 * @author cyn
 *
 */
public class HiTaskPoolExecuteData {
	private String id;
	private String taskId;
//	@ApiModelProperty("系统id")
	private String sysId;		
	private String userId;		
	private String isAllot;
	private String tenantId;		
	private Date createDate;
	private String date;
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}

package com.tansun.wf.activiti.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务跟踪信息
 * 
 * @author admin
 *
 */
public class TaskTraceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5533513988131957517L;

	private String taskId;

	private String taskName;

	private String activityId;
	
	private Date completeDate;
	
	private Date startDate;

	private Date createDate;

	private String executeUserId;

	private String description;

	private String preTaskName;

	private String preTaskUserId;

	private String comment;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getExecuteUserId() {
		return executeUserId;
	}

	public void setExecuteUserId(String executeUserId) {
		this.executeUserId = executeUserId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getPreTaskName() {
		return preTaskName;
	}

	public void setPreTaskName(String preTaskName) {
		this.preTaskName = preTaskName;
	}

	public String getPreTaskUserId() {
		return preTaskUserId;
	}

	public void setPreTaskUserId(String preTaskUserId) {
		this.preTaskUserId = preTaskUserId;
	}

	public String getActivityId() {
		return activityId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

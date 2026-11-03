package com.tansun.wf.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class WorkItem implements Serializable {


	private static final long serialVersionUID = -7362636889941587080L;
	/**
	 * 任务(活动实列)ID
	 */
	private   String   taskId;
	
	/**
	 * 流程实列ID
	 */
	private   String   processInsId;
	
	/**
	 * 任务（活动实列)名称
	 */
	private   String   taskName;
	
	
	/**
	 * 流程名称
	 */
	private   String   processDefName;
	
	
	/**
	 * 流程定义ID
	 */
	private  String   processDefineId;
	
	/**
	 * 任务实列创建时间
	 */
	private   String   createTime;
	
	/**
	 * 任务状态
	 */
	private   String   taskStatus;
	
	/**
	 * 执行表单KEY路径
	 */
	private   String   actionFormKey;
	
	/**
	 * 经办人
	 */
	private   String   participant;
	
	/**
	 * 到期日
	 */
	private   Date dueDate;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInsId() {
		return processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessDefName() {
		return processDefName;
	}

	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getActionFormKey() {
		return actionFormKey;
	}

	public void setActionFormKey(String actionFormKey) {
		this.actionFormKey = actionFormKey;
	}



	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}
	
	
	
	

}

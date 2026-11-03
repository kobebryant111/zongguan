package com.tansun.workflow.model;

import java.util.Map;

public class TransRequest extends BaseBean {

	private static final long serialVersionUID = 1L;
    /**
               * 流程定义ID
     */
	String  processDefinitionId;
	/**
	 * 活动ID
	 */
	String  activityId;
	/**
	 * 流程实例ID
	 */
	String  processInstanceId;
	/**
	 * 租户ID
	 */
	String  tenantId;
	/**
	 * 任务id
	 */
	private String taskId;
	/**
	 * 流程变量
	 */
	Map<String,Object>  processVariables;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Map<String, Object> getProcessVariables() {
		return processVariables;
	}

	public void setProcessVariables(Map<String, Object> processVariables) {
		this.processVariables = processVariables;
	}
	

}

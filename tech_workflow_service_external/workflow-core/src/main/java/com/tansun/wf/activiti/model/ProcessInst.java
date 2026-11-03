package com.tansun.wf.activiti.model;

import java.io.Serializable;
/**
 * 
 * @author lixs
 *
 */
public class ProcessInst  implements Serializable{


	private static final long serialVersionUID = -6803507541395979090L;
	
	/**
	 * 流程名称
	 */
	private  String    processName;
	
	/**
	 * 流程定义ID
	 */
	private  String    processDefineId;
	
	/**
	 * 流程实列ID
	 */
	private  String      processInstanceId;
	
	private String currentTaskId;
	
	private   String     businessKey;
	private String activityId;
	private String classfyCode;
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getCurrentTaskId() {
		return currentTaskId;
	}

	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}


    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

	public String getClassfyCode() {
		return classfyCode;
	}

	public void setClassfyCode(String classfyCode) {
		this.classfyCode = classfyCode;
	}
}

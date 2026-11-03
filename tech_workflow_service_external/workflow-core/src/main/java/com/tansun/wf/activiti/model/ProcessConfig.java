package com.tansun.wf.activiti.model;

import java.io.Serializable;

import org.camunda.bpm.engine.impl.db.DbEntity;

/**
 * 流程配置
 * @author Administrator
 *
 */
public class ProcessConfig implements Serializable ,DbEntity  {
	public String id;
	public String procDefId;
	public String actId;
	public String actName;
	public String processStartedClass;
	public String processCompletedClass;
	public String basReceiveClass;
	public String basInvokeType;
	public String basDisposeType;
	public String parParticipantMode;
	public String parNode;
	public String parRole;
	public String parPersonnl;
	public String parRuleProject;
	public String parRuleCode;
	public String timeType;
	public String timeCalulation;
	public String timeRelative;
	public String timeDisposeType;
	public String timeRemindTime;
	public String timeRemindFrequency;
	public String timeRemindNum;
	public String timeRemindType;
	public String timeRemindText;
	public String nodeFunction;
	public String processStatus;
	public String userTaskAssignment;
	
	
	public String getUserTaskAssignment() {
		return userTaskAssignment;
	}
	public void setUserTaskAssignment(String userTaskAssignment) {
		this.userTaskAssignment = userTaskAssignment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getBasReceiveClass() {
		return basReceiveClass;
	}
	public void setBasReceiveClass(String basReceiveClass) {
		this.basReceiveClass = basReceiveClass;
	}
	public String getBasInvokeType() {
		return basInvokeType;
	}
	public void setBasInvokeType(String basInvokeType) {
		this.basInvokeType = basInvokeType;
	}
	public String getBasDisposeType() {
		return basDisposeType;
	}
	public void setBasDisposeType(String basDisposeType) {
		this.basDisposeType = basDisposeType;
	}
	public String getParParticipantMode() {
		return parParticipantMode;
	}
	public void setParParticipantMode(String parParticipantMode) {
		this.parParticipantMode = parParticipantMode;
	}
	public String getParNode() {
		return parNode;
	}
	public void setParNode(String parNode) {
		this.parNode = parNode;
	}
	public String getParRole() {
		return parRole;
	}
	public void setParRole(String parRole) {
		this.parRole = parRole;
	}
	public String getParPersonnl() {
		return parPersonnl;
	}
	public void setParPersonnl(String parPersonnl) {
		this.parPersonnl = parPersonnl;
	}
	public String getParRuleProject() {
		return parRuleProject;
	}
	public void setParRuleProject(String parRuleProject) {
		this.parRuleProject = parRuleProject;
	}
	public String getParRuleCode() {
		return parRuleCode;
	}
	public void setParRuleCode(String parRuleCode) {
		this.parRuleCode = parRuleCode;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getTimeCalulation() {
		return timeCalulation;
	}
	public void setTimeCalulation(String timeCalulation) {
		this.timeCalulation = timeCalulation;
	}
	public String getTimeRelative() {
		return timeRelative;
	}
	public void setTimeRelative(String timeRelative) {
		this.timeRelative = timeRelative;
	}
	public String getTimeDisposeType() {
		return timeDisposeType;
	}
	public void setTimeDisposeType(String timeDisposeType) {
		this.timeDisposeType = timeDisposeType;
	}
	public String getTimeRemindTime() {
		return timeRemindTime;
	}
	public void setTimeRemindTime(String timeRemindTime) {
		this.timeRemindTime = timeRemindTime;
	}
	public String getTimeRemindFrequency() {
		return timeRemindFrequency;
	}
	public void setTimeRemindFrequency(String timeRemindFrequency) {
		this.timeRemindFrequency = timeRemindFrequency;
	}
	public String getTimeRemindNum() {
		return timeRemindNum;
	}
	public void setTimeRemindNum(String timeRemindNum) {
		this.timeRemindNum = timeRemindNum;
	}
	public String getTimeRemindType() {
		return timeRemindType;
	}
	public void setTimeRemindType(String timeRemindType) {
		this.timeRemindType = timeRemindType;
	}
	public String getTimeRemindText() {
		return timeRemindText;
	}
	public void setTimeRemindText(String timeRemindText) {
		this.timeRemindText = timeRemindText;
	}
	public String getNodeFunction() {
		return nodeFunction;
	}
	public void setNodeFunction(String nodeFunction) {
		this.nodeFunction = nodeFunction;
	}
	public String getProcessStartedClass() {
		return processStartedClass;
	}
	public void setProcessStartedClass(String processStartedClass) {
		this.processStartedClass = processStartedClass;
	}
	public String getProcessCompletedClass() {
		return processCompletedClass;
	}
	public void setProcessCompletedClass(String processCompletedClass) {
		this.processCompletedClass = processCompletedClass;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	@Override
	public Object getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}

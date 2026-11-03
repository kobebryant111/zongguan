package com.tansun.wf.activiti.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tansun.workflow.model.PostInfo;

public class UserTaskInfo {
    /**
     * 任务键值
     */
    private String taskKey;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 是否为结束节点
     * Y:结束，N:用户节点
     */
    private String isEndNode = "N";

    /**
     * 会签变量
     */
    private String collectionVariable;

    /**
     * 网关表达式
     */
    private List<String> expressions;

    /**
     * 到达条件
     */
    private Map<String, Object> conditions;

    /**
     * 处理人
     * @return
     */
    private String assignee;

    /**
     * 候选人
     * @return
     */
    private List<String> candidateUsers;

    /**
     * 候选组
     * @return
     */
    private List<String> candidateGroups;

    /**
     * 参与人列表
     */
    private  List<PostInfo>  postInfos;


    /**
     * 是否会签
     */
    private String isMulti;

    /**
     * 是否在子流程中
     */
    private String isInSubProcess;

    /**
     * 流程定义id
     */
    private String processDefinitionId;



 



    public String getTaskKey() {
        return taskKey;
    }

    public String getCollectionVariable() {
        return collectionVariable;
    }

    public void setCollectionVariable(String collectionVariable) {
        this.collectionVariable = collectionVariable;
    }

    public String getTaskName() {
        return taskName;
    }

    public List<String> getExpressions() {
        return expressions;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setExpressions(List<String> condition) {
        this.expressions = condition;
    }

    public Map<String, Object> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }

    public String getIsEndNode() {
        return isEndNode;
    }

    public void setIsEndNode(String isEndNode) {
        this.isEndNode = isEndNode;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public List<String> getCandidateUsers() {
        return candidateUsers;
    }

    public void setCandidateUsers(List<String> candidateUsers) {
        this.candidateUsers = candidateUsers;
    }

    public List<String> getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(List<String> candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsInSubProcess(String isInSubProcess) {
        this.isInSubProcess = isInSubProcess;
    }

    public String getIsInSubProcess() {
        return isInSubProcess;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

	public List<PostInfo> getPostInfos() {
		if (postInfos == null) {
			postInfos = new  ArrayList<>();
		}
		return postInfos;
	}

	public void setPostInfos(List<PostInfo> postInfos) {
		this.postInfos = postInfos;
	}
    
}

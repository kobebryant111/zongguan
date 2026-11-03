package com.tansun.wf.cache;

import com.tansun.workflow.model.PostInfo;

import java.util.List;

public class UserTaskCache {

    private String appId;
    private String taskId;
    private String taskName;
    private String taskType;// new、update
    private String datailUrl;
    private String processInstanceId;
    private String activityId;
    private String processDefinitionId;
    private List<PostInfo> participants;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDatailUrl() {
        return datailUrl;
    }

    public void setDatailUrl(String datailUrl) {
        this.datailUrl = datailUrl;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public List<PostInfo> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PostInfo> participants) {
        this.participants = participants;
    }
}

package com.tansun.wf.activiti.model;

import org.camunda.bpm.engine.impl.db.DbEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HiIdentityLinkEntity implements Serializable ,DbEntity {

    private String id;
    private String groupId;
    private String processInstanceId;
    private String taskId;
    private String type;
    private String userId;

    @Override
    public Object getPersistentState() {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

package com.tansun.wf.activiti.model;

import org.camunda.bpm.engine.impl.db.DbEntity;

import java.io.Serializable;

public class TaskCountEntity implements Serializable, DbEntity {
    private String id;
    private String taskId;
    private String userId;
    private String total;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public Object getPersistentState() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

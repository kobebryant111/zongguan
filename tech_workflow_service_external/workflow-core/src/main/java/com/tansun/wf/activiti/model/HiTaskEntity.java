package com.tansun.wf.activiti.model;

import org.camunda.bpm.engine.impl.db.DbEntity;

import java.io.Serializable;

public class HiTaskEntity implements Serializable, DbEntity {
    private String id;
    private String taskId;
    private String assignee;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public Object getPersistentState() {
        // TODO Auto-generated method stub
        return null;
    }
}

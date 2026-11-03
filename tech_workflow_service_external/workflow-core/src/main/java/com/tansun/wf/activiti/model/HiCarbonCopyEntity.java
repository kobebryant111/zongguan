package com.tansun.wf.activiti.model;

import org.camunda.bpm.engine.impl.db.DbEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务抄送表
 */
public class HiCarbonCopyEntity implements Serializable ,DbEntity {

    private String id;

    private String taskId;

    private String procInstId;

    private String procDefId;

    /**
     * 抄送的类型，role:抄送给角色，user:抄送个用户
     */
    private String type;

    private String userId;

    private String roleId;

    private String isRead;

    private Date createTime;


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
        return null;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

package com.tansun.wf.platform.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tansun.wf.extend.db.WfBaseEntity;

import java.util.Date;

public class WfJobEntity extends WfBaseEntity {
    private String type;
    private String executionId;
    private String processInstanceId;
    private String processDefId;
    private String processDefKey;
    private String exceptionMsg;
    private String failedActId;
    private String handlerType;
    private String jobDefId;
    private String handlerCfg;
    private String deploymentId;
    private Integer suspensionState;
    private Integer priority;
    private String tenantId;
    private Integer rev;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer pageSize;
    private Integer pageNum;

    public Integer getRev() {
        return rev;
    }

    public void setRev(Integer rev) {
        this.rev = rev;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getFailedActId() {
        return failedActId;
    }

    public void setFailedActId(String failedActId) {
        this.failedActId = failedActId;
    }

    public String getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(String handlerType) {
        this.handlerType = handlerType;
    }

    public String getJobDefId() {
        return jobDefId;
    }

    public void setJobDefId(String jobDefId) {
        this.jobDefId = jobDefId;
    }

    public String getHandlerCfg() {
        return handlerCfg;
    }

    public void setHandlerCfg(String handlerCfg) {
        this.handlerCfg = handlerCfg;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Integer getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(Integer suspensionState) {
        this.suspensionState = suspensionState;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

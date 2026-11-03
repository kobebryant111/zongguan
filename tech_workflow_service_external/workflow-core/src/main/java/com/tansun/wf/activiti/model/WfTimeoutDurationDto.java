package com.tansun.wf.activiti.model;

/**
 * 超时时长统计返回
 */
public class WfTimeoutDurationDto {

    /** 流程名称 */
    private String modelName;

    /** 流程版本 */
    private String modelVersion;

    /** 流程key */
    private String modelKey;

    /** 活动名称 */
    private String actName;

    /** 活动参与人 */
    private String actUser;

    /** 活动创建时间 */
    private String actStartTime;

    /** 活动超时时间 */
    private String actTimeoutDiff;

    // =========================================

    /** 实例创建时间 */
    private String instStartTime;

    /** 实例发起人 */
    private String instUser;

    /** 实例超时时间 */
    private String instTimeoutDiff;

    /** 实例ID */
    private String procInstId;

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActUser() {
        return actUser;
    }

    public void setActUser(String actUser) {
        this.actUser = actUser;
    }

    public String getActStartTime() {
        return actStartTime;
    }

    public void setActStartTime(String actStartTime) {
        this.actStartTime = actStartTime;
    }

    public String getActTimeoutDiff() {
        return actTimeoutDiff;
    }

    public void setActTimeoutDiff(String actTimeoutDiff) {
        this.actTimeoutDiff = actTimeoutDiff;
    }

    public String getInstStartTime() {
        return instStartTime;
    }

    public void setInstStartTime(String instStartTime) {
        this.instStartTime = instStartTime;
    }

    public String getInstUser() {
        return instUser;
    }

    public void setInstUser(String instUser) {
        this.instUser = instUser;
    }

    public String getInstTimeoutDiff() {
        return instTimeoutDiff;
    }

    public void setInstTimeoutDiff(String instTimeoutDiff) {
        this.instTimeoutDiff = instTimeoutDiff;
    }
}

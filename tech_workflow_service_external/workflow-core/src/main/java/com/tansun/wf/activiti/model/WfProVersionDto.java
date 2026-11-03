package com.tansun.wf.activiti.model;

/**
 * 流程版本统计返回
 */
public class WfProVersionDto {

    /** 流程名称 */
    private String modelName;

    /** 当前流程版本 */
    private String currentVersion;

    /** 流程小计 */
    private long versionCnt;

    /** 流程key */
    private String modelKey;

    /** 流程创建时间 */
    private String proCreateTime;

    /** 最新版本发布时间 */
    private String lastCreateTime;

    /** 版本变更率 版本/天 */
    private String proUpdateRate;

    /** 流程创建至今的天数 */
    private long proCreateDays;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public long getVersionCnt() {
        return versionCnt;
    }

    public void setVersionCnt(long versionCnt) {
        this.versionCnt = versionCnt;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public String getProCreateTime() {
        return proCreateTime;
    }

    public void setProCreateTime(String proCreateTime) {
        this.proCreateTime = proCreateTime;
    }

    public String getLastCreateTime() {
        return lastCreateTime;
    }

    public void setLastCreateTime(String lastCreateTime) {
        this.lastCreateTime = lastCreateTime;
    }

    public String getProUpdateRate() {
        return proUpdateRate;
    }

    public void setProUpdateRate(String proUpdateRate) {
        this.proUpdateRate = proUpdateRate;
    }

    public long getProCreateDays() {
        return proCreateDays;
    }

    public void setProCreateDays(long proCreateDays) {
        this.proCreateDays = proCreateDays;
    }
}

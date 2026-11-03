package com.tansun.wf.activiti.model;

import java.util.Date;

/**
 * 流程活动执行时间统计qo
 */
public class WfActivityTimeQo {

    // yyyy
    private String searchTimeBegin;

    // yyyy
    private String searchTimeEnd;

    /** 查询类型：2按流程活动切片 3按月切片（一次只查一个流程版本的一个活动） */
    private int qtype;

    /** 流程键值 */
    private String modelKey;

    /** 流程版本 */
    private String modelVersion;

    /** 流程名称 */
    private String modelName;

    /** 流程活动ID */
    private String actId;

    private Date searchTimeBeginDate;
    private Date searchTimeEndDate;
    private String procDefIdLike;

    public String getSearchTimeBegin() {
        return searchTimeBegin;
    }

    public void setSearchTimeBegin(String searchTimeBegin) {
        this.searchTimeBegin = searchTimeBegin;
    }

    public String getSearchTimeEnd() {
        return searchTimeEnd;
    }

    public void setSearchTimeEnd(String searchTimeEnd) {
        this.searchTimeEnd = searchTimeEnd;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public Date getSearchTimeBeginDate() {
        return searchTimeBeginDate;
    }

    public void setSearchTimeBeginDate(Date searchTimeBeginDate) {
        this.searchTimeBeginDate = searchTimeBeginDate;
    }

    public Date getSearchTimeEndDate() {
        return searchTimeEndDate;
    }

    public void setSearchTimeEndDate(Date searchTimeEndDate) {
        this.searchTimeEndDate = searchTimeEndDate;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getProcDefIdLike() {
        return procDefIdLike;
    }

    public void setProcDefIdLike(String procDefIdLike) {
        this.procDefIdLike = procDefIdLike;
    }
}

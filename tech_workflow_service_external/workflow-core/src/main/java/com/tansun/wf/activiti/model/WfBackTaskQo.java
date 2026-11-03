package com.tansun.wf.activiti.model;

import java.util.Date;
import java.util.List;

/**
 * 流程退回统计qo
 */
public class WfBackTaskQo {

    // yyyy
    private String searchTimeBegin;

    // yyyy
    private String searchTimeEnd;

    /** 查询类型：1按流程版本分片统计 2按流程版本下活动分片统计 */
    private int qtype;

    /** 流程实体 */
    private WfVersionVo entity;

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

    public WfVersionVo getEntity() {
        return entity;
    }

    public void setEntity(WfVersionVo entity) {
        this.entity = entity;
    }

    public String getProcDefIdLike() {
        return procDefIdLike;
    }

    public void setProcDefIdLike(String procDefIdLike) {
        this.procDefIdLike = procDefIdLike;
    }

}

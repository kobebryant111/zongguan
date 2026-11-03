package com.tansun.wf.activiti.model;

import java.util.Date;
import java.util.List;

/**
 * 流程超时率统计qo
 */
public class WfTimeoutRateQo {

    // yyyy
    private String searchTimeBegin;

    // yyyy
    private String searchTimeEnd;

    /** 查询类型：1总体 2按流程版本（一次只查一个流程） 3按月（一次只查一个流程的一个版本） */
    private int qtype;

    /** 查询实体列表 */
    private List<WfVersionVo> entitys;

    private Date searchTimeBeginDate;
    private Date searchTimeEndDate;
    private WfVersionVo entity;

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

    public List<WfVersionVo> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<WfVersionVo> entitys) {
        this.entitys = entitys;
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

}

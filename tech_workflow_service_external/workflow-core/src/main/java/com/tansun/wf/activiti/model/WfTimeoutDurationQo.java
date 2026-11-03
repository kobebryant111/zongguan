package com.tansun.wf.activiti.model;

import java.util.Date;

/**
 * 超时时长统计
 */
public class WfTimeoutDurationQo {

    // yyyy
    private String searchTimeBegin;

    // yyyy
    private String searchTimeEnd;

    // yyyy-MM
    private String searchTimeDetail;

    /** 实体信息 */
    private WfVersionVo entity;

    /** 查询类型：1查询实例 2查询活动 */
    private int qtype;

    /** 页码 */
    private int page;

    /** 分页大小 */
    private int pageSize;

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

    public String getSearchTimeDetail() {
        return searchTimeDetail;
    }

    public void setSearchTimeDetail(String searchTimeDetail) {
        this.searchTimeDetail = searchTimeDetail;
    }

    public WfVersionVo getEntity() {
        return entity;
    }

    public void setEntity(WfVersionVo entity) {
        this.entity = entity;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public String getProcDefIdLike() {
        return procDefIdLike;
    }

    public void setProcDefIdLike(String procDefIdLike) {
        this.procDefIdLike = procDefIdLike;
    }
}

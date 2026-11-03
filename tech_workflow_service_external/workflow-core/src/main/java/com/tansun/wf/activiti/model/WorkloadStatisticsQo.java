package com.tansun.wf.activiti.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 查询工作量统计 query vo
 */
public class WorkloadStatisticsQo {

    // yyyy
    private String searchTimeBegin;

    // yyyy
    private String searchTimeEnd;

    /** 查询类型：1总体 2按年 3按月 */
    private int qtype;

    /** 查询实体 */
    private WorkloadStatisticsVo entity;

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public WorkloadStatisticsVo getEntity() {
        return entity;
    }

    public void setEntity(WorkloadStatisticsVo entity) {
        this.entity = entity;
    }

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
}

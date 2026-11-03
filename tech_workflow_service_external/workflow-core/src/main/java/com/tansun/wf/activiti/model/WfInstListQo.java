package com.tansun.wf.activiti.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class WfInstListQo {
    // 页码
    private String pageNum;
    // 分页大小
    private String pageSize;
    // 是否需要完成的流程 - finish：完成 unfinish：未完成
    private String ignoreFinish;
    // 租户编号
    private String tenantId;
    // 流程定义ID
    private String processDefineId;
    // 流程定义标识
    private String processDefineKey;
    // 流程实例ID
    private String superProcessInstanceId;
    // 流程定义名称
    private String processName;
    // 业务主键
    private String businessKey;
    // 是否需要按照启动时间排序 - desc：倒序 asc：正序
    private String startTimeSort;
    // 是否需要按照结束时间排序 - desc：倒序 asc：正序
    private String endTimeSort;
    // 流程实列ID
    private String processInstanceId;
    // 启动时间 - 始
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startedBefore;
    // 启动时间 - 尾
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startedAfter;
    // 结束时间 - 始
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishedBefore;
    // 结束时间 - 尾
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishedAfter;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getIgnoreFinish() {
        return ignoreFinish;
    }

    public void setIgnoreFinish(String ignoreFinish) {
        this.ignoreFinish = ignoreFinish;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProcessDefineId() {
        return processDefineId;
    }

    public void setProcessDefineId(String processDefineId) {
        this.processDefineId = processDefineId;
    }

    public String getProcessDefineKey() {
        return processDefineKey;
    }

    public void setProcessDefineKey(String processDefineKey) {
        this.processDefineKey = processDefineKey;
    }

    public String getSuperProcessInstanceId() {
        return superProcessInstanceId;
    }

    public void setSuperProcessInstanceId(String superProcessInstanceId) {
        this.superProcessInstanceId = superProcessInstanceId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getStartTimeSort() {
        return startTimeSort;
    }

    public void setStartTimeSort(String startTimeSort) {
        this.startTimeSort = startTimeSort;
    }

    public String getEndTimeSort() {
        return endTimeSort;
    }

    public void setEndTimeSort(String endTimeSort) {
        this.endTimeSort = endTimeSort;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getStartedBefore() {
        return startedBefore;
    }

    public void setStartedBefore(Date startedBefore) {
        this.startedBefore = startedBefore;
    }

    public Date getStartedAfter() {
        return startedAfter;
    }

    public void setStartedAfter(Date startedAfter) {
        this.startedAfter = startedAfter;
    }

    public Date getFinishedBefore() {
        return finishedBefore;
    }

    public void setFinishedBefore(Date finishedBefore) {
        this.finishedBefore = finishedBefore;
    }

    public Date getFinishedAfter() {
        return finishedAfter;
    }

    public void setFinishedAfter(Date finishedAfter) {
        this.finishedAfter = finishedAfter;
    }
}

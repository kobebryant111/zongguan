package com.tansun.wf.platform.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tansun.wf.extend.db.WfBaseEntity;

import java.util.Date;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/6/1
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * JsonInclude是类注解,表示该类实例化的对象里，值为null的字段不参与序列化
 * ----------------------------------------------
 * </pre>
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WfActHiTaskinstEntity extends WfBaseEntity {
    private static final long serialVersionUID = -2390646882887647603L;
    /** PROC_DEF_ID_ */
    private String procDefId;
    /** TASK_DEF_KEY_ */
    private String taskDefKey;
    /** PROC_INST_ID_ */
    private String procInstId;
    /** EXECUTION_ID_ */
    private String executionId;
    /** NAME_ */
    private String name;
    /** PARENT_TASK_ID_ */
    private String parentTaskId;
    /** DESCRIPTION_ */
    private String description;
    /** OWNER_ */
    private String owner;
    /** ASSIGNEE_ */
    private String assignee;
    /** START_TIME_ */
    private Date startTime;
    /** CLAIM_TIME_ */
    private Date claimTime;
    /** END_TIME_ */
    private Date endTime;
    /** DURATION_ */
    private Long duration;
    /** DELETE_REASON_ */
    private String deleteReason;
    /** PRIORITY_ */
    private String priority;
    /** DUE_DATE_ */
    private Date dueDate;
    /** FORM_KEY_ */
    private String formKey;
    /** CATEGORY_ */
    private String category;
    /** TENANT_ID */
    private String tenantId;

    private String dataSourceType;
    private String procDefName;
    private Long countNum;
    private Long avgTime;
    private Long maxTime;
    private String avgTimeStr;
    private String maxTimeStr;
    private String orderType;
    private String beginDate;
    private String endDate;
    private String organiza;

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getProcDefName() {
        return procDefName;
    }

    public void setProcDefName(String procDefName) {
        this.procDefName = procDefName;
    }

    public Long getCountNum() {
        return countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }

    public Long getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Long avgTime) {
        this.avgTime = avgTime;
    }

    public Long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Long maxTime) {
        this.maxTime = maxTime;
    }

    public String getAvgTimeStr() {
        return avgTimeStr;
    }

    public void setAvgTimeStr(String avgTimeStr) {
        this.avgTimeStr = avgTimeStr;
    }

    public String getMaxTimeStr() {
        return maxTimeStr;
    }

    public void setMaxTimeStr(String maxTimeStr) {
        this.maxTimeStr = maxTimeStr;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrganiza() {
        return organiza;
    }

    public void setOrganiza(String organiza) {
        this.organiza = organiza;
    }
}

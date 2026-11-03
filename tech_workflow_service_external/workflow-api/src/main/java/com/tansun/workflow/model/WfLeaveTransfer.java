package com.tansun.workflow.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 离岗移交Entity
 *
 * @author cyn
 * @version 2018-04-09
 */
public class WfLeaveTransfer {

    private static final long serialVersionUID = 1L;
    private String id;
    private String classifyId;  // 分类ID
    private String classifyName;// 分类名称
    @JSONField(name = "operationUserId")
    private String operationUserId; //操作人
    private String procInstId;    // 流程实例ID
    private String procInstName;// 流程名称
    private String userId;        // 登录名
    private String transferId;    // 交接人
    private Date startTime;        // 开始时间
    private Date endTime;        // 结束时间
    private String status;        // 状态
    private String startStatus; // 执行状态
    private String describe;    // 描述

    private String startTimeStr;// 针对接口
    private String endTimeStr;  // 针对接口
    private String setStatus;    // 流程设置或分类设置状态
    private String ids;

    private String retCode;
    private String retMsg;
    private String tenantId ;

    private String isTransfer;  //是否永久移交
    private String isPower;    //移交人是否还有权限
    private String isTransferHistoricalTasks; //是否移交历史任务

    public String getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(String operationUserId) {
        this.operationUserId = operationUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public Date getStartTime() {
        return startTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(String startStatus) {
        this.startStatus = startStatus;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getSetStatus() {
        return setStatus;
    }

    public void setSetStatus(String setStatus) {
        this.setStatus = setStatus;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcInstName() {
        return procInstName;
    }

    public void setProcInstName(String procInstName) {
        this.procInstName = procInstName;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getIsPower() {
        return isPower;
    }

    public void setIsPower(String isPower) {
        this.isPower = isPower;
    }

    public String getIsTransferHistoricalTasks() {
        return isTransferHistoricalTasks;
    }

    public void setIsTransferHistoricalTasks(String isTransferHistoricalTasks) {
        this.isTransferHistoricalTasks = isTransferHistoricalTasks;
    }

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
    
    
}
package com.tansun.wf.platform.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tansun.wf.extend.db.WfBaseEntity;

import java.util.Date;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/9
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfLeaveTransferEntity extends WfBaseEntity {


    /**
     *
     */
    private static final long serialVersionUID = -5858042429256404453L;
    /**
     * 分类ID
     */
    private String classifyId;
    /**
     * 分类名称
     */
    private String classifyName;
    /**
     * 操作人
     */
    private String operationUserId;
    /**
     * 登录名
     */
    private String userId;
    /**
     * 交接人
     */
    private String transferId;
    /**
     * 开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 执行状态
     */
    private String startStatus;
    /**
     * 描述
     */
    private String describe;

    /**
     * 管理员ID
     */
    private String rootId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 是否永久移交
     */
    private String isTransfer;
    /**
     * 是否具有权限
     */
    private String isPower;
    /**
     * 是否移交历史任务
     */
    private String isTransferHistoricalTasks;
    /**
     * 流程变量实例id
     */
    private String procInstId;
    public String getIsTransferHistoricalTasks() {
        return isTransferHistoricalTasks;
    }

    public void setIsTransferHistoricalTasks(String isTransferHistoricalTasks) {
        this.isTransferHistoricalTasks = isTransferHistoricalTasks;
    }

    public String getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(String operationUserId) {
        this.operationUserId = operationUserId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

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

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
}

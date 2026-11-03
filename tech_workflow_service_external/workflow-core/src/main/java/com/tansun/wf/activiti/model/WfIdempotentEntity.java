package com.tansun.wf.activiti.model;

import org.camunda.bpm.engine.impl.db.DbEntity;

import java.util.Date;
import java.io.Serializable;

/**
 * 幂等操作记录表(WfIdempotentEntity)实体类
 *
 * @since 2018-10-15 17:43:50
 */
public class WfIdempotentEntity implements Serializable, DbEntity {
    private static final long serialVersionUID = -37517712835100114L;
    //主键
    private String id;
    //交易ID
    private String transactionId;
    //方法名
    private String methodName;
    //请求参数
    private String reqeustParams;
    //返回结果
    private String responseResult;
    //状态：0成功，1失败
    private String state;
    //请求开始时间
    private Date startTime;
    //请求结束时间
    private Date endTime;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object getPersistentState() {
        return null;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReqeustParams() {
        return reqeustParams;
    }

    public void setReqeustParams(String reqeustParams) {
        this.reqeustParams = reqeustParams;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
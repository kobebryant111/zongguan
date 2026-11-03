package com.tansun.wf.decision.model;

import java.io.Serializable;

/**
 * TODO 去除不必要字段
 */
public class ActExpandTracelog implements Serializable {
	private static final long serialVersionUID = -161123581321345589L;

	protected String id;// 主键 [64,0]
	protected String bussinessKey;// 业务主键 [255,0]
	protected String processInstanceId;// 流程实例ID [64,0]
	protected String nodeId;// 节点ID [255,0]
	protected String nodeName;// 节点名称 [255,0]
	protected String invokeTimes;// 调用时间 [255,0]
	protected String invokeType;// 调用类型 [255,0]
	protected String isSucess;// Y:成功，N:失败 [8,0]
	protected String expandId;// 扩展日志信息ID [64,0]
	protected String legalPersonNum;// 法人编号 [255,0]
	protected String errorContent;// 错误详情 [65535,0]
	protected String cause;// 堆栈 [65535,0]
	protected String inputParam;// 入参 [65535,0]
	protected String syncResult;// 同步返回结果 [65535,0]
	protected String asyncResult;// 异步返回结果 [65535,0]

	public ActExpandTracelog() {
		super();
	}

	/** 取值 <== 主键 [64,0] */
	public String getId() {
		return id;
	}

	/** 赋值 ==> 主键 [64,0] */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	/** 取值 <== 业务主键 [255,0] */
	public String getBussinessKey() {
		return bussinessKey;
	}

	/** 赋值 ==> 业务主键 [255,0] */
	public void setBussinessKey(String bussinessKey) {
		this.bussinessKey = bussinessKey == null ? null : bussinessKey.trim();
	}

	/** 取值 <== 流程实例ID [64,0] */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/** 赋值 ==> 流程实例ID [64,0] */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
	}

	/** 取值 <== 节点ID [255,0] */
	public String getNodeId() {
		return nodeId;
	}

	/** 赋值 ==> 节点ID [255,0] */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId == null ? null : nodeId.trim();
	}

	/** 取值 <== 节点名称 [255,0] */
	public String getNodeName() {
		return nodeName;
	}

	/** 赋值 ==> 节点名称 [255,0] */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName == null ? null : nodeName.trim();
	}

	/** 取值 <== 调用时间 [255,0] */
	public String getInvokeTimes() {
		return invokeTimes;
	}

	/** 赋值 ==> 调用时间 [255,0] */
	public void setInvokeTimes(String invokeTimes) {
		this.invokeTimes = invokeTimes == null ? null : invokeTimes.trim();
	}

	/** 取值 <== 调用类型 [255,0] */
	public String getInvokeType() {
		return invokeType;
	}

	/** 赋值 ==> 调用类型 [255,0] */
	public void setInvokeType(String invokeType) {
		this.invokeType = invokeType == null ? null : invokeType.trim();
	}

	/** 取值 <== Y:成功，N:失败 [8,0] */
	public String getIsSucess() {
		return isSucess;
	}

	/** 赋值 ==> Y:成功，N:失败 [8,0] */
	public void setIsSucess(String isSucess) {
		this.isSucess = isSucess == null ? null : isSucess.trim();
	}

	/** 取值 <== 扩展日志信息ID [64,0] */
	public String getExpandId() {
		return expandId;
	}

	/** 赋值 ==> 扩展日志信息ID [64,0] */
	public void setExpandId(String expandId) {
		this.expandId = expandId == null ? null : expandId.trim();
	}

	/** 取值 <== 错误详情 [65535,0] */
	public String getErrorContent() {
		return errorContent;
	}

	/** 赋值 ==> 错误详情 [65535,0] */
	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}

	/** 取值 <== 堆栈 [65535,0] */
	public String getCause() {
		return cause;
	}

	/** 赋值 ==> 堆栈 [65535,0] */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/** 取值 <== 入参 [65535,0] */
	public String getInputParam() {
		return inputParam;
	}

	/** 赋值 ==> 入参 [65535,0] */
	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
	}

	/** 取值 <== 同步返回结果 [65535,0] */
	public String getSyncResult() {
		return syncResult;
	}

	/** 赋值 ==> 同步返回结果 [65535,0] */
	public void setSyncResult(String syncResult) {
		this.syncResult = syncResult;
	}

	/** 取值 <== 异步返回结果 [65535,0] */
	public String getAsyncResult() {
		return asyncResult;
	}

	/** 赋值 ==> 异步返回结果 [65535,0] */
	public void setAsyncResult(String asyncResult) {
		this.asyncResult = asyncResult;
	}

	public String getLegalPersonNum() {
		return legalPersonNum;
	}

	public void setLegalPersonNum(String legalPersonNum) {
		this.legalPersonNum = legalPersonNum;
	}
}
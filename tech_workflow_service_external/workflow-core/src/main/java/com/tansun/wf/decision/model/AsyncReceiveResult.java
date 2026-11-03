package com.tansun.wf.decision.model;


import java.util.Map;

/**
 * 异步返回结果对象
 */
public class AsyncReceiveResult {
	//是否成功 Y:成功/N:失败
	private String isSuccess ="Y";
	//执行实例ID
	private String executionId;
	
	private String nodeId;
	
	private String nodeName;
	
	//流程实例Id
	private String processInstanceId;
	//错误信息
	private String errorContent;
	//返回数据
	private Map<String,Object> data;
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getErrorContent() {
		return errorContent;
	}
	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}
	public Map<String,Object> getData() {
		return data;
	}
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}



package com.tansun.wf.activiti.model;

import java.io.Serializable;
/**
 * 流程定义信息
 * @author admin
 *
 */
public class ProcessDefinitionVO implements Serializable {


	private static final long serialVersionUID = -7963541715941429242L;
	
	/**
	 * 流程名
	 */
	private  String   processName;
	
	/**
	 * 流程定义KEY
	 */
	private  String   key;
	
	
	/**
	 * 流程定义Id
	 */
	private  String   processDefinitionId;
	
	/**
	 * 版本
	 */
	private  int    version;
	
	
	/**
	 * 部署时间
	 */
	private   String   deployDateTime;
	
	/**
	 * 是否挂起
	 */
	boolean  isSuspended;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}



	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDeployDateTime() {
		return deployDateTime;
	}

	public void setDeployDateTime(String deployDateTime) {
		this.deployDateTime = deployDateTime;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}
	
	
	

}

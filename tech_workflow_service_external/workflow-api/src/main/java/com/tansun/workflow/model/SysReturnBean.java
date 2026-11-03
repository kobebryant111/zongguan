package com.tansun.workflow.model;

import java.util.List;

/**
 * 租户表
 */
public class SysReturnBean {
	
	private String total;
	private String currentPageIndex;
	private WfTenantInfo wfTenantInfo;
	private List wfTenantList;
	
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(String currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	public WfTenantInfo getWfTenantInfo() {
		return wfTenantInfo;
	}
	public void setWfTenantInfo(WfTenantInfo wfTenantInfo) {
		this.wfTenantInfo = wfTenantInfo;
	}
	public List getWfTenantList() {
		return wfTenantList;
	}
	public void setWfTenantList(List wfTenantList) {
		this.wfTenantList = wfTenantList;
	}
	
	
	
	
	
}
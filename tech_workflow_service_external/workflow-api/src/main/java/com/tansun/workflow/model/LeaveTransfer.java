package com.tansun.workflow.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 离岗移交Entity
 * @author cyn
 * @version 2018-04-09
 */
public class LeaveTransfer implements Serializable {
	
	private String id;
	private String classifyId;	// 流程分类ID
	private String classifyName;// 流程分类名称
	private String procInstId;	// 流程实例ID
	private String procInstName;// 流程名称
	private String userId;		// 登录名
	private String transferId;	// 交接人
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String status;		// 状态
	private String startStatus;	// 发起执行状态
	private String describe;	// 描述
	private Date time;			// 当前时间
	private String setStatus;	// 流程设置或分类设置状态
	private String rootId;		// 管理员ID
	private String roleCode;
	private String roleName;
	private String deptCode;
	private String deptName;
	private String orgCode;
	private String orgName;
	private String userName;
	private String transferRoleCode;
	private String transferRoleName;
	private String transferDeptCode;
	private String transferDeptName;
	private String transferOrgCode;
	private String transferOrgName;
	private String transferUserName;

	private String startTimeStr;// 针对接口
	private String endTimeStr;  // 针对接口
	private String retCode;
	private String retMsg;
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
	
//	public String getExecute() {
//		return execute;
//	}
//
//	public void setExecute(String execute) {
//		this.execute = execute;
//	}

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

	public String getSetStatus() {
		return setStatus;
	}

	public void setSetStatus(String setStatus) {
		this.setStatus = setStatus;
	}
	
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransferRoleCode() {
		return transferRoleCode;
	}

	public void setTransferRoleCode(String transferRoleCode) {
		this.transferRoleCode = transferRoleCode;
	}

	public String getTransferRoleName() {
		return transferRoleName;
	}

	public void setTransferRoleName(String transferRoleName) {
		this.transferRoleName = transferRoleName;
	}

	public String getTransferDeptCode() {
		return transferDeptCode;
	}

	public void setTransferDeptCode(String transferDeptCode) {
		this.transferDeptCode = transferDeptCode;
	}

	public String getTransferDeptName() {
		return transferDeptName;
	}

	public void setTransferDeptName(String transferDeptName) {
		this.transferDeptName = transferDeptName;
	}

	public String getTransferOrgCode() {
		return transferOrgCode;
	}

	public void setTransferOrgCode(String transferOrgCode) {
		this.transferOrgCode = transferOrgCode;
	}

	public String getTransferOrgName() {
		return transferOrgName;
	}

	public void setTransferOrgName(String transferOrgName) {
		this.transferOrgName = transferOrgName;
	}

	public String getTransferUserName() {
		return transferUserName;
	}

	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
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
}
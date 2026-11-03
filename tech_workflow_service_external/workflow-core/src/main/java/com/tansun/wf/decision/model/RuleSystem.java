package com.tansun.wf.decision.model;


/**
 * 规则引擎返回值   System属性
 */
public class RuleSystem {
	private String exceptionType;
	private String exception;
	private String invokeId;
	private String invokeTimes;
	private String isSuccess;
	private String ruleExecutResultList;
	
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getInvokeId() {
		return invokeId;
	}
	public void setInvokeId(String invokeId) {
		this.invokeId = invokeId;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getRuleExecutResultList() {
		return ruleExecutResultList;
	}
	public void setRuleExecutResultList(String ruleExecutResultList) {
		this.ruleExecutResultList = ruleExecutResultList;
	}
	public String getInvokeTimes() {
		return invokeTimes;
	}
	public void setInvokeTimes(String invokeTimes) {
		this.invokeTimes = invokeTimes;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
}
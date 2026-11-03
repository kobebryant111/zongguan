package com.tansun.wf.decision.model;

public class BaseVo {
	public static final String ERROR_CODE = "E999986";
	public static final String SUCCESS_CODE = "000000";
	public static final String SUCCESS_MSG = "OK";

	private String returnCode = "000000";
	private String returnMsg = "OK";

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}

package com.tansun.workflow.model;

//import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 0表示成功，1表示失败
	 */
//	@ApiModelProperty(value = "返回结果编号",example = "0表示成功，1表示失败",notes = "这是notes",name = "这是name")
	private String retCode;

//	@ApiModelProperty(value = "返回结果")
	private String retMsg;
	private String transNo;

	/**
	 * 交易id,每次交易id不一样,重试的交易一样
	 * @ignore
	 */
	private  String transactionId;

	/**
	 * 幂等操作后，用来表示是否重复结果，
	 * true:重复结果
	 * @ignore
	 */
	private String isRepeatResult;

	/**
	 * 分库表的切分字段值
	 * @ignore
	 */
	private String sliceValue;


	public BaseBean() {
	}

	public BaseBean(String retCode, String retMsg) {
		super();
		this.retCode = retCode;
		this.retMsg = retMsg;
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

	public String getTransNo() {
		return this.transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getIsRepeatResult() {
		return isRepeatResult;
	}

	public void setIsRepeatResult(String isRepeatResult) {
		this.isRepeatResult = isRepeatResult;
	}

	public String getSliceValue() {
		return sliceValue;
	}

	public void setSliceValue(String sliceValue) {
		this.sliceValue = sliceValue;
	}

	public static boolean isSuccess(BaseBean baseBean ){
		if (baseBean != null && "0".equals(baseBean.getRetCode())) {
			return true;
		}else {
			return false;
		}
	}
}

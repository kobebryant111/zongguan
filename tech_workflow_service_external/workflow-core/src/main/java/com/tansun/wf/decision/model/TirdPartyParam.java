package com.tansun.wf.decision.model;


/**
 * 标准参数与动态参数映射类
 */
public class TirdPartyParam {
	
	private static final String ZH = "zh";

	/**
	 * 标准key
	 */
	private String standardKey;
	
	/**
	 * 变化的key
	 */
	private String changeKey;
	
	/**
	 * 固定值：只要该值不为空，就使用该值
	 */
	private String fixedValue;
	
	private String name;
	

	public String getStandardKey() {
		return standardKey;
	}


	public String getChangeKey() {
		return changeKey;
	}


	public String getName() {
		return name;
	}


	public void setStandardKey(String standardKey) {
		this.standardKey = standardKey;
	}


	public void setChangeKey(String changeKey) {
		this.changeKey = changeKey;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFixedValue() {
		return fixedValue;
	}


	public void setFixedValue(String fixedValue) {
		this.fixedValue = fixedValue;
	}
	
}
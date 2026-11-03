package com.tansun.wf.activiti.model;

import java.io.Serializable;

public class FormType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7859572596991637303L;

	private String name;
	 
	private  Object  information;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getInformation() {
		return information;
	}

	public void setInformation(Object information) {
		this.information = information;
	}
}

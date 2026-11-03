package com.tansun.wf.activiti.model;

import java.io.Serializable;
import java.util.List;

public class TaskFormDataVO implements Serializable {

	private static final long serialVersionUID = 7535837227362008306L;
	
	private   String   formKey;
	
	
	private  List<FormPropertyVO>  formPropertyList;


	public String getFormKey() {
		return formKey;
	}


	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}


	public List<FormPropertyVO> getFormPropertyList() {
		return formPropertyList;
	}


	public void setFormPropertyList(List<FormPropertyVO> formPropertyList) {
		this.formPropertyList = formPropertyList;
	}
	
	
	
	

}

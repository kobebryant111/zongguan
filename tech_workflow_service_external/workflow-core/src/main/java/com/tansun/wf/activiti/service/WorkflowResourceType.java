package com.tansun.wf.activiti.service;

public enum WorkflowResourceType {
	XML("xml"),  IMAGE("image");
	
	String   name;
	
	 private  WorkflowResourceType(String name){
		   this.name  = name;
	 }
}

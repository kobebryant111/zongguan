package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmFhkjDeptRecord {
	/**
	*	id
	**/
	@Id
	private String id;
	/**
	*	部门id
	**/
	private String depid;
	/**
	*	部门名称
	**/
	private String deptname;
	/**
	*	0:分行1:子公司
	**/
	private String status;
}

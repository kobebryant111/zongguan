package com.work.ppm.application.model.systemmanager;

import lombok.Data;


 /**
  * 描述
  */
@Data
public class ImUserRoleRel {
	/**
	*	字段名
	**/
	private String urrUserid;
	/**
	*	字段名
	**/
	private String urrRoleid;
	 /**
	  * 系统类别
	  *  1:新应用,2:供应商管理系统
	  */
	 private Integer sysType;
}

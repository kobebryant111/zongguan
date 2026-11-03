package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 角色
  */
@Data
public class ImRole {
	/**
	*	字段名
	**/
	private String rolId;
	/**
	*	字段名
	**/
	private String rolName;
	/**
	*	字段名
	**/
	private String rolDesc;
	/**
	*	字段名
	**/
	private Integer rolLevel;
	/**
	*	字段名
	**/
	private String rolGroupId;
	/**
	*	字段名
	**/
	private Date rolCreateTime;
	/**
	*	字段名
	**/
	private String rolParentId;
	/**
	*	字段名
	**/
	private String rolCreateUser;
	 /**
	  * 系统类别
	  *  1:新应用,2:供应商管理系统
	  */
	 private Integer sysType;
	 /**
	  是否特殊菜单(0:特殊，1：正常)
	  */
	 private Integer falgSpecialRole;
}

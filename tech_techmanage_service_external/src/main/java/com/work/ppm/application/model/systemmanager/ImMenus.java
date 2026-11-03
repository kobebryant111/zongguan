package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class ImMenus {
	/**
	*	字段名
	**/
	private String routeid;
	/**
	*	字段名
	**/
	private String mid;
	/**
	*	字段名
	**/
	private String name;
	/**
	*	字段名
	**/
	private Integer type;
	/**
	*	字段名
	**/
	private String url;
	/**
	*	字段名
	**/
	private String code;
	/**
	*	字段名
	**/
	private String ordernum;
	/**
	*	字段名
	**/
	private String parentid;
	/**
	*	字段名
	**/
	private String icon;
	/**
	*	字段名
	**/
	private String readme;
	/**
	*	字段名
	**/
	private Date createdate;
	/**
	*	字段名
	**/
	private Date modifydate;

	 /**
	  * 系统类别
	  *  1:新应用,2:供应商管理系统
	  */
	 private Integer sysType;
}

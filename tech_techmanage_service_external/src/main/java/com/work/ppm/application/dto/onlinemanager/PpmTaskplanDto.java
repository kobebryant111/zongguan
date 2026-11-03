package com.work.ppm.application.dto.onlinemanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmTaskplanDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String plannoPpm;
	/**
	*	字段名
	**/
	private String planno;
	/**
	*	字段名
	**/
	private String planname;
	/**
	*	字段名
	**/
	private String sysno;
	/**
	*	字段名
	**/
	private String planuser;
	/**
	*	字段名
	**/
	private String createuser;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private Date plandate;
	/**
	*	字段名
	**/
	private Date actualdate;
	/**
	*	字段名
	**/
	private String detail;
	/**
	*	字段名
	**/
	private String state;
	/**
	*	字段名
	**/
	private String plantype;
	 /**
	  * 系统名称
	  */
	 private String sysname;
	 /**
	  * 上线用户名称
	  */
	 private String planusername;
	 /**
	  * 创建用户名称
	  */
	 private String createusername;
}

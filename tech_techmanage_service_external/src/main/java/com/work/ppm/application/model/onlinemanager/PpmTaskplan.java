package com.work.ppm.application.model.onlinemanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 计划上线窗口和版本
  */
@Data
public class PpmTaskplan {
	/**
	*	字段名
	**/
	@Id
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
}

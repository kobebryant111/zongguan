package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class ImPermissions {
	/**
	*	字段名
	**/
	@Id
	private String id;
	/**
	*	字段名
	**/
	private String rid;
	/**
	*	字段名
	**/
	private String mid;
	/**
	*	字段名
	**/
	private Date createdate;

	 /**
	  * 系统类别
	  *  1:新应用,2:供应商管理系统
	  */
	 private Integer sysType;
}

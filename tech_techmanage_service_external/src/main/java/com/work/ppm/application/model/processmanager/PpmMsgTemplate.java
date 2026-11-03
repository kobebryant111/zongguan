package com.work.ppm.application.model.processmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmMsgTemplate {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String templateno;
	/**
	*	字段名
	**/
	private String templatename;
	/**
	*	字段名
	**/
	private String flowname;
	/**
	*	字段名
	**/
	private String templatetype;
	/**
	*	字段名
	**/
	private String content;
	/**
	*	字段名
	**/
	private Long isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
}

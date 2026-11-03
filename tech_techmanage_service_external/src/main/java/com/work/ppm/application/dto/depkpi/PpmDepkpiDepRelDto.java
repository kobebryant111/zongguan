package com.work.ppm.application.dto.depkpi;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmDepkpiDepRelDto {
	/**
	*	主键
	**/
	private Long id;
	/**
	*	部门编号
	**/
	private String depid;
	/**
	*	主持工作经理
	**/
	private String director;
	/**
	*	部门分管领导//分管总经理
	**/
	private String depcharge;
	/**
	*	中心总经理
	**/
	private String centercharge;
	/**
	*	中心id
	**/
	private String centerid;
	/**
	*	年份
	**/
	private String tyear;
	/**
	*	是否删除
	**/
	private Long isdel;
	/**
	*	最后修改人
	**/
	private String lastmoduserid;
	/**
	*	最后修改时间
	**/
	private Date lastmodtime;
	/**
	*	初审/初评人
	**/
	private String firstcheck;
	/**
	*	终审/终评人
	**/
	private String secondcheck;
	/**
	*	考核制定人
	**/
	private String marker;
	/**
	 * 多个id
	  */
	private String ids;
	 /**
	  * 模板名称
	  **/
	 private String modelname;
	 /**
	  * 中心名称
	  **/
	 private String centername;
	 /**
	  * 部门名称
	  **/
	 private String depname;

	 /**
	  * 类别
	  **/
	 private Long sign;

}

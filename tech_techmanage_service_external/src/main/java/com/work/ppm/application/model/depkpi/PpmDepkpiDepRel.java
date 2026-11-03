package com.work.ppm.application.model.depkpi;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmDepkpiDepRel {
	/**
	*	主键
	**/
	@Id
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
	*	1:部门 2：中心 3：一级部 
	**/
	private Long sign;
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
 }

package com.work.ppm.application.dto.kpi;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiDepRoleDto {
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
	*	1:部门 2：中心
	**/
	private Long sign;

	 /**
	  * 多个主键已，分割
	  */
	 private String ids;
}

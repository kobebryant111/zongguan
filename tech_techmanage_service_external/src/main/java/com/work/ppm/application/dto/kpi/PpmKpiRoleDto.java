package com.work.ppm.application.dto.kpi;

import lombok.Data;
import java.util.Date;



 /**
  * 人员角色
  */
@Data
public class PpmKpiRoleDto {
	/**
	*	主键
	**/
	private Long id;
	/**
	*	工号
	**/
	private String badge;
	/**
	*	姓名
	**/
	private String empname;
	/**
	*	角色
	**/
	private String role;
	/**
	*	岗位
	**/
	private String jobid;
	/**
	*	部门编号
	**/
	private String depid;
	/**
	*	中心编号
	**/
	private String centerid;
	/**
	*	头衔
	**/
	private String headship;
	/**
	*	一级审核评价人
	**/
	private String firstcheck;
	/**
	*	二级审核评价人
	**/
	private String secondcheck;
	/**
	*	是否删除
	**/
	private Long isdel;
	/**
	*	年份
	**/
	private String tyear;
	/**
	*	最后修改人
	**/
	private String lastmoduserid;
	/**
	*	最后修改时间
	**/
	private Date lastmodtime;
	 /**
	  *	主键ID已，分割
	  **/
	 private String ids;
}

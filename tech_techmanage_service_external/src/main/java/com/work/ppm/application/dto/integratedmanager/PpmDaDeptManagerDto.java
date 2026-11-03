package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;


/**
 * 分管关系对应表
 */
@Data
public class PpmDaDeptManagerDto {
	/**
	*	字段名
	**/
	private String id;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	字段名
	**/
	private String deptid;
	/**
	*	字段名
	**/
	private String status;
	/**
	*	中心ID
	**/
	private String centerid;
	/**
	*	部门架构师人员id
	**/
	private String dejgsuserid;
	/**
	*	部门架构师人员姓名
	**/
	private String dejgsusername;
}

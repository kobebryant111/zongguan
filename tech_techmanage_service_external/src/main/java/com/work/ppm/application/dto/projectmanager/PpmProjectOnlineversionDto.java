package com.work.ppm.application.dto.projectmanager;

import lombok.Data;


 /**
  * 描述
  */
@Data
public class PpmProjectOnlineversionDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	上线编号
	**/
	private String onlineno;
	/**
	*	项目编号
	**/
	private String projectno;
	/**
	*	子项目编号
	**/
	private String subprojectno;
	/**
	*	版本申请阶段上线状态
	**/
	private String versiontype;
	/**
	*	字段名
	**/
	private String isdel;
}

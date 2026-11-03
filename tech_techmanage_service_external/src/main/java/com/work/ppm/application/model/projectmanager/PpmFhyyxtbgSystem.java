package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmFhyyxtbgSystem {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String refinstanceid;
	/**
	*	系统编号
	**/
	private String sysno;
	/**
	*	系统名称
	**/
	private String sysname;
	/**
	*	系统负责人名称
	**/
	private String tecuserid;
	/**
	*	系统负责人
	**/
	private String tecusername;
	/**
	*	变更状态
	**/
	private String changestatus;
	/**
	*	子项目编号
	**/
	private String subprojectno;
}

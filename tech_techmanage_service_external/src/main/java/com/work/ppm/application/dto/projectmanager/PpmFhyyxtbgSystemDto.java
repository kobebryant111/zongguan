package com.work.ppm.application.dto.projectmanager;

import com.work.ppm.commons.utils.StringUtil;
import lombok.Data;


 /**
  * 描述
  */
@Data
public class PpmFhyyxtbgSystemDto {
	/**
	*	字段名
	**/
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

	 /**
	  * 系统名称
	  */
	 private String sysName;
	 /**
	  * 系统编号
	  */
	 private String systemId;
	 /**
	  * 负责人名称
	  */
	 private String principalaName;
	 /**
	  * 负责人编号
	  */
	 private String principala;
	 /**
	  * 变更状态
	  */
	 private String ischange;

	 /**
	  *get
	  * @return String
	  */
	 public String getSysName() {
		 return StringUtil.isNotBlank(sysname) ? sysname : sysName;
	 }

 }

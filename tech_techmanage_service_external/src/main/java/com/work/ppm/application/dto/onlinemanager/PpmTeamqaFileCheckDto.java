package com.work.ppm.application.dto.onlinemanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmTeamqaFileCheckDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	字段名
	**/
	private String instid;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String createuser;
	/**
	*	字段名
	**/
	private String flowtype;
	/**
	*	字段名
	**/
	private String isdel;
}

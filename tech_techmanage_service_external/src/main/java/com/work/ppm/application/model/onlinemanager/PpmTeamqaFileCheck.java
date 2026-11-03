package com.work.ppm.application.model.onlinemanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmTeamqaFileCheck {
	/**
	*	字段名
	**/
	@Id
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

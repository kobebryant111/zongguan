package com.work.ppm.application.model.onlinemanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmOnlineGnd {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String onlineno;
	/**
	*	字段名
	**/
	private Date planeonline;
	/**
	*	字段名
	**/
	private String projectno;
	/**
	*	字段名
	**/
	private String subprojectno;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	字段名
	**/
	private Long wkgnd;
	/**
	*	字段名
	**/
	private String workload;
}

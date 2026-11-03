package com.work.ppm.application.dto.flowmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmFlowGrantDto {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String flowdraftno;
	/**
	*	字段名
	**/
	private String flowname;
	/**
	*	字段名
	**/
	private String sourceuserid;
	/**
	*	字段名
	**/
	private String targetuserid;
	/**
	*	字段名
	**/
	private Date startdate;
	/**
	*	字段名
	**/
	private Date enddate;
	/**
	*	字段名
	**/
	private String iseffect;
	/**
	*	字段名
	**/
	private Integer isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
	/**
	*	字段名
	**/
	private String sourceusername;
	/**
	*	字段名
	**/
	private String targetusername;
	/**
	*	字段名
	**/
	private String note;
	/**
	*	字段名
	**/
	private Integer isqy;
	 /**
	  * 时间
	  */
	private String date;
}

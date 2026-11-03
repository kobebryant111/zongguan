package com.work.ppm.application.model.flowmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmProjectFlow {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String projectno;
	/**
	*	字段名
	**/
	private String flowtitle;
	/**
	*	字段名
	**/
	private String flowname;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	字段名
	**/
	private String flowid;
	/**
	*	字段名
	**/
	private String flowurl;
	/**
	*	字段名
	**/
	private String flowcreateuserid;
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
	private String flowstatus;
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
	private String clr;
	/**
	*	字段名
	**/
	private String activityname;
	/**
	*	字段名
	**/
	private String clrid;
	/**
	*	流程节点id
	**/
	private String activityid;
}

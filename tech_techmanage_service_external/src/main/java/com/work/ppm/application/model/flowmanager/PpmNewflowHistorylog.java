package com.work.ppm.application.model.flowmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 历史流程日志
  */
@Data
public class PpmNewflowHistorylog {
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
	private String flowcaseno;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	字段名
	**/
	private Date examine;
	/**
	*	字段名
	**/
	private String conclusion;
	/**
	*	字段名
	**/
	private String suggest;
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
	private String applydesc;
	/**
	*	字段名
	**/
	private String deptno;
	/**
	*	字段名
	**/
	private String isfalse;
	/**
	*	字段名
	**/
	private String roleid;
}

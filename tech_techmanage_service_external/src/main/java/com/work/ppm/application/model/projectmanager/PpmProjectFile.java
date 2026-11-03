package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmProjectFile {
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
	private String userid;
	/**
	*	字段名
	**/
	private Date apply;
	/**
	*	字段名
	**/
	private String deptno;
	/**
	*	字段名
	**/
	private Date reviewtime;
	/**
	*	字段名
	**/
	private String operatetype;
	/**
	*	字段名
	**/
	private Date planonline;
	/**
	*	字段名
	**/
	private String projectname;
	/**
	*	字段名
	**/
	private String onlineno;
	/**
	*	字段名
	**/
	private String sysno;
	/**
	*	字段名
	**/
	private String busiuserid;
	/**
	*	字段名
	**/
	private String busideptno;
	/**
	*	字段名
	**/
	private String tectdeptno;
	/**
	*	字段名
	**/
	private String tecuserid;
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
}

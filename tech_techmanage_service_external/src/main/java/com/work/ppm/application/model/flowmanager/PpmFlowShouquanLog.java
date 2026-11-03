package com.work.ppm.application.model.flowmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmFlowShouquanLog {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String processinstid;
	/**
	*	字段名
	**/
	private String workitemid;
	/**
	*	字段名
	**/
	private String shouquanren;
	/**
	*	字段名
	**/
	private String beishouquanren;
	/**
	*	字段名
	**/
	private String processinstname;
	/**
	*	字段名
	**/
	private String processdefname;
	/**
	*	字段名
	**/
	private String activitydefid;
	/**
	*	字段名
	**/
	private Long isdel;
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

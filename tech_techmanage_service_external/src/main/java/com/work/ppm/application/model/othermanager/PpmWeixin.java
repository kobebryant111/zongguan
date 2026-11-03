package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmWeixin {
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
	private String workitemid;
	/**
	*	字段名
	**/
	private String safecode;
	/**
	*	字段名
	**/
	private String flowname;
	/**
	*	字段名
	**/
	private String flowtext;
	/**
	*	字段名
	**/
	private String users;
	/**
	*	字段名
	**/
	private String status;
	/**
	*	字段名
	**/
	private String fromuser;
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
	private String activityinstid;
	/**
	*	字段名
	**/
	private String activityinstname;
}

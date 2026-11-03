package com.work.ppm.application.dto.systemmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmSystParameterDto {
	/**
	* ID
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String key;
	/**
	*	字段名
	**/
	private String value;
	/**
	*	字段名
	**/
	private String remark;
	/**
	*	1-????0-???
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

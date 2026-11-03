package com.work.ppm.application.dto.othermanager;

import lombok.Data;
import java.util.Date;



 /**
  * 处理工作日
  */
@Data
public class PpmProcessWorkdayDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String year;
	/**
	*	字段名
	**/
	private String unworkdate;
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
	/**
	*	节假日标志
	**/
	private String holdayflag;
}

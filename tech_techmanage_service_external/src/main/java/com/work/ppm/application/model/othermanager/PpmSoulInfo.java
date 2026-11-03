package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmSoulInfo {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	角色
	**/
	private String roles;
	/**
	*	鸡汤
	**/
	private String content;
	/**
	*	加班开始时间
	**/
	private String starttime;
	/**
	*	加班结束时间
	**/
	private String endtime;
	/**
	*	字段名
	**/
	private String style;
}

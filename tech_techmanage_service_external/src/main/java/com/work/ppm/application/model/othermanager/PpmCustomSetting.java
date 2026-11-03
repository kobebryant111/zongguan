package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmCustomSetting {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	字段名
	**/
	private String name;
	/**
	*	字段名
	**/
	private String flowid;
	/**
	*	是否推送邮件，1-是，0-否
	**/
	private String isyj;
	/**
	*	是否推送短信，1-是，0-否
	**/
	private String isdx;
	/**
	*	是否推送微信，1-是，0-否
	**/
	private String iswx;
}

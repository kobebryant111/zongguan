package com.work.ppm.application.dto.depkpi;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmDepKpiConfigurationItemDto {
	/**
	*	主键
	**/
	private Long id;
	/**
	*	考核内容
	**/
	private String content;
	/**
	*	是否发起0：未发起 1：已发起
	**/
	private Long islaunch;
	/**
	*	自动发起是否开关0：关闭 1：开启
	**/
	private Long isswitch;
	/**
	*	部门考核类型
	**/
	private String itype;
	/**
	*	自动发起时间MM-dd
	**/
	private String autolaunchtime;
	/**
	*	发起时间
	**/
	private String launchtime;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	是否禁用0：警用 1：不禁用
	**/
	private Long isdisabled;
	/**
	*	周期
	**/
	private String term;
	/**
	*	发起路径
	**/
	private String launchurl;
	/**
	*	发起方式
	**/
	private Long launchtype;
}

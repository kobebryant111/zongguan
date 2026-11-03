package com.work.ppm.application.dto.kpi;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiItemSwitchItemDto {
	/**
	*	主键
	**/
	private Long id;
	/**
	*	时间
	**/
	private String term;
	/**
	*	内容
	**/
	private String content;
	/**
	*	相关角色
	**/
	private String relroles;
	/**
	*	是否发起0：未发起 1：已发起
	**/
	private Long islaunch;
	/**
	*	是否开关0：关闭 1：开启
	**/
	private Long isswitch;
	/**
	*	完成时间MM-dd
	**/
	private String finishtime;
	/**
	*	考核开关类型
	**/
	private String itype;
	/**
	*	创建时间
	**/
	private Date launchtime;
	/**
	*	是否禁用0：警用 1：不禁用
	**/
	private Long isdisabled;
	/**
	*	是否显示0：显示 1：不显示
	**/
	private Long isvisible;
	/**
	*	配置项类型
	**/
	private String itemtype;
	 /**
	  *	结束时间
	  **/
	 private String endtime;
}

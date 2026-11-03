package com.work.ppm.application.dto.kpi;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiItemSwitchDto {
	/**
	*	创建时间
	**/
	private Date launchtime;
	/**
	*	是否禁用0：禁用 1：不禁用
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
	*	完成时间MM-dd
	**/
	private String finishtime;
	/**
	*	主键
	**/
	private Long id;
	/**
	*	年份
	**/
	private String tyear;
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
	*	更新人
	**/
	private String uptuserid;
	/**
	*	更新时间
	**/
	private Date uptdate;
	/**
	*	考核开关类型
	**/
	private String itype;
	 /**
	  *	结束时间
	  **/
	 private String endtime;
}

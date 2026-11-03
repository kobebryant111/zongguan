package com.work.ppm.application.dto.flowmanager;

import lombok.Data;


 /**
  * 描述
  */
@Data
public class PpmSlaSetinfoDto {
	/**
	*	字段名
	**/
	private String id;
	/**
	*	流程类型id
	**/
	private String flowdraftno;
	/**
	*	流程类型名称
	**/
	private String flowdraftname;
	/**
	*	所属部门id
	**/
	private String depno;
	/**
	*	所属部门名称
	**/
	private String depname;
	/**
	*	节点id
	**/
	private String flownodecode;
	/**
	*	节点名称
	**/
	private String flownodename;
	/**
	*	是否预超时提醒
	**/
	private String ispretimeout;
	/**
	*	预超时提醒时间
	**/
	private String pretime;
	/**
	*	是否短信
	**/
	private String ispretimesms;
	/**
	*	短信模板
	**/
	private String pretimesms;
	/**
	*	是否邮件
	**/
	private String ispretimemail;
	/**
	*	邮件模板
	**/
	private String pretimemail;
	/**
	*	是否工作平台
	**/
	private String ispretimework;
	/**
	*	工作平台模板
	**/
	private String pretimework;
	/**
	*	是否超时提醒
	**/
	private String istimeout;
	/**
	*	超时提醒时间
	**/
	private String timeout;
	/**
	*	是否短信
	**/
	private String istimeoutsms;
	/**
	*	短信模板
	**/
	private String timeoutsms;
	/**
	*	是否邮件
	**/
	private String istimeoutmail;
	/**
	*	邮件模板
	**/
	private String timeoutmail;
	/**
	*	是否工作平台
	**/
	private String istimeoutwork;
	/**
	*	工作平台模板
	**/
	private String timeoutwork;
	/**
	*	是否人工催办
	**/
	private String isurgent;
	/**
	*	是否实时催办
	**/
	private String isacturgent;
	/**
	*	人工催办时间
	**/
	private String urgenttime;
	/**
	*	是否升级提醒1级
	**/
	private String isupgradeone;
	/**
	*	升级提醒时间
	**/
	private String upgradeone;
	/**
	*	是否管理层
	**/
	private String ismanagerone;
	/**
	*	通知人员
	**/
	private String upgradeoneusernos;
	/**
	*	特定通知人员
	**/
	private String upgradeoneusertype;
	/**
	*	是否短信
	**/
	private String isupgradeonesms;
	/**
	*	短信模板
	**/
	private String upgradeonesms;
	/**
	*	是否邮件
	**/
	private String isupgradeonemail;
	/**
	*	邮件模板
	**/
	private String upgradeonemail;
	/**
	*	是否工作平台
	**/
	private String isupgradeonework;
	/**
	*	工作平台模板
	**/
	private String upgradeonework;
	/**
	*	当前处理人通知方式是否短信
	**/
	private String isupgradeonecurrentsms;
	/**
	*	当前处理人通知方式短信模板
	**/
	private String upgradeonecurrentsms;
	/**
	*	当前处理人通知方式是否邮件
	**/
	private String isupgradeonecurrentmail;
	/**
	*	当前处理人通知方式邮件模板
	**/
	private String upgradeonecurrentmail;
	/**
	*	当前处理人通知方式是否工作平台
	**/
	private String isupgradeonecurrentwork;
	/**
	*	当前处理人通知工作平台模板
	**/
	private String upgradeonecurrentwork;
	/**
	*	是否升级提醒2
	**/
	private String isupgradetwo;
	/**
	*	升级提醒时间
	**/
	private String upgradetwo;
	/**
	*	是否管理层
	**/
	private String ismanagertwo;
	/**
	*	通知人员
	**/
	private String upgradetwousernos;
	/**
	*	特定通知人员
	**/
	private String upgradetwousertype;
	/**
	*	是否短信
	**/
	private String isupgradetwosms;
	/**
	*	短信模板
	**/
	private String upgradetwosms;
	/**
	*	是否邮件
	**/
	private String isupgradetwomail;
	/**
	*	邮件模板
	**/
	private String upgradetwomail;
	/**
	*	是否工作平台
	**/
	private String isupgradetwowork;
	/**
	*	工作平台模板
	**/
	private String upgradetwowork;
	/**
	*	当前处理人通知方式是否短信
	**/
	private String isupgradetwocurrentsms;
	/**
	*	当前处理人通知方式短信模板
	**/
	private String upgradetwocurrentsms;
	/**
	*	当前处理人通知方式是否邮件
	**/
	private String isupgradetwocurrentmail;
	/**
	*	当前处理人通知方式邮件模板
	**/
	private String upgradetwocurrentmail;
	/**
	*	当前处理人通知方式是否工作平台
	**/
	private String isupgradetwocurrentwork;
	/**
	*	当前处理人通知工作平台模板
	**/
	private String upgradetwocurrentwork;
	/**
	*	是否升级提醒3
	**/
	private String isupgradethree;
	/**
	*	升级提醒时间
	**/
	private String upgradethree;
	/**
	*	是否管理层
	**/
	private String ismanagerthree;
	/**
	*	通知人员
	**/
	private String upgradethreeusernos;
	/**
	*	特定通知人员
	**/
	private String upgradethreeusertype;
	/**
	*	是否短信
	**/
	private String isupgradethreesms;
	/**
	*	短信模板
	**/
	private String upgradethreesms;
	/**
	*	是否邮件
	**/
	private String isupgradethreemail;
	/**
	*	邮件模板
	**/
	private String upgradethreemail;
	/**
	*	是否工作平台
	**/
	private String isupgradethreework;
	/**
	*	工作平台模板
	**/
	private String upgradethreework;
	/**
	*	当前处理人通知方式是否短信
	**/
	private String isupgradethreecurrentsms;
	/**
	*	当前处理人通知方式短信模板
	**/
	private String upgradethreecurrentsms;
	/**
	*	当前处理人通知方式是否邮件
	**/
	private String isupgradethreecurrentmail;
	/**
	*	当前处理人通知方式邮件模板
	**/
	private String upgradethreecurrentmail;
	/**
	*	当前处理人通知方式是否工作平台
	**/
	private String isupgradethreecurrentwork;
	/**
	*	当前处理人通知工作平台模板
	**/
	private String upgradethreecurrentwork;
	/**
	*	通知人员名称
	**/
	private String upgradeoneusernames;
	/**
	*	通知人员名称
	**/
	private String upgradetwousernames;
	/**
	*	通知人员名称
	**/
	private String upgradethreeusernames;
	/**
	*	是否支持延期申请
	**/
	private String ispost;
	/**
	*	是否设置预期完成时间
	**/
	private String issetplandate;
	/**
	*	天数
	**/
	private String days;
	/**
	*	类型 02 自然日 01工作日
	**/
	private String daytype;
	/**
	*	是否审批节点
	**/
	private String issp;
}

package com.work.ppm.application.model.datamanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 密管设备
  */
@Data
public class PpmPwdShebei {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	系统
	**/
	private String systemname;
	/**
	*	用户类型
	**/
	private String usertype;
	/**
	*	设备ip
	**/
	private String ip;
	/**
	*	用户名
	**/
	private String username;
	/**
	*	密码
	**/
	private String pwd;
	/**
	*	密管登陆用户
	**/
	private String loginuser;
	/**
	*	到期时间
	**/
	private Date endtime;
	/**
	*	字段名
	**/
	private String status;
	/**
	*	申请人
	**/
	private String applyer;
	/**
	*	应用名称
	**/
	private String servename;
	/**
	*	字段名
	**/
	private String dealcode;
	/**
	*	开始使用时间
	**/
	private Date starttime;
	/**
	*	归还日期
	**/
	private Date checkindate;
	/**
	*	字段名
	**/
	private String hascheckin;
	/**
	*	通知状态
	**/
	private String remindstatus;
}

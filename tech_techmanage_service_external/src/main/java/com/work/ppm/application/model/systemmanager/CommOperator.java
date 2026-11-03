package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class CommOperator {
	/**
	*	主键
	**/
	@Id
	private Long operatorid;
	/**
	*	员工工号
	**/
	private String userid;
	/**
	*	登录名称
	**/
	private String operatorname;
	/**
	*	密码
	**/
	private String password;
	 /**
	  * 旧密码
	  */
	private String oldPassword;
	/**
	*	认证模式
	**/
	private String authmode;
	/**
	*	状态 neffect:无权限  running:有权限  lock:锁定  stop:用户失效
	**/
	private String status;
	/**
	*	解锁时间
	**/
	private Date unlocktime;
	/**
	*	菜单风格
	**/
	private String menutype;
	/**
	*	最后登陆时间
	**/
	private Date lastlogin;
	/**
	*	错误次数
	**/
	private Long errcount;
	/**
	*	开始时间
	**/
	private Date startdate;
	/**
	*	结束时间
	**/
	private Date enddate;
	/**
	*	生效时间
	**/
	private Date validtime;
	/**
	*	失效时间
	**/
	private Date invaldate;
	/**
	*	MAC地址
	**/
	private String maccode;
	/**
	*	IP地址
	**/
	private String ipaddress;
	/**
	*	EMAIL
	**/
	private String email;
	/**
	*	兼职id
	**/
	private Long partid;
	/**
	*	用户类型
	**/
	private String userType;
}

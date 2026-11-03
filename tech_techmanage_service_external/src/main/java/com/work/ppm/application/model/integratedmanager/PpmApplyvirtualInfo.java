package com.work.ppm.application.model.integratedmanager;

import lombok.Data;


 /**
  * 虚拟机桌面信息
  */
@Data
public class PpmApplyvirtualInfo {
	/**
	*	流程id
	**/
	private String flowid;
	/**
	*	人员类型
	**/
	private String userType;
	/**
	*	用户名
	**/
	private String username;
	/**
	*	vd账号
	**/
	private String usercode;
	/**
	*	项目经理
	**/
	private String xmjl;
	/**
	*	所属项目
	**/
	private String ssxm;
	/**
	*	配置项
	**/
	private String pzx;
	/**
	*	其他
	**/
	private String others;
	/**
	*	虚拟机号
	**/
	private String virtualNo;
	/**
	*	状态
	**/
	private String status;
	/**
	*	创建时间
	**/
	private String createtime;
	/**
	*	id
	**/
	private String id;
	/**
	*	到期日期
	**/
	private String duedate;
	/**
	*	状态
	**/
	private String state;
	/**
	*	所属部门
	**/
	private String dept;
	/**
	*	项目经理工号
	**/
	private String xmjlcode;
}

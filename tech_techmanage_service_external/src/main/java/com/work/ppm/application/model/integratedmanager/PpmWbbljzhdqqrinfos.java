package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 堡垒机用户报表-外包账号
  */
@Data
public class PpmWbbljzhdqqrinfos {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	用户ID
	**/
	private String userid;
	/**
	*	用户名称
	**/
	private String username;
	/**
	*	用户组
	**/
	private String userclass;
	/**
	*	部门编号
	**/
	private String deptno;
	/**
	*	部门名称
	**/
	private String deptname;
	/**
	*	状态    0:注销   1:使用中
	**/
	private String status;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	账号使用期限
	**/
	private Date zhsyqx;
}

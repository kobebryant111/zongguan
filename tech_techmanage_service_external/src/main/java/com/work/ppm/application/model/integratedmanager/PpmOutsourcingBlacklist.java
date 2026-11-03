package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 外包黑名单
  */
@Data
public class PpmOutsourcingBlacklist {
	/**
	*	姓名
	**/
	private String userName;
	/**
	*	身份证
	**/
	private String icard;
	/**
	*	二级部
	**/
	private String depId;
	/**
	*	供应商
	**/
	private String orgCode;
	/**
	*	离职时间
	**/
	private Date officeDate;
	/**
	*	原因
	**/
	private String why;
	/**
	*	创建时间
	**/
	private Date addDate;
	/**
	*	创建人工号
	**/
	private String addUserId;
	/**
	*	创建人名称
	**/
	private String addUserName;
	/**
	*	是否删除（0:正常，1：删除）
	**/
	private Integer isdel;
	/**
	*	自增编号
	**/
	@Id
	private Long id;
}

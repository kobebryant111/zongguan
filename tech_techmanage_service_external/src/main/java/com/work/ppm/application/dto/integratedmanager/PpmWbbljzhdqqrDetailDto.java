package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;


 /**
  * 外包堡垒机账号到期确认详情
  */
@Data
public class PpmWbbljzhdqqrDetailDto {
	/**
	*	状态    0：注销   1：使用中
	**/
	private String status;
	/**
	*	堡垒机账号申请流程ID
	**/
	private String bljapplyinstid;
	/**
	*	主键
	**/
	private Long id;
	/**
	*	流程ID
	**/
	private String instid;
	/**
	*	用户ID
	**/
	private String userid;
	/**
	*	用户名称
	**/
	private String username;
	/**
	*	手机号
	**/
	private String phone;
	/**
	*	用户组
	**/
	private String userclass;
	/**
	*	延期/注销
	**/
	private String yqorzx;
	/**
	*	是否删除  0：否  1：是
	**/
	private String isdel;
}

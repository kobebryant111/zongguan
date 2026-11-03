package com.work.ppm.application.dto.systemmanager;

import lombok.Data;


 /**
  * 分行密码权限
  */
@Data
public class PpmFhpwdXqDto {
	/**
	*	机构
	**/
	private String compid;
	/**
	*	管理员权限
	**/
	private String admindepid;
	/**
	*	非管理员权限
	**/
	private String depid;
}

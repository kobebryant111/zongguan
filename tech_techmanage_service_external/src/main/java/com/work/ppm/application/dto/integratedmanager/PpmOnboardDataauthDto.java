package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;


 /**
  * 入职部门分配数据权限
  */
@Data
public class PpmOnboardDataauthDto {
	/**
	*	自增编号
	**/
	private Integer id;
	/**
	*	中心id
	**/
	private Integer centerId;
	/**
	*	工号
	**/
	private String userId;
	/**
	*	名称
	**/
	private String userName;
}

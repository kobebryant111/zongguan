package com.work.ppm.application.model.systemmanager;

import lombok.Data;


 /**
  * 系统详情用户关系表 实体对象
  */
@Data
public class PpmSystemUserInfo {
	/**
	*	系统ID
	**/
	private Long sysId;
	/**
	*	系统负责人工号
	**/
	private String badge;
	/**
	*	系统负责人工号
	**/
	private String empName;
}

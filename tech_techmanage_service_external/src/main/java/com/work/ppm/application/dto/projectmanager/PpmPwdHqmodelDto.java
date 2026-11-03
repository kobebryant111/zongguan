package com.work.ppm.application.dto.projectmanager;

import lombok.Data;


/**
 * 密管密码获取模式
 */
@Data
public class PpmPwdHqmodelDto {
	/**
	*	流程类型
	**/
	private String flowid;
	/**
	*	密码审批模式
	**/
	private String spmodel;
	/**
	*	年度计划更新日是否审批
	**/
	private String gxrissp;
	/**
	*	字段名
	**/
	private String status;
}

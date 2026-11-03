package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


/**
 * 密管密码获取模式
 */
@Data
public class PpmPwdHqmodel {
	/**
	*	流程类型
	**/
	@Id
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

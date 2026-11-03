package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


/**
 * 分行密码权限
 */
@Data
public class PpmFhpwdXq {
	/**
	*	机构
	**/
	@Id
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

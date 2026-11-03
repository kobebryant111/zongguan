package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

import java.util.Date;


/**
 * 角色
 * @date 2020-02-21
 *
 */
@Data
public class ImRoleDto {
	/**
	 *	角色深度
	 */
    private Integer rolLevel;
	/**
	 * 父类
	 */
	private String rolParentId;
	/**
	 *	id
	 */
	private String rolId;
	/**
	 *	名称
	 */
	private String rolName;
	/**
	 *	备注
	 */
	private String rolDesc;
	/**
	 *	分组id
	 */
	private String rolGroupId;
	/**
	 *	创建者
	 */
	private String rolCreateUser;
	/**
	 *	创建时间
	 */
	private Date rolCreateTime;
	/**
	 * sql 执行状态 add update del
	 */
	private String sqlStatus;
	/**
	 * 系统类别
	 *  1:新应用,2:供应商管理系统
	 */
	private Integer sysType;
	/**
	 是否特殊菜单(0:特殊，1：正常)
	 */
	private Integer falgSpecialRole;
}

package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;

import java.util.Date;


/**
 * 描述
 * @date 2020-09-08
 *
 */
@Data
public class ManpowerdemandsInfoDto {
	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 名称
	 */
	private String username;
	/**
	 * 类型
	 */
	private String usertype;
	/**
	 * 流程id
	 */
	private String instanceid;
	/**
	 * 字段名
	 */
	private String usernum;
	/**
	 * 时间
	 */
	private Date supplytime;
	/**
	 * 部门编号
	 */
	private String depid;
	/**
	 * 类型名称
	 */
	private String usertypename;
	/**
	 *	部门名称
	 */
	private String depname;

}

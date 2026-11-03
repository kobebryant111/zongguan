package com.work.ppm.application.model.integratedmanager.ext;

import lombok.Data;


/**
 * 人员基本信息表
 */
@Data
public class PpmEmpBaseInfo {
	/**
	 * 工号
	 */
	private String badge;
	/**
	 *	姓名
	 */
	private String empname;
	/**
	 *	机构编号
	 */
	private String compid;
	/**
	 *	部门id
	 */
	private String depid;
	/**
	 *	部门名称
	 */
	private String depname;
	/**
	 *	岗位id
	 */
	private String jobid;
	/**
	 *	头衔
	 */
	private String headship;
	/**
	 *	员工 编制
	 */
	private String empauth;
	/**
	 *	办公电话
	 */
	private String officephone;
	/**
	 *	 移动电话
	 */
	private String mobiletelphone;
	/**
	 *	员工代码hr
	 */
	private String eidhr;
	/**
	 *	条线
	 */
	private Integer boardline;
	/**
	 *	全名
	 */
	private String fullname;
	/**
	 *	简称
	 */
	private String spellname;
	/**
	 * 编制属性
	 */
	private String authproperty;
	/**
	 * 员工状态
	 */
	private String empstatus;
	/**
	 *	流程id
	 */
	private String flowid;
	/**
	 * 是否文员
	 */
	private String flag;
	/**
	 * 文员所负责部门（已，分割）
	 */
	private String deps;
}

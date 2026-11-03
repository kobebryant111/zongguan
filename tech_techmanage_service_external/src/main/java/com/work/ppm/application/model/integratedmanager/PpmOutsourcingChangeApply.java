package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 外包信息变更表
 */
@Data
public class PpmOutsourcingChangeApply {
	/**
	 * 主键
	 */
	@Id
	private Long id;
	/**
	 *	申请人
	 */
	private String applyer;
	/**
	 *	流程id
	 */
	private String flowid;
	/**
	 *	部门编号
	 */
	private String deptno;
	/**
	 *	申请时间
	 */
	private Date applytime;
	/**
	 *	申请工号
	 */
	private String applyid;
	/**
	 *	部门名称
	 */
	private String deptname;
	/**
	 *	流程id
	 */
	private String instanceid;
	/**
	 *	实际变更时间
	 */
	private Date actChangetime;
	/**
	 *	最后修改人
	 */
	private String lastmoduserid;
	/**
	 *	最后修改时间
	 */
	private Date lastmodtime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 *	变更原因
	 */
	private String changereason;
	/**
	 *	是否符合
	 */
	private String isfit;
	/**
	 *	面试
	 */
	private String isinterview;
}
